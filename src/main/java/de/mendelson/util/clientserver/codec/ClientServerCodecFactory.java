//$Header: /as4/de/mendelson/util/clientserver/codec/ClientServerCodecFactory.java 10    19/02/26 9:19 Heller $
package de.mendelson.util.clientserver.codec;

import de.mendelson.IProductVersion;
import de.mendelson.util.clientserver.ClientSessionHandlerCallback;
import de.mendelson.util.systemevents.SystemEventManager;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Factory that handles encoding/decoding of the requests
 * @author S.Heller
 * @version $Revision: 10 $
 */
public class ClientServerCodecFactory implements ProtocolCodecFactory {

    private final ProtocolEncoder encoder;
    private final ProtocolDecoder decoder;

    /**
     * 
     * @param clientCallback This may be null if there is no callback or this is not a client instance
     * @param systemEventManager The system event manager, might be null
     */
    public ClientServerCodecFactory( ClientSessionHandlerCallback clientCallback, 
            IProductVersion productVersion, SystemEventManager systemEventManager) {
        this.encoder = new ClientServerEncoder(productVersion.getMagicNumber());
        this.decoder = new ClientServerDecoder(clientCallback, productVersion.getMagicNumber(), systemEventManager);
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession is) throws Exception {
        return( this.encoder);
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession is) throws Exception {
        return( this.decoder );
    }
}
