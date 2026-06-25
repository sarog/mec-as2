//$Header: /as2/de/mendelson/comm/as2/cem/clientserver/CEMSendResponse.java 8     11/06/25 13:28 Heller $
package de.mendelson.comm.as2.cem.clientserver;

import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class CEMSendResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Partner> informedPartner = null;

    public CEMSendResponse(CEMSendRequest request) {
        super(request);
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public CEMSendResponse() {
        super();
    }

    @Override
    public String toString() {
        return ("Resonse to a CEM send request");
    }

    /**
     * @return the informed partner
     */
    public List<Partner> getInformedPartner() {
        if (this.informedPartner != null) {
            return (this.informedPartner);
        } else {
            return (new ArrayList<Partner>());
        }
    }

    /**
     */
    public void setInformedPartner(List<Partner> informedPartner) {
        this.informedPartner = informedPartner;
    }
}
