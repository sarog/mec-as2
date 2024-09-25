//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleExportKeystore_fr.java 2     2/11/23 15:53 Heller $ 
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
 * @version $Revision: 2 $
 */
public class ResourceBundleExportKeystore_fr extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"button.ok", "Ok"},
        {"button.cancel", "Annuler"},
        {"button.browse", "Parcourir"},
        {"label.exportdir", "R�pertoire d''exportation"},
        {"label.exportdir.hint", "R�pertoire dans lequel le fichier keystore est cr��"},
        {"label.exportdir.help", "<HTML><strong>R�pertoire d''exportation</strong><br><br>"
            + "Veuillez indiquer le r�pertoire d''exportation vers lequel la base de donn�es<br>"
            + "de cl�s doit �tre export�e.<br>"
            + "Pour des raisons de s�curit�, les cl�s ne sont pas transf�r�es au client,<br>"
            + "de sorte que seule la sauvegarde c�t� serveur est possible.<br>"
            + "Le syst�me cr�era un fichier de sauvegarde dans ce r�pertoire, qui contiendra un horodatage."
            + "</HTML>"},
        {"label.keypass", "Mot de passe"},
        {"label.keypass.hint", "Mot de passe export� du keystore"},
        {"label.keypass.help", "<HTML><strong>Mot de passe</strong><br><br>"
            + "Il s''agit du mot de passe avec lequel la base de donn�es export�e c�t� serveur est "
            + "s�curis�e.<br>Veuillez saisir \"test\" si cette base de donn�es doit "
            + "�tre automatiquement import�e ult�rieurement dans le produit mendelson."
            + "</HTML>"},
        {"title", "Exporter toutes les entr�es dans la base de donn�es des cl�s"},
        {"filechooser.key.export", "Veuillez s�lectionner le r�pertoire d'exportation sur le serveur"},
        {"keystore.export.success.title", "Succ�s"},
        {"keystore.export.error.message", "Une erreur s''est produite au cours du processus d''exportation.\n{0}"},
        {"keystore.export.error.title", "Erreur"},
        {"keystore.exported.to.file", "Le fichier keystore a �t� �crit dans le fichier keystore \"{0}\"."},};

}
