//$Header: /as2/de/mendelson/comm/as2/message/MessageDirectionType.java 1     23/03/26 12:54 Heller $
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
 * Stores all possible message direction types for the mendelson AS2
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public enum MessageDirectionType {

    UNKNOWN(-1),
    //all is just for the filter functionality
    ALL(0),
    IN(1),
    OUT(2);

    private final int id;

    private MessageDirectionType(int id) {
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
    public static MessageDirectionType of(int id) {
        for (MessageDirectionType direction : values()) {
            if (direction.id == id) {
                return direction;
            }
        }
        return UNKNOWN;
    }
}
