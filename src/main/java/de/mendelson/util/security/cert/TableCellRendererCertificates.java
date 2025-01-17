//$Header: /as2/de/mendelson/util/security/cert/TableCellRendererCertificates.java 9     14/12/23 15:42 Heller $
package de.mendelson.util.security.cert;

import java.awt.Component;
import java.awt.Color;
import java.awt.Rectangle;
import java.security.PrivateKey;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Renders a certificate in a JTable column
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class TableCellRendererCertificates extends DefaultTableCellRenderer implements TableCellRenderer {

    public static final int ROW_HEIGHT = TableModelCertificates.ROW_HEIGHT;
    protected static final int IMAGE_HEIGHT = TableModelCertificates.IMAGE_HEIGHT;
    
    /**
     * Stores the certificates
     */
    private final CertificateManager manager;
    public static final int TYPE_FINGERPRINT_SHA1 = 1;
    public static final int TYPE_ALIAS = 2;
    public static final int TYPE_CERTIFICATE = 3;
    public static final int TYPE_ISSUER_SERIAL = 4;
    /**
     * Sets the value that is expected in the column
     */
    private final int type;

    /**
     * Creates a default table cell renderer.
     */
    public TableCellRendererCertificates(CertificateManager manager, int type) {
        super();
        this.type = type;
        this.manager = manager;
        this.setOpaque(true);
    }
    // implements javax.swing.table.TableCellRenderer

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
        String alias = null;
        if (value instanceof String) {
            try {
                if (this.type == TYPE_FINGERPRINT_SHA1) {
                    alias = this.manager.getAliasByFingerprint((String) value);
                } else if (this.type == TYPE_ALIAS) {
                    alias = (String) value;
                }
            } catch (Exception e) {
                alias = "[" + e.getClass().getSimpleName() + "]: " + e.getMessage();
            }
        } else if (value instanceof String[]) {
            if (this.type == TYPE_ISSUER_SERIAL) {
                String[] issuerSerial = (String[]) value;
                if (issuerSerial.length == 2) {
                    KeystoreCertificate certificate = this.manager.getKeystoreCertificateByIssuerDNAndSerial(issuerSerial[0], issuerSerial[1]);
                    if (certificate != null) {
                        alias = certificate.getAlias();
                    }
                }
            }
        } else if (value instanceof KeystoreCertificate) {
            alias = ((KeystoreCertificate) value).getAlias();
        }
        try {
            PrivateKey key = this.manager.getPrivateKey(alias);
            this.setIcon(new ImageIcon(TableModelCertificates.IMAGE_KEY_MULTIRESOLUTION.toMinResolution(IMAGE_HEIGHT)));
        } catch (Exception e) {
            KeystoreCertificate cert = this.manager.getKeystoreCertificate(alias);
            if (cert != null) {
                if (cert.isRootCertificate()) {
                    this.setIcon(TableModelCertificates.ICON_CERTIFICATE_ROOT);
                } else {
                    this.setIcon(TableModelCertificates.ICON_CERTIFICATE);
                }
            } else {
                this.setIcon(TableModelCertificates.ICON_CERTIFICATE_MISSING);
            }
        }
        this.setText(alias);
        return (this);
    }


    /*
     * The following methods are overridden as a performance measure to
     * to prune code-paths are often called in the case of renders
     * but which we know are unnecessary.  Great care should be taken
     * when writing your own renderer to weigh the benefits and
     * drawbacks of overriding methods like these.
     */
    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public boolean isOpaque() {
        Color back = getBackground();
        Component p = getParent();
        if (p != null) {
            p = p.getParent();
        }
        // p should now be the JTable.
        boolean colorMatch = (back != null) && (p != null)
                && back.equals(p.getBackground())
                && p.isOpaque();
        return !colorMatch && super.isOpaque();
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void validate() {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void revalidate() {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void repaint(long tm, int x, int y, int width, int height) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void repaint(Rectangle r) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        // Strings get interned...
        if (propertyName.equals("text")) {
            super.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
    }
}
