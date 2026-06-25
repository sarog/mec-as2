//$Header: /as4/de/mendelson/util/clientserver/messages/ClientServerMessage.java 15    19/02/26 9:19 Heller $
package de.mendelson.util.clientserver.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.clientserver.ClientServer;
import de.mendelson.util.clientserver.SerializationDummy;
import java.io.Serializable;
import java.lang.management.ManagementFactory;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Superclass of all messages for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 15 $
 */
public class ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String PROCESS_ID = ManagementFactory.getRuntimeMXBean().getName();
    private long referenceId = 0;
    private boolean syncRequest = false;    
    private String pid;

    public ClientServerMessage() {
        this.referenceId = ClientServer.getNextClientServerMessageReferenceId();
        this.pid = PROCESS_ID;
    }

    public long getReferenceId() {
        return (this.referenceId);
    }

    /**
     * Internal method, do NOT use it
     *
     * @return the _syncRequest
     */
    public boolean isSyncRequest() {
        return this.syncRequest;
    }

    /**
     * Internal method, do NOT use it
     *
     * @param syncRequest the syncRequest to set
     */
    public void setSyncRequest(boolean syncRequest) {
        this.syncRequest = syncRequest;
    }

    /**
     * @param referenceId the referenceId to set
     */
    @JsonIgnore
    protected void _setReferenceId(long referenceId) {
        this.setReferenceId(referenceId);
    }

    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * Dummy method for Jackson deserialization only. Do not use in logic
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * Dummy method for Jackson deserialization only. Do not use in logic
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setReferenceId(long referenceId) {
        this.referenceId = referenceId;
    }

}
