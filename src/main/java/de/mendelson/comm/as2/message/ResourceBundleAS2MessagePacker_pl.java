//$Header: /as2/de/mendelson/comm/as2/message/ResourceBundleAS2MessagePacker_pl.java 1     24/09/25 10:35 Heller $
package de.mendelson.comm.as2.message;

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
public class ResourceBundleAS2MessagePacker_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"mdn.created", "MDN wychodzący utworzony dla komunikatu AS2 \"{0}\", status ustawiony na [{1}]."},
		{"mdn.creation.start", "Utwórz wychodzący MDN, ustaw Id wiadomości na \"{0}\"."},
		{"mdn.details", "Szczegóły wychodzącego MDN: {0}"},
		{"mdn.notsigned", "Wychodzący MDN nie został podpisany."},
		{"mdn.signed", "Wychodzący MDN został podpisany algorytmem \"{0}\", alias klucza to \"{1}\" lokalnej stacji \"{2}\"."},
		{"message.compressed", "Wychodzące dane użytkownika zostały skompresowane z {0} do {1}."},
		{"message.compressed.unknownratio", "Wychodzące dane użytkownika zostały skompresowane."},
		{"message.creation.error", "Nie można utworzyć wiadomości o identyfikatorze \"{0}\": {1}. Jest to problem, który wystąpił już podczas tworzenia struktury wiadomości wychodzących w systemie użytkownika - nie ma on nic wspólnego z systemem partnera i nie podjęto żadnej próby nawiązania połączenia z partnerem."},
		{"message.creation.start", "Utwórz wychodzącą wiadomość AS2, ustaw Id wiadomości na \"{0}\"."},
		{"message.encrypted", "Wiadomość wychodząca została zaszyfrowana algorytmem {1}, użyto certyfikatu o aliasie \"{0}\" zdalnego partnera \"{2}\"."},
		{"message.notencrypted", "Wiadomość wychodząca nie została zaszyfrowana."},
		{"message.notsigned", "Wiadomość wychodząca nie została podpisana cyfrowo."},
		{"message.signed", "Wiadomość wychodząca została podpisana cyfrowo algorytmem \"{1}\", użyto klucza o aliasie \"{0}\" lokalnej stacji \"{2}\"."},
		{"signature.no.aipa", "Proces podpisywania nie używa atrybutu Algorithm Identifier Protection w podpisie (ustawionego w konfiguracji) - jest to niebezpieczne!"},
	};
}
