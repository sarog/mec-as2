//$Header: /as2/de/mendelson/util/clientserver/ResourceBundleClientServer.java 2     2/11/23 15:53 Heller $
package de.mendelson.util.clientserver;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize the mendelson products - if you want to localize
 * eagle to your language, please contact us: localize@mendelson.de
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class ResourceBundleClientServer extends MecResourceBundle {

    private static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"clientserver.start", "Starting {0} client-server interface, listening on port {1}"},
        {"clientserver.started", "{0} client-server interface started."},
    };
}