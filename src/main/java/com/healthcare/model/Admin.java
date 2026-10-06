package com.healthcare.model;

/**
 * Admin Class extending User (Demonstrating Inheritance & Polymorphism)
 */
public class Admin extends User {
    private static final long serialVersionUID = 1L;

    public Admin() {
        super();
        this.role = "ADMIN";
    }

    public Admin(int userId, String name, String email, String password, String phone, String status) {
        super(userId, name, email, password, "ADMIN", phone, status);
    }

    @Override
    public String getRoleDisplayName() {
        return "System Administrator";
    }

    @Override
    public String getProfileSummary() {
        return String.format("[ADMIN] %s - Full Access Authority", name);
    }
}
