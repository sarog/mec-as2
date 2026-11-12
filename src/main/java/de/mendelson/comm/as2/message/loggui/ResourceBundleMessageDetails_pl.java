//$Header: /as2/de/mendelson/comm/as2/message/loggui/ResourceBundleMessageDetails_pl.java 2     21/10/25 15:02 Heller $
package de.mendelson.comm.as2.message.loggui;

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
public class ResourceBundleMessageDetails_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.ok", "Ok"},
		{"header.encryption", "Szyfrowanie"},
		{"header.messageid", "Numer referencyjny"},
		{"header.senderhost", "Nadajnik"},
		{"header.signature", "Podpis cyfrowy"},
		{"header.timestamp", "data"},
		{"header.useragent", "Serwer AS2"},
		{"message.header", "Dane nagłówka"},
		{"message.payload", "Dane użytkownika"},
		{"message.payload.multiple", "Dane użytkownika ({0})"},
		{"message.raw.decrypted", "Dane transmisji (niezaszyfrowane)"},
		{"tab.log", "Dziennik tej instancji wiadomości"},
		{"title", "Szczegóły wiadomości"},
		{"title.cem", "Szczegóły komunikatu wymiany certyfikatów (CEM)"},
		{"transactiondetails.inbound.async", " Potwierdzenie wysyłane jest poprzez nawiązanie nowego połączenia z partnerem (asynchroniczna sieć MDN)."},
		{"transactiondetails.inbound.insecure", "To jest niezabezpieczone połączenie przychodzące, otrzymujesz dane od partnera \"{0}\"."},
		{"transactiondetails.inbound.secure", "To jest przychodzące bezpieczne połączenie, otrzymujesz dane od partnera \"{0}\"."},
		{"transactiondetails.inbound.sync", " Potwierdzenie wysyłane jest bezpośrednio jako odpowiedź na kanale zwrotnym połączenia przychodzącego (synchroniczny MDN)."},
		{"transactiondetails.outbound.async", " Twój partner nawiązuje nowe połączenie z Tobą w celu potwierdzenia (asynchroniczna sieć MDN)."},
		{"transactiondetails.outbound.insecure", "Jest to niezabezpieczone połączenie wychodzące, wysyłasz dane do partnera \"{0}\"."},
		{"transactiondetails.outbound.secure", "To jest bezpieczne połączenie wychodzące, wysyłasz dane do partnera \"{0}\"."},
		{"transactiondetails.outbound.sync", " Potwierdzenie otrzymasz bezpośrednio jako odpowiedź na kanale zwrotnym połączenia wychodzącego (synchroniczny MDN)."},
		{"transactionstate.error.asyncmdnsend", "<HTML> Wiadomość z asynchronicznym żądaniem MDN została odebrana i pomyślnie przetworzona, ale system nie mógł zwrócić asynchronicznego MDN lub nie został zaakceptowany przez system partnerski.</HTML>"},
		{"transactionstate.error.asyncmdnsend.details", "<HTML>Nadawca wiadomości AS2 przesyła adres URL, na który powinien zwrócić MDN - albo ten system jest niedostępny (problem z infrastrukturą lub system partnerski nie działa?), albo system partnerski nie zaakceptował asynchronicznego MDN i odpowiedział HTTP 400.</HTML>"},
		{"transactionstate.error.authentication-failed", "<HTML>Odbiorca wiadomości nie mógł pomyślnie sprawdzić podpisu nadawcy w danych. Zazwyczaj jest to problem z konfiguracją, ponieważ nadawca i odbiorca muszą używać tego samego certyfikatu. Sprawdź również szczegóły MDN w dzienniku - mogą one zawierać dodatkowe informacje.</HTML>"},
		{"transactionstate.error.connectionrefused", "<HTML>Próbowano połączyć się z systemem partnera. Próba nie powiodła się lub partner nie odpowiedział potwierdzeniem w określonym czasie.</HTML>"},
		{"transactionstate.error.connectionrefused.details", "<HTML>Może to być problem z infrastrukturą, system partnera w ogóle nie działa lub wprowadziłeś nieprawidłowy adres URL odbioru w konfiguracji? Jeśli dane zostały przesłane, a Twój partner ich nie potwierdził, być może ustawiłeś zbyt krótkie okno czasowe na potwierdzenie?</HTML>"},
		{"transactionstate.error.decompression-failed", "<HTML>Odbiorca wiadomości nie mógł zdekompresować otrzymanej wiadomości</HTML>"},
		{"transactionstate.error.decryption-failed", "<HTML>Odbiorca wiadomości nie był w stanie odszyfrować wiadomości. Zazwyczaj jest to problem z konfiguracją, czy nadawca używa prawidłowego certyfikatu do szyfrowania?</HTML>"},
		{"transactionstate.error.in", "<HTML>Pomyślnie otrzymałeś wiadomość {0} od swojego partnera \"{1}\" - ale twój system nie był w stanie jej przetworzyć i odpowiedział błędem [{2}].</HTML>"},
		{"transactionstate.error.insufficient-message-security", "<HTML>Odbiorca wiadomości oczekiwał wyższego poziomu bezpieczeństwa otrzymanych danych (na przykład zaszyfrowanych danych zamiast niezaszyfrowanych).</HTML>"},
		{"transactionstate.error.messagecreation", "<HTML>Wystąpił problem podczas generowania wychodzącej wiadomości AS2</HTML>"},
		{"transactionstate.error.messagecreation.details", "<HTML>System nie mógł wygenerować wymaganej struktury wiadomości z powodu problemu po stronie użytkownika. Nie ma to nic wspólnego z systemem partnera, nie nawiązano połączenia.</HTML>"},
		{"transactionstate.error.out", "<HTML>Pomyślnie wysłałeś wiadomość {0} do swojego partnera \"{1}\" - ale nie był on w stanie jej przetworzyć i odpowiedział błędem [{2}].</HTML>"},
		{"transactionstate.error.unexpected-processing-error", "<HTML>To jest bardzo ogólny komunikat o błędzie. Z nieznanego powodu odbiorca nie był w stanie przetworzyć wiadomości.</HTML>"},
		{"transactionstate.error.unknown", "Wystąpił nieznany błąd."},
		{"transactionstate.error.unknown-trading-partner", "<HTML>Ty i twój partner macie różne identyfikatory AS2 dla dwóch partnerów transmisji w konfiguracji. Użyto następujących identyfikatorów: \"{0}\" (nadawca wiadomości), \"{1}\" (odbiorca wiadomości)</HTML>"},
		{"transactionstate.ok.details", "<HTML>Dane zostały przesłane i transakcja została pomyślnie zakończona.</HTML>"},
		{"transactionstate.ok.receive", "<HTML> Wiadomość {0} została pomyślnie odebrana przez partnera \"{1}\". Odpowiednie potwierdzenie zostało wysłane do partnera.</HTML>"},
		{"transactionstate.ok.send", "<HTML> Wiadomość {0} została pomyślnie wysłana do partnera \"{1}\" - wysłał on odpowiednie potwierdzenie.</HTML>"},
		{"transactionstate.pending", "Ta transakcja ma status oczekiwania."},
                {"mdn.nopayload", "Jest to plik MDN — nie ma dla niego dostępnego załącznika"},
	};
}
