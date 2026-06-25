//$Header: /as2/de/mendelson/comm/as2/message/MessageContentTransferEncodingType.java 2     23/03/26 13:09 Heller $
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
 * Stores all possible message content transfer encoding types for the mendelson AS2
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public enum MessageContentTransferEncodingType {

    BINARY(1, "binary"),
    BASE64(2, "base64");

    private final int id;
    private final String displayStr;

    private MessageContentTransferEncodingType(int id, String displayStr) {
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
    public static MessageContentTransferEncodingType of(int id) {
        for (MessageContentTransferEncodingType transferEncoding : values()) {
            if (transferEncoding.id == id) {
                return transferEncoding;
            }
        }
        return BINARY;
    }
}
