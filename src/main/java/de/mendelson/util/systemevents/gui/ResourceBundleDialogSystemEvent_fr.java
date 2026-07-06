//$Header: /oftp2/de/mendelson/util/systemevents/gui/ResourceBundleDialogSystemEvent_fr.java 13    13/06/25 15:30 Heller $
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
* @version $Revision: 13 $
*/
public class ResourceBundleDialogSystemEvent_fr extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"category.all", "-- Tous --"},
		{"header.category", "Catégorie"},
		{"header.timestamp", "Horodatage"},
		{"header.type", "Type"},
		{"label.category", "Catégorie"},
		{"label.close", "Fermer"},
		{"label.date", "Date"},
		{"label.enddate", "Fin"},
		{"label.freetext", "Texte de recherche"},
		{"label.freetext.hint", "Numéro d''événement ou recherche de texte"},
		{"label.host", "Hôte"},
		{"label.id", "Numéro d''événement"},
		{"label.resetfilter", "Réinitialiser"},
		{"label.search", "Recherche d''événements"},
		{"label.startdate", "Lancement"},
		{"label.type", "Type"},
		{"label.user", "Propriétaire"},
		{"no.data", "Il n''y a pas d''événement système correspondant à la sélection de date/type actuelle."},
		{"title", "Vue des événements du système"},
		{"user.server.process", "Processus serveur"},
	};
}
