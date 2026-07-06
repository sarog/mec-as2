//$Header: /as2/de/mendelson/comm/as2/database/ResourceBundleDBDriverManager_pl.java 1     24/09/25 10:32 Heller $
package de.mendelson.comm.as2.database;

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
public class ResourceBundleDBDriverManager_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"creating.database.1", "Tworzenie bazy danych konfiguracji"},
		{"creating.database.2", "Tworzenie bazy danych runtime"},
		{"creating.database.details", "Host: {0}, Port: {1}, Użytkownik: {2}, Nazwa DB: {3}"},
		{"database.creation.failed.1", "Wystąpił błąd podczas tworzenia bazy danych konfiguracji"},
		{"database.creation.failed.2", "Wystąpił błąd podczas tworzenia bazy danych runtime"},
		{"database.creation.success.1", "Baza danych konfiguracji została pomyślnie utworzona"},
		{"database.creation.success.2", "Baza danych runtime została pomyślnie utworzona"},
		{"module.name", "[DATABASE]"},
	};
}
