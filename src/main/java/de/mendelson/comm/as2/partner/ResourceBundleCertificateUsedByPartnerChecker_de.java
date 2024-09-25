//$Header: /as2/de/mendelson/comm/as2/partner/ResourceBundleCertificateUsedByPartnerChecker_de.java 2     3.06.11 16:52 Heller $ 
package de.mendelson.comm.as2.partner;
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
 * @version $Revision: 2 $
 */
public class ResourceBundleCertificateUsedByPartnerChecker_de extends MecResourceBundle{

    @Override
  public Object[][] getContents() {
    return contents;
  }

  /**List of messages in the specific language*/
  static final Object[][] contents = {        
    {"used.crypt", "Verwendet vom Partner {0} (Verschlüsselung)." },
    {"used.sign", "Verwendet vom Partner {0} (Digitale Signatur)." },
  };		
  
}