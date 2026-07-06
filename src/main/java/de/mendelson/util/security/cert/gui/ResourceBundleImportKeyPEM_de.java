//$Header: /as4/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyPEM_de.java 1     9/12/25 17:09 Heller $ 
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
public class ResourceBundleImportKeyPEM_de extends MecResourceBundle {

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
        {"button.cancel", "Abbrechen"},
        {"button.browse", "Browse"},
        {"label.importkey", "Dateiname"},
        {"label.importkey.hint", "PEM Datei"},
        {"label.keypass", "Schlüsselpasswort"},
        {"label.keypass.hint", "Schlüsselpasswort in der PEM Datei"},
        {"title", "Schlüssel aus PEM Datei importieren"},
        {"filechooser.key.import", "Bitte wählen Sie die PEM Datei"},
        {"key.import.success.message", "Der Schlüssel wurde erfolgreich importiert."},
        {"key.import.success.title", "Erfolg"},
        {"key.import.error.message", "Es gab ein Problem während des Importvorganges.\n{0}"},
        {"key.import.error.title", "Problem"},
        {"key.import.error.entry.exists", "Der Import ist nicht möglich - ein Eintrag mit diesem Fingerprint existiert bereits, der Alias ist {0}."},
    };

}
