package com.healthcare.servlet.admin;

import com.healthcare.dao.AnalyticsDAO;
import com.healthcare.model.AnalyticsSummary;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AdminAnalyticsServlet provides charts and business reports.
 */
@WebServlet("/admin/analytics")
public class AdminAnalyticsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AnalyticsDAO analyticsDAO;

    @Override
    public void init() throws ServletException {
        this.analyticsDAO = new AnalyticsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AnalyticsSummary analytics = analyticsDAO.getAdminAnalytics();
        request.setAttribute("analytics", analytics);
        request.getRequestDispatcher("/admin/analytics.jsp").forward(request, response);
    }
}
