//$Header: /as2/de/mendelson/util/clientserver/log/search/LoglineDeserializerAS2.java 2     5/06/25 15:04 Heller $
package de.mendelson.util.clientserver.log.search;

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
 * Serialize a Logline using Jackson
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class LoglineDeserializerAS2 extends StdDeserializer<Logline> {

    public LoglineDeserializerAS2() {
        super(Logline.class);
    }

    @Override
    public Logline deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return(parser.readValueAs(LoglineImplAS2.class));
    }

}
