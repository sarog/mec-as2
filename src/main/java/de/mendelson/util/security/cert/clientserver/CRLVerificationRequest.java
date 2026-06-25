//$Header: /oftp2/de/mendelson/util/security/cert/clientserver/CRLVerificationRequest.java 8     8/04/26 15:35 Heller $
package de.mendelson.util.security.cert.clientserver;

import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.modulelock.ModuleLock;
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
 * @version $Revision: 8 $
 */
public class CRLVerificationRequest extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String KEYSTORE_USAGE_TLS = ModuleLock.Module.TLS_KEYSTORE.toDisplayStr();
    public static final String KEYSTORE_USAGE_ENC_SIGN = ModuleLock.Module.ENCSIGN_KEYSTORE.toDisplayStr();
    public static final int PROCESS_VERIFY_SINGLE = 1;
    public static final int PROCESS_VERIFY_ALL = 2;
    private String keystoreUsage;
    private int process;
    private String fingerprintSHA1;

    /**
     * 
     * @param process What to to on the server side. Please use PROCESS_VERIFY_SINGLE or PROCESS_VERIFY_ALL. If you 
     * pass PROCESS_VERIFY_SINGLE the parameter fingerprintSHA1 is required
     * @param KEYSTORE_USAGE 
     */
    public CRLVerificationRequest(int process, final String KEYSTORE_USAGE) {
        this.process = process;
        this.keystoreUsage = KEYSTORE_USAGE;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public CRLVerificationRequest() {
        super();        
    }
    
    @Override
    public String toString() {
        return ("Verify certificate (CRL)");
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }

    /**
     * @return the keystoreType, one of ModuleLock.Module.ENCSIGN_KEYSTORE, 
     * ModuleLock.Module.TLS_KEYSTORE
     */
    public String getKeystoreUsage() {
        return keystoreUsage;
    }

    /**
     * @return the fingerprintSHA1
     */
    public String getFingerprintSHA1() {
        return fingerprintSHA1;
    }

    /**
     * @return the process
     */
    public int getProcess() {
        return process;
    }

    /**
     * @param fingerprintSHA1 the fingerprintSHA1 to set
     */
    public void setFingerprintSHA1(String fingerprintSHA1) {
        this.fingerprintSHA1 = fingerprintSHA1;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(int process) {
        this.process = process;
    }

    /**
     * @param keystoreUsage the keystoreUsage to set
     */
    public void setKeystoreUsage(String keystoreUsage) {
        this.keystoreUsage = keystoreUsage;
    }
   
}
