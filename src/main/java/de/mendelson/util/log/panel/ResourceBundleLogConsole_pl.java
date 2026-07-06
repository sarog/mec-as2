//$Header: /as2/de/mendelson/util/log/panel/ResourceBundleLogConsole_pl.java 1     24/09/25 10:45 Heller $
package de.mendelson.util.log.panel;

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
public class ResourceBundleLogConsole_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"filechooser.logfile", "Wybierz plik, do którego ma zostać zapisany dziennik."},
		{"label.clear", "Usuń"},
		{"label.toclipboard", "Kopiowanie dziennika do schowka"},
		{"label.tofile", "Zapis dziennika do pliku"},
		{"title", "Wydatki"},
		{"write.failure", "Błąd podczas zapisu dziennika: {0}."},
		{"write.success", "Dziennik został pomyślnie zapisany w pliku \"{0}\"."},
	};
}
