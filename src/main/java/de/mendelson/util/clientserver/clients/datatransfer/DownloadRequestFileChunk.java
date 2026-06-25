//$Header: /as2/de/mendelson/util/clientserver/clients/datatransfer/DownloadRequestFileChunk.java 2     17/03/26 9:24 Heller $
package de.mendelson.util.clientserver.clients.datatransfer;

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
 * @version $Revision: 2 $
 */
public final class DownloadRequestFileChunk extends DownloadRequestFile implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private long offset = 0;

    @Override
    public String toString(){
        return( "Download request file chunked" );
    }

    /**
     * @return the offset
     */
    public long getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(long offset) {
        this.offset = offset;
    }

    

}
