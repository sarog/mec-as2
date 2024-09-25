//$Header: /mec_as2/de/mendelson/comm/as2/message/ResourceBundleMDNParser.java 3     3-03-16 1:47p Heller $
package de.mendelson.comm.as2.message;
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
public class ResourceBundleMDNParser extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"invalid.mdn.nocontenttype", "Invalid inbound MDN: No content type found" },
        {"structure.failure.mdn", "An inbound MDN has been parsed and there is a structure failure in the MDN (\"{0}\"). The MDN is not valid and could not be processed, the state of the referenced AS2 message/transaction has not been changed." },
    };
    
}