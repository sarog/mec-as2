//$Header: /oftp2/de/mendelson/util/clientserver/log/ClientServerLoggingHandler.java 7     31/03/26 17:51 Heller $
package de.mendelson.util.clientserver.log;

import de.mendelson.util.clientserver.ClientServerSessionHandler;
import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import de.mendelson.util.clientserver.messages.LoggableParameterClientServer;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Handler to log logger data via the client-server interface
 *
 * @author S.Heller
 * @version $Revision: 7 $
 */
public class ClientServerLoggingHandler extends Handler {

    private final ClientServerSessionHandler sessionHandler;

    public ClientServerLoggingHandler(ClientServerSessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
        this.setErrorManager(new ClientServerLoggingErrorHandler());
    }

    /**
     * Set (or change) the character encoding used by this <tt>Handler</tt>.
     * <p>
     * The encoding should be set before any <tt>LogRecords</tt> are written to
     * the <tt>Handler</tt>.
     *
     * @param encoding The name of a supported character encoding. May be null,
     * to indicate the default platform encoding.
     * @exception SecurityException if a security manager exists and if the
     * caller does not have <tt>LoggingPermission("control")</tt>.
     * @exception UnsupportedEncodingException if the named encoding is not
     * supported.
     */
    @Override
    public void setEncoding(String encoding)
            throws SecurityException, java.io.UnsupportedEncodingException {
        super.setEncoding(encoding);
    }

    /**
     * Format and publish a LogRecord.
     *
     * @param logRecord description of the log event
     */
    @Override
    public synchronized void publish(LogRecord logRecord) {
        if (!this.isLoggable(logRecord)) {
            return;
        }
        try {
            String[] loggableParameterArray = null;
            Object[] givenParameter = logRecord.getParameters();
            if (givenParameter != null) {
                loggableParameterArray = new String[givenParameter.length];
                for (int i = 0; i < givenParameter.length; i++) {
                    Object objParameter = givenParameter[i];
                    if (objParameter != null) {
                        if (!(objParameter instanceof LoggableParameterClientServer)) {
                            throw new RuntimeException("Client-Server logging handler: All log parameter must implement "
                                    + " the interface LoggableParameter, found " + objParameter.getClass().getName());
                        }
                        loggableParameterArray[i]
                                = ((LoggableParameterClientServer) objParameter).getLoggingPrefix() + ":"
                                + ((LoggableParameterClientServer) objParameter).getLoggingId();
                    }
                }
            }
            this.logMessage(logRecord.getLevel(), logRecord.getMessage(),
                    loggableParameterArray);
        } catch (Exception ex) {
            // We don't want to throw an exception here, but we
            // report the exception to any registered ErrorManager.
            reportError(null, ex, ErrorManager.WRITE_FAILURE);
        }
    }

    /**
     * Check if this Handler would actually log a given LogRecord, depending of
     * the log level
     *
     * @param logRecord a LogRecord
     * @return true if the LogRecord would be logged.
     *
     */
    @Override
    public boolean isLoggable(LogRecord logRecord) {
        return super.isLoggable(logRecord);
    }

    /**
     * Flush any buffered messages.
     */
    @Override
    public synchronized void flush() {
    }

    /**
     * Just flushes the current message
     */
    @Override
    public synchronized void close() throws SecurityException {
        this.flush();
    }

    /**
     * Finally logs the passed message to the text component and sets the canvas
     * pos
     */
    private synchronized void logMessage(Level level, String message, String[] parameter) {
        this.sessionHandler.broadcastLogMessage(level, message, parameter);
    }
}
