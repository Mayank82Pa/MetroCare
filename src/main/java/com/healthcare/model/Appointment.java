package com.healthcare.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Appointment Model Class representing consultation bookings between Patient and Doctor.
 */
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Status {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }

    private int appointmentId;
    private int patientId;
    private int doctorId;
    private Integer scheduleId;
    private Date appointmentDate;
    private Time appointmentTime;
    private String reason;
    private String status; // PENDING, CONFIRMED, CANCELLED, COMPLETED
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Joined display details for easy rendering in JSPs
    private String patientName;
    private String patientPhone;
    private String patientEmail;
    private String doctorName;
    private String doctorSpecialization;
    private double doctorFee;

    public Appointment() {
        this.status = Status.PENDING.name();
    }

    public Appointment(int appointmentId, int patientId, int doctorId, Integer scheduleId,
                       Date appointmentDate, Time appointmentTime, String reason, String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.scheduleId = scheduleId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.status = status != null ? status : Status.PENDING.name();
    }

    // Helper status checks
    public boolean isPending() {
        return Status.PENDING.name().equalsIgnoreCase(this.status);
    }

    public boolean isConfirmed() {
        return Status.CONFIRMED.name().equalsIgnoreCase(this.status);
    }

    public boolean isCancelled() {
        return Status.CANCELLED.name().equalsIgnoreCase(this.status);
    }

    public boolean isCompleted() {
        return Status.COMPLETED.name().equalsIgnoreCase(this.status);
    }

    // Getters and Setters
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

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
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

    public double getDoctorFee() {
        return doctorFee;
    }

    public void setDoctorFee(double doctorFee) {
        this.doctorFee = doctorFee;
    }
}
