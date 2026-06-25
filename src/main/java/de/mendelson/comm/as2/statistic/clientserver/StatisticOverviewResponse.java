//$Header: /as2/de/mendelson/comm/as2/statistic/clientserver/StatisticOverviewResponse.java 5     11/06/25 13:29 Heller $
package de.mendelson.comm.as2.statistic.clientserver;

import de.mendelson.comm.as2.statistic.StatisticOverviewEntry;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import java.io.Serializable;
import java.util.List;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Msg for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class StatisticOverviewResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<StatisticOverviewEntry> list = null;

    public StatisticOverviewResponse(StatisticOverviewRequest request) {
        super(request);
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public StatisticOverviewResponse() {
        super();
    }
    
    @Override
    public String toString() {
        return ("List statistic overview");
    }

    /**
     * @return the list
     */
    public List<StatisticOverviewEntry> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<StatisticOverviewEntry> list) {
        this.list = list;
    }
}
