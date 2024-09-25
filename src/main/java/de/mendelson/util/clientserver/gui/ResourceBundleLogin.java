//$Header: /as2/de/mendelson/util/clientserver/gui/ResourceBundleLogin.java 4     2/11/23 15:53 Heller $
package de.mendelson.util.clientserver.gui;

import de.mendelson.util.MecResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize the mendelson products
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class ResourceBundleLogin extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        //dialog
        {"button.ok", "Ok"},
        {"button.cancel", "Cancel"},
        {"label.user", "User:"},
        {"label.passwd", "Password:"},
        {"title.login", "Login"},};

}
