//$Header: /oftp2/de/mendelson/util/systemevents/ResourceBundleSystemEventManager_pl.java 2     20/02/26 16:54 Heller $
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
public class ResourceBundleSystemEventManager_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"error.createdir.body", "Wystąpił problem podczas tworzenia katalogu: {0}\nProblem: {1}"},
		{"error.createdir.subject", "Generowanie katalogów"},
		{"error.in.systemevent.registration", "Problem systemowy nie mógł zostać zarejestrowany w menedżerze zdarzeń systemowych: {0}"},
		{"label.body.clientip", "Adres IP: {0}"},
                {"label.body.sessionid", "Session id: [{0}]"},
		{"label.body.clientos", "System operacyjny klienta: {0}"},
		{"label.body.clientversion", "Wersja klienta: {0}"},
		{"label.body.details", "Szczegóły: {0}"},
		{"label.body.processid", "Numer procesu w systemie operacyjnym klienta: {0}"},
		{"label.body.tlsciphersuite", "Szyfr TLS: {0}"},
		{"label.body.tlsprotocol", "Protokół TLS: {0}"},
		{"label.error.clientserver", "Problem w połączeniu klient-serwer"},
		{"label.subject.login.failed", "Logowanie użytkownika nie powiodło się [{0}]."},
		{"label.subject.login.success", "Logowanie użytkownika powiodło się [{0}]."},
		{"label.subject.logoff", "Wylogowanie użytkownika [{0}]"},
		{"module.name", "[MENEDŻER ZDARZEŃ SYSTEMOWYCH]"},
	};
}
