//$Header: /as2/de/mendelson/util/clientserver/connectiontest/ResourceBundleConnectionTest_pl.java 3     16/02/26 14:07 Heller $
package de.mendelson.util.clientserver.connectiontest;

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
* @version $Revision: 3 $
*/
public class ResourceBundleConnectionTest_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"certificate.ca", "Certyfikat CA"},
		{"certificate.does.exist.local", "Ten certyfikat już istnieje w lokalnym magazynie kluczy TLS, alias to \"{0}\"."},
		{"certificate.does.not.exist.local", "Ten certyfikat nie istnieje jeszcze w lokalnym magazynie kluczy TLS - zaimportuj go."},
		{"certificate.enduser", "Certyfikat użytkownika końcowego"},
		{"certificate.selfsigned", "Podpisano własnoręcznie"},
		{"certificates.found", "{0} Certyfikaty zostały znalezione i pobrane"},
		{"certificates.found.details", "Certyfikat [{0}/{1}]: {2}"},
		{"check.for.service.oftp2", "Sprawdź, czy usługa OFTP2 jest uruchomiona..."},
		{"connection.problem", "{0} nie można osiągnąć - może to być problem z infrastrukturą lub wprowadzono nieprawidłowe dane."},
		{"connection.success", "Połączenie z {0} zostało pomyślnie nawiązane"},
		{"exception.occured", "Wystąpił problem podczas testu połączenia: [{0}] {1}"},
		{"exception.occured.oftpservice", "Nie można zidentyfikować działającego serwera OFTP2 pod podanym adresem i portem. Może to być problem tymczasowy, na przykład zdalny serwer OFTP2 nie jest obecnie uruchomiony, ale dane adresowe są prawidłowe. Wystąpił następujący problem: [{0}] {1}"},
		{"info.protocols", "Klient umożliwia negocjację za pośrednictwem następujących protokołów TLS: {0}"},
		{"info.securityprovider", "Używany dostawca zabezpieczeń TLS: {0}"},
		{"local.station", "Stacja lokalna"},
		{"protocol.information", "Użyty protokół został zidentyfikowany jako \"{0}\""},
		{"remote.service.identification", "Identyfikacja usługi zdalnego serwera: \"{0}\""},
		{"requesting.certificates", "Certyfikaty zdalnego serwera są pobierane"},
		{"result.exception", "Podczas testu wystąpił następujący błąd: {0}."},
		{"service.found.failure", "Błąd: Nie znaleziono uruchomionej usługi OFTP pod adresem {0}."},
		{"service.found.success", "Sukces: Znaleziono działającą usługę OFTP pod adresem {0}."},
		{"sni.extension.set", "Nazwa hosta dla rozszerzenia TLS SNI została ustawiona na \"{0}\"."},
		{"tag", "Test połączenia z {0}"},
		{"test.connection.direct", "Używane jest bezpośrednie połączenie IP"},
		{"test.connection.proxy.auth", "Połączenie wykorzystuje proxy {0} z uwierzytelnianiem (użytkownik \"{1}\")."},
		{"test.connection.proxy.noauth", "Połączenie używa proxy {0} bez uwierzytelniania"},
		{"test.start.plain", "Rozpocznij sprawdzanie połączenia dla {0}, PLAIN..."},
		{"test.start.ssl", "Rozpocznij sprawdzanie połączenia z {0}, TLS. Należy pamiętać, że ten test ufa każdemu certyfikatowi serwera - więc nawet jeśli ten test się powiedzie, nie oznacza to, że magazyn kluczy TLS jest poprawnie skonfigurowany."},
		{"timeout.set", "Ustaw limit czasu na {0} ms"},
		{"wrong.protocol", "Znaleziony protokół to \"{0}\", nie jest to bezpieczne połączenie. Próbowano połączyć się z tym partnerem przy użyciu jednego z protokołów [{1}]. Jednak partner nie oferuje żadnego z tych protokołów zabezpieczeń linii na podanym porcie i adresie."},
		{"wrong.protocol.hint", "Albo partner oczekuje niezabezpieczonego połączenia, albo występuje problem z protokołem, albo wymaga uwierzytelnienia klienta"}
	};
}
