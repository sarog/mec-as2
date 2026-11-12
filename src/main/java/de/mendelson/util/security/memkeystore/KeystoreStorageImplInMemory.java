//$Header: /as4/de/mendelson/util/security/memkeystore/KeystoreStorageImplInMemory.java 5     14/01/26 14:06 Heller $
package de.mendelson.util.security.memkeystore;

import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.util.security.cert.KeystoreStorage;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Keystore storage implementation that relies on a byte array
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class KeystoreStorageImplInMemory implements KeystoreStorage {

    private KeyStore keystore = null;
    private final int keystoreUsage;

    /**
     * @param keystorePass
     */
    public KeystoreStorageImplInMemory(KeyStore keystore, int keystoreUsage) throws Exception {
        try {
            if (!(keystore.getType().equals(InMemoryKeyStore.KEYSTORE_INMEMORY))) {
                throw new IllegalArgumentException(
                        "KeystoreStorageImplInMemory: passed keystore must be of type InMemoryKeyStore but is of type "
                        + keystore.getType());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw (e);
        }
        this.keystoreUsage = keystoreUsage;
        this.keystore = keystore;
    }

    @Override
    public void save() throws Exception {
        throw new IllegalAccessException("KeystoreStorageImplInMemory: "
                + "save() is not available for InMemory implementation of storage.");
    }

    @Override
    public void loadKeystoreFromServer() throws Exception {
        throw new IllegalAccessException("KeystoreStorageImplInMemory: "
                + "loadKeystoreFromServer() is not available for InMemory implementation of storage.");
    }

    @Override
    public Optional<KeystoreCertificate> getDownloadedEntriesMetadata(String fingerprintSHA1){
        return( Optional.empty() );
    }
    
    
    @Override
    public void replaceAllEntriesAndSave(List<KeystoreCertificate> oldList, List<KeystoreCertificate> newList) throws Exception {
        throw new IllegalAccessException("KeystoreStorageImplByteArray: "
                + "replaceAllEntriesAndSave() is not available for InMemory implementation of storage.");
    }

    @Override
    public Key getKey(String alias) throws Exception {
        Key key = this.keystore.getKey(alias, null);
        return (key);
    }

    @Override
    public Certificate[] getCertificateChain(String alias) throws Exception {
        Certificate[] chain = this.keystore.getCertificateChain(alias);
        return (chain);
    }

    @Override
    public X509Certificate getCertificate(String alias) throws Exception {
        return ((X509Certificate) this.keystore.getCertificate(alias));
    }

    @Override
    public void renameEntry(String oldAlias, String newAlias, char[] keypairPass) throws Exception {
        KeyStoreUtil.renameEntry(this.keystore, oldAlias, newAlias, keypairPass);
    }

    @Override
    public KeyStore getKeystore() {
        return (this.keystore);
    }

    @Override
    public char[] getKeystorePass() {
        return (null);
    }

    @Override
    public void deleteEntry(String alias) throws Exception {
        if (this.keystore == null) {
            //internal error, should not happen
            throw new Exception("CertificateManager.deleteKeystoreEntry: Unable to delete entry, keystore is not loaded.");
        }
        this.keystore.deleteEntry(alias);
    }

    @Override
    public Map<String, Certificate> loadCertificatesFromKeystore() throws Exception {
        Map<String, Certificate> certificateMap = KeyStoreUtil.getCertificatesFromKeystore(this.keystore);
        return (certificateMap);
    }

    @Override
    public boolean isKeyEntry(String alias) throws Exception {
        return (this.keystore.isKeyEntry(alias));
    }

    @Override
    public String getKeystoreStorageType() {
        return (MendelsonInMemoryProvider.KEYSTORE_INMEMORY);
    }

    @Override
    public int getKeystoreUsage() {
        return (this.keystoreUsage);
    }

    @Override
    public boolean isReadOnly() {
        return (false);
    }

}
