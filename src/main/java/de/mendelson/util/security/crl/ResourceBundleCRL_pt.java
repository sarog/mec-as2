//$Header: /as4/de/mendelson/util/security/crl/ResourceBundleCRL_pt.java 7     14/01/26 14:06 Heller $
package de.mendelson.util.security.crl;

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
 * @version $Revision: 7 $
 */
public class ResourceBundleCRL_pt extends MecResourceBundle {

    private static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    private static final Object[][] CONTENTS = {
        {"malformed.crl.url", "URL incorreto da LCR ({0})"},
        {"failed.revoked", "O certificado foi revogado: {0}"},
        {"bad.crl", "Os dados descarregados da LCR não podem ser processados"},
        {"no.crl.entry", "O certificado não tem uma extensão que remeta para um URL de LCR"},
        {"self.signed.skipped", "Auto-assinado - verificação ignorada"},
        {"ca.skipped", "Raiz da CA ou âncora de confiança - verificação ignorada" },
        {"cert.read.error", "O certificado para o URL da lista de revogação não pode ser lido"},
        {"crl.success", "Ok - O certificado não foi revogado, válido até {0}"},
        {"no.https", "Problema de ligação com o URI {0} - HTTPS não é suportado"},
        {"module.name", "[lista negra]"},
        {"error.url.retrieve", "O URL da lista de revogação não pode ser lido no certificado"},
        {"download.failed.from", "A transferência da lista negra falhou ({0})"},
        {"crl.expired", "A informação CRL recebida expirou ({0})"},
        {"error.nextupdate.missing", "A informação CRL recebida não inclui qualquer informação sobre a sua validade"},
        {"error.invalid.signature", "Erro de segurança - assinatura inválida na resposta"},
        {"error.invalid.nonce", "Erro de segurança - nonce inválido na resposta"},
        {"error.request.generation", "Problema ao gerar a solicitação ({0})"},
        {"error.issuercertificate.required", "Nenhum certificado de emissor encontrado para a verificação - por favor, importe o certificado pai" },
    };
}
