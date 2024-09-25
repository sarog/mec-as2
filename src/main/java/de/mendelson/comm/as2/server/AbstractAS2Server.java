//$Header: /as2/de/mendelson/comm/as2/server/AbstractAS2Server.java 5     24/10/23 14:06 Heller $
package de.mendelson.comm.as2.server;

import de.mendelson.util.security.cert.CertificateManager;
import java.util.Date;
import java.util.logging.Logger;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Server root for the mendelson client/server architecture
 * @author S.Heller
 * @version $Revision: 5 $
 */
public abstract class AbstractAS2Server {

    private long startTime = 0;
    private CertificateManager certificateManager;

    /** Creates a new instance of Server
     */
    public AbstractAS2Server() {
        this.startTime = new Date().getTime();
    }

    public abstract void start(boolean importTLS, boolean importSignEnc) throws Exception;

    public abstract int getPort();

    /**Returns the start time of the server*/
    public long getStartTime() {
        return startTime;
    }

    /**Has to be implemented*/
    public abstract Logger getLogger();

    /**
     * @return the certificateManager (SSL)
     */
    public CertificateManager getCertificateManager() {
        return certificateManager;
    }
}
