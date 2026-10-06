package com.healthcare.exception;

/**
 * Custom exception thrown when a requested entity (User, Doctor, Patient, Appointment) cannot be found.
 */
public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
