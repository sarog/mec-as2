//$Header: /as2/de/mendelson/util/clientserver/connectiontest/ResourceBundleConnectionTest.java 5     7/06/17 11:10a Heller $
package de.mendelson.util.clientserver.connectiontest;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class ResourceBundleConnectionTest extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"tag", "[Connection test to {0}] "},
        {"timeout.set", "Setting timeout to {0}ms" },
        {"test.start.ssl", "Starting connection check to {0} using SSL/TLS connection. Please remember that during this test your client will trust all server certificates and will ignore your TLS keystore - means it is not ensured that your TLS keystore is set up in the right way even if this test is successful."},
        {"test.start.plain", "Starting connection check to {0} using PLAIN connection..."},
        {"connection.problem", "Unable to reach {0} - infrastructure problem or wrong address entered" },
        {"connection.success", "Connection to {0} established successfully" },
        {"exception.occured", "Exception occured during connection test: [{0}] {1}" },
        {"exception.occured.oftpservice", "The system is unable to identify a running OFTP2 system at the desired address and port. This could be a temporary problem - means it is possible that the address settings are correct and the remote OFTP2 server is currently not running or there exists a temporary infrastructure problem at your partners side. The following exception occured: [{0}] {1}" },
        {"remote.service.identification", "Remote service identification: \"{0}\"" },
        {"service.found.success", "Success: Running OFTP service found at {0}" },
        {"service.found.failure", "Failure: No running OFTP service found at {0}" },
        {"wrong.protocol", "The found protocol is \"{0}\", this is not a secured connection. You tried to connect via SSL/TLS to this address but this is not provided by the remote server at this address and port." },
        {"wrong.protocol.hint", "Either there is a plain connection expected by your partner, your partner uses the wrong protocol or client authentication is required" },
        {"protocol.information", "The used protocol has been identified as \"{0}\"" },
        {"requesting.certificates", "Requesting certificate(s) from remote host" },
        {"certificates.found", "{0} certificates have been found and downloaded" },
        {"certificates.found.details", "Certificate [{0}/{1}]: {2}" },        
        {"check.for.service.oftp2", "Check for running OFTP2 service..." },
        {"certificate.ca", "CA Certificate" },        
        {"certificate.enduser", "End User Certificate" },        
        {"certificate.selfsigned", "Self Signed" },        
        {"certificate.does.not.exist.local", "This certificate does not exist in your local TLS/SSL keystore - please import it" },
        {"certificate.does.exist.local", "This certificate does exist in your local TLS/SSL keystore, the alias is \"{0}\"" },
    };

}