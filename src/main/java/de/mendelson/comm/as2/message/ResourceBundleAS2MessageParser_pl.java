//$Header: /as2/de/mendelson/comm/as2/message/ResourceBundleAS2MessageParser_pl.java 1     24/09/25 10:35 Heller $
package de.mendelson.comm.as2.message;

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
public class ResourceBundleAS2MessageParser_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"contentmic.failure", "Kod integralności wiadomości (MIC) nie pasuje do wysłanej wiadomości AS2 (oczekiwany: {0}, odebrany: {1})."},
		{"contentmic.match", "Kod integralności wiadomości (MIC) jest zgodny z wysłaną wiadomością AS2."},
		{"data.compressed.expanded", "Skompresowane dane użytkownika przychodzącego komunikatu AS2 zostały rozszerzone z {0} do {1}."},
		{"data.not.compressed", "Odebrane dane AS2 są nieskompresowane."},
		{"data.unable.to.process.content.transfer.encoding", "Odebrano dane, których nie można przetworzyć, ponieważ zawierają błędy. Kodowanie transferu zawartości \"{0}\" jest nieznane."},
		{"decryption.done.alias", "Dane przychodzącej wiadomości AS2 zostały odszyfrowane przy użyciu klucza \"{0}\" stacji lokalnej \"{3}\", algorytm szyfrowania to \"{1}\", algorytm szyfrowania klucza to \"{2}\"."},
		{"decryption.infoassigned", "Do odszyfrowania przychodzącej wiadomości AS2 użyto klucza o następujących parametrach (alias \"{0}\"):\n{1}"},
		{"decryption.inforequired", "Do odszyfrowania przychodzącej wiadomości AS2 wymagany jest klucz o następujących parametrach:\n{0}"},
		{"filename.extraction.error", "Wyodrębnienie oryginalnej nazwy pliku przychodzącej wiadomości AS2 nie jest możliwe: \"{0}\" jest ignorowane."},
		{"found.attachments", "W wiadomości AS2 znaleziono załączniki z danymi użytkownika {0}."},
		{"found.cem", "Otrzymany komunikat jest żądaniem wymiany certyfikatu (CEM)."},
		{"inbound.connection.raw", "Połączenie przychodzące z [{0}] do portu {1}"},
		{"inbound.connection.syncmdn", "Synchroniczny MDN został odebrany na kanale zwrotnym połączenia wychodzącego."},
		{"inbound.connection.tls", "Przychodzące połączenie TLS z [{0}] do portu {1} [{2}, {3}]."},
		{"inbound.connection.transferinfo", "W {1} [{2}] otrzymano {0}\"."},
		{"invalid.original.filename.body", "System wyodrębnił nieprawidłową oryginalną nazwę pliku w transakcji {0} z {1} do {2}.\nZnaleziona nazwa pliku \"{3}\" została zastąpiona nazwą \"{4}\" i przetwarzanie było kontynuowane z tą nową nazwą pliku. Może to mieć wpływ na przepływ przetwarzania, ponieważ nazwy plików czasami zawierają metadane."},
		{"invalid.original.filename.log", "W transakcji wykryto nieprawidłową oryginalną nazwę pliku. \"{0}\" zostaje zastąpione przez \"{1}\" i przetwarzanie jest kontynuowane."},
		{"invalid.original.filename.title", "Nieprawidłowa oryginalna nazwa pliku wykryta w transakcji"},
		{"mdn.answerto", "Przychodzące potwierdzenie odbioru (MDN) z numerem komunikatu \"{0}\" jest odpowiedzią na wychodzący komunikat AS2 \"{1}\"."},
		{"mdn.details", "Szczegóły potwierdzenia odbioru (MDN) otrzymanego od {0}: \"{1}\""},
		{"mdn.incoming", "Odebrana transmisja jest potwierdzeniem odbioru (MDN)."},
		{"mdn.incoming.ha", "Transmisja przychodząca to potwierdzenie odbioru (MDN), przetwarzane przez [{0}]."},
		{"mdn.incoming.relationship", "Transmisja przychodząca to potwierdzenie odbioru (MDN) [{0}]."},
		{"mdn.incoming.relationship.ha", "Transmisja przychodząca to potwierdzenie odbioru (MDN) [{0}], przetworzone przez [{1}]."},
		{"mdn.notsigned", "Otrzymane potwierdzenie odbioru (MDN) nie jest podpisane cyfrowo."},
		{"mdn.signature.failure", "Weryfikacja podpisu cyfrowego otrzymanego MDN nie powiodła się - {0}"},
		{"mdn.signature.ok", "Podpis cyfrowy otrzymanego MDN został pomyślnie zweryfikowany."},
		{"mdn.signature.using.alias", "Użyj certyfikatu \"{0}\" zdalnego partnera \"{1}\", aby zweryfikować podpis cyfrowy przychodzącego MDN."},
		{"mdn.signed", "Potwierdzenie odbioru (MDN) jest podpisane cyfrowo ({0})."},
		{"mdn.signed.error", "Przychodzące potwierdzenie odbioru (MDN) jest podpisane cyfrowo w przeciwieństwie do konfiguracji partnera \"{0}\"."},
		{"mdn.state", "Status otrzymanego potwierdzenia odbioru (MDN) to [{0}]."},
		{"mdn.unexpected.messageid", "Otrzymane potwierdzenie odbioru (MDN) odnosi się do komunikatu AS2 o numerze referencyjnym \"{0}\", który nie oczekuje MDN."},
		{"mdn.unsigned.error", "Przychodzące potwierdzenie odbioru (MDN) NIE jest podpisane cyfrowo, w przeciwieństwie do konfiguracji partnera \"{0}\"."},
		{"message.signature.failure", "Weryfikacja podpisu cyfrowego przychodzącej wiadomości AS2 nie powiodła się - {0}"},
		{"message.signature.ok", "Podpis cyfrowy przychodzącej wiadomości AS2 został pomyślnie zweryfikowany."},
		{"message.signature.using.alias", "Użyj certyfikatu \"{0}\" zdalnego partnera \"{1}\", aby zweryfikować podpis cyfrowy przychodzącej wiadomości AS2."},
		{"msg.already.processed", "Wiadomość o numerze [{0}] została już przetworzona"},
		{"msg.encrypted", "Przychodząca wiadomość AS2 jest szyfrowana."},
		{"msg.incoming", "Transmisja przychodząca to komunikat AS2 [{0}], rozmiar nieprzetworzonych danych: {1}."},
		{"msg.incoming.ha", "Transmisja przychodząca to komunikat AS2 [{0}], rozmiar nieprzetworzonych danych: {1}, przetworzony przez [{2}]."},
		{"msg.incoming.identproblem", "Transmisja przychodząca to komunikat AS2. Nie została ona przetworzona, ponieważ wystąpił problem z identyfikacją partnera."},
		{"msg.notencrypted", "Przychodząca wiadomość AS2 nie jest szyfrowana."},
		{"msg.notsigned", "Przychodząca wiadomość AS2 nie jest podpisana cyfrowo."},
		{"msg.signed", "Przychodząca wiadomość AS2 jest podpisana cyfrowo."},
		{"original.filename.found", "Oryginalna nazwa pliku została przesłana przez nadawcę jako \"{0}\"."},
		{"original.filename.undefined", "Oryginalna nazwa pliku nie została przesłana."},
		{"signature.analyzed.digest", "Nadawca użył algorytmu \"{0}\" do złożenia podpisu cyfrowego."},
		{"signature.analyzed.digest.failed", "System nie mógł znaleźć algorytmu podpisu przychodzącej wiadomości AS2."},
	};
}
