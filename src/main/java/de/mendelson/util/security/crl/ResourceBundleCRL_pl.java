//$Header: /as4/de/mendelson/util/security/crl/ResourceBundleCRL_pl.java 6     14/01/26 14:06 Heller $
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
 * ResourceBundle to localize a mendelson product
 *
 * @author S.Heller
 * @version $Revision: 6 $
 */
public class ResourceBundleCRL_pl extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"bad.crl", "Pobrane dane CRL nie mogą zostać przetworzone"},
        {"cert.read.error", "Nie można odczytać certyfikatu dla adresu URL listy odwołania"},
        {"crl.success", "Ok - certyfikat nie został unieważniony, ważne do {0}"},
        {"download.failed.from", "Pobieranie czarnej listy nie powiodło się ({0})"},
        {"error.url.retrieve", "Nie można odczytać adresu URL listy odwołania z certyfikatu"},
        {"failed.revoked", "Certyfikat został unieważniony: {0}"},
        {"malformed.crl.url", "Nieprawidłowy adres URL listy CRL ({0})"},
        {"module.name", "[czarna lista]"},
        {"no.crl.entry", "Certyfikat nie ma rozszerzenia odnoszącego się do adresu URL listy CRL"},
        {"no.https", "Problem z połączeniem z URI {0} - protokół HTTPS nie jest obsługiwany"},
        {"self.signed.skipped", "Podpisany samodzielnie - weryfikacja pominięta"},
        {"ca.skipped", "Główny urząd certyfikacji (root) lub punkt zaufania - pominięto weryfikację" },
        {"crl.expired", "Odebrana informacja CRL jest nieaktualna ({0})"},
        {"error.nextupdate.missing", "Odebrana informacja CRL nie zawiera informacji, jak długo jest ważna"},
        {"error.invalid.signature", "Błąd bezpieczeństwa - nieprawidłowy podpis w odpowiedzi"},
        {"error.invalid.nonce", "Błąd bezpieczeństwa - nieprawidłowy identyfikator nonce w odpowiedzi"},
        {"error.request.generation", "Problem podczas generowania żądania ({0})"},
        {"error.issuercertificate.required", "Nie znaleziono certyfikatu wystawcy do weryfikacji podpisu - proszę zaimportować certyfikat nadrzędny" },
    };
}
