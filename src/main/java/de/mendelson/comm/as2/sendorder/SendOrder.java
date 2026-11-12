//$Header: /as2/de/mendelson/comm/as2/sendorder/SendOrder.java 12    31/03/26 9:30 Heller $
package de.mendelson.comm.as2.sendorder;

import de.mendelson.comm.as2.message.AS2Message;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.util.clientserver.SerializationDummy;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Send order that will be enqueued into the as2 server message queue
 *
 * @author S.Heller
 * @version $Revision: 12 $
 */
public class SendOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Partner receiver;
    private AS2Message message;
    private Partner sender;
    private AtomicInteger retryCount = new AtomicInteger(0);
    private int dbId = -1;
    private String userdefinedId = null;
    private Map<String, String> userdefinedHeaderMap = new LinkedHashMap<String, String>();

    public Partner getReceiver() {
        return receiver;
    }

    public SendOrder setReceiver(Partner receiver) {
        this.receiver = receiver;
        return (this);
    }

    public AS2Message getMessage() {
        return message;
    }

    public SendOrder setMessage(AS2Message message) {
        this.message = message;
        return (this);
    }

    public Partner getSender() {
        return sender;
    }

    public SendOrder setSender(Partner sender) {
        this.sender = sender;
        return (this);
    }

    public int incRetryCount() {
        return (this.getRetryCount().incrementAndGet());
    }

    /**
     * @return the dbId
     */
    public int getDbId() {
        return dbId;
    }

    /**
     * @param dbId the dbId to set
     */
    public SendOrder setDbId(int dbId) {
        this.dbId = dbId;
        return (this);
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
    public SendOrder setUserdefinedId(String userdefinedId) {
        this.userdefinedId = userdefinedId;
        return (this);
    }

    /**
     * This is a dummy method for the deserialization process. Do not use in
     * logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public AtomicInteger getRetryCount() {
        return retryCount;
    }

    /**
     * This is a dummy method for the deserialization process. Do not use in
     * logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setRetryCount(AtomicInteger retryCount) {
        this.retryCount.set(retryCount.get());
    }

    /**
     * @return the userdefinedHeaderMap
     */
    public Map<String, String> getUserdefinedHeaderMap() {
        return userdefinedHeaderMap;
    }

    /**
     * @param userdefinedHeaderMap the userdefinedHeaderMap to set, might be null
     */
    public SendOrder setUserdefinedHeaderMap(Map<String, String> userdefinedHeaderMap) {
        this.userdefinedHeaderMap.clear();
        if (userdefinedHeaderMap != null) {
            this.userdefinedHeaderMap.putAll(userdefinedHeaderMap);
        }
        return (this);
    }

}
