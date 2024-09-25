//$Header: /as4/de/mendelson/util/httpconfig/server/ResourceBundleHTTPServerConfigProcessor_fr.java 4     12/15/17 11:33a Heller $
package de.mendelson.util.httpconfig.server;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class ResourceBundleHTTPServerConfigProcessor_fr extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"http.server.config.listener", "Port {0} ({1}), Relié à l''adaptateur réseau {2}" },
        {"http.server.config.keystorepath", "SSL keystore: \"{0}\"" },
        {"http.server.config.clientauthentication", "Le serveur requiert une authentification client: {0}" },
        {"external.ip", "IP externe: {0} / {1}" },
        {"external.ip.error", "IP externe: -Ne peut pas être détecté-" },
        {"http.receipturls", "URL de réception complète qui sont possibles dans la configuration actuelle:" },
        {"http.serverstateurl", "Affichage de l''état du serveur:" },
    };
}
