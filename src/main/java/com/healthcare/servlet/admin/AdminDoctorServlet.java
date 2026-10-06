package com.healthcare.servlet.admin;

import com.healthcare.dao.DoctorDAO;
import com.healthcare.dao.UserDAO;
import com.healthcare.model.Doctor;
import com.healthcare.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * AdminDoctorServlet handles onboarding new doctors, editing profiles, and viewing doctor rosters.
 */
@WebServlet("/admin/doctors")
public class AdminDoctorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DoctorDAO doctorDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        this.doctorDAO = new DoctorDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        List<String> specializations = doctorDAO.getAllSpecializations();

        request.setAttribute("doctors", doctors);
        request.setAttribute("specializations", specializations);
        request.getRequestDispatcher("/admin/doctors.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("addDoctor".equalsIgnoreCase(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            String specialization = request.getParameter("specialization");
            String qualification = request.getParameter("qualification");
            String expStr = request.getParameter("experienceYears");
            String feeStr = request.getParameter("consultationFee");
            String bio = request.getParameter("bio");

            try {
                ValidationUtil.validateRegistration(name, email, password, "DOCTOR");

                if (userDAO.findByEmail(email) != null) {
                    throw new Exception("Email already registered in system.");
                }

                int exp = (expStr != null && !expStr.isEmpty()) ? Integer.parseInt(expStr) : 0;
                BigDecimal fee = (feeStr != null && !feeStr.isEmpty()) ? new BigDecimal(feeStr) : BigDecimal.ZERO;

                Doctor doc = new Doctor();
                doc.setName(name.trim());
                doc.setEmail(email.trim());
                doc.setPassword(password);
                doc.setPhone(phone);
                doc.setSpecialization(specialization);
                doc.setQualification(qualification);
                doc.setExperienceYears(exp);
                doc.setConsultationFee(fee);
                doc.setBio(bio);

                doctorDAO.registerDoctor(doc);
                request.getSession().setAttribute("successMessage", "Doctor onboarded successfully!");
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Error onboarding doctor: " + e.getMessage());
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/doctors");
    }
}
