//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleRenameEntry_de.java 1     23.07.10 13:19 Heller $ 
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
public class ResourceBundleRenameEntry_de extends MecResourceBundle{

  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
        
    {"button.ok", "Ok" },
    {"button.cancel", "Abbrechen" },
    {"label.newalias", "Neuer Alias:" },
    {"label.keypairpass", "Schlüssel Password:" }, 
    {"title", "Bestehenden Eintrag ({0}) umbenennen" }
  };		
  
}