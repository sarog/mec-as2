//$Header: /as2/de/mendelson/util/security/cert/KeystoreCertificate.java 97    31/03/26 17:12 Heller $
package de.mendelson.util.security.cert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.mendelson.util.security.Base64;
import de.mendelson.util.security.BouncyCastleProviderSingleton;
import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.security.keygeneration.KeyGenerator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.AuthorityInformationAccess;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.CertificatePolicies;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.bouncycastle.asn1.x509.PolicyQualifierId;
import org.bouncycastle.asn1.x509.PolicyQualifierInfo;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.math.ec.rfc8032.Ed25519;
import org.bouncycastle.pqc.jcajce.provider.dilithium.BCDilithiumPublicKey;
import org.bouncycastle.pqc.jcajce.provider.sphincsplus.BCSPHINCSPlusPublicKey;
import org.bouncycastle.pqc.jcajce.spec.DilithiumParameterSpec;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Object that stores a single configuration certificate/key
 *
 * @author S.Heller
 * @version $Revision: 97 $
 */
public class KeystoreCertificate implements Comparable<KeystoreCertificate>, Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    
    private String alias = "";
    private X509Certificate certificate = null;
    private Key privateKey = null;
    private boolean isKeyPair = false;
    private Certificate[] certificateChain = null;
    /**Required for the external check thread to check when it is required to ask for the 
     * next CRL
     */
    private long crlStateValidUntil = 0L;
    private int lastCRLState = CertificateValiditySettings.STATE_OK;

    //lazy cache fields for faster access
    private final AtomicReference<Date> notBeforeRef = new AtomicReference<Date>();
    private final AtomicReference<Date> notAfterRef = new AtomicReference<Date>();
    private final AtomicReference<X500Principal> issuerPrincipalRef = new AtomicReference<X500Principal>();
    private final AtomicReference<X500Principal> subjectPrincipalRef = new AtomicReference<X500Principal>();
    private final AtomicReference<byte[]> fingerprintSHA1BytesRef = new AtomicReference<byte[]>();
    private final AtomicReference<String> fingerprintMD5Ref = new AtomicReference<String>();
    private final AtomicReference<String> fingerprintSHA1Ref = new AtomicReference<String>();
    private final AtomicReference<String> fingerprintSHA256Ref = new AtomicReference<String>();
    private final AtomicReference<String> issuerDNRef = new AtomicReference<String>();
    private final AtomicReference<String> subjectDNRef = new AtomicReference<String>();
    private final AtomicReference<BigInteger> serialNumberRef = new AtomicReference<BigInteger>();
    private final AtomicReference<PublicKey> publicKeyRef = new AtomicReference<PublicKey>();
    private final AtomicReference<byte[]> publicKeyEncodedRef = new AtomicReference<byte[]>();
    private final AtomicReference<byte[]> certificateEncodedRef = new AtomicReference<byte[]>();
    private final AtomicReference<String> infoTextRef = new AtomicReference<String>();

    private static final String[] KEY_USAGE_NAMES = {
        "Digital signature",
        "Non repudiation",
        "Key encipherment",
        "Data encipherment",
        "Key agreement",
        "Key certificate signing",
        "CRL signing",
        "Encipher",
        "Decipher"
    };

    private static final Map<String, String> EXTENSION_OID_MAP = new HashMap<String, String>();

    static {
        EXTENSION_OID_MAP.put("1.3.6.1.5.5.7.3.1", "Webserver authentication");
        EXTENSION_OID_MAP.put("1.3.6.1.5.5.7.3.2", "Client authentication");
        EXTENSION_OID_MAP.put("1.3.6.1.5.5.7.3.3", "Code signing");
        EXTENSION_OID_MAP.put("1.3.6.1.5.5.7.3.4", "Email protection");
        EXTENSION_OID_MAP.put("1.3.6.1.5.5.7.3.5", "IPSec end system");
        EXTENSION_OID_MAP.put("1.3.6.1.5.5.7.3.6", "IPSec tunnel");
        EXTENSION_OID_MAP.put("1.3.6.1.5.5.7.3.7", "IPSec user");
        EXTENSION_OID_MAP.put("1.3.6.1.5.5.7.3.8", "Timestamping");
        EXTENSION_OID_MAP.put("1.3.6.1.5.5.7.3.9", "OCSP Signing");
        EXTENSION_OID_MAP.put("1.3.6.1.5.5.7.3.10", "Microsoft Smart Card Logon");
        EXTENSION_OID_MAP.put("1.3.6.1.5.5.7.3.11", "Key Recovery");
        EXTENSION_OID_MAP.put("2.16.840.1.113733.1.8.1", "Verisign Server Gated Crypto");
        //Netscape extended key usages
        EXTENSION_OID_MAP.put("2.16.840.1.113730.4.1", "Netscape Server Gated Crypto");
        EXTENSION_OID_MAP.put("2.16.840.1.113730.1.2", "Netscape base URL");
        EXTENSION_OID_MAP.put("2.16.840.1.113730.1.8", "Netscape CA policy URL");
        EXTENSION_OID_MAP.put("2.16.840.1.113730.1.4", "Netscape CA revocation URL");
        EXTENSION_OID_MAP.put("2.16.840.1.113730.1.7", "Netscape cert renewal URL");
        EXTENSION_OID_MAP.put("2.16.840.1.113730.2.5", "Netscape cert sequence");
        EXTENSION_OID_MAP.put("2.16.840.1.113730.1.1", "Netscape cert type");
        EXTENSION_OID_MAP.put("2.16.840.1.113730.1.13", "Netscape comment");
        EXTENSION_OID_MAP.put("2.16.840.1.113730.1.3", "Netscape revocation URL");
        EXTENSION_OID_MAP.put("2.16.840.1.113730.1.12", "Netscape SSL server name");
        //MS extended key usages
        EXTENSION_OID_MAP.put("1.3.6.1.4.1.311.10.3.3", "Microsoft Server Gated Crypto");
        EXTENSION_OID_MAP.put("1.3.6.1.4.1.311.20.2.2", "Smart card logon");
        EXTENSION_OID_MAP.put("1.3.6.1.4.1.311.10.3.4", "Encrypting filesystem");
        EXTENSION_OID_MAP.put("1.3.6.1.4.1.311.10.3.12", "Document signing");
        EXTENSION_OID_MAP.put("1.3.6.1.4.1.311.21.5", "CA encryption certificate");
        EXTENSION_OID_MAP.put("1.3.6.1.4.1.311.10.3.1", "Microsoft trust list signing");
        EXTENSION_OID_MAP.put("1.3.6.1.4.1.311.10.3.4.1", "File recovery");
        EXTENSION_OID_MAP.put("1.3.6.1.4.1.311.10.3.11", "Key recovery");
        EXTENSION_OID_MAP.put("1.3.6.1.4.1.311.10.3.10", "Qualified subordination");
        EXTENSION_OID_MAP.put("1.3.6.1.4.1.311.10.3.9", "Root list signer");
    }

    private static final Map<String, String> POLICY_OID_MAP = new HashMap<String, String>();

    static {
        //PEPPOL - tree match is possible here (additional .x.y)
        POLICY_OID_MAP.put("1.3.6.1.4.1.27310", "OpenPeppol Infrastructure");
        POLICY_OID_MAP.put("1.3.6.1.4.1.27310.1", "Peppol PKI Services");
        POLICY_OID_MAP.put("1.3.6.1.4.1.27310.1.7", "Peppol AS4 Network Roles");
        POLICY_OID_MAP.put("1.3.6.1.4.1.27310.1.7.1", "Peppol AS4 Access Point");
        POLICY_OID_MAP.put("1.3.6.1.4.1.27310.1.7.2", "Peppol Service Metadata Publisher (SMP)");
        POLICY_OID_MAP.put("1.3.6.1.4.1.27310.1.7.3", "Peppol Service Metadata Locator (SML)");
        POLICY_OID_MAP.put("1.3.6.1.4.1.27310.1.11.1", "Peppol Payment Service Policy");
        POLICY_OID_MAP.put("1.3.6.1.4.1.27310.1.7.4", "Peppol Logistics Service Policy (e-CMR)");
        //BDEW - tree match is possible here (additional .x.y)       
        POLICY_OID_MAP.put("0.4.0.127.0.7", "BSI Germany");
        POLICY_OID_MAP.put("0.4.0.127.0.7.3", "BSI Smart Meter PKI");
        POLICY_OID_MAP.put("0.4.0.127.0.7.3.4.1", "BSI SM-PKI Certificate Policies");
        POLICY_OID_MAP.put("0.4.0.127.0.7.3.1", "BSI SM-PKI Market Partner Identification");
        POLICY_OID_MAP.put("0.4.0.127.0.7.3.4.1.1.1", "BSI Smart Meter PKI Common Policy");
        POLICY_OID_MAP.put("0.4.0.127.0.7.3.4.1.1.2", "BSI Smart Meter PKI - Gateway Administrator");
        POLICY_OID_MAP.put("0.4.0.127.0.7.3.1.2.1.1", "BDEW Market Partner - Electricity");
        POLICY_OID_MAP.put("0.4.0.127.0.7.3.1.2.2.1", "BDEW Market Partner - Gas");
        //ENTSOG - tree match is possible here (additional .x.y)
        POLICY_OID_MAP.put("0.4.0.2042", "ENTSOG ETSI Certified Trust Service");
        POLICY_OID_MAP.put("0.4.0.2042.1", "ENTSOG ETSI NCP (Normalised Certificate Policy) Family");
        POLICY_OID_MAP.put("0.4.0.2042.1.1", "ENTSOG ETSI Normalised Certificate Policy (NCP)");
        POLICY_OID_MAP.put("0.4.0.2042.1.2", "ENTSOG ETSI Extended Normalised Certificate Policy (NCP+)");
        POLICY_OID_MAP.put("0.4.0.1456.1.1", "ENTSOG ETSI Qualified Certificate Policy (QCP+)");
        //e-codex (Justice/e-Government) - tree match is possible here (additional .x.y)
        POLICY_OID_MAP.put("1.3.6.1.4.1.48311", "e-Codex European Justice Network");
        POLICY_OID_MAP.put("1.3.6.1.4.1.48311.1.1", "e-Codex Gateway Policy");
        //eDelivery - tree match is possible here (additional .x.y)
        POLICY_OID_MAP.put("0.4.0.194112", "eIDAS Trust Services (EU)");
        POLICY_OID_MAP.put("0.4.0.194112.1", "eIDAS Qualified Certificate Policies (QCP)");
        POLICY_OID_MAP.put("0.4.0.194112.1.1", "eIDAS Qualified Certificate Policy (QCP-n-qscd)");
        POLICY_OID_MAP.put("0.4.0.194112.1.2", "eIDAS Qualified Signature Policy (QCP-n)");
        POLICY_OID_MAP.put("0.4.0.194112.1.3", "eIDAS Qualified Website Authentication (QWAC)");
        //e-SENS - tree match is possible here (additional .x.y)
        POLICY_OID_MAP.put("1.3.6.1.4.1.48135", "e-SENS European Project Framework");
        POLICY_OID_MAP.put("1.3.6.1.4.1.48135.1.1", "e-SENS Non-Repudiation Policy");
        POLICY_OID_MAP.put("1.3.6.1.4.1.48135.1.2", "e-SENS Business-to-Government Policy");
        //DARZ - tree match is possible here (additional .x.y)
        POLICY_OID_MAP.put("1.3.6.1.4.1.51695", "DARZ (German PKI Root)");
        POLICY_OID_MAP.put("1.3.6.1.4.1.51695.1.2", "DARZ PKI - eANV Environment");
        //German Telematic - tree match is possible here (additional .x.y)
        POLICY_OID_MAP.put("1.3.36.8.3", "German Telematics Infrastructure (Health)");
        POLICY_OID_MAP.put("1.3.36.8.3.4", "Telematik Healthcare Professional (HBA, German)");
        POLICY_OID_MAP.put("1.3.36.8.3.3", "Telematik Institution Card (SMC-B, Hospital/Pharmacy AS4 Gateway");
        //Automotive - tree match is possible here (additional .x.y)
        POLICY_OID_MAP.put("1.3.6.1.4.1.42778", "ENX/TISAX Automotive");
        POLICY_OID_MAP.put("1.3.6.1.4.1.42778.1.1", "ENX TISAX Automotive Communication Policy");
        POLICY_OID_MAP.put("1.3.6.1.4.1.42778.1.1.1", "ENX TISAX Business Policy (Automotive)");
        POLICY_OID_MAP.put("1.3.6.1.4.1.42778.1.1.2", "ENX TISAX High Protection Policy (Sensitive intellectual property)");
        //Banking - tree match is possible here (additional .x.y)
        POLICY_OID_MAP.put("0.4.0.194953", "ETSI PSD2 (Open Banking)");
        POLICY_OID_MAP.put("0.4.0.194953.1.1", "ETSI PSD2 QWAC (Web Auth, Bank-to-Third-Party)");
        POLICY_OID_MAP.put("0.4.0.194953.1.2", "ETSI PSD2 QSealC (Electronic Seal, Secure payment)");
        //Odette Automotive - tree match is possible here (additional .x.y)
        POLICY_OID_MAP.put("1.3.6.1.4.1.21221", "Odette International (Automotive EDI)");
        //T-Systems
        POLICY_OID_MAP.put("1.3.124.1104", "T-Systems/Deutsche Telekom");
        POLICY_OID_MAP.put("1.3.124.1104.5.33", "T-Systems TeleSec Security Services");
        POLICY_OID_MAP.put("1.3.124.1104.5.33.15.3.1", "TeleSec Shared Business CA Standard Policy");
        //Additional branches- tree match is possible here (additional .x.y)
        POLICY_OID_MAP.put("1.3.6.1.4.1.7342.7.1", "GS1 Global AS4 Policy eCom");
        POLICY_OID_MAP.put("1.3.6.1.4.1.33126", "UNECE International Trade Infrastructure");
        POLICY_OID_MAP.put("1.3.6.1.4.1.34119.1.1", "STET Open Banking AS4 Policy (French/EU banking)");
        POLICY_OID_MAP.put("1.3.6.1.4.1.53444.1.1", "EU Customs Gateway Policy (National customs)");
        POLICY_OID_MAP.put("1.3.6.1.4.1.7342", "GS1 Global eCom Framework");
        POLICY_OID_MAP.put("1.3.171.1.1.1", "Deutsche Telekom Trust Center (Business)");
        POLICY_OID_MAP.put("1.3.6.1.4.1.22177", "QuoVadis (Common European PKI)");
        POLICY_OID_MAP.put("1.3.6.1.4.1.4146", "Symantec/VeriSign Trust Network");
        //D-TRUST / Bundesdruckerei
        POLICY_OID_MAP.put("1.3.6.1.4.1.6189", "D-TRUST/Bundesdruckerei");
        //Sparkasse
        POLICY_OID_MAP.put("1.3.6.1.4.1.18332", "S-Trust/Sparkassen-Finanzgruppe");
        //Generic validated
        POLICY_OID_MAP.put("2.23.140.1.2.1", "Domain Validated Certificate");
        POLICY_OID_MAP.put("2.23.140.1.2.2", "Organization Validated Certificate");
        POLICY_OID_MAP.put("2.23.140.1.2.3", "Extended Validation Certificate");
        POLICY_OID_MAP.put("1.3.6.1.4.1.6449.1.2.1", "Domain Validated Certificate (Setigo)");
        POLICY_OID_MAP.put("1.3.6.1.4.1.6449.1.2.2", "Organization Validated Certificate (Setigo)");
        POLICY_OID_MAP.put("1.3.6.1.4.1.6449.1.2.1.5.1", "Extended Validation Certificate (Setigo)");
        POLICY_OID_MAP.put("2.5.29.32", "General Purpose (anyPolicy)");
    }

    public KeystoreCertificate() {
    }

    /**
     * Clone this object
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            KeystoreCertificate clonedEntry = (KeystoreCertificate) super.clone();
            clonedEntry.resetCaching();
            return (clonedEntry);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return (null);
        }
    }

    /**
     * Resets all lazy loading cache fields. This is important for cloning to
     * ensure that the clone does not share the same reference objects for
     * caching with the original.
     */
    private void resetCaching() {
        // Reset all lazy cache AtomicReferences to null
        this.notBeforeRef.set(null);
        this.notAfterRef.set(null);
        this.issuerPrincipalRef.set(null);
        this.subjectPrincipalRef.set(null);
        this.fingerprintSHA1BytesRef.set(null);
        this.fingerprintMD5Ref.set(null);
        this.fingerprintSHA1Ref.set(null);
        this.fingerprintSHA256Ref.set(null);
        this.issuerDNRef.set(null);
        this.subjectDNRef.set(null);
        this.serialNumberRef.set(null);
        this.publicKeyRef.set(null);
        this.publicKeyEncodedRef.set(null);
        this.certificateEncodedRef.set(null);
        this.infoTextRef.set(null);
    }

    /**
     * Returns the extension value "extended key usage", OID 2.5.29.37
     *
     */
    public List<String> getExtendedKeyUsage() {
        List<String> extendedKeyUsage = new ArrayList<String>();
        try {
            List<String> oidList = this.certificate.getExtendedKeyUsage();
            for (String oid : oidList) {
                if (EXTENSION_OID_MAP.containsKey(oid)) {
                    extendedKeyUsage.add(EXTENSION_OID_MAP.get(oid));
                } else {
                    extendedKeyUsage.add(oid);
                }
            }
        } catch (Exception e) {
        }
        return (extendedKeyUsage);
    }

    /**
     * OID 2.5.29.35 - Authority Key Identifier This extension may be used
     * either as a certificate or CRL extension. It identifies the public key to
     * be used to verify the signature on this certificate or CRL. It enables
     * distinct keys used by the same CA to be distinguished (e.g., as key
     * updating occurs).
     *
     * @return
     */
    public List<String> getAuthorityKeyIdentifier() {
        List<String> authorityKeyIdentifierList = new ArrayList<String>();
        byte[] extensionValue = this.certificate.getExtensionValue("2.5.29.35");
        if (extensionValue == null) {
            //there is no such extension: return empty list
            return (authorityKeyIdentifierList);
        }
        try {
            byte[] octedBytes = ((ASN1OctetString) ASN1Primitive.fromByteArray(extensionValue)).getOctets();
            ASN1Sequence asn1Sequence = (ASN1Sequence) ASN1Primitive.fromByteArray(octedBytes);
            for (int i = 0, len = asn1Sequence.size(); i < len; i++) {
                if (asn1Sequence.getObjectAt(i) instanceof DERTaggedObject) {
                    DERTaggedObject derTagObj = (DERTaggedObject) asn1Sequence.getObjectAt(i);
                    if (derTagObj.getTagNo() == 0) {
                        DEROctetString octetStr = (DEROctetString) derTagObj.getLoadedObject();
                        byte[] identifier = octetStr.getOctets();
                        authorityKeyIdentifierList.add("[Key identifier] " + byteArrayToHexStr(identifier));
                    } else if (derTagObj.getTagNo() == 2) {
                        DEROctetString octetStr = (DEROctetString) derTagObj.getLoadedObject();
                        byte[] identifier = octetStr.getOctets();
                        authorityKeyIdentifierList.add("[Serial] " + byteArrayToHexStr(identifier));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (authorityKeyIdentifierList);
    }

    /**
     * OID 2.5.29.14 - Subject Key Identifier This extension identifies the
     * public key being certified. It enables distinct keys used by the same
     * subject to be differentiated (e.g., as key updating occurs). If the
     * certificate does not have this extension the system will compute it from
     * the internal Subject Key Identifier
     *
     * @return
     */
    public List<String> getSubjectKeyIdentifier() {
        List<String> subjectKeyIdentifierList = new ArrayList<String>();
        try {
            byte[] extensionValue = this.certificate.getExtensionValue("2.5.29.14");
            if (extensionValue == null) {
                //there is no such extension: compute the SKI and return it
                byte[] ski = calculateSKI(this.certificate);
                subjectKeyIdentifierList.add(byteArrayToHexStr(ski));
                return (subjectKeyIdentifierList);
            } else {
                byte[] octedBytes = ((ASN1OctetString) ASN1Primitive.fromByteArray(extensionValue)).getOctets();
                DEROctetString octetStr = (DEROctetString) ASN1Primitive.fromByteArray(octedBytes);
                byte[] identifier = octetStr.getOctets();
                subjectKeyIdentifierList.add(byteArrayToHexStr(identifier));

            }
        } catch (Exception e) {
        }
        return (subjectKeyIdentifierList);
    }

    /**
     * Calculates the subject key identifier of a given certificate, via RFC
     * 5280 Method 1. This might be useful if the SKI extension does not exist.
     * Its length is always 20 bytes
     *
     * @param cert
     * @return
     * @throws Exception
     */
    public static byte[] calculateSKI(X509Certificate cert) throws Exception {
        SubjectKeyIdentifier subjectKeyIdentifier
                = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(cert.getPublicKey());
        return (subjectKeyIdentifier.getKeyIdentifier());
    }

    public List<String> getPolicy() {
        List<String> policyList = new ArrayList<>();
        //OID of Certificate Policies
        byte[] extensionValue = this.certificate.getExtensionValue("2.5.29.32");
        if (extensionValue == null) {
            return policyList;
        }
        try {
            //remove ASN.1 wrapper
            byte[] octetBytes = ASN1OctetString.getInstance(extensionValue).getOctets();
            CertificatePolicies policies = CertificatePolicies.getInstance(ASN1Primitive.fromByteArray(octetBytes));
            for (PolicyInformation policyInfo : policies.getPolicyInformation()) {
                //Add OIDs - this is mainly important for AS4
                String policyOID = policyInfo.getPolicyIdentifier().getId();
                String policyOIDHumanReadable = this.getPolicyOIDHumanReadable(policyOID);
                policyList.add("(" + policyOIDHumanReadable + ")");
                if (policyInfo.getPolicyQualifiers() != null) {
                    for (int i = 0; i < policyInfo.getPolicyQualifiers().size(); i++) {
                        PolicyQualifierInfo qualifierInfo = PolicyQualifierInfo.getInstance(
                                policyInfo.getPolicyQualifiers().getObjectAt(i)
                        );
                        //check for cps link
                        if (qualifierInfo.getPolicyQualifierId().equals(PolicyQualifierId.id_qt_cps)) {
                            policyList.add(qualifierInfo.getQualifier().toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Tipp: Logge den Fehler zumindest während der Entwicklung!
            e.printStackTrace();
        }
        return policyList;
    }

    /**
     * Resolves a Policy OID to a human-readable English description. Supports
     * exact matches and hierarchical tree-based fallbacks.
     *
     * * @param policyOID The OID string (e.g., "1.3.6.1.4.1.27310.1.7.1")
     * @return A descriptive string or the OID itself if unknown.
     */
    private String getPolicyOIDHumanReadable(String policyOID) {
        if (policyOID == null || policyOID.isEmpty()) {
            return "";
        }
        if (POLICY_OID_MAP.containsKey(policyOID)) {
            return POLICY_OID_MAP.get(policyOID);
        }
        //Tree based mapping - shorten the OID (1.2.3 -> 1.2 -> 1)
        String currentOid = policyOID;
        while (currentOid.contains(".")) {
            int lastDotIndex = currentOid.lastIndexOf('.');
            if (lastDotIndex == -1) {
                break;
            }
            currentOid = currentOid.substring(0, lastDotIndex);
            if (POLICY_OID_MAP.containsKey(currentOid)) {
                return POLICY_OID_MAP.get(currentOid);
            }
        }
        return policyOID;
    }

    /**
     * Returns the key usages of this cert, OID 2.5.29.15
     */
    public List<String> getKeyUsages() {
        List<String> keyUsages = new ArrayList<String>();
        boolean[] keyUsage = this.certificate.getKeyUsage();
        try {
            if (keyUsage != null) {
                for (int i = 0; i < keyUsage.length; i++) {
                    if (keyUsage[i]) {
                        keyUsages.add(KEY_USAGE_NAMES[i]);
                    }
                }
            }
        } catch (Exception e) {
        }
        return (keyUsages);
    }

    /**
     * In fact whenever we say key we mean a pair of numbers comprising the key;
     * a key number to use in the raising of powers and another number that is
     * the modulus of the arithmetic to be used for the work.
     *
     * @return
     */
    public BigInteger getModulus() {
        PublicKey publicKey = this.getPublicKey();
        if (publicKey instanceof RSAPublicKey) {
            RSAPublicKey rsaKey = (RSAPublicKey) publicKey;
            return (rsaKey.getModulus());
        }
        return (BigInteger.ZERO);
    }

    /**
     * In fact whenever we say key we mean a pair of numbers comprising the key;
     * a key number to use in the raising of powers and another number that is
     * the modulus of the arithmetic to be used for the work.
     *
     * @return
     */
    public BigInteger getPublicExponent() {
        PublicKey publicKey = this.getPublicKey();
        if (publicKey instanceof RSAPublicKey) {
            RSAPublicKey rsaKey = (RSAPublicKey) publicKey;
            return (rsaKey.getPublicExponent());
        }
        return (BigInteger.ZERO);
    }

    /**
     * Returns the subject alternative name of this cert, OID 2.5.29.17
     */
    public List<String> getSubjectAlternativeNames() {
        List<String> alternativeNames = new ArrayList<String>();
        byte[] extensionValue = this.certificate.getExtensionValue("2.5.29.17");
        if (extensionValue == null) {
            return (alternativeNames);
        }
        try {
            byte[] octedBytes = ((ASN1OctetString) ASN1Primitive.fromByteArray(extensionValue)).getOctets();
            GeneralName[] names = (GeneralNames.getInstance(ASN1Primitive.fromByteArray(octedBytes))).getNames();
            for (GeneralName name : names) {
                ASN1Encodable encodable = name.getName();

                //IP addresses are sometimes stored as DEROctetString which would result in a single hex value on display
                // - this has to be decoded for the display
                if (encodable instanceof DEROctetString && name.getTagNo() == GeneralName.iPAddress) {
                    DEROctetString str = (DEROctetString) encodable;
                    StringBuilder decStr = new StringBuilder();
                    byte[] octets = str.getOctets();
                    for (byte octet : octets) {
                        if (decStr.length() > 0) {
                            decStr.append(".");
                        }
                        decStr.append((int) (octet & 0xFF));
                    }
                    alternativeNames.add(decStr + " (" + generalNameTagNoToString(name) + ")");
                } else {
                    alternativeNames.add(((ASN1Encodable) name.getName()).toString()
                            + " (" + generalNameTagNoToString(name) + ")");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (alternativeNames);
    }

    /**
     * Converts the tag no of a general name to a human readable value
     */
    public static final String generalNameTagNoToString(int tagNo) {
        if (tagNo == GeneralName.dNSName) {
            return ("DNS");
        }
        if (tagNo == GeneralName.directoryName) {
            return ("Directory");
        }
        if (tagNo == GeneralName.ediPartyName) {
            return ("EDI party");
        }
        if (tagNo == GeneralName.iPAddress) {
            return ("IP");
        }
        if (tagNo == GeneralName.otherName) {
            return ("Other name");
        }
        if (tagNo == GeneralName.registeredID) {
            return ("Registered ID");
        }
        if (tagNo == GeneralName.rfc822Name) {
            return ("Mail (RFC822)");
        }
        if (tagNo == GeneralName.uniformResourceIdentifier) {
            return ("URI");
        }
        if (tagNo == GeneralName.x400Address) {
            return ("x.400");
        }
        return ("");
    }

    /**
     * Converts the tag no of a general name to a human readable value
     */
    public static final String generalNameTagNoToString(GeneralName name) {
        return (generalNameTagNoToString(name.getTagNo()));
    }

    /**
     * Extracts the OCSP responder URLs as list from the Authority Information
     * Access (AIA) extension of the passed certificate. Returns an emtpy list
     * if either a problem occured or no URL exists
     */
    public static List<String> getOCSPURLs(X509Certificate certificate) {
        List<String> ocspURLs = new ArrayList<String>();
        // OID for AIA is 1.3.6.1.5.5.7.1.1
        byte[] aiaExtensionValue = certificate.getExtensionValue("1.3.6.1.5.5.7.1.1");
        if (aiaExtensionValue == null) {
            return (ocspURLs);
        }
        try {
            try (ASN1InputStream asn1In = new ASN1InputStream(aiaExtensionValue)) {
                DEROctetString aiaDEROctetString = (DEROctetString) asn1In.readObject();
                try (ASN1InputStream asn1InOctets = new ASN1InputStream(aiaDEROctetString.getOctets())) {
                    ASN1Primitive aiaDERObject = asn1InOctets.readObject();
                    AuthorityInformationAccess aia = AuthorityInformationAccess.getInstance(aiaDERObject);
                    for (AccessDescription description : aia.getAccessDescriptions()) {
                        //Check if the AccessMethod is the OID for OCSP (OID 1.3.6.1.5.5.7.48.1)
                        if (description.getAccessMethod().getId().equals("1.3.6.1.5.5.7.48.1")) {
                            //The URL is a general name (uniformResourceIdentifier)
                            GeneralName generalName = description.getAccessLocation();
                            if (generalName.getTagNo() == GeneralName.uniformResourceIdentifier) {
                                String url = DERIA5String.getInstance(generalName.getName()).getString().trim();
                                ocspURLs.add(url);
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
        }
        return (ocspURLs);
    }

    /**
     * Extracts all CRL distribution point URLs from the "CRL Distribution
     * Point" extension in a X.509 certificate. Without a CRL entry this returns
     * an empty list
     */
    public static List<String> getCRLDistributionPoints(X509Certificate certificate) {
        List<String> crlURLList = new ArrayList<String>();
        byte[] crlDPExtensionValue = certificate.getExtensionValue("2.5.29.31");
        if (crlDPExtensionValue == null) {
            return (crlURLList);
        }
        //crlDPExtensionValue is encoded in ASN.1 format.        
        //DER (Distinguished Encoding Rules) is one of ASN.1 encoding rules defined in ITU-T X.690, 2002, specification.
        //ASN.1 encoding rules can be used to encode any data object into a binary file. Read the object in octets.
        CRLDistPoint distPoint;
        try {
            try (ASN1InputStream asn1In = new ASN1InputStream(crlDPExtensionValue)) {
                DEROctetString crlDEROctetString = (DEROctetString) asn1In.readObject();
                //Get Input stream in octets
                try (ASN1InputStream asn1InOctets = new ASN1InputStream(crlDEROctetString.getOctets())) {
                    ASN1Primitive crlDERObject = asn1InOctets.readObject();
                    distPoint = CRLDistPoint.getInstance(crlDERObject);
                }
            }
        } catch (IOException e) {
            return (crlURLList);
        }
        for (DistributionPoint distributionPoint : distPoint.getDistributionPoints()) {
            DistributionPointName singleDistributionPoint = distributionPoint.getDistributionPoint();
            if (singleDistributionPoint != null && singleDistributionPoint.getType() == DistributionPointName.FULL_NAME) {
                GeneralName[] generalNames = GeneralNames.getInstance(singleDistributionPoint.getName()).getNames();
                // Look for a URI
                for (GeneralName genName : generalNames) {
                    if (genName.getTagNo() == GeneralName.uniformResourceIdentifier) {
                        //DERIA5String contains an ascii string.
                        //A IA5String is a restricted character string type in the ASN.1 notation
                        String url = DERIA5String.getInstance(genName.getName()).getString().trim();
                        crlURLList.add(url);
                    }
                }
            }
        }
        return (crlURLList);
    }

    /**
     * Returns the enwrapped certificate version
     */
    public int getVersion() {
        return (this.certificate.getVersion());
    }

    public String getSigAlgName() {
        return (this.certificate.getSigAlgName());
    }

    public String getSigAlgOID() {
        return (this.certificate.getSigAlgOID());
    }

    public String getPublicKeyAlgorithm() {
        PublicKey publicKey = this.getPublicKey();
        String algorithm = publicKey.getAlgorithm();
        //some provider just return XDH without specifying it more
        if (algorithm.equals("XDH")) {
            SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(this.getPublicKeyEncoded());
            ASN1ObjectIdentifier oid = subjectPublicKeyInfo.getAlgorithm().getAlgorithm();
            if (oid.equals(new ASN1ObjectIdentifier("1.3.101.110"))) {
                algorithm = KeyGenerator.CURVE_NAME_X25519;
            } else if (oid.equals(new ASN1ObjectIdentifier("1.3.101.111"))) {
                algorithm = KeyGenerator.CURVE_NAME_X448;
            }
        }
        return (algorithm);
    }

    /**
     * Valid date start, includes a lazy cache mechanism
     */
    @JsonIgnore
    public Date getNotBefore() {
        Date result = this.notBeforeRef.get();
        if (result == null) {
            Date computed = this.certificate.getNotBefore();
            if (this.notBeforeRef.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = notBeforeRef.get();
            }
        }
        //do not return the pointer to the class internal object
        return new Date(result.getTime());
    }

    /**
     * Valid date end, includes a lazy cache mechanism
     */
    @JsonIgnore
    public Date getNotAfter() {
        Date result = this.notAfterRef.get();
        if (result == null) {
            Date computed = this.certificate.getNotAfter();
            if (this.notAfterRef.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = notAfterRef.get();
            }
        }
        //do not return the pointer to the class internal object
        return new Date(result.getTime());
    }

    /**
     * Returns a String that contains just the parts "CN", "O", "OU", "C", "ST",
     * "L", "E" of the subject - in this order
     *
     * @return
     */
    public String getSubjectDN() {
        String result = this.subjectDNRef.get();
        if (result != null) {
            return result;
        }
        X500Principal subjectX500Principal = this.getSubjectX500Principal();
        String fullSubject = subjectX500Principal.getName();
        final String[] displayList = new String[]{"CN", "O", "OU", "C", "ST", "L", "E"};
        StringBuilder subjectBuilder = new StringBuilder();
        try {
            LdapName subjectLdapName = new LdapName(fullSubject);
            for (String displayType : displayList) {
                for (Rdn rdn : subjectLdapName.getRdns()) {
                    if (rdn.getType().equalsIgnoreCase(displayType)) {
                        if (subjectBuilder.length() > 0) {
                            subjectBuilder.append(",");
                        }
                        subjectBuilder.append(displayType)
                                .append("=")
                                .append(this.escapeRDNValue(rdn.getValue().toString()));
                    }
                }
            }
            result = subjectBuilder.toString();
        } catch (Throwable e) {
            result = fullSubject;
        }
        this.subjectDNRef.compareAndSet(null, result);
        return (this.subjectDNRef.get());
    }

    /**
     * Returns a String that contains just the parts "CN", "O", "OU", "C", "ST",
     * "L", "E" of the issuer - in this order
     *
     * @return The issuer as String - escaped if this is required by the content
     */
    public String getIssuerDN() {
        String result = this.issuerDNRef.get();
        if (result != null) {
            return result;
        }
        //issuer DN is not computed so far - compute it
        X500Principal issuerX500Principal = this.getIssuerX500Principal();
        String fullIssuer = issuerX500Principal.getName();
        final String[] displayList = new String[]{"CN", "O", "OU", "C", "ST", "L", "E"};
        StringBuilder issuerBuilder = new StringBuilder();
        try {
            LdapName issuerLdapName = new LdapName(fullIssuer);
            for (String displayType : displayList) {
                for (Rdn rdn : issuerLdapName.getRdns()) {
                    if (rdn.getType().equalsIgnoreCase(displayType)) {
                        if (issuerBuilder.length() > 0) {
                            issuerBuilder.append(",");
                        }
                        issuerBuilder.append(displayType)
                                .append("=")
                                .append(this.escapeRDNValue(rdn.getValue().toString()));
                    }
                }
            }
            result = issuerBuilder.toString();
        } catch (Throwable e) {
            result = fullIssuer;
        }
        //set the new computed value if it is not set so far by another thread
        this.issuerDNRef.compareAndSet(null, result);
        return (this.issuerDNRef.get());
    }

    /**
     * Returns the subject x500 principal, lazy cached
     *
     * @return
     */
    @JsonIgnore
    public X500Principal getSubjectX500Principal() {
        X500Principal result = this.subjectPrincipalRef.get();
        if (result == null) {
            X500Principal computed = this.certificate.getSubjectX500Principal();
            if (this.subjectPrincipalRef.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = this.subjectPrincipalRef.get();
            }
        }
        //X500Principal is immutable, no copy required
        return result;
    }

    /**
     * Returns the issuer x500 principal, lazy cached
     *
     * @return
     */
    @JsonIgnore
    public X500Principal getIssuerX500Principal() {
        X500Principal result = this.issuerPrincipalRef.get();
        if (result == null) {
            X500Principal computed = this.certificate.getIssuerX500Principal();
            if (this.issuerPrincipalRef.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = this.issuerPrincipalRef.get();
            }
        }
        //X500Principal is immutable, no copy required
        return result;
    }

    /**
     * If an issuer is requested as String there are several characters that
     * need to be escaped, e.g. ","
     *
     * @param rdnValue
     * @return
     */
    private String escapeRDNValue(String rdnValue) {
        if (rdnValue == null || rdnValue.isEmpty()) {
            return rdnValue;
        }
        final String SPECIAL_X500_CHARACTERS = ",=+<>#;\"\\";
        StringBuilder escapedValue = new StringBuilder();
        for (int i = 0; i < rdnValue.length(); i++) {
            char foundChar = rdnValue.charAt(i);
            if (SPECIAL_X500_CHARACTERS.contains(String.valueOf(foundChar))) {
                escapedValue.append("\\");
            }
            escapedValue.append(foundChar);
        }
        return (escapedValue.toString());
    }

    /**
     * Returns the serial number as decimal
     */
    public String getSerialNumberDEC() {
        BigInteger result = getSerialNumberDECBigInt();
        return (result.toString());
    }

    /**
     * Returns the serial number as decimal
     */
    public BigInteger getSerialNumberDECBigInt() {
        BigInteger result = this.serialNumberRef.get();
        if (result == null) {
            BigInteger computed = this.certificate.getSerialNumber();
            if (this.serialNumberRef.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = serialNumberRef.get();
            }
        }
        return (result);
    }

    /**
     * Returns the serial number as decimal
     */
    public String getSerialNumberHEX() {
        BigInteger result = this.serialNumberRef.get();
        if (result == null) {
            BigInteger computed = this.certificate.getSerialNumber();
            if (this.serialNumberRef.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = serialNumberRef.get();
            }
        }
        return (result.toString(16).toUpperCase());
    }

    public void setAlias(String alias) {
        if (alias == null) {
            alias = "";
        }
        this.alias = alias;
    }

    public void setCertificate(X509Certificate certificate, Certificate[] certificateChain) {
        this.certificate = certificate;
        this.certificateChain = certificateChain;
    }

    /**
     * @deprecated (This ment the private key - please use setPrivateKey
     * instead)
     */
    @Deprecated(since = "2024")
    public void setKey(Key privateKey) {
        this.setPrivateKey(privateKey);
    }

    public void setPrivateKey(Key privateKey) {
        this.privateKey = privateKey;
    }

    public void setIsKeyPair(boolean isKeyPair) {
        this.isKeyPair = isKeyPair;
    }

    public X509Certificate getX509Certificate() {
        return (this.certificate);
    }

    public boolean getIsKeyPair() {
        return (this.isKeyPair);
    }

    /**
     * Returns the private key of the entry - or null if it is not set
     *
     * @deprecated (This ment the private key - please use getPrivateKey
     * instead)
     */
    @Deprecated(since = "2024")
    public Key getKey() {
        return (this.getPrivateKey());
    }

    /**
     * Returns the private key of the entry - or null if it is not set
     */
    public Key getPrivateKey() {
        return (this.privateKey);
    }

    /**
     * Returns the public key of the entry
     */
    public PublicKey getPublicKey() {
        PublicKey result = this.publicKeyRef.get();
        if (result == null) {
            PublicKey computed = this.certificate.getPublicKey();
            if (this.publicKeyRef.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = this.publicKeyRef.get();
            }
        }
        //public key is immutable
        return (result);
    }

    /**
     * Returns the encoded public key of this entry
     */
    public byte[] getPublicKeyEncoded() {
        byte[] result = this.publicKeyEncodedRef.get();
        if (result == null) {
            byte[] computed = this.getPublicKey().getEncoded();
            if (this.publicKeyEncodedRef.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = this.publicKeyEncodedRef.get();
            }
        }
        //always return a copy, do not provide a point to internal structures
        return result.clone();
    }

    /**
     * Returns the encoded certificate of this entry or null if there is no
     * certificate assigned
     */
    public byte[] getCertificateEncoded() {
        byte[] result = this.certificateEncodedRef.get();
        if (result == null) {
            try {
                byte[] computed = this.certificate.getEncoded();
                if (this.certificateEncodedRef.compareAndSet(null, computed)) {
                    result = computed;
                } else {
                    result = this.certificateEncodedRef.get();
                }
            } catch (Throwable e) {
                return (null);
            }
        }
        //always return a copy, do not provide a point to internal structures
        return result.clone();
    }

    /**
     * KeyUsage extension, (OID = 2.5.29.15). The key usage extension defines
     * the purpose (e.g., encipherment, signature, certificate signing) of the
     * key contained in the certificate. The ASN.1 definition for this is:
     *
     * KeyUsage ::= BIT STRING { digitalSignature (0), nonRepudiation (1),
     * keyEncipherment (2), dataEncipherment (3), keyAgreement (4), keyCertSign
     * (5), --> true ONLY for CAs cRLSign (6), encipherOnly (7), decipherOnly
     * (8) }
     *
     * @return
     */
    public boolean isCACertificate() {
        boolean[] keyUsage = this.certificate.getKeyUsage();
        if (keyUsage != null) {
            return (keyUsage[5]);
        } else {
            return (false);
        }
    }

    public boolean isEndUserCertificate() {
        return (!this.isCACertificate());
    }

    /**
     * This method seems not to be reliable for all certificates - sometimes the
     * Root certificates could not be identified using it. The method
     * isCACertificate works for sure - but does only identify if a certificate
     * is an end user certificate or not.
     *
     * @return
     */
    public boolean isRootCertificate() {
        return (this.isSelfSigned() && this.certificate.getBasicConstraints() != -1);
    }

    public boolean isSelfSigned() {
        X500Principal subject = this.getSubjectX500Principal();
        X500Principal issuer = this.getIssuerX500Principal();
        return (subject.equals(issuer));
    }

    public String getAlias() {
        return (this.alias);
    }

    /**
     * Returns the key length of the passed public key. It is first analyzed if
     * this is a EC key, RSA etc..
     *
     * @param publicKey
     * @return
     */
    public int getPublicKeyLength() {
        PublicKey publicKey = this.getPublicKey();
        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(this.getPublicKeyEncoded());
        ASN1ObjectIdentifier oid = subjectPublicKeyInfo.getAlgorithm().getAlgorithm();
        if (publicKey instanceof RSAPublicKey) {
            RSAPublicKey rsaKey = (RSAPublicKey) publicKey;
            return (rsaKey.getModulus().bitLength());
        } else if (publicKey instanceof DSAPublicKey) {
            DSAPublicKey dsaKey = (DSAPublicKey) publicKey;
            return (dsaKey.getParams().getP().bitLength());
        } else if (publicKey instanceof ECPublicKey) {
            ECPublicKey ecKey = (ECPublicKey) publicKey;
            return (ecKey.getParams().getOrder().bitLength());
        } else if (publicKey instanceof BCEdDSAPublicKey) {
            BCEdDSAPublicKey edDSAPublicKey = (BCEdDSAPublicKey) publicKey;
            if (edDSAPublicKey.getAlgorithm().equals(KeyGenerator.CURVE_NAME_ED25519)) {
                return (Ed25519.PUBLIC_KEY_SIZE * 8);
            } else {
                return (0);
            }
        } else if (oid.equals(new ASN1ObjectIdentifier("1.3.101.110"))) {
            // X25519
            return (32 * 8);
        } else if (oid.equals(new ASN1ObjectIdentifier("1.3.101.111"))) {
            // X448
            return (56);
        } else if (publicKey instanceof BCDilithiumPublicKey) {
            BCDilithiumPublicKey dilithiumPublicKey = (BCDilithiumPublicKey) publicKey;
            String specName = dilithiumPublicKey.getParameterSpec().getName();
            if (specName.equals(DilithiumParameterSpec.dilithium2.getName())) {
                return (1312);
            } else if (specName.equals(DilithiumParameterSpec.dilithium3.getName())) {
                return (1952);
            } else if (specName.equals(DilithiumParameterSpec.dilithium5.getName())) {
                return (2592);
            }
        } else if (publicKey instanceof BCSPHINCSPlusPublicKey) {
            BCSPHINCSPlusPublicKey sphincsplusPublicKey = (BCSPHINCSPlusPublicKey) publicKey;
            String specName = sphincsplusPublicKey.getParameterSpec().getName();
            if (specName.contains("128")) {
                return (32);
            } else if (specName.contains("192")) {
                return (48);
            } else if (specName.contains("256")) {
                return (64);
            }
        }
        return (0);
    }

    /**
     * Returns the fingerprint bytes, lazy cached
     *
     * @return
     */
    public byte[] getFingerPrintBytesSHA1() {
        byte[] result = this.fingerprintSHA1BytesRef.get();
        if (result == null) {
            byte[] computed = getFingerPrintBytes(this.getCertificateEncoded(), "SHA1");
            //set the value atomar the first time
            if (this.fingerprintSHA1BytesRef.compareAndSet(null, computed)) {
                result = computed;
            } else {
                //the value has been already set
                result = fingerprintSHA1BytesRef.get();
            }
        }
        //always return a copy, do not provide a point to internal structures
        return result.clone();
    }

    public byte[] getFingerPrintBytesMD5() {
        return (this.getFingerPrintBytes(this.getCertificateEncoded(), "MD5"));
    }

    public byte[] getFingerPrintBytesSHA256() {
        return (this.getFingerPrintBytes(this.getCertificateEncoded(), "SHA-256"));
    }

    /**
     * Returns the fingerprint SHA1 String as hex string, lazy cached
     *
     * @return
     */
    public String getFingerPrintSHA1() {
        String result = this.fingerprintSHA1Ref.get();
        if (result == null) {
            String computed = this.getFingerPrint("SHA1");
            if (this.fingerprintSHA1Ref.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = this.fingerprintSHA1Ref.get();
            }
        }
        //String is immutable, no copy required as return
        return result;
    }

    public String getFingerPrintMD5() {
        String result = this.fingerprintMD5Ref.get();
        if (result == null) {
            String computed = this.getFingerPrint("MD5");
            if (this.fingerprintMD5Ref.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = this.fingerprintMD5Ref.get();
            }
        }
        //String is immutable, no copy required as return
        return result;
    }

    public String getFingerPrintSHA256() {
        String result = this.fingerprintSHA256Ref.get();
        if (result == null) {
            String computed = this.getFingerPrint("SHA-256");
            if (this.fingerprintSHA256Ref.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = this.fingerprintSHA256Ref.get();
            }
        }
        //String is immutable, no copy required as return
        return result;
    }

    /**
     * Deserializes a fingerprint string to a byte array It is assumed that the
     * fingerprint string has the format hex:hex:hex
     */
    public static byte[] fingerprintStrToBytes(String fingerprintStr) {
        if (fingerprintStr == null || !fingerprintStr.contains(":")) {
            throw new IllegalArgumentException("KeystoreCertificate.fingerprintStrToBytes: The certificate fingerprint \"" + fingerprintStr + "\" is not a valid fingerprint");
        }
        String[] token = fingerprintStr.split(":");
        byte[] bytes = new byte[token.length];
        for (int i = 0; i < token.length; i++) {
            while (token[i].length() < 2) {
                token[i] = "0" + token[i];
            }
            bytes[i] = fromHexString(token[i])[0];
        }
        return (bytes);
    }

    private static byte[] fromHexString(final String encoded) {
        if ((encoded.length() % 2) != 0) {
            throw new IllegalArgumentException("KeystoreCertificate.fromHexString: Input string must contain an even number of characters");
        }
        final byte[] result = new byte[encoded.length() / 2];
        final char[] enc = encoded.toCharArray();
        try {
            for (int i = 0; i < enc.length; i += 2) {
                StringBuilder curr = new StringBuilder(2);
                curr.append(enc[i]).append(enc[i + 1]);
                result[i / 2] = (byte) Integer.parseInt(curr.toString(), 16);
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("KeystoreCertificate.fromHexString: Input string must contain hex values, found: \"" + encoded + "\"");
        }
    }

    public static String byteArrayToHexStr(byte[] byteArray) {
        StringBuilder hextStringBuffer = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if (i > 0) {
                hextStringBuffer.append(":");
            }
            String singleByte = Integer.toHexString(byteArray[i] & 0xFF).toUpperCase();
            if (singleByte.isEmpty()) {
                hextStringBuffer.append("00");
            } else if (singleByte.length() == 1) {
                hextStringBuffer.append("0");
            }
            hextStringBuffer.append(singleByte);
        }
        return hextStringBuffer.toString();
    }

    /**
     * Serializes a fingerprint string from a byte array to a String It is
     * assumed that the fingerprint string has the format hex:hex:hex
     */
    public static String fingerprintBytesToStr(byte[] fingerprintBytes) {
        return (byteArrayToHexStr(fingerprintBytes));
    }

    /**
     * @param digest to create the hash value, please use "SHA1", "MD5",
     * "SHA-256" etc
     *
     */
    public static byte[] getFingerPrintBytes(byte[] certificateX509Encoded, String digest) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(digest);
            return (messageDigest.digest(certificateX509Encoded));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Returns a fingerprint string that returns the fingerprint using the
     * format n:n:n
     *
     * @param digest to create the hash value, e.g. "SHA1", "MD5", "SHA-256"
     *
     */
    private String getFingerPrint(String digest) {
        return (fingerprintBytesToStr(getFingerPrintBytes(this.getCertificateEncoded(), digest)));
    }

    /**
     * Returns the cert path for this certificate as it exists in the keystore.
     * This result includes ONLY intermediate certificates from the end
     * certificate to the trust anchor. Means any self signed certificate, any
     * certificate without basic constraints could be never part of this result
     *
     * @return null if no cert path could be found
     *
     */
    public PKIXCertPathBuilderResult getPKIXCertPathBuilderResult(Set<TrustAnchor> trustAnchors, List<X509Certificate> possibleIntermediateCertificateList) {
        try {
            X509CertSelector certSelector = new X509CertSelector();
            // RFC 5280: a certificate is unique by the combination of issuer and serial number
            certSelector.setSerialNumber(this.getSerialNumberDECBigInt());
            certSelector.setIssuer(this.getIssuerX500Principal());
            CertPathBuilder pathBuilder = CertPathBuilder.getInstance("PKIX",
                    BouncyCastleProviderSingleton.instance());
            PKIXBuilderParameters pkixParameter = new PKIXBuilderParameters(trustAnchors, certSelector);
            pkixParameter.setRevocationEnabled(false);
            // This is necessary for brainpool certificates under Java 17 and above
            pkixParameter.setSigProvider(BouncyCastleProviderSingleton.instance().getName());
            // Max path length: Anchor + 3 certificates
            pkixParameter.setMaxPathLength(3);
            List<X509Certificate> filteredCertificateList = new ArrayList<X509Certificate>();
            for (X509Certificate cert : possibleIntermediateCertificateList) {
                // Self-signed certificates (Roots) cannot be intermediate certificates
                if (cert.getSubjectX500Principal().equals(cert.getIssuerX500Principal())) {
                    continue;
                }
                filteredCertificateList.add(cert);
            }
            CertStoreParameters storeParameter = new CollectionCertStoreParameters(filteredCertificateList);
            CertStore certStore = CertStore.getInstance("Collection", storeParameter,
                    BouncyCastleProviderSingleton.instance());
            pkixParameter.addCertStore(certStore);
            return (PKIXCertPathBuilderResult) pathBuilder.build(pkixParameter);
        } catch (Throwable e) {
        }
        return null;
    }

    public PKIXCertPathBuilderResult
            getPKIXCertPathBuilderResult_OLD(Set<TrustAnchor> trustAnchors, List<X509Certificate> possibleIntermediateCertificateList) {
        try {
            X509CertSelector certSelector = new X509CertSelector();
            //do not pass the full certificate to the selector, da just add some key values, this is much faster
            //RFC 5280: a certiciate is unique by the combination of issuer and serial number
            certSelector.setSerialNumber(this.getSerialNumberDECBigInt());
            certSelector.setIssuer(this.getIssuerX500Principal());
            CertPathBuilder pathBuilder = CertPathBuilder.getInstance("PKIX",
                    BouncyCastleProviderSingleton.instance().getName());
            PKIXBuilderParameters pkixParameter = new PKIXBuilderParameters(trustAnchors, certSelector);
            pkixParameter.setRevocationEnabled(false);
            //this is necessary for brainpool certificates, else the signature check will always fail under 
            //java 17 and above
            pkixParameter.setSigProvider(BouncyCastleProviderSingleton.instance().getName());
            //a value of 5 does not work for some certificates in Bouncycastle. 3 means Anchor + 3 certificate 
            //which should be fine
            pkixParameter.setMaxPathLength(3);
            //self signed certificates could be never part of the trust path, it makes no selse to check if
            //they are in the path beween the trust anchor and the end certificate
            List<X509Certificate> filteredCertificateList = new ArrayList<X509Certificate>();
            for (X509Certificate cert : possibleIntermediateCertificateList) {
                if (cert.getSubjectX500Principal().equals(cert.getIssuerX500Principal())) {
                    //this is a self signed certificate - it could be never used as intermediate certificate
                    //this also filters the root certificate which could be never an intermediate
                    continue;
                }
                //BasicConstraints OID
                byte[] basicConstraintsExtension = cert.getExtensionValue("2.5.29.19");
                if (basicConstraintsExtension == null) {
                    //this could be never an intermediate certificate, do not add it to the list
                    continue;
                }
                filteredCertificateList.add(cert);
            }
            CertStoreParameters storeParameter = new CollectionCertStoreParameters(filteredCertificateList);
            CertStore certStore = CertStore.getInstance("Collection", storeParameter,
                    BouncyCastleProviderSingleton.instance());
            pkixParameter.addCertStore(certStore);
            PKIXCertPathBuilderResult result = (PKIXCertPathBuilderResult) pathBuilder.build(pkixParameter);
            return (result);
        } catch (Throwable e) {
        }
        return (null);
    }

    /**
     * Validates the certificate and returns the trust anchor certificate if the
     * cert path is valid and the full path could be validated
     *
     * @return null if the certificate could not be trusted or an other failure
     * like nosuchalg exception etc occurs
     */
    public X509Certificate validateCertPath(KeyStore keystore, List<X509Certificate> certificateList) throws Exception {
        CertPath certPath = this.getPKIXCertPathBuilderResult(KeyStoreUtil.getTrustAnchors(keystore), certificateList).getCertPath();
        if (certPath == null) {
            return (null);
        }
        try {
            // Validator params
            PKIXParameters params = new PKIXParameters(keystore);
            //this is necessary for brainpool certificates, else the signature check will always fail under 
            //java 17 and above
            params.setSigProvider(BouncyCastleProviderSingleton.instance().getName());
            // Disable CRL checking since we are not supplying any CRLs
            params.setRevocationEnabled(false);
            CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX",
                    BouncyCastleProviderSingleton.instance().getName());
            CertPathValidatorResult result = certPathValidator.validate(certPath, params);
            // Get the CA used to validate this path
            PKIXCertPathValidatorResult pkixResult = (PKIXCertPathValidatorResult) result;
            TrustAnchor trustAnchor = pkixResult.getTrustAnchor();
            X509Certificate taCert = trustAnchor.getTrustedCert();
            return (taCert);
        } catch (Throwable e) {
        }
        return (null);
    }

    /**
     * Returns the CN entry of the subject or null if this is not set
     */
    public String getSubjectCN() {
        return (this.getSubjectEntryUnescaped("2.5.4.3"));
    }

    /**
     * Returns the CN entry of the issuer or null if this is not set
     */
    public String getIssuerCN() {
        return (this.getIssuerEntryUnescaped("2.5.4.3"));
    }

    /**
     * Returns the CN entry of the issuer or null if this is not set
     */
    public String getIssuerOrganization() {
        return (this.getIssuerEntryUnescaped("2.5.4.10"));
    }

    /**
     * Returns the CN entry of the subject or null if this is not set
     */
    public String getSubjectOrganization() {
        return (this.getSubjectEntryUnescaped("2.5.4.10"));
    }

    /**
     * Returns the CN entry of the subject or null if this is not set
     */
    public String getSubjectOU() {
        return (this.getSubjectEntryUnescaped("2.5.4.11"));
    }

    /**
     * This returns the unescaped(!) value of the subject entry
     *
     * @param oidStr
     * @return
     */
    private String getSubjectEntryUnescaped(String oidStr) {
        ASN1ObjectIdentifier identifier = new ASN1ObjectIdentifier(oidStr);
        X500Name x500Name = X500Name.getInstance(this.getSubjectX500Principal().getEncoded());
        for (RDN rdn : x500Name.getRDNs()) {
            for (AttributeTypeAndValue attributeAndValue : rdn.getTypesAndValues()) {
                ASN1ObjectIdentifier oid = attributeAndValue.getType();
                if (oid.equals(identifier)) {
                    return (attributeAndValue.getValue().toString());
                }
            }
        }
        return null;
    }

    /**
     * This returns the unescaped(!) value of the issuer entry
     *
     * @param oidStr
     * @return
     */
    private String getIssuerEntryUnescaped(String oidStr) {
        ASN1ObjectIdentifier identifier = new ASN1ObjectIdentifier(oidStr);
        X500Name x500Name = X500Name.getInstance(this.getIssuerX500Principal().getEncoded());
        for (RDN rdn : x500Name.getRDNs()) {
            for (AttributeTypeAndValue attributeAndValue : rdn.getTypesAndValues()) {
                ASN1ObjectIdentifier oid = attributeAndValue.getType();
                if (oid.equals(identifier)) {
                    return (attributeAndValue.getValue().toString());
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return (this.alias);
    }

    @Override
    /**
     * Sort a list of KeystoreCertificate You MUST not use the equals method in
     * this method - this will result in a "Comparison method violates its
     * general contract" IllegalArgument Exception The reason ist that the
     * implementor must also ensure that the relation is transitive:
     * (x.compareTo(y)>0 && y.compareTo(z)>0) implies x.compareTo(z)>0.
     */
    public int compareTo(KeystoreCertificate otherCert) {
        return (this.alias.toUpperCase().compareTo(otherCert.alias.toUpperCase()));
    }

    private String computeInfoText() {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
        StringBuilder infoTextBuilder = new StringBuilder();
        infoTextBuilder.append("Version: ").append(this.getVersion());
        if (this.isRootCertificate() || this.isCACertificate()) {
            if (this.isKeyPair) {
                infoTextBuilder.append(" (Root key)");
            } else {
                infoTextBuilder.append(" (Root certificate)");
            }
        }
        infoTextBuilder.append("\n")
                .append("Subject: ").append(this.getSubjectDN()).append("\n")
                .append("Issuer: ").append(this.getIssuerDN()).append("\n")
                .append("Serial (dec): ").append(this.getSerialNumberDEC()).append("\n")
                .append("Serial (hex): ").append(this.getSerialNumberHEX()).append("\n")
                .append("Valid from: ").append(format.format(this.getNotBefore())).append("\n")
                .append("Valid until: ").append(format.format(this.getNotAfter())).append("\n")
                .append("Public key: ");
        int publicKeyLength = this.getPublicKeyLength();
        infoTextBuilder.append(String.valueOf(publicKeyLength))
                .append(" ").append(this.getPublicKeyAlgorithm()).append("\n")
                .append("Signature algorithm: ").append(this.getSigAlgName()).append(" (OID ")
                .append(this.getSigAlgOID()).append(")\n");
        if (this.getPublicKeyAlgorithm().startsWith("EC")) {
            try {
                ECPublicKey publicKey = (ECPublicKey) this.getPublicKey();
                String oid = this.getCurveOID(publicKey);
                String curveName = this.getCurveName(publicKey);
                infoTextBuilder
                        .append("Named Curve: ")
                        .append(curveName).append(" [OID ")
                        .append(oid).append("]\n");
            } catch (Throwable ignore) {
                infoTextBuilder
                        .append("Named Curve:  Unknown\n");
            }
        }
        try {
            infoTextBuilder.append("Fingerprint (MD5): ").append(this.getFingerPrintMD5()).append("\n")
                    .append("Fingerprint (SHA-1): ").append(this.getFingerPrintSHA1()).append("\n")
                    .append("Fingerprint (SHA-256): ").append(this.getFingerPrintSHA256()).append("\n");
        } catch (Exception e) {
            infoTextBuilder.append("Fingerprint processing failed: ").append(e.getMessage());
        }
        return (infoTextBuilder.toString());
    }

    /**
     * Returns the curve OID if this is a EC key/certificate
     */
    private String getCurveOID(ECPublicKey publicKey) throws Throwable {
        AlgorithmParameters params = AlgorithmParameters.getInstance("EC");
        params.init(publicKey.getParams());
        return (params.getParameterSpec(ECGenParameterSpec.class).getName());
    }

    /**
     * Returns the curve OID if this is a EC key/certificate
     */
    public String getCurveName(ECPublicKey publicKey) throws Throwable {
        ECParameterSpec params = publicKey.getParams();
        //convert to BC spec
        org.bouncycastle.jce.spec.ECParameterSpec spec = EC5Util.convertSpec(params);
        Enumeration<?> ecNamedCurveTable = ECNamedCurveTable.getNames();
        while (ecNamedCurveTable.hasMoreElements()) {
            String name = ecNamedCurveTable.nextElement().toString();
            X9ECParameters possibleMatch = ECNamedCurveTable.getByName(name);
            if (possibleMatch != null) {
                if (spec.getN().equals(possibleMatch.getN())
                        && spec.getH().equals(possibleMatch.getH())
                        && spec.getCurve().equals(possibleMatch.getCurve())
                        && spec.getG().equals(possibleMatch.getG())) {
                    return name;
                }
            }
        }
        Enumeration<?> ecCustomNamedCurveTable = CustomNamedCurves.getNames();
        while (ecCustomNamedCurveTable.hasMoreElements()) {
            String name = ecCustomNamedCurveTable.nextElement().toString();
            X9ECParameters possibleMatch = CustomNamedCurves.getByName(name);
            if (possibleMatch != null) {
                if (spec.getN().equals(possibleMatch.getN())
                        && spec.getH().equals(possibleMatch.getH())
                        && spec.getCurve().equals(possibleMatch.getCurve())
                        && spec.getG().equals(possibleMatch.getG())) {
                    return name;
                }
            }
        }
        return ("");
    }

    /**
     * Returns a string that contains information about the certificate
     */
    public String getInfo() {
        String result = this.infoTextRef.get();
        if (result == null) {
            String computed = this.computeInfoText();
            if (this.infoTextRef.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = this.infoTextRef.get();
            }
        }
        //String is immutable, no copy required as return
        return result;
    }

    /**
     * Returns some information about the certificate extensions
     */
    public String getInfoExtension() {
        StringBuilder extensionText = new StringBuilder();
        List<String> crlURLList = getCRLDistributionPoints(this.certificate);
        for (int i = 0; i < crlURLList.size(); i++) {
            extensionText.append("CRL distribution[").append(String.valueOf(i + 1)).append("]: ").append(crlURLList.get(i)).append("\n");
        }
        List<String> ocspURLList = getOCSPURLs(this.certificate);
        for (int i = 0; i < ocspURLList.size(); i++) {
            extensionText.append("OCSP[").append(String.valueOf(i + 1)).append("]: ").append(ocspURLList.get(i)).append("\n");
        }
        List<String> alternativeNames = this.getSubjectAlternativeNames();
        if (!alternativeNames.isEmpty()) {
            extensionText.append("Subject alternative name: ").append(this.convertListToString(alternativeNames)).append("\n");
        }
        List<String> keyUsages = this.getKeyUsages();
        if (!keyUsages.isEmpty()) {
            extensionText.append("Key usage: ").append(this.convertListToString(keyUsages)).append("\n");
        }
        List<String> extkeyUsages = this.getExtendedKeyUsage();
        if (!extkeyUsages.isEmpty()) {
            extensionText.append("Extended key usage: ").append(this.convertListToString(extkeyUsages)).append("\n");
        }
        List<String> authorityKeyIdentifier = this.getAuthorityKeyIdentifier();
        if (!authorityKeyIdentifier.isEmpty()) {
            extensionText.append("Authority key identifier: ").append(this.convertListToString(authorityKeyIdentifier)).append("\n");
        }
        List<String> subjectKeyIdentifier = this.getSubjectKeyIdentifier();
        if (!subjectKeyIdentifier.isEmpty()) {
            extensionText.append("Subject key identifier: ").append(this.convertListToString(subjectKeyIdentifier)).append("\n");
        }
        List<String> policyList = this.getPolicy();
        if (!policyList.isEmpty()) {
            extensionText.append("Certificate policies: ").append(this.convertListToString(policyList)).append("\n");
        }
        return (extensionText.toString());
    }

    /**
     * Converts the arraylist content to a comma separated string
     */
    private String convertListToString(Collection<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String value : list) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(value);
        }
        return (builder.toString());
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
        if (anObject != null && anObject instanceof KeystoreCertificate) {
            KeystoreCertificate cert = (KeystoreCertificate) anObject;
            String otherFingerPrint;
            String ownFingerPrint;
            try {
                otherFingerPrint = cert.getFingerPrintSHA1();
                ownFingerPrint = this.getFingerPrintSHA1();
                return (otherFingerPrint.equals(ownFingerPrint));
            } catch (Exception e) {
                //unable to obtain the finger print. Use the serial number and the dates.
                return (cert.getIssuerDN().equals(this.getIssuerDN())
                        && cert.getNotAfter().equals(this.getNotAfter())
                        && cert.getNotBefore().equals(this.getNotBefore()));
            }
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.alias != null ? this.alias.hashCode() : 0);
        hash = 97 * hash + (this.certificate != null ? this.certificate.hashCode() : 0);
        hash = 97 * hash + (this.isKeyPair ? 1 : 0);
        return hash;
    }

    /**
     * @return the CertificateChain
     */
    public Certificate[] getCertificateChain() {
        return (this.certificateChain);
    }

    /**
     * Sets the entry to display mode. This allows to send it to a client
     * without sending private key information
     */
    public void setToDisplayMode() {
        //makes only sense for key entries - this will generate a dummy key for the display side.
        //The key itself will not be transported to the client
        if (this.isKeyPair) {
            this.privateKey = null;
        }
    }

    /**
     * Adds this entry to the passed parent JSON node
     */
    public void addToJSON(ArrayNode parent) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(ZoneOffset.UTC);
        ObjectNode certificateNode = parent.addObject();
        certificateNode.put("alias", this.alias);
        certificateNode.put("subject", this.getSubjectDN());
        certificateNode.put("issuer", this.getIssuerDN());
        certificateNode.put("notbefore", dateFormat.format(this.getNotBefore().toInstant()));
        certificateNode.put("notafter", dateFormat.format(this.getNotAfter().toInstant()));
        certificateNode.put("length", this.getPublicKeyLength());
        certificateNode.put("algorithm", this.getPublicKeyAlgorithm());
        PublicKey publicKey = this.getPublicKey();
        if (publicKey instanceof ECPublicKey) {
            try {
                certificateNode.put("curve", this.getCurveName((ECPublicKey) publicKey));
            } catch (Throwable e) {
            }
        }
        certificateNode.put("fingerprintsha1", this.getFingerPrintSHA1());
        certificateNode.put("fingerprintsha256", this.getFingerPrintSHA256());
        certificateNode.put("serialhex", this.getSerialNumberHEX());
        certificateNode.put("serialdec", this.getSerialNumberDEC());
        if (!this.getKeyUsages().isEmpty()) {
            ArrayNode usageNode = certificateNode.putArray("usages");
            ObjectNode extNode = usageNode.addObject();
            for (String ext : this.getKeyUsages()) {
                extNode.put("usage", ext);
            }
        }
        if (!this.getExtendedKeyUsage().isEmpty()) {
            ArrayNode extArrayNode = certificateNode.putArray("extusages");
            ObjectNode extNode = extArrayNode.addObject();
            for (String ext : this.getExtendedKeyUsage()) {
                extNode.put("extusage", ext);
            }
        }
        if (!this.getSubjectAlternativeNames().isEmpty()) {
            ArrayNode sanArrayNode = certificateNode.putArray("sanlist");
            ObjectNode sanNode = sanArrayNode.addObject();
            for (String san : this.getSubjectAlternativeNames()) {
                sanNode.put("san", san);
            }
        }
        String certificateBase64 = "ENCODING_ERROR";
        try {
            certificateBase64 = Base64.encode(this.getCertificateEncoded());
        } catch (Throwable e) {
        }
        certificateNode.put("certencoded", certificateBase64);
    }

    /**
     * Serializes this keystore certificate
     */
    public byte[] serialize() throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (DataOutputStream dataOut = new DataOutputStream(out)) {
                if (this.certificateChain != null) {
                    dataOut.writeBoolean(true);
                    dataOut.writeInt(this.certificateChain.length);
                    for (Certificate certificateOfChain : this.certificateChain) {
                        byte[] encoded = certificateOfChain.getEncoded();
                        dataOut.writeInt(encoded.length);
                        dataOut.write(encoded);
                    }
                } else {
                    //marker: empty chain
                    dataOut.writeBoolean(false);
                }
                //Single certificate
                if (this.certificate != null) {
                    dataOut.writeBoolean(true);
                    byte[] certBytes = this.getCertificateEncoded();
                    dataOut.writeInt(certBytes.length);
                    dataOut.write(certBytes);
                } else {
                    //marker: certificate is null
                    dataOut.writeBoolean(false);
                }
                //private key
                if (this.privateKey != null) {
                    dataOut.writeBoolean(true);
                    byte[] keyBytes = this.privateKey.getEncoded();
                    String algorithm = this.privateKey.getAlgorithm();
                    byte[] algoBytes = algorithm.getBytes(StandardCharsets.UTF_8);
                    dataOut.writeInt(algoBytes.length);
                    dataOut.write(algoBytes);
                    dataOut.writeInt(keyBytes.length);
                    dataOut.write(keyBytes);
                } else {
                    //marker: private key is null
                    dataOut.writeBoolean(false);
                }
                if (this.alias != null) {
                    dataOut.writeBoolean(true);
                    byte[] aliasBytes = this.alias.getBytes(StandardCharsets.UTF_8);
                    dataOut.writeInt(aliasBytes.length);
                    dataOut.write(aliasBytes);
                } else {
                    dataOut.writeBoolean(false);
                }
                //store if this is a key pair - the keystore certificate could be a keypair without
                //having a private key (display mode for the client)
                dataOut.writeBoolean(this.isKeyPair);
                //CRL related values
                dataOut.writeLong(this.crlStateValidUntil);
                dataOut.writeInt(this.lastCRLState);
            }
            return out.toByteArray();
        }
    }

    private static X509Certificate deserializeFromBytes(byte[] encodedCert) throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        try (InputStream in = new ByteArrayInputStream(encodedCert)) {
            return (X509Certificate) factory.generateCertificate(in);
        }
    }

    public static KeystoreCertificate deserialize(byte[] data) throws Exception {
        KeystoreCertificate keystoreCertificate = new KeystoreCertificate();
        try (ByteArrayInputStream in = new ByteArrayInputStream(data)) {
            try (DataInputStream dataIn = new DataInputStream(in)) {
                Certificate[] certificateChain = null;
                X509Certificate singleCertificate = null;
                PrivateKey privateKey = null;
                String alias = null;
                //certificate chain
                if (dataIn.readBoolean()) {
                    int count = dataIn.readInt();
                    certificateChain = new Certificate[count];
                    for (int i = 0; i < count; i++) {
                        int len = dataIn.readInt();
                        byte[] certBytes = dataIn.readNBytes(len);
                        certificateChain[i] = deserializeFromBytes(certBytes);
                    }
                }
                //Single certificate
                if (dataIn.readBoolean()) {
                    int len = dataIn.readInt();
                    byte[] certBytes = dataIn.readNBytes(len);
                    singleCertificate = deserializeFromBytes(certBytes);
                }
                //private key
                if (dataIn.readBoolean()) {
                    int algorithmLength = dataIn.readInt();
                    String algorithm = new String(dataIn.readNBytes(algorithmLength), StandardCharsets.UTF_8);
                    int keyLength = dataIn.readInt();
                    byte[] keyBytes = dataIn.readNBytes(keyLength);
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
                    try {
                        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
                        privateKey = keyFactory.generatePrivate(keySpec);
                    } catch (NoSuchAlgorithmException e) {
                        throw new Exception(
                                "Deserialization problem of KeystoreCertificate (Unsupported key algorithm): " + algorithm, e);
                    }
                }
                //alias 
                if (dataIn.readBoolean()) {
                    int aliasLength = dataIn.readInt();
                    byte[] aliasBytes = dataIn.readNBytes(aliasLength);
                    alias = new String(aliasBytes, StandardCharsets.UTF_8);
                }

                if (privateKey != null) {
                    keystoreCertificate.setPrivateKey(privateKey);
                }
                if (singleCertificate != null) {
                    keystoreCertificate.setCertificate(singleCertificate, certificateChain);
                }
                if (alias != null) {
                    keystoreCertificate.setAlias(alias);
                }
                //keypair or not
                keystoreCertificate.setIsKeyPair(dataIn.readBoolean());
                //CRL related values
                keystoreCertificate.setCRLStateValidUntil(dataIn.readLong());
                keystoreCertificate.setLastCRLState(dataIn.readInt());
            }
        }
        return (keystoreCertificate);
    }

    /**
     * Performs a validity check on the certificate based on the configured
     * validity settings. This will return a bit array that contains problems or
     * the value CertificateValiditySettings.STATE_OK if everything is ok with
     * this certificate.
     *
     * @return A combination of short bit flags (STATUS_XXX) indicating all
     * failures found. STATUS_OK (0x0000) if valid. The status codes are defined
     * in CertificateValiditySettings
     * @param requestedOperationFlag Indicates the usage this certificate should
     * be used for, one of CertificateValiditySettigs.OPERATION_CRYPT etc
     * @param requiredChecksBitfield The required checks bitfield as defined in
     * the class CertificateValiditySettings
     *
     */
    public int getValidityValue(int requestedOperationFlag, int requiredChecksBitfield) {
        if (requiredChecksBitfield == CertificateValiditySettings.CHECK_NONE) {
            return (CertificateValiditySettings.STATE_OK);
        }
        int validityStatusValue = CertificateValiditySettings.STATE_OK;
        //date check
        if ((requiredChecksBitfield & CertificateValiditySettings.CHECK_VALIDITY_DATE) != 0) {
            Date now = new Date();
            if (now.before(this.getNotBefore()) || now.after(this.getNotAfter())) {
                validityStatusValue |= CertificateValiditySettings.STATE_DATE_INVALID;
            }
        }
        //min key size - only for RSA, ignore for EC
        if ((requiredChecksBitfield & CertificateValiditySettings.CHECK_MIN_KEY_SIZE) != 0) {
            if (!this.getPublicKeyAlgorithm().startsWith("EC")) {
                final int MIN_KEY_SIZE = 2048;
                int keySize = this.getPublicKeyLength();
                if (keySize < MIN_KEY_SIZE) {
                    validityStatusValue |= CertificateValiditySettings.STATE_KEY_SIZE_TOO_SMALL;
                }
            }
        }
        //check if this is a mendelson public certificate/key
        if ((requiredChecksBitfield & CertificateValiditySettings.CHECK_MENDELSON_PUBLIC_CERT) != 0) {
            if (CertificateValiditySettings.MENDELSON_PUBLIC_CERTIFICATES_FINGERPRINTS.contains(this.getFingerPrintSHA1())) {
                validityStatusValue |= CertificateValiditySettings.STATE_MENDELSON_PUBLIC_CERT;
            }
        }
        //certificate usage checks if this is not self signed
        if (requestedOperationFlag != CertificateValiditySettings.OPERATION_ANY) {
            if (!this.isSelfSigned()
                    && (requiredChecksBitfield & CertificateValiditySettings.CHECK_USAGE_MISMATCH) != 0) {
                if ((requestedOperationFlag & CertificateValiditySettings.OPERATION_CRYPT) != 0
                        && !this.isEncryptionUsageAllowed()) {
                    validityStatusValue |= CertificateValiditySettings.STATE_USAGE_MISMATCH_CRYPT;
                }
                if ((requestedOperationFlag & CertificateValiditySettings.OPERATION_SIGN) != 0
                        && !this.isSignatureUsageAllowed()) {
                    validityStatusValue |= CertificateValiditySettings.STATE_USAGE_MISMATCH_SIGN;
                }
                if ((requestedOperationFlag & CertificateValiditySettings.OPERATION_TLS) != 0
                        && !this.isTLSUsageAllowed()) {
                    validityStatusValue |= CertificateValiditySettings.STATE_USAGE_MISMATCH_TLS;
                }
            }
        }
        //Perform CRL check if this is not self signed
        if (!this.isSelfSigned()
                && (requiredChecksBitfield & CertificateValiditySettings.CHECK_CRL) != 0) {
            validityStatusValue |= this.lastCRLState;            
        }
        return (validityStatusValue);
    }

    /**
     * Performs a validity check on the certificate based on the configured
     * validity settings. This will throw an CertificateValidityException if the
     * state is != CertificateValiditySettings.STATE_OK. To get just the
     * validity value please use the method getValitityValue(..)
     *
     * @return A combination of short bit flags (STATUS_XXX) indicating all
     * failures found. STATUS_OK (0x0000) if valid. The status codes are defined
     * in CertificateValiditySettings
     * @param requestedOperationFlag Indicates the usage this certificate should
     * be used for, one of CertificateValiditySettigs.OPERATION_CRYPT etc
     * @param requiredChecks The required checks bitfield as defined in the
     * class CertificateValiditySettings
     * @throws CertificateValidityException if the certificate validity is not
     * CertificateValiditySettings.STATE_OK
     */
    public void checkValidity(int requestedOperationFlag, int requiredChecks)
            throws CertificateValidityException {
        int validityStatus = this.getValidityValue(requestedOperationFlag, requiredChecks);
        if (validityStatus != CertificateValiditySettings.STATE_OK) {
            throw new CertificateValidityException(validityStatus, this.alias, this.getFingerPrintSHA1());
        }
    }

    /**
     * Checks if the Key Usage allows for encryption purposes: Key Encipherment
     * (bit 2) OR Data Encipherment (bit 3).
     *
     * @return true if allowed, false otherwise.
     */
    private boolean isEncryptionUsageAllowed() {
        boolean[] keyUsage = this.certificate.getKeyUsage();
        // If the Key Usage extension is absent, assume it is unrestricted (true).
        if (keyUsage == null) {
            return true;
        }
        // Check Key Encipherment (bit 2) or Data Encipherment (bit 3)
        boolean keyEncipherment = (keyUsage.length > 2) && keyUsage[2];
        boolean dataEncipherment = (keyUsage.length > 3) && keyUsage[3];
        return keyEncipherment || dataEncipherment;
    }

    /**
     * Checks if the Key Usage allows for signature purposes: Digital Signature
     * (bit 0) OR Non Repudiation (bit 1).
     *
     * @return true if allowed, false otherwise.
     */
    private boolean isSignatureUsageAllowed() {
        boolean[] keyUsage = this.certificate.getKeyUsage();
        // If the Key Usage extension is absent, assume it is unrestricted (true).
        if (keyUsage == null) {
            return true;
        }
        // Check Digital Signature (bit 0) or Non Repudiation (bit 1)
        boolean digitalSignature = (keyUsage.length > 0) && keyUsage[0];
        boolean nonRepudiation = (keyUsage.length > 1) && keyUsage[1];
        return digitalSignature || nonRepudiation;
    }

    /**
     * Checks if the Extended Key Usage allows for TLS purposes: Webserver
     * authentication OR Client authentication.
     *
     * @return true if allowed, false otherwise.
     */
    private boolean isTLSUsageAllowed() {
        List<String> extendedUsage = this.getExtendedKeyUsage();
        // If the Extended Key Usage extension is absent, assume it is unrestricted (true).
        // Note: Some security policies require EKU to be present if key usage is restricted.
        if (extendedUsage == null || extendedUsage.isEmpty()) {
            return true;
        }
        // Check for the standard TLS EKU names (based on your map)
        final String SERVER_AUTH = "Webserver authentication";
        final String CLIENT_AUTH = "Client authentication";

        return extendedUsage.contains(SERVER_AUTH) || extendedUsage.contains(CLIENT_AUTH);
    }

    /**
     * @return the crlStateValidUntil
     */
    public long getCRLStateValidUntil() {
        return crlStateValidUntil;
    }

    /**
     * @param crlStateValidUntil the crlStateValidUntil to set
     */
    public void setCRLStateValidUntil(long crlStateValidUntil) {
        this.crlStateValidUntil = crlStateValidUntil;
    }

    /**
     * @return the lastCRLState
     */
    public int getLastCRLState() {
        return lastCRLState;
    }

    /**
     * @param lastCRLState the lastCRLState to set
     */
    public void setLastCRLState(int lastCRLState) {
        this.lastCRLState = lastCRLState;
    }

    /**Sets both states in one call for better sync*/
    public void setCRLStates( long crlStateValidUntil, int lastCRLState){
        this.lastCRLState = lastCRLState;
        this.crlStateValidUntil = crlStateValidUntil;
    }
    
}
