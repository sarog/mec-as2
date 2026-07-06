//$Header: /as4/de/mendelson/util/security/crl/CRLVerification.java 21    14/01/26 16:20 Heller $
package de.mendelson.util.security.crl;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPURL;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.security.BouncyCastleProviderSingleton;
import de.mendelson.util.security.cert.KeystoreCertificate;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.ProxySelector;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.bouncycastle.asn1.DEROctetString;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.ocsp.OCSPResponseStatus;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.bouncycastle.cert.ocsp.OCSPReqBuilder;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.bouncycastle.cert.ocsp.RevokedStatus;
import org.bouncycastle.cert.ocsp.SingleResp;
import org.bouncycastle.cert.ocsp.UnknownStatus;
import org.bouncycastle.cert.ocsp.jcajce.JcaCertificateID;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Verifies a CRL of a certificate. Supports HTTP and LDAP downloads of CRLs,
 * also CRL verification via OCSP
 *
 * @author S.Heller
 * @version $Revision: 21 $
 */
public class CRLVerification {

    private static final MecResourceBundle rb;
    private static final String MODULE_NAME;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleCRL.class.getName());
            MODULE_NAME = rb.getResourceString("module.name");
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    public CRLVerification() {
    }

    /**
     * Checks the certificate and returns the CRLRevocationInformation. The
     * CRLRevocationInformation contains a log line that describes the state of
     * the process
     *
     * @param certificate The certificate to test
     * @param issuerCertificate the parent certificate of the certificate to
     * test or null if it does not exist
     * @return
     */
    public CRLRevocationInformation checkCertificate(KeystoreCertificate certificate, X509Certificate issuerCertificate) {
        CRLRevocationState revocationState;
        long nextUpdate = 0L;
        StringBuilder builder = new StringBuilder();
        builder.append("[")
                .append(certificate.getAlias())
                .append("] ");
        if (certificate.isSelfSigned() && !certificate.isCACertificate()) {
            //self signed certificate could not be revoked
            revocationState
                    = new CRLRevocationState(CRLRevocationState.STATE_OK,
                            rb.getResourceString("self.signed.skipped"));
            //no need to check this again, its fine until the certificate expires
            nextUpdate = certificate.getX509Certificate().getNotAfter().getTime();
        } else if (certificate.isSelfSigned() && certificate.isCACertificate()) {
            //if this is a trust anchor/ca it has been imported by purpose - means to trust
            //it. In most CA root certificates there is no CRL mechanism included because of this reason
            revocationState
                    = new CRLRevocationState(CRLRevocationState.STATE_OK,
                            rb.getResourceString("ca.skipped"));
            //no need to check this again, its fine until the certificate expires
            nextUpdate = certificate.getX509Certificate().getNotAfter().getTime();
        } else {
            try {
                nextUpdate = this.checkX509Certificate(certificate, issuerCertificate);
                DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
                if (System.currentTimeMillis() > nextUpdate) {
                    revocationState = new CRLRevocationState(CRLRevocationState.STATE_CRL_EXPIRED,
                            rb.getResourceString("crl.expired", format.format(new Date(nextUpdate))));
                } else {
                    revocationState = new CRLRevocationState(CRLRevocationState.STATE_OK,
                            rb.getResourceString("crl.success", format.format(new Date(nextUpdate))));
                }
            } catch (CRLVerificationException ex) {
                revocationState = ex.getRevocationState();
            } catch (Throwable e) {
                revocationState
                        = new CRLRevocationState(CRLRevocationState.STATE_CRL_OTHER_PROBLEM,
                                "[" + e.getClass().getSimpleName() + "] " + e.getMessage());
            }
        }
        builder.append(revocationState.getDetails());
        return (new CRLRevocationInformation(revocationState, certificate.getFingerPrintSHA1(),
                MODULE_NAME + " " + builder.toString(),
                nextUpdate));
    }

    /**
     * Performs the certificate CRL check and returns the timestamp in ms until
     * when this state is valid
     *
     * @param certificate
     * @return
     * @throws CRLVerificationException
     */
    private long checkX509Certificate(KeystoreCertificate keystoreCertificate,
            X509Certificate issuerCertificate) throws CRLVerificationException {
        X509Certificate certificate = keystoreCertificate.getX509Certificate();
        List<String> crlDistributionPointList = KeystoreCertificate.getCRLDistributionPoints(certificate);
        List<String> ocspURLList = KeystoreCertificate.getOCSPURLs(certificate);
        //an issuer is always required, else the result/signature could not be verified
        if (issuerCertificate == null) {
            CRLRevocationState state = new CRLRevocationState(CRLRevocationState.STATE_CRL_ISSUER_MISSING,
                    rb.getResourceString("error.issuercertificate.required"));
            throw new CRLVerificationException(state);
        }
        //no CRL list - try OCSP
        if (crlDistributionPointList.isEmpty() && !ocspURLList.isEmpty()) {
            return (this.verifyOCSP(ocspURLList, certificate, issuerCertificate));
        }
        long nextUpdateMillis = -1;
        CRLVerificationException storedException = null;
        if (crlDistributionPointList.isEmpty()) {
            CRLRevocationState state = new CRLRevocationState(
                    CRLRevocationState.STATE_CRL_UNABLE_TO_EXTRACT_CRL_URL,
                    rb.getResourceString("error.url.retrieve"));
            throw new CRLVerificationException(state);
        }
        for (String crlURL : crlDistributionPointList) {
            try {
                X509CRL crl;
                if (crlURL.startsWith("ldap")) {
                    crl = this.downloadCRLFromLDAP(crlURL);
                } else if (crlURL.startsWith("http")) {
                    crl = this.downloadCRLFromWeb(crlURL);
                } else {
                    CRLRevocationState state = new CRLRevocationState(CRLRevocationState.STATE_CRL_MALFORMED_URL,
                            rb.getResourceString("error.malformed.url", crlURL));
                    if (storedException == null) {
                        storedException = new CRLVerificationException(state);
                    }
                    //this crl is invalid, try the next one
                    continue;
                }
                //perform the signature check of the crl. This is why the issuer certificate always have to be != null
                try {
                    //this might fail for EC or EC/Brainpool
                    crl.verify(issuerCertificate.getPublicKey());
                } catch (Exception e) {
                    try {
                        crl.verify(issuerCertificate.getPublicKey(), BouncyCastleProviderSingleton.instance());
                    } catch (Exception ex) {
                        CRLRevocationState state = new CRLRevocationState(
                                CRLRevocationState.STATE_CRL_INVALID_SIGNATURE,
                                rb.getResourceString("error.invalid.signature"));
                        if (storedException == null) {
                            storedException = new CRLVerificationException(state);
                        }
                        //this crl is invalid, try the next one
                        continue;
                    }
                }
                Date foundNextUpdateDate = crl.getNextUpdate();
                if (foundNextUpdateDate != null) {
                    if (foundNextUpdateDate.getTime() > nextUpdateMillis) {
                        nextUpdateMillis = foundNextUpdateDate.getTime();
                    }
                } else {
                    CRLRevocationState state = new CRLRevocationState(CRLRevocationState.STATE_CRL_OTHER_PROBLEM,
                            rb.getResourceString("error.nextupdate.missing"));
                    if (storedException == null) {
                        storedException = new CRLVerificationException(state);
                    }
                    continue;
                }
                X509CRLEntry revokedEntry = crl.getRevokedCertificate(certificate);
                if (revokedEntry != null) {
                    CRLRevocationState state
                            = new CRLRevocationState(CRLRevocationState.STATE_CRL_REVOKED,
                                    rb.getResourceString(
                                            "failed.revoked",
                                            revokedEntry.getRevocationReason().toString()));
                    throw new CRLVerificationException(state);
                }
            } catch (CRLVerificationException e) {
                if (storedException == null) {
                    storedException = e;
                }
            } catch (Exception e) {
                String message = "[" + e.getClass().getSimpleName() + "] " + e.getMessage();
                CRLRevocationState state = new CRLRevocationState(CRLRevocationState.STATE_CRL_OTHER_PROBLEM,
                        rb.getResourceString("error.other.problem", message));
                if (storedException == null) {
                    storedException = new CRLVerificationException(state);
                }
            }
        }
        if (storedException != null) {
            throw storedException;
        }
        if (nextUpdateMillis == -1) {
            CRLRevocationState state
                    = new CRLRevocationState(CRLRevocationState.STATE_CRL_OTHER_PROBLEM,
                            rb.getResourceString("error.nextupdate.missing"));
            throw new CRLVerificationException(state);
        }
        return (nextUpdateMillis);
    }

    /**
     * Downloads CRL from the crlUrl. Does not support HTTPS - just because CRL
     * URLS using HTTPs is bad practice
     */
    private X509CRL downloadCRLFromWeb(String crlURL) throws CRLVerificationException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .proxy(ProxySelector.getDefault())
                //01/2026: HTTP 1.1 is required for some CAs, e.g. godaddy
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        try {
            //sometimes there are CR/LF in the URI, this deletes them
            URI uri = URI.create(crlURL.trim());
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .build();
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                CRLRevocationState state = new CRLRevocationState(
                        CRLRevocationState.STATE_CRL_DOWNLOAD_FAILED,
                        rb.getResourceString("download.failed.from",
                                new Object[]{
                                    crlURL + " (HTTP " + response.statusCode() + ")"
                                }));
                throw new CRLVerificationException(state);
            }
            byte[] crlBytes = response.body();
            try (InputStream crlStream = new ByteArrayInputStream(crlBytes)) {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                X509CRL crl = (X509CRL) certificateFactory.generateCRL(crlStream);
                return crl;
            }
        } catch (MalformedURLException | IllegalArgumentException e) {
            CRLRevocationState state = new CRLRevocationState(
                    CRLRevocationState.STATE_CRL_MALFORMED_URL,
                    rb.getResourceString("malformed.crl.url", crlURL));
            throw new CRLVerificationException(state, e);
        } catch (UnknownHostException | NoRouteToHostException | SocketTimeoutException e) {
            CRLRevocationState state = new CRLRevocationState(
                    CRLRevocationState.STATE_CRL_NOT_REACHABLE,
                    rb.getResourceString("error.not.reachable", crlURL));
            throw new CRLVerificationException(state, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            CRLRevocationState state = new CRLRevocationState(
                    CRLRevocationState.STATE_CRL_OTHER_PROBLEM, "Thread interrupted");
            throw new CRLVerificationException(state, e);
        } catch (IOException e) {
            CRLRevocationState state = new CRLRevocationState(
                    CRLRevocationState.STATE_CRL_DOWNLOAD_FAILED,
                    rb.getResourceString("download.failed.from", crlURL));
            throw new CRLVerificationException(state, e);
        } catch (CRLException | CertificateException e) {
            CRLRevocationState state = new CRLRevocationState(
                    CRLRevocationState.STATE_CRL_IN_BAD_FORMAT,
                    rb.getResourceString("bad.crl"));
            throw new CRLVerificationException(state, e);
        } catch (Throwable e) {
            CRLRevocationState state = new CRLRevocationState(
                    CRLRevocationState.STATE_CRL_OTHER_PROBLEM,
                    "[" + e.getClass().getSimpleName() + "] " + e.getMessage());
            throw new CRLVerificationException(state, e);
        }
    }

    /**
     * Downloads a CRL from given LDAP url, e.g.
     * ldap://ldap.infoldap.de/dc=identity-ca,dc=infonotary,dc=com?certificateRevocationList
     *
     */
    private X509CRL downloadCRLFromLDAP(String ldapURLStr) throws CRLVerificationException {
        LDAPURL ldapURL;
        try {
            ldapURL = new LDAPURL(ldapURLStr);
        } catch (Exception e) {
            CRLRevocationState state
                    = new CRLRevocationState(CRLRevocationState.STATE_CRL_MALFORMED_URL,
                            rb.getResourceString("malformed.crl.url", e.getMessage()));
            throw new CRLVerificationException(state);
        }
        if (!ldapURL.hostProvided()) {
            CRLRevocationState state
                    = new CRLRevocationState(CRLRevocationState.STATE_CRL_MALFORMED_URL,
                            rb.getResourceString("malformed.crl.url", "No host provided in LDAP URL"));
            throw new CRLVerificationException(state);
        }
        String ldapHost = ldapURL.getHost();
        //389 raw connection or STARTTLS connection
        //636 LDAPS connection, TLS
        int ldapPort = ldapURL.getPort();
        SearchScope searchScope = ldapURL.getScope();
        String baseDN = ldapURL.getBaseDN().toString();
        Filter filter = ldapURL.getFilter();
        String searchAttributeName;
        if (!ldapURL.attributesProvided()) {
            CRLRevocationState state
                    = new CRLRevocationState(CRLRevocationState.STATE_CRL_MALFORMED_URL,
                            rb.getResourceString("malformed.crl.url", "No attributes provided in LDAP URL"));
            throw new CRLVerificationException(state);
        } else {
            searchAttributeName = ldapURL.getAttributes()[0];
        }
        try (LDAPConnection connection = new LDAPConnection(ldapHost, ldapPort)) {
            SearchResult searchResult = connection.search(
                    baseDN,
                    searchScope,
                    filter
            );
            if (searchResult.getSearchEntries() != null && !searchResult.getSearchEntries().isEmpty()) {
                //get all possible attributes                
                for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                    Collection<Attribute> attributes = entry.getAttributes();
                    for (Attribute listAttribute : attributes) {
                        if (searchAttributeName.equalsIgnoreCase(listAttribute.getBaseName())) {
                            byte[] crlBytes = entry.getAttributeValueBytes(listAttribute.getName());
                            if (crlBytes == null || crlBytes.length == 0) {
                                CRLRevocationState state
                                        = new CRLRevocationState(CRLRevocationState.STATE_CRL_DOWNLOAD_FAILED,
                                                rb.getResourceString("download.failed.from", ldapURL));
                                throw new CRLVerificationException(state);
                            }
                            try (InputStream inStream = new ByteArrayInputStream(crlBytes)) {
                                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                                return (X509CRL) certificateFactory.generateCRL(inStream);
                            }
                        }
                    }
                }
            }
        } catch (LDAPException e) {
            CRLRevocationState state = new CRLRevocationState(CRLRevocationState.STATE_CRL_NOT_REACHABLE,
                    rb.getResourceString("error.not.reachable", ldapURLStr));
            throw new CRLVerificationException(state, e);
        } catch (CertificateException | IOException e) {
            CRLRevocationState state = new CRLRevocationState(CRLRevocationState.STATE_CRL_IN_BAD_FORMAT,
                    rb.getResourceString("bad.crl"));
            throw new CRLVerificationException(state, e);
        } catch (Throwable e) {
            CRLRevocationState state
                    = new CRLRevocationState(CRLRevocationState.STATE_CRL_OTHER_PROBLEM,
                            "[" + e.getClass().getSimpleName() + "] " + e.getMessage());
            throw new CRLVerificationException(state);
        }
        CRLRevocationState state
                = new CRLRevocationState(CRLRevocationState.STATE_CRL_OTHER_PROBLEM,
                        "Attribute " + searchAttributeName + " not found on LDAP server");
        throw new CRLVerificationException(state);
    }

    /**
     * Complete OCSP Verification.
     *
     * @param certificate The certificate to check - only the serial number is
     * taken
     * @param issuerCertificate This is the certificate that signed the
     * certificate to test - mainly the intermediate certificate and if this
     * does not exist the root certificate
     * @return The timestamp in milisecs how long the current state is valid
     */
    private long verifyOCSP(List<String> ocspURLList,
            X509Certificate certificate,
            X509Certificate issuerCertificate) throws CRLVerificationException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .proxy(ProxySelector.getDefault())
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        OCSPReq ocspRequest;
        //Use a random nonce to ensure response freshness and prevent replay attacks where an 
        //old good status is resent by an attacker.
        byte[] nonce = new byte[16];
        new SecureRandom().nextBytes(nonce);
        try {
            DigestCalculator digestCalculator
                    = new JcaDigestCalculatorProviderBuilder()
                            .setProvider(BouncyCastleProviderSingleton.instance())
                            .build().get(CertificateID.HASH_SHA1);
            CertificateID certId = new JcaCertificateID(
                    digestCalculator, issuerCertificate, certificate.getSerialNumber());
            OCSPReqBuilder requestBuilder = new OCSPReqBuilder();
            requestBuilder.addRequest(certId);
            requestBuilder.setRequestExtensions(
                    new Extensions(
                            new Extension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce, false,
                                    new DEROctetString(nonce))
                    )
            );
            ocspRequest = requestBuilder.build();
        } catch (Throwable e) {
            throw new CRLVerificationException(
                    new CRLRevocationState(
                            CRLRevocationState.STATE_CRL_OTHER_PROBLEM,
                            rb.getResourceString("error.request.generation",
                                    e.getMessage()))
            );
        }
        for (String ocspURL : ocspURLList) {
            String currentURL = ocspURL.trim();
            try {
                URI uri = URI.create(currentURL);
                HttpRequest request = HttpRequest.newBuilder(uri)
                        .header("Content-Type", "application/ocsp-request")
                        .header("Accept", "application/ocsp-response")
                        .header("User-Agent", "mendelson software - www.mendelson.de")
                        .POST(HttpRequest.BodyPublishers.ofByteArray(ocspRequest.getEncoded()))
                        .build();
                HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
                if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                    //this failed but perhaps the next URL will be successful - if not at the end there will be thrown an error
                    continue;
                }
                OCSPResp ocspResp = new OCSPResp(response.body());
                if (ocspResp.getStatus() != OCSPResponseStatus.SUCCESSFUL) {
                    continue;
                }
                BasicOCSPResp basicResponse = (BasicOCSPResp) ocspResp.getResponseObject();
                //Validate signature of response
                Extension nonceExtension = basicResponse.getExtension(
                        OCSPObjectIdentifiers.id_pkix_ocsp_nonce);
                if (nonceExtension != null
                        && !Arrays.equals(nonce,
                                DEROctetString.getInstance(nonceExtension.getParsedValue()).getOctets())) {
                    throw new CRLVerificationException(new CRLRevocationState(
                            CRLRevocationState.STATE_CRL_OTHER_PROBLEM,
                            rb.getResourceString("error.invalid.nonce")));
                }
                ContentVerifierProvider verifier = new JcaContentVerifierProviderBuilder()
                        .setProvider(BouncyCastleProviderSingleton.instance())
                        .build(issuerCertificate.getPublicKey());
                if (!basicResponse.isSignatureValid(verifier)) {
                    throw new CRLVerificationException(
                            new CRLRevocationState(
                                    CRLRevocationState.STATE_CRL_INVALID_SIGNATURE,
                                    rb.getResourceString("error.invalid.signature")));
                }
                //process state of the answer
                SingleResp[] responses = basicResponse.getResponses();
                if (responses == null || responses.length == 0) {
                    //Invalid OCSP response: Payload contains no status information
                    continue;
                }
                if (responses.length > 0) {
                    SingleResp singleResponse = responses[0];
                    Object status = singleResponse.getCertStatus();
                    if (status == CertificateStatus.GOOD) {
                        Date nextUpdate = singleResponse.getNextUpdate();
                        if (nextUpdate != null) {
                            return (nextUpdate.getTime());
                        } else {
                            //nextupdate is optional in the protocol - just set the validity time to 15 minutes
                            return (System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15));
                        }
                    } else if (status instanceof RevokedStatus) {
                        RevokedStatus revokedStatus = (RevokedStatus) status;
                        throw new CRLVerificationException(
                                new CRLRevocationState(
                                        CRLRevocationState.STATE_CRL_REVOKED,
                                        rb.getResourceString("failed.revoked",
                                                revokedStatus.getRevocationReason()
                                                + " [" + revokedStatus.getRevocationTime() + "]")
                                )
                        );
                    } else if (status instanceof UnknownStatus) {
                        //Status Unknown - Responder does not know this certificate
                        continue;
                    }
                }
            } catch (CRLVerificationException e) {
                throw e;
            } catch (Throwable e) {
                throw new CRLVerificationException(new CRLRevocationState(
                        CRLRevocationState.STATE_CRL_OTHER_PROBLEM,
                        "[" + e.getClass().getSimpleName() + "] " + e.getMessage())
                );
            }
        }
        //no URL state was successful
        throw new CRLVerificationException(new CRLRevocationState(
                CRLRevocationState.STATE_CRL_OTHER_PROBLEM,
                "OCSP check failed for all OCSP URLs"));
    }

}
