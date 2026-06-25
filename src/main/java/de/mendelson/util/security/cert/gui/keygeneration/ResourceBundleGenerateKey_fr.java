//$Header: /as4/de/mendelson/util/security/cert/gui/keygeneration/ResourceBundleGenerateKey_fr.java 19    21/07/25 8:37 Heller $
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
* @version $Revision: 19 $
*/
public class ResourceBundleGenerateKey_fr extends MecResourceBundle {

	private static final long serialVersionUID = 1L;

	@Override
	public Object[][] getContents() {
		return CONTENTS;
	}
	/**List of messages in the specific language*/
	private static final Object[][] CONTENTS = {
		{"button.cancel", "Démolition"},
		{"button.ignore", "Ignorer les avertissements"},
		{"button.ok", "Ok"},
		{"button.reedit", "Réviser"},
		{"label.commonname", "Nom commun"},
		{"label.commonname.help", "<HTML><strong>Common Name</strong> (nom commun)<br><br>"
			+"Il s''agit du nom de votre domaine, tel qu''il correspond à l''enregistrement DNS. Ce paramètre est important pour le handshake d''une connexion TLS. Il est possible (mais pas recommandé !) de saisir ici une adresse IP. Il est également possible de créer un certificat wildcard en remplaçant ici des parties du domaine par *. Mais cela n''est pas non plus recommandé, car tous les partenaires n''acceptent pas de telles clés.<br>"
			+"Si vous souhaitez utiliser cette clé comme clé TLS et que cette entrée renvoie à un domaine inexistant ou ne correspond pas à votre domaine, la plupart des systèmes devraient interrompre les connexions TLS entrantes.</HTML>"},
		{"label.commonname.hint", "(nom de domaine du serveur)"},
		{"label.countrycode", "Code du pays"},
		{"label.countrycode.hint", "(2 caractères, ISO 3166)"},
		{"label.extension.ski", "Identifiant de la clé du sujet (SKI)"},
		{"label.extension.ski.help", "<HTML><strong>SKI</strong><br><br>"
			+"Il existe plusieurs façons d''identifier un certificat : à l''aide du hachage du certificat, de l''émetteur, du numéro de série et de l''identifiant de clé du demandeur (Subject Key Identifier, SKI). Le SKI fournit une identification univoque pour le demandeur du certificat et est souvent utilisé lors du travail avec la signature numérique XML ou, plus généralement, dans le domaine de la sécurité des services web. Souvent, cette extension avec l''OID 2.5.29.14 est donc nécessaire pour AS4.</HTML>"},
		{"label.keytype", "Type de clé"},
		{"label.keytype.help", "<HTML><strong>Type de clé</strong<br><br>"
			+"Il s''agit de l''algorithme de création de la clé. Pour les clés qui en résultent, il y a des avantages et des inconvénients selon l''algorithme.<br>"
			+"En 2023, nous vous recommanderions une clé RSA d''une longueur de 2048 ou 4096 bits.<br>"
			+"Il existe les types suivants :<br>"
			+"<ul><li>DSA : algorithme plus ancien, peu utilisé</li><li>RSA : standard classique, largement utilisé</li><li>ECDSA : signature efficace, courbes elliptiques</li><li>EDDSA : moderne et rapide, courbes d''Edwards</li></ul></HTML>"},
		{"label.locality", "Lieu"},
		{"label.locality.hint", "(Ville)"},
		{"label.mailaddress", "Adresse mail"},
		{"label.mailaddress.help", "<HTML><strong>Adresse e-mail</strong><br><br>"
			+"Il s''agit de l''adresse e-mail associée à la clé. Techniquement, ce paramètre n''est pas intéressant. Toutefois, si vous souhaitez faire certifier la clé, cette adresse e-mail sert généralement à la communication avec l''AC. En outre, l''adresse e-mail devrait également se trouver sur le domaine du serveur et correspondre à quelque chose comme webmaster@domain ou quelque chose de similaire, car la plupart des CA vérifient ainsi si vous êtes en possession du domaine correspondant.</HTML>"},
		{"label.namedeccurve", "Courbe"},
		{"label.namedeccurve.help", "<HTML><strong>Courbe</strong><br><br>"
			+"Permet de sélectionner le nom de la courbe elliptique à utiliser pour la génération de la clé. La longueur de clé souhaitée fait généralement partie du nom de la courbe, par exemple la clé de la courbe \"BrainpoolP256r1\" a une longueur de 256 bits. La courbe la plus utilisée en 2022 (environ 75% de tous les certificats EC sur Internet l''utilisent) est la courbe NIST P-256, que vous trouverez ici sous le nom de \"Prime256v1\". Elle est la courbe standard d''OpenSSL en 2022.</HTML>"},
		{"label.organisationname", "Organisation (nom)"},
		{"label.organisationunit", "Organisation (unité)"},
		{"label.purpose", "Extensions de clé"},
		{"label.purpose.encsign", "Cryptage et signature numérique"},
		{"label.purpose.ssl", "TLS"},
		{"label.signature", "Signature"},
		{"label.signature.help", "<HTML><strong>Signature</strong><br><br>"
			+"Il s''agit de l''algorithme de signature avec lequel la clé est signée. Il est nécessaire pour les tests d''intégrité de la clé elle-même. Ce paramètre n''a rien à voir avec les capacités de signature de la clé - vous pouvez donc par exemple créer des signatures SHA-2 avec une clé signée SHA-1 ou inversement.<br>"
			+"En 2024, nous vous recommanderions une clé signée SHA-2.<br><br>"
			+"<strong>Brève vue d''ensemble : SHA-1, SHA-2, SHA-3 et RSASSA-PSS</strong>.<br><br>"
			+"<strong>SHA-1</strong> : un algorithme de hachage plus ancien, considéré aujourd''hui comme peu sûr.<br>"
			+"<strong>SHA-2</strong> : une version plus moderne et plus sûre de SHA, qui existe en différentes variantes comme SHA-256 et SHA-512.<br>"
			+"<strong>SHA-3</strong> : le dernier algorithme de hachage, basé sur une structure différente de SHA-1 et SHA-2 et encore plus sûr contre les attaques.<br>"
			+"<strong>RSASSA-PSS (Probabilistic Signature Scheme)</strong> : il s''agit d''une extension de RSA. Elle permet de combiner la fonction de hachage SHA avec le processus de signature PSS, ce qui offre une sécurité supplémentaire.</HTML>"},
		{"label.size", "Longueur de la clé"},
		{"label.size.help", "<HTML><strong>Longueur de la clé</strong><br><br>"
			+"Il s''agit de la longueur de la clé. En principe, les opérations cryptographiques avec des clés de plus grande longueur sont plus sûres que les opérations cryptographiques avec des clés de plus petite longueur. L''inconvénient des clés de grande longueur est toutefois que les opérations cryptographiques durent beaucoup plus longtemps, ce qui peut ralentir considérablement le traitement des données en fonction de la puissance de calcul.<br>"
			+"En 2023, nous vous recommanderions une clé de 2048 ou 4096 bits.</HTML>"},
		{"label.state", "Pays"},
		{"label.subjectalternativenames", "Noms alternatifs des demandeurs"},
		{"label.validity", "Validité en jours"},
		{"label.validity.help", "<HTML><strong>Validité en jours</strong><br><br>"
			+"Cette valeur n''est intéressante que pour les clés self signed. En cas d''authentification, l''AC écrasera cette valeur.</HTML>"},
		{"title", "Création de clés"},
		{"view.basic", "Vue standard"},
		{"view.expert", "Vue d''expert"},
		{"warning.invalid.mail", "L''adresse e-mail \"{0}\" n''est pas valide."},
		{"warning.mail.in.domain", "L''adresse e-mail ne fait pas partie du domaine \"{0}\" (p. ex. monnom@{0}).\nCela peut poser un problème si la clé doit être authentifiée ultérieurement."},
		{"warning.nonexisting.domain", "Le domaine \"{0}\" n''existe pas."},
		{"warning.title", "Problème possible des paramètres clés"},
	};
}
