//$Header: /oftp2/de/mendelson/util/clientserver/codec/ResourceBundleServerDecoder.java 1     24.09.12 10:23 Heller $
package de.mendelson.util.clientserver.codec;

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
public class ResourceBundleServerDecoder extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] contents = {
        {"client.incompatible", "Unable to establish the client-server connection. The client is not compatible to the server, please use the proper client version."},};
}