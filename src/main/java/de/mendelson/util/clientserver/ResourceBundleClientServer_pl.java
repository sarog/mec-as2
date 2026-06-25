//$Header: /as2/de/mendelson/util/clientserver/ResourceBundleClientServer_pl.java 1     24/09/25 10:43 Heller $
package de.mendelson.util.clientserver;

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
public class ResourceBundleClientServer_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"clientserver.start", "Uruchom interfejs klient-serwer ({0}), nasłuchuj na porcie {1}."},
		{"clientserver.started", "Interfejs klient-serwer ({0}) został uruchomiony."},
	};
}
