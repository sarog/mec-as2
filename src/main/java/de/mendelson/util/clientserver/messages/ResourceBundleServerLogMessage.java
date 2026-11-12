//$Header: /oftp2/de/mendelson/util/clientserver/messages/ResourceBundleServerLogMessage.java 1     25/09/25 10:41 Heller $
package de.mendelson.util.clientserver.messages;

import de.mendelson.util.MecResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize the mendelson products
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class ResourceBundleServerLogMessage extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"client.server.transmission.size.cut", "The rest has been cut by the system, for the full text please visit the log directory and have a look at the server log files" },
    };
}
