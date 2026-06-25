//$Header: /as2/de/mendelson/util/security/cert/CertificateInUseInfo.java 11    9/04/26 9:45 Heller $
package de.mendelson.util.security.cert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import de.mendelson.util.clientserver.SerializationDummy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Contains information about the use of a certificate
 *
 * @author S.Heller
 * @version $Revision: 11 $
 */
public class CertificateInUseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum UsedByPartner {
        REMOTE(1),
        GATEWAY(2),
        ROUTED(3),
        LOCALSTATION(4),
        LOCALSTATION_VIRTUAL(5);

        private final int value;

        private UsedByPartner(int value) {
            this.value = value;
        }

        @JsonValue
        public int toInt() {
            return value;
        }

        @JsonCreator
        public static UsedByPartner of(int value) {
            for (UsedByPartner partner : UsedByPartner.values()) {
                if (partner.value == value) {
                    return partner;
                }
            }
            throw new IllegalArgumentException("CertificateInUseInfo.UsedByPartner: Unknown value: " + value);
        }
    }

    private final List<SingleCertificateInUseInfo> singleUsageList
            = Collections.synchronizedList(new ArrayList<SingleCertificateInUseInfo>());
    private String fingerprintSHA1;

    public CertificateInUseInfo(String fingerprintSHA1) {
        this.fingerprintSHA1 = fingerprintSHA1;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public CertificateInUseInfo() {
    }

    @JsonIgnore
    public boolean isEmpty() {
        synchronized (this.singleUsageList) {
            return (this.singleUsageList.isEmpty());
        }
    }

    @JsonIgnore
    public List<SingleCertificateInUseInfo> getUsageList() {
        List<SingleCertificateInUseInfo> list;
        synchronized (this.singleUsageList) {
            list = new ArrayList<SingleCertificateInUseInfo>(this.singleUsageList);
        }
        return (Collections.unmodifiableList(list));
    }

    @JsonIgnore
    public void addUsage(CertificateInUseInfo.UsedByPartner partnerType, String partnerName, String details) {
        SingleCertificateInUseInfo info = new SingleCertificateInUseInfo(partnerType, partnerName, details);
        synchronized (this.singleUsageList) {
            this.singleUsageList.add(info);
        }
    }

    /**
     * @return the message
     */
    @JsonIgnore
    public String getMessageAsText() {
        StringBuilder builder = new StringBuilder();
        synchronized (this.singleUsageList) {
            for (SingleCertificateInUseInfo info : this.singleUsageList) {
                builder.append(info.getPartnerName())
                        .append(" (")
                        .append(info.getDetails())
                        .append(")")
                        .append("\n");
            }
        }
        return (builder.toString());
    }

    /**
     * @return the fingerprintSHA1 of the certificate this info object is for
     */
    public String getFingerprintSHA1() {
        return fingerprintSHA1;
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
        if (anObject != null && anObject instanceof CertificateInUseInfo) {
            CertificateInUseInfo info = (CertificateInUseInfo) anObject;
            return (info.fingerprintSHA1.equals(this.fingerprintSHA1));
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.fingerprintSHA1);
        return hash;
    }

    
    /**
     * @return the singleUsageList
     */
    public List<SingleCertificateInUseInfo> getSingleUsageList() {
        List<SingleCertificateInUseInfo> tempList;
        synchronized (this.singleUsageList) {
            tempList = new ArrayList<SingleCertificateInUseInfo>(this.singleUsageList);
        }
        return tempList;
    }

    /**
     * This is a dummy method for the deserialization process. Do not use in
     * logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setSingleUsageList(List<SingleCertificateInUseInfo> singleUsageList) {
        synchronized (this.singleUsageList) {
            this.singleUsageList.clear();
            this.singleUsageList.addAll(singleUsageList);
        }
    }

    /**
     * @param fingerprintSHA1 the fingerprintSHA1 to set
     */
    public void setFingerprintSHA1(String fingerprintSHA1) {
        this.fingerprintSHA1 = fingerprintSHA1;
    }

    public static class SingleCertificateInUseInfo implements Serializable {

        private static final long serialVersionUID = 1L;
        private CertificateInUseInfo.UsedByPartner usedBy;
        private String partnerName;
        private String details;

        public SingleCertificateInUseInfo(CertificateInUseInfo.UsedByPartner usedBy, String partnerName, String details) {
            this.usedBy = usedBy;
            this.details = details;
            this.partnerName = partnerName;
        }

        /**
         * This is a dummy constructor for the deserialization process. Do not
         * use in logic.
         */
        @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
        public SingleCertificateInUseInfo() {
        }

        /**
         * @return the partnerName
         */
        public String getPartnerName() {
            return partnerName;
        }

        /**
         * @return the details
         */
        public String getDetails() {
            return details;
        }

        /**
         * @return the TYPE
         */
        public CertificateInUseInfo.UsedByPartner getUsedBy() {
            return usedBy;
        }

        /**
         * This is a dummy method for the deserialization process. Do not use in
         * logic.
         */
        @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
        public void setUsedBy(CertificateInUseInfo.UsedByPartner usedBy) {
            this.usedBy = usedBy;
        }

        /**
         * @param partnerName the partnerName to set
         */
        public void setPartnerName(String partnerName) {
            this.partnerName = partnerName;
        }

        /**
         * @param details the details to set
         */
        public void setDetails(String details) {
            this.details = details;
        }
    }

}
