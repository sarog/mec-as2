//$Header: /as2/de/mendelson/util/security/crmf/JDialogGenerateCRMF.java 9     8/04/26 13:35 Heller $
package de.mendelson.util.security.crmf;

import de.mendelson.util.IStatusBar;
import de.mendelson.util.security.cert.gui.*;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
import de.mendelson.util.clientserver.BaseClient;
import de.mendelson.util.passwordfield.PasswordOverlay;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.util.security.cert.ListCellRendererCertificates;
import de.mendelson.util.security.cert.clientserver.CRMFGenerationRequest;
import de.mendelson.util.security.cert.clientserver.CRMFGenerationResponse;
import de.mendelson.util.uinotification.UINotification;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingWorker;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Dialog to generate a CRMF file for BDEW
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class JDialogGenerateCRMF extends JDialog {

    private static final MendelsonMultiResolutionImage IMAGE_CA
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/crmf/ca.svg",
                    JDialogCertificates.IMAGE_SIZE_DIALOG);

    private static final MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(ResourceBundleCRMF.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
    }
    private final CertificateManager manager;
    private final Logger logger;
    private final BaseClient baseClient;
    private final JFrame frameParent;
    private final IStatusBar statusBar;

    /**
     * Creates new form JDialogPartnerConfig
     *
     * @param manager Manager that handles the certificates
     */
    public JDialogGenerateCRMF(JFrame frameParent, BaseClient baseClient,
            Logger logger, CertificateManager manager, IStatusBar statusBar) throws Exception {
        super(frameParent, true);
        this.frameParent = frameParent;
        this.baseClient = baseClient;
        this.logger = logger;
        this.statusBar = statusBar;
        this.setTitle(rb.getResourceString("title"));
        initComponents();
        PasswordOverlay.addTo(this.jPasswordFieldInitialPassword, rb.getResourceString("password.hint"));
        this.jLabelIcon.setIcon(new ImageIcon(IMAGE_CA.toMinResolution(
                JDialogCertificates.IMAGE_SIZE_DIALOG)));
        this.manager = manager;
        this.jComboBoxKeysEncryption.setRenderer(new ListCellRendererCertificates());
        this.jComboBoxKeysTLS.setRenderer(new ListCellRendererCertificates());
        this.jComboBoxKeysSignature.setRenderer(new ListCellRendererCertificates());
        this.jComboBoxCACertificate.setRenderer(new ListCellRendererCertificates());
        this.populateKeyList();

        this.getRootPane().setDefaultButton(this.jButtonOk);
        this.setButtonState();
    }

    private void populateKeyList() throws Exception {
        this.jComboBoxKeysEncryption.removeAllItems();
        this.jComboBoxKeysSignature.removeAllItems();
        this.jComboBoxKeysTLS.removeAllItems();
        List<KeystoreCertificate> keyList = new ArrayList<KeystoreCertificate>();
        List<KeystoreCertificate> rootList = new ArrayList<KeystoreCertificate>();
        for (KeystoreCertificate key : this.manager.getKeyStoreCertificateList()) {
            if (key.getIsKeyPair()) {
                keyList.add(key);
            }
            if (key.isCACertificate() || key.isRootCertificate()) {
                rootList.add(key);
            }
        }
        if (keyList.isEmpty()) {
            throw new Exception(rb.getResourceString("keystore.contains.nokeys"));
        } else {
            for (KeystoreCertificate key : keyList) {
                this.jComboBoxKeysEncryption.addItem(key);
                this.jComboBoxKeysSignature.addItem(key);
                this.jComboBoxKeysTLS.addItem(key);
            }
            for (KeystoreCertificate cert : rootList) {
                this.jComboBoxCACertificate.addItem(cert);
            }
        }
    }

    /**
     * Sets the state of the "ok" and "cancel" buttons of this GUI
     */
    private void setButtonState() {
    }

    private void performGeneration() {
        String filename = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        filename = Paths.get( "BDEW_" + filename + ".crmf").toAbsolutePath().toString();        
        final String finalFilename = filename;
        final String uniqueId = this.getClass().getName() + ".performGeneration." + System.currentTimeMillis();
        if (statusBar != null) {
            statusBar.startProgressIndeterminate(rb.getResourceString("title"), uniqueId);
        }
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() {
                try {
                    KeystoreCertificate certEncryption = (KeystoreCertificate) jComboBoxKeysEncryption.getSelectedItem();
                    KeystoreCertificate certSignature = (KeystoreCertificate) jComboBoxKeysSignature.getSelectedItem();
                    KeystoreCertificate certTLS = (KeystoreCertificate) jComboBoxKeysTLS.getSelectedItem();
                    KeystoreCertificate certCARoot = (KeystoreCertificate) jComboBoxCACertificate.getSelectedItem();
                    CRMFGenerationRequest request = new CRMFGenerationRequest(
                            manager.getStorageUsage(),
                            certEncryption.getFingerPrintSHA1(),
                            certSignature.getFingerPrintSHA1(),
                            certTLS.getFingerPrintSHA1(),
                            certCARoot.getFingerPrintSHA1(),
                            jRadioButtonInitial.isSelected(),
                            jPasswordFieldInitialPassword.getPassword()
                    );
                    CRMFGenerationResponse response
                            = (CRMFGenerationResponse) baseClient.sendSync(request);
                    if (response != null && response.getException() != null) {
                        throw response.getException();
                    }
                    String generationBase64 = response.getCrmfBase64();
                    Files.writeString(Paths.get(finalFilename), generationBase64);
                    UINotification.instance().addNotification(
                            IMAGE_CA, UINotification.Type.SUCCESS,
                            rb.getResourceString("success.title"),
                            rb.getResourceString("success.body", finalFilename));
                } catch (Throwable e) {
                    UINotification.instance().addNotification(e);
                }
                return null;
            }

            @Override
            protected void done() {
                if (statusBar != null) {
                    statusBar.stopProgressIfExists(uniqueId);
                }
            }
        };
        worker.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroupRequestType = new javax.swing.ButtonGroup();
        jPanelEdit = new javax.swing.JPanel();
        jLabelIcon = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxKeysEncryption = new javax.swing.JComboBox<>();
        jLabelAliasTLS = new javax.swing.JLabel();
        jPanelSpace2 = new javax.swing.JPanel();
        jPanelSpace1 = new javax.swing.JPanel();
        jPanelUIHelpLabelURL = new de.mendelson.util.balloontip.JPanelUIHelpLabel();
        jPanelSpace3 = new javax.swing.JPanel();
        jPanelSpace4 = new javax.swing.JPanel();
        jLabelAliasEncryption = new javax.swing.JLabel();
        jComboBoxKeysTLS = new javax.swing.JComboBox<>();
        jLabelAliasSignature = new javax.swing.JLabel();
        jComboBoxKeysSignature = new javax.swing.JComboBox<>();
        jRadioButtonInitial = new javax.swing.JRadioButton();
        jRadioButtonUpdate = new javax.swing.JRadioButton();
        jPasswordFieldInitialPassword = new javax.swing.JPasswordField();
        jPanelUIHelpLabelInitial = new de.mendelson.util.balloontip.JPanelUIHelpLabel();
        jPanelUIHelpLabelUpdate = new de.mendelson.util.balloontip.JPanelUIHelpLabel();
        jPanelSpace5 = new javax.swing.JPanel();
        jComboBoxCACertificate = new javax.swing.JComboBox<>();
        jPanelButtons = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelEdit.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelEdit.setLayout(new java.awt.GridBagLayout());

        jLabelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/missing_image32x32.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 10);
        jPanelEdit.add(jLabelIcon, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanelEdit.add(jPanel3, gridBagConstraints);

        jComboBoxKeysEncryption.setMinimumSize(new java.awt.Dimension(300, 24));
        jComboBoxKeysEncryption.setPreferredSize(new java.awt.Dimension(300, 24));
        jComboBoxKeysEncryption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxKeysEncryptionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 1, 10);
        jPanelEdit.add(jComboBoxKeysEncryption, gridBagConstraints);

        jLabelAliasTLS.setText(this.rb.getResourceString( "label.key.tls" ));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelEdit.add(jLabelAliasTLS, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanelEdit.add(jPanelSpace2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanelEdit.add(jPanelSpace1, gridBagConstraints);

        jPanelUIHelpLabelURL.setToolTipText(this.rb.getResourceString( "label.root.ca.help"));
        jPanelUIHelpLabelURL.setText(this.rb.getResourceString( "label.root.ca"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanelEdit.add(jPanelUIHelpLabelURL, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanelEdit.add(jPanelSpace3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanelEdit.add(jPanelSpace4, gridBagConstraints);

        jLabelAliasEncryption.setText(this.rb.getResourceString( "label.key.encryption" ));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelEdit.add(jLabelAliasEncryption, gridBagConstraints);

        jComboBoxKeysTLS.setMinimumSize(new java.awt.Dimension(300, 24));
        jComboBoxKeysTLS.setPreferredSize(new java.awt.Dimension(300, 24));
        jComboBoxKeysTLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxKeysTLSActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 1, 10);
        jPanelEdit.add(jComboBoxKeysTLS, gridBagConstraints);

        jLabelAliasSignature.setText(this.rb.getResourceString( "label.key.signature" ));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelEdit.add(jLabelAliasSignature, gridBagConstraints);

        jComboBoxKeysSignature.setMinimumSize(new java.awt.Dimension(300, 24));
        jComboBoxKeysSignature.setPreferredSize(new java.awt.Dimension(300, 24));
        jComboBoxKeysSignature.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxKeysSignatureActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 1, 10);
        jPanelEdit.add(jComboBoxKeysSignature, gridBagConstraints);

        buttonGroupRequestType.add(jRadioButtonInitial);
        jRadioButtonInitial.setSelected(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jPanelEdit.add(jRadioButtonInitial, gridBagConstraints);

        buttonGroupRequestType.add(jRadioButtonUpdate);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jPanelEdit.add(jRadioButtonUpdate, gridBagConstraints);

        jPasswordFieldInitialPassword.setText("initial_pass");
        jPasswordFieldInitialPassword.setMinimumSize(new java.awt.Dimension(150, 22));
        jPasswordFieldInitialPassword.setPreferredSize(new java.awt.Dimension(150, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jPasswordFieldInitialPassword, gridBagConstraints);

        jPanelUIHelpLabelInitial.setToolTipText(this.rb.getResourceString( "label.initial.help"));
        jPanelUIHelpLabelInitial.setText(this.rb.getResourceString( "label.initial"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelEdit.add(jPanelUIHelpLabelInitial, gridBagConstraints);

        jPanelUIHelpLabelUpdate.setToolTipText(this.rb.getResourceString( "label.update.help"));
        jPanelUIHelpLabelUpdate.setText(this.rb.getResourceString( "label.update"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanelEdit.add(jPanelUIHelpLabelUpdate, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanelEdit.add(jPanelSpace5, gridBagConstraints);

        jComboBoxCACertificate.setMinimumSize(new java.awt.Dimension(300, 24));
        jComboBoxCACertificate.setPreferredSize(new java.awt.Dimension(300, 24));
        jComboBoxCACertificate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCACertificateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 1, 10);
        jPanelEdit.add(jComboBoxCACertificate, gridBagConstraints);

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

        jButtonCancel.setText(this.rb.getResourceString( "button.cancel" ));
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelButtons.add(jButtonCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanelButtons, gridBagConstraints);

        setSize(new java.awt.Dimension(690, 466));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        this.setVisible(false);
        this.performGeneration();
        this.dispose();
    }//GEN-LAST:event_jButtonOkActionPerformed

    private void jComboBoxKeysEncryptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxKeysEncryptionActionPerformed
        this.setButtonState();
    }//GEN-LAST:event_jComboBoxKeysEncryptionActionPerformed

    private void jComboBoxKeysTLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxKeysTLSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxKeysTLSActionPerformed

    private void jComboBoxKeysSignatureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxKeysSignatureActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxKeysSignatureActionPerformed

    private void jComboBoxCACertificateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCACertificateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCACertificateActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupRequestType;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JComboBox<KeystoreCertificate> jComboBoxCACertificate;
    private javax.swing.JComboBox<KeystoreCertificate> jComboBoxKeysEncryption;
    private javax.swing.JComboBox<KeystoreCertificate> jComboBoxKeysSignature;
    private javax.swing.JComboBox<KeystoreCertificate> jComboBoxKeysTLS;
    private javax.swing.JLabel jLabelAliasEncryption;
    private javax.swing.JLabel jLabelAliasSignature;
    private javax.swing.JLabel jLabelAliasTLS;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelEdit;
    private javax.swing.JPanel jPanelSpace1;
    private javax.swing.JPanel jPanelSpace2;
    private javax.swing.JPanel jPanelSpace3;
    private javax.swing.JPanel jPanelSpace4;
    private javax.swing.JPanel jPanelSpace5;
    private de.mendelson.util.balloontip.JPanelUIHelpLabel jPanelUIHelpLabelInitial;
    private de.mendelson.util.balloontip.JPanelUIHelpLabel jPanelUIHelpLabelURL;
    private de.mendelson.util.balloontip.JPanelUIHelpLabel jPanelUIHelpLabelUpdate;
    private javax.swing.JPasswordField jPasswordFieldInitialPassword;
    private javax.swing.JRadioButton jRadioButtonInitial;
    private javax.swing.JRadioButton jRadioButtonUpdate;
    // End of variables declaration//GEN-END:variables
}
