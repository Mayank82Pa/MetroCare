package com.healthcare.servlet.doctor;

import com.healthcare.dao.DoctorDAO;
import com.healthcare.dao.FeedbackDAO;
import com.healthcare.model.Doctor;
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
 * DoctorFeedbackServlet displays ratings and patient testimonials for the doctor.
 */
@WebServlet("/doctor/feedback")
public class DoctorFeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private FeedbackDAO feedbackDAO;
    private DoctorDAO doctorDAO;

    @Override
    public void init() throws ServletException {
        this.feedbackDAO = new FeedbackDAO();
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

        List<Feedback> feedbackList = feedbackDAO.getFeedbackByDoctor(doctor.getDoctorId());
        request.setAttribute("feedbackList", feedbackList);
        request.setAttribute("doctor", doctor);
        request.getRequestDispatcher("/doctor/feedback.jsp").forward(request, response);
    }
}
