//$Header: /as2/de/mendelson/util/clientserver/AnonymousTextClient.java 11    23/03/26 8:03 Heller $
package de.mendelson.util.clientserver;

import de.mendelson.IProductVersion;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.clientserver.messages.ClientServerResponse;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Text Client implementation that sends anonymous messages (no login required).
 *
 * @author S.Heller
 * @version $Revision: 11 $
 */
public class AnonymousTextClient extends BaseTextClient implements AutoCloseable{

    public AnonymousTextClient( ClientType clientType, IProductVersion productVersion) {
        super(clientType, productVersion);
        super.addMessageProcessor(new ClientsideMessageProcessor() {

            @Override
            public boolean processMessageFromServer(ClientServerMessage message) {
                return (true);
            }

            @Override
            public void processSyncResponseFromServer(ClientServerResponse response) {                
            }
        });
    }

    /**
     * no login required, anonymous request
     */
    @Override
    public void performLogin() {
    }
   
    
    @Override
    public void disconnected() {
        super.disconnect();
    }

    /**Makes this an auto closeable client*/
    @Override
    public void close() throws Exception {
        super.disconnect();
    }
    
    /**A sync request failed
     * 
     * @param request The sync request that was not successful if it was a request, might be null
     * @param response The sync request that was not successful if it was a response, might be null
     * @param throwable The exception that occurred
     */
    @Override
    public void syncRequestFailed(ClientServerMessage request, ClientServerMessage response, Throwable throwable){    
        super.syncRequestFailed(request, response, throwable);
        throw new RuntimeException(throwable);
    }
    
}
