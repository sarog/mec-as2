//$Header: /as2/de/mendelson/util/modulelock/ResourceBundleModuleLock_pl.java 1     24/09/25 10:45 Heller $
package de.mendelson.util.modulelock;

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
public class ResourceBundleModuleLock_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"ENC/SIGN keystore", "Zarządzanie certyfikatami (szyfrowanie/podpis)"},
		{"Partner management", "Zarządzanie partnerami"},
		{"Server settings", "Ustawienia serwera"},
		{"TLS keystore", "Zarządzanie certyfikatami (TLS)"},
		{"configuration.changed.otherclient", "Inny klient mógł wprowadzić zmiany w module {0}.\nOtwórz ponownie ten interfejs konfiguracyjny, aby ponownie załadować bieżącą konfigurację."},
		{"configuration.locked.otherclient", "Moduł {0} jest otwarty wyłącznie przez innego klienta,\nObecnie nie można wprowadzać żadnych zmian.\nSzczegóły dotyczące innego klienta:\nIP: {1}\nUżytkownik: {2}\nIdentyfikator procesu: {3}"},
		{"modifications.notallowed.message", "Zmiany nie są obecnie możliwe"},
	};
}
