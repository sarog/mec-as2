//$Header: /as2/de/mendelson/comm/as2/timing/ResourceBundleFileDeleteController_fr.java 5     15.01.21 15:11 Heller $
package de.mendelson.comm.as2.timing;

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
 * @author E.Pailleau
 * @version $Revision: 5 $
 */
public class ResourceBundleFileDeleteController_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"autodelete", "{0}: Le fichier a �t� automatiquement supprim� par le processus de maintenance du syst�me."},
        {"delete.title", "Suppression de fichiers par la maintenance du syst�me"},
        {"delete.title.log", "Suppression des r�pertoires de journaux par la maintenance du syst�me" },
        {"delete.title.tempfiles", "Donn�es temporaires"},
        {"delete.title._rawincoming", "Fichiers entrants en provenance de _rawincoming"},
        {"delete.header.logfiles", "Supprimer les fichiers journaux et les fichiers relatifs aux �v�nements du syst�me datant de plus de {0} jours" },
        {"success", "SUCCES"},
        {"failure", "ERREUR"},
        {"no.entries", "{0}: Aucune entr�e trouv�e" },
    };

}
