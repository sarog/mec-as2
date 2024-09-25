//$Header: /as2/de/mendelson/comm/as2/database/ResourceBundleDBDriverManager_fr.java 5     26/07/22 16:34 Heller $
package de.mendelson.comm.as2.database;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.database.IDBDriverManager;

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
 * @version $Revision: 5 $
 */
public class ResourceBundleDBDriverManager_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"module.name", "[BASE DE DONNEES]" },
        {"creating.database." + IDBDriverManager.DB_RUNTIME, "Cr�er une base de donn�es d'ex�cution"},
        {"creating.database." + IDBDriverManager.DB_CONFIG, "Cr�er une base de donn�es de configuration"},
        {"creating.database.details", "Host: {0}, Port: {1}, Utilisateur: {2}, Nom de la base de donn�es: {3}"},
        {"database.creation.success." + IDBDriverManager.DB_RUNTIME, "La base de donn�es d'ex�cution a �t� cr��e avec succ�s" },
        {"database.creation.success." + IDBDriverManager.DB_CONFIG, "La base de donn�es de configuration a �t� cr��e avec succ�s" },
        {"database.creation.failed." + IDBDriverManager.DB_RUNTIME, "Une erreur est survenue lors de la cr�ation de la base de donn�es d''ex�cution" },
        {"database.creation.failed." + IDBDriverManager.DB_CONFIG, "Une erreur est survenue lors de la cr�ation de la base de donn�es de configuration." },
    };

}
