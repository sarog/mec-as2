//$Header: /as2/de/mendelson/comm/as2/client/manualsend/ResourceBundleManualSend_fr.java 7     19/01/23 11:19 Heller $
package de.mendelson.comm.as2.client.manualsend;

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
 * @author E.Pailleau
 * @version $Revision: 7 $
 */
public class ResourceBundleManualSend_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"button.ok", "Valider"},
        {"button.cancel", "Annuler"},
        {"button.browse", "Rechercher"},
        {"label.filename", "Envoyer a fichier"},
        {"label.filename.hint", "Fichier � envoyer � votre partenaire"},
        {"label.testdata", "Envoyer les donn�es de test"},
        {"label.partner", "Destinataire"},
        {"label.localstation", "Exp�diteur"},
        {"label.selectfile", "Merci de s�lectionner le fichier � envoyer"},
        {"title", "Envoyer un fichier � un partenaire"},
        {"send.success", "Le fichier a �t� mis en queue d''envoi avec succ�s."},
        {"send.failed", "Le fichier n''a pas �t� plac� dans le processus d''envoi en raison d''une erreur."},
    };
}
