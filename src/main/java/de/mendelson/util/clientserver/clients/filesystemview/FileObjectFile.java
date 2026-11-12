//$Header: /as2/de/mendelson/util/clientserver/clients/filesystemview/FileObjectFile.java 8     13/03/26 10:09 Heller $
package de.mendelson.util.clientserver.clients.filesystemview;

import de.mendelson.util.clientserver.SerializationDummy;
import java.net.URI;
import java.nio.file.Paths;
import javax.swing.Icon;

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
 * @version $Revision: 8 $
 */
public final class FileObjectFile extends FileObject {

    private static final long serialVersionUID = 1L;
    private boolean hidden = false;
    private boolean readOnly = false;
    private boolean executable = false;
    private String symbolicLinkTarget = null;
    private boolean symbolicLink = false;

    /**
     * Server side icon for the root - might be null
     */
    private Icon serversideIcon;

    public FileObjectFile(URI fileURI, Icon serversideIcon) {
        super(fileURI);
        this.serversideIcon = serversideIcon;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public FileObjectFile() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(Paths.get(this.getFileURI()).getFileName().toString());
        if (this.symbolicLink) {
            builder.append(" -> ");
            if (this.symbolicLinkTarget != null) {
                builder.append(this.symbolicLinkTarget);
            }
        }
        return (builder.toString());
    }

    /**
     * Overwrite the equal method of object
     *
     * @param anObject object to compare
     */
    @Override
    public boolean equals(Object anObject) {
        if (anObject == this) {
            return (true);
        }
        if (anObject != null && anObject instanceof FileObjectFile) {
            FileObjectFile otherObject = (FileObjectFile) anObject;
            return (otherObject.getFileURI().equals(this.getFileURI()));
        }
        return (false);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * @return the serversideIcon
     */
    public Icon getServersideIcon() {
        return serversideIcon;
    }

    /**
     * @return the hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @param hidden the hidden to set
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * @return the readOnly
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * @param readOnly the readOnly to set
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * @return the executable
     */
    public boolean isExecutable() {
        return executable;
    }

    /**
     * @param executable the executable to set
     */
    public void setExecutable(boolean executable) {
        this.executable = executable;
    }

    /**
     * @return the symbolicLinkTarget
     */
    public String getSymbolicLinkTarget() {
        return symbolicLinkTarget;
    }

    /**
     * @param symbolicLinkTarget the symbolicLinkTarget to set
     */
    public void setSymbolicLinkTarget(String symbolicLinkTarget) {
        this.symbolicLinkTarget = symbolicLinkTarget;
    }

    /**
     * @return the isSymbolikLink
     */
    public boolean isSymbolicLink() {
        return symbolicLink;
    }

    /**
     * @param isSymbolicLink the isSymbolicLink to set
     */
    public void setSymbolicLink(boolean isSymbolicLink) {
        this.symbolicLink = isSymbolicLink;
    }

}
