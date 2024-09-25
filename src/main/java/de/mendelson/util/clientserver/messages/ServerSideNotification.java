//$Header: /mendelson_business_integration/de/mendelson/util/clientserver/messages/ServerSideNotification.java 1     9/17/15 10:38a Heller $
package de.mendelson.util.clientserver.messages;

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
 * @version $Revision: 1 $
 */
public class ServerSideNotification extends ClientServerMessage implements Serializable{

    public ServerSideNotification(){
        super();
    }

}
