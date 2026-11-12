//$Header: /as2/de/mendelson/comm/as2/message/postprocessingevent/ResourceBundleExecuteShellCommand_pl.java 1     24/09/25 10:35 Heller $
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
public class ResourceBundleExecuteShellCommand_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"executed.command", "[Przetwarzanie końcowe] Polecenie powłoki zostało wykonane, wartość zwracana={0}."},
		{"executing.command", "[Przetwarzanie końcowe] Polecenie powłoki: \"{0}\"."},
		{"executing.receipt", "[Przetwarzanie końcowe] ({0} --> {1}) Wykonaj zdarzenie po odebraniu danych."},
		{"executing.send", "[Przetwarzanie końcowe] ({0} --> {1}) Wykonaj zdarzenie wysyłania danych."},
		{"messageid.nolonger.exist", "[Przetwarzanie końcowe] Nie można wykonać zdarzenia przetwarzania końcowego dla komunikatu \"{0}\" - ten komunikat już nie istnieje. Pomiń przetwarzanie..."},
	};
}
