package com.healthcare.servlet.patient;

import com.healthcare.dao.AppointmentDAO;
import com.healthcare.dao.DoctorDAO;
import com.healthcare.dao.MedicalRecordDAO;
import com.healthcare.dao.PatientDAO;
import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;
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
 * PatientDashboardServlet shows patient's upcoming appointments, recent prescriptions, and quick doctor booking shortcuts.
 */
@WebServlet("/patient/dashboard")
public class PatientDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PatientDAO patientDAO;
    private AppointmentDAO appointmentDAO;
    private MedicalRecordDAO medicalRecordDAO;
    private DoctorDAO doctorDAO;

    @Override
    public void init() throws ServletException {
        this.patientDAO = new PatientDAO();
        this.appointmentDAO = new AppointmentDAO();
        this.medicalRecordDAO = new MedicalRecordDAO();
        this.doctorDAO = new DoctorDAO();
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

        session.setAttribute("currentPatient", patient);

        List<Appointment> appointments = appointmentDAO.getAppointmentsByPatient(patient.getPatientId());
        List<MedicalRecord> records = medicalRecordDAO.getRecordsByPatientId(patient.getPatientId());
        List<Doctor> featuredDoctors = doctorDAO.getAllDoctors();
        if (featuredDoctors.size() > 3) {
            featuredDoctors = featuredDoctors.subList(0, 3);
        }

        long activeAppts = appointments.stream().filter(a -> a.isPending() || a.isConfirmed()).count();
        long completedAppts = appointments.stream().filter(Appointment::isCompleted).count();

        request.setAttribute("patient", patient);
        request.setAttribute("appointments", appointments);
        request.setAttribute("records", records);
        request.setAttribute("featuredDoctors", featuredDoctors);
        request.setAttribute("activeAppts", activeAppts);
        request.setAttribute("completedAppts", completedAppts);

        request.getRequestDispatcher("/patient/dashboard.jsp").forward(request, response);
    }
}
