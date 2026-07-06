//$Header: /as2/de/mendelson/util/clientserver/connectiontest/ConnectionTest.java 34    9/04/26 12:29 Heller $
package de.mendelson.util.clientserver.connectiontest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.SocketFactory;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreCertificate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Performs a connection test and returns information about the results
 *
 * @author S.Heller
 * @version $Revision: 34 $
 */
public class ConnectionTest {

    public enum PartnerRole {
        REMOTE_PARTNER(1),
        GATEWAY_PARTNER(2);

        private final int value;

        private PartnerRole(int value) {
            this.value = value;
        }

        @JsonValue
        public int toInt() {
            return value;
        }

        @JsonCreator
        public static PartnerRole of(int value) {
            for (PartnerRole role : PartnerRole.values()) {
                if (role.value == value) {
                    return role;
                }
            }
            throw new IllegalArgumentException("ConnectionTest.PartnerRole: Unknown PartnerRole: " + value);
        }
    }

    public enum Type {
        OFTP2(1),
        AS2(2),
        AS4(3),
        AUTOMATIC_CERTIFICATE_DOWNLOAD(4);

        private final int value;

        private Type(int value) {
            this.value = value;
        }

        @JsonValue
        public int toInt() {
            return value;
        }

        @JsonCreator
        public static Type of(int value) {
            for (Type type : Type.values()) {
                if (type.value == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException("ConnectionTest.Type: Unknown ConnectionType: " + value);
        }
    }

    public static final String[] DEFAULT_TLS_PROTOCOL_LIST
            = new String[]{
                "TLSv1.3",
                "TLSv1.2",
                "TLSv1.1",
                "TLSv1"
            };

    private Logger logger = null;
    private InetSocketAddress remoteAddress = null;
    private static final MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleConnectionTest.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }
    private boolean performLogging = false;

    private final Type testType;
    private ConnectionTestProxy proxy = null;

    public ConnectionTest(Logger logger, ConnectionTest.Type testType) {
        this.logger = logger;
        this.testType = testType;
        this.performLogging = (testType != Type.AUTOMATIC_CERTIFICATE_DOWNLOAD);
    }

    private String getLogTag() {
        return ("[" + rb.getResourceString("tag", this.remoteAddress.toString()) + "] ");
    }

