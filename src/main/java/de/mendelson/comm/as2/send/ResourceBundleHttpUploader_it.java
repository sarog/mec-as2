//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleHttpUploader_it.java 10    18/06/25 12:21 Heller $
package de.mendelson.comm.as2.send;

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
* @version $Revision: 10 $
*/
public class ResourceBundleHttpUploader_it extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"answer.no.sync.empty", "La conferma di ricezione sincrona ricevuta è vuota. Probabilmente si è verificato un problema nell''elaborazione dei messaggi AS2 da parte del vostro partner."},
		{"answer.no.sync.mdn", "La risposta sincrona ricevuta non è nel formato corretto. Poiché i problemi di struttura di MDN sono insoliti, potrebbe essere che questa non sia una risposta del sistema AS2 a cui si stava cercando di rivolgersi, ma forse la risposta di un proxy o la risposta di un sito web standard? Mancano i seguenti valori di intestazione HTTP: [{0}].\nI dati ricevuti iniziano con le seguenti strutture:\n{1}"},
		{"connected.to", "Connesso a {0}, attende un MDN e mantiene aperta la connessione fino a {1}."},
		{"connection.shut.down", "La connessione in uscita verso {0} è stata chiusa, è stata aperta per {1}s."},
		{"connection.tls.info", "Connessione TLS in uscita stabilita [{0}, {1}]"},
		{"error.http502", "Problema di connessione, non è stato possibile trasferire i dati. (HTTP 502 - GATEWAY ERRATO)"},
		{"error.http503", "Problema di connessione, non è stato possibile trasferire i dati. (HTTP 503 - SERVIZIO NON DISPONIBILE)"},
		{"error.http504", "Problema di connessione, non è stato possibile trasferire i dati. (HTTP 504 - TIMEOUT DEL GATEWAY)"},
		{"error.httpupload", "Trasmissione fallita, il server AS2 remoto segnala \"{0}\"."},
		{"error.noconnection", "Problema di connessione, non è stato possibile trasferire i dati."},
		{"hint.ConnectTimeoutException", "Nota:\nIn genere si tratta di un problema di infrastruttura che non ha nulla a che vedere con il protocollo AS2. Non è possibile stabilire una connessione in uscita con il partner.\nPer risolvere il problema, verificare quanto segue:\n*Si dispone di una connessione Internet attiva?\n*Controllare se è stato inserito l''URL di ricezione corretto del partner nell''amministrazione del partner.\n*Contattare il partner, forse il suo sistema AS2 non è disponibile?"},
		{"hint.SSLException", "Nota:\nIn genere si tratta di un problema di negoziazione a livello di protocollo. Il partner ha rifiutato la connessione.\nO il partner si aspetta una connessione sicura (HTTPS) e voi volevate stabilire una connessione non sicura o viceversa.\nÈ anche possibile che il partner richieda una versione TLS diversa o un algoritmo di crittografia diverso da quello offerto."},
		{"hint.SSLPeerUnverifiedException", "Nota:\nIl problema si è verificato durante l''handshake TLS. Il sistema non è stato quindi in grado di stabilire una connessione sicura con il vostro partner; il problema non ha nulla a che fare con il protocollo AS2.\nVerificare quanto segue:\n*Avete importato tutti i certificati del vostro partner nel vostro keystore TLS (per TLS, compresi i certificati intermedi/root)?\n*Il vostro partner ha importato tutti i certificati da voi (per TLS, inclusi i certificati intermedi/root)?"},
		{"hint.httpcode.signals.problem", "Nota:\nÈ stata stabilita una connessione all''host del partner, dove è in esecuzione un server Web.\nIl server remoto segnala che qualcosa non va nel percorso o nella porta della richiesta e restituisce il codice HTTP {0}.\nPer ulteriori informazioni su questo codice HTTP, utilizzare un motore di ricerca su Internet."},
		{"httpheader.deleted", "L''intestazione HTTP \"{0}\" è stata eliminata a causa delle impostazioni personalizzate dell''intestazione HTTP."},
		{"httpheader.replaced", "Il valore dell''intestazione HTTP \"{0}\" è stato sostituito dal valore definito dall''utente \"{1}\"."},
		{"httpheader.set", "L''intestazione HTTP \"{0}\" è stata impostata sul valore definito dall''utente \"{1}\"."},
		{"returncode.accepted", "Messaggio inviato con successo (HTTP {0}); {1} trasmesso in {2} [{3}]."},
		{"returncode.ok", "Messaggio inviato con successo (HTTP {0}); {1} inviato in {2} [{3}]."},
		{"sending.cem.async", "Invia un messaggio CEM a {0}, aspetta un MDN asincrono per la conferma di ricezione su {1}."},
		{"sending.cem.sync", "Inviare il messaggio CEM a {0}, attendere la conferma di ricezione da parte di MDN sincrono."},
		{"sending.mdn.async", "Invia una conferma di ricezione asincrona (MDN) a {0}."},
		{"sending.msg.async", "Invia un messaggio AS2 a {0}, aspetta un MDN asincrono per la conferma di ricezione su {1}."},
		{"sending.msg.sync", "Invia un messaggio AS2 a {0}, si aspetta un MDN sincrono per la conferma di ricezione."},
		{"strict.hostname.check", "Per la connessione TLS in uscita, viene effettuato un controllo rigoroso del nome host in relazione al certificato del server."},
		{"strict.hostname.check.skipped.selfsigned", "TLS: il controllo del nome host rigoroso è stato saltato - il server remoto utilizza un certificato autofirmato."},
		{"trust.all.server.certificates", "La connessione TLS in uscita si affiderà a tutti i certificati del server remoto se i certificati root e intermedi sono disponibili."},
		{"using.proxy", "Utilizzare il proxy {0}:{1}."},
		{"using.proxy.auth", "Utilizzare il proxy {0}:{1} (autenticazione come {2})."},
                {"httpheader.added.basicauth", "L''intestazione HTTP per l''autenticazione di base è stata aggiunta alla richiesta di invio." },
	};
}
