//$Header: /as2/de/mendelson/util/security/cert/TrustType.java 1     11/04/25 11:26 Heller $
package de.mendelson.util.security.cert;

import java.util.Objects;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Container for the trust type of a certificate
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class TrustType {

    protected static final int TRUST_TYPE_ROOT = 1;
    protected static final int TRUST_TYPE_SELFSIGNED = 2;
    protected static final int TRUST_TYPE_TRUSTED = 3;
    protected static final int TRUST_TYPE_UNTRUSTED = 4;
    
    private final int TRUST_TYPE;
    private final String text;
    
    public TrustType(final int TRUST_TYPE, String text) {
        this.TRUST_TYPE = TRUST_TYPE;
        this.text = text;
    }
    
    @Override
    public String toString(){
        return( this.text );
    }

    
    
    /**
     * Overwrite the equal method of object
     *
     * @param anObject object to compare
     */
    @Override
    public boolean equals(Object anObject) {
        if (anObject == this) {
            return (true);
        }
        if (anObject != null && anObject instanceof TrustType) {
            TrustType trustType = (TrustType) anObject;
            return (trustType.TRUST_TYPE == TRUST_TYPE);
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.TRUST_TYPE;
        hash = 59 * hash + Objects.hashCode(this.text);
        return hash;
    }

    /**
     * @return the TRUST_TYPE
     */
    public int getTrustType() {
        return TRUST_TYPE;
    }
}
