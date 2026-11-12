//$Header: /as2/de/mendelson/comm/as2/timing/ResourceBundlePartnerTLSCertificateChangedController_pl.java 1     24/09/25 10:39 Heller $
package de.mendelson.comm.as2.timing;

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
public class ResourceBundlePartnerTLSCertificateChangedController_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"autoimport.tls.check.started", "Automatyczny import zmodyfikowanych certyfikatów TLS partnerów został aktywowany."},
		{"autoimport.tls.check.stopped", "Automatyczny import zmodyfikowanych certyfikatów TLS partnerów został wyłączony."},
		{"import.failed", "Certyfikat TLS dla partnera {0} nie mógł zostać zaimportowany automatycznie: {1}"},
		{"import.success", "Certyfikat TLS \"{0}\" dla partnera [{1}] został zaimportowany automatycznie."},
		{"import.success.event.body", "System jest skonfigurowany w taki sposób, że regularnie sprawdza partnerów połączonych przez TLS, aby sprawdzić, czy zmienili swój certyfikat TLS. Jeśli tak jest, a certyfikat TLS nie istnieje w lokalnym menedżerze certyfikatów TLS, jest on automatycznie importowany.\nSystem znalazł nowy certyfikat dla partnera \"{0}\" pod adresem URL \"{1}\" i pomyślnie zaimportował go do menedżera certyfikatów TLS z aliasem \"{2}\"."},
		{"import.success.event.header", "Automatyczny import certyfikatu TLS"},
		{"module.name", "Egzamin na certyfikat TLS"},
	};
}
