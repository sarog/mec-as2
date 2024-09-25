//$Header: /as2/de/mendelson/comm/as2/server/ResourceBundleClientServerSessionHandlerLocalhost.java 4     5.06.14 12:05 Heller $
package de.mendelson.comm.as2.server;

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
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class ResourceBundleClientServerSessionHandlerLocalhost extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"only.localhost.clients", "The remote server is not configured to receive connections from other hosts but localhost."
            + " Please start the remote server with the parameter \"-allowallclients\" to change this behavior."},
        {"allowallclients.true", "**The AS2 server will accept incoming client connects from other hosts**"},
        {"allowallclients.false", "**The AS2 server accepts incoming client connections only from localhost**"},
    };
}