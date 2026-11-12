//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferences_pt.java 5     9/09/25 16:23 Heller $
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
public class ResourceBundlePreferences_pt extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"button.browse", "Navegar"},
        {"button.cancel", "Cancelar"},
        {"button.mailserverdetection", "Descobrir o servidor de correio eletrónico"},
        {"button.modify", "Editar"},
        {"button.ok", "Ok"},
        {"button.testmail", "Enviar correio de teste"},
        {"checkbox.notifycem", "Eventos de intercâmbio de certificados (CEM)"},
        {"checkbox.notifycertexpire", "Antes da expiração dos certificados"},
        {"checkbox.notifyclientserver", "Problemas com a ligação cliente-servidor"},
        {"checkbox.notifyconnectionproblem", "Para problemas de ligação"},
        {"checkbox.notifyfailure", "Após problemas no sistema"},
        {"checkbox.notifypostprocessing", "Problemas com o pós-processamento"},
        {"checkbox.notifyresend", "Após reenvios rejeitados"},
        {"checkbox.notifytransactionerror", "Após erros nas transacções"},
        {"dirmsg", "Diretório de notícias"},
        {"embedded.httpconfig.not.available", "Servidor HTTP não disponível ou problemas de acesso ao ficheiro de configuração"},
        {"event.notificationdata.modified.body", "Os dados da nota foram criados por\n\n{0}\n\npara\n\n{1}\n\n mudou."},
        {"event.notificationdata.modified.subject", "As definições de notificação foram alteradas"},
        {"event.preferences.modified.body", "Valor antigo: {0}\nNovo valor: {1}"},
        {"event.preferences.modified.subject", "O valor {0} das definições do servidor foi modificado"},
        {"filechooser.keystore", "Selecione o ficheiro do armazenamento de chaves (formato JKS)."},
        {"filechooser.selectdir", "Selecione o diretório a definir"},
        {"header.dirname", "Tipo"},
        {"header.dirvalue", "Diretório"},
        {"info.restart.client", "É necessário reiniciar o cliente para que estas alterações tenham efeito!"},
        {"label.autodelete", "Eliminação automática"},
        {"label.colorblindness", "Apoio ao daltonismo"},
        {"label.country", "País/Região"},
        {"label.country.help", "<HTML><strong>País/Região</strong><br><br>"
            + "Esta definição controla essencialmente apenas o formato da data utilizado para apresentar os dados da transação, etc. no cliente.</HTML>"},
        {"label.darkmode", "Modo escuro"},
        {"label.days", "Dias"},
        {"label.deletelogdirolderthan", "Dados de registo mais antigos que"},
        {"label.deletemsglog", "Apagamento automático de ficheiros e entradas de registo"},
        {"label.deletemsglog.help", "<HTML><strong>Apagamento automático de ficheiros e entradas de registo</strong><br><br>"
            + "Nas definições, tem a opção de apagar ficheiros antigos (manutenção do sistema).<br>"
            + "Se tiver configurado esta opção e a ativar, cada eliminação de um ficheiro antigo é registada.<br>"
            + "É também gerado um evento de sistema, que pode informar o utilizador sobre este processo através da função de notificação.</HTML>"},
        {"label.deletemsgolderthan", "Entradas de transacções mais antigas que"},
        {"label.deletestatsolderthan", "A partir de dados estatísticos mais antigos que"},
        {"label.displaymode", "Representação"},
        {"label.displaymode.help", "<HTML><strong>Apresentação</strong><br><br>"
            + "Aqui define-se um dos modos de visualização suportados pelo cliente.<br>"
            + "Isto também pode ser definido através de parâmetros da linha de comando aquando da chamada.</HTML>"},
        {"label.hicontrastmode", "Modo de alto contraste"},
        {"label.httpport", "Porta de entrada HTTP"},
        {"label.httpport.help", "<HTML><strong>Porta de entrada HTTP</strong><br><br>"
            + "Esta é a porta para ligações não encriptadas de entrada. Esta definição é transmitida ao servidor HTTP incorporado, é necessário reiniciar o servidor AS2 após uma alteração.<br>"
            + "A porta faz parte do URL para o qual o seu parceiro deve enviar mensagens AS2. Isto é http://Host:<strong>Port</strong>/as2/HttpReceiver.<br><br>"
            + "O valor predefinido é 8080.</HTML>"},
        {"label.httpsend.timeout", "Tempo limite de envio HTTP/S"},
        {"label.httpsend.timeout.help", "<HTML><strong>Tempo limite de envio HTTP/S</strong><br><br>"
            + "Este é o valor do tempo limite da ligação de rede para as ligações de saída.<br>"
            + "Se, após este período, não tiver sido estabelecida qualquer ligação ao sistema do seu parceiro, a tentativa de ligação é cancelada e podem ser efectuadas mais tarde outras tentativas de ligação, de acordo com as definições de repetição.<br><br>"
            + "O valor predefinido é 5000 ms.</HTML>"},
        {"label.httpsport", "Porta de entrada HTTPS"},
        {"label.httpsport.help", "<HTML><strong>Porta de entrada HTTPS</strong><br><br>"
            + "Esta é a porta para ligações encriptadas de entrada (TLS). Esta definição é transmitida ao servidor HTTP incorporado, é necessário reiniciar o servidor AS2 após uma alteração.<br>"
            + "A porta faz parte do URL para o qual o seu parceiro deve enviar mensagens AS2. Isto é https://Host:<strong>Port</strong>/as2/HttpReceiver<br><br>"
            + "O valor predefinido é 8443.</HTML>"},
        {"label.keystore.encryptionsign", "Armazenamento de chaves( encriptação, assinatura):"},
        {"label.keystore.https", "Keystore (para envio via Https):"},
        {"label.keystore.https.pass", "Palavra-passe do Keystore (para envio via Https):"},
        {"label.keystore.pass", "Palavra-passe do Keystore (encriptação/assinatura digital):"},
        {"label.language", "Língua"},
        {"label.language.help", "<HTML><strong>Língua</strong><br><br>"
            + "Este é o idioma de apresentação do cliente. Se executar o cliente e o servidor em processos diferentes (o que é recomendado), o idioma do servidor pode ser diferente.<br>"
            + "A língua utilizada no protocolo é sempre a língua do servidor.</HTML>"},
        {"label.litemode", "Modo de luz"},
        {"label.loghttprequests", "Registo de pedidos HTTP do servidor HTTP integrado"},
        {"label.loghttprequests.help", "<HTML><strong>Protocolo de pedido HTTP</strong><br><br>"
            + "Se estiver ativado, o servidor HTTP incorporado (Jetty) escreve um registo de pedidos nos ficheiros <strong>log/yyyy_MM_dd.jetty.request.log</strong>. Estes ficheiros de registo não são eliminados pela manutenção do sistema - por favor, elimine-os manualmente.<br><br>"
            + "Reinicie o software para que as alterações a esta definição tenham efeito.</HTML>"},
        {"label.logmessageprocessing", "Registo alargado do processamento de mensagens"},
        {"label.logmessageprocessing.help", "<HTML><strong>Registo avançado do processamento de mensagens</strong><br><br>"
            + "Se ativado, as saídas alargadas para o processamento das mensagens são enviadas para o registo.</HTML>"},
        {"label.logpollprocess", "Informações sobre o processo de sondagem de diretórios"},
        {"label.logpollprocess.help", "<HTML><strong>Informações sobre o processo de sondagem dos diretórios</strong><br><br>"
            + "Se ativar esta opção, cada operação de sondagem de um diretório de saída é anotada no registo.<br>"
            + "Uma vez que este número de entradas pode ser muito elevado, não utilize esta opção em circunstância alguma no funcionamento produtivo, mas apenas para efeitos de teste.</HTML>"},
        {"label.mailaccount", "Conta do servidor de correio eletrónico"},
        {"label.mailhost", "Servidor de correio eletrónico (SMTP)"},
        {"label.mailhost.hint", "IP ou domínio do servidor"},
        {"label.mailpass", "Palavra-passe do servidor de correio eletrónico"},
        {"label.mailport", "Porto"},
        {"label.mailport.help", "<HTML><strong>Porta SMTP</strong><br><br>"
            + "Regra geral, é um destes valores:<br>"
            + "<strong>25</strong> (porta padrão)<br>"
            + "<strong>465</strong> (porta TLS, valor obsoleto)<br>"
            + "<strong>587</strong> (porta TLS, valor predefinido)<br>"
            + "<strong>2525</strong> (porta TLS, valor alternativo, sem norma)</HTML>"},
        {"label.mailport.hint", "Porta SMTP"},
        {"label.max.inboundconnections", "Máximo de ligações paralelas de entrada"},
        {"label.max.inboundconnections.help", "<HTML><strong>Máximo de ligações paralelas de entrada</strong><br><br>"
            + "Este é o número máximo de conexões de entrada paralelas que podem ser abertas do exterior para a sua instalação do mendelson AS2. Este valor se aplica a todo o software e não se limita a parceiros individuais.<br>"
            + "A definição é transmitida para o servidor HTTP incorporado, é necessário reiniciar o servidor AS2 após uma alteração.<br><br>"
            + "Embora seja possível limitar o número de ligações de entrada paralelas, é preferível definir esta opção na sua firewall ou no seu proxy a montante - isto aplica-se a todo o seu sistema e não apenas a uma única peça de software.<br><br>"
            + "O valor predefinido é 1000.</HTML>"},
        {"label.max.outboundconnections", "Máximo de ligações paralelas de saída"},
        {"label.max.outboundconnections.help", "<HTML><strong>Máximo de conexões paralelas de saída</strong><br><br>"
            + "Este é o número máximo de ligações de saída paralelas que o seu sistema irá abrir.<br>"
            + "Este valor é utilizado principalmente para proteger o sistema do seu parceiro de ser sobrecarregado por ligações de entrada do seu lado.<br><br>"
            + "O valor predefinido é 9999.</HTML>"},
        {"label.maxmailspermin", "Número máximo de notificações/min"},
        {"label.maxmailspermin.help", "<HTML><strong>Número máximo de notificações/min</strong><br><br>"
            + "Para evitar demasiadas mensagens de correio eletrónico, pode resumir as notificações definindo o número máximo de notificações por minuto.<br>"
            + "Esta função permite-lhe receber mensagens de correio eletrónico que contêm várias notificações.</HTML>"},
        {"label.mdn.timeout", "Tempo máximo de espera para a MDN"},
        {"label.mdn.timeout.help", "<HTML><strong>Tempo máximo de espera para MDNs</strong><br><br>"
            + "O tempo que o sistema espera por uma MDN (Message Delivery Notification) para uma mensagem AS2 enviada antes de colocar a transação associada no estado \"failed\".<br>"
            + "Este valor é válido em todo o sistema para todos os parceiros.<br><br>"
            + "O valor predefinido é 30 minutos, o tempo é contado a partir do momento em que a ligação ao parceiro é estabelecida com êxito.<br><br>"
            + "No caso de uma MDN síncrona, a ligação ao parceiro mantém-se aberta até que seja recebida uma MDN no canal de retorno ou até que o tempo de espera tenha expirado. Uma vez expirado, a ligação é terminada, a transação é colocada no estado \"failed\" e é efectuado qualquer pós-processamento. Esta transação não é repetida.<br><br>"
            + "No caso de uma MDN assíncrona, o sistema aguarda a ligação de entrada do parceiro com a MDN até que este tempo de espera tenha expirado. Se nenhuma MDN tiver sido recebida após o tempo de espera ter expirado, a transação associada é definida como \"failed\" e qualquer pós-processamento definido é executado. A transação também não é repetida aqui.</HTML>"},
        {"label.min", "min"},
        {"label.notificationmail", "Destinatário da notificação Endereço de correio eletrónico"},
        {"label.notificationmail.help", "<HTML><strong>Endereço de correio eletrónico do destinatário da notificação</strong><br><br>"
            + "O endereço de correio eletrónico do destinatário da notificação.<br>"
            + "Se a notificação tiver de ser enviada a vários destinatários, introduza aqui uma lista de endereços de destinatários separada por vírgulas.</HTML>"},
        {"label.proxy.pass", "palavra-passe"},
        {"label.proxy.pass.hint", "Palavra-passe de início de sessão do proxy"},
        {"label.proxy.port.hint", "Porto"},
        {"label.proxy.url", "URL de proxy"},
        {"label.proxy.url.hint", "IP ou domínio do proxy"},
        {"label.proxy.use", "Utilizar o proxy HTTP para ligações HTTP/HTTPs de saída"},
        {"label.proxy.useauthentification", "Utilizar autenticação para proxy"},
        {"label.proxy.user", "Utilizadores"},
        {"label.proxy.user.hint", "Utilizador de início de sessão de proxy"},
        {"label.replyto", "Endereço para resposta"},
        {"label.retry.max", "Número máximo de tentativas para estabelecer uma ligação"},
        {"label.retry.max.help", "<HTML><strong>Número máximo de tentativas para estabelecer uma ligação</strong><br><br>"
            + "Este é o número de tentativas utilizadas para repetir as ligações a um parceiro se não for possível estabelecer uma ligação.<br>"
            + "O tempo de espera entre estas tentativas pode ser definido na propriedade <strong>Tempo de espera entre tentativas de ligação</strong>.<br><br>"
            + "O valor predefinido é 10.</HTML>"},
        {"label.retry.waittime", "Tempo de espera entre novas tentativas de ligação"},
        {"label.retry.waittime.help", "<HTML><strong>Tempo de espera entre novas tentativas de ligação</strong><br><br>"
            + "Este é o tempo em segundos que o sistema espera antes de voltar a ligar ao parceiro.<br>"
            + "Só é efectuada uma nova tentativa de ligação se não for possível estabelecer uma ligação a um parceiro (por exemplo, falha do sistema do parceiro ou problema de infraestrutura).<br>"
            + "O número de tentativas de ligação pode ser configurado na propriedade <strong>Número máximo de tentativas de ligação</strong>.<br><br>"
            + "O valor predefinido é 30s.</HTML>"},
        {"label.sec", "s"},
        {"label.security", "Segurança da ligação"},
        {"label.smtpauthorization.credentials", "Utilizador/palavra-passe"},
        {"label.smtpauthorization.header", "Autorização SMTP"},
        {"label.smtpauthorization.none", "Nenhum"},
        {"label.smtpauthorization.oauth2.authorizationcode", "OAuth2 (Código de autorização)"},
        {"label.smtpauthorization.oauth2.clientcredentials", "OAuth2 (credenciais do cliente)"},
        {"label.smtpauthorization.pass", "palavra-passe"},
        {"label.smtpauthorization.pass.hint", "Palavra-passe do servidor SMTP"},
        {"label.smtpauthorization.user", "Utilizadores"},
        {"label.smtpauthorization.user.hint", "Nome do utilizador do servidor SMTP"},
        {"label.stricthostcheck", "TLS: Controlo rigoroso do nome do anfitrião"},
        {"label.stricthostcheck.help", "<HTML><strong>TLS: Verificação rigorosa do nome do anfitrião</strong><br><br>"
            + "Aqui pode definir se o nome comum (CN) do certificado remoto deve corresponder ao anfitrião remoto no caso de uma ligação TLS de saída.<br>"
            + "Este controlo só se aplica aos certificados autenticados.</HTML>"},
        {"label.trustallservercerts", "TLS: Confie em todos os certificados de servidor final dos seus parceiros AS2"},
        {"label.trustallservercerts.help", "<HTML><strong>TLS: Confie em todos os certificados de servidor final dos seus parceiros AS2</strong><br><br>"
            + "Normalmente, o TLS requer que todos os certificados da cadeia de confiança do sistema AS2 do seu parceiro sejam mantidos no seu gestor de certificados TLS.<br><br>"
            + "Se ativar esta opção, confia no certificado final do sistema do seu parceiro ao estabelecer uma ligação de saída se apenas detiver os certificados raiz e intermédio associados no gestor de certificados TLS.<br>"
            + "Note-se que esta opção só faz sentido se o seu parceiro utilizar um certificado autenticado.<br>"
            + "De qualquer forma, os certificados auto-assinados são sempre aceites.<br><br>"
            + "<strong>Aviso:</strong> A ativação desta opção reduz o nível de segurança, uma vez que são possíveis ataques man-in-the-middle.</HTML>"},
        {"maintenancemultiplier.day", "Dia(s)"},
        {"maintenancemultiplier.hour", "Hora(s)"},
        {"maintenancemultiplier.minute", "Minuto(s)"},
        {"receipt.subdir", "Criar subdirectórios por parceiro para a receção de mensagens"},
        {"receipt.subdir.help", "<HTML><strong>Subdirectórios de receção</strong><br><br>"
            + "Define se os dados devem ser recebidos no diretório <strong>&lt;Local station&gt;/inbox</strong> ou <strong>&lt;Local station&gt;/inbox/&lt;Partner name&gt;</strong>.</HTML>"},
        {"remotedir.select", "Selecionar o diretório no servidor"},
        {"systemmaintenance.deleteoldlogdirs.help", "<HTML><strong>Apagar diretórios de registo antigos</strong><br><br>"
            + "Mesmo que as transacções antigas tenham sido eliminadas, os processos ainda podem ser rastreados através dos ficheiros de registo existentes.<br>"
            + "Esta definição elimina estes ficheiros de registo e também todos os ficheiros para eventos do sistema que se enquadrem no mesmo período de tempo.</HTML>"},
        {"systemmaintenance.deleteoldstatistic.help", "<HTML><strong>Apagar dados estatísticos antigos</strong><br><br>"
            + "O sistema recolhe dados de compatibilidade dos sistemas parceiros e pode apresentá-los como estatísticas.<br>"
            + "Isto determina o período de tempo em que estes dados são armazenados.</HTML>"},
        {"systemmaintenance.deleteoldtransactions.help", "<HTML><strong>Apagar entradas de transacções antigas</strong><br><br>"
            + "Isso define o período de tempo em que as transações e os dados temporários associados permanecem no sistema e são exibidos na síntese de transações.<br>"
            + "Estas definições não afectam os dados/ficheiros recebidos, pois estes não são afectados.<br>"
            + "Para transacções canceladas, o registo de transacções continua disponível "
            + "através da funcionalidade de pesquisa de registos.<br><br>"
            + "Esta configuração de manutenção irá limpar os diretórios relacionados //temp, //sent e //_rawincoming no sistema de ficheiros do servidor."
            + "</HTML>"},
        {"tab.connectivity", "Ligações"},
        {"tab.dir", "Diretórios"},
        {"tab.interface", "Módulos"},
        {"tab.language", "Cliente"},
        {"tab.log", "Protocolo"},
        {"tab.maintenance", "Manutenção do sistema"},
        {"tab.misc", "Geral"},
        {"tab.notification", "Notificação"},
        {"tab.proxy", "Proxy"},
        {"tab.security", "Segurança"},
        {"testmail", "Correio de teste"},
        {"testmail.message.error", "Erro ao enviar a mensagem de correio eletrónico de teste:\n{0}"},
        {"testmail.message.success", "Foi enviada com êxito uma mensagem de correio eletrónico de teste para {0}."},
        {"testmail.title", "Envio de uma mensagem de correio eletrónico de teste"},
        {"title", "Definições"},
        {"warning.changes.canceled", "O utilizador cancelou o diálogo de definições - não foram efectuadas quaisquer alterações às definições."},
        {"warning.clientrestart.required", "As definições do cliente foram alteradas - reinicie o cliente para as tornar válidas"},
        {"warning.serverrestart.required", "Reinicie o servidor para que estas alterações tenham efeito."},};
}
