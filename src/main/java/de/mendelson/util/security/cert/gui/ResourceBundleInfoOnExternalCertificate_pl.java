//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleInfoOnExternalCertificate_pl.java 1     24/09/25 10:46 Heller $
package de.mendelson.util.security.cert.gui;

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
public class ResourceBundleInfoOnExternalCertificate_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Zamknij"},
		{"button.ok", "Import >>"},
		{"certificate.doesnot.exist", "Ten certyfikat nie istnieje jeszcze w zarządzaniu certyfikatami"},
		{"certificate.exists", "Ten certyfikat już istnieje w zarządzaniu certyfikatami, alias to \"{0}\"."},
		{"certinfo.certfile", "Plik certyfikatu: {0}"},
		{"certinfo.index", "Certyfikat {0} od {1}"},
		{"no.certificate", "Certyfikat nie został uznany"},
		{"title.multiple", "Informacje o certyfikatach zewnętrznych"},
		{"title.single", "Informacje o certyfikacie zewnętrznym"},
	};
}
