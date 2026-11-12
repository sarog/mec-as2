//$Header: /oftp2/de/mendelson/util/security/FastKeyStoreUtil.java 5     10/10/25 13:41 Heller $
package de.mendelson.util.security;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Enumeration;
import org.bouncycastle.asn1.pkcs.MacData;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.Pfx;
import org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.pkcs.PKCS12PfxPdu;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Utility class to allow fast access to pkcs#12 keystore by reducing the
 * security
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class FastKeyStoreUtil {

    private FastKeyStoreUtil() {
    }

    public static void displayKeystoreInfo(byte[] keystoreData) throws Exception {
        PKCS12PfxPdu pfx = new PKCS12PfxPdu(keystoreData);
        final Pfx asn1Structure = pfx.toASN1Structure();
        final MacData macData = asn1Structure.getMacData();
        final BigInteger iterationCount = macData.getIterationCount();
        System.out.println("Keystore iterations: " + iterationCount);
    }

    public static byte[] serializeToFastPKCS12(KeyStore sourceKeystore, char[] password) throws Exception {
        FastPKCS12KeyStoreSpi spi = new FastPKCS12KeyStoreSpi();
        Enumeration<String> aliases = sourceKeystore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            if (sourceKeystore.isKeyEntry(alias)) {
                PrivateKey key = (PrivateKey) sourceKeystore.getKey(alias, password);
                Certificate[] chain = sourceKeystore.getCertificateChain(alias);
                spi.engineSetKeyEntry(alias, key, password, chain);
            } else if (sourceKeystore.isCertificateEntry(alias)) {
                Certificate cert = sourceKeystore.getCertificate(alias);
                spi.engineSetCertificateEntry(alias, cert);
            }
        }
        try (ByteArrayOutputStream memOut = new ByteArrayOutputStream()) {
            spi.engineStore(memOut, password);
            return (memOut.toByteArray());
        }
    }

    /**
     * Allows to store an unsecure keystore in valid PKCS#12 format for better
     * performance. As this is stored in the database only this should be no
     * problem. Never use this to store the keystore to an external file
     */
    public static class FastPKCS12KeyStoreSpi extends PKCS12KeyStoreSpi {

        public FastPKCS12KeyStoreSpi() {
            super(new DefaultJcaJceHelper(),
                    // Key encryption
                    PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC2_CBC,
                    // Certificate encryption
                    PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC2_CBC);
        }

    }

}
