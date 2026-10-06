package com.healthcare.exception;

/**
 * Custom exception representing business validation errors (e.g. invalid date format, invalid phone, slot conflict).
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}
