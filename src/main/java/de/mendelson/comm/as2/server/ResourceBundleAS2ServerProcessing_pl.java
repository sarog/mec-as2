//$Header: /as2/de/mendelson/comm/as2/server/ResourceBundleAS2ServerProcessing_pl.java 1     24/09/25 10:39 Heller $
package de.mendelson.comm.as2.server;

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
public class ResourceBundleAS2ServerProcessing_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"event.download.not.allowed.body", "Klient próbował pobrać plik, ale zostało to uniemożliwione.\nŚcieżka żądania pobrania: {0}\nDozwolone katalogi: {1}\nUżytkownik: {2}\nHost: {3}"},
		{"event.download.not.allowed.subject", "Pobieranie niedozwolone"},
		{"info.mdn.inboundfiles", "Nie można było określić komunikatu AS2 dla przychodzącego MDN.\n[Przychodzący MDN (dane): {0}]\n[Przychodzący MDN (nagłówek): {1}]"},
		{"invalid.request.from", "Otrzymano nieprawidłowe żądanie. Nie zostanie ono przetworzone, ponieważ nie ma nagłówka as2-from."},
		{"invalid.request.messageid", "Otrzymano nieprawidłowe żądanie. Nie zostanie ono przetworzone, ponieważ nie ma nagłówka message-id."},
		{"invalid.request.to", "Otrzymano nieprawidłowe żądanie. Nie zostanie ono przetworzone, ponieważ nie ma nagłówka as2-to."},
		{"local.station", "Stacja lokalna"},
		{"message.resend.newtransaction", "Ta transakcja jest ponownym wysłaniem transakcji [{0}]."},
		{"message.resend.oldtransaction", "Ta transakcja została ponownie wysłana ręcznie z nowym numerem transakcji [{0}]."},
		{"message.resend.title", "Ręczne wysyłanie danych w nowej transakcji"},
		{"send.failed", "Wysyłka nie powiodła się"},
		{"server.shutdown", "Użytkownik {0} wyłącza serwer."},
		{"sync.mdn.sent", "Synchroniczny MDN wysłany w odpowiedzi na {0}."},
		{"unable.to.process", "Błąd podczas przetwarzania na serwerze: {0}"},
	};
}
