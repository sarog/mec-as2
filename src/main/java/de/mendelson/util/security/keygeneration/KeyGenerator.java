//$Header: /as2/de/mendelson/util/security/keygeneration/KeyGenerator.java 23    2/11/23 14:03 Heller $
package de.mendelson.util.security.keygeneration;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.security.spec.ECParameterSpec;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * This class allows to generate a private key
 *
 * @author S.Heller
 * @version $Revision: 23 $
 */
public class KeyGenerator {

    public static final String KEYALGORITHM_DSA = "DSA";
    public static final String KEYALGORITHM_RSA = "RSA";
    public static final String KEYALGORITHM_ECDSA = "ECDSA";
    public static final String SIGNATUREALGORITHM_SHA256_WITH_RSA = "SHA256withRSA";
    public static final String SIGNATUREALGORITHM_SHA512_WITH_RSA = "SHA512withRSA";
    public static final String SIGNATUREALGORITHM_SHA256_WITH_RSA_RSASSA_PSS = "SHA256withRSAAndMGF1";
    public static final String SIGNATUREALGORITHM_SHA512_WITH_RSA_RSASSA_PSS = "SHA512withRSAAndMGF1";
    public static final String SIGNATUREALGORITHM_SHA1_WITH_RSA = "SHA1WithRSA";
    public static final String SIGNATUREALGORITHM_MD5_WITH_RSA = "MD5WithRSA";
    public static final String SIGNATUREALGORITHM_SHA256_WITH_ECDSA = "SHA256WithECDSA";
    public static final String SIGNATUREALGORITHM_SHA384_WITH_ECDSA = "SHA384WithECDSA";
    public static final String SIGNATUREALGORITHM_SHA512_WITH_ECDSA = "SHA512WithECDSA";
    public static final String SIGNATUREALGORITHM_SHA3_256_WITH_ECDSA = "SHA3-256WithECDSA";
    public static final String SIGNATUREALGORITHM_SHA3_384_WITH_ECDSA = "SHA3-384WithECDSA";
    public static final String SIGNATUREALGORITHM_SHA3_512_WITH_ECDSA = "SHA3-512WithECDSA";
    public static final String SIGNATUREALGORITHM_SHA3_256_WITH_RSA = "SHA3-256withRSA";
    public static final String SIGNATUREALGORITHM_SHA3_512_WITH_RSA = "SHA3-512withRSA";
    public static final String SIGNATUREALGORITHM_SHA3_256_WITH_RSA_RSASSA_PSS = "SHA3-256withRSAAndMGF1";
    public static final String SIGNATUREALGORITHM_SHA3_512_WITH_RSA_RSASSA_PSS = "SHA3-512withRSAAndMGF1";

    public static final String CURVE_NAME_ED25519 = "Ed25519";

    /**
     * Creates a new instance of KeyGenerator
     */
    public KeyGenerator() {
    }

    /**
     * Generate a key pair.
     *
     */
    public KeyGenerationResult generateKeyPair(KeyGenerationValues generationValues) throws Exception {
        //generation keypair
        SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
        KeyPair keyPair;
        KeyPairGenerator keyPairGen;
        if (generationValues.getKeyAlgorithm().startsWith("EC")) {
            if (generationValues.getECNamedCurve().equalsIgnoreCase(CURVE_NAME_ED25519)) {
                keyPairGen = KeyPairGenerator.getInstance(CURVE_NAME_ED25519);
                keyPair = keyPairGen.generateKeyPair();
            } else {
                ECNamedCurveParameterSpec curveParams = ECNamedCurveTable.getParameterSpec(generationValues.getECNamedCurve());
                ECParameterSpec ecParameterSpec = new ECNamedCurveSpec(curveParams.getName(),
                        curveParams.getCurve(),
                        curveParams.getG(),
                        curveParams.getN());
                keyPairGen
                        = KeyPairGenerator.getInstance(generationValues.getKeyAlgorithm());
                keyPairGen.initialize(ecParameterSpec, rand);
                keyPair = keyPairGen.generateKeyPair();
            }
        } else {
            keyPairGen
                    = KeyPairGenerator.getInstance(generationValues.getKeyAlgorithm());
            keyPairGen.initialize(generationValues.getKeySize(), rand);
            keyPair = keyPairGen.generateKeyPair();
        }
        X509Certificate certificate = this.generateCertificate(generationValues, keyPair);
        KeyGenerationResult result = new KeyGenerationResult(keyPair, certificate);
        return (result);
    }

