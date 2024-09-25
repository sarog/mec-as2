//$Header: /as4/de/mendelson/util/httpconfig/gui/ResourceBundleDisplayHTTPConfiguration_fr.java 4     12/11/17 1:29p Heller $ 
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
 * @version $Revision: 4 $
 */
public class ResourceBundleDisplayHTTPConfiguration_fr extends MecResourceBundle {

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "Configuration HTTP côté serveur"},
        {"reading.configuration", "Lire la configuration HTTP..."},
        {"button.ok", "Fermer" },
        {"label.info.configfile", "Cette boîte de dialogue vous montre la configuration HTTP/S côté serveur. Vous pouvez configurer les ports, les codes et les protocoles dans le fichier \"{0}\" du serveur. Veuillez redémarrer le serveur pour les modifications à appliquer." },
        {"tab.misc", "General"},
        {"tab.cipher", "Chiffrement SSL/TLS"},
        {"tab.protocols", "Protocoles SSL/TLS"},
        {"no.ssl.enabled", "La prise en charge TLS/SSL n''était pas activée dans le serveur HTTP sous-jacent.\nVeuillez modifier le fichier de configuration {0}\nselon la documentation et redémarrer le serveur." },
        {"info.cipher", "Les codes suivants sont pris en charge par le serveur HTTP sous-jacent.\nLes modèles supportés dépendent de votre Java VM ({1}).\nVous pouvez désactiver les différents chiffres dans le fichier de configuration\n(\"{0}\")." },
        {"info.protocols", "Les protocoles suivants sont pris en charge par le serveur HTTP sous-jacent.\nLes protocoles pris en charge dépendent de votre VM Java utilisé ({1}).\nVous pouvez désactiver les protocoles individuels dans le fichier de configuration\n(\"{0}\")." },
        {"no.embedded.httpserver", "Vous n''avez pas démarré le serveur HTTP sous-jacent.\nAucune information n'est disponible." },
    };
}