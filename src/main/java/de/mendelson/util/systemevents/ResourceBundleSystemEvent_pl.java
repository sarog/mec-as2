//$Header: /as4/de/mendelson/util/systemevents/ResourceBundleSystemEvent_pl.java 2     17/02/26 11:08 Heller $
package de.mendelson.util.systemevents;

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
public class ResourceBundleSystemEvent_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"category.100", "Składnik serwera"},
		{"category.1000", "Przetwarzanie danych"},
		{"category.100000", "Inne"},
		{"category.1100", "Aktywacja"},
		{"category.1200", "Obsługa plików"},
		{"category.1300", "Obsługa klienta"},
		{"category.1400", "Interfejs XML"},
		{"category.1500", "Interfejs REST"},
		{"category.200", "Połączenie"},
		{"category.300", "Transakcja"},
		{"category.400", "Certyfikat"},
		{"category.500", "Baza danych"},
		{"category.700", "Konfiguracja"},
		{"category.800", "Warunkowy"},
		{"category.900", "Powiadomienie"},
		{"origin.1", "System"},
		{"origin.2", "Użytkownicy"},
		{"origin.3", "Transakcja"},
		{"severity.1", "Info"},
		{"severity.2", "Ostrzeżenie"},
		{"severity.3", "Błąd"},
		{"type.100", "Serwer wyłączony"},
		{"type.1000", "Przetwarzanie danych"},
		{"type.100000", "Nieokreślony"},
		{"type.1001", "Przetwarzanie wstępne"},
		{"type.1002", "Przetwarzanie końcowe"},
		{"type.101", "Uruchomienie serwera"},
		{"type.102", "Uruchomiony serwer"},
		{"type.103", "Uruchomienie serwera DB"},
		{"type.104", "Uruchomiony serwer DB"},
		{"type.105", "Serwer DB wyłączony"},
		{"type.106", "Uruchomienie serwera HTTP"},
		{"type.107", "Uruchomiony serwer HTTP"},
		{"type.108", "Serwer HTTP wyłączony"},
		{"type.109", "Uruchomienie serwera TRFC"},
		{"type.110", "Uruchomiony serwer TRFC"},
		{"type.1100", "Licencja"},
		{"type.1101", "Aktualizacja licencji"},
		{"type.1102", "Wygaśnięcie licencji"},
		{"type.111", "Status serwera TRFC"},
		{"type.112", "Serwer TRFC wyłączony"},
		{"type.113", "Uruchomienie harmonogramu"},
		{"type.114", "Uruchomiony harmonogram"},
		{"type.115", "Wyłączenie harmonogramu"},
		{"type.116", "Monitorowanie katalogów (zmiana stanu)"},
		{"type.117", "Port odbioru"},
		{"type.1200", "Obsługa plików"},
		{"type.1201", "Plik (usuń)"},
		{"type.1202", "Utwórz katalog"},
		{"type.1203", "Plik (przenieś)"},
		{"type.1204", "Plik (kopia)"},
		{"type.1300", "Klient"},
		{"type.1301", "Logowanie użytkownika (powodzenie)"},
		{"type.1302", "Logowanie użytkownika (nie powiodło się)"},
		{"type.1303", "Separacja użytkowników"},
		{"type.1400", "XML"},
		{"type.1401", "Konfiguracja certyfikatu"},
		{"type.1402", "Konfiguracja partnera"},
		{"type.1500", "REST"},
		{"type.1501", "Dodaj certyfikat"},
		{"type.1502", "Konfiguracja certyfikatu"},
		{"type.1503", "Usuń certyfikat"},
		{"type.1504", "Dodaj partnera"},
		{"type.1505", "Konfiguracja partnera"},
		{"type.1506", "Usuń partnera"},
		{"type.1507", "Wyślij zamówienie"},
		{"type.1508", "Transakcja (usuń)"},
		{"type.199", "Składnik serwera"},
		{"type.200", "Połączenie"},
		{"type.201", "Test połączenia"},
		{"type.300", "Transakcja"},
		{"type.301", "Błąd transakcji"},
		{"type.302", "Transakcja (ponowna dostawa odrzucona)"},
		{"type.303", "Transakcja (zduplikowana wiadomość)"},
		{"type.304", "Transakcja (usuń)"},
		{"type.305", "Transakcja (anuluj)"},
		{"type.306", "Transakcja (ponowne wysłanie)"},
		{"type.400", "Certyfikat"},
		{"type.401", "Certyfikat (dodany)"},
		{"type.402", "Certyfikat (zmieniono alias)"},
		{"type.403", "Certyfikat (usunięty)"},
		{"type.404", "Wymiana certyfikatów"},
		{"type.405", "Certyfikat wygasa"},
		{"type.406", "Wymiana certyfikatów (żądanie przychodzące)"},
		{"type.407", "Certyfikat (import magazynu kluczy)"},
		{"type.500", "Baza danych"},
		{"type.501", "Tworzenie bazy danych"},
		{"type.502", "Baza danych (aktualizacja)"},
		{"type.503", "Baza danych (inicjalizacja)"},
		{"type.504", "Transakcja wycofania"},
		{"type.700", "Konfiguracja"},
		{"type.701", "Zmiana konfiguracji"},
		{"type.702", "Kontrola konfiguracji"},
		{"type.703", "Partner (zmodyfikowany)"},
		{"type.704", "Partner (usunięto)"},
		{"type.705", "Partner (dodano)"},
		{"type.800", "Kwota"},
		{"type.801", "Osiągnięty limit"},
		{"type.802", "Osiągnięty limit"},
		{"type.900", "Powiadomienie"},
		{"type.901", "Powiadomienie (wysyłka powiodła się)"},
		{"type.902", "Powiadomienie (wysyłka nie powiodła się)"},
	};
}
