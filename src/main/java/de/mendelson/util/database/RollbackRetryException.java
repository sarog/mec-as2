//$Header: /oftp2/de/mendelson/util/database/RollbackRetryException.java 4     12/11/25 10:52 Heller $
package de.mendelson.util.database;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Stores some information of the used jdbc driver - just for information
 * purpose
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class RollbackRetryException extends Exception {

    private static final long serialVersionUID = 1L;
    /**
     * The transaction name of the transaction where this happened - this is
     * mainly for debug purpose or additional information
     */
    private String transactionName;
    private int attempt = 1;

    public RollbackRetryException(String transactionName) {
        super();
        this.transactionName = transactionName;
    }

    /**
     * Constructs a new RollbackRetryException with the given cause.
     *
     * @param cause the original exception that triggered the retry failure
     */
    public RollbackRetryException(Throwable cause, String transactionName) {
        super(cause.getMessage(), cause);
        this.transactionName = transactionName;
    }

    /**
     * Constructs a new RollbackRetryException with the given message and cause
     *
     * @param message custom message
     * @param cause the original exception
     */
    public RollbackRetryException(String message, Throwable cause, String transactionName) {
        super(message, cause);
        this.transactionName = transactionName;
    }

    /**
     * @return the transactionName
     */
    public String getTransactionName() {
        return transactionName;
    }

    @Override
    public String getMessage() {
        return "[Rollback after " + this.attempt + " retries in " + this.getTransactionName() + "] " + super.getMessage();
    }

    /**
     * @return the attempt
     */
    public int getAttempt() {
        return attempt;
    }

    /**
     * @param attempt the attempt to set
     */
    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

}
