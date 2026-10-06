package com.healthcare.dao;

import com.healthcare.exception.DatabaseException;
import com.healthcare.model.AnalyticsSummary;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * AnalyticsDAO executes aggregated SQL reporting queries for Admin insights.
 * Demonstrates: SQL Aggregations (COUNT, SUM, AVG, GROUP BY), Collections (Map, HashMap).
 */
public class AnalyticsDAO extends BaseDAO {

    public AnalyticsSummary getAdminAnalytics() {
        AnalyticsSummary summary = new AnalyticsSummary();
        Connection conn = null;

        try {
            conn = getConnection();

            // 1. Total counts by role
            String userCountSql = "SELECT role, COUNT(*) AS count FROM users GROUP BY role";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(userCountSql)) {
                int total = 0;
                while (rs.next()) {
                    String role = rs.getString("role");
                    int count = rs.getInt("count");
                    total += count;
                    if ("DOCTOR".equalsIgnoreCase(role)) {
                        summary.setTotalDoctors(count);
                    } else if ("PATIENT".equalsIgnoreCase(role)) {
                        summary.setTotalPatients(count);
                    }
                }
                summary.setTotalUsers(total);
            }

            // 2. Total appointments by status
            String apptSql = "SELECT status, COUNT(*) AS count FROM appointments GROUP BY status";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(apptSql)) {
                int totalAppts = 0;
                while (rs.next()) {
                    String status = rs.getString("status");
                    int count = rs.getInt("count");
                    totalAppts += count;
                    if ("PENDING".equalsIgnoreCase(status)) summary.setPendingAppointments(count);
                    else if ("CONFIRMED".equalsIgnoreCase(status)) summary.setConfirmedAppointments(count);
                    else if ("COMPLETED".equalsIgnoreCase(status)) summary.setCompletedAppointments(count);
                    else if ("CANCELLED".equalsIgnoreCase(status)) summary.setCancelledAppointments(count);
                }
                summary.setTotalAppointments(totalAppts);
            }

            // 3. Total Estimated Revenue & Overall Rating
            String revSql = "SELECT COALESCE(SUM(d.consultation_fee), 0) AS total_rev " +
                    "FROM appointments a JOIN doctors d ON a.doctor_id = d.doctor_id " +
                    "WHERE a.status IN ('CONFIRMED', 'COMPLETED')";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(revSql)) {
                if (rs.next()) {
                    summary.setTotalRevenue(rs.getBigDecimal("total_rev"));
                }
            }

            String ratingSql = "SELECT COALESCE(AVG(rating), 0) AS avg_rat FROM feedback";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(ratingSql)) {
                if (rs.next()) {
                    summary.setOverallDoctorRating(rs.getDouble("avg_rat"));
                }
            }

            // 4. Specialization distribution
            Map<String, Integer> specMap = new HashMap<>();
            String specSql = "SELECT specialization, COUNT(*) AS count FROM doctors GROUP BY specialization";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(specSql)) {
                while (rs.next()) {
                    specMap.put(rs.getString("specialization"), rs.getInt("count"));
                }
            }
            summary.setSpecializationCounts(specMap);

            return summary;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to generate analytics: " + e.getMessage(), e);
        } finally {
            close(conn, null, null);
        }
    }
}
