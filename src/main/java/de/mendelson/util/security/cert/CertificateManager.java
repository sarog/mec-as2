//$Header: /as4/de/mendelson/util/security/cert/CertificateManager.java 88    14/01/26 14:05 Heller $
package de.mendelson.util.security.cert;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.security.BCCryptoHelper;
import de.mendelson.util.security.Base64;
import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.uinotification.UINotification;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.util.logging.Logger;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.auth.x500.X500Principal;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Manager that handles the keystore certificates
 *
 * @author S.Heller
 * @version $Revision: 88 $
 */
public class CertificateManager {

    private Logger logger;
    private final List<KeystoreCertificate> keyStoreCertificateList
            = Collections.synchronizedList(new ArrayList<KeystoreCertificate>());
    private final Map<String, KeystoreCertificate> fingerprintCertificateMap
            = Collections.synchronizedMap(new HashMap<String, KeystoreCertificate>());
    private final Map<String, KeystoreCertificate> aliasCertificateMap
            = Collections.synchronizedMap(new HashMap<String, KeystoreCertificate>());
    private static final MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleCertificateManager.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }
    private KeystoreStorage storage = null;
    private final Map<String, String> snapshotFingerprintAliasMap = Collections.synchronizedMap(new HashMap<String, String>());
    private Instant snapshotTimestamp = null;

    public CertificateManager(Logger logger) {
        this.logger = logger;
    }
        
    /**
     * Stores the current certificate state snapshot for later comparison.
     */
    public void markSnapshot() {
        synchronized (this.keyStoreCertificateList) {
            this.snapshotFingerprintAliasMap.clear();
            for (KeystoreCertificate cert : this.keyStoreCertificateList) {
                this.snapshotFingerprintAliasMap.put(cert.getFingerPrintSHA1(), cert.getAlias());
            }
            this.snapshotTimestamp = Instant.now();
        }
    }

    /**
     * Checks if the keystore content has changed since the last snapshot.
     *
     * @return true if there were additions, deletions or alias changes since
     * last snapshot
     */
    public boolean hasChangedSinceSnapshot() {
        if (this.snapshotTimestamp == null) {
            return true;
        }
        synchronized (this.keyStoreCertificateList) {
            Map<String, String> current = new HashMap<String, String>();
            for (KeystoreCertificate cert : this.keyStoreCertificateList) {
                current.put(cert.getFingerPrintSHA1(), cert.getAlias());
            }
            //added or changed entries
            for (Map.Entry<String, String> entry : current.entrySet()) {
                String fingerprint = entry.getKey();
                String alias = entry.getValue();
                String oldAlias = this.snapshotFingerprintAliasMap.get(fingerprint);
                if (oldAlias == null || !oldAlias.equals(alias)) {
                    return true;
                }
            }
            //removed entries
            for (String oldFingerprint : this.snapshotFingerprintAliasMap.keySet()) {
                if (!current.containsKey(oldFingerprint)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Returns the cert alias that is assigned to the cert/key of the passed
     * fingerprint (SHA1)
     */
    public String getAliasByFingerprint(byte[] fingerprintSHA1) {
        KeystoreCertificate cert = this.getKeystoreCertificateByFingerprintSHA1(fingerprintSHA1);
        //fingerprint not found
        if (cert == null) {
            return (null);
        } else {
            return (cert.getAlias());
        }
    }

    /**
     * Returns the cert alias that is assigned to the cert/key of the passed
     * fingerprint (SHA1)
     */
    public String getAliasByFingerprint(String fingerprintSHA1) {
        KeystoreCertificate cert = this.getKeystoreCertificateByFingerprintSHA1(fingerprintSHA1);
        //fingerprint not found
        if (cert == null) {
            return (null);
        } else {
            return (cert.getAlias());
        }
    }

    /**
     * Returns the certificate chain for a special alias
     */
    public Certificate[] getCertificateChain(String alias) throws Exception {
        Certificate[] chain = this.storage.getCertificateChain(alias);
        return (chain);
    }

    /**
     * Returns the X509 certificate assigned to the passed alias
     */
    public X509Certificate getX509Certificate(String alias) throws Exception {
        KeystoreCertificate certificate;
        synchronized (this.aliasCertificateMap) {
            certificate = this.aliasCertificateMap.get(alias);
        }
        if (certificate == null) {
            throw new Exception(rb.getResourceString("alias.notfound", alias));
        }
        return (certificate.getX509Certificate());
    }

    /**
     * Returns the list of available X509 certificates
     */
    public List<X509Certificate> getX509CertificateList() {
        List<X509Certificate> certList = new ArrayList<X509Certificate>();
        synchronized (this.keyStoreCertificateList) {
            for (KeystoreCertificate cert : this.keyStoreCertificateList) {
                certList.add(cert.getX509Certificate());
            }
        }
        return (certList);
    }

    /**
     * Returns the private key for an alias. If the assigned certificate does
     * not contain a private key an exception is thrown
     */
    public PrivateKey getPrivateKey(String alias) throws Exception {
        KeystoreCertificate entry;
        synchronized (this.aliasCertificateMap) {
            entry = this.aliasCertificateMap.get(alias);
        }
        if (entry == null) {
            throw new Exception(rb.getResourceString("alias.notfound", alias));
        }
        PrivateKey privateKey = (PrivateKey) entry.getPrivateKey();
        if (privateKey == null) {
            throw new Exception(rb.getResourceString("alias.hasno.privatekey", alias));
        }
        return (privateKey);

    }

    /**
     * Returns the public key for an alias.
     */
    public PublicKey getPublicKey(String alias) throws Exception {
        return (this.storage.getCertificate(alias).getPublicKey());
    }

    /**
     * Returns the private key for a passed public key. If the passed public key
     * does not exist an exception is thrown
     *
     * @param publicKey
     * @return
     */
    public PrivateKey getPrivateKeyByPublicKey(PublicKey publicKey) throws Exception {
        try {
            String lookupKeyEncoded = Base64.encode(publicKey.getEncoded());
            synchronized (this.keyStoreCertificateList) {
                for (KeystoreCertificate keystoreCertificate : this.keyStoreCertificateList) {
                    if (keystoreCertificate.getIsKeyPair()) {
                        String foundKeyEncoded = Base64.encode(keystoreCertificate.getPublicKeyEncoded());
                        if (foundKeyEncoded.equals(lookupKeyEncoded)) {
                            return (this.getPrivateKey(keystoreCertificate.getAlias()));
                        }
                    }
                }
            }
            throw new Exception();
        } catch (Exception e) {
            throw new Exception("The private key for the passed public key does not exist.");
        }
    }

    /**
     * Returns the private key for a passed fingerprint (SHA1). If the assigned
     * certificate does not contain a private key an exception is thrown
     */
    public PrivateKey getPrivateKeyByFingerprintSHA1(byte[] fingerprintStrSHA1) throws Exception {
        //this will always return the private key if there is a public and a private key entry with the same serial in
        //the keystore
        KeystoreCertificate certificate = this.getKeystoreCertificateByFingerprintSHA1(fingerprintStrSHA1);
        return (this.getPrivateKey(certificate.getAlias()));
    }

    /**
     * Returns the public key for a passed fingerprint (SHA1). If the assigned
     * certificate does not contain a public key an exception is thrown
     */
    public PublicKey getPublicKeyByFingerprintSHA1(byte[] fingerprintStrSHA1) throws Exception {
        KeystoreCertificate certificate = this.getKeystoreCertificateByFingerprintSHA1(fingerprintStrSHA1);
        return (this.getPublicKey(certificate.getAlias()));
    }

    /**
     * Returns the private key for a passed fingerprint (SHA1). If the assigned
     * certificate does not contain a private key an exception is thrown
     */
    public PrivateKey getPrivateKeyByFingerprintSHA1(String fingerprintStrSHA1) throws Exception {
        //this will always return the private key if there is a public and a private key entry with the same serial in
        //the keystore
        KeystoreCertificate certificate = this.getKeystoreCertificateByFingerprintSHA1(fingerprintStrSHA1);
        if (certificate == null) {
            throw new Exception(rb.getResourceString("certificate.not.found.fingerprint",
                    fingerprintStrSHA1));
        }
        return (this.getPrivateKey(certificate.getAlias()));
    }

    /**
     * Returns the public key for a passed fingerprint (SHA1). If the assigned
     * certificate does not contain a public key an exception is thrown
     */
    public PublicKey getPublicKeyByFingerprintSHA1(String fingerprintStrSHA1) throws Exception {
        KeystoreCertificate certificate = this.getKeystoreCertificateByFingerprintSHA1(fingerprintStrSHA1);
        return (certificate.getPublicKey());
    }

    /**
     * Returns the public key or the private key for an alias.
     */
    public Key getKey(String alias) throws Exception {
        KeystoreCertificate certificate = null;
        synchronized (this.aliasCertificateMap) {
            certificate = this.aliasCertificateMap.get(alias);
        }
        if (certificate == null) {
            throw new Exception(rb.getResourceString("alias.notfound", alias));
        }
        Key key = certificate.getPrivateKey();
        if (key == null) {
            throw new Exception(rb.getResourceString("alias.hasno.key", alias));
        } else {
            return (key);
        }
    }

    /**
     * Stores the manages keystore
     */
    public void saveKeystore() throws Throwable {
        this.storage.save();
        this.rereadKeystoreCertificates();
    }

    /**
     * Deletes all entries in the underlaying storage and replaces them by the
     * list of passed certificates
     *
     * @param newList
     * @throws Exception
     */
    public void replaceAllEntriesAndSave(List<KeystoreCertificate> newList) throws Exception {
        List<KeystoreCertificate> oldList = new ArrayList<KeystoreCertificate>();
        synchronized (this.keyStoreCertificateList) {
            oldList.addAll(this.keyStoreCertificateList);
        }
        this.storage.replaceAllEntriesAndSave(oldList, newList);
        this.rereadKeystoreCertificates();
    }

    /**
     * Deletes an entry from the actual keystore
     */
    public void deleteKeystoreEntry(String alias) throws Throwable {
        this.storage.deleteEntry(alias);
        this.rereadKeystoreCertificates();
    }

    /**
     * Renames an entry in the underlaying keystore. Please remember that
     * PKCS#12 contains no key pair password, pass null in this case
     *
     */
    public void renameAlias(String oldAlias, String newAlias) throws Throwable {
        char[] keypairPass = null;
        if (this.storage.getKeystoreStorageType().equals(BCCryptoHelper.KEYSTORE_JKS)) {
            keypairPass = this.getKeystorePass();
        }
        this.storage.renameEntry(oldAlias, newAlias, keypairPass);
        this.rereadKeystoreCertificates();
    }

    public void loadKeystoreFromServer() throws Exception {
        try {
            this.storage.loadKeystoreFromServer();
        } catch (Throwable e) {
            UINotification.instance().addNotification(e);
        }
        this.rereadKeystoreCertificates();
    }

    /**
     * Refreshes the cached certificate data. This is an expensive operation as
     * it reads and analyzes all certificates/keys from the underlaying keystore
     */
    public void rereadKeystoreCertificates() throws Exception {
        Map<String, Certificate> newCertificateMap = this.storage.loadCertificatesFromKeystore();
        //in older versions it was somehow possible to add key entries and certificate entries
        //with the same fingerprint - this is filtered here
        Map<String, KeystoreCertificate> uniqueFingerprintEntryMap = new HashMap<String, KeystoreCertificate>();
        for (String alias : newCertificateMap.keySet()) {
            KeystoreCertificate keystoreCertificate = new KeystoreCertificate();
            keystoreCertificate.setAlias(alias);
            X509Certificate foundCertificate = (X509Certificate) newCertificateMap.get(alias);
            keystoreCertificate.setCertificate(foundCertificate, this.storage.getCertificateChain(alias));
            try {
                boolean isKeyPair = this.getKeystore().isKeyEntry(alias);
                keystoreCertificate.setIsKeyPair(isKeyPair);
                if (isKeyPair) {
                    keystoreCertificate.setPrivateKey(this.storage.getKey(alias));
                }
            } catch (Throwable e) {
                //no problem, thats what we wanted to know
                keystoreCertificate.setIsKeyPair(false);
                if (this.logger != null) {
                    this.logger.warning(e.getMessage());
                }
            }
            if (uniqueFingerprintEntryMap.containsKey(keystoreCertificate.getFingerPrintSHA1())) {
                KeystoreCertificate existingKeystoreCertificate
                        = uniqueFingerprintEntryMap.get(keystoreCertificate.getFingerPrintSHA1());
                if (keystoreCertificate.getIsKeyPair()) {
                    //always add a key entry if an entry with the same fingerprint does already exist
                    uniqueFingerprintEntryMap.put(keystoreCertificate.getFingerPrintSHA1(), keystoreCertificate);
                    if (this.logger != null) {
                        this.logger.warning("CertificateManager: Skipped the entry " + existingKeystoreCertificate.getAlias()
                                + " -> a key with the same fingerprint exists");
                    }
                } else {
                    if (this.logger != null) {
                        this.logger.warning("CertificateManager: Skipped the entry " + keystoreCertificate.getAlias()
                                + " -> a key with the same fingerprint exists");
                    }
                }
            } else {
                uniqueFingerprintEntryMap.put(keystoreCertificate.getFingerPrintSHA1(), keystoreCertificate);
            }            
        }
        //add metadata to the keystore certificates if this is available
        for (KeystoreCertificate keystoreCertificate : uniqueFingerprintEntryMap.values()) {
            Optional<KeystoreCertificate> downloadedMeta = this.storage.getDownloadedEntriesMetadata(keystoreCertificate.getFingerPrintSHA1());
            if( downloadedMeta.isPresent()){
                keystoreCertificate.setCRLStateValidUntil(downloadedMeta.get().getCRLStateValidUntil());
                keystoreCertificate.setLastCRLState(downloadedMeta.get().getLastCRLState());
            }
        }
        synchronized (this.keyStoreCertificateList) {
            this.keyStoreCertificateList.clear();
            for (KeystoreCertificate keystoreCertificate : uniqueFingerprintEntryMap.values()) {
                this.keyStoreCertificateList.add(keystoreCertificate);
            }
            this.recomputeInternalCaches();
        }
    }

    /**
     * Recomputes the internal caches from the already existing list of
     * certificates
     */
    private void recomputeInternalCaches() {
        synchronized (this.keyStoreCertificateList) {
            synchronized (this.fingerprintCertificateMap) {
                this.fingerprintCertificateMap.clear();
                for (KeystoreCertificate certificate : this.keyStoreCertificateList) {
                    String fingerprint = certificate.getFingerPrintSHA1();
                    //it could happen that a cert and a key with the same fingerprint are in the keystore.
                    //the key has priority in this case
                    KeystoreCertificate existingCertificate = this.fingerprintCertificateMap.get(fingerprint);
                    if (existingCertificate == null) {
                        this.fingerprintCertificateMap.put(fingerprint, certificate);
                    } else if (!existingCertificate.getIsKeyPair()) {
                        this.fingerprintCertificateMap.put(fingerprint, certificate);
                    }
                }
            }
            synchronized (this.aliasCertificateMap) {
                this.aliasCertificateMap.clear();
                for (KeystoreCertificate certificate : this.keyStoreCertificateList) {
                    String alias = certificate.getAlias();
                    this.aliasCertificateMap.put(alias, certificate);
                }
            }
        }
    }

    /**
     * Adds a single certificate and saves the underlaying keystore
     */
    public void addCertificate(String alias, X509Certificate x509Certificate) throws Throwable {
        this.getKeystore().setCertificateEntry(alias, x509Certificate);
        this.saveKeystore();
        this.rereadKeystoreCertificates();
    }

    /**
     * Called from external if the certificate storage has been changed. This
     * method calls the normal rereadkeystore method but logs the step
     */
    public void rereadKeystoreCertificatesLogged() {
        try {
            this.rereadKeystoreCertificates();
            if (this.logger != null) {
                String usageStr;
                if (this.storage.getKeystoreUsage() == KeystoreStorageImplFile.KEYSTORE_USAGE_TLS) {
                    usageStr = "TLS";
                } else if (this.storage.getKeystoreUsage() == KeystoreStorageImplFile.KEYSTORE_USAGE_ENC_SIGN) {
                    usageStr = "Enc/Sign";
                } else {
                    usageStr = "Unknown";
                }
                this.logger.fine(rb.getResourceString("keystore.reloaded", usageStr));
            }
        } catch (Exception e) {
            if (this.logger != null) {
                this.logger.warning(rb.getResourceString("keystore.read.failure",
                        new Object[]{e.getMessage()}));
            }
        }
    }

    /**
     * Wrapper function for the underlaying keystore storage implementation
     */
    public boolean canWrite() {
        return (!this.storage.isReadOnly());
    }

    /**
     * Reads the certificates of the actual key store
     *
     */
    public void loadKeystoreCertificates(KeystoreStorage storage) {
        this.storage = storage;
        try {
            this.rereadKeystoreCertificates();
        } catch (Exception e) {
            if (this.logger != null) {
                this.logger.warning(rb.getResourceString("keystore.read.failure",
                        new Object[]{e.getMessage()}));
            }
        }
    }

    /**
     * Reads the certificates of the actual key store
     *
     */
    public void loadKeystoreCertificatesWithException(KeystoreStorage storage) throws Exception {
        this.storage = storage;
        this.rereadKeystoreCertificates();
    }

    /**
     * returns null if the alias does not exist
     */
    public KeystoreCertificate getKeystoreCertificate(String alias) {
        synchronized (this.aliasCertificateMap) {
            KeystoreCertificate certificate = this.aliasCertificateMap.get(alias);
            return (certificate);
        }
    }

    public KeystoreCertificate getKeystoreCertificateBySubjectDNNonNull(String subjectDN, String additionalInfo) throws Exception {
        KeystoreCertificate foundCert = null;
        synchronized (this.keyStoreCertificateList) {
            for (KeystoreCertificate cert : this.keyStoreCertificateList) {
                if (cert.getSubjectDN().equals(subjectDN)) {
                    //no entry found so far: always store the found one
                    if (foundCert == null) {
                        foundCert = cert;
                    } else {
                        //entry already found: overwrite it only if the found entry is a key
                        if (cert.getIsKeyPair()) {
                            foundCert = cert;
                        }
                    }
                }
            }
            if (foundCert == null) {
                throw new Exception(rb.getResourceString("certificate.not.found.subjectdn.withinfo",
                        new Object[]{subjectDN, additionalInfo}));
            } else {
                return (foundCert);
            }
        }
    }

    public KeystoreCertificate getKeystoreCertificateBySubjectKeyIdentifierNonNull(byte[] skiBytes, String additionalInfo) throws Exception {
        KeystoreCertificate foundCert = this.getKeystoreCertificateBySubjectKeyIdentifier(skiBytes);
        if (foundCert == null) {
            throw new Exception(rb.getResourceString("certificate.not.found.ski.withinfo",
                    new Object[]{KeystoreCertificate.byteArrayToHexStr(skiBytes), additionalInfo}));
        } else {
            return (foundCert);
        }

    }

    public KeystoreCertificate getKeystoreCertificateBySubjectKeyIdentifier(byte[] skiBytes) {
        String skiAsHexStr = KeystoreCertificate.byteArrayToHexStr(skiBytes);
        //it could happen that a cert and a key with the same fingerprint are in the keystore.
        //Always return the key in this case.
        KeystoreCertificate foundCert = null;
        synchronized (this.keyStoreCertificateList) {
            for (KeystoreCertificate cert : this.keyStoreCertificateList) {
                List<String> certificateSKIList = cert.getSubjectKeyIdentifier();
                for (String foundSKI : certificateSKIList) {
                    if (foundSKI.equals(skiAsHexStr)) {
                        //no entry found so far: always store the found one
                        if (foundCert == null) {
                            foundCert = cert;
                        } else {
                            //entry already found: overwrite it only if the found entry is a key
                            if (cert.getIsKeyPair()) {
                                foundCert = cert;
                            }
                        }
                    }
                }
            }
        }
        return (foundCert);
    }

    /**
     * returns null if a certificate with the issuerDN and the serial does not
     * exist
     */
    public KeystoreCertificate getKeystoreCertificateByIssuerDNAndSerial(String issuerDN, String serialDEC) {
        if (issuerDN == null || serialDEC == null) {
            return (null);
        }
        return (this.getKeystoreCertificateByIssuerAndSerial(new X500Principal(issuerDN), serialDEC));
    }

    /**
     * returns null if a certificate with the issuerDN and the serial does not
     * exist
     */
    public KeystoreCertificate getKeystoreCertificateByIssuerAndSerial(X500Principal issuer, String serialDEC) {
        KeystoreCertificate foundCert = null;
        try {
            String searchIssuerRFC2253 = buildFilteredDN(issuer);
            synchronized (this.keyStoreCertificateList) {
                for (KeystoreCertificate cert : this.keyStoreCertificateList) {
                    if (cert.getSerialNumberDEC().equals(serialDEC)) {
                        try {
                            String compareIssuerRFC2253 = buildFilteredDN(cert.getX509Certificate()
                                    .getIssuerX500Principal());
                            if (compareIssuerRFC2253.equalsIgnoreCase(searchIssuerRFC2253)) {
                                //no entry found so far: always store the found one 
                                if (foundCert == null) {
                                    foundCert = cert;
                                } else {
                                    //entry already found: overwrite it only if the newly found entry is a key 
                                    if (cert.getIsKeyPair()) {
                                        foundCert = cert;
                                    }
                                }
                            }
                        } catch (Throwable e) {
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return foundCert;
    }

    /**
     * creates a String of a passed principal that contains just
     * CN=xxx,O=xxx,OU=xxx,C=xxx,ST=xxx,L=xxx if they exist. This is useful to
     * compare the principals even if one has additional parameter like email
     * etc
     *
     * @param principal
     * @return
     * @throws Exception
     */
    private static String buildFilteredDN(X500Principal principal) throws Exception {
        LdapName ldapName = new LdapName(principal.getName(X500Principal.RFC2253));
        String[] orderedAttributes = {"CN", "O", "OU", "C", "ST", "L"};
        Map<String, String> attributeMap = new HashMap<String, String>();
        for (Rdn rdn : ldapName.getRdns()) {
            String type = rdn.getType().toUpperCase();
            if (Arrays.asList(orderedAttributes).contains(type)) {
                attributeMap.put(type, rdn.getValue().toString());
            }
        }
        List<String> partList = new ArrayList<String>();
        for (String attribute : orderedAttributes) {
            if (attributeMap.containsKey(attribute)) {
                partList.add(attribute + "=" + attributeMap.get(attribute));
            }
        }
        return String.join(",", partList);
    }

    /**
     * Throws an exception if the requested certificate does not exist in the
     * keystore - this contains the additional info Str
     */
    public KeystoreCertificate getKeystoreCertificateByFingerprintSHA1NonNull(String fingerprintSHA1, String additionalInfo) throws Exception {
        KeystoreCertificate certificate = this.getKeystoreCertificateByFingerprintSHA1(fingerprintSHA1);
        if (certificate == null) {
            throw new Exception(rb.getResourceString("certificate.not.found.fingerprint.withinfo",
                    new Object[]{fingerprintSHA1, additionalInfo}));
        } else {
            return (certificate);
        }
    }

    /**
     * Throws an exception if the requested certificate does not exist in the
     * keystore
     */
    public KeystoreCertificate getKeystoreCertificateByFingerprintSHA1NonNull(String fingerprintSHA1) throws Exception {
        KeystoreCertificate certificate = this.getKeystoreCertificateByFingerprintSHA1(fingerprintSHA1);
        if (certificate == null) {
            throw new Exception(rb.getResourceString("certificate.not.found.fingerprint", fingerprintSHA1));
        } else {
            return (certificate);
        }
    }

    /**
     * Throws an exception if the requested certificate does not exist in the
     * keystore
     */
    public KeystoreCertificate getKeystoreCertificateByFingerprintSHA1NonNull(byte[] fingerprintSHA1) throws Exception {
        KeystoreCertificate certificate = this.getKeystoreCertificateByFingerprintSHA1(fingerprintSHA1);
        if (certificate == null) {
            throw new Exception(rb.getResourceString("certificate.not.found.fingerprint",
                    KeystoreCertificate.fingerprintBytesToStr(fingerprintSHA1)));
        } else {
            return (certificate);
        }
    }

    /**
     * Throws an exception if the requested certificate does not exist in the
     * keystore- this contains the additional info Str
     */
    public KeystoreCertificate getKeystoreCertificateByFingerprintSHA1NonNull(byte[] fingerprintSHA1, String additionalInfo) throws Exception {
        KeystoreCertificate certificate = this.getKeystoreCertificateByFingerprintSHA1(fingerprintSHA1);
        if (certificate == null) {
            throw new Exception(rb.getResourceString("certificate.not.found.fingerprint.withinfo",
                    new Object[]{KeystoreCertificate.fingerprintBytesToStr(fingerprintSHA1), additionalInfo}));
        } else {
            return (certificate);
        }
    }

    private boolean issuerIsEqual(String issuer1, String issuer2) {
        final String[] compareList = new String[]{
            "C", "O", "OU", "CN", "ST", "L", "E"
        };
        try {
            LdapName name1 = new LdapName(issuer1);
            LdapName name2 = new LdapName(issuer2);
            for (Rdn rdn1 : name1.getRdns()) {
                for (Rdn rdn2 : name2.getRdns()) {
                    for (String compareType : compareList) {
                        if (rdn1.getType().equalsIgnoreCase(compareType)
                                && rdn2.getType().equalsIgnoreCase(compareType)) {
                            String value1 = rdn1.getValue().toString();
                            String value2 = rdn2.getValue().toString();
                            if (!value1.equals(value2)) {
                                return (false);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return (false);
        }
        return (true);
    }

    /**
     * Tries to find a certificate with the related issuer/serial and throws an
     * exception if it does not exist in the certificate manager
     *
     * @param issuerStrEscaped This is the issuer as string - in this format
     * commas are escaped, e.g. "O=GoDaddy.com\, Inc."
     * @param serial
     * @param additionalInfo Additional info str if the certificate has not been
     * found
     * @return
     * @throws Exception
     */
    public KeystoreCertificate getKeystoreCertificateByIssuerSerialNonNull(String issuerStrEscaped, BigInteger serial,
            String additionalInfo) throws Exception {
        KeystoreCertificate foundCert = null;
        synchronized (this.keyStoreCertificateList) {
            for (KeystoreCertificate cert : this.keyStoreCertificateList) {
                String foundIssuerDN = cert.getIssuerDN();
                BigInteger foundSerial = cert.getX509Certificate().getSerialNumber();
                if (foundSerial.equals(serial) && this.issuerIsEqual(issuerStrEscaped, foundIssuerDN)) {
                    //no entry found so far: always store the found one
                    if (foundCert == null) {
                        foundCert = cert;
                    } else {
                        //entry already found: overwrite it only if the found entry is a key
                        if (cert.getIsKeyPair()) {
                            foundCert = cert;
                        }
                    }
                }
            }
        }
        if (foundCert == null) {
            String serialHex = serial.toString(16);
            throw new Exception(rb.getResourceString("certificate.not.found.issuerserial.withinfo",
                    new Object[]{issuerStrEscaped,
                        serial.toString() + " (dec), " + serialHex + " (hex)",
                        additionalInfo}));
        } else {
            return (foundCert);
        }
    }

    /**
     * returns null if the fingerprint does not exist
     */
    public KeystoreCertificate getKeystoreCertificateByFingerprintSHA1(byte[] fingerprintSHA1) {
        if (fingerprintSHA1 == null) {
            return (null);
        }
        String fingerprintSHA1Str = KeystoreCertificate.byteArrayToHexStr(fingerprintSHA1);
        synchronized (this.fingerprintCertificateMap) {
            KeystoreCertificate foundCert = this.fingerprintCertificateMap.get(fingerprintSHA1Str);
            return (foundCert);
        }
    }

    /**
     * returns null if the fingerprint does not exist
     */
    public KeystoreCertificate getKeystoreCertificateByFingerprintSHA1(String fingerprintSHA1) {
        //just return null if the fingerprint string is invalid in any case
        if (fingerprintSHA1 == null || fingerprintSHA1.trim().isEmpty() || !fingerprintSHA1.contains(":")) {
            return (null);
        }
        synchronized (this.fingerprintCertificateMap) {
            KeystoreCertificate foundCert = this.fingerprintCertificateMap.get(fingerprintSHA1);
            return (foundCert);
        }
    }

    /**
     * Returns a list of certificates, sorted by their name
     */
    public List<KeystoreCertificate> getKeyStoreCertificateList() {
        List<KeystoreCertificate> newList;
        synchronized (this.keyStoreCertificateList) {
            newList = new ArrayList<KeystoreCertificate>(this.keyStoreCertificateList);
        }
        Collections.sort(newList);
        return (newList);
    }

    /**
     * Passes a logger to the certificate manager. There will be no logging if
     * no logger has been passed
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public char[] getKeystorePass() {
        return this.storage.getKeystorePass();
    }

    public KeyStore getKeystore() {
        return (this.storage.getKeystore());
    }

    /**
     * Returns a map with issuer as key and the available certs as value
     */
    public Map<X500Principal, List<X509Certificate>> getIssuerCertificateMap() throws Exception {
        Map<X500Principal, List<X509Certificate>> map = new HashMap<X500Principal, List<X509Certificate>>();
        synchronized (this.keyStoreCertificateList) {
            List<KeystoreCertificate> certList = keyStoreCertificateList;
            for (KeystoreCertificate keystoreCertificate : certList) {
                X509Certificate foundCert = (X509Certificate) keystoreCertificate.getX509Certificate();
                if (foundCert != null) {
                    X500Principal subjectDN = foundCert.getSubjectX500Principal();
                    List<X509Certificate> foundCertList = map.get(subjectDN);
                    if (foundCertList == null) {
                        foundCertList = new ArrayList<X509Certificate>();
                        foundCertList.add(foundCert);
                    } else {
                        if (!foundCertList.contains(foundCert)) {
                            foundCertList.add(foundCert);
                        }
                    }
                    map.put(subjectDN, foundCertList);
                }
            }
            return map;
        }
    }
       
    /**
     * Returns a map with subject as key and the available certs as value
     */
    public Map<X500Principal, X509Certificate> getSubjectCertificateMap() throws Exception {
        Map<X500Principal, X509Certificate> map = new HashMap<X500Principal, X509Certificate>();
        synchronized (this.keyStoreCertificateList) {
            for (KeystoreCertificate keystoreCertificate : keyStoreCertificateList) {
                X509Certificate foundCertX509 = (X509Certificate) keystoreCertificate.getX509Certificate();
                if (foundCertX509 != null) {
                    X500Principal subject = foundCertX509.getSubjectX500Principal();                    
                    map.put(subject, foundCertX509);
                }
            }
            return map;
        }
    }
    
    
    /**
     * Adds a new Key entry and saves the underlaying keystore
     */
    public void setKeyEntry(String alias, Key key, Certificate[] chain) throws Throwable {
        this.getKeystore().setKeyEntry(alias, key, this.getKeystorePass(), chain);
        this.saveKeystore();
        this.rereadKeystoreCertificates();
    }

    /**
     * Returns the storage type of the underlaying storage. This is one of
     * BCCryptoHelper.KEYSTORE_JKS, BCCryptoHelper.KEYSTORE_PKCS11,
     * BCCryptoHelper.KEYSTORE_PKCS12
     *
     * @return
     */
    public String getStorageType() {
        return (this.storage.getKeystoreStorageType());
    }

    /**
     * Returns the usage of the underlaying storage, e.g.
     * KeystoreStorageImplClientServer.KEYSTORE_USAGE_ENC_SIGN or
     * KeystoreStorageImplClientServer.KEYSTORE_USAGE_TLS
     *
     * @return
     */
    public int getStorageUsage() {
        return (this.storage.getKeystoreUsage());
    }

    /**
     * Compute the whole trust chain of a given alias and returns it as list
     */
    public List<X509Certificate> computeTrustChain(String alias) throws Exception {
        KeystoreCertificate keystoreCertificate = this.getKeystoreCertificate(alias);
        return (this.computeTrustChain(keystoreCertificate));
    }

    /**
     * Compute the whole trust chain of a given alias and returns it as list
     */
    public List<X509Certificate> computeTrustChain(KeystoreCertificate keystoreCertificate) throws Exception {
        Set<TrustAnchor> trustAnchors = KeyStoreUtil.getTrustAnchors(this.getKeystore());
        List<X509Certificate> certificateList = this.getX509CertificateList();
        PKIXCertPathBuilderResult result = keystoreCertificate.getPKIXCertPathBuilderResult(trustAnchors, certificateList);
        List<X509Certificate> pathList = new ArrayList<X509Certificate>();
        //self signed?
        if (result == null) {
            //it's a self signed certificate: return it without any CA/intermediate certs
            pathList.add(keystoreCertificate.getX509Certificate());
        } else {
            //trusted cert
            CertPath path = result.getCertPath();
            for (Object cert : path.getCertificates()) {
                pathList.add(0, (X509Certificate) cert);
            }
            X509Certificate anchorCertX509 = pathList.get(0);
            boolean trustChainComplete = false;
            while (!trustChainComplete) {
                KeystoreCertificate keyCertAnchor = null;
                //find out the keystore cert of the anchor
                for (KeystoreCertificate keyCert : this.getKeyStoreCertificateList()) {
                    if (keyCert.getX509Certificate().equals(anchorCertX509)) {
                        keyCertAnchor = keyCert;
                        break;
                    }
                }
                if (keyCertAnchor != null) {
                    //check if the anchor has another anchor as intermediates certificate may have the attribute "CA:true", too
                    result = keyCertAnchor.getPKIXCertPathBuilderResult(trustAnchors, this.getX509CertificateList());
                    if (result != null) {
                        anchorCertX509 = result.getTrustAnchor().getTrustedCert();
                        if (!keyCertAnchor.getX509Certificate().equals(anchorCertX509)) {
                            pathList.add(0, anchorCertX509);
                        } else {
                            trustChainComplete = true;
                        }
                    } else {
                        trustChainComplete = true;
                    }
                } else {
                    trustChainComplete = true;
                }
            }
        }
        return (pathList);
    }

    /**
     * Extracts the root certificate for a given certificate from a list of
     * certificates
     *
     * @param certToCheck
     * @param candidateRoots
     * @return
     * @throws Exception
     */
    public static X509Certificate findRootCertificateInListOrNull(X509Certificate certToCheck,
            List<X509Certificate> candidateRoots) throws Exception {
        Set<TrustAnchor> trustAnchors = new HashSet<TrustAnchor>();
        for (X509Certificate possibleRoot : candidateRoots) {
            if (isRootCertificate(possibleRoot)) {
                trustAnchors.add(new TrustAnchor(possibleRoot, null));
            }
        }
        if (trustAnchors.isEmpty()) {
            return null;
        }
        CertStore certStore = CertStore.getInstance(
                "Collection",
                new CollectionCertStoreParameters(candidateRoots)
        );
        PKIXBuilderParameters params = new PKIXBuilderParameters(trustAnchors, new X509CertSelector());
        params.addCertStore(certStore);
        params.setRevocationEnabled(false);
        //Selector for the certificate we want to validate
        X509CertSelector selector = new X509CertSelector();
        selector.setCertificate(certToCheck);
        params.setTargetCertConstraints(selector);
        CertPathBuilder builder = CertPathBuilder.getInstance("PKIX");
        try {
            PKIXCertPathBuilderResult result
                    = (PKIXCertPathBuilderResult) builder.build(params);
            //Extract the root certificate
            X509Certificate rootCert = result.getTrustAnchor().getTrustedCert();
            // Check if the root is really from the list
            for (X509Certificate candidate : candidateRoots) {
                if (candidate.equals(rootCert)) {
                    return candidate;
                }
            }
        } catch (CertPathBuilderException ex) {
            // no chain to any of the candidate roots
            return null;
        }
        return null;
    }

    /**
     * Checks if the passed certificate is a root certificate. There are two
     * conditions to meet:<br>
     * 1. Self-signed (Signature has been created with the own public key)<br>
     * 2. Its a CA certificate (basicConstraints >= 0)
     *
     * @param cert
     * @return
     */
    public static boolean isRootCertificate(X509Certificate cert) {
        if (cert == null) {
            return false;
        }
        //ca?
        if (cert.getBasicConstraints() < 0) {
            return false;
        }
        //self signed?
        try {
            cert.verify(cert.getPublicKey());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Just loads the certificates with their alias from the storage - this does
     * not recompute the internal caches
     *
     * @return
     * @throws Exception
     */
    public Map<String, Certificate> loadCertificatesFromStorage() throws Exception {
        return (this.storage.loadCertificatesFromKeystore());
    }

}
