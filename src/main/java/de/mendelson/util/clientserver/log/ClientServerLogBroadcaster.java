//$Header: /as4/de/mendelson/util/clientserver/log/ClientServerLogBroadcaster.java 2     24/03/26 11:14 Heller $
package de.mendelson.util.clientserver.log;

import de.mendelson.util.NamedThreadFactory;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.clientserver.messages.ServerLogListMessage;
import de.mendelson.util.clientserver.messages.ServerLogMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Class that collects log messages and broadcasts them to clients on a time
 * base
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class ClientServerLogBroadcaster {

    private static final int WAIT_TIME_IN_MS = 250;
    private static final int MAX_LOGMESSAGES_PER_SEND = 10;

    private final BlockingQueue<ServerLogMessage> logQueue = new LinkedBlockingQueue<ServerLogMessage>();

    private final ScheduledExecutorService scheduledExecutor
            = Executors.newSingleThreadScheduledExecutor(
                    new NamedThreadFactory("log-broadcast"));
    private final Consumer<ClientServerMessage> consumer;

    public ClientServerLogBroadcaster(Consumer<ClientServerMessage> consumer) {
        this.consumer = consumer;
        scheduledExecutor.scheduleWithFixedDelay(new BroadcastThread(), 500, WAIT_TIME_IN_MS, TimeUnit.MILLISECONDS);
    }

    /**
     * Enqueue log message instead of sending immediately
     */
    public void enqueueLogMessage(Level level, String message, String[] parameter) {
        ServerLogMessage serverMessage = new ServerLogMessage();
        serverMessage.setLevel(level);
        serverMessage.setMessage(message);
        serverMessage.setParameter(parameter);
        this.logQueue.offer(serverMessage);
    }

    public class BroadcastThread implements Runnable {

        @Override
        public void run() {
            try {
                processQueue();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        /**
         * Worker loop: batch messages and broadcast
         */
        private void processQueue() throws Exception {
            ServerLogMessage firstLogMessage = logQueue.poll();
            if (firstLogMessage != null) {
                List<ServerLogMessage> batchLogList = new ArrayList<ServerLogMessage>();
                batchLogList.add(firstLogMessage);
                logQueue.drainTo(batchLogList, MAX_LOGMESSAGES_PER_SEND - 1);
                ServerLogListMessage logListMessage = new ServerLogListMessage();
                logListMessage.setLogMessageList(batchLogList);
                consumer.accept(logListMessage);
                //If still more log messages pending, keep draining immediately
                while (logQueue.size() > MAX_LOGMESSAGES_PER_SEND) {
                    List<ServerLogMessage> nextBatchLogList = new ArrayList<ServerLogMessage>(MAX_LOGMESSAGES_PER_SEND);
                    logQueue.drainTo(nextBatchLogList, MAX_LOGMESSAGES_PER_SEND);
                    ServerLogListMessage nextLogListMessage = new ServerLogListMessage();
                    nextLogListMessage.setLogMessageList(nextBatchLogList);
                    consumer.accept(nextLogListMessage);
                }
            }
        }
    }

}
