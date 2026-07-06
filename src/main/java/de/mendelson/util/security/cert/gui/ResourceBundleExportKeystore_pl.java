//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleExportKeystore_pl.java 1     24/09/25 10:46 Heller $
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
public class ResourceBundleExportKeystore_pl extends MecResourceBundle {

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
		{"filechooser.key.export", "Wybierz katalog eksportu po stronie serwera"},
		{"keystore.export.error.message", "Wystąpił problem podczas eksportowania:\n{0}"},
		{"keystore.export.error.title", "Błąd"},
		{"keystore.export.success.title", "Sukces"},
		{"keystore.exported.to.file", "Plik magazynu kluczy został zapisany w \"{0}\"."},
		{"label.exportdir", "Katalog eksportu"},
		{"label.exportdir.help", "<HTML><strong>Katalog eksportu</strong><br><br>"
			+"Wprowadź katalog eksportu, do którego ma zostać wyeksportowany magazyn kluczy.<br>"
			+"Ze względów bezpieczeństwa klucze nie są przekazywane do klienta,<br>"
			+"aby możliwe było tylko zapisywanie po stronie serwera.<br>"
			+"System tworzy w tym katalogu plik pamięci zawierający znacznik daty.</HTML>"},
		{"label.exportdir.hint", "Katalog, w którym tworzony jest plik magazynu kluczy"},
		{"label.keypass", "Hasło"},
		{"label.keypass.help", "<HTML><strong>Hasło wyeksportowanego magazynu kluczy</strong><br><br>"
			+"Jest to hasło, którym zabezpieczony jest magazyn kluczy wyeksportowany po stronie serwera.<br>"
			+"Wprowadź \"test\", jeśli chcesz, aby ten magazyn kluczy został później automatycznie zaimportowany do produktu mendelson.</HTML>"},
		{"label.keypass.hint", "Hasło wyeksportowanego magazynu kluczy"},
		{"title", "Eksport wszystkich wpisów do pliku magazynu kluczy"},
	};
}
