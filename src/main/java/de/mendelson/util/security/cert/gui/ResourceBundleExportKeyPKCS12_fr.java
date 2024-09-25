//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleExportKeyPKCS12_fr.java 8     2/11/23 15:53 Heller $
package de.mendelson.util.security.cert.gui;

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
 * @version $Revision: 8 $
 */
public class ResourceBundleExportKeyPKCS12_fr extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"button.ok", "Valider"},
        {"button.cancel", "Annuler"},
        {"button.browse", "Parcourir..."},
        {"keystore.contains.nokeys", "Ce porte-clef ne contient aucune clef priv�e."},
        {"label.exportdir", "R�pertoire d''exportation"},
        {"label.exportdir.hint", "R�pertoire dans lequel la liste de cl�s doit �tre cr��e (PKCS#12)"},
        {"label.exportdir.help", "<HTML><strong>R�pertoire d''exportation</strong><br><br>"
            + "Veuillez indiquer ici le r�pertoire d''exportation dans lequel la cl� priv�e doit �tre export�e.<br>"
            + "Pour des raisons de s�curit�, la cl� n''est pas transf�r�e sur le client, "
            + "c''est pourquoi seule une sauvegarde c�t� serveur est possible.<br><br>"
            + "Le syst�me cr�era dans ce r�pertoire un fichier m�moire contenant un horodateur."
            + "</HTML>"},
        {"label.keypass", "Mot de passe"},
        {"label.keypass.hint", "Mot de passe pour keystore export�"},
        {"title", "Export de la clef vers le porte-clef (PKCS#12 format)"},
        {"filechooser.key.export", "Veuillez s�lectionner le r�pertoire d''exportation sur le serveur"},
        {"key.export.success.title", "Succ�s"},
        {"key.export.error.message", "Une erreur a eu lieu lors du processus d''export.\n{0}"},
        {"key.export.error.title", "Erreur"},
        {"label.alias", "Clef"},
        {"key.exported.to.file", "La clef \"{0}\" a �t� ins�r�e dans le porte-clef \"{1}\" (PKCS#12)"},};

}
