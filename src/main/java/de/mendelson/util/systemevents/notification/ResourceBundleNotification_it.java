//$Header: /oftp2/de/mendelson/util/systemevents/notification/ResourceBundleNotification_it.java 4     13/06/25 15:23 Heller $
package de.mendelson.util.systemevents.notification;

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
* @version $Revision: 4 $
*/
public class ResourceBundleNotification_it extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"authorization.credentials", "Utente/password"},
		{"authorization.none", "NESSUNO"},
		{"authorization.oauth2", "OAUTH2"},
		{"authorization.oauth2.authorizationcode", "Codice di autorizzazione"},
		{"authorization.oauth2.clientcredentials", "Credenziali del cliente"},
		{"do.not.reply", "Si prega di non rispondere a questo messaggio."},
		{"misc.message.send", "È stata inviata un''e-mail di notifica a {0} ({1}-{2}-{3})."},
		{"misc.message.send.failed", "L''invio di un''e-mail di notifica a {0} non è riuscito."},
		{"misc.message.summary.failed", "L''invio di un''e-mail di notifica di riepilogo a {0} non è riuscito."},
		{"misc.message.summary.send", "È stata inviata un''e-mail di notifica di riepilogo a {0}"},
		{"module.name", "[NOTIFICA VIA E-MAIL]"},
		{"notification.about.event", "Questa notifica si riferisce all''evento di sistema di {0}.\nUrgenza: {1}\nFonte: {2}\nTipo: {3}\nId: {4}"},
		{"notification.summary", "Riepilogo degli eventi del sistema {0}"},
		{"notification.summary.info", "Questo messaggio di riepilogo viene visualizzato perché è stato definito un numero limitato di notifiche per unità di tempo.\ndi notifiche per unità di tempo.\nPer ottenere dettagli sui singoli eventi, avviare il client e navigare in \"Eventi del file system\".\nil client e navigare in \"Eventi del file system\".\nInserire il numero univoco dell''evento nella maschera di ricerca.\ndell''evento nella maschera di ricerca."},
		{"test.message.debug", "\nL''invio del messaggio non è riuscito.\n"},
		{"test.message.send", "È stato inviato un messaggio di prova a {0}."},
	};
}
