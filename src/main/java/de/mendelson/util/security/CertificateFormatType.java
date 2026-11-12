//$Header: /as2/de/mendelson/util/security/CertificateFormatType.java 1     31/03/26 17:04 Heller $
package de.mendelson.util.security;

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
 * Stores all possible CertificateFormatTypes
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public enum CertificateFormatType {

    PEM("PEM"),
    PEM_CHAIN("PEM_CHAIN"),
    DER("DER"),
    PKCS7("PKCS#7"),
    SSH2("SSH2");
    
    private final String name;

    private CertificateFormatType(String name) {
        this.name = name;
    }

    @Override
    @JsonValue
    public String toString() {
        return name;
    }
    
    /**
     * Generates a CertificateFormatTypes from a given Str
     */
    @JsonCreator
    public static CertificateFormatType of(String name) {
        for (CertificateFormatType stateType : values()) {
            if (stateType.name.equalsIgnoreCase(name)) {
                return stateType;
            }
        }
        return PEM;
    }
}
