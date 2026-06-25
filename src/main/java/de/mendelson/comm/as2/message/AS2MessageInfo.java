//$Header: /as2/de/mendelson/comm/as2/message/AS2MessageInfo.java 79    26/03/26 9:37 Heller $
package de.mendelson.comm.as2.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.mendelson.comm.as2.AS2ServerVersion;
import de.mendelson.util.clientserver.messages.LoggableParameterClientServer;
import de.mendelson.util.security.BCCryptoHelper;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores all information about a as2 message
 *
 * @author S.Heller
 * @version $Revision: 79 $
 */
public final class AS2MessageInfo implements AS2Info, LoggableParameterClientServer {

    /**
     * AS2Info extends Serializable
     */
    private static final long serialVersionUID = 1L;
    private String senderId;
    private String receiverId;
    /**
     * Date of this message
     */
    private Date initDate = new Date();
    private Date sendDate = null;
    private String messageId;
    private String senderEMail;
    /**
     * Stores the sender host for a message
     */
    private String senderHost = null;
    /**
     * Raw data file
     */
    private String rawFilename = null;
    /**
     * Raw data header file
     */
    private String headerFilename = null;
    /**
     * Decrypted file
     */
    private String rawFilenameDecrypted = null;
    /**
     * indicates if the message is signed. Before it has been analyzed it is not
     * clear if the message/MDN contains a signature or not
     */
    private int signType = AS2Message.SIGNATURE_UNKNOWN;
    /**
     * indicates if the message is encrypted
     */
    private int encryptionType = AS2Message.ENCRYPTION_UNKNOWN;
    /**
     * Stores the compression type of this entry
     */
    private MessageCompressionType compressionType = MessageCompressionType.NONE;
    /**
     * This is the product name submitted in the user agent header
     */
    private String userAgent = AS2ServerVersion.getUserAgent();
    private MessageDirectionType direction = MessageDirectionType.UNKNOWN;
    private String receivedContentMIC;
    /**
     * Possible are AS2Message.STATE_STATE_FINISHED
     * AS2Message.STATE_STATE_PENDING AS2Message.STATE_STATE_STOPPED
     */
    private MessageStateType state = MessageStateType.PENDING;
    /**
     * stores if the MDN to this message should be sync or async
     */
    private boolean requestsSyncMDN = true;
    private String asyncMDNURL = null;
    private String subject = null;
    /**
     * There are several message types that are transported by the AS2 protocol.
     * These are the AS2 message (EDI data) and the Certificate Exchange Message
     * (CEM, contains certificates).
     */
    private MessageType messageType = MessageType.AS2;
    private int resendCounter = 0;
    /**
     * Allows to track this transmission later using the RPC XML interface
     */
    private String userdefinedId = null;
    /**
     * Stores if the transmission was transmitted using a secure connection
     */
    private boolean usesTLS = false;

    /**
     * These are the disposition notification options
     */
    private DispositionNotificationOptions dispositionNotificationOptions;

    public AS2MessageInfo() {
        this.dispositionNotificationOptions = new DispositionNotificationOptions(
                new String[]{BCCryptoHelper.ALGORITHM_SHA1});
    }

    /**
     * Initializes the message info from the passed MDN/AS2 message request
     * headers
     */
    @JsonIgnore
    public void initializeByRequestHeader(Properties requestHeader) {
        if (requestHeader.containsKey("message-id")) {
            this.setMessageId(requestHeader.getProperty("message-id"));
        }
        //MDN: server is in "server"
        //AS2 msg: server is in "user-agent"
        if (requestHeader.containsKey("server")) {
            this.setUserAgent(requestHeader.getProperty("server"));
        } else {
            this.setUserAgent(requestHeader.getProperty("user-agent"));
        }
        if (requestHeader.containsKey("as2-from")) {
            this.setSenderId(AS2MessageParser.unescapeFromToHeader(requestHeader.getProperty("as2-from")));
        }
        if (requestHeader.containsKey("as2-to")) {
            this.setReceiverId(AS2MessageParser.unescapeFromToHeader(requestHeader.getProperty("as2-to")));
        }
        if (requestHeader.containsKey("from")) {
            this.setSenderEMail(requestHeader.getProperty("from"));
        }
        if (requestHeader.containsKey("subject")) {
            this.setSubject(requestHeader.getProperty("subject"));
        }
    }

