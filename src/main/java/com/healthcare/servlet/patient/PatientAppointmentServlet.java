package com.healthcare.servlet.patient;

import com.healthcare.dao.AppointmentDAO;
import com.healthcare.dao.PatientDAO;
import com.healthcare.model.Appointment;
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
 * PatientAppointmentServlet manages appointment history, cancellation, and status tracking.
 */
@WebServlet("/patient/appointments")
public class PatientAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;

    @Override
    public void init() throws ServletException {
        this.appointmentDAO = new AppointmentDAO();
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

        List<Appointment> appointments = appointmentDAO.getAppointmentsByPatient(patient.getPatientId());
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/patient/appointments.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        Patient patient = patientDAO.getPatientByUserId(currentUser.getUserId());

        String action = request.getParameter("action");
        String appointmentIdStr = request.getParameter("appointmentId");

        if ("cancel".equalsIgnoreCase(action) && appointmentIdStr != null) {
            try {
                int apptId = Integer.parseInt(appointmentIdStr);
                appointmentDAO.cancelAppointment(apptId, patient.getPatientId(), true);
                request.getSession().setAttribute("successMessage", "Appointment #" + apptId + " has been cancelled.");
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Failed to cancel appointment: " + e.getMessage());
            }
        }
        response.sendRedirect(request.getContextPath() + "/patient/appointments");
    }
}
