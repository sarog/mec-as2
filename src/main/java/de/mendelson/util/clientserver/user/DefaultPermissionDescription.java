//$Header: /as2/de/mendelson/util/clientserver/user/DefaultPermissionDescription.java 5     2/11/23 15:53 Heller $
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
 * @version $Revision: 5 $
 */
public class DefaultPermissionDescription implements PermissionDescription{

    /**PermissionDescription extends Serializable*/
    private static final long serialVersionUID = 1L;
    public DefaultPermissionDescription(){        
    }

    @Override
    public String getDescription( int permissionIndex ){
        return( "Permission" + permissionIndex );
    }
    
}
