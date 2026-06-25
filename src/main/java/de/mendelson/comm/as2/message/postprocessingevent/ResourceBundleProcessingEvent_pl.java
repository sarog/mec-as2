//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ResourceBundleProcessingEvent_pl.java 2     22/10/25 9:38 Heller $
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
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class ResourceBundleProcessingEvent_pl extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"event.enqueued", "Zdefiniowane zdarzenie przetwarzania końcowego ({0}) zostało umieszczone w kolejce i zostanie wykonane za kilka sekund."},
        {"eventtype.1", "Wysyłka (w kolejności)"},
        {"eventtype.2", "Wysyłka (wadliwa)"},
        {"eventtype.3", "Odbiór"},
        {"processtype.1", "Wykonanie polecenia w powłoce systemowej"},
        {"processtype.2", "Prześlij wiadomość do partnera"},
        {"processtype.3", "Przenieś wiadomość do katalogu"},
        {"event.skipped.cem", "Zdefiniowane zdarzenie przetwarzania końcowego zostało pominięte, jest to CEM."},};
}
