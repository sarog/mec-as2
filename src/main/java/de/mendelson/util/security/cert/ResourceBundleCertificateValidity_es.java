//$Header: /as4/de/mendelson/util/security/cert/ResourceBundleCertificateValidity_es.java 1     14/01/26 16:23 Heller $
package de.mendelson.util.security.cert;

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
public class ResourceBundleCertificateValidity_es extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"state.1", "Caducado"},
		{"state.1024", "(CRL) Respuesta caducada"},
		{"state.1048576", "(CRL) Problema inespecífico"},
		{"state.131072", "(CRL) URL no se ha podido extraer"},
		{"state.16384", "(CRL) Formato no válido"},
		{"state.2048", "(CRL) Firma no válida"},
		{"state.256", "(CRL) Bloqueado por CA (Revocado)"},
		{"state.262144", "(CRL) Fallo en la descarga"},
		{"state.32768", "(CRL) Certificado no legible"},
		{"state.4", "Jerarquía de certificación incorrecta"},
		{"state.4096", "(CRL) Falta la extensión"},
		{"state.4194304", "Clave pública de prueba mendelson - no utilizar en operación productiva"},
		{"state.512", "(CRL) URL no accesible"},
		{"state.524288", "(CRL) Esquema de URL no compatible"},
		{"state.8192", "(CRL) URL incorrecta"},
		{"state.8388608", "(CRL) Falta el emisor - por favor, importe el certificado de nivel superior"},
	};
}
