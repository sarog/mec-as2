//$Header: /as2/de/mendelson/comm/as2/message/store/ResourceBundleMessageStoreHandler_pl.java 1     24/09/25 10:35 Heller $
package de.mendelson.comm.as2.message.store;

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
public class ResourceBundleMessageStoreHandler_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"comm.success", "Komunikacja AS2 powiodła się, dane użytkownika {0} zostały przeniesione do \"{1}\". ({2})"},
		{"dir.createerror", "Nie można utworzyć katalogu \"{0}\"."},
		{"message.error.raw.stored", "Dane transmisji zostały zapisane pod \"{0}\"."},
		{"message.error.stored", "Osadzona wiadomość została zapisana pod \"{0}\"."},
		{"outboundstatus.written", "Plik stanu dla transakcji wychodzącej został zapisany w \"{0}\"."},
	};
}
