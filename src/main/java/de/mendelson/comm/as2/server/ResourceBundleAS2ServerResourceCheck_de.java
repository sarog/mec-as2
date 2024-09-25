//$Header: /as2/de/mendelson/comm/as2/server/ResourceBundleAS2ServerResourceCheck_de.java 2     10-02-16 10:36a Heller $
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
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class ResourceBundleAS2ServerResourceCheck_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] contents = {
        {"port.in.use", "Der Port {0} wird von einem anderen Prozess belegt."},
        {"warning.few.cpucores", "Das System hat nur {0} Prozessorkern(e) erkannt, die dem mendelson AS2 Serverprozess zugeordnet sind. Mit dieser geringen Anzahl von Prozessorkernen kann die Ausf�hrungsgeschwindigkeit sehr gering sein und einige Funktionen k�nnten nur eingeschr�nkt funktinoieren. Bitte weisen Sie dem mendelson AS2 Serverprozess mindestens 4 Prozessorkerne zu."},
        {"warning.low.maxheap", "Das System hat nur ungef�hr {0} verf�gbaren Heap Speicher gefunden, der dem mendelson AS2 Serverprozess zugeordnet wurde. (Keine Sorge, das sind ca 10% weniger als Sie im Startscript angegeben haben). Bitte weisen Sie dem mendelson AS2 Serverprozess mindestens 1GB Heap Speicher zu."},};
}
