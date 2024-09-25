//$Header: /as2/de/mendelson/util/clientserver/messages/QuitRequest.java 4     7.01.11 15:43 Heller $
package de.mendelson.util.clientserver.messages;

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
public class QuitRequest extends ClientServerMessage implements Serializable{
    
    private String user = null;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    @Override
     public String toString(){
        return( "Quit request" );
    }
    
}
