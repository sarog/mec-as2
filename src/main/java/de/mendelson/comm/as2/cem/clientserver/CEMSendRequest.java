//$Header: /as2/de/mendelson/comm/as2/cem/clientserver/CEMSendRequest.java 2     14.05.13 16:38 Heller $
package de.mendelson.comm.as2.cem.clientserver;

import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.security.cert.KeystoreCertificate;
import java.io.Serializable;
import java.util.Date;
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
 * @version $Revision: 2 $
 */
public class CEMSendRequest extends ClientServerMessage implements Serializable {

    private Partner initiator = null;
    private KeystoreCertificate certificate = null;
    private Date activationDate = null;

    public CEMSendRequest() {
    }

    @Override
    public String toString() {
        return ("Send a CEM");
    }

    /**
     * @return the initiator
     */
    public Partner getInitiator() {
        return initiator;
    }

    /**
     * @param initiator the initiator to set
     */
    public void setInitiator(Partner initiator) {
        this.initiator = initiator;
    }

    /**
     * @return the certificate
     */
    public KeystoreCertificate getCertificate() {
        return certificate;
    }

    /**
     * @param certificate the certificate to set
     */
    public void setCertificate(KeystoreCertificate certificate) {
        this.certificate = certificate;
    }

    /**
     * @return the activationDate
     */
    public Date getActivationDate() {
        return activationDate;
    }

    /**
     * @param activationDate the activationDate to set
     */
    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }
    
}
