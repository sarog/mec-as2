//$Header: /as4/de/mendelson/util/clientserver/ClientServer.java 69    19/02/26 16:44 Heller $
package de.mendelson.util.clientserver;

import de.mendelson.IProductVersion;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.NamedThreadFactory;
import de.mendelson.util.clientserver.codec.ClientServerCodecFactory;
import de.mendelson.util.clientserver.codec.ClientServerEncoder;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.systemevents.SystemEventManager;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.mina.core.filterchain.IoFilter.NextFilter;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.UnorderedThreadPoolExecutor;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Server root for the mendelson client/server architecture
 *
 * @author S.Heller
 * @version $Revision: 69 $
 */
public class ClientServer {

    private long startTime = 0;
    private final Logger logger;
    private ClientServerSessionHandler sessionHandler = null;
    private int port;
    private String productName = "";
    public static final String[] SERVERSIDE_ACCEPTED_TLS_PROTOCOLS
            = new String[]{"TLSv1.2"};
    private final MecResourceBundle rb;
    private final ClientServerTLS clientserverTLS;
    private final SystemEventManager systemEventManager;
    //core pool size = 8
    //max pool size = 64    
    //use an unorderes pool for the receipt process
    private final UnorderedThreadPoolExecutor THREAD_POOL_RECEIVE_EXECUTOR
            = new UnorderedThreadPoolExecutor(
                    8,
                    64,
                    30,
                    TimeUnit.SECONDS,
                    new NamedThreadFactory("client-server-serverside-receive")
            );
    //One thread processes the write orders, this is just to prevent a block of the IO thread.
    //Once the send queue is full the IO thread is blocked (backpressure)
    private final ThreadPoolExecutor THREAD_POOL_SEND_EXECUTOR = new ThreadPoolExecutor(
            4,
            8,
            30,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(50),
            new NamedThreadFactory("client-server-serverside-send"),
            new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
            try {
                //caller should wait if the queue is full
                if (!executor.isShutdown()) {
                    //put waits for space and then adds it to the queue. This blocks the caller
                    executor.getQueue().put(runnable);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    );
    /**
     * Stores unique numbers for the client-server messages
     */
    private static final AtomicLong CLIENT_SERVER_MESSAGE_REFERENCE_ID = new AtomicLong(0);
    private final IProductVersion productVersion;

    /**
     * Creates a new instance of Server
     */
    public ClientServer(Logger logger, int port, ClientServerTLS clientserverTLS,
            IProductVersion productVersion, SystemEventManager systemEventManager) {
        this.port = port;
        this.logger = logger;
        this.clientserverTLS = clientserverTLS;
        this.productVersion = productVersion;
        this.systemEventManager = systemEventManager;
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleClientServer.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
    }

    /**
     * Returns the next unique reference id, thread safe
     */
    public static long getNextClientServerMessageReferenceId() {
        return (CLIENT_SERVER_MESSAGE_REFERENCE_ID.addAndGet(1L));
    }

    public void setSessionHandler(ClientServerSessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public void setClientServerPort(int port) {
        this.port = port;
    }

    /**
     * Returns the start time of the server
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Sends a message object to all connected clients
     */
    public void broadcastToClients(ClientServerMessage message) {
        if (this.sessionHandler != null) {
            sessionHandler.broadcast(message);
        }
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Finally starts the server
     */
    public void start() throws Exception {
        this.logger.log(Level.INFO, this.rb.getResourceString("clientserver.start",
                new Object[]{
                    this.productName,
                    String.valueOf(this.port)
                }));
        if (this.sessionHandler != null) {
            this.sessionHandler.setProductName(this.productName);
        } else {
            this.logger.log(Level.WARNING, "No session handler assigned to the client server!");
        }
        //determine the number of CPU cores + 1, this is the default for the threads in the NIO processor
        int defaultIoThreads = Runtime.getRuntime().availableProcessors() + 1;
        int ioThreadNumber = Math.max(8, defaultIoThreads);
        NioSocketAcceptor acceptor = new NioSocketAcceptor(ioThreadNumber);
        //add SSL support
        SslFilter tlsFilter = new SslFilter(this.clientserverTLS.createSSLContext());
        //If client authentication is disabled the client certificate must not be in the servers keystore
        tlsFilter.setNeedClientAuth(false);
        //allow defined TLS protocols only for the client-server connection
        tlsFilter.setEnabledProtocols(SERVERSIDE_ACCEPTED_TLS_PROTOCOLS);
        acceptor.getFilterChain().addFirst("TLS", tlsFilter);
        acceptor.getFilterChain().addLast("protocol",
                new ProtocolCodecFilter(new ClientServerCodecFactory(null,
                        this.productVersion, this.systemEventManager)));
        //..set up the thread pool for inbound messages. Use the executor for inbound messages only,
        //send message have to be written serialized and synchronized in the right order
        acceptor.getFilterChain().addLast("receive_exec",
                new ExecutorFilter(this.THREAD_POOL_RECEIVE_EXECUTOR, IoEventType.MESSAGE_RECEIVED));
        acceptor.getFilterChain().addLast("send_exec",
                new SendExecutorFilter(this.THREAD_POOL_SEND_EXECUTOR));
        if (this.sessionHandler != null) {
            acceptor.setHandler(this.sessionHandler);
        }
        //Set send and receive buffers to the right size of the size of the expected chunks
        acceptor.getSessionConfig().setReadBufferSize(2 * ClientServerEncoder.SINGLE_PACKAGE_SIZE_IN_BYTE);
        acceptor.getSessionConfig().setMaxReadBufferSize(4 * ClientServerEncoder.SINGLE_PACKAGE_SIZE_IN_BYTE);
        acceptor.getSessionConfig().setMinReadBufferSize(ClientServerEncoder.SINGLE_PACKAGE_SIZE_IN_BYTE);
        acceptor.getSessionConfig().setSendBufferSize(2 * ClientServerEncoder.SINGLE_PACKAGE_SIZE_IN_BYTE);
        //Setting TCP_NODELAY to true disables the Nagles algorithm. This is critical 
        //for synchronous request-response patterns to avoid the 40ms-200ms buffering 
        //delay for small data packets, ensuring immediatly sending of packages
        acceptor.getSessionConfig().setTcpNoDelay(true);
        //dont let the firewall cut the connection if idle, this is on TCP level
        acceptor.getSessionConfig().setKeepAlive(true);
        //finally bind the protocol handler to the port
        acceptor.bind(new InetSocketAddress(this.port));
        this.logger.log(Level.INFO, this.rb.getResourceString("clientserver.started", this.productName));
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Returns the current sessions on this server
     */
    public List<IoSession> getSessions() {
        if (this.sessionHandler != null) {
            return (this.sessionHandler.getSessions());
        } else {
            List<IoSession> emptyList = new ArrayList<IoSession>();
            return (Collections.unmodifiableList(emptyList));
        }
    }

    /**
     * A specialized filter for the client-server interface designed to decouple
     * the write operation (downstream) from the business logic thread. In MINA
     * 2.x, the standard ExecutorFilter does not support direct thread pooling
     * for write events.
     */
    public class SendExecutorFilter extends IoFilterAdapter {

        private final ThreadPoolExecutor executor;

        public SendExecutorFilter(ThreadPoolExecutor executor) {
            this.executor = executor;
        }

        @Override
        public void filterWrite(final NextFilter nextFilter,
                final IoSession session,
                final WriteRequest writeRequest) throws Exception {
            //pass the job to the executor
            this.executor.execute(new Runnable() {
                @Override
                public void run() {
                    //ensure that one session is not handled by multiple threads, the 
                    //TLS filter is really sensitive..
                    synchronized (session) {
                        try {
                            //pass the WRITE order to the next filter (codec --> TLS)
                            nextFilter.filterWrite(session, writeRequest);
                        } catch (Exception e) {
                            //Error processing in the thread
                            session.getFilterChain().fireExceptionCaught(e);
                        }
                    }
                }
            });
        }
    }

}
