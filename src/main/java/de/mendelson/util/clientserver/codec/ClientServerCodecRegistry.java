//$Header: /as2/de/mendelson/util/clientserver/codec/ClientServerCodecRegistry.java 4     13/03/26 10:09 Heller $
package de.mendelson.util.clientserver.codec;

import de.mendelson.util.clientserver.messages.ClientServerMessage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Registry that stores the used keys and links them to the allowed class. This
 * is also a whitelist for the decoding
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public abstract sealed class ClientServerCodecRegistry permits ClientServerCodecRegistryImpl{

    private static final Map<Integer, Class<? extends ClientServerMessage>> INT_TO_CLASS
            = new ConcurrentHashMap<Integer, Class<? extends ClientServerMessage>>();
    private static final Map<Class<? extends ClientServerMessage>, Integer> CLASS_TO_INT
            = new ConcurrentHashMap<Class<? extends ClientServerMessage>, Integer>();

    protected ClientServerCodecRegistry() {
    }

    protected void register(Integer key, Class<? extends ClientServerMessage> clazz) {
        INT_TO_CLASS.put(key, clazz);
        CLASS_TO_INT.put(clazz, key);
    }

    /**
     * Will return null if the passed class is not registered
     *
     * @param clazz
     * @return
     */
    protected Integer get(Class<? extends ClientServerMessage> clazz) {
        return (CLASS_TO_INT.get(clazz));
    }

    /**
     * Will return null if the passed name is not registered
     *
     * @param clazz
     * @return
     */
    protected Class<? extends ClientServerMessage> get(Integer key) {
        return (INT_TO_CLASS.get(key));
    }
   
}
