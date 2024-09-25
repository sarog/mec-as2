//$Header: /as2_cluster/de/mendelson/comm/as2/message/MessageAccessDB.java 101   5/04/17 9:13a Heller $
package de.mendelson.comm.as2.message;

import de.mendelson.comm.as2.notification.Notification;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.comm.as2.statistic.ServerInteroperabilityAccessDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

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
 * @version $Revision: 101 $
 */
public class MessageAccessDB {

    /**
     * Logger to log information to
     */
    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    /**
     * Connection to the database
     */
    private Connection runtimeConnection = null;
    private Connection configConnection = null;
    private Calendar calendarUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    /**
     * Creates new message I/O log and connects to localhost
     *
     * @param host host to connect to
     */
    public MessageAccessDB(Connection configConnection, Connection runtimeConnection) {
        this.runtimeConnection = runtimeConnection;
        this.configConnection = configConnection;
    }

    /**
     * Returns the number of transmissions in the system
     */
    public int getMessageCount() {
        int counter = 0;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = this.runtimeConnection.prepareStatement("SELECT COUNT(1) AS messagecount FROM messages");
            result = statement.executeQuery();
            if (result.next()) {
                counter = result.getInt("messagecount");
            }
        } catch (SQLException e) {
            this.logger.severe("getMessageCount: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                }
            }
        }
        return (counter);
    }

    /**
     * Returns the state of the latest passed message. Will return pending state
     * if the messageid does not exist
     */
    public int getMessageState(String messageId) {
        int state = AS2Message.STATE_PENDING;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            //desc because the latest message should be first in resultset
            statement = this.runtimeConnection.prepareStatement(
                    "SELECT state FROM messages WHERE messageid=? ORDER BY initdateutc DESC");
            statement.setString(1, messageId);
            result = statement.executeQuery();
            if (result.next()) {
                state = result.getInt("state");
            }
        } catch (Exception e) {
            this.logger.severe("getMessageState: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
            } catch (Exception e) {
                //nop
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                //nop
            }
        }
        return (state);
    }

    /**
     * Sets the corresponding message status to the new value. This will change
     * the state in any case without any check
     *
     * @param state one of the staes defined in the class AS2Message
     */
    public void setMessageState(String messageId, int fromState, int toState) {
        PreparedStatement statement = null;
        //perform the db update
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "UPDATE messages SET state=? WHERE state=? AND messageid=?");
            statement.setInt(1, toState);
            statement.setInt(2, fromState);
            statement.setString(3, messageId);
            int rows = statement.executeUpdate();
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.setMessageState: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    //nop
                }
            }
        }
        //perform the notification
        if (toState == AS2Message.STATE_STOPPED) {
            Notification notification = new Notification(this.configConnection, this.runtimeConnection);
            try {
                notification.sendTransactionError(messageId);
            } catch (Exception e) {
                this.logger.severe("MessageAccessDB.setMessageState: " + e.getMessage());
                Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
            }
        }
    }

    /**
     * Sets the corresponding message status to the new value. This will have
     * only effects if the actual message state is "pending". "Stopped" and
     * "finished" are states that MUST not be changed.
     *
     * @param newState one of the staes defined in the class AS2Message
     */
    public void setMessageState(String messageId, int newState) {
        int oldState = this.getMessageState(messageId);
        //keep red state and keep green state - only the pending state may be changed
        if (oldState != AS2Message.STATE_PENDING) {
            return;
        }
        this.setMessageState(messageId, oldState, newState);
        //store the entry in the interoperability statistic
        ServerInteroperabilityAccessDB statisticAccess = new ServerInteroperabilityAccessDB(this.configConnection, this.runtimeConnection);
        statisticAccess.addEntry(messageId);
    }

    /**
     * Returns information about the payload of a special message
     */
    public List<AS2Payload> getPayload(String messageId) {
        List<AS2Payload> payloadList = new ArrayList<AS2Payload>();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement("SELECT * FROM payload WHERE messageid=?");
            statement.setString(1, messageId);
            result = statement.executeQuery();
            while (result.next()) {
                AS2Payload payload = new AS2Payload();
                payload.setPayloadFilename(result.getString("payloadfilename"));
                payload.setOriginalFilename(result.getString("originalfilename"));
                payload.setContentId(result.getString("contentid"));
                payload.setContentType(result.getString("contenttype"));
                payloadList.add(payload);
            }
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.getPayload: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                    //nop
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    //nop
                }
            }
        }
        return (payloadList);
    }

    /**
     * Returns all detail rows from the datase
     */
    public List<AS2Info> getMessageDetails(String messageId) {
        List<AS2Info> messageList = new ArrayList<AS2Info>();
        messageList.addAll(this.getMessageOverview(messageId));
        MDNAccessDB mdnAccess = new MDNAccessDB(this.configConnection, this.runtimeConnection);
        messageList.addAll(mdnAccess.getMDN(messageId));
        return (messageList);
    }

    /**
     * Checks if a passed message id exists
     */
    public boolean messageIdExists(String messageId) {
        AS2MessageInfo info = this.getLastMessageEntry(messageId);
        return (info != null);
    }

    public String getLastMessageIdByUserdefinedId(String userdefinedId) {
        if (userdefinedId == null) {
            return (null);
        }
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            //desc because we need the latest
            statement = this.runtimeConnection.prepareStatement("SELECT messageid FROM messages WHERE userdefinedid=? ORDER BY initdateutc DESC");
            statement.setString(1, userdefinedId);
            result = statement.executeQuery();
            if (result.next()) {
                return (result.getString("messageid"));
            }
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.getMessageIdByUserdefinedId: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
            return (null);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getMessageIdByUserdefinedId: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getMessageIdByUserdefinedId: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
        }
        return (null);
    }

    /**
     * Reads information about a specific messageid from the data base, returns
     * the latest message of this id
     */
    public AS2MessageInfo getLastMessageEntry(String messageId) {
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            //desc because we need the latest
            statement = this.runtimeConnection.prepareStatement("SELECT * FROM messages WHERE messageid=? ORDER BY initdateutc DESC");
            statement.setString(1, messageId);
            result = statement.executeQuery();
            if (result.next()) {
                AS2MessageInfo info = new AS2MessageInfo();
                info.setInitDate(result.getTimestamp("initdateutc", this.calendarUTC));
                info.setEncryptionType(result.getInt("encryption"));
                info.setDirection(result.getInt("direction"));
                info.setMessageType(result.getInt("messagetype"));
                info.setMessageId(result.getString("messageid"));
                info.setRawFilename(result.getString("rawfilename"));
                info.setReceiverId(result.getString("receiverid"));
                info.setSenderId(result.getString("senderid"));
                info.setSignType(result.getInt("signature"));
                info.setState(result.getInt("state"));
                info.setRequestsSyncMDN(result.getInt("syncmdn") == 1);
                info.setHeaderFilename(result.getString("headerfilename"));
                info.setRawFilenameDecrypted(result.getString("rawdecryptedfilename"));
                info.setSenderHost(result.getString("senderhost"));
                info.setUserAgent(result.getString("useragent"));
                info.setReceivedContentMIC(result.getString("contentmic"));
                info.setCompressionType(result.getInt("msgcompression"));
                info.setAsyncMDNURL(result.getString("asyncmdnurl"));
                info.setSubject(result.getString("msgsubject"));
                info.setResendCounter(result.getInt("resendcounter"));
                info.setUserdefinedId(result.getString("userdefinedid"));
                return (info);
            }
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.getLastMessageEntry: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
            return (null);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getLastMessageEntry: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getLastMessageEntry: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
        }
        return (null);
    }

    /**
     * Returns all overview rows from the datase
     */
    public List<AS2MessageInfo> getMessageOverview(String messageId) {
        List<AS2MessageInfo> messageList = new ArrayList<AS2MessageInfo>();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement("SELECT * FROM messages WHERE messageid=? ORDER BY initdateutc ASC");
            statement.setString(1, messageId);
            result = statement.executeQuery();
            while (result.next()) {
                AS2MessageInfo info = new AS2MessageInfo();
                info.setInitDate(result.getTimestamp("initdateutc", this.calendarUTC));
                info.setEncryptionType(result.getInt("encryption"));
                info.setDirection(result.getInt("direction"));
                info.setMessageType(result.getInt("messagetype"));
                info.setMessageId(result.getString("messageid"));
                info.setRawFilename(result.getString("rawfilename"));
                info.setReceiverId(result.getString("receiverid"));
                info.setSenderId(result.getString("senderid"));
                info.setSignType(result.getInt("signature"));
                info.setState(result.getInt("state"));
                info.setRequestsSyncMDN(result.getInt("syncmdn") == 1);
                info.setHeaderFilename(result.getString("headerfilename"));
                info.setRawFilenameDecrypted(result.getString("rawdecryptedfilename"));
                info.setSenderHost(result.getString("senderhost"));
                info.setUserAgent(result.getString("useragent"));
                info.setReceivedContentMIC(result.getString("contentmic"));
                info.setCompressionType(result.getInt("msgcompression"));
                info.setAsyncMDNURL(result.getString("asyncmdnurl"));
                info.setSubject(result.getString("msgsubject"));
                info.setResendCounter(result.getInt("resendcounter"));
                info.setUserdefinedId(result.getString("userdefinedid"));
                messageList.add(info);
            }
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.getMessageOverview: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
            return (null);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getMessageOverview: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getMessageOverview: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
        }
        return (messageList);
    }

    /**
     * Returns all overview rows from the datase
     */
    public List<AS2MessageInfo> getMessageOverview(MessageOverviewFilter filter) {
        List<AS2MessageInfo> messageList = new ArrayList<AS2MessageInfo>();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
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
                parameterList.add(Integer.valueOf(AS2Message.STATE_FINISHED));
            }
            if (!filter.isShowPending()) {
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append(" state <>?");
                parameterList.add(Integer.valueOf(AS2Message.STATE_PENDING));
            }
            if (!filter.isShowStopped()) {
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append(" state <>?");
                parameterList.add(Integer.valueOf(AS2Message.STATE_STOPPED));
            }
            if (filter.getShowDirection() != MessageOverviewFilter.DIRECTION_ALL) {
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append(" direction=?");
                parameterList.add(Integer.valueOf(filter.getShowDirection()));
            }
            if (filter.getShowMessageType() != MessageOverviewFilter.MESSAGETYPE_ALL) {
                if (queryCondition.length() == 0) {
                    queryCondition.append(" WHERE");
                } else {
                    queryCondition.append(" AND");
                }
                queryCondition.append(" messagetype=?");
                parameterList.add(Integer.valueOf(filter.getShowMessageType()));
            }
            String query = "SELECT * FROM messages" + queryCondition.toString() + " ORDER BY initdateutc ASC";
            statement = this.runtimeConnection.prepareStatement(query);
            for (int i = 0; i < parameterList.size(); i++) {
                if (parameterList.get(i) instanceof Integer) {
                    statement.setInt(i + 1, ((Integer) parameterList.get(i)).intValue());
                } else {
                    statement.setString(i + 1, (String) parameterList.get(i));
                }
            }
            result = statement.executeQuery();
            while (result.next()) {
                AS2MessageInfo info = new AS2MessageInfo();
                info.setInitDate(result.getTimestamp("initdateutc", this.calendarUTC));
                info.setEncryptionType(result.getInt("encryption"));
                info.setDirection(result.getInt("direction"));
                info.setMessageType(result.getInt("messagetype"));
                info.setMessageId(result.getString("messageid"));
                info.setRawFilename(result.getString("rawfilename"));
                info.setReceiverId(result.getString("receiverid"));
                info.setSenderId(result.getString("senderid"));
                info.setSignType(result.getInt("signature"));
                info.setState(result.getInt("state"));
                info.setRequestsSyncMDN(result.getInt("syncmdn") == 1);
                info.setHeaderFilename(result.getString("headerfilename"));
                info.setRawFilenameDecrypted(result.getString("rawdecryptedfilename"));
                info.setSenderHost(result.getString("senderhost"));
                info.setUserAgent(result.getString("useragent"));
                info.setReceivedContentMIC(result.getString("contentmic"));
                info.setCompressionType(result.getInt("msgcompression"));
                info.setAsyncMDNURL(result.getString("asyncmdnurl"));
                info.setSubject(result.getString("msgsubject"));
                info.setResendCounter(result.getInt("resendcounter"));
                info.setUserdefinedId(result.getString("userdefinedid"));
                messageList.add(info);
            }
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.getMessageOverview: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getMessageOverview: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getMessageOverview: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
        }
        return (messageList);
    }

    /**
     * Returns all file names of files that could be deleted for a passed
     * message info
     */
    public List<String> getRawFilenamesToDelete(AS2MessageInfo info) {
        List<String> list = new ArrayList<String>();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            String query = "SELECT * FROM messages WHERE messageid=?";
            statement = this.runtimeConnection.prepareStatement(query);
            statement.setString(1, info.getMessageId());
            result = statement.executeQuery();
            while (result.next()) {
                String rawFilename = result.getString("rawfilename");
                if (!result.wasNull()) {
                    list.add(rawFilename);
                }
                String rawFilenameDecrypted = result.getString("rawdecryptedfilename");
                if (!result.wasNull()) {
                    list.add(rawFilenameDecrypted);
                }
                String headerFilename = result.getString("headerfilename");
                if (!result.wasNull()) {
                    list.add(headerFilename);
                }
            }
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.getRawFilenamesToDelete: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getRawFilenamesToDelete: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getRawFilenamesToDelete: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
        }
        MDNAccessDB mdnAccess = new MDNAccessDB(this.configConnection, this.runtimeConnection);
        list.addAll(mdnAccess.getRawFilenamesToDelete(info.getMessageId()));
        return (list);
    }

    /**
     * Deletes messages and MDNs of the passed id
     */
    public void deleteMessage(String messageId) {
        PreparedStatement statement = null;
        try {
            if (messageId != null) {
                statement = this.runtimeConnection.prepareStatement("DELETE FROM mdn WHERE relatedmessageid=?");
                statement.setString(1, messageId);
                statement.execute();
                statement.close();
                statement = this.runtimeConnection.prepareStatement("DELETE FROM payload WHERE messageid=?");
                statement.setString(1, messageId);
                statement.execute();
                statement.close();
                statement = this.runtimeConnection.prepareStatement("DELETE FROM messages WHERE messageid=?");
                statement.setString(1, messageId);
                statement.execute();
            } else {
                statement = this.runtimeConnection.prepareStatement("DELETE FROM payload WHERE messageid IS NULL");
                statement.execute();
                statement.close();
                statement = this.runtimeConnection.prepareStatement("DELETE FROM messages WHERE messageid IS NULL");
                statement.execute();
            }
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.deleteMessage: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.deleteMessage: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
        }
    }

    /**
     * Deletes messages and MDNs of the passed id
     */
    public void deleteMessage(AS2MessageInfo info) {
        this.deleteMessage(info.getMessageId());
    }

    /**
     * Updates a message entry in the database, only the filenames
     */
    public void setMessageSendDate(AS2MessageInfo info) {
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "UPDATE messages SET senddateutc=? WHERE messageid=?");
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()), this.calendarUTC);
            //WHERE
            statement.setString(2, info.getMessageId());
            statement.executeUpdate();
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.setMessageSendDate: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    //nop
                }
            }
        }
    }

    /**
     * Updates a message entry in the database, only the filenames
     */
    public void updateFilenames(AS2MessageInfo info) {
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "UPDATE messages SET rawfilename=?,headerfilename=?,rawdecryptedfilename=? WHERE messageid=?");
            statement.setString(1, info.getRawFilename());
            statement.setString(2, info.getHeaderFilename());
            statement.setString(3, info.getRawFilenameDecrypted());
            //WHERE
            statement.setString(4, info.getMessageId());
            statement.executeUpdate();
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.updateFilenames: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    //nop
                }
            }
        }
    }

    /**
     * Writes the payload and original filenames to the database, deleting all
     * entries first (only if a payload has been passed)
     */
    public void insertPayloads(String messageId, List<AS2Payload> payloadList) {
        if (payloadList == null || payloadList.isEmpty()) {
            return;
        }
        PreparedStatement statementDelete = null;
        PreparedStatement statementInsert = null;
        try {
            statementDelete = this.runtimeConnection.prepareStatement("DELETE FROM payload WHERE messageid=?");
            statementDelete.setString(1, messageId);
            statementDelete.executeUpdate();
            //insert
            statementInsert = this.runtimeConnection.prepareStatement(
                    "INSERT INTO payload(messageid,originalfilename,payloadfilename,contentid,contenttype)VALUES(?,?,?,?,?)");
            for (int i = 0; i < payloadList.size(); i++) {
                AS2Payload payload = payloadList.get(i);
                statementInsert.setString(1, messageId);
                statementInsert.setString(2, payload.getOriginalFilename());
                statementInsert.setString(3, payload.getPayloadFilename());
                statementInsert.setString(4, payload.getContentId());
                statementInsert.setString(5, payload.getContentType());
                statementInsert.executeUpdate();
            }

        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.insertPayload: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statementDelete);
        } finally {
            if (statementDelete != null) {
                try {
                    statementDelete.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.insertPayload: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
            if (statementInsert != null) {
                try {
                    statementInsert.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.insertPayload: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
        }
    }

    /**
     * Initializes or updates a messages in the database. If the message id
     * already exists it is updated
     *
     * @param message
     */
    public void initializeOrUpdateMessage(AS2MessageInfo info) {
        AS2MessageInfo testInfo = this.getLastMessageEntry(info.getMessageId());
        if (testInfo == null) {
            this.initializeMessage(info);
        } else {
            this.updateMessage(info);
        }
    }

    /**
     * Initializes a messages in the database.
     */
    private void initializeMessage(AS2MessageInfo info) {
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "INSERT INTO messages(initdateutc,encryption,direction,messageid,rawfilename,receiverid,senderid,"
                    + "signature,state,syncmdn,headerfilename,rawdecryptedfilename,senderhost,useragent,"
                    + "contentmic,msgcompression,messagetype,asyncmdnurl,msgsubject,userdefinedid)VALUES("
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            statement.setTimestamp(1, new java.sql.Timestamp(info.getInitDate().getTime()), this.calendarUTC);
            statement.setInt(2, info.getEncryptionType());
            statement.setInt(3, info.getDirection());
            statement.setString(4, info.getMessageId());
            statement.setString(5, info.getRawFilename());
            statement.setString(6, info.getReceiverId());
            statement.setString(7, info.getSenderId());
            statement.setInt(8, info.getSignType());
            statement.setInt(9, info.getState());
            statement.setInt(10, info.requestsSyncMDN() ? 1 : 0);
            statement.setString(11, info.getHeaderFilename());
            statement.setString(12, info.getRawFilenameDecrypted());
            statement.setString(13, info.getSenderHost());
            statement.setString(14, info.getUserAgent());
            statement.setString(15, info.getReceivedContentMIC());
            statement.setInt(16, info.getCompressionType());
            statement.setInt(17, info.getMessageType());
            statement.setString(18, info.getAsyncMDNURL());
            statement.setString(19, info.getSubject());
            if (info.getUserdefinedId() != null) {
                statement.setString(20, info.getUserdefinedId());
            } else {
                statement.setNull(20, Types.VARCHAR);
            }
            statement.executeUpdate();
            //insert payload and inc transaction counter
            AS2Message message = new AS2Message(info);
            this.insertPayloads(info.getMessageId(), message.getPayloads());
            AS2Server.incTransactionCounter();
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.initializeMessage: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.initializeMessage: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
        }
    }

    /**
     * Updates the subject of a message
     */
    public void updateSubject(AS2MessageInfo info) {
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "UPDATE messages SET msgsubject=? WHERE messageid=?");
            statement.setString(1, info.getSubject());
            //condition
            statement.setString(2, info.getMessageId());
            statement.executeUpdate();
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.updateSubject: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.updateSubject: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
        }
    }

    public void incResendCounter(String messageId) {
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "UPDATE messages SET resendcounter=(1+(SELECT resendcounter FROM messages WHERE messageId=?)) WHERE messageid=?");
            //condition
            statement.setString(1, messageId);
            statement.setString(2, messageId);
            statement.executeUpdate();
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.updateResendCounter: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.updateResendCounter: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
        }
    }

    /**
     * Inserts a new message entry into the database
     */
    private void updateMessage(AS2MessageInfo info) {
        PreparedStatement statement = null;
        try {
            statement = this.runtimeConnection.prepareStatement(
                    "UPDATE messages SET encryption=?,direction=?,rawfilename=?,receiverid=?,"
                    + "senderid=?,signature=?,state=?,syncmdn=?,headerfilename=?,useragent=?,"
                    + "rawdecryptedfilename=?,senderhost=?,"
                    + "contentmic=?,msgcompression=?,messagetype=?,asyncmdnurl=?,msgsubject=?,userdefinedid=?"
                    + " WHERE messageid=?");
            statement.setInt(1, info.getEncryptionType());
            statement.setInt(2, info.getDirection());
            statement.setString(3, info.getRawFilename());
            statement.setString(4, info.getReceiverId());
            statement.setString(5, info.getSenderId());
            statement.setInt(6, info.getSignType());
            statement.setInt(7, info.getState());
            statement.setInt(8, info.requestsSyncMDN() ? 1 : 0);
            statement.setString(9, info.getHeaderFilename());
            statement.setString(10, info.getUserAgent());
            statement.setString(11, info.getRawFilenameDecrypted());
            statement.setString(12, info.getSenderHost());
            statement.setString(13, info.getReceivedContentMIC());
            statement.setInt(14, info.getCompressionType());
            statement.setInt(15, info.getMessageType());
            statement.setString(16, info.getAsyncMDNURL());
            statement.setString(17, info.getSubject());
            if (info.getUserdefinedId() != null) {
                statement.setString(18, info.getUserdefinedId());
            } else {
                statement.setNull(18, Types.VARCHAR);
            }
            //condition
            statement.setString(19, info.getMessageId());
            statement.executeUpdate();
            //insert payload and inc transaction counter
            AS2Message message = new AS2Message(info);
            this.insertPayloads(info.getMessageId(), message.getPayloads());
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.updateMessage: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.updateMessage: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
        }
    }

    /**
     * Returns a list of all messages that are older than the passed timestamp
     *
     * @param state pass -1 for any state else only messages of the requested
     * state are returned
     */
    public List<AS2MessageInfo> getMessagesSendOlderThan(long yourCurrentTimezoneTime) {
        List<AS2MessageInfo> messageList = new ArrayList<AS2MessageInfo>();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            String query = "SELECT * FROM messages WHERE (senddateutc IS NOT NULL) AND senddateutc < ? AND state=?";
            statement = this.runtimeConnection.prepareStatement(query);
            statement.setTimestamp(1, new java.sql.Timestamp(yourCurrentTimezoneTime), this.calendarUTC);
            statement.setInt(2, AS2Message.STATE_PENDING);
            result = statement.executeQuery();
            while (result.next()) {
                AS2MessageInfo info = new AS2MessageInfo();
                info.setInitDate(result.getTimestamp("initdateutc", this.calendarUTC));
                info.setEncryptionType(result.getInt("encryption"));
                info.setDirection(result.getInt("direction"));
                info.setMessageType(result.getInt("messagetype"));
                info.setMessageId(result.getString("messageid"));
                info.setRawFilename(result.getString("rawfilename"));
                info.setReceiverId(result.getString("receiverid"));
                info.setSenderId(result.getString("senderid"));
                info.setSignType(result.getInt("signature"));
                info.setState(result.getInt("state"));
                info.setRequestsSyncMDN(result.getInt("syncmdn") == 1);
                info.setHeaderFilename(result.getString("headerfilename"));
                info.setRawFilenameDecrypted(result.getString("rawdecryptedfilename"));
                info.setSenderHost(result.getString("senderhost"));
                info.setUserAgent(result.getString("useragent"));
                info.setReceivedContentMIC(result.getString("contentmic"));
                info.setCompressionType(result.getInt("msgcompression"));
                info.setAsyncMDNURL(result.getString("asyncmdnurl"));
                info.setSubject(result.getString("msgsubject"));
                info.setResendCounter(result.getInt("resendcounter"));
                info.setUserdefinedId(result.getString("userdefinedid"));
                messageList.add(info);
            }
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.getMessagesSendOlderThan: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getMessagesSendOlderThan: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getMessagesSendOlderThan: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
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
        List<AS2MessageInfo> messageList = new ArrayList<AS2MessageInfo>();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            String query = "SELECT * FROM messages WHERE initdateutc < ?";
            if (state != -1) {
                query = query + " AND state=" + state;
            }
            statement = this.runtimeConnection.prepareStatement(query);
            statement.setTimestamp(1, new java.sql.Timestamp(initTimestamp), this.calendarUTC);
            result = statement.executeQuery();
            while (result.next()) {
                AS2MessageInfo info = new AS2MessageInfo();
                info.setInitDate(result.getTimestamp("initdateutc"));
                info.setEncryptionType(result.getInt("encryption"));
                info.setDirection(result.getInt("direction"));
                info.setMessageType(result.getInt("messagetype"));
                info.setMessageId(result.getString("messageid"));
                info.setRawFilename(result.getString("rawfilename"));
                info.setReceiverId(result.getString("receiverid"));
                info.setSenderId(result.getString("senderid"));
                info.setSignType(result.getInt("signature"));
                info.setState(result.getInt("state"));
                info.setRequestsSyncMDN(result.getInt("syncmdn") == 1);
                info.setHeaderFilename(result.getString("headerfilename"));
                info.setRawFilenameDecrypted(result.getString("rawdecryptedfilename"));
                info.setSenderHost(result.getString("senderhost"));
                info.setUserAgent(result.getString("useragent"));
                info.setReceivedContentMIC(result.getString("contentmic"));
                info.setCompressionType(result.getInt("msgcompression"));
                info.setAsyncMDNURL(result.getString("asyncmdnurl"));
                info.setSubject(result.getString("msgsubject"));
                info.setResendCounter(result.getInt("resendcounter"));
                info.setUserdefinedId(result.getString("userdefinedid"));
                messageList.add(info);
            }
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.getMessagesOlderThan: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getMessagesOlderThan: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getMessagesOlderThan: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
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
        List<AS2MessageInfo> messageList = new ArrayList<AS2MessageInfo>();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            String query = "SELECT * FROM messages WHERE initdateutc > ?";
            if (state != -1) {
                query = query + " AND state=" + state;
            }
            statement = this.runtimeConnection.prepareStatement(query);
            statement.setTimestamp(1, new java.sql.Timestamp(initTimestamp), this.calendarUTC);
            result = statement.executeQuery();
            while (result.next()) {
                AS2MessageInfo info = new AS2MessageInfo();
                info.setInitDate(result.getTimestamp("initdateutc"));
                info.setEncryptionType(result.getInt("encryption"));
                info.setDirection(result.getInt("direction"));
                info.setMessageType(result.getInt("messagetype"));
                info.setMessageId(result.getString("messageid"));
                info.setRawFilename(result.getString("rawfilename"));
                info.setReceiverId(result.getString("receiverid"));
                info.setSenderId(result.getString("senderid"));
                info.setSignType(result.getInt("signature"));
                info.setState(result.getInt("state"));
                info.setRequestsSyncMDN(result.getInt("syncmdn") == 1);
                info.setHeaderFilename(result.getString("headerfilename"));
                info.setRawFilenameDecrypted(result.getString("rawdecryptedfilename"));
                info.setSenderHost(result.getString("senderhost"));
                info.setUserAgent(result.getString("useragent"));
                info.setReceivedContentMIC(result.getString("contentmic"));
                info.setCompressionType(result.getInt("msgcompression"));
                info.setAsyncMDNURL(result.getString("asyncmdnurl"));
                info.setSubject(result.getString("msgsubject"));
                info.setResendCounter(result.getInt("resendcounter"));
                info.setUserdefinedId(result.getString("userdefinedid"));
                messageList.add(info);
            }
        } catch (Exception e) {
            this.logger.severe("MessageAccessDB.getMessagesOlderThan: " + e.getMessage());
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e, statement);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getMessagesOlderThan: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    this.logger.severe("MessageAccessDB.getMessagesOlderThan: " + e.getMessage());
                    Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
                }
            }
        }
        return (messageList);
    }

}
