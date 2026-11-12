//$Header: /as2/de/mendelson/util/clientserver/codec/SerializationModule.java 5     13/06/25 11:33 Heller $
package de.mendelson.util.clientserver.codec;

import com.fasterxml.jackson.databind.module.SimpleModule;
import de.mendelson.comm.as2.partner.PartnerCertificateInformationList;
import de.mendelson.comm.as2.partner.PartnerCertificateInformationListDeserializer;
import de.mendelson.comm.as2.partner.PartnerCertificateInformationListSerializer;
import de.mendelson.util.clientserver.log.search.Logline;
import de.mendelson.util.clientserver.log.search.LoglineDeserializerAS2;
import de.mendelson.util.clientserver.log.search.ServerSideLogfileFilter;
import de.mendelson.util.clientserver.log.search.ServerSideLogfileFilterDeserializerAS2;
import de.mendelson.util.security.cert.KeystoreCertificate;
import de.mendelson.util.security.cert.KeystoreCertificateDeserializer;
import de.mendelson.util.security.cert.KeystoreCertificateSerializer;
import de.mendelson.util.security.cert.X509CertificateDeserializer;
import de.mendelson.util.security.cert.X509CertificateSerializer;
import de.mendelson.util.systemevents.notification.NotificationData;
import de.mendelson.util.systemevents.notification.NotificationDataDeserializerAS2;
import java.awt.image.BufferedImage;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.jfree.data.time.SimpleTimePeriod;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Setup a Module for the serializer/deserializer of this product
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public class SerializationModule{

    private SerializationModule() {
    }

    protected static SimpleModule initialize(){
        SimpleModule module = new SimpleModule();
        
        module.addSerializer(KeystoreCertificate.class, new KeystoreCertificateSerializer());
        module.addSerializer(PartnerCertificateInformationList.class, new PartnerCertificateInformationListSerializer());
        module.addSerializer(ImageIcon.class, new ImageIconSerializer());
        module.addSerializer(Icon.class, new IconSerializer());
        module.addSerializer(Level.class, new LogLevelSerializer());
        module.addSerializer(SimpleTimePeriod.class, new SimpleTimePeriodSerializer());
        module.addSerializer(X509Certificate.class, new X509CertificateSerializer());
        module.addSerializer(BufferedImage.class, new BufferedImageSerializer());
        
        module.addDeserializer(KeystoreCertificate.class, new KeystoreCertificateDeserializer());
        module.addDeserializer(PartnerCertificateInformationList.class, new PartnerCertificateInformationListDeserializer());
        module.addDeserializer(ImageIcon.class, new ImageIconDeserializer());
        module.addDeserializer(Icon.class, new IconDeserializer());
        module.addDeserializer(Level.class, new LogLevelDeserializer());
        module.addDeserializer(SimpleTimePeriod.class, new SimpleTimePeriodDeserializer());
        module.addDeserializer(NotificationData.class, new NotificationDataDeserializerAS2());
        module.addDeserializer(ServerSideLogfileFilter.class, new ServerSideLogfileFilterDeserializerAS2());
        module.addDeserializer(Logline.class, new LoglineDeserializerAS2());
        module.addDeserializer(X509Certificate.class, new X509CertificateDeserializer());
        module.addDeserializer(BufferedImage.class, new BufferedImageDeserializer());
        return( module );
    }
    
}
