//$Header: /as2/de/mendelson/util/security/crmf/ResourceBundleCRMF_pl.java 1     12/01/26 8:33 Heller $
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
* ResourceBundle to localize a mendelson product
* @author S.Heller
* @version $Revision: 1 $
*/
public class ResourceBundleCRMF_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Anuluj"},
		{"button.ok", "OK"},
		{"label.initial", "Inicjalizacja (żądanie początkowe)"},
		{"label.initial.help", "<HTML><strong>Inicjalizacja (początkowe żądanie)</strong><br><br>"
			+"Wybierz tę opcję dla początkowej rejestracji w urzędzie certyfikacji. Uwierzytelnianie odbywa się za pomocą hasła początkowej rejestracji.</HTML>"},
		{"label.key.encryption", "Klucz szyfrowania"},
		{"label.key.signature", "Klucz podpisu"},
		{"label.key.tls", "Klucz TLS"},
		{"label.root.ca", "Certyfikat główny sub-CA"},
		{"label.root.ca.help", "<HTML><strong>Certyfikat główny Sub-CA</strong><br><br>"
			+"Wygenerowane żądanie CRMF jest zamknięte w strukturze komunikatu CMP, która zawiera pole odbiorcy (Recipient).<br>"
			+"Aby zapewnić transmisję zgodną ze standardem, to pole odbiorcy musi zawierać określoną nazwę wyróżniającą (DN) urzędu certyfikacji z przestrzeni nazw X.500. Można ją znaleźć w polu \"Subject\" własnego certyfikatu urzędu certyfikacji (np. CN=SM-Test-PKI-DE). Zapewnia to, że wygenerowana struktura wiadomości jest zgodna ze standardami BDEW/Smart Metering PKI. Certyfikat główny (podrzędnego) urzędu certyfikacji jest wymagany do dokładnego dopasowania tej wartości.</HTML>"},
		{"label.update", "Aktualizacja (żądanie aktualizacji)"},
		{"label.update.help", "<HTML><strong>Update (Żądanie aktualizacji)</strong><br><br>"
			+"Wybierz tę opcję, aby odnowić istniejący certyfikat przed jego wygaśnięciem. Uwierzytelnianie odbywa się automatycznie za pomocą aktualnie ważnego certyfikatu.</HTML>"},
		{"password.hint", "Początkowe hasło jednorazowe"},
		{"success.body", "Plik CRMF został zapisany pod numerem {0}"},
		{"success.title", "Utworzenie CRMF zakończyło się sukcesem"},
		{"title", "Tworzenie zapytań CRMF (BDEW)"},
	};
}
