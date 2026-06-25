//$Header: /as2/de/mendelson/util/httpconfig/server/ResourceBundleHTTPServerConfigProcessor_es.java 2     9/12/24 16:03 Heller $
package de.mendelson.util.httpconfig.server;

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
* @version $Revision: 2 $
*/
public class ResourceBundleHTTPServerConfigProcessor_es extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"http.serverstateurl", "Mostrar el estado del servidor:"},
		{"webapp.as2api.war", "API REST AS2 de mendelson"},
		{"external.ip.error", "IP externa: -No se puede determinar-."},
		{"webapp._unknown", "Servlet desconocido"},
		{"info.cipher.howtochange", "Para deshabilitar determinados cifrados para las conexiones entrantes, edite el archivo de configuración de su servidor HTTP integrado ({0}) con un editor de texto. Busque la cadena de caracteres <Set name=\"ExcludeCipherSuites\">, añada el cifrado que desea excluir y reinicie el programa."},
		{"webapp.as4api.war", "mendelson AS4 REST API"},
		{"info.cipher", "Los siguientes cifrados son soportados por el servidor HTTP subyacente en el lado de entrada.\nCuáles son compatibles depende de la máquina virtual Java que esté utilizando (actualmente {1}).\nPuede desactivar cifrados individuales en el archivo de configuración\nArchivo de configuración \"{0}\"."},
		{"http.receipturls", "URL de recepción completa de la configuración actual"},
		{"webapp.oftp2api.war", "API REST OFTP2 de mendelson"},
		{"http.server.config.tlskey.none", "Clave TLS: No se ha definido ninguna clave TLS, ¡no es posible establecer conexiones TLS entrantes!"},
		{"external.ip", "IP externa: {0} / {1}"},
		{"webapp.webas2.war", "Monitorización web del servidor AS2 de mendelson"},
		{"webapp.as4.war", "mendelson AS4 recibiendo servlet"},
		{"info.protocols", "El servidor HTTP subyacente admite los siguientes protocolos para las conexiones entrantes.\nCuáles son soportados depende de la Java VM que esté utilizando (actualmente {1}). El proveedor de seguridad TLS utilizado es {2}.\nPuede desactivar protocolos individuales en el archivo de configuración\nArchivo de configuración \"{0}\"."},
		{"http.server.config.listener", "El puerto {0} ({1}) está vinculado al adaptador de red {2}."},
		{"webapp.as2.war", "mendelson AS2 servlet receptor"},
		{"http.deployedwars", "WARs actualmente disponibles en el servidor HTTP (funcionalidad servlet):"},
		{"webapp.as2-sample.war", "Ejemplos de la API AS2 de mendelson"},
		{"webapp.as4-sample.war", "Ejemplos de la API AS4 de mendelson"},
		{"http.server.config.clientauthentication", "El servidor requiere autenticación de cliente TLS: {0}"},
		{"info.protocols.howtochange", "Para desactivar determinados protocolos en el lado de entrada, edite el archivo de configuración de su servidor HTTP integrado ({0}) con un editor de texto. Busque la cadena de caracteres <Set name=\"ExcludeProtocols\">, añada el protocolo que desea excluir y reinicie el programa."},
		{"http.server.config.tlskey.info", "Clave TLS:\n	Alias [{0}]\n	Huella digital SHA1 [{1}]\n	Número de serie [{2}]\n	Válido hasta [{3}]\n"},
	};
}
