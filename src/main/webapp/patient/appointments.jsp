<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="My Appointments - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="appointments"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <div>
                    <h1 style="font-size: 1.85rem;">My Appointment History</h1>
                    <p style="color: var(--text-muted);">Manage upcoming consultations, track status, and view doctor prescriptions</p>
                </div>
                <div>
                    <a href="${pageContext.request.contextPath}/patient/search-doctors" class="btn btn-primary btn-sm">
                        <i class="fa-solid fa-plus"></i> New Booking
                    </a>
                </div>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <div class="card">
                <div class="table-container" style="border: none; box-shadow: none;">
                    <table>
                        <thead>
                            <tr>
                                <th>Booking ID</th>
                                <th>Doctor</th>
                                <th>Specialty</th>
                                <th>Date & Time</th>
                                <th>Reason</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty appointments}">
                                    <c:forEach var="a" items="${appointments}">
                                        <tr>
                                            <td>#${a.appointmentId}</td>
                                            <td><strong>${a.doctorName}</strong></td>
                                            <td><span class="badge badge-confirmed">${a.doctorSpecialization}</span></td>
                                            <td>
                                                ${a.appointmentDate}<br/>
                                                <small style="color: var(--text-muted);">${a.appointmentTime}</small>
                                            </td>
                                            <td><span style="font-size: 0.9rem;">${a.reason}</span></td>
                                            <td>
                                                <span class="badge badge-${a.status.toLowerCase()}">${a.status}</span>
                                            </td>
                                            <td>
                                                <div style="display: flex; gap: 0.4rem; flex-wrap: wrap;">
                                                    <c:if test="${a.pending || a.confirmed}">
                                                        <form action="${pageContext.request.contextPath}/patient/appointments" method="POST" style="display: inline;">
                                                            <input type="hidden" name="action" value="cancel"/>
                                                            <input type="hidden" name="appointmentId" value="${a.appointmentId}"/>
                                                            <button type="submit" class="btn btn-danger btn-sm" data-confirm="Are you sure you want to cancel this appointment?">
                                                                Cancel
                                                            </button>
                                                        </form>
                                                    </c:if>

                                                    <c:if test="${a.completed}">
                                                        <a href="${pageContext.request.contextPath}/patient/feedback?appointmentId=${a.appointmentId}" class="btn btn-outline-primary btn-sm">
                                                            <i class="fa-solid fa-star"></i> Rate
                                                        </a>
                                                        <a href="${pageContext.request.contextPath}/patient/medical-history" class="btn btn-outline btn-sm">
                                                            <i class="fa-solid fa-file-prescription"></i> Rx
                                                        </a>
                                                    </c:if>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7" style="text-align: center; color: var(--text-muted); padding: 2rem;">No appointments booked yet.</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
