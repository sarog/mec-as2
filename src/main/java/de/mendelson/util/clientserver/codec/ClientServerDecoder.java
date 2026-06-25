//$Header: /mec_as2/de/mendelson/util/clientserver/codec/ClientServerDecoder.java 57    15/04/26 12:43 Heller $
package de.mendelson.util.clientserver.codec;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.cbor.databind.CBORMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.ClientSessionHandlerCallback;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManager;
import java.io.IOException;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Decodes the client server communication for mendelson products
 *
 * @author S.Heller
 * @version $Revision: 57 $
 */
public class ClientServerDecoder extends CumulativeProtocolDecoder {

    //max size for a single message is 200MB
    protected static final long ABSOLUTE_MAX_PAYLOAD_SIZE = 200 * 1024 * 1024;
    //The current protocol version has a header per chunk, this is the size of it
    protected static final int HEADER_SIZE_IN_BYTES = 49;
    //max string length for a single entry in the json is 20MB
    private static final int MAX_STRING_LENGTH = 20 * 1024 * 1024;
    /**
     * It is critical to keep this value at 16kB. This aligns with the maximum
     * size of a single TLS record (the TLS packet limit). Larger values would
     * force the SSL/TLS filter to fragment the application chunks into multiple
     * TLS records. In MINA this internal fragmentation has a bug and can lead
     * to synchronization errors, resulting in javax.net.ssl.SSLException: Tag
     * mismatch! or bad record MAC during decryption
     */
    protected static final int SINGLE_PACKAGE_SIZE_IN_BYTE = 16 * 1024;
    private final int CACHE_SIZE_LIMIT = 100;
    private static final MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleServerDecoder.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
    }
    private final ClientSessionHandlerCallback clientCallback;

    private record ChunkKey(long sessionId, int registryKey, long referenceId) {
        @Override
        public String toString() {
            return "Session id=" + sessionId
                    + " Message registry id=" + registryKey
                    + " Reference id=" + referenceId;
        }
    }
    
    private static final Map<Class<? extends ClientServerMessage>, ObjectReader> OBJECT_READER_CACHE
            = new ConcurrentHashMap<Class<? extends ClientServerMessage>, ObjectReader>();
    private static final ObjectMapper OBJECT_MAPPER;
    //store the chunks and add them to one stream in a cache. Every memory buffer will expire after some minutes
    //key: session id
    private final Cache<ChunkKey, ByteArrayBuilder> chunkStorage = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            //allow max CACHE_SIZE_LIMIT parallel sessions
            .maximumSize(CACHE_SIZE_LIMIT)
            .removalListener(new RemovalListener<ChunkKey, ByteArrayBuilder>() {
                @Override
                public void onRemoval(ChunkKey chunkKey, ByteArrayBuilder builder, RemovalCause cause) {
                    //An entry was removed because the max number of 
                    if (cause == RemovalCause.SIZE) {
                        String reason
                                = "Client-server protocol decoder: Cache size limit reached (max "
                                + CACHE_SIZE_LIMIT + " parallel sessions)";
                        if (systemEventManager != null) {
                            systemEventManager.systemFailure(
                                    new IOException(reason + " " + chunkKey.toString()),
                                    SystemEvent.Type.CLIENT_ANY
                            );
                        }
                    }
                    //bring the builder back to the pool
                    builder.release();
                }

            })
            .build();

    static {
        OBJECT_MAPPER = new CBORMapper();
        OBJECT_MAPPER.registerModule(SerializationModule.initialize());
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new ParameterNamesModule());
        //this is an integrated security feature of the jackson deserializer to prevent deep structures, long texts and numbers to bring the
        //deserializer to OOM situations
        OBJECT_MAPPER.getFactory().setStreamReadConstraints(StreamReadConstraints.builder()
                //prevent stack overflow attacks by deep structures
                .maxNestingDepth(50)
                //max string length of single string in JSON format is about 100kB, mostly for long log output 
                //e.g. deleted transactions
                .maxStringLength(MAX_STRING_LENGTH)
                //very long numbers in single JSON structures may result in huge CPU loaed and parser crash
                .maxNumberLength(50)
                .build());
    }

    private final long magicNumberProduct;
    protected static final int MAGIC_NUMBER_MEND = 0x4D454E44;
    private final SystemEventManager systemEventManager;

    /**
     *
     * @param clientCallback This may be null if there is no callback or this is
     * not a client instance
     */
    public ClientServerDecoder(ClientSessionHandlerCallback clientCallback, long magicNumberProduct,
            SystemEventManager systemEventManager) {
        super();
        this.magicNumberProduct = magicNumberProduct;
        this.clientCallback = clientCallback;
        this.systemEventManager = systemEventManager;
    }

    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer in, ProtocolDecoderOutput decoderOutput) throws Exception {
        try {
            //Wait for the full header
            if (in.remaining() < HEADER_SIZE_IN_BYTES) {
                return false;
            }
            in.mark();
            long magicNumberFoundProduct = in.getLong();//8 bytes
            int protocolVersion = in.getInt();//4 bytes
            int registryKey = in.getInt();//4 bytes
            int magicNumberFoundMend = in.getInt();//4 bytes
            long messageReferenceId = in.getLong();//8 bytes
            long totalSize = in.getLong();//8 bytes
            long chunkOffset = in.getLong();//8 bytes
            boolean isLastChunk = in.get() == (byte) 1;//1 bytes
            int payloadLength = in.getInt();//4 bytes
            //validate the identity of the message and the version
            if (magicNumberFoundProduct != this.magicNumberProduct || magicNumberFoundMend != MAGIC_NUMBER_MEND) {
                //Invalid magic numbers. Reset the buffer and skip 1 byte to test it again
                in.reset();
                //advance 1 byte to try to find valid magic number next time
                in.get();
                return false;
            }
            if (protocolVersion != ClientServerEncoder.CLIENT_SERVER_PROTOCOL_VERSION) {
                throw new IOException("Client-server protocol decoder: "
                        + "Incompatible mendelson protocol version found: " + protocolVersion
                        + " for client-server message " + registryKey);

            }
            //DoS Protection
            if (totalSize > ABSOLUTE_MAX_PAYLOAD_SIZE) {
                throw new IOException("Client-server protocol decoder: "
                        + "Object size " + totalSize + " exceeds absolute server limit"
                        + " for client-server message " + registryKey);
            }
            //read the full payload
            if (in.remaining() < payloadLength) {
                //rewind the input buffer, next attempt will read the data again
                in.reset();
                //buffer has not been consumed: return false
                return (false);
            }
            //store the data of a wholeMessage
            byte[] wholeMessageData = null;
            //do not store the byte data in the memory stream if this is a non chunked object and fits in one
            //chunk
            if (chunkOffset > 0 || !isLastChunk) {
                ChunkKey chunkKey = new ChunkKey(ioSession.getId(), registryKey, messageReferenceId);
                ByteArrayBuilder chunkStreamBuilder = this.chunkStorage.getIfPresent(chunkKey);
                if (chunkStreamBuilder == null) {
                    //its the first chunk: Generate the memory buffer. As the builder is organized by interal
                    //byte arrays that are linked we can just get a SINGLE_PACKAGE_SIZE_IN_BYTE sized buffer from the pool here
                    ByteArrayBuilder chunkMemoryBuilder = new ByteArrayBuilder(
                            OBJECT_MAPPER.getFactory()._getBufferRecycler(), SINGLE_PACKAGE_SIZE_IN_BYTE);
                    this.chunkStorage.put(chunkKey, chunkMemoryBuilder);
                    chunkStreamBuilder = chunkMemoryBuilder;
                }
                //Chunk sequence check - check if the offset of the chunk matches the current expectation
                if (chunkOffset != chunkStreamBuilder.size()) {
                    this.chunkStorage.invalidate(chunkKey);
                    throw new IOException("Client-server protocol decoder: "
                            + "Sequence error, expected chunk offset " + chunkStreamBuilder.size()
                            + " but got " + chunkOffset + " for client-server message " + registryKey);
                }
                //MINA stores the data either in a direct buffer outside the heap or in the heap.
                //there is no array access for direct buffers (off-heap)
                if (in.hasArray()) {
                    //heap array: use array copy, this should be the MINA default but could
                    //change in the future
                    chunkStreamBuilder.write(in.array(), in.arrayOffset() + in.position(), payloadLength);
                    in.position(in.position() + payloadLength);
                } else {
                    //direct buffer (off-heap): use a stream wrapper because the data has to be accesed
                    //via JNI from outside the JVM
                    int oldBufferUpperLimit = in.limit();
                    //generate a range on the input data which should be copied
                    int newBufferUpperLimit = in.position() + payloadLength;
                    in.limit(newBufferUpperLimit);
                    in.asInputStream().transferTo(chunkStreamBuilder);
                    //restore the limit
                    in.limit(oldBufferUpperLimit);
                }
                if (isLastChunk) {
                    wholeMessageData = chunkStreamBuilder.toByteArray();
                    if (wholeMessageData.length != totalSize) {
                        this.chunkStorage.invalidate(chunkKey);
                        throw new IOException("Client-server protocol decoder: Data integrity error, received "
                                + wholeMessageData.length + " bytes but expected " + totalSize + " bytes"
                                + " for client-server message " + registryKey);
                    }
                    this.chunkStorage.invalidate(chunkKey);
                }
            } else {
                //the whole message fits into a single chunk
                wholeMessageData = new byte[payloadLength];
                in.get(wholeMessageData);
            }
            //it is the last chunk: deserialize the object
            if (isLastChunk) {
                try {
                    Class<? extends ClientServerMessage> messageClass = ClientServerCodecRegistryImpl.instance().get(registryKey);
                    if (messageClass != null) {
                        ObjectReader objectReader = OBJECT_READER_CACHE.get(messageClass);
                        if (objectReader == null) {
                            objectReader = OBJECT_MAPPER.readerFor(messageClass);
                            OBJECT_READER_CACHE.put(messageClass, objectReader);
                        }
                        decoderOutput.write(objectReader.readValue(wholeMessageData));
                    }
                } catch (Exception e) {
                    String errorMessage = "Client-server protocol decoder: "
                            + "Deserialization failed "
                            + "for client-server message " + registryKey + " "
                            + e.getMessage();
                    if (this.clientCallback != null) {
                        this.clientCallback.error(errorMessage);
                    }
                    throw new IOException(errorMessage + ": " + e.getMessage());
                }
            }
            return true;
        } catch (Throwable e) {
            if (this.systemEventManager != null) {
                this.systemEventManager.systemFailure(e, SystemEvent.Type.CLIENT_ANY);
            }
            throw e;
        }
    }
    
}
