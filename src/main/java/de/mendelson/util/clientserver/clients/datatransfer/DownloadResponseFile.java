//$Header: /as2/de/mendelson/util/clientserver/clients/datatransfer/DownloadResponseFile.java 3     2/11/23 15:53 Heller $
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
public class DownloadResponseFile extends DownloadResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fullFilename = null;
    private boolean readOnly = false;

    public DownloadResponseFile(DownloadRequestFile request) {
        super(request);
    }

    @Override
    public String toString() {
        return ("Download response file");
    }

    /**
     * @return the fullFilename
     */
    public String getFullFilename() {
        return fullFilename;
    }

    /**
     * @param fullFilename the fullFilename to set
     */
    public void setFullFilename(String fullFilename) {
        this.fullFilename = fullFilename;
    }

    /**
     * @return the readOnly
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * @param readOnly the readOnly to set
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

}
