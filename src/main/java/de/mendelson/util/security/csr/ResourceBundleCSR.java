//$Header: /mendelson_business_integration/de/mendelson/util/security/csr/ResourceBundleCSR.java 2     3/20/17 3:07p Heller $
package de.mendelson.util.security.csr;
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
 * @version $Revision: 2 $
 */
public class ResourceBundleCSR extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
                
        {"label.selectcsrfile", "Please select the file where to store the request" },
        {"csr.title", "Trust certificate: Certificate Sign Request" },
        {"csr.title.renew", "Renew certificate: Certificate Sign Request" },
        {"csr.message.storequestion", "Would you like to trust the key at the mendelson CA\nor store the CSR to a file?" },        
        {"csr.message.storequestion.renew", "Would you like to renew the key at the mendelson CA\nor store the CSR to a file?" },        
        {"csr.generation.success.message", "The CSR has been stored to the file\n\"{0}\".\nPlease send it to your CA to perform the trust request.\nWe would suggest the mendelson CA (http://ca.mendelson-e-c.com)."},
        {"csr.option.1", "Trust at mendelson CA" },
        {"csr.option.1.renew", "Renew at mendelson CA" },
        {"csr.option.2", "Store to a file" },
        {"csr.generation.success.title", "CSR generated successfully"},
        {"csr.generation.failure.title", "CSR generation failed"},
        {"csr.generation.failure.message", "{0}"},
        {"label.selectcsrrepsonsefile", "Please select the CA answer file" },
        {"csrresponse.import.success.message", "The key has been successfully patched with the CA answer." },
        {"csrresponse.import.success.title", "Key trust path established" },
        {"csrresponse.import.failure.message", "{0}" },
        {"csrresponse.import.failure.title", "Problem patching the key" },       
    };
    
}