//$Header: /as2/de/mendelson/comm/as2/sendorder/ResourceBundleSendOrderReceiver_fr.java 3     9/25/17 1:27p Heller $
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
public class ResourceBundleSendOrderReceiver_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"async.mdn.wait", "{0}: Attente du MDN asynchrone jusqu''à {1}." },
        {"max.retry.reached", "{0}: Le maximum a été atteint réessayer, la transmission annulée." },
        {"retry", "{0}: Va réessayer d''envoyer transmission après {1}s, réessayez {2}/{3}." },
        {"as2.send.disabled", "** Le système ne sera pas envoyer de message AS2/MDN parce que le nombre de connexions sortantes parallèles est mis à 0. S''il vous plaît modifier ces paramètres dans la boîte de dialogue de configuration du serveur pour permettre l''envoi de nouveau **" },        
        {"outbound.connection.prepare.mdn", "{0}: Préparer la connexion MDN sortante vers \"{1}\", connexions actives: {2}/{3}." },
        {"outbound.connection.prepare.message", "{0}: Préparer la connexion AS2 message sortante vers \"{1}\", Connexions actives: {2}/{3}." },
        {"send.connectionsstillopen", "Vous avez réduit le nombre de connexions sortantes à {0}, mais actuellement, il y a encore {1} connexions sortantes." },
    };
    
}
