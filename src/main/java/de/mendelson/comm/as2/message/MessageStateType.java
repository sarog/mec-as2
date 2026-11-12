//$Header: /as2/de/mendelson/comm/as2/message/MessageStateType.java 1     23/03/26 13:41 Heller $
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
 * Stores all possible message state types for the mendelson AS2
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public enum MessageStateType {

    FINISHED(1),
    PENDING(2),
    STOPPED(3);

    private final int id;

    private MessageStateType(int id) {
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
    public static MessageStateType of(int id) {
        for (MessageStateType stateType : values()) {
            if (stateType.id == id) {
                return stateType;
            }
        }
        return PENDING;
    }
}
