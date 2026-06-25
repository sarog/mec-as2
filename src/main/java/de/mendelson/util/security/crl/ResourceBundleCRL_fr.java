//$Header: /as4/de/mendelson/util/security/crl/ResourceBundleCRL_fr.java 9     14/01/26 14:06 Heller $
package de.mendelson.util.security.crl;

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
 * @version $Revision: 9 $
 */
public class ResourceBundleCRL_fr extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"module.name", "[Liste de blocage]"},
        {"self.signed.skipped", "Auto-signé - vérification ignorée"},
        {"ca.skipped", "Racine CA ou ancrage de confiance - vérification ignorée" },
        {"crl.success", "Ok - le certificat n''est pas révoqué, valable jusqu'au {0}"},
        {"failed.revoked", "Le certificat est révoqué : {0}"},
        {"malformed.crl.url", "URL CRL erronée ({0})"},
        {"no.https", "Problème de connexion avec l''URI {0} - HTTPS n''est pas supporté"},
        {"bad.crl", "Les données CRL téléchargées ne sont pas traitables"},
        {"cert.read.error", "Impossible de lire le certificat pour l''URL de la liste de révocation"},
        {"error.url.retrieve", "Impossible de lire l''URL de la liste de révocation à partir du certificat"},
        {"no.crl.entry", "Le certificat n''a pas d''extension qui renvoie à une URL CRL."},
        {"download.failed.from", "Le téléchargement de la liste de révocation a échoué ({0})"},
        {"crl.expired", "L''information CRL reçue est expirée ({0})"},
        {"error.nextupdate.missing", "L'information CRL reçue ne contient aucune information sur sa durée de validité"},
        {"error.invalid.signature", "Erreur de sécurité - signature invalide dans la réponse"},
        {"error.invalid.nonce", "Erreur de sécurité - nonce invalide dans la réponse"},
        {"error.request.generation", "Problème lors de la génération de la requête ({0})"},
        {"error.issuercertificate.required", "Aucun certificat d'émetteur trouvé pour la vérification - veuillez importer le certificat parent" },
    };

}
