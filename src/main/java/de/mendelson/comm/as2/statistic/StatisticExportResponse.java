//$Header: /as2/de/mendelson/comm/as2/statistic/StatisticExportResponse.java 3     2/11/23 15:53 Heller $
package de.mendelson.comm.as2.statistic;

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
 * @version $Revision: 3 $
 */
public class StatisticExportResponse extends DownloadResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    public StatisticExportResponse(StatisticExportRequest request) {
        super(request);
    }

    @Override
    public String toString(){
        return( "Statistic export response" );
    }

}
