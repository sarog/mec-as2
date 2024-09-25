//$Header: /as2/de/mendelson/comm/as2/importexport/ConfigurationImportResponse.java 2     17.01.11 10:47 Heller $
package de.mendelson.comm.as2.importexport;

import de.mendelson.util.clientserver.clients.datatransfer.UploadResponseFile;
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
public class ConfigurationImportResponse extends UploadResponseFile implements Serializable {

    public ConfigurationImportResponse(ConfigurationImportRequest request) {
        super(request);
    }

    @Override
    public String toString() {
        return ("Import response");
    }
}
