//$Header: /oftp2/de/mendelson/util/clientserver/codec/ImageIconDeserializer.java 5     2/03/26 10:11 Heller $
package de.mendelson.util.clientserver.codec;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
 * @version $Revision: 5 $
 */
public class ImageIconDeserializer extends StdDeserializer<ImageIcon> {

    /**
     * Reuse images
     */
    private static final Map<byte[], ImageIcon> CACHE = new ConcurrentHashMap<byte[], ImageIcon>();

    public ImageIconDeserializer() {
        super(ImageIcon.class);
    }

    @Override
    public ImageIcon deserialize(JsonParser parser, DeserializationContext context) throws IOException {
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
