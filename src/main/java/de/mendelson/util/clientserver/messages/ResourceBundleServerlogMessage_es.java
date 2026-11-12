//$Header: /oftp2/de/mendelson/util/clientserver/messages/ResourceBundleServerlogMessage_es.java 1     25/09/25 10:47 Heller $
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
public class ResourceBundleServerlogMessage_es extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"client.server.transmission.size.cut", "El resto ha sido acortado por el sistema. El texto completo se encuentra en el directorio log de los registros del servidor."},
	};
}
