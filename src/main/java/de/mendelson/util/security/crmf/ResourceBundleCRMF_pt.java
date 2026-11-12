//$Header: /as2/de/mendelson/util/security/crmf/ResourceBundleCRMF_pt.java 1     12/01/26 8:33 Heller $
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
public class ResourceBundleCRMF_pt extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Cancelar"},
		{"button.ok", "OK"},
		{"label.initial", "Inicialização (Pedido inicial)"},
		{"label.initial.help", "<HTML><strong>Inicialização (pedido inicial)</strong><br><br>"
			+"Selecione esta opção para o registo inicial na AC. A autenticação é efectuada através da sua palavra-passe de registo inicial.</HTML>"},
		{"label.key.encryption", "Chave de encriptação"},
		{"label.key.signature", "Chave de assinatura"},
		{"label.key.tls", "Chave TLS"},
		{"label.root.ca", "Certificado de raiz sub-CA"},
		{"label.root.ca.help", "<HTML><strong>Certificado de raiz sub-CA</strong><br><br>"
			+"O pedido CRMF gerado é encapsulado numa estrutura de mensagem CMP que contém um campo de destinatário (Recipient).<br>"
			+"Para assegurar uma transmissão em conformidade com a norma, este campo do destinatário deve conter o Nome Distinto (DN) específico da AC do espaço de nomes X.500. Este pode ser encontrado no campo \"Subject\" do certificado da própria AC (por exemplo, CN=SM-Test-PKI-DE). Isto assegura que a estrutura da mensagem gerada está em conformidade com as normas BDEW/Smart Metering PKI. O certificado de raiz da (sub) AC é necessário para uma correspondência exacta deste valor.</HTML>"},
		{"label.update", "Atualização (Pedido de atualização)"},
		{"label.update.help", "<HTML><strong>Update (Pedido de atualização)</strong><br><br>"
			+"Selecione esta opção para renovar um certificado existente antes de este expirar. A autenticação é efectuada automaticamente através do seu certificado atualmente válido.</HTML>"},
		{"password.hint", "Palavra-passe única inicial"},
		{"success.body", "O ficheiro CRMF foi guardado em {0}"},
		{"success.title", "Criação do CRMF com êxito"},
		{"title", "Criação de um inquérito CRMF (BDEW)"},
	};
}
