//$Header: /as2/de/mendelson/util/security/crmf/ResourceBundleCRMF_fr.java 1     12/01/26 8:33 Heller $
package de.mendelson.util.security.crmf;

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
public class ResourceBundleCRMF_fr extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Annuler"},
		{"button.ok", "OK"},
		{"label.initial", "Initialisation (Initial Request)"},
		{"label.initial.help", "<HTML><strong>Initialisation (Initial Request)</strong><br><br>"
			+"Choisissez ceci pour le premier enregistrement auprès de l''AC. L''authentification se fait par votre mot de passe d''enregistrement initial.</HTML>"},
		{"label.key.encryption", "Clé de cryptage"},
		{"label.key.signature", "Clé de signature"},
		{"label.key.tls", "Clé TLS"},
		{"label.root.ca", "Sub-CA Certificat racine"},
		{"label.root.ca.help", "<HTML><strong>Sub-CA Certificat racine</strong><br><br>"
			+"La demande CRMF générée est encapsulée dans une structure de message CMP qui contient un champ destinataire (Recipient).<br>"
			+"Pour garantir une transmission conforme aux standards, ce champ destinataire doit contenir le Distinguished Name (DN) spécifique de l''AC de l''espace de noms X.500. Vous le trouverez dans le champ \"Subject\" (sujet) du certificat propre à l''AC (par ex. CN=SM-Test-PKI-DE). Cela garantit que la structure du message généré est conforme aux normes BDEW/Smart-Metering-PKI. Pour une correspondance exacte de cette valeur, le (sous-)certificat racine de l''AC est nécessaire.</HTML>"},
		{"label.update", "Mise à jour (Update Request)"},
		{"label.update.help", "<HTML><strong>Mise à jour (Update Request)</strong>.<br><br>"
			+"Choisissez ceci pour renouveler un certificat existant avant son expiration. L''authentification se fait automatiquement par votre certificat actuellement valable.</HTML>"},
		{"password.hint", "Mot de passe initial à usage unique"},
		{"success.body", "Le fichier CRMF a été enregistré sous {0}."},
		{"success.title", "Création de CRMF réussie"},
		{"title", "Création de demandes CRMF (BDEW)"},
	};
}
