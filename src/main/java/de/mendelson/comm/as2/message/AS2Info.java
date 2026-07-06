//$Header: /as2/de/mendelson/comm/as2/message/AS2Info.java 7     23/03/26 13:41 Heller $
package de.mendelson.comm.as2.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Date;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Interface for all information about a AS2 messages
 *
 * @author S.Heller
 * @version $Revision: 7 $
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AS2MDNInfo.class, name = "mdn"),
    @JsonSubTypes.Type(value = AS2MessageInfo.class, name = "msg")
})
public sealed interface AS2Info extends Serializable permits AS2MDNInfo,AS2MessageInfo{

    public boolean isMDN();

    public int getEncryptionType();

    public Date getInitDate();

    public void setInitDate(Date initDate);

    public String getSubject();

    /**
     * Returns the MessageId
     */
    public String getMessageId();

    /**
     * sets the messge id, unescaped
     */
    public void setMessageId(String messageId);

    /**
     * Returns the senderId, unescaped
     */
    public String getSenderId();

    /**
     * sets the sender id, unescaped
     */
    public void setSenderId(String senderId);

    /**
     * sets the receiver id, unescaped
     */
    public String getReceiverId();

    /**
     * sets the sender id, unescaped
     */
    public void setReceiverId(String receiverId);

    public String getRawFilename();

    public void setRawFilename(String rawFilename);

    public MessageDirectionType getDirection();

    public void setDirection(MessageDirectionType direction);

    public MessageStateType getState();

    public void setState(MessageStateType state);

    public int getSignType();

    public void setSignType(int signType);

    public String getHeaderFilename();

    public void setHeaderFilename(String headerFilename);

    public String getSenderHost();

    public void setSenderHost(String senderHost);

    /**
     * Returns the content of this object for debug purpose
     */
    public String getDebugDisplay();

    public String getUserAgent();

    public void setUserAgent(String useragent);

    public boolean isUsesTLS();

    public void setUsesTLS(boolean usesTLS);

}
