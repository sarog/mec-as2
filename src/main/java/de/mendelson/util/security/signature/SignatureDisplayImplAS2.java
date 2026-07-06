//$Header: /as2/de/mendelson/util/security/signature/SignatureDisplayImplAS2.java 9     4/02/26 12:16 Heller $
package de.mendelson.util.security.signature;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;

/**
 * Container superclass for the signature rendering
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class SignatureDisplayImplAS2 extends SignatureDisplay {

    /**
     * Icons, multi resolution
     */
    public static final MendelsonMultiResolutionImage IMAGE_SIGNATURE_STRONG
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/signature/signature_strong.svg",
                    ListCellRendererSignature.IMAGE_HEIGHT);
    public static final MendelsonMultiResolutionImage IMAGE_SIGNATURE_WEAK
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/signature/signature_weak.svg",
                    ListCellRendererSignature.IMAGE_HEIGHT);
    public static final MendelsonMultiResolutionImage IMAGE_SIGNATURE_BROKEN
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/signature/signature_broken.svg",
                    ListCellRendererSignature.IMAGE_HEIGHT);
    private static final ImageIcon ICON_SIGNATURE_STRONG = new ImageIcon(IMAGE_SIGNATURE_STRONG
            .toMinResolution(ListCellRendererSignature.IMAGE_HEIGHT));
    private static final ImageIcon ICON_SIGNATURE_WEAK = new ImageIcon(IMAGE_SIGNATURE_WEAK
            .toMinResolution(ListCellRendererSignature.IMAGE_HEIGHT));
    private static final ImageIcon ICON_SIGNATURE_BROKEN = new ImageIcon(IMAGE_SIGNATURE_BROKEN
            .toMinResolution(ListCellRendererSignature.IMAGE_HEIGHT));
    
    
    private static final MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleSignatureAS2.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    public SignatureDisplayImplAS2(Integer wrappedValue) {
        super(wrappedValue);

    }

    @Override
    public ImageIcon getIcon() {
        Integer signatureInt = (Integer) this.getWrappedValue();
        return (this.getRenderImage(signatureInt.intValue()));
    }

    @Override
    public String getText() {
        return (rb.getResourceString("signature." + this.getWrappedValue().toString()));
    }

    /**
     * Computes the render image by the given signature constant
     *
     * @param signature
     */
    private ImageIcon getRenderImage(int signature) {
        if (signature == SignatureConstantsAS2.SIGNATURE_NONE) {
            return (ICON_SIGNATURE_BROKEN);
        } else if (signature == SignatureConstantsAS2.SIGNATURE_MD5
                || signature == SignatureConstantsAS2.SIGNATURE_SHA1) {
            return (ICON_SIGNATURE_WEAK);
        } else {
            return (ICON_SIGNATURE_STRONG);
        }
    }

}
