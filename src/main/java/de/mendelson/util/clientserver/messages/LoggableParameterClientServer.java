//$Header: /as4/de/mendelson/util/clientserver/messages/LoggableParameterClientServer.java 2     24/03/26 11:14 Heller $
package de.mendelson.util.clientserver.messages;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * This interface has to be implemented for all log parameters that should be
 * sent via the client-server interface
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public interface LoggableParameterClientServer{

    /**The unique id of this parameter, e.g. the unique message id
     * 
     * @return 
     */
    public String getLoggingId();

    /**
     * The prefix for the logging process to find out the type of this parameter, e.g. "MESSAGE"
     * @return 
     */
    public String getLoggingPrefix();
}
