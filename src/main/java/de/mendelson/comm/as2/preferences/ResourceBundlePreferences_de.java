//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferences_de.java 40    27.09.18 13:21 Heller $
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
 * @version $Revision: 40 $
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
        {PreferencesAS2.SERVER_HOST, "Server host"},
        {PreferencesAS2.DIR_MSG, "Nachrichtenverzeichnis"},
        {"button.ok", "Ok"},
        {"button.cancel", "Abbrechen"},
        {"button.modify", "Bearbeiten"},
        {"button.browse", "Durchsuchen"},
        {"filechooser.selectdir", "Bitte w�hlen Sie das zu setzene Verzeichnis"},
        {"title", "Einstellungen"},
        {"tab.language", "Sprache"},
        {"tab.dir", "Verzeichnisse"},
        {"tab.security", "Sicherheit"},
        {"tab.proxy", "Proxy"},
        {"tab.misc", "Allgemein"},
        {"tab.maintenance", "Systempflege"},
        {"tab.notification", "Benachrichtigungen"},
        {"tab.interface", "Module"},
        {"tab.log", "Log"},
        {"header.dirname", "Typ"},
        {"header.dirvalue", "Verzeichnis"},
        {"label.keystore.https.pass", "Keystore Passwort (zum Senden via Https):"},
        {"label.keystore.pass", "Keystore Password (Verschl�sselung/digitale Signatur):"},
        {"label.keystore.https", "Keystore (zum Senden via Https):"},
        {"label.keystore.encryptionsign", "Keystore( Verschl., Signatur):"},
        {"label.proxy.url", "Proxy URL:"},
        {"label.proxy.user", "Proxy Login Benutzer:"},
        {"label.proxy.pass", "Proxy Login Passwort:"},
        {"label.proxy.use", "Proxy f�r ausgehende HTTP/HTTPs Verbindungen benutzen"},
        {"label.proxy.useauthentification", "Authentifizierung f�r Proxy benutzen"},
        {"filechooser.keystore", "Bitte w�hlen Sie die Keystore Datei (JKS Format)."},
        {"label.days", "Tage"},
        {"label.deletemsgolderthan", "Automatisches L�schen von Nachrichten, die �lter sind als"},
        {"label.deletemsglog", "Logeintr�ge f�r automatisch gel�schte Nachrichten"},
        {"label.deletestatsolderthan", "Automatisches L�schen von Statistikdaten, die �lter sind als"},
        {"label.asyncmdn.timeout", "Maximale Wartezeit auf asynchrone MDNs:"},
        {"label.httpsend.timeout", "HTTP(s) Sende-Timeout:"},
        {"label.min", "min"},
        {"receipt.subdir", "Unterverzeichnisse pro Partner f�r Nachrichtenempfang anlegen"},
        //notification
        {"checkbox.notifycertexpire", "Vor dem Auslaufen von Zertifikaten"},
        {"checkbox.notifytransactionerror", "Nach Fehlern in Transaktionen"},
        {"checkbox.notifycem", "Ereignisse beim Zertifikataustausch (CEM)"},
        {"checkbox.notifyfailure", "Nach Systemproblemen"},
        {"checkbox.notifyresend", "Nach abgewiesenen Resends"},
        {"button.testmail", "Sende Test Mail"},
        {"label.mailhost", "Mailserver (SMTP):"},
        {"label.mailport", "Port:"},
        {"label.mailaccount", "Mailserver Account:"},
        {"label.mailpass", "Mailserver Passwort:"},
        {"label.notificationmail", "Benachrichtigungsempf�nger eMail:"},
        {"label.replyto", "Replyto Addresse:"},
        {"label.smtpauthentication", "SMTP Authentifizierung benutzen"},
        {"label.smtpauthentication.user", "Benutzername:"},
        {"label.smtpauthentication.pass", "Passwort:"},
        {"label.security", "Verbindungssicherheit:"},
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
        {"label.retry.waittime", "Wartezeit zwischen Verbindungsaufbauversuchen"},
        {"label.sec", "s"},
        {"keystore.hint", "Achtung:\nBitte �ndern Sie diese Parameter nur, wenn Sie externe Keystores einbinden m�chten oder Sie �ber ein externes Programm die Passw�rter der unterliegenden Keystore Dateien modifiziert haben (was nicht empfehlenswert ist!). Wenn Sie diese Einstellungen �ndern, werden nicht automatisch die Pfade der unterliegenden Keystores oder deren Passw�rter angepasst."},
        {"maintenancemultiplier.day", "Tag(e)"},
        {"maintenancemultiplier.hour", "Stunde(n)"},
        {"maintenancemultiplier.minute", "Minute(n)"},
        {"label.logpollprocess", "Informationen zum Pollprozes im Log anzeigen (Viele Eintr�ge - nicht im Produktivbetrieb verwenden)"},
        {"label.max.outboundconnections", "Max ausgehende parallele Verbindungen"},
        {"event.preferences.modified.subject", "Der Wert {0} der Servereinstellungen wurde modifiziert"},
        {"event.preferences.modified.body", "Alter Wert: {0}\nNeuer Wert: {1}"},
        {"event.notificationdata.modified.subject", "Die Einstellungen zur Benachrichtigung wurden ver�ndert"},
        {"event.notificationdata.modified.body", "Die Benachrichtigungsdaten wurden von\n\n{0}\n\nnach\n\n{1}\n\n ver�ndert." },
        {"label.maxmailspermin", "Max Anzahl von Benachrichtigungen/min:"},
    };

}
