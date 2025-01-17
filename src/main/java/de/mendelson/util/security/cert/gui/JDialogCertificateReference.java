//$Header: /as2/de/mendelson/util/security/cert/gui/JDialogCertificateReference.java 5     2/11/23 14:03 Heller $
package de.mendelson.util.security.cert.gui;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
import de.mendelson.util.security.cert.CertificateInUseInfo;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.util.tables.JTableColumnResizer;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.table.TableColumn;

/**
 * Dialog to configure a single partner
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class JDialogCertificateReference extends JDialog {

    /**
     * ResourceBundle to localize the GUI
     */
    private MecResourceBundle rb = null;
    public final static MendelsonMultiResolutionImage IMAGE_REFERENCE
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/cert/gui/reference.svg", 16, 64);
    
    /**
     */
    public JDialogCertificateReference(JFrame parent,
            List<CertificateInUseInfo.SingleCertificateInUseInfo> infoList,
            KeystoreCertificate certificate) {
        super(parent, true);
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleCertificateReference.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
        this.setTitle(this.rb.getResourceString("title"));
        initComponents();
        this.setMultiresolutionIcons();        
        this.jTable.setModel(new TableModelCertificateReference());        
        this.jTable.getTableHeader().setReorderingAllowed(false);
        TableColumn column = this.jTable.getColumnModel().getColumn(0);
        column.setResizable(false);
        column.setMaxWidth(TableModelCertificateReference.ROW_HEIGHT);
        this.jTable.setRowHeight(TableModelCertificateReference.ROW_HEIGHT);        
        ((TableModelCertificateReference)this.jTable.getModel()).passNewData(infoList);        
        this.jTable.setTableHeader(null);
        JTableColumnResizer.adjustColumnWidthByContent(this.jTable);
        if( certificate.getIsKeyPair()){
            this.jLabelInfo.setText( this.rb.getResourceString("label.info.key", certificate.getAlias()));
        }else{
            this.jLabelInfo.setText( this.rb.getResourceString("label.info.certificate", certificate.getAlias()));
        }
        this.jLabelNotInUse.setVisible(infoList.isEmpty());
        this.jPanelSpaceNotInUse.setVisible(infoList.isEmpty());
        this.jScrollPaneTable.setVisible( !infoList.isEmpty());
        this.jLabelInfo.setVisible( !infoList.isEmpty());
        if( certificate.getIsKeyPair()){
            this.jLabelNotInUse.setText(this.rb.getResourceString("label.notinuse.key", certificate.getAlias()));            
        }else{
            this.jLabelNotInUse.setText(this.rb.getResourceString("label.notinuse.certificate", certificate.getAlias()));
        }
        this.getRootPane().setDefaultButton(this.jButtonOk);
    }

    private void setMultiresolutionIcons() {
        this.jLabelIcon.setIcon(new ImageIcon(IMAGE_REFERENCE.toMinResolution(32)));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelEdit = new javax.swing.JPanel();
        jLabelIcon = new javax.swing.JLabel();
        jScrollPaneTable = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jLabelInfo = new javax.swing.JLabel();
        jLabelNotInUse = new javax.swing.JLabel();
        jPanelSpaceNotInUse = new javax.swing.JPanel();
        jPanelButtons = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelEdit.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelEdit.setLayout(new java.awt.GridBagLayout());

        jLabelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/missing_image32x32.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelEdit.add(jLabelIcon, gridBagConstraints);

        jTable.setShowHorizontalLines(false);
        jTable.setShowVerticalLines(false);
        jScrollPaneTable.setViewportView(jTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(30, 10, 10, 10);
        jPanelEdit.add(jScrollPaneTable, gridBagConstraints);

        jLabelInfo.setText("<INFO>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelEdit.add(jLabelInfo, gridBagConstraints);

        jLabelNotInUse.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        jLabelNotInUse.setText("<NOT IN USE>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelEdit.add(jLabelNotInUse, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanelEdit.add(jPanelSpaceNotInUse, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanelEdit, gridBagConstraints);

        jPanelButtons.setLayout(new java.awt.GridBagLayout());

        jButtonOk.setText(this.rb.getResourceString( "button.ok" ));
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelButtons.add(jButtonOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanelButtons, gridBagConstraints);

        setSize(new java.awt.Dimension(555, 398));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonOkActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JLabel jLabelNotInUse;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelEdit;
    private javax.swing.JPanel jPanelSpaceNotInUse;
    private javax.swing.JScrollPane jScrollPaneTable;
    private javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables

    
}
