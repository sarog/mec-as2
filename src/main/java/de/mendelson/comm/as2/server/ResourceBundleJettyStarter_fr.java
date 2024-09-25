//$Header: /as2/de/mendelson/comm/as2/server/ResourceBundleJettyStarter_fr.java 2     2/05/22 10:22 Heller $
package de.mendelson.comm.as2.server;

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
 * @author E.Pailleau
 * @version $Revision: 2 $
 */
public class ResourceBundleJettyStarter_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"module.name", "[JETTY]" },
        {"httpserver.willstart", "D�marrage du serveur HTTP" },
        {"httpserver.running", "Serveur HTTP en cours d''ex�cution ({0})" },
        {"httpserver.startup.problem", "Probl�me au d�marrage ({0})" },
        {"httpserver.stopped", "Le serveur HTTP int�gr� s''est arr�t�" },
        {"deployment.success", "[{0}] a �t� d�ploy� avec succ�s" },
        {"deployment.failed", "[{0}] n''a PAS �t� d�ploy� : {1}" },
        {"listener.started", "Attente de connexions entrantes {0}"},
        {"userconfiguration.readerror", "Probl�me de lecture de la configuration utilisateur de {0} : {1} ... Ignorer la configuration utilisateur et d�marrer le serveur web en utilisant les valeurs par d�faut d�finies" },
        {"userconfiguration.reading", "Lire la configuration personnalis�e de {0}" },
        {"userconfiguration.setvar", "D�finir la valeur personnalis�e [{0}] sur [{1}]" },
    };
}
