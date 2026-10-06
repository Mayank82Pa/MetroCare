package com.healthcare.test;

import com.healthcare.exception.ValidationException;
import com.healthcare.util.PasswordUtil;
import com.healthcare.util.ValidationUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for Security and Validation Utilities (Review 2 Code Quality & Testing).
 */
public class ValidationUtilTest {

    @Test
    public void testEmailValidation() {
        assertTrue(ValidationUtil.isValidEmail("rahul@gmail.com"));
        assertTrue(ValidationUtil.isValidEmail("dr.sharma@metrocare.org"));
        assertFalse(ValidationUtil.isValidEmail("invalid-email"));
        assertFalse(ValidationUtil.isValidEmail("@missingusername.com"));
        assertFalse(ValidationUtil.isValidEmail(null));
    }

    @Test
    public void testPhoneValidation() {
        assertTrue(ValidationUtil.isValidPhone("+91 98765 43210"));
        assertTrue(ValidationUtil.isValidPhone("9876543210"));
        assertFalse(ValidationUtil.isValidPhone("123")); // too short
        assertFalse(ValidationUtil.isValidPhone("invalid_phone_string"));
    }

    @Test
    public void testPasswordHashing() {
        String plain = "doctor123";
        String hash1 = PasswordUtil.hashPassword(plain);
        String hash2 = PasswordUtil.hashPassword(plain);

        assertNotNull(hash1);
        assertEquals(64, hash1.length()); // SHA-256 is 64 hex characters
        assertEquals(hash1, hash2); // Deterministic
        assertTrue(PasswordUtil.checkPassword("doctor123", hash1));
        assertFalse(PasswordUtil.checkPassword("wrongpass", hash1));
    }

    @Test
    public void testRegistrationValidationThrowsOnInvalidInputs() {
        assertThrows(ValidationException.class, () -> {
            ValidationUtil.validateRegistration("", "user@test.com", "pass123", "PATIENT");
        });

        assertThrows(ValidationException.class, () -> {
            ValidationUtil.validateRegistration("Valid Name", "not-an-email", "pass123", "PATIENT");
        });

        assertThrows(ValidationException.class, () -> {
            ValidationUtil.validateRegistration("Valid Name", "user@test.com", "123", "PATIENT"); // too short password
        });
    }
}
