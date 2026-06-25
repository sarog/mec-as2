//$Header: /as2/de/mendelson/util/clientserver/BaseClient.java 96    23/03/26 17:58 Heller $
package de.mendelson.util.clientserver;

import de.mendelson.IProductVersion;
import de.mendelson.util.NamedThreadFactory;
import de.mendelson.util.clientserver.codec.ClientServerCodecFactory;
import de.mendelson.util.clientserver.codec.ClientServerEncoder;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import de.mendelson.util.clientserver.messages.LoginRequest;
import de.mendelson.util.clientserver.messages.LoginState;
import de.mendelson.util.clientserver.messages.QuitRequest;
import de.mendelson.util.clientserver.user.User;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.UnorderedThreadPoolExecutor;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Abstract client for a user
 *
 * @author S.Heller
 * @version $Revision: 96 $
 */
public class BaseClient {

    private ClientType clientType = ClientType.UNSPECIFIED;

    private Logger logger = Logger.getAnonymousLogger();
    private final ClientSessionHandler clientSessionHandler;
    private IoSession session = null;
    private final NioSocketConnector connector;
    private ExecutorFilter executorFilter = null;
    //use an ordered thred pool - the order for the write process is important for
    //the serialization/deserialization process
    private static final UnorderedThreadPoolExecutor THREAD_POOL_EXECUTOR
            = new UnorderedThreadPoolExecutor(
                    6,
                    16,
                    30,
                    TimeUnit.SECONDS,
                    new NamedThreadFactory("client-server-clientside-exec")
            );
    /**
     * Set default timeout for sync requests to 30s
     */
    public static final long TIMEOUT_SYNC_RECEIVE = TimeUnit.SECONDS.toMillis(60);
    public static final long TIMEOUT_SYNC_SEND = TimeUnit.SECONDS.toMillis(60);
    private final IProductVersion productVersion;

    /**
     * User that is connected
     */
    private User user = null;

    public BaseClient(ClientSessionHandlerCallback callback, ClientType clientType,
            IProductVersion productVersion) {
        //determine the number of CPU cores + 1, this is the default for the threads in the NIO processor
        int defaultIoThreads = Runtime.getRuntime().availableProcessors() + 1;
        int ioThreadNumber = Math.max(6, defaultIoThreads);
        this.connector = new NioSocketConnector(ioThreadNumber);
        this.clientType = clientType;
        this.clientSessionHandler = new ClientSessionHandler(callback);
        this.productVersion = productVersion;
    }

    /**
     * Indicates if server log messages should be displayed in the client or
     * simply ignored
     */
    public void setDisplayServerLogMessages(boolean flag) {
        this.clientSessionHandler.setDisplayServerLogMessages(flag);
    }

    public boolean getDisplayServerLogMessages() {
        return (this.clientSessionHandler.getDisplayServerLogMessages());
    }

    /**
     * Logs something to the clients log
     */
    public void log(Level logLevel, String message) {
        this.logger.log(logLevel, message);
    }

    public void setLogger(Logger logger) {
        if (logger != null) {
            this.logger = logger;
        }
    }

    /**
     * Are client and server running n the same machine?
     */
    public boolean serverRunsLocal() {
        if (!this.isConnected()) {
            return (false);
        }
        InetAddress serviceAddress = ((InetSocketAddress) this.session.getServiceAddress()).getAddress();
        InetAddress localAddress = ((InetSocketAddress) this.session.getLocalAddress()).getAddress();
        return (serviceAddress.getHostAddress().equals(localAddress.getHostAddress()));
    }

    public LoginState login(String user, char[] passwd, String clientId) {
        if (!this.isConnected()) {
            throw new IllegalStateException("[Client-Server communication] BaseClient.login: "
                    + "Not connected to a server. Please connect first.");
        }
        LoginRequest login = new LoginRequest(this.clientType);
        login.setPasswd(passwd);
        login.setUsername(user);
        login.setClientId(clientId);
        LoginState state = (LoginState) this.sendSync(login);
        if (state != null) {
            if( state.getState() == LoginState.STATE_INCOMPATIBLE_CLIENT) {
                this.clientSessionHandler.getCallback().clientIsIncompatible(state.getStateDetails());
            }
            return (state);
        }else{
            LoginState newState = new LoginState();
            newState.setState(LoginState.STATE_REJECTED);
            return( newState );
        }
    }

    /**
     * checks if the client is connected
     */
    public boolean isConnected() {
        return (this.session != null && this.session.isConnected());
    }

    /**
     * checks if the client is connected and the user logged in
     */
    public boolean isConnectedAndLoggedIn() {
        return (this.session != null && this.session.isConnected() && this.user != null);
    }

