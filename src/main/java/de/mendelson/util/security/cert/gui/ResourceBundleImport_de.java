//$Header: /as4/de/mendelson/util/security/cert/gui/ResourceBundleImport_de.java 5     6/11/23 11:38 Heller $ 
package de.mendelson.util.security.cert.gui;

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
 * @version $Revision: 5 $
 */
public class ResourceBundleImport_de extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"title", "Schl�ssel/Zertifikat importieren"},
        {"info.whattoimport", "Was m�chten Sie importieren?" },
        {"button.ok", "Ok"},
        {"button.cancel", "Abbruch"},
        
    };
}
