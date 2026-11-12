//$Header: /as4/de/mendelson/util/security/cert/gui/ResourceBundleImportKeyPEM_es.java 1     9/12/25 17:13 Heller $
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
public class ResourceBundleImportKeyPEM_es extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.browse", "Visite"},
		{"button.cancel", "Cancelar"},
		{"button.ok", "Ok"},
		{"filechooser.key.import", "Seleccione el archivo PEM"},
		{"key.import.error.entry.exists", "La importación no es posible - ya existe una entrada con esta huella digital, el alias es {0}."},
		{"key.import.error.message", "Se ha producido un problema durante el proceso de importación.\n{0}"},
		{"key.import.error.title", "Problema"},
		{"key.import.success.message", "La clave se ha importado correctamente."},
		{"key.import.success.title", "Éxito"},
		{"label.importkey", "Nombre del fichero"},
		{"label.importkey.hint", "Archivo PEM"},
		{"label.keypass", "Contraseña"},
		{"label.keypass.hint", "Contraseña clave en el archivo PEM"},
		{"title", "Importar clave desde archivo PEM"},
	};
}
