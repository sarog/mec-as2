//$Header: /as2/de/mendelson/util/clientserver/clients/datatransfer/UploadRequestChunk.java 7     26/02/26 12:53 Heller $
package de.mendelson.util.clientserver.clients.datatransfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Msg for the client server protocol
 * @author S.Heller
 * @version $Revision: 7 $
 */
public class UploadRequestChunk extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private byte[] data = null;
    private String targetHash = null;
    private boolean lastChunk = false;
    private int chunkNumber = 0;

    public void setData(byte[] data){
        this.data = data;
    }

    @Override
    public String toString() {
        return ("Upload request chunk");
    }
    
    /**
     * @return the data
     */
    @JsonIgnore
    public InputStream getDataStream() {
        InputStream inStream = new ByteArrayInputStream(this.getData());
        return (inStream);
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @return the targetHash
     */
    public String getTargetHash() {
        return this.targetHash;
    }

    /**
     * @param targetHash the targetHash to set
     */
    public void setTargetHash(String targetHash) {
        this.targetHash = targetHash;
    }

    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }

    /**
     * @return the lastChunk
     */
    public boolean isLastChunk() {
        return lastChunk;
    }

    /**
     * @param lastChunk the lastChunk to set
     */
    public void setLastChunk(boolean lastChunk) {
        this.lastChunk = lastChunk;
    }

    /**
     * @return the chunkNumber
     */
    public int getChunkNumber() {
        return chunkNumber;
    }

    /**
     * @param chunkNumber the chunkNumber to set
     */
    public void setChunkNumber(int chunkNumber) {
        this.chunkNumber = chunkNumber;
    }
    
}
