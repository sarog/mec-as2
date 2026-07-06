//$Header: /as2/de/mendelson/util/clientserver/connectiontest/ConnectionTestResult.java 8     9/04/26 8:08 Heller $
package de.mendelson.util.clientserver.connectiontest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.clientserver.ClientServerException;
import de.mendelson.util.clientserver.ClientServerExceptionContainer;
import de.mendelson.util.clientserver.SerializationDummy;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.security.cert.X509Certificate;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores the results of a connection test
 *
 * @author S.Heller
 * @version $Revision: 8 $
 */
public class ConnectionTestResult implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean connectionIsPossible = false;
    private boolean oftpServiceFound = false;
    private X509Certificate[] foundCertificates = null;
    private ClientServerExceptionContainer exceptionContainer = null;
    private String protocol = null;
    private InetSocketAddress testedRemoteAddress = null;
    private boolean wasSSLTest = false;
    private String usedCipherSuite = null;
    private String[] supportedCipherSuites = null;
    private String[] enabledCipherSuites = null;
    private String senderName = null;
    private String receiverName = null;
    private ConnectionTest.PartnerRole partnerRole = ConnectionTest.PartnerRole.REMOTE_PARTNER;

    public ConnectionTestResult(InetSocketAddress testedRemoteAddress, boolean wasSSLTest,
            String senderName, String receiverName,  ConnectionTest.PartnerRole partnerRole) {
        this.testedRemoteAddress = testedRemoteAddress;
        this.wasSSLTest = wasSSLTest;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.partnerRole = partnerRole;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public ConnectionTestResult() {
    }

    public void setUsedCipherSuite(String usedCipherSuite) {
        this.usedCipherSuite = usedCipherSuite;
    }

    public ConnectionTest.PartnerRole getPartnerRole() {
        return (this.partnerRole);
    }

    public String getSenderName() {
        return (this.senderName);
    }

    public String getReceiverName() {
        return (this.receiverName);
    }

    /**
     * @return the oftpServiceFound
     */
    public boolean isOftpServiceFound() {
        return oftpServiceFound;
    }

    /**
     * @param oftpServiceFound the oftpServiceFound to set
     */
    public void setOftpServiceFound(boolean oftpServiceFound) {
        this.oftpServiceFound = oftpServiceFound;
    }

    /**
     * @return the connectionIsPossible
     */
    public boolean isConnectionIsPossible() {
        return connectionIsPossible;
    }

    /**
     * @param connectionIsPossible the connectionIsPossible to set
     */
    public void setConnectionIsPossible(boolean connectionIsPossible) {
        this.connectionIsPossible = connectionIsPossible;
    }

    /**
     * @return the foundCertificates
     */
    public X509Certificate[] getFoundCertificates() {
        return foundCertificates;
    }

    /**
     * @return the exception
     */
    @JsonIgnore
    public ClientServerException getException() {
        return (ClientServerExceptionContainer.toThrowable(this.exceptionContainer));
    }

    /**
     * @param exception the exception to set
     */
    @JsonIgnore
    public void setException(Throwable exception) {        
        this.setExceptionContainer(ClientServerExceptionContainer.fromThrowable(exception));
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the testedRemoteAddress
     */
    public InetSocketAddress getTestedRemoteAddress() {
        return testedRemoteAddress;
    }

    /**
     * @return the wasSSLTest
     */
    public boolean isWasSSLTest() {
        return wasSSLTest;
    }

    /**
     * @return the usedCipherSuite
     */
    public String getUsedCipherSuite() {
        return usedCipherSuite;
    }

    /**
     * @return the supportedCipherSuites
     */
    public String[] getSupportedCipherSuites() {
        return supportedCipherSuites;
    }

    /**
     * @return the enabledCipherSuites
     */
    public String[] getEnabledCipherSuites() {
        return enabledCipherSuites;
    }

    /**
     * @param foundCertificates the foundCertificates to set
     */
    public void setFoundCertificates(X509Certificate[] foundCertificates) {
        this.foundCertificates = foundCertificates;
    }

    /**
     * @param testedRemoteAddress the testedRemoteAddress to set
     */
    public void setTestedRemoteAddress(InetSocketAddress testedRemoteAddress) {
        this.testedRemoteAddress = testedRemoteAddress;
    }

    /**
     * @param wasSSLTest the wasSSLTest to set
     */
    public void setWasSSLTest(boolean wasSSLTest) {
        this.wasSSLTest = wasSSLTest;
    }

    /**
     * @param supportedCipherSuites the supportedCipherSuites to set
     */
    public void setSupportedCipherSuites(String[] supportedCipherSuites) {
        this.supportedCipherSuites = supportedCipherSuites;
    }

    /**
     * @param enabledCipherSuites the enabledCipherSuites to set
     */
    public void setEnabledCipherSuites(String[] enabledCipherSuites) {
        this.enabledCipherSuites = enabledCipherSuites;
    }

    /**
     * @param senderName the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @param receiverName the receiverName to set
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * @param partnerRole the partnerRole to set
     */
    public void setPartnerRole(ConnectionTest.PartnerRole partnerRole) {
        this.partnerRole = partnerRole;
    }

     /**
     * Dummy method for Jackson deserialization only. Do not use in logic
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public ClientServerExceptionContainer getExceptionContainer() {
        return exceptionContainer;
    }

    /**
     * Dummy method for Jackson deserialization only. Do not use in logic
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setExceptionContainer(ClientServerExceptionContainer exceptionContainer) {
        this.exceptionContainer = exceptionContainer;
    }
    
}
