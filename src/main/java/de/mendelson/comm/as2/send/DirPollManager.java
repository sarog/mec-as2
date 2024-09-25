//$Header: /as2/de/mendelson/comm/as2/send/DirPollManager.java 43    9/10/15 10:28a Heller $
package de.mendelson.comm.as2.send;

import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerAccessDB;
import de.mendelson.comm.as2.server.AS2Server;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.ClientServer;
import de.mendelson.util.security.cert.CertificateManager;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */

/**
 * Manager that polls the outbox directories of the partners, creates messages
 * and sends them
 *
 * @author S.Heller
 * @version $Revision: 43 $
 */
public class DirPollManager {

    private Logger logger = Logger.getLogger(AS2Server.SERVER_LOGGER_NAME);
    private CertificateManager certificateManager;
    /**
     * Stores all poll threads key: partner DB id, value: pollThread
     */
    private final Map<String, DirPollThread> mapPollThread = Collections.synchronizedMap(new HashMap<String, DirPollThread>());
    /**
     * Localize the GUI
     */
    private MecResourceBundle rb = null;
    //DB connection
    private Connection configConnection;
    private Connection runtimeConnection;
    private ClientServer clientserver;

    public DirPollManager(CertificateManager certificateManager, Connection configConnection, Connection runtimeConnection,
            ClientServer clientserver) {
        this.configConnection = configConnection;
        this.runtimeConnection = runtimeConnection;
        this.clientserver = clientserver;
        //Load default resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleDirPollManager.class.getName());
        } //load up resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
        this.certificateManager = certificateManager;
        this.logger.info(this.rb.getResourceString("manager.started"));
    }

    /**
     * Indicates that the partner configuration has been changed: This should
     * stop now unused tasks and start other
     */
    public void partnerConfigurationChanged() {
        PartnerAccessDB access = new PartnerAccessDB(this.configConnection, this.runtimeConnection);
        List<Partner> partner = access.getPartner();
        List<Partner> localStations = access.getLocalStations();
        if (partner == null) {
            this.logger.severe("partnerConfigurationChanged: Unable to load partner");
            return;
        }
        synchronized (this.mapPollThread) {
            for (Partner sender : localStations) {
                for (Partner receiver : partner) {
                    String id = sender.getDBId() + "_" + receiver.getDBId();
                    //add partner task if it does not exist so far
                    if (!this.mapPollThread.containsKey(id) && !receiver.isLocalStation()) {
                        this.addPartnerPollThread(sender, receiver);
                    } else if (this.mapPollThread.containsKey(id)) {
                        DirPollThread thread = (DirPollThread) this.mapPollThread.get(id);
                        if (!receiver.isLocalStation()) {
                            if (thread.hasBeenModified(sender, receiver)) {
                                thread.requestStop();
                                this.mapPollThread.remove(id);
                                //restart the poll thread with the new values
                                this.addPartnerPollThread(sender, receiver);
                            }
                        } else {
                            //its a local station now: stop the task and remove it
                            thread.requestStop();
                            this.mapPollThread.remove(id);
                        }
                    }
                }
            }
            //still running task that is not in the configuration any more: stop and remove
            List<String> idList = new ArrayList<String>();
            Iterator iterator = this.mapPollThread.keySet().iterator();
            while (iterator.hasNext()) {
                idList.add((String) iterator.next());
            }
            for (String id : idList) {
                boolean idFound = false;
                for (Partner sender : localStations) {
                    for (Partner receiver : partner) {
                        String relationShipId = sender.getDBId() + "_" + receiver.getDBId();
                        if (id.equals(relationShipId)) {
                            idFound = true;
                        }
                    }
                }
                //old still running taks, has been deleted in the config: stop and remove
                if (!idFound) {
                    DirPollThread thread = this.mapPollThread.get(id);
                    thread.requestStop();
                    this.mapPollThread.remove(id);
                }
            }
        }
    }

    /**
     * Adds a new partner to the poll thread list
     *
     */
    private void addPartnerPollThread(Partner localStation, Partner partner) {
        DirPollThread thread = new DirPollThread(this.configConnection, this.runtimeConnection, this.clientserver, this.certificateManager,
                localStation, partner);
        synchronized (this.mapPollThread) {
            this.mapPollThread.put(localStation.getDBId() + "_" + partner.getDBId(), thread);
            Executors.newSingleThreadExecutor().submit(thread);            
        }
    }

}
