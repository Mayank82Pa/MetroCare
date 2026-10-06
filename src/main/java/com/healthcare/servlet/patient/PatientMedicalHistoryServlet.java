package com.healthcare.servlet.patient;

import com.healthcare.dao.MedicalRecordDAO;
import com.healthcare.dao.PatientDAO;
import com.healthcare.model.MedicalRecord;
import com.healthcare.model.Patient;
import com.healthcare.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * PatientMedicalHistoryServlet allows patients to view all past clinical records, prescriptions, and follow-up dates.
 */
@WebServlet("/patient/medical-history")
public class PatientMedicalHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private MedicalRecordDAO medicalRecordDAO;
    private PatientDAO patientDAO;

    @Override
    public void init() throws ServletException {
        this.medicalRecordDAO = new MedicalRecordDAO();
        this.patientDAO = new PatientDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        Patient patient = patientDAO.getPatientByUserId(currentUser.getUserId());

        if (patient == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        List<MedicalRecord> records = medicalRecordDAO.getRecordsByPatientId(patient.getPatientId());
        request.setAttribute("records", records);
        request.getRequestDispatcher("/patient/medical_history.jsp").forward(request, response);
    }
}
