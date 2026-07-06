//$Header: /as2/de/mendelson/util/security/cert/ResourceBundleKeystoreStorage_pl.java 1     24/09/25 10:46 Heller $
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
public class ResourceBundleKeystoreStorage_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"error.delete.notloaded", "Wpis nie mógł zostać usunięty, bazowy magazyn kluczy nie został jeszcze załadowany."},
		{"error.empty", "Nie można odczytać magazynu kluczy: Dane magazynu kluczy muszą być dłuższe niż 0."},
		{"error.filexists", "Nie można odczytać magazynu kluczy: Plik magazynu kluczy \"{0}\" nie istnieje."},
		{"error.nodata", "Nie można odczytać magazynu kluczy: Brak dostępnych danych"},
		{"error.notafile", "Nie można odczytać magazynu kluczy: Plik magazynu kluczy \"{0}\" nie jest plikiem."},
		{"error.readaccess", "Nie można odczytać magazynu kluczy: Brak możliwości odczytu do \"{0}\"."},
		{"error.save", "Nie można było zapisać danych magazynu kluczy."},
		{"error.save.notloaded", "Magazyn kluczy nie może zostać zapisany, nie został jeszcze załadowany."},
		{"keystore.read.failure", "System nie mógł odczytać podstawowych certyfikatów. Komunikat o błędzie: \"{0}\". Sprawdź, czy używasz poprawnego hasła do magazynu kluczy."},
		{"moved.keystore.reason.commandline", "Import został wywołany przez parametr wiersza poleceń podczas uruchamiania serwera."},
		{"moved.keystore.reason.initial", "Import został wykonany, ponieważ obecnie nie ma wewnętrznej pamięci klucza systemowego. Jest to proces początkowy."},
		{"moved.keystore.to.db", "Zaimportuj dane magazynu kluczy z \"{0}\" do systemu - zamierzone użycie to {1}. Wszelkie istniejące klucze/certyfikaty zostały usunięte."},
		{"moved.keystore.to.db.title", "Import pliku magazynu kluczy ({0})"},
	};
}
