//$Header: /mec_as2/de/mendelson/comm/as2/partner/PartnerCertificateInformationList.java 31    15/04/26 12:43 Heller $
package de.mendelson.comm.as2.partner;

import de.mendelson.comm.as2.cem.CEMEntry;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.MecResourceBundle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

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
 * @version $Revision: 31 $
 */
public class PartnerCertificateInformationList implements Serializable {

    private static final long serialVersionUID = 1L;
    //create empty container
    private final PartnerCertificateInformation infoTLS = new PartnerCertificateInformation(PartnerCertificateInformation.Category.TLS);
    //create empty container
    private final PartnerCertificateInformation infoCrypt = new PartnerCertificateInformation(PartnerCertificateInformation.Category.CRYPT);
    //create empty container
    private final PartnerCertificateInformation infoSign = new PartnerCertificateInformation(PartnerCertificateInformation.Category.SIGN);
    //create empty container
    private final PartnerCertificateInformation infoSignOverwriteLocalstation
            = new PartnerCertificateInformation(PartnerCertificateInformation.Category.SIGN_OVERWRITE_LOCALSTATION);
    private final PartnerCertificateInformation infoCryptOverwriteLocalstation
            = new PartnerCertificateInformation(PartnerCertificateInformation.Category.CRYPT_OVERWRITE_LOCALSTATION);
    private static final MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleCertificateInformation.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    public PartnerCertificateInformationList() {
    }

    /**
     * Returns the right info container for the passed category
     */
    private PartnerCertificateInformation getContainerByCategory(PartnerCertificateInformation.Category category) {
        if (category == PartnerCertificateInformation.Category.CRYPT) {
            return (this.getInfoCrypt());
        } else if (category == PartnerCertificateInformation.Category.SIGN) {
            return (this.getInfoSign());
        } else if (category == PartnerCertificateInformation.Category.TLS) {
            return (this.getInfoTLS());
        } else if (category == PartnerCertificateInformation.Category.CRYPT_OVERWRITE_LOCALSTATION) {
            return (this.infoCryptOverwriteLocalstation);
        } else if (category == PartnerCertificateInformation.Category.SIGN_OVERWRITE_LOCALSTATION) {
            return (this.infoSignOverwriteLocalstation);
        } else {
            throw new IllegalArgumentException("PartnerCertificateInformationList.getContainerByCategory: Unsupported category " + category);
        }
    }

    /**
     * Sets a single cert information to the partner, overwriting any existing
     * with the same status, priority and type
     */
    public void setCertificateInformation(PartnerCertificateInformation information) {
        PartnerCertificateInformation container = this.getContainerByCategory(information.getCategory());
        container.setFingerprintSHA1(information.getFingerprintSHA1());
    }

    /**
     * Returns the partner certificate with the passed category, status and
     * priority. If nothing is found, null is returned
     */
    public PartnerCertificateInformation getPartnerCertificate(PartnerCertificateInformation.Category category) {
        PartnerCertificateInformation container = this.getContainerByCategory(category);
        return (container);
    }

    /**
     * Sets a new certificate to the partner - of the specific category
     */
    public PartnerCertificateInformation setNewCertificate(String fingerprintSHA1,
            PartnerCertificateInformation.Category category) {
        PartnerCertificateInformation container = this.getContainerByCategory(category);
        container.setFingerprintSHA1(fingerprintSHA1);
        return (container);
    }

    /**
     * Returns a string that contains information about the actual certificate
     * usage
     */
    public String getCertificatePurposeDescription(CertificateManager manager, Partner partner,
            PartnerCertificateInformation.Category category) {
        StringBuilder builder = new StringBuilder();
        PartnerCertificateInformation information = this.getPartnerCertificate(category);
        String alias = manager.getAliasByFingerprint(information.getFingerprintSHA1());
        if (partner.isLocalStation()) {
            if (category == PartnerCertificateInformation.Category.CRYPT) {
                builder.append(rb.getResourceString("localstation.decrypt",
                        new Object[]{partner.getName(), alias}));
            }
            if (category == PartnerCertificateInformation.Category.SIGN) {
                builder.append(rb.getResourceString("localstation.sign",
                        new Object[]{partner.getName(), alias}));
            }
        }
        return (builder.toString());
    }

    /**
     * Overwrite the equal method of object
     *
     * @param anObject object ot compare
     */
    @Override
    public boolean equals(Object anObject) {
        if (anObject == this) {
            return (true);
        }
        if (anObject != null && anObject instanceof PartnerCertificateInformationList) {
            PartnerCertificateInformationList entry = (PartnerCertificateInformationList) anObject;
            return (entry.infoCrypt.equals(this.infoCrypt)
                    && entry.infoSign.equals(this.infoSign)
                    && entry.infoTLS.equals(this.infoTLS)
                    && entry.infoCryptOverwriteLocalstation.equals(this.infoCryptOverwriteLocalstation)
                    && entry.infoSignOverwriteLocalstation.equals(this.infoSignOverwriteLocalstation));
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.infoTLS);
        hash = 47 * hash + Objects.hashCode(this.infoCrypt);
        hash = 47 * hash + Objects.hashCode(this.infoSign);
        hash = 47 * hash + Objects.hashCode(this.infoSignOverwriteLocalstation);
        hash = 47 * hash + Objects.hashCode(this.infoCryptOverwriteLocalstation);
        return hash;
    }

