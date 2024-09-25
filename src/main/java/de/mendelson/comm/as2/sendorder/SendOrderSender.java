//$Header: /as2/de/mendelson/comm/as2/sendorder/SendOrderSender.java 10    8/08/17 11:08a Heller $
package de.mendelson.comm.as2.sendorder;

import de.mendelson.comm.as2.message.AS2Message;
import de.mendelson.comm.as2.message.AS2MessageCreation;
import de.mendelson.comm.as2.notification.Notification;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.AS2Tools;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.security.cert.CertificateManager;
import java.io.File;
import java.sql.Connection;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Sender class that enqueues send orders
 *
 * @author S.Heller
 * @version $Revision: 10 $
 */
public class SendOrderSender {

    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private MecResourceBundle rb;
    private Connection configConnection;
    private Connection runtimeConnection;
    private SendOrderAccessDB sendOrderAccess;

    public SendOrderSender(Connection configConnection, Connection runtimeConnection) {
        //Load default resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleSendOrderSender.class.getName());
        } //load up resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        this.configConnection = configConnection;
        this.runtimeConnection = runtimeConnection;
        this.sendOrderAccess = new SendOrderAccessDB(this.configConnection, this.runtimeConnection);

    }

    /**
     * @return NULL in the case of an error
     */
    public AS2Message send(CertificateManager certificateManager, Partner sender,
            Partner receiver, File[] files, String[] originalFilenames, String userdefinedId) {
        try {
            long startProcessTime = System.currentTimeMillis();
            AS2MessageCreation messageCreation = new AS2MessageCreation(certificateManager, certificateManager);
            messageCreation.setLogger(this.logger);
            messageCreation.setServerResources(this.configConnection, this.runtimeConnection);
            AS2Message message = messageCreation.createMessage(sender, receiver, files, originalFilenames, userdefinedId);
            StringBuilder filenames = new StringBuilder();
            for (File file : files) {
                if (filenames.length() > 0) {
                    filenames.append(", ");
                }
                filenames.append(file.getName());
            }
            this.logger.log(Level.INFO,
                    rb.getResourceString("message.packed",
                            new Object[]{
                                message.getAS2Info().getMessageId(),
                                filenames.toString(),
                                receiver.getName(),
                                AS2Tools.getDataSizeDisplay(message.getRawDataSize()),
                                AS2Tools.getTimeDisplay(System.currentTimeMillis() - startProcessTime),
                                (userdefinedId==null?"--":userdefinedId)
                            }),
                    message.getAS2Info());
            SendOrder order = new SendOrder();
            order.setReceiver(receiver);
            order.setMessage(message);
            order.setSender(sender);
            order.setUserdefinedId(userdefinedId);
            this.send(order);
            return (message);
        } catch (Throwable e) {
            logger.severe( rb.getResourceString("sendoder.sendfailed",
                    new Object[]{ 
                        e.getClass().getSimpleName(),
                        e.getMessage()
                    }
                    ));
            Notification.systemFailure(this.configConnection, this.runtimeConnection, e);
            e.printStackTrace();
        }
        return (null);
    }

    /**
     * Process a file object
     * http://tools.ietf.org/html/draft-meadors-multiple-attachments-ediint-08
     * 2.1: Multiple attachments in EDI-INT MUST NOT be used for batch
     * processing of EDI or other documents which are not inter-related. For
     * example numerous EDI purchase orders for different products must not be
     * sent in a multipart/related envelope but instead be transmitted in
     * separate, individual EDI-INT messages.
     *
     * That is the reason why the mendelson AS2 sends the AS2 messages using
     * single attachments, even if the software is capable of sending data using
     * the optional profile MA - multiple attachments.
     *
     * 
     * between https://tools.ietf.org/html/draft-meadors-multiple-attachments-ediint-10
     * and https://tools.ietf.org/html/draft-meadors-multiple-attachments-ediint-11 the "MUST" 
     * changed to "SHOULD" - which means that batch EDI data processing is possible since then
     *
     * @return NULL in the case of an error
     */
    public AS2Message send(CertificateManager certificateManager, Partner sender,
            Partner receiver, File file, String userdefinedId) {
        return (this.send(certificateManager, sender, receiver, new File[]{file}, null, userdefinedId));
    }

    /**
     * Enqueues an existing send order
     */
    public void resend(SendOrder order, long nextExecutionTime) {
        this.sendOrderAccess.rescheduleOrder(order, nextExecutionTime);
    }

    /**
     * Enqueues a send order
     */
    public void send(SendOrder order) {
        this.sendOrderAccess.add(order);
    }
}