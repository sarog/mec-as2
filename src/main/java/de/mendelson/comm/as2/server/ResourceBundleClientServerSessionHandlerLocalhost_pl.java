//$Header: /as2/de/mendelson/comm/as2/server/ResourceBundleClientServerSessionHandlerLocalhost_pl.java 1     24/09/25 10:39 Heller $
package de.mendelson.comm.as2.server;

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
public class ResourceBundleClientServerSessionHandlerLocalhost_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"allowallclients.false", "**Serwer AS2 akceptuje tylko żądania klientów lokalnych**."},
		{"allowallclients.true", "**Serwer AS22 akceptuje żądania klientów AS2 z innych hostów**."},
		{"only.localhost.clients", "Zdalny serwer może akceptować połączenia tylko z localhost. Aby zmienić to zachowanie, należy uruchomić go z opcją \"-allowallclients\"."},
	};
}
