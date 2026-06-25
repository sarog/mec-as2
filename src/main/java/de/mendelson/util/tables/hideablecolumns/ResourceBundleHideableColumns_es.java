//$Header: /as2/de/mendelson/util/tables/hideablecolumns/ResourceBundleHideableColumns_es.java 2     9/12/24 16:03 Heller $
package de.mendelson.util.tables.hideablecolumns;

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
* @version $Revision: 2 $
*/
public class ResourceBundleHideableColumns_es extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"label.ok", "Ok"},
		{"header.icon", "[Icono de estado] - siempre visible"},
		{"header.column", "Columna"},
		{"title", "Configuración de las columnas"},
		{"header.visible", "Visible"},
		{"label.info", "Seleccione aquí las columnas visibles."},
	};
}
