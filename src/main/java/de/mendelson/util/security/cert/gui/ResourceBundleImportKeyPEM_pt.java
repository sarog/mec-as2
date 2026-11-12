//$Header: /as4/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyPEM_pt.java 1     9/12/25 17:13 Heller $
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
public class ResourceBundleImportKeyPEM_pt extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.browse", "Navegar"},
		{"button.cancel", "Cancelar"},
		{"button.ok", "Ok"},
		{"filechooser.key.import", "Selecione o ficheiro PEM"},
		{"key.import.error.entry.exists", "A importação não é possível - já existe uma entrada com esta impressão digital, o alias é {0}."},
		{"key.import.error.message", "Ocorreu um problema durante o processo de importação.\n{0}"},
		{"key.import.error.title", "Problema"},
		{"key.import.success.message", "A chave foi importada com sucesso."},
		{"key.import.success.title", "Sucesso"},
		{"label.importkey", "Nome do ficheiro"},
		{"label.importkey.hint", "Ficheiro PEM"},
		{"label.keypass", "Palavra-passe chave"},
		{"label.keypass.hint", "Palavra-passe da chave no ficheiro PEM"},
		{"title", "Importar chave de um ficheiro PEM"},
	};
}
