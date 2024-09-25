//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferencesInterface_fr.java 2     8/11/23 11:07 Heller $
package de.mendelson.comm.as2.preferences;

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
 *
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 2 $
 */
public class ResourceBundlePreferencesInterface_fr extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {             
        {"label.cem", "Permettre l''�change de certificat (CEM)"},
        {"label.outboundstatusfiles", "�crire des fichiers de statut de transaction sortante"},        
        {"label.showsecurityoverwrite", "Gestion des partenaires: Remplacer les param�tres de s�curit� de la station locale" },
        {"label.showsecurityoverwrite.help", "<HTML><strong>Remplacer les param�tres de s�curit� de la station locale</strong><br><br>"
            + "Si vous activez cette option, un onglet suppl�mentaire s''affiche pour chaque partenaire "
            + "dans la gestion des partenaires.<br>Vous pouvez y d�finir les cl�s priv�es qui seront utilis�es "
            + "dans tous les cas pour ce partenaire � l''entr�e et � la sortie - "
            + "ind�pendamment des param�tres de la station locale correspondante.<br><br>"
            + "Cette option vous permet d'utiliser des cl�s priv�es diff�rentes pour chaque partenaire "
            + "avec la m�me station locale. Il s''agit d'une option de compatibilit� avec d''autres produits AS2 - "
            + "certains syst�mes ont exactement les m�mes exigences, "
            + "mais qui n�cessitent une configuration de relations de partenaires et non de partenaires individuels." 
            + "</HTML>"},
        {"label.showhttpheader", "Gestion des partenaires: Laissez configurer les en-t�tes de HTTP"},
        {"label.showhttpheader.help", "<HTML><strong>Laissez configurer les en-t�tes de HTTP</strong><br><br>"
            + "Si vous activez cette option, un onglet suppl�mentaire s''affiche par partenaire dans "
            + "la gestion des partenaires, dans lequel vous pouvez d�finir des en-t�tes "
            + "HTTP personnalis�s pour l''envoi de donn�es � ce partenaire."
            + "</HTML>"},
        {"label.showquota", "Gestion des partenaires: Laissez configurer l''avis de quote-part"},
    };
}
