//$Header: /as4/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyPEM.java 1     9/12/25 16:54 Heller $ 
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
 * @version $Revision: 1 $
 */
public class ResourceBundleImportKeyPEM extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"button.ok", "Ok"},
        {"button.cancel", "Cancel"},
        {"button.browse", "Browse"},
        {"label.importkey", "Filename"},
        {"label.importkey.hint", "PEM file"},
        {"label.keypass", "Key password"},
        {"label.keypass.hint", "The key password used in the PEM file"},
        {"title", "Import a key from PEM"},
        {"filechooser.key.import", "Please select the PEM file for the import"},
        {"key.import.success.message", "The key has been imported successfully."},
        {"key.import.success.title", "Success"},
        {"key.import.error.message", "There occured an error during the import process.\n{0}"},
        {"key.import.error.title", "Error"},
        {"key.import.error.entry.exists", "Import not possible - an entry with this fingerprint does already exist, the alias is {0}."},
    };

}
