//$Header: /as2/de/mendelson/comm/as2/importexport/ConfigurationExportResponse.java 1     14.01.11 12:21 Heller $
package de.mendelson.comm.as2.importexport;

import de.mendelson.util.clientserver.clients.datatransfer.DownloadResponse;
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
public class ConfigurationExportResponse extends DownloadResponse implements Serializable {

    public ConfigurationExportResponse(ConfigurationExportRequest request) {
        super(request);
    }

    @Override
    public String toString(){
        return( "Export response" );
    }

}
