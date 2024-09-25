//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferencesAS2_fr.java 7     2/08/22 15:37 Heller $
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
 * ResourceBundle to localize gui entries
 *
 * @author S.Heller
 * @version $Revision: 7 $
 */
public class ResourceBundlePreferencesAS2_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        {"TRUE", "allum�" },
        {"FALSE", "d�sactiv�" },
        {"set.to", "a �t� r�gl� sur" },
        {"setting.updated", "R�glage a �t� mis � jour" },
        {"notification.setting.updated", "Les param�tres de notification ont �t� modifi�s." },
        //preferences localized
        {PreferencesAS2.ASYNC_MDN_TIMEOUT, "D�lai d''attente pour MDN asynchrone en min"},
        {PreferencesAS2.AUTH_PROXY_PASS, "Donn�es d''acc�s au proxy HTTP (mot de passe)"},
        {PreferencesAS2.AUTH_PROXY_USE, "Utiliser les donn�es d'acc�s du proxy HTTP"},
        {PreferencesAS2.AUTH_PROXY_USER, "Donn�es d''acc�s proxy HTTP (utilisateur)"},
        {PreferencesAS2.AUTO_LOGDIR_DELETE, "Nettoyer automatiquement le r�pertoire de logs"},
        {PreferencesAS2.AUTO_LOGDIR_DELETE_OLDERTHAN, "Nettoyer le r�pertoire de logs (ant�rieur �)"},
        {PreferencesAS2.AUTO_MSG_DELETE, "Supprimer automatiquement les anciennes transactions"},
        {PreferencesAS2.AUTO_MSG_DELETE_LOG, "Supprimer les anciennes transactions (entr�e du journal)"},
        {PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN, "Supprimer les anciennes transactions (ant�rieures �)"},
        {PreferencesAS2.AUTO_MSG_DELETE_OLDERTHAN_MULTIPLIER_S, "Supprimer les anciennes transactions (unit� de temps en s)"},
        {PreferencesAS2.AUTO_STATS_DELETE, "Supprimer automatiquement les anciennes donn�es statistiques"},
        {PreferencesAS2.AUTO_STATS_DELETE_OLDERTHAN, "Supprimer les statistiques (ant�rieures �)"},
        {PreferencesAS2.CEM, "Utiliser la CEM"},
        {PreferencesAS2.COLOR_BLINDNESS, "Prise en charge du daltonisme"},
        {PreferencesAS2.COMMUNITY_EDITION, "�dition de la communaut�"},
        {PreferencesAS2.CONNECTION_RETRY_WAIT_TIME_IN_S, "Reconnexion toutes les n secondes"},
        {PreferencesAS2.COUNTRY, "Pays"},
        {PreferencesAS2.DATASHEET_RECEIPT_URL, "URL de r�ception de la fiche technique"},
        {PreferencesAS2.DIR_MSG, "R�pertoire de base pour les messages"},
        {PreferencesAS2.HTTP_SEND_TIMEOUT, "D�lai d''envoi (HTTP/S)"},
        {PreferencesAS2.KEYSTORE, "Fichier de cl� de cryptage/signature interne"},
        {PreferencesAS2.KEYSTORE_HTTPS_SEND, "Fichier de cl� TLS interne"},
        {PreferencesAS2.KEYSTORE_HTTPS_SEND_PASS, "Fichier de cl� TLS interne (mot de passe)"},
        {PreferencesAS2.KEYSTORE_PASS, "Fichier de cl� de chiffrement/signature interne (mot de passe)"},
        {PreferencesAS2.LANGUAGE, "Langue du client"},
        {PreferencesAS2.LAST_UPDATE_CHECK, "Derni�re v�rification de la nouvelle version (temps unix)"},
        {PreferencesAS2.LOG_POLL_PROCESS, "Documenter le processus Poll dans le journal"},
        {PreferencesAS2.MAX_CONNECTION_RETRY_COUNT, "Nombre de tentatives de connexion"},
        {PreferencesAS2.MAX_OUTBOUND_CONNECTIONS, "Nombre maximum de connexions sortantes simultan�es"},
        {PreferencesAS2.MAX_INBOUND_CONNECTIONS, "Nombre maximal de connexions entrantes simultan�es"}, 
        {PreferencesAS2.PROXY_HOST, "H�te proxy HTTP"},
        {PreferencesAS2.PROXY_PORT, "Port proxy HTTP"},
        {PreferencesAS2.PROXY_USE, "Utiliser un proxy HTTP pour la connexion sortante"},
        {PreferencesAS2.RECEIPT_PARTNER_SUBDIR, "Utiliser un sous-r�pertoire par partenaire"},
        {PreferencesAS2.SHOW_HTTPHEADER_IN_PARTNER_CONFIG, "Afficher la gestion des en-t�tes HTTP dans le client"},
        {PreferencesAS2.SHOW_QUOTA_NOTIFICATION_IN_PARTNER_CONFIG, "Afficher le quota dans la gestion des partenaires"},
        {PreferencesAS2.WRITE_OUTBOUND_STATUS_FILE, "Cr�er un fichier d''�tat pour chaque transaction"}, 
        {PreferencesAS2.TLS_TRUST_ALL_REMOTE_SERVER_CERTIFICATES, "(TLS) Faire confiance � tous les certificats de serveur distants"},      
        {PreferencesAS2.TLS_STRICT_HOST_CHECK, "(TLS) V�rifier l''h�te"},
        {PreferencesAS2.HTTPS_LISTEN_PORT, "Port d''entr�e HTTPS"},
        {PreferencesAS2.HTTP_LISTEN_PORT, "Port d''entr�e HTTP"},
    };
}
