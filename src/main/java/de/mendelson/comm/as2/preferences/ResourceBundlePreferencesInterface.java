//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferencesInterface.java 2     8/11/23 11:07 Heller $
package de.mendelson.comm.as2.preferences;

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
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class ResourceBundlePreferencesInterface extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {        
        {"label.showsecurityoverwrite", "Partner management: Overwrite security settings of the local station" },
        {"label.showsecurityoverwrite.help", "<HTML><strong>Overwrite security settings of the local station</strong><br><br>"
            + "If you switch this option on, an additional tab is displayed for each partner in the partner "
            + "administration. Here you can define the private keys that are always used for incoming "
            + "and outgoing calls for this partner - regardless of the settings of the respective local station.<br>"
            + "This option allows you to use different private keys for each partner at the same local station.<br><br>"
            + "This is an option for compatibility with other AS2 products - some systems have exactly these "
            + "requirements, but they require the configuration of relationships between partners "
            + "and not individual partners."
            + "</HTML>"},
        {"label.showhttpheader", "Partner management: Allow to configure the HTTP headers"},
        {"label.showhttpheader.help", "<HTML><strong>Allow to configure the HTTP headers</strong><br><br>"
            + "If you activate this option, an additional tab is displayed in the partner administration "
            + "for each partner, in which you can define user-defined HTTP headers "
            + "for sending data to this partner."
            + "</HTML>"},
        {"label.showquota", "Partner management: Allow to configure quota notification"},
        {"label.outboundstatusfiles", "Write outbound transaction status files"},
        {"label.cem", "Allow certificate exchange (CEM)"},
    };
}
