//$Header: /as2/de/mendelson/util/security/cert/gui/keygeneration/ResourceBundleDialogSubjectAlternativeNames_pl.java 1     24/09/25 10:46 He $
package de.mendelson.util.security.cert.gui.keygeneration;

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
public class ResourceBundleDialogSubjectAlternativeNames_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Rozbiórka"},
		{"button.ok", "Ok"},
		{"header.name", "Typ"},
		{"header.value", "Wartość"},
		{"info", "Za pomocą tego okna dialogowego można zarządzać alternatywnymi nazwami wnioskodawców dla procesu generowania klucza (alternatywna nazwa podmiotu). Wartości te są rozszerzeniem certyfikatu x.509. Jeśli partner to obsługuje, można tu na przykład wprowadzić dodatkowe domeny dla klucza. W OFTP2, w zależności od partnera, może być konieczne wypełnienie niektórych pól danymi identyfikacyjnymi, na przykład identyfikatorem Odette Id systemu jako adresem URL w formacie \"oftp://OdetteId\" i ponownie domeną w polu nazwy DNS."},
		{"label.add", "Dodaj"},
		{"label.del", "Usuń"},
		{"title", "Zarządzanie alternatywnymi nazwami wnioskodawców"},
	};
}
