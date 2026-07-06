//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ResourceBundleExecuteMoveToDir_pl.java 1     24/09/25 10:35 Heller $
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
public class ResourceBundleExecuteMoveToDir_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"executing.movetodir", "[Przetwarzanie końcowe] Przenieś \"{0}\" do \"{1}\"."},
		{"executing.movetodir.success", "[Przetwarzanie końcowe] Plik przeniesiony pomyślnie"},
		{"executing.receipt", "[Przetwarzanie końcowe] ({0} --> {1}) Wykonaj zdarzenie po jego otrzymaniu."},
		{"executing.send", "[Przetwarzanie końcowe] ({0} --> {1}) Wykonaj zdarzenie po wysłaniu."},
		{"executing.targetdir", "[Przetwarzanie końcowe] Katalog docelowy: \"{0}\"."},
		{"messageid.nolonger.exist", "[Przetwarzanie końcowe] Zdarzenie dla komunikatu \"{0}\" nie mogło zostać wykonane - już nie istnieje. Proces został pominięty..."},
	};
}
