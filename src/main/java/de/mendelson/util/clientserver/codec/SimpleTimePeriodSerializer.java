//$Header: /as2/de/mendelson/util/clientserver/codec/SimpleTimePeriodSerializer.java 1     4/06/25 12:03 Heller $
package de.mendelson.util.clientserver.codec;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import org.jfree.data.time.SimpleTimePeriod;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Serialize a SimpleTimePeriod using Jackson
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class SimpleTimePeriodSerializer extends StdSerializer<SimpleTimePeriod> {

    public SimpleTimePeriodSerializer() {
        super(SimpleTimePeriod.class);
    }

    @Override
    public void serialize(SimpleTimePeriod simpleTimePeriod, JsonGenerator generator, SerializerProvider provider) throws IOException {
        try {
            byte[] serializedLevel;
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                try (DataOutputStream dataOut = new DataOutputStream(out)) {
                    dataOut.writeLong(simpleTimePeriod.getStartMillis());
                    dataOut.writeLong(simpleTimePeriod.getEndMillis());
                }
                serializedLevel = out.toByteArray();
            }
            generator.writeString(Base64.getEncoder().encodeToString(serializedLevel));
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}
