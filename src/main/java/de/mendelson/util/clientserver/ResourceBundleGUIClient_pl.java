//$Header: /as2/de/mendelson/util/clientserver/ResourceBundleGUIClient_pl.java 1     24/09/25 10:43 Heller $
package de.mendelson.util.clientserver;

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
public class ResourceBundleGUIClient_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"client.received.unprocessed.message", "Serwer wysłał wiadomość, która nie została przetworzona przez klienta: {0}"},
		{"connection.closed", "Lokalne połączenie klient-serwer zostało rozłączone z serwerem"},
		{"connection.closed.message", "Lokalne połączenie klient-serwer zostało rozłączone z serwerem"},
		{"connection.closed.title", "Odłączenie lokalne"},
		{"connection.success", "Klient połączony z {0}"},
		{"connectionrefused.message", "{0}: Brak możliwości połączenia. Upewnij się, że serwer jest uruchomiony."},
		{"connectionrefused.title", "Problem z połączeniem"},
		{"error.client", "Wystąpił problem z połączeniem klient-serwer między tym klientem a serwerem: {0}"},
		{"login.failed.client.incompatible.message", "Serwer zgłasza, że ten klient nie ma prawidłowej wersji.\nUżyj klienta, który pasuje do serwera."},
		{"login.failed.client.incompatible.title", "Logowanie zostało odrzucone"},
		{"login.failure", "Logowanie jako użytkownik \"{0}\" nie powiodło się"},
		{"login.success", "Zalogowany jako użytkownik \"{0}\""},
		{"logout.from.server", "Wykonano wylogowanie z serwera"},
		{"password.required", "Błąd logowania, wymagane hasło dla użytkownika {0}."},
	};
}
