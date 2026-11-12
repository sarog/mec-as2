//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleExportCertificate_pl.java 1     24/09/25 10:46 Heller $
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
* @version $Revision: 1 $
*/
public class ResourceBundleExportCertificate_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"DER", "Format binarny (DER, *.cer)"},
		{"PEM", "Format tekstowy (PEM, *.cer)"},
		{"PEM_CHAIN", "Format tekstowy (+łańcuch uwierzytelniania) (PEM, *.pem)"},
		{"PKCS#7", "Z łańcuchem certyfikacji (PKCS#7, *.p7b)"},
		{"SSH2", "Format SSH2 (klucz publiczny, *.pub)"},
		{"button.browse", "Przeglądaj"},
		{"button.cancel", "Anuluj"},
		{"button.ok", "Ok"},
		{"certificate.export.error.message", "Eksport certyfikatu nie powiódł się:\n{0}"},
		{"certificate.export.error.title", "Błąd eksportu"},
		{"certificate.export.success.message", "Certyfikat mógł zostać pomyślnie wyeksportowany do\n\"{0}\""},
		{"certificate.export.success.title", "Sukces"},
		{"error.empty.certificate", "Brak dostępnych danych certyfikatu"},
		{"filechooser.certificate.export", "Wybierz nazwę pliku dla eksportu."},
		{"label.alias", "Alias"},
		{"label.exportfile", "Nazwa pliku"},
		{"label.exportfile.hint", "Wygenerowany plik certyfikatu"},
		{"label.exportformat", "Format"},
		{"title", "Eksport certyfikatu X.509"},
	};
}
