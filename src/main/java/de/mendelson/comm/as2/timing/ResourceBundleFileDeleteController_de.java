//$Header: /as2/de/mendelson/comm/as2/timing/ResourceBundleFileDeleteController_de.java 1     27.08.14 12:01 Heller $
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
 * @version $Revision: 1 $
 */
public class ResourceBundleFileDeleteController_de extends MecResourceBundle{
    
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"autodelete", "{0}: Die Datei ist älter als {1} Tage und wurde automatisch vom Systempflegeprozess gelöscht." },    
    };
    
}