//$Header: /as2/de/mendelson/comm/as2/partner/PartnerCertificateInformation.java 18    2/11/23 15:52 Heller $
package de.mendelson.comm.as2.partner;

import de.mendelson.comm.as2.cem.CEMEntry;
import java.io.Serializable;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Stores a certificate or key used by a partner. Every partner of a communication may use
 * several certificates with several priorities
 * @author S.Heller
 * @version $Revision: 18 $
 */
public class PartnerCertificateInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int CATEGORY_CRYPT = CEMEntry.CATEGORY_CRYPT;
    public static final int CATEGORY_SIGN = CEMEntry.CATEGORY_SIGN;
    public static final int CATEGORY_TLS = CEMEntry.CATEGORY_TLS;
    public static final int CATEGORY_SIGN_OVERWRITE_LOCALSTATION = 4;
    public static final int CATEGORY_CRYPT_OVERWRITE_LOCALSTATION = 5;
    private int category = CATEGORY_CRYPT;
    /**The fingerprint id as used in the keystore*/
    private String fingerprintSHA1;

    /**Creates an empty entry*/
    public PartnerCertificateInformation(int category) {
        this.category = category;
        this.fingerprintSHA1 = "";
    }
    
    public PartnerCertificateInformation(String fingerprintSHA1, int category) {
        this.category = category;
        this.fingerprintSHA1 = fingerprintSHA1;
    }

    /**Is there already a certificate assigned to this information?
     * 
     * @return 
     */
    public boolean isEmpty(){
        return( this.fingerprintSHA1 == null || this.fingerprintSHA1.trim().isEmpty());
    }
    
    /**
     * @return the category
     */
    public int getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(int category) {
        this.category = category;
    }

    /**
     * @return the fingerprint SHA1
     */
    public String getFingerprintSHA1() {
        return fingerprintSHA1;
    }

    /**
     * @param fingerprint the alias to set
     */
    public void setFingerprintSHA1(String fingerprint) {
        this.fingerprintSHA1 = fingerprint;
    }

    /**Overwrite the equal method of object
     *@param anObject object ot compare
     */
    @Override
    public boolean equals(Object anObject) {
        if (anObject == this) {
            return (true);
        }
        if (anObject != null && anObject instanceof PartnerCertificateInformation) {
            PartnerCertificateInformation entry = (PartnerCertificateInformation) anObject;
            return (entry.fingerprintSHA1.equals(this.fingerprintSHA1)
                    && entry.getCategory() == this.category);
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.category;
        hash = 79 * hash + (this.fingerprintSHA1 != null ? this.fingerprintSHA1.hashCode() : 0);
        return hash;
    }


    /**Just for debug purpose*/
    public String getDebugDisplay(){
        return( this.fingerprintSHA1 + " (" + CEMEntry.getCategoryLocalized(this.category) + ")");
    }

}
