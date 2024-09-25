//$Header: /as4/de/mendelson/util/httpconfig/gui/ResourceBundleDisplayHTTPConfiguration_de.java 4     12/11/17 1:29p Heller $ 
package de.mendelson.util.httpconfig.gui;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/** 
 * ResourceBundle to localize gui entries
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class ResourceBundleDisplayHTTPConfiguration_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "Serverseitige HTTP Konfiguration"},
        {"reading.configuration", "Lese HTTP Konfiguration..."},
        {"button.ok", "Schliessen" },
        {"label.info.configfile", "Dieser Dialog zeigt Ihnen die serverseitige HTTP/S Konfiguration. Sie können die Ports, die Chiffren und die Protokolle in der Datei \"{0}\" auf dem Server konfigurieren. Bitte starten Sie den Server neu, um Änderungen wirksam zu machen." },
        {"tab.misc", "Allgemein"},
        {"tab.cipher", "SSL/TLS Chiffren"},
        {"tab.protocols", "SSL/TLS Protokolle"},
        {"no.ssl.enabled", "Der TLS/SSL Support wurde im unterliegenden HTTP Server nicht eingeschaltet.\nBitte modifizieren Sie die Konfigurationsdatei {0}\nentsprechend der Dokumentation und starten Sie den Server neu." },
        {"info.cipher", "Die folgende Chiffren werden vom unterliegenden HTTP Server unterstützt.\nWelche unterstützt werden, hängt von Ihrer eingesetzten Java VM ab (aktuell {1}).\nSie können einzelne Chiffren in der Konfigurationsdatei\n\"{0}\" deaktivieren." },
        {"info.protocols", "Die folgende Protokolle werden vom unterliegenden HTTP Server unterstützt.\nWelche unterstützt werden, hängt von Ihrer eingesetzten Java VM ab (aktuell {1}).\nSie können einzelne Protokolle in der Konfigurationsdatei\n\"{0}\" deaktivieren." },
        {"no.embedded.httpserver", "Sie haben den unterliegenden HTTP Server nicht gestartet.\nEs ist keine Information verfügbar." },
    };
}