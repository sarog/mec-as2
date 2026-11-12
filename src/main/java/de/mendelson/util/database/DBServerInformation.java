//$Header: /as2/de/mendelson/util/database/DBServerInformation.java 2     17/09/25 13:16 Heller $
package de.mendelson.util.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores some information of the used data base system - just for information purpose
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class DBServerInformation implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private final AtomicReference<String> dbServerInfoStringRef = new AtomicReference<String>();
    
    private String productName = "UNKNOWN";
    private String productVersion = "UNKNOWN";
    private String host = "UNKNOWN";
    private String jdbcVersion = "UNKNOWN";
    
    public DBServerInformation(){
    }

    @JsonIgnore
    public String getDBServerInfoString(){
        String result = this.dbServerInfoStringRef.get();
        if (result == null) {
            String computed = this.computeDBServerInfoString();
            if (this.dbServerInfoStringRef.compareAndSet(null, computed)) {
                result = computed;
            } else {
                result = this.computeDBServerInfoString();
            }
        }
        //String is immutable, no copy required as return
        return result;
    }
    
    @JsonIgnore
    private String computeDBServerInfoString(){
        return( this.productName + " "
                + this.productVersion
                + "@" + this.host
                + " [JDBC " + jdbcVersion + "]");
    }
    
    
    
    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @return the productVersion
     */
    public String getProductVersion() {
        return productVersion;
    }

    /**
     * @return the address
     */
    public String getHost() {
        return host;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @param productVersion the productVersion to set
     */
    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    /**
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the jdbcVersion
     */
    public String getJDBCVersion() {
        return jdbcVersion;
    }

    /**
     * @param jdbcVersion the jdbcVersion to set
     */
    public void setJDBCVersion(String jdbcVersion) {
        this.jdbcVersion = jdbcVersion;
    }

}
