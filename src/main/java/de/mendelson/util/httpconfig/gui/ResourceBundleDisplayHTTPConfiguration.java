//$Header: /as4/de/mendelson/util/httpconfig/gui/ResourceBundleDisplayHTTPConfiguration.java 5     12/11/17 1:29p Heller $ 
package de.mendelson.util.httpconfig.gui;

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
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class ResourceBundleDisplayHTTPConfiguration extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "Server side HTTP configuration"},
        {"reading.configuration", "Reading HTTP configuration..."},
        {"button.ok", "Close" },
        {"label.info.configfile", "This dialog contains the server side HTTP/S configuration. You could set up the ports, protocols and ciphers in the file \"{0}\" on the server. Please restart the server for the changes to be applied." },
        {"tab.misc", "Misc"},
        {"tab.cipher", "SSL/TLS cipher"},
        {"tab.protocols", "SSL/TLS protocols"},
        {"no.ssl.enabled", "The TLS/SSL support has not been enabled in the underlaying HTTP server.\nPlease modify the configuration file {0}\naccording to the documentation and restart the server." },
        {"info.cipher", "The following ciphers are supported by the underlying HTTP server.\nWhich ones are supported depends on your used Java VM (it''s {1}).\nYou can disable single ciphers in the configuration file\n(\"{0}\")." },
        {"info.protocols", "The following protocols are supported by the underlying HTTP server.\nWhich ones are supported depends on your used Java VM (it''s {1}).\nYou can disable single protocols in the configuration file\n(\"{0}\")." },
        {"no.embedded.httpserver", "You did not start the embedded HTTP server.\nThere is no information available." },
    };
}