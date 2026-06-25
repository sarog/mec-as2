//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferencesAS2_pl.java 1     24/09/25 10:36 Heller $
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
public class ResourceBundlePreferencesAS2_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"FALSE", "wyłączony"},
		{"TRUE", "włączony"},
		{"asyncmdntimeout", "Limit czasu dla asynchronicznego MDN w min."},
		{"autoimportpartnertlscertificates", "Automatyczny import zmodyfikowanych certyfikatów TLS partnerów"},
		{"autologdirdelete", "Automatyczne czyszczenie katalogu dziennika"},
		{"autologdirdeleteolderthan", "Wyczyść katalog dziennika (starszy niż)"},
		{"automsgdelete", "Automatyczne usuwanie starych transakcji"},
		{"automsgdeletelog", "Usuń stare transakcje (wpis w dzienniku)"},
		{"automsgdeleteolderthan", "Usuń stare transakcje (starsze niż)"},
		{"automsgdeleteolderthanmults", "Usuń stare transakcje (jednostka czasu w s)"},
		{"autostatsdelete", "Automatyczne usuwanie starych danych statystycznych"},
		{"autostatsdeleteolderthan", "Usuń statystyki (starsze niż)"},
		{"cem", "Korzystanie z CEM"},
		{"checkrevocationlist", "Sprawdź listy unieważnionych certyfikatów"},
		{"colorblindness", "Wsparcie dla ślepoty barw"},
		{"commed", "Edycja społecznościowa"},
		{"country", "Kraj"},
		{"datasheetreceipturl", "Adres URL arkusza danych"},
		{"dirmsg", "Podstawowy katalog wiadomości"},
		{"embeddedhttpserverrequestlog", "Dziennik żądań zintegrowanego serwera HTTP"},
		{"httpsendtimeout", "Limit czasu wysyłania (HTTP/S)"},
		{"jetty.connectionlimit.maxConnections", "Maksymalna liczba jednoczesnych połączeń przychodzących"},
		{"jetty.http.port", "Port wejściowy HTTP"},
		{"jetty.ssl.port", "Port wejściowy HTTPS"},
		{"language", "Język klienta"},
		{"lastupdatecheck", "Ostatnie sprawdzenie nowej wersji (czas unixowy)"},
		{"logpollprocess", "Dokumentowanie procesu ankiety w dzienniku"},
		{"logprocessing", "Wyświetlanie dodatkowego dziennika przetwarzania"},
		{"maxoutboundconnections", "Maksymalna liczba jednoczesnych połączeń wychodzących"},
		{"module.name", "[USTAWIENIA]"},
		{"notification.setting.updated", "Ustawienia powiadomień zostały zmienione."},
		{"outboundstatusfile", "Tworzenie pliku stanu dla każdej transakcji"},
		{"proxyhost", "Host serwera proxy HTTP"},
		{"proxypass", "Dane dostępu do serwera proxy HTTP (hasło)"},
		{"proxyport", "Port proxy HTTP"},
		{"proxyuse", "Użycie proxy HTTP dla połączenia wychodzącego"},
		{"proxyuseauth", "Używanie danych dostępu proxy HTTP"},
		{"proxyuser", "Dane dostępu proxy HTTP (użytkownik)"},
		{"receiptpartnersubdir", "Użyj podkatalogu dla każdego partnera"},
		{"retrycount", "Liczba prób połączenia"},
		{"retrywaittime", "Ponowne nawiązywanie połączenia co n sekund"},
		{"set.to", "został ustawiony na"},
		{"setting.reset", "Ustawienie serwera [{0}] zostało zresetowane do wartości domyślnej."},
		{"setting.updated", "Ustawienia zostały zaktualizowane"},
		{"showhttpheaderconf", "Wyświetlanie zarządzania nagłówkami HTTP w kliencie"},
		{"showoverwritelocalstationsecurity", "Wyświetl: Zastąp zabezpieczenia stacji lokalnej"},
		{"showquotaconf", "Pokaż limit w zarządzaniu partnerami"},
		{"stricthostcheck", "(TLS) Sprawdź hosta"},
		{"trustallservercerts", "(TLS) Zaufaj wszystkim certyfikatom serwerów zdalnych"},
	};
}
