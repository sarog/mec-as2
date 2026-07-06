//$Header: /oftp2/de/mendelson/IProductVersion.java 2     25/09/25 16:57 Heller $
package de.mendelson;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Makes all software versions of the different mendelson products available in one interface
 * @author S.Heller
 * @version $Revision: 2 $
 */
public interface IProductVersion{

    /**
     * Returns the full name with build, version etc
     */
    public String getFullName();
    
    /**
     * Returns the magic number for the client-server communication
     */
    public long getMagicNumber();
}
