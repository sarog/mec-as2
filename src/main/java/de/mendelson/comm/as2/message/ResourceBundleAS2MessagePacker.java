//$Header: /as2/de/mendelson/comm/as2/message/ResourceBundleAS2MessagePacker.java 17    30-09-16 12:42p Heller $
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
 * @version $Revision: 17 $
 */
public class ResourceBundleAS2MessagePacker extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"message.signed", "{0}: Outbound message signed with the algorithm \"{2}\", using keystore alias \"{1}\"." },
        {"message.notsigned", "{0}: Outbound message is not signed." },                 
        {"message.encrypted", "{0}: Outbound message encrypted with the algorithm \"{2}\", using keystore alias \"{1}\"." }, 
        {"message.notencrypted", "{0}: Outbound message has not been encrypted." },     
        {"mdn.created", "{0}: Outbound MDN created for AS2 message \"{1}\", state set to [{2}]." },
        {"mdn.details", "{0}: Outbound MDN details: {1}" },
        {"message.compressed", "{0}: Outbound payload compressed from {1} to {2}." },
        {"message.compressed.unknownratio", "{0}: Outbound payload compressed." },
        {"mdn.signed", "{0}: Outbound MDN has been signed with the algorithm \"{1}\"." },
        {"mdn.notsigned", "{0}: Outbound MDN has not been signed." },
        {"mdn.creation.start", "{0}: Generating outbound MDN, setting message id to \"{1}\"."},
        {"message.creation.start", "{0}: Generating outbound AS2 message, setting message id to \"{0}\"."},
        {"signature.no.aipa", "{0}: The signing process does not use the Algorithm Identifier Protection Attribute as defined in the configuration - this is insecure!" },
    };
    
}