//$Header: /as2/de/mendelson/util/clientserver/messages/ServerLogListMessage.java 2     13/03/26 10:09 Heller $
package de.mendelson.util.clientserver.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 * @version $Revision: 2 $
 */
public final class ServerLogListMessage extends ClientServerMessage implements Serializable{

    private static final long serialVersionUID = 1L;
    private List<ServerLogMessage> logMessageList = new ArrayList<ServerLogMessage>();
    
    public ServerLogListMessage(){
    }    
            
    @Override
    public String toString(){
        return( "Server log messages");
    }

    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }

    /**
     * @return the logMessageList
     */
    public List<ServerLogMessage> getLogMessageList() {
        return logMessageList;
    }

    /**
     * @param logMessageList the logMessageList to set
     */
    public void setLogMessageList(List<ServerLogMessage> logMessageList) {
        this.logMessageList.clear();
        this.logMessageList.addAll(logMessageList);
    }
    
}
