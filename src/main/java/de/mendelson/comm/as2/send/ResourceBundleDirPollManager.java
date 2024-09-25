//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleDirPollManager.java 13    9/10/15 10:28a Heller $
package de.mendelson.comm.as2.send;
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
 * @version $Revision: 13 $
 */
public class ResourceBundleDirPollManager extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"manager.started", "Directory poll manager started." },
        {"poll.stopped", "Directory poll manager: Poll for relationship \"{0}/{1}\" stopped." },
        {"poll.started", "Directory poll manager: Poll for relationship \"{0}/{1}\" started. Ignore files: \"{2}\". Poll interval: {3}s" },
        {"poll.modified", "Directory poll manager: Partner settings for the relationship \"{0}/{1}\" have been modified." },
        {"warning.ro", "Outbox file {0} is read-only, ignoring." },
        {"warning.notcomplete", "Outbox file {0} is not complete so far, ignoring." },
        {"messagefile.deleted", "{0}: The file \"{1}\" has been deleted and enqueued into the processing message queue of the server." },
        {"processing.file", "Processing the file \"{0}\" for the relationship \"{1}/{2}\"." },
        {"processing.file.error", "Error processing the file \"{0}\" for the relationship  \"{1}/{2}\": \"{3}\"." },
        {"poll.log.wait", "[Outbound directory poll] {0}->{1}: Next outbound poll process in {2}s ({3})" },
        {"poll.log.polling", "[Outbound directory poll] {0}->{1}: Polling directory \"{2}\""}
    };
    
}