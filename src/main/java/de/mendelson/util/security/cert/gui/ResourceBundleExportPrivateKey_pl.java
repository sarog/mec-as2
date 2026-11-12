//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleExportPrivateKey_pl.java 1     24/09/25 10:46 Heller $
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
public class ResourceBundleExportPrivateKey_pl extends MecResourceBundle {

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
		{"key.exported.to.file", "Klucz \"{0}\" został wyeksportowany do pliku \"{1}\"."},
		{"keystore.contains.nokeys", "Ten magazyn kluczy nie zawiera żadnych kluczy prywatnych."},
		{"label.alias", "klucz"},
		{"label.exportdir", "Katalog eksportu"},
		{"label.exportdir.help", "<HTML><strong>Katalog eksportu</strong><br><br>"
			+"Wprowadź tutaj katalog eksportu, do którego ma zostać wyeksportowany klucz prywatny.<br>"
			+"Ze względów bezpieczeństwa klucz nie jest przesyłany do klienta, więc można go zapisać tylko po stronie serwera.<br><br>"
			+"System utworzy w tym katalogu plik pamięci zawierający znacznik daty.</HTML>"},
		{"label.exportdir.hint", "Katalog, w którym ma zostać utworzony plik magazynu kluczy/klucza"},
		{"label.exportformat", "Format eksportu"},
		{"label.exportformat.help", "<HTML><strong>Format eksportu</strong><br><br>"
			+"Klucz można wyeksportować w formacie magazynu kluczy (PKCS#12) lub pliku klucza zakodowanego w PEM.<br>"
			+"Najpopularniejszą formą jest format PKCS#12, plik klucza zakodowany w PEM jest wymagany tylko w specjalnych przypadkach użycia, takich jak konfiguracja odwrotnego proxy.<br><br>"
			+"W przypadku pliku klucza PEM klucz jest zapisywany bez hasła.</HTML>"},
		{"label.exportkey", "Nazwa pliku"},
		{"label.exportkey.hint", "Plik eksportu do utworzenia"},
		{"label.keypass", "hasło"},
		{"label.keypass.hint", "Hasło do eksportowanego magazynu kluczy/pliku klucza"},
		{"title", "Eksport klucza do magazynu kluczy/pliku klucza"},
	};
}
