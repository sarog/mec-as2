//$Header: /mec_as2/de/mendelson/util/database/AbstractDBDriverManagerHSQL.java 24    15/04/26 12:43 Heller $
package de.mendelson.util.database;

import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Class needed to access the database
 *
 * @author S.Heller
 * @version $Revision: 24 $
 */
public abstract class AbstractDBDriverManagerHSQL implements IDBDriverManager {

    /**
     * Removes any alias from table names and returns a set of unique table
     * names. The result is sorted to enforce a consistent locking order. Even
     * though a SQL LOCK TABLE statement is executed atomically, the database
     * acquires locks sequentially per table. By locking tables in a consistent
     * order across threads, the likelihood of deadlocks is reduced.
     *
     * @param tablenames Array of table names, optionally prefixed with aliases
     * (alias.table)
     * @return A sorted Set of unique table names without aliases
     */
    private Set<String> stripToUniqueSortedTablenames(String[] tablenames) {
        Set<String> uniqueTablenames = new TreeSet<String>();
        for (String singleTablename : tablenames) {
            int dotIndex = singleTablename.indexOf('.');
            String tableName;
            if (dotIndex > 0 && dotIndex < singleTablename.length() - 1) {
                //Format: alias.table: ignore this here, strip the alias
                tableName = singleTablename.substring(dotIndex + 1).trim();
            } else {
                tableName = singleTablename;
            }
            uniqueTablenames.add(tableName);
        }
        return (uniqueTablenames);
    }

