//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferences_fr.java 64    4/10/22 10:12 Heller $
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
 * ResourceBundle to localize gui entries
 *
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 64 $
 */
public class ResourceBundlePreferences_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        //preferences localized
        {PreferencesAS2.DIR_MSG, "Archivage message"},
        {"button.ok", "Valider"},
        {"button.cancel", "Annuler"},
        {"button.modify", "Modifier"},
        {"button.browse", "Parcourir..."},
        {"filechooser.selectdir", "S�lectionner un r�pertoire"},
        {"title", "Pr�f�rences"},
        {"tab.language", "Client"},
        {"tab.dir", "R�pertoires"},
        {"tab.security", "S�curit�"},
        {"tab.proxy", "Proxy"},
        {"tab.misc", "Divers"},
        {"tab.maintenance", "Maintenance"},
        {"tab.notification", "Notification"},
        {"tab.interface", "Modules"},
        {"tab.log", "Journal"},
        {"tab.connectivity", "Connectivit�"},
        {"header.dirname", "Type"},
        {"header.dirvalue", "R�p."},
        {"label.language", "Langue"},
        {"label.language.help", "<HTML><strong>Langue</strong><br><br>"
            + "Il s''agit de la langue d''affichage du client. Si vous ex�cutez le client "
            + "et le serveur dans des processus diff�rents (ce qui est recommand�), "
            + "la langue du serveur peut �tre diff�rente. La langue utilis�e dans "
            + "le journal sera toujours la langue du serveur."
            + "</HTML>"},
        {"label.country", "Pays/R�gion"},
        {"label.country.help", "<HTML><strong>Pays/R�gion</strong><br><br>"
            + "Ce param�tre contr�le principalement le format de date qui est utilis� pour afficher les dates de transaction, etc. dans le client."
            + "</HTML>"},
        {"label.keystore.https.pass", "Mot de passe du porte-clef (envoi https):"},
        {"label.keystore.pass", "Mot de passe du porte-clef (encryption/signature):"},
        {"label.keystore.https", "Porte-clef (envoi https):"},
        {"label.keystore.encryptionsign", "Porte-clef (enc, sign):"},
        {"label.proxy.url", "URL du proxy:"},
        {"label.proxy.url.hint", "IP ou domaine du proxy"},
        {"label.proxy.port.hint", "Port"},
        {"label.proxy.user", "Utilisateur:"},
        {"label.proxy.user.hint", "Login utilisateur du proxy"},
        {"label.proxy.pass", "Mot de passe:"},
        {"label.proxy.pass.hint", "Mot de passe utilisateur du proxy"},
        {"label.proxy.use", "Utiliser un proxy HTTP pour les connexions sortante HTTP/HTTPs"},
        {"label.proxy.useauthentification", "Utiliser l''authentification aupr�s du proxy"},
        {"filechooser.keystore", "Merci de s�lectionner le fichier porte-clef (format jks)."},
        {"label.days", "jours"},
        {"label.deletemsgolderthan", "Supprimer automatiquement les messages plus vieux que"},
        {"label.deletemsglog", "Tenir informer dans le log � propos des messages automatiquement supprim�s"},
        {"label.deletestatsolderthan", "Supprimer automatiquement les statistiques qui sont plus vieux que"},
        {"label.deletelogdirolderthan", "Suppression automatique des donn�es de journal plus anciennes que"},
        {"label.asyncmdn.timeout", "Temps d''attente maximal pour un MDN asynchrone"},
        {"label.asyncmdn.timeout.help", "<HTML><strong>Temps d''attente maximal pour un MDN asynchrone</strong>"
            + "<br><br>Le temps que le syst�me attendra un MDN (message delivery notification) asynchrone pour un message AS2 envoy� avant de mettre la transaction en �tat d'�chec."
            + "</HTML>"},
        {"label.httpsend.timeout", "Timeout sur envoi HTTP/S"},
        {"label.httpsend.timeout.help", "<HTML><strong>Timeout sur envoi HTTP/S</strong><br><br>"
            + "Il s''agit du d�lai de connexion au r�seau pour les connexions sortantes.<br>"
            + "Si, apr�s ce d�lai, aucune connexion n''a �t� �tablie avec votre syst�me partenaire, la tentative de connexion "
            + "est annul�e et d''autres tentatives de connexion seront effectu�es ult�rieurement, le cas �ch�ant, en fonction "
            + "des param�tres de r�p�tition."
            + "</HTML>"},
        {"label.min", "min"},
        {"receipt.subdir", "Cr�er des sous-r�pertoires par partenaires pour les messages re�us"},
        {"receipt.subdir.help", "<HTML><strong>Sous-r�pertoires pour l''accueil</strong><br><br>"
            + "D�finit si les donn�es doivent �tre re�ues dans le r�pertoire <strong>&lt;Station locale&gt;/inbox</strong>"
            + " ou <strong>&lt;Station locale&gt;/inbox/&lt;Nom du partenaire&gt;</strong>."
            + "</HTML>"},
        //notification
        {"checkbox.notifycertexpire", "Notifier l''expiration de certificats"},
        {"checkbox.notifytransactionerror", "Notifier les erreurs de transaction"},
        {"checkbox.notifycem", "Notifier des �v�nements d'�change certificats (CEM)"},
        {"checkbox.notifyfailure", "Notifier les problems syst�me"},
        {"checkbox.notifyresend", "Notifier renvoie rejet�s"},
        {"checkbox.notifyconnectionproblem", "Notifier les probl�mes de connexion"},
        {"checkbox.notifypostprocessing", "Probl�mes lors du post-traitement"},
        {"button.testmail", "Envoyer un e-mail de test"},
        {"label.mailhost", "H�te du serveur de mail (SMTP)"},
        {"label.mailhost.hint", "IP ou domaine du serveur"},
        {"label.mailport", "Port"},
        {"label.mailport.hint", "Port"},
        {"label.mailport.help", "<HTML><strong>SMTP Port</strong><br><br>"
            + "En g�n�ral, il s''agit de l'une de ces valeurs:<br>"
            + "<strong>25</strong> (Port standard)<br>"
            + "<strong>465</strong> (Port SSL, valeur obsol�te)<br>"
            + "<strong>587</strong> (Port SSL, valeur par d�faut)<br>"
            + "<strong>2525</strong> (Port SSL, valeur alternative, pas de standard)"
            + "</HTML>"},
        {"label.mailaccount", "Compte sur le serveur de mail"},
        {"label.mailpass", "Mot de passe sur le serveur de mail"},
        {"label.notificationmail", "Adresse de notification du destinataire"},
        {"label.notificationmail.help", "<HTML><strong>Adresse de notification du destinataire</strong><br><br>"
            + "L''adresse e-mail du destinataire de la notification.<br>"
            + "Si la notification doit �tre envoy�e � plusieurs destinataires, veuillez saisir ici une liste s�par�e par des virgules d''adresses de r�ception."
            + "</HTML>"},
        {"label.replyto", "Adresse de r�ponse (Replyto)"},
        {"label.smtpauthorization.header", "Autorisation SMTP"},
        {"label.smtpauthorization.credentials", "Utilisateur/Mot de passe"},
        {"label.smtpauthorization.none", "Aucun"},
        {"label.smtpauthorization.oauth2", "OAuth2"},
        {"label.smtpauthorization.user", "Utilisateur"},
        {"label.smtpauthorization.user.hint", "Nom d''utilisateur du serveur SMTP"},
        {"label.smtpauthorization.pass", "Mot de passe"},
        {"label.smtpauthorization.pass.hint", "Mot de passe du serveur SMTP"},
        {"label.security", "S�curit� de connexion"},
        {"testmail.message.success", "E-mail de test envoy� avec succ�s."},
        {"testmail.message.error", "Erreur lors de l''envoi de l''e-mail de test:\n{0}"},
        {"testmail.title", "R�sultat de l''envoi de l''email de test"},
        {"testmail", "L''email de test"},
        //interface
        {"label.showhttpheader", "Laissez configurer les en-t�tes de HTTP dans la configuration d''associ�"},
        {"label.showquota", "Laissez configurer l''avis de quote-part dans la configuration d''associ�"},
        {"label.cem", "Permettre l''�change de certificat (CEM)"},
        {"label.outboundstatusfiles", "�crire des fichiers de statut de transaction sortante"},
        {"info.restart.client", "Un red�marrage du client est requise pour effectuer ces modifications valide!"},
        {"remotedir.select", "S�lectionnez le r�pertoire sur le serveur"},
        //retry
        {"label.retry.max", "Le nombre maximum de tentatives de connexion"},
        {"label.retry.max.help", "<HTML><strong>Le nombre maximum de tentatives de connexion</strong>"
            + "<br><br>Il s'agit du nombre de tentatives utilis�es pour relancer les connexions � "
            + "un partenaire si une connexion n'a pas pu �tre �tablie. Le temps d''attente entre ces "
            + "tentatives peut �tre configur� dans la propri�t� <strong>Le temps d''attente entre deux tentatives de connexion</strong>.</HTML>"},
        {"label.retry.waittime", "Le temps d''attente entre deux tentatives de connexion"},
        {"label.retry.waittime.help", "<HTML><strong>Le temps d''attente entre deux tentatives de connexion</strong>"
            + "<br><br>Il s''agit du temps en secondes que le syst�me attendra avant de se reconnecter "
            + "au partenaire. Une nouvelle tentative de connexion n''est effectu�e que s''il a �t� "
            + "impossible d'�tablir une connexion avec un partenaire (par exemple, syst�me du partenaire "
            + "hors service ou probl�me d''infrastructure). Le nombre de tentatives de connexion peut �tre "
            + "configur� dans la propri�t� <strong>Le nombre maximum de tentatives de connexion</strong>."
            + "</HTML>"},
        {"label.sec", "s"},
        {"keystore.hint", "<HTML><strong>Attention:</strong><br>Veuillez modifier ces param�tres uniquement si vous souhaitez "
            + "utiliser des keystores externes pour les int�grer. Avec des chemins modifi�s, des probl�mes peuvent survenir lors de la mise � jour.</HTML>"},
        {"maintenancemultiplier.day", "jour(s)"},
        {"maintenancemultiplier.hour", "heure(s)"},
        {"maintenancemultiplier.minute", "minute(s)"},
        {"label.logpollprocess", "Affichage d''informations sur le processus de vote dans le journal (�norme quantit� d'entr�es - ne pas utiliser dans la production)"},
        {"label.max.outboundconnections", "Connexions sortantes parall�les (max)"},
        {"label.max.outboundconnections.help", "<HTML><strong>Connexions sortantes parall�les (max)</strong>"
            + "<br><br>Il s''agit du nombre maximal de connexions sortantes parall�les que votre syst�me ouvrira. "
            + "Cette valeur est principalement disponible pour �viter que le syst�me de votre partenaire ne "
            + "soit inond� de connexions entrantes de votre c�t�.</HTML>"},
        {"label.max.inboundconnections", "Max connexions parall�les entrantes"},
        {"label.max.inboundconnections.help", "<HTML><strong>Max connexions parall�les entrantes</strong>"
            + "<br><br>Il s''agit du nombre maximal de connexions entrantes parall�les que votre syst�me ouvrira. "
            + "Ce param�tre est transmis au serveur "
            + "HTTP embarqu�, vous devez red�marrer le serveur AS2 apr�s une modification."
            + "</HTML>"},
        {"event.preferences.modified.subject", "La valeur {0} du param�tre serveur a �t� modifi�e"},
        {"event.preferences.modified.body", "Valeur pr�c�dente: {0}\n\nNouvelle valeur: {1}"},
        {"event.notificationdata.modified.subject", "Les param�tres de notification ont �t� modifi�s."},
        {"event.notificationdata.modified.body", "Les donn�es d''avis sont pass�es de\n\n{0}\n\n�\n\n{1}"},
        {"label.maxmailspermin", "Nombre maximum de notifications/min"},
        {"label.maxmailspermin.help", "<HTML><strong>Nombre maximum de notifications/min</strong><br><br>"
            + "Pour �viter un trop grand nombre de courriers, vous pouvez r�sumer toutes les notifications en "
            + "d�finissant le nombre maximum de notifications qui seront envoy�es par minute. En utilisant "
            + "cette fonctionnalit�, vous recevrez des courriers contenant plusieurs notifications."
            + "</HTML>"},
        {"systemmaintenance.deleteoldtransactions.help", "<HTML><strong>Supprimer les anciennes transactions</strong><br><br>"
            + "Ce param�tre d�finit la p�riode pendant laquelle les transactions et les donn�es associ�es "
            + "(par exemple, les fichiers temporaires) restent dans le syst�me et doivent �tre affich�es dans l''aper�u "
            + "des transactions.<br>Ces param�tres n''affectent pas vos donn�es/fichiers re�us, ils ne sont pas affect�s."
            + "<br>M�me pour les transactions supprim�es, le journal des transactions est toujours disponible via la "
            + "fonction recherche log.</HTML>"},
        {"systemmaintenance.deleteoldstatistic.help", "<HTML><strong>Suppression des anciennes donn�es statistiques</strong><br><br>"
            + "Le syst�me collecte les donn�es de compatibilit� des syst�mes partenaires et peut les afficher sous forme de "
            + "statistiques. Cela d�termine la p�riode pendant laquelle ces donn�es sont conserv�es.</HTML>"},
        {"systemmaintenance.deleteoldlogdirs.help", "<HTML><strong>Suppression des anciens r�pertoires de logs</strong><br><br>M�me si les anciennes transactions ont �t� supprim�es, les op�rations peuvent toujours �tre retrac�es gr�ce aux fichiers journaux existants. Ce param�tre permet de supprimer ces fichiers journaux ainsi que tous les fichiers relatifs aux �v�nements du syst�me qui tombent dans la m�me p�riode.</HTML>"},
        {"label.colorblindness", "Support pour le daltonisme"},
        {"warning.clientrestart.required", "Les param�tres du client ont �t� modifi�s - veuillez red�marrer le client pour les rendre valides"},
        {"warning.serverrestart.required", "Veuillez red�marrer le serveur pour que ces modifications soient valables."},
        {"label.darkmode", "Mode sombre"},
        {"label.litemode", "Mode all�g�"},
        {"label.trustallservercerts", "TLS: Faire confiance � tous les certificats de serveur final de vos partenaires AS2"},
        {"label.trustallservercerts.help", "<HTML><strong>TLS: Faire confiance � tous les certificats de serveur final de vos partenaires AS2</strong><br><br>"
            + "Normalement, TLS exige que tous les certificats de la cha�ne de confiance du syst�me AS2 de votre partenaire soient conserv�s dans votre gestionnaire de certificats TLS. "
            + "Si vous activez cette option, vous faites confiance au certificat final de votre syst�me partenaire lors de l'�tablissement de la connexion sortante, "
            + "si vous ne conservez que les certificats racine et interm�diaires correspondants dans le gestionnaire de certificats TLS. "
            + "Veuillez noter que cette option n''est utile que si votre partenaire utilise un certificat certifi� - les certificats auto-sign�s sont de toute fa�on accept�s."
            + "<br><br><strong>Avertissement:</strong> l''activation de cette option diminue le niveau de s�curit�, car des attaques man-in-the-middle sont possibles!"
            + "</HTML>"},
        {"label.stricthostcheck", "TLS: Faire confiance � tous les h�tes"},
        {"label.stricthostcheck.help", "<HTML><strong>TLS: Faire confiance � tous les h�tes</strong><br><br>"
            + "Vous indiquez ici si, dans le cas d''une connexion TLS sortante, il faut v�rifier si le nom commun (CN) "
            + "du certificat distant correspond � l''h�te distant. Cette v�rification ne s''applique qu'aux certificats "
            + "authentifi�s."
            + "</HTML>"},
        {"label.httpport", "Port d''entr�e HTTP"},
        {"label.httpport.help", "<HTML><strong>Port d''entr�e HTTP</strong><br><br>"
            + "Il s''agit du port pour les connexions entrantes non crypt�es. Ce param�tre est transmis au serveur "
            + "HTTP embarqu�, vous devez red�marrer le serveur AS2 apr�s une modification.<br>"
            + "Le port fait partie de l''URL � laquelle votre partenaire doit envoyer les messages AS2. Il s''agit de http://host:<strong>port</strong>/as2/HttpReceiver."
            + "</HTML>"
        },
        {"label.httpsport", "Port d''entr�e HTTPS"},
        {"label.httpsport.help", "<HTML><strong>Port d''entr�e HTTPS</strong><br><br>"
            + "Il s''agit du port pour les connexions entrantes crypt�es. "
            + "Ce param�tre est transmis au serveur "
            + "HTTP embarqu�, vous devez red�marrer le serveur AS2 apr�s une modification.<br>"
            + "Le port fait partie de l''URL � laquelle votre partenaire doit envoyer les messages AS2. Il s''agit de https://host:<strong>port</strong>/as2/HttpReceiver."
            + "</HTML>"
        },
        {"embedded.httpconfig.not.available", "Serveur HTTP non disponible ou probl�mes d''acc�s au fichier de configuration"},};
}
