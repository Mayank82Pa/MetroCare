package com.healthcare.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Base User Class demonstrating OOP Pillars:
 * 1. Encapsulation: Private member variables with protected/public getters & setters.
 * 2. Inheritance: Serves as the superclass for Admin, Doctor, and Patient.
 * 3. Polymorphism: Defines getRoleDisplayName() and getProfileSummary() overridden by subclasses.
 */
public abstract class User implements Serializable, Identifiable {
    private static final long serialVersionUID = 1L;

    protected int userId;
    protected String name;
    protected String email;
    protected String password;
    protected String role; // "ADMIN", "DOCTOR", "PATIENT"
    protected String phone;
    protected String status; // "ACTIVE", "INACTIVE"
    protected Timestamp createdAt;

    public User() {
        this.status = "ACTIVE";
    }

    public User(int userId, String name, String email, String password, String role, String phone, String status) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.status = status != null ? status : "ACTIVE";
    }

    // Abstract method demonstrating Polymorphism
    public abstract String getRoleDisplayName();

    // Polymorphic method that can be overridden by specific user types
    public String getProfileSummary() {
        return String.format("%s (%s) - Email: %s | Phone: %s", name, getRoleDisplayName(), email, phone);
    }

    // Role helper checks
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role);
    }

    public boolean isDoctor() {
        return "DOCTOR".equalsIgnoreCase(this.role);
    }

    public boolean isPatient() {
        return "PATIENT".equalsIgnoreCase(this.role);
    }

    // Getters and Setters (Encapsulation)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
