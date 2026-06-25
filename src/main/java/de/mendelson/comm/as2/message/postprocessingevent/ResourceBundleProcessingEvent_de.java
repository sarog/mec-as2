//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ResourceBundleProcessingEvent_de.java 8     31/03/26 9:30 Heller $
package de.mendelson.comm.as2.message.postprocessingevent;
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
 * @version $Revision: 8 $
 */
public class ResourceBundleProcessingEvent_de extends MecResourceBundle{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"event.enqueued", "Das definierte Nachbearbeitungsereignis ({0}) wurde in die Warteschlange gestellt und wird in einigen Sekunden ausgeführt." },
        {"processtype." + ProcessingEventType.EXECUTE_SHELL.toInt(), "Kommando auf der Systemshell ausführen" },
        {"processtype." + ProcessingEventType.MOVE_TO_DIR.toInt(), "Nachricht in Verzeichnis verschieben" },
        {"processtype." + ProcessingEventType.MOVE_TO_PARTNER.toInt(), "Nachricht an Partner weiterleiten" },
        {"eventtype." + ProcessingEventTriggerType.RECEIPT_SUCCESS.toInt(), "Empfang" },
        {"eventtype." + ProcessingEventTriggerType.SEND_FAILURE.toInt(), "Versand (fehlerhaft)" },
        {"eventtype." + ProcessingEventTriggerType.SEND_SUCCESS.toInt(), "Versand (in Ordnung)" },
        {"event.skipped.cem", "Das definierte Nachbearbeitungsereignis wurde übersprungen, dies ist ein CEM" },
    };
    
}