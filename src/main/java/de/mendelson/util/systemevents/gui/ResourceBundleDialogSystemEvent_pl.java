//$Header: /as2/de/mendelson/util/systemevents/gui/ResourceBundleDialogSystemEvent_pl.java 2     24/09/25 12:50 Heller $
package de.mendelson.util.systemevents.gui;

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
* @version $Revision: 2 $
*/
public class ResourceBundleDialogSystemEvent_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"category.all", "-- Wszystko"},
		{"header.category", "Kategoria"},
		{"header.timestamp", "Znacznik czasu"},
		{"header.type", "Typ"},
		{"label.category", "Kategoria"},
		{"label.close", "Zamknij"},
		{"label.date", "data"},
		{"label.enddate", "Koniec"},
		{"label.freetext", "Wyszukaj tekst"},
		{"label.freetext.hint", "Numer zdarzenia lub wyszukiwanie tekstowe"},
		{"label.host", "Gospodarz"},
		{"label.id", "Numer zdarzenia"},
		{"label.resetfilter", "Reset"},
		{"label.search", "Szuk. zdarzeń"},
		{"label.startdate", "Start"},
		{"label.type", "Typ"},
		{"label.user", "Właściciel"},
		{"no.data", "Nie ma zdarzenia systemowego pasującego do bieżącej daty/typu."},
		{"title", "Widok zdarzeń systemowych"},
		{"user.server.process", "Proces serwera"},
	};
}
