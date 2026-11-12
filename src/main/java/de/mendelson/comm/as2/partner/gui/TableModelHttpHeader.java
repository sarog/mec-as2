//$Header: /as2/de/mendelson/comm/as2/partner/gui/TableModelHttpHeader.java 8     17/06/25 17:54 Heller $
package de.mendelson.comm.as2.partner.gui;

import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerHttpHeader;
import de.mendelson.util.MecResourceBundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Table model to display the properties to set
 *
 * @author S.Heller
 * @version $Revision: 8 $
 */
public class TableModelHttpHeader extends AbstractTableModel {

    private final List<PartnerHttpHeader> headerList = Collections.synchronizedList(new ArrayList<PartnerHttpHeader>());
    private static final MecResourceBundle rb;
    static{
        //load resource bundle
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundlePartnerPanel.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }
    private Partner partner = null;

    /**
     * Creates new preferences table model
     *
     */
    public TableModelHttpHeader() {
        
    }

    /**
     * Passes data to the model and fires a table data update
     *
     */
    public void passNewData(Partner partner) {
        this.partner = partner;
        synchronized (this.headerList) {
            headerList.clear();
            headerList.addAll(partner.getHttpHeader());
        }
        ((AbstractTableModel) this).fireTableDataChanged();
    }

    /**
     * Passes a new single value to the array
     *
     * @param header new header to add to the partner
     */
    public void addRow(PartnerHttpHeader header) {
        synchronized (this.headerList) {
            this.headerList.add(header);
            this.partner.addHttpHeader(header);
        }
        ((AbstractTableModel) this).fireTableDataChanged();
    }

    /**
     * Passes a new single value to the array
     *
     */
    public void deleteRow(int row) {
        synchronized (this.headerList) {
            this.headerList.remove(row);
            this.partner.setHttpHeader(this.headerList);
        }
        ((AbstractTableModel) this).fireTableDataChanged();
    }

    /**
     * return one value defined by row and column
     *
     * @param row row that contains value
     * @param col column that contains value
     */
    @Override
    public Object getValueAt(int row, int col) {
        PartnerHttpHeader header;
        synchronized (this.headerList) {
            header = this.headerList.get(row);
        }
        //preferences name
        if (col == 0) {
            return (header.getKey());
        }
        //assigned value
        if (col == 1) {
            return (header.getValue());
        }
        return (null);
    }

    /**
     * returns the number of rows in the table
     */
    @Override
    public int getRowCount() {
        synchronized (this.headerList) {
            return this.headerList.size();
        }
    }

    /**
     * returns the number of columns in the table. should be const for a table
     */
    @Override
    public int getColumnCount() {
        return (2);
    }

    /**
     * Returns the name of every column
     *
     * @param col Column to get the header name of
     */
    @Override
    public String getColumnName(int col) {

        switch (col) {
            case 0:
                return rb.getResourceString("header.httpheaderkey");
            case 1:
                return rb.getResourceString("header.httpheadervalue");
            default:
                return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (true);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        String value = (String) aValue;
        synchronized (this.headerList) {
            if (columnIndex == 0) {
                this.headerList.get(rowIndex).setKey(value);
            } else {
                this.headerList.get(rowIndex).setValue(value);
            }
            this.partner.setHttpHeader(this.headerList);
        }
    }
}
