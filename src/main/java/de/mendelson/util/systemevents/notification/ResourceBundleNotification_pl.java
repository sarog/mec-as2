//$Header: /as2/de/mendelson/util/systemevents/notification/ResourceBundleNotification_pl.java 1     24/09/25 10:47 Heller $
package de.mendelson.util.systemevents.notification;

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
public class ResourceBundleNotification_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"authorization.credentials", "Użytkownik/hasło"},
		{"authorization.none", "BRAK"},
		{"authorization.oauth2", "OAUTH2"},
		{"authorization.oauth2.authorizationcode", "Kod autoryzacji"},
		{"authorization.oauth2.clientcredentials", "Poświadczenia klienta"},
		{"do.not.reply", "Prosimy o nieodpowiadanie na tę wiadomość."},
		{"misc.message.send", "Wiadomość e-mail z powiadomieniem została wysłana do {0} ({1}-{2}-{3})."},
		{"misc.message.send.failed", "Wysłanie wiadomości e-mail z powiadomieniem do {0} nie powiodło się"},
		{"misc.message.summary.failed", "Wysłanie wiadomości e-mail z powiadomieniem podsumowującym do {0} nie powiodło się"},
		{"misc.message.summary.send", "Wiadomość e-mail z podsumowaniem została wysłana do {0}."},
		{"module.name", "[POWIADOMIENIE E-MAIL]"},
		{"notification.about.event", "To powiadomienie odnosi się do zdarzenia systemowego {0}.\nPilność: {1}\nŹródło: {2}\nTyp: {3}\nId: {4}"},
		{"notification.summary", "Podsumowanie {0} zdarzeń systemowych"},
		{"notification.summary.info", "Otrzymujesz ten komunikat podsumowujący, ponieważ zdefiniowałeś ograniczoną liczbę powiadomień na jednostkę czasu.\npowiadomień na jednostkę czasu.\nAby uzyskać szczegółowe informacje na temat poszczególnych zdarzeń, należy uruchomić klienta\nklienta i przejść do sekcji \"Zdarzenia systemu plików\".\nNastępnie wprowadź unikalny numer zdarzenia w masce wyszukiwania.\nzdarzenia w masce wyszukiwania."},
		{"test.message.debug", "\nWysłanie wiadomości nie powiodło się.\n"},
		{"test.message.send", "Wiadomość testowa została wysłana do {0}."},
	};
}
