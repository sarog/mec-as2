//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ExecuteMoveToDir.java 21    31/03/26 9:30 Heller $
package de.mendelson.comm.as2.message.postprocessingevent;

import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.comm.as2.message.AS2Payload;
import de.mendelson.comm.as2.message.MessageAccessDB;
import de.mendelson.comm.as2.message.MessageType;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerAccessDB;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.database.IDBDriverManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
 * @version $Revision: 21 $
 */
public final class ExecuteMoveToDir implements IProcessingExecution {

    private static final Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private final MessageAccessDB messageAccess;
    private final PartnerAccessDB partnerAccess;
    private static final MecResourceBundle rb;
    static{
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleExecuteMoveToDir.class.getName());
        } //load up  resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    public ExecuteMoveToDir(IDBDriverManager dbDriverManager) {
        this.messageAccess = new MessageAccessDB(dbDriverManager);
        this.partnerAccess = new PartnerAccessDB(dbDriverManager);
    }

    /**
     * Executes a post processing event: move file to directory
     */
    @Override
    public void executeProcess(ProcessingEvent event) throws Exception {
        //get all required values for this event
        AS2MessageInfo messageInfo = this.messageAccess.getLastMessageEntry(event.getMessageId());
        if (messageInfo == null) {
            throw new Exception(rb.getResourceString("messageid.nolonger.exist", event.getMessageId()));
        } else {
            if (event.getTriggerType() == ProcessingEventTriggerType.SEND_FAILURE
                    || event.getTriggerType() == ProcessingEventTriggerType.SEND_SUCCESS) {
                this.executeMoveToDirOnSend(event, messageInfo);
            } else {
                this.executeMoveToDirOnReceipt(event, messageInfo);
            }
        }
    }

    /**
     * Executes a move to dir command for an inbound AS2 message if this has
     * been defined in the partner settings
     */
    private void executeMoveToDirOnSend(ProcessingEvent event, AS2MessageInfo messageInfo) throws Exception {
        //do not execute anything for CEM messages
        if (messageInfo.getMessageType() == MessageType.CEM) {
            return;
        }
        Partner messageSender = this.partnerAccess.getPartnerByAS2Id(messageInfo.getSenderId());
        Partner messageReceiver = this.partnerAccess.getPartnerByAS2Id(messageInfo.getReceiverId());
        List<AS2Payload> payload = this.messageAccess.getPayload(messageInfo.getMessageId());
        String targetDirStr = event.getParameter().get(0);
        if (payload != null && !payload.isEmpty()) {
            logger.log(Level.INFO, rb.getResourceString("executing.send",
                    new Object[]{
                        messageSender.getName(),
                        messageReceiver.getName()
                    }), messageInfo);
            logger.log(Level.INFO, rb.getResourceString("executing.targetdir",
                    Paths.get(targetDirStr).toAbsolutePath().toString()), messageInfo);
            for (AS2Payload singlePayload : payload) {
                if (singlePayload.getPayloadFilename() == null) {
                    throw new PostprocessingException("executeMoveToDirOnSend: payload filename does not exist.",
                            messageSender, messageReceiver);
                }
                try {
                    String usedTargetFilename = singlePayload.getOriginalFilename();
                    if( usedTargetFilename == null ){
                        usedTargetFilename = Paths.get(singlePayload.getPayloadFilename()).getFileName().toString();
                    }
                    Path sourceFile = Paths.get(singlePayload.getPayloadFilename());
                    Path targetFile = Paths.get(targetDirStr, usedTargetFilename);
                    logger.log(Level.INFO, rb.getResourceString("executing.movetodir",
                            new Object[]{
                                sourceFile.toAbsolutePath().toString(),
                                targetFile.toAbsolutePath().toString()
                            }), messageInfo);
                    Files.move(sourceFile, targetFile,
                            StandardCopyOption.ATOMIC_MOVE,
                            StandardCopyOption.REPLACE_EXISTING);
                    logger.log(Level.INFO, rb.getResourceString("executing.movetodir.success"), messageInfo);
                } catch (Exception e) {
                    StringBuilder errorBuilder = new StringBuilder();
                    if (e.getCause() != null) {
                        errorBuilder.append("[")
                                .append(e.getCause().getClass().getSimpleName())
                                .append("] ")
                                .append(e.getCause().getMessage())
                                .append(" -- ");
                    }
                    errorBuilder.append("[")
                            .append(e.getClass().getSimpleName())
                            .append("] ")
                            .append(e.getMessage());
                    throw new PostprocessingException(errorBuilder.toString(), messageSender, messageReceiver);
                }
            }
        } else {
            throw new PostprocessingException("executeMoveToDirOnSend: No payload found for message "
                    + messageInfo.getMessageId(),
                    messageSender, messageReceiver);
        }
    }

    /**
     * Executes a shell command for an inbound AS2 message if this has been
     * defined in the partner settings
     */
    private void executeMoveToDirOnReceipt(ProcessingEvent event, AS2MessageInfo messageInfo) throws Exception {
        //do not execute a command for CEM messages
        if (messageInfo.getMessageType() == MessageType.CEM) {
            return;
        }
        Partner messageSender = this.partnerAccess.getPartnerByAS2Id(messageInfo.getSenderId());
        Partner messageReceiver = this.partnerAccess.getPartnerByAS2Id(messageInfo.getReceiverId());
        List<AS2Payload> payload = this.messageAccess.getPayload(messageInfo.getMessageId());
        String targetDirStr = event.getParameter().get(0);
        if (payload != null && !payload.isEmpty()) {
            logger.log(Level.INFO, rb.getResourceString("executing.receipt",
                    new Object[]{
                        messageSender.getName(),
                        messageReceiver.getName()
                    }), messageInfo);
            logger.log(Level.INFO, rb.getResourceString("executing.targetdir",
                    Paths.get(targetDirStr).toAbsolutePath().toString()), messageInfo);
            for (AS2Payload singlePayload : payload) {
                if (singlePayload.getPayloadFilename() == null) {
                    throw new PostprocessingException("executeMoveToDirOnReceipt: payload filename does not exist.",
                            messageSender, messageReceiver);
                }
                String filename = singlePayload.getPayloadFilename();
                String usedTargetFilename = singlePayload.getOriginalFilename();
                if (usedTargetFilename == null) {
                    usedTargetFilename = Paths.get(filename).getFileName().toString();
                }
                Path sourceFile = Paths.get(singlePayload.getPayloadFilename());
                Path targetFile = Paths.get(targetDirStr, usedTargetFilename);
                logger.log(Level.INFO, rb.getResourceString("executing.movetodir",
                        new Object[]{
                            sourceFile.toAbsolutePath().toString(),
                            targetFile.toAbsolutePath().toString()
                        }), messageInfo);
                try {
                    Files.move(sourceFile, targetFile,
                            StandardCopyOption.ATOMIC_MOVE,
                            StandardCopyOption.REPLACE_EXISTING);
                    logger.log(Level.INFO, rb.getResourceString("executing.movetodir.success"), messageInfo);
                } catch (Exception e) {
                    throw new PostprocessingException(e.getMessage(), messageSender, messageReceiver);
                }
            }
        } else {
            throw new PostprocessingException("executeMoveToDirOnReceipt: No payload found for message " + messageInfo.getMessageId(),
                    messageSender, messageReceiver);
        }
    }
}
