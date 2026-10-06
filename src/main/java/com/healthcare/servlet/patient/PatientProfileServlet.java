package com.healthcare.servlet.patient;

import com.healthcare.dao.PatientDAO;
import com.healthcare.dao.UserDAO;
import com.healthcare.model.Patient;
import com.healthcare.model.User;
import com.healthcare.util.DateUtil;
import com.healthcare.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * PatientProfileServlet manages demographic profile updates and password changes.
 */
@WebServlet("/patient/profile")
public class PatientProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PatientDAO patientDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        this.patientDAO = new PatientDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        Patient patient = patientDAO.getPatientByUserId(currentUser.getUserId());

        request.setAttribute("patient", patient);
        request.getRequestDispatcher("/patient/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        Patient patient = patientDAO.getPatientByUserId(currentUser.getUserId());

        String action = request.getParameter("action");

        if ("updateProfile".equalsIgnoreCase(action)) {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String dobStr = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            String bloodGroup = request.getParameter("bloodGroup");
            String address = request.getParameter("address");
            String emergency = request.getParameter("emergencyContact");

            try {
                if (!ValidationUtil.isNonEmpty(name)) {
                    throw new Exception("Name cannot be empty.");
                }

                patient.setName(name.trim());
                patient.setPhone(phone != null ? phone.trim() : "");
                patient.setDateOfBirth(DateUtil.parseSqlDate(dobStr));
                patient.setGender(gender);
                patient.setBloodGroup(bloodGroup);
                patient.setAddress(address);
                patient.setEmergencyContact(emergency);

                patientDAO.updatePatientProfile(patient);
                currentUser.setName(patient.getName());
                currentUser.setPhone(patient.getPhone());
                session.setAttribute("currentUser", currentUser);
                session.setAttribute("currentPatient", patient);

                request.getSession().setAttribute("successMessage", "Profile updated successfully.");
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Failed to update profile: " + e.getMessage());
            }
        } else if ("changePassword".equalsIgnoreCase(action)) {
            String oldPass = request.getParameter("oldPassword");
            String newPass = request.getParameter("newPassword");
            String confirmPass = request.getParameter("confirmPassword");

            try {
                User user = userDAO.authenticate(currentUser.getEmail(), oldPass);
                if (user == null) {
                    throw new Exception("Current password is incorrect.");
                }
                if (newPass == null || newPass.length() < 6) {
                    throw new Exception("New password must be at least 6 characters.");
                }
                if (!newPass.equals(confirmPass)) {
                    throw new Exception("New passwords do not match.");
                }

                userDAO.updatePassword(currentUser.getUserId(), newPass);
                request.getSession().setAttribute("successMessage", "Password updated successfully.");
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Password update error: " + e.getMessage());
            }
        }

        response.sendRedirect(request.getContextPath() + "/patient/profile");
    }
}
