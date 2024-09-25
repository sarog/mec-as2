//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleHttpUploader_fr.java 14    6.12.18 16:26 Heller $
package de.mendelson.comm.as2.send;
import de.mendelson.util.MecResourceBundle;

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 14 $
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
        {"using.proxy", "Utilisation d''un serveur mandataire (proxy) {0}:{1}." },
        {"answer.no.sync.mdn", "La synchronisation MDN re�ue ne semble pas �tre au bon format. Comme les probl�mes de structure MDN sont rares, il se peut que ce n'est pas une r�ponse du syst�me AS2 que vous vouliez aborder, mais peut-�tre un proxy ou un site web standard? Les valeurs d'en-t�te HTTP suivantes sont manquantes: [{0}].\nLes donn�es re�ues commencent par les structures suivantes: \n{1}" },
        {"hint.SSLPeerUnverifiedException", "Astuce:\nCette est un probl�me qui est survenue au cours de la n�gociation SSL. Le syst�me a �t� incapable d''�tablir une connexion s�curis�e avec votre partenaire, ce probl�me n''est pas li� protocole AS2.\nVeuillez v�rifier les points suivants � corriger cette question:\n* Avez-vous import� toutes vos partenaires certificats SSL dans votre magasin de cl�s SSL (certificats root/interm�diaires)\n*Votre partenaire import� tous vos certificats SSL dans son magasin de cl�s (Les certificats root/interm�diaire)? "},
        {"hint.ConnectTimeoutException", "Hint:\nCeci est un probl�me essentiellement d''une infrastructure qui est pas le protocole AS2 connexes. Le syst�me n'a pas pu �tablir une connexion sortante � votre syst�me partenaires AS2.\nPlease check the following to fix this issue:\n*S'il vous pla�t v�rifier les points suivants pour r�soudre ce probl�me?\n*S''il vous pla�t V�rifier l''URL de la r�ception de votre partenaire, est-il une faute de frappe?\n*S''il vous pla�t communiquer avec votre partenaire, peut-�tre son syst�me de AS2 est en baisse?" },
    };
    
}
