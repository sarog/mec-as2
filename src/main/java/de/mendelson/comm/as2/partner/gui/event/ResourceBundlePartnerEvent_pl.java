//$Header: /as2/de/mendelson/comm/as2/partner/gui/event/ResourceBundlePartnerEvent_pl.java 1     24/09/25 10:36 Heller $
package de.mendelson.comm.as2.partner.gui.event;

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
public class ResourceBundlePartnerEvent_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Rozbiórka"},
		{"button.ok", "Ok"},
		{"label.movetodir.info", "<HTML>Ustaw katalog po stronie serwera, do którego wiadomość ma zostać przeniesiona.</HTML>"},
		{"label.movetodir.remotedir.select", "Wybierz katalog docelowy na serwerze"},
		{"label.movetodir.targetdir", "Katalog docelowy ({0}):"},
		{"label.movetopartner", "Partner docelowy:"},
		{"label.movetopartner.info", "<HTML>Wybierz zdalnego partnera, do którego wiadomość ma zostać przekazana.</HTML>"},
		{"label.movetopartner.noroutingpartner", "<HTML>W systemie nie ma zdalnego partnera, do którego można wysyłać wiadomości. Najpierw dodaj partnera, do którego mają być wysyłane wiadomości.</HTML>"},
		{"label.shell.command", "({0}):"},
		{"label.shell.info", "<HTML>Ustaw polecenie powłoki, które ma być wykonane w tym przypadku. Należy pamiętać, że jest to specyficzne dla systemu operacyjnego, zostanie przekierowane do domyślnej powłoki systemu operacyjnego.</HTML>"},
		{"process.executeshell", "Wykonanie polecenia powłoki"},
		{"process.executeshell.description", "Wykonanie polecenia powłoki lub skryptu w celu przetworzenia danych."},
		{"process.movetodirectory", "Przenieś do katalogu"},
		{"process.movetodirectory.description", "Przenieś dane do innego katalogu"},
		{"process.movetopartner", "Przekazywanie do partnerów"},
		{"process.movetopartner.description", "Przekazywanie do partnera, na przykład z DMZ do systemu ERP."},
		{"shell.hint.replacement.1", "<HTML>Następujące zmienne są zastępowane wartościami systemowymi w tym poleceniu przed jego wykonaniem:<br>"
			+"<i>$'{'filename}, $'{'fullstoragefilename}, $'{'log}, $'{'subject},$'{'sender}, $'{'receiver}, $'{'messageid}, $'{'mdntext}, $'{'userdefinedid}</i>.</HTML>"},
		{"shell.hint.replacement.2", "<HTML>Następujące zmienne są zastępowane wartościami systemowymi w tym poleceniu przed jego wykonaniem:<br>"
			+"<i>$'{'filename}, $'{'fullstoragefilename}, $'{'log}, $'{'subject},$'{'sender}, $'{'receiver}, $'{'messageid}, $'{'mdntext}, $'{'userdefinedid}</i>.</HTML>"},
		{"shell.hint.replacement.3", "<HTML>Następujące zmienne są zastępowane wartościami systemowymi w tym poleceniu przed jego wykonaniem:<br>"
			+"<i>$'{'filename}, $'{'subject},$'{'sender}, $'{'receiver}, $'{'messageid}, $'{'originalfilename}</i>.</HTML>"},
		{"shell.hint.samples", "<HTML><strong>Przykłady</strong><br>"
			+"Windows: <i>cmd /c move \"$'{'filename}\" \"c:\target directory\"</i>.<br>"
			+"Linux: <i>mv \"$'{'nazwa_pliku}\" \"~/katalog_docelowy/\"</i>.</HTML>"},
		{"tab.newprocess", "Dostępne procesy przetwarzania końcowego"},
		{"title.configuration.movetodir", "Przenieś wiadomości do katalogu [Partner {0}, {1}]."},
		{"title.configuration.movetopartner", "Przekazywanie danych do partnera [Partner {0}, {1}]"},
		{"title.configuration.shell", "Konfiguracja poleceń powłoki [Partner {0}, {1}]"},
		{"title.select.process", "Wybierz nowy proces jako zdarzenie ({0})"},
		{"type.1", "po wysłaniu (sukces)"},
		{"type.2", "po wysłaniu (błąd)"},
		{"type.3", "po otrzymaniu"},
	};
}
