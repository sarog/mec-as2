//$Header: /as2/de/mendelson/comm/as2/message/clientserver/MessageLogResponse.java 5     11/06/25 13:28 Heller $
package de.mendelson.comm.as2.message.clientserver;

import de.mendelson.comm.as2.log.LogEntry;
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
public class MessageLogResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List<LogEntry> list = null;

    public MessageLogResponse(MessageLogRequest request) {
        super(request);
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public MessageLogResponse() {
        super();
    }
    
    @Override
    public String toString() {
        return ("Message log response");
    }

    /**
     * @return the list
     */
    public List<LogEntry> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<LogEntry> list) {
        this.list = list;
    }

    
}
