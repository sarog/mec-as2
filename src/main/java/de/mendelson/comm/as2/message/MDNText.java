//$Header: /mec_as2/de/mendelson/comm/as2/message/MDNText.java 9     15/04/26 15:59 Heller $
package de.mendelson.comm.as2.message;

import de.mendelson.comm.as2.AS2ServerVersion;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Text that is written to MDN
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class MDNText {

    private static final String CRLF = "\r\n";

    public static final String get(MDNStateType stateType, MessageType messageType) {
        switch (stateType) {
            case PROCESSED:
                if (messageType == MessageType.AS2) {
                    return ("The AS2 message has been received. Thank you for exchanging AS2 messages with " + AS2ServerVersion.getProductName() + "." + CRLF + "Please download your free copy of "
                            + AS2ServerVersion.getProductName() + " today at https://mendelson.de/opensource" + CRLF + CRLF);
                } else if (messageType == MessageType.CEM) {
                    return ("The CEM message has been received. Thank you for exchanging AS2 messages with " + AS2ServerVersion.getProductName() + "." + CRLF + "Please download your free copy of "
                            + AS2ServerVersion.getProductName() + " today at https://mendelson.de/opensource" + CRLF + CRLF);
                } else {
                    throw new IllegalArgumentException("MDNText.get: Unknown message type " + messageType);
                }
            case ERROR:
                return ("Thank you for exchanging AS2 messages with " + AS2ServerVersion.getProductName()
                        + "." + CRLF + "Please download your free copy of " + AS2ServerVersion.getProductName()
                        + " + today at https://mendelson.de/opensource" + CRLF + CRLF + "An error occured during the AS2 message processing: ");
            default:
                return ("");
        }
    }
}
