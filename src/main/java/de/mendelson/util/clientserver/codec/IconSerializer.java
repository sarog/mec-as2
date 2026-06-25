//$Header: /oftp2/de/mendelson/util/clientserver/codec/IconSerializer.java 5     2/03/26 10:11 Heller $
package de.mendelson.util.clientserver.codec;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
 * Serialize a Icon using Jackson
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class IconSerializer extends StdSerializer<Icon> {

    public IconSerializer() {
        super(Icon.class);
    }

    @Override
    public void serialize(Icon icon, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if( icon == null ){
            throw new IOException("IconSerializer: icon must not be null" );
        }
        byte[] imageBytes;
        //fast path - it is already a buffered image, no need to paint it
        if (icon instanceof ImageIcon && (((ImageIcon) icon).getImage() instanceof BufferedImage)) {
            BufferedImage bufferedImage = ((BufferedImage) ((ImageIcon) icon).getImage());
            imageBytes = ImageSerializationUtil.serializeBufferedImage(bufferedImage);
        } else {
            try {
                BufferedImage bufferedImage = new BufferedImage(
                        icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB
                );
                Graphics2D g2d = bufferedImage.createGraphics();
                icon.paintIcon(null, g2d, 0, 0);
                g2d.dispose();
                imageBytes = ImageSerializationUtil.serializeBufferedImage(bufferedImage);

            } catch (Throwable e) {
                throw new IOException(e.getMessage(), e);
            }
        }
        generator.writeBinary(imageBytes);
    }

}
