//$Header: /as2/de/mendelson/comm/as2/message/AS2MDNInfo.java 12    2/11/23 15:52 Heller $
package de.mendelson.comm.as2.message;

import de.mendelson.comm.as2.AS2ServerVersion;
import java.util.Date;
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
 * @author S.Heller
 * @version $Revision: 12 $
 */
public class AS2MDNInfo implements AS2Info {

    private static final long serialVersionUID = 1L;
    private String messageId;
    private String senderId;
    private String receiverId;
    /**Date of this message*/
    private Date initDate = new Date();
    private String relatedMessageId;
    /**Stores the sender host for a message
     */
    private String senderHost = null;
    /**Raw data file*/
    private String rawFilename = null;
    /**Raw data header file*/
    private String headerFilename = null;
    /**Decrypted file*/
    /**indicates if the message is signed. Before it has been analyzed it is not clear
     *if the message/MDN contains a signature or not
     */
    private int signType = AS2Message.SIGNATURE_UNKNOWN;
    /**This is the product name submitted in the user agent header*/
    private String useragent;
    private int direction = AS2MessageInfo.DIRECTION_UNKNOWN;
    /**Possible are
     * AS2Message.STATE_STATE_FINISHED
     *AS2Message.STATE_STATE_PENDING
     *AS2Message.STATE_STATE_STOPPED
     */
    private int state = AS2Message.STATE_PENDING;
    /**Contains the mdn text of a remote partner, only available for inbound messages*/
    private String remoteMDNText = null;
    private String receivedContentMIC;
    private String dispositionState = null;
    private boolean usesTLS = false;

    public AS2MDNInfo() {
        this.useragent = AS2ServerVersion.getUserAgent();
    }

    /**Initializes the message info from the passed MDN/AS2 message request headers*/
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
    }

    /**Returns the senderId, unescaped*/
    @Override
    public String getSenderId() {
        return senderId;
    }

    /**sets the sender id, unescaped*/
    @Override
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**sets the receiver id, unescaped*/
    @Override
    public String getReceiverId() {
        return receiverId;
    }

    /**sets the sender id, unescaped*/
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
    public String getRawFilename() {
        return rawFilename;
    }

    @Override
    public void setRawFilename(String rawFilename) {
        this.rawFilename = rawFilename;
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public int getSignType() {
        return signType;
    }

    @Override
    public void setSignType(int signType) {
        this.signType = signType;
    }


    public String getRelatedMessageId() {
        return relatedMessageId;
    }

    public void setRelatedMessageId(String relatedMessageId) {
        if (relatedMessageId != null && relatedMessageId.startsWith("<") && relatedMessageId.endsWith(">")) {
            relatedMessageId = relatedMessageId.substring(1, relatedMessageId.length() - 1);
        }
        this.relatedMessageId = relatedMessageId;
    }

    @Override
    public String getHeaderFilename() {
        return headerFilename;
    }

    @Override
    public void setHeaderFilename(String headerFilename) {
        this.headerFilename = headerFilename;
    }

    @Override
    public String getSenderHost() {
        return senderHost;
    }

    @Override
    public void setSenderHost(String senderHost) {
        this.senderHost = senderHost;
    }

    /**Returns the content of this object for debug purpose
     */
    @Override
    public String getDebugDisplay() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("direction=\t\t").append(this.direction);
        buffer.append("\n");
        buffer.append("headerFilename=\t\t").append(this.headerFilename==null?"NULL":this.headerFilename);
        buffer.append("\n");
        buffer.append("messageDate=\t\t").append(this.initDate==null?"NULL":this.initDate);
        buffer.append("\n");
        buffer.append("messageId=\t\t").append(this.relatedMessageId==null?"NULL":this.relatedMessageId);
        buffer.append("\n");
        buffer.append("rawFilename=\t\t").append(this.rawFilename==null?"NULL":this.rawFilename);
        buffer.append("receiverId=\t\t").append(this.receiverId==null?"NULL":this.receiverId);
        buffer.append("\n");
        buffer.append("relatedMessageId=\t\t").append(this.relatedMessageId==null?"NULL":this.relatedMessageId);
        buffer.append("senderHost=\t\t").append(this.senderHost==null?"NULL":this.senderHost);
        buffer.append("\n");
        buffer.append("senderId=\t\t").append(this.senderId==null?"NULL":this.senderId);
        buffer.append("\n");
        buffer.append("signType=\t\t").append(this.signType);
        buffer.append("\n");
        buffer.append("state=\t\t").append(this.state);
        return (buffer.toString());
    }

    @Override
    public String getUserAgent() {
        return useragent;
    }

    @Override
    public void setUserAgent(String useragent) {
        this.useragent = useragent;
    }

    /**
     * @return the remoteMDNText
     */
    public String getRemoteMDNText() {
        if( this.remoteMDNText == null ){
            return( "" );
        }
        return remoteMDNText;
    }

    /**
     * @param remoteMDNText the remoteMDNText to set
     */
    public void setRemoteMDNText(String remoteMDNText) {
        this.remoteMDNText = remoteMDNText;
    }

    public String getReceivedContentMIC() {
        return receivedContentMIC;
    }

    public void setReceivedContentMIC(String receivedContentMIC) {
        this.receivedContentMIC = receivedContentMIC;
    }

   
    /**
     * @return the messageId
     */
    @Override
    public String getMessageId() {
        return messageId;
    }

     /**Removes braces if they exist
     */
    @Override
    public void setMessageId(String messageId) {
        if (messageId != null && messageId.startsWith("<") && messageId.endsWith(">")) {
            messageId = messageId.substring(1, messageId.length() - 1);
        }
        this.messageId = messageId;
    }

    @Override
    public String getSubject(){
        return( "Message Delivery Notification" );
    }

    @Override
    public int getEncryptionType() {
        return( AS2Message.ENCRYPTION_NONE);
    }

    @Override
    public boolean isMDN() {
        return( true );
    }

    /**
     * @return the dispositionState, e.g. "processed/error: unknown-trading-partner"
     */
    public String getDispositionState() {
        return dispositionState;
    }

    /**
     * @param dispositionState the dispositionState to set, e.g. "processed/error: unknown-trading-partner"
     */
    public void setDispositionState(String dispositionState) {
        this.dispositionState = dispositionState;
    }

    @Override
    public boolean usesTLS() {
        return( this.usesTLS);
    }

    @Override
    public void setUsesTLS(boolean usesTLS) {
        this.usesTLS = usesTLS;
    }

}
