//$Header: /as2/de/mendelson/util/tables/hideablecolumns/ResourceBundleHideableColumns_fr.java 1     10.07.15 15:28 Heller $
package de.mendelson.util.tables.hideablecolumns;
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
public class ResourceBundleHideableColumns_fr extends MecResourceBundle{

  @Override
  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {
   
    {"header.column", "Colonne" },
    {"header.visible", "Visible" },
    {"title", "Configuration de colonne" },
    {"label.info", "S''il vous plaît sélectionner les colonnes visibles ci-dessous." },
    {"header.icon", "[Image] - toujours visible" },
    {"label.ok", "Ok" },
  };		
  
    
}