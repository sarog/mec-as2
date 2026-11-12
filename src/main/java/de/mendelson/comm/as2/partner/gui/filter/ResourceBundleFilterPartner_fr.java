//$Header: /as2/de/mendelson/comm/as2/partner/gui/filter/ResourceBundleFilterPartner_fr.java 1     3/12/25 11:24 Heller $
package de.mendelson.comm.as2.partner.gui.filter;

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
public class ResourceBundleFilterPartner_fr extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"category.as2id", "AS2 id"},
		{"category.comment", "Commentaire"},
		{"category.name", "Nom"},
		{"category.subject", "Sujet"},
		{"category.url", "URL d''accueil"},
	};
}
