//$Header: /as2/de/mendelson/util/clientserver/messages/LoginRequest.java 20    23/03/26 8:03 Heller $
package de.mendelson.util.clientserver.messages;

import de.mendelson.util.clientserver.ClientType;
import de.mendelson.util.clientserver.SerializationDummy;
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
 * Msg for the client server protocol. This is the initial message that should
 * be send to the server
 *
 * @author S.Heller
 * @version $Revision: 20 $
 */
public final class LoginRequest extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username = null;
    private char[] password = null;
    private String clientOSName;
    /**
     * The servers require a special client version/id because client and server
     * must be compatible. This is set here
     */
    private String clientId = null;
    private ClientType clientType;

    public LoginRequest(ClientType clientType) {
        super();
        this.clientType = clientType;
        this.clientOSName = System.getProperty("os.name");
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public LoginRequest() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return (new String(this.password));
    }

    public void setPasswd(char[] passwd) {
        this.setPassword(passwd);
    }

    @Override
    public String toString() {
        return ("Login request for user " + this.username);
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the clientOSName
     */
    public String getClientOSName() {
        return clientOSName;
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }

    /**
     * @return the clientType
     */
    public ClientType getClientType() {
        return clientType;
    }


    /**
     * @param password the password to set
     */
    public void setPassword(char[] password) {
        this.password = password;
    }

    /**
     * @param clientOSName the clientOSName to set
     */
    public void setClientOSName(String clientOSName) {
        this.clientOSName = clientOSName;
    }

    /**
     * @param clientType the clientType to set
     */
    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

}
