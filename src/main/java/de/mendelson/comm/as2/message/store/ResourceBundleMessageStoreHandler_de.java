//$Header: /as2/de/mendelson/comm/as2/message/store/ResourceBundleMessageStoreHandler_de.java 7     1.12.10 12:50 Heller $
package de.mendelson.comm.as2.message.store;
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
 * @version $Revision: 7 $
 */
public class ResourceBundleMessageStoreHandler_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"message.error.stored", "{0}: Eingebettete Nachricht wurde unter \"{1}\" gespeichert." },
        {"message.error.raw.stored", "{0}: Die Übertragungsdaten wurden unter \"{1}\" gespeichert." },        
        {"dir.createerror", "Das Verzeichnis \"{0}\" konnte nicht erstellt werden." },        
        {"comm.success", "{0}: AS2 Kommunikation erfolgreich, Nutzdaten {1} wurde nach \"{2}\" verschoben." },
        {"outboundstatus.written", "{0}: Statusdatei für Ausgangstransaktion wurde geschrieben nach \"{1}\"."},
    };
    
}