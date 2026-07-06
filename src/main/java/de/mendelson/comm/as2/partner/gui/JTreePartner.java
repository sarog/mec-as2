//$Header: /mec_as2/de/mendelson/comm/as2/partner/gui/JTreePartner.java 42    15/04/26 12:43 Heller $
package de.mendelson.comm.as2.partner.gui;

import de.mendelson.comm.as2.client.AS2Gui;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerCertificateInformation;
import de.mendelson.util.tree.SortableTreeNode;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Tree to display the AS2 partner
 *
 * @author S.Heller
 * @version $Revision: 42 $
 */
public class JTreePartner extends JTree {

    public static final int ICON_HEIGHT = AS2Gui.IMAGE_SIZE_TREENODE;
    private final List<Partner> fullPartnerList = Collections.synchronizedList(new ArrayList<Partner>());

    /**
     * Holds a new partner ID for every created partner that is always negativ
     * but unique in this lifecycle
     */
    private int uniquePartnerIdCounter = -1;
    /**
     * This is the root node
     */
    private final SortableTreeNode root;

    /**
     * Tree constructor
     */
    public JTreePartner() {
        super(new SortableTreeNode());
        this.setRootVisible(false);
        this.root = (SortableTreeNode) this.getModel().getRoot();
        this.setCellRenderer(new TreeCellRendererPartner());
        //adjust font and row height, depending on icon size
        this.setRowHeight(ICON_HEIGHT + 1);
        //the standard ratio from row hight to font size is 11/16 which is 0.68
        Font treeFont = this.getFont().deriveFont((float) (0.68f * ICON_HEIGHT));
        this.setFont(treeFont);
    }

    /**
     * Returns the partner that is the local station or null if none exists
     */
    public List<Partner> getLocalStations() {
        List<Partner> localStationList = new ArrayList<Partner>();
        synchronized (this.fullPartnerList) {
            for (Partner partner : this.fullPartnerList) {
                if (partner.isLocalStation()) {
                    localStationList.add(partner);
                }
            }
            return (localStationList);
        }
    }

    /**
     * Returns that a local station is set in the tree. The configuration should
     * always work, it should be impossible to create a configuration without
     * local station
     */
    public boolean localStationIsSet() {
        List<Partner> localStations = this.getLocalStations();
        return (localStations != null && !localStations.isEmpty());
    }

    /**
     * Selects a special partner in the tree
     */
    public void setSelectedPartner(Partner partner) {
        synchronized (this.getModel()) {
            for (int i = 0; i < this.root.getChildCount(); i++) {
                SortableTreeNode child = (SortableTreeNode) root.getChildAt(i);
                Partner foundPartner = (Partner) child.getUserObject();
                if (foundPartner.equals(partner)) {
                    this.expandPath(new TreePath(child.getPath()));
                    this.fireTreeExpanded(new TreePath(child.getPath()));
                    this.setSelectionPath(new TreePath(child.getPath()));
                    break;
                }
            }
        }
    }

    /**
     * Sets a partner as local station in the tree
     */
    public void setToLocalStation(Partner newLocalStation) {
        synchronized (this.getModel()) {
            for (int i = 0; i < this.root.getChildCount(); i++) {
                SortableTreeNode child = (SortableTreeNode) root.getChildAt(i);
                Partner partner = (Partner) child.getUserObject();
                if (partner.equals(newLocalStation)) {
                    partner.setLocalStation(partner.equals(newLocalStation));
                    this.getModel().valueForPathChanged(new TreePath(child.getPath()), partner);
                    break;
                }
            }
        }
    }

