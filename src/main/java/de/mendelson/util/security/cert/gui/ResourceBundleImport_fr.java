//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleImport_fr.java 2     2/11/23 15:53 Heller $ 
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
 * @version $Revision: 2 $
 */
public class ResourceBundleImport_fr extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"title", "Importer une cl�/un certificat"},
        {"info.whattoimport", "Que voulez-vous importer?" },
        {"button.ok", "Ok"},
        {"button.cancel", "Annuler"},        
    };
}