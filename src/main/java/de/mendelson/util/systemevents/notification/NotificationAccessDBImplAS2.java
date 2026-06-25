//$Header: /mec_as2/de/mendelson/util/systemevents/notification/NotificationAccessDBImplAS2.java 27    15/04/26 12:44 Heller $
package de.mendelson.util.systemevents.notification;

import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.oauth2.OAuth2AccessDB;
import de.mendelson.util.oauth2.OAuth2Config;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores the notification data for the AS2
 *
 * @author S.Heller
 * @version $Revision: 27 $
 */
public class NotificationAccessDBImplAS2 implements NotificationAccessDB {

    private final IDBDriverManager dbDriverManager;

    public NotificationAccessDBImplAS2(IDBDriverManager dbDriverManager) {
        this.dbDriverManager = dbDriverManager;
    }

    /**
     * Reads the notification data from the db, there is only one available
     */
    @Override
    public NotificationData getNotificationData() {
        NotificationDataImplAS2 notificationData = null;
        String transactionName = "NotificationAccess_getNotificationData";
        try (Connection configConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG)) {
            configConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = configConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                this.dbDriverManager.setTableLockREAD(transactionStatement,
                        new String[]{
                            "oauth2", "notification"});
                try {
                    try (PreparedStatement statement 
                            = configConnectionNoAutoCommit.prepareStatement(
                            "SELECT * FROM notification")) {
                        try (ResultSet result = statement.executeQuery()) {
                            if (result.next()) {
                                notificationData = new NotificationDataImplAS2();
                                notificationData.setMailServer(result.getString("mailhost"));
                                notificationData.setMailServerPort(result.getInt("mailhostport"));
                                notificationData.setNotificationMail(result.getString("notificationemailaddress"));
                                notificationData.setNotifyCertExpire(result.getInt("notifycertexpire") == 1);
                                notificationData.setNotifyTransactionError(result.getInt("notifytransactionerror") == 1);
                                notificationData.setNotifyCEM(result.getInt("notifycem") == 1);
                                notificationData.setNotifySystemFailure(result.getInt("notifysystemfailure") == 1);
                                notificationData.setNotifyResendDetected(result.getInt("notifyresend") == 1);
                                notificationData.setReplyTo(result.getString("replyto"));
                                notificationData.setUsesSMTPAuthCredentials(result.getInt("usesmtpauth") == 1);
                                notificationData.setSMTPUser(result.getString("smtpauthuser"));
                                String smtpPass = result.getString("smtpauthpass");
                                if (!result.wasNull()) {
                                    notificationData.setSMTPPass(smtpPass.toCharArray());
                                }
                                notificationData.setUsesSMTPAuthOAuth2(result.getInt("usesmtpoauth2") == 1);
                                notificationData.setConnectionSecurity(result.getInt("security"));
                                notificationData.setMaxNotificationsPerMin(result.getInt("maxnotificationspermin"));
                                notificationData.setNotifyConnectionProblem(result.getInt("notifyconnectionproblem") == 1);
                                notificationData.setNotifyPostprocessingProblem(result.getInt("notifypostprocessing") == 1);
                                int oAuth2Id = result.getInt("smtpoauth2id");
                                if (!result.wasNull()) {
                                    OAuth2AccessDB oauth2Access 
                                            = new OAuth2AccessDB(this.dbDriverManager, SystemEventManagerImplAS2.instance());
                                    OAuth2Config config = oauth2Access.getOAuth2Config(oAuth2Id, configConnectionNoAutoCommit);
                                    if (config != null) {
                                        notificationData.setOAuth2Config(config);
                                    } else {
                                        notificationData.setUsesSMTPAuthOAuth2(false);
                                    }
                                } else {
                                    notificationData.setUsesSMTPAuthOAuth2(false);
                                }
                                notificationData.setNotifyClientServerProblem(result.getInt("notifyclientserver") == 1 ? true : false);
                            }
                        }
                    }
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Exception e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        return (notificationData);
    }

    /**
     * Inserts a new message entry into the database
     */
    @Override
    public void updateNotification(NotificationData notificationData) {
        NotificationDataImplAS2 data = (NotificationDataImplAS2) notificationData;
        String transactionName = "Notification_updateNotification";
        try (Connection configConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG)) {
            configConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = configConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                this.dbDriverManager.setTableLockINSERTAndUPDATE(transactionStatement,
                        new String[]{
                            "oauth2", "notification"});
                try {
                    if (data.getOAuth2Config() != null) {
                        OAuth2AccessDB oauth2Access = new OAuth2AccessDB(this.dbDriverManager, SystemEventManagerImplAS2.instance());
                        oauth2Access.insertOrUpdateOAuth2(data.getOAuth2Config(), configConnectionNoAutoCommit);
                    }
                    try (PreparedStatement statement = configConnectionNoAutoCommit.prepareStatement(
                            "UPDATE notification SET mailhost=?,mailhostport=?,notificationemailaddress=?,"
                            + "notifycertexpire=?,notifytransactionerror=?,notifycem=?,notifysystemfailure=?,replyto=?,usesmtpauth=?,"
                            + "smtpauthuser=?,smtpauthpass=?,notifyresend=?,security=?,maxnotificationspermin=?,notifyconnectionproblem=?,"
                            + "notifypostprocessing=?,usesmtpoauth2=?,smtpoauth2id=?,notifyclientserver=?")) {
                        statement.setString(1, data.getMailServer());
                        statement.setInt(2, data.getMailServerPort());
                        statement.setString(3, data.getNotificationMail());
                        statement.setInt(4, data.getNotifyCertExpire() ? 1 : 0);
                        statement.setInt(5, data.getNotifyTransactionError() ? 1 : 0);
                        statement.setInt(6, data.getNotifyCEM() ? 1 : 0);
                        statement.setInt(7, data.isNotifySystemFailure() ? 1 : 0);
                        statement.setString(8, data.getReplyTo());
                        statement.setInt(9, data.isUsesSMTPAuthCredentials() ? 1 : 0);
                        if (data.getSMTPUser() != null) {
                            statement.setString(10, data.getSMTPUser());
                        } else {
                            statement.setNull(10, Types.VARCHAR);
                        }
                        if (data.getSMTPPass() != null) {
                            statement.setString(11, String.valueOf(data.getSMTPPass()));
                        } else {
                            statement.setNull(11, Types.VARCHAR);
                        }
                        statement.setInt(12, data.isNotifyResendDetected() ? 1 : 0);
                        statement.setInt(13, data.getConnectionSecurity());
                        statement.setInt(14, data.getMaxNotificationsPerMin());
                        statement.setInt(15, data.isNotifyConnectionProblem() ? 1 : 0);
                        statement.setInt(16, data.isNotifyPostprocessingProblem() ? 1 : 0);
                        statement.setInt(17, data.isUsesSMTPAuthOAuth2() ? 1 : 0);
                        if (data.getOAuth2Config() != null) {
                            statement.setInt(18, data.getOAuth2Config().getDBId());
                        } else {
                            statement.setNull(18, Types.INTEGER);
                        }
                        statement.setInt(19, data.isNotifyClientServerProblem() ? 1 : 0);
                        statement.executeUpdate();
                    }
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
    }

}
