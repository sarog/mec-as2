//$Header: /as2/de/mendelson/util/security/crmf/ResourceBundleCRMF_es.java 1     12/01/26 8:33 Heller $
package de.mendelson.util.security.crmf;

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
* @version $Revision: 1 $
*/
public class ResourceBundleCRMF_es extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Cancelar"},
		{"button.ok", "OK"},
		{"label.initial", "Inicialización (solicitud inicial)"},
		{"label.initial.help", "<HTML><strong>Inicialización (solicitud inicial)</strong><br><br>"
			+"Seleccione esta opción para el registro inicial con la CA. La autenticación se realiza a través de su contraseña de registro inicial.</HTML>"},
		{"label.key.encryption", "Clave de cifrado"},
		{"label.key.signature", "Clave de firma"},
		{"label.key.tls", "Clave TLS"},
		{"label.root.ca", "Certificado raíz sub-CA"},
		{"label.root.ca.help", "<HTML><strong>Certificado raíz sub-CA</strong><br><br>"
			+"La solicitud CRMF generada se encapsula en una estructura de mensaje CMP que contiene un campo de destinatario (Recipient).<br>"
			+"Para garantizar una transmisión conforme a la norma, este campo de destinatario debe contener el nombre distinguido (DN) específico de la CA del espacio de nombres X.500. Este nombre se encuentra en el campo \"Asunto\" del propio certificado de la CA (por ejemplo, CN=SM-Test-PKI-DE). Puede encontrarse en el campo \"Asunto\" del propio certificado de la CA (por ejemplo, CN=SM-Test-PKI-DE). De este modo se garantiza que la estructura del mensaje generado cumple las normas BDEW/Smart Metering PKI. El certificado raíz de la (sub)CA es necesario para una coincidencia exacta de este valor.</HTML>"},
		{"label.update", "Actualización (Solicitud de actualización)"},
		{"label.update.help", "<HTML><strong>Actualizar (Solicitud de actualización)</strong><br><br>"
			+"Seleccione esta opción para renovar un certificado existente antes de que caduque. La autenticación se realiza automáticamente a través de su certificado actualmente válido.</HTML>"},
		{"password.hint", "Contraseña inicial de un solo uso"},
		{"success.body", "El archivo CRMF se guardó en {0}"},
		{"success.title", "Creación con éxito del CRMF"},
		{"title", "Creación de consultas CRMF (BDEW)"},
	};
}
