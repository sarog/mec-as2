//$Header: /as2/de/mendelson/util/clientserver/codec/LogLevelDeserializer.java 2     13/06/25 11:22 Heller $
package de.mendelson.util.clientserver.codec;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
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
 * Serialize a ImageIcon using Jackson
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class LogLevelDeserializer extends StdDeserializer<Level> {

    public LogLevelDeserializer() {
        super(Level.class);
    }

    @Override
    public Level deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            byte[] levelBytes = parser.getBinaryValue();
            try (ByteArrayInputStream inStream = new ByteArrayInputStream(levelBytes)) {
                try( DataInputStream dataIn = new DataInputStream(inStream)){
                    int nameLength = dataIn.readInt();
                    byte[] nameBytes = dataIn.readNBytes(nameLength);
                    Level level = Level.parse(new String( nameBytes, StandardCharsets.UTF_8));
                    return( level );
                }
            }
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}
