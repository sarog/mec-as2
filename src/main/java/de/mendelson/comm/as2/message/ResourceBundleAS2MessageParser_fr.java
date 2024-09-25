//$Header: /mec_as2/de/mendelson/comm/as2/message/ResourceBundleAS2MessageParser_fr.java 22    7/20/17 4:07p Heller $
package de.mendelson.comm.as2.message;
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
 * @author E.Pailleau
 * @version $Revision: 22 $
 */
public class ResourceBundleAS2MessageParser_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"mdn.incoming", "{0}: La transmission entrante est un MDN {1}." },
        {"mdn.answerto", "{0}: Le MDN est la réponse au message AS2 \"{1}\"." },
        {"mdn.state", "{0}: L''état du MDN est [{1}]." },
        {"mdn.details", "{0}: Détails du MDN reçu ({1}): \"{2}\"" },
        {"msg.incoming", "{0}: La transmission entrante est un message AS2 [{1}], taille du message brut: {2}." },
        {"msg.incoming.identproblem", "{0}: La transmission entrante est un message AS2. Il n'a pas été traitée en raison d''un problème d''identification de partenaire." },   
        {"mdn.signed", "{0}: Le MDN est signé ({1})." },
        {"mdn.unsigned.error", "{0}: Le MDN n''est pas signé. La configuration stipule que le MDN du partenaire \"{1}\" doit être signé." },
        {"mdn.signed.error", "{0}: Le MDN est signé. La configuration stipule que le MDN du partenaire \"{1}\" ne doit pas être signé." },
        {"msg.signed", "{0}: Le message AS2 est signé." },
        {"msg.encrypted", "{0}: Le message AS2 est crypté." },
        {"msg.notencrypted", "{0}: Le message AS2 n''est pas crypté." },
        {"msg.notsigned", "{0}: Le message AS2 n''est pas signé." },
        {"mdn.notsigned", "{0}: Le MDN n''est pas signé." },
        {"message.signature.ok", "{0}: La signature numérique du message AS2 a été vérifiée avec succès." },
        {"mdn.signature.ok", "{0}: La signature numérique du MDN a été vérifiée avec succès." },
        {"mdn.signature.failure", "{0}: Vérification de signature digitale du MDN échouée - {1}" },
        {"message.signature.failure", "{0}: Vérification de signature digitale du message AS2 échouée - {1}" },
        {"message.signature.using.alias", "{0}: Utilisation du certificat \"{1}\" pour vérifier la signature du message AS2." },
        {"mdn.signature.using.alias", "{0}: Utilisation du certificat \"{1}\" pour vérifier la signature du MDN." },
        {"decryption.done.alias", "{0}: Les données du message AS2 ont été décryptées avec la clef \"{1}\", l''algorithme de chiffrement est \"{2}\", l''algorithme de chiffrement cle est \"{3}\"." },
        {"mdn.unexpected.messageid", "{0}: Le MDN référence un message AS2 avec l''identifiant \"{1}\" qui est inconnu." },
        {"mdn.unexpected.state", "{0}: Le MDN référence le message AS2 avec l''identification \"{1}\", cela n''attend pas un MDN" },
        {"data.compressed.expanded", "{0}: Le contenu compressé a vu sa taille passer de {1} à {2}." },
        {"found.attachments", "{0}: Trouvé {1} contenus en pièces attachées dans le message." },
        {"decryption.inforequired", "{0}: Afin de décrypter les données une clef avec les paramètres suivants est requise:\n{1}" },
        {"decryption.infoassigned", "{0}: Une clef avec les paramètres suivants est utilisé pour décrypter les données (alias \"{1}\"):\n{2}" },
        {"signature.analyzed.digest", "{0}: L''émetteur a utilisé l''algorithme \"{1}\" pour signer le message." },
        {"signature.analyzed.digest.failed", "{0}: Le système n''a pas pu trouver l''algorithme de signature du message AS2 entrant." },
        {"filename.extraction.error", "{0}: Extraire noms de fichier originaux n''est pas possible: \"{1}\", ignoré." },
        {"contentmic.match", "{0}: Le Message Integrity Code (MIC) assortit le message AS2 envoyé." },
        {"contentmic.failure", "{0}: Le Message Integrity Code (MIC) n'assortit pas le message AS2 envoyé (requis: {1}, reçu: {2})." },
        {"found.cem", "{0}: Le message est un message d'échange de certificat (CEM)." },
        {"data.unable.to.process.content.transfer.encoding", "Les données ont été reçues qui n''ont pas pu être traitées. Le codage de transfert de contenu \"{0}\" est inconnue."},
    };
    
}
