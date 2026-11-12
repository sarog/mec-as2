//$Header: /oftp2/de/mendelson/util/clientserver/connectiontest/OFTP2AbstractCommand.java 2     17/03/26 10:28 Heller $
package de.mendelson.util.clientserver.connectiontest;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Superclass for the commands
 * @author S.Heller
 * @version $Revision: 2 $
 */
public abstract sealed class OFTP2AbstractCommand implements OFTP2Command 
        permits OFTP2SSRM{


    @Override
    public int getMaxLength() {
        OFTP2Field[] fields = this.getFields();
        int length = 0;
        for( OFTP2Field field:fields){
            length += field.getMaxLength();
        }
        return( length );
    }

    @Override
    public abstract String getIndicator();

    @Override
    public abstract OFTP2Field[] getFields();

    /**Will throw an IllegalArgumentException if the name does not exist*/
    @Override
    public OFTP2Field getField( final String FIELD_NAME ){
        for( OFTP2Field field: this.getFields()){
            if( field.getName().equals( FIELD_NAME )){
                return( field );
            }
        }
        throw new IllegalArgumentException( "Field " + FIELD_NAME + " is not defined in the Command "
                + this.getDescription() );
    }

}
