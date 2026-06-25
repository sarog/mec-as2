//$Header: /as2/de/mendelson/comm/as2/partner/clientserver/SinglePartnerDeleteResponse.java 3     11/06/25 13:28 Heller $
package de.mendelson.comm.as2.partner.clientserver;

import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
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
public class SinglePartnerDeleteResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Partner partner = null;

    public SinglePartnerDeleteResponse(SinglePartnerDeleteRequest request) {
        super(request);
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public SinglePartnerDeleteResponse() {
        super();
    }
    
    @Override
    public String toString() {
        return ("Delete partner");
    }

    /**
     * @return the partner
     */
    public Partner getPartner() {
        return partner;
    }

    /**
     * @param partner the partner to set
     */
    public void setPartner(Partner partner) {
        this.partner = partner;
    }

   
}
