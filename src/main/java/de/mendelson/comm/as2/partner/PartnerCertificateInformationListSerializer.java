//$Header: /as2/de/mendelson/comm/as2/partner/PartnerCertificateInformationListSerializer.java 2     13/06/25 11:21 Heller $
package de.mendelson.comm.as2.partner;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software. Other product
 * and brand names are trademarks of their respective owners.
 */
/**
 * Serialize a PartnerCertificateInformationList using Jackson
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class PartnerCertificateInformationListSerializer extends StdSerializer<PartnerCertificateInformationList> {

    public PartnerCertificateInformationListSerializer() {
        super(PartnerCertificateInformationList.class);
    }

    @Override
    public void serialize(PartnerCertificateInformationList partnerCertificateInformationList, JsonGenerator generator, SerializerProvider provider) throws IOException {
        try {
            byte[] bytes = partnerCertificateInformationList.serialize();
            generator.writeBinary(bytes);
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}
