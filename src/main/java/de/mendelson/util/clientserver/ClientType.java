//$Header: /oftp2/de/mendelson/util/clientserver/ClientType.java 2     23/03/26 16:50 Heller $
package de.mendelson.util.clientserver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores all possible client types for the client-server connection
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public enum ClientType {

    UNSPECIFIED(0, "UNSPECIFIED"),
    RICH_CLIENT(1, "RICH CLIENT"),
    REST(2, "REST"),
    XML(3, "XML"),
    SENDORDER(4, "COMMAND SEND"),
    WEBINTERFACE(5, "WEB_INTERFACE"),
    COMMANDLINE_SHUTDOWN(6, "COMMANDLINE SHUTDOWN"),
    WEB(7, "WEB");

    private final int id;
    private final String displayStr;

    private ClientType(int id, String displayStr) {
        this.id = id;
        this.displayStr = displayStr;
    }

    @JsonValue
    public int toInt() {
        return id;
    }

    public String getDisplayStr(){
        return( this.displayStr );
    }
    
    /**
     * Generates a ClientType from a given int
     */
    @JsonCreator
    public static ClientType of(int id) {
        for (ClientType state : values()) {
            if (state.id == id) {
                return state;
            }
        }
        return UNSPECIFIED;
    }
}
