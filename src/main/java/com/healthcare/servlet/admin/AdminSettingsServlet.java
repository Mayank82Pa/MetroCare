package com.healthcare.servlet.admin;

import com.healthcare.dao.SettingsDAO;
import com.healthcare.model.SystemSetting;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * AdminSettingsServlet manages application configuration keys and parameters.
 */
@WebServlet("/admin/settings")
public class AdminSettingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SettingsDAO settingsDAO;

    @Override
    public void init() throws ServletException {
        this.settingsDAO = new SettingsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<SystemSetting> settings = settingsDAO.getAllSettings();
        request.setAttribute("settings", settings);
        request.getRequestDispatcher("/admin/settings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<SystemSetting> settings = settingsDAO.getAllSettings();
        try {
            for (SystemSetting setting : settings) {
                String newVal = request.getParameter("setting_" + setting.getSettingKey());
                if (newVal != null) {
                    settingsDAO.updateSetting(setting.getSettingKey(), newVal.trim());
                }
            }
            request.getSession().setAttribute("successMessage", "System settings updated successfully.");
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Failed to update settings: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/admin/settings");
    }
}
