//$Header: /oftp2/de/mendelson/util/clientserver/user/DefaultPermissionDescription.java 2     10.09.10 16:44 Heller $
package de.mendelson.util.clientserver.user;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Describe all permissions
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class DefaultPermissionDescription implements PermissionDescription{

    public DefaultPermissionDescription(){        
    }

    @Override
    public String getDescription( int permissionIndex ){
        return( "Permission" + permissionIndex );
    }
    
}
