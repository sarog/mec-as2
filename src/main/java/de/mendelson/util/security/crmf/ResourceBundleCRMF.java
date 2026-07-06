//$Header: /as4/de/mendelson/util/security/crmf/ResourceBundleCRMF.java 1     8/01/26 15:50 Heller $
package de.mendelson.util.security.crmf;

import de.mendelson.util.MecResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize gui entries
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class ResourceBundleCRMF extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }

    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"title", "CRMF Request Generation (BDEW)"},
        {"label.root.ca", "Sub CA root certificate"},
        {"label.root.ca.help", "<HTML><strong>Sub CA root certificate</strong><br><br>"
            + "The generated CRMF request is encapsulated into a CMP message structure which contains a recipient field.<br>"
            + "To ensure standard-compliant messaging, this recipient field "
            + "must contain the CAs specific Distinguished Name (DN) from the X.500 Name space. "
            + "You can find this in the \"Subject\" field of the CAs own certificate "
            + "(e.g., CN=SM-Test-PKI-DE). This ensures the generated message structure "
            + "adheres to BDEW/Smart Metering PKI standards. For a perfect match of this value "
            + "the (sub) CA root certificate is required."
            + "</HTML>"},
        {"button.ok", "Ok"},
        {"button.cancel", "Cancel"},
        {"label.key.tls", "TLS key"},
        {"label.key.encryption", "Encryption key"},
        {"label.key.signature", "Signature key"},
        {"success.title", "CRMF generation success"},
        {"success.body", "The CRMF file has been written to {0}"},
        {"password.hint", "Initial one time password"},
        {"label.initial", "Initial request"},
        {"label.initial.help", "<HTML><strong>Initial request</strong><br><br>"
            + "Use this for your first-time registration at the CA. The authentication requires your initial registration password."
            + "</HTML>"},
        {"label.update", "Update request"},
        {"label.update.help", "<HTML><strong>Update request</strong><br><br>"
            + "Use this to renew an existing certificate before it expires. The authentication is handled automatically "
            + "using your current valid certificate."
            + "</HTML>"},};

}
