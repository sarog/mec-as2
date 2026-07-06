//$Header: /as2/de/mendelson/comm/as2/partner/PartnerEventAccessDB.java 19    31/03/26 9:30 Heller $
package de.mendelson.comm.as2.partner;

import de.mendelson.comm.as2.message.postprocessingevent.ProcessingEventTriggerType;
import de.mendelson.comm.as2.message.postprocessingevent.ProcessingEventType;
import de.mendelson.util.security.Base64;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Access the partner events in the database
 *
 * @author S.Heller
 * @version $Revision: 19 $
 */
public class PartnerEventAccessDB {

    public PartnerEventAccessDB() {
    }

    /**
     * Populates the passed partner with the stored event data
     */
    public void loadPartnerEventsIntoPartner(List<Partner> partnerList, Connection configConnection) throws Exception {
        if (partnerList == null || partnerList.isEmpty()) {
            return;
        }
        int batchSize = 250;
        for (int i = 0; i < partnerList.size(); i += batchSize) {
            int toIndex = Math.min(i + batchSize, partnerList.size());
            List<Partner> subList = partnerList.subList(i, toIndex);
            this.loadPartnerEventsSublist(subList, configConnection);
        }
    }

    private void loadPartnerEventsSublist(List<Partner> partnerList, Connection configConnection) throws Exception {
        StringBuilder idList = new StringBuilder();
        for (int i = 0; i < partnerList.size(); i++) {
            if (i > 0) {
                idList.append(",");
            }
            idList.append(partnerList.get(i).getDBId());
        }
        //key: partner id
        Map<Integer, PartnerEventInformation> eventMap = new HashMap<Integer, PartnerEventInformation>();
        String query = "SELECT * FROM partnerevent WHERE partnerid IN (" + idList.toString() + ")";
        try (PreparedStatement statement = configConnection.prepareStatement(query)) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int partnerId = result.getInt("partnerid");
                    PartnerEventInformation eventInfo = new PartnerEventInformation();
                    eventInfo.setProcess(ProcessingEventTriggerType.RECEIPT_SUCCESS, 
                            ProcessingEventType.of(result.getInt("typeonreceipt")));
                    eventInfo.setProcess(ProcessingEventTriggerType.SEND_FAILURE, 
                            ProcessingEventType.of(result.getInt("typeonsenderror")));
                    eventInfo.setProcess(ProcessingEventTriggerType.SEND_SUCCESS, 
                            ProcessingEventType.of(result.getInt("typeonsendsuccess")));
                    eventInfo.setUseOnReceipt(result.getInt("useonreceipt") != 0);
                    eventInfo.setUseOnSendError(result.getInt("useonsenderror") != 0);
                    eventInfo.setUseOnSendSuccess(result.getInt("useonsendsuccess") != 0);
                    eventInfo.setParameter(ProcessingEventTriggerType.RECEIPT_SUCCESS,
                            this.deserializeList(result.getString("parameteronreceipt")));
                    eventInfo.setParameter(ProcessingEventTriggerType.SEND_FAILURE,
                            this.deserializeList(result.getString("parameteronsenderror")));
                    eventInfo.setParameter(ProcessingEventTriggerType.SEND_SUCCESS,
                            this.deserializeList(result.getString("parameteronsendsuccess")));
                    eventMap.put(partnerId, eventInfo);
                }
            }
        }        
        ProcessingEventTriggerType[] triggerTypes =ProcessingEventTriggerType.values();
        for (int i = 0; i < partnerList.size(); i++) {
            Partner partner = partnerList.get(i);
            PartnerEventInformation eventInformation = eventMap.get(partner.getDBId());
            if (eventInformation != null) {
                PartnerEventInformation partnerEventInformation = partner.getPartnerEvents();
                partnerEventInformation.setUseOnReceipt(eventInformation.isUseOnReceipt());
                partnerEventInformation.setUseOnSendError(eventInformation.isUseOnSendError());
                partnerEventInformation.setUseOnSendSuccess(eventInformation.isUseOnSendSuccess());                
                for (int ii = 0; ii < triggerTypes.length; ii++) {
                    ProcessingEventTriggerType triggerType = triggerTypes[ii];
                    partnerEventInformation.setProcess(triggerType, eventInformation.getProcess(triggerType));
                    partnerEventInformation.setParameter(triggerType, eventInformation.getParameter(triggerType));
                }
            }
        }
    }


    /**
     * Serializes a list to a single string
     *
     * @param list
     * @return
     */
    private String serializeList(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String entry : list) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(Base64.encode(entry.getBytes(StandardCharsets.UTF_8)));
        }
        return (builder.toString());
    }

    /**
     * Serializes a list to a single string
     *
     * @return
     */
    private List<String> deserializeList(String entry) {
        List<String> list = new ArrayList<String>();
        if (entry == null) {
            return (list);
        }
        String[] entryArray = entry.split(" ");
        for (String singleEntry : entryArray) {
            byte[] decodedBytes = Base64.decode(singleEntry);
            //base64 decoding failed...return empty parameters
            if (decodedBytes == null) {
                return (new ArrayList<String>());
            }
            list.add(new String(decodedBytes, StandardCharsets.UTF_8));
        }
        return (list);
    }

    /**
     * Stores the events of a partner
     *
     * @param configConnectionNoAutoCommit passed database connection - required
     * for transactional operation
     *
     */
    protected void storePartnerEvents(Partner partner, Connection configConnectionNoAutoCommit) throws Exception {
        this.deletePartnerEvents(partner, configConnectionNoAutoCommit);
        PartnerEventInformation partnerEvents = partner.getPartnerEvents();
        try (PreparedStatement statement = configConnectionNoAutoCommit.prepareStatement(
                "INSERT INTO partnerevent(partnerid,useonreceipt,useonsenderror,useonsendsuccess,"
                + "typeonreceipt,typeonsenderror,typeonsendsuccess,"
                + "parameteronreceipt,parameteronsenderror,parameteronsendsuccess)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?)")) {
            statement.setInt(1, partner.getDBId());
            statement.setInt(2, partnerEvents.isUseOnReceipt() ? 1 : 0);
            statement.setInt(3, partnerEvents.isUseOnSendError() ? 1 : 0);
            statement.setInt(4, partnerEvents.isUseOnSendSuccess() ? 1 : 0);
            statement.setInt(5, partnerEvents.getProcess(
                    ProcessingEventTriggerType.RECEIPT_SUCCESS).toInt());
            statement.setInt(6, partnerEvents.getProcess(ProcessingEventTriggerType.SEND_FAILURE).toInt());
            statement.setInt(7, partnerEvents.getProcess(ProcessingEventTriggerType.SEND_SUCCESS).toInt());
            if (partnerEvents.hasParameter(ProcessingEventTriggerType.RECEIPT_SUCCESS)) {
                statement.setString(8, this.serializeList(partnerEvents.getParameter(ProcessingEventTriggerType.RECEIPT_SUCCESS)));
            } else {
                statement.setNull(8, java.sql.Types.VARCHAR);
            }
            if (partnerEvents.hasParameter(ProcessingEventTriggerType.SEND_FAILURE)) {
                statement.setString(9, this.serializeList(partnerEvents.getParameter(ProcessingEventTriggerType.SEND_FAILURE)));
            } else {
                statement.setNull(9, java.sql.Types.VARCHAR);
            }
            if (partnerEvents.hasParameter(ProcessingEventTriggerType.SEND_SUCCESS)) {
                statement.setString(10, this.serializeList(partnerEvents.getParameter(ProcessingEventTriggerType.SEND_SUCCESS)));
            } else {
                statement.setNull(10, java.sql.Types.VARCHAR);
            }
            statement.executeUpdate();
        }
    }

    /**
     * Deletes a single partners event entry in the database
     *
     * @param configConnectionNoAutoCommit passed database connection - required
     * for transactional operation
     */
    public void deletePartnerEvents(Partner partner, Connection configConnectionNoAutoCommit) throws Exception {
        try (PreparedStatement statement = configConnectionNoAutoCommit.prepareStatement(
                "DELETE FROM partnerevent WHERE partnerid=?")) {
            statement.setInt(1, partner.getDBId());
            statement.executeUpdate();
        }
    }

}
