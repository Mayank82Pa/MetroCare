package com.healthcare.test;

import com.healthcare.model.Admin;
import com.healthcare.model.Doctor;
import com.healthcare.model.Patient;
import com.healthcare.model.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for Core Java OOP Model Hierarchy (Review 1 & Review 2 Requirement).
 * Demonstrates: Inheritance, Polymorphism, and Encapsulation validation.
 */
public class UserTest {

    @Test
    public void testDoctorInheritanceAndPolymorphism() {
        Doctor doc = new Doctor(10, "Dr. John Watson", "watson@hospital.com", "pass123", "+91 99999 88888", "ACTIVE",
                101, "Cardiology", "MBBS, MD", 12, new BigDecimal("750.00"), "Cardiology expert");

        assertTrue(doc.isDoctor());
        assertFalse(doc.isAdmin());
        assertFalse(doc.isPatient());
        assertEquals("DOCTOR", doc.getRole());
        assertEquals("Doctor / Specialist", doc.getRoleDisplayName());
        assertTrue(doc.getProfileSummary().contains("Cardiology"));
    }

    @Test
    public void testAdminInheritanceAndPolymorphism() {
        Admin admin = new Admin(1, "Super Admin", "admin@hospital.com", "admin123", "+91 90000 00000", "ACTIVE");

        assertTrue(admin.isAdmin());
        assertFalse(admin.isDoctor());
        assertEquals("ADMIN", admin.getRole());
        assertEquals("System Administrator", admin.getRoleDisplayName());
        assertTrue(admin.getProfileSummary().contains("[ADMIN]"));
    }

    @Test
    public void testPatientInheritanceAndPolymorphism() {
        Patient patient = new Patient(5, "Jane Doe", "jane@gmail.com", "pass123", "+91 88888 77777", "ACTIVE",
                202, Date.valueOf("1996-08-20"), "Female", "O+", "Baker Street", "+91 88888 00000");

        assertTrue(patient.isPatient());
        assertFalse(patient.isAdmin());
        assertEquals("PATIENT", patient.getRole());
        assertEquals("Patient", patient.getRoleDisplayName());
        assertTrue(patient.getProfileSummary().contains("O+"));
    }
}
