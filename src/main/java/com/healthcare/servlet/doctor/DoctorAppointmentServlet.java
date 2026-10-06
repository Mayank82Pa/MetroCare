package com.healthcare.servlet.doctor;

import com.healthcare.dao.AppointmentDAO;
import com.healthcare.dao.DoctorDAO;
import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;
import com.healthcare.model.User;
import com.healthcare.util.NotificationThreadService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * DoctorAppointmentServlet handles viewing queue, confirming, cancelling, or completing appointments.
 */
@WebServlet("/doctor/appointments")
public class DoctorAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AppointmentDAO appointmentDAO;
    private DoctorDAO doctorDAO;

    @Override
    public void init() throws ServletException {
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

        List<Appointment> appointments = appointmentDAO.getAppointmentsByDoctor(doctor.getDoctorId());
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/doctor/appointments.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        Doctor doctor = doctorDAO.getDoctorByUserId(currentUser.getUserId());

        String action = request.getParameter("action");
        String appointmentIdStr = request.getParameter("appointmentId");

        try {
            int appointmentId = Integer.parseInt(appointmentIdStr);
            Appointment appt = appointmentDAO.getAppointmentById(appointmentId);

            if (appt != null && appt.getDoctorId() == doctor.getDoctorId()) {
                if ("confirm".equalsIgnoreCase(action)) {
                    appointmentDAO.updateStatus(appointmentId, "CONFIRMED");
                    NotificationThreadService.getInstance().sendStatusUpdateNotification(
                            appointmentId, appt.getPatientEmail(), "CONFIRMED", doctor.getName());
                    request.getSession().setAttribute("successMessage", "Appointment #" + appointmentId + " confirmed.");
                } else if ("cancel".equalsIgnoreCase(action)) {
                    appointmentDAO.cancelAppointment(appointmentId, doctor.getDoctorId(), false);
                    NotificationThreadService.getInstance().sendStatusUpdateNotification(
                            appointmentId, appt.getPatientEmail(), "CANCELLED", doctor.getName());
                    request.getSession().setAttribute("successMessage", "Appointment #" + appointmentId + " cancelled.");
                }
            }
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Action failed: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/doctor/appointments");
    }
}
