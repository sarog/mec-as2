//$Header: /as4/de/mendelson/util/security/memkeystore/KeyAndCert.java 1     9/12/25 16:45 Heller $
package de.mendelson.util.security.memkeystore;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Container class for the transport of a key and a certificate
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class KeyAndCert {

    private final PrivateKey privateKey;
    private final X509Certificate certificate;
    private final char[] keypass;

    public KeyAndCert(PrivateKey privateKey, X509Certificate certificate, char[] keypass) {
        this.privateKey = privateKey;
        this.certificate = certificate;
        this.keypass = keypass;
    }

    /**
     * @return the privateKey
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * @return the certificate
     */
    public X509Certificate getCertificate() {
        return certificate;
    }

    /**
     * @return the keypass
     */
    public char[] getKeypass() {
        return keypass;
    }
}
