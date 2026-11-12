//$Header: /as4/de/mendelson/util/mailautoconfig/MailServiceConfiguration.java 5     11/06/25 13:17 Heller $
package de.mendelson.util.mailautoconfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.mailautoconfig.gui.ResourceBundleMailAutoConfigurationDetection;
import de.mendelson.util.systemevents.notification.NotificationData;
import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Container object to transport a single mail service configuration
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class MailServiceConfiguration implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public static final String SERVICE_POP3 = "pop3";
    public static final String SERVICE_SMTP = "smtp";
    public static final String SERVICE_IMAP = "imap";
    public static final String SERVICE_EXCHANGE = "exchange";

    public static final int SECURITY_PLAIN = NotificationData.SECURITY_PLAIN;
    public static final int SECURITY_START_TLS = NotificationData.SECURITY_START_TLS;
    public static final int SECURITY_TLS = NotificationData.SECURITY_TLS;

    private int port;
    private String service;
    private int security;
    private String mailProviderLongName;
    private String serverHost;
    

    public MailServiceConfiguration(String service, String serverHost, int port, int security, String mailProviderLongName) {
        this.service = service;
        this.port = port;
        this.security = security;
        this.serverHost = serverHost;
        this.mailProviderLongName = mailProviderLongName;
    }

     /**This is a dummy constructor for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public MailServiceConfiguration(){        
    }
    
    /**
     * Overwrite the equal method of object
     *
     * @param anObject object to compare
     */
    @Override
    public boolean equals(Object anObject) {
        if (anObject == this) {
            return (true);
        }
        if (anObject != null && anObject instanceof MailServiceConfiguration) {
            MailServiceConfiguration entry = (MailServiceConfiguration) anObject;
            return (entry.getService().equals(this.getService())
                    && entry.getSecurity() == this.getSecurity()
                    && entry.getPort() == this.getPort());
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.getPort();
        hash = 97 * hash + Objects.hashCode(this.getService());
        hash = 97 * hash + this.getSecurity();
        return hash;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * @return the security
     */
    public int getSecurity() {
        return security;
    }

    @JsonIgnore
    public String getSecurityAsString(){
        //load resource bundle
        MecResourceBundle rb;
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleMailAutoConfigurationDetection.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
        return( rb.getResourceString( "security." + this.security));
    }
    
    
    /**
     * @return the mailProviderLongName
     */
    public String getMailProviderLongName() {
        return mailProviderLongName;
    }

    /**
     * @return the serverhost
     */
    public String getServerHost() {
        return serverHost;
    }
    
    public String toDebugDisplay(){
        return( "Provider=" + this.mailProviderLongName 
                + ";Service=" + this.service 
                + "; Host=" + this.serverHost
                + "; Port=" + this.port
                + "; Security=" + this.security);
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @param service the service to set
     */
    public void setService(String service) {
        this.service = service;
    }

    public void setSecurity(int security) {
        this.security = security;
    }

    public void setMailProviderLongName(String mailProviderLongName) {
        this.mailProviderLongName = mailProviderLongName;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

}
