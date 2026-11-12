//$Header: /as2/de/mendelson/util/security/cert/ResourceBundleTableModelCertificates_pl.java 1     24/09/25 10:46 Heller $
package de.mendelson.util.security.cert;

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
public class ResourceBundleTableModelCertificates_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"header.algorithm", "Algorytm"},
		{"header.alias", "Alias"},
		{"header.expire", "Ważne do"},
		{"header.length", "Długość"},
		{"header.organization", "Organizacja"},
		{"header.trust", "Notaryzacja"},
		{"trust.root", "Certyfikat Master"},
		{"trust.selfsigned", "Podpisany samodzielnie"},
		{"trust.trusted", "Godny zaufania"},
		{"trust.untrusted", "Niegodny zaufania"},
	};
}
