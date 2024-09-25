//$Header: /as2/de/mendelson/comm/as2/configurationcheck/ConfigurationIssue.java 4     3/23/17 11:19a Heller $
package de.mendelson.comm.as2.configurationcheck;

import java.io.Serializable;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Contains a single configuration issue
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class ConfigurationIssue implements Serializable{

    public static final int NO_KEY_IN_SSL_KEYSTORE = 1;
    public static final int MULTIPLE_KEYS_IN_SSL_KEYSTORE = 2;
    public static final int CERTIFICATE_EXPIRED_SSL = 3;
    public static final int CERTIFICATE_EXPIRED_ENC_SIGN = 4;
    public static final int HUGE_AMOUNT_OF_TRANSACTIONS_NO_AUTO_DELETE = 5;
    public static final int FEW_CPU_CORES = 6;
    public static final int LOW_MAX_HEAP_MEMORY = 7;
    public static final int NO_OUTBOUND_CONNECTIONS_ALLOWED = 8;
    public static final int CERTIFICATE_MISSING_ENC_REMOTE_PARTNER = 9;
    public static final int CERTIFICATE_MISSING_SIGN_REMOTE_PARTNER = 10;
    public static final int KEY_MISSING_ENC_LOCAL_STATION = 11;
    public static final int KEY_MISSING_SIGN_LOCAL_STATION = 12;
    
    private int issueId;
    private String details = null;
    
    public ConfigurationIssue(int issueId) {
        this.issueId = issueId;
    }

    /**
     * @return the issueId
     */
    public int getIssueId() {
        return issueId;
    }

    /**
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    
    
}
