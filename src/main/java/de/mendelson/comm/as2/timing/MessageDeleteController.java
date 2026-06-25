//$Header: /mec_as2/de/mendelson/comm/as2/timing/MessageDeleteController.java 63    15/04/26 12:43 Heller $
package de.mendelson.comm.as2.timing;

import de.mendelson.comm.as2.clientserver.message.RefreshClientMessageOverviewList;
import de.mendelson.comm.as2.log.LogAccessDB;
import de.mendelson.comm.as2.message.AS2Message;
import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.comm.as2.message.MessageAccessDB;
import de.mendelson.comm.as2.message.MessageStateType;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.preferences.ResourceBundlePreferences;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.AS2Tools;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.ClientServer;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Controls the timed deletion of AS2 entries from the log
 *
 * @author S.Heller
 * @version $Revision: 63 $
 */
public class MessageDeleteController {

    private static final Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private final PreferencesAS2 preferences;
    private final MessageDeleteThread deleteThread;
    private final ClientServer clientserver;
    private static final MecResourceBundle rb;
    private static final MecResourceBundle rbTime;

    static {
        //Load default resourcebundle
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleMessageDeleteController.class.getName());
        } //load up resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        try {
            rbTime = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundlePreferences.class.getName());
        } //load up resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }
    private final LogAccessDB logAccess;
    private final IDBDriverManager dbDriverManager;
    private final MessageAccessDB messageAccess;

    public MessageDeleteController(ClientServer clientserver, IDBDriverManager dbDriverManager) {
        this.clientserver = clientserver;
        this.dbDriverManager = dbDriverManager;
        this.logAccess = new LogAccessDB(dbDriverManager);
        this.messageAccess = new MessageAccessDB(this.dbDriverManager);
        this.preferences = new PreferencesAS2(dbDriverManager);
        this.deleteThread = new MessageDeleteThread();
    }

    /**
     * Starts the embedded task that guards the log
     */
    public void startAutoDeleteControl() {
        TimingScheduledThreadPool.scheduleWithFixedDelay(this.deleteThread, 1, 1, TimeUnit.MINUTES);
    }

    /**
     * Deletes a sub list of the whole delete order from the log. Needs DELETE lock on the
     * table messagelog, messages, mdn
     *
     * @param infoList
     * @param broadcastRefresh
     * @param rawFilenameListToDelete This method will add filenames here that have to be deleted later,
     * outside of the database lock
     */
    private void deleteMessageFromLogPart(List<AS2MessageInfo> infoList,
            boolean broadcastRefresh,
            Connection runtimeConnectionNoAutoCommit, 
            List<String> rawFilenameListToDelete) throws Exception {
        List<String> messageIdList = new ArrayList<String>();
        for (AS2MessageInfo messageInfo : infoList) {
            messageIdList.add(messageInfo.getMessageId());
        }
        this.logAccess.deleteMessageLog(messageIdList, runtimeConnectionNoAutoCommit);
        //delete all raw files from the disk
        List<String> rawfilenames = this.messageAccess.getRawFilenamesToDelete(messageIdList, runtimeConnectionNoAutoCommit);
        if (rawfilenames != null && !rawfilenames.isEmpty()) {
            rawFilenameListToDelete.addAll(rawfilenames);
        }
        this.messageAccess.deleteMessages(messageIdList, runtimeConnectionNoAutoCommit);
        if (broadcastRefresh && this.clientserver != null) {
            this.clientserver.broadcastToClients(new RefreshClientMessageOverviewList());
        }
    }

    /**
     * Deletes a message entry from the log. Clears all files. Main entry point
     * for a delete operation
     */
    public void deleteMessagesFromLog(List<AS2MessageInfo> infoList, boolean broadcastRefresh, StringBuilder deleteLogBuilder) {
        final List<String> rawFilenameListToDelete = new ArrayList<String>();
        int bulkSize = 10;
        String transactionname = "Message_deleteFromLog";
        try (Connection runtimeConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                //start transaction
                this.dbDriverManager.startTransaction(transactionStatement, transactionname);
                //lock tables for delete
                this.dbDriverManager.setTableLockDELETE(transactionStatement,
                        new String[]{
                            "messages",
                            "messagelog",
                            "mdn",
                            "payload"
                        });
                try {
                    List<AS2MessageInfo> subList = new ArrayList<AS2MessageInfo>();
                    for (int i = 0; i < infoList.size(); i++) {
                        subList.add(infoList.get(i));
                        if (i != 0 && i % bulkSize == 0) {
                            this.deleteMessageFromLogPart(subList, broadcastRefresh,
                                    runtimeConnectionNoAutoCommit, rawFilenameListToDelete);
                            subList.clear();
                        }
                    }
                    if (!subList.isEmpty()) {
                        this.deleteMessageFromLogPart(subList, broadcastRefresh,
                                runtimeConnectionNoAutoCommit, rawFilenameListToDelete);
                    }
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionname);
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Exception e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        //now delete all collected files, outside of the database lock.
        for (String rawfilename : rawFilenameListToDelete) {
            Path singleFilePath = Paths.get(rawfilename);
            String fileSizeStr = "";
            try {
                long fileSize = Files.size(singleFilePath);
                fileSizeStr = AS2Tools.getDataSizeDisplay(fileSize);
            } catch (Throwable e) {
            }
            try {
                Files.delete(singleFilePath);
                deleteLogBuilder.append("[")
                        .append(rb.getResourceString("delete.ok"))
                        .append("]: ");
            } catch (NoSuchFileException e) {
                deleteLogBuilder.append("[")
                        .append(rb.getResourceString("delete.skipped"))
                        .append("]: (")
                        .append(e.getClass().getSimpleName())
                        .append(")  ")
                        .append(e.getMessage());
            } catch (Throwable e) {
                deleteLogBuilder.append("[")
                        .append(rb.getResourceString("delete.failed"))
                        .append("]: (")
                        .append(e.getClass().getSimpleName())
                        .append(")  ")
                        .append(e.getMessage());
                SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.FILE_OPERATION_ANY);
                new File(rawfilename).deleteOnExit();
            }
            deleteLogBuilder.append("  ")
                    .append(Paths.get(rawfilename).toAbsolutePath().toString());
            if (fileSizeStr.length() > 0) {
                deleteLogBuilder.append("      ")
                        .append("[")
                        .append(fileSizeStr)
                        .append("]");
            }
            deleteLogBuilder.append(System.lineSeparator());
        }
    }

    public class MessageDeleteThread implements Runnable {

        public MessageDeleteThread() {
        }

        @Override
        public void run() {
            try {
                if (preferences.getBoolean(PreferencesAS2.AUTO_MSG_DELETE)) {
                    try {
                        long olderThan = System.currentTimeMillis()
                                - TimeUnit.SECONDS.toMillis(
                                        preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN)
                                        * preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S));
                        List<AS2MessageInfo> overviewList = messageAccess.getMessagesOlderThan(olderThan, -1);
                        if (overviewList != null) {
                            List<AS2MessageInfo> deleteList = new ArrayList<AS2MessageInfo>();
                            for (AS2MessageInfo messageInfo : overviewList) {
                                if (messageInfo.getState() == MessageStateType.FINISHED
                                        || messageInfo.getState() == MessageStateType.STOPPED) {
                                    if (preferences.getBoolean(PreferencesAS2.AUTO_MSG_DELETE_LOG)) {
                                        String timeUnit = "";
                                        if (preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S)
                                                == TimeUnit.DAYS.toSeconds(1)) {
                                            timeUnit = rbTime.getResourceString("maintenancemultiplier.day");
                                        } else if (preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S)
                                                == TimeUnit.HOURS.toSeconds(1)) {
                                            timeUnit = rbTime.getResourceString("maintenancemultiplier.hour");
                                        } else if (preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S)
                                                == TimeUnit.MINUTES.toSeconds(1)) {
                                            timeUnit = rbTime.getResourceString("maintenancemultiplier.minute");
                                        }
                                        logger.fine(rb.getResourceString("autodelete",
                                                new Object[]{
                                                    messageInfo.getMessageId(),
                                                    String.valueOf(preferences.getInt(PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN)),
                                                    timeUnit
                                                }));
                                    }
                                    deleteList.add(messageInfo);
                                }
                            }
                            StringBuilder deletedTransactionLog = new StringBuilder();
                            deleteMessagesFromLog(deleteList, true, deletedTransactionLog);
                            //fire system event if automatic log deletes should be loged
                            if (preferences.getBoolean(PreferencesAS2.AUTO_MSG_DELETE_LOG)) {
                                this.fireSystemEventTransactionsDeletedByMaintenance(olderThan, deleteList,
                                        deletedTransactionLog);
                            }
                        }
                    } catch (Throwable e) {
                        SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.PROCESSING_ANY);
                    }
                }
            } catch (Throwable e) {
                SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.PROCESSING_ANY);
            }
        }

        /**
         * Fire a system event that the system maintenance process has deleted
         * transactions
         */
        private void fireSystemEventTransactionsDeletedByMaintenance(long olderThan, List<AS2MessageInfo> deletedTransactionList,
                StringBuilder transactionDeleteLog) {
            //Do not fire an event if there are no deleted transactions
            if (deletedTransactionList.isEmpty()) {
                return;
            }
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            SystemEvent event = new SystemEvent(
                    SystemEvent.Severity.INFO,
                    SystemEvent.Origin.SYSTEM,
                    SystemEvent.Type.TRANSACTION_DELETE);
            event.setSubject(rb.getResourceString("transaction.deleted.system"));
            StringBuilder builder = new StringBuilder();
            builder.append(rb.getResourceString("transaction.delete.setting.olderthan",
                    dateFormat.format(new Date(olderThan))))
                    .append(System.lineSeparator()).append(System.lineSeparator());
            for (int i = 0; i < deletedTransactionList.size(); i++) {
                AS2MessageInfo singleInfo = deletedTransactionList.get(i);
                builder.append("[")
                        .append(rb.getResourceString("transaction.deleted.transactiondate",
                                dateFormat.format(singleInfo.getInitDate())))
                        .append("] (")
                        .append(singleInfo.getSenderId())
                        .append(" --> ")
                        .append(singleInfo.getReceiverId())
                        .append(") ")
                        .append(singleInfo.getMessageId());
                builder.append(System.lineSeparator());
            }
            builder.append("---").append(System.lineSeparator())
                    .append(transactionDeleteLog);
            event.setBody(builder.toString());
            SystemEventManagerImplAS2.instance().newEvent(event);
        }

    }
}
