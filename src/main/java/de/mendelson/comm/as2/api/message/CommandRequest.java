//$Header: /as2/de/mendelson/comm/as2/api/message/CommandRequest.java 4     2/11/23 15:52 Heller $
package de.mendelson.comm.as2.api.message;

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
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class CommandRequest extends ClientServerMessage implements Serializable{

    private static final long serialVersionUID = 1L;
    private String uploadHash = null;

    public CommandRequest( String uploadHash) throws Exception{
        this.uploadHash = uploadHash;
    }

    @Override
    public String toString(){
        return( "New command request" );
    }

    /**
     * @return the uploadHash
     */
    public String getUploadHash() {
        return uploadHash;
    }
    
    /**Prevent an overwrite of the readObject method for deserialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }
}
