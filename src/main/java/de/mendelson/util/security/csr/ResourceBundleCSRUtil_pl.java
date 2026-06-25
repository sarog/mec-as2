//$Header: /as2/de/mendelson/util/security/csr/ResourceBundleCSRUtil_pl.java 1     24/09/25 10:47 Heller $
package de.mendelson.util.security.csr;

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
public class ResourceBundleCSRUtil_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"missing.cert.in.trustchain", "Do tej operacji brakuje certyfikatów łańcucha uwierzytelniania w systemie (certyfikat główny i pośredni).\nOtrzymasz te certyfikaty od swojego urzędu certyfikacji.\nNajpierw zaimportuj certyfikat z danymi klucza (wystawca)\n{0}."},
		{"no.certificates.in.reply", "Klucz nie mógł zostać załatany, w odpowiedzi urzędu certyfikacji nie znaleziono żadnych certyfikatów."},
		{"response.chain.incomplete", "Łańcuch zaufania odpowiedzi CSR jest niekompletny."},
		{"response.public.key.does.not.match", "Ta odpowiedź z urzędu certyfikacji nie pasuje do tego klucza."},
		{"response.verification.failed", "Nie można zweryfikować łańcucha zaufania odpowiedzi CSR: {0}"},
		{"verification.failed", "Weryfikacja utworzonego żądania podpisania certyfikatu (CSR) nie powiodła się."},
	};
}
