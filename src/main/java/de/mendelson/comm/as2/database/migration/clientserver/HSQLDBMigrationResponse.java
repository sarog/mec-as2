//$Header: /as2/de/mendelson/comm/as2/database/migration/clientserver/HSQLDBMigrationResponse.java 4     11/06/25 13:28 Heller $
package de.mendelson.comm.as2.database.migration.clientserver;

import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import java.io.Serializable;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Msg for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class HSQLDBMigrationResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int keystoresSuccessfullyImportedCount = 0;
    private int preferencesSuccessfullyImportedCount = 0;
    
    public HSQLDBMigrationResponse(HSQLDBMigrationRequest request) {
        super(request);
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public HSQLDBMigrationResponse() {
        super();
    }
    
    @Override
    public String toString() {
        return ("HSQLDB preferences migration response");
    }

    /**
     * @return the keystoresSuccessfullyImported
     */
    public int getKeystoresSuccessfullyImported() {
        return this.keystoresSuccessfullyImportedCount;
    }

    /**
     * @param keystoresSuccessfullyImported the keystoresSuccessfullyImported to set
     */
    public void setKeystoresSuccessfullyImported(int importCount) {
        this.keystoresSuccessfullyImportedCount = importCount;
    }

    /**
     * @return the preferencesSuccessfullyImported
     */
    public int getPreferencesSuccessfullyImported() {
        return this.preferencesSuccessfullyImportedCount;
    }

    /**
     * @param preferencesSuccessfullyImported the preferencesSuccessfullyImported to set
     */
    public void setPreferencesSuccessfullyImported(int importCount) {
        this.preferencesSuccessfullyImportedCount = importCount;
    }

}
