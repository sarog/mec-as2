//$Header: /as2/de/mendelson/comm/as2/server/AS2Server.java 82    12/15/17 11:34a Heller $
package de.mendelson.comm.as2.server;

import de.mendelson.util.httpconfig.server.HTTPServerConfigInfo;
import de.mendelson.Copyright;
import de.mendelson.comm.as2.AS2ServerVersion;
import de.mendelson.comm.as2.AS2ShutdownThread;
import de.mendelson.comm.as2.cert.CertificateCEMController;
import de.mendelson.comm.as2.configurationcheck.ConfigurationCheckController;
import de.mendelson.comm.as2.database.DBDriverManager;
import de.mendelson.comm.as2.database.DBServer;
import de.mendelson.comm.as2.log.DBLoggingHandler;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.send.DirPollManager;
import de.mendelson.comm.as2.sendorder.SendOrderAccessDB;
import de.mendelson.comm.as2.sendorder.SendOrderReceiver;
import de.mendelson.comm.as2.timing.CertificateExpireController;
import de.mendelson.comm.as2.timing.FileDeleteController;
import de.mendelson.comm.as2.timing.MDNReceiptController;
import de.mendelson.comm.as2.timing.MessageDeleteController;
import de.mendelson.comm.as2.timing.StatisticDeleteController;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.ClientServer;
import de.mendelson.util.clientserver.log.ClientServerLoggingHandler;
import de.mendelson.util.clientserver.user.DefaultPermissionDescription;
import de.mendelson.util.log.DailySubdirFileLoggingHandler;
import de.mendelson.util.security.BCCryptoHelper;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreStorage;
import de.mendelson.util.security.cert.KeystoreStorageImplFile;
import java.io.File;
import java.io.FileWriter;
import java.net.BindException;
import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.xml.XmlConfiguration;

/**
 * Class to start the AS2 server
 *
 * @author S.Heller
 * @version $Revision: 82 $
 * @since build 68
 */
public class AS2Server extends AbstractAS2Server implements AS2ServerMBean {

    public static final String SERVER_LOGGER_NAME = "de.mendelson.as2.server";
    private static int transactionCounter = 0;
    private static long rawDataSent = 0;
    private static long rawDataReceived = 0;
    /**
     * Server start time in ms
     */
    long startTime = System.currentTimeMillis();
    private Logger logger = Logger.getLogger(SERVER_LOGGER_NAME);
    /**
     * Product preferences
     */
    private PreferencesAS2 preferences = new PreferencesAS2();
    /**
     * DB server that is used
     */
    private DBServer dbServer = null;
    /**
     * Localize the output
     */
    private MecResourceBundle rb = null;
    private DirPollManager pollManager = null;
    private ConfigurationCheckController configCheckController = null;
    private CertificateManager certificateManagerEncSign = null;
    private CertificateManager certificateManagerSSL = null;
    //DB connection
    private Connection configConnection = null;
    private Connection runtimeConnection = null;
    private ClientServer clientserver;
    private ClientServerSessionHandlerLocalhost clientServerSessionHandler = null;
    private boolean startHTTPServer = false;
    /**
     * Sets if all clients may connect to this server or only clients from the
     * servers host
     */
    private boolean allowAllClients = false;
    //the HTTP server, might be null if the AS2 server is started with the option -nohttpserver
    private Server server = null;
    private HTTPServerConfigInfo httpServerConfigInfo = null;

