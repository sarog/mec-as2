//$Header: /as2/de/mendelson/comm/as2/partner/clientserver/PartnerListRequest.java 13    9/04/26 8:46 Heller $
package de.mendelson.comm.as2.partner.clientserver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
 *
 * @author S.Heller
 * @version $Revision: 13 $
 */
public class PartnerListRequest extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public static enum ListOption {
        ALL(1),
        LOCALSTATION(2),
        NON_LOCALSTATIONS(3),
        AS2_ID(4),
        DB_ID(5),
        NON_LOCALSTATIONS_SUPPORTING_CEM(6),
        PARTNER_NAME(7),
        ALL_NO_CACHE(8);

        private final int value;

        private ListOption(int value) {
            this.value = value;
        }

        @JsonValue
        public int toInt() {
            return value;
        }

        @JsonCreator
        public static ListOption of(int value) {
            for (ListOption option : ListOption.values()) {
                if (option.value == value) {
                    return option;
                }
            }
            throw new IllegalArgumentException("PartnerListRequest.ListOption: Unknown value: " + value);
        }
    }

    private ListOption listOption = ListOption.ALL;

    private String additionalListOptionStr = null;
    private int additionalListOptionInt = -1;

    /**
     * This is the constructor for the option LIST_ALL
     */
    public PartnerListRequest() {
    }

    public PartnerListRequest(PartnerListRequest.ListOption listOption) {
        this.listOption = listOption;
    }

    @Override
    public String toString() {
        return ("List partner");
    }

    /**
     * @return the listOption
     */
    public PartnerListRequest.ListOption getListOption() {
        return listOption;
    }

    /**
     * @param listOption the listOption to set
     */
    public void setListOption(PartnerListRequest.ListOption listOption) {
        this.listOption = listOption;
    }

    /**
     * @return the additionalListOption
     */
    public String getAdditionalListOptionStr() {
        return additionalListOptionStr;
    }

    /**
     */
    public void setAdditionalListOptionStr(String additionalListOptionStr) {
        this.additionalListOptionStr = additionalListOptionStr;
    }

    /**
     * @return the additionalListOptionInt
     */
    public int getAdditionalListOptionInt() {
        return additionalListOptionInt;
    }

    /**
     * @param additionalListOptionInt the additionalListOptionInt to set
     */
    public void setAdditionalListOptionInt(int additionalListOptionInt) {
        this.additionalListOptionInt = additionalListOptionInt;
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }
}
