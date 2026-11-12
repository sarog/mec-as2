//$Header: /oftp2/de/mendelson/util/clientserver/messages/ResourceBundleServerlogMessage_fr.java 1     25/09/25 10:47 Heller $
package de.mendelson.util.clientserver.messages;

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
public class ResourceBundleServerlogMessage_fr extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"client.server.transmission.size.cut", "Le reste a été raccourci par le système. Le texte complet se trouve dans le répertoire des protocoles dans les journaux du serveur."},
	};
}
