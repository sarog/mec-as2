//$Header: /as2/de/mendelson/util/clientserver/clients/datatransfer/TransferClientWithProgress.java 9     26/02/26 12:53 Heller $
package de.mendelson.util.clientserver.clients.datatransfer;

import de.mendelson.util.ProgressPanel;
import de.mendelson.util.clientserver.BaseClient;
import java.io.InputStream;
import java.util.Objects;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Requests downloads from and sends new uploads to the server
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class TransferClientWithProgress extends TransferClient {

    private final ProgressPanel progressPanel;

    public TransferClientWithProgress(BaseClient baseClient, ProgressPanel progressPanel) {
        super(baseClient);
        this.progressPanel = progressPanel;
    }

    /**
     * Consumer to update the progress panel
     */
    public void handleProgressUpload(String uniqueId, Long readBytes) {
        this.progressPanel.setProgressValue(uniqueId, readBytes.intValue());
    }
    
     /**
     * Consumer to update the progress panel
     */
    public void handleProgressDownload(String uniqueId, Long[] byteArray) {
        int progress = byteArray[0].intValue();
        int maxBytes = byteArray[1].intValue();
        this.progressPanel.setProgressMax(uniqueId,maxBytes);
        this.progressPanel.setProgressValue(uniqueId,progress);
    }

    /**
     * Sends the data of the inputstream synced to the server and returns a
     * unique number from the server for the upload process Warning: This does
     * also transfer files with the size of 0 bytes to the server Please be
     * aware of this at the server side
     * @return the hash of the uploaded data on the server side
     */
    public String uploadChunkedWithProgress(InputStream inStream, String display, int maxBytes) throws Throwable {
        String targetHash = null;
        String uniqueId = display + String.valueOf(maxBytes) + inStream.hashCode() + System.currentTimeMillis();
        try {
            this.progressPanel.startProgress(display, uniqueId, 0, maxBytes);
            targetHash = super.uploadChunked(inStream, maxBytes, this::handleProgressUpload, uniqueId);
        } finally {
            this.progressPanel.stopProgressIfExists(uniqueId);
        }
        return (targetHash);
    }
    
    public byte[] downloadChunkedWithProgress(String filenameOnServer, String display) throws Throwable {
        byte[] data = new byte[0];
        String uniqueId = display + Objects.hash(filenameOnServer) + System.currentTimeMillis();
        try {
            this.progressPanel.startProgress(display, uniqueId, 0, 0);
            data = super.downloadChunked(filenameOnServer, this::handleProgressDownload, uniqueId);
        } finally {
            this.progressPanel.stopProgressIfExists(uniqueId);
        }
        return (data);
    }
    
}
