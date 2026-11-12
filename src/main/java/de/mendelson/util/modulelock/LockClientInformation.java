//$Header: /as4/de/mendelson/util/modulelock/LockClientInformation.java 7     11/06/25 13:17 Heller $
package de.mendelson.util.modulelock;

import de.mendelson.util.clientserver.SerializationDummy;
import java.io.Serializable;
import java.util.Objects;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores information about the client that locks a module or requests a module
 * lock
 *
 * @author S.Heller
 * @version $Revision: 7 $
 */
public class LockClientInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username = null;
    private String clientIP = null;
    private String uniqueid = null;
    private String pid = null;

    public LockClientInformation(String username, String clientIP, String uniqueid, String pid) {
        this.username = username;
        this.clientIP = clientIP;
        this.uniqueid = uniqueid;
        this.pid = pid;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public LockClientInformation() {
        super();
    }
    
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

     /**
     * This is a dummy method for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the clientIP
     */
    public String getClientIP() {
        return clientIP;
    }

    /**
     * @return the unique id of the client
     */
    public String getUniqueid() {
        return uniqueid;
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
        if (anObject != null && anObject instanceof LockClientInformation) {
            LockClientInformation entry = (LockClientInformation) anObject;
            return (this.getUniqueid().equals(entry.getUniqueid()));
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.getUniqueid());
        return hash;
    }
    
    /**Returns the client side process id
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

     /**
     * This is a dummy method for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setPid(String pid) {
        this.pid = pid;
    }

     /**
     * This is a dummy method for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    protected void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    /**
     * This is a dummy method for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

}
