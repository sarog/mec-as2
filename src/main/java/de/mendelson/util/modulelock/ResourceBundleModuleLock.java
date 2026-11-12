//$Header: /as2/de/mendelson/util/modulelock/ResourceBundleModuleLock.java 6     8/04/26 15:28 Heller $
package de.mendelson.util.modulelock;

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
 *
 * @author S.Heller
 * @version $Revision: 6 $
 */
public class ResourceBundleModuleLock extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {ModuleLock.Module.ENCSIGN_KEYSTORE.toDisplayStr(), "certificate management (encryption/signature)"},
        {ModuleLock.Module.PARTNER.toDisplayStr(), "partner management"},
        {ModuleLock.Module.SERVER_SETTINGS.toDisplayStr(), "server settings"},
        {ModuleLock.Module.TLS_KEYSTORE.toDisplayStr(), "certificate management (TLS)"},
        {"modifications.notallowed.message", "Modifications are not possible at the moment"},
        {"configuration.changed.otherclient", "Another client may have made changes to the module {0}.\nPlease reopen it to reload the configuration before you may modify it."},
        {"configuration.locked.otherclient", "The module {0} is exclusively opened by another client,\nyou are not allowed to make changes.\nOther clients details:\nIP: {1}\nUser: {2}\nProcess id: {3}"},};
}
