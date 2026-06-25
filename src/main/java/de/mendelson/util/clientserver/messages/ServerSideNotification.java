//$Header: /as4/de/mendelson/util/clientserver/messages/ServerSideNotification.java 6     12/06/25 16:04 Heller $
package de.mendelson.util.clientserver.messages;

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
 * This marks a client server message as a notification from the server. It should be sent to all client modules and requires no
 * processing notification
 * @author S.Heller
 * @version $Revision: 6 $
 */
public class ServerSideNotification extends ClientServerMessage implements Serializable{

    private static final long serialVersionUID = 1L;
    
    public ServerSideNotification(){
        super();
    }

    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }
    
}
