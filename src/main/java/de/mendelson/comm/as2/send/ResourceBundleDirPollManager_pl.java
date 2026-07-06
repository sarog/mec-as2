//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleDirPollManager_pl.java 1     24/09/25 10:38 Heller $
package de.mendelson.comm.as2.send;

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
public class ResourceBundleDirPollManager_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"manager.status.modified", "Zmieniono monitorowanie katalogów, monitorowane są {0} katalogi"},
		{"messagefile.deleted", "Plik \"{0}\" został usunięty i przeniesiony do kolejki przetwarzania serwera."},
		{"none", "Brak"},
		{"poll.log.polling", "[Monitorowanie katalogów] {0}->{1}: Sprawdź katalog \"{2}\" pod kątem nowych plików"},
		{"poll.log.wait", "[Monitorowanie katalogów] {0}->{1}: Następny proces ankiety za {2}s ({3})"},
		{"poll.modified", "[Monitorowanie katalogów] Ustawienia partnera dla relacji \"{0}/{1}\" zostały zmienione."},
		{"poll.started", "Rozpoczęto monitorowanie katalogu dla relacji \"{0}/{1}\". Ignoruj: \"{2}\". Interwał: {3}s"},
		{"poll.stopped", "Monitorowanie katalogu dla relacji \"{0}/{1}\" zostało zatrzymane."},
		{"poll.stopped.notscheduled", "[Monitorowanie katalogów] System próbował zatrzymać monitorowanie katalogów dla \"{0}/{1}\" - ale nie było żadnego monitorowania."},
		{"processing.file", "Przetwórz plik \"{0}\" dla relacji \"{1}/{2}\"."},
		{"processing.file.error", "Błąd przetwarzania pliku \"{0}\" dla relacji \"{1}/{2}\": \"{3}\"."},
		{"title.list.polls.running", "Podsumowanie monitorowanych katalogów:"},
		{"title.list.polls.started", "Rozpoczęto następujące programy monitorowania"},
		{"title.list.polls.stopped", "Następujące działania monitorujące zostały zakończone"},
		{"warning.noread", "[Monitorowanie katalogów] Brak możliwości odczytu dla pliku źródłowego {0}, plik jest ignorowany."},
		{"warning.notcomplete", "[Monitorowanie katalogów] Plik źródłowy {0} nie jest jeszcze w pełni dostępny, plik jest ignorowany."},
		{"warning.ro", "[Monitorowanie katalogu] Plik wyjściowy {0} jest chroniony przed zapisem, plik ten jest ignorowany."},
	};
}
