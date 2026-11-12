//$Header: /as2/de/mendelson/comm/as2/partner/gui/ResourceBundlePartnerConfig_pl.java 2     3/12/25 11:08 Heller $
package de.mendelson.comm.as2.partner.gui;

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
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class ResourceBundlePartnerConfig_pl extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"button.cancel", "Anuluj"},
        {"button.clone", "Kopia"},
        {"button.delete", "Usuń"},
        {"button.globalchange", "Globalny"},
        {"button.new", "Nowość"},
        {"button.ok", "Ok"},
        {"dialog.partner.delete.message", "Zamierzasz usunąć partnera \"{0}\" z konfiguracji partnerów.\nWszystkie dane partnera \"{0}\" zostaną utracone.\n\nCzy naprawdę chcesz usunąć partnera \"{0}\"?"},
        {"dialog.partner.delete.title", "Usuwanie partnera"},
        {"dialog.partner.deletedir.message", "Partner \"{0}\" został usunięty z konfiguracji. Jeśli powiązany katalog\n\"{1}\"\npowinien zostać usunięty z dysku twardego?"},
        {"dialog.partner.deletedir.title", "Usuwanie katalogu wiadomości"},
        {"dialog.partner.renamedir.message", "Nazwa partnera \"{0}\" została zmieniona na \"{1}\". Jeśli odpowiedni katalog\n\"{2}\"\nna dysku twardym należy zmienić nazwę?"},
        {"dialog.partner.renamedir.title", "Zmiana nazwy katalogu wiadomości"},
        {"directory.delete.failure", "Nie można usunąć katalogu \"{0}\": [\"{1}\"]"},
        {"directory.delete.success", "Katalog \"{0}\" został usunięty."},
        {"directory.rename.failure", "Nie można zmienić nazwy katalogu \"{0}\" na \"{1}\"."},
        {"directory.rename.success", "Nazwa katalogu \"{0}\" została zmieniona na \"{1}\"."},
        {"event.partner.added.body", "Dane nowego partnera:\n\n{0}"},
        {"event.partner.added.subject", "Partner {0} został dodany przez użytkownika administracji partnerów"},
        {"event.partner.deleted.body", "Dane usuniętego partnera:\n\n{0}"},
        {"event.partner.deleted.subject", "Partner {0} został usunięty z administracji partnerów przez użytkownika"},
        {"event.partner.modified.body", "Poprzednie dane partnera:\n\n{0}\n\nNowe dane partnera:\n\n{1}"},
        {"event.partner.modified.subject", "Partner {0} został zmodyfikowany przez użytkownika"},
        {"localstation.noprivatekey.message", "Stacja lokalna musi mieć przypisany klucz prywatny."},
        {"localstation.noprivatekey.title", "Brak klucza prywatnego"},
        {"module.locked", "Administracja partnera jest otwarta wyłącznie przez innego klienta, nie można zapisać zmian!"},
        {"nolocalstation.message", "Co najmniej jeden partner musi być zdefiniowany jako stacja lokalna."},
        {"nolocalstation.title", "Brak stacji lokalnej"},
        {"saving", "Zapisz..."},
        {"text.configurationproblem", "<HTML>W konfiguracji partnera występują błędy - popraw je przed zapisaniem.</HTML>"},
        {"title", "Konfiguracja partnera"}, {"button.filter", "Filtr"},
        {"label.filterdisplay", "{0}/{1} partnerów wyświetlonych"},};
}
