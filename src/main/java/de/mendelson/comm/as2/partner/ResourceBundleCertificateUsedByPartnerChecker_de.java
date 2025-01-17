//$Header: /as2/de/mendelson/comm/as2/partner/ResourceBundleCertificateUsedByPartnerChecker_de.java 6     2/11/23 15:52 Heller $ 
package de.mendelson.comm.as2.partner;

import de.mendelson.util.MecResourceBundle;

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
 * @version $Revision: 6 $
 */
public class ResourceBundleCertificateUsedByPartnerChecker_de extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"used.crypt", "Datenverschlüsselung"},
        {"used.sign", "Digitale Signatur"},
        {"used.crypt.overwritelocalsecurity", "Datenverschlüsselung (Überschreibt lokale Station)"},
        {"used.sign.overwritelocalsecurity", "Digitale Signatur (Überschreibt lokale Station)"},
    };

}
