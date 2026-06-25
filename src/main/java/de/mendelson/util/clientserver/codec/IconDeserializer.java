//$Header: /oftp2/de/mendelson/util/clientserver/codec/IconDeserializer.java 4     13/06/25 13:25 Heller $
package de.mendelson.util.clientserver.codec;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;

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
 * @version $Revision: 4 $
 */
public class IconDeserializer extends StdDeserializer<Icon> {

    /**
     * Reuse images
     */
    private static final Map<byte[], ImageIcon> CACHE = new ConcurrentHashMap<byte[], ImageIcon>();

    public IconDeserializer() {
        super(Icon.class);
    }

    @Override
    public Icon deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            ImageIcon returnIcon;
            byte[] imageBytes = parser.getBinaryValue();
            returnIcon = CACHE.get(imageBytes);
            if (returnIcon == null) {
                //deserialize image
                BufferedImage image = ImageSerializationUtil.deserializeBufferedImage(imageBytes);
                returnIcon = new ImageIcon(image);
                CACHE.put(imageBytes, returnIcon);
            }
            return (returnIcon);
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }


}
