//$Header: /as2/de/mendelson/comm/as2/timing/ResourceBundleFileDeleteController_fr.java 1     27.08.14 12:01 Heller $
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
 * @version $Revision: 1 $
 */
public class ResourceBundleFileDeleteController_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"autodelete", "{0}: ce fichier est plus vieux que {1} jours et a été supprimé par le processus de maintenance du système." },    
    };
    
}
