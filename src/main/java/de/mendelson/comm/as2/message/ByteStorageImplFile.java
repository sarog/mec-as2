//$Header: /as2/de/mendelson/comm/as2/message/ByteStorageImplFile.java 15    13/03/26 10:09 Heller $
package de.mendelson.comm.as2.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.AS2Tools;
import de.mendelson.util.clientserver.SerializationDummy;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Container that stores byte arrays in a temp file
 *
 * @author S.Heller
 * @version $Revision: 15 $
 */
public final class ByteStorageImplFile implements IByteStorage {

    /**
     * IByteStorage extends Serializable
     */
    private static final long serialVersionUID = 1L;
    //Use a String here to keep this serializable
    private String fullFilename = null;

    public ByteStorageImplFile() {
    }

    /**
     * Returns the actual stored data size
     */
    @Override
    @JsonIgnore
    public int getSize() {
        if (this.getFullFilename() == null) {
            return (0);
        }
        try {
            return ((int) Files.size(Paths.get(this.getFullFilename())));
        } catch (IOException e) {
            return (0);
        }
    }

    @Override
    /**
     * store a byte array
     */
    @JsonIgnore
    public void put(byte[] data) throws Exception {
        //create the file storage
        Path tempFile = AS2Tools.createTempFile("AS2ByteStorage", ".bin");
        this.setFullFilename(tempFile.toAbsolutePath().toString());
        Files.write(tempFile, data,
                StandardOpenOption.SYNC,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);
    }

    @Override
    @JsonIgnore
    public byte[] get() throws Exception {
        if (this.getFullFilename() == null) {
            return (new byte[0]);
        }
        return (Files.readAllBytes(Paths.get(this.getFullFilename())));
    }

    @Override
    /**
     * Returns an input stream to read directly from the underlaying buffer
     */
    @JsonIgnore
    public InputStream getInputStream() throws Exception {
        return (Files.newInputStream(Paths.get(this.getFullFilename())));
    }

    @Override
    @JsonIgnore
    public void release() {
        try {
            Files.delete(Paths.get(this.getFullFilename()));
        } catch (IOException e) {
            //nop
        } finally {
            this.setFullFilename(null);
        }
    }

    /**This is a dummy method for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public String getFullFilename() {
        return fullFilename;
    }

    /**This is a dummy method for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setFullFilename(String fullFilename) {
        this.fullFilename = fullFilename;
    }

}
