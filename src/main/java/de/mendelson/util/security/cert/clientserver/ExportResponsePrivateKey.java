//$Header: /as4/de/mendelson/util/security/cert/clientserver/ExportResponsePrivateKey.java 3     11/06/25 13:17 Heller $
package de.mendelson.util.security.cert.clientserver;

import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
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
 * @author S.Heller
 * @version $Revision: 3 $
 */
public class ExportResponsePrivateKey extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String saveFileOnServer = null;
    
    public ExportResponsePrivateKey(ExportRequestPrivateKey request) {
        super(request);
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public ExportResponsePrivateKey() {
        super();
    }
    
    @Override
    public String toString() {
        return ("Upload response keystore");
    }
    
    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }

    /**
     * @return the saveFileOnServer
     */
    public String getSaveFileOnServer() {
        return saveFileOnServer;
    }

    /**
     * @param saveFileOnServer the saveFileOnServer to set
     */
    public void setSaveFileOnServer(String saveFileOnServer) {
        this.saveFileOnServer = saveFileOnServer;
    }
}
