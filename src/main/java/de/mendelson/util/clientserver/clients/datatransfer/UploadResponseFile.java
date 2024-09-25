//$Header: /as2/de/mendelson/util/clientserver/clients/datatransfer/UploadResponseFile.java 1     17.01.11 10:47 Heller $
package de.mendelson.util.clientserver.clients.datatransfer;

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
 * @version $Revision: 1 $
 */
public class UploadResponseFile extends ClientServerResponse implements Serializable {

    public UploadResponseFile(UploadRequestFile request) {
        super(request);
    }

    @Override
    public String toString() {
        return ("Upload response file");
    }

}
