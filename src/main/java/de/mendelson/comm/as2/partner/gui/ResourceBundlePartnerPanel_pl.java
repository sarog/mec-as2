//$Header: /as2/de/mendelson/comm/as2/partner/gui/ResourceBundlePartnerPanel_pl.java 1     24/09/25 10:35 Heller $
package de.mendelson.comm.as2.partner.gui;

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
public class ResourceBundlePartnerPanel_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"header.httpheaderkey", "Nazwa"},
		{"header.httpheadervalue", "Wartość"},
		{"httpheader.add", "Dodaj"},
		{"httpheader.delete", "Usunąć"},
		{"label.address", "Adres"},
		{"label.algorithmidentifierprotection", "Identyfikator algorytmu Atrybut ochrony"},
		{"label.algorithmidentifierprotection.help", "<HTML><strong>Atrybut ochrony identyfikatora algorytmu</strong><br><br>"
			+"Po włączeniu tej opcji (co jest zalecane) w podpisie AS2 używany jest atrybut Algorithm Identifier Protection. Atrybut ten jest zdefiniowany w dokumencie RFC 6211.<br><br>"
			+"Zastosowany podpis AS2 jest podatny na ataki polegające na zastępowaniu algorytmów.<br>"
			+"W ataku polegającym na zastąpieniu algorytmu atakujący zmienia używany algorytm lub parametry algorytmu w celu zmiany wyniku procedury weryfikacji podpisu.<br>"
			+"Atrybut ten zawiera teraz kopię odpowiednich identyfikatorów algorytmu podpisu, dzięki czemu nie można ich zmienić, zapobiegając w ten sposób atakowi podmiany algorytmu na podpis.<br><br>"
			+"Istnieją systemy AS2, które nie mogą obsłużyć tego atrybutu (mimo że RFC pochodzi z 2011 r.) i zgłaszają błąd autoryzacji.<br>"
			+"W tym przypadku atrybut można wyłączyć.</HTML>"},
		{"label.as2version", "Wersja AS2"},
		{"label.asyncmdn", "Żądanie asynchronicznego potwierdzenia odbioru (MDN)"},
		{"label.asyncmdn.help", "<HTML><strong>Asynchroniczne potwierdzenie odbioru</strong><br><br>"
			+"Partner nawiązuje nowe połączenie z systemem użytkownika w celu wysłania potwierdzenia wiadomości wychodzącej.<br>"
			+"Podpis jest weryfikowany, a dane odszyfrowywane po stronie partnera po zamknięciu połączenia przychodzącego.<br>"
			+"Z tego powodu metoda ta wymaga mniej zasobów niż metoda z synchronicznym MDN.</HTML>"},
		{"label.compression", "Kompresja danych"},
		{"label.compression.help", "<HTML><strong>Kompresja danych</strong><br><br>"
			+"Jeśli ta opcja jest włączona, wychodzące wiadomości są kompresowane przy użyciu algorytmu ZLIB.<br>"
			+"Zaletą kompresji jest to, że rozmiar wiadomości jest zwykle zmniejszony, co prowadzi do szybszej transmisji. Zmienia się również struktura wiadomości, co może rozwiązać problemy z kompatybilnością.<br>"
			+"Wadą jest to, że jest to dodatkowy etap przetwarzania, który odbywa się kosztem przepustowości.<br><br>"
			+"Ta opcja wymaga systemu AS2 po drugiej stronie, który obsługuje co najmniej AS2 1.1.</HTML>"},
		{"label.contact", "Skontaktuj się z nami"},
		{"label.contenttype", "Dane użytkownika Typ zawartości"},
		{"label.contenttype.help", "<HTML><strong>Typ zawartości danych użytkownika</strong><br><br>"
			+"Następujące typy zawartości są bezpiecznie obsługiwane w protokole AS2:<br>"
			+"application/EDI-X12<br>"
			+"aplikacja/EDIFACT<br>"
			+"application/edi-consent<br>"
			+"zastosowanie/XML<br><br>"
			+"AS2 RFC stwierdza, że wszystkie typy zawartości MIME powinny być obsługiwane w AS2.<br>"
			+"Nie jest to jednak wymóg obowiązkowy.<br>"
			+"Dlatego nie należy na tym polegać,<br>"
			+"że system partnera lub bazowe przetwarzanie SMIME mendelson AS2 może obsługiwać typy zawartości inne niż opisane.</HTML>"},
		{"label.cryptalias.cert", "Certyfikat partnera (szyfrowanie danych)"},
		{"label.cryptalias.cert.help", "<HTML><strong>Certyfikat partnera (szyfrowanie danych)</strong<br><br>"
			+"Wybierz tutaj certyfikat, który jest dostępny w systemowym menedżerze certyfikatów (podpis/szyfrowanie).<br>"
			+"Jeśli chcesz szyfrować wiadomości wychodzące do tego partnera, ten certyfikat jest używany do szyfrowania danych.</HTML>"},
		{"label.cryptalias.key", "Klucz prywatny (odszyfrowywanie danych)"},
		{"label.cryptalias.key.help", "<HTML><strong>Klucz prywatny (deszyfrowanie danych)</strong<br><br>"
			+"Wybierz tutaj klucz prywatny, który jest dostępny w menedżerze certyfikatów systemu (podpis/szyfrowanie).<br>"
			+"Jeśli wiadomości przychodzące od dowolnego partnera są szyfrowane dla tej stacji lokalnej, klucz ten jest używany do odszyfrowania.<br><br>"
			+"Ponieważ tylko ty jesteś w posiadaniu klucza prywatnego ustawionego tutaj, tylko ty możesz odszyfrować dane, które twoi partnerzy zaszyfrowali za pomocą twojego certyfikatu.<br>"
			+"Oznacza to, że każdy partner może zaszyfrować dane dla Ciebie - ale tylko Ty możesz je odszyfrować.</HTML>"},
		{"label.email", "Adres pocztowy"},
		{"label.email.help", "<HTML><strong>Adres e-mail</strong><br><br>"
			+"Wartość ta jest częścią opisu protokołu AS2, ale obecnie nie jest w ogóle używana.</HTML>"},
		{"label.email.hint", "Nieużywany ani niezatwierdzony w protokole AS2"},
		{"label.enabledirpoll", "Monitorowanie katalogów"},
		{"label.enabledirpoll.help", "<HTML><strong>Monitorowanie katalogów</strong><br><br>"
			+"Po włączeniu tej opcji system automatycznie przeszuka katalog źródłowy w poszukiwaniu nowych plików dla tego partnera.<br>"
			+"W przypadku znalezienia nowego pliku generowany jest komunikat AS2 i wysyłany do partnera.<br>"
			+"Należy pamiętać, że ta metoda monitorowania katalogów może wykorzystywać tylko ogólne parametry dla wszystkich tworzonych wiadomości.<br>"
			+"Jeśli chcesz ustawić specjalne parametry dla każdej wiadomości indywidualnie, użyj procesu wysyłania za pomocą wiersza poleceń.<br>"
			+"W przypadku pracy w klastrze (HA) należy wyłączyć monitorowanie katalogów, ponieważ proces ten nie może być synchronizowany.</HTML>"},
		{"label.encryptiontype", "Szyfrowanie wiadomości"},
		{"label.encryptiontype.help", "<HTML><strong>Szyfrowanie wiadomości</strong><br><br>"
			+"W tym miejscu wybiera się algorytm szyfrowania, za pomocą którego mają być szyfrowane wiadomości wychodzące do tego partnera.<br>"
			+"Jeśli wybrano tutaj algorytm szyfrowania, zaszyfrowana wiadomość jest również oczekiwana od tego partnera - jednak algorytm szyfrowania jest dowolny.<br><br>"
			+"Więcej informacji na temat algorytmu szyfrowania można znaleźć w Pomocy (sekcja Partner) - wszystkie algorytmy są tam wyjaśnione.</HTML>"},
		{"label.features", "Funkcje"},
		{"label.features.cem", "Wymiana certyfikatów za pośrednictwem CEM"},
		{"label.features.compression", "Kompresja danych"},
		{"label.features.ma", "Wiele załączników"},
		{"label.httpauth.asyncmdn", "Uwierzytelnianie wychodzącej asynchronicznej sieci MDN"},
		{"label.httpauth.credentials.asyncmdn", "Podstawowe uwierzytelnianie HTTP"},
		{"label.httpauth.credentials.asyncmdn.pass", "hasło"},
		{"label.httpauth.credentials.asyncmdn.user", "Nazwa użytkownika"},
		{"label.httpauth.credentials.message", "Podstawowe uwierzytelnianie HTTP"},
		{"label.httpauth.credentials.message.pass", "hasło"},
		{"label.httpauth.credentials.message.user", "Nazwa użytkownika"},
		{"label.httpauth.message", "Uwierzytelnianie wychodzących wiadomości AS2"},
		{"label.httpauth.none", "Brak"},
		{"label.httpauth.oauth2.authorizationcode.asyncmdn", "OAuth2 (kod autoryzacji)"},
		{"label.httpauth.oauth2.authorizationcode.message", "OAuth2 (kod autoryzacji)"},
		{"label.httpauth.oauth2.clientcredentials.asyncmdn", "OAuth2 (poświadczenia klienta)"},
		{"label.httpauth.oauth2.clientcredentials.message", "OAuth2 (poświadczenia klienta)"},
		{"label.httpauthentication.credentials.help", "<HTML><strong>Uwierzytelnianie dostępu podstawowego HTTP</strong><br><br>"
			+"W tym miejscu należy skonfigurować podstawowe uwierzytelnianie dostępu HTTP, jeśli jest ono włączone po stronie partnera (zdefiniowane w RFC 7617). System zdalnego partnera powinien zwrócić status <strong>HTTP 401 Unauthorised</strong> dla nieuwierzytelnionych żądań (nieprawidłowe dane logowania itp.).<br>"
			+"Jeśli połączenie z partnerem wymaga uwierzytelnienia klienta TLS (za pomocą certyfikatów), ustawienie to nie jest wymagane.<br>"
			+"W takim przypadku należy zaimportować certyfikaty partnera za pośrednictwem menedżera certyfikatów TLS.<br>"
			+"Następnie system zajmuje się uwierzytelnianiem klienta TLS.</HTML>"},
		{"label.httpversion", "Wersja protokołu HTTP"},
		{"label.httpversion.help", "<HTML><strong>Wersja protokołu HTTP</strong><br><br>"
			+"Istnieją wersje protokołu HTTP<ul><li>HTTP/1.0 (RFC 1945)</li><li>HTTP/1.1 (RFC 2616)</li><li>HTTP/2.0 (RFC 9113)</li><li>HTTP/3.0 (RFC 9114)</li></ul>HTTP/1.1 jest zwykle używany dla AS2.<br><br>"
			+"Uwaga: To <strong>nie jest</strong> wersja TLS!</HTML>"},
		{"label.id", "Identyfikator AS2"},
		{"label.id.help", "<HTML><strong>AS2 id</strong><br><br>"
			+"Unikalny identyfikator (w sieci partnerskiej), który jest używany w protokole AS2 do identyfikacji tego partnera. Można go wybrać dowolnie - wystarczy upewnić się, że jest unikalny na całym świecie.</HTML>"},
		{"label.id.hint", "Identyfikacja partnera (protokół AS2)"},
		{"label.keep.security", "Użyj ustawień zabezpieczeń stacji lokalnej"},
		{"label.keepfilenameonreceipt", "Zachowaj oryginalną nazwę pliku"},
		{"label.keepfilenameonreceipt.help", "<HTML><strong>Zachowaj oryginalną nazwę pliku</strong><br><br>"
			+"Jeśli ta opcja jest aktywna, system próbuje wyodrębnić oryginalną nazwę pliku z przychodzących wiadomości AS2 i zapisać przesłany plik pod tą nazwą, aby mógł zostać odpowiednio przetworzony.<br>"
			+"Ta opcja działa tylko wtedy, gdy nadawca dodał oryginalne informacje o nazwie pliku. Jeśli aktywujesz tę opcję, upewnij się, że Twój partner wysyła unikalne nazwy plików.<br><br>"
			+"Jeśli wyodrębniona nazwa pliku nie jest prawidłową nazwą pliku, zostanie ona zastąpiona prawidłową nazwą pliku, zostanie uruchomione ostrzeżenie o zdarzeniu systemowym POSTPROCESSING i przetwarzanie będzie kontynuowane.</HTML>"},
		{"label.localstation", "Stacja lokalna"},
		{"label.localstation.help", "<HTML><strong>Stacja lokalna</strong><br><br>"
			+"Stacja lokalna reprezentuje własny system. W systemie można utworzyć dowolną liczbę stacji lokalnych.<br>"
			+"Stacje lokalne i partnerów połączenia konfiguruje się osobno. Ogólna konfiguracja relacji partnerskiej jest następnie tworzona automatycznie na podstawie konfiguracji stacji lokalnej i partnera zdalnego.<br><br>"
			+"Istnieją dwa rodzaje partnerów:<br><br>"
			+"<table border=\"0\"><tr><td style=\"padding-left: 10px\"><img src=\"/de/mendelson/comm/as2/partner/gui/localstation.svg\" height=\"20\" width=\"20\"></td><td>Stacje lokalne</td></tr><tr><td style=\"padding-left: 10px\"><img src=\"/de/mendelson/comm/as2/partner/gui/singlepartner.svg\" height=\"20\" width=\"20\"></td><td>Usunięty partner</td></tr></table>.</HTML>"},
		{"label.maxpollfiles", "Maksymalna liczba plików/proces pobierania"},
		{"label.mdn.description", "<HTML>Powiadomienie MDN (Message Delivery Notification) jest potwierdzeniem dla wiadomości AS2. Ta sekcja definiuje zachowanie partnera dla wychodzących wiadomości AS2.</HTML>"},
		{"label.mdnurl", "MDN URL"},
		{"label.mdnurl.help", "<HTML><strong>MDN</strong> (<strong>M</strong>wiadomość <strong>D</strong>dostarczenie <strong>N</strong>powiadomienie) <strong>URL</strong><br><br>"
			+"Jest to adres URL, którego partner będzie używał dla przychodzącego asynchronicznego MDN do tej stacji lokalnej. W przypadku synchronicznym wartość ta nie jest używana, ponieważ MDN jest następnie wysyłany na kanale zwrotnym połączenia wychodzącego.<br>"
			+"Wprowadź ten adres URL w formacie <strong>PROTOCOL://HOST:PORT/PFAD</strong>.<br>"
			+"<strong>PROTOCOL</strong> musi być jednym z \"http\" lub \"https\".<br>"
			+"<strong>HOST</strong> odnosi się do własnego hosta serwera AS2.<br>"
			+"<strong>PORT</strong> to port odbiorczy systemu AS2.<strong>PFAD</strong> oznacza ścieżkę odbiorczą, na przykład \"/as2/HttpReceiver\".<strong>Cały wpis jest oznaczony jako nieprawidłowy, jeśli protokół nie jest jednym z \"http\" lub \"https\", jeśli adres URL ma nieprawidłowy format lub jeśli port nie jest zdefiniowany w adresie URL.<br><br>"
			+"Nie wprowadzaj tutaj adresu URL, który odnosi się do twojego własnego systemu poprzez \"localhost\" lub \"127.0.0.1\" - ta informacja zostanie przeanalizowana po stronie twojego partnera po otrzymaniu wiadomości AS2, a następnie wyśle on MDN do siebie.</HTML>"},
		{"label.name", "Nazwa"},
		{"label.name.help", "<HTML><strong>Nazwa</strong><br><br>"
			+"Jest to wewnętrzna nazwa partnera używana w systemie. Nie jest to wartość specyficzna dla protokołu, ale jest używana do struktury nazw plików lub struktur katalogów, które odnoszą się do tego partnera.</HTML>"},
		{"label.name.hint", "Nazwa partnera wewnętrznego"},
		{"label.notes.help", "<HTML><strong>Uwagi</strong><br><br>"
			+"W tym miejscu znajduje się opcja sporządzania notatek na temat tego partnera na własny użytek.</HTML>"},
		{"label.notify.receive", "Powiadamia, jeśli limit odbioru przekroczy następującą wartość:"},
		{"label.notify.send", "Powiadomienie, jeśli limit transmisji przekroczy następującą wartość:"},
		{"label.notify.sendreceive", "Powiadomienie, jeśli limit wysyłania/odbierania przekroczy następującą wartość:"},
		{"label.overwrite.crypt", "Odszyfrowywanie wiadomości przychodzących"},
		{"label.overwrite.crypt.help", "<HTML><strong>Odszyfruj przychodzące wiadomości</strong><br><br>"
			+"Klucz ten jest używany do odszyfrowywania wiadomości przychodzących od tego partnera - zamiast ustawionego klucza odpowiedniej stacji lokalnej.</HTML>"},
		{"label.overwrite.security", "Nadpisanie ustawień zabezpieczeń stacji lokalnej"},
		{"label.overwrite.sign", "Podpisywanie wiadomości wychodzących"},
		{"label.overwrite.sign.help", "<HTML><strong>Podpisywanie wiadomości wychodzących</strong><br><br>"
			+"Klucz ten jest używany do podpisywania wiadomości wychodzących do tego partnera - zamiast ustawionego klucza odpowiedniej stacji lokalnej.</HTML>"},
		{"label.partnercomment", "Komentarz"},
		{"label.polldir", "Monitorowany katalog"},
		{"label.pollignore", "Zignoruj odbiór dla"},
		{"label.pollignore.help", "<HTML><strong>Ignoruj odbiór dla</strong><br><br>"
			+"Monitorowanie katalogów pobiera i przetwarza określoną liczbę plików z monitorowanego katalogu w regularnych odstępach czasu.<br>"
			+"Należy upewnić się, że plik jest w pełni dostępny w tym czasie. W przypadku regularnego kopiowania plików do monitorowanego katalogu może dojść do nakładania się czasu, co spowoduje pobranie pliku, który nie jest jeszcze w pełni dostępny.<br>"
			+"Dlatego w przypadku kopiowania plików do monitorowanego katalogu przy użyciu operacji nieatomowej, należy wybrać rozszerzenie nazwy pliku w czasie procesu kopiowania, które będzie ignorowane przez proces monitorowania.<br>"
			+"Gdy cały plik jest dostępny w monitorowanym katalogu, można usunąć rozszerzenie nazwy pliku za pomocą operacji atomowej (przeniesienie, mv, zmiana nazwy), a cały plik zostanie pobrany.<br>"
			+"Lista rozszerzeń nazw plików to oddzielona przecinkami lista rozszerzeń, na przykład \"*.tmp, *.upload\".</HTML>"},
		{"label.pollignore.hint", "Lista rozszerzeń plików, które mają być ignorowane, oddzielona przecinkami (dozwolone symbole wieloznaczne)."},
		{"label.pollinterval", "Interwał odbioru"},
		{"label.productname", "Nazwa produktu"},
		{"label.signalias.cert", "Certyfikat partnera (weryfikacja podpisu cyfrowego)"},
		{"label.signalias.cert.help", "<HTML><strong>Certyfikat partnera (weryfikacja podpisu cyfrowego)</strong<br><br>"
			+"Wybierz tutaj certyfikat, który jest dostępny w systemowym menedżerze certyfikatów (podpis/szyfrowanie).<br>"
			+"Jeśli wiadomości przychodzące od tego partnera są podpisane cyfrowo dla stacji lokalnej, ten certyfikat jest używany do weryfikacji tego podpisu.</HTML>"},
		{"label.signalias.key", "Klucz prywatny (tworzenie podpisu cyfrowego)"},
		{"label.signalias.key.help", "<HTML><strong>Klucz prywatny (tworzenie podpisu cyfrowego)</strong<br><br>"
			+"Wybierz tutaj klucz prywatny, który jest dostępny w menedżerze certyfikatów systemu (podpis/szyfrowanie).<br>"
			+"Klucz ten służy do tworzenia podpisu cyfrowego dla wiadomości wychodzących do wszystkich zdalnych partnerów.<br><br>"
			+"Ponieważ tylko ty jesteś w posiadaniu klucza prywatnego ustawionego tutaj, tylko ty możesz podpisać dane.<br>"
			+"Twoi partnerzy mogą sprawdzić ten podpis z certyfikatem - gwarantuje to, że dane są niezmienione i że jesteś nadawcą.</HTML>"},
		{"label.signedmdn", "Żądanie podpisanego potwierdzenia odbioru (MDN)"},
		{"label.signedmdn.help", "<HTML><strong>Podpisane potwierdzenie odbioru</strong><br><br>"
			+"Za pomocą tego ustawienia można poinformować system partnerski dla wychodzących wiadomości AS2, że użytkownik chce otrzymać podpisane potwierdzenie odbioru (MDN).<br>"
			+"Choć początkowo brzmi to sensownie, to niestety ustawienie jest problematyczne.<br>"
			+"Dzieje się tak, ponieważ po otrzymaniu numeru MDN partnera transakcja jest zakończona.<br>"
			+"Jeśli weryfikacja podpisu MDN zostanie następnie przeprowadzona i zakończy się niepowodzeniem, nie ma już możliwości poinformowania partnera o tym problemie.<br>"
			+"Anulowanie transakcji nie jest już możliwe - transakcja została już zakończona. Oznacza to, że weryfikacja podpisu MDN w trybie automatycznym jest bezcelowa.<br>"
			+"Protokół AS2 przewiduje, że aplikacja powinna rozwiązać ten problem logiczny, ale nie jest to możliwe.<br>"
			+"Rozwiązanie mendelson AS2 wyświetla ostrzeżenie w przypadku nieudanego sprawdzenia podpisu MDN.<br><br>"
			+"Jest jeszcze jedna szczególna cecha tego ustawienia:<br>"
			+"Jeśli wystąpił problem podczas przetwarzania po stronie partnera, MDN może być zawsze niepodpisany - niezależnie od tego ustawienia.</HTML>"},
		{"label.signtype", "Podpis cyfrowy"},
		{"label.signtype.help", "<HTML><strong>Podpis cyfrowy</strong><br><br>"
			+"W tym miejscu wybiera się algorytm podpisu, którym mają być podpisywane wiadomości wychodzące do tego partnera.<br>"
			+"Jeśli wybrano tutaj algorytm podpisu, przychodząca podpisana wiadomość jest również oczekiwana od tego partnera - jednak algorytm podpisu jest dowolny.<br><br>"
			+"Wiadomość wychodząca do tego partnera jest podpisywana przy użyciu klucza prywatnego stacji lokalnej, która jest nadawcą transakcji.</HTML>"},
		{"label.subject", "Dane użytkownika Podmiot"},
		{"label.subject.help", "<HTML><strong>Podmiot danych użytkownika</strong><br><br>"
			+"$'{'filename} jest zastępowane nazwą wysyłanego pliku.<br>"
			+"Wartość ta jest przesyłana w nagłówku HTTP, obowiązują ograniczenia!<br>"
			+"Jako kodowania znaków należy użyć ISO-8859-1, tylko znaki drukowalne, bez znaków specjalnych.<br>"
			+"CR, LF i TAB są zastępowane przez \"\r\", \"\n\" i \"\t\".</HTML>"},
		{"label.syncmdn", "Żądanie synchronicznego potwierdzenia odbioru (MDN)"},
		{"label.syncmdn.help", "<HTML><strong>Synchroniczne potwierdzenie odbioru</strong><br><br>"
			+"Partner wysyła potwierdzenie odbioru (MDN) na kanale zwrotnym połączenia wychodzącego.<br>"
			+"Połączenie wychodzące pozostaje otwarte, podczas gdy partner odszyfrowuje dane i sprawdza podpis.<br>"
			+"Z tego powodu metoda ta wymaga więcej zasobów niż asynchroniczne przetwarzanie MDN.</HTML>"},
		{"label.test.connection", "Sprawdź połączenie"},
		{"label.url", "Odbieranie adresu URL"},
		{"label.url.help", "<HTML><strong>Receive URL</strong><br><br>"
			+"Jest to adres URL partnera, za pośrednictwem którego można uzyskać dostęp do jego systemu AS2.<br>"
			+"Wprowadź ten adres URL w formacie <strong>PROTOCOL://HOST:PORT/PFAD</strong>, przy czym <strong>PROTOCOL</strong> musi być jednym z \"http\" lub \"https\". <strong>HOST</strong> oznacza host serwera AS2 partnera. <strong>PORT</strong> oznacza port odbiorczy partnera. <strong>PFAD</strong> oznacza ścieżkę odbiorczą, na przykład \"/as2/HttpReceiver\" Cały wpis jest oznaczony jako nieprawidłowy, jeśli protokół nie jest jednym z \"http\" lub \"https\", jeśli adres URL ma nieprawidłowy format lub jeśli port nie jest zdefiniowany w adresie URL.<br><br>"
			+"Nie wprowadzaj tutaj adresu URL, który odnosi się do twojego własnego systemu poprzez \"localhost\" lub \"127.0.0.1\" - próbowałbyś wysłać wychodzące wiadomości AS2 do swojego własnego systemu.</HTML>"},
		{"label.usecommandonreceipt", "Odbiór"},
		{"label.usecommandonsenderror", "Wysyłka (wadliwa)"},
		{"label.usecommandonsendsuccess", "Wysyłka (udana)"},
		{"partnerinfo", "Wraz z każdą wiadomością AS2 partner wysyła również informacje o funkcjach swojego systemu AS2. Oto lista tych funkcji."},
		{"partnersystem.noinfo", "Brak dostępnych informacji - czy dokonano już transakcji?"},
		{"tab.dirpoll", "Monitorowanie katalogów"},
		{"tab.events", "Przetwarzanie końcowe"},
		{"tab.httpauth", "Uwierzytelnianie HTTP"},
		{"tab.httpheader", "Nagłówek HTTP"},
		{"tab.mdn", "MDN"},
		{"tab.misc", "Ogólne"},
		{"tab.notification", "Powiadomienie"},
		{"tab.partnersystem", "Info"},
		{"tab.receipt", "Odbiór"},
		{"tab.security", "Bezpieczeństwo"},
		{"tab.send", "Wysyłka"},
		{"title", "Konfiguracja partnera"},
		{"tooltip.button.addevent", "Utwórz nowe zdarzenie"},
		{"tooltip.button.editevent", "Edytuj wydarzenie"},
	};
}
