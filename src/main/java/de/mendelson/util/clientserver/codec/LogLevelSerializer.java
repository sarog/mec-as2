//$Header: /as2/de/mendelson/util/clientserver/codec/LogLevelSerializer.java 2     13/06/25 11:22 Heller $
package de.mendelson.util.clientserver.codec;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

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
public class LogLevelSerializer extends StdSerializer<Level> {

    public LogLevelSerializer() {
        super(Level.class);
    }

    @Override
    public void serialize(Level level, JsonGenerator generator, SerializerProvider provider) throws IOException {
        try {
            byte[] serializedLevel;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                try (DataOutputStream dataOut = new DataOutputStream(out)) {
                    byte[] name = level.getName().getBytes(StandardCharsets.UTF_8);
                    dataOut.writeInt(name.length);
                    dataOut.write(name);
                }
                serializedLevel = out.toByteArray();
            }
            generator.writeBinary(serializedLevel);
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}
