//$Header: /mec_as2/de/mendelson/comm/as2/message/ResourceBundleAS2MessageParser.java 37    7/20/17 4:07p Heller $
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
 * @version $Revision: 37 $
 */
public class ResourceBundleAS2MessageParser extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"mdn.incoming", "{0}: Inbound transmission is a MDN {1}." },
        {"mdn.answerto", "{0}: Inbound MDN is the answer to AS2 message \"{1}\"." },  
        {"mdn.state", "{0}: Inbound MDN state is [{1}]." },          
        {"mdn.details", "{0}: Inbound MDN details received from {1}: \"{2}\"" },
        {"msg.incoming", "{0}: Inbound transmission is a AS2 message [{1}], raw message size: {2}." },   
        {"msg.incoming.identproblem", "{0}: Inbound transmission is a AS2 message. It has not been processed because of a trading partner identification problem." },   
        {"mdn.signed", "{0}: Inbound MDN is signed ({1})." },
        {"mdn.unsigned.error", "{0}: Inbound MDN is not signed. The partner configuration defines MDN from the partner \"{1}\" to be signed." },
        {"mdn.signed.error", "{0}: Inbound MDN is signed. The partner configuration defines MDN from the partner \"{1}\" to be not signed." },
        {"msg.signed", "{0}: Inbound AS2 message is signed." },        
        {"msg.encrypted", "{0}: Inbound AS2 message is encrypted." },        
        {"msg.notencrypted", "{0}: Inbound AS2 message is not encrypted." },                
        {"msg.notsigned", "{0}: Inbound AS2 message is not signed." },                
        {"mdn.notsigned", "{0}: Inbound MDN is not signed." },
        {"mdn.signature.ok", "{0}: Digital signature of inbound MDN has been verified successful." },
        {"message.signature.ok", "{0}: Digital signature of inbound AS2 message has been verified successful." },
        {"message.signature.failure", "{0}: Verification of digital signature of inbound AS2 message failed {1}" },
        {"mdn.signature.failure", "{0}: Verification of digital signature of inbound MDN failed {1}" },
        {"mdn.signature.using.alias", "{0}: Using certificate \"{1}\" to verify inbound MDN signature." }, 
        {"message.signature.using.alias", "{0}: Using certificate \"{1}\" to verify inbound AS2 message signature." }, 
        {"decryption.done.alias", "{0}: The inbound AS2 message data has been decrypted using the key \"{1}\", the encryption algorithm was \"{2}\", the key encryption algorithm was \"{3}\"." },
        {"mdn.unexpected.messageid", "{0}: The inbound MDN references a AS2 message with the message id \"{1}\" that does not exist." },
        {"mdn.unexpected.state", "{0}: The inbound MDN references the AS2 message with the message id \"{1}\" that is not waiting for an MDN." },
        {"data.compressed.expanded", "{0}: The compressed payload of the inbound AS2 message has been expanded from {1} to {2}." },
        {"found.attachments", "{0}: Found {1} payload attachments in the inbound AS2 message." },
        {"decryption.inforequired", "{0}: To decrypt the data of the inbound AS2 message a key with the following parameter is required:\n{1}" },
        {"decryption.infoassigned", "{0}: A key with the following parameter has been used to decrypt the data of the inbound AS2 message (alias \"{1}\"):\n{2}" },
        {"signature.analyzed.digest", "{0}: The sender used the algorithm \"{1}\" to sign the inbound AS2 message." },
        {"signature.analyzed.digest.failed", "{0}: The system is unable to find out the sign algorithm of the inbound AS2 message." },
        {"filename.extraction.error", "{0}: Unable to extract original filename from the inbound AS2 message: \"{1}\", ignoring filename." },
        {"contentmic.match", "{0}: The Message Integrity Code (MIC) matches the sent AS2 message." },
        {"contentmic.failure", "{0}: The Message Integrity Code (MIC) does not match the sent AS2 message (required: {1}, returned: {2})." },
        {"found.cem", "{0}: The message is a Certificate Exchange Message (CEM)." },
        {"data.unable.to.process.content.transfer.encoding", "Data has arrived that could not be processed because it contains errors. The defined content transfer encoding \"{0}\" is unknown."},
    };
    
}