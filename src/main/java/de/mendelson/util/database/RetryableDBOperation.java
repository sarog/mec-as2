//$Header: /oftp2/de/mendelson/util/database/RetryableDBOperation.java 1     17/10/25 8:31 Heller $
package de.mendelson.util.database;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Interface that encapsulates a database operation that is repeatable because
 * of a deadlock
 *
 * @author S.Heller
 * @version $Revision: 1 $
 * @since build 70
 */
public interface RetryableDBOperation<T> {

    T execute() throws RollbackRetryException;
}
