package com.healthcare.model;

/**
 * Interface demonstrating OOP Interfaces requirement:
 * Provides contract for entities that have unique database identifiers.
 */
public interface Identifiable {
    int getUserId();
    String getRoleDisplayName();
}
