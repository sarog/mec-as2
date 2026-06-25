//$Header: /as4/de/mendelson/util/security/crmf/CertRequestMessageUtil.java 5     8/01/26 15:44 Heller $
package de.mendelson.util.security.crmf;

import de.mendelson.util.security.BouncyCastleProviderSingleton;
import de.mendelson.util.security.csr.CSRUtil;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cmp.PBMParameter;
import org.bouncycastle.asn1.cmp.PKIBody;
import org.bouncycastle.asn1.cmp.PKIMessage;
import org.bouncycastle.asn1.crmf.CertReqMessages;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.cert.cmp.ProtectedPKIMessage;
import org.bouncycastle.cert.cmp.ProtectedPKIMessageBuilder;
import org.bouncycastle.cert.crmf.CertificateRequestMessage;
import org.bouncycastle.cert.crmf.PKMACBuilder;
import org.bouncycastle.cert.crmf.jcajce.JcaCertificateRequestMessageBuilder;
import org.bouncycastle.cert.crmf.jcajce.JcePKMACValuesCalculator;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.MacCalculator;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Structure to create a TR-03109-4 request, this is a CRMF, certificate request
 * message format
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class CertRequestMessageUtil {

    public CertRequestMessageUtil() {
    }

    /**
     *
     * @param keyEncryption
     * @param certEncryption
     * @param keySignature
     * @param certSignature
     * @param keyTLS
     * @param certTLS
     * @param dnOfSubCA The recipient field in the CMP header represents the CAs
     * identity. When requesting certificates from a production CA (e.g.,
     * Federal Network Agency or an authorized Sub-CA), you must provide their
     * specific DN of the X500Name. This name can be found in the "Subject"
     * field of the CAs own certificate. A sample is "SM-Test-PKI-DE"
     * @param initialRequest
     * @return
     * @throws Exception
     */
    public static String buildRequest(
            PrivateKey keyEncryption, X509Certificate certEncryption,
            PrivateKey keySignature, X509Certificate certSignature,
            PrivateKey keyTLS, X509Certificate certTLS,
            X509Certificate caRootCertificate, boolean initialRequest, char[] initialPassword) throws Exception {
        ASN1EncodableVector asn1Vector = new ASN1EncodableVector();
        // Request 0: Encryption
        asn1Vector.add(buildRequest(0, certEncryption, keyEncryption,
                new KeyUsage(KeyUsage.keyAgreement), null).toASN1Structure());
        // Request 1: Signature
        asn1Vector.add(buildRequest(1, certSignature, keySignature,
                new KeyUsage(KeyUsage.digitalSignature | KeyUsage.nonRepudiation), null).toASN1Structure());
        // Request 2: TLS
        KeyPurposeId[] keyPurposeIds = {KeyPurposeId.id_kp_clientAuth, KeyPurposeId.id_kp_serverAuth};
        asn1Vector.add(buildRequest(2, certTLS, keyTLS,
                new KeyUsage(KeyUsage.digitalSignature), keyPurposeIds).toASN1Structure());
        // bundle all requests
        CertReqMessages certReqMessages = CertReqMessages.getInstance(new DERSequence(asn1Vector));
        // CMP header informationen
        X500Name subjectTLS = X500Name.getInstance(certTLS.getSubjectX500Principal().getEncoded());
        JcaX509CertificateHolder caHolder = new JcaX509CertificateHolder(caRootCertificate);
        X500Name recipientName = caHolder.getSubject();        
        // Random values for security (BDEW Standard)
        byte[] transactionId = new byte[16];
        byte[] senderNonce = new byte[16];
        java.security.SecureRandom random = new java.security.SecureRandom();
        random.nextBytes(transactionId);
        random.nextBytes(senderNonce);
        ProtectedPKIMessageBuilder pkiMsgBuilder = new ProtectedPKIMessageBuilder(
                new GeneralName(subjectTLS),
                new GeneralName(recipientName)
        );
        pkiMsgBuilder.setTransactionID(transactionId);
        pkiMsgBuilder.setSenderNonce(senderNonce);
        pkiMsgBuilder.setMessageTime(new java.util.Date());
        //Its either an initialization or an update request
        int bodyType = initialRequest ? PKIBody.TYPE_INIT_REQ : PKIBody.TYPE_KEY_UPDATE_REQ;
        PKIBody pkiBody = new PKIBody(bodyType, certReqMessages);
        pkiMsgBuilder.setBody(pkiBody);
        ProtectedPKIMessage protectedMsg;
        if (initialRequest) {
            JcePKMACValuesCalculator jceCalc
                    = new JcePKMACValuesCalculator();
            jceCalc.setProvider(BouncyCastleProviderSingleton.instance());
            PKMACBuilder pkMacBuilder = new PKMACBuilder(jceCalc);
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            int iterationCount = 1000;
            PBMParameter pbmParam = new PBMParameter(
                    salt,
                    new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256),
                    iterationCount,
                    new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256)
            );
            pkMacBuilder.setParameters(pbmParam);
            MacCalculator macCalculator = pkMacBuilder.build(initialPassword);
            //finally protect the message by password
            protectedMsg = pkiMsgBuilder.build(macCalculator);
        } else {
            //update path: use signature if there are already certificates
            pkiMsgBuilder.addCMPCertificate(new JcaX509CertificateHolder(certSignature));
            ContentSigner pkiSigner = new JcaContentSignerBuilder("SHA256withECDSA")
                    .setProvider(BouncyCastleProviderSingleton.instance())
                    .build(keySignature);
            protectedMsg = pkiMsgBuilder.build(pkiSigner);
        }
        //encode the object
        PKIMessage pkiMessage = protectedMsg.toASN1Structure();
        return Base64.getEncoder().encodeToString(pkiMessage.getEncoded());
    }

    private static CertificateRequestMessage buildRequest(
            int id, X509Certificate certificate, PrivateKey privateKey,
            KeyUsage keyUsage, KeyPurposeId[] keyPurposeIds) throws Exception {
        X500Name subject = X500Name.getInstance(certificate.getSubjectX500Principal().getEncoded());
        List<GeneralName> sanList = CSRUtil.getSubjectAlternativeNames(certificate);
        JcaCertificateRequestMessageBuilder builder
                = new JcaCertificateRequestMessageBuilder(BigInteger.valueOf(id));
        builder.setSubject(subject);
        builder.setPublicKey(certificate.getPublicKey());
        ExtensionsGenerator extGen = new ExtensionsGenerator();
        if (keyUsage != null) {
            extGen.addExtension(Extension.keyUsage, true, keyUsage);
        }
        if (sanList != null && !sanList.isEmpty()) {
            GeneralNames subjectAltNames = new GeneralNames(sanList.toArray(new GeneralName[0]));
            extGen.addExtension(Extension.subjectAlternativeName, false, subjectAltNames);
        }
        if (keyPurposeIds != null) {
            extGen.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(keyPurposeIds));
        }
        //add ski
        JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
        SubjectKeyIdentifier ski
                = extUtils.createSubjectKeyIdentifier(certificate.getPublicKey());
        extGen.addExtension(Extension.subjectKeyIdentifier, false, ski);
        //Add extensions to the builder
        Extensions extensions = extGen.generate();
        for (ASN1ObjectIdentifier oid : extensions.getExtensionOIDs()) {
            Extension ext = extensions.getExtension(oid);
            builder.addExtension(ext.getExtnId(), ext.isCritical(), ext.getParsedValue());
        }
        String pubKeyAlg = certificate.getPublicKey().getAlgorithm();
        String sigAlg = pubKeyAlg.equals("EC") ? "SHA256withECDSA" : "SHA256withRSA";
        ContentSigner signer = new JcaContentSignerBuilder(sigAlg)
                .setProvider(BouncyCastleProviderSingleton.instance())
                .build(privateKey);
        builder.setProofOfPossessionSigningKeySigner(signer);
        return builder.build();
    }

}
