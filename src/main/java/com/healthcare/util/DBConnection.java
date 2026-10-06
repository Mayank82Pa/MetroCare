package com.healthcare.util;

import com.healthcare.exception.DatabaseException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Singleton Database Connection Factory and Resource Manager.
 * Supports:
 * 1. Automatic loading from db.properties
 * 2. Seamless MySQL and embedded H2 database support
 * 3. Automatic schema initialization and sample data population for zero-friction evaluation
 * 4. Transaction management (commit, rollback, setAutoCommit)
 */
public class DBConnection {

    private static DBConnection instance;
    private static Properties properties = new Properties();
    private static boolean isInitialized = false;

    private String driver;
    private String url;
    private String user;
    private String password;
    private String dbType;

    private DBConnection() {
        loadConfiguration();
        loadDriver();
        initializeDatabaseIfNeeded();
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public static Connection getConnection() {
        return getInstance().createConnection();
    }

    private void loadConfiguration() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                properties.load(input);
                dbType = properties.getProperty("db.type", "h2").trim().toLowerCase();
            } else {
                dbType = "h2"; // default fallback
            }

            if ("mysql".equalsIgnoreCase(dbType)) {
                driver = properties.getProperty("mysql.driver", "com.mysql.cj.jdbc.Driver");
                url = properties.getProperty("mysql.url", "jdbc:mysql://localhost:3306/healthcare_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
                user = properties.getProperty("mysql.user", "root");
                password = properties.getProperty("mysql.password", "root123");
            } else {
                // H2 Embedded In-Memory / File Database
                driver = properties.getProperty("h2.driver", "org.h2.Driver");
                url = properties.getProperty("h2.url", "jdbc:h2:mem:healthcaredb;DB_CLOSE_DELAY=-1;MODE=MySQL");
                user = properties.getProperty("h2.user", "sa");
                password = properties.getProperty("h2.password", "");
            }
        } catch (Exception e) {
            System.err.println("[DBConnection] Could not load db.properties, falling back to H2 In-Memory DB: " + e.getMessage());
            dbType = "h2";
            driver = "org.h2.Driver";
            url = "jdbc:h2:mem:healthcaredb;DB_CLOSE_DELAY=-1;MODE=MySQL";
            user = "sa";
            password = "";
        }
    }

    private void loadDriver() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("[DBConnection] Primary driver not found: " + driver + ". Trying H2 driver...");
            try {
                Class.forName("org.h2.Driver");
                this.driver = "org.h2.Driver";
                this.url = "jdbc:h2:mem:healthcaredb;DB_CLOSE_DELAY=-1;MODE=MySQL";
                this.user = "sa";
                this.password = "";
                this.dbType = "h2";
            } catch (ClassNotFoundException ex) {
                throw new DatabaseException("Failed to load any JDBC Driver: " + e.getMessage(), ex);
            }
        }
    }

    public Connection createConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // If MySQL connection fails, fallback seamlessly to H2 In-Memory database
            if (!"h2".equalsIgnoreCase(dbType)) {
                System.err.println("[DBConnection] MySQL connection failed (" + e.getMessage() + "). Switching to embedded H2 In-Memory mode...");
                this.dbType = "h2";
                this.driver = "org.h2.Driver";
                this.url = "jdbc:h2:mem:healthcaredb;DB_CLOSE_DELAY=-1;MODE=MySQL";
                this.user = "sa";
                this.password = "";
                this.isInitialized = false;
                loadDriver();
                initializeDatabaseIfNeeded();
                try {
                    return DriverManager.getConnection(url, user, password);
                } catch (SQLException ex) {
                    throw new DatabaseException("Failed to establish H2 fallback connection: " + ex.getMessage(), ex);
                }
            }
            throw new DatabaseException("Database connection error: " + e.getMessage(), e);
        }
    }

    /**
     * Initializes database schema and sample data on initial startup if tables do not exist.
     */
    private synchronized void initializeDatabaseIfNeeded() {
        if (isInitialized) {
            return;
        }

        try (Connection conn = createConnection(); Statement stmt = conn.createStatement()) {
            boolean tablesExist = false;
            try (ResultSet rs = conn.getMetaData().getTables(null, null, "USERS", null)) {
                if (rs.next()) {
                    tablesExist = true;
                }
            } catch (Exception ignored) {}

            if (!tablesExist) {
                try (ResultSet rs = conn.getMetaData().getTables(null, null, "users", null)) {
                    if (rs.next()) {
                        tablesExist = true;
                    }
                } catch (Exception ignored) {}
            }

            if (!tablesExist) {
                System.out.println("[DBConnection] Initializing Database Schema from schema.sql...");
                executeSqlScript(conn, "schema.sql");
                System.out.println("[DBConnection] Populating Sample Data from sample_data.sql...");
                executeSqlScript(conn, "sample_data.sql");
                System.out.println("[DBConnection] Database initialization complete!");
            }
            isInitialized = true;
        } catch (Exception e) {
            System.err.println("[DBConnection] Notice during DB initialization: " + e.getMessage());
        }
    }

    private void executeSqlScript(Connection conn, String resourcePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.err.println("[DBConnection] Resource file not found: " + resourcePath);
                return;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sql = new StringBuilder();
            String line;
            try (Statement stmt = conn.createStatement()) {
                while ((line = reader.readLine()) != null) {
                    String trimmed = line.trim();
                    if (trimmed.startsWith("--") || trimmed.isEmpty() || trimmed.startsWith("//")) {
                        continue;
                    }
                    sql.append(line).append(" ");
                    if (trimmed.endsWith(";")) {
                        String statementToRun = sql.toString().replace(";", "").trim();
                        if (!statementToRun.isEmpty()) {
                            try {
                                stmt.execute(statementToRun);
                            } catch (SQLException e) {
                                // Ignore harmless duplicate table/index errors during re-runs
                            }
                        }
                        sql.setLength(0);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[DBConnection] Error executing " + resourcePath + ": " + e.getMessage());
        }
    }

    // Transaction Management Helpers
    public static void beginTransaction(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                throw new DatabaseException("Failed to begin transaction", e);
            }
        }
    }

    public static void commitTransaction(Connection conn) {
        if (conn != null) {
            try {
                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DatabaseException("Failed to commit transaction", e);
            }
        }
    }

    public static void rollbackTransaction(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("[DBConnection] Rollback warning: " + e.getMessage());
            }
        }
    }

    public static void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try { rs.close(); } catch (SQLException ignored) {}
        }
        if (stmt != null) {
            try { stmt.close(); } catch (SQLException ignored) {}
        }
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }
}
