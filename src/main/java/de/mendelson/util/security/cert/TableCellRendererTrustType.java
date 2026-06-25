//$Header: /as2/de/mendelson/util/security/cert/TableCellRendererTrustType.java 1     15/04/25 12:31 Heller $
package de.mendelson.util.security.cert;

import de.mendelson.util.ColorUtil;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Renderer to render the Trust type of a certificate
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class TableCellRendererTrustType extends DefaultTableCellRenderer implements TableCellRenderer {

    private static final  Color COLOR_PALE_GREEN 
            = ColorUtil.blend(UIManager.getColor("Table.background"), Color.GREEN, 0.15f);
    private static final  Color COLOR_PALE_RED 
            =  ColorUtil.blend(UIManager.getColor("Table.background"), Color.RED, 0.15f);

    public TableCellRendererTrustType() {
    }

    /**
     *
     * Returns the default table cell renderer.
     *
     * @param table the <code>JTable</code>
     * @param value the value to assign to the cell at
     * <code>[row, column]</code>
     * @param isSelected true if cell is selected
     * @param hasFocus true if cell has focus
     * @param row the row of the cell to render
     * @param column the column of the cell to render
     * @return the default table cell renderer
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            this.setBackground(table.getSelectionBackground());
            this.setForeground(table.getSelectionForeground());

        } else {
            this.setBackground(table.getBackground());
            this.setForeground(table.getForeground());
        }
        this.setEnabled(table.isEnabled());
        this.setFont(table.getFont());
        this.setText(value.toString());
        if (value instanceof TrustType) {
            TrustType trustType = (TrustType) value;
            if (trustType.getTrustType() == TrustType.TRUST_TYPE_UNTRUSTED 
                    || trustType.getTrustType() == TrustType.TRUST_TYPE_SELFSIGNED) {
                Color backgroundColor = COLOR_PALE_RED;
                if (isSelected) {
                    this.setBackground(backgroundColor.darker());
                } else {
                    this.setBackground(backgroundColor);
                }
            }else{
                Color backgroundColor = COLOR_PALE_GREEN;
                if (isSelected) {
                    this.setBackground(backgroundColor.darker());
                } else {
                    this.setBackground(backgroundColor);
                }
            }
        }
        return (this);        
    }

}
