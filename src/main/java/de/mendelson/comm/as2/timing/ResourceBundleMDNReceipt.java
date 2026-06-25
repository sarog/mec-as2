//$Header: /as2/de/mendelson/comm/as2/timing/ResourceBundleMDNReceipt.java 6     29/07/25 9:30 Heller $
package de.mendelson.comm.as2.timing;
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
 * @version $Revision: 6 $
 */
public class ResourceBundleMDNReceipt extends MecResourceBundle{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"expired", "The wait time for the MDN related to the message \"{0}\" expired. Sender: {1}, Receiver: {2}, Initial date of the message: {3}" },
    };
    
}