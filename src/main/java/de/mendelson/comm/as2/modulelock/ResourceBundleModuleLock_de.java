//$Header: /as2/de/mendelson/comm/as2/modulelock/ResourceBundleModuleLock_de.java 2     26.05.14 17:59 Heller $
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
public class ResourceBundleModuleLock_de extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {ModuleLock.MODULE_ENCSIGN_KEYSTORE, "Zertifikatverwaltung (Verschlüsselungs-/Signatur)" },
        {ModuleLock.MODULE_PARTNER, "Partnerverwaltung" },
        {ModuleLock.MODULE_SERVER_SETTINGS, "Servereinstellungen" },
        {ModuleLock.MODULE_SSL_KEYSTORE, "Zertifikatverwaltung (SSL)" },
        {"modifications.notallowed.message", "Änderungen sind im Moment nicht möglich" },
        {"configuration.changed.otherclient", "Ein anderer Client könnte Änderungen im Modul {0} vorgenommen haben.\nBitte öffnen Sie diese Konfigurationsoberfläche erneut, um die aktuelle Konfiguration neu zu laden." },
        {"configuration.locked.otherclient", "Das Modul {0} ist exklusiv von einem anderen Client geöffnet, Sie können aktuell keine Änderungen vornehmen.\nDetails des anderen Clients:\nIP: {1}\nBenutzer: {2}" },                
    };
}
