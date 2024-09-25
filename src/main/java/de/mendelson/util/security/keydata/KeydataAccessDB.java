//$Header: /as2/de/mendelson/util/security/keydata/KeydataAccessDB.java 16    23/11/23 10:22 Heller $
package de.mendelson.util.security.keydata;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.security.BCCryptoHelper;
import de.mendelson.util.security.cert.KeystoreStorageImplDB;
import de.mendelson.util.security.cert.ResourceBundleKeystoreStorage;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Database access wrapper for key/certificate information
 *
 * @author S.Heller
 * @version $Revision: 16 $
 */
public class KeydataAccessDB {

    public static String REASON_IMPORT_INITIAL ="INITIAL";
    public static String REASON_IMPORT_COMMAND_LINE_SETTINGS ="COMMAND_LINE_SETTINGS";
    
    
    private final IDBDriverManager dbDriverManager;
    private final SystemEventManager systemEventManager;
    private final MecResourceBundle rb;

    
    public static final int KEYSTORE_USAGE_TLS = KeystoreStorageImplDB.KEYSTORE_USAGE_TLS;
    public static final int KEYSTORE_USAGE_ENC_SIGN = KeystoreStorageImplDB.KEYSTORE_USAGE_ENC_SIGN;
    public static String KEYSTORE_JKS = BCCryptoHelper.KEYSTORE_JKS;
    public static String KEYSTORE_PKCS12 = BCCryptoHelper.KEYSTORE_PKCS12;

