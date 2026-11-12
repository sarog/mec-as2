//$Header: /as2/de/mendelson/comm/as2/message/clientserver/MessagePayloadRequest.java 6     11/06/25 13:28 Heller $
package de.mendelson.comm.as2.message.clientserver;

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
 * @author S.Heller
 * @version $Revision: 6 $
 */
public class MessagePayloadRequest extends ClientServerMessage implements Serializable{

    private static final long serialVersionUID = 1L;
    private String messageId = null;

    public MessagePayloadRequest(String messageId){
        this.messageId = messageId;
    }

    /**This is a dummy constructor for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public MessagePayloadRequest() {
        super();
    }
    
    @Override
    public String toString(){
        return( "Message payload request" );
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }
  
    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }
    
}
