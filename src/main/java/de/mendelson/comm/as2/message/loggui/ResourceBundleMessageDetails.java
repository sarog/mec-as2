//$Header: /mec_as2/de/mendelson/comm/as2/message/loggui/ResourceBundleMessageDetails.java 8     7.05.08 11:19 Heller $
package de.mendelson.comm.as2.message.loggui;
import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize gui entries
 * @author S.Heller
 * @version $Revision: 8 $
 */
public class ResourceBundleMessageDetails extends MecResourceBundle{
    
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        
        {"title", "Message details" },
        {"button.ok", "Ok" },
        {"header.timestamp", "Date" },
        {"header.messageid", "Ref No" },
        {"message.raw.decrypted", "Raw data (unencrypted)" },
        {"message.header", "Message header" },
        {"message.payload", "Transfered payload" },
        {"message.payload.multiple", "Payload ({0})" },
        {"tab.log", "Log of this message instance" },
        {"header.encryption", "Encryption" },
        {"header.signature", "Signature" },
        {"header.senderhost", "Sender" },
        {"header.useragent", "AS2 server" },
    };
    
}