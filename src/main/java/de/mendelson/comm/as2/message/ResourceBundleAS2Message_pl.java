//$Header: /as2/de/mendelson/comm/as2/message/ResourceBundleAS2Message_pl.java 1     24/09/25 10:35 Heller $
package de.mendelson.comm.as2.message;

import de.mendelson.util.MecResourceBundle;
/*
* Copyright (C) mendelson-e-commerce GmbH Berlin Germany
*
* This software is subject to the license agreement set forth in the license.
* Please read and agree to all terms before using this software.
* Other product and brand names are trademarks of their respective owners.
*/

/**
* ResourceBundle to localize a mendelson product
* @author S.Heller
* @version $Revision: 1 $
*/
public class ResourceBundleAS2Message_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"compression.0", "Nieznany"},
		{"compression.1", "Brak"},
		{"compression.2", "ZLIB"},
		{"direction.0", "Nieznany"},
		{"direction.1", "Nadchodzące"},
		{"direction.2", "Począwszy od"},
		{"encryption.0", "Nieznany"},
		{"encryption.1", "Brak szyfrowania"},
		{"encryption.10", "AES-256 (CBC)"},
		{"encryption.11", "RC4-40"},
		{"encryption.12", "RC4-56"},
		{"encryption.13", "RC4-128"},
		{"encryption.14", "RC4"},
		{"encryption.15", "DES"},
		{"encryption.16", "AES-128 (CBC, RSAES-OAEP)"},
		{"encryption.17", "AES-192 (CBC RSAES-OAEP)"},
		{"encryption.18", "AES-256 (CBC, RSAES-OAEP)"},
		{"encryption.19", "AES-128 (GCM)"},
		{"encryption.2", "3DES"},
		{"encryption.20", "AES-192 (GCM)"},
		{"encryption.21", "AES-256 (GCM)"},
		{"encryption.22", "AES-128 (CCM)"},
		{"encryption.23", "AES-192 (CCM)"},
		{"encryption.24", "AES-256 (CCM)"},
		{"encryption.25", "CHACHA20-POLY1305"},
		{"encryption.26", "KAMELIA-128 (CBC)"},
		{"encryption.27", "KAMELIA-192 (CBC)"},
		{"encryption.28", "CAMELLIA-256 (CBC)"},
		{"encryption.29", "AES-128 (GCM, RSAES-OAEP)"},
		{"encryption.3", "RC2-40"},
		{"encryption.30", "AES-192 (GCM RSAES-OAEP)"},
		{"encryption.31", "AES-256 (GCM, RSAES-OAEP)"},
		{"encryption.4", "RC2-64"},
		{"encryption.5", "RC2-128"},
		{"encryption.6", "RC2-196"},
		{"encryption.7", "RC2"},
		{"encryption.8", "AES-128 (CBC)"},
		{"encryption.9", "AES-192 (CBC)"},
		{"encryption.99", "Nieznany"},
		{"signature.0", "Nieznany"},
		{"signature.1", "Brak podpisu"},
		{"signature.10", "SHA-256 (RSASSA-PSS)"},
		{"signature.11", "SHA-384 (RSASSA-PSS)"},
		{"signature.12", "SHA-512 (RSASSA-PSS)"},
		{"signature.13", "SHA3-224"},
		{"signature.14", "SHA3-256"},
		{"signature.15", "SHA3-384"},
		{"signature.16", "SHA3-512"},
		{"signature.17", "SHA3-224 (RSASSA-PSS)"},
		{"signature.18", "SHA3-256 (RSASSA-PSS)"},
		{"signature.19", "SHA3-384 (RSASSA-PSS)"},
		{"signature.2", "SHA-1"},
		{"signature.20", "SHA3-512 (RSASSA-PSS)"},
		{"signature.21", "SPHINCS+"},
		{"signature.22", "DILITHIUM"},
		{"signature.3", "MD5"},
		{"signature.4", "SHA-224"},
		{"signature.5", "SHA-256"},
		{"signature.6", "SHA-384"},
		{"signature.7", "SHA-512"},
		{"signature.8", "SHA-1 (RSASSA-PSS)"},
		{"signature.9", "SHA-224 (RSASSA-PSS)"},
	};
}
