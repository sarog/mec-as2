//$Header: /as4/de/mendelson/util/systemevents/clientserver/SystemEventSearchRequest.java 6     11/06/25 13:17 Heller $
package de.mendelson.util.systemevents.clientserver;

import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.systemevents.search.ServerSideEventFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
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
 *
 * @author S.Heller
 * @version $Revision: 6 $
 */
public class SystemEventSearchRequest extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private ServerSideEventFilter filter;

    public SystemEventSearchRequest(ServerSideEventFilter filter) {
        this.filter = filter;
    }
    
    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public SystemEventSearchRequest() {
        super();
        this.filter = null;
    }

    @Override
    public String toString() {
        return ("Search for system events");
    }

    /**
     * @return the search filter
     */
    public ServerSideEventFilter getFilter() {
        return (this.filter);
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }

}
