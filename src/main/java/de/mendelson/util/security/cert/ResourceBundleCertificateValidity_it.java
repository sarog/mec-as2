//$Header: /as4/de/mendelson/util/security/cert/ResourceBundleCertificateValidity_it.java 1     14/01/26 16:23 Heller $
package de.mendelson.util.security.cert;

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
public class ResourceBundleCertificateValidity_it extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"state.1", "Scaduto"},
		{"state.1024", "(CRL) Risposta scaduta"},
		{"state.1048576", "(CRL) Problema non specifico"},
		{"state.131072", "(CRL) L''URL non è stato estratto."},
		{"state.16384", "(CRL) Formato non valido"},
		{"state.2048", "(CRL) Firma non valida"},
		{"state.256", "(CRL) Bloccato dalla CA (revocato)"},
		{"state.262144", "(CRL) Download fallito"},
		{"state.32768", "(CRL) Certificato non leggibile"},
		{"state.4", "Gerarchia di certificazione errata"},
		{"state.4096", "(CRL) Estensione mancante"},
		{"state.4194304", "Chiave di prova pubblica di Mendelson - non utilizzare in operazioni produttive"},
		{"state.512", "(CRL) URL non accessibile"},
		{"state.524288", "(CRL) Schema URL non supportato"},
		{"state.8192", "(CRL) URL non corretto"},
		{"state.8388608", "(CRL) Emittente mancante - importare certificato di livello superiore"},
	};
}
