//$Header: /as2/de/mendelson/comm/as2/configurationcheck/ResourceBundleConfigurationIssue_pl.java 1     24/09/25 10:32 Heller $
package de.mendelson.comm.as2.configurationcheck;

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
public class ResourceBundleConfigurationIssue_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"1", "Nie znaleziono klucza w magazynie kluczy TLS"},
		{"10", "Brakujący certyfikat podpisu zdalnego partnera"},
		{"11", "Brakujący klucz szyfrowania stacji lokalnej"},
		{"12", "Brakujący klucz podpisu lokalnej stacji"},
		{"13", "Użycie publicznie dostępnego klucza testowego jako klucza TLS"},
		{"14", "Użycie 32-bitowej maszyny wirtualnej Java nie jest zalecane do produktywnego użytku, ponieważ maksymalna pamięć sterty jest wtedy ograniczona do 1,3 GB."},
		{"15", "Usługa Windows uruchomiona przy użyciu lokalnego konta systemowego"},
		{"16", "Duża ilość monitorowanych katalogów na jednostkę czasu"},
		{"17", "Problem z listą bloków (TLS)"},
		{"18", "Problem z listą odwołań (enc/sign)"},
		{"19", "Klient i serwer działają w jednym procesie"},
		{"2", "Wiele kluczy znalezionych w magazynie kluczy TLS - może być tylko jeden"},
		{"20", "Niewystarczająca liczba uchwytów dla procesu serwera"},
		{"3", "Certyfikat wygasł (TLS)"},
		{"4", "Certyfikat wygasł (enc/sign)"},
		{"5", "Aktywuj automatyczne usuwanie - W systemie znajduje się duża liczba transakcji."},
		{"6", "Przydziel co najmniej 4 rdzenie procesora do systemu"},
		{"7", "Zarezerwowanie co najmniej 8 GB pamięci głównej dla procesu serwera."},
		{"8", "Ilość połączeń wychodzących jest ustawiona na 0 - system NIE będzie wysyłał"},
		{"9", "Brakujący certyfikat szyfrowania zdalnego partnera"},
		{"hint.1", "<HTML>Nie znaleziono klucza w magazynie kluczy TLS systemu.<br>"
			+"Klucze można rozpoznać po symbolu klucza znajdującym się przed nimi po otwarciu zarządzania certyfikatami.<br>"
			+"Dokładnie jeden klucz jest wymagany w magazynie kluczy TLS do wykonania procesu uzgadniania w celu zapewnienia bezpieczeństwa linii.<br>"
			+"Bez tego klucza nie można uzyskać dostępu do bezpiecznych połączeń ani ich opuścić.</HTML>"},
		{"hint.10", "<HTML>Partner połączenia nie ma przypisanego certyfikatu podpisu w konfiguracji.<br>"
			+"W takim przypadku nie można zweryfikować podpisów cyfrowych partnera. Otwórz administrację partnera i przypisz certyfikat podpisu do partnera.</HTML>"},
		{"hint.11", "<HTML>Twoja lokalna stacja nie przypisała klucza szyfrowania.<br>"
			+"W tej konfiguracji nie można odszyfrowywać przychodzących wiadomości - niezależnie od partnera.<br>"
			+"Otwórz administrację partnera i przypisz klucz prywatny do stacji lokalnej.</HTML>"},
		{"hint.12", "<HTML>Twoja lokalna stacja nie przypisała klucza podpisu.<br>"
			+"W tej konfiguracji nie można podpisywać cyfrowo wiadomości wychodzących - niezależnie od partnera.<br>"
			+"Otwórz administrację partnera i przypisz klucz prywatny do stacji lokalnej.</HTML>"},
		{"hint.13", "<HTML>W dostawie mendelson dostarcza kilka kluczy testowych.<br>"
			+"Są one publicznie dostępne na stronie internetowej mendelson.<br>"
			+"Jeśli klucze te są produktywnie wykorzystywane do zadań kryptograficznych w ramach transferu danych, zapewniają one <strong>BRAK</strong> bezpieczeństwa.<br>"
			+"Tutaj można również wysyłać niezabezpieczone i niezaszyfrowane wiadomości.<br>"
			+"Jeśli potrzebujesz certyfikowanego klucza, skontaktuj się z pomocą techniczną mendelson.</HTML>"},
		{"hint.14", "<HTML> 32-bitowe procesy Java nie mogą zarezerwować wystarczającej ilości pamięci, aby utrzymać stabilność systemu podczas wydajnej pracy. Prosimy o używanie 64-bitowej maszyny JVM.</HTML>"},
		{"hint.15", "<HTML>Serwer mendelson AS2 został skonfigurowany jako usługa systemu Windows i uruchomiony za pośrednictwem lokalnego konta systemowego (\"{0}\").<br>"
			+"Niestety, możliwe jest, że użytkownik ten utraci prawa do wcześniej zapisanych plików po aktualizacji systemu Windows, co może prowadzić do różnych problemów systemowych.<br><br>"
			+"Skonfiguruj oddzielnego użytkownika dla usługi i uruchom usługę z tym użytkownikiem.</HTML>"},
		{"hint.16", "<HTML>Zdefiniowałeś dużą liczbę relacji partnerskich w swoim systemie i monitorujesz odpowiednie katalogi wychodzące w zbyt krótkich odstępach czasu.<br>"
			+"Obecnie aktywowanych jest {0} zegarków katalogów na minutę, a system nie jest w stanie nadążyć za tak wysokim tempem.<br>"
			+"W przypadku dużej liczby partnerów zaleca się dezaktywację monitorowania wszystkich katalogów i tworzenie zadań wysyłania z zaplecza za pomocą poleceń <i>AS2Send.exe</i> lub <i>as2send.sh</i> zgodnie z wymaganiami.</HTML>"},
		{"hint.17", "<HTML>Certyfikaty uwierzytelnione zawierają link do listy unieważnień, która może być użyta do uznania tego certyfikatu za nieważny. Na przykład, jeśli certyfikat został naruszony.<br>"
			+"Wystąpił problem ze sprawdzeniem listy odwołań następującego certyfikatu TLS lub certyfikat został odwołany:<br>"
			+"<strong>{0}</strong><br><br>"
			+"Dodatkowe informacje o tym certyfikacie<br><br>"
			+"Alias: {1}<br>"
			+"Emitent: {2}<br>"
			+"Odcisk palca (SHA-1): {3}<br><br><br>"
			+"Należy pamiętać, że automatyczne sprawdzanie listy CRL można wyłączyć w ustawieniach.</HTML>"},
		{"hint.18", "<HTML>Certyfikaty uwierzytelnione zawierają łącze do listy odwołania certyfikatów (CRL), która może być użyta do uznania tego certyfikatu za nieważny. Na przykład, jeśli certyfikat został naruszony.<br>"
			+"Wystąpił problem ze sprawdzeniem listy unieważnień następującego certyfikatu enc/sign lub certyfikat został unieważniony:<br>"
			+"<strong>{0}</strong><br><br>"
			+"Dodatkowe informacje o tym certyfikacie<br><br>"
			+"Alias: {1}<br>"
			+"Emitent: {2}<br>"
			+"Odcisk palca (SHA-1): {3}<br><br><br>"
			+"Należy pamiętać, że automatyczne sprawdzanie listy CRL można wyłączyć w ustawieniach.</HTML>"},
		{"hint.19", "<HTML>Uruchomiłeś klienta i serwer produktu w jednym procesie. Nie jest to zalecane w przypadku pracy produktywnej. Ponieważ zasoby są statycznie przypisane do programów, w tym przypadku masz mniej zasobów na działanie serwera i klienta.<br><br>"
			+"Najpierw uruchom proces serwera, a następnie połącz się z klientem osobno.</HTML>"},
		{"hint.2", "<HTML>Magazyn kluczy TLS systemu zawiera kilka kluczy. Jednak może być tylko jeden - jest on używany jako klucz TLS podczas uruchamiania serwera.<br>"
			+"Usuń klucze z TLS Keystore, aż pozostanie tylko jeden klucz.<br>"
			+"Klucze w zarządzaniu certyfikatami można rozpoznać po symbolu klucza w pierwszej kolumnie.<br>"
			+"Po tej zmianie konieczne jest ponowne uruchomienie serwera.</HTML>"},
		{"hint.20", "<HTML>Można ograniczyć liczbę otwartych portów i plików na użytkownika w systemie operacyjnym.<br>"
			+"Bieżący użytkownik procesu może używać tylko {0} uchwytów, co jest zbyt małą liczbą dla działania serwera. Proces serwera używa obecnie {1} uchwytów.<br>"
			+"W systemie Linux wartość tę można wyświetlić za pomocą polecenia \"ulimit -n\".<br><br>"
			+"Zwiększ maksymalną wartość dostępnych uchwytów dla tego procesu do co najmniej {2}.</HTML>"},
		{"hint.3", "<HTML>Certyfikaty mają ograniczony okres ważności. Zazwyczaj jest to jeden, trzy lub pięć lat.<br>"
			+"Certyfikat używany w systemie do zabezpieczenia linii TLS stracił ważność.<br>"
			+"Nie jest możliwe wykonywanie operacji kryptograficznych z wygasłym certyfikatem - dlatego należy zadbać o odnowienie certyfikatu lub utworzenie nowego certyfikatu lub jego uwierzytelnienie.<br><br>"
			+"<strong>Dodatkowe informacje na temat certyfikatu:</strong<br><br>"
			+"Alias: {0}<br>"
			+"Emitent: {1}<br>"
			+"Odcisk palca (SHA-1): {2}<br>"
			+"Obowiązuje od: {3}<br>"
			+"Ważne do: {4}<br><br>"
			+"</HTML>"},
		{"hint.4", "<HTML>Certyfikaty mają ograniczony okres ważności. Zazwyczaj jest to jeden, trzy lub pięć lat.<br>"
			+"Certyfikat używany w systemie partnera do szyfrowania/deszyfrowania danych, podpisywania cyfrowego lub sprawdzania podpisu cyfrowego stracił ważność.<br>"
			+"Nie jest możliwe wykonywanie operacji kryptograficznych z wygasłym certyfikatem - dlatego należy zadbać o odnowienie certyfikatu lub utworzenie nowego certyfikatu lub jego uwierzytelnienie.<br><br>"
			+"<strong>Dodatkowe informacje na temat certyfikatu:</strong<br><br>"
			+"Alias: {0}<br>"
			+"Emitent: {1}<br>"
			+"Odcisk palca (SHA-1): {2}<br>"
			+"Obowiązuje od: {3}<br>"
			+"Ważne do: {4}<br><br>"
			+"</HTML>"},
		{"hint.5", "<HTML>W ustawieniach można zdefiniować, jak długo transakcje powinny pozostawać w systemie.<br>"
			+"Im więcej transakcji pozostaje w systemie, tym więcej zasobów wymaga administracja.<br>"
			+"Dlatego należy użyć ustawień, aby upewnić się, że w systemie nigdy nie będzie więcej niż 30000 transakcji.<br>"
			+"Należy pamiętać, że nie jest to system archiwizacji, ale adapter komunikacyjny.<br>"
			+"Użytkownik ma dostęp do wszystkich poprzednich dzienników transakcji za pośrednictwem zintegrowanej funkcji wyszukiwania w dzienniku serwera.</HTML>"},
		{"hint.6", "<HTML>Dla lepszej przepustowości konieczne jest równoległe wykonywanie różnych zadań w systemie.<br>"
			+"Dlatego konieczne jest zarezerwowanie odpowiedniej liczby rdzeni procesora dla tego procesu.</HTML>"},
		{"hint.7", "<HTML>Ten program jest napisany w języku Java.<br>"
			+"Niezależnie od fizycznej konfiguracji komputera, należy zarezerwować odpowiednią ilość pamięci dla procesu serwera. W twoim przypadku zarezerwowałeś zbyt mało pamięci.<br>"
			+"Zapoznaj się z pomocą (sekcja Instalacja) - wyjaśnia ona, jak zarezerwować odpowiednią pamięć dla danej metody uruchamiania.<br><br>"
			+"W każdym przypadku należy upewnić się, że nie zarezerwowano więcej pamięci dla procesu serwera niż wynosi pamięć główna systemu. W przeciwnym razie oprogramowanie stanie się prawie bezużyteczne, ponieważ system będzie stale zamieniał pamięć na dysk twardy.</HTML>"},
		{"hint.8", "<HTML>Dokonałeś zmian w konfiguracji, przez co połączenia wychodzące nie są obecnie możliwe.<br>"
			+"Jeśli chcesz nawiązywać połączenia wychodzące z partnerami, liczba możliwych połączeń musi wynosić co najmniej 1.</HTML>"},
		{"hint.9", "<HTML>Partner połączenia nie przypisał certyfikatu szyfrowania w konfiguracji.<br>"
			+"W takim przypadku nie można szyfrować wiadomości do partnera. Otwórz administrację partnera i przypisz certyfikat szyfrowania do partnera.</HTML>"},
	};
}
