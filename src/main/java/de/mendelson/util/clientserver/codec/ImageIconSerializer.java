//$Header: /oftp2/de/mendelson/util/clientserver/codec/ImageIconSerializer.java 6     2/03/26 10:11 Heller $
package de.mendelson.util.clientserver.codec;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Serialize a KeystoreCertificate using Jackson
 *
 * @author S.Heller
 * @version $Revision: 6 $
 */
public class ImageIconSerializer extends StdSerializer<ImageIcon> {

    public ImageIconSerializer() {
        super(ImageIcon.class);
    }

    @Override
    public void serialize(ImageIcon icon, JsonGenerator generator, SerializerProvider provider) throws IOException {
        try {
            BufferedImage bufferedImage;
            if (icon.getImage() instanceof BufferedImage) {
                bufferedImage = (BufferedImage) icon.getImage();
            } else {
                bufferedImage = new BufferedImage(
                        icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB
                );
                Graphics2D g2d = bufferedImage.createGraphics();
                icon.paintIcon(null, g2d, 0, 0);
                g2d.dispose();
            }
            byte[] imageBytes = ImageSerializationUtil.serializeBufferedImage(bufferedImage);
            generator.writeBinary(imageBytes);
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}
