//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleExportKeyPKCS12_pl.java 1     24/09/25 10:46 Heller $
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
public class ResourceBundleExportKeyPKCS12_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.browse", "Przeglądaj"},
		{"button.cancel", "Anuluj"},
		{"button.ok", "Ok"},
		{"filechooser.key.export", "Wybierz katalog eksportu na serwerze"},
		{"key.export.error.message", "Wystąpił błąd podczas eksportu.\n{0}"},
		{"key.export.error.title", "Błąd"},
		{"key.export.success.title", "Sukces"},
		{"key.exported.to.file", "Klucz \"{0}\" został wyeksportowany do pliku PKCS#12 \"{1}\"."},
		{"keystore.contains.nokeys", "Ten magazyn kluczy nie zawiera żadnych kluczy prywatnych."},
		{"label.alias", "klucz"},
		{"label.exportdir", "Katalog eksportu"},
		{"label.exportdir.help", "<HTML><strong>Katalog eksportu</strong><br><br>"
			+"Wprowadź tutaj katalog eksportu, do którego ma zostać wyeksportowany klucz prywatny.<br>"
			+"Ze względów bezpieczeństwa klucz nie jest przesyłany do klienta, więc można go zapisać tylko po stronie serwera.<br><br>"
			+"System utworzy w tym katalogu plik pamięci zawierający znacznik daty.</HTML>"},
		{"label.exportdir.hint", "Katalog, w którym ma zostać utworzony magazyn kluczy (PKCS#12)"},
		{"label.exportkey", "Nazwa pliku"},
		{"label.exportkey.hint", "Eksport pliku magazynu kluczy do utworzenia (PKCS#12)"},
		{"label.keypass", "hasło"},
		{"label.keypass.hint", "Hasło do wyeksportowanego magazynu kluczy"},
		{"title", "Eksport klucza do magazynu kluczy (format PKCS#12)"},
	};
}
