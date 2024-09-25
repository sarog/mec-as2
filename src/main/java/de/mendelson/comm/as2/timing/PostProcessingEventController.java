//$Header: /as2/de/mendelson/comm/as2/timing/PostProcessingEventController.java 19    2/11/23 15:53 Heller $
package de.mendelson.comm.as2.timing;

import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.comm.as2.message.MessageAccessDB;
import de.mendelson.comm.as2.message.postprocessingevent.ExecuteMoveToDir;
import de.mendelson.comm.as2.message.postprocessingevent.ExecuteMoveToPartner;
import de.mendelson.comm.as2.message.postprocessingevent.ExecuteShellCommand;
import de.mendelson.comm.as2.message.postprocessingevent.IProcessingExecution;
import de.mendelson.comm.as2.message.postprocessingevent.PostprocessingException;
import de.mendelson.comm.as2.message.postprocessingevent.ProcessingEvent;
import de.mendelson.comm.as2.message.postprocessingevent.ProcessingEventAccessDB;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerEventInformation;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.NamedThreadFactory;
import de.mendelson.util.clientserver.ClientServer;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.sql.Connection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Controls the timed deletion of AS2 file entries from the file system
 *
 * @author S.Heller
 * @version $Revision: 19 $
 */
public class PostProcessingEventController {

    /**
     * Logger to log information to
     */
    private final Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private EventExecutionThread executeThread;
    private ClientServer clientserver = null;
    private final CertificateManager certificateManagerEncSign;
    private final MessageAccessDB messageAccess;
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(
            new NamedThreadFactory("postprocessing"));
    private final IDBDriverManager dbDriverManager;

    public PostProcessingEventController(ClientServer clientserver, 
            CertificateManager certificateManagerEncSign,
            IDBDriverManager dbDriverManager) throws Exception {
        this.clientserver = clientserver;
        this.certificateManagerEncSign = certificateManagerEncSign;
        this.messageAccess = new MessageAccessDB(dbDriverManager);
        this.dbDriverManager = dbDriverManager;
    }

    /**
     * Starts the embedded task that guards the files to delete
     */
    public void startEventExecution() {
        this.executeThread = new EventExecutionThread(this.dbDriverManager);
        this.scheduledExecutor.scheduleWithFixedDelay(this.executeThread, 10, 10, TimeUnit.SECONDS);
    }

    public class EventExecutionThread implements Runnable {

        private final ProcessingEventAccessDB processingEventAccess;
        private final IDBDriverManager dbDriverManager;

        public EventExecutionThread(IDBDriverManager dbDriverManager) {
            this.dbDriverManager = dbDriverManager;
            this.processingEventAccess = new ProcessingEventAccessDB(
                    dbDriverManager);
        }

        @Override
        public void run() {
            Connection runtimeConnectionNoAutoCommit = null;
            try {
                runtimeConnectionNoAutoCommit = dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME);
                runtimeConnectionNoAutoCommit.setAutoCommit(false);
                boolean entryFound = true;
                while (entryFound) {
                    entryFound = false;
                    ProcessingEvent event = this.processingEventAccess.getNextEventToExecuteAsTransaction(runtimeConnectionNoAutoCommit);
                    IProcessingExecution processExecution = null;
                    if (event != null && event.getProcessType() == PartnerEventInformation.PROCESS_EXECUTE_SHELL) {
                        processExecution = new ExecuteShellCommand(this.dbDriverManager);
                        entryFound = true;
                    } else if (event != null && event.getProcessType() == PartnerEventInformation.PROCESS_MOVE_TO_DIR) {
                        processExecution = new ExecuteMoveToDir(this.dbDriverManager);
                        entryFound = true;
                    } else if (event != null && event.getProcessType() == PartnerEventInformation.PROCESS_MOVE_TO_PARTNER) {
                        processExecution = new ExecuteMoveToPartner(this.dbDriverManager,
                                PostProcessingEventController.this.certificateManagerEncSign);
                        entryFound = true;
                    }
                    if (entryFound && processExecution != null) {
                        try {
                            processExecution.executeProcess(event);
                        } catch (Throwable e) {
                            String errorMessage = "[" + e.getClass().getSimpleName() + "] " + e.getMessage();
                            AS2MessageInfo messageInfo = messageAccess.getLastMessageEntry(event.getMessageId());
                            Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
                            logger.log(Level.WARNING, errorMessage, messageInfo);                            
                            Partner sender = null;
                            Partner receiver = null;
                            if (e instanceof PostprocessingException) {
                                sender = ((PostprocessingException) e).getSender();
                                receiver = ((PostprocessingException) e).getReceiver();
                            }
                            SystemEventManagerImplAS2.instance().newEventPostprocessingError(errorMessage,
                                    event.getMessageId(), sender, receiver,
                                    event.getProcessType(), event.getEventType());
                        }
                    }
                }
            } catch (Throwable e) {
                SystemEventManagerImplAS2.instance().systemFailure(e);
            } finally {
                if (runtimeConnectionNoAutoCommit != null) {
                    try {
                        runtimeConnectionNoAutoCommit.close();
                    } catch (Exception e) {
                        SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                    }
                }
            }
        }
    }

}
