//$Header: /as2/de/mendelson/comm/as2/partner/clientserver/SinglePartnerDeleteRequest.java 3     11/06/25 13:28 Heller $
package de.mendelson.comm.as2.partner.clientserver;

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
 * @version $Revision: 3 $
 */
public class SinglePartnerDeleteRequest extends ClientServerMessage implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String as2id = null;


    public SinglePartnerDeleteRequest(String as2id) {
        this.as2id = as2id;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public SinglePartnerDeleteRequest() {
        this.as2id = "";
    }
    
    @Override
    public String toString() {
        return ("Delete partner");
    }

    /**
     * @return the as2id
     */
    public String getAS2id() {
        return as2id;
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }
}
