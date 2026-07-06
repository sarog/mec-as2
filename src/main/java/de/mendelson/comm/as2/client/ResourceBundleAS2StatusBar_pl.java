//$Header: /as2/de/mendelson/comm/as2/client/ResourceBundleAS2StatusBar_pl.java 1     24/09/25 10:31 Heller $
package de.mendelson.comm.as2.client;

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
public class ResourceBundleAS2StatusBar_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"configuration.issue", "Sprawdź swoją konfigurację"},
		{"count.all.available", "Całkowita liczba transakcji w systemie"},
		{"count.all.served", "Liczba dostarczonych transakcji"},
		{"count.failure", "Nieprawidłowe transakcje"},
		{"count.ok", "Bezbłędne transakcje"},
		{"count.pending", "Transakcje oczekujące"},
		{"count.selected", "Wybrane transakcje"},
		{"no.configuration.issues", "Brak problemów z konfiguracją"},
	};
}
