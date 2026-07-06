//$Header: /mec_oftp2/de/mendelson/util/displaymode/DisplayMode.java 5     7/04/26 16:14 Heller $
package de.mendelson.util.displaymode;

import de.mendelson.util.MendelsonMultiResolutionImage;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Store information about the display mode
 *
 * @author S.Heller
 * @version $Revision: 5 $
 */
public enum DisplayMode {

    LIGHT("LIGHT"),
    DARK("DARK"),
    HICONTRAST("HICONTRAST");

    private final String modeStr;

    /**
     * Constructor for the display mode
     *
     * @param modeStr Persistent ID, safe against obfuscation
     */
    DisplayMode(String modeStr) {
        this.modeStr = modeStr;
    }

    /**
     * Returns the str representation of the display mode
     */
    public String toDisplayStr() {
        return this.modeStr;
    }

    /**
     * Parses a string into a DisplayMode.
     *
     * * @param id The ID to parse
     * @return The matching DisplayMode or LIGHT as default
     */
    public static DisplayMode of(String modeStr) {
        if (modeStr == null) {
            return LIGHT;
        }
        for (DisplayMode mode : DisplayMode.values()) {
            if (mode.modeStr.equalsIgnoreCase(modeStr)) {
                return mode;
            }
        }
        return LIGHT;
    }

    /**
     * There are some common icons in the mendelson products that should be
     * replaced depending on the display mode. Please call this method once
     * after setting up the display mode.
     *
     * @param displayMode
     */
    public static void setupIconReplacement(DisplayMode displayMode) {
        if (displayMode == null) {
            return;
        } else if (displayMode == DARK) {
            MendelsonMultiResolutionImage.addSVGReplacement("state_finished.svg",
                    "/de/mendelson/util/displaymode/state_finished_replacement_dark.svg");
            MendelsonMultiResolutionImage.addSVGReplacement("state_stopped.svg",
                    "/de/mendelson/util/displaymode/state_stopped_replacement_dark.svg");
            MendelsonMultiResolutionImage.addSVGReplacement("help.svg",
                    "/de/mendelson/util/displaymode/help_replacement_dark.svg");
            MendelsonMultiResolutionImage.addSVGReplacement("cert_valid.svg",
                    "/de/mendelson/util/displaymode/state_finished_replacement_dark.svg");
            MendelsonMultiResolutionImage.addSVGReplacement("cert_invalid.svg",
                    "/de/mendelson/util/displaymode/state_stopped_replacement_dark.svg");
        } else if (displayMode == HICONTRAST) {
            MendelsonMultiResolutionImage.addSVGReplacement("state_finished.svg",
                    "/de/mendelson/util/displaymode/state_finished_replacement_hicontrast.svg");
            MendelsonMultiResolutionImage.addSVGReplacement("state_stopped.svg",
                    "/de/mendelson/util/displaymode/state_stopped_replacement_hicontrast.svg");
            MendelsonMultiResolutionImage.addSVGReplacement("state_allselected.svg",
                    "/de/mendelson/util/displaymode/state_allselected_replacement_hicontrast.svg");
            MendelsonMultiResolutionImage.addSVGReplacement("state_pending.svg",
                    "/de/mendelson/util/displaymode/state_pending_replacement_hicontrast.svg");
            MendelsonMultiResolutionImage.addSVGReplacement("help.svg",
                    "/de/mendelson/util/displaymode/help_replacement_hicontrast.svg");
            MendelsonMultiResolutionImage.addSVGReplacement("cert_valid.svg",
                    "/de/mendelson/util/displaymode/state_finished_replacement_hicontrast.svg");
            MendelsonMultiResolutionImage.addSVGReplacement("cert_invalid.svg",
                    "/de/mendelson/util/displaymode/state_stopped_replacement_hicontrast.svg");
        }
    }

    /**
     * Adds the color blind overlays for the common icons of the mendelson
     * products. Call this if the user requested color blind overlays
     */
    public static void addColorBlindOverlays() {
        MendelsonMultiResolutionImage.addSVGOverlay("state_finished.svg",
                "/de/mendelson/util/colorblind/overlay_state_finished.svg");
        MendelsonMultiResolutionImage.addSVGOverlay("cert_valid.svg",
                "/de/mendelson/util/colorblind/overlay_state_finished.svg");
        MendelsonMultiResolutionImage.addSVGOverlay("state_stopped.svg",
                "/de/mendelson/util/colorblind/overlay_state_stopped.svg");
        MendelsonMultiResolutionImage.addSVGOverlay("cert_invalid.svg",
                "/de/mendelson/util/colorblind/overlay_state_stopped.svg");
        MendelsonMultiResolutionImage.addSVGOverlay("state_pending.svg",
                "/de/mendelson/util/colorblind/overlay_state_pending.svg");
        MendelsonMultiResolutionImage.addSVGOverlay("state_allselected.svg",
                "/de/mendelson/util/colorblind/overlay_state_allselected.svg");
        MendelsonMultiResolutionImage.addSVGOverlay("severity_info.svg",
                "/de/mendelson/util/colorblind/overlay_severity_info.svg");
    }

}
