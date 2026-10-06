package com.healthcare.servlet.doctor;

import com.healthcare.dao.DoctorDAO;
import com.healthcare.dao.ScheduleDAO;
import com.healthcare.model.Doctor;
import com.healthcare.model.DoctorSchedule;
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
import java.sql.Time;
import java.util.List;

/**
 * DoctorScheduleServlet manages working days, start/end hours, and slot duration.
 */
@WebServlet("/doctor/schedule")
public class DoctorScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ScheduleDAO scheduleDAO;
    private DoctorDAO doctorDAO;

    @Override
    public void init() throws ServletException {
        this.scheduleDAO = new ScheduleDAO();
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

        String action = request.getParameter("action");
        String scheduleIdStr = request.getParameter("id");

        if ("delete".equalsIgnoreCase(action) && scheduleIdStr != null) {
            try {
                int schedId = Integer.parseInt(scheduleIdStr);
                scheduleDAO.deleteSchedule(schedId, doctor.getDoctorId());
                request.getSession().setAttribute("successMessage", "Schedule slot removed.");
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Failed to remove slot: " + e.getMessage());
            }
            response.sendRedirect(request.getContextPath() + "/doctor/schedule");
            return;
        }

        List<DoctorSchedule> schedules = scheduleDAO.getSchedulesByDoctor(doctor.getDoctorId());
        request.setAttribute("schedules", schedules);
        request.getRequestDispatcher("/doctor/schedule.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        Doctor doctor = doctorDAO.getDoctorByUserId(currentUser.getUserId());

        String dateStr = request.getParameter("availableDate");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String durationStr = request.getParameter("slotDurationMins");

        try {
            Date date = DateUtil.parseSqlDate(dateStr);
            Time startTime = DateUtil.parseSqlTime(startTimeStr);
            Time endTime = DateUtil.parseSqlTime(endTimeStr);
            int duration = (durationStr != null && !durationStr.isEmpty()) ? Integer.parseInt(durationStr) : 30;

            if (date == null || startTime == null || endTime == null) {
                throw new Exception("Please specify a valid date, start time, and end time.");
            }

            if (startTime.after(endTime) || startTime.equals(endTime)) {
                throw new Exception("Start time must be before end time.");
            }

            DoctorSchedule schedule = new DoctorSchedule();
            schedule.setDoctorId(doctor.getDoctorId());
            schedule.setAvailableDate(date);
            schedule.setStartTime(startTime);
            schedule.setEndTime(endTime);
            schedule.setSlotDurationMins(duration);
            schedule.setAvailable(true);

            scheduleDAO.addSchedule(schedule);
            request.getSession().setAttribute("successMessage", "Availability slot added successfully.");
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Failed to add schedule: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/doctor/schedule");
    }
}
