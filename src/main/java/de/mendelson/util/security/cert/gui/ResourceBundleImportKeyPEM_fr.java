//$Header: /as4/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyPEM_fr.java 1     9/12/25 17:13 Heller $
package de.mendelson.util.security.cert.gui;

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
* @version $Revision: 1 $
*/
public class ResourceBundleImportKeyPEM_fr extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.browse", "Parcourir"},
		{"button.cancel", "Annuler"},
		{"button.ok", "Ok"},
		{"filechooser.key.import", "Veuillez sélectionner le fichier PEM"},
		{"key.import.error.entry.exists", "L''importation n''est pas possible - une entrée avec ce fingerprint existe déjà, l''alias est {0}."},
		{"key.import.error.message", "Il y a eu un problème pendant le processus d''importation.\n{0}"},
		{"key.import.error.title", "Problème"},
		{"key.import.success.message", "La clé a été importée avec succès."},
		{"key.import.success.title", "Succès"},
		{"label.importkey", "Nom de fichier"},
		{"label.importkey.hint", "Fichier PEM"},
		{"label.keypass", "Mot de passe clé"},
		{"label.keypass.hint", "Mot de passe clé dans le fichier PEM"},
		{"title", "Importer des clés à partir d''un fichier PEM"},
	};
}
