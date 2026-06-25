//$Header: /mec_as2/de/mendelson/util/systemevents/notification/SystemEventNotificationControllerImplAS2.java 22    15/04/26 12:44 Heller $
package de.mendelson.util.systemevents.notification;

import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.oauth2.OAuth2Util;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Checks the database and switches certificates if there are two available for
 * a partner
 *
 * @author S.Heller
 * @version $Revision: 22 $
 */
public class SystemEventNotificationControllerImplAS2 extends SystemEventNotificationController {

    private final NotificationAccessDB notificationAccessDB;
    private final IDBDriverManager dbDriverManager;

    /**
     * Controller that checks notifications and sends them out if required
     *
     */
    public SystemEventNotificationControllerImplAS2(Logger logger,
            IDBDriverManager dbDriverManager) {
        super(logger);
        this.dbDriverManager = dbDriverManager;
        this.notificationAccessDB = new NotificationAccessDBImplAS2(dbDriverManager);
    }

    @Override
    public String getStorageDir() {
        return (AS2Server.LOG_DIR.toAbsolutePath().toString());
    }

    @Override
    public List<SystemEvent> filterEventsForNotification(List<SystemEvent> foundSystemEvents) {
        List<SystemEvent> filteredEventsForNotification = new ArrayList<SystemEvent>();
        if (!AS2Server.inShutdownProcess) {
            NotificationDataImplAS2 notificationData = (NotificationDataImplAS2) this.notificationAccessDB.getNotificationData();
            for (SystemEvent event : foundSystemEvents) {
                if (event.getOrigin() == SystemEvent.Origin.TRANSACTION
                        && event.getType() == SystemEvent.Type.TRANSACTION_ERROR) {
                    //Transaction failures
                    if (notificationData.getNotifyTransactionError()) {
                        filteredEventsForNotification.add(event);
                    }
                } else if (event.getOrigin() == SystemEvent.Origin.TRANSACTION
                        && event.getType() == SystemEvent.Type.CONNECTIVITY_ANY) {
                    //connection problem
                    if (notificationData.isNotifyConnectionProblem()) {
                        filteredEventsForNotification.add(event);
                    }
                } else if (event.getOrigin() == SystemEvent.Origin.TRANSACTION
                        && event.getType() == SystemEvent.Type.POST_PROCESSING) {
                    //postprocessing problem
                    if (notificationData.isNotifyPostprocessingProblem()) {
                        filteredEventsForNotification.add(event);
                    }
                } else if (event.getOrigin() == SystemEvent.Origin.SYSTEM
                        && event.getType() == SystemEvent.Type.CERTIFICATE_EXPIRE) {
                    //certificate expire
                    if (notificationData.getNotifyCertExpire()) {
                        filteredEventsForNotification.add(event);
                    }
                } else if (event.getType() == SystemEvent.Type.CERTIFICATE_EXCHANGE_ANY
                        || event.getType() == SystemEvent.Type.CERTIFICATE_EXCHANGE_REQUEST_RECEIVED) {
                    //certificate exchange event
                    if (notificationData.getNotifyCEM()) {
                        filteredEventsForNotification.add(event);
                    }
                } else if (event.getType() == SystemEvent.Type.TRANSACTION_REJECTED_RESEND) {
                    //rejected resend
                    if (notificationData.isNotifyResendDetected()) {
                        filteredEventsForNotification.add(event);
                    }
                } else if (event.getOrigin() == SystemEvent.Origin.SYSTEM
                        && event.getType() == SystemEvent.Type.CLIENT_ANY) {
                    //client-server problem
                    if (notificationData.isNotifyClientServerProblem()) {
                        filteredEventsForNotification.add(event);
                    }
                } else if (event.getSeverity() == SystemEvent.Severity.ERROR
                        && event.getOrigin() == SystemEvent.Origin.SYSTEM) {
                    //system error
                    if (notificationData.isNotifySystemFailure()) {
                        filteredEventsForNotification.add(event);
                    }
                } else if (event.getType() == SystemEvent.Type.LICENSE_EXPIRE) {
                    //license expire - always try to notify the user, there is currently no way to prevent this
                    //notification
                    filteredEventsForNotification.add(event);
                }
            }
        }
        return (filteredEventsForNotification);
    }

    /**
     * Finally inform the user..
     */
    @Override
    public void sendNotification(List<SystemEvent> systemEventsToNotifyUserOf) throws Throwable {
        NotificationImplAS2 notification = new NotificationImplAS2();
        NotificationData notificationData = this.notificationAccessDB.getNotificationData();
        if (notificationData.isUsesSMTPAuthOAuth2() && notificationData.getOAuth2Config() != null) {
            OAuth2Util.ensureValidAccessToken(this.dbDriverManager,
                    SystemEventManagerImplAS2.instance(),
                    notificationData.getOAuth2Config());
        }
        notification.sendNotification(systemEventsToNotifyUserOf, notificationData);
    }

}
