<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Doctor Dashboard - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="dashboard"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <div>
                    <h1 style="font-size: 1.85rem;">Welcome, ${doctor.name}</h1>
                    <p style="color: var(--text-muted);">${doctor.specialization} &bull; ${doctor.qualification}</p>
                </div>
                <div style="display: flex; gap: 0.75rem;">
                    <a href="${pageContext.request.contextPath}/doctor/schedule" class="btn btn-primary btn-sm">
                        <i class="fa-solid fa-clock"></i> Manage Availability
                    </a>
                </div>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <!-- Doctor Stats Grid -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div>
                        <div class="stat-val">${pendingCount}</div>
                        <div class="stat-label">Pending Requests</div>
                    </div>
                    <div class="stat-icon amber"><i class="fa-solid fa-clock"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-val">${confirmedCount}</div>
                        <div class="stat-label">Upcoming Confirmed</div>
                    </div>
                    <div class="stat-icon blue"><i class="fa-solid fa-calendar-check"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-val">${completedCount}</div>
                        <div class="stat-label">Completed Consults</div>
                    </div>
                    <div class="stat-icon green"><i class="fa-solid fa-circle-check"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-val"><fmt:formatNumber value="${doctor.averageRating}" maxFractionDigits="1"/> <i class="fa-solid fa-star stars" style="font-size: 1.2rem;"></i></div>
                        <div class="stat-label">${doctor.totalReviews} Reviews</div>
                    </div>
                    <div class="stat-icon" style="background: #fef3c7; color: #d97706;"><i class="fa-solid fa-award"></i></div>
                </div>
            </div>

            <!-- Queue & Schedules -->
            <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 2rem; margin-top: 2rem;">
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.25rem;">
                        <h3>Upcoming Patient Queue</h3>
                        <a href="${pageContext.request.contextPath}/doctor/appointments" class="btn btn-outline btn-sm">Manage Queue</a>
                    </div>
                    <div class="table-container" style="border: none; box-shadow: none;">
                        <table>
                            <thead>
                                <tr>
                                    <th>Patient</th>
                                    <th>Schedule</th>
                                    <th>Reason</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty appointments}">
                                        <c:forEach var="a" items="${appointments}">
                                            <tr>
                                                <td>
                                                    <strong>${a.patientName}</strong><br/>
                                                    <small style="color: var(--text-muted);">${a.patientPhone}</small>
                                                </td>
                                                <td>
                                                    ${a.appointmentDate}<br/>
                                                    <small style="color: var(--text-muted);">${a.appointmentTime}</small>
                                                </td>
                                                <td><span style="font-size: 0.85rem;">${a.reason}</span></td>
                                                <td>
                                                    <span class="badge badge-${a.status.toLowerCase()}">${a.status}</span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="4" style="text-align: center; color: var(--text-muted);">No appointments in your queue.</td></tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Recent Patient Reviews -->
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.25rem;">
                        <h3>Patient Reviews</h3>
                        <a href="${pageContext.request.contextPath}/doctor/feedback" class="btn btn-outline btn-sm">All</a>
                    </div>
                    <div style="display: flex; flex-direction: column; gap: 1rem;">
                        <c:choose>
                            <c:when test="${not empty feedbackList}">
                                <c:forEach var="f" items="${feedbackList}">
                                    <div style="padding: 0.85rem; border-radius: var(--radius-md); background: #f8fafc; border-left: 3px solid var(--warning);">
                                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.35rem;">
                                            <strong>${f.patientName}</strong>
                                            <span class="stars"><i class="fa-solid fa-star"></i> ${f.rating}</span>
                                        </div>
                                        <p style="font-size: 0.85rem; color: var(--text-muted); margin: 0;">"${f.comments}"</p>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p style="color: var(--text-muted); font-size: 0.9rem;">No feedback recorded yet.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
