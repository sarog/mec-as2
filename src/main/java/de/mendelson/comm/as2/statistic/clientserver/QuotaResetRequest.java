//$Header: /as2/de/mendelson/comm/as2/statistic/clientserver/QuotaResetRequest.java 7     11/06/25 13:29 Heller $
package de.mendelson.comm.as2.statistic.clientserver;

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
 * @version $Revision: 7 $
 */
public class QuotaResetRequest extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String localStationId;
    private String partnerId;

    public QuotaResetRequest(String localStationId, String partnerId) {
        this.localStationId = localStationId;
        this.partnerId = partnerId;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public QuotaResetRequest() {
    }

    @Override
    public String toString() {
        return ("Reset quota");
    }

    /**
     * @return the localStationId
     */
    public String getLocalStationId() {
        return localStationId;
    }

    /**
     * @return the partnerId
     */
    public String getPartnerId() {
        return partnerId;
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }
}
