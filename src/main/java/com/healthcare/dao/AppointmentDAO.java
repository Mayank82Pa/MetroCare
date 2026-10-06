package com.healthcare.dao;

import com.healthcare.exception.DatabaseException;
import com.healthcare.exception.ValidationException;
import com.healthcare.model.Appointment;
import com.healthcare.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * AppointmentDAO manages appointment scheduling, slot conflict prevention, and status workflows.
 * Demonstrates: PreparedStatement, Transaction Management, Multi-table JOINs, Concurrency Safety.
 */
public class AppointmentDAO extends BaseDAO {

    public Appointment getAppointmentById(int appointmentId) {
        String sql = "SELECT a.appointment_id, a.patient_id, a.doctor_id, a.schedule_id, " +
                "a.appointment_date, a.appointment_time, a.reason, a.status, a.created_at, a.updated_at, " +
                "u_pat.name AS patient_name, u_pat.phone AS patient_phone, u_pat.email AS patient_email, " +
                "u_doc.name AS doctor_name, d.specialization AS doctor_specialization, d.consultation_fee AS doctor_fee " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN users u_pat ON p.user_id = u_pat.user_id " +
                "JOIN doctors d ON a.doctor_id = d.doctor_id " +
                "JOIN users u_doc ON d.user_id = u_doc.user_id " +
                "WHERE a.appointment_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, appointmentId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToAppointment(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get appointment by ID: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    /**
     * Atomic Appointment Booking with Double-Booking Conflict Validation.
     */
    public boolean bookAppointment(Appointment appointment) throws ValidationException {
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            DBConnection.beginTransaction(conn);

            // 1. Conflict Check: Ensure doctor has no existing active appointment at that date & time
            String checkSql = "SELECT COUNT(*) FROM appointments " +
                    "WHERE doctor_id = ? AND appointment_date = ? AND appointment_time = ? " +
                    "AND status IN ('PENDING', 'CONFIRMED')";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, appointment.getDoctorId());
            checkStmt.setDate(2, appointment.getAppointmentDate());
            checkStmt.setTime(3, appointment.getAppointmentTime());
            rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                throw new ValidationException("Selected time slot is already booked. Please select another slot.");
            }

            // 2. Insert Appointment
            String insertSql = "INSERT INTO appointments (patient_id, doctor_id, schedule_id, appointment_date, appointment_time, reason, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, appointment.getPatientId());
            insertStmt.setInt(2, appointment.getDoctorId());
            if (appointment.getScheduleId() != null && appointment.getScheduleId() > 0) {
                insertStmt.setInt(3, appointment.getScheduleId());
            } else {
                insertStmt.setNull(3, java.sql.Types.INTEGER);
            }
            insertStmt.setDate(4, appointment.getAppointmentDate());
            insertStmt.setTime(5, appointment.getAppointmentTime());
            insertStmt.setString(6, appointment.getReason());
            insertStmt.setString(7, appointment.getStatus() != null ? appointment.getStatus() : "PENDING");

