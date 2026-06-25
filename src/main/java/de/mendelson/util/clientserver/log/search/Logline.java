//$Header: /oftp2/de/mendelson/util/clientserver/log/search/Logline.java 13    13/06/25 12:35 Heller $
package de.mendelson.util.clientserver.log.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.log.LogFormatter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.util.BytesRef;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Abstract class for a search log line
 *
 * @author S.Heller
 * @version $Revision: 13 $
 */
public abstract class Logline implements Serializable, Comparable<Logline> {

    private static final long serialVersionUID = 1L;
    /**
     * Human readable timestamp - mainly not used. This field is just available
     * to allow humans to open and read the server logs in an editor
     */
    public static final String KEY_DATETIME = LogFormatter.KEY_DATETIME;
    /**
     * Timestamp of the logline, this is not unique over all log entries as
     * everything could happen in one ms....
     */
    public static final String KEY_MILLISECS = LogFormatter.KEY_MILLISECS;
    /**
     * Sequence number to use for result sorting if more than one log line
     * happened in one ms
     */
    public static final String KEY_SEQUENCE = LogFormatter.KEY_SEQUENCE;
    /**
     * The log message text itself..
     */
    public static final String KEY_LOGMESSAGE = LogFormatter.KEY_LOGMESSAGE;
    /**
     * Stores all standard values of the log line, also implementation specific
     * data
     */
    private Map<String, String> map = new HashMap<String, String>();

    /**This is a dummy constructor for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    protected Logline(){        
    }
    
    
    /**
     * Pre parsed data - creates a log line from the data found in a lucene
     * result
     */    
    protected Logline(Map<String, String> map) {
        this.map.putAll(map);
    }

    /**
     * Creates a new logline - mainly from a splitted line found in the log file
     */
    protected Logline(String[] header, String logMessage) {
        this.putValue(KEY_LOGMESSAGE, logMessage);
        for (int i = 0; i < header.length; i++) {
            int indexEqualSign = header[i].indexOf("=");
            if (indexEqualSign > 0) {
                String key = header[i].substring(0, indexEqualSign).trim();
                String value = header[i].substring(indexEqualSign + 1).trim();
                this.putValue(key, value);
            }
        }
    }

    /**
     * Add a value to the logline. Uses implementation specific keys
     */
    @JsonIgnore
    public String putValue(final String KEY, String value) {
        return (this.map.put(KEY, value));
    }

    /**
     * Gets a stored value of the logline. Contains the standard logline values
     * and imeplementation specific values
     *
     * @param KEY
     * @return
     */
    @JsonIgnore
    public String getValue(final String KEY) {
        return (this.getMap().get(KEY));
    }

    /**
     * mendelson product specific extensions of the data stored in a lucene
     * document. Allows to search for results by adding message id, transmission id, 
     * session id, mep number, user defined ids etc..
     *
     * @param luceneDocument
     */
    public abstract void addAdditionalFieldsToDocument(Document luceneDocument);

    /**
     * Generates a lucene entry from a logline and adds the implementation
     * specific values to the logline
     *
     * @return
     */
    @JsonIgnore
    public Document generateLuceneDocument() {
        Document luceneDocument = new Document();
        if (this.getValue(KEY_LOGMESSAGE) != null) {
            luceneDocument.add(new StringField(
                    KEY_LOGMESSAGE, this.getValue(KEY_LOGMESSAGE), Field.Store.YES));
        }
        if (this.getValue(KEY_SEQUENCE) != null) {
            luceneDocument.add(new StringField(KEY_SEQUENCE, this.getValue(KEY_SEQUENCE), Field.Store.YES));
        }
        if (this.getValue(KEY_DATETIME) != null) {
            luceneDocument.add(new StringField(
                    KEY_DATETIME, this.getValue(KEY_DATETIME), Field.Store.YES));
        }
        if (this.getValue(KEY_MILLISECS) != null) {
            luceneDocument.add(new StoredField(KEY_MILLISECS, this.getValue(KEY_MILLISECS)));
            //the results should be sorted by the ms. This seems to make no sense but if there are
            //more results than maxresult only the newest should return
            luceneDocument.add(new SortedDocValuesField(KEY_MILLISECS, new BytesRef(this.getValue(KEY_MILLISECS))));
        }
        return (luceneDocument);
    }

    @Override
    /**
     * Allows to sort the results of a search. The search is on the first heand
     * done by the timestamp in ms. If there are two or more entries with the
     * same timestamp there is an additional (sorted) field called sequence that
     * allows to order entries in one ms
     */
    public int compareTo(Logline other) {
        String otherMS = other.getValue(KEY_MILLISECS);
        String ownMS = this.getValue(KEY_MILLISECS);

        if (otherMS == null || ownMS == null) {
            return (0);
        }
        //same processing millisec - compare the sequence id
        if (ownMS.equals(otherMS)) {
            String otherSEQ = other.getValue(KEY_SEQUENCE);
            String ownSEQ = this.getValue(KEY_SEQUENCE);
            if (otherSEQ == null || ownSEQ == null) {
                return (0);
            }
            return (ownSEQ.compareTo(otherSEQ));
        } else {
            return (ownMS.compareTo(otherMS));
        }
    }

    /**This is a dummy method for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public Map<String, String> getMap() {
        return map;
    }

   /**This is a dummy method for the deserialization process. Do not use in logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setMap(Map<String, String> map) {
        this.map.clear();
        this.map.putAll(map);
    }

}
