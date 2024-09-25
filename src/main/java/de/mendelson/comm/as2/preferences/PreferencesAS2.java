//$Header: /as2/de/mendelson/comm/as2/preferences/PreferencesAS2.java 89    7/11/23 15:34 Heller $
package de.mendelson.comm.as2.preferences;

import de.mendelson.util.preferences.PreferencesCache;
import de.mendelson.comm.as2.AS2ServerVersion;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.comm.as2.server.ServerInstance;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Class to manage the preferences of the AS2 server
 *
 * @author S.Heller
 * @version $Revision: 89 $
 */
public class PreferencesAS2 {

    private final static MecResourceBundle rb;

    static {
        //load resource bundle
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundlePreferencesAS2.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    private final static PreferencesCache SERVERSIDE_PREFERENCES_CACHE = new PreferencesCache(TimeUnit.SECONDS.toMillis(5));

    /**
     * Position of the client frame X
     */
    public static final String FRAME_X = "frameguix";
    /**
     * Position of the client frame Y
     */
    public static final String FRAME_Y = "frameguiy";
    /**
     * Position of the client frame height
     */
    public static final String FRAME_HEIGHT = "frameguiheight";
    /**
     * Position of the IDE frame WIDTH
     */
    public static final String FRAME_WIDTH = "frameguiwidth";
    /**
     * Language to use for the software localization
     */
    public static final String LANGUAGE = "language";
    public static final String COUNTRY = "country";
    /**
     * Directory the message parts are stored in
     */
    public static final String DIR_MSG = "dirmsg";
    public static final String ASYNC_MDN_TIMEOUT = "asyncmdntimeout";
    public static final String AUTH_PROXY_USER = "proxyuser";
    public static final String AUTH_PROXY_PASS = "proxypass";
    public static final String AUTH_PROXY_USE = "proxyuseauth";
    public static final String AUTO_MSG_DELETE = "automsgdelete";
    public static final String AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S = "automsgdeleteolderthanmults";
    public static final String AUTO_MSG_DELETE_OLDERTHAN = "automsgdeleteolderthan";
    public static final String AUTO_MSG_DELETE_LOG = "automsgdeletelog";
    public static final String AUTO_STATS_DELETE = "autostatsdelete";
    public static final String AUTO_STATS_DELETE_OLDERTHAN = "autostatsdeleteolderthan";
    public static final String AUTO_LOGDIR_DELETE = "autologdirdelete";
    public static final String AUTO_LOGDIR_DELETE_OLDERTHAN = "autologdirdeleteolderthan";
    public static final String LOG_POLL_PROCESS = "logpollprocess";
    public static final String PROXY_HOST = "proxyhost";
    public static final String PROXY_PORT = "proxyport";
    public static final String PROXY_USE = "proxyuse";
    public static final String RECEIPT_PARTNER_SUBDIR = "receiptpartnersubdir";
    public static final String HTTP_SEND_TIMEOUT = "httpsendtimeout";
    public static final String SHOW_QUOTA_NOTIFICATION_IN_PARTNER_CONFIG = "showquotaconf";
    public static final String SHOW_HTTPHEADER_IN_PARTNER_CONFIG = "showhttpheaderconf";
    public static final String CEM = "cem";
    public static final String COMMUNITY_EDITION = "commed";
    public static final String WRITE_OUTBOUND_STATUS_FILE = "outboundstatusfile";
    public static final String MAX_CONNECTION_RETRY_COUNT = "retrycount";
    public static final String MAX_OUTBOUND_CONNECTIONS = "maxoutboundconnections";
    public static final String CONNECTION_RETRY_WAIT_TIME_IN_S = "retrywaittime";
    public static final String DATASHEET_RECEIPT_URL = "datasheetreceipturl";
    public static final String HIDDENCOLSDEFAULT = "hiddencolsdefault";
    public static final String HIDDENCOLS = "hiddencols";
    public static final String HIDEABLECOLS = "hideablecols";
    public static final String COLOR_BLINDNESS = "colorblindness";
    public static final String LAST_UPDATE_CHECK = "lastupdatecheck";
    public static final String DISPLAY_MODE_CLIENT = "displaymodeclient";
    public static final String TLS_TRUST_ALL_REMOTE_SERVER_CERTIFICATES = "trustallservercerts";
    public static final String TLS_STRICT_HOST_CHECK = "stricthostcheck";
    public static final String EMBEDDED_HTTP_SERVER_STARTED = "embeddedhttpserverstarted";
    public static final String HTTP_LISTEN_PORT = "jetty.http.port";
    public static final String HTTPS_LISTEN_PORT = "jetty.ssl.port";
    public static final String EMBEDDED_HTTP_SERVER_SETTINGS_ACCESSIBLE = "embeddedhttpserversettingsaccessible";
    public static final String MAX_INBOUND_CONNECTIONS = "jetty.connectionlimit.maxConnections";
    public static final String NOTIFICATION_SMTP_TIMEOUT = "notificationsmtptimeout";
    public static final String NOTIFICATION_SMTP_CONNECTION_TIMEOUT = "notificationsmtpconnectiontimeout";
    public static final String SHOW_OVERWRITE_LOCALSTATION_SECURITY_IN_PARTNER_CONFIG = "showoverwritelocalstationsecurity";

    private IDBDriverManager dbDriverManager = null;

    /**
     * Server side properties are stored in the database - client side
     * properties are stored in the java preferences
     */
    private final List<String> SERVER_SIDE_PROPERTIES
            = Arrays.asList(
                    new String[]{
                        DIR_MSG,
                        ASYNC_MDN_TIMEOUT,
                        AUTH_PROXY_USER,
                        AUTH_PROXY_PASS,
                        AUTH_PROXY_USE,
                        AUTO_MSG_DELETE,
                        AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S,
                        AUTO_MSG_DELETE_OLDERTHAN,
                        AUTO_MSG_DELETE_LOG,
                        AUTO_STATS_DELETE,
                        AUTO_STATS_DELETE_OLDERTHAN,
                        AUTO_LOGDIR_DELETE,
                        AUTO_LOGDIR_DELETE_OLDERTHAN,
                        LOG_POLL_PROCESS,
                        PROXY_HOST,
                        PROXY_PORT,
                        PROXY_USE,
                        RECEIPT_PARTNER_SUBDIR,
                        HTTP_SEND_TIMEOUT,
                        CEM,
                        WRITE_OUTBOUND_STATUS_FILE,
                        MAX_CONNECTION_RETRY_COUNT,
                        MAX_OUTBOUND_CONNECTIONS,
                        CONNECTION_RETRY_WAIT_TIME_IN_S,
                        TLS_TRUST_ALL_REMOTE_SERVER_CERTIFICATES,
                        TLS_STRICT_HOST_CHECK
                    });

    /**
     * These properties are constant. You could try to change them but they will
     * always return the default value
     */
    private final List<String> CONSTANT_PROPERTIES
            = Arrays.asList(
                    new String[]{                        
                        EMBEDDED_HTTP_SERVER_STARTED,
                        NOTIFICATION_SMTP_CONNECTION_TIMEOUT,
                        NOTIFICATION_SMTP_TIMEOUT
                    });

    /**
     * These properties are stored in the embedded jetty properties file
     */
    private final List<String> JETTY_PROPERTIES = Arrays.asList(
            new String[]{
                HTTP_LISTEN_PORT,
                HTTPS_LISTEN_PORT,
                MAX_INBOUND_CONNECTIONS
            });

    /**
     * Initialize the preferences
     */
    public PreferencesAS2() {
    }

    /**
     * Initialize the preferences
     */
    public PreferencesAS2(IDBDriverManager dbDriverManager) {
        this.dbDriverManager = dbDriverManager;
    }

    /**
     * Returns the localized preference
     */
    public static String getLocalizedName(final String KEY) {
        return (rb.getResourceString(KEY));
    }

    /**
     * Returns the default value for the key
     *
     * @param KEY key to store properties with in the preferences
     */
    public static String getDefaultValue(final String KEY) {
        if (KEY.equals(FRAME_X)) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension dialogSize = new Dimension(
                    Integer.valueOf(getDefaultValue(FRAME_WIDTH)).intValue(),
                    Integer.valueOf(getDefaultValue(FRAME_HEIGHT)).intValue());
            return (String.valueOf((screenSize.width - dialogSize.width) / 2));
        }
        if (KEY.equals(FRAME_Y)) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension dialogSize = new Dimension(
                    Integer.valueOf(getDefaultValue(FRAME_WIDTH)).intValue(),
                    Integer.valueOf(getDefaultValue(FRAME_HEIGHT)).intValue());
            return (String.valueOf((screenSize.height - dialogSize.height) / 2));
        }
        if (KEY.equals(FRAME_WIDTH)) {
            return ("800");
        }
        if (KEY.equals(FRAME_HEIGHT)) {
            return ("600");
        }
        //language used for the localization
        if (KEY.equals(LANGUAGE)) {
            if (Locale.getDefault().equals(Locale.GERMANY)) {
                return ("de");
            }
            //default is always english
            return ("en");
        }
        //country used for the localization
        if (KEY.equals(COUNTRY)) {
            return (Locale.getDefault().getCountry());
        }
        //message part directory
        if (KEY.equals(DIR_MSG)) {
            return (new File(System.getProperty("user.dir")).getAbsolutePath() + FileSystems.getDefault().getSeparator() + "messages");
        }        
        if (KEY.equals(AUTH_PROXY_PASS)) {
            return ("mypass");
        }
        if (KEY.equals(AUTH_PROXY_USER)) {
            return ("myuser");
        }
        if (KEY.equals(AUTH_PROXY_USE)) {
            return ("FALSE");
        }
        //30 minutes
        if (KEY.equals(ASYNC_MDN_TIMEOUT)) {
            return ("30");
        }
        if (KEY.equals(AUTO_MSG_DELETE)) {
            return ("TRUE");
        }
        if (KEY.equals(AUTO_MSG_DELETE_LOG)) {
            return ("TRUE");
        }
        if (KEY.equals(AUTO_MSG_DELETE_OLDERTHAN)) {
            return ("5");
        }
        if (KEY.equals(AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S)) {
            return (String.valueOf(TimeUnit.DAYS.toSeconds(1)));
        }
        if (KEY.equals(AUTO_STATS_DELETE)) {
            return ("TRUE");
        }
        //delete stats older than 180 days
        if (KEY.equals(AUTO_STATS_DELETE_OLDERTHAN)) {
            return ("180");
        }
        //delete the log directories that contain all information about old transactions
        if (KEY.equals(AUTO_LOGDIR_DELETE)) {
            return ("FALSE");
        }
        if (KEY.equals(AUTO_LOGDIR_DELETE_OLDERTHAN)) {
            return ("180");
        }
        if (KEY.equals(PROXY_HOST)) {
            return ("127.0.0.1");
        }
        if (KEY.equals(PROXY_PORT)) {
            return ("8131");
        }
        if (KEY.equals(PROXY_USE)) {
            return ("FALSE");
        }
        if (KEY.equals(RECEIPT_PARTNER_SUBDIR)) {
            return ("FALSE");
        }
        if (KEY.equals(HTTP_SEND_TIMEOUT)) {
            return ("5000");
        }
        if (KEY.equals(SHOW_HTTPHEADER_IN_PARTNER_CONFIG)) {
            return ("FALSE");
        }
        if (KEY.equals(SHOW_QUOTA_NOTIFICATION_IN_PARTNER_CONFIG)) {
            return ("FALSE");
        }
        //disable CEM by default
        if (KEY.equals(CEM)) {
            return ("FALSE");
        }
        if (KEY.equals(COMMUNITY_EDITION)) {
            return (ServerInstance.ID.equals(ServerInstance.ID_COMMUNITY_EDITION) ? "TRUE" : "FALSE");
        }
        if (KEY.equals(LAST_UPDATE_CHECK)) {
            return ("0");
        }
        if (KEY.equals(WRITE_OUTBOUND_STATUS_FILE)) {
            return ("FALSE");
        }
        if (KEY.equals(MAX_CONNECTION_RETRY_COUNT)) {
            return ("10");
        }
        if (KEY.equals(CONNECTION_RETRY_WAIT_TIME_IN_S)) {
            return ("30");
        }
        if (KEY.equals(DATASHEET_RECEIPT_URL)) {
            return ("http://testas2.mendelson-e-c.com:8080/as2/HttpReceiver");
        }
        if (KEY.equals(HIDDENCOLSDEFAULT)) {
            return ("1111111111100");
        }
        if (KEY.equals(HIDDENCOLS)) {
            return ("1111111111000");
        }
        if (KEY.equals(HIDEABLECOLS)) {
            return ("0011111111111");
        }
        if (KEY.equals(LOG_POLL_PROCESS)) {
            return ("FALSE");
        }
        if (KEY.equals(MAX_OUTBOUND_CONNECTIONS)) {
            return ("9999");
        }
        if (KEY.equals(COLOR_BLINDNESS)) {
            return ("FALSE");
        }
        if (KEY.equals(DISPLAY_MODE_CLIENT)) {
            return ("LIGHT");
        }
        if (KEY.equals(TLS_TRUST_ALL_REMOTE_SERVER_CERTIFICATES)) {
            return ("FALSE");
        }
        if (KEY.equals(TLS_STRICT_HOST_CHECK)) {
            return ("FALSE");
        }
        if (KEY.equals(EMBEDDED_HTTP_SERVER_STARTED)) {
            return (System.getProperty("mendelson.as2.embeddedhttpserver", "TRUE"));
        }
        if (KEY.equals(HTTPS_LISTEN_PORT)) {
            return ("8443");
        }
        if (KEY.equals(HTTP_LISTEN_PORT)) {
            return ("8080");
        }
        if (KEY.equals(EMBEDDED_HTTP_SERVER_SETTINGS_ACCESSIBLE)) {
            JettyConfigfileHandler handler = JettyConfigfileHandler.instance();
            return (handler.configFileAccessible() ? "TRUE" : "FALSE");
        }
        if (KEY.equals(MAX_INBOUND_CONNECTIONS)) {
            return ("1000");
        }
        if( KEY.equals(NOTIFICATION_SMTP_CONNECTION_TIMEOUT)){
            return( String.valueOf(TimeUnit.SECONDS.toMillis(15)));
        }
        if( KEY.equals(NOTIFICATION_SMTP_TIMEOUT)){
            return( String.valueOf(TimeUnit.SECONDS.toMillis(15)));
        }
        if( KEY.equals(SHOW_OVERWRITE_LOCALSTATION_SECURITY_IN_PARTNER_CONFIG)){
            return( "FALSE");
        }
        throw new IllegalArgumentException("No defaults defined for prefs key " + KEY + " in " + PreferencesAS2.class.getName());
    }

    /**
     * Resets all preferences to the default value if the key is a server side stored key
     *
     * @param key
     */
    public void resetAllServerValuesToDefaultValue(Logger logger) {
        for( String key:this.SERVER_SIDE_PROPERTIES){
            this.resetToDefaultValue(logger, key);
        }
    } 
    
    /**
     * Deletes the passed key from the user defined settings - this will result
     * in reading the default value the next time it is requested
     *
     */
    public void resetToDefaultValue(Logger logger, final String KEY) {
        boolean resetPerformed = false;
        if (isServerSideProperty(KEY)) {
            PreparedStatement statement = null;
            if (this.dbDriverManager == null) {
                this.setDBDriverManagerByPluginCheck();
            }
            Connection configConnection = null;
            try {
                configConnection = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG);
                statement = configConnection.prepareStatement("DELETE FROM serversettings WHERE vkey=?");
                statement.setString(1, KEY);
                int rows = statement.executeUpdate();
                resetPerformed = rows == 1;
                if (resetPerformed) {
                    SERVERSIDE_PREFERENCES_CACHE.remove(KEY);
                }
            } catch (Exception e) {
                SystemEventManagerImplAS2.instance().systemFailure(e);
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Exception e) {
                        //nop                       
                    }
                }
                if (configConnection != null) {
                    try {
                        configConnection.close();
                    } catch (Exception e) {
                        //nop                       
                    }
                }
            }
        } else {
            Preferences preferences = Preferences.userNodeForPackage(AS2ServerVersion.class);
            preferences.remove(KEY);
        }
        if (resetPerformed) {
            String moduleName = rb.getResourceString("module.name");
            String localizedKey = rb.getResourceString(KEY);
            logger.log(Level.WARNING, moduleName + " " + rb.getResourceString("setting.reset", localizedKey));
        }
    }

    /**
     * Returns a single string value from the preferences or the default if it
     * is not found
     *
     */
    public String get(final String KEY) {
        String value = this.readSetting(KEY);
        if (value == null) {
            value = getDefaultValue(KEY);
            if (this.isServerSideProperty(KEY)) {
                SERVERSIDE_PREFERENCES_CACHE.put(KEY, value);
            }
            return (value);
        } else {
            return (value);
        }
    }

    /**
     * Stores a value in the preferences. If the passed value is null or an
     * empty string the key-value pair will be deleted from the storage.
     *
     * @param KEY Key as defined in this class
     * @param value value to set
     */
    public void put(final String KEY, String value) {
        if (value == null || value.isEmpty()) {
            this.deleteSetting(KEY);
        } else {
            this.writeSetting(KEY, value);
        }
    }

    /**
     * Puts a value to the preferences and stores the prefs
     *
     * @param KEY Key as defined in this class
     * @param value value to set
     */
    public void putInt(final String KEY, int value) {
        this.writeSetting(KEY, String.valueOf(value));
    }

    /**
     * Returns the value for the asked key, if none is defined it returns the
     * default value
     */
    public int getInt(final String KEY) {
        String value = this.readSetting(KEY);
        if (value == null) {
            value = getDefaultValue(KEY);
            if (this.isServerSideProperty(KEY)) {
                SERVERSIDE_PREFERENCES_CACHE.put(KEY, value);
            }
        }
        return (Integer.valueOf(value).intValue());
    }

    /**
     * Puts a value to the preferences and stores the setting
     *
     * @param KEY Key as defined in this class
     * @param value value to set
     */
    public void putBoolean(final String KEY, boolean value) {
        this.writeSetting(KEY, value ? "TRUE" : "FALSE");
    }

    /**
     * Returns the value for the asked key, if non is defined it returns the
     * default value
     */
    public boolean getBoolean(final String KEY) {
        String value = this.readSetting(KEY);
        if (value == null) {
            value = getDefaultValue(KEY);
            if (this.isServerSideProperty(KEY)) {
                SERVERSIDE_PREFERENCES_CACHE.put(KEY, value);
            }
        }
        return (Boolean.valueOf(value).booleanValue());
    }

    /**
     * Returns the value for the asked key, if noen is defined it returns the
     * second parameters value
     */
    public boolean getBoolean(final String KEY, boolean defaultValue) {
        String value = this.readSetting(KEY);
        if (value == null) {
            value = String.valueOf(defaultValue);
            if (this.isServerSideProperty(KEY)) {
                SERVERSIDE_PREFERENCES_CACHE.put(KEY, value);
            }
        }
        return (Boolean.valueOf(value).booleanValue());
    }

    /**
     * Indicates if this is a client- or a server setting and defines hereby the
     * storage place (db or preferences)
     *
     * @param KEY
     * @return
     */
    private boolean isServerSideProperty(String KEY) {
        return (SERVER_SIDE_PROPERTIES.contains(KEY));
    }

    /**
     * Indicates if this is a constant property. In this case it is not possible
     * to change the default value
     *
     * @param KEY
     * @return
     */
    private boolean isConstantProperty(String KEY) {
        return (CONSTANT_PROPERTIES.contains(KEY));
    }

    /**
     * Indicates if this is a property that is stored in the jetty config file
     *
     * @param KEY
     * @return
     */
    private boolean isJettyProperty(String KEY) {
        return (JETTY_PROPERTIES.contains(KEY));
    }

    /**
     * Will read a setting from the storage and return null if there is no
     * storage entry - then the default value should be returned
     *
     * @param KEY
     * @return
     */
    private String readSetting(String KEY) {
        if (isConstantProperty(KEY)) {
            return (getDefaultValue(KEY));
        }
        if (isJettyProperty(KEY)) {
            JettyConfigfileHandler handler = JettyConfigfileHandler.instance();
            return (handler.getValue(KEY, getDefaultValue(KEY)));
        }
        if (isServerSideProperty(KEY)) {
            String cachedValue = SERVERSIDE_PREFERENCES_CACHE.get(KEY);
            if (cachedValue != null) {
                return (cachedValue);
            } else {
                PreparedStatement statement = null;
                if (this.dbDriverManager == null) {
                    this.setDBDriverManagerByPluginCheck();
                }
                Connection configConnection = null;
                ResultSet result = null;
                try {
                    configConnection = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG);
                    statement = configConnection.prepareStatement("SELECT vvalue FROM serversettings WHERE vkey=?");
                    statement.setString(1, KEY);
                    result = statement.executeQuery();
                    if (result.next()) {
                        String value = result.getString("vvalue");
                        SERVERSIDE_PREFERENCES_CACHE.put(KEY, value);
                        return (value);
                    }
                } catch (Exception e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e);
                } finally {
                    if (result != null) {
                        try {
                            result.close();
                        } catch (Exception e) {
                            //nop                       
                        }
                    }
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (Exception e) {
                            //nop                       
                        }
                    }
                    if (configConnection != null) {
                        try {
                            configConnection.close();
                        } catch (Exception e) {
                            //nop                       
                        }
                    }
                }
            }
            return (null);
        } else {
            Preferences preferences = Preferences.userNodeForPackage(AS2ServerVersion.class);
            return (preferences.get(KEY, null));
        }
    }

    /**
     * Will write a setting to the storage
     *
     * @param KEY
     * @return
     */
    private void writeSetting(String KEY, String value) {
        if (isJettyProperty(KEY)) {
            //write to jetty config file if this is a jetty config setting
            JettyConfigfileHandler handler = JettyConfigfileHandler.instance();
            handler.setValue(KEY, value);
        } else if (!isConstantProperty(KEY)) {
            if (isServerSideProperty(KEY)) {
                Statement statementTransaction = null;
                PreparedStatement statementUpdate = null;
                PreparedStatement statementInsert = null;
                if (this.dbDriverManager == null) {
                    this.setDBDriverManagerByPluginCheck();
                }
                Connection configConnectionNoAutoCommit = null;
                try {
                    configConnectionNoAutoCommit = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG);
                    configConnectionNoAutoCommit.setAutoCommit(false);
                    String transactionName = "PreferencesAS2_writeSetting";
                    statementTransaction = configConnectionNoAutoCommit.createStatement();
                    dbDriverManager.startTransaction(statementTransaction, transactionName);
                    dbDriverManager.setTableLockINSERTAndUPDATE(statementTransaction,
                            new String[]{"serversettings"});
                    //try to update existing row
                    statementUpdate = configConnectionNoAutoCommit.prepareStatement("UPDATE serversettings SET vvalue=? WHERE vkey=?");
                    statementUpdate.setString(1, value);
                    statementUpdate.setString(2, KEY);
                    int updatedRows = statementUpdate.executeUpdate();
                    if (updatedRows == 0) {
                        //nothing updated - this was a new entry
                        statementInsert = configConnectionNoAutoCommit.prepareStatement("INSERT INTO serversettings(vkey,vvalue)VALUES(?,?)");
                        statementInsert.setString(1, KEY);
                        statementInsert.setString(2, value);
                        statementInsert.executeUpdate();
                    }
                    this.dbDriverManager.commitTransaction(statementTransaction, transactionName);
                    SERVERSIDE_PREFERENCES_CACHE.put(KEY, value);
                } catch (Exception e) {
                    try {
                        this.dbDriverManager.rollbackTransaction(statementTransaction);
                    } catch (Exception ex) {
                        SystemEventManagerImplAS2.instance().systemFailure(ex, SystemEvent.TYPE_DATABASE_ANY);
                    }
                    SystemEventManagerImplAS2.instance().systemFailure(e);
                } finally {
                    if (statementUpdate != null) {
                        try {
                            statementUpdate.close();
                        } catch (Exception e) {
                            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                        }
                    }
                    if (statementInsert != null) {
                        try {
                            statementInsert.close();
                        } catch (Exception e) {
                            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                        }
                    }
                    if (statementTransaction != null) {
                        try {
                            statementTransaction.close();
                        } catch (Exception e) {
                            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                        }
                    }
                    if (configConnectionNoAutoCommit != null) {
                        try {
                            configConnectionNoAutoCommit.close();
                        } catch (Exception e) {
                            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                        }
                    }
                }
            } else {
                //its a client value - just write it to the preferences
                Preferences preferences = Preferences.userNodeForPackage(AS2ServerVersion.class);
                preferences.put(KEY, value);
                try {
                    preferences.flush();
                } catch (BackingStoreException ignore) {
                }
            }
        }
    }

    private synchronized void setDBDriverManagerByPluginCheck() {
        if (this.dbDriverManager == null) {
            this.dbDriverManager = AS2Server.getActivatedDBDriverManager();
        }
    }

    /**
     * Will delete a setting in the storage
     *
     * @param KEY
     * @return
     */
    private void deleteSetting(String KEY) {
        if (isServerSideProperty(KEY)) {
            PreparedStatement statementDelete = null;
            Statement statementTransactionControl = null;
            if (this.dbDriverManager == null) {
                this.setDBDriverManagerByPluginCheck();
            }
            Connection configConnection = null;
            try {
                configConnection = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG);
                configConnection.setAutoCommit(false);
                String transactionName = "PreferencesAS2_deleteSetting";
                statementTransactionControl = configConnection.createStatement();
                this.dbDriverManager.startTransaction(statementTransactionControl, transactionName);
                this.dbDriverManager.setTableLockDELETE(statementTransactionControl,
                        new String[]{"serversettings"});
                statementDelete = configConnection.prepareStatement("DELETE FROM serversettings WHERE vkey=?");
                statementDelete.setString(1, KEY);
                statementDelete.executeUpdate();
                this.dbDriverManager.commitTransaction(statementTransactionControl, transactionName);
                SERVERSIDE_PREFERENCES_CACHE.remove(KEY);
            } catch (Exception e) {
                try {
                    this.dbDriverManager.rollbackTransaction(statementTransactionControl);
                } catch (Exception ex) {
                    SystemEventManagerImplAS2.instance().systemFailure(ex);
                }
                SystemEventManagerImplAS2.instance().systemFailure(e);
            } finally {
                if (statementDelete != null) {
                    try {
                        statementDelete.close();
                    } catch (Exception e) {
                        //nop                       
                    }
                }
                if (statementTransactionControl != null) {
                    try {
                        statementTransactionControl.close();
                    } catch (Exception e) {
                        //nop                       
                    }
                }
                if (configConnection != null) {
                    try {
                        configConnection.close();
                    } catch (Exception e) {
                        //nop                       
                    }
                }
            }
        } else {
            Preferences preferences = Preferences.userNodeForPackage(AS2ServerVersion.class);
            preferences.remove(KEY);
            try {
                preferences.flush();
            } catch (BackingStoreException ignore) {
            }
        }
    }
    
    /**
     * Clears the server side preferences cache. This might be required in HA mode 
     * if there are multiple nodes working on the same preferences and a request needs to get
     * the current stored value in the database
     */
    public void clearCache(){
        SERVERSIDE_PREFERENCES_CACHE.clear();
    }
    

}
