package com.healthcare.dao;

import com.healthcare.exception.DatabaseException;
import com.healthcare.model.Admin;
import com.healthcare.model.Doctor;
import com.healthcare.model.Patient;
import com.healthcare.model.User;
import com.healthcare.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO handles authentication, user registration, and role-based entity instantiation.
 * Demonstrates: PreparedStatement, ResultSet, Exception Handling, Polymorphic Instantiation.
 */
public class UserDAO extends BaseDAO {

    /**
     * Authenticates a user by email and plain password.
     *
     * @param email User email
     * @param plainPassword Raw password
     * @return User object (Admin, Doctor, or Patient) if valid, or null
     */
    public User authenticate(String email, String plainPassword) {
        String sql = "SELECT user_id, name, email, password, role, phone, status, created_at FROM users WHERE LOWER(email) = LOWER(?) AND status = 'ACTIVE'";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email.trim());
            rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPasswordHash = rs.getString("password");
                String inputHash = PasswordUtil.hashPassword(plainPassword);

                // Check both SHA-256 hash and plain-text fallback (for backward compatibility)
                if (storedPasswordHash.equalsIgnoreCase(inputHash) || storedPasswordHash.equals(plainPassword)) {
                    return mapResultSetToUser(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Authentication query failed: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    /**
     * Finds a user by ID.
     */
    public User findById(int userId) {
        String sql = "SELECT user_id, name, email, password, role, phone, status, created_at FROM users WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to find user by ID: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    /**
     * Finds a user by Email.
     */
    public User findByEmail(String email) {
        String sql = "SELECT user_id, name, email, password, role, phone, status, created_at FROM users WHERE LOWER(email) = LOWER(?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email.trim());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to find user by email: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    /**
     * Creates a new User and returns the generated primary key.
     */
    public int createUser(User user, Connection externalConn) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, role, phone, status) VALUES (?, ?, ?, ?, ?, ?)";
        boolean isInternalConn = (externalConn == null);
        Connection conn = isInternalConn ? getConnection() : externalConn;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail().trim());
            stmt.setString(3, PasswordUtil.hashPassword(user.getPassword()));
            stmt.setString(4, user.getRole().toUpperCase());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getStatus() != null ? user.getStatus() : "ACTIVE");

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newId = generatedKeys.getInt(1);
                user.setUserId(newId);
                return newId;
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } finally {
            if (generatedKeys != null) generatedKeys.close();
            if (stmt != null) stmt.close();
            if (isInternalConn) {
                close(conn, null, null);
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT user_id, name, email, password, role, phone, status, created_at FROM users ORDER BY user_id DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToUser(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to retrieve users: " + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, phone = ?, status = ? WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getStatus());
            stmt.setInt(4, user.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update user: " + e.getMessage(), e);
        } finally {
            close(conn, stmt);
        }
    }

    public boolean updatePassword(int userId, String newPlainPassword) {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, PasswordUtil.hashPassword(newPlainPassword));
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update password: " + e.getMessage(), e);
        } finally {
            close(conn, stmt);
        }
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete user: " + e.getMessage(), e);
        } finally {
            close(conn, stmt);
        }
    }

    /**
     * Maps ResultSet row to polymorphic User subclass (Polymorphism).
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String pass = rs.getString("password");
        String role = rs.getString("role");
        String phone = rs.getString("phone");
        String status = rs.getString("status");

        User user;
        if ("ADMIN".equalsIgnoreCase(role)) {
            user = new Admin(id, name, email, pass, phone, status);
        } else if ("DOCTOR".equalsIgnoreCase(role)) {
            user = new Doctor();
            user.setUserId(id);
            user.setName(name);
            user.setEmail(email);
            user.setPassword(pass);
            user.setRole("DOCTOR");
            user.setPhone(phone);
            user.setStatus(status);
        } else {
            user = new Patient();
            user.setUserId(id);
            user.setName(name);
            user.setEmail(email);
            user.setPassword(pass);
            user.setRole("PATIENT");
            user.setPhone(phone);
            user.setStatus(status);
        }
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}
