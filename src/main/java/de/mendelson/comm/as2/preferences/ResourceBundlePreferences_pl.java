//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferences_pl.java 1     24/09/25 10:37 Heller $
package de.mendelson.comm.as2.preferences;

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
public class ResourceBundlePreferences_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.browse", "Przeglądaj"},
		{"button.cancel", "Anuluj"},
		{"button.mailserverdetection", "Znajdź serwer pocztowy"},
		{"button.modify", "Edytuj"},
		{"button.ok", "Ok"},
		{"button.testmail", "Wyślij wiadomość testową"},
		{"checkbox.notifycem", "Zdarzenia wymiany certyfikatów (CEM)"},
		{"checkbox.notifycertexpire", "Przed wygaśnięciem certyfikatów"},
		{"checkbox.notifyclientserver", "Problemy z połączeniem klient-serwer"},
		{"checkbox.notifyconnectionproblem", "Problemy z połączeniem"},
		{"checkbox.notifyfailure", "Po problemach z systemem"},
		{"checkbox.notifypostprocessing", "Problemy z przetwarzaniem końcowym"},
		{"checkbox.notifyresend", "Po odrzuconych ponownych wysłaniach"},
		{"checkbox.notifytransactionerror", "Po błędach w transakcjach"},
		{"dirmsg", "Katalog aktualności"},
		{"embedded.httpconfig.not.available", "Serwer HTTP niedostępny lub problemy z dostępem do pliku konfiguracyjnego"},
		{"event.notificationdata.modified.body", "Dane powiadomienia zostały utworzone przez\n\n{0}\n\ndo\n\n{1}\n\n zmieniono."},
		{"event.notificationdata.modified.subject", "Ustawienia powiadomień zostały zmienione"},
		{"event.preferences.modified.body", "Stara wartość: {0}\nNowa wartość: {1}"},
		{"event.preferences.modified.subject", "Wartość {0} ustawień serwera została zmodyfikowana"},
		{"filechooser.keystore", "Wybierz plik magazynu kluczy (format JKS)."},
		{"filechooser.selectdir", "Wybierz katalog, który ma zostać ustawiony"},
		{"header.dirname", "Typ"},
		{"header.dirvalue", "Katalog"},
		{"info.restart.client", "Aby zmiany zaczęły obowiązywać, należy ponownie uruchomić klienta!"},
		{"label.autodelete", "Automatyczne usuwanie"},
		{"label.colorblindness", "Wsparcie dla ślepoty barw"},
		{"label.country", "Kraj/region"},
		{"label.country.help", "<HTML><strong>Kraj/region</strong><br><br>"
			+"To ustawienie zasadniczo kontroluje tylko format daty używany do wyświetlania danych transakcji itp. w kliencie.</HTML>"},
		{"label.darkmode", "Tryb ciemny"},
		{"label.days", "Dni"},
		{"label.deletelogdirolderthan", "Dane dziennika starsze niż"},
		{"label.deletemsglog", "Automatyczne usuwanie plików i wpisów dziennika"},
		{"label.deletemsglog.help", "<HTML><strong>Automatyczne usuwanie plików i wpisów dziennika</strong><br><br>"
			+"W ustawieniach dostępna jest opcja usuwania starych plików (konserwacja systemu).<br>"
			+"Po skonfigurowaniu i włączeniu tej opcji każde usunięcie starego pliku będzie rejestrowane.<br>"
			+"Generowane jest również zdarzenie systemowe, które może informować o tym procesie za pośrednictwem funkcji powiadomień.</HTML>"},
		{"label.deletemsgolderthan", "Wpisy transakcji, które są starsze niż"},
		{"label.deletestatsolderthan", "Z danych statystycznych, które są starsze niż"},
		{"label.displaymode", "Reprezentacja"},
		{"label.displaymode.help", "<HTML><strong>Wyświetlacz</strong><br><br>"
			+"Tutaj można ustawić jeden z obsługiwanych trybów wyświetlania klienta.<br>"
			+"Można to również ustawić za pomocą parametrów wiersza poleceń podczas wywoływania.</HTML>"},
		{"label.hicontrastmode", "Tryb wysokiego kontrastu"},
		{"label.httpport", "Port wejściowy HTTP"},
		{"label.httpport.help", "<HTML><strong>Port wejściowy HTTP</strong><br><br>"
			+"Jest to port dla przychodzących nieszyfrowanych połączeń. To ustawienie jest przekazywane do wbudowanego serwera HTTP, po zmianie należy ponownie uruchomić serwer AS2.<br>"
			+"Port jest częścią adresu URL, na który partner musi wysyłać wiadomości AS2. Jest to http://Host:<strong>Port</strong>/as2/HttpReceiver.<br><br>"
			+"Wstępnie ustawiona wartość to 8080.</HTML>"},
		{"label.httpsend.timeout", "Limit czasu wysyłania HTTP/S"},
		{"label.httpsend.timeout.help", "<HTML><strong>HTTP/S send timeout</strong><br><br>"
			+"Jest to wartość limitu czasu połączenia sieciowego dla połączeń wychodzących.<br>"
			+"Jeśli po tym czasie nie zostanie nawiązane połączenie z systemem partnerskim, próba połączenia zostanie anulowana i, jeśli to konieczne, kolejne próby połączenia zostaną podjęte później zgodnie z ustawieniami ponawiania.<br><br>"
			+"Wstępnie ustawiona wartość to 5000 ms.</HTML>"},
		{"label.httpsport", "Port wejściowy HTTPS"},
		{"label.httpsport.help", "<HTML><strong>Port wejściowy HTTPS</strong><br><br>"
			+"Jest to port dla przychodzących połączeń szyfrowanych (TLS). To ustawienie jest przekazywane do wbudowanego serwera HTTP, po zmianie należy ponownie uruchomić serwer AS2.<br>"
			+"Port jest częścią adresu URL, na który partner musi wysyłać wiadomości AS2. Jest to https://Host:<strong>Port</strong>/as2/HttpReceiver<br><br>"
			+"Wstępnie ustawiona wartość to 8443.</HTML>"},
		{"label.keystore.encryptionsign", "Keystore( szyfrowanie, podpis):"},
		{"label.keystore.https", "Magazyn kluczy (do wysyłania przez Https):"},
		{"label.keystore.https.pass", "Hasło magazynu kluczy (do wysyłania przez Https):"},
		{"label.keystore.pass", "Hasło magazynu kluczy (szyfrowanie/podpis cyfrowy):"},
		{"label.language", "Język"},
		{"label.language.help", "<HTML><strong>Język</strong><br><br>"
			+"Jest to język wyświetlania klienta. Jeśli klient i serwer są uruchomione w różnych procesach (co jest zalecane), język serwera może być inny.<br>"
			+"Językiem używanym w protokole jest zawsze język serwera.</HTML>"},
		{"label.litemode", "Tryb oświetlenia"},
		{"label.loghttprequests", "Rejestrowanie żądań HTTP ze zintegrowanego serwera HTTP"},
		{"label.loghttprequests.help", "<HTML><strong>Protokół żądania HTTP</strong><br><br>"
			+"Jeśli jest włączony, wbudowany serwer HTTP (Jetty) zapisuje dziennik żądań w plikach <strong>log/yyyy_MM_dd.jetty.request.log</strong>. Te pliki dziennika nie są usuwane przez konserwację systemu - należy je usunąć ręcznie.<br><br>"
			+"Aby zmiany tego ustawienia zaczęły obowiązywać, należy ponownie uruchomić oprogramowanie.</HTML>"},
		{"label.logmessageprocessing", "Rozszerzone rejestrowanie przetwarzania wiadomości"},
		{"label.logmessageprocessing.help", "<HTML><strong>Zaawansowane rejestrowanie przetwarzania wiadomości</strong><br><br>"
			+"W przypadku aktywacji, rozszerzone wyjścia do przetwarzania komunikatów są wysyłane do dziennika.</HTML>"},
		{"label.logpollprocess", "Informacje na temat procesu odpytywania katalogu"},
		{"label.logpollprocess.help", "<HTML><strong>Informacje o procesie odpytywania katalogów</strong><br><br>"
			+"Po włączeniu tej opcji każda operacja sondowania katalogu wyjściowego jest odnotowywana w dzienniku.<br>"
			+"Ponieważ może to być bardzo duża liczba wpisów, pod żadnym pozorem nie należy używać tej opcji w trybie produktywnym, a jedynie w celach testowych.</HTML>"},
		{"label.mailaccount", "Konto serwera pocztowego"},
		{"label.mailhost", "Serwer pocztowy (SMTP)"},
		{"label.mailhost.hint", "Adres IP lub domena serwera"},
		{"label.mailpass", "Hasło serwera pocztowego"},
		{"label.mailport", "Port"},
		{"label.mailport.help", "<HTML><strong>Port SMTP</strong><br><br>"
			+"Z reguły jest to jedna z tych wartości:<br>"
			+"<strong>25</strong> (port standardowy)<br>"
			+"<strong>465</strong> (port TLS, wartość przestarzała)<br>"
			+"<strong>587</strong> (port TLS, wartość domyślna)<br>"
			+"<strong>2525</strong> (port TLS, wartość alternatywna, brak standardu)</HTML>"},
		{"label.mailport.hint", "Port SMTP"},
		{"label.max.inboundconnections", "Maksymalna liczba przychodzących połączeń równoległych"},
		{"label.max.inboundconnections.help", "<HTML><strong>Maksymalna liczba równoległych połączeń przychodzących</strong><br><br>"
			+"Jest to maksymalna liczba równoległych połączeń przychodzących, które mogą być otwarte z zewnątrz do instalacji mendelson AS2. Wartość ta dotyczy całego oprogramowania i nie jest ograniczona do poszczególnych partnerów.<br>"
			+"Ustawienie jest przekazywane do wbudowanego serwera HTTP, po zmianie należy ponownie uruchomić serwer AS2.<br><br>"
			+"Chociaż możliwe jest ograniczenie liczby równoległych połączeń przychodzących, lepiej jest wprowadzić to ustawienie w zaporze sieciowej lub w serwerze proxy - dotyczy to wtedy całego systemu, a nie tylko pojedynczego oprogramowania.<br><br>"
			+"Wstępnie ustawiona wartość to 1000.</HTML>"},
		{"label.max.outboundconnections", "Maksymalna liczba równoległych połączeń wychodzących"},
		{"label.max.outboundconnections.help", "<HTML><strong>Maksymalna liczba równoległych połączeń wychodzących</strong><br><br>"
			+"Jest to maksymalna liczba równoległych połączeń wychodzących otwieranych przez system.<br>"
			+"Wartość ta służy głównie do ochrony systemu partnerskiego przed przeciążeniem przez połączenia przychodzące z Twojej strony.<br><br>"
			+"Wstępnie ustawiona wartość to 9999.</HTML>"},
		{"label.maxmailspermin", "Maksymalna liczba powiadomień/min"},
		{"label.maxmailspermin.help", "<HTML><strong>Maksymalna liczba powiadomień/min</strong><br><br>"
			+"Aby uniknąć zbyt wielu wiadomości e-mail, można podsumować powiadomienia, ustawiając maksymalną liczbę powiadomień na minutę.<br>"
			+"Funkcja ta umożliwia odbieranie wiadomości e-mail zawierających kilka powiadomień.</HTML>"},
		{"label.mdn.timeout", "Maksymalny czas oczekiwania na MDN"},
		{"label.mdn.timeout.help", "<HTML><strong>Maksymalny czas oczekiwania na MDN</strong><br><br>"
			+"Czas oczekiwania przez system na powiadomienie MDN (Message Delivery Notification) dla wysłanej wiadomości AS2 przed ustawieniem powiązanej transakcji na status \"nieudana\".<br>"
			+"Ta wartość jest ważna w całym systemie dla wszystkich partnerów.<br><br>"
			+"Domyślna wartość to 30 minut, czas jest liczony od momentu pomyślnego nawiązania połączenia z partnerem.<br><br>"
			+"W przypadku synchronicznego MDN połączenie z partnerem pozostaje otwarte do momentu otrzymania MDN na kanale zwrotnym lub upływu czasu oczekiwania. Po upływie tego czasu połączenie jest przerywane, transakcja jest ustawiana na status \"nieudana\" i wykonywane jest przetwarzanie końcowe. Transakcja ta nie jest powtarzana.<br><br>"
			+"W przypadku asynchronicznego MDN, system czeka na przychodzące połączenie partnera z MDN, aż upłynie ten czas oczekiwania. Jeśli po upływie czasu oczekiwania nie zostanie odebrany żaden MDN, powiązana transakcja zostanie ustawiona jako \"nieudana\" i zostanie przeprowadzone określone przetwarzanie końcowe. W tym przypadku transakcja również nie jest powtarzana.</HTML>"},
		{"label.min", "min"},
		{"label.notificationmail", "Odbiorca powiadomienia Adres pocztowy"},
		{"label.notificationmail.help", "<HTML><strong>Adres e-mail odbiorcy powiadomienia</strong><br><br>"
			+"Adres e-mail odbiorcy powiadomienia.<br>"
			+"Jeśli powiadomienie ma zostać wysłane do kilku odbiorców, wprowadź tutaj listę adresów odbiorców oddzielonych przecinkami.</HTML>"},
		{"label.proxy.pass", "hasło"},
		{"label.proxy.pass.hint", "Hasło logowania do serwera proxy"},
		{"label.proxy.port.hint", "Port"},
		{"label.proxy.url", "Adres URL serwera proxy"},
		{"label.proxy.url.hint", "Adres IP lub domena serwera proxy"},
		{"label.proxy.use", "Używanie serwera proxy HTTP dla wychodzących połączeń HTTP/HTTP"},
		{"label.proxy.useauthentification", "Użyj uwierzytelniania dla serwera proxy"},
		{"label.proxy.user", "Użytkownicy"},
		{"label.proxy.user.hint", "Użytkownik logowania proxy"},
		{"label.replyto", "Adres odpowiedzi"},
		{"label.retry.max", "Maksymalna liczba prób nawiązania połączenia"},
		{"label.retry.max.help", "<HTML><strong>Maksymalna liczba prób nawiązania połączenia</strong><br><br>"
			+"Jest to liczba ponawianych prób połączenia z partnerem, jeśli nie udało się nawiązać połączenia.<br>"
			+"Czas oczekiwania między tymi ponownymi próbami można ustawić we właściwości <strong>Czas oczekiwania między ponownymi próbami połączenia</strong>.<br><br>"
			+"Wstępnie ustawiona wartość to 10.</HTML>"},
		{"label.retry.waittime", "Czas oczekiwania między ponownymi próbami połączenia"},
		{"label.retry.waittime.help", "<HTML><strong>Czas oczekiwania pomiędzy ponownymi próbami połączenia</strong><br><br>"
			+"Jest to czas w sekundach, jaki system odczeka przed ponownym połączeniem się z partnerem.<br>"
			+"Nowa próba połączenia jest podejmowana tylko wtedy, gdy nie było możliwe nawiązanie połączenia z partnerem (np. awaria systemu partnera lub problem z infrastrukturą).<br>"
			+"Liczbę ponownych prób połączenia można skonfigurować we właściwości <strong>Maximum number of connection retries</strong>.<br><br>"
			+"Wstępnie ustawiona wartość to 30 sekund.</HTML>"},
		{"label.sec", "s"},
		{"label.security", "Bezpieczeństwo połączeń"},
		{"label.smtpauthorization.credentials", "Użytkownik/hasło"},
		{"label.smtpauthorization.header", "Autoryzacja SMTP"},
		{"label.smtpauthorization.none", "Brak"},
		{"label.smtpauthorization.oauth2.authorizationcode", "OAuth2 (kod autoryzacji)"},
		{"label.smtpauthorization.oauth2.clientcredentials", "OAuth2 (poświadczenia klienta)"},
		{"label.smtpauthorization.pass", "hasło"},
		{"label.smtpauthorization.pass.hint", "Hasło serwera SMTP"},
		{"label.smtpauthorization.user", "Użytkownicy"},
		{"label.smtpauthorization.user.hint", "Nazwa użytkownika serwera SMTP"},
		{"label.stricthostcheck", "TLS: Ścisłe sprawdzanie nazwy hosta"},
		{"label.stricthostcheck.help", "<HTML><strong>TLS: Ścisłe sprawdzanie nazwy hosta</strong><br><br>"
			+"W tym miejscu można ustawić, czy nazwa pospolita (CN) zdalnego certyfikatu powinna być zgodna ze zdalnym hostem w przypadku wychodzącego połączenia TLS.<br>"
			+"Kontrola ta dotyczy wyłącznie certyfikatów poświadczonych notarialnie.</HTML>"},
		{"label.trustallservercerts", "TLS: zaufaj wszystkim certyfikatom serwerów końcowych partnerów AS2"},
		{"label.trustallservercerts.help", "<HTML><strong>TLS: Zaufaj wszystkim certyfikatom serwerów końcowych partnerów AS2</strong>.<br><br>"
			+"Zwykle TLS wymaga, aby wszystkie certyfikaty łańcucha zaufania systemu AS2 partnera były przechowywane w menedżerze certyfikatów TLS.<br><br>"
			+"Włączenie tej opcji powoduje, że podczas nawiązywania połączenia wychodzącego użytkownik ufa certyfikatowi końcowemu systemu partnerskiego, jeśli w menedżerze certyfikatów TLS przechowywane są tylko powiązane certyfikaty główne i pośrednie.<br>"
			+"Należy pamiętać, że ta opcja ma sens tylko wtedy, gdy partner korzysta z certyfikatu poświadczonego notarialnie.<br>"
			+"Certyfikaty z podpisem własnym i tak są zawsze akceptowane.<br><br>"
			+"<strong>Ostrzeżenie:</strong> Aktywacja tej opcji obniża poziom bezpieczeństwa, ponieważ możliwe są ataki typu man-in-the-middle.</HTML>"},
		{"maintenancemultiplier.day", "Dzień (dni)"},
		{"maintenancemultiplier.hour", "Godziny"},
		{"maintenancemultiplier.minute", "Minuty"},
		{"receipt.subdir", "Tworzenie podkatalogów dla każdego partnera w celu odbierania wiadomości"},
		{"receipt.subdir.help", "<HTML><strong>Podkatalogi dla odbioru</strong><br><br>"
			+"Określa, czy dane mają być odbierane w katalogu <strong>&lt;Local station&gt;/inbox</strong> lub <strong>&lt;Local station&gt;/inbox/&lt;Partner name&gt;</strong>.</HTML>"},
		{"remotedir.select", "Wybierz katalog na serwerze"},
		{"systemmaintenance.deleteoldlogdirs.help", "<HTML><strong>Usuwanie starych katalogów logów</strong><br><br>"
			+"Nawet jeśli stare transakcje zostały usunięte, procesy nadal można śledzić za pomocą istniejących plików dziennika.<br>"
			+"To ustawienie usuwa te pliki dziennika, a także wszystkie pliki zdarzeń systemowych, które mieszczą się w tym samym przedziale czasowym.</HTML>"},
		{"systemmaintenance.deleteoldstatistic.help", "<HTML><strong>Usuwanie starych danych statystycznych</strong><br><br>"
			+"System zbiera dane dotyczące kompatybilności z systemów partnerskich i może wyświetlać je jako statystyki.<br>"
			+"Określa to ramy czasowe, w których dane te są przechowywane.</HTML>"},
		{"systemmaintenance.deleteoldtransactions.help", "<HTML><strong>Usuwanie starych wpisów transakcji</strong><br><br>"
			+"Określa on ramy czasowe, w których transakcje i powiązane z nimi dane tymczasowe pozostają w systemie i są wyświetlane w przeglądzie transakcji.<br>"
			+"Ustawienia te <strong>nie</strong> wpływają na odbierane dane/pliki, które pozostają nienaruszone.<br>"
			+"W przypadku anulowanych transakcji dziennik transakcji jest nadal dostępny za pośrednictwem funkcji wyszukiwania dziennika.<br><br>"
			+"To ustawienie konserwacji czyści powiązane katalogi //temp, //sent i //_rawincoming w systemie plików serwera.</HTML>"},
		{"tab.connectivity", "Połączenia"},
		{"tab.dir", "Katalogi"},
		{"tab.interface", "Moduły"},
		{"tab.language", "Klient"},
		{"tab.log", "Protokół"},
		{"tab.maintenance", "Konserwacja systemu"},
		{"tab.misc", "Ogólne"},
		{"tab.notification", "Powiadomienie"},
		{"tab.proxy", "Pełnomocnik"},
		{"tab.security", "Bezpieczeństwo"},
		{"testmail", "Poczta testowa"},
		{"testmail.message.error", "Błąd wysyłania testowej wiadomości e-mail:\n{0}"},
		{"testmail.message.success", "Testowa wiadomość e-mail została pomyślnie wysłana do {0}."},
		{"testmail.title", "Wysyłanie testowej wiadomości e-mail"},
		{"title", "Ustawienia"},
		{"warning.changes.canceled", "Użytkownik anulował okno dialogowe ustawień - nie wprowadzono żadnych zmian w ustawieniach."},
		{"warning.clientrestart.required", "Ustawienia klienta zostały zmienione - należy ponownie uruchomić klienta, aby były prawidłowe."},
		{"warning.serverrestart.required", "Aby zmiany zaczęły obowiązywać, należy ponownie uruchomić serwer."},
	};
}
