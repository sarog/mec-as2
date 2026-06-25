//$Header: /as2/de/mendelson/comm/as2/partner/gui/event/PartnerEventResource.java 3     11/09/25 15:41 Heller $
package de.mendelson.comm.as2.partner.gui.event;

import de.mendelson.util.MendelsonMultiResolutionImage;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Resources to display partner information - in an external class to prevent
 * image instanciation if a partner is instanciated on a headless server
 * (Problems with Debian)
 *
 * @author S.Heller
 * @version $Revision: 3 $
 */
public class PartnerEventResource {

    public static final MendelsonMultiResolutionImage IMAGE_PROCESS_EXECUTE_SHELL
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/event/external.svg", 24, 96);
    public static final MendelsonMultiResolutionImage IMAGE_PROCESS_MOVE_TO_PARTNER
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/event/send_to_singlepartner.svg", 24, 96);
    public static final MendelsonMultiResolutionImage IMAGE_PROCESS_MOVE_TO_DIR
            = MendelsonMultiResolutionImage.fromSVG("/de/mendelson/comm/as2/partner/gui/event/send_to_folder.svg", 24, 96);

    private PartnerEventResource() {
    }

}
