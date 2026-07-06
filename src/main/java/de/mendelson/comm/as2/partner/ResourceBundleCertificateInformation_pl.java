//$Header: /as2/de/mendelson/comm/as2/partner/ResourceBundleCertificateInformation_pl.java 1     24/09/25 10:35 Heller $
package de.mendelson.comm.as2.partner;

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
public class ResourceBundleCertificateInformation_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"localstation.decrypt", "Wiadomości przychodzące do lokalnej stacji \"{0}\" są odszyfrowywane przy użyciu certyfikatu \"{1}\"."},
		{"localstation.sign", "Wiadomości wychodzące ze stacji lokalnej \"{0}\" są podpisywane cyfrowo za pomocą certyfikatu \"{1}\"."},
		{"partner.encrypt", "Wiadomości wychodzące do partnera \"{0}\" są szyfrowane przy użyciu certyfikatu \"{1}\"."},
		{"partner.sign.prio", "Podpis cyfrowy wiadomości przychodzących od partnera \"{0}\" jest weryfikowany przy użyciu certyfikatu \"{1}\"."},
	};
}
