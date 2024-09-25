//$Header: /oftp2/de/mendelson/util/clientserver/TextClient.java 27    23/01/24 11:09 Heller $
package de.mendelson.util.clientserver;

import de.mendelson.util.NamedThreadFactory;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import de.mendelson.util.clientserver.messages.LoginRequest;
import de.mendelson.util.clientserver.messages.LoginState;
import de.mendelson.util.clientserver.messages.ServerInfo;
import de.mendelson.util.clientserver.user.User;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Sends a command to the OFTP2 server
 *
 * @author S.Heller
 * @version $Revision: 27 $
 */
public class TextClient extends BaseTextClient implements ClientsideMessageProcessor {

    private String user = null;
    private char[] password = null;
    private ConnectThread connectionThread = null;
    private String clientId = "undefined";

    public TextClient() {
        super();
        super.addMessageProcessor(this);
    }

    /**
     * Connects to the server and performs a login
     */
    public void connectAndLogin(String host,
            int clientServerCommPort, String clientId,
            String user, char[] password, long timeout,
            String connectionThreadNamePrefix) throws Throwable {
        this.user = user;
        this.password = password;
        this.clientId = clientId;
        this.connectionThread = new ConnectThread(host, clientServerCommPort, timeout);
        ExecutorService executor = Executors.newSingleThreadExecutor(
                new NamedThreadFactory(connectionThreadNamePrefix + "clientserver-connect-login"));
        executor.submit(this.connectionThread);
        executor.shutdown();
        this.connectionThread.getDoneSignal().await(timeout, TimeUnit.MILLISECONDS);
        if (this.connectionThread.getState() == ConnectThread.STATE_FAILURE) {
            throw (this.connectionThread.getException());
        } else if (this.connectionThread.getState() == ConnectThread.STATE_WORKING) {
            throw new Exception("Connection timeout");
        }
    }

    /**
     * The client received a message from the server. Overwrite this in your
     * text client implementation to process inbound messages from the server on
     * the client
     */
    @Override
    public boolean processMessageFromServer(ClientServerMessage message) {
        if (message instanceof ServerInfo) {
            if (this.getBaseClient().getDisplayServerLogMessages()) {
                ServerInfo serverInfo = (ServerInfo) message;
                this.getLogger().log(Level.CONFIG, "Remote server identified as " + serverInfo.getProductname());
            }
        } else if (message instanceof LoginRequest) {
            this.loginRequestedFromServer();
        }
        return (true);
    }

    private LoginState performLogin(String user, char[] passwd, String clientId) {
        this.clientId = clientId;
        this.password = passwd;
        LoginState state = this.getBaseClient().login(user, passwd, clientId);
        if (state.getState() == LoginState.STATE_INCOMPATIBLE_CLIENT) {
            if (this.getLogger() == null) {
                throw new RuntimeException("TextClient: No logger set.");
            }
            LoginFailedException e = new LoginFailedException(state.getStateDetails(), state.getState());
            this.connectionThread.signalFailure(e);
        } else if (state.getState() == LoginState.STATE_AUTHENTICATION_FAILURE_PASSWORD_REQUIRED) {
            if (this.getLogger() == null) {
                throw new RuntimeException("TextClient: No logger set.");
            }
            LoginFailedException e = new LoginFailedException(state.getStateDetails(), state.getState());
            this.connectionThread.signalFailure(e);
        } else if (state.getState() == LoginState.STATE_AUTHENTICATION_FAILURE) {
            if (this.getLogger() == null) {
                throw new RuntimeException("TextClient: No logger set.");
            }
            LoginFailedException e = new LoginFailedException(state.getStateDetails(), state.getState());
            this.connectionThread.signalFailure(e);
        } else if (state.getState() == LoginState.STATE_REJECTED) {
            if (this.getLogger() == null) {
                throw new RuntimeException("TextClient: No logger set.");
            }
            LoginFailedException e = new LoginFailedException(state.getStateDetails(), state.getState());
            this.connectionThread.signalFailure(e);
        }
        return (state);
    }

    @Override
    public void loginRequestedFromServer() {
        if (this.password == null) {
            this.connectionThread.signalFailure(new Exception("Server requested login but the client has no password defined!"));
            if (this.getBaseClient().getDisplayServerLogMessages()) {
                this.log(Level.INFO, "Login failed");
            }
        } else {
            //client id has been set in method performLogin
            LoginState state = this.performLogin(this.user, this.password, this.clientId);
            if (state.getState() == LoginState.STATE_AUTHENTICATION_SUCCESS) {
                User returnedUser = state.getUser();
                //login successful: pass a user to the base client
                this.getBaseClient().setUser(returnedUser);
                if (this.getBaseClient().getDisplayServerLogMessages()) {
                    this.log(Level.INFO, "Login successful");
                }
                this.connectionThread.signalSuccess();
            } else {
                this.log(Level.INFO, "Login failed");
            }
        }
        this.connectionThread.signalLoginProcessFinished();
    }

    /**
     * Performs a logout, closes the actual session
     */
    @Override
    public void logout() {
        if (this.getBaseClient().getDisplayServerLogMessages()) {
            this.log(Level.INFO, "Logging out");
        }
        super.logout();
    }

    /**
     * Returns the version of this class
     */
    public static String getVersion() {
        String revision = "$Revision: 27 $";
        return (revision.substring(revision.indexOf(":") + 1,
                revision.lastIndexOf("$")).trim());
    }

    /**
     * Allows a manual login, not supported so far
     */
    @Override
    public void performLogin() {
        throw new IllegalArgumentException("PerformLogin: manual login not implemented so far.");
    }

    @Override
    public void processSyncResponseFromServer(ClientServerResponse response) {
    }

    private class ConnectThread implements Runnable {

        public static final int STATE_WORKING = 0;
        public static final int STATE_FAILURE = 1;
        public static final int STATE_SUCCESS = 2;
        private final int clientServerCommPort;
        private final String host;
        private int state = STATE_WORKING;
        private Throwable exception = null;
        private final CountDownLatch doneSignal = new CountDownLatch(2);
        private final long timeout;

        public ConnectThread(String host, int clientServerCommPort, long timeout) {
            this.host = host;
            this.timeout = timeout;
            this.clientServerCommPort = clientServerCommPort;
        }

        @Override
        public void run() {
            try {
                long connectionStartTime = System.currentTimeMillis();
                connect(new InetSocketAddress(this.host, this.clientServerCommPort), this.timeout);
                while (this.state == STATE_WORKING) {
                    Thread.sleep(50);
                    //The whole login process takes some message to go back and forward - its not
                    //the one time timeout the thread should wait for the login to be done
                    if (connectionStartTime + (3 * this.timeout) < System.currentTimeMillis()) {
                        throw new Exception("Connection timeout in client-server connection");
                    }
                }
            } catch (Throwable e) {
                this.signalFailure(e);
            }
            this.doneSignal.countDown();
        }

        public void signalLoginProcessFinished() {
            this.doneSignal.countDown();
        }

        public void signalSuccess() {
            this.state = STATE_SUCCESS;
        }

        public void signalFailure(Throwable exception) {
            this.exception = exception;
            this.state = STATE_FAILURE;
        }

        /**
         * @return the state
         */
        public int getState() {
            return state;
        }

        /**
         * @return the exception
         */
        public Throwable getException() {
            return exception;
        }

        /**
         * @return the doneSignal
         */
        public CountDownLatch getDoneSignal() {
            return doneSignal;
        }
    }
}
