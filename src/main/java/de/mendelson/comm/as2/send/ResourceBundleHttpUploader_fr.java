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
        {"returncode.ok", "{0}: Message envoyé avec succès (HTTP {1}); {2} transféré en {3} [{4} KB/s]." },
        {"returncode.accepted", "{0}: Message envoyé avec succès (HTTP {1}); {2} transféré en {3} [{4} KB/s]." },
        {"sending.msg.sync", "{0}: Envoi du AS2 message vers {1}, sync MDN demandé." },
        {"sending.cem.sync", "{0}: Envoi du CEM message vers {1}, sync MDN demandé." },
        {"sending.msg.async", "{0}: Envoi du AS2 message vers {1}, async MDN demandé vers {2}." },
        {"sending.cem.async", "{0}: Envoi du CEM message vers {1}, async MDN demandé vers {2}." },
        {"sending.mdn.async", "{0}: Envoi d''un MDN asynchrone vers {1}." },
        {"error.httpupload", "{0}: La transmission a echouée, le serveur AS2 distant signale \"{1}\"." },
        {"error.noconnection", "{0}: Problème de connexion, données non transmises." },
        {"error.http502", "{0}: Problème de connexion, données non transmises. (HTTP 502 - BAD GATEWAY)" },
        {"error.http503", "{0}: Problème de connexion, données non transmises. (HTTP 503 - SERVICE UNAVAILABLE)" },
        {"error.http504", "{0}: Problème de connexion, données non transmises. (HTTP 504 - GATEWAY TIMEOUT)" },
        {"using.proxy", "{0}: Utilisation d''un serveur mandataire (proxy) {1}:{2}." },
        {"answer.no.sync.mdn", "{0}: Le MDN synchrone reçu semble avoir un format incorrect. Valeur d'entête manquante \"{1}\"." },
        {"hint.SSLPeerUnverifiedException", "Astuce:\nCette est un problème qui est survenue au cours de la négociation SSL. Le système a été incapable d''établir une connexion sécurisée avec votre partenaire, ce problème n''est pas lié protocole AS2.\nVeuillez vérifier les points suivants à corriger cette question:\n* Avez-vous importé toutes vos partenaires certificats SSL dans votre magasin de clés SSL (certificats root/intermédiaires)\n*Votre partenaire importé tous vos certificats SSL dans son magasin de clés (Les certificats root/intermédiaire)? "},
        {"hint.ConnectTimeoutException", "Hint:\nCeci est un problème essentiellement d''une infrastructure qui est pas le protocole AS2 connexes. Le système n'a pas pu établir une connexion sortante à votre système partenaires AS2.\nPlease check the following to fix this issue:\n*S'il vous plaît vérifier les points suivants pour résoudre ce problème?\n*S''il vous plaît Vérifier l''URL de la réception de votre partenaire, est-il une faute de frappe?\n*S''il vous plaît communiquer avec votre partenaire, peut-être son système de AS2 est en baisse?" },
    };
    
}
