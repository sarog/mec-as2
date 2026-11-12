//$Header: /as2/de/mendelson/comm/as2/server/ResourceBundleAS2ServerResourceCheck_pl.java 1     24/09/25 10:39 Heller $
package de.mendelson.comm.as2.server;

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
public class ResourceBundleAS2ServerResourceCheck_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"port.in.use", "Port {0} jest zajęty przez inny proces."},
		{"warning.few.cpucores", "System rozpoznał tylko {0} rdzeni procesora przypisanych do procesu serwera mendelson AS2. Przy tak małej liczbie rdzeni procesora szybkość wykonywania może być bardzo niska, a niektóre funkcje mogą działać tylko w ograniczonym zakresie. Należy przypisać co najmniej 4 rdzenie procesora do procesu serwera mendelson AS2."},
		{"warning.low.maxheap", "System znalazł tylko około {0} dostępnej pamięci sterty przydzielonej do procesu serwera mendelson AS2. (Nie martw się, to około 10% mniej niż określono w skrypcie startowym). Przydziel co najmniej 1 GB pamięci sterty procesowi serwera mendelson AS2."},
	};
}
