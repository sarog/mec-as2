//$Header: /as4/de/mendelson/util/security/cert/KeystoreStorageImplDB.java 4     9/11/23 9:52 Heller $
package de.mendelson.util.security.cert;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.security.BCCryptoHelper;
import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.security.keydata.KeydataAccessDB;
import de.mendelson.util.systemevents.SystemEventManager;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Keystore storage implementation that relies on a database storage
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class KeystoreStorageImplDB implements KeystoreStorage {

    public static final int KEYSTORE_USAGE_TLS = 1;
    public static final int KEYSTORE_USAGE_ENC_SIGN = 2;
    public static final String KEYSTORE_STORAGE_TYPE_JKS = BCCryptoHelper.KEYSTORE_JKS;
    public static final String KEYSTORE_STORAGE_TYPE_PKCS12 = BCCryptoHelper.KEYSTORE_PKCS12;

    private KeyStore keystore = null;
    private final KeyStoreUtil keystoreUtil = new KeyStoreUtil();
    private final MecResourceBundle rb;
    private int keystoreUsage = KEYSTORE_USAGE_ENC_SIGN;
    private final String keystoreStorageType;
    private final SystemEventManager systemEventManager;
    private final IDBDriverManager dbDriverManager;

    /**
     */
    public KeystoreStorageImplDB(SystemEventManager systemEventManager, 
            IDBDriverManager dbDriverManager, final int KEYSTORE_USAGE,
            final String KEYSTORE_STORAGE_TYPE) throws Exception {
        this.systemEventManager = systemEventManager;
        this.dbDriverManager = dbDriverManager;
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleKeystoreStorage.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        KeydataAccessDB dataAccessDB = new KeydataAccessDB(dbDriverManager, systemEventManager);
        byte[] data = dataAccessDB.getKeydata(KEYSTORE_USAGE);
        if( data == null ){
            throw new Exception( "Unable to access keystore data (DB, usage=" 
                    + KEYSTORE_USAGE 
                    + ", storageType=" + KEYSTORE_STORAGE_TYPE + ")");
        }
        this.keystoreUsage = KEYSTORE_USAGE;
        this.keystoreStorageType = KEYSTORE_STORAGE_TYPE;        
        BCCryptoHelper cryptoHelper = new BCCryptoHelper();
        this.keystore = cryptoHelper.createKeyStoreInstance(this.keystoreStorageType);
        this.keystoreUtil.loadKeyStore(this.keystore, data, this.getKeystorePass());
    }

    @Override
    public void save() throws Exception {
        if (this.keystore == null) {
            //internal error, should not happen
            throw new Exception(this.rb.getResourceString("error.save.notloaded"));
        }
        ByteArrayOutputStream memOut = new ByteArrayOutputStream();
        this.keystoreUtil.saveKeyStore(this.keystore, "test".toCharArray(), memOut);
        byte[] keyData = memOut.toByteArray();
        memOut.close();
        KeydataAccessDB dataAccessDB = new KeydataAccessDB(dbDriverManager, systemEventManager);
        dataAccessDB.updateKeydata(keyData, this.keystoreStorageType, this.keystoreUsage);        
    }

    @Override
    public void loadKeystoreFromServer() throws Exception{
        throw new IllegalAccessException("KeystoreStorageImplDB: loadKeystoreFromServer() is not available for this implementation of storage.");
    }
    
    
    @Override
    public void replaceAllEntriesAndSave(List<KeystoreCertificate> oldList, List<KeystoreCertificate> newList) throws Exception {
        if (this.keystore == null) {
            //internal error, should not happen
            throw new Exception(this.rb.getResourceString("error.save.notloaded"));
        }
        synchronized (this.keystore) {
            Enumeration<String> enumeration = this.keystore.aliases();
            while (enumeration.hasMoreElements()) {
                String alias = enumeration.nextElement();
                this.keystore.deleteEntry(alias);
            }
            for (KeystoreCertificate certificate : newList) {
                if (certificate.getIsKeyPair()) {
                    char[] keyPass = null;
                    if (this.getKeystoreStorageType().equals(KeystoreStorageImplDB.KEYSTORE_STORAGE_TYPE_JKS)) {
                        keyPass = this.getKeystorePass();
                    }
                    this.keystore.setKeyEntry(certificate.getAlias(), certificate.getKey(), keyPass,
                            certificate.getCertificateChain());
                } else {
                    this.keystore.setCertificateEntry(certificate.getAlias(), certificate.getX509Certificate());
                }
            }
            this.save();
        }
    }

    @Override
    public Key getKey(String alias) throws Exception {
        Key key = this.keystore.getKey(alias, this.getKeystorePass());
        return (key);
    }

    @Override
    public Certificate[] getCertificateChain(String alias) throws Exception {
        Certificate[] chain = this.keystore.getCertificateChain(alias);
        return (chain);
    }

    @Override
    public X509Certificate getCertificate(String alias) throws Exception {
        X509Certificate certificate = (X509Certificate) this.keystore.getCertificate(alias);
        return (certificate);
    }

    @Override
    public void renameEntry(String oldAlias, String newAlias, char[] keypairPass) throws Exception {
        KeyStoreUtil keystoreUtility = new KeyStoreUtil();
        keystoreUtility.renameEntry(this.keystore, oldAlias, newAlias, keypairPass);
    }

    @Override
    public KeyStore getKeystore() {
        return (this.keystore);
    }

    @Override
    public char[] getKeystorePass() {
        return ("test".toCharArray());
    }

    private void deleteEntryAndSave( String alias) throws Exception {
        if (this.keystore == null) {
            //internal error, should not happen
            throw new Exception(this.rb.getResourceString("error.delete.notloaded"));
        }
        this.keystore.deleteEntry(alias);
        this.save();
    }
    
    
    @Override
    public void deleteEntry(String alias) throws Exception {
        //because this is the direct file implementation a save is required as a reload from the keystore 
        //file might follow.
        //This is a special behavior for the file based certificate manager.
        this.deleteEntryAndSave(alias);
    }

    @Override
    public Map<String, Certificate> loadCertificatesFromKeystore() throws Exception {
        KeydataAccessDB dataAccessDB = new KeydataAccessDB(dbDriverManager, systemEventManager);
        byte[] data = dataAccessDB.getKeydata(this.getKeystoreUsage());
        if( data == null ){
            throw new Exception( "Unable to access keystore data (DB, usage=" 
                    + this.getKeystoreUsage() 
                    + ", storageType=" + this.getKeystoreStorageType() + ")");
        }
        this.keystoreUtil.loadKeyStore(this.keystore, data, this.getKeystorePass());
        Map<String, Certificate> certificateMap = this.keystoreUtil.getCertificatesFromKeystore(this.keystore);
        return (certificateMap);
    }

    @Override
    public boolean isKeyEntry(String alias) throws Exception {
        return (this.keystore.isKeyEntry(alias));
    }

    @Override
    public String getKeystoreStorageType() {
        return (this.keystoreStorageType);
    }

    @Override
    public int getKeystoreUsage() {
        return (this.keystoreUsage);
    }
}
