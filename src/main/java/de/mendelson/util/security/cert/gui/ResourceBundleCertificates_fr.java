//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleCertificates_fr.java 15    6/28/17 3:07p Heller $
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
 * @version $Revision: 15 $
 */
public class ResourceBundleCertificates_fr extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"display.ca.certs", "Afficher les certificats CA"},
        {"button.delete", "Suppression clef/certificat"},
        {"button.edit", "Renommer l''alias"},
        {"button.newkey", "Importer clef"},
        {"button.newcertificate", "Importer certificat"},
        {"button.export", "Exporter certificat"},
        {"menu.file", "Fichier"},
        {"menu.file.close", "Fermer"},
        {"menu.import", "Import"},
        {"menu.export", "Export"},
        {"menu.tools", "Tools"},
        {"menu.tools.generatekey", "G�n�rer une nouvelle cl� (Self signed)"},
        {"menu.tools.generatecsr", "Confiance au certificat: G�n�rer la CSR (en CA)"},
        {"menu.tools.generatecsr.renew", "Renouveler le certificat: G�n�rer la CSR (en CA)"},
        {"menu.tools.importcsr", "Confiance au certificat: R�ponse de CAs en mati�re de CSR d''importation"},
        {"menu.tools.importcsr.renew", "Renouveler le certificat: R�ponse de CAs en mati�re de CSR d''importation"},
        {"label.selectcsrfile", "Veuillez s�lectionner le fichier pour enregistrer la CSR"},
        {"label.cert.import", "Importer certificat (de votre partenaire commercial)"},
        {"label.cert.export", "Exporter certificat (pour votre partenaire commercial)"},
        {"label.key.import.pem", "Importer votre propre clef priv�e (depuis un PEM)"},
        {"label.key.import.pkcs12", "Importer votre propre clef priv�e (depuis un PKCS#12)"},
        {"label.key.import.jks", "Importer votre propre clef priv�e (depuis un JKS, format porte-clef JAVA)"},
        {"label.key.export.pkcs12", "Exporter votre propre clef priv�e (PKCS#12) (pour sauvegarde seulement !)"},
        {"label.keystore", "Dossier de keystore:"},
        {"title.signencrypt", "Certificats et clefs disponibles (encryption, signature)"},
        {"title.ssl", "Certificats et clefs disponibles (SSL)"},
        {"button.ok", "Valider"},
        {"button.cancel", "Annuler"},
        {"filechooser.certificate.import", "Merci de s�lectionner le fichier certificat pour l''import"},
        {"certificate.import.success.message", "Le certificat a �t� import� avec succ�s ({0})."},
        {"certificate.ca.import.success.message", "Le certificat a �t� import� avec succ�s (CA, {0})."},
        {"certificate.import.success.title", "Succ�s"},
        {"certificate.import.error.message", "Une erreur a eu lieu lors du processus d''import.\n{0}"},
        {"certificate.import.error.title", "Erreur"},
        {"certificate.import.alias", "Alias de certificat � utiliser:"},
        {"keystore.readonly.message", "Le porte-clef est en lecture seule.\nCette op�ration n''est pas permise."},
        {"keystore.readonly.title", "Porte-clef r/o"},
        {"modifications.notalllowed.message", "Modifications ne sont pas possibles"},
        {"generatekey.error.message", "{0}"},
        {"generatekey.error.title", "Erreur lors de la g�n�ration de cl�s"},
        {"tab.info.basic", "Base"},
        {"tab.info.extension", "Extension"},
        {"tab.info.trustchain", "Cha�ne de confiance"},        
        {"dialog.cert.delete.message", "Vous voulez vraiment supprimer le certificat avec le \"{0}\" alias?"},
        {"dialog.cert.delete.title", "Supprimer le certificat"},
        {"title.cert.in.use", "Le certificat est en cours d'utilisation"},
        {"cert.delete.impossible", "Il est impossible de supprimer l''entr�e:"},
        {"module.locked", "Cette gestion des certificats est verrouill� par un autre client, vous n'�tes pas autoris� � valider vos modifications!"},
    };
}