//$Header: /oftp2/de/mendelson/util/clientserver/log/search/ServerlogfileSearchRequest.java 7     13/06/25 12:35 Heller $
package de.mendelson.util.clientserver.log.search;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
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
 * @version $Revision: 7 $
 */
public class ServerlogfileSearchRequest extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private ServerSideLogfileFilter filter;
    
    public ServerlogfileSearchRequest(ServerSideLogfileFilter filter) {
        this.filter = filter;
    }

     /**This is a dummy constructor for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public ServerlogfileSearchRequest() {
        super();
        this.filter = null;
    }
    
    @Override
    public String toString() {
        return ("Search for log file entries");
    }

    /**
     * @return the search filter
     */
    public ServerSideLogfileFilter getFilter() {
        return( this.filter );
    }

    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }

     /**This is a dummy method for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setFilter(ServerSideLogfileFilter filter) {
        this.filter = filter;
    }
    
}
