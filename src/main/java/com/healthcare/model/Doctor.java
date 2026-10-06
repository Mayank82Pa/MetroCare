package com.healthcare.model;

import java.math.BigDecimal;

/**
 * Doctor Class extending User (Demonstrating Inheritance, Encapsulation & Polymorphism)
 */
public class Doctor extends User {
    private static final long serialVersionUID = 1L;

    private int doctorId;
    private String specialization;
    private String qualification;
    private int experienceYears;
    private BigDecimal consultationFee;
    private String bio;
    
    // Calculated/aggregated metrics
    private double averageRating;
    private int totalReviews;

    public Doctor() {
        super();
        this.role = "DOCTOR";
        this.consultationFee = BigDecimal.ZERO;
    }

    public Doctor(int userId, String name, String email, String password, String phone, String status,
                  int doctorId, String specialization, String qualification, int experienceYears,
                  BigDecimal consultationFee, String bio) {
        super(userId, name, email, password, "DOCTOR", phone, status);
        this.doctorId = doctorId;
        this.specialization = specialization;
        this.qualification = qualification;
        this.experienceYears = experienceYears;
        this.consultationFee = consultationFee != null ? consultationFee : BigDecimal.ZERO;
        this.bio = bio;
    }

    @Override
    public String getRoleDisplayName() {
        return "Doctor / Specialist";
    }

    @Override
    public String getProfileSummary() {
        return String.format("%s (%s) - %s, Exp: %d yrs, Fee: $%.2f",
                name, specialization, qualification, experienceYears, consultationFee);
    }

    // Getters and Setters
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }
}
