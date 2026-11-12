//$Header: /as4/de/mendelson/util/security/crl/CRLRevocationInformation.java 4     15/12/25 14:54 Heller $
package de.mendelson.util.security.crl;

import de.mendelson.util.clientserver.SerializationDummy;
import java.io.Serializable;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores information regarding a single revocation request
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class CRLRevocationInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    private CRLRevocationState revocationState;
    private String fingerprintSHA1;
    private String logLine;
    private long validUntil = 0L;

    public CRLRevocationInformation(CRLRevocationState state, String fingerprintSHA1,
            String logLine, long validUntil) {
        this.revocationState = state;
        this.fingerprintSHA1 = fingerprintSHA1;
        this.logLine = logLine;
        this.validUntil = validUntil;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public CRLRevocationInformation(){        
    }            
    
    
    /**
     * @return the state
     */
    public CRLRevocationState getRevocationState() {
        return this.revocationState;
    }

    /**
     * @return the fingerprintSHA1
     */
    public String getFingerprintSHA1() {
        return this.fingerprintSHA1;
    }

    /**
     * @return the logLine
     */
    public String getLogLine() {
        return logLine;
    }

    /**
     * @param revocationState the revocationState to set
     */
    public void setRevocationState(CRLRevocationState revocationState) {
        this.revocationState = revocationState;
    }

    /**
     * @param fingerprintSHA1 the fingerprintSHA1 to set
     */
    public void setFingerprintSHA1(String fingerprintSHA1) {
        this.fingerprintSHA1 = fingerprintSHA1;
    }

    /**
     * @param logLine the logLine to set
     */
    public void setLogLine(String logLine) {
        this.logLine = logLine;
    }

    /**
     * @return the validUntil
     */
    public long getValidUntil() {
        return validUntil;
    }

    /**
     * @param validUntil the validUntil to set
     */
    public void setValidUntil(long validUntil) {
        this.validUntil = validUntil;
    }

}
