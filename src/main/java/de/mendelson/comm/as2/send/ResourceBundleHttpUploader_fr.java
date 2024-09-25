//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleHttpUploader_fr.java 11    9/25/17 1:27p Heller $
package de.mendelson.comm.as2.send;
import de.mendelson.util.MecResourceBundle;

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 11 $
 */
public class ResourceBundleHttpUploader_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"returncode.ok", "{0}: Message envoy� avec succ�s (HTTP {1}); {2} transf�r� en {3} [{4} KB/s]." },
        {"returncode.accepted", "{0}: Message envoy� avec succ�s (HTTP {1}); {2} transf�r� en {3} [{4} KB/s]." },
        {"sending.msg.sync", "{0}: Envoi du AS2 message vers {1}, sync MDN demand�." },
        {"sending.cem.sync", "{0}: Envoi du CEM message vers {1}, sync MDN demand�." },
        {"sending.msg.async", "{0}: Envoi du AS2 message vers {1}, async MDN demand� vers {2}." },
        {"sending.cem.async", "{0}: Envoi du CEM message vers {1}, async MDN demand� vers {2}." },
        {"sending.mdn.async", "{0}: Envoi d''un MDN asynchrone vers {1}." },
        {"error.httpupload", "{0}: La transmission a echou�e, le serveur AS2 distant signale \"{1}\"." },
        {"error.noconnection", "{0}: Probl�me de connexion, donn�es non transmises." },
        {"error.http502", "{0}: Probl�me de connexion, donn�es non transmises. (HTTP 502 - BAD GATEWAY)" },
        {"error.http503", "{0}: Probl�me de connexion, donn�es non transmises. (HTTP 503 - SERVICE UNAVAILABLE)" },
        {"error.http504", "{0}: Probl�me de connexion, donn�es non transmises. (HTTP 504 - GATEWAY TIMEOUT)" },
        {"using.proxy", "{0}: Utilisation d''un serveur mandataire (proxy) {1}:{2}." },
        {"answer.no.sync.mdn", "{0}: Le MDN synchrone re�u semble avoir un format incorrect. Valeur d'ent�te manquante \"{1}\"." },
        {"hint.SSLPeerUnverifiedException", "Astuce:\nCette est un probl�me qui est survenue au cours de la n�gociation SSL. Le syst�me a �t� incapable d''�tablir une connexion s�curis�e avec votre partenaire, ce probl�me n''est pas li� protocole AS2.\nVeuillez v�rifier les points suivants � corriger cette question:\n* Avez-vous import� toutes vos partenaires certificats SSL dans votre magasin de cl�s SSL (certificats root/interm�diaires)\n*Votre partenaire import� tous vos certificats SSL dans son magasin de cl�s (Les certificats root/interm�diaire)? "},
        {"hint.ConnectTimeoutException", "Hint:\nCeci est un probl�me essentiellement d''une infrastructure qui est pas le protocole AS2 connexes. Le syst�me n'a pas pu �tablir une connexion sortante � votre syst�me partenaires AS2.\nPlease check the following to fix this issue:\n*S'il vous pla�t v�rifier les points suivants pour r�soudre ce probl�me?\n*S''il vous pla�t V�rifier l''URL de la r�ception de votre partenaire, est-il une faute de frappe?\n*S''il vous pla�t communiquer avec votre partenaire, peut-�tre son syst�me de AS2 est en baisse?" },
    };
    
}
