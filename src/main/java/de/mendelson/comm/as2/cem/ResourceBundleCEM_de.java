//$Header: /mec_as2/de/mendelson/comm/as2/cem/ResourceBundleCEM_de.java 16    15/04/26 12:42 Heller $
package de.mendelson.comm.as2.cem;
import de.mendelson.comm.as2.cem.messages.TrustResponse;
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
 * @version $Revision: 16 $
 */
public class ResourceBundleCEM_de extends MecResourceBundle{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {                
        {"trustrequest.rejected", "Die Antwort auf den eingegangen Trust Request wurde auf den Status \"" + TrustResponse.STATUS_REJECTED_STR + "\" gesetzt." },
        {"trustrequest.accepted", "Die Antwort auf den eingegangen Trust Request wurde auf den Status \"" + TrustResponse.STATUS_ACCEPTED_STR + "\" gesetzt." },
        {"trustrequest.working.on", "Bearbeite den Trust Request {0}." },
        {"trustrequest.certificates.found", "Anzahl der übertragenen Zertifikate: {0}." },
        {"cem.validated.schema", "Die eingegangene CEM Nachricht wurde erfolgreich validiert." },
        {"cem.structure.info", "Anzahl der Trust Requests in der eingegangenen CEM Struktur: {0}" },
        {"transmitted.certificate.info", "Das übermittelte Zertifikat hat die Kennwerte IssuerDN=\"{0}\" und Seriennummer \"{1}\"." },
        {CEMReceiptController.KEYSTORE_TYPE_ENC_SIGN +".cert.already.imported", "Das übermittelte CEM Zertifikat existiert bereits in dem System [enc/sign] (Alias {0}), der Import wurde übersprungen."},
        {CEMReceiptController.KEYSTORE_TYPE_SSL +".cert.already.imported", "Das übermittelte CEM Zertifikat existiert bereits in dem System [TLS] (Alias {0}), der Import wurde übersprungen."},
        {CEMReceiptController.KEYSTORE_TYPE_ENC_SIGN +".cert.imported.success", "Das übermittelte CEM Zertifikat wurde erfolgreich in das System importiert [enc/sign] (Alias {0})."},
        {CEMReceiptController.KEYSTORE_TYPE_SSL +".cert.imported.success", "Das übermittelte CEM Zertifikat wurde erfolgreich in das System importiert [TLS] (Alias {0})."},
        {"category." + CEMEntry.Category.CRYPT.toInt(), "Verschlüsselung" },
        {"category." + CEMEntry.Category.SIGN.toInt(), "Signatur" },
        {"category." + CEMEntry.Category.TLS.toInt(), "TLS" },
        {"state." + CEMEntry.Status.ACCEPTED.toInt(), "Akzeptiert von {0}" },
        {"state." + CEMEntry.Status.PENDING.toInt(), "Noch keine Antwort von {0}" },
        {"state." + CEMEntry.Status.REJECTED.toInt(), "Abgelehnt von {0}" },
        {"state." + CEMEntry.Status.CANCELED.toInt(), "Vorgang abgebrochen" },
        {"state." + CEMEntry.Status.PROCESSING_ERROR.toInt(), "Verarbeitungsfehler" },
        {"cemtype.response", "Die CEM Nachricht ist vom Typ \"Certificate response\"" },
        {"cemtype.request", "Die CEM Nachricht ist vom Typ \"Certificate request\"" },
        {"cem.response.relatedrequest.found", "Die CEM Nachricht bezieht sich auf die Anfrage \"{0}\"" },
        {"cem.response.prepared", "CEM Antwortnachricht erstellt für die Anfrage {0}" },
        {"cem.created.request", "Die CEM Anfrage wurde generiert für die Beziehung \"{0}\"-\"{1}\". Das Zertifikat mit den Kennwerten issuerDN \"{2}\" und Seriennummer \"{3}\" wurde eingebettet. Die definierte Verwendung ist {4}." },
    };
    
}