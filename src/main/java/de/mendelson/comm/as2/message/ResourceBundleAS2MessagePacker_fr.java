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
        {"message.signed", "{0}: Message sortant sign� avec l''algorithme {2}, utilisant l''alias \"{1}\" du porte-clef."},
        {"message.notsigned", "{0}: Le message sortant n''est pas sign�."},
        {"message.encrypted", "{0}: Message sortant crypt� avec l''algorithme {2}, utilisant l''alias \"{1}\" du porte-clef."},
        {"message.notencrypted", "{0}: Le message sortant n''a pas �t� crypt�."},
        {"mdn.created", "{0}: MDN sortant cr�� pour message AS2 \"{1}\", �tat pass� � [{2}]."},
        {"mdn.details", "{0}: D�tails MDN: {1}"},
        {"message.compressed", "{0}: Contenu sortant compress� de {1} � {2}."},
        {"message.compressed.unknownratio", "{0}: Contenu sortant compress�."},
        {"mdn.signed", "{0}: Le MDN sortant a �t� sign� avec l''algorithme \"{1}\"."},
        {"mdn.notsigned", "{0}: Le MDN sortant n''a pas �t� sign�."},
        {"mdn.creation.start", "{0}: G�n�ration sortant MDN, la mise en identifiant de message � \"{1}\"."},
        {"message.creation.start", "{0}: G�n�ration sortant message AS2, la mise en identifiant de message � \"{0}\"."},
        {"signature.no.aipa", "{0}: Le processus de signature ne pas utiliser l'attribut Algorithm Protection Identificateur tel que d�fini dans la configuration - ceci est peu s�curitaire!"},};

}
