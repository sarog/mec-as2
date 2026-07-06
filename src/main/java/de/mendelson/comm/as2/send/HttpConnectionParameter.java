//$Header: /as2/de/mendelson/comm/as2/send/HttpConnectionParameter.java 9     31/03/26 16:31 Heller $
package de.mendelson.comm.as2.send;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.mendelson.comm.as2.AS2ServerVersion;
import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Sets several parameter for an outbound http connection. This includes
 * routing, connection and protocol issues
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class HttpConnectionParameter {

    private boolean staleConnectionCheck = true;
    private int connectionTimeoutMillis = -1;
    private int soTimeoutMillis = -1;
    private InetAddress localAddress = null;
    private String userAgent = AS2ServerVersion.getUserAgent();
    private HttpProtocolVersion httpProtocolVersion = null;
    private boolean useExpectContinue = true;
    private ProxyObject proxy = null;
    private boolean trustAllRemoteServerCertificates = false;
    private boolean strictHostCheck = true;
    private Map<String, String> userdefinedHeaderMap = new LinkedHashMap<String, String>();

    public enum HttpProtocolVersion {
        HTTP_1_0("1.0"),
        HTTP_1_1("1.1");

        private final String versionString;

        HttpProtocolVersion(String versionString) {
            this.versionString = versionString;
        }

        @Override
        @JsonValue
        public String toString() {
            return this.versionString;
        }

        @JsonCreator
        public static HttpProtocolVersion of(String version) {
            if (version == null) {
                return HTTP_1_1;
            }
            for (HttpProtocolVersion value : values()) {
                if (value.versionString.equals(version)) {
                    return value;
                }
            }
            return( HTTP_1_1 );
        }
    }

    public HttpConnectionParameter() {
    }

    public HttpConnectionParameter setProxy(String host, int port, String user, char[] password) {
        this.setProxy(new ProxyObject());
        this.getProxy().setHost(host);
        this.getProxy().setPort(port);
        if (user != null) {
            this.getProxy().setUser(user);
            if (password != null) {
                this.getProxy().setPassword(password);
            }
        }
        return (this);
    }

    /**
     * @return the staleConnectionCheck
     */
    public boolean isStaleConnectionCheck() {
        return staleConnectionCheck;
    }

    /**
     * @param staleConnectionCheck the staleConnectionCheck to set
     */
    public HttpConnectionParameter setStaleConnectionCheck(boolean staleConnectionCheck) {
        this.staleConnectionCheck = staleConnectionCheck;
        return (this);
    }

    /**
     * @return the connectionTimeout
     */
    public int getConnectionTimeoutMillis() {
        return connectionTimeoutMillis;
    }

    /**
     * @param connectionTimeout the connectionTimeout to set
     */
    public HttpConnectionParameter setConnectionTimeoutMillis(int connectionTimeout) {
        this.connectionTimeoutMillis = connectionTimeout;
        return (this);
    }

    /**
     * @return the soTimeout
     */
    public int getSoTimeoutMillis() {
        return soTimeoutMillis;
    }

    /**
     */
    public HttpConnectionParameter setSoTimeoutMillis(int soTimeoutMillis) {
        this.soTimeoutMillis = soTimeoutMillis;
        return (this);
    }

    /**
     * @return the localAddress
     */
    public InetAddress getLocalAddress() {
        return localAddress;
    }

    /**
     * @param localAddress the localAddress to set
     */
    public HttpConnectionParameter setLocalAddress(InetAddress localAddress) {
        this.localAddress = localAddress;
        return (this);
    }

    /**
     * @return the userAgent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * @param userAgent the userAgent to set
     */
    public HttpConnectionParameter setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return (this);
    }

    /**
     * @return the httpProtocolVersion
     */
    public HttpProtocolVersion getHttpProtocolVersion() {
        return httpProtocolVersion;
    }

    /**
     * @param httpProtocolVersion the httpProtocolVersion to set
     */
    public HttpConnectionParameter setHttpProtocolVersion(HttpProtocolVersion httpProtocolVersion) {
        this.httpProtocolVersion = httpProtocolVersion;
        return (this);
    }

    /**
     * @return the useExpectContinue
     */
    public boolean isUseExpectContinue() {
        return useExpectContinue;
    }

    /**
     * @param useExpectContinue the useExpectContinue to set
     */
    public HttpConnectionParameter setUseExpectContinue(boolean useExpectContinue) {
        this.useExpectContinue = useExpectContinue;
        return (this);
    }

    /**
     * @return the proxy
     */
    public ProxyObject getProxy() {
        return proxy;
    }

    /**
     * @param proxy the proxy to set
     */
    public HttpConnectionParameter setProxy(ProxyObject proxy) {
        this.proxy = proxy;
        return (this);
    }

    /**
     * @return the trustAllRemoteServerCertificates
     */
    public boolean getTrustAllRemoteServerCertificates() {
        return trustAllRemoteServerCertificates;
    }

    /**
     * @param trustAllRemoteServerCertificates the
     * trustAllRemoteServerCertificates to set
     */
    public HttpConnectionParameter setTrustAllRemoteServerCertificates(boolean trustAllRemoteServerCertificates) {
        this.trustAllRemoteServerCertificates = trustAllRemoteServerCertificates;
        return (this);
    }

    /**
     * @return the trustAllHostnames
     */
    public boolean getStrictHostCheck() {
        return strictHostCheck;
    }

    /**
     * @param strictHostCheck the trustAllHostnames to set
     */
    public HttpConnectionParameter setStrictHostCheck(boolean strictHostCheck) {
        this.strictHostCheck = strictHostCheck;
        return (this);
    }

    /**
     * @return the userdefinedHeaderMap
     */
    public Map<String, String> getUserdefinedHeaderMap() {
        return userdefinedHeaderMap;
    }

    /**
     * @param userdefinedHeaderMap the userdefinedHeaderMap to set
     */
    public HttpConnectionParameter setUserdefinedHeaderMap(Map<String, String> userdefinedHeaderMap) {
        this.userdefinedHeaderMap.clear();
        if (userdefinedHeaderMap != null) {
            this.userdefinedHeaderMap.putAll(userdefinedHeaderMap);
        }
        return (this);
    }

}
