//$Header: /oftp2/de/mendelson/util/systemevents/gui/ResourceBundleDialogSystemEvent_it.java 5     13/06/25 15:30 Heller $
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
* @version $Revision: 5 $
*/
public class ResourceBundleDialogSystemEvent_it extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"category.all", "-- Tutti..."},
		{"header.category", "Categoria"},
		{"header.timestamp", "Timestamp"},
		{"header.type", "Tipo"},
		{"label.category", "Categoria"},
		{"label.close", "Chiudere"},
		{"label.date", "data"},
		{"label.enddate", "Fine"},
		{"label.freetext", "Testo di ricerca"},
		{"label.freetext.hint", "Ricerca per numero o testo dell''evento"},
		{"label.host", "Ospite"},
		{"label.id", "Numero dell''evento"},
		{"label.resetfilter", "Reset"},
		{"label.search", "Ricerca eventi"},
		{"label.startdate", "Inizio"},
		{"label.type", "Tipo"},
		{"label.user", "Proprietario"},
		{"no.data", "Non esiste alcun evento di sistema che corrisponda alla selezione di data/tipo corrente."},
		{"title", "Visualizzazione degli eventi di sistema"},
		{"user.server.process", "Processo del server"},
	};
}
