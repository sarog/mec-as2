//$Header: /mec_as2/de/mendelson/util/systemevents/notification/NotificationImplAS2.java 38    15/04/26 12:44 Heller $
package de.mendelson.util.systemevents.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.comm.as2.AS2ServerVersion;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.AS2Tools;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.oauth2.OAuth2Config;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManagerImplAS2;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Performs the notification for an event
 *
 * @author S.Heller
 * @version $Revision: 38 $
 */
public class NotificationImplAS2 extends Notification {

    private static final Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private static final String MODULE_NAME;
    private static final MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleNotification.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        MODULE_NAME = rb.getResourceString("module.name");
    }

    public NotificationImplAS2() {
        PreferencesAS2 preferences = new PreferencesAS2();
        this.setTimeout(
                Long.parseLong(preferences.get(PreferencesAS2.NOTIFICATION_SMTP_CONNECTION_TIMEOUT)),
                Long.parseLong(preferences.get(PreferencesAS2.NOTIFICATION_SMTP_TIMEOUT)));
    }

    @Override
    @JsonIgnore
    public String getTestMessageDebugStr() {
        return (rb.getResourceString("test.message.debug"));
    }

    /**
     * Sends out the notification
     */
    @Override
    public void sendNotification(List<SystemEvent> systemEventsToNotifyUserOf, NotificationData notificationData) {
        if (systemEventsToNotifyUserOf.size() <= notificationData.getMaxNotificationsPerMin()) {
            //send out single notifications
            for (SystemEvent event : systemEventsToNotifyUserOf) {
                try {
                    this.sendMail(AS2ServerVersion.getProductName(), event, notificationData, false);
                    logger.fine(MODULE_NAME + " " + rb.getResourceString("misc.message.send",
                            new Object[]{
                                notificationData.getNotificationMail(),
                                event.originToTextLocalized(),
                                event.categoryToTextLocalized(),
                                event.typeToTextLocalized()
                            }));
                    SystemEvent notificationSuccessEvent = new SystemEvent(SystemEvent.Severity.INFO,
                            SystemEvent.Origin.SYSTEM, SystemEvent.Type.NOTIFICATION_SEND_SUCCESS);
                    notificationSuccessEvent.setSubject(rb.getResourceString("misc.message.send",
                            new Object[]{
                                notificationData.getNotificationMail(),
                                event.originToTextLocalized(),
                                event.categoryToTextLocalized(),
                                event.typeToTextLocalized()
                            }));
                    notificationSuccessEvent.setBody(rb.getResourceString("notification.about.event",
                            new Object[]{
                                event.getHumanReadableTimestamp(),
                                event.severityToTextLocalized(),
                                event.originToTextLocalized(),
                                event.typeToTextLocalized(),
                                event.getId()
                            }));
                    SystemEventManagerImplAS2.instance().newEvent(notificationSuccessEvent);
                } catch (Exception e) {
                    SystemEvent notificationProblemEvent = new SystemEvent(SystemEvent.Severity.WARNING, SystemEvent.Origin.SYSTEM,
                            SystemEvent.Type.NOTIFICATION_SEND_FAILED);
                    notificationProblemEvent.setSubject(
                            rb.getResourceString("misc.message.send.failed",
                                    notificationData.getNotificationMail()));
                    notificationProblemEvent.setBody(
                            "[" + e.getClass().getSimpleName() + "]: " + e.getMessage() + "\n\n"
                            + rb.getResourceString("notification.about.event",
                                    new Object[]{
                                        event.getHumanReadableTimestamp(),
                                        event.severityToTextLocalized(),
                                        event.originToTextLocalized(),
                                        event.typeToTextLocalized(),
                                        event.getId()
                                    }));
                    SystemEventManagerImplAS2.instance().newEvent(notificationProblemEvent);
                }
            }
        } else {
            //send out summary of system events
            SystemEvent event = new SystemEvent(SystemEvent.Severity.INFO, SystemEvent.Origin.SYSTEM, SystemEvent.Type.OTHER);
            event.setSubject(rb.getResourceString("notification.summary",
                    new Object[]{
                        String.valueOf(systemEventsToNotifyUserOf.size())
                    }));
            StringBuilder infoText = new StringBuilder();
            infoText.append(rb.getResourceString("notification.summary.info"));

            StringBuilder summary = new StringBuilder();
            for (SystemEvent singleEvent : systemEventsToNotifyUserOf) {
                summary.append("[").append(singleEvent.getHumanReadableTimestamp()).append("]: ")
                        .append("(").append(singleEvent.severityToTextLocalized().toUpperCase()).append(")")
                        .append(" ").append(singleEvent.originToTextLocalized())
                        .append("/").append(singleEvent.typeToTextLocalized())
                        .append("\n").append("id: ").append(singleEvent.getId())
                        .append("\n").append(singleEvent.getSubject())
                        .append("\n\n");
            }
            event.setBody(
                    infoText.toString()
                    + "\n\n\n"
                    + summary.toString());
            try {
                this.sendMail(AS2ServerVersion.getProductName(), event, notificationData, false);
                SystemEvent notificationSuccessEvent = new SystemEvent(SystemEvent.Severity.INFO,
                        SystemEvent.Origin.SYSTEM, SystemEvent.Type.NOTIFICATION_SEND_SUCCESS);
                notificationSuccessEvent.setSubject(rb.getResourceString("misc.message.summary.send",
                        new Object[]{
                            notificationData.getNotificationMail(),}));
                notificationSuccessEvent.setBody(summary.toString());
                SystemEventManagerImplAS2.instance().newEvent(notificationSuccessEvent);
            } catch (Exception e) {
                SystemEvent notificationSuccessEvent = new SystemEvent(SystemEvent.Severity.WARNING,
                        SystemEvent.Origin.SYSTEM, SystemEvent.Type.NOTIFICATION_SEND_FAILED);
                notificationSuccessEvent.setSubject(rb.getResourceString("misc.message.summary.failed",
                        new Object[]{
                            notificationData.getNotificationMail()}));
                notificationSuccessEvent.setBody(
                        "[" + e.getClass().getSimpleName() + "]: " + e.getMessage() + "\n\n"
                        + summary.toString());
                SystemEventManagerImplAS2.instance().newEvent(notificationSuccessEvent);
            }
        }
    }

    /**
     * Sends a test notification
     *
     */
    @Override
    public void sendTest(String userName, String processOriginHost,
            NotificationData notificationData) throws Exception {
        String templateName = "template_notification_test";
        Properties replacement = new Properties();
        replacement.setProperty("${PRODUCTNAME}", AS2ServerVersion.getProductName());
        replacement.setProperty("${HOST}", SystemEventManagerImplAS2.instance().getHostname());
        replacement.setProperty("${USER}", System.getProperty("user.name"));
        replacement.setProperty("${MAILHOST}", notificationData.getMailServer());
        replacement.setProperty("${MAILPORT}", String.valueOf(notificationData.getMailServerPort()));
        String connectionSecurity = "NONE";
        if (notificationData.getConnectionSecurity() == NotificationData.SECURITY_TLS) {
            connectionSecurity = "TLS";
        } else if (notificationData.getConnectionSecurity() == NotificationData.SECURITY_START_TLS) {
            connectionSecurity = "STARTTLS";
        }
        replacement.setProperty("${CONNECTIONSECURITY}", connectionSecurity);
        String authorization = rb.getResourceString("authorization.none");
        if (notificationData.isUsesSMTPAuthCredentials()) {
            authorization = rb.getResourceString("authorization.credentials");
        } else if (notificationData.isUsesSMTPAuthOAuth2()) {
            authorization = rb.getResourceString("authorization.oauth2");
            if (notificationData.getOAuth2Config().getRFCMethod() == OAuth2Config.METHOD_RFC6749_4_1) {
                authorization = authorization + " (" + rb.getResourceString("authorization.oauth2.authorizationcode") + ")";
            } else if (notificationData.getOAuth2Config().getRFCMethod() == OAuth2Config.METHOD_RFC6749_4_4) {
                authorization = authorization + " (" + rb.getResourceString("authorization.oauth2.clientcredentials") + ")";
            }
        }
        replacement.setProperty("${AUTHORIZATION}", authorization);
        SystemEvent event = new SystemEvent(SystemEvent.Severity.INFO, SystemEvent.Origin.USER,
                SystemEvent.Type.CONNECTIVITY_TEST);
        event.readFromNotificationTemplate(templateName, replacement);
        event.setProcessOriginHost(processOriginHost);
        event.setUser(userName);
        String traceStr = this.sendMail(AS2ServerVersion.getProductName(), event, notificationData, true);
        logger.fine(MODULE_NAME + " " 
                + rb.getResourceString("test.message.send", notificationData.getNotificationMail()));
        if (traceStr != null && !traceStr.trim().isEmpty()) {
            traceStr = MODULE_NAME + " " + traceStr.replace("\n", "\n" + MODULE_NAME + " ");
        }
        logger.fine(traceStr);
        SystemEventManagerImplAS2.instance().newEvent(event);
    }

    @Override
    @JsonIgnore
    public String getNotificationSubjectServerIdentification() {
        return ("[" + AS2ServerVersion.getProductName() + "@"
                + SystemEventManagerImplAS2.instance().getHostname() + "]");
    }

    @Override
    @JsonIgnore
    public String getNotificationFooter() {
        return (rb.getResourceString("do.not.reply"));
    }

}
