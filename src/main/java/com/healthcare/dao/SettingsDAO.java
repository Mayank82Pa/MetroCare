package com.healthcare.dao;

import com.healthcare.exception.DatabaseException;
import com.healthcare.model.SystemSetting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * SettingsDAO manages application configuration parameters.
 */
public class SettingsDAO extends BaseDAO {

    public List<SystemSetting> getAllSettings() {
        List<SystemSetting> list = new ArrayList<>();
        String sql = "SELECT setting_id, setting_key, setting_value, description FROM system_settings ORDER BY setting_id ASC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new SystemSetting(
                        rs.getInt("setting_id"),
                        rs.getString("setting_key"),
                        rs.getString("setting_value"),
                        rs.getString("description")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get system settings: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public String getSettingValue(String key, String defaultValue) {
        String sql = "SELECT setting_value FROM system_settings WHERE setting_key = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, key);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("setting_value");
            }
            return defaultValue;
        } catch (SQLException e) {
            return defaultValue;
        } finally {
            close(conn, stmt, rs);
        }
    }

    public boolean updateSetting(String key, String value) {
        String sql = "UPDATE system_settings SET setting_value = ? WHERE setting_key = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, value);
            stmt.setString(2, key);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update setting: " + e.getMessage(), e);
        } finally {
            close(conn, stmt);
        }
    }
}
