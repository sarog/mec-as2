//$Header: /oftp2/de/mendelson/util/security/cert/gui/JTreeTrustChain.java 16    16/01/26 13:51 Heller $
package de.mendelson.util.security.cert.gui;

import de.mendelson.util.security.KeyStoreUtil;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.util.tree.SortableTreeNode;
import de.mendelson.util.uinotification.UINotification;
import java.security.cert.CertPath;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Tree to display the trust chain of a certificate
 *
 * @author S.Heller
 * @version $Revision: 16 $
 */
public class JTreeTrustChain extends JTree {

    /**
     * This is the root node
     */
    private final SortableTreeNode root;

    /**
     * Tree constructor
     */
    public JTreeTrustChain() {
        super(new SortableTreeNode());
        this.setRootVisible(true);
        this.root = (SortableTreeNode) this.getModel().getRoot();
        this.setCellRenderer(new TreeCellRendererTrustChain());
        this.setRowHeight(TreeCellRendererTrustChain.ROW_HEIGHT);
        this.setBorder(new EmptyBorder(2, 2, 2, 2));
        //prevent a collapse of this tree
        this.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {

            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
                throw new ExpandVetoException(event, "Collapsing trust chain tree is not allowed");
            }
        });
    }

    /**
     * Builds up the tree
     */
    public void buildTree(final CertificateManager manager, final String alias) {
        SwingWorker<Void, List<KeystoreCertificate>> worker = new SwingWorker<Void, List<KeystoreCertificate>>() {
            @Override
            protected Void doInBackground() throws Exception {
                //heavy cryptographic operations and path building in the background
                List<KeystoreCertificate> trustChain = computeTrustChain(manager, alias);
                publish(trustChain);
                return (null);
            }

            @Override
            protected void process(List<List<KeystoreCertificate>> chunks) {
                // SwingWorker can bundle multiple publish calls. 
                // We only care about the latest one.
                List<KeystoreCertificate> trustChain = chunks.get(chunks.size() - 1);
                if (trustChain != null && !trustChain.isEmpty()) {
                    try {
                        root.removeAllChildren();
                        DefaultTreeModel treeModel = (DefaultTreeModel) getModel();
                        treeModel.nodeStructureChanged(root);
                        SortableTreeNode parent;
                        KeystoreCertificate firstCert = trustChain.get(0);
                        // check if first cert is untrusted
                        if (!firstCert.getIssuerX500Principal().equals(firstCert.getSubjectX500Principal())) {
                            // there is a missing certificate above
                            StringBuilder text = new StringBuilder();
                            if (firstCert.getIssuerCN() != null) {
                                text.append(firstCert.getIssuerCN());
                            }
                            String organization = firstCert.getIssuerOrganization();
                            if (organization != null) {
                                text.append(" [").append(organization).append("]");
                            }
                            if (text.length() == 0) {
                                text.append("--");
                            }
                            root.setUserObject(text.toString());
                            SortableTreeNode child = new SortableTreeNode(firstCert);
                            root.add(child);
                            treeModel.nodeStructureChanged(root);
                            parent = child;
                        } else {
                            root.setUserObject(trustChain.get(0));
                            parent = root;
                        }
                        for (int i = 1; i < trustChain.size(); i++) {
                            SortableTreeNode child = new SortableTreeNode(trustChain.get(i));
                            parent.add(child);
                            treeModel.nodeStructureChanged(parent);
                            parent = child;
                        }
                        treeModel.nodeStructureChanged(parent);
                        expandPath(new TreePath(parent.getPath()));
                        setSelectionPath(new TreePath(parent.getPath()));
                    } catch (Exception e) {
                        // handle errors from background thread
                        UINotification.instance().addNotification(e);
                    }
                }
            }
        };
        worker.execute();
    }

    public void buildTree_OLD(CertificateManager manager, String alias) {
        List<KeystoreCertificate> trustChain = this.computeTrustChain(manager, alias);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                root.removeAllChildren();
                ((DefaultTreeModel) getModel()).nodeStructureChanged(root);
                //check if first cert is untrusted
                SortableTreeNode parent;
                KeystoreCertificate firstCert = trustChain.get(0);
                if (!firstCert.getIssuerX500Principal().equals(firstCert.getSubjectX500Principal())) {
                    //there is a missing certificate above - means the first certificate of the chain is not 
                    //the root of the trust chain
                    StringBuilder text = new StringBuilder();
                    if (firstCert.getIssuerCN() != null) {
                        text.append(firstCert.getIssuerCN());
                    }
                    String organization = firstCert.getIssuerOrganization();
                    if (organization != null) {
                        text.append(" [")
                                .append(organization)
                                .append("]");
                    }
                    if (text.length() == 0) {
                        text.append("--");
                    }
                    root.setUserObject(text.toString());
                    SortableTreeNode child = new SortableTreeNode(firstCert);
                    root.add(child);
                    ((DefaultTreeModel) getModel()).nodeStructureChanged(root);
                    parent = child;
                } else {
                    root.setUserObject(trustChain.get(0));
                    parent = root;
                }
                for (int i = 1; i < trustChain.size(); i++) {
                    SortableTreeNode child = new SortableTreeNode(trustChain.get(i));
                    parent.add(child);
                    ((DefaultTreeModel) getModel()).nodeStructureChanged(parent);
                    parent = child;
                }
                ((DefaultTreeModel) getModel()).nodeStructureChanged(parent);
                expandPath(new TreePath(parent.getPath()));
                setSelectionPath(new TreePath(parent.getPath()));
            }
        });
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
            return (null);
        }
    }

    /**
     * Compute the whole trust chain (e.g. for pkcs#7 export)
     */
    protected List<KeystoreCertificate> computeTrustChain(CertificateManager manager, String alias) {
        List<KeystoreCertificate> list = new ArrayList<KeystoreCertificate>();
        try {
            KeystoreCertificate certificate = manager.getKeystoreCertificate(alias);
            Set<TrustAnchor> trustAnchors = KeyStoreUtil.getTrustAnchors(manager.getKeystore());
            PKIXCertPathBuilderResult result = certificate.getPKIXCertPathBuilderResult(trustAnchors,
                    manager.getX509CertificateList());
            //self signed?
            if (result == null) {
                //it's a self signed certificate: return it without any CA/intermediate certs
                list.add(certificate);
            } else {
                //trusted cert
                CertPath certPath = result.getCertPath();
                for (Object cert : certPath.getCertificates()) {
                    X509Certificate workingCert = (X509Certificate) cert;
                    for (KeystoreCertificate availableKeystoreCert : manager.getKeyStoreCertificateList()) {
                        if (workingCert.equals(availableKeystoreCert.getX509Certificate())) {
                            list.add(0, availableKeystoreCert);
                        }
                    }
                }
                X509Certificate anchorCertificateX509 = null;
                boolean trustChainComplete = false;
                if (list.isEmpty()) {
                    anchorCertificateX509 = result.getTrustAnchor().getTrustedCert();
                    KeystoreCertificate anchorKeystoreCertificate = new KeystoreCertificate();
                    anchorKeystoreCertificate.setCertificate(anchorCertificateX509, null);
                    list.add(anchorKeystoreCertificate);
                    trustChainComplete = true;
                } else {
                    anchorCertificateX509 = list.get(0).getX509Certificate();
                }
                while (!trustChainComplete) {
                    KeystoreCertificate keyCertAnchor = null;
                    //find out the keystore cert of the anchor
                    for (KeystoreCertificate keyCert : manager.getKeyStoreCertificateList()) {
                        if (keyCert.getX509Certificate().equals(anchorCertificateX509)) {
                            keyCertAnchor = keyCert;
                            break;
                        }
                    }
                    if (keyCertAnchor != null) {
                        //check if the anchor has another anchor as intermediates certificate may have the attribute "CA:true", too
                        result = keyCertAnchor.getPKIXCertPathBuilderResult(trustAnchors,
                                manager.getX509CertificateList());
                        if (result != null) {
                            anchorCertificateX509 = result.getTrustAnchor().getTrustedCert();
                            if (!keyCertAnchor.getX509Certificate().equals(anchorCertificateX509)) {
                                for (KeystoreCertificate availableKeystoreCert : manager.getKeyStoreCertificateList()) {
                                    if (anchorCertificateX509.equals(availableKeystoreCert.getX509Certificate())) {
                                        list.add(0, availableKeystoreCert);
                                    }
                                }
                            } else {
                                trustChainComplete = true;
                            }
                        } else {
                            trustChainComplete = true;
                        }
                    } else {
                        trustChainComplete = true;
                    }
                }
                //if a certificate is imported two times into the keystore it will occure two or more times in a row in this list and
                //this will confuse the cert path display
                // - the following code will remove the certificates if they are two times in a row in the list
                KeystoreCertificate selectedCertificate = null;
                for (KeystoreCertificate cert : list) {
                    if (cert.getAlias().equals(alias) && cert.getFingerPrintSHA1().equals(list.get(list.size() - 1).getFingerPrintSHA1())) {
                        selectedCertificate = cert;
                    }
                }
                KeystoreCertificate lastCheckedCert = null;
                boolean repeatLoop = true;
                while (repeatLoop && list.size() > 1) {
                    int deleteIndex = -1;
                    for (int i = 0; i < list.size(); i++) {
                        KeystoreCertificate singleCert = list.get(i);
                        if (lastCheckedCert == null) {
                            lastCheckedCert = singleCert;
                        } else {
                            if (lastCheckedCert.getFingerPrintSHA1().equals(singleCert.getFingerPrintSHA1())) {
                                deleteIndex = i;
                                lastCheckedCert = null;
                                break;
                            }
                            lastCheckedCert = singleCert;
                        }
                    }
                    if (deleteIndex != -1) {
                        list.remove(deleteIndex);
                    } else {
                        repeatLoop = false;
                    }
                }
                //ensure that the selectedCertificate is always the last one in the path - it might be the same cert with an other
                //alias, too after this delete algorithm
                if (selectedCertificate != null) {
                    list.remove(list.size() - 1);
                    list.add(selectedCertificate);
                }
            }
        } catch (Exception e) {
            UINotification.instance().addNotification(e);
        }
        return (list);
    }

}
