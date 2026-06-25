//$Header: /as4/de/mendelson/util/clientserver/clients/preferences/PreferencesClient.java 10    17/12/25 10:00 Heller $
package de.mendelson.util.clientserver.clients.preferences;

import de.mendelson.util.clientserver.BaseClient;
import de.mendelson.util.uinotification.UINotification;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Requests and preferences from and sets new values to the server. This has to
 * be used in grahical environment only
 *
 * @author S.Heller
 * @version $Revision: 10 $
 */
public class PreferencesClient {

    private final BaseClient baseClient;
    private long syncTimeout = BaseClient.TIMEOUT_SYNC_RECEIVE;

    public PreferencesClient(BaseClient baseClient, long syncTimeout) {
        this.baseClient = baseClient;
        this.syncTimeout = syncTimeout;
    }

    public PreferencesClient(BaseClient baseClient) {
        this(baseClient, BaseClient.TIMEOUT_SYNC_RECEIVE);
    }

    /**
     * Returns a single string value from the preferences or the default if it
     * is not found
     *
     * @return In case of an error during the sync request an empty string is
     * returned
     */
    public String get(final String KEY) {
        PreferencesRequest request = new PreferencesRequest();
        request.setKey(KEY);
        request.setType(PreferencesRequest.TYPE_GET);
        PreferencesResponse response = (PreferencesResponse) this.baseClient.sendSync(request, this.syncTimeout);
        if (response != null) {
            if (response.getException() != null) {
                UINotification.instance().addNotification(response.getException());
            }
            return (response.getValue());
        } else {
            return ("");
        }
    }

    /**
     * Returns a single string value from the preferences or the default if it
     * is not found
     *
     * @return In case of an error during the sync request an empty string is
     * returned
     */
    public String getDefaultValue(final String KEY) {
        PreferencesRequest request = new PreferencesRequest();
        request.setKey(KEY);
        request.setType(PreferencesRequest.TYPE_GET_DEFAULT);
        PreferencesResponse response = (PreferencesResponse) this.baseClient.sendSync(request, this.syncTimeout);
        if (response != null) {
            if (response.getException() != null) {
                UINotification.instance().addNotification(response.getException());
            }
            return (response.getValue());
        } else {
            return ("");
        }
    }

   

    /**
     * Stores a value in the preferences and throws an exception if this did not
     * work for some reason. If the passed value is null or an empty string the
     * key-value pair will be deleted from the registry.
     *
     * @param KEY Key as defined in this class
     * @param value value to set
     */
    public void put(final String KEY, String value) throws Throwable {
        PreferencesRequest request = new PreferencesRequest();
        request.setKey(KEY);
        request.setValue(value);
        request.setType(PreferencesRequest.TYPE_SET);
        PreferencesResponse response = (PreferencesResponse) this.baseClient.sendSync(request, this.syncTimeout);
        if (response == null) {
            throw (new Exception("Timeout in client-server interface"));
        }
        if (response.getException() != null) {
            throw (response.getException());
        }
    }

    /**
     * Puts a value to the preferences and stores the prefs
     *
     * @param KEY Key as defined in this class
     * @param value value to set
     */
    public void putInt(final String KEY, int value) throws Throwable{
        this.put( KEY, String.valueOf(value) );
    }

    /**
     * Returns the value, as fallback its default value and -1 on client-server
     * connection loss
     */
    public int getInt(final String KEY) {
        PreferencesRequest request = new PreferencesRequest();
        request.setKey(KEY);
        request.setType(PreferencesRequest.TYPE_GET);
        PreferencesResponse response = (PreferencesResponse) this.baseClient.sendSync(request, this.syncTimeout);
        if (response != null) {
            if (response.getException() != null) {
                UINotification.instance().addNotification(response.getException());
            }
            return (Integer.parseInt(response.getValue()));
        } else {
            return (-1);
        }
    }

    /**
     * Puts a value to the preferences and stores the prefs
     *
     * @param KEY Key as defined in this class
     * @param value value to set
     */
    public void putBoolean(final String KEY, boolean value) throws Throwable{
        this.put( KEY, String.valueOf(value).toUpperCase() );
    }

    /**
     * Returns the value for the asked key, if non is defined it returns the
     * default value
     */
    public boolean getBoolean(final String KEY) {
        PreferencesRequest request = new PreferencesRequest();
        request.setKey(KEY);
        request.setType(PreferencesRequest.TYPE_GET);
        PreferencesResponse response = (PreferencesResponse) this.baseClient.sendSync(request, this.syncTimeout);
        if (response != null) {
            if (response.getException() != null) {
                UINotification.instance().addNotification(response.getException());
            }
            return (Boolean.parseBoolean(response.getValue()));
        } else {
            return (false);
        }
    }
}
