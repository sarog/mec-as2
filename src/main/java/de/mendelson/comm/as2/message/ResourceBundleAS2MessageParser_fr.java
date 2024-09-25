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
        {"mdn.answerto", "{0}: Le MDN est la r�ponse au message AS2 \"{1}\"." },
        {"mdn.state", "{0}: L''�tat du MDN est [{1}]." },
        {"mdn.details", "{0}: D�tails du MDN re�u ({1}): \"{2}\"" },
        {"msg.incoming", "{0}: La transmission entrante est un message AS2 [{1}], taille du message brut: {2}." },
        {"msg.incoming.identproblem", "{0}: La transmission entrante est un message AS2. Il n'a pas �t� trait�e en raison d''un probl�me d''identification de partenaire." },   
        {"mdn.signed", "{0}: Le MDN est sign� ({1})." },
        {"mdn.unsigned.error", "{0}: Le MDN n''est pas sign�. La configuration stipule que le MDN du partenaire \"{1}\" doit �tre sign�." },
        {"mdn.signed.error", "{0}: Le MDN est sign�. La configuration stipule que le MDN du partenaire \"{1}\" ne doit pas �tre sign�." },
        {"msg.signed", "{0}: Le message AS2 est sign�." },
        {"msg.encrypted", "{0}: Le message AS2 est crypt�." },
        {"msg.notencrypted", "{0}: Le message AS2 n''est pas crypt�." },
        {"msg.notsigned", "{0}: Le message AS2 n''est pas sign�." },
        {"mdn.notsigned", "{0}: Le MDN n''est pas sign�." },
        {"message.signature.ok", "{0}: La signature num�rique du message AS2 a �t� v�rifi�e avec succ�s." },
        {"mdn.signature.ok", "{0}: La signature num�rique du MDN a �t� v�rifi�e avec succ�s." },
        {"mdn.signature.failure", "{0}: V�rification de signature digitale du MDN �chou�e - {1}" },
        {"message.signature.failure", "{0}: V�rification de signature digitale du message AS2 �chou�e - {1}" },
        {"message.signature.using.alias", "{0}: Utilisation du certificat \"{1}\" pour v�rifier la signature du message AS2." },
        {"mdn.signature.using.alias", "{0}: Utilisation du certificat \"{1}\" pour v�rifier la signature du MDN." },
        {"decryption.done.alias", "{0}: Les donn�es du message AS2 ont �t� d�crypt�es avec la clef \"{1}\", l''algorithme de chiffrement est \"{2}\", l''algorithme de chiffrement cle est \"{3}\"." },
        {"mdn.unexpected.messageid", "{0}: Le MDN r�f�rence un message AS2 avec l''identifiant \"{1}\" qui est inconnu." },
        {"mdn.unexpected.state", "{0}: Le MDN r�f�rence le message AS2 avec l''identification \"{1}\", cela n''attend pas un MDN" },
        {"data.compressed.expanded", "{0}: Le contenu compress� a vu sa taille passer de {1} � {2}." },
        {"found.attachments", "{0}: Trouv� {1} contenus en pi�ces attach�es dans le message." },
        {"decryption.inforequired", "{0}: Afin de d�crypter les donn�es une clef avec les param�tres suivants est requise:\n{1}" },
        {"decryption.infoassigned", "{0}: Une clef avec les param�tres suivants est utilis� pour d�crypter les donn�es (alias \"{1}\"):\n{2}" },
        {"signature.analyzed.digest", "{0}: L''�metteur a utilis� l''algorithme \"{1}\" pour signer le message." },
        {"signature.analyzed.digest.failed", "{0}: Le syst�me n''a pas pu trouver l''algorithme de signature du message AS2 entrant." },
        {"filename.extraction.error", "{0}: Extraire noms de fichier originaux n''est pas possible: \"{1}\", ignor�." },
        {"contentmic.match", "{0}: Le Message Integrity Code (MIC) assortit le message AS2 envoy�." },
        {"contentmic.failure", "{0}: Le Message Integrity Code (MIC) n'assortit pas le message AS2 envoy� (requis: {1}, re�u: {2})." },
        {"found.cem", "{0}: Le message est un message d'�change de certificat (CEM)." },
        {"data.unable.to.process.content.transfer.encoding", "Les donn�es ont �t� re�ues qui n''ont pas pu �tre trait�es. Le codage de transfert de contenu \"{0}\" est inconnue."},
    };
    
}
