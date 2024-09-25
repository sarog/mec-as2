//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleCertificateReference_fr.java 1     7/04/22 10:33 Heller $
package de.mendelson.util.security.cert.gui;
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
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class ResourceBundleCertificateReference_fr extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {        
        {"title", "Utilisation du certificat" },   
        {"button.ok", "Ok" },
        {"label.info.certificate", "<HTML>Le certificat avec l''alias <strong>{0}</strong> est utilis� par les partenaires suivants</HTML>" },
        {"label.info.key", "<HTML>La cl� priv�e avec l''alias <strong>{0}</strong> est utilis�e par les partenaires suivants</HTML>" },
        {"label.notinuse.key", "<HTML>La cl� priv�e avec l''alias <strong>{0}</strong> n''est utilis�e par aucun partenaire dans la configuration.</HTML>" },
        {"label.notinuse.certificate", "<HTML>Le certificat avec l''alias <strong>{0}</strong> n''est utilis� par aucun partenaire dans la configuration.</HTML>" },
    };
    
}