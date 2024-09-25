//$Header: /as2/de/mendelson/util/security/JKSKeys2PKCS12.java 9     2/11/23 14:03 Heller $
package de.mendelson.util.security;

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
 * Allows the conversion of a private key that is stored in a sun keystore to
 * the pkcs#12 format
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class JKSKeys2PKCS12 {

    private Logger logger = null;

    private KeyStore targetKeyStore = null;

    public JKSKeys2PKCS12(Logger logger) {
        this.logger = logger;
    }

    /**
     * @param jksKeyStore JKS Keystore that contains the private key
     * @param jksKeyPassword JKS Key pass
     * @param alias alias of the key, used in both import and exported keystore
     */
    public void exportKeyFrom(KeyStore jksKeyStore, char[] jksKeyPassword, String alias) throws Exception {
        try{
        //extract key, for both EC and RSA
        Key jksPrivateKey = jksKeyStore.getKey(alias, jksKeyPassword);
        //Get certificate chain
        Certificate[] jksCerts = jksKeyStore.getCertificateChain(alias);
        if (jksPrivateKey == null || jksCerts == null) {
            this.logger.severe("I didn't find a private key entry with the alias \"" + alias + "\" in the JKS keystore");
            return;
        }
        KeyStore pkcs12Keystore = this.targetKeyStore;
        if (pkcs12Keystore == null) {
            pkcs12Keystore = this.generatePKCS12KeyStore();
        }
        //pkcs12 has no key password
        pkcs12Keystore.setKeyEntry(alias, jksPrivateKey, "dummy".toCharArray(), jksCerts);
        }
        catch( Exception e ){
            e.printStackTrace();
            throw e;
        }
    }

    public void setTargetKeyStore(KeyStore keystore) {
        this.targetKeyStore = keystore;
    }

    /**
     * Loads ore creates a keystore to import the keys to
     */
    private KeyStore generatePKCS12KeyStore() throws Exception {
        //do not remove the BC paramter, SUN cannot handle the format proper
        KeyStore keystore = KeyStore.getInstance(BCCryptoHelper.KEYSTORE_PKCS12, BouncyCastleProvider.PROVIDER_NAME);
        keystore.load(null, null);
        return (keystore);
    }

    /**
     * Saves the passed keystore
     *
     * @param keystorePass Password for the keystore
     */
    public void saveKeyStore(KeyStore keystore, char[] keystorePass,
            Path file) throws Exception {
        OutputStream out = null;
        try {
            out = Files.newOutputStream(file);
            keystore.store(out, keystorePass);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
