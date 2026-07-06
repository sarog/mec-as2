//$Header: /as2/de/mendelson/comm/as2/partner/PartnerEventInformation.java 22    31/03/26 9:30 Heller $
package de.mendelson.comm.as2.partner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.comm.as2.message.postprocessingevent.ProcessingEvent;
import de.mendelson.comm.as2.message.postprocessingevent.ProcessingEventTriggerType;
import de.mendelson.comm.as2.message.postprocessingevent.ProcessingEventType;
import de.mendelson.comm.as2.partner.gui.event.PartnerEventResource;
import de.mendelson.util.MendelsonMultiResolutionImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores event information of a partner
 *
 * @author S.Heller
 * @version $Revision: 22 $
 */
public class PartnerEventInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean useOnReceipt = false;
    private boolean useOnSendError = false;
    private boolean useOnSendSuccess = false;
    private ProcessingEventType processOnReceipt = ProcessingEventType.EXECUTE_SHELL;
    private ProcessingEventType processOnSendError = ProcessingEventType.EXECUTE_SHELL;
    private ProcessingEventType processOnSendSuccess = ProcessingEventType.EXECUTE_SHELL;

    private List<String> parameterOnReceipt = new ArrayList<String>();
    private List<String> parameterOnSendError = new ArrayList<String>();
    private List<String> parameterOnSendSuccess = new ArrayList<String>();

    /**
     * Creates an empty entry
     */
    public PartnerEventInformation() {
    }

    /**
     * @return the useOnSendError
     */
    public boolean isUseOnSendError() {
        return useOnSendError;
    }

    /**
     * @param useOnSendError the useOnSendError to set
     */
    public void setUseOnSendError(boolean useOnSendError) {
        this.useOnSendError = useOnSendError;
    }

    /**
     * @return the useOnSendSuccess
     */
    public boolean isUseOnSendSuccess() {
        return useOnSendSuccess;
    }

    /**
     * @param useOnSendSuccess the useOnSendSuccess to set
     */
    public void setUseOnSendSuccess(boolean useOnSendSuccess) {
        this.useOnSendSuccess = useOnSendSuccess;
    }

    /**
     * @param parameterOnSendSuccess the parameterOnSendSuccess to set
     */
    public void setParameterOnSendSuccess(List<String> parameterOnSendSuccess) {
        this.parameterOnSendSuccess.clear();
        this.parameterOnSendSuccess.addAll(parameterOnSendSuccess);
    }

    /**
     * Returns the related image that matches the requested process
     *
     * @param PROCESS_TYPE
     * @return
     */
    public static MendelsonMultiResolutionImage getImageForProcess(ProcessingEventType eventType) {
        if (eventType == ProcessingEventType.MOVE_TO_DIR) {
            return (PartnerEventResource.IMAGE_PROCESS_MOVE_TO_DIR);
        }
        if (eventType == ProcessingEventType.MOVE_TO_PARTNER) {
            return (PartnerEventResource.IMAGE_PROCESS_MOVE_TO_PARTNER);
        }
        return (PartnerEventResource.IMAGE_PROCESS_EXECUTE_SHELL);
    }

    /**
     * Serializes these partner event to XML
     *
     * @param level level in the XML hierarchy for the xml beautifying
     */
    public String toXML(int level) {
        String offset = "\t".repeat(level);
        StringBuilder builder = new StringBuilder();
        builder.append(offset).append("<events>\n")
                .append(offset).append("\t<useonreceipt>").append(String.valueOf(this.isUseOnReceipt())).append("</useonreceipt>\n")
                .append(offset).append("\t<typeonreceipt>").append(String.valueOf(this.processOnReceipt)).append("</typeonreceipt>\n");
        if (this.hasParameterOnReceipt()) {
            builder.append(offset).append("\t<onreceiptvalues>\n");
            for (String value : this.getParameterOnReceipt()) {
                builder.append(offset).append("\t\t<value>").append(this.toCDATA(value)).append("</value>\n");
            }
            builder.append(offset).append("\t</onreceiptvalues>\n");
        }
        builder.append(offset).append("\t<useonsenderror>").append(String.valueOf(this.isUseOnSendError())).append("</useonsenderror>\n")
                .append(offset).append("\t<typeonsenderror>").append(String.valueOf(this.processOnSendError)).append("</typeonsenderror>\n");
        if (this.hasParameterOnSenderror()) {
            builder.append(offset).append("\t<onsenderrorvalues>\n");
            for (String value : this.parameterOnSendError) {
                builder.append(offset).append("\t\t<value>").append(this.toCDATA(value)).append("</value>\n");
            }
            builder.append(offset).append("\t</onsenderrorvalues>\n");
        }
        builder.append(offset).append("\t<useonsendsuccess>").append(String.valueOf(this.isUseOnSendSuccess())).append("</useonsendsuccess>\n")
                .append(offset).append("\t<typeonsendsuccess>").append(String.valueOf(this.processOnSendSuccess)).append("</typeonsendsuccess>\n");
        if (this.hasParameterOnSendsuccess()) {
            builder.append(offset).append("\t<onsendsuccessvalues>\n");
            for (String value : this.parameterOnSendSuccess) {
                builder.append(offset).append("\t\t<value>").append(this.toCDATA(value)).append("</value>\n");
            }
            builder.append(offset).append("\t</onsendsuccessvalues>\n");
        }
        builder.append(offset).append("</events>\n");
        return (builder.toString());
    }

    /**
     * Adds a cdata indicator to xml data
     */
    private String toCDATA(String data) {
        return ("<![CDATA[" + data + "]]>");
    }

    public static void fromXML(Partner partner, Element element) {
        PartnerEventInformation eventInfo = partner.getPartnerEvents();
        NodeList propertiesNodeList = element.getChildNodes();
        for (int i = 0; i < propertiesNodeList.getLength(); i++) {
            if (propertiesNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element property = (Element) propertiesNodeList.item(i);
                String key = property.getTagName();
                String value = property.getTextContent();
                if (key.equals("useonreceipt")) {
                    eventInfo.setUseOnReceipt(Boolean.parseBoolean(value));
                }
                if (key.equals("useonsenderror")) {
                    eventInfo.setUseOnSendError(Boolean.parseBoolean(value));
                }
                if (key.equals("useonsendsuccess")) {
                    eventInfo.setUseOnSendSuccess(Boolean.parseBoolean(value));
                }
                if (key.equals("typeonreceipt")) {
                    eventInfo.setProcessOnReceipt(ProcessingEventType.of(Integer.parseInt(value)));
                }
                if (key.equals("typeonsenderror")) {
                    eventInfo.setProcessOnSendError(ProcessingEventType.of(Integer.parseInt(value)));
                }
                if (key.equals("typeonsendsuccess")) {
                    eventInfo.setProcessOnSendSuccess(ProcessingEventType.of(Integer.parseInt(value)));
                }
                if (key.equals("onreceiptvalues")) {
                    collectXMLValues(eventInfo.getParameterOnReceipt(), property);
                }
                if (key.equals("onsenderrorvalues")) {
                    collectXMLValues(eventInfo.getParameterOnSendError(), property);
                }
                if (key.equals("onsendsuccessvalues")) {
                    collectXMLValues(eventInfo.getParameterOnSendSuccess(), property);
                }
            }
        }
    }

    private static void collectXMLValues(List<String> list, Element element) {
        list.clear();
        NodeList propertiesNodeList = element.getChildNodes();
        for (int i = 0; i < propertiesNodeList.getLength(); i++) {
            if (propertiesNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element valueElement = (Element) propertiesNodeList.item(i);
                String valueTag = valueElement.getTagName();
                if (valueTag.equals("value")) {
                    String propertyValue = "";
                    if (valueElement.getTextContent() != null) {
                        propertyValue = valueElement.getTextContent();
                    }
                    list.add(propertyValue);
                }
            }
        }
    }

    /**
     * Overwrite the equal method of object
     *
     * @param anObject object to compare
     */
    @Override
    public boolean equals(Object anObject) {
        if (anObject == this) {
            return (true);
        }
        if (anObject != null && anObject instanceof PartnerEventInformation) {
            PartnerEventInformation entry = (PartnerEventInformation) anObject;
            return (entry.processOnReceipt == this.processOnReceipt
                    && entry.processOnSendError == this.processOnSendError
                    && entry.processOnSendSuccess == this.processOnSendSuccess
                    && this.parameterAreEqual(entry.getParameterOnReceipt(), this.getParameterOnReceipt())
                    && this.parameterAreEqual(entry.parameterOnSendError, this.parameterOnSendError)
                    && this.parameterAreEqual(entry.parameterOnSendSuccess, this.parameterOnSendSuccess));

        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.processOnReceipt);
        hash = 37 * hash + Objects.hashCode(this.processOnSendError);
        hash = 37 * hash + Objects.hashCode(this.processOnSendSuccess);
        hash = 37 * hash + Objects.hashCode(this.parameterOnSendError);
        hash = 37 * hash + Objects.hashCode(this.parameterOnSendSuccess);
        return hash;
    }

    private boolean parameterAreEqual(List<String> listA, List<String> listB) {
        StringBuilder builderA = new StringBuilder();
        for (String listAStr : listA) {
            builderA.append(listAStr);
        }
        StringBuilder builderB = new StringBuilder();
        for (String listBStr : listB) {
            builderB.append(listBStr);
        }
        return (builderA.toString().equals(builderB.toString()));
    }

    @JsonIgnore
    public void setUse(ProcessingEventTriggerType triggerType, boolean flag) {
        if (triggerType == ProcessingEventTriggerType.RECEIPT_SUCCESS) {
            this.setUseOnReceipt(flag);
        } else if (triggerType == ProcessingEventTriggerType.SEND_FAILURE) {
            this.setUseOnSendError(flag);
        } else if (triggerType == ProcessingEventTriggerType.SEND_SUCCESS) {
            this.setUseOnSendSuccess(flag);
        }
    }

    @JsonIgnore
    public void setProcess(ProcessingEventTriggerType triggerType, ProcessingEventType processType) {
        if (triggerType == ProcessingEventTriggerType.RECEIPT_SUCCESS) {
            this.setProcessOnReceipt(processType);
        } else if (triggerType == ProcessingEventTriggerType.SEND_FAILURE) {
            this.setProcessOnSendError(processType);
        } else if (triggerType == ProcessingEventTriggerType.SEND_SUCCESS) {
            this.setProcessOnSendSuccess(processType);
        }
    }

    @JsonIgnore
    public ProcessingEventType getProcess(ProcessingEventTriggerType eventTriggerType) {
        if (eventTriggerType == ProcessingEventTriggerType.RECEIPT_SUCCESS) {
            return (this.processOnReceipt);
        } else if (eventTriggerType == ProcessingEventTriggerType.SEND_FAILURE) {
            return (this.processOnSendError);
        } else if (eventTriggerType == ProcessingEventTriggerType.SEND_SUCCESS) {
            return (this.processOnSendSuccess);
        } else {
            throw new IllegalArgumentException("PartnerEventInformation.getProcess(): Undefined event type " + eventTriggerType);
        }
    }

    /**
     * @return the typeonreceipt
     */
    public ProcessingEventType getProcessOnReceipt() {
        return processOnReceipt;
    }

    /**
     * @param processonreceipt the typeonreceipt to set
     */
    public void setProcessOnReceipt(ProcessingEventType processonreceipt) {
        this.processOnReceipt = processonreceipt;
    }

    /**
     * @return the typeonsenderror
     */
    public ProcessingEventType getProcessOnSendError() {
        return processOnSendError;
    }

    /**
     * @param processonsenderror the typeonsenderror to set
     */
    public void setProcessOnSendError(ProcessingEventType processonsenderror) {
        this.processOnSendError = processonsenderror;
    }

    /**
     * @return the typeonsendsuccess
     */
    public ProcessingEventType getProcessOnSendSuccess() {
        return processOnSendSuccess;
    }

    /**
     * @param processonsendsuccess the typeonsendsuccess to set
     */
    public void setProcessOnSendSuccess(ProcessingEventType processonsendsuccess) {
        this.processOnSendSuccess = processonsendsuccess;
    }

    @JsonIgnore
    public List<String> getParameter(ProcessingEventTriggerType triggerType) {
        if (triggerType == ProcessingEventTriggerType.RECEIPT_SUCCESS) {
            return (this.getParameterOnReceipt());
        } else if (triggerType == ProcessingEventTriggerType.SEND_FAILURE) {
            return (this.getParameterOnSendError());
        } else if (triggerType == ProcessingEventTriggerType.SEND_SUCCESS) {
            return (this.getParameterOnSendSuccess());
        } else {
            throw new IllegalArgumentException("PartnerEventInformation.getParameter(): Undefined event type " + triggerType);
        }
    }

    /**
     * @return the parameteronreceipt
     */
    public List<String> getParameterOnReceipt() {
        List<String> tempList = new ArrayList<String>(this.parameterOnReceipt);
        return tempList;
    }

    @JsonIgnore
    public void setParameter(ProcessingEventTriggerType triggerType, List<String> parameter) {
        if (triggerType == ProcessingEventTriggerType.RECEIPT_SUCCESS) {
            this.setParameterOnReceipt(parameter);
        } else if (triggerType == ProcessingEventTriggerType.SEND_FAILURE) {
            this.setParameterOnSendError(parameter);
        } else if (triggerType == ProcessingEventTriggerType.SEND_SUCCESS) {
            this.setParameterOnSendSuccess(parameter);
        }
    }

    @JsonIgnore
    public void setParameter(ProcessingEventTriggerType triggerType, String parameter) {
        this.setParameter(triggerType, List.<String>of(parameter));
    }

    /**
     * @param parameteronreceipt the parameteronreceipt to set
     */
    public void setParameterOnReceipt(List<String> parameteronreceipt) {
        this.parameterOnReceipt.clear();
        this.parameterOnReceipt.addAll(parameteronreceipt);
    }

    /**
     * @return the parameteronsenderror
     */
    public List<String> getParameterOnSendError() {
        List<String> tempList = new ArrayList<String>(this.parameterOnSendError);
        return tempList;
    }

    /**
     * @param parameteronsenderror the parameteronsenderror to set
     */
    public void setParameterOnSendError(List<String> parameteronsenderror) {
        this.parameterOnSendError.clear();
        this.parameterOnSendError.addAll(parameteronsenderror);
    }

    /**
     * @return the parameteronsendsuccess
     */
    public List<String> getParameterOnSendSuccess() {
        List<String> tempList = new ArrayList<String>(this.parameterOnSendSuccess);
        return tempList;
    }

    public boolean hasParameterOnSendsuccess() {
        if (this.parameterOnSendSuccess.isEmpty()) {
            return (false);
        }
        for (String parameter : this.parameterOnSendSuccess) {
            if (parameter != null && !parameter.trim().isEmpty()) {
                return (true);
            }
        }
        return (false);
    }

    public boolean hasParameterOnSenderror() {
        if (this.parameterOnSendError.isEmpty()) {
            return (false);
        }
        for (String parameter : this.parameterOnSendError) {
            if (parameter != null && !parameter.trim().isEmpty()) {
                return (true);
            }
        }
        return (false);
    }

    public boolean hasParameterOnReceipt() {
        if (this.getParameterOnReceipt().isEmpty()) {
            return (false);
        }
        for (String parameter : this.getParameterOnReceipt()) {
            if (parameter != null && !parameter.trim().isEmpty()) {
                return (true);
            }
        }
        return (false);
    }

    public boolean hasParameter(ProcessingEventTriggerType triggerType) {
        if (triggerType == ProcessingEventTriggerType.RECEIPT_SUCCESS) {
            return (this.hasParameterOnReceipt());
        } else if (triggerType == ProcessingEventTriggerType.SEND_FAILURE) {
            return (this.hasParameterOnSenderror());
        } else if (triggerType == ProcessingEventTriggerType.SEND_SUCCESS) {
            return (this.hasParameterOnSendsuccess());
        } else {
            throw new IllegalArgumentException("PartnerEventInformation.hasParameter(): Undefined event type " + triggerType);
        }
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }

    /**
     * @return the useOnReceipt
     */
    public boolean isUseOnReceipt() {
        return useOnReceipt;
    }

    /**
     * @param useOnReceipt the useOnReceipt to set
     */
    public void setUseOnReceipt(boolean useOnReceipt) {
        this.useOnReceipt = useOnReceipt;
    }

}
