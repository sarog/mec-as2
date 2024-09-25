//$Header: /as2/de/mendelson/comm/as2/message/clientserver/MessageResponseLastMessage.java 1     20.09.12 10:49 Heller $
package de.mendelson.comm.as2.message.clientserver;

import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
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
 * @version $Revision: 1 $
 */
public class MessageResponseLastMessage extends ClientServerResponse implements Serializable {

    private AS2MessageInfo info = null;

    public MessageResponseLastMessage(MessageRequestLastMessage request) {
        super(request);
    }

    @Override
    public String toString() {
        return ("Message respond last message");
    }

    /**
     * @return the list
     */
    public AS2MessageInfo getInfo() {
        return info;
    }

    /**
     * @param list the list to set
     */
    public void setInfo(AS2MessageInfo list) {
        this.info = info;
    }

    
}
