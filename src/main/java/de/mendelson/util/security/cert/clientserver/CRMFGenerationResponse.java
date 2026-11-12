//$Header: /as4/de/mendelson/util/security/cert/clientserver/CRMFGenerationResponse.java 1     8/01/26 10:58 Heller $
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
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class CRMFGenerationResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String crmfBase64 = null;

    public CRMFGenerationResponse(CRMFGenerationRequest request) {
        super(request);
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public CRMFGenerationResponse() {
        super();
    }

    @Override
    public String toString() {
        return ("Generate CRMF");
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }

    /**
     * @return the crmfBase64
     */
    public String getCrmfBase64() {
        return crmfBase64;
    }

    /**
     * @param crmfBase64 the crmfBase64 to set
     */
    public void setCrmfBase64(String crmfBase64) {
        this.crmfBase64 = crmfBase64;
    }

   

}
