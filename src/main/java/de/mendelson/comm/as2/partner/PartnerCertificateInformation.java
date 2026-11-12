//$Header: /mec_as2/de/mendelson/comm/as2/partner/PartnerCertificateInformation.java 21    15/04/26 12:43 Heller $
package de.mendelson.comm.as2.partner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.mendelson.comm.as2.cem.CEMEntry;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores a certificate or key used by a partner. Every partner of a
 * communication may use several certificates with several priorities
 *
 * @author S.Heller
 * @version $Revision: 21 $
 */
public class PartnerCertificateInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * This interface is used to ensure the same int values in the CEMEnry enumeration and the
     * PartnerCertificateInformation enumeration (Category)
     */
    public interface CategoryID {
        int ID_CRYPT = 1;
        int ID_SIGN = 2;
        int ID_TLS = 3;
    }

    public enum Category {
        CRYPT(CategoryID.ID_CRYPT),
        SIGN(CategoryID.ID_SIGN),
        TLS(CategoryID.ID_TLS),
        SIGN_OVERWRITE_LOCALSTATION(4),
        CRYPT_OVERWRITE_LOCALSTATION(5);

        private final int id;

        Category(int id) {
            this.id = id;
        }

        @JsonValue
        public int toInt() {
            return this.id;
        }

        @JsonCreator
        public static Category of(int id) {
            for (Category category : Category.values()) {
                if (category.id == id) {
                    return category;
                }
            }
            throw new IllegalArgumentException("Unknown PartnerCertificateInformation.Category " + id);
        }
    }

    private PartnerCertificateInformation.Category category = PartnerCertificateInformation.Category.CRYPT;
    /**
     * The fingerprint id as used in the keystore
     */
    private String fingerprintSHA1;

    /**
     * Creates an empty entry
     */
    public PartnerCertificateInformation(PartnerCertificateInformation.Category category) {
        this.category = category;
        this.fingerprintSHA1 = "";
    }

    public PartnerCertificateInformation(String fingerprintSHA1, PartnerCertificateInformation.Category category) {
        this.category = category;
        this.fingerprintSHA1 = fingerprintSHA1;
    }

    /**
     * Is there already a certificate assigned to this information?
     *
     * @return
     */
    public boolean isEmpty() {
        return (this.fingerprintSHA1 == null || this.fingerprintSHA1.trim().isEmpty());
    }

    /**
     * @return the category
     */
    public PartnerCertificateInformation.Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(PartnerCertificateInformation.Category category) {
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
        if (anObject != null && anObject instanceof PartnerCertificateInformation) {
            PartnerCertificateInformation entry = (PartnerCertificateInformation) anObject;
            return (entry.fingerprintSHA1.equals(this.fingerprintSHA1)
                    && entry.getCategory() == this.category);
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.category);
        hash = 37 * hash + Objects.hashCode(this.fingerprintSHA1);
        return hash;
    }

    /**
     * Just for debug purpose
     */
    public String getDebugDisplay() {
        return (this.fingerprintSHA1 + " (" + this.category.toString() + ")");
    }

    /**
     * Serializes this certificate information
     */
    public byte[] serialize() throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (DataOutputStream dataOut = new DataOutputStream(out)) {
                dataOut.writeInt(this.category.toInt());
                byte[] fingerprintBytes = this.fingerprintSHA1.getBytes(StandardCharsets.UTF_8);
                dataOut.writeInt(fingerprintBytes.length);
                dataOut.write(fingerprintBytes);
            }
            return (out.toByteArray());
        }
    }

    public static PartnerCertificateInformation deserialize(byte[] data) throws Exception {
        PartnerCertificateInformation partnerCertificateInformation;
        try (ByteArrayInputStream in = new ByteArrayInputStream(data)) {
            try (DataInputStream dataIn = new DataInputStream(in)) {
                int category = dataIn.readInt();
                int fingerprintLength = dataIn.readInt();
                byte[] fingerprintBytes = dataIn.readNBytes(fingerprintLength);
                String fingerPrintSHA1 = new String(fingerprintBytes, StandardCharsets.UTF_8);
                partnerCertificateInformation = new PartnerCertificateInformation(fingerPrintSHA1,
                        PartnerCertificateInformation.Category.of(category));
            }
        }
        return (partnerCertificateInformation);
    }

}
