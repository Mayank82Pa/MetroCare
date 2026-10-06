package com.healthcare.servlet.admin;

import com.healthcare.dao.AnalyticsDAO;
import com.healthcare.dao.AppointmentDAO;
import com.healthcare.dao.DoctorDAO;
import com.healthcare.model.AnalyticsSummary;
import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * AdminDashboardServlet loads metrics, analytics summaries, recent appointments, and doctors.
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AnalyticsDAO analyticsDAO;
    private AppointmentDAO appointmentDAO;
    private DoctorDAO doctorDAO;

    @Override
    public void init() throws ServletException {
        this.analyticsDAO = new AnalyticsDAO();
        this.appointmentDAO = new AppointmentDAO();
        this.doctorDAO = new DoctorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AnalyticsSummary analytics = analyticsDAO.getAdminAnalytics();
            List<Appointment> recentAppointments = appointmentDAO.getAllAppointments();
            if (recentAppointments.size() > 5) {
                recentAppointments = recentAppointments.subList(0, 5);
            }
            List<Doctor> topDoctors = doctorDAO.getAllDoctors();

            request.setAttribute("analytics", analytics);
            request.setAttribute("recentAppointments", recentAppointments);
            request.setAttribute("topDoctors", topDoctors);

            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading admin dashboard: " + e.getMessage());
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        }
    }
}
