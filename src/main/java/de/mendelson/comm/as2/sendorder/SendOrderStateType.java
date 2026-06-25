//$Header: /as2/de/mendelson/comm/as2/sendorder/SendOrderStateType.java 1     31/03/26 8:24 Heller $
package de.mendelson.comm.as2.sendorder;

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
 * Stores all possible send order states
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public enum SendOrderStateType {

    WAITING(0),
    PROCESSING(1);

    private final int id;

    private SendOrderStateType(int id) {
        this.id = id;
    }

    @JsonValue
    public int toInt() {
        return id;
    }
    
    /**
     * Generates a SendOrderStateType from a given int
     */
    @JsonCreator
    public static SendOrderStateType of(int id) {
        for (SendOrderStateType stateType : values()) {
            if (stateType.id == id) {
                return stateType;
            }
        }
        return WAITING;
    }
}
