//$Header: /as4/de/mendelson/util/security/cert/ResourceBundleCertificateValidity_pt.java 1     14/01/26 16:23 Heller $
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
public class ResourceBundleCertificateValidity_pt extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"state.1", "Expirado"},
		{"state.1024", "(CRL) Resposta expirada"},
		{"state.1048576", "(LCR) Problema não específico"},
		{"state.131072", "(CRL) URL não pôde ser extraído"},
		{"state.16384", "(LCR) Formato inválido"},
		{"state.2048", "(LCR) Assinatura inválida"},
		{"state.256", "(CRL) Bloqueado pela CA (Revogado)"},
		{"state.262144", "(LCR) A transferência falhou"},
		{"state.32768", "(LCR) Certificado não legível"},
		{"state.4", "Hierarquia de certificação incorrecta"},
		{"state.4096", "(CRL) Extensão em falta"},
		{"state.4194304", "Chave pública do teste de mendelson - não utilizar em operações produtivas"},
		{"state.512", "(LCR) URL não acessível"},
		{"state.524288", "(CRL) Esquema de URL não suportado"},
		{"state.8192", "(LCR) URL incorreto"},
		{"state.8388608", "(LCR) Emissor em falta - importar certificado de nível superior"},
	};
}
