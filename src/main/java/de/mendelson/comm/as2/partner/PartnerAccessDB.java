//$Header: /mec_as2/de/mendelson/comm/as2/partner/PartnerAccessDB.java 123   15/04/26 12:43 Heller $
package de.mendelson.comm.as2.partner;

import de.mendelson.comm.as2.cert.CertificateAccessDB;
import de.mendelson.comm.as2.message.MessageCompressionType;
import de.mendelson.comm.as2.message.MessageContentTransferEncodingType;
import de.mendelson.comm.as2.send.HttpConnectionParameter;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.oauth2.OAuth2AccessDB;
import de.mendelson.util.oauth2.OAuth2Config;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Implementation of a server log for the mendelson as2 server database
 *
 * @author S.Heller
 * @version $Revision: 123 $
 */
public class PartnerAccessDB {

    private final CertificateAccessDB certificateAccess;
    private final PartnerEventAccessDB eventAccess;
    private final OAuth2AccessDB oAuth2Access;
    private final IDBDriverManager dbDriverManager;

    /**
     *
     */
    public PartnerAccessDB(IDBDriverManager dbDriverManager) {
        this.dbDriverManager = dbDriverManager;
        this.certificateAccess = new CertificateAccessDB();
        this.eventAccess = new PartnerEventAccessDB();
        this.oAuth2Access = new OAuth2AccessDB(dbDriverManager, SystemEventManagerImplAS2.instance());
    }

