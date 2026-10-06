package com.healthcare.servlet.doctor;

import com.healthcare.dao.AppointmentDAO;
import com.healthcare.dao.DoctorDAO;
import com.healthcare.dao.FeedbackDAO;
import com.healthcare.dao.ScheduleDAO;
import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;
import com.healthcare.model.DoctorSchedule;
import com.healthcare.model.Feedback;
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
 * DoctorDashboardServlet displays upcoming appointments, schedule status, patient stats, and recent ratings.
 */
@WebServlet("/doctor/dashboard")
public class DoctorDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DoctorDAO doctorDAO;
    private AppointmentDAO appointmentDAO;
    private ScheduleDAO scheduleDAO;
    private FeedbackDAO feedbackDAO;

    @Override
    public void init() throws ServletException {
        this.doctorDAO = new DoctorDAO();
        this.appointmentDAO = new AppointmentDAO();
        this.scheduleDAO = new ScheduleDAO();
        this.feedbackDAO = new FeedbackDAO();
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

        session.setAttribute("currentDoctor", doctor);

        List<Appointment> appointments = appointmentDAO.getAppointmentsByDoctor(doctor.getDoctorId());
        List<DoctorSchedule> schedules = scheduleDAO.getSchedulesByDoctor(doctor.getDoctorId());
        List<Feedback> feedbackList = feedbackDAO.getFeedbackByDoctor(doctor.getDoctorId());

        long pendingCount = appointments.stream().filter(Appointment::isPending).count();
        long confirmedCount = appointments.stream().filter(Appointment::isConfirmed).count();
        long completedCount = appointments.stream().filter(Appointment::isCompleted).count();

        request.setAttribute("doctor", doctor);
        request.setAttribute("appointments", appointments);
        request.setAttribute("schedules", schedules);
        request.setAttribute("feedbackList", feedbackList);
        request.setAttribute("pendingCount", pendingCount);
        request.setAttribute("confirmedCount", confirmedCount);
        request.setAttribute("completedCount", completedCount);

        request.getRequestDispatcher("/doctor/dashboard.jsp").forward(request, response);
    }
}
