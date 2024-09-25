//$Header: /as2/de/mendelson/util/mailautoconfig/gui/ResourceBundleMailAutoConfigurationDetection_fr.java 4     2/11/23 15:53 Heller $ 
package de.mendelson.util.mailautoconfig.gui;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.mailautoconfig.MailServiceConfiguration;

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
 * @version $Revision: 4 $
 */
public class ResourceBundleMailAutoConfigurationDetection_fr extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"button.ok", "Utiliser la configuration s�lectionn�e"},
        {"button.cancel", "Annuler"},
        {"title", "Trouver les param�tres du serveur de messagerie"},
        {"label.mailaddress", "Adresse e-mail" },
        {"button.start.detection", "D�couvrir" },
        {"header.service", "Service" },
        {"header.host", "Host" },
        {"header.port", "Port" },
        {"header.security", "S�curit�" },
        {"progress.detection", "D�couvrir les param�tres du serveur de messagerie" },
        {"security." + MailServiceConfiguration.SECURITY_PLAIN, "Aucun" },
        {"security." + MailServiceConfiguration.SECURITY_START_TLS, "StartTLS" },
        {"security." + MailServiceConfiguration.SECURITY_TLS, "TLS" },
        {"label.detectedprovider", "<HTML>Le fournisseur de messagerie d�tect� est <strong>{0}</strong></HTML>"},
        {"detection.failed.title", "�chec de la reconnaissance" },
        {"detection.failed.text", "Le syst�me n''a pas pu trouver les param�tres du serveur de messagerie pour l''adresse de messagerie {0}." },
        {"label.email.hint", "Adresse e-mail valide pour trouver les param�tres du serveur" },
        {"email.invalid.title", "Adresse non valide"},
        {"email.invalid.text", "L''analyse n''a pas �t� effectu�e - l''adresse de messagerie {0} n''est pas valide."},
    };
}
