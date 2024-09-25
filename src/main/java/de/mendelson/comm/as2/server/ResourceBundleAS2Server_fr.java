//$Header: /as2/de/mendelson/comm/as2/server/ResourceBundleAS2Server_fr.java 18    29/04/22 17:03 Heller $
package de.mendelson.comm.as2.server;

import de.mendelson.comm.as2.AS2ServerVersion;
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
 * @author E.Pailleau
 * @version $Revision: 18 $
 */
public class ResourceBundleAS2Server_fr extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"fatal.limited.strength", "La force principale limit�e a �t� d�tect�e dans le JVM. Veuillez installer le \"Unlimited jurisdiction key strength policy\" dossiers avant de courir le serveur " + AS2ServerVersion.getProductName() + "." },
        {"server.willstart", "{0} commence maintenant"},
        {"server.start.details", "{0} param�tre:\n\nD�marrer le serveur HTTP int�gr�: {1}\nAutoriser les connexions client-serveur � partir d''autres h�tes: {2}\nM�moire: {3}\nVersion Java : {4}\nUtilisateur du syst�me: {5}"},
        {"server.started", "D�marrage du " + AS2ServerVersion.getFullProductName() + " dans {0} ms."},
        {"server.already.running", "Une instance de " + AS2ServerVersion.getProductName() + " semble d�j� en cours.\nIl est aussi possible qu''une instance pr�c�dente du programme ne s''est pas termin�e correctement. Si vous �tes s�r qu''aucune autre instance n''est en cours\nmerci de supprimer le fichier de lock \"{0}\" (Date de d�marrage {1}) et red�marrer le serveur."},
        {"server.nohttp", "Le HTTP serveur int�gr� n''a pas �t� commenc�." },  
        {"server.startup.failed", "Il y a eu un probl�me lors du d�marrage du serveur - le d�marrage a �t� interrompu." },
        {"server.shutdown", "{0} est en train de s''�teindre." },
        {"bind.exception", "{0}\nVous avez d�fini un port qui est actuellement utilis� dans votre syst�me par un autre processus. Il peut s''agir du port client-serveur ou du port HTTP/S que vous avez d�fini dans la configuration HTTP.\nVeuillez modifier votre configuration ou arr�ter l''autre processus avant d'utiliser le {1}."},
         {"server.started.issues", "Avertissement: Des probl�mes de configuration ont �t� trouv�s {0} lors du d�marrage du serveur." },
        {"server.started.issue", "Avertissement: Un probl�me de configuration a �t� d�tect� lors du d�marrage du serveur." },
        {"ha.notavailable.subject", "Le mode haute disponibilit� n''est pas autoris�" },
        {"ha.notavailable.body", "Veuillez obtenir une licence pour le plugin HA (haute disponibilit�) pour utiliser cette fonctionnalit�." },
        {"server.hello", "C''est {0}" },
        {"server.hello.licenseexpire", "La licence expire dans {0} jours ({1}). Vous devez renouveler la licence via le support mendelson (service@mendelson.de) si vous souhaitez continuer � l''utiliser par la suite." },
         {"server.hello.licenseexpire.single", "La licence expire dans {0} jour ({1}). Vous devez renouveler la licence via le support mendelson (service@mendelson.de) si vous souhaitez continuer � l''utiliser par la suite." },
    };
}
