 ///$Header: /mec_as2/de/mendelson/comm/as2/servlet/HttpReceiver.java 72    15/04/26 12:43 Heller $
package de.mendelson.comm.as2.servlet;

import de.mendelson.Copyright;
import de.mendelson.comm.as2.AS2ServerVersion;
import de.mendelson.comm.as2.clientserver.message.IncomingMessageRequest;
import de.mendelson.comm.as2.clientserver.message.IncomingMessageResponse;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.AS2Tools;
import de.mendelson.util.clientserver.AnonymousTextClient;
import de.mendelson.util.clientserver.BaseClient;
import de.mendelson.util.clientserver.ClientType;
import de.mendelson.util.clientserver.connectionpool.PooledAnonymousTextClient;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import javax.net.ssl.SSLSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Servlet to receive AS2 messages via HTTP
 *
 * @author S.Heller
 * @version $Revision: 72 $
 */
public class HttpReceiver extends HttpServlet {

    public HttpReceiver() {
    }

    /**
     * A GET request should be rejected
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        out.println("<HTML>");
        out.println("    <HEAD>");
        out.println("        <META NAME=\"description\" CONTENT=\"mendelson-e-commerce GmbH: Your EAI partner\">");
        out.println("        <META NAME=\"copyright\" CONTENT=\"mendelson-e-commerce GmbH\">");
        out.println("        <META NAME=\"robots\" CONTENT=\"NOINDEX,NOFOLLOW,NOARCHIVE,NOSNIPPET\">");
        out.println("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        out.println("        <title>" + AS2ServerVersion.getProductName() + "</title>");
        out.println("        <link rel=\"shortcut icon\" href=\"images/mendelson_favicon.png\" type=\"image/x-icon\" />");
        out.println("    </HEAD>");
        out.println("    <BODY>");
        out.println("<H2>" + AS2ServerVersion.getProductName() + " "
                + AS2ServerVersion.getVersion()
                + " " + AS2ServerVersion.getBuild() + "</H2>");
        out.println("<BR> " + Copyright.getCopyrightMessage());
        out.println("<BR><br>You have performed an HTTP GET on this URL. <BR>");
        out.println("To submit an AS2 message, you must POST the message to this URL <BR>");
        out.println("    </BODY>");
        out.println("</HTML>");
    }

    /**
     * POST by the HTTP client: receive the message and work on it
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //this is set by the Jetty starter, it adds a timing handler to the embedded jetty. 
        //If it is not available the current time millis will be taken
        Long receiptStartTimeMSAttr = (Long) request.getAttribute("jetty.request.startTime");
        if (receiptStartTimeMSAttr == null) {
            //fallback: use the doPost time, this is a smaller time which will show much too high transfer rates
            receiptStartTimeMSAttr = Long.valueOf(System.currentTimeMillis());
        }
        long receiptStartTimeMS = Long.valueOf(receiptStartTimeMSAttr);
        //stores if the commit already occured. Do not send an additional error in this case        
        boolean committed = false;
        Path dataFile = null;
        try {
            String tlsProtocol = "-";
            String cipherSuite = "-";
            int localPort = request.getLocalPort();
            String remoteAddress = request.getRemoteAddr();
            //might be one of
            //javax.servlet.request.ssl_session
            //org.eclipse.jetty.servlet.request.ssl_session
            //[]...
            String sslSessionAttributeKey = null;
            Enumeration<String> attributeEnumeration = request.getAttributeNames();
            while (attributeEnumeration.hasMoreElements()) {
                String attributeKey = attributeEnumeration.nextElement();
                if (attributeKey.toLowerCase().contains(".ssl_session")) {
                    sslSessionAttributeKey = attributeKey;
                    break;
                }
            }
            //get SSL information
            if (sslSessionAttributeKey != null) {
                SSLSession sslSession = (SSLSession) request.getAttribute(sslSessionAttributeKey);
                if (sslSession != null) {
                    tlsProtocol = sslSession.getProtocol();
                    cipherSuite = sslSession.getCipherSuite();
                }
            }
            try (InputStream inStream = request.getInputStream()) {
                //store the data in a file to process it later. This may be useful
                //for a huge data request that may lead to a out of memory fairly easy.
                dataFile = AS2Tools.createTempFile("as2", "request");
                long transferredBytes = Files.copy(inStream, dataFile, StandardCopyOption.REPLACE_EXISTING);
                long receiptEndTime = System.currentTimeMillis();
                //extract header
                LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
                Enumeration<String> enumeration = request.getHeaderNames();
                while (enumeration.hasMoreElements()) {
                    String key = enumeration.nextElement();
                    headerMap.put(key.toLowerCase(), request.getHeader(key));
                }
                //check if this is a AS2 message that requests async MDN. In this case return the ok code
                //before processing the message, there is no need to keep the connection alive.
                boolean isAS2MessageRequestingAsyncMDN = headerMap.containsKey("receipt-delivery-option")
                        && headerMap.get("receipt-delivery-option") != null
                        && !headerMap.get("receipt-delivery-option").trim().isEmpty();
                if (isAS2MessageRequestingAsyncMDN) {
                    this.informAS2ServerIncomingMessage(
                            dataFile, headerMap, request, null, tlsProtocol, cipherSuite, localPort,
                            remoteAddress, receiptStartTimeMS, receiptEndTime, transferredBytes);
                    committed = true;
                    response.setStatus(HttpServletResponse.SC_OK);
                    //close the connection
                    response.getWriter().flush();
                    response.getWriter().close();
                } else {
                    this.informAS2ServerIncomingMessage(dataFile, headerMap, request, response, tlsProtocol, cipherSuite, localPort,
                            remoteAddress, receiptStartTimeMS, receiptEndTime, transferredBytes);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            if (!committed) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } finally {
            if (dataFile != null) {
                try {
                    Files.delete(dataFile);
                } catch (IOException e) {
                    SystemEvent event = new SystemEvent(
                            SystemEvent.Severity.WARNING,
                            SystemEvent.Origin.SYSTEM,
                            SystemEvent.Type.FILE_DELETE);
                    event.setSubject(event.typeToTextLocalized())
                            .setBody("[" + e.getClass().getSimpleName() + "]: " + e.getMessage());
                    SystemEventManagerImplAS2.instance().newEvent(event);
                }
            }
        }
    }//end of doPost

    /**
     * Informs the AS2 server that a new message arrived and returns the HTTP
     * returncode that has been set by the processing server
     */
    private void informAS2ServerIncomingMessage(Path dataFile,
            LinkedHashMap<String, String> headerMap, HttpServletRequest request,
            HttpServletResponse response, String tlsProtocol,
            String cipherSuite, int localPort, String remoteAddress,
            long receiptStartTime, long receiptEndTime, long transferredBytes) throws Throwable {
        IncomingMessageRequest messageRequest = new IncomingMessageRequest();
        messageRequest.setMessageDataFilename(dataFile.toAbsolutePath().toString());
        messageRequest.setContentType(request.getContentType());
        messageRequest.setUsesTLS(request.isSecure());
        messageRequest.setLocalPort(localPort);
        messageRequest.setTLSProtocol(tlsProtocol);
        messageRequest.setCipherSuite(cipherSuite);
        messageRequest.setRemoteAddress(remoteAddress);
        String remoteHost = request.getRemoteHost();
        if (remoteHost == null) {
            remoteHost = request.getRemoteAddr();
        }
        messageRequest.setRemoteHost(remoteHost);
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key != null && value != null) {
                messageRequest.addHeader(key, value);
            }
        }
        messageRequest.setTransferredBytes(transferredBytes);
        messageRequest.setReceiptStartTime(receiptStartTime);
        messageRequest.setReceiptEndTime(receiptEndTime);
        try (AnonymousTextClient client = PooledAnonymousTextClient.createClient(
                ClientType.WEB, AS2ServerVersion.instance())) {
            client.setDisplayServerLogMessages(false);
            client.connect("localhost", AS2Server.CLIENTSERVER_COMM_PORT, 30000);
            IncomingMessageResponse messageResponse = (IncomingMessageResponse) client.sendSyncWaitInfinite(messageRequest);
            if (messageResponse.getException() != null) {
                throw (messageResponse.getException());
            }
            //build up response, this is the sync MDN
            if (response != null) {
                if (messageResponse.getHttpReturnCode() != HttpServletResponse.SC_OK) {
                    response.setStatus(messageResponse.getHttpReturnCode());
                }
                //add MDN data
                if (messageResponse.getMDNData() != null) {
                    Properties mdnHeader = messageResponse.getHeader();
                    for (String headerKey : mdnHeader.stringPropertyNames()) {
                        response.setHeader(headerKey, mdnHeader.getProperty(headerKey));
                    }
                    response.setContentLength(messageResponse.getMDNData().length);
                    response.getOutputStream().write(messageResponse.getMDNData());
                    response.getOutputStream().flush();
                }
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Receive AS2 messages via HTTP/S";
    }
}
