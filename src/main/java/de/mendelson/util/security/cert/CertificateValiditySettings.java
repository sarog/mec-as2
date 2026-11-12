//$Header: /as2/de/mendelson/util/security/cert/CertificateValiditySettings.java 10    23/03/26 14:37 Heller $
package de.mendelson.util.security.cert;

import de.mendelson.util.security.crl.CRLRevocationState;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores and handles the validity of a keystore certificate
 *
 * @author S.Heller
 * @version $Revision: 10 $
 */
public class CertificateValiditySettings implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Bit-Flags to control which certificate validity checks should be
     * performed.
     */
    public static final int CHECK_NONE = 0x00000000;
    /**
     * Checks the validity date (NotBefore, NotAfter)
     */
    public static final int CHECK_VALIDITY_DATE = 0x00000001;
    /**
     * Checks against the Certificate Revocation List (CRL)
     */
    public static final int CHECK_CRL = 0x00000002;
    /**
     * Checks the entire certificate chain (Chain of Trust)
     */
    public static final int CHECK_CHAIN_VALIDITY = 0x00000004;
    /**
     * Checks if the key size meets the minimum requirement
     */
    public static final int CHECK_MIN_KEY_SIZE = 0x00000008;
    /**
     * This flag is used to check general usage requirements in
     * validitySettings.
     */
    public static final int CHECK_USAGE_MISMATCH = 0x00000010;
    /**
     * Checks if the certificate is a mendelson public certificate
     */
    public static final int CHECK_MENDELSON_PUBLIC_CERT = 0x00000020;
    /**
     * The certificate passed all validity checks required by the set flags.
     */
    public static final int STATE_OK = CHECK_NONE;
    /**
     * The certificate is expired (NotAfter) or not yet valid (NotBefore).
     */
    public static final int STATE_DATE_INVALID = CHECK_VALIDITY_DATE;
    /**
     * The certificate chain is broken, incomplete, or contains untrusted roots.
     */
    public static final int STATE_CHAIN_INVALID = CHECK_CHAIN_VALIDITY;
    /**
     * The key size (e.g. RSA bit length) is below the required minimum
     * threshold.
     */
    public static final int STATE_KEY_SIZE_TOO_SMALL = CHECK_MIN_KEY_SIZE;
    /**
     * The certificate does not permit the required encryption purpose.
     */
    public static final int STATE_USAGE_MISMATCH_CRYPT = 0x00000020;
    /**
     * The certificate does not permit the required signature purpose.
     */
    public static final int STATE_USAGE_MISMATCH_SIGN = 0x00000040;
    /**
     * The certificate does not permit the required TLS purpose (Server/Client).
     */
    public static final int STATE_USAGE_MISMATCH_TLS = 0x00000080;
    /**
     * The certificate is explicitly listed as revoked on the Certificate
     * Revocation List (CRL)
     */
    public static final int STATE_CRL_REVOKED = 0x00000100;
    /**
     * The Certificate Revocation List (CRL) could not be downloaded or
     * retrieved (network error, timeout)
     */
    public static final int STATE_CRL_NOT_REACHABLE = 0x00000200;
    /**
     * The downloaded Certificate Revocation List (CRL) itself is expired
     * (nextUpdate time passed)
     */
    public static final int STATE_CRL_EXPIRED = 0x00000400;
    /**
     * The Certificate Revocation List (CRL) signature is invalid or not trusted
     */
    public static final int STATE_CRL_INVALID_SIGNATURE = 0x00000800;
    /**
     * The certificate does not contain a Certificate Distribution Point (CDP)
     * extension needed for CRL checking
     */
    public static final int STATE_CRL_NO_CDP_EXTENSION = 0x00001000;
    /**
     * The CRL URL retrieved from the certificate is syntactically invalid or
     * uses an unsupported protocol.
     */
    public static final int STATE_CRL_MALFORMED_URL = 0x00002000;
    /**
     * The downloaded CRL file is not a valid format or is otherwise corrupted
     */
    public static final int STATE_CRL_IN_BAD_FORMAT = 0x00004000;
    /**
     * The certificate file itself could not be read or parsed
     */
    public static final int STATE_CRL_CERT_NOT_READABLE = 0x00008000;
    /**
     * The certificate lacks the Certificate Distribution Point (CDP) extension
     */
    public static final int STATE_CRL_UNABLE_TO_EXTRACT_CRL_URL = 0x00020000;
    /**
     * The attempt to download the CRL failed specifically due to a network or
     * IO error.
     */
    public static final int STATE_CRL_DOWNLOAD_FAILED = 0x00040000;
    /**
     * The CRL URL uses an unsupported protocol (e.g., HTTPS, FTP)
     */
    public static final int STATE_CRL_UNSUPPORTED_CRL_URL_SCHEME = 0x00080000;
    /**
     * A general unmapped problem occurred during CRL processing.
     */
    public static final int STATE_CRL_OTHER_PROBLEM = 0x00100000;
    /**
     * The certificate chain is incomplete; the issuer certificate is missing to
     * perform a CRL check which is required for ldap CRL check
     */
    public static final int STATE_CRL_ISSUER_MISSING = 0x00800000;
    /**
     * An unexpected or unhandled exception occurred during the validation
     * process.
     */
    public static final int STATE_INTERNAL_ERROR = 0x00200000;
    /**
     * The certificate is not a mendelson public certificate, it might be
     * required to warn the user if it is used in processing
     */
    public static final int STATE_MENDELSON_PUBLIC_CERT = 0x00400000;
    /**
     * Defines the certificate to be used in encryption. If this is checked and
     * does not match the state will contain STATE_USAGE_MISMATCH_CRYPT
     */
    public static final int OPERATION_CRYPT = STATE_USAGE_MISMATCH_CRYPT;
    /**
     * Defines the certificate to be used in digital signature. If this is
     * checked and does not match the state will contain
     * STATE_USAGE_MISMATCH_SIGN
     */
    public static final int OPERATION_SIGN = STATE_USAGE_MISMATCH_SIGN;
    /**
     * Defines the certificate to be used in TLS. If this is checked and does
     * not match the state will contain STATE_USAGE_MISMATCH_TLS
     */
    public static final int OPERATION_TLS = STATE_USAGE_MISMATCH_TLS;
    /**
     * Do not check the purpose for unknown operations. The purpose of the
     * certificate must not be checked in this case even if the purpose check
     * flag is set in the check bit array
     */
    public static final int OPERATION_ANY = 0;

    /**
     * The SHA-1 fingerprints of the public available mendelson test keys
     */
    private static final String[] TEST_KEYS_FINGERPRINTS_SHA1_ARRAY = new String[]{
        "6D:9A:2C:79:02:0B:F1:6B:20:78:E4:A3:BE:DF:93:DD:2A:AD:B7:40", //key2
        "3D:A0:27:42:4D:92:6D:04:BB:74:66:1D:48:3E:61:6A:46:2A:05:B7", //key1
        "08:FF:33:83:DF:8B:2F:9F:40:BB:F7:88:FE:FD:9C:15:40:E4:FE:C6", //key4
        "DC:99:5A:83:60:A4:37:C4:30:3B:10:AC:31:4E:D9:21:16:61:36:77", //key3  
        "12:78:6E:51:E8:67:E1:58:5D:B9:77:E0:91:BC:DE:72:51:62:5A:8E", //eDelivery key1 encryption
        "3F:7D:3B:15:E6:55:C4:C1:30:B6:3D:9D:D9:41:C2:40:20:F0:BD:D4", //eDelivery key2 signature
    };
    //Set for a quick search
    public static final Set<String> MENDELSON_PUBLIC_CERTIFICATES_FINGERPRINTS
            = Collections.unmodifiableSet(
                    new HashSet<String>(Arrays.asList(TEST_KEYS_FINGERPRINTS_SHA1_ARRAY))
            );

    public static final List<Integer> CRL_PROBLEM_STATES
            = Collections.unmodifiableList(
                    List.<Integer>of(
                            STATE_CRL_REVOKED,
                            STATE_CRL_NOT_REACHABLE,
                            STATE_CRL_EXPIRED,
                            STATE_CRL_INVALID_SIGNATURE,
                            STATE_CRL_NO_CDP_EXTENSION,
                            STATE_CRL_MALFORMED_URL,
                            STATE_CRL_IN_BAD_FORMAT,
                            STATE_CRL_CERT_NOT_READABLE,
                            STATE_CRL_UNABLE_TO_EXTRACT_CRL_URL,
                            STATE_CRL_DOWNLOAD_FAILED,
                            STATE_CRL_UNSUPPORTED_CRL_URL_SCHEME,
                            STATE_CRL_ISSUER_MISSING,
                            STATE_CRL_OTHER_PROBLEM
                    )
            );

    public CertificateValiditySettings() {
    }

    /**
     * Checks if the given flag combination contains any specific CRL problem
     * bit. This is helpful to check the detailed error cause when CHECK_CRL is
     * set in the main result
     *
     * @param flag The short containing the accumulated status bits from a
     * validation process.
     * @return true if any specific CRL status bit (STATUS_CRL_REVOKED up to
     * STATUS_NO_CDP_EXTENSION) is set.
     */
    public static boolean isCRLProblem(int flag) {
        for (int problemState : CRL_PROBLEM_STATES) {
            if ((flag & problemState) != 0) {
                return (true);
            }
        }
        return (false);
    }

    /**
     * Maps the state of the revocation call to the validity check state
     *
     * @param revocationState
     * @return
     */
    public static int stateFromCRLRevocationState(CRLRevocationState revocationState) {
        if (revocationState.getState() == CRLRevocationState.STATE_OK) {
            return STATE_OK;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_REVOKED) {
            return STATE_CRL_REVOKED;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_UNABLE_TO_EXTRACT_CRL_URL) {
            return STATE_CRL_UNABLE_TO_EXTRACT_CRL_URL;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_NO_CDP_EXTENSION) {
            return STATE_CRL_NO_CDP_EXTENSION;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_NOT_REACHABLE) {
            return STATE_CRL_NOT_REACHABLE;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_MALFORMED_URL) {
            return STATE_CRL_MALFORMED_URL;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_CERT_NOT_READABLE) {
            return STATE_CRL_CERT_NOT_READABLE;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_DOWNLOAD_FAILED) {
            return STATE_CRL_DOWNLOAD_FAILED;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_OTHER_PROBLEM) {
            return STATE_CRL_OTHER_PROBLEM;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_UNSUPPORTED_CRL_URL_SCHEME) {
            return STATE_CRL_UNSUPPORTED_CRL_URL_SCHEME;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_IN_BAD_FORMAT) {
            return STATE_CRL_IN_BAD_FORMAT;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_EXPIRED) {
            return STATE_CRL_EXPIRED;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_INVALID_SIGNATURE) {
            return STATE_CRL_INVALID_SIGNATURE;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_ISSUER_MISSING) {
            return STATE_CRL_ISSUER_MISSING;
        }
        if (revocationState.getState() == CRLRevocationState.STATE_CRL_INVALID_SIGNATURE) {
            return STATE_CRL_INVALID_SIGNATURE;
        }
        return STATE_INTERNAL_ERROR;
    }

}
