package com.healthcare.servlet.patient;

import com.healthcare.dao.AppointmentDAO;
import com.healthcare.dao.DoctorDAO;
import com.healthcare.dao.PatientDAO;
import com.healthcare.dao.ScheduleDAO;
import com.healthcare.exception.ValidationException;
import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;
import com.healthcare.model.DoctorSchedule;
import com.healthcare.model.Patient;
import com.healthcare.model.User;
import com.healthcare.util.DateUtil;
import com.healthcare.util.NotificationThreadService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * BookAppointmentServlet handles slot discovery, conflict verification, and appointment creation.
 */
@WebServlet("/patient/book-appointment")
public class BookAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DoctorDAO doctorDAO;
    private PatientDAO patientDAO;
    private ScheduleDAO scheduleDAO;
    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        this.doctorDAO = new DoctorDAO();
        this.patientDAO = new PatientDAO();
        this.scheduleDAO = new ScheduleDAO();
        this.appointmentDAO = new AppointmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String doctorIdStr = request.getParameter("doctorId");
        String dateStr = request.getParameter("date");

        if (doctorIdStr == null || doctorIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/patient/search-doctors");
            return;
        }

        try {
            int doctorId = Integer.parseInt(doctorIdStr);
            Doctor doctor = doctorDAO.getDoctorById(doctorId);
            if (doctor == null) {
                response.sendRedirect(request.getContextPath() + "/patient/search-doctors");
                return;
            }

            List<DoctorSchedule> upcomingSchedules = scheduleDAO.getSchedulesByDoctor(doctorId);
            List<String> availableSlots = new ArrayList<>();
            DoctorSchedule selectedSchedule = null;

            if (dateStr != null && !dateStr.isEmpty()) {
                Date selectedDate = DateUtil.parseSqlDate(dateStr);
                if (selectedDate != null) {
                    selectedSchedule = scheduleDAO.getScheduleForDoctorOnDate(doctorId, selectedDate);
                    if (selectedSchedule != null) {
                        List<String> allSlots = selectedSchedule.generateTimeSlots();
                        List<Time> bookedTimes = appointmentDAO.getBookedTimesForDoctorOnDate(doctorId, selectedDate);
                        List<String> bookedStrings = new ArrayList<>();
                        for (Time t : bookedTimes) {
                            bookedStrings.add(t.toString().substring(0, 5));
                        }
                        for (String slot : allSlots) {
                            if (!bookedStrings.contains(slot)) {
                                availableSlots.add(slot);
                            }
                        }
                    }
                }
            }

            request.setAttribute("doctor", doctor);
            request.setAttribute("upcomingSchedules", upcomingSchedules);
            request.setAttribute("selectedSchedule", selectedSchedule);
            request.setAttribute("availableSlots", availableSlots);
            request.setAttribute("selectedDateStr", dateStr);

            request.getRequestDispatcher("/patient/book_appointment.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading booking details: " + e.getMessage());
            request.getRequestDispatcher("/patient/search_doctors.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        Patient patient = patientDAO.getPatientByUserId(currentUser.getUserId());

        String doctorIdStr = request.getParameter("doctorId");
        String scheduleIdStr = request.getParameter("scheduleId");
        String dateStr = request.getParameter("appointmentDate");
        String timeStr = request.getParameter("appointmentTime");
        String reason = request.getParameter("reason");

        try {
            if (doctorIdStr == null || dateStr == null || timeStr == null || reason == null || reason.trim().isEmpty()) {
                throw new ValidationException("Please fill out all required booking fields.");
            }

            int doctorId = Integer.parseInt(doctorIdStr);
            Integer scheduleId = (scheduleIdStr != null && !scheduleIdStr.isEmpty()) ? Integer.parseInt(scheduleIdStr) : null;
            Date apptDate = DateUtil.parseSqlDate(dateStr);
            Time apptTime = DateUtil.parseSqlTime(timeStr);

            if (apptDate == null || apptTime == null) {
                throw new ValidationException("Invalid date or time format.");
            }

            Doctor doctor = doctorDAO.getDoctorById(doctorId);
            if (doctor == null) {
                throw new ValidationException("Selected doctor not found.");
            }

            Appointment appt = new Appointment();
            appt.setPatientId(patient.getPatientId());
            appt.setDoctorId(doctorId);
            appt.setScheduleId(scheduleId);
            appt.setAppointmentDate(apptDate);
            appt.setAppointmentTime(apptTime);
            appt.setReason(reason.trim());
            appt.setStatus("PENDING");

            boolean booked = appointmentDAO.bookAppointment(appt);
            if (booked) {
                // Trigger asynchronous email/SMS notification thread
                NotificationThreadService.getInstance().sendAppointmentBookingNotification(
                        appt, currentUser.getEmail(), doctor.getName());

                request.getSession().setAttribute("successMessage",
                        "Appointment booked successfully with " + doctor.getName() + " on " + dateStr + " at " + timeStr);
                response.sendRedirect(request.getContextPath() + "/patient/appointments");
            } else {
                throw new ValidationException("Could not book appointment. Please select another slot.");
            }
        } catch (ValidationException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/patient/book-appointment?doctorId=" + doctorIdStr + "&date=" + dateStr);
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Booking error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/patient/search-doctors");
        }
    }
}
