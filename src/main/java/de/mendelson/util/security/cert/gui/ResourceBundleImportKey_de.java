//$Header: /as4/de/mendelson/util/security/cert/gui/ResourceBundleImportKey_de.java 6     6/11/23 11:38 Heller $ 
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
 * @version $Revision: 6 $
 */
public class ResourceBundleImportKey_de extends MecResourceBundle {

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
        {"button.cancel", "Abbruch"},
        {"button.browse", "Durchsuchen"},
        {"keystore.contains.nokeys", "Diese Schl�sseldatei beinhaltet keine privaten Schl�ssel."},
        {"label.importkey", "Dateiname"},
        {"label.importkey.hint", "Schl�sseldatei, die importiert werden soll (PKCS#12, JKS)"},
        {"label.keypass", "Passwort"},
        {"label.keypass.hint", "Keystore Passwort (PKCS#12, JKS)"},
        {"title", "Schl�ssel aus Schl�sseldatei importieren (PKCS#12, JKS Format)"},
        {"filechooser.key.import", "Bitte w�hlen Sie eine PKCS#12/JKS Schl�sseldatei Datei f�r den Import"},
        {"multiple.keys.message", "Bitte w�hlen Sie den zu importierenden Schl�ssel"},
        {"multiple.keys.title", "Mehrere Schl�ssel enthalten"},
        {"key.import.success.message", "Der Schl�ssel wurde erfolgreich importiert."},
        {"key.import.success.title", "Erfolg"},
        {"key.import.error.message", "Es trat ein Fehler w�hrend des Importprozesses auf.\n{0}"},
        {"key.import.error.title", "Fehler"},
        {"enter.keypassword", "Bitte geben Sie das Schl�sselpasswort f�r \"{0}\" ein"},
    };

}
