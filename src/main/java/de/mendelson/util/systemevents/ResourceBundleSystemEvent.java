//$Header: /mec_as2/de/mendelson/util/systemevents/ResourceBundleSystemEvent.java 37    15/04/26 12:44 Heller $
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
 * @version $Revision: 37 $
 */
public class ResourceBundleSystemEvent extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"type." + SystemEvent.Type.CERTIFICATE_ADD.toInt(), "Certificate (add)"},
        {"type." + SystemEvent.Type.CERTIFICATE_ANY.toInt(), "Certificate"},
        {"type." + SystemEvent.Type.CERTIFICATE_DEL.toInt(), "Certificate (delete)"},
        {"type." + SystemEvent.Type.CERTIFICATE_EXCHANGE_ANY.toInt(), "Certificate (exchange)"},
        {"type." + SystemEvent.Type.CERTIFICATE_EXCHANGE_REQUEST_RECEIVED.toInt(), "Certificate (inbound exchange request)"},
        {"type." + SystemEvent.Type.CERTIFICATE_IMPORT_KEYSTORE.toInt(), "Certificate (Full keystore import)"},
        {"type." + SystemEvent.Type.CERTIFICATE_EXPIRE.toInt(), "Certificate (expire)"},
        {"type." + SystemEvent.Type.CERTIFICATE_MODIFY.toInt(), "Certificate (alias modified)"},
        {"type." + SystemEvent.Type.CONNECTIVITY_ANY.toInt(), "Connectivity"},
        {"type." + SystemEvent.Type.CONNECTIVITY_TEST.toInt(), "Connection test"},
        {"type." + SystemEvent.Type.DATABASE_ANY.toInt(), "Database"},
        {"type." + SystemEvent.Type.DATABASE_CREATION.toInt(), "Database (creation)"},
        {"type." + SystemEvent.Type.DATABASE_UPDATE.toInt(), "Database (update)"},
        {"type." + SystemEvent.Type.DATABASE_INITIALIZATION.toInt(), "Database (initialization)"},
        {"type." + SystemEvent.Type.DATABASE_ROLLBACK.toInt(), "Transation rollback"},
        {"type." + SystemEvent.Type.NOTIFICATION_ANY.toInt(), "Notification"},
        {"type." + SystemEvent.Type.NOTIFICATION_SEND_FAILED.toInt(), "Notification send (failed)"},
        {"type." + SystemEvent.Type.NOTIFICATION_SEND_SUCCESS.toInt(), "Notification send (success)"},
        {"type." + SystemEvent.Type.PARTNER_ADD.toInt(), "Partner (add)"},
        {"type." + SystemEvent.Type.PARTNER_DEL.toInt(), "Partner (delete)"},
        {"type." + SystemEvent.Type.PARTNER_MODIFY.toInt(), "Partner (modify)"},
        {"type." + SystemEvent.Type.QUOTA_ANY, "Quota"},
        {"type." + SystemEvent.Type.QUOTA_RECEIVE_EXCEEDED.toInt(), "Quota exceeded"},
        {"type." + SystemEvent.Type.QUOTA_SEND_EXCEEDED.toInt(), "Quota exceeded"},
        {"type." + SystemEvent.Type.QUOTA_RECEIVE_EXCEEDED.toInt(), "Quota exceeded"},
        {"type." + SystemEvent.Type.SERVER_CONFIGURATION_CHANGED.toInt(), "Configuration changed"},
        {"type." + SystemEvent.Type.SERVER_CONFIGURATION_ANY.toInt(), "Configuration"},
        {"type." + SystemEvent.Type.SERVER_CONFIGURATION_CHECK.toInt(), "Configuration check"},
        {"type." + SystemEvent.Type.SERVER_COMPONENTS_ANY.toInt(), "Server component"},
        {"type." + SystemEvent.Type.MAIN_SERVER_RUNNING.toInt(), "Server is running"},
        {"type." + SystemEvent.Type.MAIN_SERVER_SHUTDOWN.toInt(), "Server shutdown"},
        {"type." + SystemEvent.Type.MAIN_SERVER_STARTUP_BEGIN.toInt(), "Server startup"},
        {"type." + SystemEvent.Type.DATABASE_SERVER_STARTUP_BEGIN.toInt(), "DB server startup"},
        {"type." + SystemEvent.Type.DATABASE_SERVER_RUNNING.toInt(), "DB server is running"},
        {"type." + SystemEvent.Type.DATABASE_SERVER_SHUTDOWN.toInt(), "DB server shutdown"},        
        {"type." + SystemEvent.Type.HTTP_SERVER_STARTUP_BEGIN.toInt(), "HTTP server startup"},
        {"type." + SystemEvent.Type.HTTP_SERVER_RUNNING.toInt(), "HTTP server is running"},
        {"type." + SystemEvent.Type.HTTP_SERVER_SHUTDOWN.toInt(), "HTTP server shutdown"},
        {"type." + SystemEvent.Type.TRFC_SERVER_STARTUP_BEGIN.toInt(), "TRFC server startup"},
        {"type." + SystemEvent.Type.TRFC_SERVER_RUNNING.toInt(), "TRFC server is running"},
        {"type." + SystemEvent.Type.TRFC_SERVER_SHUTDOWN.toInt(), "TRFC server shutdown"},
        {"type." + SystemEvent.Type.TRFC_SERVER_STATE.toInt(), "TRFC server state"},      
        {"type." + SystemEvent.Type.SCHEDULER_SERVER_STARTUP_BEGIN.toInt(), "Scheduler server startup"},      
        {"type." + SystemEvent.Type.SCHEDULER_SERVER_RUNNING.toInt(), "Scheduler server running"},      
        {"type." + SystemEvent.Type.SCHEDULER_SERVER_SHUTDOWN.toInt(), "Scheduler server shutdown"},      
        {"type." + SystemEvent.Type.TRANSACTION_ANY.toInt(), "Transaction"},
        {"type." + SystemEvent.Type.TRANSACTION_ERROR.toInt(), "Transaction (error)"},
        {"type." + SystemEvent.Type.TRANSACTION_REJECTED_RESEND.toInt(), "Transaction (rejected resend)"},
        {"type." + SystemEvent.Type.TRANSACTION_DUPLICATE_MESSAGE.toInt(), "Transaction (duplicate message)"},
        {"type." + SystemEvent.Type.TRANSACTION_DELETE.toInt(), "Transaction (delete)"},
        {"type." + SystemEvent.Type.TRANSACTION_CANCEL.toInt(), "Transaction (cancel)"},
        {"type." + SystemEvent.Type.TRANSACTION_RESEND.toInt(), "Transaction (resend)"},
        {"type." + SystemEvent.Type.PROCESSING_ANY.toInt(), "Data processing"},
        {"type." + SystemEvent.Type.PRE_PROCESSING.toInt(), "Preprocessing"},
        {"type." + SystemEvent.Type.POST_PROCESSING.toInt(), "Postprocessing"},
        {"type." + SystemEvent.Type.LICENSE_ANY.toInt(), "License"},
        {"type." + SystemEvent.Type.LICENSE_EXPIRE.toInt(), "License expire"},
        {"type." + SystemEvent.Type.LICENSE_UPDATE.toInt(), "License update"},
        {"type." + SystemEvent.Type.FILE_OPERATION_ANY.toInt(), "File operation"},
        {"type." + SystemEvent.Type.FILE_DELETE.toInt(), "File (delete)"},
        {"type." + SystemEvent.Type.FILE_MOVE.toInt(), "File (move)"},
        {"type." + SystemEvent.Type.FILE_COPY.toInt(), "File (copy)"},
        {"type." + SystemEvent.Type.FILE_MKDIR.toInt(), "Create dir"},
        {"type." + SystemEvent.Type.DIRECTORY_MONITORING_STATE_CHANGED.toInt(), "Directory monitoring state changed"},
        {"type." + SystemEvent.Type.CLIENT_ANY.toInt(), "Client (any)"},
        {"type." + SystemEvent.Type.CLIENT_LOGIN_FAILURE.toInt(), "Client login (failure)"},
        {"type." + SystemEvent.Type.CLIENT_LOGIN_SUCCESS.toInt(), "Client login (success)"},
        {"type." + SystemEvent.Type.CLIENT_LOGOFF.toInt(), "Client logoff"},
        {"type." + SystemEvent.Type.OTHER.toInt(), "Other"},
        {"type." + SystemEvent.Type.PORT_LISTENER.toInt(), "Port listener"},
        {"type." + SystemEvent.Type.XML_INTERFACE_ANY.toInt(), "XML"},
        {"type." + SystemEvent.Type.XML_INTERFACE_CERTIFICATE_MODIFICATION.toInt(), "Certificate modification"},
        {"type." + SystemEvent.Type.XML_INTERFACE_PARTNER_MODIFICATION.toInt(), "Partner modification"},
        {"type." + SystemEvent.Type.REST_INTERFACE_ANY.toInt(), "REST"},
        {"type." + SystemEvent.Type.REST_INTERFACE_CERTIFICATE_ADD.toInt(), "Certificate add" },
        {"type." + SystemEvent.Type.REST_INTERFACE_CERTIFICATE_DEL.toInt(), "Certificate del" },
        {"type." + SystemEvent.Type.REST_INTERFACE_CERTIFICATE_MODIFICATION.toInt(), "Certificate modification" },
        {"type." + SystemEvent.Type.REST_INTERFACE_PARTNER_ADD.toInt(), "Partner add" },
        {"type." + SystemEvent.Type.REST_INTERFACE_PARTNER_DEL.toInt(), "Partner del" },
        {"type." + SystemEvent.Type.REST_INTERFACE_PARTNER_MODIFICATION.toInt(), "Partner modification" },
        {"type." + SystemEvent.Type.REST_INTERFACE_SENDORDER.toInt(), "Send order" },
        {"type." + SystemEvent.Type.REST_INTERFACE_TRANSACTION_DEL.toInt(), "Transaction (delete)" },
        {"origin." + SystemEvent.Origin.SYSTEM.toInt(), "System" },
        {"origin." + SystemEvent.Origin.TRANSACTION.toInt(), "Transaction" },
        {"origin." + SystemEvent.Origin.USER.toInt(), "User" },
        {"severity." + SystemEvent.Severity.ERROR.toInt(), "Error"},
        {"severity." + SystemEvent.Severity.WARNING.toInt(), "Warning"},
        {"severity." + SystemEvent.Severity.INFO.toInt(), "Info"},
        {"category." + SystemEvent.Category.LICENSE.toInt(), "License" },
        {"category." + SystemEvent.Category.CERTIFICATE.toInt(), "Certificate" },
        {"category." + SystemEvent.Category.CONFIGURATION.toInt(), "Configuration" },
        {"category." + SystemEvent.Category.CONNECTIVITY.toInt(), "Connectivity" },
        {"category." + SystemEvent.Category.DATABASE.toInt(), "Database" },
        {"category." + SystemEvent.Category.NOTIFICATION.toInt(), "Notification" },
        {"category." + SystemEvent.Category.OTHER.toInt(), "Other" },
        {"category." + SystemEvent.Category.PROCESSING.toInt(), "Data processing" },
        {"category." + SystemEvent.Category.QUOTA.toInt(), "Quota" },
        {"category." + SystemEvent.Category.SERVER_COMPONENTS.toInt(), "Server components" },
        {"category." + SystemEvent.Category.TRANSACTION.toInt(), "Transaction" },
        {"category." + SystemEvent.Category.FILE_OPERATION.toInt(), "File operation" },
        {"category." + SystemEvent.Category.CLIENT_OPERATION.toInt(), "Client operation" },
        {"category." + SystemEvent.Category.XML_INTERFACE.toInt(), "XML interface" },
        {"category." + SystemEvent.Category.REST_INTERFACE.toInt(), "REST interface" },
    };
}
