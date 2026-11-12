//$Header: /mec_as2/de/mendelson/comm/as2/AS2ServerVersion.java 98    15/04/26 16:11 Heller $
package de.mendelson.comm.as2;

import de.mendelson.Copyright;
import de.mendelson.IProductVersion;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Class to check the version and the build of the AS2 server
 *
 * @author S.Heller
 * @version $Revision: 98 $
 */
public class AS2ServerVersion implements IProductVersion {

    private static AS2ServerVersion instance;

    /**
     * Prevent a direct instance by new()
     */
    private AS2ServerVersion() {
    }

    /**
     * Singleton for the whole application
     */
    public static synchronized AS2ServerVersion instance() {
        if (instance == null) {
            instance = new AS2ServerVersion();
        }
        return instance;
    }

    /**
     * Returns the used user agent
     */
    public static String getUserAgent() {
        StringBuilder agent = new StringBuilder();
        agent.append(AS2ServerVersion.getProductName()).append(" ");
        agent.append(AS2ServerVersion.getVersion()).append(" ");
        agent.append(AS2ServerVersion.getBuild()).append(" - www.mendelson.de");
        return (agent.toString());
    }

    /**
     * Returns this products version number
     */
    public static String getVersion() {
        return ("1.1");
    }

    /**
     * Returns the internal build number
     */
    public static String getBuild() {
        return ("build " + getBuildNo());
    }

    /**
     * Returns the internal build number
     */
    public static int getBuildNo() {
        return (69);
    }

    /**
     * returns the name of this product
     */
    public static String getProductName() {
        return ("mendelson opensource AS2");
    }

    /**
     * The data base has a version. It is found in the column actualversion of
     * the table version. If the found version does not match the version
     * defined here, an auto update of the database is performed.
     */
    public static int getRequiredDBVersionConfig() {
        return (57);
    }

    /**
     * The data base has a version. It is found in the column actualversion of
     * the table version. If the found version does not match the version
     * defined here, an auto update of the database is performed.
     */
    public static int getRequiredDBVersionRuntime() {
        return (56);
    }

    /**
     * returns the short name of this product, return the full name if not such
     * short name exists!
     */
    public static String getProductNameShortcut() {
        return ("mendelson opensource AS2");
    }

    /**
     * Returns the date the package was last modified
     */
    public static String getLastModificationDate() {
        String fullDate = "$Date: 15/04/26 16:11 $";
        return (fullDate.substring(fullDate.indexOf(":") + 1, fullDate.lastIndexOf("$")));
    }

    /**
     * Gets the copyright message for this product
     */
    public static String getCopyrightMessage() {
        return (Copyright.getCopyrightMessage());
    }

    /**
     * Gets the company name
     */
    public static String getCompany() {
        return ("mendelson-e-commerce GmbH");
    }

    /**
     * Gets the company address
     */
    public static String getStreet() {
        return ("Kurfürstendamm 30");
    }

    /**
     * Gets the company zip
     */
    public static String getZip() {
        return ("D-10719 Berlin");
    }

    /**
     * Gets the company info email
     */
    public static String getInfoEmail() {
        return ("service@mendelson.de");
    }

    /**
     * Returns the full name with build, version etc
     */
    public static String getFullProductName() {
        StringBuilder builder = new StringBuilder();
        builder.append(AS2ServerVersion.getProductName())
                .append(" ")
                .append(AS2ServerVersion.getVersion())
                .append(" ")
                .append(AS2ServerVersion.getBuild());
        return (builder.toString());
    }

    /**
     * Makes this a IProductVersion
     */
    @Override
    public String getFullName() {
        return (AS2ServerVersion.getFullProductName());
    }

    @Override
    public long getMagicNumber() {
        //"MENOSAS2" in ASCII
        return 0x4D454E4F53415332L;
    }
}