    /**
     * As there are implemenations that require to lock views/aliasses the
     * format of a single tablename might be alias.tablename. This method will
     * cut off the alias as this is not used for HSQLDB
     *
     * @param statement
     * @param tablenames
     * @throws SQLException
     */
    @Override
    public void setTableLockExclusive(Statement statement, String[] tablenames) throws SQLException {
        Set<String> uniqueTablenames = this.stripToUniqueSortedTablenames(tablenames);
        StringBuilder builder = new StringBuilder();
        for (String tablename : uniqueTablenames) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(tablename)
                    .append(" READ,")
                    .append(tablename)
                    .append(" WRITE");
        }
        statement.execute("LOCK TABLE " + builder.toString());
    }

    /**
     * As there are implemenations that require to lock views/aliasses the
     * format of a single tablename might be alias.tablename. This method will
     * cut off the alias as this is not used for HSQLDB
     *
     * @param statement
     * @param tablenames
     * @throws SQLException
     */
    @Override
    public void setTableLockDELETE(Statement statement, String[] tablenames) throws SQLException {
        Set<String> uniqueTablenames = this.stripToUniqueSortedTablenames(tablenames);
        StringBuilder builder = new StringBuilder();
        for (String tablename : uniqueTablenames) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(tablename)
                    .append(" WRITE");
        }
        statement.execute("LOCK TABLE " + builder.toString());
    }

    /**
     * As there are implemenations that require to lock views/aliasses the
     * format of a single tablename might be alias.tablename. This method will
     * cut off the alias as this is not used for HSQLDB
     *
     * @param statement
     * @param tablenames
     * @throws SQLException
     */
    @Override
    public void setTableLockINSERTAndUPDATE(Statement statement, String[] tablenames) throws SQLException {
        Set<String> uniqueTablenames = this.stripToUniqueSortedTablenames(tablenames);
        StringBuilder builder = new StringBuilder();
        for (String tablename : uniqueTablenames) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(tablename)
                    .append(" WRITE");
        }
        statement.execute("LOCK TABLE " + builder.toString());
    }

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
    @Override
    public void setTableLockREAD(Statement statement, String[] tablenames) throws SQLException {
        Set<String> uniqueTablenames = this.stripToUniqueSortedTablenames(tablenames);
        StringBuilder builder = new StringBuilder();
        for (String tablename : uniqueTablenames) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(tablename).append(" READ");
        }
        statement.execute("LOCK TABLE " + builder.toString());
    }

    /**
     * Creates a Row Lock for a SINGLE row of a table for a later UPDATE on this
     * row. It is important to use this ONLY if there is a single possible row
     * for the condition. If the condition matches multiple lines there could be
     * phantom reads/race condition if a row is locked and in the update there
     * are now lines that match the update
     *
     * @param statement The transaction statement
     * @param tablename The tablename the row is in
     * @param conditionQuery The query to select the rows to lock, without WHERE
     * but in prepared statement syntax
     * @param parameterList The list of parameter for the passed "?" in the
     * condition query
     * @throws SQLException
     */
    @Override
    public <T> T setSingleRowLockForModification(
            Statement statement, String tablename, String conditionQuery, List<Object> parameterList, 
            String returnColumnName, Class<T> returnType) throws SQLException {
        String conditionQueryLimit = this.addLimitToQuery(conditionQuery, 1);
        //do not change this to "SELECT 1 FROM" - using "*" here allows an additional SELECT without phantom reads on the locked row which is sometimes necessary
        String query = "SELECT * FROM " + tablename + " WHERE " + conditionQueryLimit + " FOR UPDATE";
        try (PreparedStatement selectUpdateStatement = statement.getConnection().prepareStatement(query)) {
            for (int i = 0; i < parameterList.size(); i++) {
                Object parameter = parameterList.get(i);
                if (parameter == null) {
                    throw new SQLException("setSingleRowLockForModification: NULL parameter are not allowed in the parameter list");
                }
                selectUpdateStatement.setObject(i + 1, parameterList.get(i));
            }
            try (ResultSet result = selectUpdateStatement.executeQuery()) {
                if (result.next()) {                
                T returnValue = null;                
                if (returnType == Integer.class) {
                    Integer value = result.getInt(returnColumnName);
                    if (!result.wasNull()) {
                        returnValue = (T) value;
                    }
                } else if (returnType == String.class) {
                    String value = result.getString(returnColumnName);
                    if (!result.wasNull()) {
                        returnValue = (T) value;
                    }
                } else {
                    throw new SQLException("setSingleRowLockForModification: Unsupported return type: " + returnType.getSimpleName());
                }                
                if (returnValue == null) {
                    return null;
                } else {
                    return returnValue; 
                }
            }
            }
        }
        //no lock
        return (null);
    }

    @Override
    public void startTransaction(Statement statement, String transactionName) throws SQLException {
        if (statement.getConnection().getAutoCommit()) {
            throw new SQLException("Transaction "
                    + transactionName
                    + " started on database connection that is in auto commit mode");
        }
        //since HSQLDB 2.7.0 it is required to add the isolation level to the START TRANSACTION command
        // - READ COMMITTED was the default isolation level before so this is just added
        statement.execute("START TRANSACTION ISOLATION LEVEL READ COMMITTED");
    }

    @Override
    public void commitTransaction(Statement statement, String transactionName) throws SQLException {
        statement.execute("COMMIT");
    }

    @Override
    public void rollbackTransaction(Statement statement) throws SQLException {
        statement.execute("ROLLBACK");
    }

    @Override
    public void setTextParameterAsJavaObject(PreparedStatement statement, int index, String text) throws SQLException {
        if (text == null) {
            statement.setNull(index, Types.JAVA_OBJECT);
        } else {
            statement.setObject(index, text);
        }
    }

    /**
     * Reads a binary object from the database and returns a byte array that
     * contains it. Will return null if the read data was null. Reading and
     * writing binary objects differs relating the used database system
     */
    @Override
    public String readTextStoredAsJavaObject(ResultSet result, String columnName) throws Exception {
        Object object = result.getObject(columnName);
        if (!result.wasNull()) {
            if (object instanceof String) {
                return ((String) object);
            } else if (object instanceof byte[]) {
                return (new String((byte[]) object));
            }
        }
        return (null);
    }

    /**
     * Reads a binary object from the database and returns a byte array that
     * contains it. Will return null if the read data was null. Reading and
     * writing binary objects differs relating the used database system
     */
    @Override
    public Object readObjectStoredAsJavaObject(ResultSet result, String columnName) throws Exception {
        Object object = result.getObject(columnName);
        if (!result.wasNull()) {
            return (object);
        } else {
            return (null);
        }

    }

    /**
     * Sets text data as parameter to a stored procedure. The handling depends
     * if the database supports java objects
     *
     */
    @Override
    public void setObjectParameterAsJavaObject(PreparedStatement statement, int index, Object obj) throws Exception {
        if (obj == null) {
            statement.setNull(index, Types.JAVA_OBJECT);
        } else {
            statement.setObject(index, obj);
        }
    }

    /**
     * Sets byte array data as parameter to a stored procedure. The handling
     * depends if the database supports java objects
     *
     */
    @Override
    public void setBytesParameterAsJavaObject(PreparedStatement statement, int index, byte[] data) throws Exception {
        this.setObjectParameterAsJavaObject(statement, index, data);
    }

    /**
     * Reads a binary object from the database and returns a byte array that
     * contains it. Will return null if the read data was null. Reading and
     * writing binary objects differs relating the used database system
     */
    @Override
    public byte[] readBytesStoredAsJavaObject(ResultSet result, String columnName) throws Exception {
        Object object = result.getObject(columnName);
        if (!result.wasNull()) {
            return ((byte[]) object);
        }
        return (null);
    }

    /**
     * Adds a limit clause to the SQL query. LIMIT is common but no used by all
     * databases - e.g. not by the oracle database
     *
     * @param query
     * @param maxRows
     */
    @Override
    public String addLimitToQuery(String query, int maxRows) {
        return (query + " LIMIT " + maxRows);
    }

    /**
     * Executes a database operation with retries. After all retries fail, the
     * exception is notified once. Optional is used here to prevent an
     * autoboxing of the result (as is might be null) Means int a =
     * executeWithRetry(...) should be already prevented by the compiler, it
     * might result in a NullPointer Exception because of the autoboxing
     *
     * @param operation Operation to execute
     * @param <T> Return type
     * @return Result of operation if successful
     */
    @Override
    public <T> Optional<T> executeWithRetry(SystemEventManager systemEventManager,
            RetryableDBOperation<T> operation) {
        //3 retries before the system gives up
        int maxRetries = 3;
        RollbackRetryException lastException = null;
        for (int attempt = 0; attempt < maxRetries; attempt++) {
            try {
                T result = operation.execute();
                return Optional.ofNullable(result);
            } catch (RollbackRetryException e) {
                lastException = e;
                lastException.setAttempt(attempt + 1);
                //add a pause but not for the last try
                if (attempt + 1 < maxRetries) {
                    long waitTimeMs = 100L * (attempt + 1) + (long) (Math.random() * 100L);
                    try {
                        Thread.sleep(waitTimeMs);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        if (lastException != null) {
            systemEventManager.systemFailure(lastException, SystemEvent.Type.DATABASE_ROLLBACK);
        }
        return Optional.empty();
    }
}
