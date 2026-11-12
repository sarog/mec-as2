//$Header: /as2/de/mendelson/util/security/crmf/ResourceBundleCRMF_it.java 1     12/01/26 8:33 Heller $
package de.mendelson.util.security.crmf;

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
public class ResourceBundleCRMF_it extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Annullamento"},
		{"button.ok", "OK"},
		{"label.initial", "Inizializzazione (richiesta iniziale)"},
		{"label.initial.help", "<HTML><strong>Inizializzazione (richiesta iniziale)</strong><br><br>"
			+"Selezionare questa opzione per la registrazione iniziale con la CA. L''autenticazione avviene tramite la password di registrazione iniziale.</HTML>"},
		{"label.key.encryption", "Chiave di crittografia"},
		{"label.key.signature", "Chiave di firma"},
		{"label.key.tls", "Chiave TLS"},
		{"label.root.ca", "Certificato radice sub-CA"},
		{"label.root.ca.help", "<HTML><strong>Certificato radice sub-CA</strong><br><br>"
			+"La richiesta CRMF generata è incapsulata in una struttura di messaggio CMP che contiene un campo destinatario (Recipient).<br>"
			+"Per garantire una trasmissione conforme agli standard, questo campo del destinatario deve contenere il Distinguished Name (DN) specifico della CA dallo spazio dei nomi X.500. Questo si trova nel campo \"Subject\" del certificato stesso (ad esempio CN=SM-Test-PKI-DE). Questo si trova nel campo \"Subject\" del certificato della CA stessa (ad esempio, CN=SM-Test-PKI-DE). Ciò garantisce che la struttura del messaggio generato sia conforme agli standard della PKI BDEW/Smart Metering. Per una corrispondenza esatta di questo valore è necessario il certificato radice della (sotto)CA.</HTML>"},
		{"label.update", "Aggiornamento (Richiesta di aggiornamento)"},
		{"label.update.help", "<HTML><strong>Aggiornamento (Richiesta di aggiornamento)</strong><br><br>"
			+"Selezionare questa opzione per rinnovare un certificato esistente prima della sua scadenza. L''autenticazione avviene automaticamente tramite il certificato attualmente valido.</HTML>"},
		{"password.hint", "Password iniziale unica"},
		{"success.body", "Il file CRMF è stato salvato sotto {0}"},
		{"success.title", "Creazione del CRMF riuscita"},
		{"title", "Creazione di una richiesta CRMF (BDEW)"},
	};
}
