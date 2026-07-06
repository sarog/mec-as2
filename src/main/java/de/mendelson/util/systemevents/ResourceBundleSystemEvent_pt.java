//$Header: /as4/de/mendelson/util/systemevents/ResourceBundleSystemEvent_pt.java 6     17/02/26 11:08 Heller $
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
public class ResourceBundleSystemEvent_pt extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"category.100", "Componente do servidor"},
		{"category.1000", "Processamento de dados"},
		{"category.100000", "Outros"},
		{"category.1100", "Ativação"},
		{"category.1200", "Operação de ficheiro"},
		{"category.1300", "Funcionamento do cliente"},
		{"category.1400", "Interface XML"},
		{"category.1500", "Interface REST"},
		{"category.200", "Ligação"},
		{"category.300", "Transação"},
		{"category.400", "Certificado"},
		{"category.500", "Base de dados"},
		{"category.700", "Configuração"},
		{"category.800", "Contingente"},
		{"category.900", "Notificação"},
		{"origin.1", "Sistema"},
		{"origin.2", "Utilizadores"},
		{"origin.3", "Transação"},
		{"severity.1", "Informações"},
		{"severity.2", "Aviso"},
		{"severity.3", "Erro"},
		{"type.100", "Encerramento do servidor"},
		{"type.1000", "Processamento de dados"},
		{"type.100000", "Não especificado"},
		{"type.1001", "Pré-processamento"},
		{"type.1002", "Pós-processamento"},
		{"type.101", "Início do servidor"},
		{"type.102", "Servidor em funcionamento"},
		{"type.103", "Início do servidor DB"},
		{"type.104", "Servidor DB em execução"},
		{"type.105", "Encerramento do servidor de BD"},
		{"type.106", "Início do servidor HTTP"},
		{"type.107", "Servidor HTTP em execução"},
		{"type.108", "Encerramento do servidor HTTP"},
		{"type.109", "Início do servidor TRFC"},
		{"type.110", "Servidor TRFC em execução"},
		{"type.1100", "Licença"},
		{"type.1101", "Atualização da licença"},
		{"type.1102", "Termo da licença"},
		{"type.111", "Estado do servidor TRFC"},
		{"type.112", "Encerramento do servidor TRFC"},
		{"type.113", "O programador é iniciado"},
		{"type.114", "Programador em execução"},
		{"type.115", "Encerramento do programador"},
		{"type.116", "Monitorização de diretórios (estado alterado)"},
		{"type.117", "Porta de receção"},
		{"type.1200", "Operação de ficheiro"},
		{"type.1201", "Ficheiro (apagar)"},
		{"type.1202", "Criar diretório"},
		{"type.1203", "Ficheiro (mover)"},
		{"type.1204", "Ficheiro (cópia)"},
		{"type.1300", "Cliente"},
		{"type.1301", "Início de sessão do utilizador (sucesso)"},
		{"type.1302", "Início de sessão do utilizador (falhou)"},
		{"type.1303", "Separação de utilizadores"},
		{"type.1400", "XML"},
		{"type.1401", "Configuração do certificado"},
		{"type.1402", "Configuração de parceiros"},
		{"type.1500", "REST"},
		{"type.1501", "Adicionar certificado"},
		{"type.1502", "Configuração do certificado"},
		{"type.1503", "Eliminar o certificado"},
		{"type.1504", "Adicionar parceiro"},
		{"type.1505", "Configuração de parceiros"},
		{"type.1506", "Eliminar parceiro"},
		{"type.1507", "Enviar encomenda"},
		{"type.1508", "Transação (eliminar)"},
		{"type.199", "Componente do servidor"},
		{"type.200", "Ligação"},
		{"type.201", "Teste de ligação"},
		{"type.300", "Transação"},
		{"type.301", "Erro de transação"},
		{"type.302", "Transação (reenvio rejeitado)"},
		{"type.303", "Transação (mensagem duplicada)"},
		{"type.304", "Transação (eliminar)"},
		{"type.305", "Transação (cancelar)"},
		{"type.306", "Transação (reenvio)"},
		{"type.400", "Certificado"},
		{"type.401", "Certificado (acrescentado)"},
		{"type.402", "Certificado (pseudónimo alterado)"},
		{"type.403", "Certificado (suprimido)"},
		{"type.404", "Troca de certificados"},
		{"type.405", "O certificado expira"},
		{"type.406", "Troca de certificados (pedido de entrada)"},
		{"type.407", "Certificado (importação do Keystore)"},
		{"type.500", "Base de dados"},
		{"type.501", "Criação da base de dados"},
		{"type.502", "Base de dados (Atualização)"},
		{"type.503", "Base de dados (inicialização)"},
		{"type.504", "Transação de reversão"},
		{"type.700", "Configuração"},
		{"type.701", "Alteração da configuração"},
		{"type.702", "Verificação da configuração"},
		{"type.703", "Parceiro (modificado)"},
		{"type.704", "Parceiro (eliminado)"},
		{"type.705", "Parceiro (adicionado)"},
		{"type.800", "Quota"},
		{"type.801", "Quota atingida"},
		{"type.802", "Quota atingida"},
		{"type.900", "Notificação"},
		{"type.901", "Notificação (envio bem sucedido)"},
		{"type.902", "Notificação (falha na expedição)"},                
	};
}
