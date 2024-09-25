//$Header: /as2/de/mendelson/comm/as2/cem/CEMInitiator.java 27    14.05.13 16:38 Heller $
package de.mendelson.comm.as2.cem;

import de.mendelson.comm.as2.cem.messages.EDIINTCertificateExchangeRequest;
import de.mendelson.comm.as2.cem.messages.EndEntity;
import de.mendelson.comm.as2.cem.messages.TradingPartnerInfo;
import de.mendelson.comm.as2.cem.messages.TrustRequest;
import de.mendelson.comm.as2.cert.CertificateAccessDB;
import de.mendelson.comm.as2.message.AS2Message;
import de.mendelson.comm.as2.message.AS2MessageCreation;
import de.mendelson.comm.as2.message.AS2MessageInfo;
import de.mendelson.comm.as2.message.AS2Payload;
import de.mendelson.comm.as2.message.UniqueId;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerAccessDB;
import de.mendelson.comm.as2.partner.PartnerSystem;
import de.mendelson.comm.as2.partner.PartnerSystemAccessDB;
import de.mendelson.comm.as2.sendorder.SendOrder;
import de.mendelson.comm.as2.sendorder.SendOrderSender;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.AS2Tools;
import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreCertificate;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Initiates a CEM request
 *
 * @author S.Heller
 * @version $Revision: 27 $
 */
public class CEMInitiator {

    /**
     * Logger to log inforamtion to
     */
    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    /**
     * Stores the certificates
     */
    private CertificateManager certificateManagerEncSign;
    /**
     * Partner access
     */
    private CertificateAccessDB certificateAccess;
    private Connection configConnection;
    private Connection runtimeConnection;

    /**
     * Creates new message I/O log and connects to localhost
     *
     * @param host host to connect to
     */
    public CEMInitiator(Connection configConnection,
            Connection runtimeConnection, CertificateManager certificateManagerEncSign) {
        this.configConnection = configConnection;
        this.runtimeConnection = runtimeConnection;
        this.certificateManagerEncSign = certificateManagerEncSign;
        this.certificateAccess = new CertificateAccessDB(this.configConnection, this.runtimeConnection);
    }

    /**
     * Returns a list of partners that have bee informed by the requests
     */
    public List<Partner> sendRequests(Partner initiator, KeystoreCertificate certificate,
            boolean encryptionUsage, boolean signatureUsage, boolean sslUsage, Date respondByDate)
            throws Exception {
        //get all partner that are CEM enabled
        List<Partner> cemPartnerList = new ArrayList<Partner>();
        PartnerAccessDB partnerAccess = new PartnerAccessDB(this.configConnection, this.runtimeConnection);
        PartnerSystemAccessDB systemAccess = new PartnerSystemAccessDB(this.configConnection, this.runtimeConnection);
        List<Partner> remotePartnerList = partnerAccess.getNonLocalStations();
        for (Partner partner : remotePartnerList) {
            PartnerSystem partnerSystem = systemAccess.getPartnerSystem(partner);
            if (partnerSystem != null && partnerSystem.supportsCEM()) {
                cemPartnerList.add(partner);
            }
        }
        List<SendOrder> orderList = new ArrayList<SendOrder>();
        for (Partner receiver : cemPartnerList) {
            SendOrder sendOrder = this.createRequest(initiator, receiver, certificate, encryptionUsage, signatureUsage, sslUsage, respondByDate);
            orderList.add(sendOrder);
        }
        for (SendOrder order : orderList) {
            SendOrderSender orderSender = new SendOrderSender(this.configConnection, this.runtimeConnection);
            orderSender.send(order);
            //set the certificates as fallback to the partner
            if (encryptionUsage) {
                this.setCertificateToPartner(initiator, certificate, CEMEntry.CATEGORY_CRYPT, 2);
            }
            if (signatureUsage) {
                this.setCertificateToPartner(initiator, certificate, CEMEntry.CATEGORY_SIGN, 2);
            }
        }
        return (cemPartnerList);
    }

