//$Header: /as2/de/mendelson/comm/as2/message/ResourceBundleAS2MessagePacker_fr.java 9     30-09-16 12:42p Heller $
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
 *
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 9 $
 */
public class ResourceBundleAS2MessagePacker_fr extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] contents = {
        {"message.signed", "{0}: Message sortant signé avec l''algorithme {2}, utilisant l''alias \"{1}\" du porte-clef."},
        {"message.notsigned", "{0}: Le message sortant n''est pas signé."},
        {"message.encrypted", "{0}: Message sortant crypté avec l''algorithme {2}, utilisant l''alias \"{1}\" du porte-clef."},
        {"message.notencrypted", "{0}: Le message sortant n''a pas été crypté."},
        {"mdn.created", "{0}: MDN sortant créé pour message AS2 \"{1}\", état passé à [{2}]."},
        {"mdn.details", "{0}: Détails MDN: {1}"},
        {"message.compressed", "{0}: Contenu sortant compressé de {1} à {2}."},
        {"message.compressed.unknownratio", "{0}: Contenu sortant compressé."},
        {"mdn.signed", "{0}: Le MDN sortant a été signé avec l''algorithme \"{1}\"."},
        {"mdn.notsigned", "{0}: Le MDN sortant n''a pas été signé."},
        {"mdn.creation.start", "{0}: Génération sortant MDN, la mise en identifiant de message à \"{1}\"."},
        {"message.creation.start", "{0}: Génération sortant message AS2, la mise en identifiant de message à \"{0}\"."},
        {"signature.no.aipa", "{0}: Le processus de signature ne pas utiliser l'attribut Algorithm Protection Identificateur tel que défini dans la configuration - ceci est peu sécuritaire!"},};

}
