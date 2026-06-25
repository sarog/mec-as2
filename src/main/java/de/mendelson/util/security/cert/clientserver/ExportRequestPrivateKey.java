//$Header: /as4/de/mendelson/util/security/cert/clientserver/ExportRequestPrivateKey.java 4     11/06/25 13:17 Heller $
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
 * @version $Revision: 4 $
 */
public class ExportRequestPrivateKey extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String EXPORTFORMAT_PKCS12 = "PKCS#12";
    public static final String EXPORTFORMAT_PEM = "PEM";
    public static final int KEYSTORE_USAGE_TLS = KeystoreStorageImplFile.KEYSTORE_USAGE_TLS;
    public static final int KEYSTORE_USAGE_ENC_SIGN = KeystoreStorageImplFile.KEYSTORE_USAGE_ENC_SIGN;
    private int keystoreUsageSource;
    private String fingerprintSHA1;
    private String serverSideFilename;
    private char[] serverSidePass;
    private String exportFormat;

    public ExportRequestPrivateKey(final int KEYSTORE_USAGE_SOURCE,
            String fingerprintSHA1,
            String serverSideFilename,
            char[] serverSidePass,
            String exportFormat) {
        this.keystoreUsageSource = KEYSTORE_USAGE_SOURCE;
        this.fingerprintSHA1 = fingerprintSHA1;
        this.serverSideFilename = serverSideFilename;
        this.serverSidePass = serverSidePass;
        this.exportFormat = exportFormat;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public ExportRequestPrivateKey() {
        super();
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
     * @return the keystoreType, one of
     * ExportRequestPrivateKeyPKCS12.KEYSTORE_USAGE_TLS or
     * ExportRequestPrivateKeyPKCS12.KEYSTORE_USAGE_ENC_SIGN
     */
    public int getKeystoreUsageSource() {
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

    /**
     * @return the exportFormat
     */
    public String getExportFormat() {
        return exportFormat;
    }

    /**
     * @param keystoreUsageSource the keystoreUsageSource to set
     */
    public void setKeystoreUsageSource(int keystoreUsageSource) {
        this.keystoreUsageSource = keystoreUsageSource;
    }

    /**
     * @param fingerprintSHA1 the fingerprintSHA1 to set
     */
    public void setFingerprintSHA1(String fingerprintSHA1) {
        this.fingerprintSHA1 = fingerprintSHA1;
    }

    /**
     * @param serverSideFilename the serverSideFilename to set
     */
    public void setServerSideFilename(String serverSideFilename) {
        this.serverSideFilename = serverSideFilename;
    }

    /**
     * @param serverSidePass the serverSidePass to set
     */
    public void setServerSidePass(char[] serverSidePass) {
        this.serverSidePass = serverSidePass;
    }

    /**
     * @param exportFormat the exportFormat to set
     */
    public void setExportFormat(String exportFormat) {
        this.exportFormat = exportFormat;
    }
}
