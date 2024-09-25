//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferences_de.java 75    17/11/22 15:09 Heller $
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
 * ResourceBundle to localize gui entries
 *
 * @author S.Heller
 * @version $Revision: 75 $
 */
public class ResourceBundlePreferences_de extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        //preferences localized
        {PreferencesAS2.DIR_MSG, "Nachrichtenverzeichnis"},
        {"button.ok", "Ok"},
        {"button.cancel", "Abbrechen"},
        {"button.modify", "Bearbeiten"},
        {"button.browse", "Durchsuchen"},
        {"filechooser.selectdir", "Bitte w�hlen Sie das zu setzene Verzeichnis"},
        {"title", "Einstellungen"},
        {"tab.language", "Client"},
        {"tab.dir", "Verzeichnisse"},
        {"tab.security", "Sicherheit"},
        {"tab.proxy", "Proxy"},
        {"tab.misc", "Allgemein"},
        {"tab.maintenance", "Systempflege"},
        {"tab.notification", "Benachrichtigung"},
        {"tab.interface", "Module"},
        {"tab.log", "Protokoll"},
        {"tab.connectivity", "Verbindungen"},
        {"header.dirname", "Typ"},
        {"header.dirvalue", "Verzeichnis"},
        {"label.language", "Sprache"},
        {"label.language.help", "<HTML><strong>Sprache</strong><br><br>"
            + "Dies ist die Anzeigesprache des Clients. Wenn Sie Client und Server in verschiedenen Prozessen laufen lassen "
            + "(was empfohlen wird), kann die Serversprache eine andere sein. "
            + "Die Sprache, die im Protokoll verwendet wird, ist immer die Sprache des Servers."
            + "</HTML>"},
        {"label.country", "Land/Region"},
        {"label.country.help", "<HTML><strong>Land/Region</strong><br><br>"
            + "Diese Einstellung steuert im Wesentlichen nur das Datumsformat, das f�r die Anzeige von Transaktionsdaten usw. im Client verwendet wird."
            + "</HTML>"},
        {"label.keystore.https.pass", "Keystore Passwort (zum Senden via Https):"},
        {"label.keystore.pass", "Keystore Password (Verschl�sselung/digitale Signatur):"},
        {"label.keystore.https", "Keystore (zum Senden via Https):"},
        {"label.keystore.encryptionsign", "Keystore( Verschl., Signatur):"},
        {"label.proxy.url", "Proxy URL:"},
        {"label.proxy.url.hint", "Proxy IP oder Domain"},
        {"label.proxy.port.hint", "Port"},
        {"label.proxy.user", "Benutzer:"},
        {"label.proxy.user.hint", "Proxy Login Benutzer"},
        {"label.proxy.pass", "Passwort:"},
        {"label.proxy.pass.hint", "Proxy Login Passwort"},
        {"label.proxy.use", "HTTP Proxy f�r ausgehende HTTP/HTTPs Verbindungen benutzen"},
        {"label.proxy.useauthentification", "Authentifizierung f�r Proxy benutzen"},
        {"filechooser.keystore", "Bitte w�hlen Sie die Keystore Datei (JKS Format)."},
        {"label.days", "Tage"},
        {"label.deletemsgolderthan", "Automatisches L�schen von Nachrichten, die �lter sind als"},
        {"label.deletemsglog", "Automatisches L�schen von Dateien und Logeintr�gen protokollieren"},
        {"label.deletestatsolderthan", "Automatisches L�schen von Statistikdaten, die �lter sind als"},
        {"label.deletelogdirolderthan", "Automatisches L�schen von Protokolldaten, die �lter sind als"},
        {"label.asyncmdn.timeout", "Maximale Wartezeit auf asynchrone MDNs"},
        {"label.asyncmdn.timeout.help", "<HTML><strong>Maximale Wartezeit auf asynchrone MDNs</strong>"
            + "<br><br>Die Zeit, die das System auf eine asynchrone MDN (Message Delivery Notification) f�r eine gesendete AS2 Nachricht wartet, bevor es die Transaktion in den Status \"fehlgeschlagen\" versetzt."
            + "</HTML>"},
        {"label.httpsend.timeout", "HTTP/S Sende-Timeout"},
        {"label.httpsend.timeout.help", "<HTML><strong>HTTP/S Sende-Timeout</strong><br><br>"
            + "Dies ist Wert f�r die Zeit�berschreitung der Netzwerkverbindung f�r ausgehende Verbindungen.<br>"
            + "Wenn nach dieser Zeit keine Verbindung zu Ihrem Partnersystem zustande gekommen ist, wird der Verbindungsversuch abgebrochen und es werden gegebenenfalls entsprechend "
            + "der Wiederholungseinstellungen sp�ter weitere Verbindungsversuche unternommen."
            + "</HTML>"},
        {"label.min", "min"},
        {"receipt.subdir", "Unterverzeichnisse pro Partner f�r Nachrichtenempfang anlegen"},
        {"receipt.subdir.help", "<HTML><strong>Unterverzeichnisse f�r Empfang</strong><br><br>"
            + "Stellt ein, ob Daten im Verzeichnis <strong>&lt;Lokale Station&gt;/inbox</strong>"
            + " oder <strong>&lt;Lokale Station&gt;/inbox/&lt;Partnername&gt;</strong> empfangen werden sollen."
            + "</HTML>"},
        //notification
        {"checkbox.notifycertexpire", "Vor dem Auslaufen von Zertifikaten"},
        {"checkbox.notifytransactionerror", "Nach Fehlern in Transaktionen"},
        {"checkbox.notifycem", "Ereignisse beim Zertifikataustausch (CEM)"},
        {"checkbox.notifyfailure", "Nach Systemproblemen"},
        {"checkbox.notifyresend", "Nach abgewiesenen Resends"},
        {"checkbox.notifyconnectionproblem", "Bei Verbindungsproblemen"},
        {"checkbox.notifypostprocessing", "Probleme bei der Nachbearbeitung"},
        {"button.testmail", "Sende Test Mail"},
        {"label.mailhost", "Mailserver (SMTP)"},
        {"label.mailhost.hint", "IP oder Domain des Servers"},
        {"label.mailport", "Port"},
        {"label.mailport.help", "<HTML><strong>SMTP Port</strong><br><br>"
            + "In der Regel ist es einer dieser Werte:<br>"
            + "<strong>25</strong> (Standard Port)<br>"
            + "<strong>465</strong> (SSL Port, �berholter Wert)<br>"
            + "<strong>587</strong> (SSL Port, Standardwert)<br>"
            + "<strong>2525</strong> (SSL Port, alternativer Wert, kein Standard)"
            + "</HTML>"},
        {"label.mailport.hint", "SMTP Port"},
        {"label.mailaccount", "Mailserver Konto"},
        {"label.mailpass", "Mailserver Passwort"},
        {"label.notificationmail", "Benachrichtigungsempf�nger Mailadresse"},
        {"label.notificationmail.help", "<HTML><strong>Benachrichtigungsempf�nger Mailadresse</strong><br><br>"
            + "Die Mail Adresse des Empf�ngers der Benachrichtigung.<br>"
            + "Wenn die Benachrichtigung an mehrere Empf�nger geschickt werden soll, geben Sie hier bitte eine kommaseparierte Liste von Empfangsadressen ein."
            + "</HTML>"},
        {"label.replyto", "Replyto Adresse"},
        {"label.smtpauthorization.header", "SMTP Autorisierung"},
        {"label.smtpauthorization.credentials", "Benutzer/Passwort"},
        {"label.smtpauthorization.none", "Keine"},
        {"label.smtpauthorization.oauth2", "OAuth2"},
        {"label.smtpauthorization.user", "Benutzer"},
        {"label.smtpauthorization.user.hint", "SMTP Server Benutzername"},
        {"label.smtpauthorization.pass", "Passwort"},
        {"label.smtpauthorization.pass.hint", "SMTP Server Passwort"},
        {"label.security", "Verbindungssicherheit"},
        {"testmail.message.success", "Eine Test-eMail wurde erfolgreich versandt."},
        {"testmail.message.error", "Fehler beim Senden der Test-eMail:\n{0}"},
        {"testmail.title", "Senden einer Test-eMail"},
        {"testmail", "Test Mail"},
        //interface
        {"label.showhttpheader", "Anzeige der HTTP Header Konfiguration bei den Partnereinstellungen"},
        {"label.showquota", "Anzeige der Benachrichtigungskonfiguration bei den Partnereinstellungen"},
        {"label.cem", "Zertifikataustausch erlauben (CEM)"},
        {"label.outboundstatusfiles", "Statusdateien f�r ausgehende Transaktionen schreiben"},
        {"info.restart.client", "Sie m�ssen den Client neu starten, damit diese �nderungen g�ltig werden!"},
        {"remotedir.select", "Verzeichnis auf dem Server w�hlen"},
        //retry
        {"label.retry.max", "Max Anzahl der Versuche zum Verbindungsaufbau"},
        {"label.retry.max.help", "<HTML><strong>Max Anzahl der Versuche zum Verbindungsaufbau</strong>"
            + "<br><br>Dies ist die Anzahl der Wiederholungsversuche, die verwendet werden, um Verbindungen "
            + "zu einem Partner zu wiederholen, wenn eine Verbindung nicht hergestellt werden konnte. Die "
            + "Wartezeit zwischen diesen Wiederholungsversuchen kann in der Eigenschaft <strong>Wartezeit zwischen Verbindungswiederholungen</strong> eingestellt werden."
            + "</HTML>"},
        {"label.retry.waittime", "Wartezeit zwischen Verbindungswiederholungen"},
        {"label.retry.waittime.help", "<HTML><strong>Wartezeit zwischen Verbindungswiederholungen</strong>"
            + "<br><br>Dies ist die Zeit in Sekunden, die das System wartet, bevor es erneut eine Verbindung "
            + "zum Partner herstellt. Ein erneuter Verbindungsversuch wird nur dann durchgef�hrt, wenn "
            + "es nicht m�glich war, eine Verbindung zu einem Partner herzustellen (z.B. Ausfall des "
            + "Partnersystems oder Infrastrukturproblem). Die Anzahl der Verbindungswiederholungen kann "
            + "in der Eigenschaft <strong>Maximale Anzahl von Verbindungswiederholungen</strong> konfiguriert werden."
            + "</HTML>"},
        {"label.sec", "s"},
        {"keystore.hint", "<HTML><strong>Achtung:</strong><br>Bitte �ndern Sie diese Parameter nur, wenn Sie externe Keystores "
            + "einbinden m�chten. Mit ver�nderten Pfaden kann es zu Problemen beim Update kommen.</HTML>"},
        {"maintenancemultiplier.day", "Tag(e)"},
        {"maintenancemultiplier.hour", "Stunde(n)"},
        {"maintenancemultiplier.minute", "Minute(n)"},
        {"label.logpollprocess", "Informationen zum Pollprozess im Protokoll anzeigen (viele Eintr�ge - nicht im Produktivbetrieb verwenden)"},
        {"label.max.outboundconnections", "Max ausgehende parallele Verbindungen"},
        {"label.max.outboundconnections.help", "<HTML><strong>Max ausgehende parallele Verbindungen</strong>"
            + "<br><br>Dies ist die Anzahl der maximalen parallelen ausgehenden Verbindungen, die Ihr "
            + "System �ffnen wird. Dieser Wert dient haupts�chlich dazu, Ihr Partnersystem vor einer �berlastung "
            + "durch eingehende Verbindungen von Ihrer Seite zu sch�tzen.</HTML>"},
        {"label.max.inboundconnections", "Max eingehende parallele Verbindungen"},
        {"label.max.inboundconnections.help", "<HTML><strong>Max eingehende parallele Verbindungen</strong>"
            + "<br><br>Dies ist die Anzahl der maximalen parallelen eingehenden Verbindungen, die zu Ihrem "
            + "System ge�ffnet werden d�rfen. "
            + "Diese Einstellung wird an den eingebetteten HTTP Server weiter gegeben, "
            + "Sie m�ssen nach einer �nderung den AS2 Server neu starten."
            + "</HTML>"},
        {"event.preferences.modified.subject", "Der Wert {0} der Servereinstellungen wurde modifiziert"},
        {"event.preferences.modified.body", "Alter Wert: {0}\nNeuer Wert: {1}"},
        {"event.notificationdata.modified.subject", "Die Einstellungen zur Benachrichtigung wurden ver�ndert"},
        {"event.notificationdata.modified.body", "Die Benachrichtigungsdaten wurden von\n\n{0}\n\nnach\n\n{1}\n\n ver�ndert."},
        {"label.maxmailspermin", "Max Anzahl von Benachrichtigungen/min"},
        {"label.maxmailspermin.help", "<HTML><strong>Max Anzahl von Benachrichtigungen/min</strong><br><br>"
            + "Um zu viele Mails zu vermeiden, k�nnen Sie Benachrichtigungen zusammenfassen, indem Sie die maximale "
            + "Anzahl von Benachrichtigungen pro Minute festlegen. Mit dieser Funktion erhalten Sie Mails, die "
            + "mehrere Benachrichtigungen enthalten."
            + "</HTML>"},
        {"systemmaintenance.deleteoldtransactions.help", "<HTML><strong>L�schen alter Nachrichten</strong><br><br>Dies legt den Zeitrahmen fest, in dem die "
            + "Transaktionen und die zugeh�rigen Daten (z.B. tempor�re Dateien) im System verbleiben und in der Transaktions�bersicht "
            + "angezeigt werden sollen.<br>Diese Einstellungen betreffen <strong>nicht</strong> Ihre empfangenen Daten/Dateien, diese "
            + "bleiben unber�hrt. F�r gel�schte Transaktionen ist das Transaktionsprotokoll �ber die Funktionalit�t der Logsuche "
            + "weiterhin verf�gbar."
            + "</HTML>"},
        {"systemmaintenance.deleteoldstatistic.help", "<HTML><strong>L�schen alter Statistikdaten</strong><br><br>Das System sammelt Kompatibilit�tsdaten "
            + "der Partnersysteme und kann diese als Statistik darstellen. Dies legt den Zeitrahmen fest, in dem diese Daten vorgehalten werden.</HTML>"},
        {"systemmaintenance.deleteoldlogdirs.help", "<HTML><strong>L�schen alter Logverzeichnisse</strong><br><br>"
            + "Auch wenn alte Transaktionen gel�scht wurden, lassen sich die Vorg�nge noch �ber bestehende Protokolldateien nachvollziehen. "
            + "Diese Einstellung l�scht diese Protokolldateien und auch alle Dateien f�r Systemereignisse, die in den gleichen Zeitraum fallen.</HTML>"},
        {"label.colorblindness", "Unterst�tzung f�r Farbblindheit"},
        {"warning.clientrestart.required", "Die Client Einstellungen wurden ge�ndert - bitte starten Sie den Client neu, damit sie g�ltig werden"},
        {"warning.serverrestart.required", "Bitte starten Sie den Server neu, damit diese �nderungen g�ltig werden."},
        {"label.darkmode", "Dunkler Modus"},
        {"label.litemode", "Heller Modus"},
        {"label.trustallservercerts", "TLS: Allen Endserverzertifikaten Ihrer AS2 Partner vertrauen"},
        {"label.trustallservercerts.help", "<HTML><strong>TLS: Allen Endserverzertifikaten Ihrer AS2 Partner vertrauen</strong><br><br>"
            + "Normalerweise ist es f�r TLS erforderlich, alle Zertifikate der Trust Chain des AS2 Systems Ihres Partners in Ihrem TLS Zertifikatsmanager "
            + "zu halten. Wenn Sie diese Option aktivieren, vertrauen Sie beim ausgehenden Verbindungsaufbau dem Endzertifikat Ihres Partnersystems, wenn Sie nur die zugeh�rigen Stamm- und Zwischenzertifikate im TLS Zertifikatsmanager vorhalten. "
            + "Bitte beachten Sie, dass diese Option nur sinnvoll ist, wenn Ihr Partner ein beglaubigtes Zertifikat verwendet - selbst signierte Zertifikate werden ohnehin akzeptiert."
            + "<br><br><strong>Warnung:</strong> Die Aktivierung dieser Option senkt das Sicherheitsniveau, da Man-in-the-Middle Angriffe m�glich sind!" + "</HTML>"},
        {"label.stricthostcheck", "TLS: Strikte Pr�fung des Hostnames"},
        {"label.stricthostcheck.help", "<HTML><strong>TLS: Strikte Pr�fung des Hostnames</strong><br><br>"
            + "Hiermit stellen Sie ein, ob im Falle einer ausgehenden TLS Verbindung gepr�ft werden soll, ob der Common Name "
            + "(CN) des entfernten Zertifikats mit dem entfernten Host �bereinstimmt. Diese Pr�fung gilt nur f�r "
            + "beglaubigte Zertifikate."
            + "</HTML>"},
        {"label.httpport", "HTTP Eingangsport"},
        {"label.httpport.help", "<HTML><strong>HTTP Eingangsport</strong><br><br>"
            + "Dies ist der Port f�r eingehende unverschl�sselte Verbindungen. Diese Einstellung wird an den eingebetteten HTTP Server weiter gegeben, "
            + "Sie m�ssen nach einer �nderung den AS2 Server neu starten.<br>"
            + "Der Port ist Teil der URL, an die Ihr Partner AS2 Nachrichten senden muss. Dies ist http://Host:<strong>Port</strong>/as2/HttpReceiver."
            + "</HTML>"
        },
        {"label.httpsport", "HTTPS Eingangsport"},
        {"label.httpsport.help", "<HTML><strong>HTTPS Eingangsport</strong><br><br>"
            + "Dies ist der Port f�r eingehende verschl�sselte Verbindungen (TLS). "
            + "Diese Einstellung wird an den eingebetteten HTTP Server weiter gegeben, "
            + "Sie m�ssen nach einer �nderung den AS2 Server neu starten.<br>"
            + "Der Port ist Teil der URL, an die Ihr Partner AS2 Nachrichten senden muss. Dies ist https://Host:<strong>Port</strong>/as2/HttpReceiver."
            + "</HTML>"
        },
        {"embedded.httpconfig.not.available", "HTTP Server nicht verf�gbar oder Zugriffsprobleme auf Konfigurationsdatei"},};

}
