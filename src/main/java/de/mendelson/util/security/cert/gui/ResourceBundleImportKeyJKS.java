//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyJKS.java 1     23.07.10 13:19 Heller $ 
package de.mendelson.util.security.cert.gui;
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
 * @version $Revision: 1 $
 */
public class ResourceBundleImportKeyJKS extends MecResourceBundle{

  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
        
    {"button.ok", "Ok" },
    {"button.cancel", "Cancel" },
    {"button.browse", "Browse" },        
            
    {"keystore.contains.nokeys", "This keystore does not contain private keys." },
    
    {"label.importkey", "Import key file (JKS):" },
    {"label.keypass", "Key password for importing key:" },            
            
    {"title", "Import keys from keystore(JKS format)" },     
    {"filechooser.key.import", "Please select the JKS keystore file for the import" }, 
            
    {"multiple.keys.message", "Please select the key to import" },
    {"multiple.keys.title", "Keystore contains multiple keys" },
    
    {"key.import.success.message", "The key has been imported successfully." },
    {"key.import.success.title", "Success" },
    {"key.import.error.message", "There occured an error during the import process.\n{0}" },
    {"key.import.error.title", "Error" },        
            
    {"enter.keypassword", "Enter key password for \"{0}\"" },
    
  };		
  
}