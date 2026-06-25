//$Header: /as2/de/mendelson/util/clientserver/connectiontest/gui/ResourceBundleDialogConnectionTestResult_pl.java 1     24/09/25 10:44 Helle $
package de.mendelson.util.clientserver.connectiontest.gui;

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
public class ResourceBundleDialogConnectionTestResult_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"AVAILABLE", "[PREZENT]"},
		{"FAILED", "[ERROR]"},
		{"NOT_AVAILABLE", "[NIEDOSTĘPNE]."},
		{"OK", "[SUKCES]"},
		{"button.close", "Zamknij"},
		{"button.viewcert", "<HTML>Importuj certyfikat(y)</HTML>"},
		{"description.1", "System wykonał test połączenia z adresem {0}, port {1}. Poniższy wynik pokazuje, czy połączenie się powiodło i czy pod tym adresem działa serwer OFTP2. Jeśli użyto połączenia TLS i zakończyło się ono powodzeniem, można pobrać certyfikaty partnera i zaimportować je do magazynu kluczy."},
		{"description.2", "System wykonał test połączenia z adresem {0}, port {1}. Poniższy wynik pokazuje, czy nawiązanie połączenia powiodło się i czy pod tym adresem działa serwer HTTP. Nawet jeśli test się powiedzie, nie ma pewności, czy jest to normalny serwer HTTP, czy serwer AS2. Jeśli należy użyć połączenia TLS (HTTPS) i było to możliwe, można pobrać certyfikaty partnera i zaimportować je do magazynu kluczy."},
		{"description.3", "System wykonał test połączenia z adresem {0}, port {1}. Poniższy wynik pokazuje, czy nawiązanie połączenia powiodło się i czy pod tym adresem działa serwer HTTP. Nawet jeśli test się powiedzie, nie ma pewności, czy jest to normalny serwer HTTP, czy serwer AS4. Jeśli należy użyć połączenia TLS (HTTPS) i było to możliwe z powodzeniem, można pobrać certyfikaty partnera i zaimportować je do magazynu kluczy."},
		{"header.plain", "{0} [Niezabezpieczone połączenie]"},
		{"header.ssl", "{0} [połączenie TLS]"},
		{"label.certificates.available.local", "Certyfikaty partnerów (TLS) są dostępne w systemie"},
		{"label.connection.established", "Ustanowiono proste połączenie IP"},
		{"label.running.oftpservice", "Znaleziono działającą usługę OFTP"},
		{"no.certificate.plain", "Niedostępne (połączenie niezabezpieczone)"},
		{"title", "Wynik testu połączenia"},
		{"used.cipher", "Do testu użyto następującego algorytmu szyfrowania: \"{0}\""},
	};
}
