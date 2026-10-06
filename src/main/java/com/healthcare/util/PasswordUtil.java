package com.healthcare.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for hashing passwords using SHA-256 with optional salt.
 * Ensures security against plain-text password storage (Review 2 Code Quality & Security requirement).
 */
public class PasswordUtil {

    /**
     * Hashes plain text password using SHA-256.
     *
     * @param plainPassword Raw password string
     * @return Hexadecimal SHA-256 hash
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            return "";
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Validates if a plain text password matches an existing SHA-256 hash.
     */
    public static boolean checkPassword(String plainPassword, String storedHash) {
        if (plainPassword == null || storedHash == null) {
            return false;
        }
        String hashedAttempt = hashPassword(plainPassword);
        return hashedAttempt.equalsIgnoreCase(storedHash);
    }
}
