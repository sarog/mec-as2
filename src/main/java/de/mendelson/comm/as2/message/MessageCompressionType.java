//$Header: /as2/de/mendelson/comm/as2/message/MessageCompressionType.java 2     23/03/26 12:56 Heller $
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
 * Stores all possible compression types for the mendelson AS2
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public enum MessageCompressionType {

    UNKNOWN(0),
    NONE(1),
    ZLIB(2);

    private final int id;

    private MessageCompressionType(int id) {
        this.id = id;
    }

    @JsonValue
    public int toInt() {
        return id;
    }
    
    /**
     * Generates a MessageCompression from a given int
     */
    @JsonCreator
    public static MessageCompressionType of(int id) {
        for (MessageCompressionType compressionType : values()) {
            if (compressionType.id == id) {
                return compressionType;
            }
        }
        return UNKNOWN;
    }
}
