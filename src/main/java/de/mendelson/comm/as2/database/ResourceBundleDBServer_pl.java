//$Header: /as2/de/mendelson/comm/as2/database/ResourceBundleDBServer_pl.java 1     24/09/25 10:32 Heller $
package de.mendelson.comm.as2.database;

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
public class ResourceBundleDBServer_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"database.1", "Baza danych konfiguracji"},
		{"database.2", "Baza danych środowiska uruchomieniowego"},
		{"dbserver.running.embedded", "Uruchomiony zintegrowany serwer DB {0}"},
		{"dbserver.running.external", "Zewnętrzny serwer DB {0} jest dostępny"},
		{"dbserver.shutdown", "Serwer bazy danych został wyłączony"},
		{"dbserver.startup", "Uruchom zintegrowany serwer DB..."},
		{"info.clientdriver", "Sterownik klienta: {0}"},
		{"info.host", "Host: {0}"},
		{"info.jdbc", "JDBC: {0}"},
		{"info.serveridentification", "Identyfikacja serwera: {0}"},
		{"info.user", "Użytkownik: {0}"},
		{"module.name", "[DATABASE]"},
		{"update.error.futureversion", "System znalazł przyszłą wersję {0}. Wersja bazy danych obsługiwana przez tę wersję to {1}, ale znaleziona baza danych ma wersję {2}. Nie można kontynuować pracy z tą bazą danych ani jej modyfikować."},
		{"update.error.hsqldb", "FATAL: Nie można zmodyfikować bazy danych z wersji {0} do wersji {1}.\nNależy usunąć wszystkie odpowiednie pliki AS2_DB_*.* w katalogu instalacyjnym.\nSpowoduje to utratę wszystkich danych zdefiniowanych przez użytkownika."},
		{"update.error.mysql", "FATAL: Nie można zmodyfikować bazy danych z wersji {0} do wersji {1}.\nUruchom MySQLWorkbench i usuń odpowiednią bazę danych."},
		{"update.error.oracledb", "FATAL: Nie można zmodyfikować bazy danych z wersji {0} do wersji {1}.\nUruchom Oracle SQL Developer i usuń bazę danych."},
		{"update.error.postgres", "FATAL: Nie można zmodyfikować bazy danych z wersji {0} do wersji {1}.\nUruchom pgAdmin i usuń odpowiednią bazę danych."},
		{"update.notfound", "W przypadku aktualizacji, plik update{0}to{1}.sql i/lub plik Update{0}to{1}.class muszą istnieć w (zasobowym) katalogu {2}."},
		{"update.progress", "Rozpoczęto przyrostową aktualizację bazy danych..."},
		{"update.progress.version.end", "Aktualizacja {1} do wersji {0} gotowa."},
		{"update.progress.version.start", "Rozpoczęcie aktualizacji {1} do wersji {0}..."},
		{"update.successfully", "{0}: Baza danych została pomyślnie zmodyfikowana dla wymaganej wersji."},
		{"update.versioninfo", "Automatyczna aktualizacja bazy danych: Znaleziona wersja bazy danych to {0}, wymagana wersja to {1}."},
		{"upgrade.required", "Należy przeprowadzić aktualizację.\nPrzed uruchomieniem serwera należy wykonać plik as2upgrade.bat lub as2upgrade.sh."},
	};
}
