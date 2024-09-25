//$Header: /as2/de/mendelson/comm/as2/server/ResourceBundleAS2Server_de.java 10    11/29/17 11:09a Heller $
package de.mendelson.comm.as2.server;

import de.mendelson.comm.as2.AS2ServerVersion;
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
 * @version $Revision: 10 $
 */
public class ResourceBundleAS2Server_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"fatal.limited.strength", "Diese Java VM unterstützt nicht die notwendige Schlüssellänge. Bitte installieren Sie die \"Unlimited jurisdiction key strength policy\" Dateien, bevor Sie den " + AS2ServerVersion.getProductName() + " Server starten." },
        {"server.willstart", "{0}"},
        {"server.started", AS2ServerVersion.getFullProductName() + " gestartet in {0} ms."},
        {"server.already.running", "Eine " + AS2ServerVersion.getProductName() + " Instanz scheint bereits zu laufen.\nEs könnte jedoch auch sein, dass eine vorherige Instanz nicht korrekt beendet wurde." + " Wenn Sie sicher sind, dass keine andere Instanz läuft,\nlöschen Sie bitte die Lock Datei \"{0}\"\n(Start Datum {1}) und starten den Server erneut."},
        {"server.nohttp", "Der integrierte HTTP Server wurde nicht gestartet." },    
        {"bind.exception", "{0}\nSie haben einen Port definiert, der derzeit von einem anderen Prozess in Ihrem System verwendet wird.\nDies kann der Client-Server-Port oder der HTTP/S-Port sein, den Sie in der HTTP-Konfiguration definiert haben.\nBitte ändern Sie Ihre Konfiguration oder stoppen Sie den anderen Prozess, bevor Sie den {1} verwenden."},
    };
}