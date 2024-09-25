//$Header: /as2/de/mendelson/comm/as2/message/loggui/ResourceBundleFileDisplay.java 5     18.08.11 16:28 Heller $
package de.mendelson.comm.as2.message.loggui;
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
public class ResourceBundleFileDisplay extends MecResourceBundle{
    
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        
        {"no.file", "** NO DATA AVAILABLE **" },
        {"file.notfound", "** FILE {0} IS NO LONGER AVAILABLE **" },
        {"file.tolarge", "** {0}: DATA SIZE TO LARGE TO DISPLAY **" },
    };
    
}