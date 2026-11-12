//$Header: /as2/de/mendelson/comm/as2/message/AS2Payload.java 29    27/02/26 13:08 Heller $
package de.mendelson.comm.as2.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.mendelson.util.clientserver.SerializationDummy;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores all information about an as2 payload. Since AS2 1.2 it is allowed to
 * have multiple attachments in as2 transmission
 *
 * @author S.Heller
 * @version $Revision: 29 $
 */
public class AS2Payload implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Original filename of the sender, mustn't be provided
     */
    private String originalFilename = null;
    private ByteStorage byteStorageData = new ByteStorage();
    /**
     * Filename of the payload in the as2 system
     */
    private String payloadFilename = null;
    /**
     * Content id of this payload. May be null but is important for CEM because
     * the different certificates are referenced by their content id header
     */
    private String contentId = null;
    /**
     * content type of this payload. Is not important any may be null for normal
     * AS2 messages but is important for CEM because the description xml is
     * identified by its content type
     */
    private String contentType = null;

    public AS2Payload() {
    }

    /**
     * Returns the content of this object for debug purpose
     */
    @JsonIgnore
    public String getDebugDisplay() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("originalFilename=\t\t").append(this.originalFilename);
        buffer.append("\n");
        buffer.append("data size=\t\t").append(this.getByteStorageData() != null ? String.valueOf(this.getByteStorageData().getSize()) : "0");
        buffer.append("\n");
        buffer.append("payloadFilename=\t\t").append(this.payloadFilename);
        buffer.append("\n");
        buffer.append("\n");
        return (buffer.toString());
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public AS2Payload setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
        return (this);
    }

    @JsonIgnore
    public byte[] getData() throws Exception {
        return this.getByteStorageData().get();
    }

    @JsonIgnore
    public AS2Payload setData(byte[] data) throws Exception {
        this.getByteStorageData().put(data);
        return (this);
    }

    public String getPayloadFilename() {
        return payloadFilename;
    }

    public AS2Payload setPayloadFilename(String payloadFilename) {
        this.payloadFilename = payloadFilename;
        return (this);
    }

    /**
     * Writes the payload to the message to the passed file
     */
    @JsonIgnore
    public void writeTo(Path file) throws Exception {
        try (InputStream inStream = this.getByteStorageData().getInputStream()) {
            try (OutputStream outStream = new BufferedOutputStream(Files.newOutputStream(file,
                    StandardOpenOption.SYNC,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE))) {
                inStream.transferTo(outStream);
            }
        }
    }

    /**
     * The standard instance of this payload does not contain the data but just
     * a reference to its filename. Calling this method will load the data into
     * the object if possible.
     */
    @JsonIgnore
    public AS2Payload loadDataFromPayloadFile() throws Exception {
        this.getByteStorageData().put(Files.readAllBytes(Paths.get(this.payloadFilename)));
        return (this);
    }

    /**
     * @return the contentId
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * @param contentId the contentId to set
     */
    public AS2Payload setContentId(String contentId) {
        if (contentId != null && contentId.startsWith("<") && contentId.endsWith(">")) {
            contentId = contentId.substring(1, contentId.length() - 1);
        }
        this.contentId = contentId;
        return (this);
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public AS2Payload setContentType(String contentType) {
        this.contentType = contentType;
        return (this);
    }

    /**
     * Releases all resources that have been allocated, e.g. temp files
     */
    @JsonIgnore
    public void releaseResources() {
        if (this.getByteStorageData() != null) {
            this.getByteStorageData().release();
        }
    }

    /**
     * Adds this entry to the passed parent JSON node
     */
    public void addToJSON(ArrayNode parent) {
        ObjectNode node = parent.addObject();
        if (this.contentId != null) {
            node.put("contentid", this.contentId);
        }
        if (this.contentType != null) {
            node.put("contenttype", this.contentType);
        }
        if (this.originalFilename != null) {
            node.put("originalfilename", this.originalFilename);
        }
        if (this.payloadFilename != null) {
            node.put("fullstoragefilename", this.payloadFilename);
        }

    }

    /**
     * This is a dummy method for the deserialization process. Do not use in
     * logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public ByteStorage getByteStorageData() {
        return byteStorageData;
    }

    /**
     * This is a dummy method for the deserialization process. Do not use in
     * logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setByteStorageData(ByteStorage byteStorageData) {
        this.byteStorageData = byteStorageData;
    }
}
