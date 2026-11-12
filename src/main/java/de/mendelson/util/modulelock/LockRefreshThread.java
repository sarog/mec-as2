//$Header: /as2/de/mendelson/util/modulelock/LockRefreshThread.java 10    8/04/26 15:28 Heller $
package de.mendelson.util.modulelock;

import de.mendelson.util.NamedThreadFactory;
import de.mendelson.util.modulelock.message.ModuleLockRequest;
import de.mendelson.util.clientserver.BaseClient;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Refreshes the lock if a client has the exclusive lock on a module
 *
 * @author S.Heller
 * @version $Revision: 10 $
 */
public class LockRefreshThread implements Runnable {

    /**
     * Central pool for all lock refresh tasks.
     */
    private static final ScheduledThreadPoolExecutor LOCK_EXECUTOR
            = new ScheduledThreadPoolExecutor(2, new NamedThreadFactory("modulelock-refresh"));

    private final ModuleLock.Module module;
    private final BaseClient baseClient;
    private ScheduledFuture<?> refreshFuture;

    public LockRefreshThread(BaseClient baseClient, ModuleLock.Module module) {
        this.module = module;
        this.baseClient = baseClient;
    }

    /**
     * Starts the periodic refresh task.
     */
    public synchronized void startLocking() {
        if (this.refreshFuture == null || this.refreshFuture.isCancelled()) {
            //Schedule the task: start after 15s because a lock is already set in this state, 
            //then repeat every 15 seconds
            this.refreshFuture = LOCK_EXECUTOR.scheduleWithFixedDelay(this, 15, 15, TimeUnit.SECONDS);
        }
    }

    /**
     * Stops the periodic refresh task and removes it from the executor.
     */
    public synchronized void stopLocking() {
        if (this.refreshFuture != null) {
            this.refreshFuture.cancel(false);
            this.refreshFuture = null;
        }
    }

    /**
     * Executed by the LOCK_EXECUTOR. Performs a single refresh request
     */
    @Override
    public void run() {
        try {
            ModuleLockRequest request = new ModuleLockRequest(this.module, ModuleLockRequest.Type.REFRESH);
            this.baseClient.sendSync(request, TimeUnit.SECONDS.toMillis(15));
        } catch (Throwable e) {
            //Catch everything to prevent the scheduler from suppressing subsequent executions 
            //of this task if an exception occurs, e.g. network timeout
        }
    }
}
