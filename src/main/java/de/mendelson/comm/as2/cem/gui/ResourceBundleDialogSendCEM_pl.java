//$Header: /as2/de/mendelson/comm/as2/cem/gui/ResourceBundleDialogSendCEM_pl.java 2     21/10/25 10:50 Heller $
package de.mendelson.comm.as2.cem.gui;

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
public class ResourceBundleDialogSendCEM_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Anuluj"},
		{"button.ok", "Ok"},
		{"cem.informed", "Podjęto próbę poinformowania następujących partnerów za pośrednictwem CEM, sprawdź powodzenie w administracji CEM: {0}"},
		{"cem.not.informed", "Następujący partnerzy nie zostali poinformowani za pośrednictwem CEM, prosimy o przeprowadzenie wymiany certyfikatów za pośrednictwem poczty elektronicznej lub podobnej: {0}"},
		{"cem.request.failed", "Żądanie CEM nie mogło zostać wykonane:\n{0}"},
		{"cem.request.success", "Żądanie CEM zostało pomyślnie wykonane."},
		{"cem.request.title", "Wymiana certyfikatów za pośrednictwem CEM"},
		{"label.activationdate", "Data aktywacji"},
		{"label.certificate", "Certyfikat"},
		{"label.initiator", "Stacja lokalna"},
		{"label.receiver", "Odbiorca"},
		{"partner.all", "-Wszyscy partnerzy--"},
		{"partner.cem.hint", "Systemy partnerskie muszą obsługiwać CEM, aby mogły zostać tutaj uwzględnione"},
		{"purpose.encryption", "Szyfrowanie"},
		{"purpose.signature", "Podpis cyfrowy"},
		{"purpose.ssl", "TLS"},
		{"title", "Wymiana certyfikatów z partnerami (CEM)"},
	};
}
