//$Header: /as2/de/mendelson/util/clientserver/codec/SimpleTimePeriodDeserializer.java 1     4/06/25 12:03 Heller $
package de.mendelson.util.clientserver.codec;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
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
 * Serialize a ImageIcon using Jackson
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class SimpleTimePeriodDeserializer extends StdDeserializer<SimpleTimePeriod> {

    public SimpleTimePeriodDeserializer() {
        super(SimpleTimePeriod.class);
    }

    @Override
    public SimpleTimePeriod deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            String base64 = parser.getValueAsString();
            byte[] periodBytes = Base64.getDecoder().decode(base64);
            try (ByteArrayInputStream inStream = new ByteArrayInputStream(periodBytes)) {
                try( DataInputStream dataIn = new DataInputStream(inStream)){
                    long start = dataIn.readLong();
                    long end = dataIn.readLong();
                    return( new SimpleTimePeriod(start, end) );
                }
            }
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}
