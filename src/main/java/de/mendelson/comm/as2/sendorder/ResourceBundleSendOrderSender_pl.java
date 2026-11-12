//$Header: /as2/de/mendelson/comm/as2/sendorder/ResourceBundleSendOrderSender_pl.java 1     24/09/25 10:38 Heller $
package de.mendelson.comm.as2.sendorder;

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
public class ResourceBundleSendOrderSender_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"message.packed", "Wychodząca wiadomość AS2 od \"{0}\" dla odbiorcy \"{1}\" utworzona w {3}, rozmiar nieprzetworzonych danych: {2}, identyfikator zdefiniowany przez użytkownika: \"{4}\"."},
		{"sendoder.sendfailed", "Wystąpił problem podczas przetwarzania żądania wysłania: [{0}] \"{1}\" - dane nie zostały przesłane do partnera."},
	};
}
