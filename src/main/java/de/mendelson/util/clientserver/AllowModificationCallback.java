//$Header: /oftp2/de/mendelson/util/clientserver/AllowModificationCallback.java 1     23.05.14 12:24 Heller $
package de.mendelson.util.clientserver;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Interface every class has to implement that will be executed if a 
 * certificate modification is requested by the user
 * @author S.Heller
 * @version $Revision: 1 $
 */
public interface AllowModificationCallback{
   
    public boolean allowModification();
    
}