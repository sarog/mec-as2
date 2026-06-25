//$Header: /oftp2/de/mendelson/util/clientserver/ClientServerExceptionContainer.java 4     7/11/25 12:50 Heller $
package de.mendelson.util.clientserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Client server exception that is used for the client-server interface, this is
 * type safe serializable
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class ClientServerExceptionContainer implements Serializable {

    private String originalClassName;
    private String originalMessage;
    private List<String> originalStackTrace;

    /**
     * This is a dummy constructor for the deserialization process. Do not use
     * in logic.
     */
    @SerializationDummy(reason = "This is a dummy constructor for client-server serialization only - do not use in logic.")
    public ClientServerExceptionContainer() {
    }

    public ClientServerExceptionContainer(
            String originalClassName,
            String originalMessage,
            List<String> originalStackTrace) {
        this.originalClassName = originalClassName;
        this.originalMessage = originalMessage;
        this.originalStackTrace = originalStackTrace;
    }

    public static ClientServerExceptionContainer fromThrowable(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        List<String> stackTrace = new ArrayList<String>();
        StackTraceElement[] elements = throwable.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            stackTrace.add(elements[i].toString());
        }
        return new ClientServerExceptionContainer(
                throwable.getClass().getName(),
                throwable.getMessage(),
                stackTrace);
    }

    public static ClientServerException toThrowable(ClientServerExceptionContainer container) {
        if (container == null) {
            return (null);
        }
        return (new ClientServerException(container.getOriginalClassName(),
                container.getOriginalMessage(), container.getOriginalStackTrace()));
    }

    public String getOriginalClassName() {
        return originalClassName;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public List<String> getOriginalStackTrace() {
        return originalStackTrace;
    }

    @Override
    public String toString() {
        return "Client-Server Exception: " + getOriginalClassName() + ": " + originalMessage;
    }

    /**
     * @param originalClassName the originalClassName to set
     */
    public void setOriginalClassName(String originalClassName) {
        this.originalClassName = originalClassName;
    }

    /**
     * @param originalMessage the originalMessage to set
     */
    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    /**
     * @param originalStackTrace the originalStackTrace to set
     */
    public void setOriginalStackTrace(List<String> originalStackTrace) {
        this.originalStackTrace = originalStackTrace;
    }

}
