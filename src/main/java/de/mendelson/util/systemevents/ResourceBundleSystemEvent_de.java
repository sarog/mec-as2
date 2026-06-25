//$Header: /mec_as2/de/mendelson/util/systemevents/ResourceBundleSystemEvent_de.java 42    15/04/26 12:44 Heller $
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
 * ResourceBundle to localize the mendelson products
 *
 * @author S.Heller
 * @version $Revision: 42 $
 */
public class ResourceBundleSystemEvent_de extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"type." + SystemEvent.Type.CERTIFICATE_ADD.toInt(), "Zertifikat (hinzugefügt)"},
        {"type." + SystemEvent.Type.CERTIFICATE_ANY.toInt(), "Zertifikat"},
        {"type." + SystemEvent.Type.CERTIFICATE_DEL.toInt(), "Zertifikat (gelöscht)"},
        {"type." + SystemEvent.Type.CERTIFICATE_EXCHANGE_ANY.toInt(), "Zertifikataustausch"},
        {"type." + SystemEvent.Type.CERTIFICATE_EXCHANGE_REQUEST_RECEIVED.toInt(), "Zertifikataustausch (eingehende Anfrage)"},
        {"type." + SystemEvent.Type.CERTIFICATE_EXPIRE.toInt(), "Zertifikat läuft aus"},
        {"type." + SystemEvent.Type.CERTIFICATE_MODIFY.toInt(), "Zertifikat (Alias verändert)"},
        {"type." + SystemEvent.Type.CERTIFICATE_IMPORT_KEYSTORE.toInt(), "Zertifikat (Keystore Import)"},
        {"type." + SystemEvent.Type.CONNECTIVITY_ANY.toInt(), "Verbindung"},
        {"type." + SystemEvent.Type.CONNECTIVITY_TEST.toInt(), "Verbindungstest"},
        {"type." + SystemEvent.Type.DATABASE_ANY.toInt(), "Datenbank"},
        {"type." + SystemEvent.Type.DATABASE_CREATION.toInt(), "Datenbankerstellung"},
        {"type." + SystemEvent.Type.DATABASE_UPDATE.toInt(), "Datenbank (Update)"},
        {"type." + SystemEvent.Type.DATABASE_INITIALIZATION.toInt(), "Datenbank (Initialisierung)"},
        {"type." + SystemEvent.Type.NOTIFICATION_ANY.toInt(), "Benachrichtigung"},
        {"type." + SystemEvent.Type.NOTIFICATION_SEND_FAILED.toInt(), "Benachrichtigung (Versand fehlgeschlagen)"},
        {"type." + SystemEvent.Type.NOTIFICATION_SEND_SUCCESS.toInt(), "Benachrichtigung (Versand erfolgreich)"},
        {"type." + SystemEvent.Type.PARTNER_ADD.toInt(), "Partner (hinzugefügt)"},
        {"type." + SystemEvent.Type.PARTNER_DEL.toInt(), "Partner (gelöscht)"},
        {"type." + SystemEvent.Type.PARTNER_MODIFY.toInt(), "Partner (modifiziert)"},
        {"type." + SystemEvent.Type.QUOTA_ANY.toInt(), "Quota"},
        {"type." + SystemEvent.Type.QUOTA_RECEIVE_EXCEEDED.toInt(), "Quota erreicht"},
        {"type." + SystemEvent.Type.QUOTA_SEND_EXCEEDED.toInt(), "Quota erreicht"},
        {"type." + SystemEvent.Type.QUOTA_RECEIVE_EXCEEDED.toInt(), "Quota erreicht"},
        {"type." + SystemEvent.Type.SERVER_CONFIGURATION_CHANGED.toInt(), "Konfigurationsänderung"},
        {"type." + SystemEvent.Type.SERVER_CONFIGURATION_ANY.toInt(), "Konfiguration"},
        {"type." + SystemEvent.Type.SERVER_CONFIGURATION_CHECK.toInt(), "Konfigurationsprüfung"},
        {"type." + SystemEvent.Type.SERVER_COMPONENTS_ANY.toInt(), "Server Komponente"},
        {"type." + SystemEvent.Type.MAIN_SERVER_RUNNING.toInt(), "Server läuft"},
        {"type." + SystemEvent.Type.MAIN_SERVER_SHUTDOWN.toInt(), "Server heruntergefahren"},
        {"type." + SystemEvent.Type.MAIN_SERVER_STARTUP_BEGIN.toInt(), "Serverstart"},
        {"type." + SystemEvent.Type.DATABASE_SERVER_STARTUP_BEGIN.toInt(), "DB Server startet"},
        {"type." + SystemEvent.Type.DATABASE_SERVER_RUNNING.toInt(), "DB Server läuft"},
        {"type." + SystemEvent.Type.DATABASE_SERVER_SHUTDOWN.toInt(), "DB Server heruntergefahren"},
        {"type." + SystemEvent.Type.DATABASE_ROLLBACK.toInt(), "Transaktion Rollback"},
        {"type." + SystemEvent.Type.HTTP_SERVER_STARTUP_BEGIN.toInt(), "HTTP Server startet"},
        {"type." + SystemEvent.Type.HTTP_SERVER_RUNNING.toInt(), "HTTP Server läuft"},
        {"type." + SystemEvent.Type.HTTP_SERVER_SHUTDOWN.toInt(), "HTTP Server heruntergefahren"},
        {"type." + SystemEvent.Type.TRFC_SERVER_STARTUP_BEGIN.toInt(), "TRFC Server startet"},
        {"type." + SystemEvent.Type.TRFC_SERVER_RUNNING.toInt(), "TRFC Server läuft"},
        {"type." + SystemEvent.Type.TRFC_SERVER_SHUTDOWN.toInt(), "TRFC Server heruntergefahren"},
        {"type." + SystemEvent.Type.TRFC_SERVER_STATE.toInt(), "TRFC Server Status"},
        {"type." + SystemEvent.Type.SCHEDULER_SERVER_STARTUP_BEGIN.toInt(), "Scheduler startet"},      
        {"type." + SystemEvent.Type.SCHEDULER_SERVER_RUNNING.toInt(), "Scheduler läuft"},      
        {"type." + SystemEvent.Type.SCHEDULER_SERVER_SHUTDOWN.toInt(), "Scheduler heruntergefahren"},      
        {"type." + SystemEvent.Type.TRANSACTION_ANY.toInt(), "Transaktion"},
        {"type." + SystemEvent.Type.TRANSACTION_ERROR.toInt(), "Transaktionsfehler"},
        {"type." + SystemEvent.Type.TRANSACTION_REJECTED_RESEND.toInt(), "Transaktion (erneute Zustellung zurückgewiesen)"},
        {"type." + SystemEvent.Type.TRANSACTION_DUPLICATE_MESSAGE.toInt(), "Transaktion (doppelte Nachricht)"},
        {"type." + SystemEvent.Type.TRANSACTION_DELETE.toInt(), "Transaktion (löschen)"},
        {"type." + SystemEvent.Type.TRANSACTION_CANCEL.toInt(), "Transaktion (abbrechen)"},
        {"type." + SystemEvent.Type.TRANSACTION_RESEND.toInt(), "Transaktion (erneut senden)"},
        {"type." + SystemEvent.Type.PROCESSING_ANY.toInt(), "Datenverarbeitung"},
        {"type." + SystemEvent.Type.PRE_PROCESSING.toInt(), "Vorverarbeitung"},
        {"type." + SystemEvent.Type.POST_PROCESSING.toInt(), "Nachverarbeitung"},
        {"type." + SystemEvent.Type.LICENSE_ANY.toInt(), "Lizenz"},
        {"type." + SystemEvent.Type.LICENSE_EXPIRE.toInt(), "License Ablauf"},
        {"type." + SystemEvent.Type.LICENSE_UPDATE.toInt(), "License Aktualisierung"},
        {"type." + SystemEvent.Type.FILE_OPERATION_ANY.toInt(), "Dateioperation"},
        {"type." + SystemEvent.Type.FILE_DELETE.toInt(), "Datei (löschen)"},
        {"type." + SystemEvent.Type.FILE_MOVE.toInt(), "Datei (verschieben)"},
        {"type." + SystemEvent.Type.FILE_COPY, "Datei (kopieren)"},
        {"type." + SystemEvent.Type.FILE_MKDIR.toInt(), "Verzeichnis erstellen"},
        {"type." + SystemEvent.Type.DIRECTORY_MONITORING_STATE_CHANGED.toInt(), "Verzeichnisüberwachung (Status verändert)"},        
        {"type." + SystemEvent.Type.CLIENT_ANY.toInt(), "Client"},
        {"type." + SystemEvent.Type.CLIENT_LOGIN_FAILURE.toInt(), "Benutzeranmeldung (Fehlgeschlagen)"},
        {"type." + SystemEvent.Type.CLIENT_LOGIN_SUCCESS.toInt(), "Benutzeranmeldung (Erfolg)"},
        {"type." + SystemEvent.Type.CLIENT_LOGOFF.toInt(), "Benutzertrennung"},
        {"type." + SystemEvent.Type.OTHER.toInt(), "Unspezifiziert"},
        {"type." + SystemEvent.Type.PORT_LISTENER.toInt(), "Empfangsport"},
        {"type." + SystemEvent.Type.XML_INTERFACE_ANY.toInt(), "XML"},
        {"type." + SystemEvent.Type.XML_INTERFACE_CERTIFICATE_MODIFICATION.toInt(), "Zertifikatkonfiguration"},
        {"type." + SystemEvent.Type.XML_INTERFACE_PARTNER_MODIFICATION.toInt(), "Partnerkonfiguration"},
        {"type." + SystemEvent.Type.REST_INTERFACE_ANY.toInt(), "REST"},
        {"type." + SystemEvent.Type.REST_INTERFACE_CERTIFICATE_ADD.toInt(), "Zertifikat hinzufügen" },
        {"type." + SystemEvent.Type.REST_INTERFACE_CERTIFICATE_DEL.toInt(), "Zertifikat löschen" },
        {"type." + SystemEvent.Type.REST_INTERFACE_CERTIFICATE_MODIFICATION.toInt(), "Zertifikatkonfiguration" },
        {"type." + SystemEvent.Type.REST_INTERFACE_PARTNER_ADD.toInt(), "Partner hinzufügen" },
        {"type." + SystemEvent.Type.REST_INTERFACE_PARTNER_DEL.toInt(), "Partner löschen" },
        {"type." + SystemEvent.Type.REST_INTERFACE_PARTNER_MODIFICATION.toInt(), "Partnerkonfiguration" },
        {"type." + SystemEvent.Type.REST_INTERFACE_SENDORDER.toInt(), "Sendeauftrag" },
        {"type." + SystemEvent.Type.REST_INTERFACE_TRANSACTION_DEL.toInt(), "Transaktion (löschen)" },
        {"origin." + SystemEvent.Origin.SYSTEM.toInt(), "System"},
        {"origin." + SystemEvent.Origin.TRANSACTION.toInt(), "Transaktion"},
        {"origin." + SystemEvent.Origin.USER.toInt(), "Benutzer"},
        {"severity." + SystemEvent.Severity.ERROR.toInt(), "Fehler"},
        {"severity." + SystemEvent.Severity.WARNING.toInt(), "Warnung"},
        {"severity." + SystemEvent.Severity.INFO.toInt(), "Info"},
        {"category." + SystemEvent.Category.LICENSE.toInt(), "Aktivierung"},
        {"category." + SystemEvent.Category.CERTIFICATE.toInt(), "Zertifikat"},
        {"category." + SystemEvent.Category.CONFIGURATION.toInt(), "Konfiguration"},
        {"category." + SystemEvent.Category.CONNECTIVITY.toInt(), "Verbindung"},
        {"category." + SystemEvent.Category.DATABASE.toInt(), "Datenbank"},
        {"category." + SystemEvent.Category.NOTIFICATION.toInt(), "Benachrichtigung"},
        {"category." + SystemEvent.Category.OTHER.toInt(), "Andere"},
        {"category." + SystemEvent.Category.PROCESSING.toInt(), "Datenverarbeitung"},
        {"category." + SystemEvent.Category.QUOTA.toInt(), "Kontingent"},
        {"category." + SystemEvent.Category.SERVER_COMPONENTS.toInt(), "Server Komponente"},
        {"category." + SystemEvent.Category.TRANSACTION.toInt(), "Transaktion"},
        {"category." + SystemEvent.Category.FILE_OPERATION.toInt(), "Dateioperation" },
        {"category." + SystemEvent.Category.CLIENT_OPERATION.toInt(), "Client Operation" },
        {"category." + SystemEvent.Category.XML_INTERFACE.toInt(), "XML Schnittstelle" },
        {"category." + SystemEvent.Category.REST_INTERFACE.toInt(), "REST Schnittstelle" },
    };
}
