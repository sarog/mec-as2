//$Header: /as2/de/mendelson/comm/as2/configurationcheck/ResourceBundleConfigurationIssue.java 5     3/23/17 11:19a Heller $
package de.mendelson.comm.as2.configurationcheck;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize gui entries
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class ResourceBundleConfigurationIssue extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        //preferences localized
        {String.valueOf(ConfigurationIssue.CERTIFICATE_EXPIRED_ENC_SIGN), "Certificate expired (enc/sign)"},
        {String.valueOf(ConfigurationIssue.CERTIFICATE_EXPIRED_SSL), "Certificate expired (SSL)"},
        {String.valueOf(ConfigurationIssue.MULTIPLE_KEYS_IN_SSL_KEYSTORE), "Multiple keys found in SSL keystore - must be single key"},
        {String.valueOf(ConfigurationIssue.NO_KEY_IN_SSL_KEYSTORE), "No key found in SSL keystore"},
        {String.valueOf(ConfigurationIssue.HUGE_AMOUNT_OF_TRANSACTIONS_NO_AUTO_DELETE), "Setup auto delete process - Huge amount of transactions in the system"},
        {String.valueOf(ConfigurationIssue.FEW_CPU_CORES), "Assign min 4 CPU cores to the server process"},
        {String.valueOf(ConfigurationIssue.LOW_MAX_HEAP_MEMORY), "Setup min 1GB heap memory for the server process"},
        {String.valueOf(ConfigurationIssue.NO_OUTBOUND_CONNECTIONS_ALLOWED), "Outbound connections is set to 0 - system will NOT send"},
        {String.valueOf(ConfigurationIssue.CERTIFICATE_MISSING_ENC_REMOTE_PARTNER), "Missing enc certificate of remote partner"},
        {String.valueOf(ConfigurationIssue.CERTIFICATE_MISSING_SIGN_REMOTE_PARTNER), "Missing sign certificate of remote partner"},        
        {String.valueOf(ConfigurationIssue.KEY_MISSING_ENC_LOCAL_STATION), "Missing enc key of local station"},
        {String.valueOf(ConfigurationIssue.KEY_MISSING_SIGN_LOCAL_STATION), "Missing sign key of local station"},
    };
}