    /**
     * Returns all available certificates as list
     */
    public Collection<PartnerCertificateInformation> asList() {
        PartnerCertificateInformation.Category[] categories = PartnerCertificateInformation.Category.values();
        List<PartnerCertificateInformation> list = new ArrayList<PartnerCertificateInformation>();
        for (PartnerCertificateInformation.Category category : categories) {
            PartnerCertificateInformation container = this.getContainerByCategory(category);
            list.add(container);
        }
        return (list);
    }

    /**
     * @return the infoTLS
     */
    public PartnerCertificateInformation getInfoTLS() {
        return infoTLS;
    }

    /**
     * @param infoTLS the infoTLS to set
     */
    public void setInfoTLS(PartnerCertificateInformation infoTLS) {
        this.infoTLS.setFingerprintSHA1(infoTLS.getFingerprintSHA1());
    }

    /**
     * @return the infoCrypt
     */
    public PartnerCertificateInformation getInfoCrypt() {
        return infoCrypt;
    }

    /**
     * @param infoCrypt the infoCrypt to set
     */
    public void setInfoCrypt(PartnerCertificateInformation infoCrypt) {
        this.infoCrypt.setFingerprintSHA1(infoTLS.getFingerprintSHA1());
    }

    /**
     * @return the infoSign
     */
    public PartnerCertificateInformation getInfoSign() {
        return infoSign;
    }

    /**
     * @param infoSign the infoSign to set
     */
    public void setInfoSign(PartnerCertificateInformation infoSign) {
        this.infoSign.setFingerprintSHA1(infoTLS.getFingerprintSHA1());
    }

    /**
     * Serializes this certificate information list
     */
    public byte[] serialize() throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (DataOutputStream dataOut = new DataOutputStream(out)) {
                byte[] tls = this.infoTLS.serialize();
                byte[] crypt = this.infoCrypt.serialize();
                byte[] cryptOverwrite = this.infoCryptOverwriteLocalstation.serialize();
                byte[] sign = this.infoSign.serialize();
                byte[] signOverwrite = this.infoSignOverwriteLocalstation.serialize();
                dataOut.writeInt(tls.length);
                dataOut.write(tls);
                dataOut.writeInt(crypt.length);
                dataOut.write(crypt);
                dataOut.writeInt(cryptOverwrite.length);
                dataOut.write(cryptOverwrite);
                dataOut.writeInt(sign.length);
                dataOut.write(sign);
                dataOut.writeInt(signOverwrite.length);
                dataOut.write(signOverwrite);
            }
            return out.toByteArray();
        }
    }

    public static PartnerCertificateInformationList deserialize(byte[] data) throws Exception {
        PartnerCertificateInformationList partnerCertificateInformationList = new PartnerCertificateInformationList();
        try (ByteArrayInputStream in = new ByteArrayInputStream(data)) {
            try (DataInputStream dataIn = new DataInputStream(in)) {
                int tlsLength = dataIn.readInt();
                byte[] tlsArray = dataIn.readNBytes(tlsLength);
                PartnerCertificateInformation tls = PartnerCertificateInformation.deserialize(tlsArray);
                partnerCertificateInformationList.setCertificateInformation(tls);
                int cryptLength = dataIn.readInt();
                byte[] cryptArray = dataIn.readNBytes(cryptLength);
                PartnerCertificateInformation crypt = PartnerCertificateInformation.deserialize(cryptArray);
                partnerCertificateInformationList.setCertificateInformation(crypt);
                int cryptOverwriteLength = dataIn.readInt();
                byte[] cryptOverwriteArray = dataIn.readNBytes(cryptOverwriteLength);
                PartnerCertificateInformation cryptOverwrite = PartnerCertificateInformation.deserialize(cryptOverwriteArray);
                partnerCertificateInformationList.setCertificateInformation(cryptOverwrite);
                int signLength = dataIn.readInt();
                byte[] signArray = dataIn.readNBytes(signLength);
                PartnerCertificateInformation sign = PartnerCertificateInformation.deserialize(signArray);
                partnerCertificateInformationList.setCertificateInformation(sign);
                int signOverwriteLength = dataIn.readInt();
                byte[] signOverwriteArray = dataIn.readNBytes(signOverwriteLength);
                PartnerCertificateInformation signOverwrite = PartnerCertificateInformation.deserialize(signOverwriteArray);
                partnerCertificateInformationList.setCertificateInformation(signOverwrite);
            }
        }
        return (partnerCertificateInformationList);
    }

}
