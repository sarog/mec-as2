//$Header: /as2/de/mendelson/comm/as2/server/ResourceBundleAS2ServerProcessing_de.java 7     12/06/17 11:41a Heller $
package de.mendelson.comm.as2.server;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 *
 * @author S.Heller
 * @version $Revision: 7 $
 */
public class ResourceBundleAS2ServerProcessing_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"send.failed", "Versand fehlgeschlagen"},
        {"unable.to.process", "Fehler beim Verarbeiten auf dem Server: {0}"},
        {"server.shutdown", "Der Benutzer {0} fährt den Server herunter."},
        {"sync.mdn.sent", "{0}: Synchrone MDN als Antwort auf {1} versandt."},
        {"invalid.request.from", "Eine ungültige Anfrage ist eingegangen. Sie wird nicht verarbeitet, weil kein as2-from Header vorhanden ist."},
        {"invalid.request.to", "Eine ungültige Anfrage ist eingegangen. Sie wird nicht verarbeitet, weil kein as2-to Header vorhanden ist."},
        {"invalid.request.messageid", "Eine ungültige Anfrage ist eingegangen. Sie wird nicht verarbeitet, weil kein message-id Header vorhanden ist."},        
        {"info.mdn.inboundfiles", "Für die eingegangene MDN war es nicht möglich, die referenzierte AS2 Nachricht zu ermitteln.\n[Eingegangene MDN (Daten): {0}]\n[Eingegangene MDN (Header): {1}]"},
    };
}