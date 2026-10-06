package com.healthcare.servlet;

import com.healthcare.dao.PatientDAO;
import com.healthcare.dao.UserDAO;
import com.healthcare.exception.ValidationException;
import com.healthcare.model.Patient;
import com.healthcare.util.DateUtil;
import com.healthcare.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * RegisterServlet handles patient self-registration with input validation and transactional record creation.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    private PatientDAO patientDAO;

    @Override
    public void init() throws ServletException {
        this.userDAO = new UserDAO();
        this.patientDAO = new PatientDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");
        String dobStr = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");
        String bloodGroup = request.getParameter("bloodGroup");
        String address = request.getParameter("address");
        String emergencyContact = request.getParameter("emergencyContact");

        try {
            // Validation
            ValidationUtil.validateRegistration(name, email, password, "PATIENT");

            if (!password.equals(confirmPassword)) {
                throw new ValidationException("Passwords do not match.");
            }

            if (userDAO.findByEmail(email) != null) {
                throw new ValidationException("An account with this email already exists.");
            }

            Patient patient = new Patient();
            patient.setName(name.trim());
            patient.setEmail(email.trim());
            patient.setPassword(password);
            patient.setPhone(phone != null ? phone.trim() : "");
            patient.setDateOfBirth(DateUtil.parseSqlDate(dobStr));
            patient.setGender(gender);
            patient.setBloodGroup(bloodGroup);
            patient.setAddress(address);
            patient.setEmergencyContact(emergencyContact);

            boolean success = patientDAO.registerPatient(patient);
            if (success) {
                request.getSession(true).setAttribute("successMessage", "Registration successful! Please login with your credentials.");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            } else {
                throw new ValidationException("Failed to register account. Please try again.");
            }
        } catch (ValidationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            preserveFormInputs(request, name, email, phone, dobStr, gender, bloodGroup, address, emergencyContact);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            preserveFormInputs(request, name, email, phone, dobStr, gender, bloodGroup, address, emergencyContact);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    private void preserveFormInputs(HttpServletRequest request, String name, String email, String phone,
                                    String dob, String gender, String blood, String address, String emergency) {
        request.setAttribute("formName", name);
        request.setAttribute("formEmail", email);
        request.setAttribute("formPhone", phone);
        request.setAttribute("formDob", dob);
        request.setAttribute("formGender", gender);
        request.setAttribute("formBlood", blood);
        request.setAttribute("formAddress", address);
        request.setAttribute("formEmergency", emergency);
    }
}
