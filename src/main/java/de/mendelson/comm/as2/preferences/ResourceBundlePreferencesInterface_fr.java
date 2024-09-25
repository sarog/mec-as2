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
        {"label.cem", "Permettre l''échange de certificat (CEM)"},
        {"label.outboundstatusfiles", "Écrire des fichiers de statut de transaction sortante"},        
        {"label.showsecurityoverwrite", "Gestion des partenaires: Remplacer les paramètres de sécurité de la station locale" },
        {"label.showsecurityoverwrite.help", "<HTML><strong>Remplacer les paramètres de sécurité de la station locale</strong><br><br>"
            + "Si vous activez cette option, un onglet supplémentaire s''affiche pour chaque partenaire "
            + "dans la gestion des partenaires.<br>Vous pouvez y définir les clés privées qui seront utilisées "
            + "dans tous les cas pour ce partenaire à l''entrée et à la sortie - "
            + "indépendamment des paramètres de la station locale correspondante.<br><br>"
            + "Cette option vous permet d'utiliser des clés privées différentes pour chaque partenaire "
            + "avec la même station locale. Il s''agit d'une option de compatibilité avec d''autres produits AS2 - "
            + "certains systèmes ont exactement les mêmes exigences, "
            + "mais qui nécessitent une configuration de relations de partenaires et non de partenaires individuels." 
            + "</HTML>"},
        {"label.showhttpheader", "Gestion des partenaires: Laissez configurer les en-têtes de HTTP"},
        {"label.showhttpheader.help", "<HTML><strong>Laissez configurer les en-têtes de HTTP</strong><br><br>"
            + "Si vous activez cette option, un onglet supplémentaire s''affiche par partenaire dans "
            + "la gestion des partenaires, dans lequel vous pouvez définir des en-têtes "
            + "HTTP personnalisés pour l''envoi de données à ce partenaire."
            + "</HTML>"},
        {"label.showquota", "Gestion des partenaires: Laissez configurer l''avis de quote-part"},
    };
}
