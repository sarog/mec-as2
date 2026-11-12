//$Header: /as2/de/mendelson/comm/as2/timing/ResourceBundleFileDeleteController_es.java 3     9/09/25 16:23 Heller $
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
* @author S.Heller
* @version $Revision: 3 $
*/
public class ResourceBundleFileDeleteController_es extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"delete.title.log", "Eliminación de directorios de registro mediante el mantenimiento del sistema"},
		{"no.entries", "{0}: No se han encontrado entradas"},
		{"delete.title.tempfiles", "Archivos temporales"},
                {"delete.title.sentfiles", "Archivos enviados" },
		{"delete.title._rawincoming", "Archivos entrantes de _rawincoming"},
		{"success", "ÉXITO"},
		{"failure", "ERROR"},
		{"autodelete", "{0}: El fichero ha sido borrado automáticamente por el proceso de mantenimiento del sistema."},
		{"delete.header.logfiles", "Borrar archivos de registro y archivos de eventos del sistema con más de {0} días de antigüedad."},
		{"delete.title", "Eliminación de archivos mediante el mantenimiento del sistema"},
	};
}
