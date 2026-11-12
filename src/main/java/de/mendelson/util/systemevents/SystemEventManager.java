//$Header: /mec_as2/de/mendelson/util/systemevents/SystemEventManager.java 40    15/04/26 12:44 Heller $
package de.mendelson.util.systemevents;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.NamedThreadFactory;
import de.mendelson.util.clientserver.ClientType;
import de.mendelson.util.clientserver.messages.LoginRequest;
import de.mendelson.util.clientserver.messages.LoginState;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;


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
 * @version $Revision: 40 $
 */
public abstract class SystemEventManager {

    private static final DateTimeFormatter EVENT_FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("HH-mm-ss-SSS");
    private static final DateTimeFormatter DAILY_SUBDIR_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final MecResourceBundle rb;
    public static final String MODULE_NAME;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleSystemEventManager.class.getName());
            MODULE_NAME = rb.getResourceString("module.name");
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    private static final String HOST_NAME;

    static {
        String tempHostName;
        try {
            tempHostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            tempHostName = "Unknown";
        }
        HOST_NAME = tempHostName;
    }

    /**
     * Store calls are not executed async, this is the executor
     */
    private final ExecutorService storageQueueExecutor = Executors.newSingleThreadExecutor(
            new NamedThreadFactory("systemevent-write")
    );

    protected SystemEventManager() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                shutdown();
            }
        }, "systemevent-shutdown"));
    }

    public String getHostname() {
        return (HOST_NAME);
    }

    public abstract Path getStorageMainDir();

    /**
     * Stores the system event to a file - to be browsed later. This does not
     * immediately stores the event sync on the hard disk, this is enqueued and
     * performed async by an additional thread
     */
    protected void storeEventToFile(final SystemEvent event) throws Exception {
        if (this.storageQueueExecutor.isShutdown()) {
            //the executor is already shut down: write sync
            this.writeEventToFileSync(event);
        } else {
            Runnable eventWriter = new Runnable() {
                @Override
                public void run() {
                    try {
                        writeEventToFileSync(event);
                    } catch (Throwable e) {
                    }
                }
            };
            try {
                this.storageQueueExecutor.submit(eventWriter);
            } catch (RejectedExecutionException e) {
                //the executor is just shut down: write sync
                this.writeEventToFileSync(event);
            }
        }
    }

    private void writeEventToFileSync(SystemEvent event) throws Exception {
        Path storageDir = Paths.get(this.getStorageMainDir().toString(),
                LocalDateTime.now().format(DAILY_SUBDIR_FORMAT),
                "events");
        String storageFilePrefix
                = LocalDateTime.now().format(EVENT_FILE_DATE_FORMAT)
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
        SystemEvent event = new SystemEvent(SystemEvent.Severity.INFO, SystemEvent.Origin.USER,
                SystemEvent.Type.CLIENT_LOGIN_SUCCESS);
        StringBuilder builder = new StringBuilder();
        builder.append(rb.getResourceString("label.body.tlsprotocol",
                (tlsProtocol == null ? "--" : tlsProtocol))).append("\n")
                .append(rb.getResourceString("label.body.tlsciphersuite",
                        (tlsCipherSuite == null ? "--" : tlsCipherSuite))).append("\n")
                .append(rb.getResourceString("label.body.clientip",
                        remoteAddress.toString())).append("\n")
                .append(rb.getResourceString("label.body.processid",
                        loginRequest.getPid())).append("\n")
                .append(rb.getResourceString("label.body.sessionid",
                        sessionId)).append("\n")
                .append(rb.getResourceString("label.body.clientos",
                        loginRequest.getClientOSName())).append("\n")
                .append(rb.getResourceString("label.body.details",
                        loginState.getStateDetails())).append("\n");
        event.setBody(builder.toString());
        String subject = rb.getResourceString("label.subject.login.success",
                loginState.getUser().getName());
        if (loginRequest.getClientType() != ClientType.UNSPECIFIED) {
            subject = subject + " ("
                    + loginRequest.getClientType().getDisplayStr()
                    + ")";
        }
        event.setSubject(subject);
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
        SystemEvent event = new SystemEvent(SystemEvent.Severity.WARNING, SystemEvent.Origin.USER,
                SystemEvent.Type.CLIENT_LOGIN_FAILURE);
        StringBuilder builder = new StringBuilder();
        builder.append(rb.getResourceString("label.body.clientip",
                remoteAddress.toString())).append("\n")
                .append(rb.getResourceString("label.body.processid",
                        loginRequest.getPid())).append("\n")
                .append(rb.getResourceString("label.body.clientos",
                        loginRequest.getClientOSName())).append("\n")
                .append(rb.getResourceString("label.body.clientversion",
                        loginRequest.getClientId())).append("\n")
                .append(rb.getResourceString("label.body.details",
                        loginState.getStateDetails())).append("\n");
        event.setBody(builder.toString());
        String subject = rb.getResourceString("label.subject.login.failed",
                loginState.getUser().getName());
        if (loginRequest.getClientType() != ClientType.UNSPECIFIED) {
            subject = subject + " ("
                    + loginRequest.getClientType().getDisplayStr()
                    + ")";
        }
        event.setSubject(subject);
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
            String message, ClientType clientType) {
        SystemEvent event = new SystemEvent(SystemEvent.Severity.INFO, SystemEvent.Origin.USER,
                SystemEvent.Type.CLIENT_LOGOFF);
        StringBuilder builder = new StringBuilder();
        builder.append(rb.getResourceString("label.body.clientip", remoteIP)).append("\n");
        builder.append(rb.getResourceString("label.body.processid", processId)).append("\n");
        builder.append(rb.getResourceString("label.body.sessionid", sessionId)).append("\n");
        if (message != null && !message.trim().isEmpty()) {
            builder.append(rb.getResourceString("label.body.details", message)).append("\n");
        }
        event.setBody(builder.toString());
        String subject = rb.getResourceString("label.subject.logoff", userName);
        if (clientType != ClientType.UNSPECIFIED) {
            subject = subject + " ("
                    + clientType.getDisplayStr()
                    + ")";
        }
        event.setSubject(subject);
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
        SystemEvent event = new SystemEvent(SystemEvent.Severity.ERROR, SystemEvent.Origin.SYSTEM,
                SystemEvent.Type.CLIENT_ANY);
        StringBuilder builder = new StringBuilder();
        builder.append(rb.getResourceString("label.body.clientip", remoteIP)).append("\n")
                .append(rb.getResourceString("label.body.processid", processId)).append("\n\n");
        if (message != null && !message.trim().isEmpty()) {
            builder.append(rb.getResourceString("label.body.details", message)).append("\n");
        }
        event.setBody(builder.toString())
                .setSubject(rb.getResourceString("label.error.clientserver"));
        try {
            this.storeEventToFile(event);
        } catch (Exception e) {
            System.out.println("[Client-Server] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    /**
     * A problem occurred during a directory creation process
     */
    public void newEventExceptionInDirectoryCreation(Throwable exception, String directory) {
        SystemEvent event = new SystemEvent(SystemEvent.Severity.ERROR, SystemEvent.Origin.SYSTEM,
                SystemEvent.Type.FILE_MKDIR);
        event.setSubject(rb.getResourceString("error.createdir.subject"))
                .setBody(rb.getResourceString("error.createdir.body",
                        new Object[]{
                            directory,
                            "[" + exception.getClass().getSimpleName() + "] " + exception.getMessage()}
                ));
        this.newEvent(event);
    }

    public abstract void systemFailure(Throwable exception, 
            SystemEvent.Type eventType, PreparedStatement statement);

    public abstract void systemFailure(Throwable exception, 
            SystemEvent.Type eventType);

    public abstract void systemFailure(Throwable exception);

    public abstract void newEvent(SystemEvent event);

    public abstract void newEvent(SystemEvent.Severity severity, 
            SystemEvent.Origin origin, 
            SystemEvent.Type type, String subject, String body);

    /**
     * Has to be called in the system shutdown routine
     */
    public void shutdown() {
        this.storageQueueExecutor.shutdown();
        try {
            if (!this.storageQueueExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
                this.storageQueueExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            this.storageQueueExecutor.shutdownNow();
        }
    }

}