    /**
     * Perform a IP connection test, similar to a telnet connection
     *
     * @param partnerRole The role of the remote partner in the communication,
     * one of ConnectionTest.PartnerRole.REMOTE_PARTNER or
     * ConnectionTest.PartnerRole.GATEWAY_PARTNER
     */
    public ConnectionTestResult checkConnectionPlain(String host, int port, long timeout,
            String senderName, String receiverName, ConnectionTest.PartnerRole partnerRole) {
        this.remoteAddress = new InetSocketAddress(host, port);
        ConnectionTestResult testResult = new ConnectionTestResult(this.remoteAddress, false,
                senderName, receiverName, partnerRole);
        Socket socket = null;
        try {
            try {
                if (this.performLogging) {
                    this.logger.info(this.getLogTag() + rb.getResourceString("test.start.plain", this.remoteAddress.toString()));
                    this.logger.info(this.getLogTag() + rb.getResourceString("timeout.set", String.valueOf(timeout)));
                }
                if (this.proxy != null) {
                    try {
                        socket = this.createPlainProxySocket(timeout);
                    } catch (Exception e) {
                        if (this.performLogging) {
                            this.logger.severe(this.getLogTag() + rb.getResourceString("result.exception",
                                    "[" + e.getClass().getSimpleName() + "]: " + e.getMessage()));
                            this.logger.severe(this.getLogTag() + rb.getResourceString("connection.problem",
                                    this.remoteAddress.toString()));
                        }
                        testResult.setException(e);
                        testResult.setConnectionIsPossible(false);
                        return (testResult);
                    }
                } else {
                    //direct connection, no proxy
                    socket = new Socket();
                    socket.connect(this.remoteAddress, (int) timeout);
                }
                //set socket timeout after connection, for data transfer
                socket.setSoTimeout((int) timeout);
                testResult.setConnectionIsPossible(true);
                if (this.performLogging) {
                    this.logger.config(this.getLogTag() + rb.getResourceString("connection.success", this.remoteAddress.toString()));
                }
            } catch (Exception exception) {
                testResult.setException(exception);
                testResult.setConnectionIsPossible(false);
                if (this.performLogging) {
                    this.logger.severe(this.getLogTag() + rb.getResourceString("result.exception",
                            "[" + exception.getClass().getSimpleName() + "]: " + exception.getMessage()));
                    this.logger.severe(this.getLogTag() + rb.getResourceString("connection.problem", this.remoteAddress.toString()));
                }
                return (testResult);
            }
            if (this.testType == ConnectionTest.Type.OFTP2) {
                /* read SSRM */
                String foundSSRM = "";
                try {
                    if (this.performLogging) {
                        this.logger.info(this.getLogTag() + rb.getResourceString("check.for.service.oftp2"));
                    }
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        foundSSRM = in.readLine();
                    }
                } catch (Exception e) {
                    if (this.performLogging) {
                        this.logger.severe(this.getLogTag() + rb.getResourceString("exception.occured.oftpservice", new Object[]{
                            e.getClass().getSimpleName(), e.getMessage()
                        }));
                    }
                    testResult.setException(e);
                    return (testResult);
                }
                OFTP2SSRM ssrm = new OFTP2SSRM();
                StringBuilder expectedSSRM = new StringBuilder();
                expectedSSRM.append(ssrm.getIndicator());
                expectedSSRM.append(new String(ssrm.getField(OFTP2SSRM.SSRMMSG).getDefaultValue()));
                if (this.performLogging) {
                    this.logger.info(this.getLogTag() + rb.getResourceString("remote.service.identification", foundSSRM));
                }
                if (foundSSRM != null && foundSSRM.endsWith(expectedSSRM.toString())) {
                    if (this.performLogging) {
                        this.logger.config(this.getLogTag() + rb.getResourceString("service.found.success", remoteAddress));
                    }
                    testResult.setOftpServiceFound(true);
                } else {
                    testResult.setOftpServiceFound(false);
                    if (this.performLogging) {
                        this.logger.severe(this.getLogTag() + rb.getResourceString("service.found.failure", remoteAddress));
                    }
                }
            }
        } catch (Exception e) {
            if (this.performLogging) {
                this.logger.severe(this.getLogTag() + rb.getResourceString("exception.occured", new Object[]{
                    e.getClass().getName(), e.getMessage()
                }));
            }
            testResult.setException(e);
            return (testResult);
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    //nop
                }
            }
        }
        return (testResult);
    }

    /**
     * Performs a SSL connection test with the default TLS protocol list and SNI
     *
     * @param partnerRole The role of the remote partner in the communication,
     * one of ConnectionTest.PartnerRole.REMOTE_PARTNER or
     * ConnectionTest.PartnerRole.GATEWAY_PARTNER
     */
    public ConnectionTestResult checkConnectionTLS(String host, int port, long timeout,
            CertificateManager certificateManagerTLS,
            String senderName, String receiverName, ConnectionTest.PartnerRole partnerRole) {
        return (this.checkConnectionTLS(host, port, timeout, certificateManagerTLS, DEFAULT_TLS_PROTOCOL_LIST, true,
                senderName, receiverName, partnerRole));
    }

    /**
     * Let the user examine the contents of a certificate file from a SSL
     * connection.
     *
     * @param certificateManagerSSL The certificate manager to check if the
     * remote certificate has been already imported in the local SSL keystore,
     * might be null - then no test is performed
     * @param partnerRole The role of the remote partner in the communication,
     * one of ConnectionTest.PartnerRole.REMOTE_PARTNER or
     * ConnectionTest.PartnerRole.GATEWAY_PARTNER
     *
     */
    public ConnectionTestResult checkConnectionTLS(String host, int port, long timeout,
            CertificateManager certificateManagerSSL, String[] protocols, boolean useSNI,
            String senderName, String receiverName, ConnectionTest.PartnerRole partnerRole) {
        this.remoteAddress = new InetSocketAddress(host, port);
        ConnectionTestResult testResult = new ConnectionTestResult(this.remoteAddress, true,
                senderName, receiverName, partnerRole);
        Socket plainSocket = null;
        SSLSocket sslSocket = null;
        SSLSession sslSession = null;
        StringBuilder protocolBuilder = new StringBuilder();
        for (String singleProtocolStr : protocols) {
            if (protocolBuilder.length() > 0) {
                protocolBuilder.append(" ,");
            }
            protocolBuilder.append(singleProtocolStr);
        }
        try {
            try {
                if (this.proxy != null) {
                    try {
                        plainSocket = this.createPlainProxySocket(timeout);
                    } catch (Exception e) {
                        if (this.performLogging) {
                            this.logger.severe(this.getLogTag() + " " + e.getMessage());
                        }
                        testResult.setException(e);
                        return (testResult);
                    }
                } else {
                    plainSocket = new Socket();
                    plainSocket.connect(this.remoteAddress, (int) timeout);
                }
                SSLSocketFactory socketFactory;
                SSLContext sslContext = SSLContext.getInstance("TLS");
                X509TrustManager[] trustManagerTrustAll = this.createTrustManagerTrustAll();
                sslContext.init(null, trustManagerTrustAll, null);
                socketFactory = sslContext.getSocketFactory();
                sslSocket = (SSLSocket) socketFactory.createSocket(plainSocket, host, port, true);
                sslSocket.setSoTimeout((int) timeout);
                sslSocket.setEnabledProtocols(protocols);
                SSLParameters sslParams = sslSocket.getSSLParameters();
                sslParams.setEndpointIdentificationAlgorithm(null);
                if (useSNI) {
                    SNIHostName sniHostName = new SNIHostName(host);
                    List<SNIServerName> sniList = new ArrayList<SNIServerName>(1);
                    sniList.add(sniHostName);
                    sslParams.setServerNames(sniList);
                    if (this.performLogging) {
                        this.logger.info(this.getLogTag() + rb.getResourceString("sni.extension.set", host));
                    }
                }
                sslSocket.setSSLParameters(sslParams);
                if (this.performLogging) {
                    this.logger.info(this.getLogTag()
                            + rb.getResourceString("info.protocols", protocolBuilder.toString()));
                }
                Provider usedProvider = sslContext.getProvider();
                if (this.performLogging) {
                    this.logger.info(this.getLogTag()
                            + rb.getResourceString("info.securityprovider", usedProvider.getName()));
                }
                if (this.performLogging) {
                    this.logger.info(this.getLogTag()
                            + rb.getResourceString("test.start.ssl", this.remoteAddress.toString()));
                    this.logger.info(this.getLogTag()
                            + rb.getResourceString("timeout.set", String.valueOf(timeout)));
                }
            } catch (Throwable ex) {
                testResult.setException(ex);
                testResult.setConnectionIsPossible(false);
                if (this.performLogging) {
                    this.logger.severe(this.getLogTag()
                            + rb.getResourceString("connection.problem", this.remoteAddress.toString()));
                }
                return (testResult);
            }
            if (this.performLogging) {
                this.logger.config(this.getLogTag()
                        + rb.getResourceString("connection.success", this.remoteAddress.toString()));
            }
            testResult.setConnectionIsPossible(true);
            String foundProtocol = null;
            try {
                sslSocket.startHandshake();
                sslSession = sslSocket.getSession();
                foundProtocol = sslSession.getProtocol();
                String usedCipherSuite = sslSession.getCipherSuite();
                String[] supportedCipherSuites = sslSocket.getSupportedCipherSuites();
                String[] enabledCipherSuites = sslSocket.getEnabledCipherSuites();
                testResult.setProtocol(foundProtocol);
                testResult.setUsedCipherSuite(usedCipherSuite);
                testResult.setSupportedCipherSuites(supportedCipherSuites);
                testResult.setEnabledCipherSuites(enabledCipherSuites);
            } catch (Throwable e) {
                if (this.performLogging) {
                    String errorMessage = rb.getResourceString("wrong.protocol",
                            new Object[]{
                                foundProtocol,
                                protocolBuilder.toString()
                            });
                    this.logger.severe(this.getLogTag() + errorMessage);
                    this.logger.warning(this.getLogTag() + rb.getResourceString("wrong.protocol.hint"));
                }
                testResult.setException(e);
                return (testResult);
            }
            if (this.performLogging) {
                this.logger.info(this.getLogTag() + rb.getResourceString("protocol.information",
                        new Object[]{foundProtocol, sslSession.getCipherSuite()}));
                this.logger.info(this.getLogTag() + rb.getResourceString("requesting.certificates"));
            }
            X509Certificate[] certs = (X509Certificate[]) sslSession.getPeerCertificates();
            testResult.setFoundCertificates(certs);
            if (this.performLogging) {
                this.logger.config(this.getLogTag() + rb.getResourceString("certificates.found", String.valueOf(certs.length)));
            }
            certs = KeyStoreUtil.orderX509CertChain(certs);
            for (int i = 0; i < certs.length; i++) {
                KeystoreCertificate keystoreCert = new KeystoreCertificate();
                keystoreCert.setCertificate(certs[i], null);
                StringBuilder certDescription = new StringBuilder();
                certDescription.append(keystoreCert.getSubjectDN());
                if (keystoreCert.isCACertificate()) {
                    certDescription.append(" (" + rb.getResourceString("certificate.ca") + ")");
                } else {
                    certDescription.append(" (" + rb.getResourceString("certificate.enduser") + ")");
                }
                if (keystoreCert.isSelfSigned()) {
                    certDescription.append(" (" + rb.getResourceString("certificate.selfsigned") + ")");
                }
                if (this.performLogging) {
                    this.logger.config(this.getLogTag() + rb.getResourceString("certificates.found.details",
                            new Object[]{
                                String.valueOf(i + 1),
                                String.valueOf(certs.length),
                                certDescription.toString()
                            }));
                }
                if (certificateManagerSSL != null) {
                    String foundFingerPrintSHA1 = keystoreCert.getFingerPrintSHA1();
                    String localAlias = certificateManagerSSL.getAliasByFingerprint(foundFingerPrintSHA1);
                    if (this.performLogging) {
                        if (localAlias == null) {
                            this.logger.warning(this.getLogTag() + rb.getResourceString("certificate.does.not.exist.local"));
                        } else {
                            this.logger.config(this.getLogTag() + rb.getResourceString("certificate.does.exist.local",
                                    localAlias));
                        }
                    }
                }
            }
            if (this.testType == ConnectionTest.Type.OFTP2) {
                if (this.performLogging) {
                    this.logger.info(this.getLogTag() + rb.getResourceString("check.for.service.oftp2"));
                }
                String foundSSRM = "";
                try (BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()))) {
                    foundSSRM = in.readLine();
                } catch (Throwable e) {
                    this.logger.severe(this.getLogTag() + rb.getResourceString("exception.occured.oftpservice", new Object[]{
                        e.getClass().getSimpleName(), e.getMessage()
                    }));
                    testResult.setException(e);
                    return (testResult);
                }
                OFTP2SSRM ssrm = new OFTP2SSRM();
                StringBuilder expectedSSRM = new StringBuilder();
                expectedSSRM.append(ssrm.getIndicator());
                expectedSSRM.append(new String(ssrm.getField(OFTP2SSRM.SSRMMSG).getDefaultValue()));
                if (this.performLogging) {
                    this.logger.info(this.getLogTag() + rb.getResourceString("remote.service.identification", foundSSRM));
                }
                if (foundSSRM != null && foundSSRM.endsWith(expectedSSRM.toString())) {
                    if (this.performLogging) {
                        this.logger.config(this.getLogTag() + rb.getResourceString("service.found.success", remoteAddress));
                    }
                    testResult.setOftpServiceFound(true);
                } else {
                    testResult.setOftpServiceFound(false);
                    if (this.performLogging) {
                        this.logger.severe(this.getLogTag() + rb.getResourceString("service.found.failure", remoteAddress));
                    }
                }
            }
        } catch (Throwable e) {
            if (this.performLogging) {
                this.logger.severe(this.getLogTag() + rb.getResourceString("exception.occured", new Object[]{
                    e.getClass().getName(), e.getMessage()
                }));
            }
            testResult.setException(e);
            return (testResult);
        } finally {
            if (sslSocket != null && !sslSocket.isClosed()) {
                try {
                    sslSocket.close();
                } catch (IOException e) {
                }
            }
            if (plainSocket != null && !plainSocket.isClosed()) {
                try {
                    plainSocket.close();
                } catch (IOException e) {
                }
            }
            if (sslSession != null) {
                sslSession.invalidate();
            }
        }
        return (testResult);
    }

    /**
     * If a proxy is used and TLS is used it is required to connect to the proxy
     * in plain HTTP, send a CONNECT command and build the SSL layer on top of
     * this proxy socket
     *
     * @return
     * @throws Exception
     */
    private Socket createPlainProxySocket(long timeoutInMS) throws Exception {
        Socket plainSocket;
        //Use the jsch proxy implementation
        ProxyHTTP jschProxy = new ProxyHTTP(this.proxy.getAddress(), this.proxy.getPort());
        if (this.proxy.usesAuthentication()) {
            jschProxy.setUserPasswd(this.proxy.getUserName(), this.proxy.getPassword());
            if (this.performLogging) {
                this.logger.warning(rb.getResourceString("test.connection.proxy.auth",
                        new Object[]{
                            this.proxy.getAddress(),
                            this.proxy.getUserName()
                        }));
            }
        } else {
            if (this.performLogging) {
                this.logger.warning(rb.getResourceString("test.connection.proxy.noauth",
                        this.proxy.getAddress()));
            }
        }
        //let jsch open the CONNECT connection to the proxy
        SocketFactory dummyFactory = new SocketFactory() {
            @Override
            public Socket createSocket(String host, int port) throws IOException {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), (int) timeoutInMS);
                return socket;
            }

            @Override
            public InputStream getInputStream(Socket socket) throws IOException {
                return (socket.getInputStream());
            }

            @Override
            public OutputStream getOutputStream(Socket socket) throws IOException {
                return socket.getOutputStream();
            }
        };
        jschProxy.connect(dummyFactory, this.remoteAddress.getHostName(),
                this.remoteAddress.getPort(), (int) timeoutInMS);
        plainSocket = jschProxy.getSocket();
        return (plainSocket);
    }

    /**
     * Generates a new Trust manager that trusts all remote certificates
     */
    private X509TrustManager[] createTrustManagerTrustAll() {
        X509TrustManager[] trustManagerTrustAll = {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] certChain, String auth) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certChain, String auth) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
        return (trustManagerTrustAll);
    }

    /**
     * @return the proxy
     */
    public ConnectionTestProxy getProxy() {
        return proxy;
    }

    /**
     * @param proxy the proxy to set
     */
    public void setProxy(ConnectionTestProxy proxy) {
        this.proxy = proxy;
    }

}
