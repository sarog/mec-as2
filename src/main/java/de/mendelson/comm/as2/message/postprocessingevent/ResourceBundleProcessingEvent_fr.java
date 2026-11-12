//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ResourceBundleProcessingEvent_fr.java 7     31/03/26 9:30 Heller $
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
 * @version $Revision: 7 $
 */
public class ResourceBundleProcessingEvent_fr extends MecResourceBundle{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"event.enqueued", "L''événement de post-traitement défini a été demandé ({0}) et sera exécuté en quelques secondes." },
        {"processtype." + ProcessingEventType.EXECUTE_SHELL.toInt(), "Exécuter une commande shell" },
        {"processtype." + ProcessingEventType.MOVE_TO_DIR.toInt(), "Déplacer le message vers le répertoire" },
        {"processtype." + ProcessingEventType.MOVE_TO_PARTNER.toInt(), "Transmettre le message au partenaire" },
        {"eventtype." + ProcessingEventTriggerType.RECEIPT_SUCCESS.toInt(), "Réception" },
        {"eventtype." + ProcessingEventTriggerType.SEND_FAILURE.toInt(), "Expédition (incorrect)" },
        {"eventtype." + ProcessingEventTriggerType.SEND_SUCCESS.toInt(), "Expédition (tout droit)" },
         {"event.skipped.cem", "L''événement de post-traitement défini a été ignoré, il s''agit d''un CEM." },
    };
    
}