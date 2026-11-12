//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ProcessingEventType.java 1     31/03/26 8:58 Heller $
package de.mendelson.comm.as2.message.postprocessingevent;

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
 * Stores all possible ProcessingEventType for the mendelson AS2
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public enum ProcessingEventType {

    EXECUTE_SHELL(1),
    MOVE_TO_PARTNER(2),
    MOVE_TO_DIR(3);

    private final int id;

    private ProcessingEventType(int id) {
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
    public static ProcessingEventType of(int id) {
        for (ProcessingEventType stateType : values()) {
            if (stateType.id == id) {
                return stateType;
            }
        }
        return EXECUTE_SHELL;
    }
}
