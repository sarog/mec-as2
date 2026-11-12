//$Header: /mec_as2/de/mendelson/comm/as2/cert/CertificateAccessDB.java 38    15/04/26 12:42 Heller $
package de.mendelson.comm.as2.cert;

import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerCertificateInformation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Access the certificate lists in the database
 *
 * @author S.Heller
 * @version $Revision: 38 $
 */
public class CertificateAccessDB {

    public CertificateAccessDB() {
    }

    /**
     * Returns the list of certificates used by the passed partner READ lock on
     * certificates
     */
    public void loadPartnerCertificateInformationIntoPartner(List<Partner> partnerList, Connection configConnection) throws Exception {
        if (partnerList == null || partnerList.isEmpty()) {
            return;
        }
        int batchSize = 250;
        for (int i = 0; i < partnerList.size(); i += batchSize) {
            int toIndex = Math.min(i + batchSize, partnerList.size());
            List<Partner> subList = partnerList.subList(i, toIndex);
            this.loadPartnerCertificateInformationSublist(subList, configConnection);
        }
    }

    private void loadPartnerCertificateInformationSublist(List<Partner> partnerList, Connection configConnection) throws Exception {
        StringBuilder idList = new StringBuilder();
        for (int i = 0; i < partnerList.size(); i++) {
            if (i > 0) {
                idList.append(",");
            }
            idList.append(partnerList.get(i).getDBId());
        }
        //key: partner id
        Map<Integer, List<PartnerCertificateInformation>> certMap
                = new HashMap<Integer, List<PartnerCertificateInformation>>();
        String query = "SELECT partnerid, fingerprintsha1, category FROM certificates "
                + "WHERE partnerid IN (" + idList.toString() + ")";
        try (PreparedStatement statement = configConnection.prepareStatement(query)) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int partnerId = result.getInt("partnerid");
                    String fingerprint = result.getString("fingerprintsha1");
                    PartnerCertificateInformation information = new PartnerCertificateInformation(
                            fingerprint, PartnerCertificateInformation.Category.of(
                                    result.getInt("category")));
                    List<PartnerCertificateInformation> partnerCerts = certMap.get(partnerId);
                    if (partnerCerts == null) {
                        partnerCerts = new ArrayList<PartnerCertificateInformation>();
                        certMap.put(partnerId, partnerCerts);
                    }
                    partnerCerts.add(information);
                }
            }
        }
        for (Partner partner : partnerList) {
            List<PartnerCertificateInformation> certs = certMap.get(partner.getDBId());
            if (certs != null) {
                for (PartnerCertificateInformation info : certs) {
                    partner.setCertificateInformation(info);
                }
            }
        }
    }

    /**
     * Stores the actual partner certificate list of a partner Needs DELETE lock
     * on certificates
     */
    public void storePartnerCertificateInformationList(Partner partner, Connection configConnection) throws Exception {
        this.deletePartnerCertificateInformationList(partner, configConnection);
        Collection<PartnerCertificateInformation> list = partner.getPartnerCertificateInformationList().asList();
        if (!list.isEmpty()) {
            try (PreparedStatement statement = configConnection.prepareStatement(
                    "INSERT INTO certificates(partnerid,fingerprintsha1,category)VALUES(?,?,?)")) {
                for (PartnerCertificateInformation certInfo : list) {
                    statement.setInt(1, partner.getDBId());
                    statement.setString(2, certInfo.getFingerprintSHA1());
                    statement.setInt(3, certInfo.getCategory().toInt());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        }
    }

    /**
     * Deletes the actual partner certificate list of a partner. A config
     * connection is passed to allow the storing process in a transactional way.
     * DELETE lock on table certificates
     */
    public void deletePartnerCertificateInformationList(Partner partner, Connection configConnection) throws Exception {
        try (PreparedStatement statement = configConnection.prepareStatement(
                "DELETE FROM certificates WHERE partnerid=?")) {
            statement.setInt(1, partner.getDBId());
            statement.executeUpdate();
        }
    }
}
