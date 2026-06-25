//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleImportKey_pl.java 1     24/09/25 10:46 Heller $
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
public class ResourceBundleImportKey_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.browse", "Przeglądaj"},
		{"button.cancel", "Rozbiórka"},
		{"button.ok", "Ok"},
		{"enter.keypassword", "Wprowadź hasło klucza dla \"{0}\""},
		{"filechooser.key.import", "Wybierz plik klucza elektronicznego PKCS#12/JKS do importu"},
		{"key.import.error.entry.exists", "Import niemożliwy - wpis dla tego odcisku palca już istnieje z aliasem {0}."},
		{"key.import.error.message", "Wystąpił błąd podczas procesu importowania.\n{0}"},
		{"key.import.error.title", "Błąd"},
		{"key.import.success.message", "Klucz został pomyślnie zaimportowany."},
		{"key.import.success.title", "Sukces"},
		{"keystore.contains.nokeys", "Ten plik klucza nie zawiera żadnych kluczy prywatnych."},
		{"label.importkey", "Nazwa pliku"},
		{"label.importkey.hint", "Plik klucza do zaimportowania (PKCS#12, JKS)"},
		{"label.keypass", "hasło"},
		{"label.keypass.hint", "Hasło magazynu kluczy (PKCS#12, JKS)"},
		{"multiple.keys.message", "Wybierz klucz, który ma zostać zaimportowany"},
		{"multiple.keys.title", "W tym kilka kluczy"},
		{"title", "Import klucza z pliku klucza (PKCS#12, format JKS)"},
	};
}
