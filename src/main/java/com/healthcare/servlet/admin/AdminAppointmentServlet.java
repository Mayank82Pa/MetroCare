package com.healthcare.servlet.admin;

import com.healthcare.dao.AppointmentDAO;
import com.healthcare.model.Appointment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * AdminAppointmentServlet provides system-wide appointment oversight and status administration.
 */
@WebServlet("/admin/appointments")
public class AdminAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        this.appointmentDAO = new AppointmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Appointment> appointments = appointmentDAO.getAllAppointments();
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/admin/appointments.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("updateStatus".equalsIgnoreCase(action)) {
            try {
                int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
                String newStatus = request.getParameter("status");
                appointmentDAO.updateStatus(appointmentId, newStatus);
                request.getSession().setAttribute("successMessage", "Appointment #" + appointmentId + " updated to " + newStatus);
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Failed to update appointment: " + e.getMessage());
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/appointments");
    }
}
