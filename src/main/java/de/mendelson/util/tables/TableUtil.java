//$Header: /as2/de/mendelson/util/tables/TableUtil.java 1     11/04/25 9:10 Heller $
package de.mendelson.util.tables;

import java.awt.Rectangle;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Contains static methods for the JTables of the mendelson projects
 */
public class TableUtil {

    private TableUtil() {
    }
    
    /**Scrolls a row to visibility in a given JTable
     * 
     * @param table
     * @param row 
     */
    public static void scrollTableRowToVisible(JTable table, int row) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Rectangle visible = table.getVisibleRect();
                    Rectangle cell = table.getCellRect(row, 0, true);
                    if (cell.y < visible.y) {
                        visible.y = cell.y;
                        table.scrollRectToVisible(visible);
                    } else if (cell.y + cell.height > visible.y + visible.height) {
                        visible.y = cell.y + cell.height - visible.height;
                        table.scrollRectToVisible(visible);
                    }
                } catch (Throwable e) {
                }
            }
        };
        SwingUtilities.invokeLater(runnable);
    }
    
}
