//$Header: /as2/de/mendelson/util/clientserver/clients/datatransfer/DownloadResponseFileLimited.java 6     17/03/26 9:24 Heller $
package de.mendelson.util.clientserver.clients.datatransfer;

import de.mendelson.util.clientserver.SerializationDummy;
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
 * @version $Revision: 6 $
 */
public final class DownloadResponseFileLimited extends DownloadResponseFile implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean sizeExceeded = false;

    public DownloadResponseFileLimited(DownloadRequestFileLimited request) {
        super(request);
    }

    /**This is a dummy constructor for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public DownloadResponseFileLimited() {
        super();
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
