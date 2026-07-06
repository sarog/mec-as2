//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ExecuteShellCommand.java 24    31/03/26 9:30 Heller $
package de.mendelson.comm.as2.message.postprocessingevent;

import de.mendelson.comm.as2.log.LogAccessDB;
import de.mendelson.comm.as2.log.LogEntry;
import de.mendelson.comm.as2.message.AS2LoggerOutputStream;
import de.mendelson.comm.as2.message.AS2MDNInfo;
import de.mendelson.comm.as2.message.AS2Message;
import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.comm.as2.message.AS2Payload;
import de.mendelson.comm.as2.message.MDNAccessDB;
import de.mendelson.comm.as2.message.MessageAccessDB;
import de.mendelson.comm.as2.message.MessageType;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerAccessDB;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.Exec;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.database.IDBDriverManager;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
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
 * Allows to execute a shell command. This is used to execute a shell command on
 * message receipt
 *
 * @author S.Heller
 * @version $Revision: 24 $
 */
public final class ExecuteShellCommand implements IProcessingExecution {

    private static final Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private final MessageAccessDB messageAccess;
    private final MDNAccessDB mdnAccess;
    private final PartnerAccessDB partnerAccess;
    private final IDBDriverManager dbDriverManager;
    /**
     * Localize your GUI!
     */
    private static final MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleExecuteShellCommand.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    public ExecuteShellCommand(IDBDriverManager dbDriverManager) {
        this.dbDriverManager = dbDriverManager;
        this.messageAccess = new MessageAccessDB(dbDriverManager);
        this.mdnAccess = new MDNAccessDB(dbDriverManager);
        this.partnerAccess = new PartnerAccessDB(dbDriverManager);
    }

    /**
     * Executes a post processing shell command as defined in the partner events
     */
    @Override
    public void executeProcess(ProcessingEvent event) throws Exception {
        //get all required values for this event
        AS2MessageInfo messageInfo = this.messageAccess.getLastMessageEntry(event.getMessageId());
        if (messageInfo == null) {
            throw new Exception(rb.getResourceString("messageid.nolonger.exist", event.getMessageId()));
        }
        AS2MDNInfo mdnInfo = null;
        if (event.getMDNId() != null) {
            List<AS2MDNInfo> mdnInfoList = this.mdnAccess.getMDN(event.getMessageId());
            if (mdnInfoList != null && !mdnInfoList.isEmpty()) {
                mdnInfo = mdnInfoList.get(0);
            }
        }
        if (event.getTriggerType() == ProcessingEventTriggerType.SEND_FAILURE
                || event.getTriggerType() == ProcessingEventTriggerType.SEND_SUCCESS) {
            this.executeShellCommandOnSend(event, messageInfo, mdnInfo);
        } else {
            this.executeShellCommandOnReceipt(event, messageInfo);
        }
    }

    /**
     * Executes a shell command for an inbound AS2 message if this has been
     * defined in the partner settings
     */
    private void executeShellCommandOnSend(ProcessingEvent event, AS2MessageInfo messageInfo, AS2MDNInfo mdnInfo)
            throws Exception {
        //do not execute a command for CEM messages
        if (messageInfo.getMessageType() == MessageType.CEM) {
            return;
        }
        Partner messageSender = this.partnerAccess.getPartnerByAS2Id(messageInfo.getSenderId());
        Partner messageReceiver = this.partnerAccess.getPartnerByAS2Id(messageInfo.getReceiverId());
        List<AS2Payload> payload = this.messageAccess.getPayload(messageInfo.getMessageId());
        String rawCommand = event.getParameter().get(0);
        if (payload != null && !payload.isEmpty()) {
            logger.log(Level.INFO, rb.getResourceString("executing.send",
                    new Object[]{
                        messageSender.getName(),
                        messageReceiver.getName()
                    }), messageInfo);
            for (AS2Payload singlePayload : payload) {
                if (singlePayload.getPayloadFilename() == null) {
                    throw new PostprocessingException("executeShellCommandOnSend: payload filename does not exist.",
                            messageSender, messageReceiver);
                }
                String originalFilename = singlePayload.getOriginalFilename();
                String command = rawCommand.replace("${filename}", this.sanitize(originalFilename));
                command = command.replace("${fullstoragefilename}", singlePayload.getPayloadFilename());
                command = command.replace("${sender}", messageSender.getName());
                command = command.replace("${receiver}", messageReceiver.getName());
                command = command.replace("${messageid}", messageInfo.getMessageId());
                if (messageInfo.getSubject() != null) {
                    command = command.replace("${subject}", this.sanitize(messageInfo.getSubject()));
                } else {
                    command = command.replace("${subject}", "");
                }
                if (messageInfo.getUserdefinedId() != null) {
                    command = command.replace("${userdefinedid}", messageInfo.getUserdefinedId());
                } else {
                    command = command.replace("${userdefinedid}", "");
                }
                if (mdnInfo != null) {
                    command = command.replace("${mdntext}", mdnInfo.getRemoteMDNText());
                } else {
                    command = command.replace("${mdntext}", "");
                }
                //add log?
                if (command.contains("${log}")) {
                    try {
                        LogAccessDB logAccess = new LogAccessDB(this.dbDriverManager);
                        List<LogEntry> entries = logAccess.getLog(messageInfo.getMessageId());
                        StringBuilder logBuffer = new StringBuilder();
                        for (LogEntry logEntry : entries) {
                            logBuffer.append(logEntry.getMessage()).append("\\n");
                        }
                        //dont use single and double quotes, this is used in command line environment
                        String logText = logBuffer.toString().replace("\"", "");
                        logText = logText.replace("'", "");
                        command = command.replace("${log}", logText);
                    } catch (Exception e) {
                        throw new PostprocessingException(e.getMessage(), messageSender, messageReceiver);
                    }
                }
                logger.log(Level.INFO, rb.getResourceString("executing.command",
                        new Object[]{command}), messageInfo);
                int returnCode = 0;
                try {
                    Exec exec = new Exec();
                    exec.setWaitFor(true);
                    returnCode = exec.start(command, new PrintStream(new AS2LoggerOutputStream(logger, messageInfo)),
                            new PrintStream(new AS2LoggerOutputStream(logger, messageInfo)));
                    logger.log(Level.INFO, rb.getResourceString("executed.command",
                            new Object[]{String.valueOf(returnCode)}), messageInfo);
                } catch (Throwable e) {
                    throw new PostprocessingException(e.getMessage(), messageSender, messageReceiver);
                }
                if (returnCode != 0) {
                    throw new PostprocessingException(rb.getResourceString("executed.command",
                            new Object[]{String.valueOf(returnCode)}),
                            messageSender, messageReceiver);
                }
            }
        } else {
            throw new PostprocessingException("executeShellCommandOnSend: No payload found for message " + messageInfo.getMessageId(),
                    messageSender, messageReceiver);
        }
    }

