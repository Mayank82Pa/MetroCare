package com.healthcare.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Analytics Summary Model Class used in the Admin and Doctor dashboards for metrics aggregation.
 * Demonstrates use of Java Collections Framework (Map, HashMap).
 */
public class AnalyticsSummary implements Serializable {
    private static final long serialVersionUID = 1L;

    private int totalUsers;
    private int totalDoctors;
    private int totalPatients;
    private int totalAppointments;
    private int pendingAppointments;
    private int confirmedAppointments;
    private int completedAppointments;
    private int cancelledAppointments;
    private BigDecimal totalRevenue;
    private double overallDoctorRating;

    // Distribution maps (Specialization -> Doctor Count, Status -> Appointment Count)
    private Map<String, Integer> specializationCounts = new HashMap<>();
    private Map<String, Integer> monthlyAppointmentCounts = new HashMap<>();

    public AnalyticsSummary() {
        this.totalRevenue = BigDecimal.ZERO;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getTotalDoctors() {
        return totalDoctors;
    }

    public void setTotalDoctors(int totalDoctors) {
        this.totalDoctors = totalDoctors;
    }

    public int getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(int totalPatients) {
        this.totalPatients = totalPatients;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(int totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public int getPendingAppointments() {
        return pendingAppointments;
    }

    public void setPendingAppointments(int pendingAppointments) {
        this.pendingAppointments = pendingAppointments;
    }

    public int getConfirmedAppointments() {
        return confirmedAppointments;
    }

    public void setConfirmedAppointments(int confirmedAppointments) {
        this.confirmedAppointments = confirmedAppointments;
    }

    public int getCompletedAppointments() {
        return completedAppointments;
    }

    public void setCompletedAppointments(int completedAppointments) {
        this.completedAppointments = completedAppointments;
    }

    public int getCancelledAppointments() {
        return cancelledAppointments;
    }

    public void setCancelledAppointments(int cancelledAppointments) {
        this.cancelledAppointments = cancelledAppointments;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getOverallDoctorRating() {
        return overallDoctorRating;
    }

    public void setOverallDoctorRating(double overallDoctorRating) {
        this.overallDoctorRating = overallDoctorRating;
    }

    public Map<String, Integer> getSpecializationCounts() {
        return specializationCounts;
    }

    public void setSpecializationCounts(Map<String, Integer> specializationCounts) {
        this.specializationCounts = specializationCounts;
    }

    public Map<String, Integer> getMonthlyAppointmentCounts() {
        return monthlyAppointmentCounts;
    }

    public void setMonthlyAppointmentCounts(Map<String, Integer> monthlyAppointmentCounts) {
        this.monthlyAppointmentCounts = monthlyAppointmentCounts;
    }
}
