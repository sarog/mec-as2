//$Header: /oftp2/de/mendelson/util/clientserver/codec/BufferedImageDeserializer.java 2     13/06/25 13:25 Heller $
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

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * DeSerialize a BufferedImage using Jackson
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class BufferedImageDeserializer extends StdDeserializer<BufferedImage> {

    /**
     * Reuse images
     */
    private static final Map<byte[], BufferedImage> CACHE = new ConcurrentHashMap<byte[], BufferedImage>();

    public BufferedImageDeserializer() {
        super(Icon.class);
    }

    @Override
    public BufferedImage deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            BufferedImage image;
            byte[] imageBytes = parser.getBinaryValue();
            image = CACHE.get(imageBytes);
            if (image == null) {
                //deserialize image
                image = ImageSerializationUtil.deserializeBufferedImage(imageBytes);
                CACHE.put(imageBytes, image);
            }
            return (image);
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}
