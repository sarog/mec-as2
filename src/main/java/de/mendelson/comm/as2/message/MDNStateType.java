//$Header: /as2/de/mendelson/comm/as2/message/MDNStateType.java 2     26/03/26 9:37 Heller $
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
 * Stores all possible MDN state types for the mendelson AS2
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public enum MDNStateType {

    PROCESSED(1),
    ERROR(2);

    private final int id;

    private MDNStateType(int id) {
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
    public static MDNStateType of(int id) {
        for (MDNStateType stateType : values()) {
            if (stateType.id == id) {
                return stateType;
            }
        }
        return PROCESSED;
    }
}
