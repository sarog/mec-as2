//$Header: /oftp2/de/mendelson/util/clientserver/messages/ClientServerResponse.java 13    6/11/25 12:07 Heller $
package de.mendelson.util.clientserver.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.clientserver.ClientServerException;
import de.mendelson.util.clientserver.ClientServerExceptionContainer;
import de.mendelson.util.clientserver.SerializationDummy;
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
 * @version $Revision: 13 $
 */
public class ClientServerResponse extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private ClientServerExceptionContainer exceptionContainer = null;

    public ClientServerResponse(ClientServerMessage request) {
        super._setReferenceId(request.getReferenceId());
        super.setSyncRequest(true);
    }
    
    /**
     * Dummy constructor for Jackson deserialization only. Do not use in logic
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    protected ClientServerResponse() {
    }

    @Override
    public boolean isSyncRequest() {
        return (true);
    }

    /**
     * @return the exception
     */
    @JsonIgnore
    public ClientServerException getException() {
        return (ClientServerExceptionContainer.toThrowable(this.exceptionContainer));
    }

    /**
     * @param exception the exception to set
     */
    @JsonIgnore
    public void setException(Throwable exception) {        
        this.setExceptionContainer(ClientServerExceptionContainer.fromThrowable(exception));
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }

    /**
     * Dummy method for Jackson deserialization only. Do not use in logic
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public ClientServerExceptionContainer getExceptionContainer() {
        return exceptionContainer;
    }

    /**
     * Dummy method for Jackson deserialization only. Do not use in logic
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setExceptionContainer(ClientServerExceptionContainer exceptionContainer) {
        this.exceptionContainer = exceptionContainer;
    }
}