    /**
     * Sends the request to the partner
     */
    private SendOrder createRequest(Partner initiator, Partner receiver, KeystoreCertificate certificate,
            boolean encryptionUsage, boolean signatureUsage, boolean sslUsage, Date respondByDate)
            throws Exception {
        EDIINTCertificateExchangeRequest request = new EDIINTCertificateExchangeRequest();
        String requestId = UniqueId.createId();
        String requestContentId = UniqueId.createId();
        String certContentId = UniqueId.createId();
        request.setRequestId(requestId);
        TradingPartnerInfo partnerInfo = new TradingPartnerInfo();
        partnerInfo.setSenderAS2Id(initiator.getAS2Identification());
        request.setTradingPartnerInfo(partnerInfo);
        EndEntity endEntity = new EndEntity();
        endEntity.setContentId(certContentId);
        endEntity.setIssuerName(certificate.getIssuerDN());
        endEntity.setSerialNumber(certificate.getSerialNumberDEC());
        TrustRequest trustRequest = new TrustRequest();
        trustRequest.setResponseURL(initiator.getMdnURL());
        trustRequest.setRespondByDate(respondByDate);
        trustRequest.setCertUsageEncryption(encryptionUsage);
        trustRequest.setCertUsageSSL(sslUsage);
        trustRequest.setCertUsageSignature(signatureUsage);
        trustRequest.setEndEntity(endEntity);
        request.addTrustRequest(trustRequest);
        //export the certificate to a file and create a payload
        File certFile = this.exportCertificate(certificate, certContentId);
        AS2Payload[] payloads = new AS2Payload[2];
        File descriptionFile = this.storeRequest(request);
        //build up the XML description as payload
        AS2Payload payloadXML = new AS2Payload();
        payloadXML.setPayloadFilename(descriptionFile.getAbsolutePath());
        payloadXML.loadDataFromPayloadFile();
        payloadXML.setContentId(requestContentId);
        payloadXML.setContentType("application/ediint-cert-exchange+xml");
        payloads[0] = payloadXML;
        //build up the certificate as payload
        AS2Payload payloadCert = new AS2Payload();
        payloadCert.setPayloadFilename(certFile.getAbsolutePath());
        payloadCert.loadDataFromPayloadFile();
        payloadCert.setContentId(certContentId);
        payloadCert.setContentType("application/pkcs7-mime; smime-type=certs-only");
        payloads[1] = payloadCert;
        //send the message
        AS2MessageCreation creation = new AS2MessageCreation(this.certificateManagerEncSign, this.certificateManagerEncSign);
        AS2Message message = creation.createMessage(initiator, receiver, payloads, AS2Message.MESSAGETYPE_CEM);
        SendOrder order = new SendOrder();
        order.setReceiver(receiver);
        order.setMessage(message);
        order.setSender(initiator);
        //enter the request to the CEM table in the db
        CEMAccessDB cemAccess = new CEMAccessDB(this.configConnection, this.runtimeConnection);
        cemAccess.insertRequest((AS2MessageInfo) order.getMessage().getAS2Info(), initiator, order.getReceiver(), request);
        return (order);
    }

    /**
     * Sets a certificate to a partner
     */
    private void setCertificateToPartner(Partner partner, KeystoreCertificate certificate, int category, int prio) {
        partner.getPartnerCertificateInformationList().insertNewCertificate(certificate.getFingerPrintSHA1(), category, prio);
        this.certificateAccess.storePartnerCertificateInformationList(partner);
        //display the changes in the certificates for the user in the log
        this.logger.fine(partner.getPartnerCertificateInformationList().getCertificatePurposeDescription(this.certificateManagerEncSign, partner, category));
    }

    private File exportCertificate(KeystoreCertificate certificate, String certContentId)
            throws Exception {
        KeyStoreUtil util = new KeyStoreUtil();
        String tempDir = System.getProperty("java.io.tmpdir");
        File[] exportFile = util.exportX509CertificatePKCS7(this.certificateManagerEncSign.getKeystore(),
                certificate.getAlias(), tempDir + certContentId + ".p7c");
        return (exportFile[0]);
    }

    private File storeRequest(EDIINTCertificateExchangeRequest request) throws Exception {
        File descriptionFile = AS2Tools.createTempFile("request", ".xml");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(descriptionFile), "UTF-8");
        writer.write(request.toXML());
        writer.flush();
        writer.close();
        return (descriptionFile);
    }
}
