//$Header: /as4/de/mendelson/util/security/cert/ResourceBundleCertificateManager_fr.java 9     13-07-16 2:01p Heller $
package de.mendelson.util.security.cert;

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
 * @version $Revision: 9 $
 */
public class ResourceBundleCertificateManager_fr extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"keystore.reloaded", "Les clefs priv�es et les certificats ont �t� recharg�s."},
        {"alias.notfound", "Le porte-clef ne contient aucun certificat sous l''alias \"{0}\"."},
        {"alias.hasno.privatekey", "Le porte-clef ne contient aucune clef priv�e sous l''alias \"{0}\"."},
        {"alias.hasno.key", "Le porte-clef ne contient aucun objet sous l''alias \"{0}\"."},
        {"certificate.not.found.fingerprint", "Le certificat avec le \"{0}\" d'empreintes SHA-1 n''existe pas."},
        {"certificate.not.found.fingerprint.withinfo", "Le certificat avec le \"{0}\" d'empreintes SHA-1 n''existe pas. ({1})" },
        {"certificate.not.found.subjectdn.withinfo", "Le certificat avec le \"{0}\" subjectDN n''existe pas. ({1})" },
        {"certificate.not.found.ski.withinfo", "Le certificat avec le \"{0}\" Subject Key Identifier n''existe pas. ({1})" },
        {"certificate.not.found.issuerserial.withinfo", "Le certificat avec \"{0}/{1}\" n''existe pas. ({2})"},
        {"keystore.read.failure", "Le syst�me est incapable de lire les certificats. Erreur: \"{0}\". S''il vous pla�t vous assurer que vous utilisez le mot de passe keystore correct."},
    };
}
