//$Header: /mendelson_business_integration/de/mendelson/util/security/csr/ResourceBundleCSR_fr.java 2     3/20/17 3:07p Heller $
package de.mendelson.util.security.csr;

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
public class ResourceBundleCSR_fr extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] contents = {
        {"label.selectcsrfile", "Veuillez sélectionner le fichier pour enregistrer la CSR"},
        {"csr.title", "Confiance au certificat: Certificate Sign Request"},
        {"csr.title.renew", "Renouveler le certificat: Certificate Sign Request"},
        {"csr.message.storequestion", "Souhaitez-vous faire confiance à la clé à la CA Mendelson \nou stocker le CSR vers un fichier?"},
        {"csr.message.storequestion.renew", "Souhaitez-vous renouveler la clé à la CA Mendelson \nou stocker le CSR vers un fichier?"},
        {"csr.generation.success.message", "La CSR a été écrite dans le fichier\n\"{0}\".\nVeuillez envoyer la demande générée à votre autorité de certification."},
        {"csr.option.1", "Trust à Mendelson CA"},
        {"csr.option.1.renew", "Renouveler à Mendelson CA"},
        {"csr.option.2", "Stocker dans un fichier"},
        {"csr.generation.success.title", "CSR générée avec succès"},
        {"csr.generation.failure.title", "Génération de CSR a échoué"},
        {"csr.generation.failure.message", "{0}"},
        {"label.selectcsrrepsonsefile", "Veuillez sélectionner le fichier de réponse CA"},
        {"csrresponse.import.success.message", "La clé a été patchée avec succès avec la réponse de CA."},
        {"csrresponse.import.success.title", "Chemin de confiance clé établi"},
        {"csrresponse.import.failure.message", "{0}"},
        {"csrresponse.import.failure.title", "Problème"},};
}
