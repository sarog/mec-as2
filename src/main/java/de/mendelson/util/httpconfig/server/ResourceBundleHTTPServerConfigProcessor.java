//$Header: /as4/de/mendelson/util/httpconfig/server/ResourceBundleHTTPServerConfigProcessor.java 4     12/15/17 11:33a Heller $
package de.mendelson.util.httpconfig.server;

import de.mendelson.util.MecResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class ResourceBundleHTTPServerConfigProcessor extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"http.server.config.listener", "Port {0} ({1}) is bound to the adapter {2}"},
        {"http.server.config.keystorepath", "SSL keystore path: \"{0}\""},
        {"http.server.config.clientauthentication", "Server requires client authentication: {0}"},
        {"external.ip", "External ip: {0} / {1}" },
        {"external.ip.error", "External ip: -Unable to compute-" },
        {"http.receipturls", "Full receipt URLs that are possible in the current configuration:" },
        {"http.serverstateurl", "Check the server state:" },
    };

}
