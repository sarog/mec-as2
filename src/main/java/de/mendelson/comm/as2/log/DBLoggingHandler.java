//$Header: /as2/de/mendelson/comm/as2/log/DBLoggingHandler.java 24    2/11/23 14:02 Heller $
package de.mendelson.comm.as2.log;

import de.mendelson.comm.as2.message.AS2MDNInfo;
import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.comm.as2.server.AS2Server;

import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import de.mendelson.util.database.IDBDriverManager;
import java.io.UnsupportedEncodingException;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Handler to log logger data to a data base
 * @author S.Heller
 * @version $Revision: 24 $
 */
public class DBLoggingHandler extends Handler {

    private final Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private LogAccessDB logAccess;

    public DBLoggingHandler(IDBDriverManager dbDriverManager) {
        //store all log levels by default, could be overwritten
        this.setLevel(Level.ALL);
        try {
            this.logAccess = new LogAccessDB(dbDriverManager);
        } catch (Exception e) {
            this.logger.severe("DBLoggingHandler: " + e.getMessage());
        }
    }

    /**
     * Set (or change) the character encoding used by this <tt>Handler</tt>.
     * <p>
     * The encoding should be set before any <tt>LogRecords</tt> are written
     * to the <tt>Handler</tt>.
     *
     * @param encoding  The name of a supported character encoding.
     *	      May be null, to indicate the default platform encoding.
     * @exception  SecurityException  if a security manager exists and if
     *             the caller does not have <tt>LoggingPermission("control")</tt>.
     * @exception  UnsupportedEncodingException if the named encoding is
     *		not supported.
     */
    @Override
    public void setEncoding(String encoding)
            throws SecurityException, java.io.UnsupportedEncodingException {
        super.setEncoding(encoding);
    }

    /**
     * Format and publish a LogRecord.
     * @param  record  description of the log event
     */
    @Override
    public synchronized void publish(LogRecord record) {
        if (!isLoggable(record)) {
            return;
        }
        try {
            this.logMessage(record.getLevel(), record.getMillis(), record.getMessage(),
                    record.getParameters());
        } catch (Exception ex) {
            // We don't want to throw an exception here, but we
            // report the exception to any registered ErrorManager.
            reportError(null, ex, ErrorManager.WRITE_FAILURE);
        }
    }

    /**
     * Check if this Handler would actually log a given LogRecord, depending of the
     * log level
     * @param record a LogRecord
     * @return true if the LogRecord would be logged.
     *
     */
    @Override
    public boolean isLoggable(LogRecord record) {
        return super.isLoggable(record);
    }

    /**
     * Flush any buffered messages.
     */
    @Override
    public synchronized void flush() {
    }

    /**Just flushes the current message
     */
    @Override
    public synchronized void close() throws SecurityException {
        this.flush();
    }

    /**Finally logs the passed message to the text component and sets the canvas pos
     */
    private synchronized void logMessage(Level level, long millis, String message,
            Object[] parameter) {
        if (parameter != null && parameter.length > 0) {
            if (parameter[0] instanceof AS2MessageInfo) {
                AS2MessageInfo info = (AS2MessageInfo) parameter[0];
                message = "[" + info.getMessageId() + "] " + message;
                this.logAccess.logAsTransaction(
                        level, millis, message, info.getMessageId());
            } else if (parameter[0] instanceof AS2MDNInfo) {
                AS2MDNInfo info = (AS2MDNInfo) parameter[0];
                message = "[" + info.getMessageId() + "] " + message;
                this.logAccess.logAsTransaction(
                        level, millis, message, info.getRelatedMessageId());
            }
        }
    }
}
