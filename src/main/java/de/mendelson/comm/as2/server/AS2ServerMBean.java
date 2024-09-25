//$Header: /as2/de/mendelson/comm/as2/server/AS2ServerMBean.java 1     17.04.09 15:24 Heller $
package de.mendelson.comm.as2.server;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * MBean interface for the AS2 server. Do NOT change the class name of this interface,
 * this will result in a NotCompliantMBeanException.
 * @author S.Heller
 * @version $Revision: 1 $
 */
public interface AS2ServerMBean{
    

    public String getServerVersion();

    public long getUptimeInMS();

    public long getUsedMemoryInBytes();

    public long getTotalMemoryInBytes();
   
    public long getRawDataSentInBytesInUptime();

    public long getRawDataReceivedInBytesInUptime();

    public long getTransactionCountInUptime();

}