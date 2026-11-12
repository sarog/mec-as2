//$Header: /as2/de/mendelson/comm/as2/timing/ResourceBundleMessageDeleteController_pl.java 1     24/09/25 10:39 Heller $
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
public class ResourceBundleMessageDeleteController_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"autodelete", "{0}: Ten komunikat jest starszy niż {1} {2} i został automatycznie usunięty przez proces konserwacji systemu."},
		{"delete.failed", "USUNIĘCIE NIE POWIODŁO SIĘ"},
		{"delete.ok", "POMYŚLNE USUNIĘCIE"},
		{"delete.skipped", "USUŃ POMINIĘTE"},
		{"transaction.delete.setting.olderthan", "Proces jest skonfigurowany do usuwania transakcji z zielonym statusem, które są starsze niż {0}."},
		{"transaction.deleted.system", "Transakcje usunięte przez proces konserwacji systemu"},
		{"transaction.deleted.transactiondate", "Data transakcji: {0}"},
		{"transaction.deleted.user", "{0} Transakcje anulowane przez użytkownika"},
	};
}