            int rows = insertStmt.executeUpdate();
            if (rows > 0) {
                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    appointment.setAppointmentId(generatedKeys.getInt(1));
                }
            }

            DBConnection.commitTransaction(conn);
            return true;
        } catch (ValidationException e) {
            DBConnection.rollbackTransaction(conn);
            throw e;
        } catch (SQLException e) {
            DBConnection.rollbackTransaction(conn);
            throw new DatabaseException("Failed to book appointment: " + e.getMessage(), e);
        } finally {
            close(null, checkStmt, rs);
            close(conn, insertStmt, null);
        }
    }

    public List<Appointment> getAppointmentsByPatient(int patientId) {
        String sql = "SELECT a.appointment_id, a.patient_id, a.doctor_id, a.schedule_id, " +
                "a.appointment_date, a.appointment_time, a.reason, a.status, a.created_at, a.updated_at, " +
                "u_pat.name AS patient_name, u_pat.phone AS patient_phone, u_pat.email AS patient_email, " +
                "u_doc.name AS doctor_name, d.specialization AS doctor_specialization, d.consultation_fee AS doctor_fee " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN users u_pat ON p.user_id = u_pat.user_id " +
                "JOIN doctors d ON a.doctor_id = d.doctor_id " +
                "JOIN users u_doc ON d.user_id = u_doc.user_id " +
                "WHERE a.patient_id = ? " +
                "ORDER BY a.appointment_date DESC, a.appointment_time DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Appointment> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patientId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToAppointment(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get patient appointments: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        String sql = "SELECT a.appointment_id, a.patient_id, a.doctor_id, a.schedule_id, " +
                "a.appointment_date, a.appointment_time, a.reason, a.status, a.created_at, a.updated_at, " +
                "u_pat.name AS patient_name, u_pat.phone AS patient_phone, u_pat.email AS patient_email, " +
                "u_doc.name AS doctor_name, d.specialization AS doctor_specialization, d.consultation_fee AS doctor_fee " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN users u_pat ON p.user_id = u_pat.user_id " +
                "JOIN doctors d ON a.doctor_id = d.doctor_id " +
                "JOIN users u_doc ON d.user_id = u_doc.user_id " +
                "WHERE a.doctor_id = ? " +
                "ORDER BY a.appointment_date ASC, a.appointment_time ASC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Appointment> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, doctorId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToAppointment(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get doctor appointments: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public List<Appointment> getAllAppointments() {
        String sql = "SELECT a.appointment_id, a.patient_id, a.doctor_id, a.schedule_id, " +
                "a.appointment_date, a.appointment_time, a.reason, a.status, a.created_at, a.updated_at, " +
                "u_pat.name AS patient_name, u_pat.phone AS patient_phone, u_pat.email AS patient_email, " +
                "u_doc.name AS doctor_name, d.specialization AS doctor_specialization, d.consultation_fee AS doctor_fee " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN users u_pat ON p.user_id = u_pat.user_id " +
                "JOIN doctors d ON a.doctor_id = d.doctor_id " +
                "JOIN users u_doc ON d.user_id = u_doc.user_id " +
                "ORDER BY a.appointment_date DESC, a.appointment_time DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Appointment> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToAppointment(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get all appointments: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public boolean updateStatus(int appointmentId, String newStatus) {
        String sql = "UPDATE appointments SET status = ?, updated_at = CURRENT_TIMESTAMP WHERE appointment_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStatus.toUpperCase());
            stmt.setInt(2, appointmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update appointment status: " + e.getMessage(), e);
        } finally {
            close(conn, stmt);
        }
    }

    public boolean cancelAppointment(int appointmentId, int patientOrDoctorId, boolean isPatient) {
        String sql = isPatient ?
                "UPDATE appointments SET status = 'CANCELLED', updated_at = CURRENT_TIMESTAMP WHERE appointment_id = ? AND patient_id = ?" :
                "UPDATE appointments SET status = 'CANCELLED', updated_at = CURRENT_TIMESTAMP WHERE appointment_id = ? AND doctor_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, appointmentId);
            stmt.setInt(2, patientOrDoctorId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to cancel appointment: " + e.getMessage(), e);
        } finally {
            close(conn, stmt);
        }
    }

    public List<Time> getBookedTimesForDoctorOnDate(int doctorId, Date date) {
        List<Time> bookedTimes = new ArrayList<>();
        String sql = "SELECT appointment_time FROM appointments WHERE doctor_id = ? AND appointment_date = ? AND status IN ('PENDING', 'CONFIRMED')";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, doctorId);
            stmt.setDate(2, date);
            rs = stmt.executeQuery();
            while (rs.next()) {
                bookedTimes.add(rs.getTime("appointment_time"));
            }
            return bookedTimes;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get booked times: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    private Appointment mapResultSetToAppointment(ResultSet rs) throws SQLException {
        Appointment a = new Appointment();
        a.setAppointmentId(rs.getInt("appointment_id"));
        a.setPatientId(rs.getInt("patient_id"));
        a.setDoctorId(rs.getInt("doctor_id"));
        int scheduleId = rs.getInt("schedule_id");
        a.setScheduleId(rs.wasNull() ? null : scheduleId);
        a.setAppointmentDate(rs.getDate("appointment_date"));
        a.setAppointmentTime(rs.getTime("appointment_time"));
        a.setReason(rs.getString("reason"));
        a.setStatus(rs.getString("status"));
        a.setCreatedAt(rs.getTimestamp("created_at"));
        a.setUpdatedAt(rs.getTimestamp("updated_at"));

        a.setPatientName(rs.getString("patient_name"));
        a.setPatientPhone(rs.getString("patient_phone"));
        a.setPatientEmail(rs.getString("patient_email"));
        a.setDoctorName(rs.getString("doctor_name"));
        a.setDoctorSpecialization(rs.getString("doctor_specialization"));
        a.setDoctorFee(rs.getDouble("doctor_fee"));
        return a;
    }
}
