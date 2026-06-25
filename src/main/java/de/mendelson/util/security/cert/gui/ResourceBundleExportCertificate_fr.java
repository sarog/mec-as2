//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleExportCertificate_fr.java 14    31/03/26 17:12 Heller $
package de.mendelson.util.security.cert.gui;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.security.CertificateFormatType;
import de.mendelson.util.security.cert.KeystoreCertificate;
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
 * @author E.Pailleau
 * @version $Revision: 14 $
 */
public class ResourceBundleExportCertificate_fr extends MecResourceBundle{

    private static final long serialVersionUID = 1L;
    
    @Override
  public Object[][] getContents() {
    return CONTENTS;
  }

  /**List of messages in the specific language*/
  private static final Object[][] CONTENTS = {
        
    {"button.ok", "Valider" },
    {"button.cancel", "Annuler" },
    {"button.browse", "Parcourir..." },            
    {"title", "Exporter un certificat X.509" },
    {"label.exportfile", "Nom du fichier" },
    {"label.exportfile.hint", "Fichier de certificat qui est généré"},
    {"label.alias", "Alias" },        
    {"label.exportformat", "Format" },
    {"error.empty.certificate", "Aucune donnée de certificat disponible" },
    {"filechooser.certificate.export", "Merci de sélectionner le fichier d''export du certificat." },
    {"certificate.export.error.title", "L''export du certificat a échoué" },
    {"certificate.export.error.message", "L''export du certificat suivant a échoué:\n{0}" },
    {"certificate.export.success.title", "Succès" },
    {"certificate.export.success.message", "Le certificat a été exporté avec succès a\n\"{0}\"" }, 
    {CertificateFormatType.PEM.toString(), "Format texte (PEM. *.cer)" },
    {CertificateFormatType.PEM_CHAIN.toString(), "Format texte (+chaîne certification) (PEM. *.cer)" },
    {CertificateFormatType.DER.toString(), "Format binaire (DER, *.cer)" },
    {CertificateFormatType.PKCS7.toString(), "Avec chaîne de confiance (PKCS#7, *.p7b)" },  
    {CertificateFormatType.SSH2.toString(), "Format SSH2 (clé publique, *.pub)"},
  };		
  
}
