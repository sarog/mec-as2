//$Header: /as2/de/mendelson/util/clientserver/connectionpool/TextClientConnectionPool.java 5     15/05/25 11:55 Heller $
package de.mendelson.util.clientserver.connectionpool;

import de.mendelson.util.clientserver.BaseClient;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Connection pool to reuse client-server connections
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class TextClientConnectionPool {

    private static final int CONNECTION_IDLE_TIME_BEFORE_REMOVE_IN_S = 60;
    private final LinkedBlockingQueue<PooledTextClient> QUEUE = new LinkedBlockingQueue<PooledTextClient>();
    private final ClientPoolCleanupThread cleanupThread = new ClientPoolCleanupThread();

    private TextClientConnectionPool() {
        ConnectionPoolCleanupThreadPool.scheduleWithFixedDelay(this.cleanupThread, 30, 30, TimeUnit.SECONDS);
    }

    private static TextClientConnectionPool instance;

    /**
     * Singleton for the whole application
     */
    public static synchronized TextClientConnectionPool instance() {
        if (instance == null) {
            instance = new TextClientConnectionPool();
        }
        return instance;
    }

    /**
     * Release the connection back to the pool
     */
    public void releaseClient(PooledTextClient client) {
        if (client != null) {
            //start the idle time for the connection - this is requested later in the cleanup process
            client.setReleaseTime(System.currentTimeMillis());
            try {
                this.QUEUE.put(client);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get an idle PooledTextClient from the PooledTextClient pool
     */
    public PooledTextClient getIdleClient() {
        PooledTextClient client = this.QUEUE.poll();
        if (client == null || !client.isConnected()) {
            return (null);
        }
        client.setReleaseTime(0);
        return (client);
    }

    protected void cleanup() {
        synchronized (this.QUEUE) {
            for (PooledTextClient client : this.QUEUE) {
                if (client != null && client.isConnected() && client.getReleaseTime() != 0
                        && (System.currentTimeMillis() - client.getReleaseTime()
                        > TimeUnit.SECONDS.toMillis(CONNECTION_IDLE_TIME_BEFORE_REMOVE_IN_S))) {
                    QUEUE.remove(client);
                    try {
                        client.destroyClient();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class ClientPoolCleanupThread implements Runnable {

        @Override
        public void run() {
            cleanup();
        }
    }

}
