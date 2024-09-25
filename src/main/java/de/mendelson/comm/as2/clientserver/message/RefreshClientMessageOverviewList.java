//$Header: /as2/de/mendelson/comm/as2/clientserver/message/RefreshClientMessageOverviewList.java 4     14.01.11 17:34 Heller $
package de.mendelson.comm.as2.clientserver.message;

import de.mendelson.util.clientserver.messages.ClientServerMessage;
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
public class RefreshClientMessageOverviewList extends ClientServerMessage implements Serializable{

    /**Event if the clients prevent update - the delete updates should
     * be always displayed
     */
    public static final int OPERATION_PROCESSING_UPDATE = 1;
    public static final int OPERATION_DELETE_UPDATE = 2;

    private int operation = OPERATION_PROCESSING_UPDATE;

    @Override
    public String toString(){
        return( "Message overview list refresh request" );
    }

    /**
     * @return the operation
     */
    public int getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(int operation) {
        this.operation = operation;
    }
    
    
}
