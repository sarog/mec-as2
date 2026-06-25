//$Header: /as2/de/mendelson/util/clientserver/connectionpool/PooledAnonymousTextClient.java 7     23/03/26 8:03 Heller $
package de.mendelson.util.clientserver.connectionpool;

import de.mendelson.IProductVersion;
import de.mendelson.util.clientserver.AnonymousTextClient;
import de.mendelson.util.clientserver.ClientType;
import java.net.InetSocketAddress;

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
public class PooledAnonymousTextClient extends AnonymousTextClient implements AutoCloseable {

    private long releaseTime = 0;

    /**
     *
     * @param CLIENT_TYPE Client Type as defined in the BaseClient
     */
    private PooledAnonymousTextClient(ClientType clientType, IProductVersion productVersion) {
        super(clientType, productVersion);
    }

    public static PooledAnonymousTextClient createClient(ClientType clientType, IProductVersion productVersion) {
        PooledAnonymousTextClient client = AnonymousTextClientConnectionPool.instance().getIdleClient();
        if (client == null) {
            client = new PooledAnonymousTextClient(clientType, productVersion);
        }else{
            client.getBaseClient().setClientType( clientType );
        }
        return (client);
    }

    /**
     * Returns the version of this class
     */
    public static String getVersion() {
        String revision = "$Revision: 7 $";
        return (revision.substring(revision.indexOf(":") + 1,
                revision.lastIndexOf("$")).trim());
    }

    @Override
    public void connect(InetSocketAddress hostAddress, long timeout) throws Exception {
        if (!super.isConnected()) {
            //if it is a new connection a new connection process is required - else the existing connection is used and the login 
            //process is skipped
            super.connect(hostAddress, timeout);
        }
    }

    @Override
    public void connect(String host, int port, long timeout) throws Exception {
        if (!super.isConnected()) {
            //if it is a new connection a new connection process is required - else the existing connection is used and the login 
            //process is skipped
            super.connect(host, port, timeout);
        }
    }

    /**
     * Finally closes the client
     */
    public void destroyClient() throws Exception {
        super.disconnect();
    }

    /**
     * Makes this class AutoCloseable: release the client into the pool. Please
     * call the method destroyClient to finally close it and clean it
     */
    @Override
    public void close() throws Exception {
        AnonymousTextClientConnectionPool.instance().releaseClient(this);
    }
        
    /**
     * @return the releaseTime
     */
    public long getReleaseTime() {
        return this.releaseTime;
    }

    /**
     * @param releaseTime the releaseTime to set
     */
    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

}
