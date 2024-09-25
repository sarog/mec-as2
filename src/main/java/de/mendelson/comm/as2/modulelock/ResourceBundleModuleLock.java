//$Header: /as2/de/mendelson/comm/as2/modulelock/ResourceBundleModuleLock.java 2     26.05.14 17:59 Heller $
package de.mendelson.comm.as2.modulelock;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class ResourceBundleModuleLock extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {ModuleLock.MODULE_ENCSIGN_KEYSTORE, "certificate management (encryption/signature)" },
        {ModuleLock.MODULE_PARTNER, "partner management" },
        {ModuleLock.MODULE_SERVER_SETTINGS, "server settings" },
        {ModuleLock.MODULE_SSL_KEYSTORE, "certificate management (SSL)" },
        {"modifications.notallowed.message", "Modifications are not possible at the moment" },
        {"configuration.changed.otherclient", "Another client may have made changes to the module {0}.\nPlease reopen it to reload the configuration before you may modify it." },
        {"configuration.locked.otherclient", "The module {0} is exclusively opened by another client, you are not allowed to make changes.\nOther clients details:\nIP: {1}\nUser: {2}" },                
    };
}
