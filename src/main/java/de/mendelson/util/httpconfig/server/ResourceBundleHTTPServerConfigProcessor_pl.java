//$Header: /as2/de/mendelson/util/httpconfig/server/ResourceBundleHTTPServerConfigProcessor_pl.java 1     24/09/25 10:45 Heller $
package de.mendelson.util.httpconfig.server;

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
public class ResourceBundleHTTPServerConfigProcessor_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"external.ip", "Zewnętrzny adres IP: {0} / {1}"},
		{"external.ip.error", "Zewnętrzny adres IP: -Nie można określić-"},
		{"http.deployedwars", "Aktualnie dostępne WAR w serwerze HTTP (funkcjonalność serwletu):"},
		{"http.receipturls", "Kompletne adresy URL bieżącej konfiguracji"},
		{"http.server.config.clientauthentication", "Serwer wymaga uwierzytelnienia klienta TLS: {0}"},
		{"http.server.config.listener", "Port {0} ({1}) jest powiązany z kartą sieciową {2}."},
		{"http.server.config.tlskey.info", "Klucz TLS:\n	Alias [{0}]\n	Odcisk palca SHA1 [{1}]\n	Numer seryjny [{2}]\n	Ważny do [{3}]\n"},
		{"http.server.config.tlskey.none", "Klucz TLS: Nie zdefiniowano klucza TLS, przychodzące połączenia TLS nie są możliwe!"},
		{"http.serverstateurl", "Wyświetlanie stanu serwera:"},
		{"info.cipher", "Następujące szyfry są obsługiwane przez bazowy serwer HTTP po stronie wejściowej.\nTo, które z nich są obsługiwane, zależy od używanej maszyny wirtualnej Java (obecnie {1}).\nPoszczególne szyfry można wyłączyć w pliku konfiguracyjnym\nPlik konfiguracyjny \"{0}\"."},
		{"info.cipher.howtochange", "Aby wyłączyć określone szyfry dla połączeń przychodzących, należy edytować plik konfiguracyjny wbudowanego serwera HTTP ({0}) za pomocą edytora tekstu. Należy wyszukać ciąg znaków <Set name=\"ExcludeCipherSuites\">, dodać szyfr, który ma zostać wykluczony i ponownie uruchomić program."},
		{"info.protocols", "Następujące protokoły są obsługiwane przez bazowy serwer HTTP dla połączeń przychodzących.\nTo, które z nich są obsługiwane, zależy od używanej maszyny wirtualnej Java (obecnie {1}). Używany dostawca zabezpieczeń TLS to {2}.\nPoszczególne protokoły można wyłączyć w pliku konfiguracyjnym\nPlik konfiguracyjny \"{0}\"."},
		{"info.protocols.howtochange", "Aby wyłączyć określone protokoły po stronie wejściowej, należy edytować plik konfiguracyjny wbudowanego serwera HTTP ({0}) za pomocą edytora tekstu. Należy wyszukać ciąg znaków <Set name=\"ExcludeProtocols\">, dodać protokół, który ma zostać wykluczony i ponownie uruchomić program."},
		{"webapp._unknown", "Nieznany serwlet"},
		{"webapp.as2-sample.war", "Przykłady API mendelson AS2"},
		{"webapp.as2.war", "mendelson AS2 odbierający serwlet"},
		{"webapp.as2api.war", "mendelson AS2 REST API"},
		{"webapp.as4-sample.war", "Przykłady API mendelson AS4"},
		{"webapp.as4.war", "mendelson AS4 odbierający serwlet"},
		{"webapp.as4api.war", "mendelson AS4 REST API"},
		{"webapp.oftp2api.war", "mendelson OFTP2 REST API"},
		{"webapp.webas2.war", "mendelson AS2 Server Web Monitoring"},
	};
}
