//$Header: /as2/de/mendelson/util/security/encryption/EncryptionDisplayImplAS2.java 9     4/02/26 13:23 Heller $
package de.mendelson.util.security.encryption;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;

/**
 * Container superclass for the encryption rendering
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class EncryptionDisplayImplAS2 extends EncryptionDisplay {

    /**
     * Icons, multi resolution
     */
    public static final MendelsonMultiResolutionImage IMAGE_ENCRYPTION_STRONG
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/encryption/encryption_strong.svg",
                    ListCellRendererEncryption.IMAGE_HEIGHT);
    public static final MendelsonMultiResolutionImage IMAGE_ENCRYPTION_WEAK
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/encryption/encryption_weak.svg",
                    ListCellRendererEncryption.IMAGE_HEIGHT);
    public static final MendelsonMultiResolutionImage IMAGE_ENCRYPTION_BROKEN
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/encryption/encryption_broken.svg",
                    ListCellRendererEncryption.IMAGE_HEIGHT);

    private static final ImageIcon ICON_ENCRYPTION_STRONG = new ImageIcon(IMAGE_ENCRYPTION_STRONG
            .toMinResolution(ListCellRendererEncryption.IMAGE_HEIGHT));
    private static final ImageIcon ICON_ENCRYPTION_WEAK = new ImageIcon(IMAGE_ENCRYPTION_WEAK
            .toMinResolution(ListCellRendererEncryption.IMAGE_HEIGHT));
    private static final ImageIcon ICON_ENCRYPTION_BROKEN = new ImageIcon(IMAGE_ENCRYPTION_BROKEN
            .toMinResolution(ListCellRendererEncryption.IMAGE_HEIGHT));

    private static final MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleEncryptionAS2.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    public EncryptionDisplayImplAS2(Integer wrappedValue) {
        super(wrappedValue);
    }

    @Override
    public ImageIcon getIcon() {
        Integer encryptionInt = (Integer) this.getWrappedValue();
        return (this.getRenderImage(encryptionInt.intValue()));
    }

    @Override
    public String getText() {
        return (rb.getResourceString("encryption." + this.getWrappedValue().toString()));
    }

    /**
     * Computes the render image by the given encryption constant
     *
     * @param encryption
     */
    private ImageIcon getRenderImage(int encryption) {
        if (encryption == EncryptionConstantsAS2.ENCRYPTION_NONE) {
            return (ICON_ENCRYPTION_BROKEN);
        } else if (encryption == EncryptionConstantsAS2.ENCRYPTION_RC2_128
                || encryption == EncryptionConstantsAS2.ENCRYPTION_RC2_196
                || encryption == EncryptionConstantsAS2.ENCRYPTION_RC2_40
                || encryption == EncryptionConstantsAS2.ENCRYPTION_RC2_64
                || encryption == EncryptionConstantsAS2.ENCRYPTION_RC2_UNKNOWN
                || encryption == EncryptionConstantsAS2.ENCRYPTION_RC4_128
                || encryption == EncryptionConstantsAS2.ENCRYPTION_RC4_40
                || encryption == EncryptionConstantsAS2.ENCRYPTION_RC4_56
                || encryption == EncryptionConstantsAS2.ENCRYPTION_RC4_UNKNOWN
                || encryption == EncryptionConstantsAS2.ENCRYPTION_DES
                || encryption == EncryptionConstantsAS2.ENCRYPTION_3DES) {
            return (ICON_ENCRYPTION_WEAK);
        } else {
            return (ICON_ENCRYPTION_STRONG);
        }
    }

}
