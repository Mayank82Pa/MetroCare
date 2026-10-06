package com.healthcare.util;

import com.healthcare.exception.ValidationException;
import java.util.regex.Pattern;

/**
 * Validation utility for user input verification.
 */
public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^[+]?[0-9\\s\\-()]{8,20}$"
    );

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    public static boolean isNonEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static void validateRegistration(String name, String email, String password, String role) throws ValidationException {
        if (!isNonEmpty(name) || name.trim().length() < 2) {
            throw new ValidationException("Full name must be at least 2 characters long.");
        }
        if (!isValidEmail(email)) {
            throw new ValidationException("Please provide a valid email address.");
        }
        if (!isNonEmpty(password) || password.length() < 6) {
            throw new ValidationException("Password must be at least 6 characters long.");
        }
        if (!isNonEmpty(role) || (!role.equalsIgnoreCase("PATIENT") && !role.equalsIgnoreCase("DOCTOR") && !role.equalsIgnoreCase("ADMIN"))) {
            throw new ValidationException("Invalid user role specified.");
        }
    }
}
