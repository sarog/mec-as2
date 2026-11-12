//$Header: /as2/de/mendelson/util/clientserver/log/search/ServerSideLogfileFilterDeserializerAS2.java 1     4/06/25 14:51 Heller $
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
 * Serialize a ImageIcon using Jackson
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class ServerSideLogfileFilterDeserializerAS2 extends StdDeserializer<ServerSideLogfileFilter> {

    public ServerSideLogfileFilterDeserializerAS2() {
        super(ServerSideLogfileFilter.class);
    }

    @Override
    public ServerSideLogfileFilter deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return(parser.readValueAs(ServerSideLogfileFilterImplAS2.class));
    }

}
