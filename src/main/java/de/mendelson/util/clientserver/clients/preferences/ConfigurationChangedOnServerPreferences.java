//$Header: /as4/de/mendelson/util/clientserver/clients/preferences/ConfigurationChangedOnServerPreferences.java 4     11/06/25 13:17 Heller $
package de.mendelson.util.clientserver.clients.preferences;

import de.mendelson.util.clientserver.SerializationDummy;
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
 * @version $Revision: 4 $
 */
public class ConfigurationChangedOnServerPreferences extends ConfigurationChangedOnServer implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String newValue = null;
    private String oldValue = null;
    private String key = null;
    
    /**
     * 
     * @param key
     * @param oldValue
     * @param newValue 
     */
    public ConfigurationChangedOnServerPreferences( String key, String oldValue, String newValue ){
        super(ConfigurationChangedOnServer.TYPE_SERVER_PREFERENCES);
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    protected ConfigurationChangedOnServerPreferences() {
        super();
    }
    
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the newValue
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * @return the oldValue
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @param newValue the newValue to set
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    /**
     * @param oldValue the oldValue to set
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }
        
    
    @Override
    public String toString(){
        return( "Preferences settings changed on server" );
    }

}
