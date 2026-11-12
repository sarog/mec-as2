//$Header: /as2/de/mendelson/comm/as2/sendorder/ResourceBundleSendOrderReceiver_pl.java 1     24/09/25 10:38 Heller $
package de.mendelson.comm.as2.sendorder;

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
public class ResourceBundleSendOrderReceiver_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"as2.send.disabled", "** Liczba równoległych połączeń wychodzących jest ustawiona na 0 - system nie będzie wysyłał wiadomości MDN ani AS2. Zmień to ustawienie w ustawieniach serwera, jeśli chcesz wysyłać **."},
		{"async.mdn.wait", "Oczekiwanie na asynchroniczny MDN do {0}."},
		{"max.retry.reached", "Osiągnięto maksymalną liczbę ponownych prób ({0}), transakcja zostaje zakończona."},
		{"outbound.connection.prepare.mdn", "Przygotuj wychodzące połączenie MDN do \"{0}\", aktywne połączenia: {1}/{2}."},
		{"outbound.connection.prepare.message", "Przygotuj wychodzące połączenie wiadomości AS2 do \"{0}\", aktywne połączenia: {1}/{2}."},
		{"retry", "Spróbuj nowej transmisji po {0}s, powtórz {1}/{2}."},
		{"send.connectionsstillopen", "Zmniejszono liczbę połączeń wychodzących do {0}, ale nadal istnieje {1} połączeń wychodzących."},
	};
}
