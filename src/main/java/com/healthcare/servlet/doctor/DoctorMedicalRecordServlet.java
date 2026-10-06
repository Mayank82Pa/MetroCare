package com.healthcare.servlet.doctor;

import com.healthcare.dao.AppointmentDAO;
import com.healthcare.dao.DoctorDAO;
import com.healthcare.dao.MedicalRecordDAO;
import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;
import com.healthcare.model.MedicalRecord;
import com.healthcare.model.User;
import com.healthcare.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * DoctorMedicalRecordServlet manages adding diagnosis, prescriptions, and viewing patient clinical history.
 */
@WebServlet("/doctor/medical-records")
public class DoctorMedicalRecordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private MedicalRecordDAO medicalRecordDAO;
    private AppointmentDAO appointmentDAO;
    private DoctorDAO doctorDAO;

    @Override
    public void init() throws ServletException {
        this.medicalRecordDAO = new MedicalRecordDAO();
        this.appointmentDAO = new AppointmentDAO();
        this.doctorDAO = new DoctorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        Doctor doctor = doctorDAO.getDoctorByUserId(currentUser.getUserId());

        if (doctor == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String apptIdStr = request.getParameter("appointmentId");
        if (apptIdStr != null && !apptIdStr.isEmpty()) {
            int apptId = Integer.parseInt(apptIdStr);
            Appointment appt = appointmentDAO.getAppointmentById(apptId);
            MedicalRecord existingRecord = medicalRecordDAO.getRecordByAppointmentId(apptId);
            request.setAttribute("appointment", appt);
            request.setAttribute("existingRecord", existingRecord);
        }

        List<MedicalRecord> records = medicalRecordDAO.getRecordsByDoctorId(doctor.getDoctorId());
        request.setAttribute("records", records);
        request.getRequestDispatcher("/doctor/medical_records.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        Doctor doctor = doctorDAO.getDoctorByUserId(currentUser.getUserId());

        try {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            int patientId = Integer.parseInt(request.getParameter("patientId"));
            String diagnosis = request.getParameter("diagnosis");
            String prescription = request.getParameter("prescription");
            String notes = request.getParameter("clinicalNotes");
            String followUpStr = request.getParameter("followUpDate");

            if (diagnosis == null || diagnosis.trim().isEmpty() || prescription == null || prescription.trim().isEmpty()) {
                throw new Exception("Diagnosis and Prescription are required fields.");
            }

            Date followUp = DateUtil.parseSqlDate(followUpStr);

            MedicalRecord record = new MedicalRecord();
            record.setAppointmentId(appointmentId);
            record.setPatientId(patientId);
            record.setDoctorId(doctor.getDoctorId());
            record.setDiagnosis(diagnosis.trim());
            record.setPrescription(prescription.trim());
            record.setClinicalNotes(notes);
            record.setFollowUpDate(followUp);

            medicalRecordDAO.addMedicalRecord(record);
            request.getSession().setAttribute("successMessage", "Medical record and prescription saved successfully.");
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Failed to save medical record: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/doctor/appointments");
    }
}
