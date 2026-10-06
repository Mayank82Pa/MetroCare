<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Manage Appointments - MetroCare"/>
</jsp:include>

<div class="dashboard-layout">
    <jsp:include page="../includes/sidebar.jsp">
        <jsp:param name="active" value="appointments"/>
    </jsp:include>

    <div class="content-wrapper">
        <div class="main-content">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <div>
                    <h1 style="font-size: 1.85rem;">Hospital Appointments Master View</h1>
                    <p style="color: var(--text-muted);">Inspect, confirm, reschedule, or cancel all hospital bookings in real time</p>
                </div>
            </div>

            <jsp:include page="../includes/alerts.jsp"/>

            <div class="card">
                <div class="table-container" style="border: none; box-shadow: none;">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Patient Info</th>
                                <th>Doctor & Specialty</th>
                                <th>Schedule</th>
                                <th>Reason</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="a" items="${appointments}">
                                <tr>
                                    <td>#${a.appointmentId}</td>
                                    <td>
                                        <strong>${a.patientName}</strong><br/>
                                        <small style="color: var(--text-muted);">${a.patientPhone}</small>
                                    </td>
                                    <td>
                                        <strong>${a.doctorName}</strong><br/>
                                        <small style="color: var(--primary);">${a.doctorSpecialization}</small>
                                    </td>
                                    <td>
                                        ${a.appointmentDate}<br/>
                                        <small style="color: var(--text-muted);">${a.appointmentTime}</small>
                                    </td>
                                    <td><span style="font-size: 0.9rem;">${a.reason}</span></td>
                                    <td>
                                        <span class="badge badge-${a.status.toLowerCase()}">${a.status}</span>
                                    </td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/admin/appointments" method="POST" style="display: flex; gap: 0.35rem;">
                                            <input type="hidden" name="action" value="updateStatus"/>
                                            <input type="hidden" name="appointmentId" value="${a.appointmentId}"/>
                                            <select name="status" class="form-control" style="padding: 0.3rem 0.5rem; font-size: 0.8rem; width: 110px;">
                                                <option value="PENDING" ${a.status == 'PENDING' ? 'selected' : ''}>PENDING</option>
                                                <option value="CONFIRMED" ${a.status == 'CONFIRMED' ? 'selected' : ''}>CONFIRMED</option>
                                                <option value="COMPLETED" ${a.status == 'COMPLETED' ? 'selected' : ''}>COMPLETED</option>
                                                <option value="CANCELLED" ${a.status == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
                                            </select>
                                            <button type="submit" class="btn btn-outline-primary btn-sm">Update</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
