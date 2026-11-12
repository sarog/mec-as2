//$Header: /as2/de/mendelson/comm/as2/client/manualsend/ResourceBundleManualSend_pl.java 1     24/09/25 10:32 Heller $
package de.mendelson.comm.as2.client.manualsend;

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
public class ResourceBundleManualSend_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.browse", "Przeglądaj"},
		{"button.cancel", "Anuluj"},
		{"button.ok", "Ok"},
		{"label.filename", "Wyślij plik"},
		{"label.filename.hint", "Plik do wysłania do partnera"},
		{"label.localstation", "Nadajnik"},
		{"label.partner", "Odbiornik"},
		{"label.selectfile", "Wybierz plik do wysłania"},
		{"label.testdata", "Wysyłanie danych testowych"},
		{"send.failed", "Z powodu błędu plik nie mógł zostać przesłany do procesu wysyłki."},
		{"send.success", "Plik został pomyślnie przesłany do procesu wysyłki."},
		{"title", "Ręczne wysyłanie plików"},
	};
}
