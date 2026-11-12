//$Header: /oftp2/de/mendelson/util/systemevents/notification/ResourceBundleNotification_fr.java 19    13/06/25 15:23 Heller $
package de.mendelson.util.systemevents.notification;

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
* @version $Revision: 19 $
*/
public class ResourceBundleNotification_fr extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"authorization.credentials", "Utilisateur/mot de passe"},
		{"authorization.none", "AUCUNE"},
		{"authorization.oauth2", "OAUTH2"},
		{"authorization.oauth2.authorizationcode", "Code d''autorisation"},
		{"authorization.oauth2.clientcredentials", "Crédits clients"},
		{"do.not.reply", "Ne répondez pas à ce message."},
		{"misc.message.send", "Un e-mail de notification a été envoyé à {0} ({1}-{2}-{3})."},
		{"misc.message.send.failed", "L''envoi d''un e-mail de notification à {0} a échoué"},
		{"misc.message.summary.failed", "L''envoi d''un e-mail de notification récapitulatif à {0} a échoué"},
		{"misc.message.summary.send", "Un e-mail de notification récapitulatif a été envoyé à {0}."},
		{"module.name", "[NOTIFICATION D''EMAIL]"},
		{"notification.about.event", "Cette notification concerne l''événement système de {0}.\nUrgence : {1}\nSource : {2}\nType : {3}\nId : {4}"},
		{"notification.summary", "Résumé de {0} événements système"},
		{"notification.summary.info", "Vous recevez ce message récapitulatif parce que vous avez défini un nombre limité d''heures.\nvous avez défini des notifications par unité de temps.\nPour obtenir des détails sur les différents événements, veuillez démarrer\nle client et naviguez vers \"Événements système du fichier\".\nDans le masque de recherche, saisissez le numéro unique de l''événement.\nde l''événement."},
		{"test.message.debug", "\nL''envoi du message a échoué.\n"},
		{"test.message.send", "Un message de test a été envoyé à {0}."},
	};
}
