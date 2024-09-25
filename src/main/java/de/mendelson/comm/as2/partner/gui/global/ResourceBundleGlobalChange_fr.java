//$Header: /as2/de/mendelson/comm/as2/partner/gui/global/ResourceBundleGlobalChange_fr.java 2     26/08/22 14:17 Heller $
package de.mendelson.comm.as2.partner.gui.global;
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
 * @version $Revision: 2 $
 */
public class ResourceBundleGlobalChange_fr extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "Changements globaux pour tous les partenaires" },
        {"button.ok", "Fermer" },    
        {"button.set", "D�finir" },
        {"partnersetting.changed", "Changement de r�glage pour {0} partenaires." },
        {"partnersetting.notchanged", "Les param�tres n''ont pas �t� modifi�s - valeur erron�e" },  
        {"info.text", "<HTML>Cette bo�te de dialogue vous permet de d�finir des valeurs pour les param�tres "
            + "de tous les partenaires en m�me temps. Si vous avez appuy� sur \"D�finir\", la valeur correspondante "
            + "est remplac�e pour <strong>TOUS<strong> les partenaires.</HTML>" },
        {"label.dirpoll", "Effectuer un sondage d''annuaire pour tous les partenaires" },
        {"label.maxpollfiles", "Nombre maximum de fichiers de tous les partenaires par processus de polling" },
        {"label.pollinterval", "R�pertoire Intervalle de poll de tous les partenaires" },
    };
    
}