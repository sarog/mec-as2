//$Header: /as4/de/mendelson/util/systemevents/ResourceBundleSystemEvent_it.java 6     17/02/26 11:08 Heller $
package de.mendelson.util.systemevents;

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
* @version $Revision: 6 $
*/
public class ResourceBundleSystemEvent_it extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"category.100", "Componente server"},
		{"category.1000", "Elaborazione dei dati"},
		{"category.100000", "Altro"},
		{"category.1100", "Attivazione"},
		{"category.1200", "Operazione di file"},
		{"category.1300", "Funzionamento del cliente"},
		{"category.1400", "Interfaccia XML"},
		{"category.1500", "Interfaccia REST"},
		{"category.200", "Connessione"},
		{"category.300", "Transazione"},
		{"category.400", "Certificato"},
		{"category.500", "Database"},
		{"category.700", "Configurazione"},
		{"category.800", "Contingente"},
		{"category.900", "Notifica"},
		{"origin.1", "Sistema"},
		{"origin.2", "Utenti"},
		{"origin.3", "Transazione"},
		{"severity.1", "Info"},
		{"severity.2", "Avvertenze"},
		{"severity.3", "Errore"},
		{"type.100", "Spegnimento del server"},
		{"type.1000", "Elaborazione dei dati"},
		{"type.100000", "Non specificato"},
		{"type.1001", "Pre-elaborazione"},
		{"type.1002", "Post-elaborazione"},
		{"type.101", "Avvio del server"},
		{"type.102", "Server in funzione"},
		{"type.103", "Avvio del server DB"},
		{"type.104", "Server DB in esecuzione"},
		{"type.105", "Server DB spento"},
		{"type.106", "Avvio del server HTTP"},
		{"type.107", "Server HTTP in esecuzione"},
		{"type.108", "Spegnimento del server HTTP"},
		{"type.109", "Avvio del server TRFC"},
		{"type.110", "Server TRFC in esecuzione"},
		{"type.1100", "Licenza"},
		{"type.1101", "Aggiornamento della licenza"},
		{"type.1102", "Scadenza della licenza"},
		{"type.111", "Stato del server TRFC"},
		{"type.112", "Spegnimento del server TRFC"},
		{"type.113", "Avvio dello scheduler"},
		{"type.114", "Scheduler in funzione"},
		{"type.115", "Spegnimento dello scheduler"},
		{"type.116", "Monitoraggio della directory (stato modificato)"},
		{"type.117", "Porta di ricezione"},
		{"type.1200", "Operazione di file"},
		{"type.1201", "File (eliminare)"},
		{"type.1202", "Creare una directory"},
		{"type.1203", "File (spostamento)"},
		{"type.1204", "File (copia)"},
		{"type.1300", "Cliente"},
		{"type.1301", "Accesso utente (successo)"},
		{"type.1302", "Accesso utente (fallito)"},
		{"type.1303", "Separazione degli utenti"},
		{"type.1400", "XML"},
		{"type.1401", "Configurazione del certificato"},
		{"type.1402", "Configurazione del partner"},
		{"type.1500", "REST"},
		{"type.1501", "Aggiungi certificato"},
		{"type.1502", "Configurazione del certificato"},
		{"type.1503", "Cancellare il certificato"},
		{"type.1504", "Aggiungi partner"},
		{"type.1505", "Configurazione del partner"},
		{"type.1506", "Cancellare il partner"},
		{"type.1507", "Inviare l''ordine"},
		{"type.1508", "Transazione (eliminare)"},
		{"type.199", "Componente server"},
		{"type.200", "Connessione"},
		{"type.201", "Test di connessione"},
		{"type.300", "Transazione"},
		{"type.301", "Errore di transazione"},
		{"type.302", "Transazione (riconsegna rifiutata)"},
		{"type.303", "Transazione (messaggio duplicato)"},
		{"type.304", "Transazione (eliminare)"},
		{"type.305", "Transazione (annullamento)"},
		{"type.306", "Transazione (reinvio)"},
		{"type.400", "Certificato"},
		{"type.401", "Certificato (aggiunto)"},
		{"type.402", "Certificato (alias modificato)"},
		{"type.403", "Certificato (cancellato)"},
		{"type.404", "Scambio di certificati"},
		{"type.405", "Scadenza del certificato"},
		{"type.406", "Scambio di certificati (richiesta in entrata)"},
		{"type.407", "Certificato (Importazione del Keystore)"},
		{"type.500", "Database"},
		{"type.501", "Creazione del database"},
		{"type.502", "Database (Aggiornamento)"},
		{"type.503", "Database (inizializzazione)"},
		{"type.504", "Transazione di rollback"},
		{"type.700", "Configurazione"},
		{"type.701", "Modifica della configurazione"},
		{"type.702", "Controllo della configurazione"},
		{"type.703", "Partner (modificato)"},
		{"type.704", "Partner (cancellato)"},
		{"type.705", "Partner (aggiunto)"},
		{"type.800", "Quota"},
		{"type.801", "Quota raggiunta"},
		{"type.802", "Quota raggiunta"},
		{"type.900", "Notifica"},
		{"type.901", "Notifica (invio riuscito)"},
		{"type.902", "Notifica (invio non riuscito)"},                
	};
}
