package com.healthcare.servlet.patient;

import com.healthcare.dao.DoctorDAO;
import com.healthcare.model.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * DoctorSearchServlet enables patients to filter doctors by specialization, name, or keywords.
 */
@WebServlet("/patient/search-doctors")
public class DoctorSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DoctorDAO doctorDAO;

    @Override
    public void init() throws ServletException {
        this.doctorDAO = new DoctorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("query");
        String specialization = request.getParameter("specialization");

        List<Doctor> doctors;
        if ((keyword != null && !keyword.trim().isEmpty()) || (specialization != null && !specialization.trim().isEmpty())) {
            doctors = doctorDAO.searchDoctors(keyword, specialization);
        } else {
            doctors = doctorDAO.getAllDoctors();
        }

        List<String> specializations = doctorDAO.getAllSpecializations();

        request.setAttribute("doctors", doctors);
        request.setAttribute("specializations", specializations);
        request.setAttribute("selectedSpec", specialization);
        request.setAttribute("enteredQuery", keyword);

        request.getRequestDispatcher("/patient/search_doctors.jsp").forward(request, response);
    }
}
