//$Header: /oftp2/de/mendelson/util/security/memkeystore/InMemoryKeyStore.java 5     13/10/25 11:16 Heller $
package de.mendelson.util.security.memkeystore;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Fast im memory keystore implementation
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public final class InMemoryKeyStore extends KeyStoreSpi {

    public static final String KEYSTORE_INMEMORY = MendelsonInMemoryProvider.KEYSTORE_INMEMORY;

    private final Map<String, InMemoryEntry> STORE = new ConcurrentHashMap<String, InMemoryEntry>();

    public static class InMemoryEntry {
        private final Key key;
        private final Certificate[] chain;
        private final Date creationDate;

        public InMemoryEntry(Key key, Certificate[] chain) {
            this.key = key;
            this.chain = chain;
            this.creationDate = new Date();
        }

        public Key getKey() {
            return key;
        }

        public Certificate[] getCertificateChain() {
            return chain;
        }

        public Date getCreationDate() {
            return creationDate;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof InMemoryEntry)) return false;
            InMemoryEntry other = (InMemoryEntry) obj;
            return Objects.equals(key, other.key)
                    && Arrays.equals(chain, other.chain)
                    && Objects.equals(creationDate, other.creationDate);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(key, creationDate);
            result = 31 * result + Arrays.hashCode(chain);
            return result;
        }
    }

    @Override
    public Key engineGetKey(String alias, char[] password) {
        InMemoryEntry e = STORE.get(alias);
        return e != null ? e.getKey() : null;
    }

    @Override
    public Certificate[] engineGetCertificateChain(String alias) {
        InMemoryEntry e = STORE.get(alias);
        return e != null ? e.getCertificateChain() : null;
    }

    @Override
    public Certificate engineGetCertificate(String alias) {
        Certificate[] chain = engineGetCertificateChain(alias);
        return (chain != null && chain.length > 0) ? chain[0] : null;
    }

    @Override
    public void engineSetKeyEntry(String alias, Key key, char[] password, Certificate[] chain) {
        STORE.put(alias, new InMemoryEntry(key, chain));
    }

    @Override
    public void engineSetKeyEntry(String alias, byte[] key, Certificate[] chain) {
        throw new UnsupportedOperationException("Binary key entries not supported");
    }

    @Override
    public void engineSetCertificateEntry(String alias, Certificate cert) {
        STORE.put(alias, new InMemoryEntry(null, new Certificate[]{cert}));
    }

    @Override
    public boolean engineContainsAlias(String alias) {
        return STORE.containsKey(alias);
    }

    @Override
    public void engineDeleteEntry(String alias) {
        STORE.remove(alias);
    }

    @Override
    public Enumeration<String> engineAliases() {
        return Collections.enumeration(STORE.keySet());
    }

    @Override
    public int engineSize() {
        return STORE.size();
    }

    @Override
    public boolean engineIsKeyEntry(String alias) {
        InMemoryEntry e = STORE.get(alias);
        return e != null && e.getKey() != null;
    }

    @Override
    public boolean engineIsCertificateEntry(String alias) {
        InMemoryEntry e = STORE.get(alias);
        return e != null && e.getKey() == null && e.getCertificateChain() != null;
    }

    @Override
    public String engineGetCertificateAlias(Certificate cert) {
        for (Map.Entry<String, InMemoryEntry> e : STORE.entrySet()) {
            Certificate[] chain = e.getValue().getCertificateChain();
            if (chain != null && chain.length > 0 && chain[0].equals(cert)) {
                return e.getKey() != null ? null : e.getKey().toString();
            }
        }
        return null;
    }

    @Override
    public void engineStore(OutputStream stream, char[] password) {
        throw new UnsupportedOperationException("InMemoryKeyStore does not persist");
    }

    @Override
    public void engineLoad(InputStream stream, char[] password) {
        this.STORE.clear();
    }

    @Override
    public KeyStore.Entry engineGetEntry(String alias, KeyStore.ProtectionParameter protParam) throws KeyStoreException {
        InMemoryEntry e = STORE.get(alias);
        if (e == null) return null;

        if (e.getKey() != null) {
            return new KeyStore.PrivateKeyEntry((PrivateKey) e.getKey(), e.getCertificateChain());
        } else if (e.getCertificateChain() != null && e.getCertificateChain().length > 0) {
            return new KeyStore.TrustedCertificateEntry(e.getCertificateChain()[0]);
        }
        return null;
    }

    @Override
    public void engineSetEntry(String alias, KeyStore.Entry entry, KeyStore.ProtectionParameter protParam) throws KeyStoreException {
        if (entry instanceof KeyStore.PrivateKeyEntry) {
            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) entry;
            STORE.put(alias, new InMemoryEntry(pkEntry.getPrivateKey(), pkEntry.getCertificateChain()));
        } else if (entry instanceof KeyStore.TrustedCertificateEntry) {
            KeyStore.TrustedCertificateEntry certEntry = (KeyStore.TrustedCertificateEntry) entry;
            STORE.put(alias, new InMemoryEntry(null, new Certificate[]{certEntry.getTrustedCertificate()}));
        } else {
            throw new KeyStoreException("Unsupported entry type: " + entry.getClass());
        }
    }

    @Override
    public boolean engineEntryInstanceOf(String alias, Class<? extends KeyStore.Entry> entryClass) {
        InMemoryEntry e = STORE.get(alias);
        if (e == null) return false;
        if (entryClass == KeyStore.PrivateKeyEntry.class) return e.getKey() != null;
        if (entryClass == KeyStore.TrustedCertificateEntry.class) return e.getKey() == null && e.getCertificateChain() != null;
        return false;
    }

    @Override
    public Date engineGetCreationDate(String alias) {
        InMemoryEntry e = STORE.get(alias);
        return e != null ? e.getCreationDate() : null;
    }
}