//$Header: /as2/de/mendelson/comm/as2/database/ResourceBundleDBServer_fr.java 9     2.10.18 13:14 Heller $
package de.mendelson.comm.as2.database;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/** 
 * ResourceBundle to localize the mendelson business integration  - if you want to localize 
 * eagle to your language, please contact us: localize@mendelson.de
 * @author S.Heller
 * @author E.Pailleau
 * @version $Revision: 9 $
 */
public class ResourceBundleDBServer_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"database." + DBDriverManager.DB_CONFIG, "base de configuration" },
        {"database." + DBDriverManager.DB_RUNTIME, "base de maturit�" },
        {"dbserver.startup", "D�marrer le serveur de base de donn�es.." },
        {"dbserver.running", "{0} donn�es en cours d''ex�cution"},
        {"update.versioninfo", "Mise � jour automatique de BD: la version de BD trouv� est {0}"
            + ", la version de BD requise est {1}."},
        {"update.progress", "Mise � jour incrementale de base de donn�es ..."},
        {"update.progress.version.start", "(Commencement) La {1} a �t� mise � jour vers la version {0}."},
        {"update.progress.version.end", "(Fin) La {1} a �t� mise � jour vers la version {0}."},
        {"update.error", "FATAL: impossible de mettre � jour la base de donn�es "
            + " de la version {0} vers la version {1}.\n"
            + "Merci de supprimer enti�rement la base de donn�e par la suppression"
            + " de tous les fichiers  AS2_DB_*.* dans le r�pertoire d''installation.\n"
            + "Toute vos donn�es personnelles seront d�truite � l''issue de cette op�ration."},
        {"update.successfully", "La mise � jour de la BD vers la version requise a �t� r�alis�e avec succ�s."},
        {"update.notfound", "Pour la mise � jour, the fichier update{0}to{1}.sql et/ou "
            + "Update{0}to{1}.class doivent �tre pr�sents dans le r�pertoire sqlscript."},
        {"upgrade.required", "Une mise � jour est n�cessaire.\nS''il vous pla�t ex�cuter as2upgrade.bat ou as2upgrade.sh avant de d�marrer le serveur."},
    };
}