    /**
     * Creates a new AS2 server and starts it
     */
    public AS2Server(boolean startHTTPServer, boolean allowAllClients) throws Exception {
        //Load default resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleAS2Server.class.getName());
        } //load up  resourcebundle
        catch (MissingResourceException e) {
            throw new Exception("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        this.startHTTPServer = startHTTPServer;
        this.allowAllClients = allowAllClients;
        this.performStartupChecks();
        ServerStartupSequence startup = new ServerStartupSequence(this.logger);
        startup.performWork();
        this.checkLock();
        int clientServerCommPort = this.preferences.getInt(PreferencesAS2.CLIENTSERVER_COMM_PORT);
        this.clientserver = new ClientServer(logger, clientServerCommPort);
        this.clientserver.setProductName(AS2ServerVersion.getFullProductName());
        this.setupClientServerSessionHandler();
        this.start();
        //start the partner poll threads
        this.pollManager.partnerConfigurationChanged();
    }

    private void performStartupChecks() throws Exception {
        //check if all ports are available
        AS2ServerResourceCheck resourceCheck = new AS2ServerResourceCheck();
        resourceCheck.performPortCheck();
        resourceCheck.checkCPUCores(this.logger);
        resourceCheck.checkHeap(this.logger);
        BCCryptoHelper helper = new BCCryptoHelper();
        //check if the jurisdiction policy strength package has been installed
        boolean unlimitedStrengthInstalled = helper.performUnlimitedStrengthJurisdictionPolicyTest();
        if (!unlimitedStrengthInstalled) {
            this.logger.severe(this.rb.getResourceString("fatal.limited.strength"));
            System.exit(1);
        }
    }

    @Override
    public void start() throws Exception {
        try {
            this.logger.info(rb.getResourceString("server.willstart", AS2ServerVersion.getFullProductName()));
            this.logger.info(Copyright.getCopyrightMessage());
            this.ensureRunningDBServer();
            this.configConnection = DBDriverManager.getConnectionWithoutErrorHandling(DBDriverManager.DB_CONFIG, "localhost");
            this.runtimeConnection = DBDriverManager.getConnectionWithoutErrorHandling(DBDriverManager.DB_RUNTIME, "localhost");
            this.server = this.startHTTPServer();
            this.httpServerConfigInfo = HTTPServerConfigInfo.computeHTTPServerConfigInfo(this.server, this.startHTTPServer,
                    "/as2/HttpReceiver", "/as2/ServerState");
            this.certificateManagerEncSign = new CertificateManager(this.logger);
            KeystoreStorage signEncStorage = new KeystoreStorageImplFile(
                    this.preferences.get(PreferencesAS2.KEYSTORE),
                    this.preferences.get(PreferencesAS2.KEYSTORE_PASS).toCharArray(),
                    BCCryptoHelper.KEYSTORE_PKCS12);
            this.certificateManagerEncSign.loadKeystoreCertificates(signEncStorage);
            this.certificateManagerSSL = new CertificateManager(this.logger);
            KeystoreStorage sslStorage = new KeystoreStorageImplFile(
                    this.preferences.get(PreferencesAS2.KEYSTORE_HTTPS_SEND),
                    this.preferences.get(PreferencesAS2.KEYSTORE_HTTPS_SEND_PASS).toCharArray(),
                    BCCryptoHelper.KEYSTORE_JKS);
            this.certificateManagerSSL.loadKeystoreCertificates(sslStorage);
            this.startSendOrderReceiver();
            this.setupLogger();
            //start control threads
            MDNReceiptController receiptController = new MDNReceiptController(this.clientserver, this.configConnection, this.runtimeConnection);
            receiptController.startMDNCheck();
            MessageDeleteController logDeleteController = new MessageDeleteController(this.clientserver, this.configConnection, this.runtimeConnection);
            logDeleteController.startAutoDeleteControl();
            FileDeleteController fileDeleteController = new FileDeleteController(this.clientserver, this.configConnection, this.runtimeConnection);
            fileDeleteController.startAutoDeleteControl();
            StatisticDeleteController statsDeleteController = new StatisticDeleteController(this.configConnection, this.runtimeConnection);
            statsDeleteController.startAutoDeleteControl();
            this.pollManager = new DirPollManager(this.certificateManagerEncSign, this.configConnection,
                    this.runtimeConnection, this.clientserver);
            this.configCheckController = new ConfigurationCheckController(
                    this.certificateManagerEncSign,
                    this.certificateManagerSSL,
                    this.configConnection,
                    this.runtimeConnection);
            this.clientServerSessionHandler.addServerProcessing(
                    new AS2ServerProcessing(this.clientserver, this.pollManager,
                            this.certificateManagerEncSign, this.certificateManagerSSL, this.configConnection, this.runtimeConnection,
                            this.configCheckController, this.httpServerConfigInfo));
            CertificateExpireController expireController = new CertificateExpireController(this.certificateManagerEncSign,
                    this.certificateManagerSSL,
                    this.configConnection, this.runtimeConnection);
            expireController.startCertExpireControl();
            this.configCheckController.start();
            ModuleLockReleaseController lockReleaseController = new ModuleLockReleaseController(
                    this.configConnection, this.runtimeConnection);
            lockReleaseController.startLockReleaseControl();
            CertificateCEMController cemController = new CertificateCEMController(this.clientserver, this.configConnection, this.runtimeConnection, this.certificateManagerEncSign);
            Executors.newSingleThreadExecutor().submit(cemController);
            Runtime.getRuntime().addShutdownHook(new AS2ShutdownThread(this.dbServer));
            //listen for inbound client connects
            this.clientserver.start();
            this.logger.info(rb.getResourceString("server.started",
                    String.valueOf(System.currentTimeMillis() - this.startTime)));
            //display the startup message on the console
            System.out.println(rb.getResourceString("server.started",
                    String.valueOf(System.currentTimeMillis() - this.startTime)));
        } catch (BindException e) {
            //populate the bind exception with some more information for the user
            BindException bindException = new BindException(this.rb.getResourceString("bind.exception",
                    new Object[]{ e.getMessage(),
                        AS2ServerVersion.getProductName()
                    }));
            throw bindException;
        } catch (Exception e) {
            throw e;
        }
    }

    private void setupLogger() {
        this.logger.setUseParentHandlers(false);
        //send the log info to the attached clients of the client-server framework
        this.logger.addHandler(new ClientServerLoggingHandler(this.clientServerSessionHandler));
        this.logger.addHandler(new DBLoggingHandler());
        //add file logger that logs in a daily subdir
        this.logger.addHandler(new DailySubdirFileLoggingHandler(new File(this.preferences.get(PreferencesAS2.DIR_LOG)), "as2.log"));
        this.logger.setLevel(Level.ALL);
    }

    private void setupClientServerSessionHandler() {
        //set up session handler for incoming client requests
        this.clientServerSessionHandler = new ClientServerSessionHandlerLocalhost(this.logger,
                new String[]{AS2ServerVersion.getFullProductName()}, this.allowAllClients);
        this.clientServerSessionHandler.setAnonymousProcessing(new AnonymousProcessingAS2());
        this.clientserver.setSessionHandler(this.clientServerSessionHandler);
        this.clientServerSessionHandler.setProductName(AS2ServerVersion.getProductName());
        this.clientServerSessionHandler.setPermissionDescription(new DefaultPermissionDescription());
    }

    @Override
    public Logger getLogger() {
        return (this.logger);
    }

    /**
     * Checks for a lock to prevent starting the server several times on the
     * same machine
     *
     */
    private void checkLock() {
        //check if lock file exists, if it exists cancel!
        File lockFile = new File(AS2ServerVersion.getProductName().replace(' ', '_') + ".lock");
        if (lockFile.exists()) {
            DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            this.logger.severe(rb.getResourceString("server.already.running",
                    new Object[]{
                        lockFile.getAbsolutePath(),
                        format.format(new java.util.Date(lockFile.lastModified()))
                    }));
            throw new RuntimeException(rb.getResourceString("server.already.running",
                    new Object[]{
                        lockFile.getAbsolutePath(),
                        format.format(new java.util.Date(lockFile.lastModified()))
                    }));
        } else {
            try {
                FileWriter writer = new FileWriter(lockFile);
                writer.write("");
                writer.flush();
                writer.close();
            } catch (Exception e) {
                this.logger.severe("checkLock: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    private void startSendOrderReceiver() throws Exception {
        //reset the send order state of available send orders back to waiting
        SendOrderAccessDB sendOrderAccess = new SendOrderAccessDB(this.configConnection, this.runtimeConnection);
        sendOrderAccess.resetAllToWaiting();
        SendOrderReceiver receiver = new SendOrderReceiver(this.configConnection, this.runtimeConnection,
                this.clientserver);
        Executors.newSingleThreadExecutor().submit(receiver);
    }

    private Server startHTTPServer() throws Exception {
        //start the HTTP server if this is requested
        if (this.startHTTPServer) {
            //setup jetty base and home path
            System.setProperty("jetty.home", new File("./jetty9").getAbsolutePath());
            System.setProperty("jetty.base", new File("./jetty9").getAbsolutePath());
            //setup jetty logging
            System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StrErrLog");
            System.setProperty("org.eclipse.jetty.LEVEL", "INFO");
            System.setProperty("org.eclipse.jetty.websocket.LEVEL", "INFO");
            //org.eclipse.jetty.start.Main.main(new String[0]);
            URL jettyXMLConfigurationURL = new File(System.getProperty("jetty.home") + "/etc/jetty.xml").toURI().toURL();
            XmlConfiguration jettyXMLConfiguration = new XmlConfiguration(jettyXMLConfigurationURL);
            org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server();
            jettyXMLConfiguration.configure(server);
            server.start();
            return (server);
        } else {
            this.logger.info(rb.getResourceString("server.nohttp"));
        }
        return (null);
    }

    /**
     * Starts the database server
     */
    private void ensureRunningDBServer() throws Exception {
        //start the database server
        this.dbServer = new DBServer();
        this.dbServer.ensureServerIsRunning();
    }

    @Override
    public int getPort() {
        return (this.preferences.getInt(PreferencesAS2.CLIENTSERVER_COMM_PORT));
    }

    /**
     * MBean interface
     */
    @Override
    public long getUsedMemoryInBytes() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }

    /**
     * MBean interface
     */
    @Override
    public long getTotalMemoryInBytes() {
        return (Runtime.getRuntime().totalMemory());
    }

    /**
     * MBean interface
     */
    @Override
    public String getServerVersion() {
        return (AS2ServerVersion.getProductName() + " " + AS2ServerVersion.getVersion() + " " + AS2ServerVersion.getBuild());
    }

    /**
     * MBean interface
     */
    @Override
    public long getUptimeInMS() {
        return (System.currentTimeMillis() - this.startTime);
    }

    @Override
    public long getRawDataSentInBytesInUptime() {
        return (rawDataSent);
    }

    @Override
    public long getRawDataReceivedInBytesInUptime() {
        return (rawDataReceived);
    }

    @Override
    public long getTransactionCountInUptime() {
        return (transactionCounter);
    }

    public static synchronized void incTransactionCounter() {
        transactionCounter++;
    }

    public static synchronized void incRawSentData(long size) {
        rawDataSent += size;
    }

    public static synchronized void incRawReceivedData(long size) {
        rawDataReceived += size;
    }

    /**
     * Deletes the lock file
     */
    public static void deleteLockFile() {
        File lockFile = new File(AS2ServerVersion.getProductName().replace(' ', '_') + ".lock");
        lockFile.delete();
    }
}
