//$Header: /oftp2/de/mendelson/util/security/keydata/KeystoreData.java 2     9/10/25 16:52 Heller $
package de.mendelson.util.security.keydata;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores keystore data
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class KeystoreData {

    private byte[] data;
    private final String securityProvider;
    private final int storageType;

    public KeystoreData(String securityProvider, int storageType, byte[] data) {
        this.securityProvider = securityProvider;
        this.storageType = storageType;
        this.data = data;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @return the securityProvider
     */
    public String getSecurityProvider() {
        return securityProvider;
    }

    /**
     * @return the storageType
     */
    public int getStorageType() {
        return storageType;
    }
    
    public String getStorageTypeAsStr(){
        return( KeydataAccessDB.intToKeystoreTypeStr(this.storageType));
    }

    /**
     * @param data the data to set
     */
    public void setData(byte[] data) {
        this.data = data;
    }
    

}
