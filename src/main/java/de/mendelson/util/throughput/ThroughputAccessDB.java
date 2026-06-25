//$Header: /mec_as2/de/mendelson/util/throughput/ThroughputAccessDB.java 7     15/04/26 12:44 Heller $
package de.mendelson.util.throughput;

import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.systemevents.SystemEvent;
import de.mendelson.util.systemevents.SystemEventManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Access system throughput
 *
 * @author S.Heller
 * @version $Revision: 7 $
 */
public class ThroughputAccessDB {

    private final IDBDriverManager dbDriverManager;
    private final SystemEventManager eventManager;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");

    public ThroughputAccessDB(IDBDriverManager dbDriverManager, SystemEventManager eventManager) {
        this.dbDriverManager = dbDriverManager;
        this.eventManager = eventManager;
    }

    /**
     * Adds a transaction to the current counter and returns the new one
     *
     * @return
     */
    public long addTransaction() {
        long currentCounter = 0;
        try (Connection runtimeConnectionNoAutoCommit = this.dbDriverManager.getConnectionWithoutErrorHandling(
                IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            this.addTransactionAsTransaction(runtimeConnectionNoAutoCommit);
        } catch (Throwable e) {
            this.eventManager.systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        return (currentCounter);
    }

    /**
     * Adds a transaction to the current counter and returns the new one
     *
     * @return
     */
    public long addTransactionAsTransaction(Connection runtimeConnectionNoAutoCommit) throws Exception {
        String transactionName = "Throughput_addTransaction";
        long currentCounter = 0;
        String monthStr = LocalDateTime.now().format(DATE_FORMAT);
        try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
            this.dbDriverManager.startTransaction(transactionStatement, transactionName);
            this.dbDriverManager.setTableLockDELETE(transactionStatement,
                    new String[]{"throughput"});
            try {                
                currentCounter = this.readTransactionCounter(monthStr, runtimeConnectionNoAutoCommit);
                currentCounter++;
                this.writeTransactionCounter(monthStr, currentCounter, runtimeConnectionNoAutoCommit);
                this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                return (currentCounter);
            } catch (Throwable e) {
                this.eventManager.systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                this.dbDriverManager.rollbackTransaction(transactionStatement);
            }
        }
        return (currentCounter);
    }

    /**
     * Returns the sum of the transactions of the passed month array. The month
     * array must contain String in the format yyyymm
     *
     * @return
     */
    public long getTransactions(String[] monthArray) {
        String transactionName = "Throughput_getTransactions";
        long currentCounter = 0;
        try (Connection runtimeConnectionNoAutoCommit = this.dbDriverManager.getConnectionWithoutErrorHandling(
                IDBDriverManager.DB_RUNTIME)) {
            runtimeConnectionNoAutoCommit.setAutoCommit(false);
            try (Statement transactionStatement = runtimeConnectionNoAutoCommit.createStatement()) {
                this.dbDriverManager.startTransaction(transactionStatement, transactionName);
                this.dbDriverManager.setTableLockREAD(transactionStatement,
                        new String[]{"throughput"});
                try {
                    for (String monthStr : monthArray) {
                        currentCounter += this.readTransactionCounter(monthStr, runtimeConnectionNoAutoCommit);
                    }
                    this.dbDriverManager.commitTransaction(transactionStatement, transactionName);
                    return (currentCounter);
                } catch (Throwable e) {
                    this.eventManager.systemFailure(e, SystemEvent.Type.DATABASE_ROLLBACK);
                    this.dbDriverManager.rollbackTransaction(transactionStatement);
                }
            }
        } catch (Throwable e) {
            this.eventManager.systemFailure(e, SystemEvent.Type.DATABASE_ANY);
        }
        return (currentCounter);
    }

    /**
     * Reads the number of transactions for a given month
     */
    private int readTransactionCounter(String monthStr, Connection runtimeConnectionNoAutoCommit) throws Exception {
        int transactionCounter = 0;
        String query = "SELECT transactioncounter FROM throughput WHERE month=?";
        query = this.dbDriverManager.addLimitToQuery(query, 1);
        try (PreparedStatement statement = runtimeConnectionNoAutoCommit.prepareStatement(query)) {
            statement.setString(1, monthStr);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    transactionCounter = result.getInt("transactioncounter");
                }
            }
            return (transactionCounter);
        }
    }

    /**
     * Writes a new value to the transactionCounter or adds it if it does not
     * exist so far
     */
    private void writeTransactionCounter(String monthStr, long transactionCounter, Connection runtimeConnectionNoAutoCommit) throws Exception {
        try (PreparedStatement updateStatement = runtimeConnectionNoAutoCommit.prepareStatement(
                "UPDATE throughput SET transactioncounter=? WHERE month=?")) {
            updateStatement.setLong(1, transactionCounter);
            updateStatement.setString(2, monthStr);
            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 0) {
                try (PreparedStatement insertStatement = runtimeConnectionNoAutoCommit.prepareStatement(
                        "INSERT INTO throughput(month,transactioncounter)VALUES(?,?)")) {
                    insertStatement.setString(1, monthStr);
                    insertStatement.setLong(2, transactionCounter);
                    insertStatement.executeUpdate();
                }
            }
        }
    }
}
