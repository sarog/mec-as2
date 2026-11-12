//$Header: /as2/de/mendelson/comm/as2/datasheet/gui/ResourceBundleCreateDataSheet_pl.java 1     24/09/25 10:32 Heller $
package de.mendelson.comm.as2.datasheet.gui;

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
public class ResourceBundleCreateDataSheet_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Anuluj"},
		{"button.ok", ">> Utwórz arkusz danych"},
		{"file.written", "Arkusz danych (PDF) został zapisany po \"{0}\". Prosimy o przesłanie go do nowego partnera w celu wymiany danych granicznych komunikacji."},
		{"label.comment", "Komentarz"},
		{"label.compression", "Kompresja danych"},
		{"label.encryption", "Szyfrowanie"},
		{"label.info", "<HTML><strong>Możesz użyć tego dialogu, aby utworzyć arkusz danych, który ułatwi połączenie nowego partnera</strong>.</HTML>"},
		{"label.localpartner", "Lokalny partner"},
		{"label.newpartner", "Nowy partner - jeszcze nie w systemie"},
		{"label.receipturl", "Twój adres URL dla odbioru AS2"},
		{"label.remotepartner", "Zdalny partner"},
		{"label.requestsignedeerp", "Oczekuj podpisanego EERP"},
		{"label.signature", "Podpis cyfrowy"},
		{"label.signedmdn", "Podpisano MDN"},
		{"label.syncmdn", "Synchroniczna sieć MDN"},
		{"label.usedataencryption", "Szyfrowanie danych"},
		{"label.usedatasignature", "Używanie podpisanych danych"},
		{"label.usesessionauth", "Użyj uwierzytelniania sesji"},
		{"label.usessl", "Używanie TLS"},
		{"progress", "Utwórz PDF"},
		{"title", "Arkusz danych dla nowego połączenia komunikacyjnego"},
	};
}
