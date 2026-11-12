//$Header: /as4/de/mendelson/util/security/crmf/ResourceBundleCRMF_de.java 1     8/01/26 15:50 Heller $
package de.mendelson.util.security.crmf;

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
public class ResourceBundleCRMF_de extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"title", "CRMF-Anfrageerstellung (BDEW)"},
        {"label.root.ca", "Sub-CA Stammzertifikat"},
        {"label.root.ca.help", "<HTML><strong>Sub-CA Stammzertifikat</strong><br><br>"
            + "Die erzeugte CRMF-Anfrage wird in eine CMP-Nachrichtenstruktur gekapselt, die ein Empfängerfeld (Recipient) enthält.<br>"
            + "Um eine standardkonforme Übertragung zu gewährleisten, muss dieses Empfängerfeld "
            + "den spezifischen Distinguished Name (DN) der CA aus dem X.500-Namensraum enthalten. "
            + "Diesen finden Sie im Feld \"Subject\" (Betreff) des CA-eigenen Zertifikats "
            + "(z. B. CN=SM-Test-PKI-DE). Dies stellt sicher, dass die erzeugte Nachrichtenstruktur "
            + "den BDEW/Smart-Metering-PKI-Standards entspricht. Für eine exakte Übereinstimmung dieses Wertes "
            + "wird das (Sub-) CA-Stammzertifikat benötigt."
            + "</HTML>"},
        {"button.ok", "OK"},
        {"button.cancel", "Abbrechen"},
        {"label.key.tls", "TLS Schlüssel"},
        {"label.key.encryption", "Verschlüsselungsschlüssel"},
        {"label.key.signature", "Signaturschlüssel"},
        {"success.title", "CRMF Erstellung erfolgreich"},
        {"success.body", "Die CRMF Datei wurde unter {0} gespeichert"},
        {"password.hint", "Initiales Einmalpasswort"},
        {"label.initial", "Initialisierung (Initial Request)"},
        {"label.initial.help", "<HTML><strong>Initialisierung (Initial Request)</strong><br><br>"
            + "Wählen Sie dies für die Erstregistrierung bei der CA. Die Authentifizierung erfolgt über Ihr initiales Registrierungspasswort."
            + "</HTML>"},
        {"label.update", "Aktualisierung (Update Request)"},
        {"label.update.help", "<HTML><strong>Aktualisierung (Update Request)</strong><br><br>"
            + "Wählen Sie dies, um ein bestehendes Zertifikat vor dessen Ablauf zu erneuern. Die Authentifizierung erfolgt automatisch "
            + "über Ihr aktuell gültiges Zertifikat."
            + "</HTML>"},};

}
