<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Patient Appointments - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="appointments"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <div>
                    <h1 style="font-size: 1.85rem;">Patient Consultation Queue</h1>
                    <p style="color: var(--text-muted);">Confirm upcoming requests, start medical consultations, and record prescriptions</p>
                </div>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <div class="card">
                <div class="table-container" style="border: none; box-shadow: none;">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Patient Name</th>
                                <th>Contact</th>
                                <th>Date & Time</th>
                                <th>Consultation Reason</th>
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
                                            <td><strong>${a.patientName}</strong></td>
                                            <td>
                                                ${a.patientPhone}<br/>
                                                <small style="color: var(--text-muted);">${a.patientEmail}</small>
                                            </td>
                                            <td>
                                                ${a.appointmentDate}<br/>
                                                <small style="color: var(--primary);">${a.appointmentTime}</small>
                                            </td>
                                            <td><span style="font-size: 0.9rem;">${a.reason}</span></td>
                                            <td>
                                                <span class="badge badge-${a.status.toLowerCase()}">${a.status}</span>
                                            </td>
                                            <td>
                                                <div style="display: flex; gap: 0.4rem; flex-wrap: wrap;">
                                                    <c:if test="${a.pending}">
                                                        <form action="${pageContext.request.contextPath}/doctor/appointments" method="POST" style="display: inline;">
                                                            <input type="hidden" name="action" value="confirm"/>
                                                            <input type="hidden" name="appointmentId" value="${a.appointmentId}"/>
                                                            <button type="submit" class="btn btn-success btn-sm"><i class="fa-solid fa-check"></i> Accept</button>
                                                        </form>
                                                        <form action="${pageContext.request.contextPath}/doctor/appointments" method="POST" style="display: inline;">
                                                            <input type="hidden" name="action" value="cancel"/>
                                                            <input type="hidden" name="appointmentId" value="${a.appointmentId}"/>
                                                            <button type="submit" class="btn btn-danger btn-sm" data-confirm="Decline this appointment?"><i class="fa-solid fa-xmark"></i> Decline</button>
                                                        </form>
                                                    </c:if>

                                                    <c:if test="${a.confirmed}">
                                                        <a href="${pageContext.request.contextPath}/doctor/medical-records?appointmentId=${a.appointmentId}" class="btn btn-primary btn-sm">
                                                            <i class="fa-solid fa-notes-medical"></i> Add Diagnosis
                                                        </a>
                                                        <form action="${pageContext.request.contextPath}/doctor/appointments" method="POST" style="display: inline;">
                                                            <input type="hidden" name="action" value="cancel"/>
                                                            <input type="hidden" name="appointmentId" value="${a.appointmentId}"/>
                                                            <button type="submit" class="btn btn-outline btn-sm" data-confirm="Cancel this appointment?">Cancel</button>
                                                        </form>
                                                    </c:if>

                                                    <c:if test="${a.completed}">
                                                        <a href="${pageContext.request.contextPath}/doctor/medical-records?appointmentId=${a.appointmentId}" class="btn btn-outline-primary btn-sm">
                                                            <i class="fa-solid fa-file-lines"></i> View Record
                                                        </a>
                                                    </c:if>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7" style="text-align: center; color: var(--text-muted); padding: 2rem;">No appointments found.</td></tr>
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
