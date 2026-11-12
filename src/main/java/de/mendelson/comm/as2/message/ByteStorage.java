//$Header: /as2/de/mendelson/comm/as2/message/ByteStorage.java 19    5/11/25 11:18 Heller $
package de.mendelson.comm.as2.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Container that stores byte arrays
 *
 * @author S.Heller
 * @version $Revision: 19 $
 */
public class ByteStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Switch to file storage at 20 MB data size
     */
    private static final int THRESHOLD = 20 * 1024 * 1024;
    private IByteStorage storage = null;

    public ByteStorage() {
        super();
    }

    /**
     * Returns the actual stored data size
     */
    @JsonIgnore
    public int getSize() {
        if (this.storage == null) {
            return (0);
        }
        return (this.storage.getSize());
    }

    /**
     * store a byte array
     */
    @JsonIgnore
    public void put(byte[] data) throws Exception {
        //release an existing storage if it exists
        if (this.storage != null) {
            this.storage.release();
        }
        if (data.length > THRESHOLD) {
            this.setStorage(new ByteStorageImplFile());
        } else {
            this.setStorage(new ByteStorageImplMemory());
        }
        this.storage.put(data);
    }

    @JsonIgnore
    public byte[] get() throws Exception {
        if (this.storage == null) {
            return (new byte[0]);
        } else {
            return (this.storage.get());
        }
    }

    /**
     * Returns an input stream to read directly from the underlaying buffer
     */
    @JsonIgnore
    public InputStream getInputStream() throws Exception {
        return (this.storage.getInputStream());
    }

    /**
     * Releases the allocated resources
     */
    @JsonIgnore
    public void release() {
        if (this.storage != null) {
            this.storage.release();
        }
    }

    /**
     * @return the storage
     */
    public IByteStorage getStorage() {
        return storage;
    }

    /**
     * @param storage the storage to set
     */
    public void setStorage(IByteStorage storage) {
        this.storage = storage;
    }
}
