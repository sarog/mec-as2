//$Header: /oftp2/de/mendelson/util/systemevents/notification/ResourceBundleNotification.java 16    12/12/23 14:44 Heller $
package de.mendelson.util.systemevents.notification;

import de.mendelson.util.MecResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * ResourceBundle to localize the mendelson products - if you want to localize
 * eagle to your language, please contact us: localize@mendelson.de
 *
 * @author S.Heller
 * @version $Revision: 16 $
 */
public class ResourceBundleNotification extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {        
        {"module.name", "[MAIL NOTIFICATION]" },
        {"test.message.send", "A test email has been sent to {0}."},
        {"test.message.debug", "\nThe send mail process failed.\n"},
        {"misc.message.send", "A notification mail has been sent to {0} ({1}-{2}-{3})."},
        {"misc.message.send.failed", "The notification send process to {0} failed"},
        {"notification.about.event", "This notification is related to the system event from {0}.\nSeverity: {1}\nOrigin: {2}\nType: {3}\nId: {4}"},
        {"notification.summary", "Summary of {0} system events"},
         {"notification.summary.info", "You receive this summary message because you have defined\n"
            + "a limited number of notifications per time unit. To get details of each event,\n"
            + "please start the client and navigate to \"File-System Events\".\n"
            + "Then enter the unique number of the event in the search mask."},
        {"misc.message.summary.send", "A notification mail has been sent to {0} (summary)"},
        {"misc.message.summary.failed", "The notification send process to {0} failed (summary)"},
        {"do.not.reply", "Please do not reply to this mail."},
        {"authorization.none", "NONE" },
        {"authorization.oauth2", "OAUTH2" },
        {"authorization.oauth2.authorizationcode", "Authorization code" },
        {"authorization.oauth2.clientcredentials", "Client credentials" },
        {"authorization.credentials", "User/password" },
    };

}
