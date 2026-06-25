//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ResourceBundleExecuteMoveToPartner_pl.java 1     24/09/25 10:35 Heller $
package de.mendelson.comm.as2.message.postprocessingevent;

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
public class ResourceBundleExecuteMoveToPartner_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"executing.movetopartner", "[Przekaż wiadomość z pliku \"{0}\" do partnera docelowego \"{1}\"."},
		{"executing.movetopartner.success", "[Przetwarzanie końcowe] Zlecenie wysyłki zostało pomyślnie utworzone (\"{0}\")."},
		{"executing.receipt", "[Przetwarzanie końcowe] ({0} --> {1}) Wykonaj zdarzenie po jego otrzymaniu."},
		{"executing.send", "[Przetwarzanie końcowe] ({0} --> {1}) Wykonaj zdarzenie po wysłaniu."},
		{"executing.targetpartner", "[Przetwarzanie końcowe] Partner docelowy: \"{0}\"."},
		{"messageid.nolonger.exist", "[Post-processing] Zdarzenie post-processingu nie mogło zostać wykonane - komunikat \"{0}\" nie istnieje już w systemie..pomiń wykonanie zdarzenia"},
		{"targetpartner.does.not.exist", "[Postprocessing] Partner docelowy z identyfikatorem AS2 \"{0}\" nie istnieje w systemie..pomiń wykonanie zdarzenia"},
	};
}
