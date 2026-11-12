//$Header: /oftp2/de/mendelson/util/help/HelpHelper.java 6     7/04/26 16:29 Heller $
package de.mendelson.util.help;

import de.mendelson.util.displaymode.DisplayMode;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import oracle.help.Help;
import oracle.help.java.tree.TopicTreeComponent;
import oracle.help.navigator.Navigator;
import java.util.logging.Logger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Setup for the Properties Sheet
 *
 * @author S.Heller
 * @version $Revision: 6 $
 */
public class HelpHelper {

    private HelpHelper() {
    }

    /**
     * Copies the CSS files to the right position, depending on the display mode
     * to use
     *
     * @param displayMode
     * @param logger
     */
    public static void copyCSS(DisplayMode displayMode, Logger logger) {
        //copy theme CSS to the right place
        Path sourceCSS = Path.of("doc", "CSS_LIGHT.css");
        if (displayMode == DisplayMode.DARK) {
            sourceCSS = Path.of("doc", "CSS_DARK.css");
        } else if (displayMode == DisplayMode.HICONTRAST) {
            sourceCSS = Path.of("doc", "CSS_HICONTRAST.css");
        }
        try {
            Files.copy(sourceCSS, Path.of("doc", "mec_HTMLdoc.css"), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            logger.warning("The file " + Path.of("doc", "mec_HTMLdoc.css").toAbsolutePath().toString()
                    + " is r/o, unable to set the help system theme.");
        }
    }

    /**
     * Sets the right Font to all navigators
     *
     * @param help Help to customize
     * @param font Font to set
     */
    public static void setFonts(Help help, Font font) {
        for (Navigator navigator : help.getAllNavigators()) {
            if (navigator.getType().endsWith("TOCNavigator")) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        List<JComponent> treeList = getAllSubComponentsOfClass(navigator, "TopicTreeComponent");
                        for (JComponent component : treeList) {
                            ((TopicTreeComponent) component).setFont(font);
                        }
                    }
                });
            }
        }
    }

    private static List<JComponent> getAllSubComponentsOfClass(Container parentContainer, String subclassName) {
        List<JComponent> componentList = new LinkedList<JComponent>();
        for (Component component : parentContainer.getComponents()) {
            try {
                if (component.getClass().getName().endsWith(subclassName)) {
                    componentList.add((JComponent) component);
                }
                componentList.addAll(getAllSubComponentsOfClass((JComponent) component, subclassName));
            } catch (ClassCastException e) {
                continue;
            }
        }
        return (componentList);
    }
}
