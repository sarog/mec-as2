//$Header: /as2/de/mendelson/comm/as2/partner/PartnerHttpHeader.java 7     12/03/26 15:37 Heller $
package de.mendelson.comm.as2.partner;

import java.io.Serializable;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores all information about a single user defined http header of a partner
 *
 * @author S.Heller
 * @version $Revision: 7 $
 */
public class PartnerHttpHeader implements Serializable, Comparable<PartnerHttpHeader> {

    private static final long serialVersionUID = 1L;
    private String key = "";
    private String value = "";

    public PartnerHttpHeader() {
        super();
    }

    public PartnerHttpHeader(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }

    /**
     * Make the header comparable, allows Collections.sort()
     * @param other
     * @return 
     */
    @Override
    public int compareTo(PartnerHttpHeader other) {
        if (other == null) {
            return 1;
        }
        String otherKey = other.getKey() == null ? "" : other.getKey();
        return this.key.compareToIgnoreCase(otherKey);
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
        if (anObject != null && anObject instanceof PartnerHttpHeader) {
            PartnerHttpHeader header = (PartnerHttpHeader) anObject;
            return (header.getKey().equalsIgnoreCase(this.getKey()) && header.getValue().equals(this.getValue()));
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.getKey() != null ? this.getKey().hashCode() : 0);
        hash = 89 * hash + (this.getValue() != null ? this.getValue().hashCode() : 0);
        return hash;
    }

    /**
     * @return the key, non-null
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        if (key == null) {
            key = "";
        }
        this.key = key;
    }

    /**
     * @return the value, non-null
     */
    public String getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        if (value == null) {
            value = "";
        }
        this.value = value;
    }

}
