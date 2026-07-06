//$Header: /as4/de/mendelson/util/security/crl/CRLRevocationState.java 8     14/01/26 14:06 Heller $
package de.mendelson.util.security.crl;

import de.mendelson.util.clientserver.SerializationDummy;
import java.io.Serializable;
import de.mendelson.util.security.cert.CertificateValiditySettings;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores information regarding a single revocation request
 * @author S.Heller
 * @version $Revision: 8 $
 */
public class CRLRevocationState implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    public static final int STATE_OK = CertificateValiditySettings.STATE_OK;
    public static final int STATE_CRL_REVOKED = CertificateValiditySettings.STATE_CRL_REVOKED;    
    public static final int STATE_CRL_UNABLE_TO_EXTRACT_CRL_URL 
            = CertificateValiditySettings.STATE_CRL_UNABLE_TO_EXTRACT_CRL_URL;
    public static final int STATE_CRL_NO_CDP_EXTENSION 
            = CertificateValiditySettings.STATE_CRL_NO_CDP_EXTENSION;
    public static final int STATE_CRL_NOT_REACHABLE = CertificateValiditySettings.STATE_CRL_NOT_REACHABLE;
    public static final int STATE_CRL_MALFORMED_URL = CertificateValiditySettings.STATE_CRL_MALFORMED_URL;
    public static final int STATE_CRL_CERT_NOT_READABLE = CertificateValiditySettings.STATE_CRL_CERT_NOT_READABLE;
    public static final int STATE_CRL_DOWNLOAD_FAILED = CertificateValiditySettings.STATE_CRL_DOWNLOAD_FAILED;
    public static final int STATE_CRL_OTHER_PROBLEM = CertificateValiditySettings.STATE_CRL_OTHER_PROBLEM;
    public static final int STATE_CRL_UNSUPPORTED_CRL_URL_SCHEME 
            = CertificateValiditySettings.STATE_CRL_UNSUPPORTED_CRL_URL_SCHEME;
    public static final int STATE_CRL_IN_BAD_FORMAT = CertificateValiditySettings.STATE_CRL_IN_BAD_FORMAT;
    public static final int STATE_CRL_EXPIRED = CertificateValiditySettings.STATE_CRL_EXPIRED;
    public static final int STATE_CRL_INVALID_SIGNATURE = CertificateValiditySettings.STATE_CRL_INVALID_SIGNATURE;
    public static final int STATE_CRL_ISSUER_MISSING = CertificateValiditySettings.STATE_CRL_ISSUER_MISSING;

   
    private int state;
    private String details;
  
    
    public CRLRevocationState( int state, String details){
        this.state = state;
        this.details = details;
    }

     /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public CRLRevocationState(){
    }
    
    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }
    
    
}
