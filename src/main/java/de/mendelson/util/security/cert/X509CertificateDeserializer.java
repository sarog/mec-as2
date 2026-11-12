//$Header: /oftp2/de/mendelson/util/security/cert/X509CertificateDeserializer.java 4     13/10/25 17:58 Heller $
package de.mendelson.util.security.cert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Serialize a X509Certificate using Jackson
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class X509CertificateDeserializer extends StdDeserializer<X509Certificate> {

    public X509CertificateDeserializer() {
        super(X509Certificate.class);
    }

    @Override
    public X509Certificate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            byte[] certBytes = parser.getBinaryValue();
            X509Certificate certificate = deserializeFromBytes(certBytes);
            return (certificate);
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }
    
    private static X509Certificate deserializeFromBytes(byte[] encodedCert) throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        try (InputStream in = new ByteArrayInputStream(encodedCert)) {
            return (X509Certificate) factory.generateCertificate(in);
        }
    }

}
