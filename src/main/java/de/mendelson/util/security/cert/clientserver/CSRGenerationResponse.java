//$Header: /as2/de/mendelson/util/security/cert/clientserver/CSRGenerationResponse.java 8     12/01/26 8:26 Heller $
package de.mendelson.util.security.cert.clientserver;

import com.fasterxml.jackson.annotation.JsonProperty;
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
 *
 * @author S.Heller
 * @version $Revision: 8 $
 */
public class CSRGenerationResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String csrBase64 = null;

    public CSRGenerationResponse(CSRGenerationRequest request) {
        super(request);
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public CSRGenerationResponse() {
        super();
    }
  

    @Override
    public String toString() {
        return ("Generate CSR");
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }

    /**
     * @return the csrPEM
     */
    @JsonProperty("csrBase64")
    public String getCSRBase64() {
        return this.csrBase64;
    }

    /**
     * @param csrBase64 the csr to set, in BASE64 encoding
     */
    @JsonProperty("csrBase64")
    public void setCSRBase64(String csrBase64) {
        this.csrBase64 = csrBase64;
    }

}
