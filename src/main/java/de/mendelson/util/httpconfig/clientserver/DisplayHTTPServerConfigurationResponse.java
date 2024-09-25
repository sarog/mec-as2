//$Header: /as4/de/mendelson/util/httpconfig/clientserver/DisplayHTTPServerConfigurationResponse.java 3     12/11/17 1:29p Heller $
package de.mendelson.util.httpconfig.clientserver;

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
 * @version $Revision: 3 $
 */
public class DisplayHTTPServerConfigurationResponse extends ClientServerResponse implements Serializable {

    

    private String httpServerConfigFile = null;
    private List<String> cipherList = new ArrayList<String>();
    private List<String> protocolList = new ArrayList<String>();
    private boolean sslEnabled = false;
    private boolean embeddedHTTPServerStarted = false;
    private String javaVersion = null;

    /**
     * @return the configurationStr
     */
    public String getConfigurationStr() {
        return configurationStr;
    }

    private String configurationStr = "";

    public DisplayHTTPServerConfigurationResponse(DisplayHTTPServerConfigurationRequest request) {
        super(request);
    }

    public void setConfigurationStr(String configurationStr) {
        this.configurationStr = configurationStr;
    }

    @Override
    public String toString() {
        return ("Display information about the server");
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

    public List<String> getCipher() {
        return (this.cipherList);
    }

    public void addProtocol(String protocol) {
        this.protocolList.add(protocol);
    }

    public List<String> getProtocol() {
        return (this.protocolList);
    }

    /**
     * @return the sslEnabled
     */
    public boolean isSSLEnabled() {
        return sslEnabled;
    }

    /**
     * @param sslEnabled the sslEnabled to set
     */
    public void setSSLEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
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
    
}
