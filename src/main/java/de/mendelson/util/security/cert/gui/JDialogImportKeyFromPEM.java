//$Header: /as2/de/mendelson/util/security/cert/gui/JDialogImportKeyFromPEM.java 3     8/04/26 13:35 Heller $
package de.mendelson.util.security.cert.gui;

import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.MecFileChooser;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.TextOverlay;
import de.mendelson.util.passwordfield.PasswordOverlay;
import de.mendelson.util.security.BouncyCastleProviderSingleton;
import de.mendelson.util.security.memkeystore.InMemoryKeyStore;
import de.mendelson.util.security.memkeystore.InMemoryKeyStoreUtil;
import de.mendelson.util.security.memkeystore.KeyAndCert;
import de.mendelson.util.uinotification.UINotification;
import java.io.FileReader;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Dialog to import a key from a PEM file
 *
 * @author S.Heller
 * @version $Revision: 3 $
 */
public class JDialogImportKeyFromPEM extends JDialog {

    private static final  MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleImportKeyPEM.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
    }
    private final CertificateManager manager;
    private String newAlias = null;
    private final Logger logger;

    /**
     * Creates new form JDialogPartnerConfig
     *
     * @param manager Manager that handles the certificates
     */
    public JDialogImportKeyFromPEM(JFrame parent, Logger logger, CertificateManager manager) {
        super(parent, true);
        this.setTitle(rb.getResourceString("title"));
        initComponents();
        PasswordOverlay.addTo(this.jPasswordFieldPassphrase,
                rb.getResourceString("label.keypass.hint"));
        TextOverlay.addTo(this.jTextFieldImportKeystoreFile, rb.getResourceString("label.importkey.hint"));
        this.jLabelImage.setIcon(new ImageIcon(
                JDialogCertificates.IMAGE_IMPORT_MULTIRESOLUTION.toMinResolution(
                JDialogCertificates.IMAGE_SIZE_DIALOG)));
        this.manager = manager;
        this.logger = logger;
        this.getRootPane().setDefaultButton(this.jButtonOk);
        this.setButtonState();
    }

    public String getNewAlias() {
        return (this.newAlias);
    }

    /**
     * Sets the ok and cancel buttons of this GUI
     */
    private void setButtonState() {
        this.jButtonOk.setEnabled(!this.jTextFieldImportKeystoreFile.getText().isEmpty());
    }

    /**
     * Tries to import a key from a PKCS#12 or JKS formatted keystore file
     *
     */
    private void performImport() {
        try {
            this.importKeyAndCertificate(
                    this.jTextFieldImportKeystoreFile.getText(),
                    this.jPasswordFieldPassphrase.getPassword());
        } catch (Throwable e) {
            UINotification.instance().addNotification(null,
                    UINotification.Type.ERROR,
                    rb.getResourceString("key.import.error.title"),
                    rb.getResourceString("key.import.error.message",
                            "[" + e.getClass().getSimpleName() + "]:" + e.getMessage()));
        }
    }

    private void importKeyAndCertificate(String pemFile, char[] keypass) throws Exception {
        KeyAndCert keyAndCert = loadKeyAndCertFromPEM(pemFile, keypass);
        if (this.manager.getStorageType().equals(InMemoryKeyStore.KEYSTORE_INMEMORY)) {
            InMemoryKeyStoreUtil.importKey(keyAndCert, this.manager.getKeystore());
        }
    }

    /**
     * Reads the PEM file and extracts the certificate and the private key.
     *
     * @param pemFilePath Path to the PEM file.
     * @param password Key password (if the key is encrypted).
     */
    private KeyAndCert loadKeyAndCertFromPEM(String pemFilePath, char[] password) throws Exception {
        JcaPEMKeyConverter keyConverter = new JcaPEMKeyConverter().setProvider(BouncyCastleProviderSingleton.instance());
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter().setProvider(BouncyCastleProviderSingleton.instance());
        PrivateKey privateKey = null;
        X509Certificate certificate = null;
        try (PEMParser parser = new PEMParser(new FileReader(pemFilePath))) {
            Object object;
            while ((object = parser.readObject()) != null) {
                if (object instanceof PKCS8EncryptedPrivateKeyInfo) {
                    // Case 1: encrypted private key (-----BEGIN ENCRYPTED PRIVATE KEY-----)
                    PKCS8EncryptedPrivateKeyInfo pkcs8Encrypted = (PKCS8EncryptedPrivateKeyInfo) object;
                    InputDecryptorProvider decryptorProvider = new JceOpenSSLPKCS8DecryptorProviderBuilder()
                            .setProvider(BouncyCastleProviderSingleton.instance()).build(password);
                    PrivateKeyInfo privateKeyInfo = pkcs8Encrypted.decryptPrivateKeyInfo(decryptorProvider);
                    privateKey = keyConverter.getPrivateKey(privateKeyInfo);
                } else if (object instanceof PEMKeyPair) {
                    // Case 2: unencrypted key pair.
                    PEMKeyPair pemKeyPair = (PEMKeyPair) object;
                    privateKey = keyConverter.getKeyPair(pemKeyPair).getPrivate();
                } else if (object instanceof X509CertificateHolder) {
                    // Case 3: X.509 Certificate (-----BEGIN CERTIFICATE-----)
                    X509CertificateHolder certHolder = (X509CertificateHolder) object;
                    certificate = certConverter.getCertificate(certHolder);
                }
            }
        } catch (IOException e) {
            throw new Exception("Error reading or parsing the PEM file: " + e.getMessage(), e);
        } catch (Throwable e) {
            throw new Exception("Error during key decryption or conversion: " + e.getMessage(), e);
        }
        if (privateKey != null && certificate != null) {
            return new KeyAndCert(privateKey, certificate, password);
        } else if (privateKey != null) {
            throw new Exception("Found just the private key - certificate (public key information) is missing in the PEM file");
        } else {
            throw new Exception("No private key and no certificate found in the PEM file");
        }
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
        jLabelImage = new javax.swing.JLabel();
        jLabelImportFilePEM = new javax.swing.JLabel();
        jTextFieldImportKeystoreFile = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButtonBrowseImportFile = new javax.swing.JButton();
        jLabelKeyPassphrase = new javax.swing.JLabel();
        jPasswordFieldPassphrase = new javax.swing.JPasswordField();
        jPanelSpace2 = new javax.swing.JPanel();
        jPanelButtons = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelEdit.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelEdit.setLayout(new java.awt.GridBagLayout());

        jLabelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/util/security/cert/gui/missing_image32x32.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 10);
        jPanelEdit.add(jLabelImage, gridBagConstraints);

        jLabelImportFilePEM.setText(this.rb.getResourceString( "label.importkey"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelEdit.add(jLabelImportFilePEM, gridBagConstraints);

        jTextFieldImportKeystoreFile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldImportKeystoreFileKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jTextFieldImportKeystoreFile, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanelEdit.add(jPanel3, gridBagConstraints);

        jButtonBrowseImportFile.setText("..");
        jButtonBrowseImportFile.setToolTipText(this.rb.getResourceString( "button.browse"));
        jButtonBrowseImportFile.setMargin(new java.awt.Insets(2, 8, 2, 8));
        jButtonBrowseImportFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseImportFileActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanelEdit.add(jButtonBrowseImportFile, gridBagConstraints);

        jLabelKeyPassphrase.setText(this.rb.getResourceString( "label.keypass"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanelEdit.add(jLabelKeyPassphrase, gridBagConstraints);

        jPasswordFieldPassphrase.setMinimumSize(new java.awt.Dimension(150, 20));
        jPasswordFieldPassphrase.setPreferredSize(new java.awt.Dimension(150, 20));
        jPasswordFieldPassphrase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordFieldPassphraseKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelEdit.add(jPasswordFieldPassphrase, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanelEdit.add(jPanelSpace2, gridBagConstraints);

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

        setSize(new java.awt.Dimension(437, 278));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPasswordFieldPassphraseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldPassphraseKeyReleased
        this.setButtonState();
    }//GEN-LAST:event_jPasswordFieldPassphraseKeyReleased

    private void jButtonBrowseImportFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBrowseImportFileActionPerformed
        JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        MecFileChooser chooser = new MecFileChooser(
                parent,
                rb.getResourceString("filechooser.key.import"));
        chooser.browseFilename(this.jTextFieldImportKeystoreFile);
        this.setButtonState();
    }//GEN-LAST:event_jButtonBrowseImportFileActionPerformed

    private void jTextFieldImportKeystoreFileKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldImportKeystoreFileKeyReleased
        this.setButtonState();
    }//GEN-LAST:event_jTextFieldImportKeystoreFileKeyReleased

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        this.setVisible(false);
        this.performImport();
        this.dispose();
    }//GEN-LAST:event_jButtonOkActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBrowseImportFile;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JLabel jLabelImportFilePEM;
    private javax.swing.JLabel jLabelKeyPassphrase;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelEdit;
    private javax.swing.JPanel jPanelSpace2;
    private javax.swing.JPasswordField jPasswordFieldPassphrase;
    private javax.swing.JTextField jTextFieldImportKeystoreFile;
    // End of variables declaration//GEN-END:variables

   
}
