//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ResourceBundleProcessingEvent.java 6     31/03/26 9:30 Heller $
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
 * @version $Revision: 6 $
 */
public class ResourceBundleProcessingEvent extends MecResourceBundle{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"event.enqueued", "The defined postprocessing event ({0}) has been enqueued and will be executed in some seconds." },
        {"processtype." + ProcessingEventType.EXECUTE_SHELL.toInt(), "Execute shell command" },
        {"processtype." + ProcessingEventType.MOVE_TO_DIR.toInt(), "Move message to directory" },
        {"processtype." + ProcessingEventType.MOVE_TO_PARTNER.toInt(), "Forward message to partner" },
        {"eventtype." + ProcessingEventTriggerType.RECEIPT_SUCCESS.toInt(), "Receipt" },
        {"eventtype." + ProcessingEventTriggerType.SEND_FAILURE.toInt(), "Send (failed)" },
        {"eventtype." + ProcessingEventTriggerType.SEND_SUCCESS.toInt(), "Send (success)" },
        {"event.skipped.cem", "The defined postprocessing has been skipped because this is a CEM" },
    };
    
}