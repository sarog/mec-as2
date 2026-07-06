//$Header: /as4/de/mendelson/util/security/cert/ResourceBundleCertificateValidity.java 3     14/01/26 14:42 Heller $
package de.mendelson.util.security.cert;
import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize gui entries
 * @author S.Heller
 * @version $Revision: 3 $
 */
public class ResourceBundleCertificateValidity extends MecResourceBundle{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    private static final Object[][] CONTENTS = {                
        {"state." + CertificateValiditySettings.STATE_CHAIN_INVALID, "Invalid trust chain" },        
        {"state." + CertificateValiditySettings.STATE_DATE_INVALID, "Expired" },        
        {"state." + CertificateValiditySettings.STATE_MENDELSON_PUBLIC_CERT, "Public mendelson test key - do not use in production" },
        {"state." + CertificateValiditySettings.STATE_CRL_CERT_NOT_READABLE, "(CRL) Certificate not readable" },
        {"state." + CertificateValiditySettings.STATE_CRL_DOWNLOAD_FAILED, "(CRL) Download failed" },
        {"state." + CertificateValiditySettings.STATE_CRL_EXPIRED, "(CRL) Answer expired" },
        {"state." + CertificateValiditySettings.STATE_CRL_INVALID_SIGNATURE, "(CRL) Invalid signature" },
        {"state." + CertificateValiditySettings.STATE_CRL_IN_BAD_FORMAT, "(CRL) Bad format" },
        {"state." + CertificateValiditySettings.STATE_CRL_ISSUER_MISSING, "(CRL) Issuer missing - please import parent certificate" },
        {"state." + CertificateValiditySettings.STATE_CRL_MALFORMED_URL, "(CRL) Malformed URL" },
        {"state." + CertificateValiditySettings.STATE_CRL_NOT_REACHABLE, "(CRL) URL not reachable" },
        {"state." + CertificateValiditySettings.STATE_CRL_NO_CDP_EXTENSION, "(CRL) Missing extension" },
        {"state." + CertificateValiditySettings.STATE_CRL_OTHER_PROBLEM, "(CRL) Unspecified problem" },
        {"state." + CertificateValiditySettings.STATE_CRL_REVOKED, "(CRL) Revoked by CA" },
        {"state." + CertificateValiditySettings.STATE_CRL_UNABLE_TO_EXTRACT_CRL_URL, "(CRL) Unable to extract URL" },
        {"state." + CertificateValiditySettings.STATE_CRL_UNSUPPORTED_CRL_URL_SCHEME, "(CRL) Unsupported URL scheme" },
    };
    
}