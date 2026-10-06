package com.healthcare.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Feedback Model Class representing ratings (1-5 stars) and reviews for doctors.
 */
public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;

    private int feedbackId;
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private int rating;
    private String comments;
    private Timestamp createdAt;

    // Joined Details
    private String patientName;
    private String doctorName;
    private String doctorSpecialization;

    public Feedback() {}

    public Feedback(int feedbackId, int appointmentId, int patientId, int doctorId, int rating, String comments) {
        this.feedbackId = feedbackId;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.rating = rating;
        this.comments = comments;
    }

    // Getters and Setters
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }
}
