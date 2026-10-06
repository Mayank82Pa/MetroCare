package com.healthcare.servlet.patient;

import com.healthcare.dao.AppointmentDAO;
import com.healthcare.dao.FeedbackDAO;
import com.healthcare.dao.PatientDAO;
import com.healthcare.model.Appointment;
import com.healthcare.model.Feedback;
import com.healthcare.model.Patient;
import com.healthcare.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * PatientFeedbackServlet allows patients to rate and review doctors after consultation.
 */
@WebServlet("/patient/feedback")
public class PatientFeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private FeedbackDAO feedbackDAO;
    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;

    @Override
    public void init() throws ServletException {
        this.feedbackDAO = new FeedbackDAO();
        this.appointmentDAO = new AppointmentDAO();
        this.patientDAO = new PatientDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String apptIdStr = request.getParameter("appointmentId");
        if (apptIdStr == null || apptIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/patient/appointments");
            return;
        }

        int apptId = Integer.parseInt(apptIdStr);
        Appointment appointment = appointmentDAO.getAppointmentById(apptId);
        boolean alreadySubmitted = feedbackDAO.hasFeedbackForAppointment(apptId);

        request.setAttribute("appointment", appointment);
        request.setAttribute("alreadySubmitted", alreadySubmitted);
        request.getRequestDispatcher("/patient/feedback.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        Patient patient = patientDAO.getPatientByUserId(currentUser.getUserId());

        try {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comments = request.getParameter("comments");

            if (rating < 1 || rating > 5) {
                throw new Exception("Rating must be between 1 and 5.");
            }

            if (feedbackDAO.hasFeedbackForAppointment(appointmentId)) {
                throw new Exception("Feedback has already been submitted for this appointment.");
            }

            Feedback feedback = new Feedback();
            feedback.setAppointmentId(appointmentId);
            feedback.setPatientId(patient.getPatientId());
            feedback.setDoctorId(doctorId);
            feedback.setRating(rating);
            feedback.setComments(comments);

            feedbackDAO.submitFeedback(feedback);
            request.getSession().setAttribute("successMessage", "Thank you! Your feedback has been recorded.");
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Error submitting feedback: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/patient/appointments");
    }
}
