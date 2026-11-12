//$Header: /as2/de/mendelson/comm/as2/send/ConnectionManagerSendTime.java 2     7/05/25 16:24 Heller $
package de.mendelson.comm.as2.send;

import de.mendelson.comm.as2.message.AS2Info;
import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.comm.as2.message.MessageAccessDB;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.protocol.HttpContext;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.net.ssl.SSLSession;
import org.apache.http.config.Registry;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Connection Manager to update the send time
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class ConnectionManagerSendTime extends BasicHttpClientConnectionManager {

    private final IDBDriverManager dbDriverManager;
    private final Logger logger;
    private final AS2Info as2Info;
    private HttpClientConnection connection = null;
    private boolean mdnWaitTimeExpired = false;
    private static final DateTimeFormatter RECEIPT_SIGNAL_WAIT_END_TIME_FORMATTER
            = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);

    private static final MecResourceBundle rb;
    private HttpRoute route = null;
    private long connectionStartTime = 0;

    static {
        //Load default resourcebundle
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleHttpUploader.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    public ConnectionManagerSendTime(Logger logger,
            IDBDriverManager dbDriverManager,
            AS2Info as2Info,
            Registry<ConnectionSocketFactory> registry) {
        super(registry);
        this.dbDriverManager = dbDriverManager;
        this.logger = logger;
        this.as2Info = as2Info;
    }

    @Override
    public void connect(HttpClientConnection connection,
            HttpRoute route, int connectTimeout, HttpContext context) throws IOException {
        super.connect(connection, route, connectTimeout, context);
        if (this.dbDriverManager != null && !this.as2Info.isMDN()) {
            MessageAccessDB messageAccess = new MessageAccessDB(this.dbDriverManager);
            messageAccess.setMessageSendDate((AS2MessageInfo) this.as2Info);
            MessageHttpUploader.addConnectionManager(this.as2Info.getMessageId(), this);
            this.route = route;
            this.connection = connection;
        }
        if (this.logger != null) {
            if (connection instanceof ManagedHttpClientConnection) {
                ManagedHttpClientConnection managedConnection = (ManagedHttpClientConnection) connection;
                SSLSession sslSession = managedConnection.getSSLSession();
                if (sslSession != null) {
                    this.logger.log(Level.INFO,
                            rb.getResourceString("connection.tls.info",
                                    new Object[]{
                                        sslSession.getProtocol(),
                                        sslSession.getCipherSuite()
                                    }),
                            this.as2Info);

                }
            }
        }
        this.connectionStartTime = System.currentTimeMillis();
        if (this.logger != null) {
            if (!this.as2Info.isMDN()) {
                PreferencesAS2 preferences;
                if (this.dbDriverManager != null) {
                    preferences = new PreferencesAS2(this.dbDriverManager);
                } else {
                    preferences = new PreferencesAS2();
                }
                long waitTimeInMs = TimeUnit.MINUTES.toMillis(preferences.getInt(PreferencesAS2.MDN_WAIT_TIME));
                LocalTime time = Instant.ofEpochMilli(System.currentTimeMillis() + waitTimeInMs)
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime();
                this.logger.log(Level.INFO, rb.getResourceString("connected.to",
                        new Object[]{
                            route.getTargetHost(),
                            RECEIPT_SIGNAL_WAIT_END_TIME_FORMATTER.format(time)
                        }),
                        this.as2Info);
            }
        }
    }

    @Override
    public synchronized void shutdown() {
        if (this.connection != null && this.connection.isOpen()) {
            try {
                this.connection.close();
                this.mdnWaitTimeExpired = true;
                if (this.logger != null) {
                    float connectionOpenTimeInS = (System.currentTimeMillis() - this.connectionStartTime) / 1000;
                    String target = "unknown";
                    if (this.route != null && this.route.getTargetHost() != null
                            && this.route.getTargetHost().getHostName() != null) {
                        target = this.route.getTargetHost().getHostName()
                                + ":" + this.route.getTargetHost().getPort();
                    }
                    this.logger.log(Level.INFO, rb.getResourceString("connection.shut.down",
                            new Object[]{
                                target,
                                String.format("%.1f", connectionOpenTimeInS)
                            }),
                            this.as2Info);
                }
            } catch (Exception e) {
                SystemEventManagerImplAS2.instance().systemFailure(e);
            }
        }
        this.connection = null;
    }

    /**
     * It should be possible from outside to find out if a socket closed
     * execption in the upload procedure has its reason in a manual close of the
     * connection or not. The connection is closed manual if the receipt signal
     * expired - and the uploader should know if this is the reason
     *
     * @return the signalWaitTimeExpired
     */
    public boolean isMDNWaitTimeExpired() {
        return (this.mdnWaitTimeExpired);
    }

}
