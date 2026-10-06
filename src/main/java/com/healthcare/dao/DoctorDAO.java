package com.healthcare.dao;

import com.healthcare.exception.DatabaseException;
import com.healthcare.model.Doctor;
import com.healthcare.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DoctorDAO handles doctor profile persistence, search by specialization, and rating metrics.
 */
public class DoctorDAO extends BaseDAO {

    private final UserDAO userDAO = new UserDAO();

    public Doctor getDoctorById(int doctorId) {
        String sql = "SELECT d.doctor_id, d.user_id, d.specialization, d.qualification, d.experience_years, " +
                "d.consultation_fee, d.bio, u.name, u.email, u.phone, u.status, u.created_at, " +
                "COALESCE(AVG(f.rating), 0) AS avg_rating, COUNT(f.feedback_id) AS total_reviews " +
                "FROM doctors d " +
                "JOIN users u ON d.user_id = u.user_id " +
                "LEFT JOIN feedback f ON d.doctor_id = f.doctor_id " +
                "WHERE d.doctor_id = ? " +
                "GROUP BY d.doctor_id, d.user_id, d.specialization, d.qualification, d.experience_years, " +
                "d.consultation_fee, d.bio, u.name, u.email, u.phone, u.status, u.created_at";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, doctorId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToDoctor(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get doctor by ID: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public Doctor getDoctorByUserId(int userId) {
        String sql = "SELECT d.doctor_id, d.user_id, d.specialization, d.qualification, d.experience_years, " +
                "d.consultation_fee, d.bio, u.name, u.email, u.phone, u.status, u.created_at, " +
                "COALESCE(AVG(f.rating), 0) AS avg_rating, COUNT(f.feedback_id) AS total_reviews " +
                "FROM doctors d " +
                "JOIN users u ON d.user_id = u.user_id " +
                "LEFT JOIN feedback f ON d.doctor_id = f.doctor_id " +
                "WHERE d.user_id = ? " +
                "GROUP BY d.doctor_id, d.user_id, d.specialization, d.qualification, d.experience_years, " +
                "d.consultation_fee, d.bio, u.name, u.email, u.phone, u.status, u.created_at";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToDoctor(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get doctor by User ID: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT d.doctor_id, d.user_id, d.specialization, d.qualification, d.experience_years, " +
                "d.consultation_fee, d.bio, u.name, u.email, u.phone, u.status, u.created_at, " +
                "COALESCE(AVG(f.rating), 0) AS avg_rating, COUNT(f.feedback_id) AS total_reviews " +
                "FROM doctors d " +
                "JOIN users u ON d.user_id = u.user_id " +
                "LEFT JOIN feedback f ON d.doctor_id = f.doctor_id " +
                "GROUP BY d.doctor_id, d.user_id, d.specialization, d.qualification, d.experience_years, " +
                "d.consultation_fee, d.bio, u.name, u.email, u.phone, u.status, u.created_at " +
                "ORDER BY d.doctor_id ASC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToDoctor(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get all doctors: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public List<Doctor> searchDoctors(String keyword, String specialization) {
        List<Doctor> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT d.doctor_id, d.user_id, d.specialization, d.qualification, d.experience_years, " +
                "d.consultation_fee, d.bio, u.name, u.email, u.phone, u.status, u.created_at, " +
                "COALESCE(AVG(f.rating), 0) AS avg_rating, COUNT(f.feedback_id) AS total_reviews " +
                "FROM doctors d " +
                "JOIN users u ON d.user_id = u.user_id " +
                "LEFT JOIN feedback f ON d.doctor_id = f.doctor_id " +
                "WHERE u.status = 'ACTIVE' "
        );

        List<Object> params = new ArrayList<>();
        if (specialization != null && !specialization.trim().isEmpty() && !"ALL".equalsIgnoreCase(specialization.trim())) {
            sql.append("AND LOWER(d.specialization) = LOWER(?) ");
            params.add(specialization.trim());
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (LOWER(u.name) LIKE LOWER(?) OR LOWER(d.specialization) LIKE LOWER(?) OR LOWER(d.qualification) LIKE LOWER(?)) ");
            String term = "%" + keyword.trim() + "%";
            params.add(term);
            params.add(term);
            params.add(term);
        }

        sql.append("GROUP BY d.doctor_id, d.user_id, d.specialization, d.qualification, d.experience_years, " +
                "d.consultation_fee, d.bio, u.name, u.email, u.phone, u.status, u.created_at " +
                "ORDER BY avg_rating DESC, d.experience_years DESC");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToDoctor(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to search doctors: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public List<String> getAllSpecializations() {
        List<String> specs = new ArrayList<>();
        String sql = "SELECT DISTINCT specialization FROM doctors WHERE specialization IS NOT NULL AND specialization != '' ORDER BY specialization ASC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                specs.add(rs.getString("specialization"));
            }
            return specs;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get specializations: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    /**
     * Creates a doctor profile within a transaction (User + Doctor record).
     */
    public boolean registerDoctor(Doctor doctor) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            DBConnection.beginTransaction(conn);

            // 1. Create base user record
            doctor.setRole("DOCTOR");
            int userId = userDAO.createUser(doctor, conn);

            // 2. Create doctor profile record
            String sql = "INSERT INTO doctors (user_id, specialization, qualification, experience_years, consultation_fee, bio) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, userId);
            stmt.setString(2, doctor.getSpecialization());
            stmt.setString(3, doctor.getQualification());
            stmt.setInt(4, doctor.getExperienceYears());
            stmt.setBigDecimal(5, doctor.getConsultationFee() != null ? doctor.getConsultationFee() : BigDecimal.ZERO);
            stmt.setString(6, doctor.getBio());
            stmt.executeUpdate();

            DBConnection.commitTransaction(conn);
            return true;
        } catch (Exception e) {
            DBConnection.rollbackTransaction(conn);
            throw new DatabaseException("Doctor registration failed: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, null);
        }
    }

    public boolean updateDoctorProfile(Doctor doctor) {
        Connection conn = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtDoc = null;

        try {
            conn = getConnection();
            DBConnection.beginTransaction(conn);

            // 1. Update user fields
            String sqlUser = "UPDATE users SET name = ?, phone = ? WHERE user_id = ?";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setString(1, doctor.getName());
            stmtUser.setString(2, doctor.getPhone());
            stmtUser.setInt(3, doctor.getUserId());
            stmtUser.executeUpdate();

            // 2. Update doctor details
            String sqlDoc = "UPDATE doctors SET specialization = ?, qualification = ?, experience_years = ?, consultation_fee = ?, bio = ? WHERE doctor_id = ?";
            stmtDoc = conn.prepareStatement(sqlDoc);
            stmtDoc.setString(1, doctor.getSpecialization());
            stmtDoc.setString(2, doctor.getQualification());
            stmtDoc.setInt(3, doctor.getExperienceYears());
            stmtDoc.setBigDecimal(4, doctor.getConsultationFee());
            stmtDoc.setString(5, doctor.getBio());
            stmtDoc.setInt(6, doctor.getDoctorId());
            stmtDoc.executeUpdate();

            DBConnection.commitTransaction(conn);
            return true;
        } catch (Exception e) {
            DBConnection.rollbackTransaction(conn);
            throw new DatabaseException("Failed to update doctor profile: " + e.getMessage(), e);
        } finally {
            if (stmtUser != null) try { stmtUser.close(); } catch (SQLException ignored) {}
            if (stmtDoc != null) try { stmtDoc.close(); } catch (SQLException ignored) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    private Doctor mapResultSetToDoctor(ResultSet rs) throws SQLException {
        Doctor d = new Doctor();
        d.setDoctorId(rs.getInt("doctor_id"));
        d.setUserId(rs.getInt("user_id"));
        d.setName(rs.getString("name"));
        d.setEmail(rs.getString("email"));
        d.setPhone(rs.getString("phone"));
        d.setStatus(rs.getString("status"));
        d.setRole("DOCTOR");
        d.setCreatedAt(rs.getTimestamp("created_at"));

        d.setSpecialization(rs.getString("specialization"));
        d.setQualification(rs.getString("qualification"));
        d.setExperienceYears(rs.getInt("experience_years"));
        d.setConsultationFee(rs.getBigDecimal("consultation_fee"));
        d.setBio(rs.getString("bio"));

        d.setAverageRating(rs.getDouble("avg_rating"));
        d.setTotalReviews(rs.getInt("total_reviews"));
        return d;
    }
}
