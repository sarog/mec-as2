//$Header: /as2/de/mendelson/util/clientserver/log/search/gui/ResourceBundleDialogSearchLogfile_pl.java 1     24/09/25 10:44 Heller $
package de.mendelson.util.clientserver.log.search.gui;

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
public class ResourceBundleDialogSearchLogfile_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.close", "Zamknij"},
		{"label.enddate", "Koniec"},
		{"label.info", "<html>Zdefiniuj przedział czasowy, wprowadź pełny numer komunikatu AS2 lub pełny numer MDN, aby znaleźć wszystkie wpisy dziennika dla niego na serwerze - następnie naciśnij przycisk \"Wyszukaj dziennik\". Możesz zdefiniować numer zdefiniowany przez użytkownika dla każdej transakcji podczas wysyłania danych do uruchomionego serwera za pośrednictwem wiersza poleceń.</html"},
		{"label.mdnid", "Numer MDN"},
		{"label.messageid", "Numer wiadomości"},
		{"label.search", "<html><div style=\"text-align:center\">Log<br>wyszukaj</div></html>"},
		{"label.startdate", "Początek"},
		{"label.uid", "Identyfikacja zdefiniowana przez użytkownika"},
		{"no.data.mdnid", "**Brak danych dziennika dla numeru MDN \"{0}\" w wybranym okresie. Jako ciągu wyszukiwania należy użyć pełnego numeru MDN, który można znaleźć w dzienniku transmisji."},
		{"no.data.messageid", "**Brak danych dziennika dla komunikatu AS2 o numerze \"{0}\" w wybranym okresie. Użyj pełnego numeru komunikatu jako ciągu wyszukiwania."},
		{"no.data.uid", "**Brak danych dziennika dla numeru zdefiniowanego przez użytkownika \"{0}\" w wybranym okresie. Wybierz pełny numer zdefiniowany przez użytkownika, który został nadany transmisji jako szukany ciąg."},
		{"problem.serverside", "Wystąpił problem po stronie serwera podczas przeszukiwania plików dziennika: [{0}] {1}"},
		{"textfield.preset", "Numer komunikatu AS2, numer MDN lub identyfikacja zdefiniowana przez użytkownika"},
		{"title", "Przeszukiwanie wpisów dziennika serwera"},
	};
}
