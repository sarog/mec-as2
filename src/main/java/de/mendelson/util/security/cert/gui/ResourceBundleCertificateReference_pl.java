//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleCertificateReference_pl.java 1     24/09/25 10:46 Heller $
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
public class ResourceBundleCertificateReference_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.ok", "Ok"},
		{"label.info.certificate", "<HTML>Certyfikat z aliasem <strong>{0}</strong> jest używany przez następujących partnerów</HTML>"},
		{"label.info.key", "<HTML>Klucz prywatny z aliasem <strong>{0}</strong> jest używany przez następujących partnerów</HTML>"},
		{"label.notinuse.certificate", "<HTML>Certyfikat z aliasem <strong>{0}</strong> nie jest używany przez żadnego partnera w konfiguracji.</HTML>"},
		{"label.notinuse.key", "<HTML>Klucz prywatny z aliasem <strong>{0}</strong> nie jest używany przez żadnego partnera w konfiguracji.</HTML>"},
		{"title", "Wykorzystanie certyfikatu"},
	};
}
