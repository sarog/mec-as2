//$Header: /as4/de/mendelson/util/security/cert/ResourceBundleCertificateValidity_de.java 3     14/01/26 14:42 Heller $
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
 * @version $Revision: 3 $
 */
public class ResourceBundleCertificateValidity_de extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"state." + CertificateValiditySettings.STATE_CHAIN_INVALID, "Fehlerhafte Zertifizierungshierarchie"},
        {"state." + CertificateValiditySettings.STATE_DATE_INVALID, "Abgelaufen"},
        {"state." + CertificateValiditySettings.STATE_MENDELSON_PUBLIC_CERT, "Öffentlicher mendelson Testschlüssel - nicht im produktiven Betrieb verwenden"},
        {"state." + CertificateValiditySettings.STATE_CRL_CERT_NOT_READABLE, "(CRL) Zertifikat nicht lesbar"},
        {"state." + CertificateValiditySettings.STATE_CRL_DOWNLOAD_FAILED, "(CRL) Download fehlgeschlagen"},
        {"state." + CertificateValiditySettings.STATE_CRL_EXPIRED, "(CRL) Antwort abgelaufen"},
        {"state." + CertificateValiditySettings.STATE_CRL_INVALID_SIGNATURE, "(CRL) Ungültige Signatur"},
        {"state." + CertificateValiditySettings.STATE_CRL_IN_BAD_FORMAT, "(CRL) Ungültiges Format"},
        {"state." + CertificateValiditySettings.STATE_CRL_ISSUER_MISSING, "(CRL) Aussteller fehlt - bitte übergeordnetes Zertifikat importieren"},
        {"state." + CertificateValiditySettings.STATE_CRL_MALFORMED_URL, "(CRL) Fehlerhafte URL"},
        {"state." + CertificateValiditySettings.STATE_CRL_NOT_REACHABLE, "(CRL) URL nicht erreichbar"},
        {"state." + CertificateValiditySettings.STATE_CRL_NO_CDP_EXTENSION, "(CRL) Fehlende Erweiterung (Extension)"},
        {"state." + CertificateValiditySettings.STATE_CRL_OTHER_PROBLEM, "(CRL) Unspezifisches Problem"},
        {"state." + CertificateValiditySettings.STATE_CRL_REVOKED, "(CRL) Durch CA gesperrt (Revoked)"},
        {"state." + CertificateValiditySettings.STATE_CRL_UNABLE_TO_EXTRACT_CRL_URL, "(CRL) URL konnte nicht extrahiert werden"},
        {"state." + CertificateValiditySettings.STATE_CRL_UNSUPPORTED_CRL_URL_SCHEME, "(CRL) Nicht unterstütztes URL Schema"},};

}
