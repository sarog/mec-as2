//$Header: /as2/de/mendelson/util/systemevents/notification/NotificationDataDeserializerAS2.java 1     4/06/25 12:04 Heller $
package de.mendelson.util.systemevents.notification;

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
public class NotificationDataDeserializerAS2 extends StdDeserializer<NotificationData> {

    public NotificationDataDeserializerAS2() {
        super(NotificationData.class);
    }

    @Override
    public NotificationData deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        return(parser.readValueAs(NotificationDataImplAS2.class));
    }

}
