//$Header: /as2/de/mendelson/util/clientserver/clients/filesystemview/FileFilter.java 6     28/05/25 16:54 Heller $
package de.mendelson.util.clientserver.clients.filesystemview;

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
 * @version $Revision: 6 $
 */
public class FileFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    private int dummy = 1;
    
    public FileFilter() {
    }

    public boolean displayFile(String filePath) {
        return (true);
    }

    /**
     * Required for Bean serialisation, e.g. via Jackson
     */
    public int getDummy() {
        return dummy;
    }

    /**
     * Required for Bean serialisation, e.g. via Jackson
     */
    public void setDummy(int dummy) {
        this.dummy = dummy;
    }

}
