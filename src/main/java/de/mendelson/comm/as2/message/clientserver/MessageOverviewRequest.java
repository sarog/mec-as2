//$Header: /as2/de/mendelson/comm/as2/message/clientserver/MessageOverviewRequest.java 7     11/06/25 13:28 Heller $
package de.mendelson.comm.as2.message.clientserver;

import de.mendelson.comm.as2.message.MessageOverviewFilter;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
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
 * @version $Revision: 7 $
 */
public class MessageOverviewRequest extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private MessageOverviewFilter filter = null;
    private String messageId = null;

    public MessageOverviewRequest(String messageId) {
        super();
        this.messageId = messageId;
    }

    public MessageOverviewRequest(MessageOverviewFilter filter) {
        super();
        this.filter = filter;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public MessageOverviewRequest() {
        super();
        this.messageId = "";
    }
    
    @Override
    public String toString() {
        return ("Message overview request");
    }

    /**
     * @return the filter
     */
    public MessageOverviewFilter getFilter() {
        return filter;
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }

}
