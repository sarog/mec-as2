//$Header: /as2/de/mendelson/util/security/ResourceBundleKeyStoreUtil_pl.java 1     24/09/25 10:46 Heller $
package de.mendelson.util.security;

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
public class ResourceBundleKeyStoreUtil_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"alias.exist", "Wpis z aliasem \"{0}\" już istnieje w podstawowym magazynie kluczy."},
		{"alias.rename.new.equals.old", "Zmiana nazwy wpisu magazynu kluczy: Nowy i stary alias są identyczne."},
		{"privatekey.notfound", "Magazyn kluczy nie zawiera klucza prywatnego z aliasem \"{0}\"."},
		{"readerror.invalidcert", "To nie jest prawidłowy certyfikat lub używa nieobsługiwanego kodowania."},
		{"readerror.zipcert", "To nie jest ważny certyfikat, ale archiwum zip."},
		{"ssh2.algorithmn.not.supported", "Kodowanie SSH2 nie jest obsługiwane dla kluczy algorytmu \"{0}\". Obsługiwane algorytmy to: DSA, RSA"},
	};
}
