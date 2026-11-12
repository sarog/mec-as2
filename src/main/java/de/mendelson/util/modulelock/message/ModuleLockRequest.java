//$Header: /as2/de/mendelson/util/modulelock/message/ModuleLockRequest.java 8     8/04/26 15:28 Heller $
package de.mendelson.util.modulelock.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.mendelson.util.clientserver.SerializationDummy;
import de.mendelson.util.clientserver.messages.ClientServerMessage;
import de.mendelson.util.modulelock.ModuleLock;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Msg for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 8 $
 */
public class ModuleLockRequest extends ClientServerMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Type {
        SET(1),
        RELEASE(2),
        REFRESH(3),
        LOCK_INFO(4);

        private final int id;

        Type(int id) {
            this.id = id;
        }

        @JsonValue
        public int toInt() {
            return this.id;
        }

        @JsonCreator
        public static Type of(int id) {
            for (Type type : Type.values()) {
                if (type.id == id) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown ModuleLockRequest.Type id: " + id);
        }
    }

    private ModuleLock.Module module;
    private Type type = Type.SET;

    public ModuleLockRequest(ModuleLock.Module module, ModuleLockRequest.Type type) {
        this.module = module;
        this.type = type;
    }

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public ModuleLockRequest() {
        super();
        this.module = ModuleLock.Module.SERVER_SETTINGS;
    }

    @Override
    public String toString() {
        return ("Module lock request");
    }

    /**
     * @return the type
     */
    public ModuleLockRequest.Type getType() {
        return type;
    }

    /**
     * @return the module
     */
    public ModuleLock.Module getModule() {
        return module;
    }

    /**
     * Prevent an overwrite of the readObject method for de-serialization
     */
    private void readObject(ObjectInputStream inStream) throws ClassNotFoundException, IOException {
        inStream.defaultReadObject();
    }

    /**
     * This is a dummy method for the deserialization process. Do not use in
     * logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setModule(ModuleLock.Module module) {
        this.module = module;
    }

    /**
     * This is a dummy method for the deserialization process. Do not use in
     * logic.
     */
    @SerializationDummy(reason = "This is a dummy method for client-server serialization only - do not use in logic.")
    public void setType(ModuleLockRequest.Type type) {
        this.type = type;
    }

}
