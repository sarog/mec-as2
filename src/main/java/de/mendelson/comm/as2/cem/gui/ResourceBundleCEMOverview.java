//$Header: /as2/de/mendelson/comm/as2/cem/gui/ResourceBundleCEMOverview.java 11    2/11/23 15:52 Heller $
package de.mendelson.comm.as2.cem.gui;
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
 * @version $Revision: 11 $
 */
public class ResourceBundleCEMOverview extends MecResourceBundle{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "Certificate Exchange Manager" },
        {"button.sendcem", "New exchange" },
        {"button.requestdetails", "Request details" },
        {"button.responsedetails", "Response details" },
        {"button.exit", "Close" },
        {"button.cancel", "Cancel" },
        {"button.refresh", "Refresh" },
        {"button.remove", "Delete" },
        {"header.state", "Answer" },
        {"header.category", "Used for" },
        {"header.requestdate", "Requested at" },
        {"header.initiator", "From" },
        {"header.receiver", "To" },
        {"label.certificate", "Certificate:"},
        {"header.alias", "Certificate"},
        {"header.activity", "System activity" },
        {"activity.waitingforprocessing", "Waiting for processing" },
        {"activity.waitingforanswer", "Waiting for answer" },
        {"activity.waitingfordate", "Waiting for activation date ({0})" },
        {"activity.activated", "None - Activated at {0}" },
        {"activity.none", "None" },
        {"tab.certificate", "Certificate Information" },
        {"tab.reasonforrejection", "Reason for rejection" },
    };
    
}