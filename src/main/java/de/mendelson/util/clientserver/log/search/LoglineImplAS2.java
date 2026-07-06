//$Header: /as2/de/mendelson/util/clientserver/log/search/LoglineImplAS2.java 4     13/06/25 11:22 Heller $
package de.mendelson.util.clientserver.log.search;

import de.mendelson.util.clientserver.SerializationDummy;
import java.io.Serializable;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Stores the information about an event
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class LoglineImplAS2 extends Logline implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String KEY_MESSAGEID = "msgid";
    public static final String KEY_MDNID = "mdnid";
    public static final String KEY_USERDEFINED_ID = "uid";

    /**
     * Pre parsed data
     */
    public LoglineImplAS2(Map<String, String> map) {
        super(map);
    }

    public LoglineImplAS2(String[] header, String logMessage) {
        super(header, logMessage);
    }

    /**This is a dummy constructor for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public LoglineImplAS2() {
        super();
    }
    
    @Override
    public void addAdditionalFieldsToDocument(Document luceneDocument) {
        if (this.getValue(KEY_MESSAGEID) != null) {
            luceneDocument.add(new StringField(
                    KEY_MESSAGEID, this.getValue(KEY_MESSAGEID),
                    Field.Store.YES));
        }
        if (this.getValue(KEY_MDNID) != null) {
            luceneDocument.add(new StringField(
                    KEY_MDNID, this.getValue(KEY_MDNID),
                    Field.Store.YES));
        }
        if (this.getValue(KEY_USERDEFINED_ID) != null) {
            luceneDocument.add(new StringField(
                    KEY_USERDEFINED_ID, this.getValue(KEY_USERDEFINED_ID),
                    Field.Store.YES));
        }
    }

}
