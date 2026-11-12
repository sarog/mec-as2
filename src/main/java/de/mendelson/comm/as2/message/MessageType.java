//$Header: /as2/de/mendelson/comm/as2/message/MessageType.java 3     26/03/26 9:37 Heller $
package de.mendelson.comm.as2.message;

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
 * Stores all possible message types for the mendelson AS2
 *
 * @author S.Heller
 * @version $Revision: 3 $
 */
public enum MessageType {

    //all is just used for the filter
    ALL(0),
    AS2(1),
    CEM(2),
    MDN(3);

    private final int id;

    private MessageType(int id) {
        this.id = id;
    }

    @JsonValue
    public int toInt() {
        return id;
    }
    
    /**
     * Generates a ClientType from a given int
     */
    @JsonCreator
    public static MessageType of(int id) {
        for (MessageType messageType : values()) {
            if (messageType.id == id) {
                return messageType;
            }
        }
        return AS2;
    }
}
