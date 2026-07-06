//$Header: /as2/de/mendelson/comm/as2/message/ByteStorageImplMemory.java 5     13/03/26 10:09 Heller $
package de.mendelson.comm.as2.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.clientserver.SerializationDummy;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Container that stores byte arrays in memory
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public final class ByteStorageImplMemory implements IByteStorage {

    /**
     * IByteStorage extends Serializable
     */
    private static final long serialVersionUID = 1L;
    private byte[] byteBuffer = null;

    public ByteStorageImplMemory() {
        super();
    }
    
    /**
     * Returns the actual stored data size
     */
    @Override
    @JsonIgnore
    public int getSize() {
        if (this.getByteBuffer() == null) {
            return (0);
        }
        return (this.getByteBuffer().length);
    }
    
    /**
     * store a byte array
     */
    @Override
    @JsonIgnore
    public void put(byte[] data) {
        this.setByteBuffer(data);
    }

    @Override
    public byte[] get() {
        return (this.getByteBuffer());
    }
    
    /**
     * Returns an input stream to read directly from the underlaying buffer
     */
    @Override
    @JsonIgnore
    public InputStream getInputStream() {
        return (new ByteArrayInputStream(this.getByteBuffer()));
    }

    @Override
    @JsonIgnore
    public void release() {
        //nop
    }

    /**This is a dummy method for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public byte[] getByteBuffer() {
        return byteBuffer;
    }

    /**This is a dummy method for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setByteBuffer(byte[] byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
}
