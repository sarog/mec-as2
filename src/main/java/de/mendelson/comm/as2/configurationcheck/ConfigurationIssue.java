//$Header: /as2/de/mendelson/comm/as2/configurationcheck/ConfigurationIssue.java 26    7/07/25 17:06 Heller $
package de.mendelson.comm.as2.configurationcheck;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.SerializationDummy;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

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
 * @version $Revision: 26 $
 */
public class ConfigurationIssue implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int NO_KEY_IN_TLS_KEYSTORE = 1;
    public static final int MULTIPLE_KEYS_IN_TLS_KEYSTORE = 2;
    public static final int CERTIFICATE_EXPIRED_TLS = 3;
    public static final int CERTIFICATE_EXPIRED_ENC_SIGN = 4;
    public static final int HUGE_AMOUNT_OF_TRANSACTIONS_NO_AUTO_DELETE = 5;
    public static final int FEW_CPU_CORES = 6;
    public static final int LOW_MAX_HEAP_MEMORY = 7;
    public static final int NO_OUTBOUND_CONNECTIONS_ALLOWED = 8;
    public static final int CERTIFICATE_MISSING_ENC_REMOTE_PARTNER = 9;
    public static final int CERTIFICATE_MISSING_SIGN_REMOTE_PARTNER = 10;
    public static final int KEY_MISSING_ENC_LOCAL_STATION = 11;
    public static final int KEY_MISSING_SIGN_LOCAL_STATION = 12;
    public static final int USE_OF_TEST_KEYS_IN_TLS = 13;
    public static final int JVM_32_BIT = 14;
    public static final int WINDOWS_SERVICE_LOCAL_SYSTEM_ACCOUNT = 15;
    public static final int TOO_MANY_DIR_POLLS = 16;
    public static final int CRL_CERTIFICATE_REVOCATION_TLS = 17;
    public static final int CRL_CERTIFICATE_REVOCATION_ENC_SIGN = 18;
    public static final int CLIENT_SERVER_IN_ONE_PROCESS = 19;
    public static final int NOT_ENOUGH_HANDLES = 20;
    private int issueId;
    private String details = null;
    private String subject = null;
    private String hintAsHTML = null;

    private static final MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleConfigurationIssue.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public ConfigurationIssue() {
        super();
        this.issueId = -1;
        this.subject = "";
        this.hintAsHTML = "";
    }
    
    public ConfigurationIssue(int issueId) {
        super();
        this.issueId = issueId;
        this.subject = rb.getResourceString(String.valueOf(this.issueId));
        this.hintAsHTML = rb.getResourceString("hint." + String.valueOf(this.issueId));
    }

    /**Returns a list of issues that allow the user to jump into a configuration*/
    public boolean hasJumpTargetInUI(){
        return( this.getIssueId() == NO_KEY_IN_TLS_KEYSTORE
                || this.getIssueId() == MULTIPLE_KEYS_IN_TLS_KEYSTORE
                || this.getIssueId() == CERTIFICATE_EXPIRED_TLS
                || this.getIssueId() == CERTIFICATE_EXPIRED_ENC_SIGN
                || this.getIssueId() == HUGE_AMOUNT_OF_TRANSACTIONS_NO_AUTO_DELETE
                || this.getIssueId() == NO_OUTBOUND_CONNECTIONS_ALLOWED
                || this.getIssueId() == CERTIFICATE_MISSING_ENC_REMOTE_PARTNER
                || this.getIssueId() == CERTIFICATE_MISSING_SIGN_REMOTE_PARTNER
                || this.getIssueId() == KEY_MISSING_ENC_LOCAL_STATION
                || this.getIssueId() == KEY_MISSING_SIGN_LOCAL_STATION
                || this.getIssueId() == USE_OF_TEST_KEYS_IN_TLS
                || this.getIssueId() == CRL_CERTIFICATE_REVOCATION_ENC_SIGN
                || this.getIssueId() == CRL_CERTIFICATE_REVOCATION_TLS);
    }
    
    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
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

    /**
     * @return Some sentences about the problem and how to fix it in the program configuration etc
     */
    public String getHintAsHTML() {
        return hintAsHTML;
    }

    /**
     */
    @JsonIgnore
    public void setHintParameter(Object[] parameter) {
        this.setHintAsHTML(rb.getResourceString("hint." + String.valueOf(this.getIssueId()), parameter));
    }
    
    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }

    /**
     * @param issueId the issueId to set
     */
    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    /**
     * This is a dummy method for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * This is a dummy method for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setHintAsHTML(String hintAsHTML) {
        this.hintAsHTML = hintAsHTML;
    }
}
