//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleHttpUploader_fr.java 31    18/06/25 12:21 Heller $
package de.mendelson.comm.as2.send;

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
* @version $Revision: 31 $
*/
public class ResourceBundleHttpUploader_fr extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"answer.no.sync.empty", "L''accusé de réception synchrone reçu est vide. Il y a probablement eu un problème lors du traitement des messages AS2 de la part de votre partenaire - veuillez contacter votre partenaire en conséquence."},
		{"answer.no.sync.mdn", "L''accusé de réception synchrone reçu n''est pas dans le bon format. Comme les problèmes de structure MDN sont inhabituels, il se peut qu''il ne s''agisse pas d''une réponse du système AS2 auquel vous vouliez vous adresser, mais peut-être de la réponse d''un proxy ou d''un site web standard ? Les valeurs d''en-tête HTTP suivantes sont manquantes : [{0}].\nLes données reçues commencent par les structures suivantes :\n{1}"},
		{"connected.to", "Connecté à {0}, attend un MDN et maintient la connexion ouverte jusqu''à {1}."},
		{"connection.shut.down", "La connexion sortante vers {0} a été fermée, elle était ouverte à {1}s"},
		{"connection.tls.info", "Connexion TLS sortante établie [{0}, {1}]"},
		{"error.http502", "Problème de connexion, aucune donnée n''a pu être transmise. (HTTP 502 - BAD GATEWAY)"},
		{"error.http503", "Problème de connexion, aucune donnée n''a pu être transmise. (HTTP 503 - SERVICE INDISPONIBLE)"},
		{"error.http504", "Problème de connexion, aucune donnée n''a pu être transmise. (HTTP 504 - GATEWAY TIMEOUT)"},
		{"error.httpupload", "Échec de la transmission, le serveur AS2 distant signale \"{0}\"."},
		{"error.noconnection", "Problème de connexion, aucune donnée n''a pu être transmise."},
		{"hint.ConnectTimeoutException", "Remarque :\nIl s''agit en général d''un problème d''infrastructure qui n''a rien à voir avec le protocole AS2. Il n''est pas possible d''établir une connexion sortante avec votre partenaire.\nVeuillez vérifier les points suivants pour résoudre le problème :\n*Est-ce que vous avez une connexion Internet active ?\n*Veuillez vérifier si vous avez saisi la bonne URL de réception de votre partenaire dans la gestion des partenaires ?\n*Veuillez contacter votre partenaire, il se peut que son système AS2 ne soit pas disponible ?"},
		{"hint.SSLException", "Remarque :\nIl s''agit généralement d''un problème de négociation au niveau du protocole. Votre partenaire a rejeté votre connexion.\nSoit votre partenaire s''attendait à une connexion sécurisée (HTTPS) et vous avez voulu établir une connexion non sécurisée, soit l''inverse.\nIl est également possible que votre partenaire exige une autre version de TLS ou un autre algorithme de cryptage que celui que vous proposez."},
		{"hint.SSLPeerUnverifiedException", "Remarque :\nCe problème s''est produit pendant le handshake TLS. Le système n''a donc pas pu établir de connexion sécurisée avec votre partenaire, le problème n''a rien à voir avec le protocole AS2.\nVeuillez vérifier les points suivants :\n*Avez-vous importé tous les certificats de votre partenaire dans votre Keystore TLS (pour TLS, y compris les certificats intermédiaires/root) ?\n*Votre partenaire a-t-il importé tous vos certificats (pour TLS, y compris les certificats intermédiaires/root) ?"},
		{"hint.httpcode.signals.problem", "Remarque :\nUne connexion a été établie avec votre hôte partenaire - un serveur web y fonctionne.\nLe serveur distant signale que quelque chose ne va pas avec le chemin ou le port de la requête et renvoie le code HTTP {0}.\nVeuillez utiliser un moteur de recherche Internet si vous avez besoin de plus d''informations sur ce code HTTP."},
		{"httpheader.deleted", "L''en-tête HTTP \"{0}\" a été supprimé à cause des paramètres personnalisés de l''en-tête HTTP."},
		{"httpheader.replaced", "La valeur de l''en-tête HTTP \"{0}\" a été remplacée par la valeur définie par l''utilisateur \"{1}\"."},
		{"httpheader.set", "L''en-tête HTTP \"{0}\" a été remplacé par la valeur définie par l''utilisateur \"{1}\"."},
		{"returncode.accepted", "Message envoyé avec succès (HTTP {0}) ; {1} transféré dans {2} [{3}]."},
		{"returncode.ok", "Message envoyé avec succès (HTTP {0}) ; {1} envoyé en {2} [{3}]."},
		{"sending.cem.async", "Envoyer le message CEM sur {0}, attendre MDN asynchrone pour confirmation de réception sur {1}."},
		{"sending.cem.sync", "Envoyer le message CEM à {0}, attendre MDN synchrone pour confirmation de réception."},
		{"sending.mdn.async", "Envoyer un accusé de réception asynchrone (MDN) à {0}."},
		{"sending.msg.async", "Envoyer le message AS2 à {0}, attendre le MDN asynchrone pour confirmation de réception sur {1}."},
		{"sending.msg.sync", "Envoyer le message AS2 à {0}, attendre MDN synchrone pour confirmation de réception."},
		{"strict.hostname.check", "Lors de la connexion TLS sortante, un contrôle strict du nom d''hôte est effectué en ce qui concerne le certificat du serveur."},
		{"strict.hostname.check.skipped.selfsigned", "TLS : la vérification stricte du nom d''hôte a été ignorée - le serveur distant utilise un certificat self signed."},
		{"trust.all.server.certificates", "La connexion TLS sortante fera confiance à tous les certificats du serveur distant si le certificat racine et le certificat intermédiaire sont disponibles."},
		{"using.proxy", "Utiliser le proxy {0}:{1}."},
		{"using.proxy.auth", "Utiliser le proxy {0}:{1} (authentification en tant que {2})."},
                {"httpheader.added.basicauth", "L''en-tête HTTP pour l'authentification de base a été ajouté à la demande d''envoi." },
	};
}
