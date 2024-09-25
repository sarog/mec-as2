//$Header: /as2/de/mendelson/util/security/cert/clientserver/DownloadResponseKeystore.java 5     2/11/23 15:53 Heller $
package de.mendelson.util.security.cert.clientserver;

import de.mendelson.util.clientserver.messages.ClientServerResponse;
import de.mendelson.util.security.cert.KeystoreCertificate;
import java.io.IOException;
import java.io.ObjectInputStream;
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
 * @version $Revision: 5 $
 */
public class DownloadResponseKeystore extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<KeystoreCertificate> certList = new ArrayList<KeystoreCertificate>();    

    public DownloadResponseKeystore(DownloadRequestKeystore request) {
        super( request );
    }

    public void addCertificateList( List<KeystoreCertificate> list ){
        this.certList.addAll(list);
    }

    public List<KeystoreCertificate> getCertificateList(){
        return( this.certList );
    }
    
    
    @Override
    public String toString() {
        return ("Download response keystore");
    }    
    
    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }

}
