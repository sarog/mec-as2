//$Header: /as4/de/mendelson/util/security/cert/ResourceBundleCertificateValidity_pl.java 1     14/01/26 16:23 Heller $
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
* ResourceBundle to localize a mendelson product
* @author S.Heller
* @version $Revision: 1 $
*/
public class ResourceBundleCertificateValidity_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"state.1", "Wygasł"},
		{"state.1024", "(CRL) Odpowiedź wygasła"},
		{"state.1048576", "(CRL) Niespecyficzny problem"},
		{"state.131072", "(CRL) nie można wyodrębnić adresu URL"},
		{"state.16384", "(CRL) Nieprawidłowy format"},
		{"state.2048", "(CRL) Nieprawidłowy podpis"},
		{"state.256", "(CRL) Zablokowany przez urząd certyfikacji (cofnięty)"},
		{"state.262144", "(CRL) Pobieranie nie powiodło się"},
		{"state.32768", "(CRL) Certyfikat nieczytelny"},
		{"state.4", "Nieprawidłowa hierarchia certyfikacji"},
		{"state.4096", "(CRL) Brakujące rozszerzenie"},
		{"state.4194304", "Publiczny klucz testowy mendelsona - nie używać w pracy produkcyjnej"},
		{"state.512", "(CRL) URL niedostępny"},
		{"state.524288", "(CRL) Nieobsługiwany schemat URL"},
		{"state.8192", "(CRL) Nieprawidłowy adres URL"},
		{"state.8388608", "(CRL) Brak wystawcy - zaimportuj certyfikat wyższego poziomu"},
	};
}
