//$Header: /as2/de/mendelson/comm/as2/statistic/clientserver/StatisticDetailRequest.java 8     23/03/26 13:42 Heller $
package de.mendelson.comm.as2.statistic.clientserver;

import de.mendelson.comm.as2.message.MessageDirectionType;
import de.mendelson.comm.as2.message.MessageStateType;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jfree.data.time.SimpleTimePeriod;
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
public class StatisticDetailRequest extends ClientServerMessage implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String as2IdentificationLocal;
    private String as2IdentificationPartner;
    private MessageDirectionType direction;
    
    private List<SimpleTimePeriod> periods = new ArrayList<SimpleTimePeriod>();
    private List<MessageStateType> states = new ArrayList<MessageStateType>();
    private List<String> seriesName = new ArrayList<String>();
    
    public StatisticDetailRequest() {
    }

    public void addRequest( SimpleTimePeriod period, MessageStateType state, String seriesName ){
        this.periods.add( period );
        this.states.add( state);
        this.getSeriesName().add( seriesName);
    }
    
    
    @Override
    public String toString() {
        return ("List statistic details");
    }

    /**
     * @return the as2IdentificationLocal
     */
    public String getAS2IdentificationLocal() {
        return as2IdentificationLocal;
    }

    /**
     * @param as2IdentificationLocal the as2IdentificationLocal to set
     */
    public void setAS2IdentificationLocal(String as2IdentificationLocal) {
        this.as2IdentificationLocal = as2IdentificationLocal;
    }

    /**
     * @return the as2IdentificationPartner
     */
    public String getAS2IdentificationPartner() {
        return as2IdentificationPartner;
    }

    /**
     * @param as2IdentificationPartner the as2IdentificationPartner to set
     */
    public void setAS2IdentificationPartner(String as2IdentificationPartner) {
        this.as2IdentificationPartner = as2IdentificationPartner;
    }

    /**
     * @return the direction
     */
    public MessageDirectionType getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(MessageDirectionType direction) {
        this.direction = direction;
    }

    /**
     * @return the periods
     */
    public List<SimpleTimePeriod> getPeriods() {
        return periods;
    }

    /**
     * @return the states
     */
    public List<MessageStateType> getStates() {
        return(Collections.unmodifiableList(states));
    }

    /**
     * @return the seriesName
     */
    public List<String> getSeriesName() {
        return seriesName;
    }
    
    /**Prevent an overwrite of the readObject method for de-serialization*/
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException{
        inStream.defaultReadObject();
    }

}
