//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferencesInterface_pl.java 1     24/09/25 10:36 Heller $
package de.mendelson.comm.as2.preferences;

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
public class ResourceBundlePreferencesInterface_pl extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"autoimport.tls", "Certyfikaty TLS: Automatyczny import w przypadku zmiany"},
		{"autoimport.tls.help", "<HTML><strong>Certyfikaty TLS: Automatyczny import po zmianie</strong><br><br>"
			+"Jeśli połączenie partnerskie jest realizowane przez HTTPS (TLS, adres URL zaczyna się od \"https\"), można regularnie sprawdzać, czy certyfikat TLS po stronie partnera uległ zmianie. Jeśli został on zmieniony i nie znajduje się jeszcze w systemie, zostanie automatycznie zaimportowany wraz z całym łańcuchem uwierzytelniania.<br>"
			+"System sprawdza certyfikaty partnerów co 15 minut. Dlatego może upłynąć trochę czasu, zanim zmiana certyfikatu TLS partnera zostanie rozpoznana.<br><br>"
			+"Proces ten można również przeprowadzić ręcznie, wykonując test połączenia z partnerem, a następnie importując brakujące certyfikaty TLS.<br><br>"
			+"Należy pamiętać, że jest to problematyczne ustawienie na poziomie bezpieczeństwa, ponieważ automatycznie ufasz znalezionemu certyfikatowi - bez pytania.</HTML>"},
		{"label.cem", "Zezwalaj na wymianę certyfikatów (CEM)"},
		{"label.checkrevocationlists", "Certyfikaty: Sprawdź listy odwołań"},
		{"label.checkrevocationlists.help", "<HTML><strong>Certyfikaty: Sprawdź listy unieważnień</strong><br><br>"
			+"Lista unieważnień to lista certyfikatów, które zostały uznane za nieważne z powodu różnych obaw lub problemów związanych z bezpieczeństwem. Problemy te mogą dotyczyć na przykład kompromitacji klucza prywatnego, utraty certyfikatu lub podejrzenia o nieuczciwą działalność. Listy unieważnień są zarządzane przez urzędy certyfikacji lub inne zaufane podmioty, które są upoważnione do wydawania certyfikatów. Sprawdzanie list unieważnień jest ważne, aby upewnić się, że certyfikaty używane w połączeniu lub do operacji kryptograficznej są ważne i godne zaufania. Certyfikat, który znajduje się na liście unieważnień, nie powinien być już używany do operacji kryptograficznych, ponieważ jest potencjalnie niezabezpieczony i może stanowić zagrożenie dla integralności komunikacji.<br><br>"
			+"Za pomocą tego ustawienia można określić, czy system sprawdza również listy odwołań podczas sprawdzania konfiguracji.</HTML>"},
		{"label.outboundstatusfiles", "Pliki statusu dla transakcji wychodzących"},
		{"label.outboundstatusfiles.help", "<HTML><strong>Pliki statusu dla transakcji wychodzących</strong><br><br>"
			+"Po włączeniu tej opcji plik statusu jest zapisywany w katalogu \"outboundstatus\" dla każdej transakcji wychodzącej.<br>"
			+"Plik ten jest wykorzystywany do celów integracji i zawiera informacje na temat danej transakcji. Obejmuje to na przykład status transakcji, numer wiadomości, identyfikator nadawcy i odbiorcy.<br>"
			+"Nazwa pliku statusu zawiera numer wiadomości i kończy się na \".sent.state\". Po wysłaniu danych można przeanalizować ten plik i sprawdzić status transakcji.</HTML>"},
		{"label.showhttpheader", "Zarządzanie partnerami: Wyświetlanie konfiguracji nagłówka HTTP"},
		{"label.showhttpheader.help", "<HTML><strong>Wyświetlanie konfiguracji nagłówka HTTP</strong><br><br>"
			+"Włączenie tej opcji spowoduje wyświetlenie dodatkowej zakładki w administracji partnera dla każdego partnera, w której można zdefiniować zdefiniowane przez użytkownika nagłówki HTTP do wysyłania danych do tego partnera.</HTML>"},
		{"label.showquota", "Zarządzanie partnerami: wyświetlanie konfiguracji powiadomień (limit)"},
		{"label.showsecurityoverwrite", "Zarządzanie partnerami: nadpisywanie ustawień zabezpieczeń stacji lokalnej"},
		{"label.showsecurityoverwrite.help", "<HTML><strong>Nadpisanie ustawień zabezpieczeń stacji lokalnej</strong><br><br>"
			+"Włączenie tej opcji spowoduje wyświetlenie dodatkowej karty dla każdego partnera w zarządzaniu partnerami.<br>"
			+"Pozwala to zdefiniować klucze prywatne, które są używane dla tego partnera w ruchu przychodzącym i wychodzącym w każdym przypadku - niezależnie od ustawień odpowiedniej stacji lokalnej.<br>"
			+"Ta opcja umożliwia korzystanie z różnych kluczy prywatnych dla każdego partnera w tej samej stacji lokalnej.<br><br>"
			+"Jest to opcja zapewniająca kompatybilność z innymi produktami AS2 - niektóre systemy mają dokładnie takie wymagania, ale wymagają konfiguracji relacji partnerów, a nie poszczególnych partnerów.</HTML>"},
	};
}
