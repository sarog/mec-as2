//$Header: /oftp2/de/mendelson/util/systemevents/gui/ResourceBundleDialogSystemEvent_pt.java 3     13/06/25 15:30 Heller $
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
public class ResourceBundleDialogSystemEvent_pt extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"category.all", "-- Todos"},
		{"header.category", "Categoria"},
		{"header.timestamp", "Carimbo de data/hora"},
		{"header.type", "Tipo"},
		{"label.category", "Categoria"},
		{"label.close", "Fechar"},
		{"label.date", "data"},
		{"label.enddate", "Fim"},
		{"label.freetext", "Texto de pesquisa"},
		{"label.freetext.hint", "Pesquisa de número de evento ou de texto"},
		{"label.host", "Anfitrião"},
		{"label.id", "Número do evento"},
		{"label.resetfilter", "Reiniciar"},
		{"label.search", "Pesquisa de eventos"},
		{"label.startdate", "Início"},
		{"label.type", "Tipo"},
		{"label.user", "Proprietário"},
		{"no.data", "Não existe nenhum evento do sistema que corresponda à seleção de data/tipo atual."},
		{"title", "Visualização de eventos do sistema"},
		{"user.server.process", "Processo do servidor"},
	};
}
