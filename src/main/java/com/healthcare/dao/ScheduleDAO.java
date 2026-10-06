package com.healthcare.dao;

import com.healthcare.exception.DatabaseException;
import com.healthcare.model.DoctorSchedule;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ScheduleDAO handles doctor working availability, date blocks, and time slot configurations.
 */
public class ScheduleDAO extends BaseDAO {

    public boolean addSchedule(DoctorSchedule schedule) {
        String sql = "INSERT INTO doctor_schedule (doctor_id, available_date, start_time, end_time, slot_duration_mins, is_available) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, schedule.getDoctorId());
            stmt.setDate(2, schedule.getAvailableDate());
            stmt.setTime(3, schedule.getStartTime());
            stmt.setTime(4, schedule.getEndTime());
            stmt.setInt(5, schedule.getSlotDurationMins() > 0 ? schedule.getSlotDurationMins() : 30);
            stmt.setBoolean(6, schedule.isAvailable());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    schedule.setScheduleId(keys.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to add doctor schedule: " + e.getMessage(), e);
        } finally {
            close(conn, stmt);
        }
    }

    public List<DoctorSchedule> getSchedulesByDoctor(int doctorId) {
        String sql = "SELECT s.schedule_id, s.doctor_id, s.available_date, s.start_time, s.end_time, " +
                "s.slot_duration_mins, s.is_available, u.name AS doctor_name, d.specialization " +
                "FROM doctor_schedule s " +
                "JOIN doctors d ON s.doctor_id = d.doctor_id " +
                "JOIN users u ON d.user_id = u.user_id " +
                "WHERE s.doctor_id = ? " +
                "ORDER BY s.available_date ASC, s.start_time ASC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DoctorSchedule> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, doctorId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToSchedule(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get doctor schedules: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public DoctorSchedule getScheduleForDoctorOnDate(int doctorId, Date date) {
        String sql = "SELECT s.schedule_id, s.doctor_id, s.available_date, s.start_time, s.end_time, " +
                "s.slot_duration_mins, s.is_available, u.name AS doctor_name, d.specialization " +
                "FROM doctor_schedule s " +
                "JOIN doctors d ON s.doctor_id = d.doctor_id " +
                "JOIN users u ON d.user_id = u.user_id " +
                "WHERE s.doctor_id = ? AND s.available_date = ? AND s.is_available = TRUE " +
                "ORDER BY s.schedule_id DESC LIMIT 1";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, doctorId);
            stmt.setDate(2, date);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToSchedule(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to find doctor schedule for date: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public boolean deleteSchedule(int scheduleId, int doctorId) {
        String sql = "DELETE FROM doctor_schedule WHERE schedule_id = ? AND doctor_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, scheduleId);
            stmt.setInt(2, doctorId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete schedule: " + e.getMessage(), e);
        } finally {
            close(conn, stmt);
        }
    }

    private DoctorSchedule mapResultSetToSchedule(ResultSet rs) throws SQLException {
        DoctorSchedule s = new DoctorSchedule();
        s.setScheduleId(rs.getInt("schedule_id"));
        s.setDoctorId(rs.getInt("doctor_id"));
        s.setAvailableDate(rs.getDate("available_date"));
        s.setStartTime(rs.getTime("start_time"));
        s.setEndTime(rs.getTime("end_time"));
        s.setSlotDurationMins(rs.getInt("slot_duration_mins"));
        s.setAvailable(rs.getBoolean("is_available"));
        s.setDoctorName(rs.getString("doctor_name"));
        s.setSpecialization(rs.getString("specialization"));
        return s;
    }
}
