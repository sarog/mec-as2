//$Header: /as4/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyPEM_it.java 1     9/12/25 17:13 Heller $
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
public class ResourceBundleImportKeyPEM_it extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.browse", "Sfogliare"},
		{"button.cancel", "Annullamento"},
		{"button.ok", "Ok"},
		{"filechooser.key.import", "Selezionare il file PEM"},
		{"key.import.error.entry.exists", "L''importazione non è possibile: esiste già una voce con questa impronta digitale, l''alias è {0}."},
		{"key.import.error.message", "Si è verificato un problema durante il processo di importazione.\n{0}"},
		{"key.import.error.title", "Problema"},
		{"key.import.success.message", "La chiave è stata importata con successo."},
		{"key.import.success.title", "Il successo"},
		{"label.importkey", "Nome del file"},
		{"label.importkey.hint", "File PEM"},
		{"label.keypass", "Password chiave"},
		{"label.keypass.hint", "Password della chiave nel file PEM"},
		{"title", "Importare la chiave da un file PEM"},
	};
}
