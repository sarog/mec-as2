//$Header: /as4/de/mendelson/util/clientserver/about/ServerInfoResponse.java 9     12/06/25 11:13 Heller $
package de.mendelson.util.clientserver.about;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import java.io.Serializable;
import java.util.Properties;
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
 * @version $Revision: 9 $
 */
public class ServerInfoResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SERVER_PRODUCT_NAME = ServerInfoRequest.SERVER_PRODUCT_NAME;
    public static final String SERVER_VERSION = ServerInfoRequest.SERVER_VERSION;
    public static final String SERVER_BUILD = ServerInfoRequest.SERVER_BUILD;
    public static final String SERVER_START_TIME = ServerInfoRequest.SERVER_START_TIME;

    /**
     * Properties to return
     */
    private Properties properties = new Properties();

    public ServerInfoResponse(ServerInfoRequest request) {
        super(request);
    }

    /**This is a dummy constructor for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public ServerInfoResponse() {
        super();
    }
        
    
    /**
     * Sets a single property
     */
    @JsonIgnore
    public void setProperty(String key, String value) {
        if (key != null && value != null) {
            this.properties.setProperty(key.toLowerCase(), value);
        }
    }

    /**
     * Returns the server properties
     */
    public Properties getProperties() {
        return (this.properties);
    }

    @Override
    public String toString() {
        return ("Server info response");
    }

    /**This is a dummy method for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
