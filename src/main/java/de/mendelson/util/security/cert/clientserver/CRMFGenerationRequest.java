//$Header: /as4/de/mendelson/util/security/cert/clientserver/CRMFGenerationRequest.java 3     8/01/26 15:43 Heller $
package de.mendelson.util.security.cert.clientserver;

import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.security.cert.KeystoreStorageImplFile;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Msg for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 3 $
 */
public class CRMFGenerationRequest extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int KEYSTORE_USAGE_TLS = KeystoreStorageImplFile.KEYSTORE_USAGE_TLS;
    public static final int KEYSTORE_USAGE_ENC_SIGN = KeystoreStorageImplFile.KEYSTORE_USAGE_ENC_SIGN;
    private int keystoreUsageSource;
    private String fingerprintSHA1Encryption;
    private String fingerprintSHA1Signature;
    private String fingerprintSHA1TLS;
    private String fingerprintSHA1CARoot;
    private boolean initialRequest = false;
    private char[] initialRequestPassword;

    public CRMFGenerationRequest(
            final int KEYSTORE_USAGE_SOURCE,
            String fingerprintSHA1Encryption, 
            String fingerprintSHA1Signature, 
            String fingerprintSHA1TLS, String fingerprintSHA1CARoot,
            boolean initialRequest, char[] initialRequestPassword) {
        this.keystoreUsageSource = KEYSTORE_USAGE_SOURCE;
        this.fingerprintSHA1Encryption = fingerprintSHA1Encryption;
        this.fingerprintSHA1Signature = fingerprintSHA1Signature;
        this.fingerprintSHA1TLS = fingerprintSHA1TLS;
        this.fingerprintSHA1CARoot = fingerprintSHA1CARoot;
        this.initialRequest = initialRequest;
        this.initialRequestPassword = initialRequestPassword;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public CRMFGenerationRequest() {
        super();
    }

    @Override
    public String toString() {
        return ("CRMF generation request");
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }

    /**
     * @return the keystoreUsageSource
     */
    public int getKeystoreUsageSource() {
        return keystoreUsageSource;
    }

    /**
     * @param keystoreUsageSource the keystoreUsageSource to set
     */
    public void setKeystoreUsageSource(int keystoreUsageSource) {
        this.keystoreUsageSource = keystoreUsageSource;
    }

    /**
     * @return the fingerprintSHA1Encryption
     */
    public String getFingerprintSHA1Encryption() {
        return fingerprintSHA1Encryption;
    }

    /**
     * @param fingerprintSHA1Encryption the fingerprintSHA1Encryption to set
     */
    public void setFingerprintSHA1Encryption(String fingerprintSHA1Encryption) {
        this.fingerprintSHA1Encryption = fingerprintSHA1Encryption;
    }

    /**
     * @return the fingerprintSHA1Signature
     */
    public String getFingerprintSHA1Signature() {
        return fingerprintSHA1Signature;
    }

    /**
     * @param fingerprintSHA1Signature the fingerprintSHA1Signature to set
     */
    public void setFingerprintSHA1Signature(String fingerprintSHA1Signature) {
        this.fingerprintSHA1Signature = fingerprintSHA1Signature;
    }

    /**
     * @return the fingerprintSHA1TLS
     */
    public String getFingerprintSHA1TLS() {
        return fingerprintSHA1TLS;
    }

    /**
     * @param fingerprintSHA1TLS the fingerprintSHA1TLS to set
     */
    public void setFingerprintSHA1TLS(String fingerprintSHA1TLS) {
        this.fingerprintSHA1TLS = fingerprintSHA1TLS;
    }

    /**
     * @return the initialRequest
     */
    public boolean isInitialRequest() {
        return initialRequest;
    }

    /**
     * @param initialRequest the initialRequest to set
     */
    public void setInitialRequest(boolean initialRequest) {
        this.initialRequest = initialRequest;
    }

    /**
     * @return the initialRequestPassword
     */
    public char[] getInitialRequestPassword() {
        return initialRequestPassword;
    }

    /**
     * @param initialRequestPassword the initialRequestPassword to set
     */
    public void setInitialRequestPassword(char[] initialRequestPassword) {
        this.initialRequestPassword = initialRequestPassword;
    }

    /**
     * @return the fingerprintSHA1CARoot
     */
    public String getFingerprintSHA1CARoot() {
        return fingerprintSHA1CARoot;
    }

    /**
     * @param fingerprintSHA1CARoot the fingerprintSHA1CARoot to set
     */
    public void setFingerprintSHA1CARoot(String fingerprintSHA1CARoot) {
        this.fingerprintSHA1CARoot = fingerprintSHA1CARoot;
    }

}
