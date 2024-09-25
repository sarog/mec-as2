//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleHttpUploader_fr.java 20    4/08/22 10:44 Heller $
package de.mendelson.comm.as2.send;
import de.mendelson.util.MecResourceBundle;

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 20 $
 */
public class ResourceBundleHttpUploader_fr extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"returncode.ok", "Message envoy� avec succ�s (HTTP {0}); {1} transf�r� en {2} [{3} KB/s]." },
        {"returncode.accepted", "Message envoy� avec succ�s (HTTP {0}); {1} transf�r� en {2} [{3} KB/s]." },
        {"sending.msg.sync", "Envoi du AS2 message vers {0}, sync MDN demand�." },
        {"sending.cem.sync", "Envoi du CEM message vers {0}, sync MDN demand�." },
        {"sending.msg.async", "Envoi du AS2 message vers {0}, async MDN demand� vers {1}." },
        {"sending.cem.async", "Envoi du CEM message vers {0}, async MDN demand� vers {1}." },
        {"sending.mdn.async", "Envoi d''un MDN asynchrone vers {0}." },
        {"error.httpupload", "La transmission a echou�e, le serveur AS2 distant signale \"{0}\"." },
        {"error.noconnection", "Probl�me de connexion, donn�es non transmises." },
        {"error.http502", "Probl�me de connexion, donn�es non transmises. (HTTP 502 - BAD GATEWAY)" },
        {"error.http503", "Probl�me de connexion, donn�es non transmises. (HTTP 503 - SERVICE UNAVAILABLE)" },
        {"error.http504", "Probl�me de connexion, donn�es non transmises. (HTTP 504 - GATEWAY TIMEOUT)" },
        {"using.proxy", "Utiliser le proxy {0}:{1}." },
        {"using.proxy.auth", "Utiliser le proxy {0}:{1} (authentification en tant que {2})." },
        {"answer.no.sync.mdn", "La synchronisation MDN re�ue ne semble pas �tre au bon format. Comme les probl�mes de structure MDN sont rares, il se peut que ce n''est pas une r�ponse du syst�me AS2 que vous vouliez aborder, mais peut-�tre un proxy ou un site web standard? Les valeurs d'en-t�te HTTP suivantes sont manquantes: [{0}].\nLes donn�es re�ues commencent par les structures suivantes: \n{1}" },
        {"answer.no.sync.empty", "L''accus� de r�ception synchrone (MDN) re�u est vide. Il y a probablement eu un probl�me lors du traitement des messages AS2 de la part de votre partenaire - veuillez contacter votre partenaire en cons�quence." },
        {"hint.SSLPeerUnverifiedException", "Indice:\nCette est un probl�me qui est survenue au cours de la n�gociation SSL. Le syst�me a �t� incapable d''�tablir une connexion s�curis�e avec votre partenaire, ce probl�me n''est pas li� protocole AS2.\nVeuillez v�rifier les points suivants � corriger cette question:\n* Avez-vous import� toutes vos partenaires certificats SSL dans votre magasin de cl�s SSL (certificats root/interm�diaires)\n*Votre partenaire import� tous vos certificats SSL dans son magasin de cl�s (Les certificats root/interm�diaire)? "},
        {"hint.ConnectTimeoutException", "Indice:\nCeci est un probl�me essentiellement d''une infrastructure qui est pas le protocole AS2 connexes. Le syst�me n'a pas pu �tablir une connexion sortante � votre syst�me partenaires AS2.\nPlease check the following to fix this issue:\n*S'il vous pla�t v�rifier les points suivants pour r�soudre ce probl�me?\n*S''il vous pla�t V�rifier l''URL de la r�ception de votre partenaire, est-il une faute de frappe?\n*S''il vous pla�t communiquer avec votre partenaire, peut-�tre son syst�me de AS2 est en baisse?" },
        {"hint.SSLException", "Indice:\nC''est principalement un probl�me de n�gociation au niveau du protocole. Votre partenaire a rejet� votre connexion.\nSoit votre partenaire s''attendait � une connexion s�curis�e (HTTPS) et vous avez essay� une connexion non s�curis�e (HTTP) ou vice versa.\nIl est �galement possible que votre partenaire s'attende � une autre version du protocole SSL/TLS ou � un autre chiffrement que celui que vous proposez." },
        {"hint.httpcode.signals.problem", "Indice:\nUne connexion a �t� �tablie avec l''h�te de vos partenaires - un serveur web s''y trouve. Le serveur distant signale que quelque chose ne va pas avec le chemin de requ�te ou le port et renvoie le code HTTP {0}.\nVeuillez vous r�f�rer � un moteur de recherche Internet si vous souhaitez plus d''informations sur ce code HTTP." },
        {"trust.all.server.certificates", "La connexion TLS sortante fera confiance � tous les certificats du serveur d''extr�mit� distant si les certificats racine et interm�diaire sont disponibles." },
        {"strict.hostname.check", "Lors de la connexion TLS sortante, un contr�le strict du nom d''h�te est effectu� en ce qui concerne le certificat du serveur." },
        {"strict.hostname.check.skipped.selfsigned", "TLS: La v�rification stricte du nom d''h�te a �t� ignor�e - le serveur distant utilise un certificat self signed." },
    };
    
}
