//$Header: /as2/de/mendelson/comm/as2/sendorder/ResourceBundleSendOrderReceiver_de.java 3     9/25/17 1:27p Heller $
package de.mendelson.comm.as2.sendorder;
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
 * @version $Revision: 3 $
 */
public class ResourceBundleSendOrderReceiver_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"async.mdn.wait", "{0}: Warte auf asynchrone MDN bis {1}." },
        {"max.retry.reached", "{0}: Die maximale Anzahl von Wiederholungsversuchen wurde erreicht, die Transaktion wird beendet." },
        {"retry", "{0}: Versuche eine erneute Übertragung nach {1}s, Wiederholung {2}/{3}." },
        {"as2.send.disabled", "** Die Anzahl der parallelen ausgehenden Verbindungen ist auf 0 gestellt - das System wird weder MDN noch AS2 Nachrichten versenden. Bitte ändern Sie diese Einstellung in den Servereinstellungen, wenn Sie senden wollen **" },        
        {"outbound.connection.prepare.mdn", "{0}: Bereite ausgehende MDN Verbindung vor nach to \"{1}\", aktive Verbindungen: {2}/{3}." },
        {"outbound.connection.prepare.message", "{0}: Bereite ausgehende AS2 Nachrichtenverbindung vor nach \"{1}\", aktive Verbindungen: {2}/{3}." },
        { "as2.send.newmaxconnections", "Die Anzahl der parallel ausgehenden Verbindungen wurde auf {0} gesetzt."},
        {"send.connectionsstillopen", "Sie haben die Anzahl der ausgehenden Verbindungen auf {0} reduziert, aber zur Zeit gibt es noch {1} ausgehende Verbindungen." },
    };
    
}