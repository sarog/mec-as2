//$Header: /as4/de/mendelson/util/clientserver/clients/filesystemview/FileSystemViewRequest.java 9     11/06/25 13:16 Heller $
package de.mendelson.util.clientserver.clients.filesystemview;

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
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class FileSystemViewRequest extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int TYPE_LIST_ROOTS = 1;
    public static final int TYPE_LIST_CHILDREN = 2;
    public static final int TYPE_GET_ABSOLUTE_PATH_STR = 3;
    public static final int TYPE_GET_PATH_ELEMENTS = 4;    
    public static final int TYPE_GET_FILE_SEPARATOR = 5;    
    private int requestType = TYPE_LIST_ROOTS;
    private String requestFilePath = null;
    private FileFilter fileFilter = new FileFilter();

    public FileSystemViewRequest( int requestType ){
        this.requestType = requestType;
    }
    
    /**This is a dummy constructor for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public FileSystemViewRequest() {
        super();
    }
    
    public void setFileFilter( FileFilter fileFilter ){
        if( fileFilter != null ){
            this.fileFilter = fileFilter;
        }
    }
    
    public int getRequestType(){
        return( this.requestType);
    }
    
    @Override
    public String toString() {
        return ("File system view request");
    }

    /**
     * @return the fileFilter
     */
    public FileFilter getFileFilter() {
        return fileFilter;
    }

    /**
     * @return the requestFilePath
     */
    public String getRequestFilePath() {
        return requestFilePath;
    }

    /**
     * @param requestFilePath the requestFilePath to set
     */
    public void setRequestFilePath(String requestFilePath) {
        this.requestFilePath = requestFilePath;
    }

    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }
    
}
