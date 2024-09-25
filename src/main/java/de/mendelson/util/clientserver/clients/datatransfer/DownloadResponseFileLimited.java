//$Header: /as2/de/mendelson/util/clientserver/clients/datatransfer/DownloadResponseFileLimited.java 3     2/11/23 15:53 Heller $
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
 * @version $Revision: 3 $
 */
public class DownloadResponseFileLimited extends DownloadResponseFile implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean sizeExceeded = false;

    public DownloadResponseFileLimited(DownloadRequestFileLimited request) {
        super(request);
    }

    /**
     * @return the exceeded
     */
    public boolean isSizeExceeded() {
        return sizeExceeded;
    }

    /**
     * @param exceeded the exceeded to set
     */
    public void setSizeExceeded(boolean exceeded) {
        this.sizeExceeded = exceeded;
    }



}
