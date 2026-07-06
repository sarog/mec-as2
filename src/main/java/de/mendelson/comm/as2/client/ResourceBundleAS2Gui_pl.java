//$Header: /as2/de/mendelson/comm/as2/client/ResourceBundleAS2Gui_pl.java 2     24/09/25 11:20 Heller $
package de.mendelson.comm.as2.client;

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
* @version $Revision: 2 $
*/
public class ResourceBundleAS2Gui_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"buy.license", "Kup licencję"},
		{"configurecolumns", "Kolumny"},
		{"dbconnection.failed.message", "Nie można nawiązać połączenia z serwerem bazy danych AS2: {0}"},
		{"dbconnection.failed.title", "Brak możliwości połączenia"},
		{"delete.msg", "Usuń"},
		{"details", "Szczegóły wiadomości"},
		{"dialog.msg.delete.message", "Czy naprawdę chcesz trwale usunąć wybrane wiadomości?"},
		{"dialog.msg.delete.title", "Usuwanie wiadomości"},
		{"dialog.resend.message", "Czy naprawdę chcesz ponownie wysłać wybraną transakcję?"},
		{"dialog.resend.message.multiple", "Czy naprawdę chcesz ponownie wysłać {0} wybranych transakcji?"},
		{"dialog.resend.title", "Wyślij ponownie dane"},
		{"fatal.error", "Błąd"},
		{"filter", "Filtry"},
		{"filter.direction", "Ograniczenie kierunku"},
		{"filter.direction.inbound", "Nadchodzące"},
		{"filter.direction.outbound", "Począwszy od"},
		{"filter.from", "Od"},
		{"filter.localstation", "Ograniczenie stacji lokalnej"},
		{"filter.none", "-- Brak"},
		{"filter.partner", "Ograniczenia dla partnerów"},
		{"filter.showfinished", "Pokaz zakończony"},
		{"filter.showpending", "Pokaż oczekiwanie"},
		{"filter.showstopped", "Pokaz zatrzymany"},
		{"filter.to", "Do"},
		{"filter.use", "Ograniczenie czasowe"},
		{"keyrefresh", "Aktualizacja certyfikatów"},
		{"login.failed.client.incompatible.message", "Serwer zgłasza, że ten klient nie ma prawidłowej wersji.\nUżyj klienta, który pasuje do serwera."},
		{"login.failed.client.incompatible.title", "Logowanie zostało odrzucone"},
		{"logputput.disabled", "** Dane wyjściowe dziennika zostały pominięte"},
		{"logputput.enabled", "** Wyjście dziennika zostało aktywowane"},
		{"menu.file", "Plik"},
		{"menu.file.cem", "Zarządzanie wymianą certyfikatów (CEM)"},
		{"menu.file.cemsend", "Wymiana certyfikatów z partnerami (CEM)"},
		{"menu.file.certificate", "Certyfikaty"},
		{"menu.file.certificate.signcrypt", "Podpis/szyfr."},
		{"menu.file.certificate.ssl", "TLS"},
		{"menu.file.certificates", "Certyfikaty"},
		{"menu.file.datasheet", "Arkusz danych dla połączenia"},
		{"menu.file.exit", "Wyjście"},
		{"menu.file.ha", "Instancje wysokiej dostępności"},
		{"menu.file.migrate.hsqldb", "Migracja z HSQLDB"},
		{"menu.file.partner", "Partner"},
		{"menu.file.preferences", "Ustawienia"},
		{"menu.file.quota", "Warunki"},
		{"menu.file.resend", "Wyślij jako nową transakcję"},
		{"menu.file.resend.multiple", "Wyślij jako nowe transakcje"},
		{"menu.file.searchinserverlog", "Dziennik serwera wyszukiwania"},
		{"menu.file.send", "Wyślij plik do partnera"},
		{"menu.file.serverinfo", "Pokaż konfigurację serwera HTTP"},
		{"menu.file.statistic", "Statystyki"},
		{"menu.file.systemevents", "Zdarzenia systemowe"},
		{"menu.help", "Pomoc"},
		{"menu.help.about", "O"},
		{"menu.help.forum", "Forum"},
		{"menu.help.helpsystem", "System pomocy"},
		{"menu.help.shop", "Sklep internetowy mendelson"},
		{"menu.help.supportrequest", "Prośba o wsparcie"},
		{"msg.delete.success.multiple", "{0} Wiadomości zostały usunięte"},
		{"msg.delete.success.single", "{0} Wiadomość została usunięta"},
		{"new.version", "Dostępna jest nowa wersja. Kliknij tutaj, aby ją pobrać."},
		{"new.version.logentry.1", "Dostępna jest nowa wersja."},
		{"new.version.logentry.2", "Można je pobrać ze strony {0}."},
		{"no.helpset.for.language", "Niestety nie ma systemu pomocy dla twojego języka, używany jest angielski system pomocy."},
		{"refresh.overview", "Aktualizacja listy transakcji"},
		{"resend.failed.nopayload", "Ponowne wysłanie jako nowej transakcji nie powiodło się: Wybrana transakcja {0} nie zawiera danych użytkownika."},
		{"resend.failed.unknown.receiver", "Ponowne wysłanie nie powiodło się: Nieznany odbiorca {0} - sprawdź, czy ten partner nadal istnieje w systemie."},
		{"resend.failed.unknown.sender", "Ponowne wysłanie nie powiodło się: Nieznany nadawca {0} - sprawdź, czy ten partner nadal istnieje w systemie."},
		{"server.answer.timeout.details", "Serwer nie odpowiada w określonym czasie - czy obciążenie jest zbyt duże?"},
		{"server.answer.timeout.title", "Limit czasu w połączeniu klient-serwer"},
		{"stoprefresh.msg", "Aktualizacje wł./wył."},
		{"tab.transactions", "Transakcje"},
		{"tab.welcome", "Wiadomości i aktualizacje"},
		{"uploading.to.server", "Transfer na serwer"},
		{"warning.refreshstopped", "Aktualizacja interfejsu użytkownika jest wyłączona."},
		{"welcome", "Witamy, {0}"},
	};
}
