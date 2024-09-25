//$Header: /oftp2/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyPEM_de.java 4     7/12/22 16:31 Heller $ 
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
 * @version $Revision: 4 $
 */
public class ResourceBundleImportKeyPEM_de extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"button.ok", "Ok"},
        {"button.cancel", "Abbrechen"},
        {"button.browse", "Durchsuchen"},
        {"label.importkey", "Schl�sseldatei (PEM)"},
        {"label.importkey.hint", "Dateiinhalt beginnt mit --- BEGIN PRIVATE KEY ---"},
        {"label.importcert", "Zertifikatdatei (PEM)"},
        {"label.importcert.hint", "Dateiinhalt beginnt mit --- BEGIN CERTIFICATE ---"},
        {"label.alias", "Alias"},
        {"label.alias.hint", "Neuer Alias, der f�r diesen Schl�ssel verwendet werden soll"},
        {"label.keypass", "Passwort"},
        {"label.keypass.hint", "Passwort privater Schl�ssel"},
        {"title", "Schl�ssel importieren (PEM Format)"},
        {"filechooser.cert.import", "Bitte w�hlen Sie das zu importierende Zertifikat"},
        {"filechooser.key.import", "Bitte w�hlen Sie die zu importierende Schl�sseldatei (PEM Format)"},
        {"key.import.success.message", "Der Schl�ssel wurde erfolgreich importiert."},
        {"key.import.success.title", "Erfolg"},
        {"key.import.error.message", "Es gab einen Fehler beim Import des Schl�ssels.\n{0}"},
        {"key.import.error.title", "Fehler"},};

}
