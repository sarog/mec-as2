//$Header: /oftp2/de/mendelson/util/clientserver/codec/ClientServerCodecFactory.java 2     24.09.12 10:29 Heller $
package de.mendelson.util.clientserver.codec;

import java.util.logging.Logger;
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
 * @version $Revision: 2 $
 */
public class ClientServerCodecFactory implements ProtocolCodecFactory {

    private final ProtocolEncoder encoder;
    private final ProtocolDecoder decoder;

    public ClientServerCodecFactory( Logger logger) {
        this.encoder = new ClientServerEncoder();
        this.decoder = new ClientServerDecoder(logger);
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession is) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession is) throws Exception {
        return decoder;
    }
}
