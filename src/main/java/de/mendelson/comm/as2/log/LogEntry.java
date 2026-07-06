//$Header: /as2/de/mendelson/comm/as2/log/LogEntry.java 14    9/03/26 13:40 Heller $
package de.mendelson.comm.as2.log;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Enwrapps a single db log entry in an object
 *
 * @author S.Heller
 * @version $Revision: 14 $
 */
public class LogEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Level level;
    private String message;
    private long millis;
    private String messageId;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getMillis() {
        return millis;
    }

    public void setMillis(long millis) {
        this.millis = millis;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * Serializes this partner to XML
     *
     * @param level level in the XML hierarchie for the xml beautifying
     */
    public String toXML(int level) {
        String offset = "\t".repeat(level);
        StringBuilder builder = new StringBuilder();
        builder.append(offset).append("<logentry level=\"").append(String.valueOf(this.level.intValue())).append("\"");
        builder.append(" time=\"").append(this.getMillis()).append("\">");
        if (this.message != null) {
            builder.append(this.toCDATA(this.message));
        }
        builder.append(offset).append("</logentry>\n");
        return (builder.toString());
    }

    /**
     * Adds a cdata indicator to xml data
     */
    private String toCDATA(String data) {
        return ("<![CDATA[" + data + "]]>");
    }
    
    /**Adds this entry to the passed parent JSON node*/
    public void addToJSON( ArrayNode parent){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(ZoneOffset.UTC);
        ObjectNode node = parent.addObject();        
        node.put( "timestamp", dateFormat.format(Instant.ofEpochMilli(this.getMillis())));
        node.put( "unixtimestamp", this.getMillis());
        node.put( "level", String.valueOf(this.level.intValue()));
        node.put( "entry", this.message);
    }
    
}
