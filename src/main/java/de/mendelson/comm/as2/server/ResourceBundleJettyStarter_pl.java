//$Header: /as2/de/mendelson/comm/as2/server/ResourceBundleJettyStarter_pl.java 1     24/09/25 10:39 Heller $
package de.mendelson.comm.as2.server;

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
public class ResourceBundleJettyStarter_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"deployment.failed", "[{0}] NIE zostało dostarczone: {1}"},
		{"deployment.success", "[{0}] został pomyślnie wdrożony"},
		{"httpserver.running", "Uruchomiony zintegrowany serwer HTTP ({0})"},
		{"httpserver.startup.problem", "Problem na początku ({0})"},
		{"httpserver.stopped", "Zintegrowany serwer HTTP zatrzymany"},
		{"httpserver.willstart", "Uruchomienie zintegrowanego serwera HTTP"},
		{"listener.started", "Oczekiwanie na połączenia przychodzące {0}"},
		{"module.name", "[JETTY]"},
		{"tls.keystore.reloaded", "Zmiany zostały zarejestrowane w magazynie kluczy TLS, a dane magazynu kluczy serwera HTTP zostały zaktualizowane."},
		{"userconfiguration.readerror", "Problem z odczytaniem konfiguracji użytkownika {0}: {1} ... Zignorowanie konfiguracji użytkownika i uruchomienie serwera WWW przy użyciu zdefiniowanych wartości domyślnych"},
		{"userconfiguration.reading", "Odczyt konfiguracji zdefiniowanej przez użytkownika z {0}."},
		{"userconfiguration.setvar", "Ustawienie wartości zdefiniowanej przez użytkownika [{0}] na [{1}]."},
	};
}
