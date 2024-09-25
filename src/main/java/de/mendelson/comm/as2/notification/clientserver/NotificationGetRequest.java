//$Header: /as2/de/mendelson/comm/as2/notification/clientserver/NotificationGetRequest.java 1     18.09.12 14:07 Heller $
package de.mendelson.comm.as2.notification.clientserver;

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
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class NotificationGetRequest extends ClientServerMessage implements Serializable {

    public NotificationGetRequest() {
    }

    @Override
    public String toString() {
        return ("Load notification data");
    }
    
}
