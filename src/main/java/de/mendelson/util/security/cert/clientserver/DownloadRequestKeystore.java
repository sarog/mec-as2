//$Header: /as4/de/mendelson/util/security/cert/clientserver/DownloadRequestKeystore.java 6     11/06/25 13:17 Heller $
package de.mendelson.util.security.cert.clientserver;

import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.security.cert.KeystoreStorageImplFile;
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
 * @version $Revision: 6 $
 */
public class DownloadRequestKeystore extends ClientServerMessage implements Serializable {

    public static final int KEYSTORE_TYPE_TLS = KeystoreStorageImplFile.KEYSTORE_USAGE_TLS;
    public static final int KEYSTORE_TYPE_ENC_SIGN = KeystoreStorageImplFile.KEYSTORE_USAGE_ENC_SIGN;
    
    private static final long serialVersionUID = 1L;

    private int keystoreUsage;

    public DownloadRequestKeystore(final int KEYSTORE_USAGE) {
        this.keystoreUsage = KEYSTORE_USAGE;
    }

     /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public DownloadRequestKeystore() {
        super();    
        this.keystoreUsage = KEYSTORE_TYPE_ENC_SIGN;
    }
    
    
    @Override
    public String toString() {
        return ("Download request keystore");
    }
    
    public int getKeystoreUsage(){
        return( this.keystoreUsage);
    }
    
    /**
     * This is a dummy method for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setKeystoreUsage(final int KEYSTORE_USAGE){
        this.keystoreUsage = KEYSTORE_USAGE;
    }
    
    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }
    

}
