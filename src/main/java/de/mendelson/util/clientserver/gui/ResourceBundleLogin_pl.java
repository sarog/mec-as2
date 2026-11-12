//$Header: /as2/de/mendelson/util/clientserver/gui/ResourceBundleLogin_pl.java 1     24/09/25 10:44 Heller $
package de.mendelson.util.clientserver.gui;

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
public class ResourceBundleLogin_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Anuluj"},
		{"button.ok", "Ok"},
		{"label.passwd", "hasło"},
		{"label.user", "Użytkownicy"},
		{"title.login", "Rejestracja"},
	};
}
