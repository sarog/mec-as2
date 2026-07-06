//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleHttpUploader_es.java 9     18/06/25 12:21 Heller $
package de.mendelson.comm.as2.send;

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
* @version $Revision: 9 $
*/
public class ResourceBundleHttpUploader_es extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"answer.no.sync.empty", "El acuse de recibo síncrono recibido está vacío. Probablemente ha habido un problema al procesar los mensajes AS2 por parte de su interlocutor; póngase en contacto con él."},
		{"answer.no.sync.mdn", "El acuse de recibo síncrono recibido no tiene el formato correcto. Dado que los problemas de estructura de MDN son inusuales, podría ser que no se tratara de una respuesta del sistema AS2 al que intentaba dirigirse, sino tal vez de la respuesta de un proxy o de la respuesta de un sitio web estándar. Faltan los siguientes valores de cabecera HTTP [{0}].\nLos datos recibidos comienzan con las siguientes estructuras:\n{1}"},
		{"connected.to", "Conectado a {0}, espera un MDN y mantiene la conexión abierta hasta {1}"},
		{"connection.shut.down", "La conexión saliente a {0} se cerró, estuvo abierta durante {1}s"},
		{"connection.tls.info", "Conexión TLS saliente establecida [{0}, {1}]"},
		{"error.http502", "Problema de conexión, no se han podido transferir datos. (HTTP 502 - PUERTA DE ENLACE INCORRECTA)"},
		{"error.http503", "Problema de conexión, no se han podido transferir datos. (HTTP 503 - SERVICIO NO DISPONIBLE)"},
		{"error.http504", "Problema de conexión, no se han podido transferir datos. (HTTP 504 - TIEMPO DE ESPERA DE LA PASARELA)"},
		{"error.httpupload", "Transmisión fallida, el servidor AS2 remoto informa \"{0}\"."},
		{"error.noconnection", "Problema de conexión, no se han podido transferir datos."},
		{"hint.ConnectTimeoutException", "Nota:\nEsto suele ser un problema de infraestructura que no tiene nada que ver con el protocolo AS2. No es posible establecer una conexión saliente con su interlocutor.\nPor favor, compruebe lo siguiente para resolver el problema:\n*¿Tiene una conexión a Internet activa?\n*Compruebe si ha introducido correctamente la URL de recepción de su interlocutor en la administración de interlocutores.\n*Por favor, póngase en contacto con su socio, ¿tal vez su sistema AS2 no está disponible?"},
		{"hint.SSLException", "Nota:\nNormalmente se trata de un problema de negociación a nivel de protocolo. Su interlocutor ha rechazado su conexión.\nO bien su interlocutor espera una conexión segura (HTTPS) y usted quería establecer una conexión no segura o viceversa.\nTambién es posible que su socio requiera una versión TLS diferente o un algoritmo de encriptación diferente al que usted ofrece."},
		{"hint.SSLPeerUnverifiedException", "Nota:\nEste problema se produjo durante el handshake TLS. Por lo tanto, el sistema no ha podido establecer una conexión segura con su interlocutor, el problema no tiene nada que ver con el protocolo AS2.\nPor favor, compruebe lo siguiente:\n*¿Ha importado todos los certificados de su interlocutor a su almacén de claves TLS (para TLS, incl. certificados intermedios/root)?\n*¿Ha importado su socio todos los certificados suyos (para TLS, incl. certificados intermedios/root)?"},
		{"hint.httpcode.signals.problem", "Nota:\nSe ha establecido una conexión con su host asociado - un servidor web se está ejecutando allí.\nEl servidor remoto está señalando que algo está mal con la ruta de petición o el puerto y está devolviendo el código HTTP {0}.\nPor favor, utilice un motor de búsqueda de Internet si necesita más información sobre este código HTTP."},
		{"httpheader.deleted", "El encabezado HTTP \"{0}\" se ha eliminado debido a la configuración personalizada del encabezado HTTP"},
		{"httpheader.replaced", "El valor del encabezado HTTP \"{0}\" ha sido sustituido por el valor definido por el usuario \"{1}\""},
		{"httpheader.set", "El encabezado HTTP \"{0}\" se ha establecido en el valor definido por el usuario \"{1}\"."},
		{"returncode.accepted", "Mensaje enviado con éxito (HTTP {0}); {1} transmitido en {2} [{3}]."},
		{"returncode.ok", "Mensaje enviado con éxito (HTTP {0}); {1} enviado en {2} [{3}]."},
		{"sending.cem.async", "Enviar mensaje CEM a {0}, esperar MDN asíncrono para acuse de recibo en {1}."},
		{"sending.cem.sync", "Enviar mensaje CEM a {0}, esperar MDN síncrono para confirmar recepción."},
		{"sending.mdn.async", "Enviar acuse de recibo asíncrono (MDN) a {0}."},
		{"sending.msg.async", "Enviar mensaje AS2 a {0}, esperar MDN asíncrono para acuse de recibo en {1}."},
		{"sending.msg.sync", "Enviar mensaje AS2 a {0}, esperar MDN síncrono para acuse de recibo."},
		{"strict.hostname.check", "Para la conexión TLS saliente, se realiza una comprobación estricta del nombre de host con respecto al certificado del servidor."},
		{"strict.hostname.check.skipped.selfsigned", "TLS: Se ha omitido la comprobación estricta del nombre de host - el servidor remoto utiliza un certificado autofirmado."},
		{"trust.all.server.certificates", "La conexión TLS saliente confiará en todos los certificados del servidor remoto si los certificados raíz e intermedio están disponibles."},
		{"using.proxy", "Utilice el proxy {0}:{1}."},
		{"using.proxy.auth", "Utilizar proxy {0}:{1} (autenticación como {2})."},
                {"httpheader.added.basicauth", "Se ha añadido la cabecera HTTP para la autenticación básica a la solicitud de envío" },
	};
}
