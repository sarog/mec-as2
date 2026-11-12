//$Header: /oftp2/de/mendelson/util/security/memkeystore/MendelsonInMemoryProvider.java 1     10/10/25 12:05 Heller $
package de.mendelson.util.security.memkeystore;

import java.security.Provider;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Provider for the in memory keystore
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class MendelsonInMemoryProvider extends Provider {

    private static final String NAME = "mendelsonInMemory";
    public static final String KEYSTORE_INMEMORY = "INMEMORY";
    private static MendelsonInMemoryProvider provider;

    public MendelsonInMemoryProvider() {
        super(NAME, 1.0, "mendelson InMemory KeyStore Provider");
        put("KeyStore." + KEYSTORE_INMEMORY, InMemoryKeyStore.class.getName());
    }

    public String getName() {
        return (NAME);
    }

    /**
     * Returns the MendelsonInMemory provider instance.
     *
     * @return The MendelsonInMemory provider instance.
     */
    public static MendelsonInMemoryProvider instance() {
        if (provider != null) {
            return (provider);
        } else {
            provider = new MendelsonInMemoryProvider();
            return (provider);

        }
    }
}