    /**
     *
     * @param generationValues
     * @param keyPair
     * @return
     * @throws Exception
     */
    private X509Certificate generateCertificate(KeyGenerationValues generationValues, KeyPair keyPair) throws Exception {
        SubjectPublicKeyInfo publicKeyInformation = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
        PrivateKey privateKey = keyPair.getPrivate();
        JcaContentSignerBuilder builder;
        if (generationValues.getECNamedCurve() != null && generationValues.getECNamedCurve().equals(CURVE_NAME_ED25519)) {
            builder = new JcaContentSignerBuilder(CURVE_NAME_ED25519)
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME);
        } else {
            builder = new JcaContentSignerBuilder(generationValues.getSignatureAlgorithm())
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME);
        }
        ContentSigner signer = builder.build(privateKey);
        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append("CN=").append(replace(generationValues.getCommonName(), ",", "\\,"));
        nameBuilder.append(",OU=").append(replace(generationValues.getOrganisationUnit(), ",", "\\,"));
        nameBuilder.append(",O=").append(replace(generationValues.getOrganisationName(), ",", "\\,"));
        nameBuilder.append(",L=").append(replace(generationValues.getLocalityName(), ",", "\\,"));
        nameBuilder.append(",ST=").append(replace(generationValues.getStateName(), ",", "\\,"));
        nameBuilder.append(",C=").append(replace(generationValues.getCountryCode(), ",", "\\,"));
        nameBuilder.append(",E=").append(replace(generationValues.getEmailAddress(), ",", "\\,"));
        X500Name issuerName = new X500Name(nameBuilder.toString());
        X500Name subjectName = new X500Name(nameBuilder.toString());
        Date startDate = new Date(System.currentTimeMillis());
        long duration = TimeUnit.DAYS.toMillis(generationValues.getKeyValidInDays());
        Date endDate = new Date(startDate.getTime() + duration);
        BigInteger serialNumber = new BigInteger(Long.toString(System.currentTimeMillis() / 1000));
        X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(
                issuerName, serialNumber, startDate, endDate, subjectName, publicKeyInformation);
        //add a key extension if this is requested
        if (generationValues.getKeyExtension() != null) {
            certificateBuilder.addExtension(Extension.keyUsage, true, generationValues.getKeyExtension());
        }
        //add a extended key extension if this is requested
        if (generationValues.getExtendedKeyExtension() != null) {
            certificateBuilder.addExtension(Extension.extendedKeyUsage, false, generationValues.getExtendedKeyExtension());
        }
        //add SKI
        if (generationValues.generateSKI()) {
            SubjectKeyIdentifier subjectKeyIdentifier = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(keyPair.getPublic());
            certificateBuilder.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);
        }
        //add subject alternative names
        if (!generationValues.getSubjectAlternativeNames().isEmpty()) {
            GeneralName[] generalNamesArray = new GeneralName[generationValues.getSubjectAlternativeNames().size()];
            generationValues.getSubjectAlternativeNames().toArray(generalNamesArray);
            certificateBuilder.addExtension(Extension.subjectAlternativeName, false, new GeneralNames(generalNamesArray));
        }
        X509CertificateHolder certificateHolder = certificateBuilder.build(signer);
        X509Certificate certificate = new JcaX509CertificateConverter()
                .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                .getCertificate(certificateHolder);
        return (certificate);
    }

    /**
     * Replaces the string tag by the string replacement in the sourceString
     *
     * @param source Source string
     * @param tag	String that will be replaced
     * @param replacement String that will replace the tag
     * @return String that contains the replaced values
     */
    private static String replace(String source, String tag, String replacement) {
        if (source == null) {
            return null;
        }
        StringBuilder buffer = new StringBuilder();
        while (true) {
            int index = source.indexOf(tag);
            if (index == -1) {
                buffer.append(source);
                return (buffer.toString());
            }
            buffer.append(source.substring(0, index));
            buffer.append(replacement);
            source = source.substring(index + tag.length());
        }
    }

    public static String signatureAlgorithmToDisplay(final String signatureAlgorithm) {
        if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_MD5_WITH_RSA)) {
            return ("MD5");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA1_WITH_RSA)) {
            return ("SHA-1");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA256_WITH_ECDSA)) {
            return ("SHA-2 256");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA384_WITH_ECDSA)) {
            return ("SHA-2 384");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA512_WITH_ECDSA)) {
            return ("SHA-2 512");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA3_256_WITH_ECDSA)) {
            return ("SHA-3 256");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA3_384_WITH_ECDSA)) {
            return ("SHA-3 384");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA3_512_WITH_ECDSA)) {
            return ("SHA-3 512");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA256_WITH_RSA)) {
            return ("SHA-2 256");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA256_WITH_RSA_RSASSA_PSS)) {
            return ("SHA-2 256 (RSASSA-PSS)");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA512_WITH_RSA)) {
            return ("SHA-2 512");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA512_WITH_RSA_RSASSA_PSS)) {
            return ("SHA-2 512 (RSASSA-PSS)");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA3_256_WITH_RSA)) {
            return ("SHA-3 256");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA3_256_WITH_RSA_RSASSA_PSS)) {
            return ("SHA-3 256 (RSASSA-PSS)");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA3_512_WITH_RSA)) {
            return ("SHA-3 512");
        } else if (signatureAlgorithm.equalsIgnoreCase(KeyGenerator.SIGNATUREALGORITHM_SHA3_512_WITH_RSA_RSASSA_PSS)) {
            return ("SHA-3 512 (RSASSA-PSS)");
        }
        return (signatureAlgorithm);
    }

}
