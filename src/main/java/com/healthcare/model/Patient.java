package com.healthcare.model;

import java.sql.Date;

/**
 * Patient Class extending User (Demonstrating Inheritance, Encapsulation & Polymorphism)
 */
public class Patient extends User {
    private static final long serialVersionUID = 1L;

    private int patientId;
    private Date dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String address;
    private String emergencyContact;

    public Patient() {
        super();
        this.role = "PATIENT";
    }

    public Patient(int userId, String name, String email, String password, String phone, String status,
                   int patientId, Date dateOfBirth, String gender, String bloodGroup,
                   String address, String emergencyContact) {
        super(userId, name, email, password, "PATIENT", phone, status);
        this.patientId = patientId;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.emergencyContact = emergencyContact;
    }

    @Override
    public String getRoleDisplayName() {
        return "Patient";
    }

    @Override
    public String getProfileSummary() {
        return String.format("%s - Blood: %s, Gender: %s, Phone: %s",
                name, bloodGroup != null ? bloodGroup : "N/A", gender, phone);
    }

    // Getters and Setters
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
}
