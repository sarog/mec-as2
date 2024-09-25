//$Header: /oftp2/de/mendelson/util/security/ResourceBundleKeyStoreUtil_de.java 3     3/31/17 10:56a Heller $
package de.mendelson.util.security;
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
 * @version $Revision: 3 $
 */
public class ResourceBundleKeyStoreUtil_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {                
        {"alias.exist", "Ein Eintrag mit dem Alias \"{0}\" ist bereits in dem unterliegenden Keystore vorhanden." },
        {"readerror.invalidcert", "Die ist kein gültiges Zertifikat oder es verwendet ein nicht unterstütztes Encoding." },
        {"readerror.zipcert", "Dies ist kein gültiges Zertifikat, sondern ein zip Archiv." },
        {"privatekey.notfound", "Der Keystore beinhaltet keinen privaten Schlüssel mit dem Alias \"{0}\"." },
    };
    
}