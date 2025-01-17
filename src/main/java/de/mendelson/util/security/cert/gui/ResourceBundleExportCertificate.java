//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleExportCertificate.java 11    2/11/23 15:53 Heller $ 
package de.mendelson.util.security.cert.gui;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.security.cert.KeystoreCertificate;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize gui entries
 *
 * @author S.Heller
 * @version $Revision: 11 $
 */
public class ResourceBundleExportCertificate extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"button.ok", "Ok"},
        {"button.cancel", "Cancel"},
        {"button.browse", "Browse"},
        {"title", "Export X.509 Certificate"},
        {"label.exportfile", "Filename"},
        {"label.exportfile.hint", "File with exported certificate(s) to generate"},
        {"label.alias", "Alias"},
        {"label.exportformat", "Format"},
        {"error.empty.certificate", "No certificate data available" },
        {"filechooser.certificate.export", "Please select the filename to export the certificate to."},
        {"certificate.export.error.title", "Certificate export failed"},
        {"certificate.export.error.message", "The export of this certificate failed:\n{0}"},
        {"certificate.export.success.title", "Success"},
        {"certificate.export.success.message", "The certificate has been exported successfully to\n\"{0}\"."},
        {KeystoreCertificate.CERTIFICATE_FORMAT_PEM, "Text format (PEM, *.cer)"},
        {KeystoreCertificate.CERTIFICATE_FORMAT_DER, "Binary format (DER, *.cer)"},
        {KeystoreCertificate.CERTIFICATE_FORMAT_PKCS7, "With full trust chain (PKCS#7, *.p7b)"},
        {KeystoreCertificate.CERTIFICATE_FORMAT_SSH2, "SSH2 format (public key, *.pub)"},        
    };

}
