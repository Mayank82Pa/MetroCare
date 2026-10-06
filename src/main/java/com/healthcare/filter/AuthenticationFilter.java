package com.healthcare.filter;

import com.healthcare.model.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Authentication and Role-Based Authorization Filter.
 * Demonstrates: Servlet Filter Lifecycle, Session Management, Security Interception.
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/admin/*", "/doctor/*", "/patient/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Prevent browser caching of protected pages
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setDateHeader("Expires", 0);

        HttpSession session = httpRequest.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("currentUser") : null;
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());

        if (loggedInUser == null) {
            httpRequest.getSession(true).setAttribute("errorMessage", "Please log in to access this area.");
            httpResponse.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        // Role-based Access Control (RBAC)
        String role = loggedInUser.getRole();

        if (path.startsWith("/admin/") && !"ADMIN".equalsIgnoreCase(role)) {
            httpResponse.sendRedirect(contextPath + "/unauthorized.jsp");
            return;
        }

        if (path.startsWith("/doctor/") && !"DOCTOR".equalsIgnoreCase(role)) {
            httpResponse.sendRedirect(contextPath + "/unauthorized.jsp");
            return;
        }

        if (path.startsWith("/patient/") && !"PATIENT".equalsIgnoreCase(role)) {
            httpResponse.sendRedirect(contextPath + "/unauthorized.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
