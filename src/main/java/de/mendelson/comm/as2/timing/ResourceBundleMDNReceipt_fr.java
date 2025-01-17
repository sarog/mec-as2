//$Header: /as2/de/mendelson/comm/as2/timing/ResourceBundleMDNReceipt_fr.java 5     2/11/23 15:53 Heller $
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
 * @author E.Pailleau
 * @version $Revision: 5 $
 */
public class ResourceBundleMDNReceipt_fr extends MecResourceBundle{
    
    private static final long serialVersionUID = 1L;
    
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"expired", "Temps d''attente pour le MDN expiré." },    
    };
    
}
