//$Header: /as2/de/mendelson/comm/as2/client/manualsend/ManualSendRequest.java 18    17/06/25 12:46 Heller $
package de.mendelson.comm.as2.client.manualsend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.clients.datatransfer.UploadRequestFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Message for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 18 $
 */
public class ManualSendRequest extends UploadRequestFile implements Serializable {

    
    private static final long serialVersionUID = 1L;
    private String senderAS2Id = null;
    //If the sender AS2 name is used this results always in a AS2 id lookup on the server side!
    //Better use the AS2 id if this is known
    private String senderAS2Name = null;
    private String receiverAS2Id;
    //If the receivers AS2 name is used this results always in a AS2 id lookup on the server side!
    //Better use the AS2 id if this is known
    private String receiverAS2Name = null;
    private List<String> filenames = new ArrayList<String>();
    private String resendMessageId = null;
    private String userdefinedId = null;
    private List<String> uploadHashs = new ArrayList<String>();
    private String subject = null;
    private boolean sendTestdata = false;
    private List<String> payloadContentTypes = new ArrayList<String>();
    private Map<String, String> userdefinedHeaderMap = new LinkedHashMap<String, String>();

    @Override
    public String toString() {
        return ("Manual send request");
    }

    /**
     * @return the sender
     */
    public String getSenderAS2Id() {
        return this.senderAS2Id;
    }

    /**
     */
    public ManualSendRequest setSenderAS2Id(String senderAS2Id) {
        this.senderAS2Id = senderAS2Id;
        return( this );
    }

    /**
     * @return the receiver
     */
    public String getReceiverAS2Id() {
        return this.receiverAS2Id;
    }

    /**
     */
    public ManualSendRequest setReceiverAS2Id(String receiverAS2Id) {
        this.receiverAS2Id = receiverAS2Id;
        return( this );
    }

    /**
     * @return the filename
     */
    public List<String> getFilenames() {
        return (this.filenames);
    }

    /**
     * @param filename the filename of a payload
     * @param payloadContentType The content type of this payload as set in the outbound AS2 message - may be null for the
     * default value or the value defined in the receiver
     */
    @JsonIgnore
    public ManualSendRequest addFilename(String filename, String payloadContentType) {
        this.filenames.add(filename);
        this.payloadContentTypes.add( payloadContentType );
        return( this );
    }

    /**
     * @return the resendMessageId
     */
    public String getResendMessageId() {
        return (this.resendMessageId);
    }

    /**
     * Set this message id if this is a resend of an existing message
     *
     * @param resendMessageId the resendMessageId to set
     */
    public ManualSendRequest setResendMessageId(String resendMessageId) {
        this.resendMessageId = resendMessageId;
        return( this );
    }

    /**
     * @return the userdefinedId
     */
    public String getUserdefinedId() {
        return userdefinedId;
    }

    /**
     * Sets a user defined id to this transaction. If this is set the user
     * defined id could be used later to track the progress of this send
     * transmission.
     *
     * @param userdefinedId the userdefinedId to set
     */
    public ManualSendRequest setUserdefinedId(String userdefinedId) {
        this.userdefinedId = userdefinedId;
        return( this );
    }

    /**
     * @return the uploadHashs
     */
    public List<String> getUploadHashs() {
        return (this.uploadHashs);
    }

    /**
     * @param uploadHashs the uploadHashs to set
     */
    public ManualSendRequest setUploadHashs(List<String> uploadHashs) {
        this.uploadHashs.clear();
        this.uploadHashs.addAll(uploadHashs);
        return( this );
    }

    @Override
    @JsonIgnore
    public void setUploadHash(String singleUploadHash) {
        this.uploadHashs.add(singleUploadHash);
    }

    @Override
    @JsonIgnore
    public String getUploadHash() {
        if( !this.uploadHashs.isEmpty()){
            return( this.uploadHashs.get(0));
        }else{
            return( null );
        }
    }

    /**
     * Indicates that no file should be send but test data that is generated on the server
     */
    public boolean isSendTestdata() {
        return (this.sendTestdata);
    }

   /**
     * Indicates that no file should be send but test data that is generated on the server
     */
    public ManualSendRequest setSendTestdata(boolean sendTestdata) {
        this.sendTestdata = sendTestdata;
        return( this );
    }

    /**
     * @return the payloadContentType
     */
    public List<String> getPayloadContentTypes() {
        return this.payloadContentTypes;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public ManualSendRequest setSubject(String subject) {
        this.subject = subject;
        return( this);
    }

    /**
     * @return the senderAS2Name
     */
    public String getSenderAS2Name() {
        return this.senderAS2Name;
    }

    /**
     * @param senderAS2Name the senderAS2Name to set
     */
    public ManualSendRequest setSenderAS2Name(String senderAS2Name) {
        this.senderAS2Name = senderAS2Name;
        return( this );
    }

    /**
     * @return the receiverAS2Name
     */
    public String getReceiverAS2Name() {
        return receiverAS2Name;
    }

    /**
     * @param receiverAS2Name the receiverAS2Name to set
     */
    public ManualSendRequest setReceiverAS2Name(String receiverAS2Name) {
        this.receiverAS2Name = receiverAS2Name;
        return( this );
    }

    
    
    /**This is a dummy method for the deserialization process. Do not use in logic.
     */    
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public ManualSendRequest setFilenames(List<String> filenames) {
        this.filenames.clear();
        this.filenames.addAll(filenames);
        return( this );
    }    

    /**This is a dummy method for the deserialization process. Do not use in logic.
     */ 
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public ManualSendRequest setPayloadContentTypes(List<String> payloadContentTypes) {
        this.payloadContentTypes.clear();
        this.payloadContentTypes.addAll(payloadContentTypes);
        return( this );
    }

    /**
     * @return the userdefinedHeaderMap
     */
    public Map<String, String> getUserdefinedHeaderMap() {
        return userdefinedHeaderMap;
    }

    /**
     * @param userdefinedHeaderMap the userdefinedHeaderMap to set
     */
    public ManualSendRequest setUserdefinedHeaderMap(Map<String, String> userdefinedHeaderMap) {        
        this.userdefinedHeaderMap.clear();
        if( userdefinedHeaderMap != null ){
            this.userdefinedHeaderMap.putAll(userdefinedHeaderMap);
        }
        return( this );
    }

    
    
}
