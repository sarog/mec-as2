//$Header: /as2/de/mendelson/comm/as2/message/loggui/ResourceBundleFileDisplay_de.java 4     18.08.11 16:28 Heller $
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
 * @version $Revision: 4 $
 */
public class ResourceBundleFileDisplay_de extends MecResourceBundle{
    
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        
        {"no.file", "** KEINE DATEN VERF�GBAR **" },
        {"file.notfound", "** DATEI {0} IST NICHT L�NGER VERF�GBAR **" },
        {"file.tolarge", "** {0}: DATEN DIESER GR�SSE SIND NICHT ANZEIGBAR **" },
    };
    
}