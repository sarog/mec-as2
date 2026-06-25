//$Header: /oftp2/de/mendelson/util/security/cert/X509CertificateSerializer.java 4     13/10/25 17:58 Heller $
package de.mendelson.util.security.cert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
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
public class X509CertificateSerializer extends StdSerializer<X509Certificate> {

    public X509CertificateSerializer() {
        super(X509Certificate.class);
    }

    @Override
    public void serialize(X509Certificate certificate, JsonGenerator generator, SerializerProvider provider) throws IOException {
        try {
            byte[] encoded = certificate.getEncoded();
            generator.writeBinary(encoded);
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}
