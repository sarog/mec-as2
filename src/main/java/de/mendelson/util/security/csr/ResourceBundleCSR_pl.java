//$Header: /as2/de/mendelson/util/security/csr/ResourceBundleCSR_pl.java 1     24/09/25 10:47 Heller $
package de.mendelson.util.security.csr;

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
public class ResourceBundleCSR_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"ca.connection.problem", "HTTP {0}: Mendelson CA jest obecnie niedostępny. Spróbuj ponownie później."},
		{"cancel", "Anuluj"},
		{"csr.generation.failure.message", "{0}"},
		{"csr.generation.failure.title", "Błędy podczas tworzenia CSR"},
		{"csr.generation.success.message", "Wygenerowane żądanie uwierzytelnienia zostało zapisane w pliku\n\"{0}\".\nPrześlij te dane do swojego urzędu certyfikacji.\nZalecamy CA mendelson (http://ca.mendelson-e-c.com)."},
		{"csr.generation.success.title", "CSR został pomyślnie utworzony"},
		{"csr.message.storequestion", "Czy chcesz, aby klucz został uwierzytelniony przez mendelson CA\nlub zapisać żądanie w pliku?"},
		{"csr.message.storequestion.renew", "Czy chcesz odnowić klucz w mendelson CA\nlub zapisać żądanie w pliku?"},
		{"csr.option.1", "Poświadczenie notarialne w mendelson CA"},
		{"csr.option.1.renew", "Renew at mendelson CA"},
		{"csr.option.2", "Zapisz do pliku"},
		{"csr.title", "Uwierzytelnianie certyfikatu: Żądanie podpisania certyfikatu (CSR)"},
		{"csr.title.renew", "Odnowienie certyfikatu: żądanie podpisania certyfikatu (CSR)"},
		{"csrresponse.import.failure.message", "{0}"},
		{"csrresponse.import.failure.title", "Problem podczas łatania klucza"},
		{"csrresponse.import.success.message", "Klucz został pomyślnie załatany dzięki odpowiedzi z urzędu certyfikacji."},
		{"csrresponse.import.success.title", "Sukces"},
		{"label.selectcsrfile", "Wybierz plik do zapisania CSR"},
		{"label.selectcsrrepsonsefile", "Wybierz plik odpowiedzi urzędu certyfikacji"},
	};
}
