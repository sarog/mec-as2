//$Header: /as2/de/mendelson/comm/as2/modulelock/AllowConfigurationModificationCallback.java 2     26.05.14 17:59 Heller $
package de.mendelson.comm.as2.modulelock;

import de.mendelson.comm.as2.clientserver.message.ModuleLockRequest;
import de.mendelson.comm.as2.clientserver.message.ModuleLockResponse;
import de.mendelson.util.MecResourceBundle;
import de.mendelson.util.clientserver.BaseClient;
import de.mendelson.util.clientserver.AllowModificationCallback;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Handles the refresh for a locked module, executed on the client side.
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class AllowConfigurationModificationCallback implements AllowModificationCallback {

    private String moduleName;
    private JFrame parent;
    private boolean hadLockAtOpenTime;
    private BaseClient baseClient;
    private MecResourceBundle rb;

    public AllowConfigurationModificationCallback(JFrame parent, BaseClient baseClient, String moduleName, boolean hasLock) {
        this.moduleName = moduleName;
        this.parent = parent;
        this.hadLockAtOpenTime = hasLock;
        this.baseClient = baseClient;
        //Load default resourcebundle
        try {
            this.rb = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleModuleLock.class.getName());
        } //load up resourcebundle
        catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    @Override
    public boolean allowModification() {
        String moduleNameLocalized = this.rb.getResourceString( this.moduleName);
        if (!this.hadLockAtOpenTime) {
            //two different cases: 
            //*Another client is currently locking the module
            //*Another client has locked this at the opening time of this module - this requires a reopen of the module to get the current configuration
            // which might have been changed by the other client
            ModuleLockRequest request = new ModuleLockRequest(this.moduleName, ModuleLockRequest.TYPE_LOCK_INFO);
            ModuleLockResponse response = (ModuleLockResponse) this.baseClient.sendSync(request);
            ClientInformation lockKeeper = response.getLockKeeper();
            if (lockKeeper == null) {
                String text = this.rb.getResourceString( "configuration.changed.otherclient", moduleNameLocalized );
                JOptionPane.showMessageDialog(this.parent,
                        text,
                        this.rb.getResourceString( "modifications.notallowed.message" ),
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String text = this.rb.getResourceString( "configuration.locked.otherclient",
                        new Object[]{ moduleNameLocalized,
                            lockKeeper.getClientIP(),
                            lockKeeper.getUsername()
                        });
                JOptionPane.showMessageDialog(this.parent,
                        text,
                        this.rb.getResourceString( "modifications.notallowed.message" ),
                        JOptionPane.ERROR_MESSAGE);
            }
            return (false);
        }
        return (this.hadLockAtOpenTime);
    }

}
