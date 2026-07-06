//$Header: /as2/de/mendelson/comm/as2/cem/gui/ResourceBundleCEMOverview_pl.java 2     21/10/25 10:50 Heller $
package de.mendelson.comm.as2.cem.gui;

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
public class ResourceBundleCEMOverview_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"activity.activated", "Brak - aktywowane na {0}."},
		{"activity.none", "Brak"},
		{"activity.waitingforanswer", "Oczekiwanie na odpowiedź"},
		{"activity.waitingfordate", "Poczekaj do daty aktywacji ({0})"},
		{"activity.waitingforprocessing", "Oczekiwanie na przetwarzanie"},
		{"button.cancel", "Anuluj"},
		{"button.exit", "Zamknij"},
		{"button.refresh", "Odświeżanie"},
		{"button.remove", "Usuń"},
		{"button.requestdetails", "Szczegóły zapytania"},
		{"button.responsedetails", "Szczegóły odpowiedzi"},
		{"button.sendcem", "Nowa giełda"},
		{"header.activity", "Aktywność systemu"},
		{"header.alias", "Certyfikat"},
		{"header.category", "Używany do"},
		{"header.initiator", "Od"},
		{"header.receiver", "Do"},
		{"header.requestdate", "Data żądania"},
		{"header.state", "Odpowiedź"},
		{"label.certificate", "Certyfikat"},
		{"tab.certificate", "Informacje o certyfikacie"},
		{"tab.reasonforrejection", "Powody odrzucenia"},
		{"title", "Zarządzanie wymianą certyfikatów"},
	};
}
