//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ProcessingEvent.java 24    1/04/26 11:28 Heller $
package de.mendelson.comm.as2.message.postprocessingevent;

import de.mendelson.comm.as2.message.AS2MDNInfo;
import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.comm.as2.message.MessageStateType;
import de.mendelson.comm.as2.message.MessageType;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerAccessDB;
import de.mendelson.comm.as2.partner.PartnerEventInformation;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.database.IDBDriverManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.logging.Level;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Object that stores a post processing event that is defined in the partner
 * panel
 *
 * @author S.Heller
 * @version $Revision: 24 $
 */
public class ProcessingEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleProcessingEvent.class.getName());
        }
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    private static final Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    /**
     * Related message/mdn id
     */
    private String messageId;
    private String mdnId;
    /**
     * Date of this message
     */
    private long initDate;
    /**
     * Command to execute
     */
    private List<String> parameter = new ArrayList<String>();
    private ProcessingEventType processType;
    private ProcessingEventTriggerType triggerType;

    public ProcessingEvent(ProcessingEventTriggerType triggerType, ProcessingEventType processingEventType, String messageId,
            String mdnId, List<String> parameter, long initDate) {
        this.triggerType = triggerType;
        this.processType = processingEventType;
        this.messageId = messageId;
        this.mdnId = mdnId;
        this.parameter.addAll(parameter);
        this.initDate = initDate;
    }

    public ProcessingEvent(ProcessingEventTriggerType triggerType, ProcessingEventType processType, 
            String messageId, String mdnId, List<String> parameter) {
        this(triggerType, processType, messageId, mdnId, parameter, System.currentTimeMillis());
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public ProcessingEvent() {
    }

    public static String getLocalizedProcessType(ProcessingEventType processingEventType) {
        return (rb.getResourceString("processtype." + processingEventType.toInt()));
    }

    public static String getLocalizedEventType(ProcessingEventTriggerType triggerType) {
        return (rb.getResourceString("eventtype." + triggerType.toInt()));
    }

    /**
     * Enqueue an event if it should be executed for the passed message/MDN id
     * combination
     */
    public static void enqueueEventIfRequired(IDBDriverManager dbDriverManager,
            AS2MessageInfo messageInfo, AS2MDNInfo mdnInfo) {
        PartnerAccessDB partnerAccess = new PartnerAccessDB(dbDriverManager);
        Partner messageSender = partnerAccess.getPartnerByAS2Id(messageInfo.getSenderId());
        Partner messageReceiver = partnerAccess.getPartnerByAS2Id(messageInfo.getReceiverId());
        PartnerEventInformation receiverEvents = messageReceiver.getPartnerEvents();
        PartnerEventInformation senderEvents = messageSender.getPartnerEvents();
        ProcessingEventTriggerType triggerType;
        ProcessingEventType processType;
        List<String> parameter = new ArrayList<String>();
        if (messageSender.isLocalStation()) {
            if (messageInfo.getState() == MessageStateType.STOPPED) {
                triggerType = ProcessingEventTriggerType.SEND_FAILURE;
                processType = receiverEvents.getProcess(triggerType);
                if (receiverEvents.isUseOnSendError()
                        && receiverEvents.hasParameter(triggerType)) {
                    parameter = receiverEvents.getParameter(triggerType);
                }
            } else {
                triggerType = ProcessingEventTriggerType.SEND_SUCCESS;
                processType = receiverEvents.getProcess(triggerType);
                if (receiverEvents.isUseOnSendSuccess()
                        && receiverEvents.hasParameter(triggerType)) {
                    parameter = receiverEvents.getParameter(triggerType);
                }
            }
        } else {
            triggerType = ProcessingEventTriggerType.RECEIPT_SUCCESS;
            processType = senderEvents.getProcess(triggerType);
            if (senderEvents.isUseOnReceipt()
                    && senderEvents.hasParameter(triggerType)) {
                parameter = senderEvents.getParameter(triggerType);
            }
        }
        if (!parameter.isEmpty()) {
            String messageId = messageInfo.getMessageId();
            String mdnId = null;
            if (mdnInfo != null) {
                mdnId = mdnInfo.getMessageId();
            }
            //do not enqueue this if its a CEM - but display that the postprocessing has been skipped
            if (messageInfo.getMessageType() == MessageType.CEM) {
                logger.log(Level.INFO, rb.getResourceString("event.skipped.cem"), messageInfo);
                return;
            } else {
                ProcessingEvent event = new ProcessingEvent(triggerType, processType, messageId, mdnId, parameter);
                ProcessingEventAccessDB processingEventDB = new ProcessingEventAccessDB(
                        dbDriverManager);
                processingEventDB.addEventToExecute(event);
                logger.log(Level.INFO, rb.getResourceString("event.enqueued",
                        rb.getResourceString("processtype." + event.getProcessType().toInt())), messageInfo);
            }
        }
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @return the initDate
     */
    public long getInitDate() {
        return initDate;
    }

    /**
     * @return the command
     */
    public List<String> getParameter() {
        return (this.parameter);
    }

    /**
     * @return the type
     */
    public ProcessingEventTriggerType getTriggerType() {
        return triggerType;
    }

    /**
     * @return the mdnId
     */
    public String getMDNId() {
        return mdnId;
    }

    /**
     * @return the processType
     */
    public ProcessingEventType getProcessType() {
        return processType;
    }

}
