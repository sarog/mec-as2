//$Header: /as4/de/mendelson/util/clientserver/codec/ClientServerEncoder.java 32    20/02/26 11:19 Heller $
package de.mendelson.util.clientserver.codec;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.cbor.databind.CBORMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderException;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Encodes the client-server communication
 *
 *
 * @author S.Heller
 * @version $Revision: 32 $
 */
public class ClientServerEncoder implements ProtocolEncoder {

    private static final ObjectMapper OBJECT_MAPPER;
    private static final Map<Class<? extends ClientServerMessage>, ObjectWriter> OBJECT_WRITER_CACHE
            = new ConcurrentHashMap<Class<? extends ClientServerMessage>, ObjectWriter>();
    //If a serialized object + its header is larger than this size it is chunked to be sent
    private static final int HEADER_SIZE_IN_BYTES = ClientServerDecoder.HEADER_SIZE_IN_BYTES;
    public static final int SINGLE_PACKAGE_SIZE_IN_BYTE = ClientServerDecoder.SINGLE_PACKAGE_SIZE_IN_BYTE;
    private static final int CHUNK_SIZE_IN_BYTE = SINGLE_PACKAGE_SIZE_IN_BYTE - HEADER_SIZE_IN_BYTES;
    private static final int BUFFER_SIZE_2KB = 2 * 1024;
    private static final long ABSOLUTE_MAX_PAYLOAD_SIZE = ClientServerDecoder.ABSOLUTE_MAX_PAYLOAD_SIZE;
    public static final int CLIENT_SERVER_PROTOCOL_VERSION = 1;

    static {
        SimpleModule module = SerializationModule.initialize();
        OBJECT_MAPPER = new CBORMapper();
        OBJECT_MAPPER.registerModule(module);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new ParameterNamesModule());
        OBJECT_MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
        OBJECT_MAPPER.deactivateDefaultTyping();
    }

    private final long magicNumberProduct;

    public ClientServerEncoder(long magicNumberProduct) {
        this.magicNumberProduct = magicNumberProduct;
    }

    public void encodeClientServerMessage(IoSession ioSession, Object message, ProtocolEncoderOutput encoderOutput) throws Exception {
        if (!(message instanceof ClientServerMessage)) {
            throw new Exception("[Client-Server] Serialization problem: There are only ClientServerMessages allowed, found "
                    + message.getClass().getName());
        }
        ClientServerMessage clientServerMessage = (ClientServerMessage) message;
        Class<? extends ClientServerMessage> messageClass = clientServerMessage.getClass();
        try {
            Integer registryKey = ClientServerCodecRegistryImpl.instance().get(messageClass);
            if (registryKey == null) {
                throw new Exception("[Client-Server] Serialization problem: " + clientServerMessage.getClass().getName()
                        + " should be serialized but is not registered in the ClientServerCodecRegistry");
            }
            ObjectWriter objectWriter = OBJECT_WRITER_CACHE.get(messageClass);
            if (objectWriter == null) {
                objectWriter = OBJECT_MAPPER.writerFor(messageClass);
                OBJECT_WRITER_CACHE.put(messageClass, objectWriter);
            }
            ByteArrayBuilder objectSerializedBuilder = new ByteArrayBuilder(
                    OBJECT_MAPPER.getFactory()._getBufferRecycler(), SINGLE_PACKAGE_SIZE_IN_BYTE);
            try {
                objectWriter.writeValue((OutputStream) objectSerializedBuilder, message);
                long totalSize = objectSerializedBuilder.size();
                if (totalSize > ABSOLUTE_MAX_PAYLOAD_SIZE) {
                    throw new Exception("[Client-Server] Serialization problem: "
                            + "Message payload too large: "
                            + totalSize + " bytes (max: " + ABSOLUTE_MAX_PAYLOAD_SIZE + ") "
                            + "for message " + messageClass.getName());
                }
                long offset = 0;
                long messageReferenceId = clientServerMessage.getReferenceId();
                byte[] objectSerializedBytes = objectSerializedBuilder.toByteArray();
                while (offset < totalSize) {
                    int currentPayloadLength = (int) Math.min(CHUNK_SIZE_IN_BYTE, totalSize - offset);
                    boolean isLastChunk = (offset + currentPayloadLength) >= totalSize;
                    //Header size: 8bytes(Magic)+4bytes(Version)+4bytes(Key)+4bytes(MendelsonMagic)
                    //+8bytes(messageReferenceId)
                    //+8bytes(Total size)+8bytes(Offset of chunk)+1byte(Last indicator)+4(data Lenght)
                    //= HEADER_SIZE_IN_BYTES Bytes
                    //allocate a heap buffer (parameter false), this is faster than the direct buffer allocation
                    IoBuffer buffer = IoBuffer.allocate(HEADER_SIZE_IN_BYTES + currentPayloadLength, false);
                    try {
                        //write the client server protocol header to the buffer
                        buffer.putLong(this.magicNumberProduct);//8 bytes
                        buffer.putInt(CLIENT_SERVER_PROTOCOL_VERSION);//4 bytes
                        buffer.putInt(registryKey);//4 bytes
                        buffer.putInt(ClientServerDecoder.MAGIC_NUMBER_MEND);//4 bytes
                        buffer.putLong(messageReferenceId);//8 bytes
                        buffer.putLong(totalSize);//8 bytes
                        buffer.putLong(offset);//8 bytes
                        buffer.put(isLastChunk ? (byte) 1 : (byte) 0);//1 bytes
                        buffer.putInt(currentPayloadLength);//4 bytes
                        //write the data chunk to the buffer
                        buffer.put(objectSerializedBytes, (int) offset, currentPayloadLength);
                        buffer.flip();
                        encoderOutput.write(buffer);
                    } finally {
                        buffer.free();
                    }
                    offset += currentPayloadLength;
                }
            } finally {
                objectSerializedBuilder.release();
            }
        } catch (Throwable e) {
            throw new ProtocolEncoderException(e.getMessage(), e);
        }
    }

    @Override
    public void encode(final IoSession ioSession, Object message, ProtocolEncoderOutput encoderOutput) throws Exception {
        this.encodeClientServerMessage(ioSession, message, encoderOutput);
    }

    @Override
    public void dispose(IoSession session) throws Exception {
        // nothing to dispose
    }

}
