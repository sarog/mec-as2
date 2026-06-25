//$Header: /as2/de/mendelson/util/security/cert/ResourceBundleCertificateManager_pl.java 1     24/09/25 10:46 Heller $
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
public class ResourceBundleCertificateManager_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"access.problem", "Problemy z dostępem do {0}"},
		{"alias.hasno.key", "Plik certyfikatu nie zawiera klucza z aliasem \"{0}\"."},
		{"alias.hasno.privatekey", "Plik certyfikatu nie zawiera klucza prywatnego z aliasem \"{0}\"."},
		{"alias.notfound", "Plik certyfikatu nie zawiera certyfikatu z aliasem \"{0}\"."},
		{"certificate.not.found.fingerprint", "Certyfikat z odciskiem palca SHA-1 \"{0}\" nie istnieje."},
		{"certificate.not.found.fingerprint.withinfo", "Certyfikat z odciskiem palca SHA-1 \"{0}\" nie istnieje w systemie. ({1})"},
		{"certificate.not.found.issuerserial.withinfo", "Wymagany jest certyfikat z wystawcą \"{0}\" i numerem seryjnym \"{1}\", ale nie istnieje on w systemie ({2})."},
		{"certificate.not.found.ski.withinfo", "Certyfikat o identyfikatorze klucza podmiotu \"{0}\" nie istnieje w systemie. ({1})"},
		{"certificate.not.found.subjectdn.withinfo", "Certyfikat z subjectDN \"{0}\" nie istnieje w systemie. ({1})"},
		{"event.certificate.added.body", "Do systemu dodano nowy certyfikat z następującymi danymi:\n\n{0}"},
		{"event.certificate.added.subject", "{0}: Dodano nowy certyfikat (alias \"{1}\")."},
		{"event.certificate.deleted.body", "Następujący certyfikat został usunięty z systemu:\n\n{0}"},
		{"event.certificate.deleted.subject", "{0}: Certyfikat został usunięty (alias \"{1}\")."},
		{"event.certificate.modified.body", "Alias certyfikatu \"{0}\" zostanie zmieniony na \"{1}\"\n\n\nTo są dane certyfikatu:\n\n{2}"},
		{"event.certificate.modified.subject", "{0}: Alias certyfikatu został zmieniony"},
		{"keystore.JKS", "Magazyn kluczy TLS"},
		{"keystore.PKCS11", "HSM/PKCS#11"},
		{"keystore.PKCS12", "Magazyn kluczy szyfrowania/podpisów"},
		{"keystore.read.failure", "System nie może odczytać zapisanych certyfikatów/kluczy. Komunikat o błędzie: \"{0}\". Upewnij się, że ustawiłeś prawidłowe hasło do magazynu kluczy."},
		{"keystore.reloaded", "({0}) Plik certyfikatu został ponownie załadowany, wszystkie klucze i certyfikaty zostały zaktualizowane."},
	};
}
