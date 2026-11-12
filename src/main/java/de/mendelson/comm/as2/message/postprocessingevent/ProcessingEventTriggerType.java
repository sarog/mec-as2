//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ProcessingEventTriggerType.java 1     31/03/26 9:25 Heller $
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
public enum ProcessingEventTriggerType {

    SEND_SUCCESS(1),
    SEND_FAILURE(2),
    RECEIPT_SUCCESS(3);

    private final int id;

    private ProcessingEventTriggerType(int id) {
        this.id = id;
    }

    @JsonValue
    public int toInt() {
        return id;
    }
    
    /**
     * Generates a ProcessingEventType from a given int
     */
    @JsonCreator
    public static ProcessingEventTriggerType of(int id) {
        for (ProcessingEventTriggerType stateType : values()) {
            if (stateType.id == id) {
                return stateType;
            }
        }
        return RECEIPT_SUCCESS;
    }
}
