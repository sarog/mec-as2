//$Header: /as2/de/mendelson/util/clientserver/connectiontest/clientserver/ConnectionTestRequest.java 12    9/04/26 8:08 Heller $
package de.mendelson.util.clientserver.connectiontest.clientserver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.connectiontest.ConnectionTest;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Msg for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 12 $
 */
public class ConnectionTestRequest extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String[] tlsProtocols = ConnectionTest.DEFAULT_TLS_PROTOCOL_LIST;
    private String host = null;
    private int port;
    private long timeout = TimeUnit.SECONDS.toMillis(2);
    /**
     * Some additional information for the log etc
     */
    private String partnerName = null;
    private  ConnectionTest.PartnerRole partnerRole = ConnectionTest.PartnerRole.REMOTE_PARTNER;

    /**
     * @param host Host IP to test to
     * @param port Port to test to
     * @param protocols List of TLS protocols that are allowed
     * @param partnerName Name of the partner to display in the log and result
     * @param partnerRole One of the constants defined in ConnectionTest
     */
    public ConnectionTestRequest(String host, int port, String[] protocols, String partnerName, 
            ConnectionTest.PartnerRole partnerRole) {
        this.tlsProtocols = protocols;
        if (this.tlsProtocols == null) {
            this.tlsProtocols = new String[0];
        }
        this.host = host;
        this.port = port;
        this.partnerName = partnerName;
        this.partnerRole = partnerRole;
    }

    /**
     * Performs a TLS connection test with the default TLS protocols if tls is
     * set. To specify the used protocols use the other constructor
     *
     * @param host
     * @param port
     * @param tls
     * @param partnerName Name of the partner to display in the log and result
     * @param partnerRole One of the constants defined in ConnectionTest
     */
    public ConnectionTestRequest(String host, int port, boolean tls, String partnerName, ConnectionTest.PartnerRole partnerRole) {
        if (tls) {
            this.tlsProtocols = ConnectionTest.DEFAULT_TLS_PROTOCOL_LIST;
        } else {
            this.tlsProtocols = new String[0];
        }
        this.host = host;
        this.port = port;
        this.partnerName = partnerName;
        this.partnerRole = partnerRole;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public ConnectionTestRequest() {
        super();
    }

    @Override
    public String toString() {
        return ("Connection test request");
    }

    /**
     * @return the timeout
     */
    public long getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * @return an empty array if this is a non SSL request
     */
    public String[] getTLSProtocols() {
        return (this.tlsProtocols);
    }

    @JsonIgnore
    public boolean getIsSSL() {
        return (this.tlsProtocols != null && this.tlsProtocols.length > 0);
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @return the partnerName - may return null
     */
    public String getPartnerName() {
        return partnerName;
    }

    /**
     * @return the partnerRole
     */
    public ConnectionTest.PartnerRole getPartnerRole() {
        return partnerRole;
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @param tlsProtocols the tlsProtocols to set
     */
    public void setTLSProtocols(String[] tlsProtocols) {
        this.tlsProtocols = tlsProtocols;
    }

    /**
     * Some additional information for the log etc
     * @param partnerName the partnerName to set
     */
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    /**
     * @param partnerRole the partnerRole to set
     */
    public void setPartnerRole(ConnectionTest.PartnerRole partnerRole) {
        this.partnerRole = partnerRole;
    }

}
