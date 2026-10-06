<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Patient Dashboard - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="dashboard"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <div>
                    <h1 style="font-size: 1.85rem;">Hello, ${patient.name}</h1>
                    <p style="color: var(--text-muted);">Blood Group: <strong>${patient.bloodGroup != null ? patient.bloodGroup : 'N/A'}</strong> &bull; Phone: ${patient.phone}</p>
                </div>
                <div>
                    <a href="${pageContext.request.contextPath}/patient/search-doctors" class="btn btn-primary">
                        <i class="fa-solid fa-calendar-plus"></i> Book Consultation
                    </a>
                </div>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <!-- Patient Stats Grid -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div>
                        <div class="stat-val">${activeAppts}</div>
                        <div class="stat-label">Active / Scheduled Consultations</div>
                    </div>
                    <div class="stat-icon blue"><i class="fa-solid fa-calendar-check"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-val">${completedAppts}</div>
                        <div class="stat-label">Completed Consultations</div>
                    </div>
                    <div class="stat-icon green"><i class="fa-solid fa-circle-check"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-val">${records.size()}</div>
                        <div class="stat-label">Digital Prescriptions</div>
                    </div>
                    <div class="stat-icon" style="background-color: #cffafe; color: #0891b2;"><i class="fa-solid fa-file-prescription"></i></div>
                </div>
            </div>

            <!-- Appointments & Quick Search -->
            <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 2rem; margin-top: 2rem;">
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.25rem;">
                        <h3>Upcoming & Recent Appointments</h3>
                        <a href="${pageContext.request.contextPath}/patient/appointments" class="btn btn-outline btn-sm">All History</a>
                    </div>
                    <div class="table-container" style="border: none; box-shadow: none;">
                        <table>
                            <thead>
                                <tr>
                                    <th>Doctor</th>
                                    <th>Specialty</th>
                                    <th>Schedule</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty appointments}">
                                        <c:forEach var="a" items="${appointments}" begin="0" end="4">
                                            <tr>
                                                <td><strong>${a.doctorName}</strong></td>
                                                <td><span class="badge badge-confirmed">${a.doctorSpecialization}</span></td>
                                                <td>
                                                    ${a.appointmentDate}<br/>
                                                    <small style="color: var(--text-muted);">${a.appointmentTime}</small>
                                                </td>
                                                <td>
                                                    <span class="badge badge-${a.status.toLowerCase()}">${a.status}</span>
                                                </td>
                                                <td>
                                                    <c:if test="${a.completed}">
                                                        <a href="${pageContext.request.contextPath}/patient/feedback?appointmentId=${a.appointmentId}" class="btn btn-outline-primary btn-sm">
                                                            <i class="fa-solid fa-star"></i> Rate
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${a.pending || a.confirmed}">
                                                        <form action="${pageContext.request.contextPath}/patient/appointments" method="POST" style="display: inline;">
                                                            <input type="hidden" name="action" value="cancel"/>
                                                            <input type="hidden" name="appointmentId" value="${a.appointmentId}"/>
                                                            <button type="submit" class="btn btn-outline btn-sm" data-confirm="Cancel this appointment?">Cancel</button>
                                                        </form>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="5" style="text-align: center; color: var(--text-muted); padding: 2rem;">No appointments yet. Click "Book Consultation" to get started!</td></tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Featured Specialists -->
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.25rem;">
                        <h3>Available Specialists</h3>
                        <a href="${pageContext.request.contextPath}/patient/search-doctors" class="btn btn-outline btn-sm">Explore</a>
                    </div>
                    <div style="display: flex; flex-direction: column; gap: 1rem;">
                        <c:forEach var="doc" items="${featuredDoctors}">
                            <div style="display: flex; align-items: center; justify-content: space-between; padding: 0.85rem; border-radius: var(--radius-md); background: #f8fafc;">
                                <div style="display: flex; align-items: center; gap: 0.75rem;">
                                    <div class="avatar" style="width: 38px; height: 38px; font-size: 0.95rem;">${doc.name.substring(0, 1)}</div>
                                    <div>
                                        <div style="font-weight: 700; font-size: 0.9rem;">${doc.name}</div>
                                        <div style="font-size: 0.8rem; color: var(--text-muted);">${doc.specialization} &bull; $${doc.consultationFee}</div>
                                    </div>
                                </div>
                                <a href="${pageContext.request.contextPath}/patient/book-appointment?doctorId=${doc.doctorId}" class="btn btn-primary btn-sm">Book</a>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
