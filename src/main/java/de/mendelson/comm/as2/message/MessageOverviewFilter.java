//$Header: /as2/de/mendelson/comm/as2/message/MessageOverviewFilter.java 15    23/03/26 12:56 Heller $
package de.mendelson.comm.as2.message;

import de.mendelson.comm.as2.partner.Partner;
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
 * Filter to apply for the message overview
 * @author S.Heller
 * @version $Revision: 15 $
 */
public class MessageOverviewFilter implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private boolean showFinished = true;
    private boolean showPending = true;
    private boolean showStopped = true;
    private Partner showPartner = null;
    private Partner showLocalStation = null;
    private MessageDirectionType direction = MessageDirectionType.ALL;
    private MessageType messageType = MessageType.AS2;
    private int limit = 1000;
    private long startTime = 0L;
    private long endTime = 0L;
    private String userdefinedId = null;
    
    /**Filters for the message type that should be displayed*/
    public void setShowMessageType( MessageType messageType ){
        this.messageType = messageType;
    }

    /**Show INBOUND/OUTBOUND only?*/
    public void setShowDirection( MessageDirectionType direction ){
        if( direction != MessageDirectionType.ALL
                && direction != MessageDirectionType.IN
                && direction != MessageDirectionType.OUT ){
            throw new IllegalArgumentException( "MessageOverviewFilter.setShowDirection(): Invalid value " + direction + "." );
        }
        this.direction = direction;
    }

    
    
    /**Returns the message type that should be shown or MESSAGETYPE_ALL if no filter should be applied
     * for the message type
     */
    public MessageType getShowMessageType(){
        return( this.messageType);
    }

    /**Returns the direction that should be filtered or DIRECTION_ALL if no filter should be applied
     * for the direction
     * @return
     */
    public MessageDirectionType getShowDirection(){
        return( this.direction);
    }

    /**Pass null to show all partners
     */
    public void setShowPartner( Partner partner ){
        this.showPartner = partner;
    }
    
    /**Returns null if all partner should be shown
     */
    public Partner getShowPartner(){
        return( this.showPartner);
    }
    
    public boolean isShowFinished() {
        return showFinished;
    }

    public void setShowFinished(boolean showFinished) {
        this.showFinished = showFinished;
    }

    public boolean isShowPending() {
        return showPending;
    }

    public void setShowPending(boolean showPending) {
        this.showPending = showPending;
    }

    public boolean isShowStopped() {
        return showStopped;
    }

    public void setShowStopped(boolean showStopped) {
        this.showStopped = showStopped;
    }

    public Partner getShowLocalStation() {
        return showLocalStation;
    }

    public void setShowLocalStation(Partner showLocalStation) {
        this.showLocalStation = showLocalStation;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the userdefinedId. If this is null there should be no filter
     * for the user defined id
     */
    public String getUserdefinedId() {
        return userdefinedId;
    }

    /**
     * @param userdefinedId the userdefinedId to set. Set this to null to
     * ignore this (this is the default)
     */
    public void setUserdefinedId(String userdefinedId) {
        this.userdefinedId = userdefinedId;
    }
    
    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }
    
}
