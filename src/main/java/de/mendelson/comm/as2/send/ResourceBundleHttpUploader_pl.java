//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleHttpUploader_pl.java 1     24/09/25 10:38 Heller $
package de.mendelson.comm.as2.send;

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
public class ResourceBundleHttpUploader_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"answer.no.sync.empty", "Otrzymane synchroniczne potwierdzenie odbioru jest puste. Prawdopodobnie wystąpił problem z przetwarzaniem wiadomości AS2 po stronie partnera - skontaktuj się z nim w tej sprawie."},
		{"answer.no.sync.mdn", "Otrzymane potwierdzenie synchroniczne nie ma prawidłowego formatu. Ponieważ problemy ze strukturą MDN są nietypowe, być może nie jest to odpowiedź z systemu AS2, do którego próbowałeś się zwrócić, ale być może odpowiedź z serwera proxy lub odpowiedź ze standardowej strony internetowej? Brakuje następujących wartości nagłówka HTTP: [{0}].\nOtrzymane dane zaczynają się od następujących struktur:\n{1}"},
		{"connected.to", "Połączenie z {0}, oczekiwanie na MDN i utrzymywanie połączenia otwartego do {1}."},
		{"connection.shut.down", "Połączenie wychodzące do {0} zostało zamknięte, było otwarte przez {1}s"},
		{"connection.tls.info", "Ustanowiono wychodzące połączenie TLS [{0}, {1}]."},
		{"error.http502", "Problem z połączeniem, nie można przesłać danych. (HTTP 502 - ZŁA BRAMA)"},
		{"error.http503", "Problem z połączeniem, nie można przesłać danych. (HTTP 503 - USŁUGA NIEDOSTĘPNA)"},
		{"error.http504", "Problem z połączeniem, nie można było przesłać danych. (HTTP 504 - PRZEKROCZENIE LIMITU CZASU BRAMY)"},
		{"error.httpupload", "Transmisja nie powiodła się, zdalny serwer AS2 zgłasza \"{0}\"."},
		{"error.noconnection", "Problem z połączeniem, nie można przesłać danych."},
		{"hint.ConnectTimeoutException", "Uwaga:\nZazwyczaj jest to problem związany z infrastrukturą, który nie ma nic wspólnego z protokołem AS2. Nie można nawiązać połączenia wychodzącego z partnerem.\nAby rozwiązać problem, należy sprawdzić następujące elementy:\n*Czy masz aktywne połączenie internetowe?\n*Sprawdź, czy wprowadziłeś poprawny adres URL swojego partnera w panelu administracyjnym partnera.\n*Skontaktuj się z partnerem, być może jego system AS2 jest niedostępny?"},
		{"hint.SSLException", "Uwaga:\nZazwyczaj jest to problem związany z negocjacjami na poziomie protokołu. Twój partner odrzucił Twoje połączenie.\nAlbo twój partner oczekuje bezpiecznego połączenia (HTTPS), a ty chciałeś ustanowić niezabezpieczone połączenie, albo odwrotnie.\nMożliwe jest również, że Twój partner wymaga innej wersji TLS lub innego algorytmu szyfrowania niż oferowany przez Ciebie."},
		{"hint.SSLPeerUnverifiedException", "Uwaga:\nProblem wystąpił podczas uzgadniania TLS. W związku z tym system nie był w stanie nawiązać bezpiecznego połączenia z partnerem, problem nie ma nic wspólnego z protokołem AS2.\nSprawdź następujące elementy:\n*Czy zaimportowałeś wszystkie certyfikaty swojego partnera do swojego magazynu kluczy TLS (dla TLS, w tym certyfikaty pośrednie/główne)?\n*Czy Twój partner zaimportował wszystkie certyfikaty od Ciebie (dla TLS, w tym certyfikaty pośrednie/główne)?"},
		{"hint.httpcode.signals.problem", "Uwaga:\nNawiązano połączenie z hostem partnerskim - działa tam serwer WWW.\nZdalny serwer sygnalizuje, że coś jest nie tak ze ścieżką lub portem żądania i zwraca kod HTTP {0}.\nJeśli potrzebujesz więcej informacji na temat tego kodu HTTP, skorzystaj z wyszukiwarki internetowej."},
		{"httpheader.added.basicauth", "Nagłówek HTTP dla uwierzytelniania podstawowego został dodany do żądania wysłania"},
		{"httpheader.deleted", "Nagłówek HTTP \"{0}\" został usunięty z powodu niestandardowych ustawień nagłówka HTTP"},
		{"httpheader.replaced", "Wartość nagłówka HTTP \"{0}\" została zastąpiona wartością zdefiniowaną przez użytkownika \"{1}\""},
		{"httpheader.set", "Nagłówek HTTP \"{0}\" został ustawiony na zdefiniowaną przez użytkownika wartość \"{1}\""},
		{"returncode.accepted", "Wiadomość wysłana pomyślnie (HTTP {0}); {1} przesłane w {2} [{3}]."},
		{"returncode.ok", "Wiadomość wysłana pomyślnie (HTTP {0}); {1} wysłane w {2} [{3}]."},
		{"sending.cem.async", "Wyślij wiadomość CEM do {0}, oczekuj asynchronicznego MDN dla potwierdzenia odbioru w {1}."},
		{"sending.cem.sync", "Wyślij wiadomość CEM do {0}, oczekuj synchronicznego potwierdzenia odbioru przez MDN."},
		{"sending.mdn.async", "Wysyła asynchroniczne potwierdzenie odbioru (MDN) do {0}."},
		{"sending.msg.async", "Wyślij wiadomość AS2 do {0}, oczekuj asynchronicznego MDN dla potwierdzenia odbioru w {1}."},
		{"sending.msg.sync", "Wyślij wiadomość AS2 do {0}, oczekuj synchronicznego MDN dla potwierdzenia odbioru."},
		{"strict.hostname.check", "W przypadku wychodzącego połączenia TLS przeprowadzana jest ścisła kontrola nazwy hosta w odniesieniu do certyfikatu serwera."},
		{"strict.hostname.check.skipped.selfsigned", "TLS: Sprawdzanie ścisłej nazwy hosta zostało pominięte - serwer zdalny używa certyfikatu z podpisem własnym."},
		{"trust.all.server.certificates", "Wychodzące połączenie TLS będzie ufać wszystkim certyfikatom zdalnego serwera, jeśli dostępne są certyfikaty główne i pośrednie."},
		{"using.proxy", "Użyj proxy {0}:{1}."},
		{"using.proxy.auth", "Użyj proxy {0}:{1} (uwierzytelnianie jako {2})."},
	};
}
