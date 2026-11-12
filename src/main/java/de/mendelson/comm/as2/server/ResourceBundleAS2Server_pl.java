//$Header: /as2/de/mendelson/comm/as2/server/ResourceBundleAS2Server_pl.java 1     24/09/25 10:39 Heller $
package de.mendelson.comm.as2.server;

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
public class ResourceBundleAS2Server_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"bind.exception", "{0}\nZdefiniowano port, który jest obecnie używany przez inny proces w systemie.\nMoże to być port klient-serwer lub port HTTP/S zdefiniowany w konfiguracji HTTP.\nZmień konfigurację lub zatrzymaj inny proces przed użyciem {1}."},
		{"fatal.limited.strength", "Ta maszyna wirtualna Java nie obsługuje wymaganej długości klucza. Przed uruchomieniem serwera mendelson AS2 należy zainstalować pliki \"Unlimited jurisdiction key strength policy\"."},
		{"server.already.running", "Wydaje się, że instancja mendelson AS2 jest już uruchomiona.\nMoże to jednak oznaczać, że poprzednia instancja nie została poprawnie zakończona. Jeśli jesteś pewien, że żadna inna instancja nie jest uruchomiona,\nusuń plik blokady \"{0}\"\n(data rozpoczęcia {1}) i uruchom ponownie serwer."},
		{"server.hello", "To jest {0}"},
		{"server.hello.licenseexpire", "Licencja wygasa za {0} dni ({1}). Musisz odnowić licencję za pośrednictwem pomocy technicznej mendelson (service@mendelson.de), jeśli chcesz nadal z niej korzystać po tym czasie."},
		{"server.hello.licenseexpire.single", "Licencja wygasa za {0} dzień ({1}). Musisz odnowić licencję za pośrednictwem pomocy technicznej mendelson (service@mendelson.de), jeśli chcesz nadal z niej korzystać."},
		{"server.nohttp", "Zintegrowany serwer HTTP nie został uruchomiony."},
		{"server.shutdown", "{0} wyłącza się."},
		{"server.start.details", "{0} Parametr:\n\nUruchamia zintegrowany serwer HTTP: {1}\nZezwala na połączenia klient-serwer z innych hostów: {2}\nPamięć sterty: {3}\nWersja Java: {4}\nUżytkownik systemu: {5}\nIdentyfikacja systemu: {6}"},
		{"server.started", "mendelson AS2 2025 build 652 uruchomiony w {0} ms."},
		{"server.started.issue", "Ostrzeżenie: Podczas uruchamiania serwera wykryto 1 problem z konfiguracją."},
		{"server.started.issues", "Ostrzeżenie: Podczas uruchamiania serwera wykryto {0} problemów z konfiguracją."},
		{"server.started.usedlibs", "Używane biblioteki"},
		{"server.startup.failed", "Wystąpił problem z uruchomieniem serwera - uruchomienie zostało anulowane"},
		{"server.willstart", "{0} startuje"},
	};
}
