//$Header: /oftp2/de/mendelson/util/clientserver/messages/ResourceBundleServerlogMessage_pt.java 1     25/09/25 10:47 Heller $
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
public class ResourceBundleServerlogMessage_pt extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"client.server.transmission.size.cut", "O resto foi encurtado pelo sistema. O texto completo pode ser encontrado no diretório de logs nos registos do servidor."},
	};
}
