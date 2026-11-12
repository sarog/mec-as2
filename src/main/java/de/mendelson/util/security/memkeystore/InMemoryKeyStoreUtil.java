//$Header: /as4/de/mendelson/util/security/memkeystore/InMemoryKeyStoreUtil.java 4     9/12/25 17:42 Heller $
package de.mendelson.util.security.memkeystore;

import de.mendelson.util.security.KeyStoreUtil;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Utilities for the in memory keystore
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public final class InMemoryKeyStoreUtil {

    private InMemoryKeyStoreUtil() {
    }

    /**
     * Import a key and all trust chain certificates from another keystore
     */
    public static void importKey(KeyStore sourceKeyStore, KeyStore targetKeyStore, String alias) throws Exception {
        if (sourceKeyStore.isKeyEntry(alias)) {
            Key importKey = sourceKeyStore.getKey(alias, new char[]{});
            Certificate[] certchain = sourceKeyStore.getCertificateChain(alias);
            if (certchain == null || certchain.length == 0) {
                throw new Exception("Private key with alias " + alias + " found in external keystore does not contain a certificate.");
            }
            targetKeyStore.setKeyEntry(alias, importKey, null, certchain);
            //add certificates of the trust chain if they do not exist so far in the target keystore
            List<String> targetFingerprintList = new ArrayList<String>();
            Enumeration<String> targetAliasEnumeration = targetKeyStore.aliases();
            while (targetAliasEnumeration.hasMoreElements()) {
                String targetAlias = targetAliasEnumeration.nextElement();
                Certificate certificate = targetKeyStore.getCertificate(targetAlias);
                targetFingerprintList.add(KeyStoreUtil.generateFingerprintSHA1(certificate));
            }
            for (Certificate newCertificate : certchain) {
                if (newCertificate instanceof X509Certificate) {
                    X509Certificate newCertificateX509 = (X509Certificate) newCertificate;
                    String newCertFingerprint = KeyStoreUtil.generateFingerprintSHA1(newCertificateX509);
                    if (!targetFingerprintList.contains(newCertFingerprint)) {
                        String proposedAlias = KeyStoreUtil.getProposalCertificateAliasForImport(newCertificateX509);
                        while (targetKeyStore.containsAlias(proposedAlias)) {
                            proposedAlias = proposedAlias + "0";
                        }
                        targetKeyStore.setCertificateEntry(proposedAlias, newCertificateX509);
                    }
                }
            }
        } else {
            throw new Exception("External keystore doesn't contain a private key with alias " + alias);
        }
    }

    /**
     * Import a keypair
     */
    public static void importKey(KeyAndCert keyAndCert,
            KeyStore targetKeyStore) throws Exception {
        PrivateKey importKey = keyAndCert.getPrivateKey();
        X509Certificate importCertificate = keyAndCert.getCertificate();
        String alias = KeyStoreUtil.getProposalCertificateAliasForImport(importCertificate);
        Certificate[] certChain = new Certificate[]{importCertificate};
        targetKeyStore.setKeyEntry(alias, importKey, keyAndCert.getKeypass(), certChain);
        //add certificates of the trust chain if they do not exist so far in the target keystore
        List<String> targetFingerprintList = new ArrayList<String>();
        Enumeration<String> targetAliasEnumeration = targetKeyStore.aliases();
        while (targetAliasEnumeration.hasMoreElements()) {
            String targetAlias = targetAliasEnumeration.nextElement();
            Certificate certificate = targetKeyStore.getCertificate(targetAlias);
            targetFingerprintList.add(KeyStoreUtil.generateFingerprintSHA1(certificate));
        }
        for (Certificate newCertificate : certChain) {
            if (newCertificate instanceof X509Certificate) {
                X509Certificate newCertificateX509 = (X509Certificate) newCertificate;
                String newCertFingerprint = KeyStoreUtil.generateFingerprintSHA1(newCertificateX509);
                if (!targetFingerprintList.contains(newCertFingerprint)) {
                    String proposedAlias = KeyStoreUtil.getProposalCertificateAliasForImport(newCertificateX509);
                    while (targetKeyStore.containsAlias(proposedAlias)) {
                        proposedAlias = proposedAlias + "0";
                    }
                    targetKeyStore.setCertificateEntry(proposedAlias, newCertificateX509);
                }
            }
        }
    }

}
