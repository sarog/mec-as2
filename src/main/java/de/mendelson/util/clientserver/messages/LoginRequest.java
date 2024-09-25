//$Header: /as2/de/mendelson/util/clientserver/messages/LoginRequest.java 9     4/06/18 12:22p Heller $
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
 * Msg for the client server protocol. This is the initial message that should
 * be send to the server
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class LoginRequest extends ClientServerMessage implements Serializable {

    public static final long serialVersionUID = 1L;
    private String username = null;
    private char[] password = null;
    /**
     * The servers require a special client version/id because client and server
     * must be compatible. This is set here
     */
    private String clientId = null;

    public String getUserName() {
        return username;
    }

    public void setUserName(String user) {
        this.username = user;
    }

    public String getPasswd() {
        return (new String(this.password));
    }

    public void setPasswd(char[] passwd) {
        this.password = passwd;
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

}
