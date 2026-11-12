//$Header: /as4/de/mendelson/util/clientserver/clients/preferences/PreferencesResponse.java 7     11/06/25 13:17 Heller $
package de.mendelson.util.clientserver.clients.preferences;

import de.mendelson.util.clientserver.SerializationDummy;
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
 * @author S.Heller
 * @version $Revision: 7 $
 */
public class PreferencesResponse extends ClientServerResponse implements Serializable{

    private static final long serialVersionUID = 1L;
    private String value = null;

    public PreferencesResponse( PreferencesRequest request ){
        super( request );
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public PreferencesResponse() {
        super();
    }
    
    
    @Override
    public String toString(){
        return( "Preferences response" );
    }
    
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    
}