    public synchronized boolean connect(InetSocketAddress hostAddress, long timeout) {
        if (this.isConnected()) {
            throw new IllegalStateException("[Client-Server communication] BaseClient.connect: "
                    + "Already connected to " + hostAddress + ". Please disconnect first.");
        }
        try {
            this.connector.setConnectTimeoutMillis(timeout);
            this.connector.setHandler(this.clientSessionHandler);
            //add SSL support
            SslFilter sslFilter = new SslFilter(this.generateSSLContext());
            sslFilter.setEnabledProtocols(ClientServer.SERVERSIDE_ACCEPTED_TLS_PROTOCOLS);
            sslFilter.setNeedClientAuth(false);
            this.connector.getFilterChain().addFirst("TLS", sslFilter);
            this.connector.getFilterChain().addLast("protocol", new ProtocolCodecFilter(
                    new ClientServerCodecFactory(this.clientSessionHandler.getCallback(),
                            this.productVersion, null)
            ));
            //The executor filter allows all following
            //filter to work on the executors thread model. Use the executor for inbound messages only,
            //send message have to be written serialized in the right order
            this.executorFilter = new ExecutorFilter(THREAD_POOL_EXECUTOR, IoEventType.MESSAGE_RECEIVED);
            this.connector.getFilterChain().addLast("executor", this.executorFilter);

            //Set send and receive buffers to 64KB, the default is 2kB. Some messages are up to 512kB 
            this.connector.getSessionConfig().setReadBufferSize(2 * ClientServerEncoder.SINGLE_PACKAGE_SIZE_IN_BYTE);
            this.connector.getSessionConfig().setMaxReadBufferSize(4 * ClientServerEncoder.SINGLE_PACKAGE_SIZE_IN_BYTE);
            this.connector.getSessionConfig().setMinReadBufferSize(ClientServerEncoder.SINGLE_PACKAGE_SIZE_IN_BYTE);
            this.connector.getSessionConfig().setSendBufferSize(2 * ClientServerEncoder.SINGLE_PACKAGE_SIZE_IN_BYTE);
            //Setting TCP_NODELAY to true disables the Nagles algorithm. This is critical 
            //for synchronous request-response patterns to avoid the 40ms-200ms buffering 
            //delay for small data packets, ensuring immediatly sending of packages
            this.connector.getSessionConfig().setTcpNoDelay(true);
            //dont let the firewall cut the connection if idle
            this.connector.getSessionConfig().setKeepAlive(true);
            ConnectFuture connectFuture = null;
            boolean connected = false;
            try {
                connectFuture = this.connector.connect(hostAddress).awaitUninterruptibly();
            } finally {
                if (connectFuture != null) {
                    if (connectFuture.isConnected()) {
                        this.session = connectFuture.getSession();
                        connected = true;
                    } else {
                        this.connector.dispose();
                        connected = false;
                    }
                }
            }
            return (connected);
        } catch (Exception e) {
            this.log(Level.WARNING, "[Client-Server communication] "
                    + "BaseClient.connect: " + e.getMessage());
            return false;
        }
    }

