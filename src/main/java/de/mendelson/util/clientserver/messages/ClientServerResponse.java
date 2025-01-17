//$Header: /as2/de/mendelson/util/clientserver/messages/ClientServerResponse.java 9     2/11/23 15:53 Heller $
package de.mendelson.util.clientserver.messages;

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
 * A sync response from the server - will follow a request
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class ClientServerResponse extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private Throwable exception = null;

    public ClientServerResponse(ClientServerMessage request) {
        super._setReferenceId(request.getReferenceId());
        super._setSyncRequest(true);
    }

    @Override
    public boolean _isSyncRequest() {
        return (true);
    }

    /**
     * @return the exception
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * @param exception the exception to set
     */
    public void setException(Throwable exception) {
        this.exception = exception;
    }
    
    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }
}