    /**
     * Executes a shell command for an inbound AS2 message if this has been
     * defined in the partner settings
     */
    private void executeShellCommandOnReceipt(ProcessingEvent event, AS2MessageInfo messageInfo)
            throws Exception {
        //do not execute a command for CEM messages
        if (messageInfo.getMessageType() == MessageType.CEM) {
            return;
        }
        Partner messageSender = this.partnerAccess.getPartnerByAS2Id(messageInfo.getSenderId());
        Partner messageReceiver = this.partnerAccess.getPartnerByAS2Id(messageInfo.getReceiverId());
        List<AS2Payload> payload = this.messageAccess.getPayload(messageInfo.getMessageId());
        String definedRawCommand = event.getParameter().get(0);
        if (payload != null) {
            logger.log(Level.INFO, rb.getResourceString("executing.receipt",
                    new Object[]{
                        messageSender.getName(),
                        messageReceiver.getName()
                    }), messageInfo);
            for (int i = 0; i < payload.size(); i++) {
                if (payload.get(i).getPayloadFilename() == null) {
                    continue;
                }
                String filename = payload.get(i).getPayloadFilename();
                String originalFilename = payload.get(i).getOriginalFilename();
                if (originalFilename == null) {
                    originalFilename = "NOT_TRANSMITTED";
                }
                String rawCommand = definedRawCommand.replace("${filename}",
                        Paths.get(filename).toAbsolutePath().toString());
                rawCommand = rawCommand.replace("${sender}", messageSender.getName());
                rawCommand = rawCommand.replace("${receiver}", messageReceiver.getName());
                rawCommand = rawCommand.replace("${messageid}", messageInfo.getMessageId());
                if (messageInfo.getSubject() != null) {
                    rawCommand = rawCommand.replace("${subject}", this.sanitize(messageInfo.getSubject()));
                } else {
                    rawCommand = rawCommand.replace("${subject}", "");
                }
                rawCommand = rawCommand.replace("${originalfilename}", this.sanitize(originalFilename));
                logger.log(Level.INFO, rb.getResourceString("executing.command",
                        new Object[]{rawCommand}), messageInfo);
                Exec exec = new Exec();
                exec.setWaitFor(true);
                int returnCode = 0;
                try {
                    returnCode = exec.start(rawCommand, new PrintStream(new AS2LoggerOutputStream(logger, messageInfo)),
                            new PrintStream(new AS2LoggerOutputStream(logger, messageInfo)));
                    logger.log(Level.INFO, rb.getResourceString("executed.command",
                            new Object[]{String.valueOf(returnCode)}), messageInfo);
                } catch (Throwable e) {
                    throw new PostprocessingException(e.getMessage(), messageSender, messageReceiver);
                }
                if (returnCode != 0) {
                    throw new PostprocessingException(rb.getResourceString("executed.command",
                            new Object[]{String.valueOf(returnCode)}),
                            messageSender, messageReceiver);
                }
            }
        } else {
            throw new PostprocessingException("executeShellCommandOnReceipt: No payload found for message " + messageInfo.getMessageId(),
                    messageSender, messageReceiver);
        }
    }

    /**
     * This method prevents shell injection via message parameter - means if there is something
     * weird in the subject etc this should be removed before the command should
     * be executed on the shell. It is very uncommon that this happens - which partner will send you a subject that will
     * attack your system? Makes no sense. But anyway
     */
    private String sanitize(String inputStr) {
        if (inputStr == null) {
            return "";
        }
        //Remove and replace the following character which VERY are dangerous: ; & | > < ` $ \
        return inputStr.replaceAll("[;&|><`\\$\\\\]", "_");
    }

}
