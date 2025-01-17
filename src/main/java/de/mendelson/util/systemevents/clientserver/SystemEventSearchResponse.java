//$Header: /as2/de/mendelson/util/systemevents/clientserver/SystemEventSearchResponse.java 5     2/11/23 15:53 Heller $
package de.mendelson.util.systemevents.clientserver;

import de.mendelson.util.clientserver.messages.ClientServerResponse;
import de.mendelson.util.systemevents.SystemEvent;
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
 * @version $Revision: 5 $
 */
public class SystemEventSearchResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private final List<SystemEvent> eventResultList = new ArrayList<SystemEvent>();
      
    public SystemEventSearchResponse(SystemEventSearchRequest request) {
        super(request);
    }
    
    /**
     * @return the event result List
     */
    public List<SystemEvent> getSearchResults() {
        return (this.eventResultList);
    }

    /**
     * @param eventList the eventList to set
     */
    public void setEventResultList(List<SystemEvent> eventList) {
        this.eventResultList.addAll(eventList);
    }

    @Override
    public String toString() {
        return ("Search for system events");
    }

    

}
