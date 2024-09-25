//$Header: /as2/de/mendelson/util/systemevents/notification/NotificationImplAS2.java 33    12/12/23 14:47 Heller $
package de.mendelson.util.systemevents.notification;

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
 * @version $Revision: 33 $
 */
public class NotificationImplAS2 extends Notification {

    private final static Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private final String MODULE_NAME;
    /**
     * localize your output
     */
    private MecResourceBundle rb = null;

    public NotificationImplAS2() {
        //Load resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleNotification.class.getName());
        } //load up  resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        MODULE_NAME = rb.getResourceString( "module.name");
        PreferencesAS2 preferences = new PreferencesAS2();
        this.setTimeout(
                Long.valueOf(preferences.get(PreferencesAS2.NOTIFICATION_SMTP_CONNECTION_TIMEOUT)).longValue(),
                Long.valueOf(preferences.get(PreferencesAS2.NOTIFICATION_SMTP_TIMEOUT)).longValue());                
    }

    @Override
    public String getTestMessageDebugStr() {
        return (this.rb.getResourceString("test.message.debug"));
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
                    logger.fine(MODULE_NAME + " " + this.rb.getResourceString("misc.message.send",
                            new Object[]{
                                notificationData.getNotificationMail(),
                                event.originToTextLocalized(),
                                event.categoryToTextLocalized(),
                                event.typeToTextLocalized()
                            }));
                    SystemEvent notificationSuccessEvent = new SystemEvent(SystemEvent.SEVERITY_INFO,
                            SystemEvent.ORIGIN_SYSTEM, SystemEvent.TYPE_NOTIFICATION_SEND_SUCCESS);
                    notificationSuccessEvent.setSubject(this.rb.getResourceString("misc.message.send",
                            new Object[]{
                                notificationData.getNotificationMail(),
                                event.originToTextLocalized(),
                                event.categoryToTextLocalized(),
                                event.typeToTextLocalized()
                            }));
                    notificationSuccessEvent.setBody(this.rb.getResourceString("notification.about.event",
                            new Object[]{
                                event.getHumanReadableTimestamp(),
                                event.severityToTextLocalized(),
                                event.originToTextLocalized(),
                                event.typeToTextLocalized(),
                                event.getId()
                            }));
                    SystemEventManagerImplAS2.instance().newEvent(notificationSuccessEvent);
                } catch (Exception e) {
                    SystemEvent notificationProblemEvent = new SystemEvent(SystemEvent.SEVERITY_WARNING, SystemEvent.ORIGIN_SYSTEM,
                            SystemEvent.TYPE_NOTIFICATION_SEND_FAILED);
                    notificationProblemEvent.setSubject(
                            this.rb.getResourceString("misc.message.send.failed",
                                    notificationData.getNotificationMail()));
                    notificationProblemEvent.setBody(
                            "[" + e.getClass().getSimpleName() + "]: " + e.getMessage() + "\n\n"
                            + this.rb.getResourceString("notification.about.event",
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
            SystemEvent event = new SystemEvent(SystemEvent.SEVERITY_INFO, SystemEvent.ORIGIN_SYSTEM, SystemEvent.TYPE_OTHER);
            event.setSubject(this.rb.getResourceString("notification.summary",
                    new Object[]{
                        String.valueOf(systemEventsToNotifyUserOf.size())
                    }));
            StringBuilder infoText = new StringBuilder();
            infoText.append(this.rb.getResourceString("notification.summary.info"));

            StringBuilder summary = new StringBuilder();
            for (SystemEvent singleEvent : systemEventsToNotifyUserOf) {
                summary.append("[" + singleEvent.getHumanReadableTimestamp() + "]: ")
                        .append("(" + singleEvent.severityToTextLocalized().toUpperCase() + ")")
                        .append(" ").append(singleEvent.originToTextLocalized())
                        .append("/").append(singleEvent.typeToTextLocalized())
                        .append("\n").append("id: " + singleEvent.getId())
                        .append("\n").append(singleEvent.getSubject())
                        .append("\n\n");
            }
            event.setBody(
                    infoText.toString()
                    + "\n\n\n"
                    + summary.toString());
            try {
                this.sendMail(AS2ServerVersion.getProductName(), event, notificationData, false);
                SystemEvent notificationSuccessEvent = new SystemEvent(SystemEvent.SEVERITY_INFO,
                        SystemEvent.ORIGIN_SYSTEM, SystemEvent.TYPE_NOTIFICATION_SEND_SUCCESS);
                notificationSuccessEvent.setSubject(this.rb.getResourceString("misc.message.summary.send",
                        new Object[]{
                            notificationData.getNotificationMail(),}));
                notificationSuccessEvent.setBody(summary.toString());
                SystemEventManagerImplAS2.instance().newEvent(notificationSuccessEvent);
            } catch (Exception e) {
                SystemEvent notificationSuccessEvent = new SystemEvent(SystemEvent.SEVERITY_WARNING,
                        SystemEvent.ORIGIN_SYSTEM, SystemEvent.TYPE_NOTIFICATION_SEND_FAILED);
                notificationSuccessEvent.setSubject(this.rb.getResourceString("misc.message.summary.failed",
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
        String authorization = this.rb.getResourceString( "authorization.none");
        if( notificationData.usesSMTPAuthCredentials()){
            authorization = this.rb.getResourceString( "authorization.credentials");
        }else if( notificationData.usesSMTPAuthOAuth2()){
            authorization = this.rb.getResourceString( "authorization.oauth2");
            if( notificationData.getOAuth2Config().getRFCMethod() == OAuth2Config.METHOD_RFC6749_4_1){
                authorization = authorization + " (" + this.rb.getResourceString( "authorization.oauth2.authorizationcode") + ")";
            }else if( notificationData.getOAuth2Config().getRFCMethod() == OAuth2Config.METHOD_RFC6749_4_4){
                authorization = authorization + " (" + this.rb.getResourceString( "authorization.oauth2.clientcredentials") + ")";
            }
        }
        replacement.setProperty("${AUTHORIZATION}", authorization);
        SystemEvent event = new SystemEvent(SystemEvent.SEVERITY_INFO, SystemEvent.ORIGIN_USER,
                SystemEvent.TYPE_CONNECTIVITY_TEST);
        event.readFromNotificationTemplate(templateName, replacement);
        event.setProcessOriginHost(processOriginHost);
        event.setUser(userName);
        String traceStr = this.sendMail(AS2ServerVersion.getProductName(), event, notificationData, true);
        logger.fine(MODULE_NAME + " " + this.rb.getResourceString("test.message.send", notificationData.getNotificationMail()));
        if( traceStr != null && !traceStr.trim().isEmpty()){
            traceStr = MODULE_NAME + " " + AS2Tools.replace(traceStr, "\n", "\n" + MODULE_NAME + " ");
        }
        logger.fine(traceStr);
        SystemEventManagerImplAS2.instance().newEvent(event);
    }

    @Override
    public String getNotificationSubjectServerIdentification() {
        return ("[" + AS2ServerVersion.getProductName() + "@" 
                + SystemEventManagerImplAS2.instance().getHostname() + "]");
    }

    @Override
    public String getNotificationFooter() {
        return (this.rb.getResourceString("do.not.reply"));
    }

}
