//$Header: /as4/de/mendelson/util/security/cert/gui/ResourceBundleCertificates_pl.java 6     5/02/26 15:42 Heller $
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
* ResourceBundle to localize a mendelson product
* @author S.Heller
* @version $Revision: 6 $
*/
public class ResourceBundleCertificates_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Anuluj"},
		{"button.delete", "Usuń klucz/certyfikat"},
		{"button.delete.all.expired", "Usuń wszystkie wygasłe klucze/certyfikaty"},
		{"button.edit", "Zmień nazwę aliasu"},
		{"button.export", "Eksport"},
		{"button.import", "Import"},
		{"button.keycopy", "Kopiuj do {0} zarządzania"},
		{"button.keycopy.signencrypt", "Szyfrowanie/podpis"},
		{"button.keycopy.tls", "TLS"},
		{"button.newkey", "Klucz importu"},
		{"button.ok", "Ok"},
		{"button.reference", "Pokaż użycie"},
		{"cert.delete.impossible", "Wpis nie może zostać usunięty, jest w użyciu.\nAby uzyskać więcej informacji, użyj opcji \"Pokaż użycie\"."},
		{"certificate.ca.import.success.message", "Certyfikat CA został pomyślnie zaimportowany z aliasem \"{0}\"."},
		{"certificate.import.alias", "Alias dla tego certyfikatu:"},
		{"certificate.import.error.message", "Podczas importowania wystąpił błąd:\n{0}"},
		{"certificate.import.error.title", "Błąd"},
		{"certificate.import.success.message", "Certyfikat został pomyślnie zaimportowany z aliasem \"{0}\"."},
		{"certificate.import.success.title", "Sukces"},
		{"dialog.cert.delete.message", "Czy naprawdę chcesz usunąć certyfikat z aliasem \"{0}\"?"},
		{"dialog.cert.delete.title", "Usuń certyfikat"},
		{"filechooser.certificate.import", "Wybierz plik certyfikatu do zaimportowania"},
		{"generatekey.error.message", "{0}"},
		{"generatekey.error.title", "Błąd podczas generowania klucza"},
		{"keycopy.success.text", "Wpis [{0}] został skopiowany pomyślnie"},
		{"keycopy.target.exists.text", "Ten wpis już istnieje w docelowej administracji certyfikatów (alias {0})."},
		{"keycopy.target.exists.title", "Wpis już istnieje w miejscu docelowym"},
		{"keycopy.target.ro.text", "Operacja nie powiodła się - plik klucza celu jest zabezpieczony przed zapisem."},
		{"keycopy.target.ro.title", "Cel jest tylko do odczytu"},
		{"keystore.readonly.message", "Zabezpieczony przed zapisem. Modyfikacja nie jest możliwa."},
		{"keystore.readonly.title", "Magazyn kluczy zabezpieczony przed zapisem - edycja niemożliwa"},
		{"label.cert.export", "Certyfikat eksportowy (dla partnera)"},
		{"label.cert.import", "Certyfikat importu (od partnera)"},
		{"label.cert.invalid", "Ten certyfikat jest nieważny {0}"},
		{"label.cert.valid", "Ten certyfikat jest ważny"},
		{"label.key.export.pkcs12", "Klucz eksportu (PKCS#12, PEM) (tylko do celów tworzenia kopii zapasowych!)"},
		{"label.key.import", "Import własnego klucza prywatnego (z Keystore PKCS#12, JKS)"},
                {"label.key.import.pem", "Import własnego klucza prywatnego (PEM)" }, 
		{"label.key.invalid", "Ten klucz jest nieprawidłowy {0}"},
		{"label.key.valid", "Ten klucz jest ważny"},
		{"label.keystore", "Miejsce przechowywania"},
		{"label.keystore.export", "Wyeksportuj wszystkie wpisy jako plik magazynu kluczy (tylko do celów tworzenia kopii zapasowych!)."},
		{"label.selectcsrfile", "Wybierz plik do zapisania żądania uwierzytelnienia"},
		{"label.trustanchor", "Kotwica zaufania"},
		{"menu.export", "Eksport"},
		{"menu.file", "Plik"},
		{"menu.file.close", "Wyjście"},
		{"menu.import", "Import"},
		{"menu.tools", "Rozszerzony"},
		{"menu.tools.generatecsr", "Uwierzytelnianie certyfikatu: Wygeneruj żądanie uwierzytelnienia (do CA)"},
		{"menu.tools.generatecsr.renew", "Odnowienie certyfikatu: Wygeneruj żądanie uwierzytelnienia (do CA)"},
		{"menu.tools.generatekey", "Wygeneruj nowy klucz (samopodpisany)"},
		{"menu.tools.importcsr", "Uwierzytelnianie certyfikatu: Importuj odpowiedź urzędu certyfikacji na żądanie uwierzytelnienia"},
		{"menu.tools.importcsr.renew", "Odnów certyfikat: Importuj odpowiedź urzędu certyfikacji na żądanie uwierzytelnienia"},
		{"menu.tools.verifyall", "Sprawdzanie list odwołania wszystkich certyfikatów (CRL)"},
                {"menu.tools.crmf", "Generowanie żądania CRMF (BDEW)"},
		{"modifications.notalllowed.message", "Modyfikacje nie są możliwe"},
		{"module.locked", "To zarządzanie certyfikatami jest obecnie otwarte wyłącznie przez innego klienta, nie można wprowadzać żadnych zmian!"},
		{"module.locked.text", "Moduł {0} jest używany wyłącznie przez innego klienta ({1})."},
		{"module.locked.title", "Moduł jest używany"},
		{"success.deleteallexpired.text", "{0} wygasłe i nieużywane klucze/certyfikaty zostały usunięte"},
		{"success.deleteallexpired.title", "Usuwanie wygasłych i nieużywanych certyfikatów/kluczy"},
		{"tab.info.basic", "szczegóły"},
		{"tab.info.extension", "Rozszerzenia"},
		{"tab.info.trustchain", "Ścieżka certyfikacji"},
		{"title.cert.in.use", "Certyfikat jest używany"},
		{"title.signencrypt", "Klucze i certyfikaty (szyfrowanie, podpisy)"},
		{"title.tls", "Klucze i certyfikaty (TLS)"},
		{"warning.deleteallexpired.expired.but.used.text", "{0} Wygasłe klucze/certyfikaty są używane w konfiguracji i dlatego nie są usuwane."},
		{"warning.deleteallexpired.expired.but.used.title", "Używane klucze/certyfikaty"},
		{"warning.deleteallexpired.noneavailable.text", "Nie ma wygasłych, nieużywanych wpisów"},
		{"warning.deleteallexpired.noneavailable.title", "Brak dostępnych"},
		{"warning.deleteallexpired.text", "Czy naprawdę chcesz usunąć {0} wygasłych i nieużywanych wpisów?"},
		{"warning.deleteallexpired.title", "Usuwanie wygasłych i nieużywanych kluczy/certyfikatów"},
                {"certificates.save", "Zapisz certyfikaty" },
	};
}
