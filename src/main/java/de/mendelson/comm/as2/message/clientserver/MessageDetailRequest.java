//$Header: /as2/de/mendelson/comm/as2/message/clientserver/MessageDetailRequest.java 1     18.09.12 12:02 Heller $
package de.mendelson.comm.as2.message.clientserver;

import de.mendelson.comm.as2.message.MessageOverviewFilter;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
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
 * @version $Revision: 1 $
 */
public class MessageDetailRequest extends ClientServerMessage implements Serializable{

    private String messageId = null;

    public MessageDetailRequest(String messageId){
        this.messageId = messageId;
    }
    
    @Override
    public String toString(){
        return( "Message detail request" );
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

   

    
}