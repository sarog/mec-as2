//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferences_fr.java 92    9/09/25 16:23 Heller $
package de.mendelson.comm.as2.preferences;

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
 *
 * @author S.Heller
 * @version $Revision: 92 $
 */
public class ResourceBundlePreferences_fr extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"button.browse", "Parcourir"},
        {"button.cancel", "Annuler"},
        {"button.mailserverdetection", "Trouver le serveur de messagerie"},
        {"button.modify", "Modifier"},
        {"button.ok", "Ok"},
        {"button.testmail", "Envoyer un mail de test"},
        {"checkbox.notifycem", "Événements lors de l''échange de certificats (CEM)"},
        {"checkbox.notifycertexpire", "Avant l''expiration des quotas"},
        {"checkbox.notifyclientserver", "Problèmes de connexion client-serveur"},
        {"checkbox.notifyconnectionproblem", "En cas de problèmes de connexion"},
        {"checkbox.notifyfailure", "Après des problèmes de système"},
        {"checkbox.notifypostprocessing", "Problèmes de suivi"},
        {"checkbox.notifyresend", "Après des resends refusés"},
        {"checkbox.notifytransactionerror", "Après des erreurs dans les transactions"},
        {"dirmsg", "Répertoire des messages"},
        {"embedded.httpconfig.not.available", "Serveur HTTP non disponible ou problèmes d''accès au fichier de configuration"},
        {"event.notificationdata.modified.body", "Les données de notification ont été fournies par\n\n{0}\n\nvers\n\n{1}\n\n est modifié."},
        {"event.notificationdata.modified.subject", "Les paramètres de notification ont été modifiés"},
        {"event.preferences.modified.body", "Ancienne valeur : {0}\nNouvelle valeur : {1}"},
        {"event.preferences.modified.subject", "La valeur {0} des paramètres du serveur a été modifiée"},
        {"filechooser.keystore", "Veuillez sélectionner le fichier keystore (format JKS)."},
        {"filechooser.selectdir", "Veuillez sélectionner le répertoire à définir"},
        {"header.dirname", "Type"},
        {"header.dirvalue", "Répertoire"},
        {"info.restart.client", "Vous devez redémarrer le client pour que ces modifications soient valables !"},
        {"label.autodelete", "Suppression automatique"},
        {"label.colorblindness", "Soutien aux daltoniens"},
        {"label.country", "Pays/Région"},
        {"label.country.help", "<HTML><strong>Pays/Région</strong><br><br>"
            + "Ce paramètre ne contrôle essentiellement que le format de date utilisé pour l''affichage des données de transaction, etc. dans le client.</HTML>"},
        {"label.darkmode", "Mode sombre"},
        {"label.days", "Jours"},
        {"label.deletelogdirolderthan", "Des données de journaux antérieures à"},
        {"label.deletemsglog", "Consigner la suppression automatique de fichiers et d''entrées de journal"},
        {"label.deletemsglog.help", "<HTML><strong>Enregistrer la suppression automatique de fichiers et d''entrées de journal</strong>.<br><br>"
            + "Dans les paramètres, vous avez la possibilité de faire supprimer les anciens fichiers (maintenance du système).<br>"
            + "Si vous avez configuré cette option et que vous l''avez activée, chaque suppression d''un ancien fichier sera consignée.<br>"
            + "En outre, un événement système est également généré, ce qui peut vous informer de cette opération via la fonction de notification.</HTML>"},
        {"label.deletemsgolderthan", "Des enregistrements de transactions antérieurs à"},
        {"label.deletestatsolderthan", "A partir de données statistiques datant de plus de"},
        {"label.displaymode", "Présentation"},
        {"label.displaymode.help", "<HTML><strong>Représentation</strong><br><br>"
            + "Permet de définir l''un des modes de présentation pris en charge par le client.<br>"
            + "Cela peut également être défini par un paramètre de ligne de commande lors de l''appel.</HTML>"},
        {"label.hicontrastmode", "Mode contraste élevé"},
        {"label.httpport", "Port d''entrée HTTP"},
        {"label.httpport.help", "<HTML><strong>port d''entrée HTTP</strong><br><br>"
            + "Il s''agit du port pour les connexions entrantes non cryptées. Ce paramètre est transmis au serveur HTTP embarqué, vous devez redémarrer le serveur AS2 après une modification.<br>"
            + "Le port fait partie de l''URL à laquelle votre partenaire doit envoyer les messages AS2. Il s''agit de http://Host:<strong>Port</strong>/as2/HttpReceiver.<br><br>"
            + "La valeur par défaut est 8080.</HTML>"},
        {"label.httpsend.timeout", "HTTP/S Timeout d''envoi"},
        {"label.httpsend.timeout.help", "<HTML><strong>HTTP/S Timeout d''envoi</strong><br><br>"
            + "Il s''agit de la valeur du délai d''attente de la connexion réseau pour les connexions sortantes.<br>"
            + "Si, après ce délai, aucune connexion n''a été établie avec votre système partenaire, la tentative de connexion est interrompue et d''autres tentatives de connexion seront effectuées ultérieurement, le cas échéant, en fonction des paramètres de répétition.<br><br>"
            + "La valeur de préréglage est de 5000ms.</HTML>"},
        {"label.httpsport", "Port d''entrée HTTPS"},
        {"label.httpsport.help", "<HTML><strong>port d''entrée HTTPS</strong><br><br>"
            + "Il s''agit du port pour les connexions entrantes cryptées (TLS). Ce paramètre est transmis au serveur HTTP embarqué, vous devez redémarrer le serveur AS2 après une modification.<br>"
            + "Le port fait partie de l''URL à laquelle votre partenaire doit envoyer les messages AS2. Voici https://Host:<strong>Port</strong>/as2/HttpReceiver<br><br>"
            + "La valeur par défaut est 8443.</HTML>"},
        {"label.keystore.encryptionsign", "Keystore( cryptage, signature) :"},
        {"label.keystore.https", "Keystore (pour l''envoi via Https) :"},
        {"label.keystore.https.pass", "Mot de passe Keystore (pour l''envoi via Https) :"},
        {"label.keystore.pass", "Keystore Password (cryptage/signature numérique) :"},
        {"label.language", "Langue"},
        {"label.language.help", "<HTML><strong>Langue</strong><br><br>"
            + "Il s''agit de la langue d''affichage du client. Si vous exécutez le client et le serveur dans des processus différents (ce qui est recommandé), la langue du serveur peut être différente.<br>"
            + "La langue utilisée dans le protocole est toujours la langue du serveur.</HTML>"},
        {"label.litemode", "Mode clair"},
        {"label.loghttprequests", "Enregistrement des requêtes HTTP du serveur HTTP intégré"},
        {"label.loghttprequests.help", "<HTML><strong>HTTP Protocole de requête</strong>.<br><br>"
            + "Lorsqu''il est activé, le serveur HTTP embarqué (Jetty) écrit un journal des requêtes dans les fichiers <strong>log/yyyy_MM_dd.jetty.request.log</strong>. Ces fichiers journaux ne sont pas supprimés par la maintenance du système - veuillez les supprimer manuellement.<br><br>"
            + "Veuillez redémarrer le logiciel pour que les modifications de ce paramètre soient valables.</HTML>"},
        {"label.logmessageprocessing", "Journalisation avancée du traitement des messages"},
        {"label.logmessageprocessing.help", "<HTML><strong>Journalisation avancée du traitement des messages</strong>.<br><br>"
            + "Si elle est activée, des sorties étendues sont émises dans le journal pour le traitement des messages.</HTML>"},
        {"label.logpollprocess", "Informations sur le processus de poll des répertoires"},
        {"label.logpollprocess.help", "<HTML><strong>Informations sur le processus de sondage des répertoires</strong>.<br><br>"
            + "Si vous activez cette option, chaque interrogation d''un répertoire de départ est notée dans le journal.<br>"
            + "Comme il peut s''agir d''un grand nombre d''entrées, n''utilisez en aucun cas cette option en mode productif, mais uniquement à des fins de test.</HTML>"},
        {"label.mailaccount", "Compte de serveur de messagerie"},
        {"label.mailhost", "Serveur de messagerie (SMTP)"},
        {"label.mailhost.hint", "IP ou domaine du serveur"},
        {"label.mailpass", "Mot de passe du serveur de messagerie"},
        {"label.mailport", "Port"},
        {"label.mailport.help", "<HTML><strong>Port SMTP</strong><br><br>"
            + "En général, il s''agit de l''une de ces valeurs :<br>"
            + "<strong>25</strong> (port standard)<br>"
            + "<strong>465</strong> (port TLS, valeur obsolète)<br>"
            + "<strong>587</strong> (port TLS, valeur par défaut)<br>"
            + "<strong>2525</strong> (port TLS, valeur alternative, pas de standard)</HTML>"},
        {"label.mailport.hint", "Port SMTP"},
        {"label.max.inboundconnections", "Max connexions parallèles entrantes"},
        {"label.max.inboundconnections.help", "<HTML><strong>Max connexions parallèles entrantes</strong><br><br>"
            + "Il s''agit du nombre maximal de connexions entrantes parallèles qui peuvent être ouvertes depuis l''extérieur vers votre installation mendelson AS2. Cette valeur est valable pour l''ensemble du logiciel et n''est pas limitée à certains partenaires.<br>"
            + "Le réglage est transmis au serveur HTTP embarqué, vous devez redémarrer le serveur AS2 après une modification.<br><br>"
            + "Bien qu''il soit possible de limiter le nombre de connexions entrantes parallèles, il est préférable d''effectuer ce réglage sur votre pare-feu ou dans votre proxy en amont - cela s''applique alors à l''ensemble de votre système et non à un seul logiciel.<br><br>"
            + "La valeur de préréglage est 1000.</HTML>"},
        {"label.max.outboundconnections", "Max connexions parallèles sortantes"},
        {"label.max.outboundconnections.help", "<HTML><strong>Maximum de connexions parallèles sortantes</strong>.<br><br>"
            + "Il s''agit du nombre maximal de connexions sortantes parallèles que votre système va ouvrir.<br>"
            + "Cette valeur sert principalement à protéger votre système partenaire d''une surcharge due à des connexions entrantes de votre part.<br><br>"
            + "La valeur par défaut est 9999.</HTML>"},
        {"label.maxmailspermin", "Nombre max. de notifications/min"},
        {"label.maxmailspermin.help", "<HTML><strong>Nombre maximal de notifications/min</strong>.<br><br>"
            + "Pour éviter un trop grand nombre de courriers, vous pouvez regrouper les notifications en définissant le nombre maximum de notifications par minute.<br>"
            + "Cette fonction vous permet de recevoir des courriers contenant plusieurs notifications.</HTML>"},
        {"label.mdn.timeout", "Temps d''attente maximal sur MDN"},
        {"label.mdn.timeout.help", "<HTML><strong>Temps d''attente maximum pour les MDN</strong>.<br><br>"
            + "Le temps pendant lequel le système attend un MDN (Message Delivery Notification) pour un message AS2 envoyé avant de placer la transaction associée dans le statut \"échec\".<br>"
            + "Cette valeur est valable dans tout le système pour tous les partenaires.<br><br>"
            + "La valeur par défaut est de 30 min, le temps est compté à partir du moment où la connexion avec le partenaire a été établie avec succès.<br><br>"
            + "Dans le cas d''un MDN synchrone, la connexion avec le partenaire est maintenue ouverte jusqu''à ce qu''un MDN soit reçu sur le canal de retour ou que ce délai d''attente soit écoulé. Lorsqu''elle est écoulée, la connexion est interrompue, la transaction passe à l''état \"échec\" et l''éventuel traitement ultérieur est effectué. Cette transaction n''est pas répétée.<br><br>"
            + "Dans le cas d''un MDN asynchrone, on attend la connexion entrante du partenaire avec le MDN jusqu''à ce que ce temps d''attente soit écoulé. Si aucun MDN n''a été reçu après l''expiration du délai d''attente, la transaction correspondante est définie comme \"échouée\" et le traitement ultérieur éventuellement défini est exécuté. Ici aussi, la transaction n''est pas répétée.</HTML>"},
        {"label.min", "min"},
        {"label.notificationmail", "Destinataire de la notification Adresse e-mail"},
        {"label.notificationmail.help", "<HTML><strong>Destinataire de la notification Adresse e-mail</strong<br><br>"
            + "L''adresse e-mail du destinataire de la notification.<br>"
            + "Si la notification doit être envoyée à plusieurs destinataires, veuillez saisir ici une liste séparée par des virgules d''adresses de réception.</HTML>"},
        {"label.proxy.pass", "Mot de passe"},
        {"label.proxy.pass.hint", "Mot de passe de connexion proxy"},
        {"label.proxy.port.hint", "Port"},
        {"label.proxy.url", "URL du proxy"},
        {"label.proxy.url.hint", "IP ou domaine proxy"},
        {"label.proxy.use", "Utiliser un proxy HTTP pour les connexions sortantes HTTP/HTTPs"},
        {"label.proxy.useauthentification", "Utiliser l''authentification pour le proxy"},
        {"label.proxy.user", "Utilisateur"},
        {"label.proxy.user.hint", "Utilisateur de connexion proxy"},
        {"label.replyto", "Adresse de Replyto"},
        {"label.retry.max", "Nombre maximal de tentatives d''établissement de la connexion"},
        {"label.retry.max.help", "<HTML><strong>Nombre maximal de tentatives de connexion</strong>.<br><br>"
            + "Il s''agit du nombre de tentatives de reconnexion utilisées pour répéter les connexions avec un partenaire lorsqu''une connexion n''a pas pu être établie.<br>"
            + "Le temps d''attente entre ces tentatives de reconnexion peut être réglé dans la propriété <strong>Temps d''attente entre les reconnexions</strong>.<br><br>"
            + "La valeur par défaut est 10.</HTML>"},
        {"label.retry.waittime", "Temps d''attente entre les reconnexions"},
        {"label.retry.waittime.help", "<HTML><strong>Temps d''attente entre les reconnexions</strong>.<br><br>"
            + "Il s''agit du temps, en secondes, que le système attend avant de se reconnecter au partenaire.<br>"
            + "Une nouvelle tentative de connexion n''est effectuée que s''il n''a pas été possible d''établir une connexion avec un partenaire (p. ex. panne du système partenaire ou problème d''infrastructure).<br>"
            + "Le nombre de connexions répétées peut être configuré dans la propriété <strong>Nombre maximal de connexions répétées</strong>.<br><br>"
            + "La valeur de préréglage est de 30s.</HTML>"},
        {"label.sec", "s"},
        {"label.security", "Sécurité des connexions"},
        {"label.smtpauthorization.credentials", "Utilisateur/mot de passe"},
        {"label.smtpauthorization.header", "Autorisation SMTP"},
        {"label.smtpauthorization.none", "Pas de"},
        {"label.smtpauthorization.oauth2.authorizationcode", "OAuth2 (code d''autorisation)"},
        {"label.smtpauthorization.oauth2.clientcredentials", "OAuth2 (identifiants client)"},
        {"label.smtpauthorization.pass", "Mot de passe"},
        {"label.smtpauthorization.pass.hint", "Mot de passe du serveur SMTP"},
        {"label.smtpauthorization.user", "Utilisateur"},
        {"label.smtpauthorization.user.hint", "Nom d''utilisateur du serveur SMTP"},
        {"label.stricthostcheck", "TLS : contrôle strict du nom d''hôte"},
        {"label.stricthostcheck.help", "<HTML><strong>TLS : contrôle strict du nom d''hôte</strong>.<br><br>"
            + "Permet de définir si, dans le cas d''une connexion TLS sortante, il faut vérifier si le nom commun (CN) du certificat distant correspond à l''hôte distant.<br>"
            + "Ce contrôle ne s''applique qu''aux certificats certifiés.</HTML>"},
        {"label.trustallservercerts", "TLS : faire confiance à tous les certificats de serveur final de vos partenaires AS2"},
        {"label.trustallservercerts.help", "<HTML><strong>TLS : faire confiance à tous les certificats de serveur final de vos partenaires AS2</strong>.<br><br>"
            + "Normalement, TLS exige que tous les certificats de la chaîne de confiance du système AS2 de votre partenaire soient conservés dans votre gestionnaire de certificats TLS.<br><br>"
            + "Si vous activez cette option, vous faites confiance au certificat final de votre système partenaire lors de l''établissement de la connexion sortante, si vous ne conservez que les certificats racine et intermédiaire correspondants dans le gestionnaire de certificats TLS.<br>"
            + "Veuillez noter que cette option n''a de sens que si votre partenaire utilise un certificat certifié.<br>"
            + "Les certificats auto-signés sont de toute façon toujours acceptés.<br><br>"
            + "<strong>Avertissement:</strong>L''activation de cette option diminue le niveau de sécurité, car des attaques man-in-the-middle sont possibles.</HTML>"},
        {"maintenancemultiplier.day", "jour(s)"},
        {"maintenancemultiplier.hour", "heure(s)"},
        {"maintenancemultiplier.minute", "minute(s)"},
        {"receipt.subdir", "Créer des sous-répertoires par partenaire pour la réception de messages"},
        {"receipt.subdir.help", "<HTML><strong>Sous-répertoires pour l''accueil</strong<br><br>"
            + "Définit si les données doivent être reçues dans le répertoire <strong>&lt;Station locale&gt;/inbox</strong> ou <strong>&lt;Station locale&gt;/inbox/&lt;Nom du partenaire&gt;</strong>.</HTML>"},
        {"remotedir.select", "Choisir un répertoire sur le serveur"},
        {"systemmaintenance.deleteoldlogdirs.help", "<HTML><strong>Effacer les anciens répertoires de journaux</strong>.<br><br>"
            + "Même si d''anciennes transactions ont été supprimées, il est encore possible de retracer les opérations via les fichiers journaux existants.<br>"
            + "Ce paramètre supprime ces fichiers journaux ainsi que tous les fichiers d''événements système qui tombent dans la même période.</HTML>"},
        {"systemmaintenance.deleteoldstatistic.help", "<HTML><strong>Effacer les anciennes données statistiques</strong>.<br><br>"
            + "Le système recueille les données de compatibilité des systèmes partenaires et peut les présenter sous forme de statistiques.<br>"
            + "Cela détermine la période pendant laquelle ces données sont conservées.</HTML>"},
        {"systemmaintenance.deleteoldtransactions.help", "<HTML><strong>Effacer les anciennes entrées de transaction</strong>.<br><br>"
            + "Cela détermine la période de temps pendant laquelle les transactions et les données temporaires associées doivent rester dans le système et être affichées dans le récapitulatif des transactions.<br>"
            + "Ces réglages n''affectent <strong>pas</strong> vos données/fichiers reçus, qui restent inchangés.<br>"
            + "Pour les transactions supprimées, le journal des transactions reste disponible via la fonctionnalité "
            + "de recherche dans le journal.<br><br>"
            + "Ce paramètre de maintenance nettoiera les répertoires associés //temp, //sent et //_rawincoming dans le système de fichiers du serveur."
            + "</HTML>"},
        {"tab.connectivity", "Connexions"},
        {"tab.dir", "Répertoires"},
        {"tab.interface", "Modules"},
        {"tab.language", "Client"},
        {"tab.log", "Protocole"},
        {"tab.maintenance", "Soins du système"},
        {"tab.misc", "Généralités"},
        {"tab.notification", "Notification"},
        {"tab.proxy", "Proxy"},
        {"tab.security", "Sécurité"},
        {"testmail", "Courrier de test"},
        {"testmail.message.error", "Erreur lors de l''envoi de l''e-mail de test :\n{0}"},
        {"testmail.message.success", "Un e-mail de test a été envoyé avec succès à {0}."},
        {"testmail.title", "Envoyer un e-mail de test"},
        {"title", "Réglages"},
        {"warning.changes.canceled", "L''utilisateur a interrompu la boîte de dialogue des paramètres - aucune modification n''a été apportée aux paramètres."},
        {"warning.clientrestart.required", "Les paramètres du client ont été modifiés - veuillez redémarrer le client pour qu''ils soient valides."},
        {"warning.serverrestart.required", "Veuillez redémarrer le serveur pour que ces modifications soient valables."},};
}
