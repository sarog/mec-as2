//$Header: /as4/de/mendelson/util/httpconfig/clientserver/DisplayHTTPServerConfigurationResponse.java 12    11/06/25 13:17 Heller $
package de.mendelson.util.httpconfig.clientserver;

import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class DisplayHTTPServerConfigurationResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String httpServerConfigFile = null;
    private String httpServerUserConfigFile = null;
    private List<String> cipherList = new ArrayList<String>();
    private List<String> protocolList = new ArrayList<String>();
    private boolean tlsEnabled = false;
    private boolean embeddedHTTPServerStarted = false;
    private String javaVersion = null;
    private String embeddedJettyServerVersion = null;
    private String miscConfigurationText = "";
    private String protocolConfigurationText = "";
    private String cipherConfigurationText = "";

    public DisplayHTTPServerConfigurationResponse(DisplayHTTPServerConfigurationRequest request) {
        super(request);
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public DisplayHTTPServerConfigurationResponse() {
        super();
    }
    
    /**
     * @return the protocolConfigurationText
     */
    public String getProtocolConfigurationText() {
        return protocolConfigurationText;
    }

    /**
     * @param protocolConfigurationText the protocolConfigurationText to set
     */
    public void setProtocolConfigurationText(String protocolConfigurationText) {
        this.protocolConfigurationText = protocolConfigurationText;
    }

    /**
     * @return the cipherConfigurationText
     */
    public String getCipherConfigurationText() {
        return cipherConfigurationText;
    }

    /**
     * @param cipherConfigurationText the cipherConfigurationText to set
     */
    public void setCipherConfigurationText(String cipherConfigurationText) {
        this.cipherConfigurationText = cipherConfigurationText;
    }

    public void setMiscConfigurationText(String miscConfigurationText) {
        this.miscConfigurationText = miscConfigurationText;
    }

    /**
     * @return the configurationStr
     */
    public String getMiscConfigurationText() {
        return (this.miscConfigurationText);
    }

    @Override
    public String toString() {
        return ("Display information about the HTTP server");
    }

    /**
     * @return the httpServerConfigFile
     */
    public String getHttpServerConfigFile() {
        return httpServerConfigFile;
    }

    /**
     * @param httpServerConfigFile the httpServerConfigFile to set
     */
    public void setHttpServerConfigFile(String httpServerConfigFile) {
        this.httpServerConfigFile = httpServerConfigFile;
    }

    public void addCipher(String cipher) {
        this.cipherList.add(cipher);
    }

    public void setCipherList( List<String> cipherList) {
        this.cipherList.clear();
        this.cipherList.addAll( cipherList);
    }
    
    public List<String> getCipherList() {
        return (this.cipherList);
    }

    public void addProtocol(String protocol) {
        this.protocolList.add(protocol);
    }

    public void setProtocolList( List<String> protocolList) {
        this.protocolList.clear();
        this.protocolList.addAll( protocolList);
    }
    
    public List<String> getProtocolList() {
        return (this.protocolList);
    }

    /**
     * @return the sslEnabled
     */
    public boolean isTLSEnabled() {
        return tlsEnabled;
    }

    /**
     * @param tlsEnabled the sslEnabled to set
     */
    public void setTLSEnabled(boolean tlsEnabled) {
        this.tlsEnabled = tlsEnabled;
    }

    /**
     * @return the embeddedHTTPServerStartet
     */
    public boolean isEmbeddedHTTPServerStarted() {
        return embeddedHTTPServerStarted;
    }

    /**
     * @param embeddedHTTPServerStartet the embeddedHTTPServerStartet to set
     */
    public void setEmbeddedHTTPServerStarted(boolean embeddedHTTPServerStartet) {
        this.embeddedHTTPServerStarted = embeddedHTTPServerStartet;
    }

    /**
     * @return the javaVersion
     */
    public String getJavaVersion() {
        return javaVersion;
    }

    /**
     * @param javaVersion the javaVersion to set
     */
    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    /**
     * @return the embeddedJettyServerVersion
     */
    public String getEmbeddedJettyServerVersion() {
        return embeddedJettyServerVersion;
    }

    /**
     * @param embeddedJettyServerVersion the embeddedJettyServerVersion to set
     */
    public void setEmbeddedJettyServerVersion(String embeddedJettyServerVersion) {
        this.embeddedJettyServerVersion = embeddedJettyServerVersion;
    }

    /**
     * @return the httpUserServerConfigFile
     */
    public String getHTTPServerUserConfigFile() {
        return httpServerUserConfigFile;
    }

    /**
     * @param httpUserServerConfigFile the httpUserServerConfigFile to set
     */
    public void setHTTPServerUserConfigFile(String httpUserServerConfigFile) {
        this.httpServerUserConfigFile = httpUserServerConfigFile;
    }

}
