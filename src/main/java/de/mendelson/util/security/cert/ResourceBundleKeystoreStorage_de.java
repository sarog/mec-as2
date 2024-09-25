//$Header: /as2/de/mendelson/util/security/cert/ResourceBundleKeystoreStorage_de.java 9     23/11/23 10:22 Heller $
package de.mendelson.util.security.cert;

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
 * @version $Revision: 9 $
 */
public class ResourceBundleKeystoreStorage_de extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"error.save.notloaded", "Keystore kann nicht gespeichert werden, er wurde noch nicht geladen."},
        {"error.delete.notloaded", "Der Eintrag konnte nicht gelöscht werden, der unterliegende Keystore wurde noch nicht geladen."},
        {"error.readaccess", "Der Keystore konnte nicht gelesen werden: Kein Lesezugriff möglich auf \"{0}\"."},
        {"error.filexists", "Der Keystore konnte nicht gelesen werden: Die Keystore Datei \"{0}\" existiert nicht."},
        {"error.notafile", "Der Keystore konnte nicht gelesen werden: Die Keystore Datei \"{0}\" ist keine Datei."},
        {"error.nodata", "Der Keystore konnte nicht gelesen werden: Keine Daten verfügbar"},
        {"error.empty", "Der Keystore konnte nicht gelesen werden: Die Keystore Daten müssen länger als 0 sein."},
        {"error.save", "Die Daten des Keystores konnte nicht gespeichert werden."},
        {"keystore.read.failure", "Das System konnte die unterliegenden Zertifikate nicht lesen. Fehlermeldung: \"{0}\". Bitte prüfen Sie, ob Sie das richtige Passwort für den Keystore verwenden."},
        {"moved.keystore.to.db", "Importiere die Keystore Daten von \"{0}\" in das System - Verwendungszweck ist {1}. Eventuell bestehende Schlüssel/Zertifikate wurden gelöscht."},
        {"moved.keystore.reason.commandline", "Der Import wurde durch einen Befehlszeilenparameter beim Start des Servers ausgelöst."},
        {"moved.keystore.reason.initial", "Der Import wurde durchgeführt, weil derzeit kein systeminterner Schlüsselspeicher vorhanden ist. Dies ist ein initialer Vorgang."},
        {"moved.keystore.to.db.title", "Import einer Keystore Datei ({0})"},
    };

}
