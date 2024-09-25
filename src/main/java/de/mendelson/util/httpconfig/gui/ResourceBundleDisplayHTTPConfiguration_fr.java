//$Header: /as2/de/mendelson/util/httpconfig/gui/ResourceBundleDisplayHTTPConfiguration_fr.java 10    17/01/23 11:06 Heller $ 
package de.mendelson.util.httpconfig.gui;

import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/** 
 * ResourceBundle to localize gui entries
 * @author S.Heller
 * @version $Revision: 10 $
 */
public class ResourceBundleDisplayHTTPConfiguration_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "Configuration HTTP c�t� serveur"},
        {"reading.configuration", "Lire la configuration HTTP..."},
        {"button.ok", "Fermer" },
        {"label.info.configfile", "Cette bo�te de dialogue vous montre la configuration HTTP/S c�t� serveur. Le serveur HTTP fourni a la version <strong>jetty {0}</strong>. "
            + "Vous pouvez configurer les codes et les protocoles dans le fichier \"{1}\" du serveur. Les param�tres de base peuvent �tre configur�s dans "
            + "le fichier \"{2}\" ou directement via les param�tres du serveur. Veuillez red�marrer le serveur pour les modifications � appliquer." },
        {"tab.misc", "General"},
        {"tab.cipher", "Chiffrement TLS"},
        {"tab.protocols", "Protocoles TLS"},
        {"no.ssl.enabled", "La prise en charge SSL n''�tait pas activ�e dans le serveur HTTP sous-jacent.\nVeuillez modifier le fichier de configuration {0}\nselon la documentation et red�marrer le serveur." },        
        {"no.embedded.httpserver", "Vous n''avez pas d�marr� le serveur HTTP sous-jacent.\nAucune information n'est disponible." },                
    };
}