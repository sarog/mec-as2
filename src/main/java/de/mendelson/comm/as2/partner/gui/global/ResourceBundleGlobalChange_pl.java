//$Header: /as2/de/mendelson/comm/as2/partner/gui/global/ResourceBundleGlobalChange_pl.java 1     24/09/25 10:36 Heller $
package de.mendelson.comm.as2.partner.gui.global;

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
public class ResourceBundleGlobalChange_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.ok", "Zamknij"},
		{"button.set", "Zestaw"},
		{"info.text", "<HTML>Możesz użyć tego okna dialogowego, aby ustawić parametry wszystkich partnerów na określone wartości w tym samym czasie. Po naciśnięciu przycisku \"Ustaw\" odpowiednia wartość dla <strong>WSZYSTKICH</strong> partnerów zostanie nadpisana.</HTML>"},
		{"label.dirpoll", "Przeprowadzenie ankiety katalogowej dla wszystkich partnerów"},
		{"label.maxpollfiles", "Maksymalna liczba plików od wszystkich partnerów na proces odpytywania"},
		{"label.pollinterval", "Interwał sondowania katalogu wszystkich partnerów"},
		{"partnersetting.changed", "Ustawienia zostały zmienione dla {0} partnerów."},
		{"partnersetting.notchanged", "Ustawienia nie zostały zmienione - nieprawidłowa wartość"},
		{"title", "Globalne zmiany dla wszystkich partnerów"},
	};
}
