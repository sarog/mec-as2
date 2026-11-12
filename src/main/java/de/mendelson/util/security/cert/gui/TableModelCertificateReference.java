//$Header: /mec_as4/de/mendelson/util/security/cert/gui/TableModelCertificateReference.java 9     14/04/26 9:05 Heller $
package de.mendelson.util.security.cert.gui;

import de.mendelson.util.MendelsonMultiResolutionImage;
import de.mendelson.util.security.cert.CertificateInUseInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Model to display all files that are open and save/close them
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class TableModelCertificateReference extends AbstractTableModel {

    protected static final int ROW_HEIGHT = JDialogCertificates.IMAGE_SIZE_TABLE + 3;
    protected static final int IMAGE_HEIGHT = JDialogCertificates.IMAGE_SIZE_TABLE;

    public static final MendelsonMultiResolutionImage IMAGE_PARTNER
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/cert/gui/singlepartner.svg",
                    IMAGE_HEIGHT);
    public static final MendelsonMultiResolutionImage IMAGE_PARTNER_GATEWAY
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/cert/gui/singlepartner_gateway.svg",
                    IMAGE_HEIGHT);
    public static final MendelsonMultiResolutionImage IMAGE_PARTNER_ROUTED
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/cert/gui/singlepartner_routed.svg",
                    IMAGE_HEIGHT);
    public static final MendelsonMultiResolutionImage IMAGE_PARTNER_LOCALSTATION
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/cert/gui/localstation.svg",
                    IMAGE_HEIGHT);
    public static final MendelsonMultiResolutionImage IMAGE_PARTNER_LOCALSTATION_VIRTUAL
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/util/security/cert/gui/localidentity.svg",
                    IMAGE_HEIGHT);

    private final List<CertificateInUseInfo.SingleCertificateInUseInfo> useList = Collections.synchronizedList(new ArrayList<CertificateInUseInfo.SingleCertificateInUseInfo>());

    public TableModelCertificateReference() {
    }

    public void passNewData(List<CertificateInUseInfo.SingleCertificateInUseInfo> useInfoList) {
        synchronized (this.useList) {
            this.useList.clear();
            this.useList.addAll(useInfoList);
        }
        ((AbstractTableModel) this).fireTableDataChanged();
    }

    /**
     * Number of rows to display
     */
    @Override
    public int getRowCount() {
        synchronized (this.useList) {
            return (this.useList.size());
        }
    }

    /**
     * Number of cols to display
     */
    @Override
    public int getColumnCount() {
        return (3);
    }

    /**
     * Returns a value at a specific position in the grid
     */
    @Override
    public Object getValueAt(int row, int col) {
        CertificateInUseInfo.SingleCertificateInUseInfo info = null;
        synchronized (this.useList) {
            info = this.useList.get(row);
        }
        if (col == 0) {
            CertificateInUseInfo.UsedByPartner type = info.getUsedBy();
            if (type == CertificateInUseInfo.UsedByPartner.REMOTE) {
                return (new ImageIcon(IMAGE_PARTNER.toMinResolution(IMAGE_HEIGHT)));
            } else if (type == CertificateInUseInfo.UsedByPartner.GATEWAY) {
                return (new ImageIcon(IMAGE_PARTNER_GATEWAY.toMinResolution(IMAGE_HEIGHT)));
            } else if (type == CertificateInUseInfo.UsedByPartner.ROUTED) {
                return (new ImageIcon(IMAGE_PARTNER_ROUTED.toMinResolution(IMAGE_HEIGHT)));
            } else if (type == CertificateInUseInfo.UsedByPartner.LOCALSTATION) {
                return (new ImageIcon(IMAGE_PARTNER_LOCALSTATION.toMinResolution(IMAGE_HEIGHT)));
            } else if (type == CertificateInUseInfo.UsedByPartner.LOCALSTATION_VIRTUAL) {
                return (new ImageIcon(IMAGE_PARTNER_LOCALSTATION_VIRTUAL.toMinResolution(IMAGE_HEIGHT)));
            }
            return (null);
        }
        if (col == 1) {
            return (info.getPartnerName());
        }
        return (info.getDetails());
    }

    /**
     * Returns the name of every column
     *
     * @param col Column to get the header name of
     */
    @Override
    public String getColumnName(int col) {
        return (new String[]{
            "", "", ""
        }[col]);
    }

    /**
     * Set how to display the grid elements
     *
     * @param col requested column
     */
    @Override
    public Class getColumnClass(int col) {
        return (new Class[]{
            ImageIcon.class,
            String.class,
            String.class,}[col]);
    }

    /**
     * Swing GUI checks which cols are editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return (false);
    }

}
