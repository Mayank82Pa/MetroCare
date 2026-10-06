<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Admin Dashboard - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="dashboard"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <div>
                    <h1 style="font-size: 1.85rem;">System Administration Dashboard</h1>
                    <p style="color: var(--text-muted);">Overview of registered users, hospital schedules, and live appointments</p>
                </div>
                <div style="display: flex; gap: 0.75rem;">
                    <a href="${pageContext.request.contextPath}/admin/doctors" class="btn btn-primary btn-sm">
                        <i class="fa-solid fa-user-doctor"></i> Onboard Doctor
                    </a>
                </div>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <!-- Metric Cards -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div>
                        <div class="stat-val">${analytics.totalUsers}</div>
                        <div class="stat-label">Total Users</div>
                    </div>
                    <div class="stat-icon blue"><i class="fa-solid fa-users"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-val">${analytics.totalDoctors}</div>
                        <div class="stat-label">Active Doctors</div>
                    </div>
                    <div class="stat-icon" style="background-color: #cffafe; color: #0891b2;"><i class="fa-solid fa-user-doctor"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-val">${analytics.totalPatients}</div>
                        <div class="stat-label">Registered Patients</div>
                    </div>
                    <div class="stat-icon green"><i class="fa-solid fa-bed-pulse"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-val">${analytics.totalAppointments}</div>
                        <div class="stat-label">All Appointments</div>
                    </div>
                    <div class="stat-icon amber"><i class="fa-solid fa-calendar-check"></i></div>
                </div>
            </div>

            <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 2rem; margin-top: 2rem;">
                <!-- Recent Appointments Table -->
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.25rem;">
                        <h3>Recent Appointments</h3>
                        <a href="${pageContext.request.contextPath}/admin/appointments" class="btn btn-outline btn-sm">View All</a>
                    </div>
                    <div class="table-container" style="border: none; box-shadow: none;">
                        <table>
                            <thead>
                                <tr>
                                    <th>Patient</th>
                                    <th>Doctor</th>
                                    <th>Date & Time</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty recentAppointments}">
                                        <c:forEach var="appt" items="${recentAppointments}">
                                            <tr>
                                                <td>
                                                    <strong>${appt.patientName}</strong><br/>
                                                    <small style="color: var(--text-muted);">${appt.patientPhone}</small>
                                                </td>
                                                <td>
                                                    ${appt.doctorName}<br/>
                                                    <small style="color: var(--primary);">${appt.doctorSpecialization}</small>
                                                </td>
                                                <td>
                                                    ${appt.appointmentDate}<br/>
                                                    <small style="color: var(--text-muted);">${appt.appointmentTime}</small>
                                                </td>
                                                <td>
                                                    <span class="badge badge-${appt.status.toLowerCase()}">${appt.status}</span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="4" style="text-align: center; color: var(--text-muted);">No appointments recorded yet.</td></tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Doctor Roster Highlights -->
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.25rem;">
                        <h3>Top Doctors</h3>
                        <a href="${pageContext.request.contextPath}/admin/doctors" class="btn btn-outline btn-sm">Manage</a>
                    </div>
                    <div style="display: flex; flex-direction: column; gap: 1rem;">
                        <c:forEach var="doc" items="${topDoctors}">
                            <div style="display: flex; align-items: center; justify-content: space-between; padding: 0.75rem; border-radius: var(--radius-md); background: #f8fafc;">
                                <div style="display: flex; align-items: center; gap: 0.75rem;">
                                    <div class="avatar" style="width: 38px; height: 38px; font-size: 0.95rem;">${doc.name.substring(0, 1)}</div>
                                    <div>
                                        <div style="font-weight: 700; font-size: 0.9rem;">${doc.name}</div>
                                        <div style="font-size: 0.8rem; color: var(--text-muted);">${doc.specialization}</div>
                                    </div>
                                </div>
                                <div style="text-align: right;">
                                    <span class="stars"><i class="fa-solid fa-star"></i> <fmt:formatNumber value="${doc.averageRating}" maxFractionDigits="1"/></span>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
