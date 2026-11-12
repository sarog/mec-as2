//$Header: /as2/de/mendelson/util/security/cert/KeystoreCertificateDeserializer.java 2     13/06/25 11:22 Heller $
package de.mendelson.util.security.cert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Serialize a KeystoreCertificate using Jackson
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class KeystoreCertificateDeserializer extends StdDeserializer<KeystoreCertificate> {

    public KeystoreCertificateDeserializer() {
        super(KeystoreCertificate.class);
    }

    @Override
    public KeystoreCertificate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            byte[] bytes = parser.getBinaryValue();
            return KeystoreCertificate.deserialize(bytes);
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}
