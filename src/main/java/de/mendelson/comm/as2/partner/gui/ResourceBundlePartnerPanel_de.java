//$Header: /as2/de/mendelson/comm/as2/partner/gui/ResourceBundlePartnerPanel_de.java 74    19/01/23 10:00 Heller $
package de.mendelson.comm.as2.partner.gui;
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
 * @version $Revision: 74 $
 */
public class ResourceBundlePartnerPanel_de extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "Partnerkonfiguration" },
        {"label.name", "Name" },
        {"label.name.help", "<HTML><strong>Name</strong><br><br>"
            + "Dies ist der interne Name des Partners, wie er im System verwendet wird. Es handelt sich nicht um einen "
            + "protokollspezifischen Wert, sondern er wird f�r den Aufbau von Dateinamen oder Verzeichnisstrukturen "
            + "verwendet, die sich auf diesen Partner beziehen."
            + "</HTML>"},
        {"label.name.hint", "Interner Partnernname"},
        {"label.id", "AS2 id" },
        {"label.id.help", "<HTML><strong>AS2 id</strong><br><br>"
            + "Die (in Ihrem Partnernetzwerk) eindeutige Kennung, die im AS2 Protokoll zur Identifizierung dieses Partners verwendet wird. Sie k�nnen diese frei w�hlen - stellen Sie nur sicher, dass sie eindeutig ist."
            + "</HTML>"},
        {"label.id.hint", "Partneridentifikation (AS2 Protokoll)"},
        {"label.partnercomment", "Kommentar" },
        {"label.url", "Empfangs-URL" },
        {"label.url.help", "<HTML><strong>Empfangs-URL</strong><br><br>"
            + "Dies ist die URL Ihres Partners, �ber die sein AS2 System erreichbar ist.<br>"
            + "Bitte geben Sie diese URL im Format <strong>PROTOKOLL://HOST:PORT/PFAD</strong> an, wobei das <strong>PROTOKOLL</strong> eines von \"http\" oder \"https\" sein mu�. <strong>HOST</strong> bezeichnet den AS2 Server Host Ihres Partners. <strong>PORT</strong> ist der Empfangsport Ihres Partners. Wird er nicht angegeben, wird der Wert \"80\" gesetzt. <strong>PFAD</strong> bezeichnet den Empfangspfad, zum Beispiel \"/as2/HttpReceiver\".</HTML>"},
        {"label.mdnurl", "MDN URL" },
        {"label.mdnurl.help", "<HTML><strong>MDN</strong> (<strong>M</strong>essage <strong>D</strong>elivery <strong>N</strong>otification) <strong>URL</strong><br><br>"
            + "Dies ist die URL, die Ihr Partner f�r die eingehende asynchrone MDN zu dieser lokalen Station verwenden wird. Im synchronen Fall "
            + "wird dieser Wert nicht verwendet, da die MDN dann auf dem R�ckkanal der ausgehenden Verbindung geschickt wird.<br>"
            + "Bitte geben Sie diese URL im Format <strong>PROTOKOLL://HOST:PORT/PFAD</strong> an.<br><strong>PROTOKOLL</strong> "
            + "mu� eines von \"http\" oder \"https\" sein.<br><strong>HOST</strong> bezeichnet Ihren eigenen AS2 Server Host.<br>"
            + "<strong>PORT</strong> ist der Empfangsport Ihres AS2 Systems. Wird er nicht angegeben, wird der Wert \"80\" gesetzt.<br>"
            + "<strong>PFAD</strong> bezeichnet den Empfangspfad, zum Beispiel \"/as2/HttpReceiver\".</HTML>"},
        {"label.signalias.key", "Privater Schl�ssel (Digitale Signatur erstellen)" },
        {"label.signalias.key.help", "<HTML><strong>Privater Schl�ssel (Digitale Signatur erstellen)</strong><br><br>"
            + "Bitte w�hlen Sie hier einen privaten Schl�ssel aus, der im Zertifikatmanager (Signatur/Verschl�sselung) des Systems verf�gbar ist.<br>"
            + "Mit diesem Schl�ssel erstellen Sie eine digitale Signatur f�r ausgehende Nachrichten an alle entfernten Partner."
            + "</HTML>"},
        {"label.cryptalias.key", "Privater Schl�ssel (Datenentschl�sselung)" },
        {"label.cryptalias.key.help", "<HTML><strong>Privater Schl�ssel (Datenentschl�sselung)</strong><br><br>"
            + "Bitte w�hlen Sie hier einen privaten Schl�ssel aus, der im Zertifikatmanager (Signatur/Verschl�sselung) des Systems verf�gbar ist.<br>"
            + "Wenn eingehende Nachrichten beliebiger Partner f�r diese lokale Station verschl�sselt sind, wird dieser Schl�ssel zur Entschl�sselung verwendet."
            + "</HTML>"},
        {"label.signalias.cert", "Partnerzertifikat (Digitale Signatur verifizieren)" },
        {"label.signalias.cert.help", "<HTML><strong>Partnerzertifikat (Digitale Signatur verifizieren)</strong><br><br>"
            + "Bitte w�hlen Sie hier ein Zertifikat aus, das im Zertifikatmanager (Signatur/Verschl�sselung) des Systems verf�gbar ist.<br>"
            + "Wenn eingehende Nachrichten dieses Partners f�r eine lokale Station digital signiert sind, wird dieses Zertifikat verwendet, um diese Signatur zu pr�fen."
            + "</HTML>"},
        {"label.cryptalias.cert", "Partnerzertifikat (Datenverschl�sselung)" },
        {"label.cryptalias.cert.help", "<HTML><strong>Partnerzertifikat (Datenverschl�sselung)</strong><br><br>"
            + "Bitte w�hlen Sie hier ein Zertifikat aus, das im Zertifikatmanager (Signatur/Verschl�sselung) des Systems verf�gbar ist.<br>"
            + "Wenn Sie ausgehende Nachrichten an diesen Partner verschl�sseln m�chten, wird dieses Zertifikat zum Verschl�sseln der Daten verwendet."
            + "</HTML>"},
        {"label.signtype", "Digitale Signatur" },
        {"label.signtype.help", "<HTML><strong>Digitale Signatur</strong><br><br>"
            + "Hier w�hlen Sie den Signaturalgorithmus, mit dem ausgehende Nachrichten an diesen Partner signiert werden sollen.<br>"
            + "Wenn Sie hier einen Signaturalgorithmus gew�hlt haben, wird auch eingehend von diesem Partner eine signierte Nachricht erwartet - der Signaturalgorithmus ist dabei jedoch beliebig."
            + "</HTML>"},
        {"label.encryptiontype", "Nachrichtenverschl�sselung" },
        {"label.encryptiontype.help", "<HTML><strong>Nachrichtenverschl�sselung</strong><br><br>"
            + "Hier w�hlen Sie den Verschl�sselungsalgorithmus, mit dem ausgehende Nachrichten an diesen Partner verschl�sselt werden sollen.<br>"
            + "Wenn Sie hier einen Verschl�sselungsalgorithmus gew�hlt haben, wird auch eingehend von diesem Partner eine verschl�sselte Nachricht erwartet - der Verschl�sselungsalgorithmus ist dabei jedoch beliebig."
            + "</HTML>"},
        {"label.email", "Mail Adresse" },
        {"label.email.help", "<HTML><strong>Mail Adresse</strong><br><br>"
            + "Dieser Wert ist Teil der AS2 Protokollbeschreibung, wird aber derzeit �berhaupt nicht verwendet."
            + "</HTML>"},
        {"label.email.hint", "Im AS2 Protokoll nicht verwendet oder validiert"},
        {"label.localstation", "Lokale Station" },  
        {"label.localstation.help", "<HTML><strong>Lokale Station</strong><br><br>"
            + "Es gibt zwei Arten von Partnern: Lokale Stationen und entfernte Partner. Eine lokale Station repr�sentiert Ihr eigenes System."
            + "</HTML>"},
        {"label.compression", "Ausgehende Nachrichten komprimieren (ben�tigt AS2 1.1 Gegenstelle)" }, 
        {"label.usecommandonreceipt", "Nach Empfang:" },
        {"label.usecommandonsenderror", "Nach Versand (fehlerhaft):"},
        {"label.usecommandonsendsuccess", "Nach Versand (erfolgreich):"},
        {"label.keepfilenameonreceipt", "Original Dateiname beibehalten"},
        {"label.keepfilenameonreceipt.help", "<HTML><strong>Original Dateiname beibehalten</strong><br><br>"
            + "Ist dies aktiviert, versucht das System, den original Dateinamen aus eingehenden AS2 Nachrichten zu extrahieren und die �bertragene "
            + "Datei unter diesem Namen zu speichern, damit sie entsprechend weiterverarbeitet werden kann.<br>"
            + "Diese Option funktioniert nur, wenn der Absender die urspr�nglichen Dateinameninformationen hinzugef�gt hat. "
            + "Wenn Sie diese Option aktivieren, stellen Sie bitte sicher, dass Ihr Partner eindeutige Dateinamen sendet.</HTML>"},        
        {"label.address", "Adresse" },
        {"label.notes.help", "<HTML><strong>Notizen</strong><br><br>"
            + "Hier finden Sie die M�glichkeit von Notizen zu diesem Partner f�r Ihren eigenen Gebrauch."
            + "</HTML>"}, 
        {"label.contact", "Kontakt" },        
        {"tab.misc", "Allgemein" },
        {"tab.security", "Sicherheit" },
        {"tab.send", "Versand" },
        {"tab.mdn", "MDN" },   
        {"tab.dirpoll", "Verzeichnis�berwachung" }, 
        {"tab.receipt", "Empfang" },  
        {"tab.httpauth", "HTTP Authentifizierung" },
        {"tab.httpheader", "HTTP Header"},
        {"tab.notification", "Benachrichtigung" },
        {"tab.events", "Nachbearbeitung" },
        {"tab.partnersystem", "Info" },
        {"label.subject", "Nutzdaten Subject" },
        {"label.subject.help", "<HTML><strong>Nutzdaten Subject</strong><br><br>$'{'filename} wird durch den Sendedateinamen ersetzt.<br>Dieser Wert wird im HTTP Header �bertragen, daf�r gelten Einschr�nkungen! Bitte verwenden Sie als Zeichenkodierung ISO-8859-1, nur druckbare Zeichen, keine Sonderzeichen. CR, LF und TAB werden ersetzt durch \"\\r\", \"\\n\" und \"\\t\".</HTML>"},        
        {"label.contenttype", "Nutzdaten Content Type" },
        {"label.contenttype.help", "<HTML><strong>Nutzdaten Content Type</strong><br><br>"
            + "Im AS2 Protokoll werden folgende Content Types sicher unterst�tzt:<br>"
            + "application/EDI-X12<br>"
            + "application/EDIFACT<br>"
            + "application/edi-consent<br>"
            + "application/XML<br><br>"
            + "Das AS2 RFC sagt aus, dass alle MIME Content Typen im AS2 unterst�tzt werden sollen - das ist aber keine "
            + "zwingende Voraussetzung. Somit sollten Sie sich nicht darauf verlassen, dass das System Ihres Partners "
            + "oder die unterliegende SMIME Verarbeitung des mendelson AS2 mit anderen Content Typen als den beschriebenen"
            + "umgehen kann."
        + "</HTML>"}, 
        {"label.syncmdn", "Synchrone Empfangsbest�tigung (MDN) anfordern" },
        {"label.syncmdn.help", "<HTML><strong>Synchrone Empfangsbest�tigung</strong><br><br>"
            + "Der Partner sendet die Empfangsbest�tigung (MDN) auf dem R�ckkanal Ihrer ausgehenden Verbindung. "
            + "Die ausgehende Verbindung wird offen gehalten, w�hrend der Partner die Daten entschl�sselt "
            + "und die Signatur pr�ft - aus diesem Grund hat diese Methode einen h�heren Ressourcenbedarf "
            + "als die asynchrone MDN-Verarbeitung.</HTML>" },
        {"label.asyncmdn", "Asynchrone Empfangsbest�tigung (MDN) anfordern" },
        {"label.asyncmdn.help", "<HTML><strong>Asynchrone Empfangsbest�tigung</strong><br><br>"
            + "Der Partner baut eine neue Verbindung zu Ihrem System auf, um die Best�tigung f�r Ihre "
            + "ausgehende Nachricht zu senden. Die �berpr�fung der Signatur und die Entschl�sselung der Daten "
            + "auf der Partnerseite erfolgt nach dem Schlie�en der eingehenden Verbindung - aus diesem "
            + "Grund ben�tigt diese Methode weniger Ressourcen als die Methode mit synchroner MDN.</HTML>" },
        {"label.signedmdn", "Signierte Empfangsbest�tigung (MDN) anfordern" },
        {"label.signedmdn.help", "<HTML><strong>Signierte Empfangsbest�tigung</strong><br><br>"
            + "Mit dieser Einstellung k�nnen Sie dem Partnersystem f�r ausgehende AS2 Nachrichten mitteilen, dass Sie eine signierte Empfangsbest�tigung (MDN) w�nschen.<br>"
            + "Obwohl das zun�chst sinnvoll klingt, ist die Einstellung leider problematisch. Denn wenn die MDN des Partners empfangen ist, ist damit die Transaktion beendet. "
            + "Wenn dann die Signaturverifikation der MDN durchgef�hrt wird und fehl schl�gt, gibt es gar keine M�glichkeit mehr, dem Partner dieses Problem mitzuteilen. "
            + "Ein Transaktionsabbruch ist nicht mehr m�glich - die Transaktion ist bereits beendet. Somit ist das Verifizieren der Signatur der MDN im automatischen Betrieb sinnlos. "
            + "Das AS2 Protokoll schreibt hier vor, dass die Applikation dieses logische Problem l�sen soll, was aber nicht m�glich ist.<br>"
            + "Die mendelson AS2 L�sung zeigt im Fall einer fehlgeschlagenen MDN Signaturpr�fung eine Warnung an.<br><br>"
            + "Es gibt noch eine Besonderheit dieser Einstellung: Wenn es in der Verarbeitung auf Partnerseite zu einem Problem kam, darf die MDN immer unsigniert sein - unabh�ngig von dieser Einstellung."
            + "</HTML>" },
        {"label.enabledirpoll", "Verzeichnis�berwachung aktiviert" },
        {"label.enabledirpoll.help", "<HTML><strong>Verzeichnis�berwachung aktiviert</strong><br><br>"
            + "Wenn Sie diese Option einschalten, wird das System das Ausgangsverzeichnis f�r diesen Partner automatisch nach "
            + "neuen Dateien durchsuchen. Wird eine neue Datei gefunden, wird daraus eine AS2 Nachricht generiert und an den Partner verschickt.<br>"
            + "Bitte beachten Sie, dass diese Methode der Verzeichnis�berwachung nur generelle Parameter f�r alle Nachrichtenerstellungen verwenden kann. "
            + "Wenn Sie spezielle Parameter f�r jede Nachricht einzeln einstellen m�chten, verwenden Sie bitte den Sendeprozess �ber die Kommandozeile.<br>"
            + "Im Falle des Clusterbetriebs (HA) m�ssen Sie alle Verzeichnis�berwachungen ausschalten, da dieser Vorgang nicht synchronisiert werden kann."
            + "</HTML>" },        
        {"label.polldir", "�berwachtes Verzeichnis" },
        {"label.pollinterval", "Abholintervall" },
        {"label.pollignore", "Abholen ignorieren f�r" },        
        {"label.pollignore.help", "<HTML><strong>Abholen ignorieren f�r</strong><br><br>"
            + "Die Verzeichnis�berwachnung wird in regelm�ssigen Abst�nden eine definierte Anzahl von Dateien aus dem "
            + "�berwachten Verzeichnis abholen und verarbeiten. Es muss sichergestellt sein, dass zu diesem Zeitpunkt die Datei "
            + "vollst�ndig vorhanden ist. Wenn Sie regelm�ssig Dateien in das �berwachte Verzeichnis kopieren, kann es hierbei zu "
            + "zeitlichen �berschneidungen kommen - dass also eine Datei abgeholt wird, die noch gar nicht komplett verf�gbar ist. "
            + "Daher sollten Sie, wenn Sie die Dateien mit einer nichtatomaren Operation in das �berwachte Verzeichnis kopieren, zum Zeitpunkt "
            + "des Kopierprozesses eine Dateinamenserweiterung w�hlen, die vom �berwachnungsprozess ignoriert wird. Nachdem die gesamt Datei dann im �berwachten "
            + "Verzeichnis verf�gbar ist, k�nnen Sie die Dateinamenserweiterung mit einer atomaren Operation (move, mv, rename) entfernen und die vollst�ndige "
            + "Datei wird abgeholt."
            + "<br>Die List der Dateinameserweiterungen ist eine kommagetrennte Liste von Erweiterungen, zum Beispiel "
            + "\"*.tmp, *.upload\"."
            + "</HTML>"},
        {"label.pollignore.hint", "Liste der Dateierweiterungen, die ignoriert werden sollen, kommagetrennt (Wildcards erlaubt)."},
        {"label.maxpollfiles", "Max Dateianzahl/Abholvorgang"},
        {"label.httpauth.none", "Keine"},
        {"label.httpauth.message", "Authentifizierung ausgehender AS2 Nachrichten" },
        {"label.httpauth.credentials.message", "Basis HTTP Authentifizierung" },
        {"label.httpauth.credentials.message.user", "Benutzername" },
        {"label.httpauth.credentials.message.pass", "Passwort" },
        {"label.httpauth.oauth2.message", "OAuth2"},
        {"label.httpauth.asyncmdn", "Authentifizierung ausgehender asynchroner MDN" },
        {"label.httpauth.credentials.asyncmdn", "Basis HTTP Authentifizierung" },
        {"label.httpauth.credentials.asyncmdn.user", "Benutzername" },
        {"label.httpauth.credentials.asyncmdn.pass", "Passwort" },    
        {"label.httpauth.oauth2.asyncmdn", "OAuth2"},
        {"label.notify.send", "Benachrichtigen, wenn das Sendekontingent folgenden Wert �bersteigt:" },
        {"label.notify.receive", "Benachrichtigen, wenn das Empfangskontingent folgenden Wert �bersteigt:" },
        {"label.notify.sendreceive", "Benachrichtigen, wenn das Sende/Empfangskontingent folgenden Wert �bersteigt:" },
        {"header.httpheaderkey", "Name" },
        {"header.httpheadervalue", "Wert" },
        {"httpheader.add", "Hinzuf�gen" },
        {"httpheader.delete", "Entfernen" },
        {"label.as2version", "AS2 Version:" },
        {"label.productname", "Produktname:" },
        {"label.features", "Funktionen:" },
        {"label.features.cem", "Zertifikataustausch �ber CEM" },
        {"label.features.ma", "Mehrere Anh�nge" },
        {"label.features.compression", "Datenkomprimierung" },
        {"partnerinfo", "Ihr Partner �bermittelt mit jeder AS2 Nachricht auch Informationen �ber die Funktionen seines AS2 Systems. Dies ist die Liste dieser Funktionen." },
        {"partnersystem.noinfo", "Keine Information verf�gbar - gab es schon eine Transaktion?" },
        {"label.httpversion", "HTTP Protokollversion" },
        {"label.test.connection", "Verbindung pr�fen" },                
        {"label.mdn.description", "<HTML>Die MDN (Message Delivery Notification) ist die Best�tigung f�r die AS2 Nachricht. Dieser Abschnitt definiert das Verhalten Ihres Partners f�r Ihre ausgehenden AS2-Nachrichten.</HTML>" },                        
        {"label.algorithmidentifierprotection", "<HTML>\"Algorithm Identifier Protection Attribute\" in der Signatur verwenden (empfohlen), weitere Informationen unter RFC 6211</HTML>" },        
        {"tooltip.button.editevent", "Ereignis bearbeiten" },
        {"tooltip.button.addevent", "Neues Ereignis erstellen" },
        {"label.httpauthentication.credentials.help", "<HTML><strong>HTTP Basis Zugangsauthentifizierung</strong><br><br>"
            + "Bitte richten Sie hier die HTTP Basis-Zugangsauthentifizierung ein, wenn dies auf der Seite "
            + "Ihres Partners aktiviert ist (definiert in RFC 7617). Auf nicht authentifizierte Anfragen "
            + "(falsche Anmeldedaten usw.) sollte das System des entfernten Partners einen <strong>HTTP 401 Unauthorized</strong> "
            + "Status zur�ckgeben.<br>Wenn die Verbindung zu Ihrem Partner TLS-Client-Authentifizierung (�ber Zertifikate) "
            + "erfordert, ist hier keine Einstellung erforderlich. In diesem Fall importieren Sie bitte die Zertifikate "
            + "des Partners �ber den TLS-Zertifikatsmanager - das System k�mmert sich dann um die TLS-Client-Authentifizierung."
            + "</HTML>" },
    };
    
}