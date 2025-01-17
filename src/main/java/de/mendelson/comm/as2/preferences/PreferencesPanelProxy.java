//$Header: /as2/de/mendelson/comm/as2/preferences/PreferencesPanelProxy.java 24    2/11/23 15:53 Heller $
package de.mendelson.comm.as2.preferences;

import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
import de.mendelson.util.TextOverlay;
import de.mendelson.util.clientserver.BaseClient;
import de.mendelson.util.clientserver.clients.preferences.PreferencesClient;
import de.mendelson.util.passwordfield.PasswordOverlay;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Panel to define the proxy settings
 *
 * @author S.Heller
 * @version: $Revision: 24 $
 */
public class PreferencesPanelProxy extends PreferencesPanel {

    /**
     * Localize the GUI
     */
    private MecResourceBundle rb = null;

    private final PreferencesClient preferences;
    private String preferencesStrAtLoadTime = "";

    private final MendelsonMultiResolutionImage IMAGE_PROXY
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/preferences/proxy.svg",
                    JDialogPreferences.IMAGE_HEIGHT, JDialogPreferences.IMAGE_HEIGHT * 2);

    /**
     * Creates new form PreferencesPanelDirectories
     */
    public PreferencesPanelProxy(BaseClient baseClient) {
        //load resource bundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundlePreferences.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle "
                    + e.getClassName() + " not found.");
        }
        this.preferences = new PreferencesClient(baseClient);
        this.initComponents();
        TextOverlay.addTo(this.jTextFieldProxyPort, this.rb.getResourceString("label.proxy.port.hint"));
        TextOverlay.addTo(this.jTextFieldProxyURL, this.rb.getResourceString("label.proxy.url.hint"));
        TextOverlay.addTo(this.jTextFieldProxyUser, this.rb.getResourceString("label.proxy.user.hint"));
        PasswordOverlay.addTo(this.jPasswordFieldProxyPass, this.rb.getResourceString("label.proxy.pass.hint"));
    }

    private void setButtonState() {
        this.jTextFieldProxyURL.setEnabled(this.jCheckBoxUseProxy.isSelected());
        this.jTextFieldProxyURL.setEditable(this.jCheckBoxUseProxy.isSelected());
        this.jTextFieldProxyPort.setEnabled(this.jCheckBoxUseProxy.isSelected());
        this.jTextFieldProxyPort.setEditable(this.jCheckBoxUseProxy.isSelected());
        this.jTextFieldProxyUser.setEnabled(this.jCheckBoxUseProxy.isSelected()
                && this.jCheckBoxUseProxyAuthentification.isSelected());
        this.jTextFieldProxyUser.setEditable(this.jCheckBoxUseProxy.isSelected()
                && this.jCheckBoxUseProxyAuthentification.isSelected());
        this.jPasswordFieldProxyPass.setEnabled(this.jCheckBoxUseProxy.isSelected()
                && this.jCheckBoxUseProxyAuthentification.isSelected());
        this.jPasswordFieldProxyPass.setEditable(this.jCheckBoxUseProxy.isSelected()
                && this.jCheckBoxUseProxyAuthentification.isSelected());
        this.jCheckBoxUseProxyAuthentification.setEnabled(this.jCheckBoxUseProxy.isSelected());
    }

    /**
     * Sets new preferences to this panel to changes/modify
     */
    @Override
    public void loadPreferences() {
        this.jTextFieldProxyURL.setText(this.preferences.get(PreferencesAS2.PROXY_HOST));
        this.jTextFieldProxyPort.setText(this.preferences.get(PreferencesAS2.PROXY_PORT));
        this.jTextFieldProxyUser.setText(this.preferences.get(PreferencesAS2.AUTH_PROXY_USER));
        this.jPasswordFieldProxyPass.setText(this.preferences.get(PreferencesAS2.AUTH_PROXY_PASS));
        this.jCheckBoxUseProxy.setSelected(this.preferences.getBoolean(PreferencesAS2.PROXY_USE));
        this.jCheckBoxUseProxyAuthentification.setSelected(this.preferences.getBoolean(PreferencesAS2.AUTH_PROXY_USE));
        this.setButtonState();
        this.preferencesStrAtLoadTime = this.captureSettingsToStr();
    }

    /**Helper method to find out if there are changes in the GUI before storing them to the server*/
    private String captureSettingsToStr(){
        StringBuilder builder = new StringBuilder();
        builder.append( PreferencesAS2.PROXY_HOST ).append("=")
                .append( this.jTextFieldProxyURL.getText()).append(";");        
        builder.append( PreferencesAS2.PROXY_PORT ).append("=")
                .append( this.jTextFieldProxyPort.getText()).append(";");        
        builder.append( PreferencesAS2.AUTH_PROXY_USER ).append("=")
                .append( this.jTextFieldProxyUser.getText()).append(";");
        builder.append( PreferencesAS2.AUTH_PROXY_PASS ).append("=")
                .append( new String(this.jPasswordFieldProxyPass.getPassword())).append(";");
        builder.append( PreferencesAS2.PROXY_USE ).append("=")
                .append( this.jCheckBoxUseProxy.isSelected()).append(";");
        builder.append( PreferencesAS2.AUTH_PROXY_USE ).append("=")
                .append( this.jCheckBoxUseProxyAuthentification.isSelected()).append(";");
        return( builder.toString() );
    }
    
    
    @Override
    public boolean preferencesAreModified() {
        return( !this.preferencesStrAtLoadTime.equals(this.captureSettingsToStr()) );
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelMargin = new javax.swing.JPanel();
        jTextFieldProxyUser = new javax.swing.JTextField();
        jLabelProxyUser = new javax.swing.JLabel();
        jLabelProxyPass = new javax.swing.JLabel();
        jPasswordFieldProxyPass = new javax.swing.JPasswordField();
        jPanelSpace = new javax.swing.JPanel();
        jLabelProxyURL = new javax.swing.JLabel();
        jTextFieldProxyURL = new javax.swing.JTextField();
        jCheckBoxUseProxy = new javax.swing.JCheckBox();
        jCheckBoxUseProxyAuthentification = new javax.swing.JCheckBox();
        jLabelColon = new javax.swing.JLabel();
        jTextFieldProxyPort = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        jPanelMargin.setLayout(new java.awt.GridBagLayout());

        jTextFieldProxyUser.setPreferredSize(new java.awt.Dimension(200, 20));
        jTextFieldProxyUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldProxyUserKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMargin.add(jTextFieldProxyUser, gridBagConstraints);

        jLabelProxyUser.setText(this.rb.getResourceString( "label.proxy.user"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 5, 5);
        jPanelMargin.add(jLabelProxyUser, gridBagConstraints);

        jLabelProxyPass.setText(this.rb.getResourceString( "label.proxy.pass"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 5, 5);
        jPanelMargin.add(jLabelProxyPass, gridBagConstraints);

        jPasswordFieldProxyPass.setPreferredSize(new java.awt.Dimension(200, 20));
        jPasswordFieldProxyPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordFieldProxyPassKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMargin.add(jPasswordFieldProxyPass, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanelMargin.add(jPanelSpace, gridBagConstraints);

        jLabelProxyURL.setText(this.rb.getResourceString( "label.proxy.url"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMargin.add(jLabelProxyURL, gridBagConstraints);

        jTextFieldProxyURL.setPreferredSize(new java.awt.Dimension(200, 20));
        jTextFieldProxyURL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldProxyURLKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMargin.add(jTextFieldProxyURL, gridBagConstraints);

        jCheckBoxUseProxy.setText(this.rb.getResourceString( "label.proxy.use"));
        jCheckBoxUseProxy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxUseProxyItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        jPanelMargin.add(jCheckBoxUseProxy, gridBagConstraints);

        jCheckBoxUseProxyAuthentification.setText(this.rb.getResourceString( "label.proxy.useauthentification"));
        jCheckBoxUseProxyAuthentification.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxUseProxyAuthentificationItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanelMargin.add(jCheckBoxUseProxyAuthentification, gridBagConstraints);

        jLabelColon.setText(":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanelMargin.add(jLabelColon, gridBagConstraints);

        jTextFieldProxyPort.setPreferredSize(new java.awt.Dimension(50, 20));
        jTextFieldProxyPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldProxyPortKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMargin.add(jTextFieldProxyPort, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jPanelMargin, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldProxyPortKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProxyPortKeyReleased
        this.setButtonState();
    }//GEN-LAST:event_jTextFieldProxyPortKeyReleased

    private void jCheckBoxUseProxyAuthentificationItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxUseProxyAuthentificationItemStateChanged
        this.setButtonState();
    }//GEN-LAST:event_jCheckBoxUseProxyAuthentificationItemStateChanged

    private void jTextFieldProxyURLKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProxyURLKeyReleased
        this.setButtonState();
    }//GEN-LAST:event_jTextFieldProxyURLKeyReleased

    private void jCheckBoxUseProxyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxUseProxyItemStateChanged
        this.setButtonState();
    }//GEN-LAST:event_jCheckBoxUseProxyItemStateChanged

    private void jPasswordFieldProxyPassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldProxyPassKeyReleased
        this.setButtonState();
    }//GEN-LAST:event_jPasswordFieldProxyPassKeyReleased

    private void jTextFieldProxyUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProxyUserKeyReleased
        this.setButtonState();
    }//GEN-LAST:event_jTextFieldProxyUserKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxUseProxy;
    private javax.swing.JCheckBox jCheckBoxUseProxyAuthentification;
    private javax.swing.JLabel jLabelColon;
    private javax.swing.JLabel jLabelProxyPass;
    private javax.swing.JLabel jLabelProxyURL;
    private javax.swing.JLabel jLabelProxyUser;
    private javax.swing.JPanel jPanelMargin;
    private javax.swing.JPanel jPanelSpace;
    private javax.swing.JPasswordField jPasswordFieldProxyPass;
    private javax.swing.JTextField jTextFieldProxyPort;
    private javax.swing.JTextField jTextFieldProxyURL;
    private javax.swing.JTextField jTextFieldProxyUser;
    // End of variables declaration//GEN-END:variables

    @Override
    public void savePreferences() {
        try {
            int proxyPort = Integer.valueOf(this.jTextFieldProxyPort.getText().trim()).intValue();
            this.preferences.putInt(PreferencesAS2.PROXY_PORT, proxyPort);
        } catch (Exception e) {
            //just ignore this - the formerly value will be kept and the user will see this one he opens the preferences again
        }
        this.preferences.putBoolean(PreferencesAS2.AUTH_PROXY_USE, this.jCheckBoxUseProxyAuthentification.isSelected());
        this.preferences.put(PreferencesAS2.PROXY_HOST, this.jTextFieldProxyURL.getText());
        this.preferences.putBoolean(PreferencesAS2.PROXY_USE, this.jCheckBoxUseProxy.isSelected());
        this.preferences.put(PreferencesAS2.AUTH_PROXY_PASS, new String(this.jPasswordFieldProxyPass.getPassword()));
        this.preferences.put(PreferencesAS2.AUTH_PROXY_USER, this.jTextFieldProxyUser.getText());
    }

    @Override
    public ImageIcon getIcon() {
        return (new ImageIcon(IMAGE_PROXY));
    }

    @Override
    public String getTabResource() {
        return ("tab.proxy");
    }

}
