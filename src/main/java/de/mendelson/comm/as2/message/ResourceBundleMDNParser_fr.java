//$Header: /mec_as2/de/mendelson/comm/as2/message/ResourceBundleMDNParser_fr.java 4     3-03-16 1:47p Heller $
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
 * @author E.Pailleau
 * @version $Revision: 4 $
 */
public class ResourceBundleMDNParser_fr extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"invalid.mdn.nocontenttype", "Un MDN entrant est invalide: Aucun type de contenu trouv�" },
        {"structure.failure.mdn", "Un MDN entrant a �t� analys� et il y a un �chec de structure dans le MDN (\"{0}\"). Le MDN est inadmissible et ne pourrait pas �tre trait�, le statut du message AS2/de transaction r�f�renc�s ne sera pas chang�." },
    };
    
}
