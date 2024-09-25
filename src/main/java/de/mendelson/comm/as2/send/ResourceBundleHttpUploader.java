//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleHttpUploader.java 19    9/25/17 1:27p Heller $
package de.mendelson.comm.as2.send;
import de.mendelson.util.MecResourceBundle;

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @version $Revision: 19 $
 */
public class ResourceBundleHttpUploader extends MecResourceBundle{
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"returncode.ok", "{0}: Message sent successfully (HTTP {1}); {2} transfered in {3} [{4} KB/s]." },
        {"returncode.accepted", "{0}: Message sent successfully (HTTP {1}); {2} transfered in {3} [{4} KB/s]." },
        {"sending.msg.sync", "{0}: Sending AS2 message to {1}, sync MDN requested." },
        {"sending.cem.sync", "{0}: Sending CEM message to {1}, sync MDN requested." },
        {"sending.msg.async", "{0}: Sending AS2 message to {1}, async MDN requested at {2}." },
        {"sending.cem.async", "{0}: Sending CEM message to {1}, async MDN requested at {2}." },
        {"sending.mdn.async", "{0}: Sending async MDN to {1}." },  
        {"error.httpupload", "{0}: Transmission failed, remote AS2 server reports \"{1}\"." },
        {"error.noconnection", "{0}: Connection problem, failed to transmit data." },
        {"error.http502", "{0}: Connection problem, failed to transmit data (HTTP 502 - BAD GATEWAY)" },
        {"error.http503", "{0}: Connection problem, failed to transmit data (HTTP 503 - SERVICE UNAVAILABLE)" },
        {"error.http504", "{0}: Connection problem, failed to transmit data (HTTP 504 - GATEWAY TIMEOUT)" },
        {"using.proxy", "{0}: Using proxy {1}:{2}." },
        {"answer.no.sync.mdn", "{0}: The received sync MDN seems not to be in right format. Missing header value \"{1}\"." },
        {"hint.SSLPeerUnverifiedException", "Hint:\nThis is a problem that occured during the SSL handshake. The system was unable to establish a secure connection to your partner, this problem is not AS2 protocol related.\nPlease check the following to fix this issue:\n*Have you imported all your partners SSL certificates into your SSL keystore (incl. root/intermediate certificates)?\n*Has your partner imported all your certificates into his SSL keystore (incl. root/intermediate certificates)?" },
        {"hint.ConnectTimeoutException", "Hint:\nThis is a mainly an infrastructure problem which is not AS2 protocol related. The system was unable to establish an outbound connection to your partners AS2 system.\nPlease check the following to fix this issue:\n*Do you have an active internet connection?\n*Please recheck the receipt URL of your partner, is there a typo?\n*Please contact your partner, perhaps his AS2 system is down?" },
    };
    
}