    /**
     * Requires a query to select partners from the DB. Works in a transaction
     * context on the passed database connection. Requires a LOCK on the
     * following tables: partner, certificates, httpheader, partnerevent, oauth2
     */
    private List<Partner> getPartnerByQuery(String query, String parameter,
            Connection configConnectionNoAutoCommit) throws Exception {
        List<Partner> partnerList = new ArrayList<Partner>();
        try (PreparedStatement preparedStatement = configConnectionNoAutoCommit.prepareStatement(query)) {
            if (parameter != null) {
                preparedStatement.setString(1, parameter);
            }
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    Partner partner = new Partner();
                    partner.setAS2Identification(result.getString("as2ident"));
                    partner.setName(result.getString("partnername"));
                    partner.setDBId(result.getInt("id"));
                    partner.setLocalStation(result.getInt("islocal") == 1);
                    partner.setSignType(result.getInt("sign"));
                    partner.setEncryptionType(result.getInt("encrypt"));
                    partner.setEmail(result.getString("email"));
                    partner.setURL(result.getString("url"));
                    partner.setMdnURL(result.getString("mdnurl"));
                    partner.setSubject(result.getString("msgsubject"));
                    partner.setContentType(result.getString("contenttype"));
                    partner.setSyncMDN(result.getInt("syncmdn") == 1);
                    partner.setPollIgnoreListString(result.getString("pollignorelist"));
                    partner.setPollInterval(result.getInt("pollinterval"));
                    partner.setCompressionType(MessageCompressionType.of(result.getInt("msgcompression")));
                    partner.setSignedMDN(result.getInt("signedmdn") == 1);
                    partner.setKeepOriginalFilenameOnReceipt(result.getInt("keeporiginalfilenameonreceipt") == 1);
                    HTTPAuthentication authentication = partner.getAuthenticationCredentialsMessage();
                    authentication.setUser(result.getString("httpauthuser"));
                    authentication.setPassword(result.getString("httpauthpass"));
                    authentication.setEnabled(result.getInt("usehttpauth") == 1);
                    HTTPAuthentication asyncAuthentication = partner.getAuthenticationCredentialsAsyncMDN();
                    asyncAuthentication.setUser(result.getString("httpauthuserasnymdn"));
                    asyncAuthentication.setPassword(result.getString("httpauthpassasnymdn"));
                    asyncAuthentication.setEnabled(result.getInt("usehttpauthasyncmdn") == 1);
                    partner.setComment(this.dbDriverManager.readTextStoredAsJavaObject(result, "partnercomment"));
                    partner.setContactAS2(this.dbDriverManager.readTextStoredAsJavaObject(result, "partnercontact"));
                    partner.setContactCompany(this.dbDriverManager.readTextStoredAsJavaObject(result, "partneraddress"));
                    partner.setNotifyReceive(result.getInt("notifyreceive"));
                    partner.setNotifySend(result.getInt("notifysend"));
                    partner.setNotifySendReceive(result.getInt("notifysendreceive"));
                    partner.setNotifyReceiveEnabled(result.getInt("notifyreceiveenabled") == 1);
                    partner.setNotifySendEnabled(result.getInt("notifysendenabled") == 1);
                    partner.setNotifySendReceiveEnabled(result.getInt("notifysendreceiveenabled") == 1);
                    partner.setContentTransferEncoding(
                            MessageContentTransferEncodingType.of( result.getInt("contenttransferencoding")));
                    partner.setHttpProtocolVersion(HttpConnectionParameter.HttpProtocolVersion.of(
                            result.getString("httpversion")));
                    partner.setMaxPollFiles(result.getInt("maxpollfiles"));
                    partner.setUseAlgorithmIdentifierProtectionAttribute(result.getInt("algidentprotatt") == 1);
                    partner.setEnableDirPoll(result.getInt("enabledirpoll") == 1);
                    partner.setOverwriteLocalStationSecurity(result.getInt("overwritelocalsecurity") == 1);                    
                    partner.setUseOAuth2Message(result.getInt("useoauth2message") == 1);
                    int oAuth2ReferenceMessage = result.getInt("oauth2idmessage");
                    if (!result.wasNull()) {
                        OAuth2Config oAuth2ConfigMessage = this.oAuth2Access.getOAuth2Config(oAuth2ReferenceMessage, configConnectionNoAutoCommit);
                        partner.setOAuth2Message(oAuth2ConfigMessage);
                    }
                    partner.setUseOAuth2MDN(result.getInt("useoauth2mdn") == 1);
                    int oAuth2ReferenceMDN = result.getInt("oauth2idmdn");
                    if (!result.wasNull()) {
                        OAuth2Config oAuth2ConfigMDN = this.oAuth2Access.getOAuth2Config(oAuth2ReferenceMDN, configConnectionNoAutoCommit);
                        partner.setOAuth2MDN(oAuth2ConfigMDN);
                    }
                    partnerList.add(partner);
                }
                Collections.sort(partnerList);
                this.eventAccess.loadPartnerEventsIntoPartner(partnerList, configConnectionNoAutoCommit);
                this.certificateAccess.loadPartnerCertificateInformationIntoPartner(partnerList, configConnectionNoAutoCommit);
                this.loadHTTPHeaderIntoPartner(partnerList, configConnectionNoAutoCommit);
                return (partnerList);
            }
        }
    }

    /**
     * Returns the number of partner in the system
     */
    public int getPartnerCount() {
        int counter = 0;
        try (Connection configConnectionAutoCommit
                = this.dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG)) {
            try (PreparedStatement statement = configConnectionAutoCommit.prepareStatement(
                    "SELECT COUNT(1) AS partnercount FROM partner")) {
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        counter = result.getInt("partnercount");
                    }
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        return (counter);
    }

    /**
     * Requires a query to select partners from the DB. Establishes a new
     * connection to the database and gets the data transactional
     *
     */
    private List<Partner> getPartnerByQuery(String query, String parameter) {
        List<Partner> partnerList = new ArrayList<Partner>();
        String transactionName = "Partner_read";
        try (Connection configConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG)) {
            configConnectionNoAutoCommit.setAutoCommit(false);
            configConnectionNoAutoCommit.setReadOnly(true);
            try (Statement transactionStatement = configConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{
                                "partner",
                                "certificates",
                                "httpheader",
                                "partnerevent",
                                "oauth2"
                            });
                    partnerList.addAll(this.getPartnerByQuery(query, parameter, configConnectionNoAutoCommit));
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        return (partnerList);
    }

    /**
     * Returns all partner stored in the DB, even the local station
     */
    public List<Partner> getAllPartner() {
        List<Partner> partnerList = new ArrayList<Partner>();
        String transactionName = "Partner_getAllPartner";
        try (Connection configConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG)) {
            configConnectionNoAutoCommit.setAutoCommit(false);
            configConnectionNoAutoCommit.setReadOnly(true);
            try (Statement transactionStatement = configConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{
                                "partner",
                                "certificates",
                                "httpheader",
                                "partnerevent",
                                "oauth2"
                            });
                    List<String> as2IdList = new ArrayList<String>();
                    try (Statement as2idStatement = configConnectionNoAutoCommit.createStatement()) {
                        try (ResultSet result = as2idStatement.executeQuery(
                                "SELECT as2ident FROM partner ORDER BY partnername")) {
                            while (result.next()) {
                                as2IdList.add(result.getString("as2ident"));
                            }
                        }
                    }
                    for (String as2Id : as2IdList) {
                        Partner foundPartner = PartnerCache.instance().get(PartnerCache.AS2ID, as2Id);
                        if (foundPartner == null) {
                            foundPartner = this.getPartnerByAS2Id(as2Id, configConnectionNoAutoCommit);
                            if (foundPartner != null) {
                                PartnerCache.instance().put(foundPartner);
                            }
                        }
                        if (foundPartner != null) {
                            partnerList.add(foundPartner);
                        }
                    }
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        return (partnerList);
    }

    /**
     * Returns all partner stored in the DB, even the local station
     */
    public List<Partner> getAllPartnerNoCache() {
        return (this.getPartnerByQuery(
                "SELECT * FROM partner ORDER BY partnername", null));
    }

    /**
     * Returns all partner stored in the DB with all information, even the local
     * station. The transactional context of the passed connection has to be
     * handled outside
     */
    public List<Partner> getAllPartner(Connection configConnectionNoAutoCommit) throws Exception {
        List<Partner> partnerList = new ArrayList<Partner>();
        List<String> as2IdList = new ArrayList<String>();
        try (Statement as2idStatement = configConnectionNoAutoCommit.createStatement()) {
            try (ResultSet result = as2idStatement.executeQuery(
                    "SELECT as2ident FROM partner ORDER BY partnername")) {
                while (result.next()) {
                    as2IdList.add(result.getString("as2ident"));
                }
            }
        }
        for (String as2Id : as2IdList) {
            Partner foundPartner = PartnerCache.instance().get(PartnerCache.AS2ID, as2Id);
            if (foundPartner == null) {
                foundPartner = this.getPartnerByAS2Id(as2Id, configConnectionNoAutoCommit);
                if (foundPartner != null) {
                    PartnerCache.instance().put(foundPartner);
                }
            }
            if (foundPartner != null) {
                partnerList.add(foundPartner);
            }
        }
        return (partnerList);
    }

    /**
     * Returns all partner stored in the DB with all information, even the local
     * station. The transactional context of the passed connection has to be
     * handled outside
     */
    public List<Partner> getAllPartnerNoCache(Connection configConnectionNoAutoCommit) throws Exception {
        return (this.getPartnerByQuery(
                "SELECT * FROM partner ORDER BY partnername", null, configConnectionNoAutoCommit));
    }

    /**
     * Returns all local stations stored in the DB
     */
    public List<Partner> getLocalStations() {
        List<Partner> partnerList = new ArrayList<Partner>();
        String transactionName = "Partner_getLocalStations";
        try (Connection configConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG)) {
            configConnectionNoAutoCommit.setAutoCommit(false);
            configConnectionNoAutoCommit.setReadOnly(true);
            try (Statement transactionStatement = configConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{
                                "partner",
                                "certificates",
                                "httpheader",
                                "partnerevent",
                                "oauth2"
                            });
                    List<String> as2IdList = new ArrayList<String>();
                    try (Statement as2idStatement = configConnectionNoAutoCommit.createStatement()) {
                        try (ResultSet result = as2idStatement.executeQuery(
                                "SELECT as2ident FROM partner WHERE islocal=1 ORDER BY partnername")) {
                            while (result.next()) {
                                as2IdList.add(result.getString("as2ident"));
                            }
                        }
                    }
                    for (String as2Id : as2IdList) {
                        Partner foundPartner = PartnerCache.instance().get(PartnerCache.AS2ID, as2Id);
                        if (foundPartner == null) {
                            foundPartner = this.getPartnerByAS2Id(as2Id, configConnectionNoAutoCommit);
                            if (foundPartner != null) {
                                PartnerCache.instance().put(foundPartner);
                            }
                        }
                        if (foundPartner != null) {
                            partnerList.add(foundPartner);
                        }
                    }
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        return (partnerList);
    }

    /**
     * Returns all partner stored in the DB, even the local station
     */
    public List<Partner> getNonLocalStations() {
        List<Partner> partnerList = new ArrayList<Partner>();
        String transactionName = "Partner_getNonLocalStations";
        try (Connection configConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG)) {
            configConnectionNoAutoCommit.setAutoCommit(false);
            configConnectionNoAutoCommit.setReadOnly(true);
            try (Statement transactionStatement = configConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockREAD(transactionStatement,
                            new String[]{
                                "partner",
                                "certificates",
                                "httpheader",
                                "partnerevent",
                                "oauth2"
                            });
                    List<String> as2IdList = new ArrayList<String>();
                    try (Statement as2idStatement = configConnectionNoAutoCommit.createStatement()) {
                        try (ResultSet result = as2idStatement.executeQuery(
                                "SELECT as2ident FROM partner WHERE islocal<>1 ORDER BY partnername")) {
                            while (result.next()) {
                                as2IdList.add(result.getString("as2ident"));
                            }
                        }
                    }
                    for (String as2Id : as2IdList) {
                        Partner foundPartner = PartnerCache.instance().get(PartnerCache.AS2ID, as2Id);
                        if (foundPartner == null) {
                            foundPartner = this.getPartnerByAS2Id(as2Id, configConnectionNoAutoCommit);
                            if (foundPartner != null) {
                                PartnerCache.instance().put(foundPartner);
                            }
                        }
                        if (foundPartner != null) {
                            partnerList.add(foundPartner);
                        }
                    }
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        return (partnerList);
    }

    /**
     * Updates a single partner to the database by creating a new DB connection
     */
    public void updatePartner(Partner partner) {
        String transactionName = "PartnerAccessDB_updatePartner";
        try (Connection configConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG)) {
            configConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = configConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockDELETE(
                            transactionStatement,
                            new String[]{
                                "partner",
                                "certificates",
                                "partnerevent",
                                "httpheader",
                                "partnersystem",
                                "oauth2"
                            });
                    this.updatePartner(partner, configConnectionNoAutoCommit);
                    PartnerCache.instance().put(partner);
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
    }

    /**
     * Updates a single partner in the db
     */
    /**
     * Inserts a new partner into the database DELETE lock on certificates
     * UPDATE lock on partner DELETE lock on partnerevent
     *
     */
    public void updatePartner(Partner partner, Connection configConnectionNoAutoCommit) throws Exception {
        try (PreparedStatement preparedStatement = configConnectionNoAutoCommit.prepareStatement(
                "UPDATE partner SET "
                + "as2ident=?,partnername=?,islocal=?,sign=?,encrypt=?,email=?,url=?,"
                + "mdnurl=?,msgsubject=?,contenttype=?,syncmdn=?,pollignorelist=?,"
                + "pollinterval=?,msgcompression=?,signedmdn=?,"
                + "usehttpauth=?,httpauthuser=?,httpauthpass=?,"
                + "usehttpauthasyncmdn=?,httpauthuserasnymdn=?,httpauthpassasnymdn=?,"
                + "keeporiginalfilenameonreceipt=?,partnercomment=?,notifysend=?,"
                + "notifyreceive=?,notifysendreceive=?,notifysendenabled=?,"
                + "notifyreceiveenabled=?,notifysendreceiveenabled=?,"
                + "contenttransferencoding=?,httpversion=?,"
                + "maxpollfiles=?,partnercontact=?,partneraddress=?,algidentprotatt=?,"
                + "enabledirpoll=?,useoauth2message=?,useoauth2mdn=?,"
                + "oauth2idmessage=?,oauth2idmdn=?,overwritelocalsecurity=? "
                + "WHERE id=?")) {
            preparedStatement.setString(1, partner.getAS2Identification());
            preparedStatement.setString(2, partner.getName());
            preparedStatement.setInt(3, partner.isLocalStation() ? 1 : 0);
            preparedStatement.setInt(4, partner.getSignType());
            preparedStatement.setInt(5, partner.getEncryptionType());
            preparedStatement.setString(6, partner.getEmail());
            preparedStatement.setString(7, partner.getURL());
            preparedStatement.setString(8, partner.getMdnURL());
            preparedStatement.setString(9, partner.getSubject());
            preparedStatement.setString(10, partner.getContentType());
            preparedStatement.setInt(11, partner.isSyncMDN() ? 1 : 0);
            preparedStatement.setString(12, partner.getPollIgnoreListAsString());
            preparedStatement.setInt(13, partner.getPollInterval());
            preparedStatement.setInt(14, partner.getCompressionType().toInt());
            preparedStatement.setInt(15, partner.isSignedMDN() ? 1 : 0);
            preparedStatement.setInt(16, partner.getAuthenticationCredentialsMessage().isEnabled() ? 1 : 0);
            preparedStatement.setString(17, partner.getAuthenticationCredentialsMessage().getUser());
            preparedStatement.setString(18, partner.getAuthenticationCredentialsMessage().getPassword());
            preparedStatement.setInt(19, partner.getAuthenticationCredentialsAsyncMDN().isEnabled() ? 1 : 0);
            preparedStatement.setString(20, partner.getAuthenticationCredentialsAsyncMDN().getUser());
            preparedStatement.setString(21, partner.getAuthenticationCredentialsAsyncMDN().getPassword());
            preparedStatement.setInt(22, partner.getKeepOriginalFilenameOnReceipt() ? 1 : 0);
            this.dbDriverManager.setTextParameterAsJavaObject(preparedStatement, 23, partner.getComment());
            preparedStatement.setInt(24, partner.getNotifySend());
            preparedStatement.setInt(25, partner.getNotifyReceive());
            preparedStatement.setInt(26, partner.getNotifySendReceive());
            preparedStatement.setInt(27, partner.isNotifySendEnabled() ? 1 : 0);
            preparedStatement.setInt(28, partner.isNotifyReceiveEnabled() ? 1 : 0);
            preparedStatement.setInt(29, partner.isNotifySendReceiveEnabled() ? 1 : 0);
            preparedStatement.setInt(30, partner.getContentTransferEncoding().toInt());
            preparedStatement.setString(31, partner.getHttpProtocolVersion().toString());
            preparedStatement.setInt(32, partner.getMaxPollFiles());
            this.dbDriverManager.setTextParameterAsJavaObject(preparedStatement, 33, partner.getContactAS2());
            this.dbDriverManager.setTextParameterAsJavaObject(preparedStatement, 34, partner.getContactCompany());
            preparedStatement.setInt(35, partner.getUseAlgorithmIdentifierProtectionAttribute() ? 1 : 0);
            preparedStatement.setInt(36, partner.isEnableDirPoll() ? 1 : 0);
            preparedStatement.setInt(37, partner.usesOAuth2Message() ? 1 : 0);
            preparedStatement.setInt(38, partner.usesOAuth2MDN() ? 1 : 0);
            if (partner.getOAuth2Message() != null) {
                this.oAuth2Access.insertOrUpdateOAuth2(partner.getOAuth2Message(), configConnectionNoAutoCommit);
                preparedStatement.setInt(39, partner.getOAuth2Message().getDBId());
            } else {
                preparedStatement.setNull(39, Types.INTEGER);
            }
            if (partner.getOAuth2MDN() != null) {
                this.oAuth2Access.insertOrUpdateOAuth2(partner.getOAuth2MDN(), configConnectionNoAutoCommit);
                preparedStatement.setInt(40, partner.getOAuth2MDN().getDBId());
            } else {
                preparedStatement.setNull(40, Types.INTEGER);
            }
            preparedStatement.setInt(41, partner.isOverwriteLocalStationSecurity() ? 1 : 0);
            //where statement
            preparedStatement.setInt(42, partner.getDBId());
            preparedStatement.executeUpdate();
            this.storeHTTPHeader(partner, configConnectionNoAutoCommit);
            this.certificateAccess.storePartnerCertificateInformationList(partner, configConnectionNoAutoCommit);
            this.eventAccess.storePartnerEvents(partner, configConnectionNoAutoCommit);
        }
    }

    /**
     * Deletes a single partner from the database by creating a new connection
     */
    public void deletePartner(Partner partner) {
        String transactionName = "PartnerAccess_delete";
        try (Connection configConnectionNoAutoCommit = this.dbDriverManager
                .getConnectionWithoutErrorHandling(IDBDriverManager.DB_CONFIG)) {
            configConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = configConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockDELETE(
                            transactionStatement,
                            new String[]{
                                "partner",
                                "certificates",
                                "partnerevent",
                                "httpheader",
                                "partnersystem"
                            });
                    this.deletePartner(partner, configConnectionNoAutoCommit);
                    PartnerCache.instance().remove(partner);
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                } catch (Exception e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
    }

    /**
     * Deletes a single partner from the database
     */
    public void deletePartner(Partner partner, Connection configConnectionNoAutoCommit) throws Exception {
        PartnerSystemAccessDB partnerSystemAccess = new PartnerSystemAccessDB(this.dbDriverManager);
        this.deleteHTTPHeader(partner, configConnectionNoAutoCommit);
        this.certificateAccess.deletePartnerCertificateInformationList(partner, configConnectionNoAutoCommit);
        this.eventAccess.deletePartnerEvents(partner, configConnectionNoAutoCommit);
        partnerSystemAccess.deletePartnerSystem(partner, configConnectionNoAutoCommit);
        try (PreparedStatement preparedStatement = configConnectionNoAutoCommit.prepareStatement(
                "DELETE FROM partner WHERE id=?")) {
            preparedStatement.setInt(1, partner.getDBId());
            preparedStatement.executeUpdate();
            //this worked fine - try to delete oauth2 references which might fail because they might be used somewhere else
            OAuth2Config oauth2Message = partner.getOAuth2Message();
            if (oauth2Message != null) {
                this.oAuth2Access.deleteOAuth2(oauth2Message.getDBId());
            }
            OAuth2Config oauth2MDN = partner.getOAuth2MDN();
            if (oauth2MDN != null) {
                this.oAuth2Access.deleteOAuth2(oauth2MDN.getDBId());
            }
        }
    }

    /**
     * Inserts a single partner to the database by creating a new DB connection
     */
    public void insertPartner(Partner partner) {
        try (Connection configConnectionNoAutoCommit = this.dbDriverManager.getConnectionWithoutErrorHandling(
                IDBDriverManager.DB_CONFIG)) {
            configConnectionNoAutoCommit.setAutoCommit(false);
            String transactionName = "PartnerAccessDB_insertPartner";
            try (Statement transactionStatement = configConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                try {
                    this.dbDriverManager.setTableLockDELETE(transactionStatement,
                            new String[]{
                                "partner",
                                "certificates",
                                "partnerevent",
                                "httpheader",
                                "oauth2"
                            });
                    this.insertPartner(partner, configConnectionNoAutoCommit);
                    PartnerCache.instance().put(partner);
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                } catch (Throwable e) {
                    SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            SystemEventManagerImplAS2.instance().systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
    }

    /**
     * Inserts a new partner into the database. This has to happen transactional
     * as data is stored in multiple tables and other processes may read
     * incomplete data else
     */
    public void insertPartner(Partner partner, Connection configConnectionNoAutoCommit) throws Exception {
        try (PreparedStatement preparedStatement = configConnectionNoAutoCommit.prepareStatement(
                "INSERT INTO partner("
                + "as2ident,partnername,islocal,sign,encrypt,email,url,mdnurl,"
                + "msgsubject,contenttype,syncmdn,pollignorelist,pollinterval,"
                + "msgcompression,signedmdn,"
                + "usehttpauth,httpauthuser,httpauthpass,usehttpauthasyncmdn,"
                + "httpauthuserasnymdn,httpauthpassasnymdn,keeporiginalfilenameonreceipt,"
                + "partnercomment,notifysend,notifyreceive,notifysendreceive,"
                + "notifysendenabled,notifyreceiveenabled,notifysendreceiveenabled,"
                + "contenttransferencoding,httpversion,"
                + "maxpollfiles,partnercontact,partneraddress,algidentprotatt,enabledirpoll,"
                + "useoauth2message,useoauth2mdn,oauth2idmessage,oauth2idmdn,overwritelocalsecurity"
                + ")VALUES("
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            preparedStatement.setString(1, partner.getAS2Identification());
            preparedStatement.setString(2, partner.getName());
            preparedStatement.setInt(3, partner.isLocalStation() ? 1 : 0);
            preparedStatement.setInt(4, partner.getSignType());
            preparedStatement.setInt(5, partner.getEncryptionType());
            preparedStatement.setString(6, partner.getEmail());
            preparedStatement.setString(7, partner.getURL());
            preparedStatement.setString(8, partner.getMdnURL());
            preparedStatement.setString(9, partner.getSubject());
            preparedStatement.setString(10, partner.getContentType());
            preparedStatement.setInt(11, partner.isSyncMDN() ? 1 : 0);
            preparedStatement.setString(12, partner.getPollIgnoreListAsString());
            preparedStatement.setInt(13, partner.getPollInterval());
            preparedStatement.setInt(14, partner.getCompressionType().toInt());
            preparedStatement.setInt(15, partner.isSignedMDN() ? 1 : 0);
            preparedStatement.setInt(16, partner.getAuthenticationCredentialsMessage().isEnabled() ? 1 : 0);
            preparedStatement.setString(17, partner.getAuthenticationCredentialsMessage().getUser());
            preparedStatement.setString(18, partner.getAuthenticationCredentialsMessage().getPassword());
            preparedStatement.setInt(19, partner.getAuthenticationCredentialsAsyncMDN().isEnabled() ? 1 : 0);
            preparedStatement.setString(20, partner.getAuthenticationCredentialsAsyncMDN().getUser());
            preparedStatement.setString(21, partner.getAuthenticationCredentialsAsyncMDN().getPassword());
            preparedStatement.setInt(22, partner.getKeepOriginalFilenameOnReceipt() ? 1 : 0);
            this.dbDriverManager.setTextParameterAsJavaObject(preparedStatement, 23, partner.getComment());
            preparedStatement.setInt(24, partner.getNotifySend());
            preparedStatement.setInt(25, partner.getNotifyReceive());
            preparedStatement.setInt(26, partner.getNotifySendReceive());
            preparedStatement.setInt(27, partner.isNotifySendEnabled() ? 1 : 0);
            preparedStatement.setInt(28, partner.isNotifyReceiveEnabled() ? 1 : 0);
            preparedStatement.setInt(29, partner.isNotifySendReceiveEnabled() ? 1 : 0);
            preparedStatement.setInt(30, partner.getContentTransferEncoding().toInt());
            preparedStatement.setString(31, partner.getHttpProtocolVersion().toString());
            preparedStatement.setInt(32, partner.getMaxPollFiles());
            this.dbDriverManager.setTextParameterAsJavaObject(preparedStatement, 33, partner.getContactAS2());
            this.dbDriverManager.setTextParameterAsJavaObject(preparedStatement, 34, partner.getContactCompany());
            preparedStatement.setInt(35, partner.getUseAlgorithmIdentifierProtectionAttribute() ? 1 : 0);
            preparedStatement.setInt(36, partner.isEnableDirPoll() ? 1 : 0);
            preparedStatement.setInt(37, partner.usesOAuth2Message() ? 1 : 0);
            preparedStatement.setInt(38, partner.usesOAuth2MDN() ? 1 : 0);
            if (partner.getOAuth2Message() != null) {
                this.oAuth2Access.insertOrUpdateOAuth2(partner.getOAuth2Message(), configConnectionNoAutoCommit);
                preparedStatement.setInt(39, partner.getOAuth2Message().getDBId());
            } else {
                preparedStatement.setNull(39, Types.INTEGER);
            }
            if (partner.getOAuth2MDN() != null) {
                this.oAuth2Access.insertOrUpdateOAuth2(partner.getOAuth2MDN(), configConnectionNoAutoCommit);
                preparedStatement.setInt(40, partner.getOAuth2MDN().getDBId());
            } else {
                preparedStatement.setNull(40, Types.INTEGER);
            }
            preparedStatement.setInt(41, partner.isOverwriteLocalStationSecurity() ? 1 : 0);
            preparedStatement.executeUpdate();
        }
        partner.setDBId(this.getDBIdForPartner(partner.getAS2Identification(), configConnectionNoAutoCommit));
        this.storeHTTPHeader(partner, configConnectionNoAutoCommit);
        this.certificateAccess.storePartnerCertificateInformationList(partner, configConnectionNoAutoCommit);
        this.eventAccess.storePartnerEvents(partner, configConnectionNoAutoCommit);
    }

    /**
     * returns the internal database id for the passed partner as2
     * identification
     *
     * @param as2ident
     * @param configConnection
     * @return
     */
    private int getDBIdForPartner(String as2ident, Connection configConnection) throws Exception {
        String query = this.dbDriverManager.addLimitToQuery("SELECT id FROM partner WHERE as2ident=?", 1);
        try (PreparedStatement statement = configConnection.prepareStatement(query)) {
            statement.setString(1, as2ident);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return (result.getInt("id"));
                } else {
                    return (-1);
                }
            }
        }
    }

    /**
     * Loads a specified partner from the DB
     *
     * @return null if the partner does not exist
     */
    private Partner getPartnerByAS2Id(String as2ident, Connection configConnection) throws Exception {
        if (as2ident == null) {
            return (null);
        }
        String query = this.dbDriverManager.addLimitToQuery(
                "SELECT * FROM partner WHERE as2ident=?", 1);
        List<Partner> partnerList = this.getPartnerByQuery(query, as2ident, configConnection);
        if (partnerList == null || partnerList.isEmpty()) {
            return (null);
        }
        return (partnerList.get(0));
    }

    /**
     * Loads a specified partner from the DB
     *
     * @return null if the partner does not exist
     */
    public Partner getPartnerByAS2Id(String as2ident) {
        if (as2ident == null) {
            return (null);
        }
        Partner foundPartner = PartnerCache.instance().get(PartnerCache.AS2ID, as2ident);
        if (foundPartner != null) {
            return (foundPartner);
        }
        String query = this.dbDriverManager.addLimitToQuery(
                "SELECT * FROM partner WHERE as2ident=?", 1);
        List<Partner> partner = this.getPartnerByQuery(query, as2ident);
        if (partner == null || partner.isEmpty()) {
            return (null);
        }
        Partner loadedPartner = partner.get(0);
        PartnerCache.instance().put(loadedPartner);
        return (loadedPartner);
    }

    /**
     * Loads a specified partner from the DB
     *
     * @return null if the partner does not exist
     */
    public Partner getPartnerByName(String partnerName) {
        Partner foundPartner = PartnerCache.instance().get(PartnerCache.NAME, partnerName);
        if (foundPartner != null) {
            return (foundPartner);
        }
        String query = this.dbDriverManager.addLimitToQuery(
                "SELECT * FROM partner WHERE upper(partnername)=?", 1);
        List<Partner> partner = this.getPartnerByQuery(query, partnerName.toUpperCase());
        if (partner == null || partner.isEmpty()) {
            return (null);
        }
        Partner loadedPartner = partner.get(0);
        PartnerCache.instance().put(loadedPartner);
        return (loadedPartner);
    }

    /*
     * loads the partner specific http headers from the db and assigns it to the
     * passed partner
     */
    public void loadHTTPHeaderIntoPartner(List<Partner> partnerList, Connection configConnection) throws Exception {
        if (partnerList == null || partnerList.isEmpty()) {
            return;
        }
        int batchSize = 250;
        for (int i = 0; i < partnerList.size(); i += batchSize) {
            int toIndex = Math.min(i + batchSize, partnerList.size());
            List<Partner> subList = partnerList.subList(i, toIndex);
            this.loadHTTPHeaderIntoPartnerSublist(subList, configConnection);
        }
    }

    private void loadHTTPHeaderIntoPartnerSublist(List<Partner> partnerList, Connection configConnection) throws Exception {
        StringBuilder idList = new StringBuilder();
        for (int i = 0; i < partnerList.size(); i++) {
            if (i > 0) {
                idList.append(",");
            }
            idList.append(partnerList.get(i).getDBId());
        }
        //key: partner id
        Map<Integer, List<PartnerHttpHeader>> headerMap = new HashMap<Integer, List<PartnerHttpHeader>>();
        String query = "SELECT partnerid, headerkey, headervalue FROM httpheader "
                + "WHERE partnerid IN (" + idList.toString() + ")";
        try (PreparedStatement statement = configConnection.prepareStatement(query)) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int partnerId = result.getInt("partnerid");
                    PartnerHttpHeader header = new PartnerHttpHeader();
                    header.setKey(result.getString("headerkey"));
                    header.setValue(result.getString("headervalue"));
                    List<PartnerHttpHeader> partnerHeaders = headerMap.get(partnerId);
                    if (partnerHeaders == null) {
                        partnerHeaders = new ArrayList<PartnerHttpHeader>();
                        headerMap.put(partnerId, partnerHeaders);
                    }
                    partnerHeaders.add(header);
                }
            }
        }
        for (int i = 0; i < partnerList.size(); i++) {
            Partner partner = partnerList.get(i);
            List<PartnerHttpHeader> headers = headerMap.get(partner.getDBId());
            if (headers != null) {
                for (PartnerHttpHeader header : headers) {
                    partner.addHttpHeader(header);
                }
            }
        }
    }


    /**
     * Deletes a single partners http header from the database. Requires DELETE
     * lock on httpheader
     *
     */
    private void deleteHTTPHeader(Partner partner, Connection configConnection) throws Exception {
        try (PreparedStatement statement
                = configConnection.prepareStatement(
                        "DELETE FROM httpheader WHERE partnerid=?")) {
            statement.setInt(1, partner.getDBId());
            statement.executeUpdate();
        }
    }

    /**
     * Updates a single partners http header in the db. Requires DELETE lock on
     * httpheader
     */
    private void storeHTTPHeader(Partner partner, Connection configConnectionNoAutoCommit) throws Exception {
        this.deleteHTTPHeader(partner, configConnectionNoAutoCommit);
        //clear unused headers in the partner object
        partner.deleteEmptyHttpHeader();
        List<PartnerHttpHeader> headerList = partner.getHttpHeader();
        if (!headerList.isEmpty()) {
            try (PreparedStatement statement = configConnectionNoAutoCommit.prepareStatement(
                    "INSERT INTO httpheader(partnerid,headerkey,headervalue)VALUES(?,?,?)")) {
                for (PartnerHttpHeader header : headerList) {
                    statement.setInt(1, partner.getDBId());
                    statement.setString(2, header.getKey());
                    statement.setString(3, header.getValue());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        }
    }

}
