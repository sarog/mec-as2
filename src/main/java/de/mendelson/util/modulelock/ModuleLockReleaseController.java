//$Header: /as2/de/mendelson/util/modulelock/ModuleLockReleaseController.java 4     2/11/23 14:03 Heller $
package de.mendelson.util.modulelock;

import de.mendelson.util.NamedThreadFactory;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.systemevents.SystemEventManager;
import java.sql.Connection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Heartbeat control for the exclusive modules: If a client does not refresh its
 * lock all n seconds and this is detected by this class the exclusive lock is
 * deleted in the server. This might be necessary if a client has been shut down
 * without deleting it's exclusive lock on a module (or a connection has been
 * cut or something else)
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class ModuleLockReleaseController {

    /**
     * Logger to log information to
     */
    private final Logger logger;
    private final LockReleaseThread releaseThread = new LockReleaseThread();
    private final SystemEventManager systemEventManager;

    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(
            new NamedThreadFactory("module-lock-heartbeat"));
    private final IDBDriverManager dbDriverManager;

    public ModuleLockReleaseController(IDBDriverManager dbDriverManager,
            SystemEventManager systemEventManager,
            String serverLoggerName) throws Exception {
        this.dbDriverManager = dbDriverManager;
        this.logger = Logger.getLogger(serverLoggerName);
        this.systemEventManager = systemEventManager;
    }

    /**
     * Starts the embedded task that guards the log
     */
    public void startLockReleaseControl() {
        this.scheduledExecutor.scheduleWithFixedDelay(this.releaseThread, 0, 15, TimeUnit.SECONDS);
    }

    public class LockReleaseThread implements Runnable {

        public LockReleaseThread() {
        }

        @Override
        public void run() {
            Connection runtimeConnectionNoAutoCommit = null;
            try {
                runtimeConnectionNoAutoCommit = dbDriverManager.getConnectionWithoutErrorHandling(IDBDriverManager.DB_RUNTIME);
                runtimeConnectionNoAutoCommit.setAutoCommit(false);
                ModuleLock.releaseAllLocksOlderThan(dbDriverManager, runtimeConnectionNoAutoCommit, TimeUnit.SECONDS.toMillis(45));
            } catch (Throwable e) {
                systemEventManager.systemFailure(e);
            } finally {
                if (runtimeConnectionNoAutoCommit != null) {
                    try {
                        runtimeConnectionNoAutoCommit.close();
                    } catch (Exception e) {
                        systemEventManager.systemFailure(e);
                    }
                }
            }
        }
    }
}
