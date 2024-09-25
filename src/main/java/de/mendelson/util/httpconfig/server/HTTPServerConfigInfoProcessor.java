//$Header: /as4/de/mendelson/util/httpconfig/server/HTTPServerConfigInfoProcessor.java 6     12/15/17 11:33a Heller $
package de.mendelson.util.httpconfig.server;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.httpconfig.clientserver.DisplayHTTPServerConfigurationRequest;
import de.mendelson.util.httpconfig.clientserver.DisplayHTTPServerConfigurationResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.mina.core.session.IoSession;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Processes a http config request on the server side
 *
 * @author S.Heller
 * @version $Revision: 6 $
 */
public class HTTPServerConfigInfoProcessor {

    private HTTPServerConfigInfo httpServerConfigInfo;
    private MecResourceBundle rb;

    public HTTPServerConfigInfoProcessor(HTTPServerConfigInfo httpServerConfigInfo) {
        this.httpServerConfigInfo = httpServerConfigInfo;
        //Load default resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleHTTPServerConfigProcessor.class.getName());
        } //load up  resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    /**
     * Async request from a client to display information about the server
     */
    public void processDisplayServerConfigurationRequest(IoSession session, DisplayHTTPServerConfigurationRequest request) {
        DisplayHTTPServerConfigurationResponse response = new DisplayHTTPServerConfigurationResponse(request);
        Path httpServerConfigFile = Paths.get("./jetty9/etc/jetty.xml");
        response.setHttpServerConfigFile(httpServerConfigFile.normalize().toAbsolutePath().toString());
        StringBuilder logBuilder = new StringBuilder();
        List<HTTPServerConfigInfo.Listener> listenerList = this.httpServerConfigInfo.getListener();
        List<String> receiptURLList = new ArrayList<String>();
        List<String> serverStateURLList = new ArrayList<String>();
        boolean sslEnabled = false;
        response.setEmbeddedHTTPServerStarted(this.httpServerConfigInfo.isEmbeddedHTTPServerStarted());
        for (HTTPServerConfigInfo.Listener listener : listenerList) {
            String protocol = "NON-SSL";
            if (listener.getProtocol() != null && listener.getProtocol().toLowerCase().contains("ssl")) {
                protocol = "SSL";
                sslEnabled = true;
                receiptURLList.add("https://<HOST>:" + listener.getPort() + this.httpServerConfigInfo.getReceiptURLPath());
                serverStateURLList.add("https://<HOST>:" + listener.getPort() + this.httpServerConfigInfo.getServerStatePath());
            } else {
                receiptURLList.add("http://<HOST>:" + listener.getPort() + this.httpServerConfigInfo.getReceiptURLPath());
                serverStateURLList.add("http://<HOST>:" + listener.getPort() + this.httpServerConfigInfo.getServerStatePath());
            }
            String adapterStr = "<unknown>";
            if (listener.getAdapter() != null) {
                adapterStr = listener.getAdapter();
            }
            logBuilder.append(
                    this.rb.getResourceString("http.server.config.listener",
                            new Object[]{String.valueOf(listener.getPort()),
                                protocol,
                                adapterStr
                            })
            );
            logBuilder.append("\n");
        }
        //find out WAN IP
        String hostname = null;
        BufferedReader in = null;
        try {
            URL whatismyip = new URL("http://mendelson-e-c.com/mendelson_whatsmyip.php");
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine(); //you get the IP as a String
            if (ip == null) {
                ip = "Unknown IP";
            }
            //try to get host name for the answer            
            hostname = "Unknown host";
            try {
                hostname = InetAddress.getByName(ip).getHostName();
            } catch (Exception e) {
                //nop
            }
            logBuilder.append(this.rb.getResourceString("external.ip",
                    new Object[]{ip, hostname}));
        } catch (Exception e) {
            logBuilder.append(this.rb.getResourceString("external.ip.error"));
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    //nop
                }
            }
        }
        //build receipt URLS to display
        List<String> tempList = new ArrayList<String>();
        if (hostname != null) {
            for (String receiptURL : receiptURLList) {
                String fullReceiptURL = this.replace(receiptURL, "<HOST>", hostname);
                tempList.add(fullReceiptURL);
            }
            receiptURLList.clear();
            receiptURLList.addAll(tempList);
        }
        logBuilder.append("\n");
        logBuilder.append("\n");
        logBuilder.append(this.rb.getResourceString("http.receipturls"));
        logBuilder.append("\n");
        for (String receiptURL : receiptURLList) {
            logBuilder.append(receiptURL);
            logBuilder.append("\n");
        }
        if (!serverStateURLList.isEmpty()) {
            logBuilder.append("\n");
            logBuilder.append(this.rb.getResourceString("http.serverstateurl"));
            logBuilder.append("\n");
            logBuilder.append( this.replace(serverStateURLList.get(0), "<HOST>", hostname));
            logBuilder.append("\n");
            logBuilder.append("\n");
        }
        response.setSSLEnabled(sslEnabled);
        if (sslEnabled) {
            if (this.httpServerConfigInfo.getKeystorePath() != null) {
                //normalize this path, may contain "/./" parts
                Path keystoreFile = Paths.get(this.httpServerConfigInfo.getKeystorePath());
                logBuilder.append(this.rb.getResourceString("http.server.config.keystorepath",
                        keystoreFile.normalize().toAbsolutePath()));
                logBuilder.append("\n");
            }
            logBuilder.append(this.rb.getResourceString("http.server.config.clientauthentication",
                    String.valueOf(this.httpServerConfigInfo.needsClientAuthentication())));
            logBuilder.append("\n");
            for (String protocol : this.httpServerConfigInfo.getPossibleProtocols()) {
                response.addProtocol(protocol);
            }
            for (String cipher : this.httpServerConfigInfo.getPossibleCipher()) {
                response.addCipher(cipher);
            }
        }
        response.setConfigurationStr(logBuilder.toString());
        response.setJavaVersion(System.getProperty("java.version"));
        //its a sync request
        session.write(response);
    }

    /**
     * Replaces the string tag by the string replacement in the sourceString
     *
     * @param source Source string
     * @param tag	String that will be replaced
     * @param replacement String that will replace the tag
     * @return String that contains the replaced values
     */
    private String replace(String source, String tag, String replacement) {
        if (source == null) {
            return null;
        }
        StringBuilder buffer = new StringBuilder();
        while (true) {
            int index = source.indexOf(tag);
            if (index == -1) {
                buffer.append(source);
                return (buffer.toString());
            }
            buffer.append(source.substring(0, index));
            buffer.append(replacement);
            source = source.substring(index + tag.length());
        }
    }

}
