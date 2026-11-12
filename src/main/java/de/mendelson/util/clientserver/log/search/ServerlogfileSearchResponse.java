//$Header: /as4/de/mendelson/util/clientserver/log/search/ServerlogfileSearchResponse.java 6     11/06/25 13:17 Heller $
package de.mendelson.util.clientserver.log.search;

import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import java.io.Serializable;
import java.util.ArrayList;
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
 * @version $Revision: 6 $
 */
public class ServerlogfileSearchResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Logline> resultList = new ArrayList<Logline>();
      
    public ServerlogfileSearchResponse(ServerlogfileSearchRequest request) {
        super(request);
    }
    
    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public ServerlogfileSearchResponse() {
        super();
    }
    
    /**
     * @return the event result List
     */
    public List<Logline> getResultList() {
        return (this.resultList);
    }

    /**
     * @param eventList the eventList to set
     */
    public void setResultList(List<Logline> resultList) {
        this.resultList.clear();
        this.resultList.addAll(resultList);
    }

    @Override
    public String toString() {
        return ("Search for server log file entries");
    }

    

}
