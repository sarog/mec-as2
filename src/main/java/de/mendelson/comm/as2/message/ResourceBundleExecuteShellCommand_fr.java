//$Header: /as2/de/mendelson/comm/as2/message/ResourceBundleExecuteShellCommand_fr.java 3     11.11.08 16:05 Heller $
package de.mendelson.comm.as2.message;
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
 * @version $Revision: 3 $
 */
public class ResourceBundleExecuteShellCommand_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"executing.receipt", "{0}: Exécution de la commande système sur réception d''un contenu." },
        {"executing.send", "{0}: Exécution de la commande système sur envoyer d''un contenu." },
        {"executing.command", "{0}: Commande système: \"{1}\"." },
        {"executed.command", "{0}: Commande système a exporté, returncode={1}." },
    };
    
}
