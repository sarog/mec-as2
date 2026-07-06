//$Header: /as2/de/mendelson/util/security/cert/gui/keygeneration/ResourceBundleGenerateKey_pl.java 1     24/09/25 10:46 Heller $
package de.mendelson.util.security.cert.gui.keygeneration;

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
public class ResourceBundleGenerateKey_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Rozbiórka"},
		{"button.ignore", "Ignorowanie ostrzeżeń"},
		{"button.ok", "Ok"},
		{"button.reedit", "Korekta"},
		{"label.commonname", "Nazwa zwyczajowa"},
		{"label.commonname.help", "<HTML><strong>Nazwa zwyczajowa</strong><br><br>"
			+"Jest to nazwa domeny odpowiadająca wpisowi DNS. Ten parametr jest ważny dla uzgadniania połączenia TLS. W tym miejscu można (ale nie zaleca się!) wprowadzić adres IP. Możliwe jest również utworzenie certyfikatu wieloznacznego poprzez zastąpienie części domeny znakiem *. Nie jest to jednak zalecane, ponieważ nie wszyscy partnerzy akceptują takie klucze.<br>"
			+"Jeśli chcesz użyć tego klucza jako klucza TLS, a ten wpis odnosi się do nieistniejącej domeny lub nie odpowiada Twojej domenie, większość systemów powinna przerwać przychodzące połączenia TLS.</HTML>"},
		{"label.commonname.hint", "(Nazwa domeny serwera)"},
		{"label.countrycode", "Kod kraju"},
		{"label.countrycode.hint", "(2 znaki, ISO 3166)"},
		{"label.extension.ski", "Identyfikator klucza przedmiotu (SKI)"},
		{"label.extension.ski.help", "<HTML><strong>SKI</strong><br><br>"
			+"Istnieje kilka sposobów identyfikacji certyfikatu: za pomocą skrótu certyfikatu, wystawcy, numeru seryjnego i identyfikatora klucza podmiotu (SKI). SKI zapewnia unikalny identyfikator wnioskodawcy certyfikatu i jest często używany podczas pracy z cyfrowym podpisem XML lub ogólnie w obszarze bezpieczeństwa usług internetowych. To rozszerzenie z OID 2.5.29.14 jest zatem często wymagane dla AS4.</HTML>"},
		{"label.keytype", "Typ klucza"},
		{"label.keytype.help", "<HTML><strong>Typ klucza</strong><br><br>"
			+"Jest to algorytm tworzenia klucza. W zależności od algorytmu istnieją zalety i wady wynikowych kluczy.<br>"
			+"Od 2023 r. zalecamy stosowanie klucza RSA o długości 2048 lub 4096 bitów.<br>"
			+"Istnieją następujące typy:<br>"
			+"<ul><li>DSA: Starszy algorytm, rzadko używany</li><li>RSA: Klasyczny standard, szeroko stosowany</li><li>ECDSA: Wydajny podpis, krzywe eliptyczne</li><li>EDDSA: Nowoczesny i szybki, krzywe Edwardsa</li></ul>.</HTML>"},
		{"label.locality", "Lokalizacja"},
		{"label.locality.hint", "(Miasto)"},
		{"label.mailaddress", "Adres pocztowy"},
		{"label.mailaddress.help", "<HTML><strong>Adres e-mail</strong><br><br>"
			+"Jest to adres e-mail powiązany z kluczem. Z technicznego punktu widzenia parametr ten nie ma znaczenia. Jeśli jednak chcesz uwierzytelnić klucz, ten adres e-mail jest zwykle używany do komunikacji z urzędem certyfikacji. Ponadto adres e-mail powinien również znajdować się w domenie serwera i odpowiadać czemuś w rodzaju webmaster@domain lub podobnemu, ponieważ większość urzędów certyfikacji używa go do sprawdzenia, czy jesteś w posiadaniu powiązanej domeny.</HTML>"},
		{"label.namedeccurve", "Krzywa"},
		{"label.namedeccurve.help", "<HTML><strong>Krzywa</strong><br><br>"
			+"W tym miejscu wybiera się nazwę krzywej eliptycznej, która ma zostać użyta do wygenerowania klucza. Żądana długość klucza jest zwykle częścią nazwy krzywej, na przykład klucz krzywej \"BrainpoolP256r1\" ma długość 256 bitów. Najczęściej używaną krzywą od 2022 r. (około 75% wszystkich certyfikatów WE w Internecie korzysta z niej) jest NIST P-256, którą można znaleźć tutaj pod nazwą \"Prime256v1\". Jest to standardowa krzywa OpenSSL od 2022 roku.</HTML>"},
		{"label.organisationname", "Organizacja (nazwa)"},
		{"label.organisationunit", "Organizacja (jednostka)"},
		{"label.purpose", "Kluczowe rozszerzenia"},
		{"label.purpose.encsign", "Szyfrowanie i podpis cyfrowy"},
		{"label.purpose.ssl", "TLS"},
		{"label.signature", "Podpis"},
		{"label.signature.help", "<HTML><strong>Podpis</strong><br><br>"
			+"Jest to algorytm podpisu, którym podpisywany jest klucz. Jest on wymagany do testów integralności samego klucza. Ten parametr nie ma nic wspólnego z możliwościami podpisu klucza - na przykład można również tworzyć podpisy SHA-2 za pomocą klucza podpisanego SHA-1 lub odwrotnie.<br>"
			+"Zalecamy użycie klucza podpisanego SHA-2 od 2024 roku.<br><br>"
			+"<strong>Krótki przegląd: SHA-1, SHA-2, SHA-3 i RSASSA-PSS</strong><br><br>"
			+"<strong>SHA-1</strong>: Starszy algorytm skrótu, który jest obecnie uważany za niezabezpieczony.<br>"
			+"<strong>SHA-2</strong>: Bardziej nowoczesna i bezpieczna wersja SHA, która istnieje w różnych wariantach, takich jak SHA-256 i SHA-512.<br>"
			+"<strong>SHA-3</strong>: Najnowszy algorytm hashujący, który opiera się na innej strukturze niż SHA-1 i SHA-2 i jest jeszcze bardziej bezpieczny przed atakami.<br>"
			+"<strong>RSASSA-PSS (Probabilistic Signature Scheme)</strong>: Jest to rozszerzenie RSA. Łączy funkcję skrótu SHA z procedurą podpisu PSS, co zapewnia dodatkowe bezpieczeństwo.</HTML>"},
		{"label.size", "Długość klucza"},
		{"label.size.help", "<HTML><strong>Długość klucza</strong><br><br>"
			+"Jest to długość klucza. Zasadniczo operacje kryptograficzne z kluczami o większej długości są bezpieczniejsze niż operacje kryptograficzne z kluczami o mniejszej długości. Wadą dużych długości kluczy jest jednak to, że operacje kryptograficzne trwają znacznie dłużej, co może znacznie spowolnić przetwarzanie danych w zależności od mocy obliczeniowej.<br>"
			+"Zalecamy klucz o długości 2048 lub 4096 bitów od 2023 roku.</HTML>"},
		{"label.state", "Kraj"},
		{"label.subjectalternativenames", "Alternatywne nazwy wnioskodawców"},
		{"label.validity", "Ważność w dniach"},
		{"label.validity.help", "<HTML><strong>Ważność w dniach</strong><br><br>"
			+"Ta wartość jest interesująca tylko dla kluczy z podpisem własnym. W przypadku uwierzytelnienia urząd certyfikacji nadpisze tę wartość.</HTML>"},
		{"title", "Generowanie kluczy"},
		{"view.basic", "Widok standardowy"},
		{"view.expert", "Opinia eksperta"},
		{"warning.invalid.mail", "Adres e-mail \"{0}\" jest nieprawidłowy."},
		{"warning.mail.in.domain", "Adres e-mail nie jest częścią domeny \"{0}\" (np. myname@{0}).\nMoże to stanowić problem, jeśli klucz ma zostać później uwierzytelniony."},
		{"warning.nonexisting.domain", "Domena \"{0}\" nie istnieje."},
		{"warning.title", "Możliwy problem z kluczowymi parametrami"},
	};
}
