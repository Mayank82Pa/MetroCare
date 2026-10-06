package com.healthcare.servlet;

import com.healthcare.dao.DoctorDAO;
import com.healthcare.dao.PatientDAO;
import com.healthcare.dao.UserDAO;
import com.healthcare.model.Doctor;
import com.healthcare.model.Patient;
import com.healthcare.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LoginServlet handles user authentication, session binding, and role-based redirects.
 * Demonstrates: Servlet Lifecycle, Request/Response handling, Session Management.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    private DoctorDAO doctorDAO;
    private PatientDAO patientDAO;

    @Override
    public void init() throws ServletException {
        this.userDAO = new UserDAO();
        this.doctorDAO = new DoctorDAO();
        this.patientDAO = new PatientDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            User user = (User) session.getAttribute("currentUser");
            redirectToDashboard(user, response, request.getContextPath());
            return;
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please provide both email and password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = userDAO.authenticate(email.trim(), password.trim());
            if (user == null) {
                request.setAttribute("errorMessage", "Invalid email or password. Please try again.");
                request.setAttribute("enteredEmail", email);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            // Bind session and profile details
            HttpSession session = request.getSession(true);
            session.setAttribute("currentUser", user);

            if (user.isDoctor()) {
                Doctor doctor = doctorDAO.getDoctorByUserId(user.getUserId());
                session.setAttribute("currentDoctor", doctor);
            } else if (user.isPatient()) {
                Patient patient = patientDAO.getPatientByUserId(user.getUserId());
                session.setAttribute("currentPatient", patient);
            }

            redirectToDashboard(user, response, request.getContextPath());
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Login failed due to a system error: " + e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private void redirectToDashboard(User user, HttpServletResponse response, String contextPath) throws IOException {
        if (user.isAdmin()) {
            response.sendRedirect(contextPath + "/admin/dashboard");
        } else if (user.isDoctor()) {
            response.sendRedirect(contextPath + "/doctor/dashboard");
        } else {
            response.sendRedirect(contextPath + "/patient/dashboard");
        }
    }
}
