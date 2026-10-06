package com.healthcare.dao;

import com.healthcare.util.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Base DAO class providing connection utilities and abstraction for child DAOs.
 * Demonstrates Abstraction and Code Reusability in JDBC layer.
 */
public abstract class BaseDAO {

    protected Connection getConnection() {
        return DBConnection.getConnection();
    }

    protected void close(Connection conn, Statement stmt, ResultSet rs) {
        DBConnection.closeResources(conn, stmt, rs);
    }

    protected void close(Connection conn, Statement stmt) {
        DBConnection.closeResources(conn, stmt, null);
    }
}
