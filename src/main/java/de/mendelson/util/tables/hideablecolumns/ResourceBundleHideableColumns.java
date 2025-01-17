//$Header: /as2/de/mendelson/util/tables/hideablecolumns/ResourceBundleHideableColumns.java 5     2/11/23 15:53 Heller $
package de.mendelson.util.tables.hideablecolumns;

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
 * @version $Revision: 5 $
 */
public class ResourceBundleHideableColumns extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"header.column", "Column"},
        {"header.visible", "Visible"},
        {"title", "Column configuration"},
        {"label.info", "Please select the visible columns below."},
        {"header.icon", "[Status icon] - always visible"},
        {"label.ok", "Ok"},};

}
