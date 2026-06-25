//$Header: /as2/de/mendelson/comm/as2/partner/PartnerCertificateInformationListDeserializer.java 2     13/06/25 11:21 Heller $
package de.mendelson.comm.as2.partner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
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
public class PartnerCertificateInformationListDeserializer extends StdDeserializer<PartnerCertificateInformationList> {

    public PartnerCertificateInformationListDeserializer() {
        super(PartnerCertificateInformationList.class);
    }

    @Override
    public PartnerCertificateInformationList deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            byte[] bytes = parser.getBinaryValue();
            return PartnerCertificateInformationList.deserialize(bytes);
        } catch (Throwable e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}
