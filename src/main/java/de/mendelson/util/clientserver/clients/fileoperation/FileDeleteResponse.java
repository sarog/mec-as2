//$Header: /as2/de/mendelson/util/clientserver/clients/fileoperation/FileDeleteResponse.java 3     2/11/23 15:53 Heller $
package de.mendelson.util.clientserver.clients.fileoperation;

import de.mendelson.util.clientserver.messages.ClientServerResponse;
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
public class FileDeleteResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean success = false;

    public FileDeleteResponse(FileDeleteRequest request) {
        super(request);
    }

    @Override
    public String toString() {
        return ("File delete response");
    }

    /**
     * @return the result
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * @param result the result to set
     */
    public void setSuccess(boolean result) {
        this.success = result;
    }
}
