//$Header: /as2/de/mendelson/util/modulelock/AllowConfigurationModificationCallback.java 6     8/04/26 15:28 Heller $
package de.mendelson.util.modulelock;

import de.mendelson.util.modulelock.message.ModuleLockRequest;
import de.mendelson.util.modulelock.message.ModuleLockResponse;
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
 * @version $Revision: 6 $
 */
public class AllowConfigurationModificationCallback implements AllowModificationCallback {

    private final ModuleLock.Module module;
    private final JFrame parent;
    private final boolean hadLockAtOpenTime;
    private final BaseClient baseClient;
    private final MecResourceBundle rb;

    public AllowConfigurationModificationCallback(JFrame parent, BaseClient baseClient, ModuleLock.Module module, boolean hasLock) {
        this.module = module;
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
    public boolean allowModification(boolean silent) {
        if( silent ){
            return(this.hadLockAtOpenTime);
        }else{
            if (!this.hadLockAtOpenTime) {
                this.displayLockinformationDialog();
            }
            return(this.hadLockAtOpenTime);
        }        
    }

    /**
     * Displays a dialog with lock information - only useful if the caller has
     * not the lock
     *
     */
    public void displayLockinformationDialog(){
        //two different cases: 
        //*Another client is currently locking the module
        //*Another client has locked this at the opening time of this module - this requires a reopen of the module to get the current configuration
        // which might have been changed by the other client
        String moduleNameLocalized = this.rb.getResourceString(this.module.toDisplayStr());
        ModuleLockRequest request = new ModuleLockRequest(this.module, ModuleLockRequest.Type.LOCK_INFO);
        ModuleLockResponse response = (ModuleLockResponse) this.baseClient.sendSync(request);
        LockClientInformation lockKeeper = response.getLockKeeper();
        if (lockKeeper == null) {
            String text = this.rb.getResourceString("configuration.changed.otherclient", moduleNameLocalized);
            JOptionPane.showMessageDialog(this.parent,
                    text,
                    this.rb.getResourceString("modifications.notallowed.message"),
                    JOptionPane.ERROR_MESSAGE);
        } else {
            ModuleLock.displayDialogModuleLocked(this.parent, lockKeeper, this.module);
        }

    }

}
