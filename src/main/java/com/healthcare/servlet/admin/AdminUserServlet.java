package com.healthcare.servlet.admin;

import com.healthcare.dao.UserDAO;
import com.healthcare.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * AdminUserServlet handles listing, updating status, and deleting user accounts.
 */
@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String userIdStr = request.getParameter("id");

        if ("delete".equalsIgnoreCase(action) && userIdStr != null) {
            try {
                int userId = Integer.parseInt(userIdStr);
                userDAO.deleteUser(userId);
                request.getSession().setAttribute("successMessage", "User deleted successfully.");
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Failed to delete user: " + e.getMessage());
            }
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        List<User> users = userDAO.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/admin/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("toggleStatus".equalsIgnoreCase(action)) {
            try {
                int userId = Integer.parseInt(request.getParameter("userId"));
                String newStatus = request.getParameter("status");
                User user = userDAO.findById(userId);
                if (user != null) {
                    user.setStatus(newStatus);
                    userDAO.updateUser(user);
                    request.getSession().setAttribute("successMessage", "User status updated to " + newStatus);
                }
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Failed to update status: " + e.getMessage());
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}
