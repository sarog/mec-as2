//$Header: /as2/de/mendelson/comm/as2/partner/gui/filter/DialogFilterPartner.java 3     23/03/26 14:37 Heller $
package de.mendelson.comm.as2.partner.gui.filter;

import de.mendelson.util.MecResourceBundle;
import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Partner filter dialog
 *
 * @author S.Heller
 * @version $Revision: 3 $
 */
public class DialogFilterPartner extends JDialog {

    private static final MecResourceBundle rb;

    static {
        try {
            rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleFilterPartner.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }
    private boolean canceled = true;
    private final KeyEventDispatcher keyEventDispatcherESC;
    private final KeyEventDispatcher keyEventDispatcherEnter;
    private final ComponentListener componentListenerParent;
    private final JDialog parentDialog;
    private final JButton jButtonFilter;
    private AWTEventListener clickOutsideListener = null;
    private final Consumer<FilterData> filterConsumer;
    private final Timer keystrokeThrottleTimer;

    private static final List<String> CATEGORY_LIST
            = List.<String>of(
                    "category.as2id",
                    "category.comment",                    
                    "category.name",
                    "category.url",                    
                    "category.subject"
            );
    private static final int CATEGORY_PRESELECTION_INDEX = 2;

    /**
     * Creates new form WizardNewPartner
     */
    public DialogFilterPartner(JDialog parentDialog, JButton jButtonFilter, Consumer<FilterData> filterConsumer) {
        super(parentDialog, false);
        this.parentDialog = parentDialog;
        this.jButtonFilter = jButtonFilter;
        this.filterConsumer = filterConsumer;
        this.setUndecorated(true);
        this.setResizable(false);
        initComponents();
        this.pack();
        for (String category : CATEGORY_LIST) {
            this.jComboBoxCategory.addItem(rb.getResourceString(category));
        }
        this.jComboBoxCategory.setSelectedIndex(CATEGORY_PRESELECTION_INDEX);
        this.jTextFieldSearchValue.requestFocusInWindow();
        this.jTextFieldSearchValue.selectAll();
        this.keyEventDispatcherESC = new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        setVisible(false);
                    }
                }
                return false;
            }
        };
        this.keyEventDispatcherEnter = new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        performSearch();
                        setVisible(false);
                    }
                }
                return false;
            }
        };
        this.componentListenerParent = new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                Point locationPoint = computeDialogPosition();
                setLocation(locationPoint);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                Point locationPoint = computeDialogPosition();
                setLocation(locationPoint);
            }

            @Override
            public void componentShown(ComponentEvent e) {
                Point locationPoint = computeDialogPosition();
                setLocation(locationPoint);
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                DialogFilterPartner.this.setVisible(false);
            }
        };
        //The popup compnent of the JCombobox is outside of the dialog - do not close the dialog on click then
        this.jComboBoxCategory.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                removeClickOutsideListener();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                addClickOutsideListener();
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
        final int delayMs = 200;
        keystrokeThrottleTimer = new Timer(delayMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Stop the timer once the action is executed
                DialogFilterPartner.this.keystrokeThrottleTimer.stop();
                performSearch();
            }
        });
        this.addDocumentListenerToTextField();
        this.jComboBoxCategory.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    performSearch();
                }
            }
        });
    }

    /**
     * Computes the dialog position which is relative to the given button. Its
     * required to recompute the position if the parent is moved
     *
     * @return
     */
    private Point computeDialogPosition() {
        Point buttonLocationOnScreen = jButtonFilter.getLocationOnScreen();
        Dimension buttonSize = jButtonFilter.getSize();
        return (new Point(buttonLocationOnScreen.x + buttonSize.width, buttonLocationOnScreen.y));
    }

    private void addDocumentListenerToTextField() {
        this.jTextFieldSearchValue.getDocument().addDocumentListener(new DocumentListener() {
            private void triggerFilter() {
                // Restart the timer on every keystroke
                if (keystrokeThrottleTimer.isRunning()) {
                    keystrokeThrottleTimer.restart();
                } else {
                    keystrokeThrottleTimer.start();
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                triggerFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                triggerFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    /**
     * Call this to remove the currently set value
     */
    public void clearSearchStr() {
        this.jTextFieldSearchValue.setText("");
    }

    private void performSearch() {
        if (DialogFilterPartner.this.filterConsumer != null) {
            String searchValue = DialogFilterPartner.this.jTextFieldSearchValue.getText();
            int categoryIndex = this.jComboBoxCategory.getSelectedIndex();
            if (categoryIndex != -1) {
                String category = CATEGORY_LIST.get(categoryIndex);
                FilterData filterData = new FilterData(searchValue, category);
                DialogFilterPartner.this.filterConsumer.accept(filterData);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelMain = new javax.swing.JPanel();
        jComboBoxCategory = new javax.swing.JComboBox();
        jTextFieldSearchValue = new javax.swing.JTextField();
        jPanelButtons = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelMain.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelMain.setLayout(new java.awt.GridBagLayout());

        jComboBoxCategory.setMinimumSize(new java.awt.Dimension(120, 24));
        jComboBoxCategory.setPreferredSize(new java.awt.Dimension(120, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanelMain.add(jComboBoxCategory, gridBagConstraints);

        jTextFieldSearchValue.setMinimumSize(new java.awt.Dimension(200, 22));
        jTextFieldSearchValue.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanelMain.add(jTextFieldSearchValue, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanelMain, gridBagConstraints);

        jPanelButtons.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanelButtons, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBoxCategory;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JTextField jTextFieldSearchValue;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the canceled
     */
    public boolean isCanceled() {
        return (this.canceled);
    }

    @Override
    public void setVisible(boolean flag) {
        super.setVisible(flag);
        if (flag) {
            Point locationPoint = this.computeDialogPosition();
            this.setLocation(locationPoint);
            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .addKeyEventDispatcher(this.keyEventDispatcherESC);
            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .addKeyEventDispatcher(this.keyEventDispatcherEnter);
            this.parentDialog.addComponentListener(this.componentListenerParent);
            this.addClickOutsideListener();
        } else {
            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .removeKeyEventDispatcher(this.keyEventDispatcherESC);
            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .removeKeyEventDispatcher(this.keyEventDispatcherEnter);
            this.parentDialog.removeComponentListener(this.componentListenerParent);
            this.removeClickOutsideListener();
        }
    }

    private void addClickOutsideListener() {
        this.clickOutsideListener = new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event.getID() == MouseEvent.MOUSE_PRESSED) {
                    MouseEvent mouseEvent = (MouseEvent) event;
                    Rectangle dialogBounds = DialogFilterPartner.this.getBounds();
                    Point clickPoint = mouseEvent.getLocationOnScreen();
                    if (!dialogBounds.contains(clickPoint) && DialogFilterPartner.this.isVisible()) {
                        Rectangle buttonBounds = jButtonFilter.getBounds();
                        Point buttonLocationOnScreen = jButtonFilter.getLocationOnScreen();
                        buttonBounds.setLocation(buttonLocationOnScreen);
                        if (!buttonBounds.contains(clickPoint)) {
                            DialogFilterPartner.this.setVisible(false);
                        }
                    }
                }
            }
        };
        Toolkit.getDefaultToolkit().addAWTEventListener(
                this.clickOutsideListener,
                AWTEvent.MOUSE_EVENT_MASK
        );
    }

    private void removeClickOutsideListener() {
        if (this.clickOutsideListener != null) {
            Toolkit.getDefaultToolkit().removeAWTEventListener(this.clickOutsideListener);
            this.clickOutsideListener = null;
        }
    }

    /**
     * Data Transfer Object (DTO) to bundle the search value and the selected
     * category for the Consumer function.
     */
    public static class FilterData {

        private final String searchValue;
        private final String category;

        /**
         * @param searchValue The text currently in the search field.
         * @param category The selected filter category which is the rb key
         */
        public FilterData(String searchValue, String category) {
            this.searchValue = searchValue;
            this.category = category;
        }

        public String getSearchValue() {
            return searchValue;
        }

        public String getCategory() {
            return category;
        }
    }

}
