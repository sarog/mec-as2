//$Header: /as2/de/mendelson/comm/as2/sendorder/ResourceBundleSendOrderSender.java 3     8/08/17 11:08a Heller $
package de.mendelson.comm.as2.sendorder;
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
 * @version $Revision: 3 $
 */
public class ResourceBundleSendOrderSender extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"message.packed", "{0}: Outbound AS2 message created from \"{1}\" for the receiver \"{2}\" in {4}, raw message size: {3}, user defined id: \"{5}\"" },
        {"sendoder.sendfailed", "A problem occured during processing a send order: [{0}] \"{1}\" - the data has not been transmitted to the partner." },
    };
    
}