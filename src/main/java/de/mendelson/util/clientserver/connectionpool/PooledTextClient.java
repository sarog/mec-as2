//$Header: /as2/de/mendelson/util/clientserver/connectionpool/PooledTextClient.java 7     23/03/26 8:03 Heller $
package de.mendelson.util.clientserver.connectionpool;

import de.mendelson.IProductVersion;
import de.mendelson.util.clientserver.ClientType;
import de.mendelson.util.clientserver.ClientsideMessageProcessor;
import de.mendelson.util.clientserver.TextClient;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Text Client to connect to a mendelson product. Tries to reuse connections for
 * faster processing
 *
 * @author S.Heller
 * @version $Revision: 7 $
 */
public class PooledTextClient extends TextClient implements ClientsideMessageProcessor, AutoCloseable {

    private long releaseTime = 0;

    /**
     *
     * @param CLIENT_TYPE Client Type as defined in the BaseClient
     */
    private PooledTextClient(ClientType clientType, IProductVersion productVersion) {
        super(clientType, productVersion);
    }

    public static PooledTextClient createClient(ClientType clientType, IProductVersion productVersion) {
        PooledTextClient client = TextClientConnectionPool.instance().getIdleClient();
        if (client == null) {
            client = new PooledTextClient(clientType, productVersion);
        }else{
            client.getBaseClient().setClientType(clientType);
        }
        return (client);
    }

    /**
     * Connects to the server and performs a login
     */
    @Override
    public void connectAndLogin(String host,
            int clientServerCommPort, String clientId,
            String user, char[] password, long timeout,
            String connectionThreadNamePrefix) throws Throwable {
        if (!super.isConnected()) {
            //if it is a new connection a new login process is required - else the existing connection is used and the login 
            //process is skipped
            super.connectAndLogin(host, clientServerCommPort, clientId, user, password, timeout, connectionThreadNamePrefix);
        }
    }

    /**
     * Returns the version of this class
     */
    public static String getVersion() {
        String revision = "$Revision: 7 $";
        return (revision.substring(revision.indexOf(":") + 1,
                revision.lastIndexOf("$")).trim());
    }

    /**
     * Finally closes the client
     */
    public void destroyClient() throws Exception {
        this.logout();
        this.disconnect();
    }

    /**
     * Makes this class AutoCloseable: release the client into the pool. Please
     * call the method destroyClient to finally close it and clean it
     */
    @Override
    public void close() throws Exception {
        TextClientConnectionPool.instance().releaseClient(this);
    }

    /**
     * @return the releaseTime
     */
    public long getReleaseTime() {
        return releaseTime;
    }

    /**
     * @param releaseTime the releaseTime to set
     */
    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

}
