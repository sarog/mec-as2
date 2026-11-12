//$Header: /as2/de/mendelson/util/httpconfig/gui/ResourceBundleDisplayHTTPConfiguration_pl.java 1     24/09/25 10:45 Heller $
package de.mendelson.util.httpconfig.gui;

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
public class ResourceBundleDisplayHTTPConfiguration_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.ok", "Zamknij"},
		{"label.info.configfile", "To okno dialogowe pokazuje konfigurację HTTP/S po stronie serwera. Dostarczony serwer HTTP ma wersję <strong>jetty {0}</strong>. Szyfry i protokoły można skonfigurować w pliku \"{1}\" na serwerze. Podstawowe ustawienia należy wprowadzić w pliku \"{2}\" lub bezpośrednio w ustawieniach serwera. Aby zmiany zaczęły obowiązywać, należy ponownie uruchomić serwer."},
		{"no.embedded.httpserver", "Nie uruchomiono bazowego serwera HTTP.\nBrak dostępnych informacji."},
		{"no.ssl.enabled", "Obsługa TLS nie została włączona w bazowym serwerze HTTP.\nZmodyfikuj plik konfiguracyjny {0}\nzgodnie z dokumentacją i ponownie uruchomić serwer."},
		{"reading.configuration", "Czytaj konfigurację HTTP..."},
		{"tab.cipher", "Szyfry TLS"},
		{"tab.misc", "Ogólne"},
		{"tab.protocols", "Protokoły TLS"},
		{"title", "Konfiguracja HTTP po stronie serwera"},
	};
}
