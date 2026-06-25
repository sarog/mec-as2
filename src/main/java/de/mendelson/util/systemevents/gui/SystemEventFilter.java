//$Header: /mec_as2/de/mendelson/util/systemevents/gui/SystemEventFilter.java 5     15/04/26 12:44 Heller $
package de.mendelson.util.systemevents.gui;

import de.mendelson.util.systemevents.SystemEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Filter to display system events in the user interface
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class SystemEventFilter {

    private final List<SystemEvent.Origin> originList 
            = Collections.synchronizedList(new ArrayList<SystemEvent.Origin>());
    private final List<SystemEvent.Severity> severityList 
            = Collections.synchronizedList(new ArrayList<SystemEvent.Severity>());
    private SystemEvent.Category acceptedCategory = SystemEvent.Category.FILTER_ACCEPT_ALL;

    public SystemEventFilter() {
        //default: no not filter any entry
        synchronized (this.originList) {
            this.originList.add(SystemEvent.Origin.SYSTEM);
            this.originList.add(SystemEvent.Origin.TRANSACTION);
            this.originList.add(SystemEvent.Origin.USER);
        }
        synchronized (this.severityList) {
            this.severityList.add(SystemEvent.Severity.ERROR);
            this.severityList.add(SystemEvent.Severity.INFO);
            this.severityList.add(SystemEvent.Severity.WARNING);
        }
    }

    public void setAcceptedCategory(SystemEvent.Category category) {
        this.acceptedCategory = category;
    }

    public SystemEvent.Category getAcceptedCategory() {
        return (this.acceptedCategory);
    }

    public void addAcceptedOrigin(SystemEvent.Origin origin) {
        synchronized (this.originList) {
            this.originList.add(origin);
        }
    }

    public void addAcceptedSeverity(SystemEvent.Severity severity) {
        synchronized (this.severityList) {
            this.severityList.add(severity);
        }
    }

    private List<SystemEvent.Origin> getOriginList() {
        synchronized (this.originList) {
            List<SystemEvent.Origin> tempList = new ArrayList<SystemEvent.Origin>(this.originList);
            return (tempList);
        }
    }

    private List<SystemEvent.Severity> getSeverityList() {
        synchronized (this.severityList) {
            List<SystemEvent.Severity> tempList = new ArrayList<SystemEvent.Severity>(this.severityList);
            return (tempList);
        }
    }

    public void setValues(SystemEventFilter filter) {
        synchronized (this.originList) {
            this.originList.clear();
            this.originList.addAll(filter.getOriginList());
        }
        synchronized (this.severityList) {
            this.severityList.clear();
            this.severityList.addAll(filter.getSeverityList());
        }
        this.acceptedCategory = filter.getAcceptedCategory();
    }

    public void clear() {
        synchronized (this.originList) {
            this.originList.clear();
        }
        synchronized (this.severityList) {
            this.severityList.clear();
        }
        this.acceptedCategory = SystemEvent.Category.FILTER_ACCEPT_ALL;
    }

    /**
     * Accepts a system event - or not
     */
    public boolean accept(SystemEvent systemEvent) {
        boolean severityAccepted = false;
        synchronized (this.severityList) {
            severityAccepted = this.severityList.contains(systemEvent.getSeverity());
        }
        boolean originAccepted = false;
        synchronized (this.originList) {
            originAccepted = this.originList.contains(systemEvent.getOrigin());
        }
        boolean categoryAccepted = true;
        if (this.acceptedCategory != SystemEvent.Category.FILTER_ACCEPT_ALL 
                && systemEvent.getCategory() != this.acceptedCategory) {
            categoryAccepted = false;
        }
        return (severityAccepted && originAccepted && categoryAccepted);
    }

}
