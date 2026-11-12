//$Header: /as2/de/mendelson/comm/as2/statistic/StatisticExportResponse.java 4     26/06/25 16:23 Heller $
package de.mendelson.comm.as2.statistic;

import de.mendelson.util.clientserver.SerializationDummy;
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
 * @version $Revision: 4 $
 */
public class StatisticExportResponse extends DownloadResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    public StatisticExportResponse(StatisticExportRequest request) {
        super(request);
    }

    /**This is a dummy constructor for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public StatisticExportResponse() {
        super();
    }
    
    @Override
    public String toString(){
        return( "Statistic export response" );
    }

}
