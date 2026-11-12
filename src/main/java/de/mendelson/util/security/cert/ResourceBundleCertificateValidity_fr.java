//$Header: /as4/de/mendelson/util/security/cert/ResourceBundleCertificateValidity_fr.java 1     14/01/26 16:23 Heller $
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
* ResourceBundle to localize a mendelson product
* @author S.Heller
* @version $Revision: 1 $
*/
public class ResourceBundleCertificateValidity_fr extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"state.1", "Expiré"},
		{"state.1024", "(CRL) Réponse expirée"},
		{"state.1048576", "(CRL) Problème non spécifique"},
		{"state.131072", "(CRL) L''URL n''a pas pu être extraite"},
		{"state.16384", "(CRL) Format non valide"},
		{"state.2048", "(CRL) Signature non valide"},
		{"state.256", "(CRL) Bloqué par la CA (Revoked)"},
		{"state.262144", "(CRL) Échec du téléchargement"},
		{"state.32768", "(CRL) Certificat non lisible"},
		{"state.4", "Hiérarchie de certification erronée"},
		{"state.4096", "(CRL) Extension manquante (Extension)"},
		{"state.4194304", "Clé de test mendelson publique - ne pas utiliser en production"},
		{"state.512", "(CRL) URL inaccessible"},
		{"state.524288", "(CRL) Schéma URL non pris en charge"},
		{"state.8192", "(CRL) URL erronée"},
		{"state.8388608", "(CRL) Émetteur manquant - veuillez importer le certificat supérieur"},
	};
}
