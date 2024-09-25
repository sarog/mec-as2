//$Header: /as2/de/mendelson/util/security/cert/clientserver/ExportResponsePrivateKeyPKCS12.java 3     2/11/23 15:53 Heller $
package de.mendelson.util.security.cert.clientserver;

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
public class ExportResponsePrivateKeyPKCS12 extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String saveFileOnServer = null;
    
    public ExportResponsePrivateKeyPKCS12(ExportRequestPrivateKeyPKCS12 request) {
        super(request);
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
