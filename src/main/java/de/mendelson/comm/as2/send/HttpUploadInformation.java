//$Header: /as2/de/mendelson/comm/as2/send/HttpUploadInformation.java 2     26/05/25 12:52 Heller $
package de.mendelson.comm.as2.send;

import javax.servlet.http.HttpServletResponse;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Contains information regarding the HTTP upload process
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class HttpUploadInformation {

    private long uploadTimeInMS = 0;
    private long uploadedByteCount = 0;
    private int httpReturnCode = HttpServletResponse.SC_NOT_FOUND;

    public HttpUploadInformation() {
    }

    /**
     * @return the uploadTime
     */
    public long getUploadTimeInMS() {
        return uploadTimeInMS;
    }

    /**
     * @param uploadTime the uploadTime to set
     */
    public void setUploadTimeInMS(long uploadTimeInMS) {
        if( uploadTimeInMS < 1){
            //values below 1ms do not make sense but this could happen in fast networks. And it would result
            //in really huge data transfer rates which is impossible
            uploadTimeInMS = 1;
        }
        this.uploadTimeInMS = uploadTimeInMS;
    }

    /**
     * @return the httpReturnCode
     */
    public int getHTTPReturnCode() {
        return httpReturnCode;
    }

    /**
     * @param httpReturnCode the httpReturnCode to set
     */
    public void setHTTPReturnCode(int httpReturnCode) {
        this.httpReturnCode = httpReturnCode;
    }

    /**
     * @return the uploadedBytes
     */
    public long getUploadedByteCount() {
        return uploadedByteCount;
    }

    /**
     * @param uploadedBytes the uploadedBytes to set
     */
    public void setUploadedByteCount(long uploadedByteCount) {
        this.uploadedByteCount = uploadedByteCount;
    }

}
