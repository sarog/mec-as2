//$Header: /as4/de/mendelson/util/security/cert/gui/keygeneration/ResourceBundleGenerateKey_pt.java 3     21/07/25 8:37 Heller $
package de.mendelson.util.security.cert.gui.keygeneration;

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
* @version $Revision: 3 $
*/
public class ResourceBundleGenerateKey_pt extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Demolição"},
		{"button.ignore", "Ignorar avisos"},
		{"button.ok", "Ok"},
		{"button.reedit", "Rever"},
		{"label.commonname", "Nome comum"},
		{"label.commonname.help", "<HTML><strong>Nome Comum</strong><br><br>"
			+"Este é o nome do seu domínio tal como corresponde à entrada DNS. Este parâmetro é importante para o aperto de mão de uma ligação TLS. É possível (mas não recomendado!) introduzir um endereço IP aqui. Também é possível criar um certificado wildcard, substituindo partes do domínio por *. No entanto, isto também não é recomendado porque nem todos os parceiros aceitam este tipo de chaves.<br>"
			+"Se pretender utilizar esta chave como uma chave TLS e esta entrada se referir a um domínio inexistente ou não corresponder ao seu domínio, a maioria dos sistemas deve abortar as ligações TLS de entrada.</HTML>"},
		{"label.commonname.hint", "(Nome de domínio do servidor)"},
		{"label.countrycode", "Código do país"},
		{"label.countrycode.hint", "(2 caracteres, ISO 3166)"},
		{"label.extension.ski", "Identificador de chave de assunto (SKI)"},
		{"label.extension.ski.help", "<HTML><strong>SKI</strong><br><br>"
			+"Existem várias formas de identificar um certificado: utilizando o hash do certificado, o emissor, o número de série e o identificador de chave de assunto (SKI). O SKI fornece um identificador único para o requerente do certificado e é frequentemente utilizado quando se trabalha com a assinatura digital XML ou no domínio da segurança dos serviços Web em geral. Esta extensão com o OID 2.5.29.14 é, por conseguinte, frequentemente necessária para o AS4.</HTML>"},
		{"label.keytype", "Tipo de chave"},
		{"label.keytype.help", "<HTML><strong>Tipo de chave</strong><br><br>"
			+"Este é o algoritmo para criar a chave. Dependendo do algoritmo, existem vantagens e desvantagens para as chaves resultantes.<br>"
			+"A partir de 2023, recomendamos uma chave RSA com um comprimento de chave de 2048 ou 4096 bits.<br>"
			+"Existem os seguintes tipos:<br>"
			+"<ul><li>DSA: Algoritmo mais antigo, quase nunca utilizado</li><li>RSA: Padrão clássico, muito utilizado</li><li>ECDSA: Assinatura eficiente, curvas elípticas</li><li>EDDSA: Moderno e rápido, curvas de Edwards</li></ul></HTML>"},
		{"label.locality", "Localização"},
		{"label.locality.hint", "(Cidade)"},
		{"label.mailaddress", "Endereço postal"},
		{"label.mailaddress.help", "<HTML><strong>Endereço de correio eletrónico</strong><br><br>"
			+"Este é o endereço de correio eletrónico que está ligado à chave. Tecnicamente, este parâmetro não tem interesse. No entanto, se pretender que a chave seja autenticada, este endereço de correio eletrónico é normalmente utilizado para comunicação com a CA. Além disso, o endereço de correio eletrónico também deve estar no domínio do servidor e corresponder a algo como webmaster@domínio ou semelhante, porque a maioria das ACs utiliza-o para verificar se está na posse do domínio associado.</HTML>"},
		{"label.namedeccurve", "Curva"},
		{"label.namedeccurve.help", "<HTML><strong>Curva</strong><br><br>"
			+"Aqui seleciona o nome da curva elíptica a ser utilizada para a geração da chave. O comprimento de chave pretendido faz normalmente parte do nome da curva, por exemplo, a chave da curva \"BrainpoolP256r1\" tem um comprimento de 256 bits. A curva mais utilizada a partir de 2022 (cerca de 75% de todos os certificados EC na Internet utilizam-na) é a NIST P-256, que pode ser encontrada aqui sob o nome \"Prime256v1\". É a curva padrão do OpenSSL a partir de 2022.</HTML>"},
		{"label.organisationname", "Organização (nome)"},
		{"label.organisationunit", "Organização (Unidade)"},
		{"label.purpose", "Extensões principais"},
		{"label.purpose.encsign", "Encriptação e assinatura digital"},
		{"label.purpose.ssl", "TLS"},
		{"label.signature", "Assinatura"},
		{"label.signature.help", "<HTML><strong>Assinatura</strong><br><br>"
			+"Este é o algoritmo de assinatura com o qual a chave é assinada. É necessário para testes de integridade da própria chave. Este parâmetro não tem nada a ver com as capacidades de assinatura da chave - por exemplo, também é possível criar assinaturas SHA-2 com uma chave assinada com SHA-1 ou vice-versa.<br>"
			+"Recomendamos uma chave assinada SHA-2 a partir de 2024.<br><br>"
			+"<strong>Síntese breve: SHA-1, SHA-2, SHA-3 e RSASSA-PSS</strong><br><br>"
			+"<strong>SHA-1</strong>: Um algoritmo de hash mais antigo que agora é considerado inseguro.<br>"
			+"<strong>SHA-2</strong>: Uma versão mais moderna e segura do SHA, que existe em diferentes variantes, como SHA-256 e SHA-512.<br>"
			+"<strong>SHA-3</strong>: O algoritmo de hash mais recente, que se baseia numa estrutura diferente do SHA-1 e do SHA-2 e é ainda mais seguro contra ataques.<br>"
			+"<strong>RSASSA-PSS (Esquema de Assinatura Probabilística)</strong>: Trata-se de uma extensão do RSA. Combina a função hash SHA com o procedimento de assinatura PSS, o que proporciona segurança adicional.</HTML>"},
		{"label.size", "Comprimento da chave"},
		{"label.size.help", "<HTML><strong>Comprimento da chave</strong><br><br>"
			+"Este é o comprimento da chave. Em princípio, as operações criptográficas com um comprimento de chave mais longo são mais seguras do que as operações criptográficas com chaves de comprimento de chave mais curto. No entanto, a desvantagem dos comprimentos de chave maiores é que as operações criptográficas demoram muito mais tempo, o que pode abrandar significativamente o processamento de dados, dependendo da capacidade de computação.<br>"
			+"Recomendamos uma chave com um comprimento de 2048 ou 4096 bits a partir de 2023.</HTML>"},
		{"label.state", "País"},
		{"label.subjectalternativenames", "Nomes alternativos de candidatos"},
		{"label.validity", "Validade em dias"},
		{"label.validity.help", "<HTML><strong>Validade em dias</strong><br><br>"
			+"Este valor só tem interesse para chaves auto-assinadas. Em caso de autenticação, a CA substituirá este valor.</HTML>"},
		{"title", "Geração de chaves"},
		{"view.basic", "Vista standard"},
		{"view.expert", "Opinião de especialistas"},
		{"warning.invalid.mail", "O endereço de correio eletrónico \"{0}\" é inválido."},
		{"warning.mail.in.domain", "O endereço de correio eletrónico não faz parte do domínio \"{0}\" (por exemplo, myname@{0}).\nIsto pode ser um problema se a chave tiver de ser autenticada mais tarde."},
		{"warning.nonexisting.domain", "O domínio \"{0}\" não existe."},
		{"warning.title", "Possível problema com os parâmetros-chave"},
	};
}
