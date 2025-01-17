//$Header: /as2/de/mendelson/util/systemevents/SystemEventManager.java 24    2/11/23 15:53 Heller $
package de.mendelson.util.systemevents;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.messages.LoginRequest;
import de.mendelson.util.clientserver.messages.LoginState;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Performs the notification for an event
 *
 * @author S.Heller
 * @version $Revision: 24 $
 */
public abstract class SystemEventManager {

    private final static DateFormat EVENT_FILE_DATE_FORMAT = new SimpleDateFormat("HH-mm-ss-SSS");
    private final static DateFormat DAILY_SUBDIR_FORMAT = new SimpleDateFormat("yyyyMMdd");
    /**
     * Always localize your output
     */
    private final MecResourceBundle rb;

    private String hostname = null;

    protected SystemEventManager() {
        //Load resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleSystemEventManager.class.getName());
        } //load up  resourcebundle        
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    public synchronized String getHostname() {
        if (this.hostname == null) {
            try {
                this.hostname = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                this.hostname = "Unknown";
            }
        }
        return (this.hostname);
    }

    public abstract Path getStorageMainDir();

    /**
     * Stores the system event to a file - to be browsed later
     */
    protected void storeEventToFile(SystemEvent event) throws Exception {
        Path storageDir = Paths.get(this.getStorageMainDir().toString(),
                DAILY_SUBDIR_FORMAT.format(new Date()),
                "events");
        String storageFilePrefix = EVENT_FILE_DATE_FORMAT.format(new Date())
                + "_" + event.severityToFilename()
                + "_" + event.originToFilename()
                + "_" + event.typeToFilename()
                + "_";
        String storageFileSuffix = ".event";
        event.store(storageDir, storageFilePrefix, storageFileSuffix);
    }

    /**
     * Throws a new system event that a login was successful
     *
     * @param tlsProtocol The used TLS protocol or null if this could not be
     * determined or the connection is unsecured
     * @param tlsProtocol The used TLS cipher suite or null if this could not be
     * determined or the connection is unsecured
     *
     */
    public void newEventClientLoginSuccess(LoginState loginState, SocketAddress remoteAddress, String sessionId,
            LoginRequest loginRequest, String tlsProtocol, String tlsCipherSuite) {
        SystemEvent event = new SystemEvent(SystemEvent.SEVERITY_INFO, SystemEvent.ORIGIN_USER,
                SystemEvent.TYPE_CLIENT_LOGIN_SUCCESS);
        StringBuilder builder = new StringBuilder();
        builder.append(this.rb.getResourceString("label.body.tlsprotocol",
                (tlsProtocol == null ? "--" : tlsProtocol))).append("\n");
        builder.append(this.rb.getResourceString("label.body.tlsciphersuite",
                (tlsCipherSuite == null ? "--" : tlsCipherSuite))).append("\n");
        builder.append(this.rb.getResourceString("label.body.clientip",
                remoteAddress.toString())).append("\n");
        builder.append(this.rb.getResourceString("label.body.processid",
                loginRequest.getPID())).append("\n");
        builder.append(this.rb.getResourceString("label.body.clientos",
                loginRequest.getClientOSName())).append("\n");
        builder.append(this.rb.getResourceString("label.body.details",
                loginState.getStateDetails())).append("\n");
        event.setBody(builder.toString());
        event.setSubject(this.rb.getResourceString("label.subject.login.success", 
                loginState.getUser().getName()));
        try {
            this.storeEventToFile(event);
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Throws a new system event that a login has failed
     */
    public void newEventClientLoginFailure(LoginState loginState, SocketAddress remoteAddress, String sessionId,
            LoginRequest loginRequest) {
        SystemEvent event = new SystemEvent(SystemEvent.SEVERITY_WARNING, SystemEvent.ORIGIN_USER,
                SystemEvent.TYPE_CLIENT_LOGIN_FAILURE);
        StringBuilder builder = new StringBuilder();
        builder.append(this.rb.getResourceString("label.body.clientip", 
                remoteAddress.toString())).append("\n");
        builder.append(this.rb.getResourceString("label.body.processid", 
                loginRequest.getPID())).append("\n");
        builder.append(this.rb.getResourceString("label.body.clientos", 
                loginRequest.getClientOSName())).append("\n");
        builder.append(this.rb.getResourceString("label.body.clientversion", 
                loginRequest.getClientId())).append("\n");
        builder.append(this.rb.getResourceString("label.body.details", 
                loginState.getStateDetails())).append("\n");
        event.setBody(builder.toString());
        event.setSubject(this.rb.getResourceString("label.subject.login.failed", 
                loginState.getUser().getName()));
        try {
            this.storeEventToFile(event);
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Throws a new system event that a client has disconnected
     */
    public void newEventClientLogoff(String remoteIP, String userName, String processId, String sessionId,
            String message) {
        SystemEvent event = new SystemEvent(SystemEvent.SEVERITY_INFO, SystemEvent.ORIGIN_USER,
                SystemEvent.TYPE_CLIENT_LOGOFF);
        StringBuilder builder = new StringBuilder();
        builder.append(this.rb.getResourceString("label.body.clientip", remoteIP)).append("\n");
        builder.append(this.rb.getResourceString("label.body.processid", processId)).append("\n");
        if (message != null && !message.trim().isEmpty()) {
            builder.append(this.rb.getResourceString("label.body.details", message)).append("\n");
        }
        event.setBody(builder.toString());
        event.setSubject(this.rb.getResourceString("label.subject.logoff", userName));
        try {
            this.storeEventToFile(event);
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Throws a new system event that a problem occurred in the client-server
     * interface
     */
    public void newEventExceptionInClientServerProcess(String remoteIP, String userName, String processId, String sessionId,
            String message) {
        SystemEvent event = new SystemEvent(SystemEvent.SEVERITY_ERROR, SystemEvent.ORIGIN_SYSTEM,
                SystemEvent.TYPE_CLIENT_ANY);
        StringBuilder builder = new StringBuilder();
        builder.append(this.rb.getResourceString("label.body.clientip", remoteIP)).append("\n");
        builder.append(this.rb.getResourceString("label.body.processid", processId)).append("\n\n");
        if (message != null && !message.trim().isEmpty()) {
            builder.append(this.rb.getResourceString("label.body.details", message)).append("\n");
        }
        event.setBody(builder.toString());
        event.setSubject(this.rb.getResourceString("label.error.clientserver"));
        try {
            this.storeEventToFile(event);
        } catch (Exception e) {
            return;
        }
    }

    public abstract void systemFailure(Throwable exception, int eventType, PreparedStatement statement);

    public abstract void systemFailure(Throwable exception, int eventType);

    public abstract void systemFailure(Throwable exception);

    public abstract void newEvent(SystemEvent event);

    public abstract void newEvent(int severity, int origin, int type, String subject, String body);

}
