//$Header: /as4/de/mendelson/util/security/crl/ResourceBundleCRL_de.java 9     14/01/26 14:06 Heller $
package de.mendelson.util.security.crl;

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
public class ResourceBundleCRL_de extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"module.name", "[Sperrliste]"},
        {"self.signed.skipped", "Selbssigniert - Überprüfung übersprungen"},
        {"ca.skipped", "CA-Root oder Trust-Anchor - Prüfung übersprungen" },
        {"crl.success", "Ok - Das Zertifikat ist nicht gesperrt, gültig bis {0}"},
        {"failed.revoked", "Das Zertifikat ist gesperrt: {0}"},
        {"malformed.crl.url", "Fehlerhafte CRL URL ({0})"},
        {"no.https", "Verbindungsproblem mit URI {0} - HTTPS wird nicht unterstützt"},
        {"bad.crl", "Die heruntergeladenen CRL Daten sind nicht verarbeitbar"},
        {"cert.read.error", "Zertifikat für die Sperrlisten URL kann nicht gelesen werden"},
        {"error.url.retrieve", "Sperrlisten URL kann nicht aus dem Zertifikat gelesen werden"},
        {"no.crl.entry", "Das Zertifikat hat keine Erweiterung, die auf eine CRL URL verweist"},
        {"download.failed.from", "Das Herunterladen der Sperrliste schlug fehl ({0})"},
        {"crl.expired", "Die empfangene CRL Information is veraltet ({0})"},
        {"error.nextupdate.missing", "Die empfangene CRL Information beinhaltet keine Information, wie lange sie gültig ist"},
        {"error.not.reachable.ldap", "Die CRL ist nicht über die LDAP {0} erreichbar"},
        {"error.invalid.signature", "Sicherheitsfehler - Ungültige Signatur in der Antwort"},
        {"error.invalid.nonce", "Sicherheitsfehler - Ungültiger Nonce in der Antwort"},
        {"error.request.generation", "Problem beim Generieren der Anfrage ({0})"},
        {"error.issuercertificate.required", "Kein Ausstellerzertifikat für die Signaturprüfung gefunden - bitte importieren Sie das übergeordnete Zertifikat" },
    };

}