    /**
     * Generates the SSL context for the TLS handling
     */
    private SSLContext generateSSLContext() throws Exception {
        X509TrustManager trustManagerTrustAll = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates,
                    String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates,
                    String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new TrustManager[]{trustManagerTrustAll}, null);
        return (context);
    }

    public void broadcast(ClientServerMessage message) {
        if (!this.isConnected()) {
            throw new IllegalStateException("[Client-Server communication] "
                    + "BaseClient.broadcast: Not connected to a server. Please connect first.");
        }
        this.session.write(message);
    }

    /**
     * Sends an async message to the server and does not care for an answer
     */
    public void sendAsync(ClientServerMessage message) {
        if (!this.isConnected()) {
            throw new IllegalStateException("[Client-Server communication] BaseClient.sendAsync: "
                    + "Not connected to a server. Please connect first.");
        }
        WriteFuture future = this.session.write(message);
    }

    /**
     * Sends a sync message and throws a timeout exception if the client does
     * not answer in a proper time
     *
     */
    public ClientServerResponse sendSync(ClientServerMessage request) {
        return (this.sendSync(request, TIMEOUT_SYNC_RECEIVE));
    }

    /**
     * Sends a sync message and waits infinite for an answer. A connection
     * timeout will occur if the request could not be sent to the server anyway.
     * Once the request has been sent this will wait for an answer without any
     * timeout.
     *
     */
    public ClientServerResponse sendSyncWaitInfinite(ClientServerMessage request) {
        if (!this.isConnected()) {
            throw new IllegalStateException("[Client-Server communication] BaseClient.sendSyncWaitInfinite: "
                    + "Not connected to a server. Please connect first.");
        }
        ClientServerResponse response = null;
        request.setSyncRequest(true);
        try {
            this.clientSessionHandler.addSyncRequest(request);
            WriteFuture writeFuture = this.session.write(request);
            boolean isSent = writeFuture.await(TIMEOUT_SYNC_SEND, TimeUnit.MILLISECONDS);
            if (!isSent) {
                StringBuilder message = new StringBuilder("[Client-Server communication]")
                        .append("(").append(request.toString()).append(") ");
                message.append("Send timeout - Could not send sync request to server after " + TIMEOUT_SYNC_SEND + "ms");
                throw new TimeoutException(message.toString());
            }
            if (writeFuture.getException() != null) {
                throw (writeFuture.getException());
            }
            response = this.clientSessionHandler.waitForSyncAnswerInfinite(this.session, request.getReferenceId());
        } catch (Throwable throwable) {
            this.clientSessionHandler.syncRequestFailed(request, response, throwable);
        } finally {
            //remove the request from the map
            this.clientSessionHandler.removeSyncRequest(request);
        }
        return (response);
    }

    /**
     * Sends a sync message and throws a timeout exception if the client does
     * not answer in a proper time. The passed timeout is not the connection
     * timeout (this is set to TIMEOUT_SYNC_SEND [in ms] by default - it is the
     * sync wait timeout)
     *
     */
    public ClientServerResponse sendSync(ClientServerMessage request, long timeout) {
        if (!this.isConnected()) {
            throw new IllegalStateException("[Client-Server communication] "
                    + "BaseClient.sendSync: Not connected to a server. Please connect first.");
        }
        ClientServerResponse response = null;
        request.setSyncRequest(true);
        try {
            this.clientSessionHandler.addSyncRequest(request);
            WriteFuture writeFuture = this.session.write(request);
            boolean isSent = writeFuture.await(TIMEOUT_SYNC_SEND, TimeUnit.MILLISECONDS);
            if (!isSent) {
                StringBuilder message = new StringBuilder("[Client-Server communication]")
                        .append("(").append(request.toString()).append(") ")
                        .append("Send timeout - Could not send sync request to server after " + TIMEOUT_SYNC_SEND + "ms");
                SyncRequestTimeoutException exception = new SyncRequestTimeoutException(
                        SyncRequestTimeoutException.TIMEOUT_TYPE_SEND,
                        message.toString(), request, TIMEOUT_SYNC_SEND);
                throw exception;
            }
            if (writeFuture.getException() != null) {
                throw (writeFuture.getException());
            }
            response = this.clientSessionHandler.waitForSyncAnswer(request.getReferenceId(), timeout);
            if (response == null) {
                StringBuilder message = new StringBuilder("[Client-Server communication]");
                message.append("(").append(request.toString()).append(") ")
                        .append("Receipt timeout - Could not receive the sync response after " + timeout + "ms [");
                message.append(request.getClass().getSimpleName()).append("]");
                SyncRequestTimeoutException exception = new SyncRequestTimeoutException(
                        SyncRequestTimeoutException.TIMEOUT_TYPE_RECEIVE,
                        message.toString(), request, timeout);
                throw exception;
            }
        } catch (Throwable throwable) {
            this.clientSessionHandler.syncRequestFailed(request, response, throwable);
        } finally {
            //remove the request from the map
            this.clientSessionHandler.removeSyncRequest(request);
        }
        return (response);
    }

    /**
     * Performs a logout, closes the session
     */
    public void logout() {
        if (this.session != null) {
            if (this.session.isConnected()) {
                if (this.user == null) {
                    this.log(Level.WARNING, "[Client-Server communication] Logout failed, you are not logged in so far!");
                } else {
                    QuitRequest quitRequest = new QuitRequest();
                    quitRequest.setUser(this.user.getName());
                    this.session.write(quitRequest);
                    this.user = null;
                }
            }
            this.session.close(false);
        }
    }

    /**
     * Disconnects the connector
     */
    public void disconnect() {
        try {
            if (this.executorFilter != null) {
                this.executorFilter.destroy();
            }
            if (this.connector != null && !this.connector.isDisposed()) {
                this.connector.dispose(true);
            }
        } catch (Exception e) {
            //nop
        }
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the current user or null if there is nobody logged in
     */
    public User getUser() {
        return (this.user);
    }

    /**
     * Returns the host address if the server is connected and the client is
     * logged in- else null
     *
     * @return null if the client is not connected or not logged in to the
     * server
     */
    public InetAddress getHostAddress() {
        if (this.isConnectedAndLoggedIn()) {
            InetAddress serviceAddress = ((InetSocketAddress) this.session.getServiceAddress()).getAddress();
            return (serviceAddress);
        } else {
            return (null);
        }
    }

    /**
     * @return the clientType
     */
    public ClientType getClientType() {
        return clientType;
    }

    /**
     * Sets a new client type, this makes only sense for a reuse of the client
     */
    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

}
