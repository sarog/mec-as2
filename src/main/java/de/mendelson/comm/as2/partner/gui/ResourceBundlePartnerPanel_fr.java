//$Header: /as2/de/mendelson/comm/as2/partner/gui/ResourceBundlePartnerPanel_fr.java 50    19/01/23 10:00 Heller $
package de.mendelson.comm.as2.partner.gui;

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
 * @author E.Pailleau
 * @version $Revision: 50 $
 */
public class ResourceBundlePartnerPanel_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"title", "Configuration des partenaires"},
        {"label.name", "Nom"},
        {"label.name.help", "<HTML><strong>Nom</strong><br><br>"
            + "Il s''agit du nom interne du partenaire tel qu''il est utilis� dans le syst�me. Il ne s''agit pas d''une "
            + "valeur sp�cifique au protocole, mais elle est utilis�e pour construire tout nom de fichier ou structure "
            + "de r�pertoire li� � ce partenaire."
            + "</HTML>"},
        {"label.name.hint", "Nom du partenaire interne"},
        {"label.id", "AS2 id"},
        {"label.id.help", "<HTML><strong>AS2 id</strong><br><br>"
            + "L''identification unique (dans votre r�seau de partenaires) utilis�e dans le protocole AS2 pour identifier "
            + "ce partenaire. Vous pouvez le choisir librement - assurez-vous simplement qu''il est unique."
            + "</HTML>"},
        {"label.id.hint", "Identification des partenaires (protocole AS2)"},
        {"label.partnercomment", "Commentaire"},
        {"label.url", "URL de r�ception"},
        {"label.url.help", "<HTML><strong>URL de r�ception</strong><br><br>"
            + "Il s''agit de l'URL de votre partenaire via laquelle son syst�me AS2 est accessible.<br>"
            + "Veuillez sp�cifier cette URL au format <strong>PROTOCOL://HOST:PORT/CHEMIN</strong>, o� le "
            + "<strong>PROTOCOL</strong> doit �tre l''un des formats \"http\" ou \"https\". <strong>HOST</strong> "
            + "indique l''h�te du serveur AS2 de votre partenaire. <strong>PORT</strong> est le port de r�ception de "
            + "votre partenaire. Si elle n''est pas sp�cifi�e, la valeur \"80\" sera fix�e. <strong>CHEMIN</strong> est le "
            + "chemin de r�ception, par exemple \"/as2/HttpReceiver\".</HTML>"},
        {"label.mdnurl", "URL des MDN"},
        {"label.mdnurl.help", "<HTML><strong>URL des MDN</strong> (<strong>M</strong>essage <strong>D</strong>elivery <strong>N</strong>otification)<br><br>"
            + "C''est l''URL que votre partenaire utilisera pour le MDN asynchrone entrant vers cette station locale. Dans le cas synchrone, "
            + "cette valeur n''est pas utilis�e, car le MDN est envoy� sur le canal de retour de la connexion sortante.<br>"
            + "Veuillez sp�cifier cette URL au format <strong>PROTOCOL://HOST:PORT/CHEMIN</strong>. <br><strong>PROTOCOLE</strong> "
            + "doit �tre l''un de \"http\" ou \"https\".<br><strong>HOST</strong> indique votre propre h�te de serveur AS2.<br>"
            + "<strong>PORT</strong> est le port de r�ception de votre syst�me AS2. S''il n''est pas sp�cifi�, "
            + "la valeur \"80\" sera d�finie.<br><strong>CHEMIN</strong> indique le "
            + "chemin de r�ception, par exemple \"/as2/HttpReceiver\".</HTML>"},
        {"label.signalias.key", "Clef priv�e (Cr�ation de signature)"},
        {"label.signalias.key.help", "<HTML><strong>Clef priv�e (Cr�ation de signature)</strong><br><br>"
            + "Veuillez s�lectionner ici une cl� priv�e disponible dans le gestionnaire de certificats (signature/chiffrement) du syst�me.<br>"
            + "Avec cette cl�, vous cr�ez une signature num�rique pour les messages sortants destin�s � tous les partenaires distants."
            + "</HTML>"},
        {"label.cryptalias.key", "Clef priv�e (D�cryptage)"},
        {"label.cryptalias.key.help", "<HTML><strong>Clef priv�e (D�cryptage)</strong><br><br>"
            + "Veuillez s�lectionner ici une cl� priv�e disponible dans le gestionnaire de certificats (signature/chiffrement) du syst�me.<br>"
            + "Si les messages entrants de n''importe quel partenaire sont crypt�s pour cette station locale, cette cl� est utilis�e pour le d�cryptage."
            + "</HTML>"},
        {"label.signalias.cert", "Certificat du partenaire (V�rification de la signature)"},
        {"label.signalias.cert.help", "<HTML><strong>Certificat du partenaire (V�rification de la signature)</strong><br><br>"
            + "Veuillez s�lectionner ici un certificat qui est disponible dans le gestionnaire de certificats (signature/chiffrement) du syst�me.<br>"
            + "Si les messages entrants de ce partenaire sont sign�s num�riquement pour une station locale, ce certificat est utilis� pour v�rifier cette signature."
            + "</HTML>"},
        {"label.cryptalias.cert", "Certificat du partenaire (Cryptage)"},
        {"label.cryptalias.cert.help", "<HTML><strong>Certificat du partenaire (Cryptage)</strong><br><br>"
            + "Veuillez s�lectionner ici un certificat qui est disponible dans le gestionnaire de certificats (signature/chiffrement) du syst�me.<br>"
            + "Si vous souhaitez crypter les messages sortants � destination de ce partenaire, ce certificat sera utilis� pour crypter les donn�es."
            + "</HTML>"},
        {"label.signtype", "Algorithme de signature num�rique"},
        {"label.signtype.help", "<HTML><strong>Algorithme de signature num�rique</strong><br><br>"
            + "Vous choisissez ici l''algorithme de signature avec lequel les messages sortants destin�s � ce partenaire doivent �tre sign�s.<br>"
            + "Si vous avez choisi ici un algorithme de signature, un message sign� est �galement attendu en entr�e de ce partenaire - l''algorithme de signature est toutefois arbitraire."
            + "</HTML>"},
        {"label.encryptiontype", "Algorithme de chiffrement des messages"},
        {"label.encryptiontype.help", "<HTML><strong>Algorithme de chiffrement des messages</strong><br><br>"
            + "Vous choisissez ici l''algorithme de cryptage avec lequel les messages sortants � destination de ce partenaire doivent �tre crypt�s.<br>"
            + "Si vous avez choisi ici un algorithme de cryptage, un message crypt� est �galement attendu en entr�e de ce partenaire - l''algorithme de cryptage est toutefois arbitraire."
            + "</HTML>"},
        {"label.email", "E-mail"},
        {"label.email.help", "<HTML><strong>E-mail</strong><br><br>"
            + "Cette valeur fait partie de la description du protocole AS2 mais n'est en fait actuellement pas du tout utilis�e."
            + "</HTML>"},
        {"label.email.hint", "Non utilis� ou valid� dans le protocole AS2"},
        {"label.localstation", "Station locale"},
        {"label.localstation.help", "<HTML><strong>Station locale</strong><br><br>"
            + "Il existe deux types de partenaires: Les stations locales et les partenaires distants. Une station locale repr�sente votre propre syst�me."
            + "</HTML>"},
        {"label.compression", "Compresser les messages sortants (n�cessite une solution AS2 1.1 en face)"},
        {"label.usecommandonreceipt", "Sur r�ception de message:"},
        {"label.usecommandonsenderror", "Sur envoi �chou� de message:"},
        {"label.usecommandonsendsuccess", "Sur envoi r�ussi de message:"},
        {"label.keepfilenameonreceipt", "Garder le nom de fichier original sur r�ception"},
        {"label.keepfilenameonreceipt.help", "<HTML><strong>Garder le nom de fichier original sur r�ception</strong><br><br>"
            + "Si cette option est activ�e, le syst�me tente d''extraire le nom de fichier original des messages AS2 entrants "
            + "et d''enregistrer le fichier transmis sous ce nom afin qu''il puisse �tre trait� en cons�quence.<br>"
            + "Cette option ne fonctionnera que si l''exp�diteur a ajout� les informations relatives au nom de fichier original. Si vous l''activez, veillez � ce que votre partenaire envoie des noms de fichiers uniques.</HTML>"},
        {"label.address", "Adresse"},
        {"label.notes.help", "<HTML><strong>Notes</strong><br><br>"
            + "Vous trouverez ici la possibilit� de prendre des notes sur ce partenaire pour votre propre usage."
            + "</HTML>"},
        {"label.contact", "Contact"},
        {"tab.misc", "Divers"},
        {"tab.security", "S�curit�"},
        {"tab.send", "Envoi"},
        {"tab.mdn", "MDN"},
        {"tab.dirpoll", "Scrutation de r�pertoire"},
        {"tab.receipt", "R�ception"},
        {"tab.httpauth", "Authentication HTTP"},
        {"tab.httpheader", "En-t�te de HTTP"},
        {"tab.notification", "Notification"},
        {"tab.events", "Post-traitement"},
        {"tab.partnersystem", "Info"},
        {"label.subject", "Sujet du contenu"},
        {"label.subject.help", "<HTML><strong>Sujet du contenu</strong><br><br>$'{'filename} sera remplac� par le nom de fichier send.<br>Cette valeur sera transf�r�e dans l''en-t�te HTTP, il y a des restrictions! Veuillez utiliser la norme ISO-8859-1 pour l''encodage des caract�res, uniquement des caract�res imprimables, pas de caract�res sp�ciaux. CR, LF et TAB sont remplac�s par \"\\r\", \"\\n\" et \"\\t\".</HTML>"},
        {"label.contenttype", "Type de contenu"},
        {"label.contenttype.help", "<HTML><strong>Type de contenu</strong><br><br>"
            + "application/EDI-X12<br>"
            + "application/EDIFACT<br>"
            + "application/edi-consent<br>"
            + "application/XML<br><br>"
            + "Le RFC AS2 indique que tous les types de contenu MIME doivent �tre pris en charge dans l''AS2 - "
            + "mais ce n''est pas une condition obligatoire. Vous ne devez donc pas compter sur le "
            + "syst�me de votre partenaire ou sur le traitement SMIME sous-jacent de l''AS2 de "
            + "mendelson pour g�rer d''autres types de contenu que ceux d�crits."
            + "</HTML>"},
        {"label.syncmdn", "Utilise des MDN synchrone"},
        {"label.syncmdn.help", "<HTML><strong>MDN synchrone</strong><br><br>"
            + "Le partenaire envoie la confirmation (MDN) sur le canal de retour de votre connexion sortante. "
            + "La connexion sortante reste ouverte pendant que le partenaire d�crypte les donn�es et v�rifie "
            + "la signature. C''est la raison pour laquelle cette m�thode n�cessite plus de ressources que "
            + "le traitement MDN asynchrone.</HTML>"},
        {"label.asyncmdn", "Utilise des MDN asynchrone"},
        {"label.asyncmdn.help", "<HTML><strong>MDN asynchrone</strong><br><br>"
            + "Le partenaire �tablit une nouvelle connexion � votre syst�me pour envoyer une confirmation pour votre "
            + "message sortant. La v�rification de la signature et le d�cryptage des donn�es du c�t� de votre partenaire "
            + "sont effectu�s apr�s la fermeture de la connexion entrante. C''est la raison pour laquelle cette m�thode "
            + "n�cessite moins de ressources que le MDN synchrone.</HTML>"},
        {"label.signedmdn", "Utilise des MDN sign�s"},
        {"label.signedmdn.help", "<HTML><strong>MDN sign�s</strong><br><br>"
            + "Ce param�tre vous permet d''indiquer au syst�me partenaire pour les messages AS2 sortants que vous souhaitez un accus� de r�ception sign� (MDN).<br>"
            + "Bien que cette option semble logique au premier abord, elle est malheureusement probl�matique. En effet, lorsque le MDN du partenaire est re�u, la transaction est termin�e. "
            + "Si la v�rification de la signature du MDN est effectu�e et �choue, il n''est plus possible d'informer le partenaire de ce probl�me. "
            + "Une interruption de la transaction n''est plus possible - la transaction est d�j� termin�e. La v�rification de la signature du MDN en mode automatique n'a donc aucun sens. "
            + "Le protocole AS2 prescrit ici que l''application doit r�soudre ce probl�me logique, ce qui n''est pas possible.<br>"
            + "La solution AS2 de mendelson affiche un avertissement en cas d''�chec de la v�rification de la signature MDN.<br><br>"
            + "Il existe encore une particularit� de ce r�glage : si un probl�me est survenu lors du traitement c�t� partenaire, le MDN peut toujours �tre non sign� - ind�pendamment de ce r�glage."
            + "</HTML>"},
        {"label.enabledirpoll", "Activer le sondage d''annuaire"},
        {"label.pollignore.help", "<HTML><strong>Activer le sondage d''annuaire</strong><br><br>"
            + "La surveillance des r�pertoires ira chercher � intervalles r�guliers un nombre d�fini de fichiers dans le r�pertoire surveill� et les traitera. "
            + "Il faut s''assurer qu''� ce moment-l�, le fichier soit "
            + "pr�sent dans son int�gralit�. Si vous copiez r�guli�rement des fichiers dans le r�pertoire surveill�, il peut arriver que des chevauchements temporels se produisent, "
            + "c''est-�-dire qu''un fichier soit r�cup�r� alors qu''il n'est pas encore enti�rement disponible. "
            + "C''est pourquoi, si vous copiez les fichiers dans le r�pertoire surveill� � l''aide d''une op�ration non atomique, vous devriez choisir une extension de nom de fichier au moment du processus de copie qui sera ignor�e par le processus de surveillance. Une fois que le fichier entier est dans le r�pertoire surveill� "
            + "r�pertoire, vous pouvez supprimer l''extension de nom de fichier � l''aide d''une op�ration atomique (move, mv, rename) et le fichier complet est r�cup�r�. "
            + "<br>La liste des extensions de nom de fichier est une liste d''extensions s�par�es par des virgules, par exemple \"*.tmp, *.upload\"."
            + "</HTML>"},
        {"label.enabledirpoll.help", "<HTML><strong>Activer le sondage d''annuaire</strong><br><br>"
            + "Si vous activez cette option, le syst�me cherchera automatiquement de nouveaux fichiers dans le r�pertoire de d�part "
            + "pour ce partenaire. Si un nouveau fichier est trouv�, un message AS2 est g�n�r� � partir de celui-ci et envoy� au partenaire.<br>"
            + "Veuillez noter que cette m�thode de surveillance du r�pertoire ne peut utiliser que des param�tres g�n�raux "
            + "pour toutes les cr�ations de messages. Si vous souhaitez d�finir des param�tres sp�cifiques pour chaque message "
            + "individuellement, veuillez utiliser le processus d''envoi via la ligne de commande.<br>"
            + "En cas de fonctionnement en cluster (HA), vous devez d�sactiver toutes les surveillances de r�pertoire, car ce "
            + "processus ne peut pas �tre synchronis�."
            + "</HTML>"},
        {"label.polldir", "R�pertoire de scrutation"},
        {"label.pollinterval", "Intervalle de scrutation"},
        {"label.pollignore", "Ignorer les fichiers"},
        {"label.pollignore.hint", "Liste des fichiers � ignorer, s�par�s par virgules (caract�res g�n�riques autoris�s)."},
        {"label.maxpollfiles", "Max fichiers/sondage"},
        {"label.httpauth.message", "Authentification des messages AS2 sortants"},
        {"label.httpauth.none", "Aucune"},
        {"label.httpauth.credentials.message", "Authentification HTTP basique"},
        {"label.httpauth.credentials.message.user", "Utilisateur"},
        {"label.httpauth.credentials.message.pass", "Mot de passe"},
        {"label.httpauth.oauth2.message", "OAuth2"},
        {"label.httpauth.asyncmdn", "Authentification des MDN asynchrones sortants"},
        {"label.httpauth.credentials.asyncmdn", "Authentification HTTP basique"},
        {"label.httpauth.credentials.asyncmdn.user", "Utilisateur"},
        {"label.httpauth.credentials.asyncmdn.pass", "Mot de passe"},
        {"label.httpauth.oauth2.asyncmdn", "OAuth2"},
        {"label.notify.send", "Notifier lors d''un d�passement de quota sur message envoy�"},
        {"label.notify.receive", "Notifier lors d''un d�passement de quota sur message re�u"},
        {"label.notify.sendreceive", "Notifier lors d''un d�passement de quota sur message envoy� ou re�u"},
        {"header.httpheaderkey", "Nom"},
        {"header.httpheadervalue", "Valeur"},
        {"httpheader.add", "Ajouter "},
        {"httpheader.delete", "�liminer"},
        {"label.as2version", "Version AS2:"},
        {"label.productname", "Nom du produit:"},
        {"label.features", "Fonctionnalit�s:"},
        {"label.features.cem", "Certificat d'�change via CEM"},
        {"label.features.ma", "Plusieurs pi�ces jointes"},
        {"label.features.compression", "Compression"},
        {"partnerinfo", "Votre partenaire transmet avec chaque message AS2 quelques informations � propos de ses capacit�s de syst�me AS2. Il s'agit d'une liste de fonctions qui a �t� transmise par votre partenaire."},
        {"partnersystem.noinfo", "Aucune information n''est disponible, qu''il y avait d�j� une transaction?"},
        {"label.httpversion", "Version du protocole HTTP"},
        {"label.test.connection", "Connexion de test"},
        {"label.mdn.description", "<HTML>Le MDN (Message Delivery Notification) est la confirmation du message AS2. Cette section d�finit le comportement de votre partenaire pour vos messages AS2 sortants.</HTML>"},
        {"label.algorithmidentifierprotection", "<HTML>Utiliser l''attribut de protection de l''identificateur d''algorithme dans la signature (recommand�), voir RFC 6211</HTML>"},
        {"tooltip.button.editevent", "Modifier l'�v�nement"},
        {"tooltip.button.addevent", "Cr�er un nouvel �v�nement"},
        {"label.httpauthentication.credentials.help", "<HTML><strong>Authentification d''acc�s de base HTTP</strong><br><br>"
            + "Veuillez configurer ici l''authentification d''acc�s de base HTTP si celle-ci est activ�e du c�t� "
            + "de votre partenaire (d�finie dans la RFC 7617). Pour les demandes non authentifi�es (donn�es de "
            + "connexion incorrectes, etc.), le syst�me du partenaire distant doit renvoyer un <strong>HTTP 401 "
            + "Unauthorized</strong> status.<br>Si la connexion � votre partenaire n�cessite l''authentification du "
            + "client TLS (via des certificats), aucun r�glage n''est n�cessaire ici. Dans ce cas, veuillez "
            + "importer les certificats du partenaire via le gestionnaire de certificats TLS - le syst�me se "
            + "chargera alors de l''authentification du client TLS."
            + "</HTML>"},};
}
