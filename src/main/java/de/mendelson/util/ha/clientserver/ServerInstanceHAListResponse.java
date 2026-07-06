//$Header: /mec_oftp2/de/mendelson/util/ha/clientserver/ServerInstanceHAListResponse.java 4     8/04/26 8:13 Heller $
package de.mendelson.util.ha.clientserver;

import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import de.mendelson.util.ha.ServerInstanceHA;
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
 * @version $Revision: 4 $
 */
public class ServerInstanceHAListResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ServerInstanceHA> list = null;

    
    public ServerInstanceHAListResponse(ServerInstanceHAListRequest request) {
        super(request);
    }

    /**This is a dummy constructor for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public ServerInstanceHAListResponse() {
        super();
    }   
    
    @Override
    public String toString() {
        return ("List HA instances");
    }

    /**
     * @return the list
     */
    public List<ServerInstanceHA> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<ServerInstanceHA> list) {
        this.list = list;
    }
}
