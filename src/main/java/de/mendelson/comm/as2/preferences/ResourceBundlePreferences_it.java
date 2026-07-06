//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferences_it.java 11    9/09/25 16:23 Heller $
package de.mendelson.comm.as2.preferences;

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
 *
 * @author S.Heller
 * @version $Revision: 11 $
 */
public class ResourceBundlePreferences_it extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"button.browse", "Sfogliare"},
        {"button.cancel", "Annullamento"},
        {"button.mailserverdetection", "Scoprire il server di posta"},
        {"button.modify", "Modifica"},
        {"button.ok", "Ok"},
        {"button.testmail", "Inviare una mail di prova"},
        {"checkbox.notifycem", "Eventi di scambio di certificati (CEM)"},
        {"checkbox.notifycertexpire", "Prima della scadenza dei certificati"},
        {"checkbox.notifyclientserver", "Problemi con la connessione client-server"},
        {"checkbox.notifyconnectionproblem", "Per problemi di connessione"},
        {"checkbox.notifyfailure", "Dopo i problemi di sistema"},
        {"checkbox.notifypostprocessing", "Problemi di post-elaborazione"},
        {"checkbox.notifyresend", "Dopo i reinvii rifiutati"},
        {"checkbox.notifytransactionerror", "Dopo gli errori nelle transazioni"},
        {"dirmsg", "Elenco delle notizie"},
        {"embedded.httpconfig.not.available", "Server HTTP non disponibile o problemi di accesso al file di configurazione"},
        {"event.notificationdata.modified.body", "I dati di notifica sono stati creati da\n\n{0}\n\na\n\n{1}\n\n modificato."},
        {"event.notificationdata.modified.subject", "Le impostazioni di notifica sono state modificate"},
        {"event.preferences.modified.body", "Vecchio valore: {0}\nNuovo valore: {1}"},
        {"event.preferences.modified.subject", "Il valore {0} delle impostazioni del server è stato modificato"},
        {"filechooser.keystore", "Selezionare il file del keystore (formato JKS)."},
        {"filechooser.selectdir", "Selezionare la directory da impostare"},
        {"header.dirname", "Tipo"},
        {"header.dirvalue", "Elenco"},
        {"info.restart.client", "Per rendere effettive le modifiche, è necessario riavviare il client!"},
        {"label.autodelete", "Cancellazione automatica"},
        {"label.colorblindness", "Supporto per il daltonismo"},
        {"label.country", "Paese/Regione"},
        {"label.country.help", "<HTML><strong>Paese/Regione</strong><br><br>"
            + "Questa impostazione controlla essenzialmente solo il formato della data utilizzato per visualizzare i dati delle transazioni ecc. nel client.</HTML>"},
        {"label.darkmode", "Modalità scura"},
        {"label.days", "Giorni"},
        {"label.deletelogdirolderthan", "I dati di log più vecchi di"},
        {"label.deletemsglog", "Cancellazione automatica dei file e delle voci di registro"},
        {"label.deletemsglog.help", "<HTML><strong>Cancellazione automatica di file e voci di registro</strong><br><br>"
            + "Nelle impostazioni è possibile eliminare i vecchi file (manutenzione del sistema).<br>"
            + "Se è stata impostata e attivata questa opzione, ogni eliminazione di un vecchio file viene registrata.<br>"
            + "Viene generato anche un evento di sistema, che può informare l''utente di questo processo tramite la funzione di notifica.</HTML>"},
        {"label.deletemsgolderthan", "Le voci di transazione più vecchie di"},
        {"label.deletestatsolderthan", "Da dati statistici più vecchi di"},
        {"label.displaymode", "Rappresentazione"},
        {"label.displaymode.help", "<HTML><strong>Display</strong><br><br>"
            + "Qui si imposta una delle modalità di visualizzazione supportate dal client.<br>"
            + "Questo può essere impostato anche tramite i parametri della riga di comando al momento della chiamata.</HTML>"},
        {"label.hicontrastmode", "Modalità ad alto contrasto"},
        {"label.httpport", "Porta di ingresso HTTP"},
        {"label.httpport.help", "<HTML><strong>Porta di ingresso HTTP</strong><br><br>"
            + "È la porta per le connessioni in entrata non criptate. Questa impostazione viene trasmessa al server HTTP incorporato; è necessario riavviare il server AS2 dopo una modifica.<br>"
            + "La porta fa parte dell''URL a cui il partner deve inviare i messaggi AS2. Questo è http://Host:<strong>Port</strong>/as2/HttpReceiver.<br><br>"
            + "Il valore preimpostato è 8080.</HTML>"},
        {"label.httpsend.timeout", "HTTP/S Timeout di invio"},
        {"label.httpsend.timeout.help", "<HTML><strong>HTTP/S timeout di invio</strong><br><br>"
            + "È il valore del timeout della connessione di rete per le connessioni in uscita.<br>"
            + "Se dopo questo tempo non è stata stabilita alcuna connessione con il sistema partner, il tentativo di connessione viene annullato e, se necessario, vengono effettuati altri tentativi di connessione in base alle impostazioni di ripetizione.<br><br>"
            + "Il valore preimpostato è 5000ms.</HTML>"},
        {"label.httpsport", "Porta di ingresso HTTPS"},
        {"label.httpsport.help", "<HTML><strong>Porta di ingresso HTTPS</strong><br><br>"
            + "È la porta per le connessioni criptate in entrata (TLS). Questa impostazione viene trasmessa al server HTTP incorporato; è necessario riavviare il server AS2 dopo una modifica.<br>"
            + "La porta fa parte dell''URL a cui il partner deve inviare i messaggi AS2. Questo è https://Host:<strong>Port</strong>/as2/HttpReceiver<br><br>"
            + "Il valore preimpostato è 8443.</HTML>"},
        {"label.keystore.encryptionsign", "Keystore( crittografia, firma):"},
        {"label.keystore.https", "Keystore (per l''invio via Https):"},
        {"label.keystore.https.pass", "Password del keystore (per l''invio via Https):"},
        {"label.keystore.pass", "Password del Keystore (crittografia/firma digitale):"},
        {"label.language", "Lingua"},
        {"label.language.help", "<HTML><strong>Lingua</strong><br><br>"
            + "È la lingua di visualizzazione del client. Se il client e il server vengono eseguiti in processi diversi (cosa consigliata), la lingua del server può essere diversa.<br>"
            + "La lingua utilizzata nel protocollo è sempre quella del server.</HTML>"},
        {"label.litemode", "Modalità luce"},
        {"label.loghttprequests", "Registrazione delle richieste HTTP dal server HTTP integrato"},
        {"label.loghttprequests.help", "<HTML><strong>Protocollo di richiesta HTTP</strong><br><br>"
            + "Se attivato, il server HTTP incorporato (Jetty) scrive un registro delle richieste nei file <strong>log/yy_MM_dd.jetty.request.log</strong>. Questi file di registro non vengono cancellati dalla manutenzione del sistema; si consiglia di cancellarli manualmente.<br><br>"
            + "Per rendere effettive le modifiche a questa impostazione, riavviare il software.</HTML>"},
        {"label.logmessageprocessing", "Registrazione estesa dell''elaborazione dei messaggi"},
        {"label.logmessageprocessing.help", "<HTML><strong>Logistica avanzata dell''elaborazione dei messaggi</strong><br><br>"
            + "Se attivata, le uscite estese per l''elaborazione dei messaggi vengono inviate al log.</HTML>"},
        {"label.logpollprocess", "Informazioni sul processo di polling della directory"},
        {"label.logpollprocess.help", "<HTML><strong>Informazioni sul processo di scrutinio delle directory</strong><br><br>"
            + "Se si attiva questa opzione, ogni operazione di polling di una directory di output viene annotata nel log.<br>"
            + "Poiché il numero di voci può essere molto elevato, non utilizzare questa opzione in nessun caso nel funzionamento produttivo, ma solo a scopo di prova.</HTML>"},
        {"label.mailaccount", "Account del server di posta"},
        {"label.mailhost", "Server di posta (SMTP)"},
        {"label.mailhost.hint", "IP o dominio del server"},
        {"label.mailpass", "Password del server di posta"},
        {"label.mailport", "Porto"},
        {"label.mailport.help", "<HTML><strong>Porta SMTP</strong><br><br>"
            + "Di norma, si tratta di uno di questi valori:<br>"
            + "<strong>25</strong> (porta standard)<br>"
            + "<strong>465</strong> (porta TLS, valore obsoleto)<br>"
            + "<strong>587</strong> (porta TLS, valore predefinito)<br>"
            + "<strong>2525</strong> (porta TLS, valore alternativo, non standard)</HTML>"},
        {"label.mailport.hint", "Porta SMTP"},
        {"label.max.inboundconnections", "Connessioni parallele massime in ingresso"},
        {"label.max.inboundconnections.help", "<HTML><strong>Massimo di connessioni parallele in ingresso</strong><br><br>"
            + "Questo è il numero massimo di connessioni parallele in entrata che possono essere aperte dall''esterno all''installazione di mendelson AS2. Questo valore si applica all''intero software e non è limitato ai singoli partner.<br>"
            + "L''impostazione viene trasmessa al server HTTP incorporato; è necessario riavviare il server AS2 dopo una modifica.<br><br>"
            + "Anche se è possibile limitare il numero di connessioni parallele in entrata, è meglio effettuare questa impostazione sul firewall o sul proxy upstream, in modo da applicarla all''intero sistema e non solo a un singolo software.<br><br>"
            + "Il valore preimpostato è 1000.</HTML>"},
        {"label.max.outboundconnections", "Connessioni parallele in uscita massime"},
        {"label.max.outboundconnections.help", "<HTML><strong>Massima connessione parallela in uscita</strong><br><br>"
            + "È il numero massimo di connessioni parallele in uscita che il sistema può aprire.<br>"
            + "Questo valore viene utilizzato principalmente per proteggere il sistema del partner dal sovraccarico di connessioni in entrata dal proprio lato.<br><br>"
            + "Il valore preimpostato è 9999.</HTML>"},
        {"label.maxmailspermin", "Numero massimo di notifiche/min"},
        {"label.maxmailspermin.help", "<HTML><strong>Numero massimo di notifiche/min</strong><br><br>"
            + "Per evitare un numero eccessivo di e-mail, è possibile riassumere le notifiche impostando il numero massimo di notifiche al minuto.<br>"
            + "Questa funzione consente di ricevere messaggi di posta elettronica contenenti diverse notifiche.</HTML>"},
        {"label.mdn.timeout", "Tempo massimo di attesa per MDN"},
        {"label.mdn.timeout.help", "<HTML><strong>Tempo massimo di attesa per MDN</strong><br><br>"
            + "Il tempo in cui il sistema attende una MDN (Message Delivery Notification) per un messaggio AS2 inviato prima di impostare la transazione associata sullo stato \"fallito\".<br>"
            + "Questo valore è valido a livello di sistema per tutti i partner.<br><br>"
            + "Il valore predefinito è 30 minuti; il tempo viene conteggiato dal momento in cui la connessione al partner viene stabilita con successo.<br><br>"
            + "Nel caso di un MDN sincrono, la connessione con il partner viene mantenuta aperta finché non viene ricevuto un MDN sul canale di ritorno o finché il tempo di attesa non è scaduto. Una volta scaduto, la connessione viene terminata, la transazione viene impostata sullo stato \"failed\" e viene eseguita l''eventuale post-elaborazione. Questa transazione non viene ripetuta.<br><br>"
            + "Nel caso di un MDN asincrono, il sistema attende la connessione in entrata del partner con l''MDN fino alla scadenza di questo tempo di attesa. Se allo scadere del tempo di attesa non è stato ricevuto alcun MDN, la transazione associata viene impostata come \"fallita\" e viene eseguita l''eventuale post-elaborazione definita. Anche in questo caso la transazione non viene ripetuta.</HTML>"},
        {"label.min", "min"},
        {"label.notificationmail", "Destinatario della notifica Indirizzo postale"},
        {"label.notificationmail.help", "<HTML><strong>Indirizzo e-mail del destinatario della notifica</strong><br><br>"
            + "L''indirizzo e-mail del destinatario della notifica.<br>"
            + "Se la notifica deve essere inviata a più destinatari, inserire qui un elenco di indirizzi di destinatari separati da virgole.</HTML>"},
        {"label.proxy.pass", "password"},
        {"label.proxy.pass.hint", "Password di accesso al proxy"},
        {"label.proxy.port.hint", "Porto"},
        {"label.proxy.url", "URL proxy"},
        {"label.proxy.url.hint", "IP o dominio del proxy"},
        {"label.proxy.use", "Utilizzare un proxy HTTP per le connessioni HTTP/HTTP in uscita"},
        {"label.proxy.useauthentification", "Utilizzare l''autenticazione per il proxy"},
        {"label.proxy.user", "Utenti"},
        {"label.proxy.user.hint", "Utente login proxy"},
        {"label.replyto", "Indirizzo Replyto"},
        {"label.retry.max", "Numero massimo di tentativi di connessione"},
        {"label.retry.max.help", "<HTML><strong>Numero massimo di tentativi per stabilire una connessione</strong><br><br>"
            + "È il numero di tentativi utilizzati per ripetere le connessioni a un partner se non è stato possibile stabilire una connessione.<br>"
            + "Il tempo di attesa tra questi tentativi può essere impostato nella proprietà <strong>Tempo di attesa tra i tentativi di connessione</strong>.<br><br>"
            + "Il valore preimpostato è 10.</HTML>"},
        {"label.retry.waittime", "Tempo di attesa tra i tentativi di connessione"},
        {"label.retry.waittime.help", "<HTML><strong>Tempo di attesa tra i tentativi di connessione</strong><br><br>"
            + "È il tempo in secondi che il sistema attende prima di riconnettersi al partner.<br>"
            + "Un nuovo tentativo di connessione viene effettuato solo se non è stato possibile stabilire una connessione con un partner (ad esempio, guasto del sistema del partner o problema di infrastruttura).<br>"
            + "Il numero di tentativi di connessione può essere configurato nella proprietà <strong>Numero massimo di tentativi di connessione</strong>.<br><br>"
            + "Il valore preimpostato è 30s.</HTML>"},
        {"label.sec", "s"},
        {"label.security", "Sicurezza della connessione"},
        {"label.smtpauthorization.credentials", "Utente/password"},
        {"label.smtpauthorization.header", "Autorizzazione SMTP"},
        {"label.smtpauthorization.none", "Nessuno"},
        {"label.smtpauthorization.oauth2.authorizationcode", "OAuth2 (Codice di autorizzazione)"},
        {"label.smtpauthorization.oauth2.clientcredentials", "OAuth2 (credenziali del cliente)"},
        {"label.smtpauthorization.pass", "password"},
        {"label.smtpauthorization.pass.hint", "Password del server SMTP"},
        {"label.smtpauthorization.user", "Utenti"},
        {"label.smtpauthorization.user.hint", "Nome utente del server SMTP"},
        {"label.stricthostcheck", "TLS: controllo rigoroso del nome host"},
        {"label.stricthostcheck.help", "<HTML><strong>TLS: Controllo rigoroso del nome host</strong><br><br>"
            + "Qui si può impostare se il nome comune (CN) del certificato remoto deve corrispondere all''host remoto nel caso di una connessione TLS in uscita.<br>"
            + "Questo controllo si applica solo ai certificati autenticati.</HTML>"},
        {"label.trustallservercerts", "TLS: fidatevi di tutti i certificati dei server finali dei vostri partner AS2."},
        {"label.trustallservercerts.help", "<HTML><strong>TLS: fidatevi di tutti i certificati dei server finali dei vostri partner AS2</strong>.<br><br>"
            + "Normalmente, TLS richiede che tutti i certificati della catena di fiducia del sistema AS2 del partner siano conservati nel proprio gestore di certificati TLS.<br><br>"
            + "Se si attiva questa opzione, ci si affida al certificato finale del sistema partner quando si stabilisce una connessione in uscita, se si dispone solo dei certificati root e intermedi associati nel gestore di certificati TLS.<br>"
            + "Si noti che questa opzione ha senso solo se il partner utilizza un certificato autenticato.<br>"
            + "I certificati autofirmati sono comunque sempre accettati.<br><br>"
            + "<strong>Attenzione: </strong> L''attivazione di questa opzione abbassa il livello di sicurezza, in quanto sono possibili attacchi man-in-the-middle.</HTML>"},
        {"maintenancemultiplier.day", "Giorno/i"},
        {"maintenancemultiplier.hour", "Ora/e"},
        {"maintenancemultiplier.minute", "Minuti"},
        {"receipt.subdir", "Creare sottodirectory per ogni partner per la ricezione dei messaggi"},
        {"receipt.subdir.help", "<HTML><strong>Sottodirectory per la ricezione</strong><br><br>"
            + "Imposta se i dati devono essere ricevuti nella directory <strong>&lt;Local station&gt;/inbox</strong> o <strong>&lt;Local station&gt;/inbox/&lt;Partner name&gt;</strong>.</HTML>"},
        {"remotedir.select", "Selezionare la directory sul server"},
        {"systemmaintenance.deleteoldlogdirs.help", "<HTML><strong>Eliminazione delle vecchie directory di log</strong><br><br>"
            + "Anche se le vecchie transazioni sono state cancellate, i processi possono essere rintracciati attraverso i file di log esistenti.<br>"
            + "Questa impostazione elimina questi file di registro e anche tutti i file relativi agli eventi di sistema che rientrano nello stesso periodo di tempo.</HTML>"},
        {"systemmaintenance.deleteoldstatistic.help", "<HTML><strong>Cancellazione di vecchi dati statistici</strong><br><br>"
            + "Il sistema raccoglie i dati di compatibilità dai sistemi partner e può visualizzarli sotto forma di statistiche.<br>"
            + "Determina l''intervallo di tempo in cui i dati vengono memorizzati.</HTML>"},
        {"systemmaintenance.deleteoldtransactions.help", "<HTML><strong>Cancellazione di vecchie voci di transazione</strong><br><br>"
            + "Definisce l''intervallo di tempo in cui le transazioni e i dati temporanei associati rimangono nel sistema e vengono visualizzati nella panoramica delle transazioni.<br>"
            + "Queste impostazioni non influiscono sui dati/file ricevuti, che rimangono inalterati.<br>"
            + "Per le transazioni annullate, il registro delle transazioni è ancora disponibile tramite la "
            + "funzionalità di ricerca del registro.<br><br>"
            + "Questa impostazione di manutenzione pulirà le directory correlate //temp, //sent e //_rawincoming nel file system del server."
            + "</HTML>"},
        {"tab.connectivity", "Connessioni"},
        {"tab.dir", "Elenchi"},
        {"tab.interface", "Moduli"},
        {"tab.language", "Cliente"},
        {"tab.log", "Protocollo"},
        {"tab.maintenance", "Manutenzione del sistema"},
        {"tab.misc", "Generale"},
        {"tab.notification", "Notifica"},
        {"tab.proxy", "Proxy"},
        {"tab.security", "Sicurezza"},
        {"testmail", "Posta di prova"},
        {"testmail.message.error", "Errore nell''invio dell''e-mail di prova:\n{0}"},
        {"testmail.message.success", "Un''e-mail di prova è stata inviata con successo a {0}."},
        {"testmail.title", "Invio di un''e-mail di prova"},
        {"title", "Impostazioni"},
        {"warning.changes.canceled", "L''utente ha annullato la finestra di dialogo delle impostazioni - non è stata apportata alcuna modifica alle impostazioni."},
        {"warning.clientrestart.required", "Le impostazioni del client sono state modificate: riavviare il client per renderle valide."},
        {"warning.serverrestart.required", "Per rendere effettive le modifiche, riavviare il server."},};
}
