//$Header: /as2/de/mendelson/comm/as2/sendorder/ResourceBundleSendOrderSender_de.java 3     8/08/17 11:08a Heller $
package de.mendelson.comm.as2.sendorder;
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
public class ResourceBundleSendOrderSender_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"message.packed", "{0}: Ausgehende AS2 Nachricht aus \"{1}\" für den Empfänger \"{2}\" erstellt in {4}, Rohdatengrösse: {3}, benutzerdefinierte id: \"{5}\"" },
        {"sendoder.sendfailed", "Es trat ein Problem beim Verarbeiten eines Sendeauftrags auf: [{0}] \"{1}\" - die Daten wurden nicht an den Partner übermittelt." },
    };
    
}