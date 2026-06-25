//$Header: /as2/de/mendelson/util/clientserver/connectionpool/ConnectionPoolCleanupThreadPool.java 1     15/05/25 8:38 Heller $
package de.mendelson.util.clientserver.connectionpool;

import de.mendelson.util.NamedThreadFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Thread pool for cleanup schedules of the connection pools
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class ConnectionPoolCleanupThreadPool {

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newScheduledThreadPool(1,
            new NamedThreadFactory("client_server_pool_maintainance"));

    private ConnectionPoolCleanupThreadPool(){        
    }
    
    public static void scheduleWithFixedDelay(Runnable task, int startDelay, int executionDelay, TimeUnit timeunit){
        SCHEDULED_EXECUTOR.scheduleWithFixedDelay(task, startDelay, executionDelay, timeunit);
    }
    
}
