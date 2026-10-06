package com.healthcare.dao;

import com.healthcare.exception.DatabaseException;
import com.healthcare.model.Patient;
import com.healthcare.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * PatientDAO handles patient profiles, demographic data, and registration transactions.
 */
public class PatientDAO extends BaseDAO {

    private final UserDAO userDAO = new UserDAO();

    public Patient getPatientById(int patientId) {
        String sql = "SELECT p.patient_id, p.user_id, p.date_of_birth, p.gender, p.blood_group, " +
                "p.address, p.emergency_contact, u.name, u.email, u.phone, u.status, u.created_at " +
                "FROM patients p " +
                "JOIN users u ON p.user_id = u.user_id " +
                "WHERE p.patient_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patientId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPatient(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get patient by ID: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public Patient getPatientByUserId(int userId) {
        String sql = "SELECT p.patient_id, p.user_id, p.date_of_birth, p.gender, p.blood_group, " +
                "p.address, p.emergency_contact, u.name, u.email, u.phone, u.status, u.created_at " +
                "FROM patients p " +
                "JOIN users u ON p.user_id = u.user_id " +
                "WHERE p.user_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPatient(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get patient by user ID: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT p.patient_id, p.user_id, p.date_of_birth, p.gender, p.blood_group, " +
                "p.address, p.emergency_contact, u.name, u.email, u.phone, u.status, u.created_at " +
                "FROM patients p " +
                "JOIN users u ON p.user_id = u.user_id " +
                "ORDER BY p.patient_id ASC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToPatient(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to retrieve all patients: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    /**
     * Registers a new patient using an atomic transaction across users and patients tables.
     */
    public boolean registerPatient(Patient patient) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            DBConnection.beginTransaction(conn);

            // 1. Create base user record
            patient.setRole("PATIENT");
            int userId = userDAO.createUser(patient, conn);

            // 2. Create patient profile
            String sql = "INSERT INTO patients (user_id, date_of_birth, gender, blood_group, address, emergency_contact) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, userId);
            stmt.setDate(2, patient.getDateOfBirth());
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getBloodGroup());
            stmt.setString(5, patient.getAddress());
            stmt.setString(6, patient.getEmergencyContact());
            stmt.executeUpdate();

            DBConnection.commitTransaction(conn);
            return true;
        } catch (Exception e) {
            DBConnection.rollbackTransaction(conn);
            throw new DatabaseException("Patient registration failed: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, null);
        }
    }

    public boolean updatePatientProfile(Patient patient) {
        Connection conn = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtPat = null;

        try {
            conn = getConnection();
            DBConnection.beginTransaction(conn);

            // 1. Update users table
            String sqlUser = "UPDATE users SET name = ?, phone = ? WHERE user_id = ?";
            stmtUser = conn.prepareStatement(sqlUser);
            stmtUser.setString(1, patient.getName());
            stmtUser.setString(2, patient.getPhone());
            stmtUser.setInt(3, patient.getUserId());
            stmtUser.executeUpdate();

            // 2. Update patients table
            String sqlPat = "UPDATE patients SET date_of_birth = ?, gender = ?, blood_group = ?, address = ?, emergency_contact = ? WHERE patient_id = ?";
            stmtPat = conn.prepareStatement(sqlPat);
            stmtPat.setDate(1, patient.getDateOfBirth());
            stmtPat.setString(2, patient.getGender());
            stmtPat.setString(3, patient.getBloodGroup());
            stmtPat.setString(4, patient.getAddress());
            stmtPat.setString(5, patient.getEmergencyContact());
            stmtPat.setInt(6, patient.getPatientId());
            stmtPat.executeUpdate();

            DBConnection.commitTransaction(conn);
            return true;
        } catch (Exception e) {
            DBConnection.rollbackTransaction(conn);
            throw new DatabaseException("Failed to update patient profile: " + e.getMessage(), e);
        } finally {
            if (stmtUser != null) try { stmtUser.close(); } catch (SQLException ignored) {}
            if (stmtPat != null) try { stmtPat.close(); } catch (SQLException ignored) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        }
    }

    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        p.setPatientId(rs.getInt("patient_id"));
        p.setUserId(rs.getInt("user_id"));
        p.setName(rs.getString("name"));
        p.setEmail(rs.getString("email"));
        p.setPhone(rs.getString("phone"));
        p.setStatus(rs.getString("status"));
        p.setRole("PATIENT");
        p.setCreatedAt(rs.getTimestamp("created_at"));

        p.setDateOfBirth(rs.getDate("date_of_birth"));
        p.setGender(rs.getString("gender"));
        p.setBloodGroup(rs.getString("blood_group"));
        p.setAddress(rs.getString("address"));
        p.setEmergencyContact(rs.getString("emergency_contact"));
        return p;
    }
}
