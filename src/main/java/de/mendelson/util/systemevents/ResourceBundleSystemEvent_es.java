//$Header: /as4/de/mendelson/util/systemevents/ResourceBundleSystemEvent_es.java 6     17/02/26 11:08 Heller $
package de.mendelson.util.systemevents;

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
* @version $Revision: 6 $
*/
public class ResourceBundleSystemEvent_es extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"category.100", "Componente de servidor"},
		{"category.1000", "Tratamiento de datos"},
		{"category.100000", "Otros"},
		{"category.1100", "Activación"},
		{"category.1200", "Operación de archivo"},
		{"category.1300", "Operación cliente"},
		{"category.1400", "Interfaz XML"},
		{"category.1500", "Interfaz REST"},
		{"category.200", "Conexión"},
		{"category.300", "Transacción"},
		{"category.400", "Certificado"},
		{"category.500", "Base de datos"},
		{"category.700", "Configuración"},
		{"category.800", "Contingente"},
		{"category.900", "Notificación"},
		{"origin.1", "Sistema"},
		{"origin.2", "Usuarios"},
		{"origin.3", "Transacción"},
		{"severity.1", "Información"},
		{"severity.2", "Advertencia"},
		{"severity.3", "Error"},
		{"type.100", "Cierre del servidor"},
		{"type.1000", "Tratamiento de datos"},
		{"type.100000", "Sin especificar"},
		{"type.1001", "Preprocesamiento"},
		{"type.1002", "Tratamiento posterior"},
		{"type.101", "Inicio del servidor"},
		{"type.102", "Servidor en funcionamiento"},
		{"type.103", "Se inicia el servidor de base de datos"},
		{"type.104", "Servidor de base de datos en funcionamiento"},
		{"type.105", "Cierre del servidor de base de datos"},
		{"type.106", "Se inicia el servidor HTTP"},
		{"type.107", "Servidor HTTP en ejecución"},
		{"type.108", "Cierre del servidor HTTP"},
		{"type.109", "Inicio del servidor TRFC"},
		{"type.110", "Servidor TRFC en funcionamiento"},
		{"type.1100", "Licencia"},
		{"type.1101", "Actualización de licencias"},
		{"type.1102", "Expiración de la licencia"},
		{"type.111", "Estado del servidor TRFC"},
		{"type.112", "Cierre del servidor TRFC"},
		{"type.113", "Se inicia el programador"},
		{"type.114", "Programador en marcha"},
		{"type.115", "Apagado del programador"},
		{"type.116", "Supervisión de directorios (cambio de estado)"},
		{"type.117", "Puerto de recepción"},
		{"type.1200", "Operación de archivo"},
		{"type.1201", "Archivo (suprimir)"},
		{"type.1202", "Crear directorio"},
		{"type.1203", "Archivo (mover)"},
		{"type.1204", "Archivo (copia)"},
		{"type.1300", "Cliente"},
		{"type.1301", "Inicio de sesión de usuario (correcto)"},
		{"type.1302", "Inicio de sesión de usuario (fallido)"},
		{"type.1303", "Separación de usuarios"},
		{"type.1400", "XML"},
		{"type.1401", "Configuración del certificado"},
		{"type.1402", "Configuración de socios"},
		{"type.1500", "REST"},
		{"type.1501", "Añadir certificado"},
		{"type.1502", "Configuración del certificado"},
		{"type.1503", "Borrar certificado"},
		{"type.1504", "Añadir socio"},
		{"type.1505", "Configuración de socios"},
		{"type.1506", "Borrar socio"},
		{"type.1507", "Enviar pedido"},
		{"type.1508", "Transacción (suprimir)"},
		{"type.199", "Componente de servidor"},
		{"type.200", "Conexión"},
		{"type.201", "Prueba de conexión"},
		{"type.300", "Transacción"},
		{"type.301", "Error de transacción"},
		{"type.302", "Transacción (devolución rechazada)"},
		{"type.303", "Transacción (mensaje duplicado)"},
		{"type.304", "Transacción (suprimir)"},
		{"type.305", "Transacción (cancelar)"},
		{"type.306", "Transacción (reenvío)"},
		{"type.400", "Certificado"},
		{"type.401", "Certificado (añadido)"},
		{"type.402", "Certificado (alias modificado)"},
		{"type.403", "Certificado (suprimido)"},
		{"type.404", "Intercambio de certificados"},
		{"type.405", "Caduca el certificado"},
		{"type.406", "Intercambio de certificados (solicitud entrante)"},
		{"type.407", "Certificado (importación de almacén de claves)"},
		{"type.500", "Base de datos"},
		{"type.501", "Creación de bases de datos"},
		{"type.502", "Base de datos (actualización)"},
		{"type.503", "Base de datos (inicialización)"},
		{"type.504", "Operación de reversión"},
		{"type.700", "Configuración"},
		{"type.701", "Cambio de configuración"},
		{"type.702", "Comprobación de la configuración"},
		{"type.703", "Socio (modificado)"},
		{"type.704", "Socio (suprimido)"},
		{"type.705", "Socio (añadido)"},
		{"type.800", "Cuota"},
		{"type.801", "Cuota alcanzada"},
		{"type.802", "Cuota alcanzada"},
		{"type.900", "Notificación"},
		{"type.901", "Notificación (envío correcto)"},
		{"type.902", "Notificación (envío fallido)"},
	};
}
