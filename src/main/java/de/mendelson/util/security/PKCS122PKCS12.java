//$Header: /oftp2/de/mendelson/util/security/PKCS122PKCS12.java 13    3/11/23 10:16 Heller $
package de.mendelson.util.security;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.logging.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * This class allows to import a key that exist in pkcs#12 keystore into an
 * other pkcs12 keystore
 *
 * @author S.Heller
 * @version $Revision: 13 $
 */
public class PKCS122PKCS12{

    private Logger logger = Logger.getAnonymousLogger();
    /**
     * Keystore to use, if this is not set a new one will be created
     */
    private KeyStore targetKeystore = null;
    /**
     * Default pass for a new created keystore, overwrite this by using the
     * setKeyStore() method
     */
    private char[] targetKeystorePass = "test".toCharArray();

    /**
     * Creates a new instance of PEMUtil
     *
     * @param logger Logger to log the information to
     */
    public PKCS122PKCS12(Logger logger) {
        this.logger = logger;
        //forget it to work without BC at this point, the SUN JCE provider
        //could not handle pcks12
        // performs "Security.addProvider(new BouncyCastleProvider());" and adds some BC related system properties
        new BCCryptoHelper().initialize();
    }

    /**
     * @param sourceKeyStore The keystore where to export the key to
     * @param keyAlias The alias of the key to export
     */
    public void exportKeyFrom(KeyStore sourceKeyStore, String keyAlias) throws Exception {
        if (sourceKeyStore.isKeyEntry(keyAlias)) {
            Key key = sourceKeyStore.getKey(keyAlias, null);
            Certificate[] certs = sourceKeyStore.getCertificateChain(keyAlias);
            if (certs == null || certs.length == 0) {
                throw new Exception("PKCS#12 import: private key with alias " + keyAlias + " does not contain a certificate.");
            }
            KeyStore targetStore = this.targetKeystore;
            if (targetStore == null) {
                targetStore = this.generateKeyStore();
            }
            String targetKeyAlias = keyAlias;
            int count = 1;
            //rename the alias for the target if it already exists in the target store
            while( targetStore.containsAlias(targetKeyAlias)){
                targetKeyAlias = keyAlias + "_" + String.valueOf(count);
                count++;
            }
            //PKCS12 keys dont have a password
            targetStore.setKeyEntry(targetKeyAlias, key, null, certs);
        } else {
            throw new Exception("PKCS#12 export: The source keystore doesn't contain a private key with the alias " + keyAlias);
        }
    }

    /**
     * @param providerName The name of the crypto provider, e.g. BouncyCastleProvider.PROVIDER_NAME
     */
    public void exportKeyFrom(InputStream sourceKeystoreStream, char[] sourceKeypass,
            String alias, String providerName) throws Exception {
        //open keystore
        KeyStore sourceKeystore = KeyStore.getInstance(BCCryptoHelper.KEYSTORE_PKCS12, 
                providerName);
        sourceKeystore.load(sourceKeystoreStream, sourceKeypass);
        this.exportKeyFrom(sourceKeystore, alias);
    }

    /**
     * Loads ore creates a keystore to import the keys to
     */
    private KeyStore generateKeyStore() throws Exception {
        //do not remove the BC paramter, SUN cannot handle the format proper
        KeyStore localKeystore = KeyStore.getInstance(BCCryptoHelper.KEYSTORE_PKCS12, 
                BouncyCastleProvider.PROVIDER_NAME);
        localKeystore.load(null, null);
        return (localKeystore);
    }

    /**
     * Sets an already existing keystore to this class. Without an existing
     * keystore a new one is created
     */
    public void setTargetKeyStore(KeyStore targetKeystore, char[] targetKeystorePass) {
        this.targetKeystore = targetKeystore;
        this.targetKeystorePass = targetKeystorePass;
    }

    /**
     * Saves the passed keystore
     *
     */
    public void saveTargetKeyStoreTo(Path file) throws Exception {
        OutputStream out = null;
        try {
            out = Files.newOutputStream(file);
            this.targetKeystore.store(out, this.targetKeystorePass);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
