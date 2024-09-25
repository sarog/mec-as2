//$Header: /as4/de/mendelson/util/clientserver/clients/filesystemview/ResourceBundleFileBrowser_de.java 6     6/11/23 11:38 Heller $
package de.mendelson.util.clientserver.clients.filesystemview;

import de.mendelson.util.MecResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize the mendelson products
 *
 * @author S.Heller
 * @version $Revision: 6 $
 */
public class ResourceBundleFileBrowser_de extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        //dialog
        {"button.ok", "Auswählen"},
        {"button.cancel", "Abbrechen"},
        {"wait", "Bitte warten"},};

}
