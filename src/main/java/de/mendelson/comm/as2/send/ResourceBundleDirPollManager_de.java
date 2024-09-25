//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleDirPollManager_de.java 13    9/10/15 10:28a Heller $
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
public class ResourceBundleDirPollManager_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return contents;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] contents = {
        {"manager.started", "Manager zur Verzeichnisüberwachung gestartet." },
        {"poll.stopped", "Verzeichnisüberwachung für die Beziehung \"{0}/{1}\" wurde gestoppt." },
        {"poll.started", "Verzeichnisüberwachung für die Beziehung \"{0}/{1}\" wurde gestartet. Ignoriere: \"{2}\". Intervall: {3}s" },
        {"poll.modified", "Directory poll manager: Partner settings for the relationship \"{0}/{1}\" have been modified." },
        {"warning.ro", "Ausgangsdatei {0} ist schreibgeschützt, Datei wird ignoriert." },
        {"warning.notcomplete", "Ausgangsdatei {0} ist noch nicht vollständig vorhanden, Datei wird ignoriert." },
        {"messagefile.deleted", "{0}: Die Datei \"{1}\" wurde gelöscht und der Verarbeitungswarteschlange des Servers übergeben." },
        {"processing.file", "Verarbeite die Datei \"{0}\" für die Beziehung \"{1}/{2}\"." }, 
        {"processing.file.error", "Verarbeitungsfehler der Datei \"{0}\" für die Beziehung \"{1}/{2}\": \"{3}\"." },
        {"poll.log.wait", "[Verzeichnisüberwachung] {0}->{1}: Nächster Pollprozess in {2}s ({3})" },
        {"poll.log.polling", "[Verzeichnisüberwachung] {0}->{1}: Polle Verzeichnis \"{2}\""}
    };
    
}