    /**
     * Serializes this partner to XML
     *
     * @param level level in the XML hierarchy for the xml beautifying
     */
    public String toXML(int level) {
        String offset = "\t".repeat(level);
        StringBuilder builder = new StringBuilder();
        builder.append(offset).append("<messageinfo>\n")
                .append(offset).append("\t<id>").append(this.toCDATA(this.messageId)).append("</id>\n")
                .append(offset).append("\t<userdefinedid>");
        if (this.userdefinedId != null) {
            builder.append(this.toCDATA(this.userdefinedId));
        }
        builder.append("</userdefinedid>\n")
                .append(offset).append("\t<senderid>").append(this.getSenderId()).append("</senderid>\n")
                .append(offset).append("\t<receiverid>").append(this.receiverId).append("</receiverid>\n")
                .append(offset).append("\t<signtype>").append(this.signType).append("</signtype>\n")
                .append(offset).append("\t<encryptiontype>").append(this.encryptionType).append("</encryptiontype>\n")
                .append(offset).append("\t<compressiontype>").append(this.compressionType).append("</compressiontype>\n")
                .append(offset).append("\t<state>").append(this.state).append("</state>\n")
                .append(offset).append("</messageinfo>\n");
        return (builder.toString());
    }

    /**
     * Adds this entry to the passed parent JSON node
     */
    public void addToJSON(ArrayNode parent, Map<String, String> as2Id2NameMap) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(ZoneOffset.UTC);
        ObjectNode node = parent.addObject();
        node.put("messageid", this.messageId)
                .put("userdefinedid", this.userdefinedId == null ? "--" : this.userdefinedId)
                .put("initdate", dateFormat.format(this.initDate.toInstant()));
        if (this.sendDate != null) {
            node.put("senddate", dateFormat.format(this.sendDate.toInstant()));
        }
        node.put("senderid", this.getSenderId())
                .put("sendername", as2Id2NameMap.getOrDefault(this.getSenderId(), "_UNKNOWN"))
                .put("receiverid", this.receiverId)
                .put("receivername", as2Id2NameMap.getOrDefault(this.receiverId, "_UNKNOWN"))
                .put("messagetype", this.getMessageType().toInt())
                .put("signtype", this.signType)
                .put("encryptiontype", this.encryptionType)
                .put("compressiontype", this.compressionType.toInt())
                .put("state", this.state.toInt())
                .put("direction", this.direction.toInt())
                .put("mdnmode", this.isRequestsSyncMDN() ? "SYNC" : "ASYNC")
                .put("useragent", this.userAgent);
        if (this.subject != null) {
            node.put("subject", this.subject);
        }
        node.put("tls", this.usesTLS);
    }

    /**
     * Adds a CDATA indicator to XML data
     */
    private String toCDATA(String data) {
        return ("<![CDATA[" + data + "]]>");
    }

    /**
     * Returns the senderId, unescaped
     */
    @Override
    public String getSenderId() {
        return (this.senderId);
    }

    /**
     * sets the sender id, unescaped
     */
    @Override
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * sets the receiver id, unescaped
     */
    @Override
    public String getReceiverId() {
        return receiverId;
    }

    /**
     * sets the sender id, unescaped
     */
    @Override
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public Date getInitDate() {
        return initDate;
    }

    @Override
    public void setInitDate(Date messageDate) {
        this.initDate = messageDate;
    }

    @Override
    public String getMessageId() {
        return messageId;
    }

    /**
     * Removes braces if they exist
     */
    @Override
    public void setMessageId(String messageId) {
        if (messageId != null && messageId.startsWith("<") && messageId.endsWith(">")) {
            messageId = messageId.substring(1, messageId.length() - 1);
        }
        this.messageId = messageId;
    }

    @Override
    public String getRawFilename() {
        return rawFilename;
    }

    @Override
    public void setRawFilename(String rawFilename) {
        this.rawFilename = rawFilename;
    }

    public String getSenderEMail() {
        return senderEMail;
    }

    public void setSenderEMail(String senderEMail) {
        this.senderEMail = senderEMail;
    }

    /**
     * Returns the direction of the transaction: inbound or outbound
     *
     * @return AS2MessageInfo.DIRECTION_IN or AS2MessageInfo.DIRECTION_OUT
     */
    @Override
    public MessageDirectionType getDirection() {
        return direction;
    }

    @Override
    public void setDirection(MessageDirectionType direction) {
        this.direction = direction;
    }

    @Override
    public MessageStateType getState() {
        return state;
    }

    @Override
    public void setState(MessageStateType state) {
        this.state = state;
    }

    @Override
    public int getSignType() {
        return signType;
    }

    @Override
    public void setSignType(int signType) {
        this.signType = signType;
        if (signType == AS2Message.SIGNATURE_MD5) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_MD5);
        } else if (signType == AS2Message.SIGNATURE_SHA1) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA1);
        } else if (signType == AS2Message.SIGNATURE_SHA1_RSASSA_PSS) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA_1_RSASSA_PSS);
        } else if (signType == AS2Message.SIGNATURE_SHA224) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA224);
        } else if (signType == AS2Message.SIGNATURE_SHA224_RSASSA_PSS) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA_224_RSASSA_PSS);
        } else if (signType == AS2Message.SIGNATURE_SHA256) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA256);
        } else if (signType == AS2Message.SIGNATURE_SHA256_RSASSA_PSS) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA_256_RSASSA_PSS);
        } else if (signType == AS2Message.SIGNATURE_SHA384) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA384);
        } else if (signType == AS2Message.SIGNATURE_SHA384_RSASSA_PSS) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA_384_RSASSA_PSS);
        } else if (signType == AS2Message.SIGNATURE_SHA512) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA512);
        } else if (signType == AS2Message.SIGNATURE_SHA512_RSASSA_PSS) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA_512_RSASSA_PSS);
        } else if (signType == AS2Message.SIGNATURE_SHA3_224) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA3_224);
        } else if (signType == AS2Message.SIGNATURE_SHA3_256) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA3_256);
        } else if (signType == AS2Message.SIGNATURE_SHA3_384) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA3_384);
        } else if (signType == AS2Message.SIGNATURE_SHA3_512) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA3_512);
        } else if (signType == AS2Message.SIGNATURE_SHA3_224_RSASSA_PSS) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA3_224_RSASSA_PSS);
        } else if (signType == AS2Message.SIGNATURE_SHA3_256_RSASSA_PSS) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA3_256_RSASSA_PSS);
        } else if (signType == AS2Message.SIGNATURE_SHA3_384_RSASSA_PSS) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA3_384_RSASSA_PSS);
        } else if (signType == AS2Message.SIGNATURE_SHA3_512_RSASSA_PSS) {
            this.dispositionNotificationOptions.setSignaturHashFunction(BCCryptoHelper.ALGORITHM_SHA3_512_RSASSA_PSS);
        }
    }

    @Override
    public int getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(int encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getReceivedContentMIC() {
        return receivedContentMIC;
    }

    public void setReceivedContentMIC(String receivedContentMIC) {
        this.receivedContentMIC = receivedContentMIC;
    }

    public void setRequestsSyncMDN(boolean requestsSyncMDN) {
        this.requestsSyncMDN = requestsSyncMDN;
    }

    public String getAsyncMDNURL() {
        return asyncMDNURL;
    }

    public void setAsyncMDNURL(String asyncMDNURL) {
        this.asyncMDNURL = asyncMDNURL;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getHeaderFilename() {
        return headerFilename;
    }

    @Override
    public void setHeaderFilename(String headerFilename) {
        this.headerFilename = headerFilename;
    }

    public String getRawFilenameDecrypted() {
        return rawFilenameDecrypted;
    }

    public void setRawFilenameDecrypted(String rawFilenameDecrypted) {
        this.rawFilenameDecrypted = rawFilenameDecrypted;
    }

    @Override
    public String getSenderHost() {
        return senderHost;
    }

    @Override
    public void setSenderHost(String senderHost) {
        this.senderHost = senderHost;
    }

    /**
     * Returns the content of this object for debug purpose
     */
    @Override
    @JsonIgnore
    public String getDebugDisplay() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("asyncMDNURL=\t\t").append(this.asyncMDNURL);
        buffer.append("\n");
        buffer.append("direction=\t\t").append(this.direction.name());
        buffer.append("\n");
        buffer.append("encryptionType=\t\t").append(this.encryptionType);
        buffer.append("\n");
        buffer.append("headerFilename=\t\t").append(this.headerFilename);
        buffer.append("\n");
        buffer.append("messageDate=\t\t").append(this.initDate);
        buffer.append("\n");
        buffer.append("messageId=\t\t").append(this.messageId);
        buffer.append("\n");
        buffer.append("rawFilename=\t\t").append(this.rawFilename);
        buffer.append("\n");
        buffer.append("rawFilenameDecrypted=\t\t").append(this.rawFilenameDecrypted);
        buffer.append("\n");
        buffer.append("receivedContentMIC=\t\t").append(this.receivedContentMIC);
        buffer.append("\n");
        buffer.append("receiverId=\t\t").append(this.receiverId);
        buffer.append("\n");
        buffer.append("requestsSyncMDN=\t\t").append(this.isRequestsSyncMDN());
        buffer.append("\n");
        buffer.append("senderEMail=\t\t").append(this.senderEMail);
        buffer.append("\n");
        buffer.append("senderHost=\t\t").append(this.senderHost);
        buffer.append("\n");
        buffer.append("senderId=\t\t").append(this.getSenderId());
        buffer.append("\n");
        buffer.append("signType=\t\t").append(this.signType);
        buffer.append("\n");
        buffer.append("subject=\t\t").append(this.subject);
        buffer.append("\n");
        buffer.append("state=\t\t").append(this.state.name());
        return (buffer.toString());
    }

    @Override
    public String getUserAgent() {
        return this.userAgent;
    }

    @Override
    public void setUserAgent(String useragent) {
        this.userAgent = useragent;
    }

    public DispositionNotificationOptions getDispositionNotificationOptions() {
        return dispositionNotificationOptions;
    }

    public void setDispositionNotificationOptions(DispositionNotificationOptions dispositionNotificationOptions) {
        this.dispositionNotificationOptions = dispositionNotificationOptions;
    }

    /**
     * @return the compressionType
     */
    public MessageCompressionType getCompressionType() {
        return compressionType;
    }

    /**
     * @param compressionType the compressionType to set
     */
    public void setCompressionType(MessageCompressionType compressionType) {
        this.compressionType = compressionType;
    }

    /**
     * There are several message types that are tansported by the AS2 protocol.
     * These are the AS2 message (EDI data) and the Certificate Exchange Message
     * (CEM, contains certificates). The constants are
     * AS2Message.MESSAGETYPE_CEM and AS2Message.MESSAGETYPE_AS2
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * There are several message types that are tansported by the AS2 protocol.
     * These are the AS2 message (EDI data) and the Certificate Exchange Message
     * (CEM, contains certificates).
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    @JsonIgnore
    public boolean isMDN() {
        return (false);
    }

    /**
     * Overwrite the equal method of object
     *
     * @param anObject object ot compare
     */
    @Override
    public boolean equals(Object anObject) {
        if (anObject == this) {
            return (true);
        }
        if (anObject != null && anObject instanceof AS2MessageInfo) {
            AS2MessageInfo info = (AS2MessageInfo) anObject;
            return (this.messageId != null && this.messageId.equals(info.messageId));
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.messageId != null ? this.messageId.hashCode() : 0);
        return hash;
    }

    /**
     * @return the resendCounter
     */
    public int getResendCounter() {
        return resendCounter;
    }

    /**
     * @param resendCounter the resendCounter to set
     */
    public void setResendCounter(int resendCounter) {
        this.resendCounter = resendCounter;
    }

    /**
     * @return the userdefinedId
     */
    public String getUserdefinedId() {
        return userdefinedId;
    }

    /**
     * @param userdefinedId the userdefinedId to set
     */
    public void setUserdefinedId(String userdefinedId) {
        this.userdefinedId = userdefinedId;
    }

    /**
     * @param usesTLS the usesTLS to set
     */
    @Override
    public void setUsesTLS(boolean usesTLS) {
        this.usesTLS = usesTLS;
    }

    /**
     * @return the sendDate
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * @param sendDate the sendDate to set
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * stores if the MDN to this message should be sync or async
     *
     * @return the requestsSyncMDN
     */
    public boolean isRequestsSyncMDN() {
        return requestsSyncMDN;
    }

    /**
     * Stores if the transmission was transmitted using a secure connection
     *
     * @return the usesTLS
     */
    @Override
    public boolean isUsesTLS() {
        return usesTLS;
    }

    @JsonIgnore
    @Override
    public String getLoggingId() {
        return (this.messageId == null ? "" : this.messageId);
    }

    @JsonIgnore
    @Override
    public String getLoggingPrefix() {
        return ("MSG");
    }

}
