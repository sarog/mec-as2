//$Header: /mec_as2/de/mendelson/comm/as2/message/MessageAccessDB.java 179   15/04/26 12:43 Heller $
package de.mendelson.comm.as2.message;

import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.comm.as2.statistic.ServerInteroperabilityAccessDB;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.database.RetryableDBOperation;
import de.mendelson.util.database.RollbackRetryException;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import de.mendelson.util.throughput.ThroughputAccessDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Implementation of a server log for the as2 server database
 *
 * @author S.Heller
 * @version $Revision: 179 $
 */
public class MessageAccessDB {

    private final Calendar calendarUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    private final IDBDriverManager dbDriverManager;

    /**
     * Creates new message I/O log and connects to localhost
     *
     */
    public MessageAccessDB(IDBDriverManager dbDriverManager) {
        this.dbDriverManager = dbDriverManager;
    }

    /**
     * Returns the number of transmissions in the system of a special state
     */
    public int getMessageCount(MessageStateType state) {
        String transactionName = "MessageAccessDB_getMessageCount_state";
        int counter = 0;
        try (Connection runtimeConnectionNoAutoCommit
                = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement, new String[]{"messages"});
                    try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(
                            "SELECT COUNT(1) AS messagecount FROM messages WHERE state=?")) {
                        statement.setInt(1, state.toInt());
                        try (ResultSet result = statement.executeQuery()) {
                            if (result.next()) {
                                counter = result.getInt("messagecount");
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
        return (counter);
    }

    /**
     * Returns the number of transmissions in the system
     */
    public int getMessageCount() {
        String transactionName = "MessageAccessDB_getMessageCount";
        int counter = 0;
        try (Connection runtimeConnectionNoAutoCommit
                = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement, new String[]{"messages"});
                    try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(
                            "SELECT COUNT(1) AS messagecount FROM messages")) {
                        try (ResultSet result = statement.executeQuery()) {
                            if (result.next()) {
                                counter = result.getInt("messagecount");
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
        return (counter);
    }

    /**
     * Returns the state of the latest passed message. Will return pending state
     * if the messageid does not exist.
     *
     * @return One of AS2Message.STATE_PENDING, AS2Message.STATE_FINISHED,
     * AS2Message.STATE_STOPPED
     */
    public MessageStateType getMessageState(String messageId) {
        String transactionName = "MessageAccessDB_getMessageState_messageid";
        MessageStateType state = MessageStateType.PENDING;
        try (Connection runtimeConnectionNoAutoCommit
                = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{"messages"});
                    //desc because the latest message should be first in resultset
                    try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(
                            "SELECT state FROM messages WHERE messageid=? ORDER BY initdateutc DESC")) {
                        statement.setString(1, messageId);
                        try (ResultSet result = statement.executeQuery()) {
                            if (result.next()) {
                                state = MessageStateType.of(result.getInt("state"));
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
        return (state);
    }

    public void setMessageState(String messageId, MessageStateType fromState, MessageStateType toState) {
        String transactionName = "MessageAccessDB_setMessageState";
        try (Connection runtimeConnectionNoAutoCommit
                = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                try {
                    this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                    String lockedMessageId = this.dbDriverManager.<String>setSingleRowLockForModification(
                            transactionStatement, "messages",
                            "state=? AND messageid=?",
                            List.of(fromState.toInt(), messageId),
                            "messageid", String.class);
                    if (lockedMessageId == null) {
                        this.dbDriverManager.rollbackTransaction(transactionStatement);
                    } else {
                        this.setMessageState(runtimeConnectionNoAutoCommit,
                                messageId, fromState, toState);
                        this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                    }
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
            if (toState == MessageStateType.FINISHED && fromState != MessageStateType.FINISHED) {
                ThroughputAccessDB throughputAccess
                        = new ThroughputAccessDB(this.dbDriverManager, SystemEventManagerImplAS2.instance());
                throughputAccess.addTransactionAsTransaction(runtimeConnectionNoAutoCommit);
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
    }

    /**
     * Sets the corresponding message status to the new value. This will change
     * the state in any case without any check; needs INSERT/UPDATE lock on
     * messages
     */
    private void setMessageState(Connection runtimeConnectionNoAutoCommit,
            String messageId, MessageStateType fromState, MessageStateType toState) throws Exception {
        try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(
                "UPDATE messages SET state=? WHERE state=? AND messageid=?")) {
            statement.setInt(1, toState.toInt());
            statement.setInt(2, fromState.toInt());
            statement.setString(3, messageId);
            statement.executeUpdate();
        }
        //A transaction has been stopped. This is worth a system event because a notification might be triggered
        //for such an event
        if (toState == MessageStateType.STOPPED) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        SystemEventManagerImplAS2.instance().newEventTransactionError(messageId, dbDriverManager);
                    } catch (Exception e) {
                        SystemEventManagerImplAS2.instance().systemFailure(e);
                    }
                }
            };
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(runnable);
            executor.shutdown();
        }
    }

    /**
     * Sets the corresponding message status to the new value. This will have
     * only effects if the actual message state is "pending". "Stopped" and
     * "finished" are states that MUST not be changed.
     *
     * @param newMessageState one of the states defined in the class AS2Message
     */
    public void setMessageState(String messageId, MessageStateType newMessageState) {
        MessageStateType oldMessageState = this.getMessageState(messageId);
        //keep red state and keep green state - only the pending state may be changed
        if (oldMessageState != MessageStateType.PENDING) {
            return;
        }
        this.setMessageState(messageId, oldMessageState, newMessageState);
        //store the entry in the interoperability statistic
        ServerInteroperabilityAccessDB statisticAccess
                = new ServerInteroperabilityAccessDB(this.dbDriverManager);
        List<AS2MessageInfo> overviewList = this.getMessageOverview(messageId);
        statisticAccess.addEntry(messageId, newMessageState, overviewList);
    }

    /**
     * Returns information about the payload of a special message
     */
    public List<AS2Payload> getPayload(String messageId) {
        String transactionName = "MessageAccess_getPayload";
        List<AS2Payload> payloadList = new ArrayList<AS2Payload>();
        try (Connection runtimeConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{"payload"});
                    try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(
                            "SELECT * FROM payload WHERE messageid=?")) {
                        statement.setString(1, messageId);
                        try (ResultSet result = statement.executeQuery()) {
                            while (result.next()) {
                                AS2Payload payload = new AS2Payload();
                                payload.setPayloadFilename(result.getString("payloadfilename"))
                                        .setOriginalFilename(result.getString("originalfilename"))
                                        .setContentId(result.getString("contentid"))
                                        .setContentType(result.getString("contenttype"));
                                payloadList.add(payload);
                            }
                        }
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
        return (payloadList);
    }

    /**
     * Returns all detail rows from the datase
     */
    public List<AS2Info> getMessageDetails(String messageId) {
        List<AS2Info> messageList = new ArrayList<AS2Info>();
        messageList.addAll(this.getMessageOverview(messageId));
        MDNAccessDB mdnAccess = new MDNAccessDB(this.dbDriverManager);
        messageList.addAll(mdnAccess.getMDN(messageId));
        return (messageList);
    }

    /**
     * Checks if a passed message id exists
     */
    public boolean messageIdExists(String messageId) {
        boolean messageIdExists = false;
        String transactionName = "MessageAccess_messageIdExists_messageid";
        try (Connection runtimeConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{"messages"});
                    try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(
                            "SELECT 1 FROM messages WHERE messageid=?")) {
                        statement.setString(1, messageId);
                        try (ResultSet result = statement.executeQuery()) {
                            if (result.next()) {
                                messageIdExists = true;
                            }
                        }
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
        return (messageIdExists);
    }

    public String getLastMessageIdByUserdefinedId(String userdefinedId) {
        if (userdefinedId == null) {
            return (null);
        }
        String transactionname = "MessageAccess_getLastMessageIdByUserdefinedId";
        String lastMessageId = null;
        try (Connection runtimeConnectionNoAutoCommit
                = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionname);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{"messages"});
                    //desc because we need the latest
                    String query = "SELECT messageid FROM messages WHERE userdefinedid=? ORDER BY initdateutc DESC";
                    query = this.dbDriverManager.addLimitToQuery(query, 1);
                    try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(query)) {
                        statement.setString(1, userdefinedId);
                        try (ResultSet result = statement.executeQuery()) {
                            if (result.next()) {
                                lastMessageId = result.getString("messageid");
                            }
                        }
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
        return (lastMessageId);
    }

    /**
     * Reads information about a specific messageid from the data base, returns
     * the latest message of this id
     */
    public AS2MessageInfo getLastMessageEntry(String messageId) {
        AS2MessageInfo info = null;
        String transactionname = "MessageAccess_getLastMessageEntry";
        try (Connection runtimeConnectionNoAutoCommit
                = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionname);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{"messages"});
                    //desc because we need the latest
                    String query = "SELECT * FROM messages WHERE messageid=? ORDER BY initdateutc DESC";
                    query = this.dbDriverManager.addLimitToQuery(query, 1);
                    try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(query)) {
                        statement.setString(1, messageId);
                        try (ResultSet result = statement.executeQuery()) {
                            if (result.next()) {
                                info = new AS2MessageInfo();
                                info.setInitDate(result.getTimestamp("initdateutc", this.calendarUTC));
                                info.setSendDate(result.getTimestamp("senddateutc", this.calendarUTC));
                                info.setEncryptionType(result.getInt("encryption"));
                                info.setDirection(MessageDirectionType.of(result.getInt("direction")));
                                info.setMessageType(MessageType.of(result.getInt("messagetype")));
                                info.setMessageId(result.getString("messageid"));
                                info.setRawFilename(result.getString("rawfilename"));
                                info.setReceiverId(result.getString("receiverid"));
                                info.setSenderId(result.getString("senderid"));
                                info.setSignType(result.getInt("signature"));
                                info.setState(MessageStateType.of(result.getInt("state")));
                                info.setRequestsSyncMDN(result.getInt("syncmdn") == 1);
                                info.setHeaderFilename(result.getString("headerfilename"));
                                info.setRawFilenameDecrypted(result.getString("rawdecryptedfilename"));
                                info.setSenderHost(result.getString("senderhost"));
                                info.setUserAgent(result.getString("useragent"));
                                info.setReceivedContentMIC(result.getString("contentmic"));
                                info.setCompressionType(MessageCompressionType.of(result.getInt("msgcompression")));
                                info.setAsyncMDNURL(result.getString("asyncmdnurl"));
                                info.setSubject(result.getString("msgsubject"));
                                info.setResendCounter(result.getInt("resendcounter"));
                                info.setUserdefinedId(result.getString("userdefinedid"));
                                info.setUsesTLS(result.getInt("secureconnection") == 1);
                            }
                        }
                    }
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionname);
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        return (info);
    }

