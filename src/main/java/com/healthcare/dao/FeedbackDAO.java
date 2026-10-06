package com.healthcare.dao;

import com.healthcare.exception.DatabaseException;
import com.healthcare.model.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * FeedbackDAO handles patient ratings and doctor reviews.
 */
public class FeedbackDAO extends BaseDAO {

    public boolean submitFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback (appointment_id, patient_id, doctor_id, rating, comments) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, feedback.getAppointmentId());
            stmt.setInt(2, feedback.getPatientId());
            stmt.setInt(3, feedback.getDoctorId());
            stmt.setInt(4, feedback.getRating());
            stmt.setString(5, feedback.getComments());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    feedback.setFeedbackId(keys.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to submit feedback: " + e.getMessage(), e);
        } finally {
            close(conn, stmt);
        }
    }

    public List<Feedback> getFeedbackByDoctor(int doctorId) {
        String sql = "SELECT f.feedback_id, f.appointment_id, f.patient_id, f.doctor_id, f.rating, f.comments, f.created_at, " +
                "u_pat.name AS patient_name, u_doc.name AS doctor_name, d.specialization AS doctor_specialization " +
                "FROM feedback f " +
                "JOIN patients p ON f.patient_id = p.patient_id " +
                "JOIN users u_pat ON p.user_id = u_pat.user_id " +
                "JOIN doctors d ON f.doctor_id = d.doctor_id " +
                "JOIN users u_doc ON d.user_id = u_doc.user_id " +
                "WHERE f.doctor_id = ? " +
                "ORDER BY f.created_at DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Feedback> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, doctorId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToFeedback(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get feedback by doctor: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public boolean hasFeedbackForAppointment(int appointmentId) {
        String sql = "SELECT COUNT(*) FROM feedback WHERE appointment_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, appointmentId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to check feedback existence: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    private Feedback mapResultSetToFeedback(ResultSet rs) throws SQLException {
        Feedback f = new Feedback();
        f.setFeedbackId(rs.getInt("feedback_id"));
        f.setAppointmentId(rs.getInt("appointment_id"));
        f.setPatientId(rs.getInt("patient_id"));
        f.setDoctorId(rs.getInt("doctor_id"));
        f.setRating(rs.getInt("rating"));
        f.setComments(rs.getString("comments"));
        f.setCreatedAt(rs.getTimestamp("created_at"));

        f.setPatientName(rs.getString("patient_name"));
        f.setDoctorName(rs.getString("doctor_name"));
        f.setDoctorSpecialization(rs.getString("doctor_specialization"));
        return f;
    }
}
