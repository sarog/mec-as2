//$Header: /as4/de/mendelson/util/security/cert/CertificateValidityException.java 8     19/12/25 10:17 Heller $
package de.mendelson.util.security.cert;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Exception that is thrown if the validation process of a certificate fails and
 * the system must not use it
 *
 * @author S.Heller
 * @version $Revision: 8 $
 */
public class CertificateValidityException extends Exception {

    private final int statusFlags;
    private final String alias;
    private final String fingerprintSHA1;

    public CertificateValidityException(int statusFlags, String alias, String fingerprintSHA1) {
        super(getDetailedStatusMessage(statusFlags, alias, fingerprintSHA1));
        this.statusFlags = statusFlags;
        this.alias = alias;
        this.fingerprintSHA1 = fingerprintSHA1;
    }

    /**
     * Returns the status flags of the validation, these bits are defined in the
     * class CertificateValiditySettings.STATUS_XX
     *
     * @return
     */
    public int getStatusFlags() {
        return (this.statusFlags);
    }

    private static String getDetailedStatusMessage(int statusFlags, String alias, String fingerprintSHA1) {
        if (statusFlags == 0) {
            return "Certificate validation successful (No issues detected)";
        }
        StringBuilder message = new StringBuilder();
        message.append("The used certificate "
                + "(Alias: \"" + alias + "\", Fingerprint SHA1: "
                + fingerprintSHA1 + ") is invalid. Detected issues: [");
        List<String> issues = new ArrayList<String>();
        if ((statusFlags & CertificateValiditySettings.STATE_DATE_INVALID) != 0) {
            issues.add("Date Invalid (Expired or Not Yet Valid)");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_CHAIN_INVALID) != 0) {
            issues.add("Trust Chain Invalid (Broken or Untrusted Root)");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_KEY_SIZE_TOO_SMALL) != 0) {
            issues.add("Key Size Too Small");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_INTERNAL_ERROR) != 0) {
            issues.add("Internal Error during Validation");
        }
        if ((statusFlags & (CertificateValiditySettings.STATE_USAGE_MISMATCH_CRYPT
                | CertificateValiditySettings.STATE_USAGE_MISMATCH_SIGN
                | CertificateValiditySettings.STATE_USAGE_MISMATCH_TLS)) != 0) {
            List<String> usages = new ArrayList<>();
            if ((statusFlags & CertificateValiditySettings.STATE_USAGE_MISMATCH_CRYPT) != 0) {
                usages.add("Cryptographic Use");
            }
            if ((statusFlags & CertificateValiditySettings.STATE_USAGE_MISMATCH_SIGN) != 0) {
                usages.add("Signature Use");
            }
            if ((statusFlags & CertificateValiditySettings.STATE_USAGE_MISMATCH_TLS) != 0) {
                usages.add("TLS/Server Use");
            }
            issues.add("Usage Mismatch: Missing required purpose(s) for: [" + String.join(", ", usages) + "]");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_CRL_REVOKED) != 0) {
            issues.add("Revoked (Explicitly listed on CRL of CA)");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_CRL_NOT_REACHABLE) != 0) {
            issues.add("CRL Unreachable");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_CRL_DOWNLOAD_FAILED) != 0) {
            issues.add("CRL Download Failed");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_CRL_EXPIRED) != 0) {
            issues.add("CRL Expired");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_CRL_INVALID_SIGNATURE) != 0) {
            issues.add("Invalid CRL Signature");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_CRL_NO_CDP_EXTENSION) != 0) {
            issues.add("Missing CRL CDP Extension");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_CRL_MALFORMED_URL) != 0) {
            issues.add("Malformed CRL URL");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_CRL_IN_BAD_FORMAT) != 0) {
            issues.add("Bad CRL Format");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_CRL_UNSUPPORTED_CRL_URL_SCHEME) != 0) {
            issues.add("Unsupported CRL URL Scheme");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_CRL_CERT_NOT_READABLE) != 0) {
            issues.add("The CRL certificate could not be read or parsed");
        }
        if ((statusFlags & CertificateValiditySettings.STATE_CRL_OTHER_PROBLEM) != 0) {
            issues.add("Other CRL Processing Error");
        }
        message.append(String.join("; ", issues));
        message.append( "]");
        return (message.toString());
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @return the fingerprintSHA1
     */
    public String getFingerprintSHA1() {
        return fingerprintSHA1;
    }

}
