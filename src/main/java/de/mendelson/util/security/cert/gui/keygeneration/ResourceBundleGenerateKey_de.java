//$Header: /as2/de/mendelson/util/security/cert/gui/keygeneration/ResourceBundleGenerateKey_de.java 13    24/10/22 12:52 Heller $
package de.mendelson.util.security.cert.gui.keygeneration;
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
 * @author S.Heller
 * @version $Revision: 13 $
 */
public class ResourceBundleGenerateKey_de extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {

        {"title", "Schl�sselerstellung" },
        {"button.ok", "Ok" },
        {"button.cancel", "Abbruch" },
        {"label.keytype", "Schl�sseltyp" },
        {"label.keytype.help", "<HTML><strong>Schl�sseltyp</strong><br><br>"
            + "Dies ist der Algorithmus zum Erstellen des Schl�ssels. F�r die daraus resultierenden Schl�ssel gibt es je nach Algorithmus vor- und Nachteile.<br>"
            + "Wir w�rden Ihnen Stand 2022 einen RSA Schl�ssel mit der Schl�ssell�nge 2048 oder 4096 bit empfehlen."
            + "</HTML>"
        },
        {"label.signature", "Signatur" },
        {"label.signature.help", "<HTML><strong>Signatur</strong><br><br>"
            + "Dies ist der Signaturalgorithmus, mit dem der Schl�ssel signiert ist. "
            + "Er wird f�r Integrit�tstests des Schl�ssels selber ben�tigt. Dieser Parameter hat nichts mit den "
            + "Signaturf�higkeiten des Schl�ssels zu tun - Sie k�nnen also zum Beispiel mit einem SHA-1 signierten "
            + "Schl�ssel auch SHA-2 Signaturen erstellen "
            + "oder anders herum.<br>"
            + "Wir w�rden Ihnen Stand 2022 einen SHA-2 signierten Schl�ssel empfehlen."
            + "</HTML>"
        },
        {"label.size", "Schl�ssell�nge" },
        {"label.size.help", "<HTML><strong>Schl�ssell�nge</strong><br><br>"
            + "Dies ist die Schl�ssell�nge des Schl�ssels. Prinzipiell sind kryptographische Operationen mit "
            + "gr�sserer Schl�ssell�nge sicherer als kryptographische Operationen mit Schl�sseln kleinerer Schl�ssell�nge. "
            + "Der Nachteil grosser Schl�ssell�ngen ist jedoch, dass kryptographische Operationen signifikant l�nger dauern, "
            + "was die Datenverarbeitung je nach Rechenleistung signifikant verlangsamen kann.<br>"
            + "Wir w�rden Ihnen Stand 2022 einen Schl�ssel der L�nge 2048 oder 4096 bit empfehlen."
            + "</HTML>"
        },
        {"label.commonname", "Common Name" },
        {"label.commonname.help", "<HTML><strong>Common Name</strong><br><br>"
            + "Dies ist der Name Ihrer Domain, wie es dem DNS Eintrag entspricht. Dieser Parameter ist wichtig f�r das Handshake einer TLS Verbindung. "
            + "Es ist m�glich (aber nicht empfehlenswert!), hier eine IP Adresse einzugeben. Es ist ebenfalls m�glich, "
            + "ein Wildcard Zertifikat zu erstellen, wenn Sie hier Teile der Domain durch * ersetzen. "
            + "Aber auch das ist nicht empfehlenswert, weil nicht alle Partner solche Schl�ssel akzeptieren.<br>"
            + "Wenn Sie diesen Schl�ssel als TLS Schl�ssel verwenden m�chten und dieser Eintrag auf eine nichtexistiente Domain verweist oder nicht Ihrer Domain entspricht, "
            + "sollten die meisten Systeme eingehende TLS Verbindungen abbrechen."
            + "</HTML>"
        },
        {"label.commonname.hint", "(Domain Name des Servers)" },
        {"label.organisationunit", "Organisation (Unit)" },
        {"label.organisationname", "Organisation (Name)" },
        {"label.locality", "Ort" },
        {"label.locality.hint", "(Stadt)" },
        {"label.state", "Land" },
        {"label.countrycode", "L�ndercode" },
        {"label.countrycode.hint", "(2 Zeichen, ISO 3166)" },
        {"label.mailaddress", "Mail Adresse" },
        {"label.mailaddress.help", "<HTML><strong>Mail Adresse</strong><br><br>"
            + "Dies ist die Mailadresse, die mit dem Schl�ssel verkn�pft ist. Technisch ist "
            + "dieser Parameter uninteressant. Wenn Sie den Schl�ssel jedoch beglaubigen lassen "
            + "m�chten, dient diese Mailadresse in der Regel f�r die Kommunikation mit der CA. "
            + "Ausserdem sollte die Mailadresse sich auch auf der Domain des Servers befinden "
            + "und so etwas wie webmaster@domain oder �hnlichem entsprechen, "
            + "weil die meisten CAs somit pr�fen, ob Sie im Besitz der zugeh�rigen Domain sind."
            + "</HTML>"
        },
        {"label.validity", "G�ltigkeit in Tagen" },
        {"label.validity.help", "<HTML><strong>G�ltigkeit in Tagen</strong><br><br>"
            + "Dieser Wert ist nur f�r self signed Schl�ssel interessant. Im Falle einer Beglaubigung wird die CA diesen Wert �berschreiben."
            + "</HTML>"
        },
        {"label.purpose", "Verwendungszweck / Schl�sselerweiterungen" },
        {"label.purpose.encsign", "Verschl�sselung und digitale Signatur" },
        {"label.purpose.ssl", "TLS/SSL" },
        {"label.subjectalternativenames", "Alternative Antragstellernamen" },        
        {"warning.mail.in.domain", "Die Mailadresse ist nicht Teil der Domain \"{0}\" (z.B. meinname@{0}).\nDies kann ein Problem sein, wenn der Schl�ssel sp�ter beglaubigt werden soll."},
        {"warning.nonexisting.domain", "Die Domain \"{0}\" existiert nicht." },
        {"warning.invalid.mail", "Die Mail Adresse \"{0}\" ist ung�ltig." },
        {"button.reedit", "�berarbeiten" },
        {"button.ignore", "Warnungen ignorieren" },
        {"warning.title", "M�gliches Problem der Schl�sselparameter" },
        {"view.expert", "Experten Ansicht" },
        {"view.basic", "Standard Ansicht" },
        {"label.namedeccurve", "Kurve" },
        {"label.namedeccurve.help", "<HTML><strong>Kurve</strong><br><br>"
            + "Hiermit w�hlen Sie den Namen der EC Kurve aus, der f�r die Generation des Schl�ssels verwendet werden soll. "
            + "Die gew�nschte Schl�ssell�nge ist in der Regel Teil des Namens der Kurve, so hat zum Beispiel der Schl�ssel der Kurve "
            + "\"BrainpoolP256r1\" eine L�nge von 256bit. Die Stand 2022 am meisten verwendete Kurve (ca 75% aller EC Zertifikate im Internet verwenden sie) ist NIST P-256, die Sie hier unter dem "
            + "Namen \"Prime256v1\" finden. Sie ist Stand 2022 die Standardkurve von OpenSSL."
            + "</HTML>" },
    };
    
}