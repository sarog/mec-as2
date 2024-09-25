//$Header: /as2/de/mendelson/comm/as2/sendorder/ResourceBundleSendOrderReceiver.java 3     9/25/17 1:27p Heller $
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
public class ResourceBundleSendOrderReceiver extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"async.mdn.wait", "{0}: Will wait for async MDN until {1}." },  
        {"max.retry.reached", "{0}: The max retry has been reached, transmission canceled." },
        {"retry", "{0}: Will retry to send transmission after {1}s, retry {2}/{3}." },
        {"as2.send.disabled", "** The system will not send any AS2 message/MDN because the number of parallel outbound connections is set to 0. Please modify these settings in the server settings dialog to enable sending again **" },
        {"outbound.connection.prepare.mdn", "{0}: Preparing outbound MDN connection to \"{1}\", active connections: {2}/{3}." },
        {"outbound.connection.prepare.message", "{0}: Preparing outbound AS2 message connection to \"{1}\", active connections: {2}/{3}." },
        {"as2.send.newmaxconnections", "The number of parallel outbound connections has been set to {0}."},
        {"send.connectionsstillopen", "You have reduced the number of outbound connections to {0} but currently there are still {1} outbound connections." },
    };
    
}