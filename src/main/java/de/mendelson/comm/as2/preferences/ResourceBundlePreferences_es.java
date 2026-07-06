//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferences_es.java 5     9/09/25 16:23 Heller $
package de.mendelson.comm.as2.preferences;

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
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class ResourceBundlePreferences_es extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"button.browse", "Visite"},
        {"button.cancel", "Cancelar"},
        {"button.mailserverdetection", "Averiguar el servidor de correo"},
        {"button.modify", "Editar"},
        {"button.ok", "Ok"},
        {"button.testmail", "Enviar correo de prueba"},
        {"checkbox.notifycem", "Actos de intercambio de certificados (CEM)"},
        {"checkbox.notifycertexpire", "Antes de la expiración de los certificados"},
        {"checkbox.notifyclientserver", "Problemas con la conexión cliente-servidor"},
        {"checkbox.notifyconnectionproblem", "Para problemas de conexión"},
        {"checkbox.notifyfailure", "Tras los problemas del sistema"},
        {"checkbox.notifypostprocessing", "Problemas con el tratamiento posterior"},
        {"checkbox.notifyresend", "Tras los reenvíos rechazados"},
        {"checkbox.notifytransactionerror", "Tras errores en las transacciones"},
        {"dirmsg", "Directorio de noticias"},
        {"embedded.httpconfig.not.available", "Servidor HTTP no disponible o problemas de acceso al fichero de configuración"},
        {"event.notificationdata.modified.body", "Los datos de notificación fueron creados por\n\n{0}\n\na\n\n{1}\n\n cambiado."},
        {"event.notificationdata.modified.subject", "Se ha modificado la configuración de las notificaciones"},
        {"event.preferences.modified.body", "Valor antiguo: {0}\nNuevo valor: {1}"},
        {"event.preferences.modified.subject", "Se ha modificado el valor {0} de la configuración del servidor"},
        {"filechooser.keystore", "Seleccione el archivo de almacén de claves (formato JKS)."},
        {"filechooser.selectdir", "Seleccione el directorio que desea configurar"},
        {"header.dirname", "Tipo"},
        {"header.dirvalue", "Directorio"},
        {"info.restart.client", "Debe reiniciar el cliente para que estos cambios surtan efecto."},
        {"label.autodelete", "Eliminación automática"},
        {"label.colorblindness", "Ayuda para el daltonismo"},
        {"label.country", "País/Región"},
        {"label.country.help", "<HTML><strong>País/Región</strong><br><br>"
            + "Este ajuste sólo controla esencialmente el formato de fecha utilizado para mostrar los datos de las transacciones, etc. en el cliente.</HTML>"},
        {"label.darkmode", "Modo oscuro"},
        {"label.days", "Días"},
        {"label.deletelogdirolderthan", "Registrar datos anteriores a"},
        {"label.deletemsglog", "Borrado automático de archivos y entradas de registro"},
        {"label.deletemsglog.help", "<HTML><strong>Borrado automático de archivos y entradas de registro</strong><br><br>"
            + "En la configuración, tienes la opción de borrar archivos antiguos (mantenimiento del sistema).<br>"
            + "Si ha configurado esta opción y la activa, se registrará cada eliminación de un archivo antiguo.<br>"
            + "También se genera un evento del sistema, que puede informarle de este proceso a través de la función de notificación.</HTML>"},
        {"label.deletemsgolderthan", "Entradas de transacciones anteriores a"},
        {"label.deletestatsolderthan", "A partir de datos estadísticos más antiguos que"},
        {"label.displaymode", "Representación"},
        {"label.displaymode.help", "<HTML><strong>Pantalla</strong><br><br>"
            + "Aquí se establece uno de los modos de visualización soportados por el cliente.<br>"
            + "Esto también se puede establecer mediante parámetros de línea de comandos al llamar.</HTML>"},
        {"label.hicontrastmode", "Modo de alto contraste"},
        {"label.httpport", "Puerto de entrada HTTP"},
        {"label.httpport.help", "<HTML><strong>Puerto de entrada HTTP</strong><br><br>"
            + "Este es el puerto para las conexiones entrantes no encriptadas. Esta configuración se pasa al servidor HTTP incrustado, debe reiniciar el servidor AS2 después de un cambio.<br>"
            + "El puerto forma parte de la URL a la que su interlocutor debe enviar los mensajes AS2. Se trata de http://Host:<strong>Port</strong>/as2/HttpReceiver.<br><br>"
            + "El valor preestablecido es 8080.</HTML>"},
        {"label.httpsend.timeout", "HTTP/S Tiempo de espera de envío"},
        {"label.httpsend.timeout.help", "<HTML><strong>HTTP/S enviar tiempo de espera</strong><br><br>"
            + "Este es el valor del tiempo de espera de la conexión de red para las conexiones salientes.<br>"
            + "Si transcurrido este tiempo no se ha establecido ninguna conexión con el sistema asociado, el intento de conexión se cancela y pueden realizarse más intentos de conexión posteriormente según los ajustes de reintento.<br><br>"
            + "El valor preestablecido es 5000ms.</HTML>"},
        {"label.httpsport", "Puerto de entrada HTTPS"},
        {"label.httpsport.help", "<HTML><strong>Puerto de entrada HTTPS</strong><br><br>"
            + "Este es el puerto para las conexiones cifradas entrantes (TLS). Esta configuración se pasa al servidor HTTP incrustado, debe reiniciar el servidor AS2 después de un cambio.<br>"
            + "El puerto forma parte de la URL a la que su interlocutor debe enviar los mensajes AS2. Este es https://Host:<strong>Puerto</strong>/as2/HttpReceptor<br><br>"
            + "El valor preestablecido es 8443.</HTML>"},
        {"label.keystore.encryptionsign", "Keystore( cifrado, firma):"},
        {"label.keystore.https", "Keystore (para envío vía Https):"},
        {"label.keystore.https.pass", "Contraseña del almacén de claves (para el envío a través de Https):"},
        {"label.keystore.pass", "Contraseña del almacén de claves (cifrado/firma digital):"},
        {"label.language", "Idioma"},
        {"label.language.help", "<HTML><strong>Idioma</strong><br><br>"
            + "Es el idioma de visualización del cliente. Si ejecutas el cliente y el servidor en procesos diferentes (lo cual es recomendable), el idioma del servidor puede ser diferente.<br>"
            + "El idioma utilizado en el protocolo es siempre el del servidor.</HTML>"},
        {"label.litemode", "Modo luz"},
        {"label.loghttprequests", "Registro de peticiones HTTP desde el servidor HTTP integrado"},
        {"label.loghttprequests.help", "<HTML><strong>Protocolo de solicitud HTTP</strong><br><br>"
            + "Si está activado, el servidor HTTP integrado (Jetty) escribe un registro de peticiones en los archivos <strong>log/yyyy_MM_dd.jetty.request.log</strong>. Estos archivos de registro no son eliminados por el mantenimiento del sistema - por favor, elimínelos manualmente.<br><br>"
            + "Reinicie el software para que los cambios en esta configuración surtan efecto.</HTML>"},
        {"label.logmessageprocessing", "Registro ampliado del procesamiento de mensajes"},
        {"label.logmessageprocessing.help", "<HTML><strong>Registro avanzado del procesamiento de mensajes</strong><br><br>"
            + "Si se activa, las salidas ampliadas para procesar los mensajes se envían al registro.</HTML>"},
        {"label.logpollprocess", "Información sobre el proceso de sondeo de directorios"},
        {"label.logpollprocess.help", "<HTML><strong>Información sobre el proceso de sondeo de los directorios</strong><br><br>"
            + "Si activa esta opción, cada operación de sondeo de un directorio de salida se anota en el registro.<br>"
            + "Dado que puede tratarse de un número muy elevado de entradas, no utilice esta opción en ningún caso en funcionamiento productivo, sino sólo a modo de prueba.</HTML>"},
        {"label.mailaccount", "Cuenta del servidor de correo"},
        {"label.mailhost", "Servidor de correo (SMTP)"},
        {"label.mailhost.hint", "IP o dominio del servidor"},
        {"label.mailpass", "Contraseña del servidor de correo"},
        {"label.mailport", "Puerto"},
        {"label.mailport.help", "<HTML><strong>Puerto SMTP</strong><br><br>"
            + "Por regla general, es uno de estos valores:<br>"
            + "<strong>25</strong> (puerto estándar)<br>"
            + "<strong>465</strong> (puerto TLS, valor obsoleto)<br>"
            + "<strong>587</strong> (puerto TLS, valor por defecto)<br>"
            + "<strong>2525</strong> (puerto TLS, valor alternativo, no estándar)</HTML>"},
        {"label.mailport.hint", "Puerto SMTP"},
        {"label.max.inboundconnections", "Máximo de conexiones paralelas entrantes"},
        {"label.max.inboundconnections.help", "<HTML><strong>Máximo de conexiones paralelas entrantes</strong><br><br>"
            + "Este es el número máximo de conexiones entrantes paralelas que pueden abrirse desde el exterior a su instalación AS2 de mendelson. Este valor se aplica a todo el software y no se limita a interlocutores individuales.<br>"
            + "La configuración se transmite al servidor HTTP incrustado, debe reiniciar el servidor AS2 después de un cambio.<br><br>"
            + "Aunque es posible limitar el número de conexiones entrantes paralelas, es mejor realizar este ajuste en el cortafuegos o en el proxy de subida, ya que así se aplica a todo el sistema y no sólo a un único programa.<br><br>"
            + "El valor preestablecido es 1000.</HTML>"},
        {"label.max.outboundconnections", "Máximo de conexiones paralelas salientes"},
        {"label.max.outboundconnections.help", "<HTML><strong>Máximo de conexiones paralelas salientes</strong><br><br>"
            + "Es el número máximo de conexiones salientes paralelas que abrirá tu sistema.<br>"
            + "Este valor se utiliza principalmente para proteger su sistema asociado de la sobrecarga de conexiones entrantes de su lado.<br><br>"
            + "El valor preestablecido es 9999.</HTML>"},
        {"label.maxmailspermin", "Número máximo de notificaciones/min"},
        {"label.maxmailspermin.help", "<HTML><strong>Número máximo de notificaciones/min</strong><br><br>"
            + "Para evitar demasiados correos electrónicos, puedes resumir las notificaciones fijando el número máximo de notificaciones por minuto.<br>"
            + "Esta función le permite recibir correos que contengan varias notificaciones.</HTML>"},
        {"label.mdn.timeout", "Tiempo máximo de espera para MDN"},
        {"label.mdn.timeout.help", "<HTML><strong>Tiempo máximo de espera para MDN</strong><br><br>"
            + "El tiempo que el sistema espera una MDN (Notificación de entrega de mensaje) para un mensaje AS2 enviado antes de establecer la transacción asociada en estado \"fallido\".<br>"
            + "Este valor es válido en todo el sistema para todos los socios.<br><br>"
            + "El valor por defecto es de 30 minutos, el tiempo se cuenta a partir del momento en que se establece con éxito la conexión con el interlocutor.<br><br>"
            + "En el caso de una MDN sincrónica, la conexión con el interlocutor se mantiene abierta hasta que se recibe una MDN en el canal de retorno o ha expirado este tiempo de espera. Una vez transcurrido, la conexión se interrumpe, la transacción pasa a estado \"fallido\" y se efectúa cualquier tratamiento posterior. Esta transacción no se repite.<br><br>"
            + "En el caso de una MDN asíncrona, el sistema espera la conexión entrante del interlocutor con la MDN hasta que haya expirado este tiempo de espera. Si una vez transcurrido el tiempo de espera no se ha recibido ninguna MDN, la transacción asociada se establece como \"fallida\" y se lleva a cabo cualquier postprocesamiento definido. En este caso tampoco se repite la transacción.</HTML>"},
        {"label.min", "min"},
        {"label.notificationmail", "Destinatario de la notificación Dirección de correo"},
        {"label.notificationmail.help", "<HTML><strong>Dirección de correo electrónico del destinatario de la notificación</strong><br><br>"
            + "Dirección de correo electrónico del destinatario de la notificación.<br>"
            + "Si la notificación debe enviarse a varios destinatarios, introduzca aquí una lista de direcciones de destinatarios separada por comas.</HTML>"},
        {"label.proxy.pass", "contraseña"},
        {"label.proxy.pass.hint", "Contraseña de acceso al proxy"},
        {"label.proxy.port.hint", "Puerto"},
        {"label.proxy.url", "URL proxy"},
        {"label.proxy.url.hint", "IP o dominio del proxy"},
        {"label.proxy.use", "Utilizar un proxy HTTP para las conexiones HTTP/HTTP salientes"},
        {"label.proxy.useauthentification", "Utilizar autenticación para proxy"},
        {"label.proxy.user", "Usuarios"},
        {"label.proxy.user.hint", "Usuario de inicio de sesión proxy"},
        {"label.replyto", "Dirección de respuesta"},
        {"label.retry.max", "Número máximo de intentos para establecer una conexión"},
        {"label.retry.max.help", "<HTML><strong>Número máximo de intentos para establecer una conexión</strong><br><br>"
            + "Es el número de reintentos utilizados para repetir las conexiones con un interlocutor si no se ha podido establecer una conexión.<br>"
            + "El tiempo de espera entre estos reintentos se puede establecer en la propiedad <strong>Tiempo de espera entre reintentos de conexión</strong>.<br><br>"
            + "El valor preestablecido es 10.</HTML>"},
        {"label.retry.waittime", "Tiempo de espera entre reintentos de conexión"},
        {"label.retry.waittime.help", "<HTML><strong>Tiempo de espera entre reintentos de conexión</strong><br><br>"
            + "Es el tiempo en segundos que el sistema espera antes de volver a conectarse al interlocutor.<br>"
            + "Sólo se realiza un nuevo intento de conexión si no ha sido posible establecer una conexión con un interlocutor (por ejemplo, fallo del sistema del interlocutor o problema de infraestructura).<br>"
            + "El número de reintentos de conexión se puede configurar en la propiedad <strong>Número máximo de reintentos de conexión</strong>.<br><br>"
            + "El valor preestablecido es 30s.</HTML>"},
        {"label.sec", "s"},
        {"label.security", "Seguridad de la conexión"},
        {"label.smtpauthorization.credentials", "Usuario/contraseña"},
        {"label.smtpauthorization.header", "Autorización SMTP"},
        {"label.smtpauthorization.none", "Ninguno"},
        {"label.smtpauthorization.oauth2.authorizationcode", "OAuth2 (Código de autorización)"},
        {"label.smtpauthorization.oauth2.clientcredentials", "OAuth2 (credenciales del cliente)"},
        {"label.smtpauthorization.pass", "contraseña"},
        {"label.smtpauthorization.pass.hint", "Contraseña del servidor SMTP"},
        {"label.smtpauthorization.user", "Usuarios"},
        {"label.smtpauthorization.user.hint", "Nombre de usuario del servidor SMTP"},
        {"label.stricthostcheck", "TLS: Comprobación estricta del nombre de host"},
        {"label.stricthostcheck.help", "<HTML><strong>TLS: Comprobación estricta del nombre de host</strong><br><br>"
            + "Aquí puedes establecer si el nombre común (CN) del certificado remoto debe coincidir con el host remoto en el caso de una conexión TLS saliente.<br>"
            + "Este control sólo se aplica a los certificados notariales.</HTML>"},
        {"label.trustallservercerts", "TLS: Confíe en todos los certificados de servidor final de sus socios AS2"},
        {"label.trustallservercerts.help", "<HTML><strong>TLS: Confíe en todos los certificados de servidor final de sus socios AS2</strong><br><br>"
            + "Normalmente, TLS requiere que todos los certificados de la cadena de confianza del sistema AS2 de su socio se encuentren en su gestor de certificados TLS.<br><br>"
            + "Si activa esta opción, confiará en el certificado final de su sistema asociado al establecer una conexión saliente si sólo dispone de los certificados raíz e intermedio asociados en el gestor de certificados TLS.<br>"
            + "Tenga en cuenta que esta opción sólo tiene sentido si su pareja utiliza un certificado notarial.<br>"
            + "En cualquier caso, siempre se aceptan los certificados autofirmados.<br><br>"
            + "<strong>Atención:</strong> Activar esta opción disminuye el nivel de seguridad, ya que es posible que se produzcan ataques man-in-the-middle.</HTML>"},
        {"maintenancemultiplier.day", "Día(s)"},
        {"maintenancemultiplier.hour", "Hora(s)"},
        {"maintenancemultiplier.minute", "Minuto(s)"},
        {"receipt.subdir", "Crear subdirectorios por interlocutor para la recepción de mensajes"},
        {"receipt.subdir.help", "<HTML><strong>Subdirectorios de recepción</strong><br><br>"
            + "Establece si los datos deben recibirse en el directorio <strong>&lt;Estación local&gt;/inbox</strong> o <strong>&lt;Estación local&gt;/inbox/&lt;Nombre de socio&gt;</strong>.</HTML>"},
        {"remotedir.select", "Seleccione el directorio en el servidor"},
        {"systemmaintenance.deleteoldlogdirs.help", "<HTML><strong>Eliminación de directorios de registro antiguos</strong><br><br>"
            + "Aunque se hayan eliminado las transacciones antiguas, los procesos pueden seguir rastreándose a través de los archivos de registro existentes.<br>"
            + "Esta configuración elimina estos archivos de registro y también todos los archivos de eventos del sistema que caen dentro del mismo período de tiempo.</HTML>"},
        {"systemmaintenance.deleteoldstatistic.help", "<HTML><strong>Borrar datos estadísticos antiguos</strong><br><br>"
            + "El sistema recopila datos de compatibilidad de los sistemas asociados y puede mostrarlos en forma de estadísticas.<br>"
            + "Determina el periodo de tiempo en el que se almacenan estos datos.</HTML>"},
        {"systemmaintenance.deleteoldtransactions.help", "<HTML><strong>Borrar entradas de transacciones antiguas</strong><br><br>"
            + "Define el plazo durante el cual las transacciones y los datos temporales asociados permanecen en el sistema y se muestran en el resumen de transacciones.<br>"
            + "Estos ajustes <strong>no</strong> afectan a los datos/archivos recibidos, éstos permanecen inalterados.<br>"
            + "Para las transacciones canceladas, el registro de transacciones sigue estando disponible a través de la "
            + "función de búsqueda de registros.<br><br>"
            + "Esta configuración de mantenimiento limpiará los directorios relacionados //temp, //sent y //_rawincoming en el sistema de archivos del servidor."
            + "</HTML>"},
        {"tab.connectivity", "Conexiones"},
        {"tab.dir", "Directorios"},
        {"tab.interface", "Módulos"},
        {"tab.language", "Cliente"},
        {"tab.log", "Protocolo"},
        {"tab.maintenance", "Mantenimiento del sistema"},
        {"tab.misc", "General"},
        {"tab.notification", "Notificación"},
        {"tab.proxy", "Proxy"},
        {"tab.security", "Seguridad"},
        {"testmail", "Correo de prueba"},
        {"testmail.message.error", "Error al enviar el e-mail de prueba:\n{0}"},
        {"testmail.message.success", "Se ha enviado correctamente un correo electrónico de prueba a {0}."},
        {"testmail.title", "Enviar un correo electrónico de prueba"},
        {"title", "Ajustes"},
        {"warning.changes.canceled", "El usuario ha cancelado el diálogo de ajustes - no se ha realizado ningún cambio en los ajustes."},
        {"warning.clientrestart.required", "Los ajustes del cliente han sido modificados - por favor reinicie el cliente para que sean válidos"},
        {"warning.serverrestart.required", "Reinicie el servidor para que estos cambios surtan efecto."},};
}
