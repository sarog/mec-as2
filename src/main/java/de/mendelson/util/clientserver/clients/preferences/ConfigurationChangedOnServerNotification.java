//$Header: /as2/de/mendelson/util/clientserver/clients/preferences/ConfigurationChangedOnServerNotification.java 3     2/11/23 15:53 Heller $
package de.mendelson.util.clientserver.clients.preferences;

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
 * @version $Revision: 3 $
 */
public class ConfigurationChangedOnServerNotification extends ConfigurationChangedOnServer implements Serializable{
    
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public ConfigurationChangedOnServerNotification(){
        super( ConfigurationChangedOnServer.TYPE_NOTIFICATION_SETTINGS);
    }
    
    
    @Override
    public String toString(){
        return( "Notification settings changed on server" );
    }

}
