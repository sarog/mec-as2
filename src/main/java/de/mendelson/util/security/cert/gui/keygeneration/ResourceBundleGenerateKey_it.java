//$Header: /as4/de/mendelson/util/security/cert/gui/keygeneration/ResourceBundleGenerateKey_it.java 4     21/07/25 8:37 Heller $
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
* @version $Revision: 4 $
*/
public class ResourceBundleGenerateKey_it extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Demolizione"},
		{"button.ignore", "Ignorare gli avvisi"},
		{"button.ok", "Ok"},
		{"button.reedit", "Rivedere"},
		{"label.commonname", "Nome comune"},
		{"label.commonname.help", "<HTML><strong>Nome comune</strong><br><br>"
			+"Si tratta del nome del dominio corrispondente alla voce DNS. Questo parametro è importante per l''handshake di una connessione TLS. È possibile (ma non consigliato!) inserire un indirizzo IP. È anche possibile creare un certificato wildcard sostituendo parti del dominio con *. Tuttavia, anche questo non è consigliato, perché non tutti i partner accettano tali chiavi.<br>"
			+"Se si desidera utilizzare questa chiave come chiave TLS e questa voce fa riferimento a un dominio inesistente o non corrisponde al proprio dominio, la maggior parte dei sistemi dovrebbe interrompere le connessioni TLS in entrata.</HTML>"},
		{"label.commonname.hint", "(nome di dominio del server)"},
		{"label.countrycode", "Codice paese"},
		{"label.countrycode.hint", "(2 caratteri, ISO 3166)"},
		{"label.extension.ski", "Identificatore chiave del soggetto (SKI)"},
		{"label.extension.ski.help", "<HTML><strong>SKI</strong><br><br>"
			+"Esistono diversi modi per identificare un certificato: utilizzando l''hash del certificato, l''emittente, il numero di serie e l''identificatore della chiave del soggetto (SKI). Lo SKI fornisce un identificatore univoco per il richiedente del certificato ed è spesso utilizzato quando si lavora con la firma digitale XML o nell''ambito della sicurezza dei servizi web in generale. Questa estensione con l''OID 2.5.29.14 è quindi spesso richiesta per AS4.</HTML>"},
		{"label.keytype", "Tipo di chiave"},
		{"label.keytype.help", "<HTML><strong>Tipo di chiave</strong><br><br>"
			+"Si tratta dell''algoritmo per la creazione della chiave. A seconda dell''algoritmo, vi sono vantaggi e svantaggi per le chiavi risultanti.<br>"
			+"A partire dal 2023, si consiglia una chiave RSA con una lunghezza di 2048 o 4096 bit.<br>"
			+"Esistono i seguenti tipi:<br>"
			+"<ul><li>DSA: algoritmo più vecchio, quasi mai utilizzato</li><li>RSA: standard classico, ampiamente utilizzato</li><li>ECDSA: firma efficiente, curve ellittiche</li><li>EDDSA: moderno e veloce, curve di Edwards</li></ul></HTML>"},
		{"label.locality", "Posizione"},
		{"label.locality.hint", "(Città)"},
		{"label.mailaddress", "Indirizzo postale"},
		{"label.mailaddress.help", "<HTML><strong>Indirizzo di posta elettronica</strong><br><br>"
			+"È l''indirizzo e-mail collegato alla chiave. Tecnicamente, questo parametro non ha alcun interesse. Tuttavia, se si desidera che la chiave sia autenticata, questo indirizzo e-mail viene solitamente utilizzato per comunicare con la CA. Inoltre, l''indirizzo e-mail dovrebbe essere sul dominio del server e corrispondere a qualcosa come webmaster@dominio o simile, perché la maggior parte delle CA lo utilizza per verificare se si è in possesso del dominio associato.</HTML>"},
		{"label.namedeccurve", "Curva"},
		{"label.namedeccurve.help", "<HTML><strong>Curva</strong><br><br>"
			+"Qui si seleziona il nome della curva ellittica da utilizzare per la generazione della chiave. La lunghezza della chiave desiderata è solitamente parte del nome della curva, ad esempio la chiave della curva \"BrainpoolP256r1\" ha una lunghezza di 256 bit. La curva più comunemente usata nel 2022 (circa il 75% di tutti i certificati CE su Internet la utilizza) è la NIST P-256, che si può trovare qui sotto il nome di \"Prime256v1\". È la curva standard di OpenSSL a partire dal 2022.</HTML>"},
		{"label.organisationname", "Organizzazione (nome)"},
		{"label.organisationunit", "Organizzazione (Unità)"},
		{"label.purpose", "Estensioni chiave"},
		{"label.purpose.encsign", "Crittografia e firma digitale"},
		{"label.purpose.ssl", "TLS"},
		{"label.signature", "Firma"},
		{"label.signature.help", "<HTML><strong>Firma</strong><br><br>"
			+"È l''algoritmo di firma con cui viene firmata la chiave. È necessario per i test di integrità della chiave stessa. Questo parametro non ha nulla a che vedere con le capacità di firma della chiave: ad esempio, è possibile creare firme SHA-2 con una chiave firmata SHA-1 o viceversa.<br>"
			+"Si consiglia una chiave firmata SHA-2 a partire dal 2024.<br><br>"
			+"<strong>Breve panoramica: SHA-1, SHA-2, SHA-3 e RSASSA-PSS</strong><br><br>"
			+"<strong>SHA-1</strong>: un vecchio algoritmo di hash che ora è considerato insicuro.<br>"
			+"<strong>SHA-2</strong>: una versione più moderna e sicura di SHA, che esiste in diverse varianti come SHA-256 e SHA-512.<br>"
			+"<strong>SHA-3</strong>: l''ultimo algoritmo di hash, basato su una struttura diversa rispetto a SHA-1 e SHA-2 e ancora più sicuro contro gli attacchi.<br>"
			+"<strong>RSASSA-PSS (Probabilistic Signature Scheme)</strong>: è un''estensione di RSA. Combina la funzione di hash SHA con la procedura di firma PSS, che fornisce ulteriore sicurezza.</HTML>"},
		{"label.size", "Lunghezza della chiave"},
		{"label.size.help", "<HTML><strong>Lunghezza del tasto</strong><br><br>"
			+"Questa è la lunghezza della chiave. In linea di principio, le operazioni crittografiche con chiavi di lunghezza maggiore sono più sicure di quelle con chiavi di lunghezza minore. Tuttavia, lo svantaggio di chiavi di lunghezza elevata è che le operazioni crittografiche richiedono tempi molto più lunghi, il che può rallentare notevolmente l''elaborazione dei dati a seconda della potenza di calcolo.<br>"
			+"Si consiglia una chiave con una lunghezza di 2048 o 4096 bit a partire dal 2023.</HTML>"},
		{"label.state", "Paese"},
		{"label.subjectalternativenames", "Nomi alternativi del richiedente"},
		{"label.validity", "Validità in giorni"},
		{"label.validity.help", "<HTML><strong>Validità in giorni</strong><br><br>"
			+"Questo valore è interessante solo per le chiavi autofirmate. In caso di autenticazione, la CA sovrascriverà questo valore.</HTML>"},
		{"title", "Generazione di chiavi"},
		{"view.basic", "Vista standard"},
		{"view.expert", "Il punto di vista dell''esperto"},
		{"warning.invalid.mail", "L''indirizzo di posta \"{0}\" non è valido."},
		{"warning.mail.in.domain", "L''indirizzo e-mail non fa parte del dominio \"{0}\" (ad esempio, myname@{0}).\nQuesto può essere un problema se la chiave deve essere autenticata in seguito."},
		{"warning.nonexisting.domain", "Il dominio \"{0}\" non esiste."},
		{"warning.title", "Possibile problema con i parametri chiave"},
	};
}
