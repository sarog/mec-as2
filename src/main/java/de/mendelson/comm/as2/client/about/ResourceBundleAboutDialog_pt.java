//$Header: /mec_as2/de/mendelson/comm/as2/client/about/ResourceBundleAboutDialog_pt.java 1     15/04/26 15:09 Heller $
package de.mendelson.comm.as2.client.about;

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
public class ResourceBundleAboutDialog_pt extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"builddate", "Data da versão: {0}"},
		{"button.ok", "Ok"},
		{"tab.about", "Versão"},
		{"tab.license", "Licença"},
		{"title", "Sobre"},
	};
}
