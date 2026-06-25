//$Header: /as2/de/mendelson/comm/as2/partner/gui/JDialogPartnerConfig.java 96    9/04/26 8:46 Heller $
package de.mendelson.comm.as2.partner.gui;

import de.mendelson.comm.as2.client.AS2Gui;
import de.mendelson.comm.as2.client.AS2StatusBar;
import de.mendelson.comm.as2.clientserver.message.PartnerConfigurationChanged;
import de.mendelson.util.modulelock.LockClientInformation;
import de.mendelson.util.modulelock.ModuleLock;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerSystem;
import de.mendelson.comm.as2.partner.clientserver.PartnerListRequest;
import de.mendelson.comm.as2.partner.clientserver.PartnerListResponse;
import de.mendelson.comm.as2.partner.clientserver.PartnerModificationRequest;
import de.mendelson.comm.as2.partner.gui.filter.DialogFilterPartner;
import de.mendelson.comm.as2.partner.gui.filter.DialogFilterPartner.FilterData;
import de.mendelson.comm.as2.partner.gui.filter.ResourceBundleFilterPartner;
import de.mendelson.comm.as2.partner.gui.global.JDialogGlobalChange;
import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.util.ColorUtil;
import de.mendelson.util.LayoutManagerJToolbar;
import de.mendelson.util.LockingGlassPane;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.MendelsonMultiResolutionImage;
import de.mendelson.util.clientserver.AllowModificationCallback;
import de.mendelson.util.clientserver.GUIClient;
import de.mendelson.util.clientserver.clients.fileoperation.FileOperationClient;
import de.mendelson.util.clientserver.clients.filesystemview.FileSystemViewRequest;
import de.mendelson.util.clientserver.clients.filesystemview.FileSystemViewResponse;
import de.mendelson.util.clientserver.clients.preferences.PreferencesClient;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.uinotification.UINotification;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Dialog to configure the partner of the AS2 server
 *
 * @author S.Heller
 * @version $Revision: 96 $
 */
public class JDialogPartnerConfig extends JDialog {

