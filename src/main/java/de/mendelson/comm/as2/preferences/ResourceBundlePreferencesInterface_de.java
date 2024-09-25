//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferencesInterface_de.java 2     8/11/23 11:07 Heller $
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
 * ResourceBundle to localize gui entries
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class ResourceBundlePreferencesInterface_de extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {          
        {"label.cem", "Zertifikataustausch erlauben (CEM)"},
        {"label.outboundstatusfiles", "Statusdateien für ausgehende Transaktionen schreiben"},        
        {"label.showsecurityoverwrite", "Partnerverwaltung: Sicherheitseinstellungen der lokalen Station überschreiben" },
        {"label.showsecurityoverwrite.help", "<HTML><strong>Sicherheitseinstellungen der lokalen Station überschreiben</strong><br><br>"
            + "Wenn Sie diese Option einschalten, wird in der Partnerverwaltung pro Partner ein zusätzlicher "
            + "Tab angezeigt.<br>Darin können Sie die privaten Schlüssel definieren, die für diesen Partner ein- und ausgehend "
            + "auf jeden Fall verwendet werden - unabhängig von den Einstellungen der jeweiligen lokalen Station.<br>"
            + "Diese Option ermöglicht es Ihnen, für jeden Partner bei gleicher lokaler Station unterschiedliche private "
            + "Schlüssel zu verwenden.<br><br>Dies ist eine Option für die Kompatibilität mit anderen AS2 Produkten - manche Systeme "
            + "haben genau diese Anforderungen, die jedoch eine Konfiguration von Beziehungen von Partnern und "
            + "nicht Einzelpartnern voraussetzen."
            + "</HTML>"},
        {"label.showhttpheader", "Partnerverwaltung: Anzeige der HTTP Header Konfiguration"},
        {"label.showhttpheader.help", "<HTML><strong>Anzeige der HTTP Header Konfiguration</strong><br><br>"
            + "Wenn Sie diese Option einschalten, wird in der Partnerverwaltung pro Partner ein zusätzlicher "
            + "Tab angezeigt, in dem Sie benutzerdefinierte HTTP Header für den Datenversand an diesen Partner "
            + "definieren können."
            + "</HTML>"},
        {"label.showquota", "Partnerverwaltung: Anzeige der Benachrichtigungskonfiguration"},
    };

}