    /**
     * Builds up the tree with a given list of partners
     */
    public void buildTree(List<Partner> newFullPartnerList, List<Partner> filteredPartnerList) throws Exception {
        SortableTreeNode firstNodePartner = null;
        synchronized (this.fullPartnerList) {
            this.fullPartnerList.clear();
            this.fullPartnerList.addAll(newFullPartnerList);
        }
        synchronized (this.getModel()) {
            this.root.removeAllChildren();
            SortableTreeNode nodePartner = null;
            for (Partner partner : filteredPartnerList) {
                nodePartner = new SortableTreeNode(partner);
                this.root.add(nodePartner);
                if (firstNodePartner == null) {
                    firstNodePartner = nodePartner;
                }
            }
            if (nodePartner != null) {
                this.expandPath(new TreePath(nodePartner.getPath()));
                this.fireTreeExpanded(new TreePath(nodePartner.getPath()));

            }
        }
        SortableTreeNode firstNodePartnerFinal = firstNodePartner;
        ((DefaultTreeModel) this.getModel()).nodeStructureChanged(this.root);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (firstNodePartnerFinal != null) {
                    setSelectionPath(new TreePath(firstNodePartnerFinal.getPath()));
                }
            }
        });
    }

    /**
     * Returns a specified partner from the tree
     */
    public Partner getDisplayedPartnerByName(String name) {
        synchronized (this.getModel()) {
            for (int i = 0; i < this.root.getChildCount(); i++) {
                SortableTreeNode child = (SortableTreeNode) root.getChildAt(i);
                Partner partner = (Partner) child.getUserObject();
                if (partner.getName().equals(name)) {
                    return (partner);
                }
            }
        }
        return (null);
    }

    /**
     * Creates a new partner, adds it to the tree and selects it
     */
    public Partner createNewPartner(CertificateManager certificateManagerEncSign) {
        Partner partner = new Partner();
        partner.setDBId(this.uniquePartnerIdCounter);
        this.uniquePartnerIdCounter--;
        partner.setName("Partner");
        partner.setAS2Identification("AS2Ident");
        partner.setURL(Partner.DEFAULT_URL);
        partner.setMdnURL(Partner.DEFAULT_URL);
        partner.setLocalStation(false);
        List<KeystoreCertificate> list = certificateManagerEncSign.getKeyStoreCertificateList();
        if (!list.isEmpty()) {
            KeystoreCertificate certificate = list.get(0);
            //just take the first entry
            PartnerCertificateInformation signInfo = new PartnerCertificateInformation(
                    certificate.getFingerPrintSHA1(),
                    PartnerCertificateInformation.Category.SIGN);
            partner.setCertificateInformation(signInfo);
            PartnerCertificateInformation cryptInfo = new PartnerCertificateInformation(
                    certificate.getFingerPrintSHA1(),
                    PartnerCertificateInformation.Category.CRYPT);
            partner.setCertificateInformation(cryptInfo);
        }
        this.addPartner(partner);
        return (partner);
    }

    public void addPartner(Partner partner) {
        synchronized (this.getModel()) {
            synchronized (this.fullPartnerList) {
                this.fullPartnerList.add(partner);
            }
            SortableTreeNode node = new SortableTreeNode(partner);
            this.root.add(node);
            ((DefaultTreeModel) this.getModel()).nodeStructureChanged(this.root);
            this.setSelectionPath(new TreePath(node.getPath()));
        }
    }

    /**
     * Returns the selected partner or null if no partner is selected
     */
    public Partner getSelectedPartner() {
        SortableTreeNode selectedNode = this.getSelectedNode();
        if (selectedNode == null) {
            return (null);
        }
        Partner partner = (Partner) selectedNode.getUserObject();
        return (partner);
    }

    /**
     * Tries to delete the selected partner, will return null on any problem
     */
    public Partner deleteSelectedPartner() {
        synchronized (this.getModel()) {
            //do not delete the last child
            if (this.root.getChildCount() == 1) {
                return (null);
            }
            SortableTreeNode selectedNode = this.getSelectedNode();
            if (selectedNode == null) {
                return (null);
            }
            Partner partnerToDelete = (Partner) selectedNode.getUserObject();
            int index = this.root.getIndex(selectedNode);
            this.root.remove(selectedNode);
            if (index > 0) {
                index--;
            }
            ((DefaultTreeModel) this.getModel()).nodeStructureChanged(this.root);
            SortableTreeNode newSelectedNode = (SortableTreeNode) root.getChildAt(index);
            this.setSelectionPath(new TreePath(newSelectedNode.getPath()));
            synchronized (this.fullPartnerList) {
                this.fullPartnerList.remove(partnerToDelete);
            }
            return (partnerToDelete);
        }
    }

    /**
     * Returns the selected node of the Tree
     */
    public SortableTreeNode getSelectedNode() {
        synchronized (this.getModel()) {
            TreePath path = this.getSelectionPath();
            if (path != null) {
                return ((SortableTreeNode) path.getLastPathComponent());
            }
        }
        return (null);
    }

    /**
     * Returns a list of all partners
     */
    public List<Partner> getAllPartner() {
        synchronized (this.fullPartnerList) {
            return (Collections.unmodifiableList(this.fullPartnerList));
        }
    }

    public void partnerChanged(Partner partner) {
        synchronized (this.getModel()) {
            for (int i = 0; i < this.root.getChildCount(); i++) {
                SortableTreeNode child = (SortableTreeNode) root.getChildAt(i);
                Partner foundPartner = (Partner) child.getUserObject();
                if (foundPartner.equals(partner)) {
                    ((DefaultTreeModel) this.getModel()).nodeChanged(child);
                    break;
                }
            }
        }
    }
}
