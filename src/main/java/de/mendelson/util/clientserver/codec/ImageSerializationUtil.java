//$Header: /oftp2/de/mendelson/util/clientserver/codec/ImageSerializationUtil.java 3     2/03/26 10:11 Heller $
package de.mendelson.util.clientserver.codec;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Helper class to serialize/deserialize Images
 *
 * @author S.Heller
 * @version $Revision: 3 $
 */
public class ImageSerializationUtil {

    private ImageSerializationUtil() {
    }

    /**
     * Serializes a buffered image, includes compression. Compression might not be really useful here as there
     * are a lot of small icons and the compression algorithm makes no sense for this small data...
     */
    protected static byte[] serializeBufferedImage(BufferedImage bufferedImage) throws IOException {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[] pixelArray = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, pixelArray, 0, width);
        try (ByteArrayOutputStream compressedByteOut = new ByteArrayOutputStream()) {
            try (GZIPOutputStream gzipOut = new GZIPOutputStream(compressedByteOut)) {
                try (DataOutputStream dataOut = new DataOutputStream(gzipOut)) {
                    dataOut.writeInt(width);
                    dataOut.writeInt(height);
                    for (int pixel : pixelArray) {
                        dataOut.writeInt(pixel);
                    }
                    dataOut.flush();
                    gzipOut.finish();
                    return (compressedByteOut.toByteArray());
                }
            }
        }
    }

    /**
     * Deserializes a buffered image
     */
    protected static BufferedImage deserializeBufferedImage(byte[] compressedBytes) throws IOException {
        try (ByteArrayInputStream compressedByteIn = new ByteArrayInputStream(compressedBytes)) {
            try (GZIPInputStream gzipIn = new GZIPInputStream(compressedByteIn)) {
                try (DataInputStream dataIn = new DataInputStream(gzipIn)) {
                    int width = dataIn.readInt();
                    int height = dataIn.readInt();
                    int[] pixelArray = new int[width * height];
                    //direct read from the instream to the pixel array
                    for (int i = 0; i < pixelArray.length; i++) {
                        pixelArray[i] = dataIn.readInt();
                    }
                    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                    bufferedImage.setRGB(0, 0, width, height, pixelArray, 0, width);
                    return( bufferedImage );
                }
            }
        }
    }

}
