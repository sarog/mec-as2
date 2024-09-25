//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferencesAS2_de.java 8     2/08/22 15:37 Heller $
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
 * @version $Revision: 8 $
 */
public class ResourceBundlePreferencesAS2_de extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"TRUE", "eingeschaltet" },
        {"FALSE", "ausgeschaltet" },
        {"set.to", "wurde gesetzt auf" },
        {"setting.updated", "Einstellung wurde aktualisiert" },
        {"notification.setting.updated", "Die Benachrichtigungseinstellungen wurden ver�ndert." },
        //preferences localized
        {PreferencesAS2.ASYNC_MDN_TIMEOUT, "Timeout f�r async MDN in min"},
        {PreferencesAS2.AUTH_PROXY_PASS, "HTTP Proxy Zugangsdaten (Passwort)"},
        {PreferencesAS2.AUTH_PROXY_USE, "Verwende HTTP Proxy Zugangsdaten"},
        {PreferencesAS2.AUTH_PROXY_USER, "HTTP Proxy Zugangsdaten (Benutzer)"},
        {PreferencesAS2.AUTO_LOGDIR_DELETE, "Log Verzeichnis automatisch aufr�umen"},
        {PreferencesAS2.AUTO_LOGDIR_DELETE_OLDERTHAN, "Log Verzeichnis aufr�umen (�lter als)"},
        {PreferencesAS2.AUTO_MSG_DELETE, "Alte Transaktionen automatisch l�schen"},
        {PreferencesAS2.AUTO_MSG_DELETE_LOG, "Alte Transaktionen l�schen (Logeintrag)"},
        {PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN, "Alte Transaktionen l�schen (�lter als)"},
        {PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S, "Alte Transaktionen l�schen (Zeiteinheit in s)"},
        {PreferencesAS2.AUTO_STATS_DELETE, "Alte Statistikdaten automatisch l�schen"},
        {PreferencesAS2.AUTO_STATS_DELETE_OLDERTHAN, "Statistik l�schen (�lter als)"},
        {PreferencesAS2.CEM, "CEM verwenden"},
        {PreferencesAS2.COLOR_BLINDNESS, "Unterst�tzung f�r Farbenblindheit"},
        {PreferencesAS2.COMMUNITY_EDITION, "Community edition"},
        {PreferencesAS2.CONNECTION_RETRY_WAIT_TIME_IN_S, "Erneuter Verbindungsaufbau all n Sek"},
        {PreferencesAS2.COUNTRY, "Land"},
        {PreferencesAS2.DATASHEET_RECEIPT_URL, "Empfangs URL f�rs Datenblatt"},
        {PreferencesAS2.DIR_MSG, "Basisverzeichnis f�r Nachrichten"},
        {PreferencesAS2.HTTP_SEND_TIMEOUT, "Sende Timeout (HTTP/S)"},
        {PreferencesAS2.KEYSTORE, "Interne Verschl�sselungs-/Signatur Schl�sseldatei"},
        {PreferencesAS2.KEYSTORE_HTTPS_SEND, "Interne TLS Schl�sseldatei"},
        {PreferencesAS2.KEYSTORE_HTTPS_SEND_PASS, "Interne TLS Schl�sseldatei (Passwort)"},
        {PreferencesAS2.KEYSTORE_PASS, "Interne Verschl�sselungs-/Signatur Schl�sseldatei (Passwort)"},
        {PreferencesAS2.LANGUAGE, "Client Sprache"},
        {PreferencesAS2.LAST_UPDATE_CHECK, "Letze Pr�fung auf neue Version (unix time)"},
        {PreferencesAS2.LOG_POLL_PROCESS, "Poll Prozess im Protokoll documentieren"},
        {PreferencesAS2.MAX_CONNECTION_RETRY_COUNT, "Anzahl der Verbindungsversuche"},
        {PreferencesAS2.MAX_OUTBOUND_CONNECTIONS, "Max Menge gleichzeitig ausgehender Verbindungen"},
        {PreferencesAS2.MAX_INBOUND_CONNECTIONS, "Max Menge gleichzeitig eingehender Verbindungen"},  
        {PreferencesAS2.PROXY_HOST, "HTTP Proxy Host"},
        {PreferencesAS2.PROXY_PORT, "HTTP Proxy Port"},
        {PreferencesAS2.PROXY_USE, "Verwende HTTP Proxy f�r ausgehende Verbindung"},
        {PreferencesAS2.RECEIPT_PARTNER_SUBDIR, "Verwende Unterverzeichnis pro Partner"},
        {PreferencesAS2.SHOW_HTTPHEADER_IN_PARTNER_CONFIG, "HTTP Header Verwaltung im Client anzeigen"},
        {PreferencesAS2.SHOW_QUOTA_NOTIFICATION_IN_PARTNER_CONFIG, "Quota in Partnerverwaltung anzeigen"},
        {PreferencesAS2.WRITE_OUTBOUND_STATUS_FILE, "Statusdatei f�r jede Transaktion erstellen"},       
        {PreferencesAS2.TLS_TRUST_ALL_REMOTE_SERVER_CERTIFICATES, "(TLS) Allen entfernten Serverzertifikaten vertrauen"},  
        {PreferencesAS2.TLS_STRICT_HOST_CHECK, "(TLS) Host pr�fen"},
        {PreferencesAS2.HTTPS_LISTEN_PORT, "HTTPS Eingangsport"},
        {PreferencesAS2.HTTP_LISTEN_PORT, "HTTP Eingangsport"},   
    };
}
