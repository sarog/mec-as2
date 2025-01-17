//$Header: /as2/de/mendelson/comm/as2/partner/gui/ResourceBundlePartnerConfig.java 14    2/11/23 15:52 Heller $
package de.mendelson.comm.as2.partner.gui;
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
 * @version $Revision: 14 $
 */
public class ResourceBundlePartnerConfig extends MecResourceBundle{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "Partner configuration" },
        {"button.ok", "Ok" },
        {"button.cancel", "Cancel" },
        {"button.new", "New" },
        {"button.clone", "Clone" },
        {"button.delete", "Delete" },
        {"button.globalchange", "Global" },
        {"nolocalstation.message", "There has to be defined min one partner as local station." },
        {"nolocalstation.title", "No local station" },
        {"localstation.noprivatekey.message", "Please assign a private key to every of the local stations." },
        {"localstation.noprivatekey.title", "No key assignment" },
        {"dialog.partner.delete.message", "You are up to delete the partner \"{0}\" from the partner configuration.\nAll the partners data of \"{0}\" will be lost.\n\nDo you really want to delete the partner \"{0}\"?" },
        {"dialog.partner.delete.title", "Delete partner" },
        {"dialog.partner.deletedir.message", "The partner \"{0}\" has been deleted. Should the underlaying directory\n\"{1}\"\nbe deleted on the harddisk, too?" },
        {"dialog.partner.deletedir.title", "Delete partner message directory" },
        {"dialog.partner.renamedir.message", "The partner \"{0}\" has been renamed to \"{1}\". Should the underlaying directory\n\"{2}\"\nbe renamed on the harddisk, too?" },
        {"dialog.partner.renamedir.title", "Rename partner message directory" },
        {"directory.rename.failure", "Unable to rename \"{0}\" to \"{1}\"." },
        {"directory.rename.success", "The directory \"{0}\" has been renamed to \"{1}\"." },
        {"directory.delete.failure", "Unable to delete \"{0}\": [\"{1}\"]" },
        {"directory.delete.success", "The directory \"{0}\" has been deleted." },
        {"saving", "Saving..." },
        {"module.locked", "The partner management is locked by another client, you are not allowed to commit your changes!" },
        {"event.partner.deleted.subject", "The partner {0} has been deleted by the user" },
        {"event.partner.deleted.body", "Key data of this partner:\n\n{0}" },
        {"event.partner.added.subject", "The partner {0} has been added by the user" },
        {"event.partner.added.body", "Key data of the new partner:\n\n{0}" },
        {"event.partner.modified.subject", "The partner {0} has been modified by the user" },
        {"event.partner.modified.body", "Key data of the formerly configuration:\n\n{0}\n\nKey data of the new configuration:\n\n{1}" },
    };
    
}