    public KeydataAccessDB(IDBDriverManager dbDriverManager, SystemEventManager systemEventManager) {
        this.dbDriverManager = dbDriverManager;
        this.systemEventManager = systemEventManager;
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleKeystoreStorage.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    public static int keystoreTypeStrToInt(String keystoreType) {
        if (keystoreType != null && keystoreType.equals(BCCryptoHelper.KEYSTORE_JKS)) {
            return (1);
        } else if (keystoreType != null && keystoreType.equals(BCCryptoHelper.KEYSTORE_PKCS12)) {
            return (2);
        } else {
            throw new IllegalArgumentException("KeydataAccessDB.keystoreTypeStrToInt: Unsupported keystore type " + keystoreType);
        }
    }

    public static String intToKeystoreTypeStr(int keystoreType) {
        if (keystoreType == 1) {
            return (BCCryptoHelper.KEYSTORE_JKS);
        } else if (keystoreType == 2) {
            return (BCCryptoHelper.KEYSTORE_PKCS12);
        } else {
            throw new IllegalArgumentException("KeydataAccessDB.intToKeystoreTypeStr: Unsupported keystore type " + keystoreType);
        }
    }

    /**
     * Returns the timestamp in ms the keydata entry has been modified last
     */
    public long getLastChanged(int purpose) {
        Connection configConnectionAutoCommit = null;
        try {
            configConnectionAutoCommit = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG);
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                statement = configConnectionAutoCommit.prepareStatement("SELECT lastchanged FROM keydata WHERE purpose=?");
                statement.setInt(1, purpose);
                result = statement.executeQuery();
                if (result.next()) {
                    long timestamp = result.getLong("lastchanged");
                    return (timestamp);
                }
            } finally {
                if (result != null) {
                    result.close();
                }
                if (statement != null) {
                    statement.close();
                }
            }
        } catch (Throwable e) {
            this.systemEventManager.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
        } finally {
            if (configConnectionAutoCommit != null) {
                try {
                    configConnectionAutoCommit.close();
                } catch (Exception e) {
                    this.systemEventManager.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
        }
        return (0);
    }

    /**
     * Returns the key storage of the requested purpose
     */
    public byte[] getKeydata(int purpose) {
        Connection configConnectionAutoCommit = null;
        try {
            configConnectionAutoCommit = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG);
            PreparedStatement selectStatement = null;
            ResultSet result = null;
            try {
                selectStatement = configConnectionAutoCommit.prepareStatement("SELECT storagedata FROM keydata WHERE purpose=?");
                selectStatement.setInt(1, purpose);
                result = selectStatement.executeQuery();
                if (result.next()) {
                    byte[] keyData = this.dbDriverManager.readBytesStoredAsJavaObject(result, "storagedata");
                    return (keyData);
                }
            } finally {
                if (result != null) {
                    result.close();
                }
                if (selectStatement != null) {
                    selectStatement.close();
                }
            }
        } catch (Throwable e) {
            this.systemEventManager.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
        } finally {
            if (configConnectionAutoCommit != null) {
                try {
                    configConnectionAutoCommit.close();
                } catch (Exception e) {
                    this.systemEventManager.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
        }
        return (null);
    }

    /**
     * Writes to the log that there has been a keystore import into the system
     * @param importReason One of REASON_IMPORT_INITIAL, REASON_IMPORT_COMMAND_LINE_SETTINGS
     */
    public void logKeystoreImport(Logger logger, Path keystoreFile, int purpose,
            String importReason) {
        String moveTitle = this.rb.getResourceString("moved.keystore.to.db.title",
                purpose == KeystoreStorageImplDB.KEYSTORE_USAGE_TLS ? "TLS" : "ENC/SIGN");
        String reasonText = "";
        if( importReason.equals( REASON_IMPORT_COMMAND_LINE_SETTINGS)){
            reasonText = this.rb.getResourceString( "moved.keystore.reason.commandline");
        }else if( importReason.equals(REASON_IMPORT_INITIAL)){
            reasonText = this.rb.getResourceString( "moved.keystore.reason.initial");
        }
        String moveText = this.rb.getResourceString("moved.keystore.to.db",
                new Object[]{
                    keystoreFile.toAbsolutePath().toString(),
                    purpose == KeystoreStorageImplDB.KEYSTORE_USAGE_TLS ? "TLS" : "ENC/SIGN"
                });
        logger.info(moveText);
        SystemEvent event = new SystemEvent(SystemEvent.SEVERITY_INFO, SystemEvent.ORIGIN_SYSTEM,
                SystemEvent.TYPE_CERTIFICATE_IMPORT_KEYSTORE);
        event.setBody(moveText + "\n" + reasonText);
        event.setSubject(moveTitle);
        systemEventManager.newEvent(event);
    }

    /**
     * Inserts a new key store into the database if none exists so far
     */
    public void insertKeydataFromFileIfItDoesNotExistInDB(Logger logger, Path keystoreFile, String storageType, int purpose) {
        Connection configConnectionNoAutoCommit = null;
        Statement transactionStatement = null;
        String transactionName = "Keydata_insert";
        try {
            configConnectionNoAutoCommit = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG);
            configConnectionNoAutoCommit.setAutoCommit(false);
            transactionStatement = configConnectionNoAutoCommit.createStatement();
            this.dbDriverManager.startTransaction(transactionStatement, transactionName);
            //start transaction - these tables have to be locked first to forbit any write operation
            this.dbDriverManager.setTableLockINSERTAndUPDATE(transactionStatement,
                    new String[]{
                        "keydata"});
            PreparedStatement checkStatement = null;
            ResultSet result = null;
            boolean entryExists = true;
            try {
                String query = "SELECT COUNT(1) AS counter FROM keydata WHERE purpose=?";
                checkStatement = configConnectionNoAutoCommit.prepareStatement(query);
                checkStatement.setInt(1, purpose);
                result = checkStatement.executeQuery();
                if (result.next()) {
                    entryExists = result.getInt("counter") > 0;
                }
            } finally {
                if (result != null) {
                    result.close();
                }
                if (checkStatement != null) {
                    checkStatement.close();
                }
            }
            if (!entryExists) {
                byte[] keystoreData = Files.readAllBytes(keystoreFile);
                PreparedStatement insertStatement = null;
                try {
                    insertStatement = configConnectionNoAutoCommit.prepareStatement(
                            "INSERT INTO keydata(storagedata,storagetype,purpose,lastchanged)VALUES(?,?,?,?)");
                    this.dbDriverManager.setBytesParameterAsJavaObject(insertStatement, 1, keystoreData);
                    insertStatement.setInt(2, keystoreTypeStrToInt(storageType));
                    insertStatement.setInt(3, purpose);
                    insertStatement.setLong(4, System.currentTimeMillis());
                    insertStatement.executeUpdate();
                } finally {
                    if (insertStatement != null) {
                        insertStatement.close();
                    }
                }
            }
            this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
            if (!entryExists) {
                this.logKeystoreImport(logger, keystoreFile, purpose, REASON_IMPORT_INITIAL);
            }
        } catch (Exception e) {
            try {
                //an error occured - rollback transaction and release all table locks
                this.dbDriverManager.rollbackTransaction(transactionStatement);
            } catch (Exception ex) {
                this.systemEventManager.systemFailure(ex, SystemEvent.TYPE_DATABASE_ANY);
            }
            this.systemEventManager.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
        } finally {
            if (transactionStatement != null) {
                try {
                    transactionStatement.close();
                } catch (Exception e) {
                    this.systemEventManager.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
            if (configConnectionNoAutoCommit != null) {
                try {
                    configConnectionNoAutoCommit.close();
                } catch (Exception e) {
                    //nop
                }
            }
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte singleByte : bytes) {
            builder.append(String.format("%02x", singleByte));
        }
        return builder.toString();
    }

    /**
     * Updates an existing key store in the database. The update will only
     * happen if the keystoreData is different to the keystore data of this
     * purpose that is already in the system. Else the system data will left
     * untouched.
     */
    public void updateKeydata(byte[] keystoreData, String keystoreType, int purpose) {
        Connection configConnectionNoAutoCommit = null;
        Statement transactionStatement = null;
        String transactionName = "update_Keydata";
        try {
            configConnectionNoAutoCommit = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG);
            configConnectionNoAutoCommit.setAutoCommit(false);
            transactionStatement = configConnectionNoAutoCommit.createStatement();
            this.dbDriverManager.startTransaction(transactionStatement, transactionName);
            //start transaction - these tables have to be locked first to forbit any write operation
            this.dbDriverManager.setTableLockINSERTAndUPDATE(transactionStatement,
                    new String[]{
                        "keydata"});
            PreparedStatement updateStatement = null;
            try {
                updateStatement = configConnectionNoAutoCommit.prepareStatement(
                        "UPDATE keydata SET storagedata=?,storagetype=?,lastchanged=? WHERE purpose=?");
                this.dbDriverManager.setBytesParameterAsJavaObject(updateStatement, 1, keystoreData);
                updateStatement.setInt(2, keystoreTypeStrToInt(keystoreType));
                updateStatement.setLong(3, System.currentTimeMillis());
                updateStatement.setInt(4, purpose);
                updateStatement.executeUpdate();
            } finally {
                if (updateStatement != null) {
                    updateStatement.close();
                }
            }
            this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
        } catch (Exception e) {
            try {
                //an error occured - rollback transaction and release all table locks
                this.dbDriverManager.rollbackTransaction(transactionStatement);
            } catch (Exception ex) {
                this.systemEventManager.systemFailure(ex, SystemEvent.TYPE_DATABASE_ANY);
            }
            this.systemEventManager.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
        } finally {
            if (transactionStatement != null) {
                try {
                    transactionStatement.close();
                } catch (Exception e) {
                    this.systemEventManager.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
            if (configConnectionNoAutoCommit != null) {
                try {
                    configConnectionNoAutoCommit.close();
                } catch (Exception e) {
                    //nop
                }
            }
        }
    }

    /**
     * Deletes the keydata entry of a special purpose
     */
    public void deleteKeydata(int purpose) {
        Connection configConnectionNoAutoCommit = null;
        Statement transactionStatement = null;
        String transactionName = "delete_Keydata";
        try {
            configConnectionNoAutoCommit = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG);
            configConnectionNoAutoCommit.setAutoCommit(false);
            transactionStatement = configConnectionNoAutoCommit.createStatement();
            this.dbDriverManager.startTransaction(transactionStatement, transactionName);
            //start transaction - these tables have to be locked first to forbit any write operation
            this.dbDriverManager.setTableLockDELETE(transactionStatement,
                    new String[]{
                        "keydata"});
            PreparedStatement deleteStatement = null;
            try {
                deleteStatement = configConnectionNoAutoCommit.prepareStatement(
                        "DELETE FROM keydata WHERE purpose=?");
                deleteStatement.setInt(1, purpose);
                deleteStatement.executeUpdate();
            } finally {
                if (deleteStatement != null) {
                    deleteStatement.close();
                }
            }
            this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
        } catch (Exception e) {
            try {
                //an error occured - rollback transaction and release all table locks
                this.dbDriverManager.rollbackTransaction(transactionStatement);
            } catch (Exception ex) {
                this.systemEventManager.systemFailure(ex, SystemEvent.TYPE_DATABASE_ANY);
            }
            this.systemEventManager.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
        } finally {
            if (transactionStatement != null) {
                try {
                    transactionStatement.close();
                } catch (Exception e) {
                    this.systemEventManager.systemFailure(e, SystemEvent.TYPE_DATABASE_ANY);
                }
            }
            if (configConnectionNoAutoCommit != null) {
                try {
                    configConnectionNoAutoCommit.close();
                } catch (Exception e) {
                    //nop
                }
            }
        }
    }

}
