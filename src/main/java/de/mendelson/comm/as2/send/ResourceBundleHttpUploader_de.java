//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleHttpUploader_de.java 19    9/25/17 1:27p Heller $
package de.mendelson.comm.as2.send;
import de.mendelson.util.MecResourceBundle;

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @version $Revision: 19 $
 */
public class ResourceBundleHttpUploader_de extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"returncode.ok", "{0}: Nachricht erfolgreich versandt (HTTP {1}); {2} �bertragen in {3} [{4} KB/s]." },
        {"returncode.accepted", "{0}: Nachricht erfolgreich versandt (HTTP {1}); {2} �bertragen in {3} [{4} KB/s]." },
        {"sending.msg.sync", "{0}: Sende AS2 Nachricht an {1}, erwarte synchrone MDN zur Empfangsbest�tigung." },
        {"sending.cem.sync", "{0}: Sende CEM Nachricht an {1}, erwarte synchrone MDN zur Empfangsbest�tigung." },
        {"sending.msg.async", "{0}: Sende AS2 Nachricht an {1}, erwarte asynchrone MDN zur Empfangsbest�tigung auf {2}." },
        {"sending.cem.async", "{0}: Sende CEM Nachricht an {1}, erwarte asynchrone MDN zur Empfangsbest�tigung auf {2}." },
        {"sending.mdn.async", "{0}: Sende asynchrone Empfangsbest�tigung (MDN) an {1}." },
        {"error.httpupload", "{0}: �bertragung fehlgeschlagen, entfernter AS2 Server meldet \"{1}\"." },
        {"error.noconnection", "{0}: Verbindungsproblem, es konnten keine Daten �bertragen werden." },
        {"error.http502", "{0}: Verbindungsproblem, es konnten keine Daten �bertragen werden. (HTTP 502 - BAD GATEWAY)" },
        {"error.http503", "{0}: Verbindungsproblem, es konnten keine Daten �bertragen werden. (HTTP 503 - SERVICE UNAVAILABLE)" },
        {"error.http504", "{0}: Verbindungsproblem, es konnten keine Daten �bertragen werden. (HTTP 504 - GATEWAY TIMEOUT)" },
        {"using.proxy", "{0}: Benutze Proxy {1}:{2}." },  
        {"answer.no.sync.mdn", "{0}: Die empfangene synchrone Empfangsbest�tigung ist nicht im richtigen Format. Der Header \"{1}\" konnte nicht gefunden werden." },
        {"hint.SSLPeerUnverifiedException", "Hinweis:\nDieses Problem passierte w�hrend des SSL Handshake. Das System konnte somit keine sichere Verbindung zu Ihrem Partner aufbauen, das Problem hat nichts mit dem AS2 Protokoll zu tun.\nBitte pr�fen Sie folgendes:\n*Haben Sie alle Zertifikate Ihres Partners in Ihren SSL Keystore importiert (f�r SSL, inkl Intermediate/Root Zertifikate)?\n*Hat Ihr Partner alle Zertifiakte von Ihnen importiert (f�r SSL, inkl Intermediate/Root Zertifikate)?" }, 
        {"hint.ConnectTimeoutException", "Hinweis:\nDies ist in der Regel ein Infrastrukturproblem, das nichts mit dem AS2 Protokoll zu tun hat. Es ist nicht m�glich, eine ausgehende Verbindung zu Ihrem Partner aufzubauen.\nBitte pr�fen Sie folgendes, um das Problem zu beheben:\n*Haben Sie eine aktive Internetverbindung?\n*Bitte pr�fen sie, ob Sie in der Partnerverwaltung die richtige EmpfangsURL Ihres Partners eingegeben haben?\n*Bitte kontaktieren Sie Ihren Partner, eventuell ist sein AS2 System nicht verf�gbar?" },
    };
    
}