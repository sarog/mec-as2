//$Header: /as2/de/mendelson/util/security/cert/gui/keygeneration/ResourceBundleGenerateKey_fr.java 13    24/10/22 12:52 Heller $
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
 * ResourceBundle to localize gui entries
 * @author S.Heller
 * @version $Revision: 13 $
 */
public class ResourceBundleGenerateKey_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "G�n�rer la cl�"},
        {"button.ok", "Valider"},
        {"button.cancel", "Annuler"},
        {"label.keytype", "Type de cl�"},
        {"label.keytype.help", "<HTML><strong>Type de cl�</strong><br><br>"
            + "Il s''agit de l''algorithme de cr�ation de la cl�. Pour les cl�s qui en r�sultent, il y a des avantages et des inconv�nients selon l''algorithme.<br>"
            + "En 2022, nous vous recommanderions une cl� RSA avec une longueur de cl� de 2048 ou 4096 bits."
            + "</HTML>"
        },
        {"label.signature", "Signature"},
        {"label.signature.help", "<HTML><strong>Signature</strong><br><br>"
            + "Il s''agit de l''algorithme de signature avec lequel la cl� est sign�e. Il est n�cessaire "
            + "pour les tests d''int�grit� de la cl� elle-m�me. Ce param�tre n''a rien � voir avec les "
            + "capacit�s de signature de la cl� - vous pouvez donc par exemple cr�er des signatures SHA-2 "
            + "avec une cl� sign�e SHA-1 ou inversement.<br>"
            + "En 2022, nous vous recommandons d''utiliser une cl� sign�e SHA-2."
            + "</HTML>"
        },
        {"label.size", "Taille"},
        {"label.size.help", "<HTML><strong>Taille</strong><br><br>"
            + "Il s''agit de la longueur de la cl�. En principe, les op�rations cryptographiques avec des cl�s de plus grande "
            + "longueur sont plus s�res que les op�rations cryptographiques avec des cl�s de plus petite longueur. L''inconv�nient "
            + "des cl�s de grande longueur est que les op�rations cryptographiques durent beaucoup plus longtemps, ce qui peut "
            + "ralentir consid�rablement le traitement des donn�es en fonction de la puissance de calcul.<br>" 
            + "En 2022, nous vous recommandons une cl� de 2048 ou 4096 bits."
            + "</HTML>"
        },
        {"label.commonname", "Common name"},
        {"label.commonname.help", "<HTML><strong>Common name</strong><br><br>"
            + "Il s''agit du nom de votre domaine, tel qu''il correspond � l''enregistrement DNS. "
            + "Ce param�tre est important pour le handshake d''une connexion TLS. "
            + "Il est possible (mais pas recommand�!) de saisir ici une adresse IP. Il est �galement "
            + "possible de cr�er un certificat wildcard en rempla�ant ici des parties du domaine par *. "
            + "Mais cela n''est pas non plus recommand�, car tous les partenaires n''acceptent pas de "
            + "telles cl�s. Si vous souhaitez utiliser cette cl� comme cl� TLS et que cette entr�e "
            + "renvoie � un domaine inexistant ou ne correspond pas � votre domaine, la plupart "
            + "des syst�mes devraient interrompre les connexions TLS entrantes."
            + "</HTML>"
        },
        {"label.commonname.hint", "(Le nom de domaine)" },
        {"label.organisationunit", "Unit� d''organisation"},
        {"label.organisationname", "Nom de l''organisation"},
        {"label.locality", "Localit�"},
        {"label.locality.hint", "(City)" },
        {"label.state", "�tat"},
        {"label.countrycode", "Code pays"},
        {"label.countrycode.hint", "(2 chiffres, ISO 3166)" },
        {"label.mailaddress", "EMail"},
        {"label.mailaddress.help", "<HTML><strong>EMail</strong><br><br>"
            + "Il s''agit de l''adresse e-mail associ�e � la cl�. Techniquement, ce param�tre n''est pas important. "
            + "Toutefois, si vous souhaitez faire certifier la cl�, cette adresse e-mail sert g�n�ralement � la "
            + "communication avec l''AC. En outre, l''adresse e-mail devrait �galement se trouver sur le domaine "
            + "du serveur et correspondre � quelque chose comme webmaster@domain ou quelque chose de similaire, "
            + "car la plupart des AC v�rifient ainsi si vous �tes en possession du domaine correspondant."
            + "</HTML>"
        },
        {"label.validity", "Validit� en jours"},
        {"label.validity.help", "<HTML><strong>Validit� en jours</strong><br><br>"
            + "Cette valeur n''est int�ressante que pour les cl�s self signed. En cas d''authentification, l''AC "
            + "�crasera cette valeur."
            + "</HTML>"
        },
        {"label.purpose", "Usage cl� / utilisation de cl� suppl�mentaire"},
        {"label.purpose.encsign", "Chiffrage et signature"},
        {"label.purpose.ssl", "TLS/SSL"},
        {"label.subjectalternativenames", "Subject alternative names" },        
        {"warning.mail.in.domain", "L''adresse e-mail ne fait pas partie du domaine \"{0}\" (e.g. myname@{0}).\nCela pourrait �tre un probl�me si vous souhaitez faire confiance � la cl� plus tard."},
        {"warning.nonexisting.domain", "Le nom de domaine \"{0}\" ne semble pas exister." },
        {"warning.invalid.mail", "L''adresse mail \"{0}\" est invalide." },
        {"button.reedit", "Modifier les param�tres" },
        {"button.ignore", "Ignorer les avertissements" },
        {"warning.title", "Possible probl�me de param�tres" },
        {"view.expert", "Vue d''experts" },
        {"view.basic", "Vue de base" },
        {"label.namedeccurve", "Courbe" },
        {"label.namedeccurve.help", "<HTML><strong>Courbe</strong><br><br>"
            + "Vous choisissez ici le nom de la courbe EC qui doit �tre utilis� pour la g�n�ration "
            + "de la cl�. La longueur de cl� souhait�e fait g�n�ralement partie du nom de la courbe, "
            + "par exemple la cl� de la courbe \"BrainpoolP256r1\" a une longueur de 256 bits. La courbe "
            + "la plus utilis�e en 2022 (environ 75% de tous les certificats EC sur Internet l''utilisent) "
            + "est la courbe NIST P-256, que vous trouverez ici sous le nom \"Prime256v1\". Elle est "
            + "la courbe standard d''OpenSSL en 2022."
            + "</HTML>" },
    };
}
