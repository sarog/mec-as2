//$Header: /as4/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyPEM_pl.java 1     9/12/25 17:13 Heller $
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
public class ResourceBundleImportKeyPEM_pl extends MecResourceBundle {

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
		{"filechooser.key.import", "Wybierz plik PEM"},
		{"key.import.error.entry.exists", "Import nie jest możliwy - wpis z tym odciskiem palca już istnieje, alias to {0}."},
		{"key.import.error.message", "Wystąpił problem podczas procesu importowania.\n{0}"},
		{"key.import.error.title", "Problem"},
		{"key.import.success.message", "Klucz został pomyślnie zaimportowany."},
		{"key.import.success.title", "Sukces"},
		{"label.importkey", "Nazwa pliku"},
		{"label.importkey.hint", "Plik PEM"},
		{"label.keypass", "Kluczowe hasło"},
		{"label.keypass.hint", "Hasło klucza w pliku PEM"},
		{"title", "Import klucza z pliku PEM"},
	};
}