    private static final MecResourceBundle rb;
    private static final MecResourceBundle rbFilter;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundlePartnerConfig.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        try {
            rbFilter = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleFilterPartner.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }
    private final List<Partner> fullPartnerList = new ArrayList<Partner>();
    private final List<Partner> filteredPartnerList = new ArrayList<Partner>();
    private final JPanelPartner panelEditPartner;
    private final JTreePartner jTreePartner;
    private final CertificateManager certificateManagerEncSign;
    private final CertificateManager certificateManagerTLS;
    private final GUIClient guiClient;
    private static final Logger logger = Logger.getLogger("de.mendelson.as2.client");
    private final AS2StatusBar status;
    private final List<AllowModificationCallback> allowModificationCallbackList
            = new ArrayList<AllowModificationCallback>();
    private final LockClientInformation lockKeeper;
    private static final MendelsonMultiResolutionImage IMAGE_DELETE
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/delete.svg",
                    AS2Gui.IMAGE_SIZE_TOOLBAR);
    private static final MendelsonMultiResolutionImage IMAGE_COPY
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/copypartner.svg",
                    AS2Gui.IMAGE_SIZE_TOOLBAR);
    private static final MendelsonMultiResolutionImage IMAGE_ADD
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/add.svg",
                    AS2Gui.IMAGE_SIZE_TOOLBAR);
    private static final MendelsonMultiResolutionImage IMAGE_PARTNER_GROUP
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/global/partner_group.svg",
                    AS2Gui.IMAGE_SIZE_TOOLBAR);
    private static final MendelsonMultiResolutionImage IMAGE_FILTER
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/filter.svg",
                    AS2Gui.IMAGE_SIZE_TOOLBAR);
    private static final MendelsonMultiResolutionImage IMAGE_FILTER_CLEAR
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/filter_clear_noborder.svg",
                    AS2Gui.IMAGE_SIZE_POPUP);
    private static final MendelsonMultiResolutionImage IMAGE_FILTER_ACTIVE
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/filter_active.svg",
                    AS2Gui.IMAGE_SIZE_TOOLBAR);
    private Color colorRed = Color.RED.darker();
    private final DialogFilterPartner filterDialog;
    private final Consumer<FilterData> filterdataConsumer;

    /**
     * Creates new form JDialogMessageMapping
     */
    public JDialogPartnerConfig(JFrame parent,
            GUIClient guiClient,
            AS2StatusBar status, boolean changesAllowed,
            LockClientInformation lockKeeper,
            CertificateManager certificateManagerEncSign,
            CertificateManager certificateManagerTLS,
            List<PartnerSystem> partnerSystemList,
            String activatedPlugins) {
        super(parent, true);
        this.status = status;
        this.guiClient = guiClient;
        this.lockKeeper = lockKeeper;
        this.certificateManagerEncSign = certificateManagerEncSign;
        this.certificateManagerTLS = certificateManagerTLS;
        this.jTreePartner = new JTreePartner();
        //create tree gap
        this.jTreePartner.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        this.initComponents();
        this.setMultiresolutionIcons();
        this.jScrollPaneTree.setViewportView(this.jTreePartner);
        this.panelEditPartner = new JPanelPartner(this.guiClient.getBaseClient(),
                this.jTreePartner,
                this.certificateManagerEncSign,
                this.certificateManagerTLS,
                this.jButtonPartnerConfigOk, this.status, changesAllowed,
                partnerSystemList, activatedPlugins,
                this.jPanelConfigurationWarning);
        this.jPanelPartner.add(this.panelEditPartner, BorderLayout.CENTER);
        this.getRootPane().setDefaultButton(this.jButtonPartnerConfigOk);
        this.filterdataConsumer = new Consumer<FilterData>() {

            @Override
            public void accept(FilterData filterData) {
                filterPartner(filterData);
            }
        };
        this.filterDialog = new DialogFilterPartner(this, this.jButtonFilter, this.filterdataConsumer);
        this.filterDialog.setVisible(false);
        try {
            this.fullPartnerList.addAll(this.loadAllPartnerFromServer());
            this.filteredPartnerList.addAll(this.fullPartnerList);
            this.jLabelFilterCount.setText(rb.getResourceString("label.filterdisplay",
                            new Object[]{
                                String.valueOf(this.filteredPartnerList.size()),
                                String.valueOf(this.fullPartnerList.size()),}));
            this.jTreePartner.buildTree(Collections.unmodifiableList(this.fullPartnerList),
                    Collections.unmodifiableList(this.filteredPartnerList));
        } catch (Exception e) {
            UINotification.instance().addNotification(e);
            return;
        }
        this.jTreePartner.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent evt) {
                displayPartnerValues();
            }
        });
        this.jToolBar.setLayout(new LayoutManagerJToolbar());
        if (UIManager.getColor("Objects.RedStatus") != null) {
            this.colorRed = UIManager.getColor("Objects.RedStatus");
        } else {
            this.colorRed = ColorUtil.getBestContrastColorAroundForeground(
                    this.jPanelModuleLockWarning.getBackground(), this.colorRed);
        }
        this.jPanelModuleLockWarning.setBorder(new LineBorder(this.colorRed, 1));
        this.jLabelModuleLockedWarning.setForeground(this.colorRed);
        this.jPanelConfigurationWarning.setBorder(new LineBorder(this.colorRed, 1));
        this.jLabelConfigurationWarning.setForeground(this.colorRed);
        this.jPanelModuleLockWarning.setVisible(!changesAllowed);
        this.jPanelConfigurationWarning.setVisible(false);
        this.jButtonCancelFilter.setVisible(false);
    }

    @Override
    public void setVisible(boolean flag) {
        if (flag) {
            this.displayPartnerValues();
        }
        super.setVisible(flag);
    }

    private void setMultiresolutionIcons() {
        this.jButtonDeletePartner.setIcon(new ImageIcon(IMAGE_DELETE.toMinResolution(AS2Gui.IMAGE_SIZE_TOOLBAR)));
        this.jButtonClonePartner.setIcon(new ImageIcon(IMAGE_COPY.toMinResolution(AS2Gui.IMAGE_SIZE_TOOLBAR)));
        this.jButtonNewPartner.setIcon(new ImageIcon(IMAGE_ADD.toMinResolution(AS2Gui.IMAGE_SIZE_TOOLBAR)));
        this.jButtonGlobalSettings.setIcon(new ImageIcon(IMAGE_PARTNER_GROUP.toMinResolution(AS2Gui.IMAGE_SIZE_TOOLBAR)));
        this.jButtonFilter.setIcon(new ImageIcon(IMAGE_FILTER.toMinResolution(AS2Gui.IMAGE_SIZE_TOOLBAR)));
        this.jButtonCancelFilter.setIcon(new ImageIcon(IMAGE_FILTER_CLEAR.toMinResolution(AS2Gui.IMAGE_SIZE_POPUP)));

    }

    /**
     * Sends a request to the server to load the partner
     */
    private List<Partner> loadAllPartnerFromServer() throws Exception {
        PartnerListResponse response = (PartnerListResponse) this.guiClient.getBaseClient().sendSync(
                new PartnerListRequest(PartnerListRequest.ListOption.ALL_NO_CACHE), Partner.TIMEOUT_PARTNER_REQUEST);
        if (response.getException() != null) {
            throw (response.getException());
        }
        List<Partner> partnerList = response.getList();
        return (partnerList);
    }

    /**
     * Filters the partner
     */
    private void filterPartner(FilterData filterData) {
        synchronized (this.filteredPartnerList) {
            this.filteredPartnerList.clear();
            if (filterData.getSearchValue().isBlank()) {
                this.filteredPartnerList.addAll(this.fullPartnerList);
            } else {
                synchronized (this.fullPartnerList) {
                    String searchValueLower = filterData.getSearchValue().toLowerCase();
                    for (Partner partner : this.fullPartnerList) {
                        if (filterData.getCategory().equals("category.as2id")) {
                            if (partner.getAS2Identification().toLowerCase().contains(searchValueLower)) {
                                this.filteredPartnerList.add(partner);
                            }
                        } else if (filterData.getCategory().equals("category.name")) {
                            if (partner.getName().toLowerCase().contains(searchValueLower)) {
                                this.filteredPartnerList.add(partner);
                            }
                        } else if (filterData.getCategory().equals("category.comment")) {
                            boolean match
                                    = Optional.ofNullable(partner.getContactCompany())
                                            .orElse("")
                                            .toLowerCase()
                                            .contains(searchValueLower)
                                    || Optional.ofNullable(partner.getContactAS2())
                                            .orElse("")
                                            .toLowerCase()
                                            .contains(searchValueLower);
                            if (match) {
                                this.filteredPartnerList.add(partner);
                            }
                        } else if (filterData.getCategory().equals("category.url")) {
                            if (!partner.isLocalStation()) {
                                if (partner.getURL().toLowerCase().contains(searchValueLower)) {
                                    this.filteredPartnerList.add(partner);
                                }
                            }
                        } else if (filterData.getCategory().equals("category.subject")) {
                            if (!partner.isLocalStation()) {
                                if (partner.getSubject().toLowerCase().contains(searchValueLower)) {
                                    this.filteredPartnerList.add(partner);
                                }
                            }
                        }
                    }
                }
            }
            try {
                synchronized (this.fullPartnerList) {
                    this.jLabelFilterCount.setText(rb.getResourceString("label.filterdisplay",
                            new Object[]{
                                String.valueOf(this.filteredPartnerList.size()),
                                String.valueOf(this.fullPartnerList.size()),}));
                    if (filterData.getSearchValue().isBlank()) {
                        this.jButtonCancelFilter.setVisible(false);
                        this.jButtonFilter.setIcon(
                                new ImageIcon(IMAGE_FILTER.toMinResolution(AS2Gui.IMAGE_SIZE_TOOLBAR)));
                    } else {
                        this.jButtonCancelFilter.setVisible(true);
                        this.jButtonCancelFilter.setText(
                                "[" + rbFilter.getResourceString(filterData.getCategory()) + "] "
                                + filterData.getSearchValue());
                        this.jButtonFilter.setIcon(
                                new ImageIcon(IMAGE_FILTER_ACTIVE.toMinResolution(AS2Gui.IMAGE_SIZE_TOOLBAR)));
                    }
                    this.jTreePartner.buildTree(Collections.unmodifiableList(this.fullPartnerList),
                            Collections.unmodifiableList(this.filteredPartnerList));
                }
            } catch (Exception e) {
                UINotification.instance().addNotification(e);
            }
        }
    }

    /**
     * Asks the server for an absolute path on its side - this is useful if
     * client and server are running on different OS or if the client/server
     * path structure is not the same
     *
     * @param directory
     * @return A string array of the size 2: 0: path, 1: path separator
     */
    private String[] getAbsolutePathOnServerSide(String directory) {
        FileSystemViewRequest request = new FileSystemViewRequest(FileSystemViewRequest.TYPE_GET_ABSOLUTE_PATH_STR);
        request.setRequestFilePath(directory);
        FileSystemViewResponse response = (FileSystemViewResponse) this.guiClient.getBaseClient().sendSync(request, Partner.TIMEOUT_PARTNER_REQUEST);
        return (new String[]{response.getParameterString(), response.getServerSideFileSeparator()});
    }

    /**
     * Selects a partner - by its name. If the name does not exist nothing
     * happens
     */
    public void setPreselectedPartner(String partnerName) {
        Partner partner = this.jTreePartner.getDisplayedPartnerByName(partnerName);
        if (partner != null) {
            this.jTreePartner.setSelectedPartner(partner);
        }
    }

    /**
     * Adds a callback that is called if a user tries to modify the
     * configuration A modification will be prevented if one of the callbacks
     * does not allow it
     */
    public void addAllowModificationCallback(AllowModificationCallback callback) {
        this.allowModificationCallbackList.add(callback);
    }

    /**
     * Checks if the operation is allowed - this could be set by external
     * callbacks
     */
    private boolean isOperationAllowed(boolean silent) {
        for (AllowModificationCallback callback : this.allowModificationCallbackList) {
            boolean modificationAllowed = callback.allowModification(silent);
            if (!modificationAllowed) {
                return (false);
            }
        }
        return (true);
    }

    public void setDisplayOverwriteLocalstationSecurity(boolean display) {
        this.panelEditPartner.setDisplayOverwriteLocalstationSecurity(display);
    }

    public void setDisplayNotificationPanel(boolean display) {
        this.panelEditPartner.setDisplayNotificationPanel(display);
    }

    public void setDisplayHttpHeaderPanel(boolean display) {
        this.panelEditPartner.setDisplayHttpHeaderPanel(display);
    }

    private void displayPartnerValues() {
        DefaultMutableTreeNode selectedNode = this.jTreePartner.getSelectedNode();
        if (selectedNode == null) {
            return;
        }
        Partner selectedPartner = (Partner) selectedNode.getUserObject();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                panelEditPartner.setPartner(selectedPartner, selectedNode);
            }
        });
    }

    private void deleteSelectedPartner() {
        Partner partner = this.jTreePartner.getSelectedPartner();
        if (partner != null) {
            //ask the user if the partner should be really deleted, all data is lost
            int requestValue = JOptionPane.showConfirmDialog(
                    this, rb.getResourceString("dialog.partner.delete.message", partner.getName()),
                    rb.getResourceString("dialog.partner.delete.title"),
                    JOptionPane.YES_NO_OPTION);
            if (requestValue != JOptionPane.YES_OPTION) {
                return;
            }
            partner = this.jTreePartner.deleteSelectedPartner();
            if (partner != null) {
                this.fullPartnerList.remove(partner);
            }
        }
    }

    private boolean checkAllLocalStationsHavePrivateKeys() {
        List<Partner> localStations = this.jTreePartner.getLocalStations();
        //no local station? should not happen
        if (localStations == null || localStations.isEmpty()) {
            return (false);
        }
        for (Partner localStation : localStations) {
            String signSerial = localStation.getSignFingerprintSHA1();
            String cryptSerial = localStation.getCryptFingerprintSHA1();
            try {
                this.certificateManagerEncSign.getPrivateKeyByFingerprintSHA1(signSerial);
                this.certificateManagerEncSign.getPrivateKeyByFingerprintSHA1(cryptSerial);
            } catch (Exception e) {
                UINotification.instance().addNotification(e);
                return (false);
            }
        }
        return (true);
    }

    /**
     * A partner name has been changed: Ask if the underlaying directory should
     * be changed, too
     */
    private void handlePartnerNameChange(Partner existingPartner, Partner newPartner) {
        //get the message path from the server
        PreferencesClient preferences = new PreferencesClient(this.guiClient.getBaseClient(), Partner.TIMEOUT_PARTNER_REQUEST);
        String messageDir = preferences.get(PreferencesAS2.DIR_MSG);
        String[] serversideInfo = this.getAbsolutePathOnServerSide(messageDir);
        String serverSideMessagePath = serversideInfo[0];
        String serverSideFileSeparator = serversideInfo[1];
        int requestValue = JOptionPane.showConfirmDialog(this, rb.getResourceString("dialog.partner.renamedir.message",
                new Object[]{existingPartner.getName(), newPartner.getName(),
                    existingPartner.computeMessagePath(serverSideMessagePath, serverSideFileSeparator)}),
                rb.getResourceString("dialog.partner.renamedir.title"),
                JOptionPane.YES_NO_OPTION);
        if (requestValue != JOptionPane.YES_OPTION) {
            return;
        }
        FileOperationClient fileClient = new FileOperationClient(this.guiClient.getBaseClient());
        boolean success = fileClient.rename(existingPartner.computeMessagePath(serverSideMessagePath, serverSideFileSeparator),
                newPartner.computeMessagePath(serverSideMessagePath, serverSideFileSeparator));
        if (success) {
            this.logger.log(Level.FINE, rb.getResourceString("directory.rename.success",
                    new Object[]{existingPartner.computeMessagePath(serverSideMessagePath, serverSideFileSeparator),
                        newPartner.computeMessagePath(serverSideMessagePath, serverSideFileSeparator)}));
        } else {
            this.logger.log(Level.SEVERE, rb.getResourceString("directory.rename.failure",
                    new Object[]{existingPartner.computeMessagePath(serverSideMessagePath, serverSideFileSeparator),
                        newPartner.computeMessagePath(serverSideMessagePath, serverSideFileSeparator)}));
        }
    }

    private void handlePartnerDelete(Partner existingPartner) {
        //get the message path from the server
        PreferencesClient preferences = new PreferencesClient(this.guiClient.getBaseClient(), Partner.TIMEOUT_PARTNER_REQUEST);
        String messageDir = preferences.get(PreferencesAS2.DIR_MSG);
        String[] serversideInfo = this.getAbsolutePathOnServerSide(messageDir);
        String serverSideMessagePath = serversideInfo[0];
        String serverSideFileSeparator = serversideInfo[1];
        int requestValue = JOptionPane.showConfirmDialog(
                this, rb.getResourceString("dialog.partner.deletedir.message",
                        new Object[]{existingPartner.getName(),
                            existingPartner.computeMessagePath(serverSideMessagePath, serverSideFileSeparator)}),
                rb.getResourceString("dialog.partner.deletedir.title"),
                JOptionPane.YES_NO_OPTION);
        if (requestValue != JOptionPane.YES_OPTION) {
            return;
        }
        FileOperationClient fileClient = new FileOperationClient(this.guiClient.getBaseClient());
        boolean success = fileClient.delete(existingPartner.computeMessagePath(serverSideMessagePath, serverSideFileSeparator));
        if (success) {
            this.logger.log(Level.FINE, rb.getResourceString("directory.delete.success",
                    new Object[]{existingPartner.computeMessagePath(serverSideMessagePath, serverSideFileSeparator)}));
        } else {
            this.logger.log(Level.WARNING, rb.getResourceString("directory.delete.failure",
                    new Object[]{
                        existingPartner.computeMessagePath(serverSideMessagePath, serverSideFileSeparator),
                        fileClient.getLastException().getMessage()
                    }));
        }
    }

    /**
     * Lock the component: Add a glasspane that prevents any action on the UI
     */
    protected void lock() {
        //init glasspane for first use
        if (!(this.getGlassPane() instanceof LockingGlassPane)) {
            this.setGlassPane(new LockingGlassPane());
        }
        this.getGlassPane().setVisible(true);
        this.getGlassPane().requestFocusInWindow();
    }

    /**
     * Unlock the component: remove the glasspane that prevents any action on
     * the UI
     */
    protected void unlock() {
        getGlassPane().setVisible(false);
    }

    private void okPressed() {
        //check if a local station is set
        if (!JDialogPartnerConfig.this.jTreePartner.localStationIsSet()) {
            UINotification.instance().addNotification(
                    null,
                    UINotification.Type.ERROR,
                    JDialogPartnerConfig.rb.getResourceString("nolocalstation.title"),
                    JDialogPartnerConfig.rb.getResourceString("nolocalstation.message")
            );
            return;
        }
        //check if the localstation contains a private key in security settings
        if (!JDialogPartnerConfig.this.checkAllLocalStationsHavePrivateKeys()) {
            UINotification.instance().addNotification(
                    null,
                    UINotification.Type.ERROR,
                    JDialogPartnerConfig.rb.getResourceString("localstation.noprivatekey.title"),
                    JDialogPartnerConfig.rb.getResourceString("localstation.noprivatekey.message")
            );
            return;
        }
        final String uniqueId = this.getClass().getName() + ".okPressed." + System.currentTimeMillis();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try {
                    //detect if a partner name has been changed
                    for (Partner newPartner : JDialogPartnerConfig.this.fullPartnerList) {
                        if (newPartner.getDBId() != -1) {
                            PartnerListRequest request = new PartnerListRequest(PartnerListRequest.ListOption.DB_ID);
                            request.setAdditionalListOptionInt(newPartner.getDBId());
                            List<Partner> checkList = ((PartnerListResponse) JDialogPartnerConfig.this.guiClient.getBaseClient().
                                    sendSync(request, Partner.TIMEOUT_PARTNER_REQUEST)).getList();
                            if (checkList != null && !checkList.isEmpty() && !newPartner.getName().equals(checkList.get(0).getName())) {
                                JDialogPartnerConfig.this.handlePartnerNameChange(checkList.get(0), newPartner);
                            }
                        }
                    }
                    //detect if a partner has been deleted
                    List<Partner> existingPartnerArray = ((PartnerListResponse) JDialogPartnerConfig.this.guiClient.getBaseClient().
                            sendSync(new PartnerListRequest(PartnerListRequest.ListOption.ALL_NO_CACHE), Partner.TIMEOUT_PARTNER_REQUEST)).getList();
                    for (Partner existingPartner : existingPartnerArray) {
                        boolean doesStillExist = false;
                        for (Partner newPartner : JDialogPartnerConfig.this.fullPartnerList) {
                            if (newPartner.getDBId() == existingPartner.getDBId()) {
                                doesStillExist = true;
                                break;
                            }
                        }
                        if (!doesStillExist) {
                            JDialogPartnerConfig.this.handlePartnerDelete(existingPartner);
                        }
                    }
                    JDialogPartnerConfig.this.lock();
                    //display wait indicator
                    JDialogPartnerConfig.this.status.startProgressIndeterminate(
                            JDialogPartnerConfig.rb.getResourceString("saving"), uniqueId);
                    PartnerModificationRequest modificationRequest = new PartnerModificationRequest();
                    modificationRequest.setData(JDialogPartnerConfig.this.fullPartnerList);
                    ClientServerResponse response
                            = JDialogPartnerConfig.this.guiClient.getBaseClient().sendSync(
                                    modificationRequest, Partner.TIMEOUT_PARTNER_REQUEST);
                    if (response.getException() != null) {
                        throw (response.getException());
                    }
                    //inform the server that the configuration has been changed
                    PartnerConfigurationChanged signal = new PartnerConfigurationChanged();
                    JDialogPartnerConfig.this.guiClient.sendAsync(signal);
                } catch (Throwable e) {
                    JDialogPartnerConfig.this.unlock();
                    JDialogPartnerConfig.this.status.stopProgressIfExists(uniqueId);
                    UINotification.instance().addNotification(e);
                    e.printStackTrace();
                } finally {
                    JDialogPartnerConfig.this.unlock();
                    JDialogPartnerConfig.this.status.stopProgressIfExists(uniqueId);
                    JDialogPartnerConfig.this.setVisible(false);
                    JDialogPartnerConfig.this.dispose();
                }
            }
        };
        GUIClient.submit(runnable);
    }

    /**
     * Clones the selected partner and select it
     */
    private void cloneSelectedPartner() {
        Partner selectedPartner = this.jTreePartner.getSelectedPartner();
        Partner newPartner = (Partner) selectedPartner.clone();
        if (newPartner == null) {
            return;
        }
        newPartner.setDBId(-1);
        newPartner.setCryptFingerprintSHA1(selectedPartner.getCryptFingerprintSHA1());
        newPartner.setSignFingerprintSHA1(selectedPartner.getSignFingerprintSHA1());
        //find the next unique name
        String rawName = selectedPartner.getName();
        boolean alreadyUsed = true;
        int counter = 0;
        while (alreadyUsed) {
            String testName = rawName + String.valueOf(counter);
            alreadyUsed = false;
            for (Partner checkPartner : this.fullPartnerList) {
                if (checkPartner.getName().equals(testName)) {
                    alreadyUsed = true;
                    break;
                }
            }
            if (!alreadyUsed) {
                newPartner.setName(testName);
            }
            counter++;
        }
        //find the next unique id
        String rawId = selectedPartner.getAS2Identification();
        alreadyUsed = true;
        counter = 0;
        while (alreadyUsed) {
            String testId = rawId + String.valueOf(counter);
            alreadyUsed = false;
            for (Partner checkPartner : this.fullPartnerList) {
                if (checkPartner.getAS2Identification().equals(testId)) {
                    alreadyUsed = true;
                    break;
                }
            }
            if (!alreadyUsed) {
                newPartner.setAS2Identification(testId);
            }
            counter++;
        }
        this.jTreePartner.addPartner(newPartner);
        this.fullPartnerList.add(newPartner);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jToolBar = new javax.swing.JToolBar();
        jButtonNewPartner = new javax.swing.JButton();
        jButtonClonePartner = new javax.swing.JButton();
        jButtonDeletePartner = new javax.swing.JButton();
        jButtonGlobalSettings = new javax.swing.JButton();
        jButtonFilter = new javax.swing.JButton();
        jPanelMain = new javax.swing.JPanel();
        jPanelModuleLockWarning = new javax.swing.JPanel();
        jLabelModuleLockedWarning = new javax.swing.JLabel();
        jButtonModuleLockInfo = new javax.swing.JButton();
        jPanelPartnerMain = new javax.swing.JPanel();
        jSplitPane = new javax.swing.JSplitPane();
        jPanelPartnerTree = new javax.swing.JPanel();
        jScrollPaneTree = new javax.swing.JScrollPane();
        jButtonCancelFilter = new javax.swing.JButton();
        jPanelPartner = new javax.swing.JPanel();
        jPanelButton = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonPartnerConfigOk = new de.mendelson.comm.as2.partner.gui.JButtonPartnerConfigOk();
        jLabelFilterCount = new javax.swing.JLabel();
        jPanelConfigurationWarning = new javax.swing.JPanel();
        jLabelConfigurationWarning = new javax.swing.JLabel();

        setTitle(this.rb.getResourceString( "title" ));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jToolBar.setFloatable(false);
        jToolBar.setRollover(true);

        jButtonNewPartner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonNewPartner.setText(this.rb.getResourceString( "button.new"));
        jButtonNewPartner.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonNewPartner.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonNewPartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewPartnerActionPerformed(evt);
            }
        });
        jToolBar.add(jButtonNewPartner);

        jButtonClonePartner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonClonePartner.setText(this.rb.getResourceString( "button.clone"));
        jButtonClonePartner.setFocusable(false);
        jButtonClonePartner.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonClonePartner.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonClonePartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClonePartnerActionPerformed(evt);
            }
        });
        jToolBar.add(jButtonClonePartner);

        jButtonDeletePartner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonDeletePartner.setText(this.rb.getResourceString( "button.delete"));
        jButtonDeletePartner.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonDeletePartner.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonDeletePartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeletePartnerActionPerformed(evt);
            }
        });
        jToolBar.add(jButtonDeletePartner);

        jButtonGlobalSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonGlobalSettings.setText(this.rb.getResourceString( "button.globalchange"));
        jButtonGlobalSettings.setFocusable(false);
        jButtonGlobalSettings.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonGlobalSettings.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonGlobalSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGlobalSettingsActionPerformed(evt);
            }
        });
        jToolBar.add(jButtonGlobalSettings);

        jButtonFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/mendelson/comm/as2/partner/gui/missing_image24x24.gif"))); // NOI18N
        jButtonFilter.setText(this.rb.getResourceString( "button.filter"));
        jButtonFilter.setFocusable(false);
        jButtonFilter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonFilter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFilterActionPerformed(evt);
            }
        });
        jToolBar.add(jButtonFilter);

        getContentPane().add(jToolBar, java.awt.BorderLayout.NORTH);

        jPanelMain.setLayout(new java.awt.GridBagLayout());

        jPanelModuleLockWarning.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 51, 0)));
        jPanelModuleLockWarning.setLayout(new java.awt.GridBagLayout());

        jLabelModuleLockedWarning.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabelModuleLockedWarning.setForeground(new java.awt.Color(204, 51, 0));
        jLabelModuleLockedWarning.setText(this.rb.getResourceString( "module.locked"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelModuleLockWarning.add(jLabelModuleLockedWarning, gridBagConstraints);

        jButtonModuleLockInfo.setText("...");
        jButtonModuleLockInfo.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonModuleLockInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModuleLockInfoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelModuleLockWarning.add(jButtonModuleLockInfo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 9.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jPanelModuleLockWarning, gridBagConstraints);

        jPanelPartnerMain.setLayout(new java.awt.GridBagLayout());

        jSplitPane.setDividerLocation(170);

        jPanelPartnerTree.setLayout(new java.awt.GridBagLayout());

        jScrollPaneTree.setPreferredSize(new java.awt.Dimension(150, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelPartnerTree.add(jScrollPaneTree, gridBagConstraints);

        jButtonCancelFilter.setText("Filter");
        jButtonCancelFilter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCancelFilter.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonCancelFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelFilterActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanelPartnerTree.add(jButtonCancelFilter, gridBagConstraints);

        jSplitPane.setLeftComponent(jPanelPartnerTree);

        jPanelPartner.setLayout(new java.awt.BorderLayout());
        jSplitPane.setRightComponent(jPanelPartner);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelPartnerMain.add(jSplitPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jPanelPartnerMain, gridBagConstraints);

        jPanelButton.setLayout(new java.awt.GridBagLayout());

        jButtonCancel.setText(this.rb.getResourceString( "button.cancel" ));
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelButton.add(jButtonCancel, gridBagConstraints);

        jButtonPartnerConfigOk.setText(this.rb.getResourceString( "button.ok" ));
        jButtonPartnerConfigOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPartnerConfigOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelButton.add(jButtonPartnerConfigOk, gridBagConstraints);

        jLabelFilterCount.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        jLabelFilterCount.setText("0/0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelButton.add(jLabelFilterCount, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jPanelButton, gridBagConstraints);

        jPanelConfigurationWarning.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 51, 0)));
        jPanelConfigurationWarning.setLayout(new java.awt.GridBagLayout());

        jLabelConfigurationWarning.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabelConfigurationWarning.setForeground(new java.awt.Color(204, 51, 0));
        jLabelConfigurationWarning.setText(this.rb.getResourceString( "text.configurationproblem"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanelConfigurationWarning.add(jLabelConfigurationWarning, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 9.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jPanelConfigurationWarning, gridBagConstraints);

        getContentPane().add(jPanelMain, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(994, 718));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDeletePartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeletePartnerActionPerformed
        if (!this.isOperationAllowed(false)) {
            return;
        }
        this.deleteSelectedPartner();
    }//GEN-LAST:event_jButtonDeletePartnerActionPerformed

    private void jButtonNewPartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewPartnerActionPerformed
        if (!this.isOperationAllowed(false)) {
            return;
        }
        Partner partner = this.jTreePartner.createNewPartner(this.certificateManagerEncSign);
        this.fullPartnerList.add(partner);
    }//GEN-LAST:event_jButtonNewPartnerActionPerformed

    private void jTreePartnerValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreePartnerValueChanged
    }//GEN-LAST:event_jTreePartnerValueChanged

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closeDialog

    private void jButtonClonePartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClonePartnerActionPerformed
        if (!this.isOperationAllowed(false)) {
            return;
        }
        this.cloneSelectedPartner();
    }//GEN-LAST:event_jButtonClonePartnerActionPerformed

    private void jButtonPartnerConfigOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPartnerConfigOkActionPerformed
        this.okPressed();
    }//GEN-LAST:event_jButtonPartnerConfigOkActionPerformed

    private void jButtonModuleLockInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModuleLockInfoActionPerformed
        JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        ModuleLock.displayDialogModuleLocked(parent, this.lockKeeper, ModuleLock.Module.PARTNER);
    }//GEN-LAST:event_jButtonModuleLockInfoActionPerformed

    private void jButtonGlobalSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGlobalSettingsActionPerformed
        JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        JDialog dialog = new JDialogGlobalChange(parent, this.fullPartnerList);
        dialog.setVisible(true);
        this.displayPartnerValues();
    }//GEN-LAST:event_jButtonGlobalSettingsActionPerformed

    private void jButtonFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFilterActionPerformed
        if (this.filterDialog.isVisible()) {
            this.filterDialog.setVisible(false);
        } else {
            this.filterDialog.toFront();
            this.filterDialog.setVisible(true);
        }
    }//GEN-LAST:event_jButtonFilterActionPerformed

    private void jButtonCancelFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelFilterActionPerformed
        this.filterPartner(new FilterData("", ""));
    }//GEN-LAST:event_jButtonCancelFilterActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonCancelFilter;
    private javax.swing.JButton jButtonClonePartner;
    private javax.swing.JButton jButtonDeletePartner;
    private javax.swing.JButton jButtonFilter;
    private javax.swing.JButton jButtonGlobalSettings;
    private javax.swing.JButton jButtonModuleLockInfo;
    private javax.swing.JButton jButtonNewPartner;
    private de.mendelson.comm.as2.partner.gui.JButtonPartnerConfigOk jButtonPartnerConfigOk;
    private javax.swing.JLabel jLabelConfigurationWarning;
    private javax.swing.JLabel jLabelFilterCount;
    private javax.swing.JLabel jLabelModuleLockedWarning;
    private javax.swing.JPanel jPanelButton;
    private javax.swing.JPanel jPanelConfigurationWarning;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelModuleLockWarning;
    private javax.swing.JPanel jPanelPartner;
    private javax.swing.JPanel jPanelPartnerMain;
    private javax.swing.JPanel jPanelPartnerTree;
    private javax.swing.JScrollPane jScrollPaneTree;
    private javax.swing.JSplitPane jSplitPane;
    private javax.swing.JToolBar jToolBar;
    // End of variables declaration//GEN-END:variables
}
