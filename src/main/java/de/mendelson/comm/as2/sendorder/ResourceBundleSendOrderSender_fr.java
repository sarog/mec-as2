//$Header: /as2/de/mendelson/comm/as2/sendorder/ResourceBundleSendOrderSender_fr.java 3     8/08/17 11:08a Heller $
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
 * @author E.Pailleau
 * @version $Revision: 3 $
 */
public class ResourceBundleSendOrderSender_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"message.packed", "{0}: Message AS2 sortant cr�� avec \"{1}\" du destinataire \"{2}\" dans {4}, taille brute du message: {3}, id d�fini par l''utilisateur: \"{5}\"" },
        {"sendoder.sendfailed", "Un probl�me s'est produit lors du traitement d'une commande d'envoi: [{0}] \"{1}\" - les donn�es n''ont pas �t� transmises au partenaire." },
    };
    
}