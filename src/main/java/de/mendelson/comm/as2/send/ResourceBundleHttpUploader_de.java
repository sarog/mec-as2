//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleHttpUploader_de.java 23    7.12.18 10:25 Heller $
package de.mendelson.comm.as2.send;
import de.mendelson.util.MecResourceBundle;

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @version $Revision: 23 $
 */
public class ResourceBundleHttpUploader_de extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"returncode.ok", "Nachricht erfolgreich versandt (HTTP {0}); {1} �bertragen in {2} [{3} KB/s]." },
        {"returncode.accepted", "Nachricht erfolgreich versandt (HTTP {0}); {1} �bertragen in {2} [{3} KB/s]." },
        {"sending.msg.sync", "Sende AS2 Nachricht an {0}, erwarte synchrone MDN zur Empfangsbest�tigung." },
        {"sending.cem.sync", "Sende CEM Nachricht an {0}, erwarte synchrone MDN zur Empfangsbest�tigung." },
        {"sending.msg.async", "Sende AS2 Nachricht an {0}, erwarte asynchrone MDN zur Empfangsbest�tigung auf {1}." },
        {"sending.cem.async", "Sende CEM Nachricht an {0}, erwarte asynchrone MDN zur Empfangsbest�tigung auf {1}." },
        {"sending.mdn.async", "Sende asynchrone Empfangsbest�tigung (MDN) an {0}." },
        {"error.httpupload", "�bertragung fehlgeschlagen, entfernter AS2 Server meldet \"{0}\"." },
        {"error.noconnection", "Verbindungsproblem, es konnten keine Daten �bertragen werden." },
        {"error.http502", "Verbindungsproblem, es konnten keine Daten �bertragen werden. (HTTP 502 - BAD GATEWAY)" },
        {"error.http503", "Verbindungsproblem, es konnten keine Daten �bertragen werden. (HTTP 503 - SERVICE UNAVAILABLE)" },
        {"error.http504", "Verbindungsproblem, es konnten keine Daten �bertragen werden. (HTTP 504 - GATEWAY TIMEOUT)" },
        {"using.proxy", "Benutze Proxy {0}:{1}." },  
        {"answer.no.sync.mdn", "Die empfangene synchrone Empfangsbest�tigung ist nicht im richtigen Format. Da MDN-Strukturprobleme ungew�hnlich sind, k�nnte es sein, dass dies keine Antwort des AS2-Systems ist, das Sie ansprechen wollten, sondern vielleicht die Antwort eines Proxies oder die Antwort einer Standard-Website? Die folgenden HTTP Header-Werte fehlen: [{0}].\nDie erhaltenen Daten fangen mit folgenden Strukturen an:\n{1}" },
        {"hint.SSLPeerUnverifiedException", "Hinweis:\nDieses Problem passierte w�hrend des SSL Handshake. Das System konnte somit keine sichere Verbindung zu Ihrem Partner aufbauen, das Problem hat nichts mit dem AS2 Protokoll zu tun.\nBitte pr�fen Sie folgendes:\n*Haben Sie alle Zertifikate Ihres Partners in Ihren SSL Keystore importiert (f�r SSL, inkl Intermediate/Root Zertifikate)?\n*Hat Ihr Partner alle Zertifiakte von Ihnen importiert (f�r SSL, inkl Intermediate/Root Zertifikate)?" }, 
        {"hint.ConnectTimeoutException", "Hinweis:\nDies ist in der Regel ein Infrastrukturproblem, das nichts mit dem AS2 Protokoll zu tun hat. Es ist nicht m�glich, eine ausgehende Verbindung zu Ihrem Partner aufzubauen.\nBitte pr�fen Sie folgendes, um das Problem zu beheben:\n*Haben Sie eine aktive Internetverbindung?\n*Bitte pr�fen sie, ob Sie in der Partnerverwaltung die richtige EmpfangsURL Ihres Partners eingegeben haben?\n*Bitte kontaktieren Sie Ihren Partner, eventuell ist sein AS2 System nicht verf�gbar?" },
    };
    
}