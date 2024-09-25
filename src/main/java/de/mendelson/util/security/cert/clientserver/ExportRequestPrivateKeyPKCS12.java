//$Header: /as4/de/mendelson/util/security/cert/clientserver/ExportRequestPrivateKeyPKCS12.java 5     9/11/23 9:52 Heller $
package de.mendelson.util.security.cert.clientserver;

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
 * @version $Revision: 5 $
 */
public class ExportRequestPrivateKeyPKCS12 extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int KEYSTORE_USAGE_TLS = KeystoreStorageImplFile.KEYSTORE_USAGE_TLS;
    public static final int KEYSTORE_USAGE_ENC_SIGN = KeystoreStorageImplFile.KEYSTORE_USAGE_ENC_SIGN;
    private final int keystoreUsageSource;
    private final String fingerprintSHA1;
    private final String serverSideFilename;
    private final char[] serverSidePass;

    public ExportRequestPrivateKeyPKCS12(final int KEYSTORE_USAGE_SOURCE,
            String fingerprintSHA1,
            String serverSideFilename,
            char[] serverSidePass) {
        this.keystoreUsageSource = KEYSTORE_USAGE_SOURCE;
        this.fingerprintSHA1 = fingerprintSHA1;
        this.serverSideFilename = serverSideFilename;
        this.serverSidePass = serverSidePass;
    }

    @Override
    public String toString() {
        return ("Export private key to pkcs12");
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }

    /**
     * @return the keystoreType, one of ExportRequestPrivateKeyPKCS12.KEYSTORE_USAGE_TLS 
     * or ExportRequestPrivateKeyPKCS12.KEYSTORE_USAGE_ENC_SIGN
     */
    public int getKeystoreUsage() {
        return keystoreUsageSource;
    }

    /**
     * @return the fingerprintSHA1
     */
    public String getFingerprintSHA1() {
        return fingerprintSHA1;
    }

    /**
     * @return the serverSideFilename
     */
    public String getServerSideFilename() {
        return serverSideFilename;
    }

    /**
     * @return the serverSidePass
     */
    public char[] getServerSidePass() {
        return serverSidePass;
    }
}
