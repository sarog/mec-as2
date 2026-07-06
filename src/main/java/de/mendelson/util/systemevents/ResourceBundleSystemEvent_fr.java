//$Header: /as4/de/mendelson/util/systemevents/ResourceBundleSystemEvent_fr.java 35    17/02/26 11:08 Heller $
package de.mendelson.util.systemevents;

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
* @version $Revision: 35 $
*/
public class ResourceBundleSystemEvent_fr extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"category.100", "Composant serveur"},
		{"category.1000", "Traitement des données"},
		{"category.100000", "Autres"},
		{"category.1100", "Activation"},
		{"category.1200", "Opération de fichier"},
		{"category.1300", "Opération client"},
		{"category.1400", "Interface XML"},
		{"category.1500", "Interface REST"},
		{"category.200", "Connexion"},
		{"category.300", "Transaction"},
		{"category.400", "Certificat"},
		{"category.500", "Base de données"},
		{"category.700", "Configuration"},
		{"category.800", "Contingent"},
		{"category.900", "Notification"},
		{"origin.1", "Système"},
		{"origin.2", "Utilisateur"},
		{"origin.3", "Transaction"},
		{"severity.1", "Info"},
		{"severity.2", "Avertissement"},
		{"severity.3", "Erreur"},
		{"type.100", "Serveur arrêté"},
		{"type.1000", "Traitement des données"},
		{"type.100000", "Non spécifié"},
		{"type.1001", "Prétraitement"},
		{"type.1002", "Traitement ultérieur"},
		{"type.101", "Démarrage du serveur"},
		{"type.102", "Serveur en cours d''exécution"},
		{"type.103", "Le serveur DB démarre"},
		{"type.104", "Serveur DB en cours d''exécution"},
		{"type.105", "Serveur DB arrêté"},
		{"type.106", "Le serveur HTTP démarre"},
		{"type.107", "Serveur HTTP en cours d''exécution"},
		{"type.108", "Serveur HTTP arrêté"},
		{"type.109", "Le serveur TRFC démarre"},
		{"type.110", "Le serveur TRFC fonctionne"},
		{"type.1100", "Licence"},
		{"type.1101", "Mise à jour de la licence"},
		{"type.1102", "Déroulement de la licence"},
		{"type.111", "Statut du serveur TRFC"},
		{"type.112", "Serveur TRFC arrêté"},
		{"type.113", "L''ordonnanceur démarre"},
		{"type.114", "Ordonnanceur en cours d''exécution"},
		{"type.115", "Ordonnanceur arrêté"},
		{"type.116", "Surveillance du répertoire (statut modifié)"},
		{"type.117", "Port de réception"},
		{"type.1200", "Opération de fichier"},
		{"type.1201", "Fichier (supprimer)"},
		{"type.1202", "Créer un répertoire"},
		{"type.1203", "Fichier (déplacer)"},
		{"type.1204", "Fichier (copier)"},
		{"type.1300", "Client"},
		{"type.1301", "Connexion utilisateur (succès)"},
		{"type.1302", "Connexion utilisateur (Échec)"},
		{"type.1303", "Séparation des utilisateurs"},
		{"type.1400", "XML"},
		{"type.1401", "Configuration du certificat"},
		{"type.1402", "Configuration du partenaire"},
		{"type.1500", "REST"},
		{"type.1501", "Ajouter un certificat"},
		{"type.1502", "Configuration du certificat"},
		{"type.1503", "Supprimer le certificat"},
		{"type.1504", "Ajouter un partenaire"},
		{"type.1505", "Configuration du partenaire"},
		{"type.1506", "Supprimer un partenaire"},
		{"type.1507", "Ordre d''envoi"},
		{"type.1508", "Transaction (supprimer)"},
		{"type.199", "Composant serveur"},
		{"type.200", "Connexion"},
		{"type.201", "Test de connexion"},
		{"type.300", "Transaction"},
		{"type.301", "Erreur de transaction"},
		{"type.302", "Transaction (nouvelle livraison refusée)"},
		{"type.303", "Transaction (double message)"},
		{"type.304", "Transaction (supprimer)"},
		{"type.305", "Transaction (annuler)"},
		{"type.306", "Transaction (envoyer à nouveau)"},
		{"type.400", "Certificat"},
		{"type.401", "Certificat (ajouté)"},
		{"type.402", "Certificat (alias modifié)"},
		{"type.403", "Certificat (supprimé)"},
		{"type.404", "Échange de certificats"},
		{"type.405", "Certificat expire"},
		{"type.406", "Échange de certificats (demande entrante)"},
		{"type.407", "Certificat (Keystore Import)"},
		{"type.500", "Base de données"},
		{"type.501", "Création de bases de données"},
		{"type.502", "Base de données (mise à jour)"},
		{"type.503", "Base de données (initialisation)"},
		{"type.504", "Transaction Rollback"},
		{"type.700", "Configuration"},
		{"type.701", "Changement de configuration"},
		{"type.702", "Contrôle de la configuration"},
		{"type.703", "Partenaires (modifié)"},
		{"type.704", "Partenaires (supprimé)"},
		{"type.705", "Partenaires (ajoutés)"},
		{"type.800", "Quota"},
		{"type.801", "Quota atteint"},
		{"type.802", "Quota atteint"},
		{"type.900", "Notification"},
		{"type.901", "Notification (envoi réussi)"},
		{"type.902", "Notification (échec de l''envoi)"},
	};
}
