package com.healthcare.dao;

import com.healthcare.exception.DatabaseException;
import com.healthcare.model.MedicalRecord;
import com.healthcare.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * MedicalRecordDAO handles patient diagnostic records, prescriptions, and clinical history.
 */
public class MedicalRecordDAO extends BaseDAO {

    public boolean addMedicalRecord(MedicalRecord record) {
        String sql = "INSERT INTO medical_records (appointment_id, patient_id, doctor_id, diagnosis, prescription, clinical_notes, follow_up_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            DBConnection.beginTransaction(conn);

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, record.getAppointmentId());
            stmt.setInt(2, record.getPatientId());
            stmt.setInt(3, record.getDoctorId());
            stmt.setString(4, record.getDiagnosis());
            stmt.setString(5, record.getPrescription());
            stmt.setString(6, record.getClinicalNotes());
            stmt.setDate(7, record.getFollowUpDate());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                // Also update appointment status to COMPLETED
                String updateAppt = "UPDATE appointments SET status = 'COMPLETED', updated_at = CURRENT_TIMESTAMP WHERE appointment_id = ?";
                try (PreparedStatement apptStmt = conn.prepareStatement(updateAppt)) {
                    apptStmt.setInt(1, record.getAppointmentId());
                    apptStmt.executeUpdate();
                }
            }

            DBConnection.commitTransaction(conn);
            return true;
        } catch (SQLException e) {
            DBConnection.rollbackTransaction(conn);
            throw new DatabaseException("Failed to add medical record: " + e.getMessage(), e);
        } finally {
            close(conn, stmt);
        }
    }

    public MedicalRecord getRecordByAppointmentId(int appointmentId) {
        String sql = "SELECT m.record_id, m.appointment_id, m.patient_id, m.doctor_id, m.diagnosis, " +
                "m.prescription, m.clinical_notes, m.follow_up_date, m.created_at, " +
                "u_pat.name AS patient_name, u_doc.name AS doctor_name, d.specialization AS doctor_specialization, " +
                "a.appointment_date " +
                "FROM medical_records m " +
                "JOIN appointments a ON m.appointment_id = a.appointment_id " +
                "JOIN patients p ON m.patient_id = p.patient_id " +
                "JOIN users u_pat ON p.user_id = u_pat.user_id " +
                "JOIN doctors d ON m.doctor_id = d.doctor_id " +
                "JOIN users u_doc ON d.user_id = u_doc.user_id " +
                "WHERE m.appointment_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, appointmentId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToMedicalRecord(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get medical record by appointment ID: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public List<MedicalRecord> getRecordsByPatientId(int patientId) {
        String sql = "SELECT m.record_id, m.appointment_id, m.patient_id, m.doctor_id, m.diagnosis, " +
                "m.prescription, m.clinical_notes, m.follow_up_date, m.created_at, " +
                "u_pat.name AS patient_name, u_doc.name AS doctor_name, d.specialization AS doctor_specialization, " +
                "a.appointment_date " +
                "FROM medical_records m " +
                "JOIN appointments a ON m.appointment_id = a.appointment_id " +
                "JOIN patients p ON m.patient_id = p.patient_id " +
                "JOIN users u_pat ON p.user_id = u_pat.user_id " +
                "JOIN doctors d ON m.doctor_id = d.doctor_id " +
                "JOIN users u_doc ON d.user_id = u_doc.user_id " +
                "WHERE m.patient_id = ? " +
                "ORDER BY m.created_at DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<MedicalRecord> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patientId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToMedicalRecord(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get patient medical history: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public List<MedicalRecord> getRecordsByDoctorId(int doctorId) {
        String sql = "SELECT m.record_id, m.appointment_id, m.patient_id, m.doctor_id, m.diagnosis, " +
                "m.prescription, m.clinical_notes, m.follow_up_date, m.created_at, " +
                "u_pat.name AS patient_name, u_doc.name AS doctor_name, d.specialization AS doctor_specialization, " +
                "a.appointment_date " +
                "FROM medical_records m " +
                "JOIN appointments a ON m.appointment_id = a.appointment_id " +
                "JOIN patients p ON m.patient_id = p.patient_id " +
                "JOIN users u_pat ON p.user_id = u_pat.user_id " +
                "JOIN doctors d ON m.doctor_id = d.doctor_id " +
                "JOIN users u_doc ON d.user_id = u_doc.user_id " +
                "WHERE m.doctor_id = ? " +
                "ORDER BY m.created_at DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<MedicalRecord> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, doctorId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToMedicalRecord(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get doctor medical records: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    private MedicalRecord mapResultSetToMedicalRecord(ResultSet rs) throws SQLException {
        MedicalRecord m = new MedicalRecord();
        m.setRecordId(rs.getInt("record_id"));
        m.setAppointmentId(rs.getInt("appointment_id"));
        m.setPatientId(rs.getInt("patient_id"));
        m.setDoctorId(rs.getInt("doctor_id"));
        m.setDiagnosis(rs.getString("diagnosis"));
        m.setPrescription(rs.getString("prescription"));
        m.setClinicalNotes(rs.getString("clinical_notes"));
        m.setFollowUpDate(rs.getDate("follow_up_date"));
        m.setCreatedAt(rs.getTimestamp("created_at"));

        m.setPatientName(rs.getString("patient_name"));
        m.setDoctorName(rs.getString("doctor_name"));
        m.setDoctorSpecialization(rs.getString("doctor_specialization"));
        m.setAppointmentDate(rs.getDate("appointment_date"));
        return m;
    }
}
