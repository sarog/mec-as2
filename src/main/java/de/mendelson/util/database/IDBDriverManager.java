//$Header: /oftp2/de/mendelson/util/database/IDBDriverManager.java 23    27/11/25 9:56 Heller $
package de.mendelson.util.database;

import de.mendelson.util.systemevents.SystemEventManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Interface for all supported database drivers
 *
 * @author S.Heller
 * @version $Revision: 23 $
 */
public interface IDBDriverManager {

    public static int DB_CONFIG = 1;
    public static int DB_RUNTIME = 2;
    public static int DB_DEPRICATED = 3;

    /**
     * Setup the driver manager, initialize the connection pool. It's for some
     * cases important that this method could be called multiple times without
     * setting up the connection pool again (e.g. web interfaces)
     *
     */
    public void setupConnectionPool();

    /**
     * shutdown the connection pool
     */
    public void shutdownConnectionPool() throws SQLException;

    /**
     * Creates a new locale database
     *
     * @return true if it was created successfully
     * @param DB_TYPE of the database that should be created, as defined in this
     * class
     */
    public boolean createDatabase(final int DB_TYPE) throws Exception;

    /**
     * Returns a connection to the database
     *
     * @param DB_TYPE of the database that should be created, as defined in this
     * class
     */
    public Connection getConnectionWithoutErrorHandling(final int DB_TYPE)
            throws Exception;

    /**
     * Returns the SQL statement that is used to lock a table on database level
     * exclusive for a transaction
     *
     * @param tablenames Every single tablename could be either the name of a
     * table or a string in the format alias.tablename whic is required for some
     * drivers if they need to lock views with alias
     *
     * @return
     */
    public void setTableLockExclusive(Statement statement, String[] tablenames) throws SQLException;

    /**
     * Returns the SQL statement that is used to lock a table on database level
     * for an INSERT or UPDATE operation. The lock level should be that high
     * that no other session could perform an update or insert operation to the
     * same table(s) meanwhile
     *
     * @param tablenames Every single tablename could be either the name of a
     * table or a string in the format alias.tablename whic is required for some
     * drivers if they need to lock views with alias
     *
     * @return
     */
    public void setTableLockINSERTAndUPDATE(Statement statement, String[] tablenames) throws SQLException;

    /**
     * Returns the SQL statement that is used to lock a table on database level
     * for a DELETE operation
     *
     * @param tablenames Every single tablename could be either the name of a
     * table or a string in the format alias.tablename whic is required for some
     * drivers if they need to lock views with alias
     *
     * @return
     */
    public void setTableLockDELETE(Statement statement, String[] tablenames) throws SQLException;

    /**
     * Tries to acquire a READ lock on the given tables to ensure a consistent
     * read snapshot while still allowing other clients to read but not to
     * write.
     *
     * @param statement Statement to execute the lock
     * @param tablenames List of table names (optionally with alias, e.g.,
     * alias.table)
     * @throws SQLException If lock cannot be acquired
     */
    public void setTableLockREAD(Statement statement, String[] tablenames) throws SQLException;

    /**
     * Starts a transaction. Implementations might do nothing as this concept is
     * database specific. HSQLB has no "BEGIN transaction" concept
     *
     * @param statement
     * @param transactionName
     */
    public void startTransaction(Statement statement, String transactionName) throws SQLException;

    /**
     * Commits a transaction
     *
     * @param transactionName
     */
    public void commitTransaction(Statement statement, String transactionName) throws SQLException;

    /**
     * Rollback a transaction
     */
    public void rollbackTransaction(Statement statement) throws SQLException;

    /**
     * Sets text data as parameter to a stored procedure. The handling depends
     * if the database supports java objects. PostgreSQL for example could not
     * deal with the JDBC type JAVA_OBJECT
     */
    public void setTextParameterAsJavaObject(PreparedStatement statement, int index, String text) throws SQLException;

    /**
     * Reads a binary object from the database and returns a String that
     * contains it. Will return null if the read data was null. Reading and
     * writing binary objects differs relating the used database system.
     * PostgreSQL for example could not deal with the JDBC type JAVA_OBJECT
     */
    public String readTextStoredAsJavaObject(ResultSet result, String columnName) throws Exception;

    /**
     * Reads a binary object from the database and returns a byte array that
     * contains it. Will return null if the read data was null. Reading and
     * writing binary objects differs relating the used database system
     */
    public Object readObjectStoredAsJavaObject(ResultSet result, String columnName) throws Exception;

    /**
     * Sets an Object data as parameter to a stored procedure. The handling
     * depends if the database supports java objects
     *
     */
    public void setObjectParameterAsJavaObject(PreparedStatement statement, int index, Object obj) throws Exception;

    /**
     * Sets an Object data as parameter to a stored procedure. The handling
     * depends if the database supports java objects
     *
     */
    public void setBytesParameterAsJavaObject(PreparedStatement statement, int index, byte[] data) throws Exception;

    /**
     * Reads a binary object from the database and returns a byte array that
     * contains it. Will return null if the read data was null. Reading and
     * writing binary objects differs relating the used database system
     */
    public byte[] readBytesStoredAsJavaObject(ResultSet result, String columnName) throws Exception;

    /**
     * Returns some connection pool information for debug purpose
     */
    public String getPoolInformation(int DB_TYPE);

    /**
     * Adds a limit clause to the SQL query. LIMIT is common but no used by all
     * databases - e.g. not by the oracle database
     *
     * @param query
     * @param maxRows
     */
    public String addLimitToQuery(String query, int maxRows);

    /**
     * Executes a database operation with retries. After all retries fail, the
     * exception is notified once. Optional is used here to prevent an autoboxing of the result (as is might be null)
     * Means int a = executeWithRetry(...) should be already prevented by the compiler, it might result in a NullPointer
     * Exception because of the autoboxing
     *
     * @param operation Operation to execute
     * @param <T> Return type
     * @return Result of operation if successful, null if thee number of max retries has been reached
     */
    public <T> Optional<T> executeWithRetry(SystemEventManager systemEventManager,
            RetryableDBOperation<T> operation);

    
    /**Creates a Row Lock for a SINGLE row of a table for a later UPDATE/DELETE on this row
     * 
     * @param statement The transaction statement
     * @param tablename The tablename the row is in
     * @param conditionQuery The query to select the rows to lock, without WHERE but in prepared statement syntax
     * @param parameterList The list of parameter for the passed "?" in the condition query
     * @return the value of the column of the returnIntColumnName or null if there was no lock
     * @throws SQLException 
     */
    public <T> T setSingleRowLockForModification(
            Statement statement, String tablename, String conditionQuery, List<Object> parameterList, 
            String returnColumnName, Class<T> returnType) throws SQLException;
    
    
    
}
