//$Header: /oftp2/de/mendelson/util/clientserver/codec/BufferedImageSerializer.java 4     2/03/26 10:21 Heller $
package de.mendelson.util.clientserver.codec;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Serialize a BufferedImage using Jackson
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class BufferedImageSerializer extends StdSerializer<BufferedImage> {

    public BufferedImageSerializer() {
        super(BufferedImage.class);
    }

    @Override
    public void serialize(BufferedImage image, JsonGenerator generator, SerializerProvider provider) throws IOException {
        try {
            byte[] imageBytes = ImageSerializationUtil.serializeBufferedImage(image);
            generator.writeBinary(imageBytes);
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}
