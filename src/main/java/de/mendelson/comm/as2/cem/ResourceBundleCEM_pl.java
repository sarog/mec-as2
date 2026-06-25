//$Header: /as2/de/mendelson/comm/as2/cem/ResourceBundleCEM_pl.java 2     22/10/25 11:27 Heller $
package de.mendelson.comm.as2.cem;

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
public class ResourceBundleCEM_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"ENC_SIGN.cert.already.imported", "Przesłany certyfikat CEM już istnieje w systemie [enc/sign] (alias {0}), import został pominięty."},
		{"ENC_SIGN.cert.imported.success", "Przesłany certyfikat CEM został pomyślnie zaimportowany do systemu [enc/sign] (alias {0})."},
		{"TLS.cert.already.imported", "Przesłany certyfikat CEM już istnieje w systemie [TLS] (alias {0}), import został pominięty."},
		{"TLS.cert.imported.success", "Przesłany certyfikat CEM został pomyślnie zaimportowany do systemu [TLS] (alias {0})."},
		{"category.1", "Szyfrowanie"},
		{"category.2", "Podpis"},
		{"category.3", "TLS"},
		{"cem.created.request", "Żądanie CEM zostało wygenerowane dla relacji \"{0}\"-\"{1}\". Certyfikat z parametrami issuerDN \"{2}\" i numerem seryjnym \"{3}\" został osadzony. Zdefiniowane użycie to {4}."},
		{"cem.response.prepared", "Komunikat odpowiedzi CEM utworzony dla żądania {0}"},
		{"cem.response.relatedrequest.found", "Komunikat CEM odnosi się do żądania \"{0}\""},
		{"cem.structure.info", "Liczba otrzymanych żądań zaufania w strukturze CEM: {0}"},
		{"cem.validated.schema", "Przychodzący komunikat CEM został pomyślnie zweryfikowany."},
		{"cemtype.request", "Komunikat CEM jest typu \"Żądanie certyfikatu\""},
		{"cemtype.response", "Komunikat CEM jest typu \"Odpowiedź na certyfikat\""},
		{"state.1", "Brak odpowiedzi od {0}"},
		{"state.2", "Odrzucone przez {0}"},
		{"state.3", "Zaakceptowany przez {0}"},
		{"state.99", "Proces anulowany"},
		{"state.999", "Błędy przetwarzania"},
		{"transmitted.certificate.info", "Przesłany certyfikat ma parametry IssuerDN=\"{0}\" i numer seryjny \"{1}\"."},
		{"trustrequest.accepted", "Odpowiedź na otrzymane żądanie zaufania została ustawiona na status \"Zaakceptowany\"."},
		{"trustrequest.certificates.found", "Liczba przeniesionych certyfikatów: {0}."},
		{"trustrequest.rejected", "Odpowiedź na otrzymane żądanie zaufania miała status \"Odrzucone\"."},
		{"trustrequest.working.on", "Przetwórz żądanie zaufania {0}."},
	};
}