    /**
     * Returns all overview rows from the database - opens a new connection
     */
    public List<AS2MessageInfo> getMessageOverview(String messageId) {
        List<AS2MessageInfo> messageList = new ArrayList<AS2MessageInfo>();
        String transactionname = "MessageAccess_getMessageOverview";
        try (Connection runtimeConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionname);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{"messages"});
                    String query = "SELECT * FROM messages WHERE messageid=? ORDER BY initdateutc ASC";
                    try (PreparedStatement selectStatement = runtimeConnectionNoAutoCommit.prepareStatement(query)) {
                        selectStatement.setString(1, messageId);
                        try (ResultSet result = selectStatement.executeQuery()) {
                            while (result.next()) {
                                AS2MessageInfo info = new AS2MessageInfo();
                                info.setInitDate(new Date(result.getTimestamp("initdateutc", this.calendarUTC).getTime()));
                                Timestamp sendDate = result.getTimestamp("senddateutc", this.calendarUTC);
                                if (result.wasNull()) {
                                    info.setSendDate(null);
                                } else {
                                    info.setSendDate(new Date(sendDate.getTime()));
                                }
                                info.setEncryptionType(result.getInt("encryption"));
                                info.setDirection(MessageDirectionType.of(result.getInt("direction")));
                                info.setMessageType(MessageType.of(result.getInt("messagetype")));
                                info.setMessageId(result.getString("messageid"));
                                info.setRawFilename(result.getString("rawfilename"));
                                info.setReceiverId(result.getString("receiverid"));
                                info.setSenderId(result.getString("senderid"));
                                info.setSignType(result.getInt("signature"));
                                info.setState(MessageStateType.of(result.getInt("state")));
                                info.setRequestsSyncMDN(result.getInt("syncmdn") == 1);
                                info.setHeaderFilename(result.getString("headerfilename"));
                                info.setRawFilenameDecrypted(result.getString("rawdecryptedfilename"));
                                info.setSenderHost(result.getString("senderhost"));
                                info.setUserAgent(result.getString("useragent"));
                                info.setReceivedContentMIC(result.getString("contentmic"));
                                info.setCompressionType(MessageCompressionType.of(result.getInt("msgcompression")));
                                info.setAsyncMDNURL(result.getString("asyncmdnurl"));
                                info.setSubject(result.getString("msgsubject"));
                                info.setResendCounter(result.getInt("resendcounter"));
                                info.setUserdefinedId(result.getString("userdefinedid"));
                                info.setUsesTLS(result.getInt("secureconnection") == 1);
                                messageList.add(info);
                            }
                        }
                        this.dbDriverManager.commitTransaction(transactionStatement, transactionname);
                    }
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        return (messageList);
    }

    /**
     * Returns all overview rows from the datase
     */
    public List<AS2MessageInfo> getMessageOverview(MessageOverviewFilter filter) {
        List<AS2MessageInfo> messageList = new ArrayList<AS2MessageInfo>();
        try (Connection runtimeConnectionAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            List<Object> parameterList = new ArrayList<Object>();
            StringBuilder queryCondition = new StringBuilder();
            if (filter.getShowPartner() != null) {
                Partner partner = filter.getShowPartner();
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append("(senderid=? OR receiverid=?)");
                parameterList.add(partner.getAS2Identification());
                parameterList.add(partner.getAS2Identification());
            }
            if (filter.getShowLocalStation() != null) {
                Partner localStation = filter.getShowLocalStation();
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append("(senderid=? OR receiverid=?)");
                parameterList.add(localStation.getAS2Identification());
                parameterList.add(localStation.getAS2Identification());
            }
            if (!filter.isShowFinished()) {
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append(" state <>?");
                parameterList.add(MessageStateType.FINISHED.toInt());
            }
            if (!filter.isShowPending()) {
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append(" state <>?");
                parameterList.add(MessageStateType.PENDING.toInt());
            }
            if (!filter.isShowStopped()) {
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append(" state <>?");
                parameterList.add(MessageStateType.STOPPED.toInt());
            }
            if (filter.getShowDirection() != MessageDirectionType.ALL) {
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append(" direction=?");
                parameterList.add(filter.getShowDirection().toInt());
            }
            if (filter.getShowMessageType() != MessageType.ALL) {
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append(" messagetype=?");
                parameterList.add(filter.getShowMessageType().toInt());
            }
            if (filter.getUserdefinedId() != null) {
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append(" userdefinedid=?");
                parameterList.add(filter.getUserdefinedId());
            }
            boolean useTimeFilter = filter.getStartTime() != 0L && filter.getEndTime() != 0L;
            if (useTimeFilter) {
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append(" CAST(initdateutc AS DATE)>=? AND CAST(initdateutc AS DATE)<=?");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(filter.getStartTime());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                parameterList.add(new Timestamp(calendar.getTimeInMillis()));
                calendar.setTimeInMillis(filter.getEndTime());
                calendar.add(Calendar.DAY_OF_YEAR, 0);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                parameterList.add(new Timestamp(calendar.getTimeInMillis()));
            }
            //Hint: This is the wrong order! It should be ordered using "ASC". But the HSQLDB LIMIT clause
            //just takes the n first rows of the result set and returns them. Means the first n results are taken now 
            //in the wrong order and then the returned list of transactions is built in the wrong order again 
            //(add every row to the pos 0 of the list)
            //- then the result is as if the LIMIT has been taken from the other side of the result set
            String query = "SELECT * FROM messages" + queryCondition.toString()
                    + " ORDER BY initdateutc DESC";
            if (!useTimeFilter) {
                //do NOT use the limit if a time filter is set as the user want to see all transactions in range
                query = this.dbDriverManager.addLimitToQuery(query, filter.getLimit());
            }
            try (PreparedStatement statement = runtimeConnectionAutoCommit.prepareStatement(query)) {
                for (int i = 0; i < parameterList.size(); i++) {
                    if (parameterList.get(i) instanceof Integer) {
                        statement.setInt(i + 1, ((Integer) parameterList.get(i)).intValue());
                    } else if (parameterList.get(i) instanceof Timestamp) {
                        statement.setTimestamp(i + 1, (Timestamp) parameterList.get(i));
                    } else {
                        statement.setString(i + 1, (String) parameterList.get(i));
                    }
                }
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        AS2MessageInfo info = new AS2MessageInfo();
                        info.setInitDate(result.getTimestamp("initdateutc", this.calendarUTC));
                        info.setSendDate(result.getTimestamp("senddateutc", this.calendarUTC));
                        info.setEncryptionType(result.getInt("encryption"));
                        info.setDirection(MessageDirectionType.of(result.getInt("direction")));
                        info.setMessageType(MessageType.of(result.getInt("messagetype")));
                        info.setMessageId(result.getString("messageid"));
                        info.setRawFilename(result.getString("rawfilename"));
                        info.setReceiverId(result.getString("receiverid"));
                        info.setSenderId(result.getString("senderid"));
                        info.setSignType(result.getInt("signature"));
                        info.setState(MessageStateType.of(result.getInt("state")));
                        info.setRequestsSyncMDN(result.getInt("syncmdn") == 1);
                        info.setHeaderFilename(result.getString("headerfilename"));
                        info.setRawFilenameDecrypted(result.getString("rawdecryptedfilename"));
                        info.setSenderHost(result.getString("senderhost"));
                        info.setUserAgent(result.getString("useragent"));
                        info.setReceivedContentMIC(result.getString("contentmic"));
                        info.setCompressionType(MessageCompressionType.of(result.getInt("msgcompression")));
                        info.setAsyncMDNURL(result.getString("asyncmdnurl"));
                        info.setSubject(result.getString("msgsubject"));
                        info.setResendCounter(result.getInt("resendcounter"));
                        info.setUserdefinedId(result.getString("userdefinedid"));
                        info.setUsesTLS(result.getInt("secureconnection") == 1);
                        //change the order of the list. This is required because of the LIMIT clause of HSQLDB
                        messageList.add(0, info);
                    }
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        return (messageList);
    }

    /**
     * Returns all file names of files that could be deleted for a passed
     * message info
     */
    public List<String> getRawFilenamesToDelete(List<String> messageIds,
            Connection runtimeConnectionNoAutoCommit) throws Exception {
        List<String> filenameList = new ArrayList<String>();
        StringBuilder query = new StringBuilder("SELECT rawfilename,rawdecryptedfilename,headerfilename "
                + "FROM messages WHERE messageid IN (");
        for (int i = 0; i < messageIds.size(); i++) {
            if (i > 0) {
                query.append(",");
            }
            query.append("?");
        }
        query.append(")");
        try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(query.toString())) {
            for (int i = 0; i < messageIds.size(); i++) {
                statement.setString(i + 1, messageIds.get(i));
            }
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    String rawFilename = result.getString("rawfilename");
                    if (!result.wasNull()) {
                        filenameList.add(rawFilename);
                    }
                    String rawFilenameDecrypted = result.getString("rawdecryptedfilename");
                    if (!result.wasNull()) {
                        filenameList.add(rawFilenameDecrypted);
                    }
                    String headerFilename = result.getString("headerfilename");
                    if (!result.wasNull()) {
                        filenameList.add(headerFilename);
                    }
                }
            }
        }
        MDNAccessDB mdnAccess = new MDNAccessDB(this.dbDriverManager);
        filenameList.addAll(mdnAccess.getRawFilenamesToDelete(messageIds, runtimeConnectionNoAutoCommit));
        return (filenameList);
    }

    /**
     * Deletes messages and MDNs of the passed id - transactional Returns the
     * number of deleted messages. Means if the returned value is 0 none of the
     * passed message ids did exist in the database
     */
    public int deleteMessages(List<String> messageIds, Connection runtimeConnectionNoAutoCommit) throws Exception {
        if (messageIds != null && !messageIds.isEmpty()) {
            StringBuilder deleteQuery = new StringBuilder("DELETE FROM mdn WHERE relatedmessageid IN (");
            for (int i = 0; i < messageIds.size(); i++) {
                if (i > 0) {
                    deleteQuery.append(",");
                }
                deleteQuery.append("?");
            }
            deleteQuery.append(")");
            try (PreparedStatement mdnDeleteStatement
                    = runtimeConnectionNoAutoCommit.prepareStatement(deleteQuery.toString())) {
                for (int i = 0; i < messageIds.size(); i++) {
                    mdnDeleteStatement.setString(i + 1, messageIds.get(i));
                }
                mdnDeleteStatement.executeUpdate();
            }
            deleteQuery = new StringBuilder("DELETE FROM payload WHERE messageid IN (");
            for (int i = 0; i < messageIds.size(); i++) {
                if (i > 0) {
                    deleteQuery.append(",");
                }
                deleteQuery.append("?");
            }
            deleteQuery.append(")");
            try (PreparedStatement payload1DeleteStatement
                    = runtimeConnectionNoAutoCommit.prepareStatement(deleteQuery.toString())) {
                for (int i = 0; i < messageIds.size(); i++) {
                    payload1DeleteStatement.setString(i + 1, messageIds.get(i));
                }
                payload1DeleteStatement.executeUpdate();
            }
            deleteQuery = new StringBuilder("DELETE FROM messages WHERE messageid IN (");
            for (int i = 0; i < messageIds.size(); i++) {
                if (i > 0) {
                    deleteQuery.append(",");
                }
                deleteQuery.append("?");
            }
            deleteQuery.append(")");
            try (PreparedStatement message1DeleteStatement
                    = runtimeConnectionNoAutoCommit.prepareStatement(deleteQuery.toString())) {
                for (int i = 0; i < messageIds.size(); i++) {
                    message1DeleteStatement.setString(i + 1, messageIds.get(i));
                }
                int deletedMessages = message1DeleteStatement.executeUpdate();
                return (deletedMessages);
            }
        } else {
            try (PreparedStatement payload2DeleteStatement
                    = runtimeConnectionNoAutoCommit.prepareStatement("DELETE FROM payload WHERE messageid IS NULL")) {
                payload2DeleteStatement.executeUpdate();
            }
            try (PreparedStatement message2DeleteStatement
                    = runtimeConnectionNoAutoCommit.prepareStatement("DELETE FROM messages WHERE messageid IS NULL")) {
                int deletedMessages = message2DeleteStatement.executeUpdate();
                return (deletedMessages);
            }
        }
    }

    /**
     * Deletes messages and MDNs of the passed id by opening a new database
     * connection - transactional Returns the number of deleted messages. If non
     * of the messages ids exists 0 will be returned
     */
    public int deleteMessages(List<String> messageIds) {
        String transactionname = "Message_delete";
        try (Connection runtimeConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                //start transaction
                this.dbDriverManager.startTransaction(transactionStatement, transactionname);
                try {
                    this.dbDriverManager.setTableLockDELETE(transactionStatement,
                            new String[]{
                                "mdn",
                                "payload",
                                "messages",});
                    int deletedMessages = this.deleteMessages(messageIds, runtimeConnectionNoAutoCommit);
                    //all ok - finish transaction and release all locks
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionname);
                    return (deletedMessages);
                } catch (Exception e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        return (0);
    }

    public void setMessageSendDate(final AS2MessageInfo info) {
        String transactionName = "MessageAccessDB_setMessageSendDate";
        RetryableDBOperation<Void> dbOperation = new RetryableDBOperation<Void>() {
            @Override
            public Void execute() throws RollbackRetryException {
                try (Connection runtimeConnectionNoAutoCommit = dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
                    runtimeConnectionNoAutoCommit.setAutoCommit(false);
                    try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                        dbDriverManager.startTransaction(transactionStatement, transactionName);
                        try {
                            String lockedMessageId = dbDriverManager.<String>setSingleRowLockForModification(
                                    transactionStatement, "messages",
                                    "messageid=?", List.of(info.getMessageId()),
                                    "messageid",
                                    String.class);
                            if (lockedMessageId == null) {
                                dbDriverManager.rollbackTransaction(transactionStatement);
                            } else {
                                try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(
                                        "UPDATE messages SET senddateutc=? WHERE messageid=?")) {
                                    statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()), calendarUTC);
                                    statement.setString(2, lockedMessageId);
                                    statement.executeUpdate();
                                }
                                dbDriverManager.commitTransaction(transactionStatement, transactionName);
                            }
                        } catch (Exception e) {
                            dbDriverManager.rollbackTransaction(transactionStatement);
                            throw new RollbackRetryException(e, transactionName);
                        }
                    }
                } catch (RollbackRetryException e) {
                    throw e;
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
                }
                return null;
            }
        };
        this.dbDriverManager.executeWithRetry(SystemEventManagerImplAS2.instance(), dbOperation);
    }

    /**
     * Updates the filenames of a single message
     */
    public void updateFilenames(final AS2MessageInfo info) {
        final String transactionName = "MessageAccessDB_updateFilenames";
        RetryableDBOperation<Void> dbOperation = new RetryableDBOperation<Void>() {
            @Override
            public Void execute() throws RollbackRetryException {
                try (Connection runtimeConnectionNoAutoCommit = dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
                    runtimeConnectionNoAutoCommit.setAutoCommit(false);
                    try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                        dbDriverManager.startTransaction(transactionStatement, transactionName);
                        try {
                            String lockedMessageId = dbDriverManager.<String>setSingleRowLockForModification(
                                    transactionStatement,
                                    "messages",
                                    "messageid=?",
                                    List.of(info.getMessageId()),
                                    "messageid",
                                    String.class);
                            if (lockedMessageId == null) {
                                dbDriverManager.rollbackTransaction(transactionStatement);
                            } else {
                                try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(
                                        "UPDATE messages SET rawfilename=?,headerfilename=?,rawdecryptedfilename=? WHERE messageid=?")) {
                                    statement.setString(1, info.getRawFilename());
                                    statement.setString(2, info.getHeaderFilename());
                                    statement.setString(3, info.getRawFilenameDecrypted());
                                    statement.setString(4, lockedMessageId);
                                    statement.executeUpdate();
                                }
                                dbDriverManager.commitTransaction(transactionStatement, transactionName);
                            }
                        } catch (Throwable e) {
                            dbDriverManager.rollbackTransaction(transactionStatement);
                            throw new RollbackRetryException(e, transactionName);
                        }
                    }
                } catch (RollbackRetryException e) {
                    throw e;
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
                }
                return null;
            }
        };
        this.dbDriverManager.executeWithRetry(
                SystemEventManagerImplAS2.instance(), dbOperation);
    }

    /**
     * Updates the subject of a message
     */
    public void updateSubject(final AS2MessageInfo info) {
        final String transactionName = "MessageAccessDB_updateSubject";
        RetryableDBOperation<Void> dbOperation = new RetryableDBOperation<Void>() {
            @Override
            public Void execute() throws RollbackRetryException {
                try (Connection runtimeConnectionNoAutoCommit = dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
                    runtimeConnectionNoAutoCommit.setAutoCommit(false);
                    try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                        dbDriverManager.startTransaction(transactionStatement, transactionName);
                        try {
                            String lockedMessageId = dbDriverManager.<String>setSingleRowLockForModification(
                                    transactionStatement,
                                    "messages",
                                    "messageid=?",
                                    List.of(info.getMessageId()),
                                    "messageid",
                                    String.class);
                            if (lockedMessageId == null) {
                                dbDriverManager.rollbackTransaction(transactionStatement);
                            } else {
                                try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(
                                        "UPDATE messages SET msgsubject=? WHERE messageid=?")) {
                                    statement.setString(1, info.getSubject());
                                    statement.setString(2, lockedMessageId);
                                    statement.executeUpdate();
                                }
                                dbDriverManager.commitTransaction(transactionStatement, transactionName);
                            }
                        } catch (Throwable e) {
                            dbDriverManager.rollbackTransaction(transactionStatement);
                            throw new RollbackRetryException(e, transactionName);
                        }
                    }
                } catch (RollbackRetryException e) {
                    throw e;
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
                }
                return null;
            }
        };
        this.dbDriverManager.executeWithRetry(
                SystemEventManagerImplAS2.instance(), dbOperation);
    }

    public void incResendCounter(String messageId) {
        String transactionName = "MessageAccessDB_incResendCounter";
        try (Connection runtimeConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    String lockedMessageId = this.dbDriverManager.<String>setSingleRowLockForModification(
                            transactionStatement, "messages",
                            "messageid=?", List.of(messageId),
                            "messageid",
                            String.class);
                    if (lockedMessageId == null) {
                        this.dbDriverManager.rollbackTransaction(transactionStatement);
                    } else {
                        this.incResendCounter(runtimeConnectionNoAutoCommit, lockedMessageId);
                        this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                    }
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
    }

    /**
     * Requires a INSERT/UPDATE lock on the table messages
     *
     * @param runtimeConnectionNoAutoCommit
     * @param messageId
     * @throws Exception
     */
    private void incResendCounter(Connection runtimeConnectionNoAutoCommit, String messageId) throws Exception {
        int currentCounter = 0;
        try (PreparedStatement statementSelect = runtimeConnectionNoAutoCommit.prepareStatement(
                "SELECT resendcounter FROM messages WHERE messageId=?")) {
            statementSelect.setString(1, messageId);
            try (ResultSet result = statementSelect.executeQuery()) {
                if (result.next()) {
                    currentCounter = result.getInt("resendcounter");
                }
            }
        }
        try (PreparedStatement statementUpdate = runtimeConnectionNoAutoCommit.prepareStatement(
                "UPDATE messages SET resendcounter=? WHERE messageid=?")) {
            statementUpdate.setInt(1, currentCounter + 1);
            statementUpdate.setString(2, messageId);
            statementUpdate.executeUpdate();
        }
    }

    /**
     * Establishes a new DB connection, writes the payload and original
     * filenames to the database, deleting all entries first (only if a payload
     * has been passed)
     */
    public void insertPayloads(String messageId, List<AS2Payload> payloadList) {
        String transactionName = "Message_insertPayload";
        try (Connection runtimeConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockDELETE(transactionStatement,
                            new String[]{"payload"});
                    this.deleteThenInsertPayloads(messageId, payloadList, runtimeConnectionNoAutoCommit);
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

    /**
     * Writes the payload and original filenames to the database, deleting all
     * entries first (only if a payload has been passed) This needs a DELETE
     * lock on the table payload
     */
    private void deleteThenInsertPayloads(String messageId, List<AS2Payload> payloadList,
            Connection runtimeConnectionNoAutoCommit) throws Exception {
        if (payloadList == null || payloadList.isEmpty()) {
            return;
        }
        try (PreparedStatement statementDelete
                = runtimeConnectionNoAutoCommit.prepareStatement(
                        "DELETE FROM payload WHERE messageid=?")) {
            statementDelete.setString(1, messageId);
            statementDelete.executeUpdate();
        }
        try (PreparedStatement statementInsert
                = runtimeConnectionNoAutoCommit.prepareStatement(
                        "INSERT INTO payload(messageid,originalfilename,payloadfilename,contentid,contenttype)"
                        + "VALUES(?,?,?,?,?)")) {
            for (AS2Payload payload : payloadList) {
                statementInsert.setString(1, messageId);
                statementInsert.setString(2, payload.getOriginalFilename());
                statementInsert.setString(3, payload.getPayloadFilename());
                statementInsert.setString(4, payload.getContentId());
                statementInsert.setString(5, payload.getContentType());
                statementInsert.executeUpdate();
            }
        }
    }

    /**
     * Initializes or updates a messages in the database. If the message id
     * already exists it is updated
     *
     */
    public void initializeOrUpdateMessage(AS2MessageInfo info) {
        String transactionName = "MessageAccessDB_initializeOrUpdateMessage";
        try (Connection runtimeConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockINSERTAndUPDATE(transactionStatement,
                            new String[]{"messages"});
                    int updatedMessageCount = this.updateMessage(info, runtimeConnectionNoAutoCommit);
                    if (updatedMessageCount == 0) {
                        this.initializeMessage(info, runtimeConnectionNoAutoCommit);
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

    /**
     * Initializes a messages in the database.
     */
    private void initializeMessage(AS2MessageInfo info, Connection runtimeConnectionNoAutoCommit) throws Exception {
        try (PreparedStatement preparedStatement
                = runtimeConnectionNoAutoCommit.prepareStatement(
                        "INSERT INTO messages(initdateutc,encryption,direction,messageid,rawfilename,receiverid,senderid,"
                        + "signature,state,syncmdn,headerfilename,rawdecryptedfilename,senderhost,useragent,"
                        + "contentmic,msgcompression,messagetype,asyncmdnurl,msgsubject,userdefinedid,"
                        + "secureconnection)VALUES("
                        + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(info.getInitDate().getTime()), this.calendarUTC);
            preparedStatement.setInt(2, info.getEncryptionType());
            preparedStatement.setInt(3, info.getDirection().toInt());
            preparedStatement.setString(4, info.getMessageId());
            preparedStatement.setString(5, info.getRawFilename());
            preparedStatement.setString(6, info.getReceiverId());
            preparedStatement.setString(7, info.getSenderId());
            preparedStatement.setInt(8, info.getSignType());
            preparedStatement.setInt(9, info.getState().toInt());
            preparedStatement.setInt(10, info.isRequestsSyncMDN() ? 1 : 0);
            preparedStatement.setString(11, info.getHeaderFilename());
            preparedStatement.setString(12, info.getRawFilenameDecrypted());
            preparedStatement.setString(13, info.getSenderHost());
            preparedStatement.setString(14, info.getUserAgent());
            preparedStatement.setString(15, info.getReceivedContentMIC());
            preparedStatement.setInt(16, info.getCompressionType().toInt());
            preparedStatement.setInt(17, info.getMessageType().toInt());
            preparedStatement.setString(18, info.getAsyncMDNURL());
            preparedStatement.setString(19, info.getSubject());
            if (info.getUserdefinedId() != null) {
                preparedStatement.setString(20, info.getUserdefinedId());
            } else {
                preparedStatement.setNull(20, Types.VARCHAR);
            }
            preparedStatement.setInt(21, info.isUsesTLS() ? 1 : 0);
            preparedStatement.executeUpdate();
        }
        AS2Server.incTransactionCounter();
    }

    /**
     * Updates an existing message in the database and returns a value != 0 if
     * this was successful - means the message id did already exist and has been
     * updated. This needs a INSERT/UPDATE lock on the table payload and
     * messages
     */
    private int updateMessage(AS2MessageInfo info, Connection runtimeConnectionNoAutoCommit) throws Exception {
        int updatedEntries;
        try (PreparedStatement preparedStatement = runtimeConnectionNoAutoCommit.prepareStatement(
                "UPDATE messages SET encryption=?,rawfilename=?,receiverid=?,"
                + "senderid=?,signature=?,syncmdn=?,headerfilename=?,"
                + "rawdecryptedfilename=?,senderhost=?,"
                + "contentmic=?,msgcompression=?,messagetype=?,asyncmdnurl=?,"
                + "secureconnection=?"
                + " WHERE messageid=?")) {
            preparedStatement.setInt(1, info.getEncryptionType());
            preparedStatement.setString(2, info.getRawFilename());
            preparedStatement.setString(3, info.getReceiverId());
            preparedStatement.setString(4, info.getSenderId());
            preparedStatement.setInt(5, info.getSignType());
            preparedStatement.setInt(6, info.isRequestsSyncMDN() ? 1 : 0);
            preparedStatement.setString(7, info.getHeaderFilename());
            preparedStatement.setString(8, info.getRawFilenameDecrypted());
            preparedStatement.setString(9, info.getSenderHost());
            preparedStatement.setString(10, info.getReceivedContentMIC());
            preparedStatement.setInt(11, info.getCompressionType().toInt());
            preparedStatement.setInt(12, info.getMessageType().toInt());
            preparedStatement.setString(13, info.getAsyncMDNURL());
            preparedStatement.setInt(14, info.isUsesTLS() ? 1 : 0);
            //condition
            preparedStatement.setString(15, info.getMessageId());
            updatedEntries = preparedStatement.executeUpdate();
        }
        return (updatedEntries);
    }

    /**
     * Returns a list of all messages that are older than the passed timestamp
     *
     */
    public List<AS2MessageInfo> getMessagesSendOlderThan(long yourCurrentTimezoneTime) {
        String transactionName = "MessageAccess_getMessagesSendOlderThan";
        List<AS2MessageInfo> messageList = new ArrayList<AS2MessageInfo>();
        try (Connection runtimeConnectionNoAutoCommit
                = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{"messages"});
                    String query = "SELECT * FROM messages WHERE (senddateutc IS NOT NULL) "
                            + "AND senddateutc < ? AND state=?";
                    try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(query)) {
                        statement.setTimestamp(1, new java.sql.Timestamp(yourCurrentTimezoneTime), this.calendarUTC);
                        statement.setInt(2, MessageStateType.PENDING.toInt());
                        try (ResultSet result = statement.executeQuery()) {
                            while (result.next()) {
                                AS2MessageInfo info = new AS2MessageInfo();
                                info.setInitDate(result.getTimestamp("initdateutc", this.calendarUTC));
                                info.setSendDate(result.getTimestamp("senddateutc", this.calendarUTC));
                                info.setEncryptionType(result.getInt("encryption"));
                                info.setDirection(MessageDirectionType.of(result.getInt("direction")));
                                info.setMessageType(MessageType.of(result.getInt("messagetype")));
                                info.setMessageId(result.getString("messageid"));
                                info.setRawFilename(result.getString("rawfilename"));
                                info.setReceiverId(result.getString("receiverid"));
                                info.setSenderId(result.getString("senderid"));
                                info.setSignType(result.getInt("signature"));
                                info.setState(MessageStateType.of(result.getInt("state")));
                                info.setRequestsSyncMDN(result.getInt("syncmdn") == 1);
                                info.setHeaderFilename(result.getString("headerfilename"));
                                info.setRawFilenameDecrypted(result.getString("rawdecryptedfilename"));
                                info.setSenderHost(result.getString("senderhost"));
                                info.setUserAgent(result.getString("useragent"));
                                info.setReceivedContentMIC(result.getString("contentmic"));
                                info.setCompressionType(MessageCompressionType.of( result.getInt("msgcompression")));
                                info.setAsyncMDNURL(result.getString("asyncmdnurl"));
                                info.setSubject(result.getString("msgsubject"));
                                info.setResendCounter(result.getInt("resendcounter"));
                                info.setUserdefinedId(result.getString("userdefinedid"));
                                info.setUsesTLS(result.getInt("secureconnection") == 1);
                                messageList.add(info);
                            }
                        }
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
        return (messageList);
    }

    /**
     * Returns a list of all messages that are older than the passed timestamp
     *
     * @param state pass -1 for any state else only messages of the requested
     * state are returned
     */
    public List<AS2MessageInfo> getMessagesOlderThan(long initTimestamp, int state) {
        String transactionName = "MessageAccess_getMessagesOlderThan";
        List<AS2MessageInfo> messageList = new ArrayList<AS2MessageInfo>();
        try (Connection runtimeConnectionNoAutoCommit
                = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{"messages"});
                    String query = "SELECT * FROM messages WHERE initdateutc < ?";
                    if (state != -1) {
                        query = query + " AND state=" + state;
                    }
                    try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(query)) {
                        statement.setTimestamp(1, new java.sql.Timestamp(initTimestamp), this.calendarUTC);
                        try (ResultSet result = statement.executeQuery()) {
                            while (result.next()) {
                                AS2MessageInfo info = new AS2MessageInfo();
                                info.setInitDate(result.getTimestamp("initdateutc", this.calendarUTC));
                                info.setSendDate(result.getTimestamp("senddateutc", this.calendarUTC));
                                info.setEncryptionType(result.getInt("encryption"));
                                info.setDirection(MessageDirectionType.of(result.getInt("direction")));
                                info.setMessageType(MessageType.of(result.getInt("messagetype")));
                                info.setMessageId(result.getString("messageid"));
                                info.setRawFilename(result.getString("rawfilename"));
                                info.setReceiverId(result.getString("receiverid"));
                                info.setSenderId(result.getString("senderid"));
                                info.setSignType(result.getInt("signature"));
                                info.setState(MessageStateType.of(result.getInt("state")));
                                info.setRequestsSyncMDN(result.getInt("syncmdn") == 1);
                                info.setHeaderFilename(result.getString("headerfilename"));
                                info.setRawFilenameDecrypted(result.getString("rawdecryptedfilename"));
                                info.setSenderHost(result.getString("senderhost"));
                                info.setUserAgent(result.getString("useragent"));
                                info.setReceivedContentMIC(result.getString("contentmic"));
                                info.setCompressionType(MessageCompressionType.of(result.getInt("msgcompression")));
                                info.setAsyncMDNURL(result.getString("asyncmdnurl"));
                                info.setSubject(result.getString("msgsubject"));
                                info.setResendCounter(result.getInt("resendcounter"));
                                info.setUserdefinedId(result.getString("userdefinedid"));
                                info.setUsesTLS(result.getInt("secureconnection") == 1);
                                messageList.add(info);
                            }
                        }
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
        return (messageList);
    }

    /**
     * Returns a list of all messages that are younger than the passed timestamp
     *
     * @param state pass -1 for any state else only messages of the requested
     * state are returned
     */
    public List<AS2MessageInfo> getMessagesYoungerThan(long initTimestamp, int state) {
        String transactionName = "MessageAccess_getMessagesYoungerThan";
        List<AS2MessageInfo> messageList = new ArrayList<AS2MessageInfo>();
        try (Connection runtimeConnectionNoAutoCommit
                = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{"messages"});
                    String query = "SELECT * FROM messages WHERE initdateutc > ?";
                    if (state != -1) {
                        query = query + " AND state=" + state;
                    }
                    try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(query)) {
                        statement.setTimestamp(1, new java.sql.Timestamp(initTimestamp), this.calendarUTC);
                        try (ResultSet result = statement.executeQuery()) {
                            while (result.next()) {
                                AS2MessageInfo info = new AS2MessageInfo();
                                info.setInitDate(result.getTimestamp("initdateutc", this.calendarUTC));
                                info.setSendDate(result.getTimestamp("senddateutc", this.calendarUTC));
                                info.setEncryptionType(result.getInt("encryption"));
                                info.setDirection(MessageDirectionType.of( result.getInt("direction")));
                                info.setMessageType(MessageType.of(result.getInt("messagetype")));
                                info.setMessageId(result.getString("messageid"));
                                info.setRawFilename(result.getString("rawfilename"));
                                info.setReceiverId(result.getString("receiverid"));
                                info.setSenderId(result.getString("senderid"));
                                info.setSignType(result.getInt("signature"));
                                info.setState(MessageStateType.of(result.getInt("state")));
                                info.setRequestsSyncMDN(result.getInt("syncmdn") == 1);
                                info.setHeaderFilename(result.getString("headerfilename"));
                                info.setRawFilenameDecrypted(result.getString("rawdecryptedfilename"));
                                info.setSenderHost(result.getString("senderhost"));
                                info.setUserAgent(result.getString("useragent"));
                                info.setReceivedContentMIC(result.getString("contentmic"));
                                info.setCompressionType(MessageCompressionType.of(result.getInt("msgcompression")));
                                info.setAsyncMDNURL(result.getString("asyncmdnurl"));
                                info.setSubject(result.getString("msgsubject"));
                                info.setResendCounter(result.getInt("resendcounter"));
                                info.setUserdefinedId(result.getString("userdefinedid"));
                                info.setUsesTLS(result.getInt("secureconnection") == 1);
                                messageList.add(info);
                            }
                        }
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
        return (messageList);
    }

}
