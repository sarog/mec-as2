//$Header: /as4/de/mendelson/util/mailautoconfig/clientserver/MailAutoConfigDetectRequest.java 5     11/06/25 13:17 Heller $
package de.mendelson.util.mailautoconfig.clientserver;

import de.mendelson.util.clientserver.BaseClient;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Msg for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class MailAutoConfigDetectRequest extends ClientServerMessage implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String mailAddress;
    
    public MailAutoConfigDetectRequest(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public MailAutoConfigDetectRequest() {
        super();        
    }
    
    @Override
    public String toString() {
        return ("Request a mail server configuration");
    }
    
    /**
     * @return the mailAddress
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }
 
}
