//$Header: /as4/de/mendelson/util/security/crl/ResourceBundleCRL.java 8     14/01/26 14:06 Heller $
package de.mendelson.util.security.crl;
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
 * @version $Revision: 8 $
 */
public class ResourceBundleCRL extends MecResourceBundle{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    private static final Object[][] CONTENTS = {                
        {"module.name", "[CRL]" },
        {"self.signed.skipped", "Is self signed - verification skipped." },
        {"ca.skipped", "CA root or trust anchor - verification skipped." },
        {"crl.success", "Ok - the certificate has not been revoked, valid until {0}" },
        {"failed.revoked", "The certificate has been revoked, reason: {0}"},
        {"malformed.crl.url", "Malformed CRL URL ({0})" },
        {"no.https", "Problem connecting to URI {0} - HTTPS is not supported" },
        {"bad.crl", "Problem generating a X509CRL from the downloaded CRL data" },
        {"cert.read.error", "Cannot read certificate to get CRL URLs" },
        {"error.url.retrieve", "Cannot get CRL distribution point URLs from certificate" },
        {"no.crl.entry", "Certificate does not have a CRL distribution point" },
        {"download.failed.from", "The download of the revocation list failed ({0})"},
        {"crl.expired", "The received CRL information is expired ({0})"},
	{"error.nextupdate.missing", "The received CRL information does not include any information on how long it is valid"},
        {"error.invalid.signature", "Security error - invalid signature in answer" },
        {"error.invalid.nonce", "Security error - invalid nonce in answer" },
        {"error.request.generation", "Problem generating request ({0})" },
        {"error.issuercertificate.required", "No issuer certificate found for revocation check - please import the parent certificate" },
    };
    
}