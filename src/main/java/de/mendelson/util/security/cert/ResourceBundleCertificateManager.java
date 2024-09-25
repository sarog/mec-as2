//$Header: /as4/de/mendelson/util/security/cert/ResourceBundleCertificateManager.java 9     13-07-16 2:01p Heller $
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
 * @version $Revision: 9 $
 */
public class ResourceBundleCertificateManager extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {                
        {"keystore.reloaded", "Keys and certificates have been reloaded." },
        {"alias.notfound", "The keystore does not contain a certificate with the alias \"{0}\"." },
        {"alias.hasno.privatekey","The keystore does not contain a private key with the alias \"{0}\"." },
        {"alias.hasno.key","The keystore does not contain a key with the alias \"{0}\"." },
        {"certificate.not.found.fingerprint", "The certificate with the SHA-1 fingerprint \"{0}\" does not exist." },
        {"certificate.not.found.issuerserial.withinfo", "The certificate with the issuer \"{0}\" and the serial \"{1}\" is requested but does not exist in the system ({2})"},        
        {"certificate.not.found.subjectdn.withinfo", "The certificate with the subjectDN \"{0}\" is requested but does not exist in the system. ({1})" },
        {"certificate.not.found.ski.withinfo", "The certificate with the Subject Key Identifier \"{0}\" is requested but does not exist in the system. ({1})" },
        {"certificate.not.found.fingerprint.withinfo", "The certificate with the SHA-1 fingerprint \"{0}\" does not exist. ({1})" },
        {"keystore.read.failure", "The system is unable to read the underlaying certificates. Error message: \"{0}\". Please ensure that you are using the correct keystore password."},
    };
    
}