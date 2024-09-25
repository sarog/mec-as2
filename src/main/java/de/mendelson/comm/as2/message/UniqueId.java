//$Header: /as2/de/mendelson/comm/as2/message/UniqueId.java 12    2/11/23 15:52 Heller $
package de.mendelson.comm.as2.message;

import de.mendelson.comm.as2.AS2ServerVersion;
import de.mendelson.comm.as2.server.ServerInstance;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Class that ensures that a requested number is unique in the VM
 *
 * @author S.Heller
 * @version $Revision: 12 $
 */
public class UniqueId {

    private final static AtomicLong CURRENT_MESSAGE_ID = new AtomicLong(0);
    private final static AtomicLong CURRENT_ID = new AtomicLong(System.currentTimeMillis());
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");

    /**
     * Creates a new message id for the AS2 messages
     */
    public static String createMessageId(String senderId, String receiverId) {
        long id = CURRENT_MESSAGE_ID.getAndAdd(1);
        StringBuilder idBuffer = new StringBuilder();
        idBuffer.append(AS2ServerVersion.getProductNameShortcut().replace(' ', '_'))
                .append("-")
                .append(ServerInstance.ID)
                .append("-")
                .append(String.valueOf(System.currentTimeMillis()))
                .append("-")
                .append(String.valueOf(id))
                .append("@");
        if (senderId != null) {
            idBuffer.append(senderId);
        } else {
            idBuffer.append("unknown");
        }
        idBuffer.append("_");
        if (receiverId != null) {
            idBuffer.append(receiverId);
        } else {
            idBuffer.append("unknown");
        }
        return (idBuffer.toString());
    }

    /**
     * Creates a new id in the format yyyyMMddHHmm-nn
     */
    public static String createId() {
        long id = CURRENT_ID.getAndAdd(1);
        StringBuilder idBuffer = new StringBuilder();
        idBuffer.append(DATE_FORMAT.format(new Date()))
                .append("-")
                .append(id);
        return (idBuffer.toString());
    }
}
