//$Header: /as2/de/mendelson/comm/as2/message/IByteStorage.java 3     13/03/26 10:09 Heller $
package de.mendelson.comm.as2.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Interface for the byte storage implementations
 * @author S.Heller
 * @version $Revision: 3 $
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ByteStorageImplFile.class, name = "file"),
    @JsonSubTypes.Type(value = ByteStorageImplMemory.class, name = "mem")
})
public sealed interface IByteStorage extends Serializable permits ByteStorageImplFile,ByteStorageImplMemory{

    /**Returns the actual stored data size*/
    public int getSize();

    /**store a byte array*/
    public void put(byte[] data) throws Exception;

    /**Returns the stored data*/
    public byte[] get() throws Exception;

    /**Releases the storage*/
    public void release();

    /**Returns an input stream to read directly from the underlaying storage*/
    public InputStream getInputStream() throws Exception;
}
