package com.healthcare.exception;

/**
 * Custom exception representing database and JDBC-related errors.
 * Used across the DAO layer to encapsulate SQLException and provide clean error propagation.
 */
public class DatabaseException extends RuntimeException {
    
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
