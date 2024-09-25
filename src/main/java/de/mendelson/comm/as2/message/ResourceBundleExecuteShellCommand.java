//$Header: /as2/de/mendelson/comm/as2/message/ResourceBundleExecuteShellCommand.java 2     11.11.08 16:05 Heller $
package de.mendelson.comm.as2.message;
import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class ResourceBundleExecuteShellCommand extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"executing.receipt", "{0}: Executing shell command after receipt for payload." },
        {"executing.send", "{0}: Executing shell command after send for payload." },
        {"executing.command", "{0}: Shell command: \"{1}\"." },
        {"executed.command", "{0}: Shell command executed, returncode={1}." },
    };
    
}