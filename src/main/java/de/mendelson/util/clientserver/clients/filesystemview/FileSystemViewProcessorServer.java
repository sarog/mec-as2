//$Header: /mendelson_business_integration/de/mendelson/util/clientserver/clients/filesystemview/FileSystemViewProcessorServer.java 7     31- $
package de.mendelson.util.clientserver.clients.filesystemview;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Processes file system view requests
 *
 * @author S.Heller
 * @version $Revision: 7 $
 */
public class FileSystemViewProcessorServer {

    private Logger logger;

    public FileSystemViewProcessorServer(Logger logger) {
        this.logger = logger;
    }

    public FileSystemViewResponse performRequest(FileSystemViewRequest request) {
        FileFilter filter = request.getFileFilter();
        FileSystemViewResponse response = new FileSystemViewResponse(request);
        final int requestType = request.getRequestType();
        switch (requestType) {
            case FileSystemViewRequest.TYPE_LIST_ROOTS:
                List<FileObject> rootList = new ArrayList<FileObject>();
                try {
                    File[] roots = File.listRoots();
                    for (File root : roots) {
                        rootList.add(new FileObject(root, FileObject.TYPE_ROOT));
                    }
                } catch (Throwable e) {
                    //this.logger.warning("FileSystemViewProcessorServer [LIST_ROOTS]: Unable to determine file system roots: " + e.getMessage());
                } finally {
                    response.setParameterFileArray(rootList);
                }
                break;
            case FileSystemViewRequest.TYPE_LIST_CHILDREN:
                FileObject parent = request.getParameterFile();
                File[] children = parent.getFile().listFiles();
                List<FileObject> childList = new ArrayList<FileObject>();
                try {
                    for (File child : children) {
                        if (!child.getName().equals(".") && !child.getName().equals("..")) {
                            if (child.isDirectory()) {
                                childList.add(new FileObject(child, FileObject.TYPE_DIR));
                            } else {
                                if (filter.displayFile(child)) {
                                    childList.add(new FileObject(child, FileObject.TYPE_FILE));
                                }
                            }
                        }                        
                    }
                    Collections.sort(childList);
                } catch (Throwable e) {
                    //this.logger.warning("FileSystemViewProcessorServer [LIST_CHILDREN]: Unable to list children: " + e.getMessage());
                } finally {
                    response.setParameterFileArray(childList);
                }
                break;
            case FileSystemViewRequest.TYPE_GET_PATH_STR:
                String pathStr = "";
                try {
                    FileObject file = request.getParameterFile();
                    pathStr = file.getFile().getAbsolutePath();
                } catch (Throwable e) {
                    //this.logger.warning("FileSystemViewProcessorServer [GET_PATH_STR]: Unable to get path: " + e.getMessage());
                } finally {
                    response.setParameterString(pathStr);
                }
                break;
            case FileSystemViewRequest.TYPE_GET_PATH_ELEMENTS:
                List<FileObject> elements = new ArrayList<FileObject>();
                String pathString = request.getParameterString();
                if (pathString != null) {
                    File pathFile = new File(pathString);
                    while (pathFile != null) {
                        if (pathFile.exists()) {
                            int type = FileObject.TYPE_FILE;
                            if (pathFile.isDirectory()) {
                                type = FileObject.TYPE_DIR;
                            }
                            if (pathFile.getParentFile() == null) {
                                type = FileObject.TYPE_ROOT;
                            }
                            FileObject object = new FileObject(pathFile, type);
                            elements.add(0, object);
                        }
                        pathFile = pathFile.getParentFile();
                    }
                }
                response.setParameterFileArray(elements);
                break;
        }
        return (response);
    }

}
