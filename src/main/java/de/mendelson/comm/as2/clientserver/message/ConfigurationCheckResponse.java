//$Header: /as2/de/mendelson/comm/as2/clientserver/message/ConfigurationCheckResponse.java 4     2/11/23 15:52 Heller $
package de.mendelson.comm.as2.clientserver.message;

import de.mendelson.comm.as2.configurationcheck.ConfigurationIssue;
import de.mendelson.util.clientserver.messages.ClientServerResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Msg for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class ConfigurationCheckResponse extends ClientServerResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private final List<ConfigurationIssue> issueList = new ArrayList<ConfigurationIssue>();

    public ConfigurationCheckResponse(ConfigurationCheckRequest request) {
        super(request);
    }

    @Override
    public String toString() {
        return ("Configuration check response");
    }

    public void addIsse(ConfigurationIssue issue) {
        this.issueList.add(issue);
    }

    public List<ConfigurationIssue> getIssues() {
        return (this.issueList);
    }

}
