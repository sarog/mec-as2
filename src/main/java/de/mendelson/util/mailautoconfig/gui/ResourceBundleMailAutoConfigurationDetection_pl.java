//$Header: /as2/de/mendelson/util/mailautoconfig/gui/ResourceBundleMailAutoConfigurationDetection_pl.java 1     24/09/25 10:45 Heller $
package de.mendelson.util.mailautoconfig.gui;

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
public class ResourceBundleMailAutoConfigurationDetection_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Rozbiórka"},
		{"button.ok", "Użyj wybranej konfiguracji"},
		{"button.start.detection", "Dowiedz się"},
		{"detection.failed.text", "System nie mógł znaleźć ustawień serwera pocztowego dla adresu pocztowego {0}."},
		{"detection.failed.title", "Rozpoznanie nie powiodło się"},
		{"email.invalid.text", "Kontrola nie została przeprowadzona - adres pocztowy {0} jest nieprawidłowy."},
		{"email.invalid.title", "Nieprawidłowy adres"},
		{"header.host", "Gospodarz"},
		{"header.port", "Port"},
		{"header.security", "Bezpieczeństwo"},
		{"header.service", "Usługa"},
		{"label.detectedprovider", "<HTML>Uznanym dostawcą poczty jest <strong>{0}</strong>.</HTML>"},
		{"label.email.hint", "Prawidłowy adres e-mail w celu uzyskania informacji o ustawieniach serwera"},
		{"label.mailaddress", "Adres pocztowy"},
		{"progress.detection", "Sprawdź ustawienia serwera poczty"},
		{"security.0", "Brak"},
		{"security.1", "StartTLS"},
		{"security.2", "TLS"},
		{"title", "Sprawdź ustawienia serwera poczty"},
	};
}
