//$Header: /as2/de/mendelson/util/security/cert/clientserver/UploadRequestKeystore.java 14    19/08/25 11:21 Heller $
package de.mendelson.util.security.cert.clientserver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.util.security.cert.KeystoreStorageImplFile;
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
 * @version $Revision: 14 $
 */
public class UploadRequestKeystore extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final int KEYSTORE_TYPE_TLS = KeystoreStorageImplFile.KEYSTORE_USAGE_TLS;
    public static final int KEYSTORE_TYPE_ENC_SIGN = KeystoreStorageImplFile.KEYSTORE_USAGE_ENC_SIGN;
    
    private int keystoreUsage;
    private List<KeystoreCertificate> certificateList = new ArrayList<KeystoreCertificate>();

    public UploadRequestKeystore(final int KEYSTORE_USAGE) {
        this.keystoreUsage = KEYSTORE_USAGE;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public UploadRequestKeystore() {
        super();
        keystoreUsage = KEYSTORE_TYPE_ENC_SIGN;
    }
    
    
    @Override
    public String toString() {
        return ("Upload request keystore");
    }

    @JsonIgnore
    public void addCertificateList(List<KeystoreCertificate> list) {
        this.certificateList.addAll(list);
    }

    public List<KeystoreCertificate> getCertificateList() {
        return (this.certificateList);
    }
    
    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }

    /**
     * @return the keystoreUsage
     */
    public int getKeystoreUsage() {
        return keystoreUsage;
    }

    /**
     * @param certificateList the certificateList to set
     */
    public void setCertificateList(List<KeystoreCertificate> certificateList) {
        this.certificateList.clear();
        this.certificateList.addAll( certificateList );
    }

}
