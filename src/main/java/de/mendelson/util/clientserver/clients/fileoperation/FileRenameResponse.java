//$Header: /as2/de/mendelson/util/clientserver/clients/fileoperation/FileRenameResponse.java 4     2/11/23 15:53 Heller $
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
 * @version $Revision: 4 $
 */
public class FileRenameResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean success = false;

    public FileRenameResponse(FileRenameRequest request) {
        super(request);
    }

    @Override
    public String toString() {
        return ("File rename response");
    }

    /**
     * @return the result
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
