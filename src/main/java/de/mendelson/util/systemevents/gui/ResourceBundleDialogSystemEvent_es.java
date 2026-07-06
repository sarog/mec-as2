//$Header: /oftp2/de/mendelson/util/systemevents/gui/ResourceBundleDialogSystemEvent_es.java 3     13/06/25 15:30 Heller $
package de.mendelson.util.systemevents.gui;

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
public class ResourceBundleDialogSystemEvent_es extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"category.all", "-- Todos --"},
		{"header.category", "Categoría"},
		{"header.timestamp", "Marca de tiempo"},
		{"header.type", "Tipo"},
		{"label.category", "Categoría"},
		{"label.close", "Cerrar"},
		{"label.date", "fecha"},
		{"label.enddate", "Fin"},
		{"label.freetext", "Buscar texto"},
		{"label.freetext.hint", "Búsqueda por número de evento o texto"},
		{"label.host", "Anfitrión"},
		{"label.id", "Número del acontecimiento"},
		{"label.resetfilter", "Restablecer"},
		{"label.search", "Búsqueda de eventos"},
		{"label.startdate", "Inicio"},
		{"label.type", "Tipo"},
		{"label.user", "Propietario"},
		{"no.data", "No hay ningún evento del sistema que coincida con la selección de fecha/tipo actual."},
		{"title", "Vista de los eventos del sistema"},
		{"user.server.process", "Proceso de servidor"},
	};
}
